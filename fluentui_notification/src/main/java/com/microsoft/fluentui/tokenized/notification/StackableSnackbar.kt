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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt

// Constants
private const val FADE_OUT_DURATION = 350 // milliseconds
private const val STACKED_WIDTH_SCALE_FACTOR = 0.95f // Scale factor for stacked cards

/** Single card model contains an id and a composable content lambda. */
data class CardModel(
    val id: String,
    var inRemoval: MutableState<Boolean> = mutableStateOf(false),
    var isHidden: MutableState<Boolean> = mutableStateOf(false),
    val content: @Composable () -> Unit
)

/** Public state object to control the stack. */
class CardStackState(
    internal val cards: MutableList<CardModel>,
    internal val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default),
    internal val maxCollapsedSize: Int = 5,
    internal val maxExpandedSize: Int = 10
) {
    internal val snapshotStateList = mutableStateListOf<CardModel>().apply { addAll(cards) }
    internal var expanded by mutableStateOf(false)
    internal val maxSize =
        max(maxCollapsedSize, maxExpandedSize) // All cards above this will be deleted

    fun addCard(card: CardModel) {
        snapshotStateList.add(card)
        val maxSize = if (expanded) maxExpandedSize else maxCollapsedSize
        scope.launch {
            if (snapshotStateList.count { !it.inRemoval.value } > maxSize) {
                popBack()
                //delay(10) // TODO: Check without delay
            }
        }
    }

    fun removeCardById(id: String) {
        snapshotStateList.removeAll { it.id == id }
    }

    fun expand() {
        if (!expanded) {
            expanded = true
            val maxSize = maxExpandedSize

            scope.launch {
                while (snapshotStateList.count { !it.inRemoval.value } > maxSize) {
                    popBack()
                    delay(10) // prevent tight loop
                }
            }
        }
    }

    fun collapse() {
        if (expanded) {
            expanded = false
            val maxSize = maxCollapsedSize

            scope.launch {
                while (snapshotStateList.count { !it.inRemoval.value } > maxSize) {
                    popBack()
                    delay(10) // prevent tight loop
                }
            }
        }
    }

    fun toggleExpanded() {
        if (expanded) {
            collapse()
        } else {
            expand()
        }
    }

    fun popAt(index: Int): CardModel? {
        if (snapshotStateList.isEmpty() || index !in snapshotStateList.indices) return null
        val poppedCardModel: CardModel = snapshotStateList[index]
        if (poppedCardModel.inRemoval.value) return null
        scope.launch {
            snapshotStateList[index].inRemoval.value = true
            delay(FADE_OUT_DURATION.toLong())
            val currentIndex = snapshotStateList.indexOfFirst { it.id == poppedCardModel.id }
            if (currentIndex != -1) {
                snapshotStateList.removeAt(currentIndex)
            }
        }
        return poppedCardModel
    }

    fun popBack(): CardModel? {
        val index = snapshotStateList.indexOfFirst { !it.inRemoval.value }
        return if (index != -1) popAt(index) else null
    }

    fun popFront(): CardModel? {
        val index = snapshotStateList.indexOfLast { !it.inRemoval.value }
        return if (index != -1) popAt(index) else null
    }

    /**
     * Hides the card at the specified index.
     * @param index index of the card to hide
     * @return the hidden card or null if index is invalid
     */
    fun hideAt(index: Int): CardModel? {
        if (snapshotStateList.isEmpty() || index !in snapshotStateList.indices) return null
        val card = snapshotStateList[index]
        if (card.isHidden.value) return null
        scope.launch {
            card.inRemoval.value = true // reuse the same animation trigger state as pop
            delay(FADE_OUT_DURATION.toLong())
            card.isHidden.value = true
            card.inRemoval.value = false // reset animation flag
        }
        return card
    }

    fun hideFront(): CardModel? {
        val index = snapshotStateList.indexOfFirst { !it.isHidden.value && !it.inRemoval.value }
        return if (index != -1) hideAt(index) else null
    }

    fun hideBack(): CardModel? {
        val index = snapshotStateList.indexOfLast { !it.isHidden.value && !it.inRemoval.value }
        return if (index != -1) hideAt(index) else null
    }

    fun unhideAll() {
        snapshotStateList.forEach { it.isHidden.value = false }
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
            if (state.expanded) {
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
    // var animatedStackHeight = targetHeight

//    Dialog(
//        onDismissRequest = {},
//        properties = DialogProperties(
//            dismissOnBackPress = false,
//            dismissOnClickOutside = false
//        )
//    ) {
//        val window = (LocalView.current.parent as? DialogWindowProvider)?.window
//        SideEffect {
//            if (window != null) {
//                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
//                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
//                if(state.expanded) window.setDimAmount(0.2f) else window.setDimAmount(0f)
//                window.setGravity(Gravity.BOTTOM)
//                window.attributes.y = stackOffset.y.roundToInt()
//                window.attributes.x = stackOffset.x.roundToInt()
//                window.attributes.y = 200
//            }
//        }
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
       // val listSnapshot = state.snapshotStateList.toList()
        val visibleCards = state.snapshotStateList
            .filter { !it.isHidden.value }
            .toList() // to avoid concurrent modification issues

        visibleCards.forEachIndexed { index, cardModel ->
            // compute logical index from top (0 is top)
            val logicalIndex = visibleCards.size - 1 - index
            val isTop = logicalIndex == 0

            key(cardModel.id) {
                // Each card will be placed offset from top by logicalIndex * peekHeight
                CardStackItem(
                    model = cardModel,
                    inRemoval = cardModel.inRemoval.value,
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
    //}
}

@Composable
private fun CardStackItem(
    model: CardModel,
    inRemoval: Boolean,
    expanded: Boolean,
    index: Int,
    isTop: Boolean,
    cardWidth: Dp,
    cardHeight: Dp,
    peekHeight: Dp,
    stackedWidthScaleFactor: Float = 0.95f,
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

    // Card Width Animation
    val targetWidth = mutableStateOf(with(LocalDensity.current) {
        if (expanded) {
            cardWidth.toPx()
        } else {
            cardWidth.toPx() * stackedWidthScaleFactor.pow(index)
        }
    })
    val animatedWidth = remember { Animatable(targetWidth.value) }
    LaunchedEffect(index, expanded) {
        animatedWidth.animateTo(
            targetWidth.value,
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
    LaunchedEffect(inRemoval) {
        if (inRemoval) {
            //opacityProgress.snapTo(1f)
            opacityProgress.animateTo(
                0f,
                animationSpec = tween(
                    durationMillis = FADE_OUT_DURATION,
                    easing = FastOutSlowInEasing
                )
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
                alpha = opacityProgress.value,
                scaleX = animatedWidth.value / with(localDensity) { cardWidth.toPx() },
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
                stackState.addCard(CardModel(id = id) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        BasicText("Card: $id")
                        BasicText("Some detail here")
                    }
                })
            }, text = "Add card")

            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = {
                stackState.popFront()
            }, text = "Remove top card")
            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = {
                stackState.popBack()
            }, text = "Remove last card")
        }
    }
}