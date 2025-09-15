package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.animateContentSize
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
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

private const val FADE_OUT_DURATION = 350
private const val STACKED_WIDTH_SCALE_FACTOR = 0.95f

//TODO: Add accessibility support for the stack and individual cards
//TODO: Make dynamically sized cards based on content
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
    internal var maxCollapsedSize: Int = 5,
    internal var maxExpandedSize: Int = 10
) {
    internal val snapshotStateList: MutableList<SnackbarItemModel> =
        mutableStateListOf<SnackbarItemModel>().apply { addAll(cards) }

    internal var expanded by mutableStateOf(false)
        private set

    internal var maxCurrentSize = maxCollapsedSize

    /**
     * Adds a new snackbar card to the top of the stack.
     * If the stack exceeds the maximum size, the oldest card will be hidden.
     * @param card The [SnackbarItemModel] to add.
     */
    fun addCard(card: SnackbarItemModel) {
        maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize
        snapshotStateList.add(card)
        if (sizeVisible() > maxCurrentSize) {
            hideBack()
        }
    }

    /**
     * Removes a snackbar card from the stack by its unique [id].
     * @param id The id of the card to remove.
     * @return `true` if the card was found and removed, `false` otherwise.
     * DOES NOT ANIMATE THE REMOVAL, JUST REMOVES IT IMMEDIATELY
     */
    fun removeCardById(id: String): Boolean {
        snapshotStateList.firstOrNull { it.id == id }?.let {
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    /**
     * Hides a specific card in the stack by its unique [id].
     * @param id The id of the card to hide.
     * @return `true` if the card was found and hidden, `false` otherwise.
     */
    fun hideCardById(id: String): Boolean {
        snapshotStateList.firstOrNull { it.id == id }?.let {
            it.hidden.value = true
            return true
        }
        return false
    }

    /**
     * Shows a specific card in the stack by its unique [id].
     * @param id The id of the card to show.
     * @return `true` if the card was found and shown, `false` otherwise.
     */
    fun showCardById(id: String): Boolean {
        snapshotStateList.firstOrNull { it.id == id }?.let {
            it.hidden.value = false
            return true
        }
        return false
    }

    /**
     * Toggles the stack between its collapsed and expanded states.
     * It automatically handles showing or hiding cards to match the respective size limits.
     */
    fun toggleExpanded() {
        val currentSize = snapshotStateList.count { !it.hidden.value }
        expanded = !expanded
        maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize
        val (count, sequence, targetHidden) =
            if (currentSize > maxCurrentSize) {
                Triple(currentSize - maxCurrentSize, snapshotStateList, true)
            } else {
                Triple(maxCurrentSize - currentSize, snapshotStateList.asReversed(), false)
            }

        var slots = count
        sequence.forEach {
            if (slots <= 0) return@forEach
            if (it.hidden.value != targetHidden) {
                it.hidden.value = targetHidden
                slots--
            }
        }
    }

    /**
     * Hides the oldest visible card from the stack (the one at the bottom).
     * @return `true` if a card was hidden, `false` if there were no visible cards.
     */
    fun hideBack(): Boolean {
        snapshotStateList.firstOrNull { !it.hidden.value }?.let {
            it.hidden.value = true
            return true
        }
        return false
    }

    /**
     * Hides the newest visible card in the stack (the one at the top).
     * @return `true` if a card was hidden, `false` if there were no visible cards.
     */
    fun hideFront(): Boolean {
        snapshotStateList.lastOrNull { !it.hidden.value }?.let {
            it.hidden.value = true
            return true
        }
        return false
    }

    /**
     * Removes the oldest visible card from the stack (the one at the bottom).
     * @return `true` if a card was removed, `false` if there were no visible cards.
     */
    fun removeBack(skipHidden: Boolean = false): Boolean {
        snapshotStateList.firstOrNull { skipHidden && !it.hidden.value || !skipHidden }?.let {
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    /**
     * Removes the newest visible card from the stack (the one at the top).
     * @return `true` if a card was removed, `false` if there were no visible cards.
     */
    fun removeFront(skipHidden: Boolean = false): Boolean {
        snapshotStateList.lastOrNull { skipHidden && !it.hidden.value || !skipHidden }?.let {
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    /**
     * Reveals the oldest hidden card in the stack (the one at the bottom).
     * @return `true` if a card was revealed, `false` if there were no hidden cards.
     */
    fun showBack(): Boolean {
        snapshotStateList.lastOrNull { it.hidden.value }?.let {
            it.hidden.value = false
            return true
        }
        return false
    }

    /**
     * Reveals the newest hidden card in the stack (the one at the top).
     * @return `true` if a card was revealed, `false` if there were no hidden cards.
     */
    fun showFront(): Boolean {
        snapshotStateList.firstOrNull { it.hidden.value }?.let {
            it.hidden.value = false
            return true
        }
        return false
    }

    /**
     * Reveals all previously hidden cards.
     */
    fun showAll() {
        snapshotStateList.forEach {
            it.hidden.value = false
        }
    }

    /**
     * Returns the current number of cards in the stack.
     */
    fun size(): Int = snapshotStateList.size

    /**
     * Returns the current number of visible cards in the stack.
     */
    fun sizeVisible(): Int = snapshotStateList.count { !it.hidden.value }
}

/**
 * Creates and remembers a [SnackbarStackState] in the current composition.
 * This is the recommended way to create a state object for the [SnackbarStack].
 *
 * @param initial An optional initial list of [SnackbarItemModel]s to populate the stack.
 * @return A remembered [SnackbarStackState] instance.
 */
@Composable
fun rememberSnackbarStackState(
    initial: List<SnackbarItemModel> = emptyList(),
    maxExpandedSize: Int = 10,
    maxCollapsedSize: Int = 5
): SnackbarStackState {
    return remember {
        SnackbarStackState(
            cards = initial.toMutableList(),
            maxExpandedSize = maxExpandedSize,
            maxCollapsedSize = maxCollapsedSize
        )
    }
}

data class SnackbarStackConfig(
    val cardWidthExpanded: Dp = 320.dp,
    val cardHeightExpanded: Dp = 160.dp,
    val cardGapExpanded: Dp = 10.dp,
    val cardWidthCollapsed: Dp = 280.dp,
    val cardHeightCollapsed: Dp = 80.dp,
    val cardGapCollapsed: Dp = 8.dp,
    internal val stackAbove: Boolean = true, //TODO: Fix Stack Above option, disabling for now
)

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
    contentModifier: Modifier = Modifier,
    snackBarStackConfig: SnackbarStackConfig = SnackbarStackConfig(),
) {
    val count by remember { derivedStateOf { state.sizeVisible() } }

    val cardHeight = animateDpAsState(
        targetValue = if (state.expanded) snackBarStackConfig.cardHeightExpanded else snackBarStackConfig.cardHeightCollapsed,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
    val peekHeight = animateDpAsState(
        targetValue = if (state.expanded) snackBarStackConfig.cardGapExpanded else snackBarStackConfig.cardGapCollapsed,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
    val targetHeight by remember {
        derivedStateOf {
            if (count == 0) {
                0.dp
            } else if (state.expanded) {
                cardHeight.value * (count + 1) + (count - 1) * peekHeight.value
            } else {
                cardHeight.value + (count - 1) * peekHeight.value
            }
        }
    }

    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )
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
                val totalVisibleCards = state.sizeVisible()
                var visibleIndex = 0
                state.snapshotStateList.forEach { snackBarModel ->
                    var logicalIndex = state.maxCurrentSize
                    val invertedLogicalIndex = visibleIndex
                    if (!snackBarModel.hidden.value) {
                        logicalIndex = totalVisibleCards - 1 - visibleIndex++
                    }
                    key(snackBarModel.id) {
                        SnackbarStackItem(
                            model = snackBarModel,
                            isHidden = snackBarModel.hidden.value,
                            expanded = state.expanded,
                            index = logicalIndex,
                            invertedIndex = invertedLogicalIndex,
                            onSwipedAway = { idToRemove ->
                                state.removeCardById(idToRemove)
                                state.showBack()
                            },
                            onClick = {
                                state.toggleExpanded()
                            }
                        )
                    }
                }
            }
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
    expanded: Boolean,
    index: Int,
    invertedIndex: Int,
    stackedWidthScaleFactor: Float = STACKED_WIDTH_SCALE_FACTOR,
    onSwipedAway: (String) -> Unit,
    onClick: () -> Unit = {},
    snackBarStackConfig: SnackbarStackConfig = SnackbarStackConfig(),
) {

    val cardWidth =
        if (expanded) snackBarStackConfig.cardWidthExpanded else snackBarStackConfig.cardWidthCollapsed

    val cardHeight =
        if (expanded) snackBarStackConfig.cardHeightExpanded else snackBarStackConfig.cardHeightCollapsed

    val peekHeight =
        if (expanded) snackBarStackConfig.cardGapExpanded else snackBarStackConfig.cardGapCollapsed

    val stackAbove = snackBarStackConfig.stackAbove

    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val isTop = index == 0
    val targetYOffset =
        with(localDensity) { if (expanded) (invertedIndex * (peekHeight + cardHeight)).toPx() else (index * peekHeight).toPx() }
    val animatedYOffset = remember {
        Animatable(with(localDensity) { cardHeight.toPx() * if (stackAbove) 1f else -1f })
    }
    LaunchedEffect(index, expanded, invertedIndex) {
        animatedYOffset.animateTo(
            targetYOffset * (if (stackAbove) -1f else 1f),
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }

    val targetWidth = with(localDensity) {
        if (expanded) {
            cardWidth.toPx()
        } else if (isHidden) {
            cardWidth.toPx() * stackedWidthScaleFactor.pow(index)
        } else {
            cardWidth.toPx() * stackedWidthScaleFactor.pow(index)
        }
    }

    val animatedWidth = animateFloatAsState(
        targetValue = targetWidth,
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
    )

    val slideInProgress = remember { Animatable(1f) }
    LaunchedEffect(Unit) {
        slideInProgress.animateTo(
            0f,
            animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
        )
    }

    val opacityProgress = remember { Animatable(0f) }
    LaunchedEffect(isHidden) {
        if (isHidden) {
            opacityProgress.animateTo(0f, tween(FADE_OUT_DURATION))
        } else {
            opacityProgress.animateTo(1f, tween(FADE_OUT_DURATION))
        }
    }

    val swipeX = remember { Animatable(0f) }
    val offsetX: Float = if (isTop || expanded) swipeX.value else 0f

    val token = model.snackbarToken
    val snackBarInfo = SnackBarInfo(model.snackbarStyle, false)

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    offsetX.roundToInt(),//+ (slideInProgress.value * with(localDensity) { 200.dp.toPx() }).roundToInt(),
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
                    enabled = isTop || expanded,
                    onClick = {
                        onClick()
                    }
                )
                .animateContentSize()
        )
        {
            model.content()
        }
    }
}