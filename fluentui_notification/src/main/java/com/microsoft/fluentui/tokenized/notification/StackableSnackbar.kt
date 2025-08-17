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
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.times
import com.microsoft.fluentui.tokenized.controls.BasicCard
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import kotlin.math.abs
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
    internal val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main),
    internal val maxCollapsedSize: Int = 3,
    internal val maxExpandedSize: Int = 10
) {
    internal val snapshotStateList: MutableList<CardModel> =
        mutableStateListOf<CardModel>().apply { addAll(cards) }
    internal val hiddenIndicesList: MutableList<Pair<Int, CardModel>> = mutableListOf()
    internal var expanded by mutableStateOf(false)

    private val listOperationMutex = Mutex()

    private val expandMutex = Mutex()

    fun addCard(card: CardModel) {
        scope.launch {
            listOperationMutex.withLock {
                withContext(Dispatchers.Main) {
                    snapshotStateList.add(card)
                }
            }

            val maxSize = if (expanded) maxExpandedSize else maxCollapsedSize
            val visibleCount = snapshotStateList.count { !it.hidden.value }

            if (visibleCount > maxSize) {
                popBack()
            }
        }
    }

    fun removeCardById(id: String) {
        scope.launch {
            listOperationMutex.withLock {
                withContext(Dispatchers.Main) {
                    val index = snapshotStateList.indexOfFirst { it.id == id }
                    if (index != -1) {
                        snapshotStateList.removeAt(index)
                    }
                }
            }
        }
    }

    fun toggleExpanded() {
        scope.launch {
            expandMutex.withLock {
                val currentExpanded = expanded
                val maxSize = if (currentExpanded) maxCollapsedSize else maxExpandedSize
                val visibleCards = snapshotStateList.filter { !it.hidden.value }
                val currentSize = visibleCards.size

                withContext(Dispatchers.Main) {
                    expanded = !currentExpanded
                }

                if (currentSize > maxSize) {
                    val indicesToHide = (0 until (currentSize - maxSize)).toList()
                    hideAtParallel(indices = indicesToHide, remove = false)
                } else {
                    val indicesToShow =
                        (0 until minOf(maxSize - currentSize, hiddenIndicesList.size)).toList()
                    showAtParallel(indices = indicesToShow)
                }
            }
        }
    }

    fun popBack() {
        scope.launch {
            val index = snapshotStateList.indexOfFirst { !it.hidden.value }
            if (index != -1) {
                hideAtSingle(index, remove = true)
            }
        }
    }

    fun popFront() {
        scope.launch {
            val index = snapshotStateList.indexOfLast { !it.hidden.value }
            if (index != -1) {
                hideAtSingle(index, remove = true)
            }
        }
    }

    /**
     * Shows cards at the specified indices in parallel.
     */
    fun showAt(indices: List<Int>) {
        scope.launch {
            showAtParallel(indices)
        }
    }

    /**
     * Shows cards in parallel for smooth animation
     */
    private suspend fun showAtParallel(indices: List<Int>) {
        val cardsToShow = mutableListOf<Pair<Int, CardModel>>()

        // First, collect all cards to show while holding the lock
        listOperationMutex.withLock {
            indices.reversed().forEach { idx ->
                if (idx in hiddenIndicesList.indices) {
                    val (hiddenIndex, card) = hiddenIndicesList[idx]
                    if (card.hidden.value) {
                        cardsToShow.add(idx to card)
                    }
                }
            }

            // Add all cards back to the list immediately
            withContext(Dispatchers.Main) {
                cardsToShow.forEach { (_, card) ->
                    card.isReshown.value = true
                    snapshotStateList.add(0, card)
                    card.hidden.value = false
                }
            }
        }

        // Now animate all cards in parallel (outside the lock)
        coroutineScope {
            cardsToShow.map { (idx, card) ->
                launch {
                    delay(FADE_OUT_DURATION.toLong())
                    withContext(Dispatchers.Main) {
                        card.isReshown.value = false
                    }
                    listOperationMutex.withLock {
                        withContext(Dispatchers.Main) {
                            hiddenIndicesList.removeIf { it.second.id == card.id }
                        }
                    }
                }
            }
        }
    }

    /**
     * Hides a single card (sequential operation)
     */
    private suspend fun hideAtSingle(index: Int, remove: Boolean) {
        if (index in snapshotStateList.indices) {
            val card = snapshotStateList[index]
            if (!card.hidden.value) {
                withContext(Dispatchers.Main) {
                    card.hidden.value = true
                }

                delay(FADE_OUT_DURATION.toLong())

                listOperationMutex.withLock {
                    withContext(Dispatchers.Main) {
                        if (remove) {
                            snapshotStateList.remove(card)
                        } else {
                            hiddenIndicesList.add(index to card)
                            snapshotStateList.remove(card)
                        }
                    }
                }
            }
        }
    }

    /**
     * Hides cards in parallel for smooth animation
     */
    private suspend fun hideAtParallel(indices: List<Int>, remove: Boolean) {
        val cardsToHide = mutableListOf<Pair<Int, CardModel>>()

        // Collect cards and mark them as hidden immediately
        listOperationMutex.withLock {
            indices.forEach { idx ->
                if (idx in snapshotStateList.indices) {
                    val card = snapshotStateList[idx]
                    if (!card.hidden.value) {
                        cardsToHide.add(idx to card)
                        withContext(Dispatchers.Main) {
                            card.hidden.value = true
                        }
                    }
                }
            }
        }

        // Animate all cards in parallel
        if (cardsToHide.isNotEmpty()) {
            delay(FADE_OUT_DURATION.toLong())

            // Remove all cards at once after animation
            listOperationMutex.withLock {
                withContext(Dispatchers.Main) {
                    cardsToHide.forEach { (idx, card) ->
                        if (remove) {
                            snapshotStateList.remove(card)
                        } else {
                            if (!hiddenIndicesList.any { it.second.id == card.id }) {
                                hiddenIndicesList.add(idx to card)
                            }
                            snapshotStateList.remove(card)
                        }
                    }
                }
            }
        }
    }

    fun size(): Int = snapshotStateList.size
}

// Rest of the implementation remains the same...
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
    stackOffset: Offset = Offset(0f, 0f),
    stackAbove: Boolean = true,
    contentModifier: Modifier = Modifier
) {
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

    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

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
                    state.toggleExpanded()
                },
                tooltipText = "Notification Stack",
            )
    ) {
        val visibleCards = state.snapshotStateList.toList()

        visibleCards.forEachIndexed { index, cardModel ->
            val logicalIndex = visibleCards.size - 1 - index
            val isTop = logicalIndex == 0

            key(cardModel.id) {
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
                    onSwipedAway = { idToRemove ->
                        state.removeCardById(idToRemove)
                        state.showAt(listOf(0))
                    },
                    stackAbove = stackAbove
                )
            }
        }
    }
}

// CardStackItem and animation functions remain the same as in your original implementation...
@Composable
private fun CardAdjustAnimation(
    expanded: Boolean,
    isReshown: Boolean = false,
    index: Int,
    stackAbove: Boolean = true,
    targetYOffset: MutableState<Float>,
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
    animatedWidth: Animatable<Float, AnimationVector1D>,
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
    isReshown: Boolean = false,
    isTop: Boolean = true,
    slideInProgress: Animatable<Float, AnimationVector1D>
) {
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
    isHidden: Boolean = false,
    isReshown: Boolean = false,
    opacityProgress: Animatable<Float, AnimationVector1D>
) {
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
    isReshown: Boolean = false,
    expanded: Boolean,
    index: Int,
    isTop: Boolean,
    cardWidth: Dp,
    cardHeight: Dp,
    peekHeight: Dp,
    stackedWidthScaleFactor: Float = 0.95f,
    onSwipedAway: (String) -> Unit,
    stackAbove: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    val targetYOffset =
        mutableStateOf(with(localDensity) { if (expanded) (index * (peekHeight + cardHeight)).toPx() else (index * peekHeight).toPx() })
    val animatedYOffset = remember {
        Animatable(0f)
    }
    CardAdjustAnimation(
        expanded = expanded,
        isReshown = isReshown,
        index = index,
        stackAbove = stackAbove,
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
        remember { Animatable(if (isReshown) 0f else 1f) }
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
                    onDragStart = {},
                    onDragEnd = {
                        val threshold = with(localDensity) { (cardWidth / 4).toPx() }
                        scope.launch {
                            if (abs(swipeX.value) > threshold) {
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
                                onSwipedAway(model.id)
                            } else {
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
        BasicCard(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .shadow(
                    elevation = 12.dp
                )
                .background(Color.Gray)
        )
        {
            model.content()
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
                stackState.showAt(listOf(0, 1, 2))
            }, text = "Show hidden cards")
        }
    }
}