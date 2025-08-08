package com.microsoft.fluentui.tokenized.notification

import android.view.Gravity
import android.view.WindowManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.util.clickableWithTooltip
import kotlinx.coroutines.launch
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.math.abs
import kotlin.math.roundToInt

/** Single card model contains an id and a composable content lambda. */
data class CardModel(
    val id: String,
    var inRemoval: Boolean = false,
    val content: @Composable () -> Unit
)

/** Public state object to control the stack. */
class CardStackState(
    internal val cards: MutableList<CardModel>
) {
    internal val snapshotStateList = mutableStateListOf<CardModel>().apply { addAll(cards) }
    internal var expanded by mutableStateOf(false)

    suspend fun addCard(card: CardModel, maxSize: Int = 6) {
        snapshotStateList.add(0, card)
        if (snapshotStateList.size >= maxSize) {
            popBack()
        }
    }

    fun removeCardById(id: String) {
        snapshotStateList.removeAll { it.id == id }
    }

    suspend fun popFront(): CardModel? {
        if (snapshotStateList.isEmpty()) return null
        snapshotStateList.get(0).inRemoval = true
        delay(360)
        return if (snapshotStateList.isNotEmpty()) snapshotStateList.removeAt(0) else null
    }

    suspend fun popBack(): CardModel? {
        if (snapshotStateList.isEmpty()) return null
        snapshotStateList.get(snapshotStateList.size - 1).inRemoval = true
        delay(360)
        return snapshotStateList.removeAt(snapshotStateList.size - 1)
    }

    fun size(): Int = snapshotStateList.size
}

@Composable
fun rememberCardStackState(initial: List<CardModel> = emptyList()): CardStackState {
    return remember { CardStackState(initial.toMutableList()) }
}

/**
 * CardStack composable.
 * @param state state controlling cards
 * @param cardWidth fixed width of the stack
 * @param cardHeight base height for each card
 * @param peekHeight how much of the previous card is visible under the top card
 * @param contentModifier modifier applied to each card slot
 */
@Composable
fun CardStack(
    state: CardStackState,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 320.dp,
    cardHeight: Dp = 160.dp,
    peekHeight: Dp = 10.dp,
    stackOffset: Offset = Offset(0f, 0f), // offset for the stack position
    stackAbove: Boolean = true, // if true, cards stack above each other (negative offset)
    contentModifier: Modifier = Modifier
) {
    // Total stack height: cardHeight + (count-1) * peekHeight
    // Total in expanded state: cardHeight * count + (count-1) * peekHeight
    val count by remember { derivedStateOf { state.size() } }

    val targetHeight by remember(count, cardHeight, peekHeight, state.expanded) {
        mutableStateOf(
            if (state.expanded || true) {
                cardHeight * count + (if (count > 0) (count - 1) * peekHeight else 0.dp)
            } else {
                cardHeight + (if (count > 0) (count - 1) * peekHeight else 0.dp)
            }
        )
    }

    // Smoothly animate stack height when count changes
    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        val window = (LocalView.current.parent as? DialogWindowProvider)?.window
        SideEffect {
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                window.setDimAmount(0f)
                window.setGravity(Gravity.BOTTOM)
                window.attributes.y = stackOffset.y.roundToInt()
                window.attributes.x = stackOffset.x.roundToInt()
                window.attributes.y = 200
            }
        }
        Box(
            modifier = modifier
                .width(cardWidth)
                .height(if (state.snapshotStateList.size == 0) 0.dp else animatedStackHeight)
                .wrapContentHeight(
                    align = if (stackAbove) {
                        Alignment.Bottom
                    } else {
                        Alignment.Top
                    }
                )
                .clickableWithTooltip(
                    onClick = {
                        state.expanded = !state.expanded
                    },
                    tooltipText = "Notification Stack",
                )
        ) {
            // Show cards in reverse visual order: bottom-most drawn first
            val listSnapshot = state.snapshotStateList.toList()

            listSnapshot.reversed().forEachIndexed { visuallyReversedIndex, cardModel ->
                // compute logical index from top (0 is top)
                val logicalIndex = listSnapshot.size - 1 - visuallyReversedIndex
                val isTop = logicalIndex == 0

                key(cardModel.id) {
                    // Each card will be placed offset from top by logicalIndex * peekHeight
                    CardStackItem(
                        model = cardModel,
                        expanded = state.expanded,
                        index = logicalIndex,
                        isTop = isTop,
                        cardHeight = cardHeight,
                        peekHeight = peekHeight,
                        cardWidth = cardWidth,
                        onSwipedAway = { idToRemove -> state.removeCardById(idToRemove) },
                        stackAbove = stackAbove,
                        contentModifier = contentModifier
                    )
                }
            }
        }
    }
}

@Composable
private fun CardStackItem(
    model: CardModel,
    expanded: Boolean,
    index: Int,
    isTop: Boolean,
    cardWidth: Dp,
    cardHeight: Dp,
    peekHeight: Dp,
    onSwipedAway: (String) -> Unit,
    stackAbove: Boolean = false,
    contentModifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    // Card Adjust Animation
    val targetYOffset =
        mutableStateOf(with(LocalDensity.current) { if (expanded) (index * (peekHeight + cardHeight)).toPx() else (index * peekHeight).toPx() })
    val animatedYOffset = remember { Animatable(targetYOffset.value) }
    LaunchedEffect(index, expanded) {
        animatedYOffset.animateTo(
            targetYOffset.value * (if (stackAbove) -1f else 1f),
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }

    // Slide In Animation TODO: Add configurations
    val slideInProgress = remember { Animatable(1f) } // 1 = offscreen right, 0 = in place
    LaunchedEffect(model.id) {
        if (isTop) {
            slideInProgress.snapTo(1f)
            slideInProgress.animateTo(
                0f,
                animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
            )
        } else {
            slideInProgress.snapTo(0f)
        }
    }

    // Fade Out Animation  TODO: Add configurations
    val opacityProgress = remember { Animatable(1f) }
    LaunchedEffect(model.inRemoval) {
        if (model.inRemoval) {
            opacityProgress.snapTo(1f)
            opacityProgress.animateTo(
                0f,
                animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
            )
        }
    }

    val swipeX = remember { Animatable(0f) }

    val offsetX: Float = if (isTop || expanded) swipeX.value else 0f
    val localDensity = LocalDensity.current
    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    offsetX.roundToInt() + (slideInProgress.value * with(localDensity) { 200.dp.toPx() }).roundToInt(),
                    animatedYOffset.value.roundToInt()
                )
            }
            .graphicsLayer(
                alpha = opacityProgress.value
            )
            .width(cardWidth)
            .height(cardHeight)
            .padding(horizontal = 0.dp)
            .then(if (isTop || expanded) Modifier.pointerInput(model.id) {
                detectDragGestures(
                    onDragStart = { /* no-op */ },
                    onDragEnd = {
                        // decide threshold
                        val threshold = with(localDensity) { (cardWidth / 4).toPx() }
                        scope.launch {
                            if (abs(swipeX.value) > threshold) {
                                // animate off screen in the drag direction then remove
                                val target =
                                    if (swipeX.value > 0) with(localDensity) { cardWidth.toPx() * 1.2f } else -with(
                                        localDensity
                                    ) { cardWidth.toPx() * 1.2f }
                                swipeX.animateTo(
                                    target,
                                    animationSpec = tween(
                                        durationMillis = 240,
                                        easing = FastOutLinearInEasing
                                    )
                                )
                                // remove after animation
                                onSwipedAway(model.id)
                            } else {
                                // return to center
                                swipeX.animateTo(
                                    0f,
                                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                                )
                            }
                        }
                    },
                    onDragCancel = {
                        scope.launch {
                            swipeX.animateTo(
                                0f,
                                animationSpec = spring(stiffness = Spring.StiffnessMedium)
                            )
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            swipeX.snapTo(swipeX.value + dragAmount.x)
                        }
                    }
                )
            } else Modifier)
    ) {
        // Card visuals TODO: Replace with card composable
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    elevation = if (isTop || expanded) 12.dp else 4.dp,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(width = 1.dp, color = Color(0x22000000), shape = RoundedCornerShape(12.dp))
                .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                .then(contentModifier),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                model.content()
            }
        }
    }
}

@Composable
fun DemoCardStack() {
    val stackState = rememberCardStackState()
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        CardStack(
            state = stackState,
            modifier = Modifier.padding(16.dp),
            cardWidth = 340.dp,
            cardHeight = 100.dp,
            peekHeight = 10.dp,
            stackAbove = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Button(onClick = {
                val id = UUID.randomUUID().toString()
                scope.launch {
                    stackState.addCard(CardModel(id = id) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            BasicText("Card: $id")
                            BasicText("Some detail here")
                        }
                    })
                }
            }, text = "Add card")

            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = {
                scope.launch {
                    stackState.popFront()
                }
            }, text = "Remove top card")
            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = {
                scope.launch {
                    stackState.popBack()
                }
            }, text = "Remove last card")
        }
    }
}