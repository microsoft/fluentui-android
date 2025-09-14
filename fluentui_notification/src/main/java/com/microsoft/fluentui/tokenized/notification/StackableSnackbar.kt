package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import kotlinx.coroutines.launch
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.times
import com.microsoft.fluentui.theme.token.controlTokens.SnackBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SnackBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.SnackbarStyle
import com.microsoft.fluentui.tokenized.controls.BasicCard
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

private const val FADE_OUT_DURATION = 350
private const val STACKED_WIDTH_SCALE_FACTOR = 0.95f

//TODO: Make stack scrollable in expanded state
//TODO: Add accessibility support for the stack and individual cards
//TODO: Make dynamically sized cards based on content
//TODO: Make card owner of the hide/show animation states
/**
 * Represents a single item within the Snackbar stack.
 *
 * @param id A unique identifier for this snackbar item.
 * @param hidden A mutable state to control the visibility (hidden/shown) of the card. `true` if hidden, `false` otherwise.
 * @param isReshown A mutable state to indicate if the card is being reshown after being hidden. This helps in triggering specific animations.
 * @param content The composable content to be displayed inside the snackbar card.
 */
@Stable
data class SnackbarItemModel(
    val id: String,
    val hidden: MutableState<Boolean> = mutableStateOf(false),
    val isReshown: MutableState<Boolean> = mutableStateOf(false),
    val snackbarToken: SnackBarTokens = SnackBarTokens(),
    val snackbarStyle: SnackbarStyle = SnackbarStyle.Neutral,
    val content: @Composable () -> Unit,
)

/**
 * A state object that can be hoisted to control and observe the [SnackbarStack].
 * It provides methods to add, remove, and manage the state of snackbar items.
 *
 * @param cards The initial list of [SnackbarItemModel] to populate the stack.
 * @param scope The [CoroutineScope] to launch operations like adding, removing, and animating cards.
 * @param maxCollapsedSize The maximum number of visible cards when the stack is collapsed.
 * @param maxExpandedSize The maximum number of visible cards when the stack is expanded.
 */
class SnackbarStackState(
    internal val cards: MutableList<SnackbarItemModel>,
    internal val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main),
    internal val maxCollapsedSize: Int = 3,
    internal val maxExpandedSize: Int = 20
) {
    internal val snapshotStateList: MutableList<SnackbarItemModel> =
        mutableStateListOf<SnackbarItemModel>().apply { addAll(cards) }
    internal val hiddenIndicesList: MutableList<Pair<Int, SnackbarItemModel>> = mutableListOf()
    internal var expanded by mutableStateOf(false)

    private val listOperationMutex = Mutex()

    private val expandMutex = Mutex()

    /**
     * Adds a new snackbar card to the top of the stack.
     * If the stack exceeds the maximum size, the oldest card will be hidden.
     * @param card The [SnackbarItemModel] to add.
     */
    fun addCard(card: SnackbarItemModel) {
        scope.launch {
            listOperationMutex.withLock {
                snapshotStateList.add(card)
            }

            val maxSize = if (expanded) maxExpandedSize else maxCollapsedSize
            val visibleCount = snapshotStateList.count { !it.hidden.value }

            if (visibleCount > maxSize) {
                popBack(remove = false)
            }
        }
    }

    /**
     * Removes a snackbar card from the stack by its unique [id].
     * @param id The id of the card to remove.
     */
    fun removeCardById(id: String) {
        scope.launch {
            listOperationMutex.withLock {
                val index = snapshotStateList.indexOfFirst { it.id == id }
                if (index != -1) {
                    snapshotStateList.removeAt(index)
                }
            }
        }
    }

    /**
     * Toggles the stack between its collapsed and expanded states.
     * It automatically handles showing or hiding cards to match the respective size limits.
     */
    fun toggleExpanded() {
        scope.launch {
            expandMutex.withLock {
                val currentExpanded = expanded
                val maxSize = if (currentExpanded) maxCollapsedSize else maxExpandedSize
                val visibleCards = snapshotStateList.filter { !it.hidden.value }
                val currentSize = visibleCards.size
                expanded = !currentExpanded
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

    /**
     * Hides the oldest visible card from the stack (the one at the bottom).
     * @param remove If `true`, the card is permanently removed. If `false`, it's moved to a hidden list and can be reshown later.
     */
    fun popBack(remove: Boolean = true) {
        scope.launch {
            val index = snapshotStateList.indexOfFirst { !it.hidden.value }
            if (index != -1) {
                hideAtSingle(index, remove = remove)
            }
        }
    }

    /**
     * Hides the newest visible card from the stack (the one at the top).
     * @param remove If `true`, the card is permanently removed. If `false`, it's moved to a hidden list and can be reshown later.
     */
    fun popFront(remove: Boolean = true) {
        scope.launch {
            val index = snapshotStateList.indexOfLast { !it.hidden.value }
            if (index != -1) {
                hideAtSingle(index, remove = remove)
            }
        }
    }

    /**
     * Reveals previously hidden cards at the specified indices in the hidden list.
     * @param indices A list of indices corresponding to the cards in the hidden list to show.
     */
    fun showAt(indices: List<Int>) {
        scope.launch {
            showAtParallel(indices)
        }
    }

    /**
     * Reveals all previously hidden cards.
     */
    fun showAll() {
        scope.launch {
            val indicesToShow = (0 until hiddenIndicesList.size).toList()
            showAtParallel(indices = indicesToShow)
        }
    }

    /**
     * Shows cards in parallel for smooth animation.
     */
    private suspend fun showAtParallel(indices: List<Int>) {
        val cardsToShow = mutableListOf<Pair<Int, SnackbarItemModel>>()

        listOperationMutex.withLock {
            indices.reversed().forEach { idx ->
                if (idx in hiddenIndicesList.indices) {
                    val (hiddenIndex, card) = hiddenIndicesList[idx]
                    if (card.hidden.value) {
                        cardsToShow.add(idx to card)
                    }
                }
            }

            cardsToShow.forEach { (_, card) ->
                card.isReshown.value = true
                snapshotStateList.add(0, card)
                card.hidden.value = false
            }
        }

        coroutineScope {
            cardsToShow.map { (idx, card) ->
                launch {
                    delay(FADE_OUT_DURATION.toLong())
                    card.isReshown.value = false
                    listOperationMutex.withLock {
                        val iterator = hiddenIndicesList.iterator()
                        while (iterator.hasNext()) {
                            val item = iterator.next()
                            if (item.second.id == card.id) {
                                iterator.remove()
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Hides a single card (sequential operation).
     */
    private suspend fun hideAtSingle(index: Int, remove: Boolean) {
        if (index in snapshotStateList.indices) {
            val card = snapshotStateList[index]
            if (!card.hidden.value) {
                card.hidden.value = true

                delay(FADE_OUT_DURATION.toLong())

                listOperationMutex.withLock {
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

    /**
     * Hides cards in parallel for smooth animation.
     */
    private suspend fun hideAtParallel(indices: List<Int>, remove: Boolean) {
        val cardsToHide = mutableListOf<Pair<Int, SnackbarItemModel>>()

        listOperationMutex.withLock {
            indices.forEach { idx ->
                if (idx in snapshotStateList.indices) {
                    val card = snapshotStateList[idx]
                    if (!card.hidden.value) {
                        cardsToHide.add(idx to card)
                        card.hidden.value = true
                    }
                }
            }
        }

        if (cardsToHide.isNotEmpty()) {
            delay(FADE_OUT_DURATION.toLong())

            listOperationMutex.withLock {
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

    /**
     * Returns the current number of visible cards in the stack.
     */
    fun size(): Int = snapshotStateList.size
}

/**
 * Creates and remembers a [SnackbarStackState] in the current composition.
 * This is the recommended way to create a state object for the [SnackbarStack].
 *
 * @param initial An optional initial list of [SnackbarItemModel]s to populate the stack.
 * @return A remembered [SnackbarStackState] instance.
 */
@Composable
fun rememberSnackbarStackState(initial: List<SnackbarItemModel> = emptyList()): SnackbarStackState {
    return remember { SnackbarStackState(initial.toMutableList()) }
}

/**
 * A composable that displays a stack of snackbar notifications.
 * It animates the cards based on the provided [SnackbarStackState].
 * The stack can be expanded or collapsed by clicking on it.
 *
 * @param state The [SnackbarStackState] that controls the content and behavior of the stack.
 * @param modifier The [Modifier] to be applied to the stack container.
 * @param cardWidth The fixed width for each card in the stack.
 * @param cardHeight The base height for each card in the stack.
 * @param peekHeight The height of the portion of the underlying cards that is visible when the stack is collapsed.
 * @param stackAbove If `true`, the stack builds upwards from the bottom. If `false`, it builds downwards from the top.
 * @param contentModifier A modifier to be applied to each individual card slot within the stack.
 */
@Composable
fun SnackbarStack(
    state: SnackbarStackState,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 320.dp,
    cardHeight: Dp = 160.dp,
    peekHeight: Dp = 10.dp,
    stackAbove: Boolean = true,
    contentModifier: Modifier = Modifier
) {
    val count by remember { derivedStateOf { state.size() } }

    val targetHeight by remember {
        derivedStateOf {
            if (count == 0) {
                0.dp
            } else if (state.expanded) {
                cardHeight * count + (count - 1) * peekHeight
            } else {
                cardHeight + (count - 1) * peekHeight
            }
        }
    }

    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    val toggleExpanded = remember<() -> Unit> { { state.toggleExpanded() } }
    val scrollState = rememberScrollState()

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val parentMaxHeight = this.maxHeight
        val contentHeight = if (state.size() == 0) 0.dp else animatedStackHeight

        val visibleHeight =
            if (state.expanded) minOf(contentHeight, parentMaxHeight) else contentHeight

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(visibleHeight)
                .then(if (state.expanded) Modifier.verticalScroll(scrollState) else Modifier),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(contentHeight),
                contentAlignment = Alignment.BottomCenter
            ) {
                state.snapshotStateList.forEachIndexed { index, snackbarModel ->
                    val logicalIndex = state.size() - 1 - index

                    key(snackbarModel.id) {
                        SnackbarStackItem(
                            model = snackbarModel,
                            isHidden = snackbarModel.hidden.value,
                            isReshown = snackbarModel.isReshown.value,
                            expanded = state.expanded,
                            index = logicalIndex,
                            cardHeight = cardHeight,
                            peekHeight = peekHeight,
                            cardWidth = cardWidth,
                            onSwipedAway = { idToRemove ->
                                state.removeCardById(idToRemove)
                                state.showAt(listOf(0))
                            },
                            stackAbove = stackAbove,
                            onClick = {
                                toggleExpanded()
                            }
                        )
                    }
                }
            }
        }

        LaunchedEffect(state.expanded, contentHeight, count) {
            if (state.expanded) {
                snapshotFlow { scrollState.maxValue }
                    .filter { it > 0 }
                    .first()
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }
    }
}


/**
 * Manages the vertical offset animation of a card when the stack's state changes.
 */
@Composable
private fun CardAdjustAnimation(
    expanded: Boolean,
    isReshown: Boolean = false,
    index: Int,
    stackAbove: Boolean = true,
    targetYOffset: Float,
    animatedYOffset: Animatable<Float, AnimationVector1D>
) {
    LaunchedEffect(index, expanded, isReshown) {
        animatedYOffset.animateTo(
            targetYOffset * (if (stackAbove) -1f else 1f),
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }
}

/**
 * Manages the width animation of a card when the stack's state changes.
 */
@Composable
private fun CardWidthAnimation(
    expanded: Boolean,
    index: Int,
    animatedWidth: Animatable<Float, AnimationVector1D>,
    targetWidth: Float
) {
    LaunchedEffect(index, expanded) {
        animatedWidth.animateTo(
            targetWidth,
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }
}

/**
 * Manages the initial slide-in animation for a new card.
 */
@Composable
private fun SlideInAnimation(
    model: SnackbarItemModel,
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

/**
 * Manages the fade-in/fade-out animation when a card is hidden or reshown.
 */
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

/**
 * A private composable that represents a single, animatable card within the [SnackbarStack].
 * It handles its own animations for position, width, opacity, and swipe gestures.
 */
@Composable
private fun SnackbarStackItem(
    model: SnackbarItemModel,
    isHidden: Boolean,
    isReshown: Boolean = false,
    expanded: Boolean,
    index: Int,
    cardWidth: Dp,
    cardHeight: Dp,
    peekHeight: Dp,
    stackedWidthScaleFactor: Float = STACKED_WIDTH_SCALE_FACTOR,
    onSwipedAway: (String) -> Unit,
    onClick: () -> Unit = {},
    stackAbove: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val isTop = index == 0
    val targetYOffset =
        with(localDensity) { if (expanded) (index * (peekHeight + cardHeight)).toPx() else (index * peekHeight).toPx() }
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

    val targetWidth = with(localDensity) {
        if (expanded) {
            cardWidth.toPx()
        } else {
            cardWidth.toPx() * stackedWidthScaleFactor.pow(index)
        }
    }
    val animatedWidth = remember { Animatable(targetWidth) }
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

    val token = model.snackbarToken
    val snackBarInfo = SnackBarInfo(model.snackbarStyle, false)

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
            .then(
                if (isTop || expanded) Modifier.pointerInput(model.id) {
                    detectHorizontalDragGestures(
                        onDragStart = {},
                        onDragEnd = {
                            val threshold = with(localDensity) { (cardWidth / 4).toPx() }
                            scope.launch {
                                if (abs(swipeX.value) > threshold) {
                                    val target = if (swipeX.value > 0)
                                        with(localDensity) { cardWidth.toPx() * 1.2f }
                                    else
                                        -with(localDensity) { cardWidth.toPx() * 1.2f }

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
                        }
                    ) { change, dragAmountX ->
                        change.consume()
                        scope.launch {
                            swipeX.snapTo(swipeX.value + dragAmountX)
                        }
                    }
                } else Modifier
            )

    ) {
        BasicCard(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .shadow(
                    elevation = token.shadowElevationValue(snackBarInfo)
                )
                .background(token.backgroundBrush(snackBarInfo))
                .clickable(
                    onClick = {
                        onClick()
                    }
                )
        )
        {
            model.content()
        }
    }
}