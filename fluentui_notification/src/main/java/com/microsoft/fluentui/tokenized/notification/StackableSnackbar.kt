package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import com.microsoft.fluentui.theme.token.controlTokens.SnackBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SnackbarStyle
import com.microsoft.fluentui.theme.token.controlTokens.StackableSnackBarTokens
import com.microsoft.fluentui.tokenized.controls.BasicCard
import kotlin.math.abs
import kotlin.math.pow

private const val FADE_OUT_DURATION = 350
private const val STACKED_WIDTH_SCALE_FACTOR = 0.95f

//TODO: Add accessibility support for the stack and individual cards
//TODO: Make dynamically sized cards based on content

/**
 * Represents a single item in the snackBar stack.
 *
 * @param snackBarToken The tokens to customize the appearance of the snackBar.
 * @param snackBarStyle The style of the snackBar, e.g., Neutral, Success, Error.
 * @param id A unique identifier for this snackBar item. Defaults to a random UUID.
 * @param hidden A mutable state to control the visibility of the snackBar item.
 * @param content The composable content to be displayed inside the snackBar.
 */
@Stable
data class SnackBarItemModel(
    val snackBarToken: StackableSnackBarTokens = StackableSnackBarTokens(),
    val snackBarStyle: SnackbarStyle = SnackbarStyle.Neutral,
    val id: String = java.util.UUID.randomUUID().toString(),
    internal val hidden: MutableState<Boolean> = mutableStateOf(false),
    val content: @Composable () -> Unit,
)

/**
 * Manages the state of a [SnackbarStack]. It allows for adding, removing, hiding, and showing snackBar items.
 *
 * @param cards The initial list of [SnackbarItemModel] to be displayed.
 * @param maxCollapsedSize The maximum number of visible snackBars when the stack is collapsed.
 * @param maxExpandedSize The maximum number of visible snackBars when the stack is expanded.
 */
class SnackBarStackState(
    internal val cards: MutableList<SnackBarItemModel>,
    internal var maxCollapsedSize: Int = 5,
    internal var maxExpandedSize: Int = 10
) {
    internal val snapshotStateList: MutableList<SnackBarItemModel> =
        mutableStateListOf<SnackBarItemModel>().apply { addAll(cards) }

    /**
     * Whether the snackBar stack is currently expanded.
     */
    internal var expanded by mutableStateOf(false)
        private set

    internal var maxCurrentSize = maxCollapsedSize

    /**
     * Adds a new snackBar card to the top of the stack.
     * If the number of visible cards exceeds the current maximum, the card at the back is hidden.
     * @param card The [SnackbarItemModel] to add.
     */
    fun addCard(card: SnackBarItemModel) {
        maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize
        snapshotStateList.add(card)
        if (sizeVisible() > maxCurrentSize) {
            hideBack()
        }
    }

    /**
     * Removes a snackBar card from the stack by its ID.
     * @param id The unique identifier of the card to remove.
     * @return `true` if a card was removed, `false` otherwise.
     */
    fun removeCardById(id: String): Boolean {
        snapshotStateList.firstOrNull { it.id == id }?.let {
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    /**
     * Hides a snackBar card by its ID without removing it from the stack.
     * @param id The unique identifier of the card to hide.
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
     * Shows a previously hidden snackBar card by its ID.
     * @param id The unique identifier of the card to show.
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
     * Toggles the expanded/collapsed state of the snackBar stack.
     * This adjusts the visibility of cards based on [maxCollapsedSize] and [maxExpandedSize].
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
     * Hides the card at the back of the visible stack (the one added earliest).
     * @return `true` if a card was hidden, `false` otherwise.
     */
    fun hideBack(): Boolean {
        snapshotStateList.firstOrNull { !it.hidden.value }?.let {
            it.hidden.value = true
            return true
        }
        return false
    }

    /**
     * Hides the card at the front of the visible stack (the one added most recently).
     * @return `true` if a card was hidden, `false` otherwise.
     */
    fun hideFront(): Boolean {
        snapshotStateList.lastOrNull { !it.hidden.value }?.let {
            it.hidden.value = true
            return true
        }
        return false
    }

    /**
     * Removes the card at the back of the stack.
     * @param skipHidden If `true`, removes the oldest *visible* card. If `false`, removes the oldest card regardless of visibility.
     * @return `true` if a card was removed, `false` otherwise.
     */
    fun removeBack(skipHidden: Boolean = false): Boolean {
        snapshotStateList.firstOrNull { skipHidden && !it.hidden.value || !skipHidden }?.let {
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    /**
     * Removes the card at the front of the stack.
     * @param skipHidden If `true`, removes the newest *visible* card. If `false`, removes the newest card regardless of visibility.
     * @return `true` if a card was removed, `false` otherwise.
     */
    fun removeFront(skipHidden: Boolean = false): Boolean {
        snapshotStateList.lastOrNull { skipHidden && !it.hidden.value || !skipHidden }?.let {
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    /**
     * Shows the newest hidden card from the back of the stack.
     * @return `true` if a hidden card was shown, `false` otherwise.
     */
    fun showBack(): Boolean {
        snapshotStateList.lastOrNull { it.hidden.value }?.let {
            it.hidden.value = false
            return true
        }
        return false
    }

    /**
     * Shows the oldest hidden card from the front of the stack.
     * @return `true` if a hidden card was shown, `false` otherwise.
     */
    fun showFront(): Boolean {
        snapshotStateList.firstOrNull { it.hidden.value }?.let {
            it.hidden.value = false
            return true
        }
        return false
    }

    /**
     * Makes all snackBar cards in the stack visible.
     */
    fun showAll() {
        snapshotStateList.forEach {
            it.hidden.value = false
        }
    }

    /**
     * @return The total number of cards in the stack, including hidden ones.
     */
    fun size(): Int = snapshotStateList.size

    /**
     * @return The number of currently visible cards in the stack.
     */
    fun sizeVisible(): Int = snapshotStateList.count { !it.hidden.value }
}

/**
 * Creates and remembers a [SnackbarStackState] instance.
 *
 * @param initial The initial list of [SnackbarItemModel]s to populate the stack.
 * @param maxExpandedSize The maximum number of visible snackBars when the stack is expanded.
 * @param maxCollapsedSize The maximum number of visible snackBars when the stack is collapsed.
 * @return A remembered [SnackbarStackState] instance.
 */
@Composable
fun rememberSnackBarStackState(
    initial: List<SnackBarItemModel> = emptyList(),
    maxExpandedSize: Int = 10,
    maxCollapsedSize: Int = 5
): SnackBarStackState {
    return remember {
        SnackBarStackState(
            cards = initial.toMutableList(),
            maxExpandedSize = maxExpandedSize,
            maxCollapsedSize = maxCollapsedSize
        )
    }
}

/**
 * Configuration for the visual properties of the [SnackBarStack].
 *
 * @param cardWidthExpanded The width of a snackBar card when the stack is expanded.
 * @param cardHeightExpanded The height of a snackBar card when the stack is expanded.
 * @param cardGapExpanded The vertical spacing between cards when the stack is expanded.
 * @param cardWidthCollapsed The width of a snackBar card when the stack is collapsed.
 * @param cardHeightCollapsed The height of a snackBar card when the stack is collapsed.
 * @param cardGapCollapsed The vertical spacing (peek height) between cards when the stack is collapsed.
 * @param stackAbove Internal flag to control stacking direction. Currently not implemented.
 */
data class SnackBarStackConfig(
    val cardWidthExpanded: Dp = 320.dp,
    val cardHeightExpanded: Dp = 160.dp,
    val cardGapExpanded: Dp = 10.dp,
    val cardWidthCollapsed: Dp = 280.dp,
    val cardHeightCollapsed: Dp = 80.dp,
    val cardGapCollapsed: Dp = 8.dp,
    internal val stackAbove: Boolean = true, //TODO: Fix Stack Above option, disabling for now
)

/**
 * A composable that displays a stack of snackBar notifications.
 *
 * The stack can be in a collapsed state, showing a condensed view of notifications,
 * or an expanded state, showing a scrollable list.
 *
 * @param state The [SnackBarStackState] that manages the content and state of the stack.
 * @param modifier The modifier to be applied to the stack container.
 * @param contentModifier The modifier to be applied to the content within each snackBar card.
 * @param snackBarStackConfig The configuration for the visual properties of the stack.
 * @param enableSwipeToDismiss If `true`, allows users to swipe away the top card (in collapsed mode) or any card (in expanded mode).
 * @param expandOnCardClick If `true`, toggles the expanded state of the stack when the top card is clicked.
 */
@Composable
fun SnackBarStack(
    state: SnackBarStackState,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    snackBarStackConfig: SnackBarStackConfig = SnackBarStackConfig(),
    enableSwipeToDismiss: Boolean = true,
    expandOnCardClick: Boolean = true,
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(animatedStackHeight)
            .then(if (state.expanded) Modifier.verticalScroll(scrollState) else Modifier),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedStackHeight),
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
                    SnackBarStackItem(
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
                            if (expandOnCardClick) {
                                state.toggleExpanded()
                            }
                        },
                        snackBarStackConfig = snackBarStackConfig,
                        enableSwipeToDismiss = enableSwipeToDismiss
                    )
                }
            }
        }
    }
}

/**
 * Represents a single animated item within the [SnackBarStack].
 * This composable manages its own position, scale, opacity, and swipe-to-dismiss behavior.
 *
 * @param model The data model for this snackBar item.
 * @param isHidden Whether the item is currently hidden and should be faded out.
 * @param expanded Whether the parent stack is in an expanded state.
 * @param index The logical index of the card from the top of the stack (0 is the topmost).
 * @param invertedIndex The logical index from the bottom of the stack (0 is the bottommost).
 * @param stackedWidthScaleFactor The factor by which to scale the width of cards underneath the top card in collapsed mode.
 * @param onSwipedAway Callback invoked when the card has been successfully swiped away.
 * @param onClick Callback invoked when the card is clicked.
 * @param snackBarStackConfig Configuration for the visual properties.
 * @param enableSwipeToDismiss Whether swipe-to-dismiss is enabled for this item.
 */
@Composable
private fun SnackBarStackItem(
    model: SnackBarItemModel,
    isHidden: Boolean,
    expanded: Boolean,
    index: Int,
    invertedIndex: Int,
    stackedWidthScaleFactor: Float = STACKED_WIDTH_SCALE_FACTOR,
    onSwipedAway: (String) -> Unit,
    onClick: () -> Unit = {},
    snackBarStackConfig: SnackBarStackConfig,
    enableSwipeToDismiss: Boolean = true,
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
    LaunchedEffect(targetYOffset) {
        animatedYOffset.animateTo(
            targetYOffset * (if (stackAbove) -1f else 1f),
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }

    val targetWidthScale = if (expanded) 1f else stackedWidthScaleFactor.pow(index)
    val animatedWidthScale = animateFloatAsState(
        targetValue = targetWidthScale,
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

    val token = model.snackBarToken
    val snackBarInfo = SnackBarInfo(model.snackBarStyle, false)

    Box(
        modifier = Modifier
            .graphicsLayer(
                alpha = opacityProgress.value,
                translationX = offsetX + (slideInProgress.value * with(localDensity) { 200.dp.toPx() }),
                translationY = animatedYOffset.value,
                scaleX = animatedWidthScale.value,
                scaleY = animatedWidthScale.value
            )
            .width(cardWidth)
            .height(cardHeight)
            .padding(horizontal = 0.dp)
            .then(
                if (enableSwipeToDismiss && (isTop || expanded)) Modifier.pointerInput(model.id) {
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
                .shadow(
                    elevation = token.shadowElevationValue(snackBarInfo),
                    shape = token.cardShape(snackBarInfo),
                    clip = false
                )
                .clip(token.cardShape(snackBarInfo))
                .background(token.backgroundBrush(snackBarInfo))
                .clickable(
                    enabled = isTop || expanded,
                    onClick = {
                        onClick()
                    }
                )
                .padding(token.contentPadding(snackBarInfo))
                .animateContentSize()
        )
        {
            model.content()
        }
    }
}

/**
 * A composable that displays a scrim over its background. It dims the background
 * and intercepts all clicks, preventing them from reaching the content behind it.
 *
 * @param isActivated Whether the scrim is currently active.
 * @param onDismiss A lambda to be invoked when the scrim area is clicked.
 */
@Composable
fun Scrim(
    isActivated: Boolean,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrimColor by animateColorAsState(
        targetValue = if (isActivated) Color.Black.copy(alpha = 0.6f) else Color.Transparent,
        animationSpec = tween(durationMillis = 300),
        label = "ScrimColorAnimation"
    )

    if (scrimColor.alpha > 0f) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(scrimColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDismiss
                )
        )
    }
}