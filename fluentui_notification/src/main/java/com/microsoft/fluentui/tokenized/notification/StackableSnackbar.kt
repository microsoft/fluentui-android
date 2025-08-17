package com.microsoft.fluentui.tokenized.notification

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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.times
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    val hidden: MutableState<Boolean> = mutableStateOf(false),
    val isReshown: MutableState<Boolean> = mutableStateOf(false),
    val content: @Composable () -> Unit
)

/** Public state object to control the stack. */
class CardStackState(
    internal val cards: MutableList<CardModel>,
    internal val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main), // TODO: Fix concurrency issues, investigate Dispatchers.Main, also Mutexes where needed
    internal val maxCollapsedSize: Int = 3,
    internal val maxExpandedSize: Int = 10
) {
    internal val snapshotStateList: MutableList<CardModel> =
        mutableStateListOf<CardModel>().apply { addAll(cards) }
    internal val hiddenIndicesList: MutableList<Pair<Int, CardModel>> = mutableListOf()
    internal var expanded by mutableStateOf(false)
    internal val maxSize =
        max(maxCollapsedSize, maxExpandedSize) // All cards above this will be deleted

    fun addCard(card: CardModel) {
        snapshotStateList.add(card)
        val maxSize = if (expanded) maxExpandedSize else maxCollapsedSize
        scope.launch {
            if (snapshotStateList.count { !it.hidden.value } > maxSize) {
                popBack()
                //delay(10) // TODO: Check without delay
            }
        }
    }

    fun removeCardById(id: String) {
        val index = snapshotStateList.indexOfFirst { it.id == id }
        if (index != -1) {
            snapshotStateList.removeAt(index)
        }
    }

    fun expand() {
        if (!expanded) {
            val maxSize = maxExpandedSize
            val currentSize = snapshotStateList.count { !it.hidden.value }
            if( currentSize > maxSize) {
                hideAt((0..currentSize - maxSize - 1).toList(), remove = false)
            }
            else {
                showAt((0..maxSize - currentSize).toList())
            }
            expanded = true
        }
    }

    fun collapse() {
        if (expanded) {
            expanded = false
            val maxSize = maxCollapsedSize
            val currentSize = snapshotStateList.count { !it.hidden.value }
            if( currentSize > maxSize) {
                hideAt((0..currentSize - maxSize - 1).toList(), remove = false)
            }
            else {
                showAt((0..maxSize - currentSize).toList())
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

    fun popAt(index: Int) {
        hideAt(listOf(index), remove = true)
    }

    fun popBack() {
        val index = snapshotStateList.indexOfFirst { !it.hidden.value }
        if (index != -1) popAt(index) else null
    }

    fun popFront() {
        val index = snapshotStateList.indexOfLast { !it.hidden.value }
        if (index != -1) popAt(index) else null
    }

    /**
     * Shows the card at the specified indices.
     * @param indices list of indices to restore
     * @return list of restored cards
     */
    fun showAt(indices: List<Int>): List<CardModel> {
        val restored = mutableListOf<CardModel>()
        indices.sortedDescending().forEach { idx ->
            if (idx in hiddenIndicesList.indices) {
                val (hiddenIndex, card) = hiddenIndicesList[idx]
                if (card.hidden.value) {
                    restored.add(card)
                    scope.launch {
                        card.isReshown.value = true
                        snapshotStateList.add(
                            hiddenIndex.coerceAtMost(snapshotStateList.size),
                            card
                        )
                        card.hidden.value = false
                        hiddenIndicesList.removeAt(idx)

                        delay(FADE_OUT_DURATION.toLong())
                        card.isReshown.value = false
                    }
                }
            }
        }
        return restored
    }

    /**
     * Hides the cards at the specified indices.
     * @param indices list of indices to hide
     * @param remove if true, removes the card from the stack, otherwise just hides it
     */
    fun hideAt(indices: List<Int>, remove: Boolean = false) {
        indices.forEach { idx ->
            if (idx in snapshotStateList.indices) {
                val card = snapshotStateList[idx]
                if (!card.hidden.value) {
                    scope.launch {
                        card.hidden.value = true
                        delay(FADE_OUT_DURATION.toLong())
                        if (remove) snapshotStateList.remove(card)
                        else {
                            hiddenIndicesList.add(idx to card)
                            snapshotStateList.remove(card)
                        }
                    }
                }
            }
        }
    }

    fun hideFront() {
        val index = snapshotStateList.indexOfFirst { !it.hidden.value && !it.hidden.value }
        if (index != -1) hideAt(listOf(index)) else null
    }

    fun hideBack() {
        val index = snapshotStateList.indexOfLast { !it.hidden.value && !it.hidden.value }
        if (index != -1) hideAt(listOf(index)) else null
    }

    fun unhideAll() {
        snapshotStateList.forEach { it.hidden.value = false }
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
                    //state.expanded = !state.expanded
                    state.toggleExpanded()
                },
                tooltipText = "Notification Stack",
            )
    ) {
        // Show cards in reverse visual order: bottom-most drawn first
        // val listSnapshot = state.snapshotStateList.toList()
        val visibleCards = state.snapshotStateList
            //.filter { !it.hidden.value }
            .toList() // to avoid concurrent modification issues

        visibleCards.forEachIndexed { index, cardModel ->
            // compute logical index from top (0 is top)
            val logicalIndex = visibleCards.size - 1 - index
            val isTop = logicalIndex == 0

            key(cardModel.id) {
                // Each card will be placed offset from top by logicalIndex * peekHeight
                CardStackItem(
                    model = cardModel,
                    isHidden = cardModel.hidden.value,
                    isReshown = cardModel.isReshown.value,
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

@Composable
private fun CardAdjustAnimation(
    expanded: Boolean,
    isReshown: Boolean = false, // used to trigger re-show animation
    index: Int,
    stackAbove: Boolean = true, // if true, cards stack above each other (negative offset)
    targetYOffset: MutableState<Float>,// target Y offset for the card
    animatedYOffset: Animatable<Float, AnimationVector1D>
) {
    LaunchedEffect(index, expanded, isReshown) {
        animatedYOffset.animateTo(
            targetYOffset.value * (if (stackAbove) -1f else 1f),
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }
}

@Composable
private fun CardWidthAnimation(
    expanded: Boolean,
    index: Int,
    animatedWidth: Animatable<Float, AnimationVector1D>, // default to 0f,
    targetWidth: MutableState<Float>
) {
    LaunchedEffect(index, expanded) {
        animatedWidth.animateTo(
            targetWidth.value,
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }
}

@Composable
private fun SlideInAnimation(
    model: CardModel,
    isReshown: Boolean = false, // used to trigger re-show animation
    isTop: Boolean = true, // if true, the card is the top-most in the stack
    slideInProgress: Animatable<Float, AnimationVector1D> // progress of the slide-in animation
) {
    // Slide In Animation TODO: Add configurations
    LaunchedEffect(model.id) {
        if (isReshown) {
            slideInProgress.snapTo(0f)
        } else {
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
    }
}

@Composable
private fun HideAnimation(
    isHidden: Boolean = false, // if true, the card is hidden
    isReshown: Boolean = false, // used to trigger re-show animation
    opacityProgress: Animatable<Float, AnimationVector1D>
) {
    // Fade Out Animation  TODO: Add configurations
    LaunchedEffect(isHidden, isReshown) {
        if (isHidden) {
            opacityProgress.snapTo(1f)
            opacityProgress.animateTo(
                0f,
                animationSpec = tween(
                    durationMillis = FADE_OUT_DURATION,
                    easing = FastOutSlowInEasing
                )
            )
        }
        if (isReshown) {
            opacityProgress.snapTo(0f)
            opacityProgress.animateTo(
                1f,
                animationSpec = tween(
                    durationMillis = FADE_OUT_DURATION,
                    easing = LinearOutSlowInEasing
                )
            )
        }
    }
}

@Composable
private fun CardStackItem(
    model: CardModel,
    isHidden: Boolean,
    isReshown: Boolean = false, // used to trigger re-show animation
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
    val localDensity = LocalDensity.current

    val targetYOffset = mutableStateOf(with(localDensity) { if (expanded) (index * (peekHeight + cardHeight)).toPx() else (index * peekHeight).toPx() })
    val animatedYOffset = remember {
        Animatable(
            if (isReshown && !expanded) {
                targetYOffset.value * (if (stackAbove) -1f else 1f)
            } else targetYOffset.value
        )
    }
    CardAdjustAnimation(
        expanded = expanded,
        isReshown = isReshown,
        index = index,
        targetYOffset = targetYOffset,
        animatedYOffset = animatedYOffset
    )

    val targetWidth = mutableStateOf(with(localDensity) {
        if (expanded) {
            cardWidth.toPx()
        } else {
            cardWidth.toPx() * stackedWidthScaleFactor.pow(index)
        }
    })
    val animatedWidth = remember { Animatable(targetWidth.value) }
    CardWidthAnimation(
        expanded = expanded,
        index = index,
        animatedWidth = animatedWidth,
        targetWidth = targetWidth
    )

    val slideInProgress =
        remember { Animatable(if (isReshown) 0f else 1f) } // 1 = offscreen right, 0 = in place
    SlideInAnimation(
        model = model,
        isReshown = isReshown,
        isTop = isTop,
        slideInProgress = slideInProgress
    )

    val opacityProgress = remember { Animatable(1f) }
    HideAnimation(
        isHidden = isHidden,
        isReshown = isReshown,
        opacityProgress = opacityProgress
    )

    val swipeX = remember { Animatable(0f) }
    val offsetX: Float = if (isTop || expanded) swipeX.value else 0f
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
//
//            Button(onClick = {
//                stackState.popBack()
//            }, text = "Remove last card")

            Button(onClick = {
                stackState.hideAt(listOf(3, 4, 5), remove = false)
            }, text = "Show last removed card")

            Button(onClick = {
                stackState.showAt(listOf(0, 1, 2))
            }, text = "Show first removed cards")
        }
    }
}