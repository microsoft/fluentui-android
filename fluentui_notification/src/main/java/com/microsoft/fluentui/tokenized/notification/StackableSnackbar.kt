package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.microsoft.fluentui.tokenized.controls.BasicCard
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.util.clickableWithTooltip
import com.microsoft.fluentui.util.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    suspend fun addCard(card: CardModel, maxSize: Int = 5) {
        // add to front so index 0 is top
        snapshotStateList.add(0, card)
        if(snapshotStateList.size >= maxSize) {
            popBack()
        }
    }

    fun removeCardById(id: String) {
        snapshotStateList.removeAll { it.id == id }
    }

    fun popFront(): CardModel? {
        return if (snapshotStateList.isNotEmpty()) snapshotStateList.removeAt(0) else null
    }

    suspend fun popBack(): CardModel? {
        snapshotStateList.get(snapshotStateList.size -1).inRemoval = true
        delay(360)
        return if (snapshotStateList.isNotEmpty()) snapshotStateList.removeAt(snapshotStateList.size - 1) else null
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
    stackAbove: Boolean = true, // if true, cards stack above each other (negative offset)
    contentModifier: Modifier = Modifier
) {
    // Total stack height: cardHeight + (count-1) * peekHeight
    val count by remember { derivedStateOf { state.size() } }

    val targetHeight by remember(count, cardHeight, peekHeight) {
        mutableStateOf(cardHeight + (if (count > 0) (count - 1) * peekHeight else 0.dp))
    }

    // Smoothly animate stack height when count changes
    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Box(
        modifier = modifier
            .width(cardWidth)
            .height(animatedStackHeight)
            .wrapContentHeight(
                align = if (stackAbove) {
                    Alignment.Bottom
                } else {
                    Alignment.Top
                }
            )
            .clickableWithTooltip(
                onClick = {},
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

@Composable
private fun CardStackItem(
    model: CardModel,
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
    val targetYOffset = with(LocalDensity.current) { (index * peekHeight).toPx() }
    val animatedYOffset = remember { Animatable(targetYOffset) }
    LaunchedEffect(index) {
        animatedYOffset.animateTo(
            targetYOffset * (if (stackAbove) -1f else 1f),
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }

    // Slide In Animation TODO: Add configurations
    val slideInProgress = remember { Animatable(1f) } // 1 = offscreen right, 0 = in place
    LaunchedEffect(model.id) {
        // if this is top when added, slide from right
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
        if(model.inRemoval) {
            if (!isTop) {
                opacityProgress.snapTo(1f)
                opacityProgress.animateTo(
                    0f,
                    animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
                )
            } else {
                slideInProgress.snapTo(1f)
            }
        }
    }

    val swipeX = remember { Animatable(0f) }
    val removalJob = remember { mutableStateOf<Job?>(null) }

    val offsetX: Float = if (isTop) swipeX.value else 0f
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
            .then(if (isTop) Modifier.pointerInput(model.id) {
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
                .shadow(elevation = if (isTop) 12.dp else 4.dp, shape = RoundedCornerShape(12.dp))
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
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        CardStack(
            state = stackState,
            modifier = Modifier.padding(16.dp),
            cardWidth = 340.dp,
            cardHeight = 180.dp,
            peekHeight = 10.dp,
            stackAbove = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Button(onClick = {
                val id = java.util.UUID.randomUUID().toString()
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
                stackState.popFront()
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