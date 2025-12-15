package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.ButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.SnackBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SnackbarStyle
import com.microsoft.fluentui.theme.token.controlTokens.StackableSnackBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.StackableSnackbarEntryAnimationType
import com.microsoft.fluentui.theme.token.controlTokens.StackableSnackbarExitAnimationType
import com.microsoft.fluentui.tokenized.controls.Button
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.pow

private const val ANIMATION_DURATION_MS = 250
private const val SWIPE_AWAY_ANIMATION_TARGET_FACTOR = 1.2f
private const val SWIPE_TO_DISMISS_THRESHOLD_DIVISOR = 4
private const val DEFAULT_NUMBER_OF_SNACKBARS_EXPANDED = 10
private const val DEFAULT_NUMBER_OF_SNACKBARS_COLLAPSED = 5

//TODO: Add accessibility support for the stack and individual snackbars
//TODO: Perf, reduce recompositions, make stable, minimize launch effect tracked variables

private object SnackBarTestTags {
    const val SNACK_BAR = "snack_bar"
    const val SNACK_BAR_ICON = "snack_bar_icon"
    const val SNACK_BAR_SUBTITLE = "snack_bar_subtitle"
    const val SNACK_BAR_ACTION_BUTTON = "snack_bar_action_button"
}

/**
 * Enum class to define the visibility state of a snackbar item within the stack.
 */
enum class ItemVisibility {
    /** The snackbar is currently visible. */
    Visible,

    /** The snackbar is currently hidden. */
    Hidden,

    /** The snackbar is being removed with an animation. */
    BeingRemoved
}

private val DEFAULT_SNACKBAR_TOKENS = StackableSnackBarTokens()

/**
 * Data model for an individual snackbar item.
 *
 * @property message The main text message to be displayed in the snackbar.
 * @property id A unique identifier for the snackbar item. Defaults to a random UUID.
 * @property style The visual style of the snackbar, e.g., Neutral, Danger, Warning.
 * @property leadingIcon An optional leading icon to display.
 * @property trailingIcon An optional trailing icon to display.
 * @property subTitle An optional subtitle text.
 * @property actionText Optional text for the action button. If null, no action button is shown.
 * @property snackBarToken The tokens for customizing the snackbar's appearance.
 * @property onActionTextClicked The callback to be invoked when the action button is clicked.
 */
@Stable
data class SnackBarItemModel(
    val message: String,
    val id: String = java.util.UUID.randomUUID().toString(),
    val style: SnackbarStyle = SnackbarStyle.Neutral,
    val leadingIcon: FluentIcon? = null,
    var trailingIcon: FluentIcon? = null,
    val subTitle: String? = null,
    val actionText: String? = null,
    val snackBarToken: StackableSnackBarTokens = DEFAULT_SNACKBAR_TOKENS,
    val onActionTextClicked: () -> Unit = {}
)

internal data class SnackbarItemInternal(
    val model: SnackBarItemModel,
    var visibility: MutableState<ItemVisibility> = mutableStateOf(ItemVisibility.Visible),
    var contentHeight: MutableIntState = mutableIntStateOf(0)
)

/**
 * State holder for a stack of snackbars. It manages the list of items, their visibility, and height,
 * providing methods to add, remove, and toggle the stack's expanded state.
 *
 * @param initialSnackbars The initial list of snackbars to be displayed.
 * @param maxCollapsedSize The maximum number of snackbars to show when the stack is collapsed.
 * @param maxExpandedSize The maximum number of snackbars to show when the stack is expanded.
 */
class SnackBarStackState(
    internal val initialSnackbars: MutableList<SnackBarItemModel> = mutableListOf(),
    internal var maxCollapsedSize: Int = DEFAULT_NUMBER_OF_SNACKBARS_COLLAPSED,
    internal var maxExpandedSize: Int = DEFAULT_NUMBER_OF_SNACKBARS_EXPANDED
) {
    internal val snapshotStateList: MutableList<SnackbarItemInternal> =
        mutableStateListOf<SnackbarItemInternal>().apply {
            addAll(initialSnackbars.map { SnackbarItemInternal(it) })
        }

    var expanded by mutableStateOf(false)
        private set
    internal var maxCurrentSize = maxCollapsedSize

    internal var combinedStackHeight by mutableIntStateOf(0)

    fun getSnackBarItemById(id: String): SnackBarItemModel? =
        snapshotStateList.firstOrNull { it.model.id == id }?.model

    fun getSnackbarItemByIndex(index: Int): SnackBarItemModel? =
        snapshotStateList.getOrNull(index)?.model

    fun getSnackBarItemIndexById(id: String): Int? =
        snapshotStateList.indexOfFirst { it.model.id == id }.let { if (it == -1) null else it }

    fun getAllSnackBarItems(): List<SnackBarItemModel> = snapshotStateList.map { it.model }

    suspend fun clearAllSnackBars(animateRemoval: Boolean = false) {
        if (animateRemoval) {
            snapshotStateList.reversed().forEach {
                it.visibility.value = ItemVisibility.BeingRemoved
                delay(40)
            }
            delay(ANIMATION_DURATION_MS.toLong())
        }
        snapshotStateList.clear()
    }

    /**
     * Adds a new snackbar to the stack. If the stack exceeds its maximum current size, the
     * oldest visible snackbar is automatically hidden.
     *
     * @param snackbar The [SnackBarItemModel] to add.
     */
    fun addSnackbar(snackbar: SnackBarItemModel) {
        if (snapshotStateList.any { it.model.id == snackbar.id }) {
            return
        }
        maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize
        snapshotStateList.add(SnackbarItemInternal(snackbar))
        if (sizeVisible() > maxCurrentSize) {
            hideOldest()
        }
    }

    /**
     * Removes a snackbar from the stack by its unique ID.
     *
     * @param id The ID of the snackbar to remove.
     * @return `true` if the snackbar was found and removed, `false` otherwise.
     */
    fun removeSnackbarById(id: String): Boolean {
        snapshotStateList.firstOrNull { it.model.id == id }?.let {
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    /**
     * Removes a snackbar with an exit animation. The actual removal from the state list is
     * delayed to allow the animation to complete.
     *
     * @param id The ID of the snackbar to remove.
     * @param showLastHiddenSnackbarOnRemove If `true`, the oldest hidden snackbar will become visible after removal.
     * @param onRemoveCompleteCallback A callback invoked after the removal process is complete.
     */
    suspend fun removeSnackbarByIdWithAnimation(
        id: String,
        showLastHiddenSnackbarOnRemove: Boolean = true,
        onRemoveCompleteCallback: () -> Unit = {}
    ) {
        val snackbar = snapshotStateList.firstOrNull { it.model.id == id } ?: return
        snackbar.visibility.value = ItemVisibility.BeingRemoved
        delay(ANIMATION_DURATION_MS.toLong())
        snapshotStateList.remove(snackbar)
        if (showLastHiddenSnackbarOnRemove) {
            onVisibleSizeChange()
        }
        onRemoveCompleteCallback()
    }

    /**
     * Toggles the expanded state of the snackbar stack. This changes the layout and the
     * maximum number of visible snackbars.
     */
    fun toggleExpandedState() {
        expanded = !expanded
        maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize
        onVisibleSizeChange()
    }

    /**
     * Adjusts the visibility of snackbars based on the current expanded/collapsed state and
     * the configured maximum size. This function ensures the number of visible snackbars
     * does not exceed the allowed maximum.
     */
    private fun onVisibleSizeChange() {
        val numVisibleSnackbars =
            snapshotStateList.count { it.visibility.value == ItemVisibility.Visible }
        var (numUpdatesRequired, sequenceIterationOrder, targetVisibilityAfterUpdate) =
            if (numVisibleSnackbars > maxCurrentSize) {
                Triple(
                    numVisibleSnackbars - maxCurrentSize,
                    snapshotStateList,
                    ItemVisibility.Hidden
                )
            } else {
                Triple(
                    maxCurrentSize - numVisibleSnackbars,
                    snapshotStateList.asReversed(),
                    ItemVisibility.Visible
                )
            }

        sequenceIterationOrder.forEach {
            if (numUpdatesRequired <= 0) return@forEach
            if (it.visibility.value != targetVisibilityAfterUpdate) {
                it.visibility.value = targetVisibilityAfterUpdate
                numUpdatesRequired--
            }
        }
    }

    /**
     * Hides the oldest visible snackbar.
     *
     * @return `true` if a snackbar was hidden, `false` otherwise.
     */
    fun hideOldest(): Boolean {
        snapshotStateList.firstOrNull { it.visibility.value == ItemVisibility.Visible }?.let {
            it.visibility.value = ItemVisibility.Hidden
            return true
        }
        return false
    }

    /**
     * Hides the newest visible snackbar.
     *
     * @return `true` if a snackbar was hidden, `false` otherwise.
     */
    fun hideLatest(): Boolean {
        snapshotStateList.lastOrNull { it.visibility.value == ItemVisibility.Visible }?.let {
            it.visibility.value = ItemVisibility.Hidden
            return true
        }
        return false
    }

    /**
     * Removes the newest snackbar from the stack.
     *
     * @param skipHidden If `true`, only visible snackbars are considered for removal.
     * @return `true` if a snackbar was removed, `false` otherwise.
     */
    fun removeLatest(skipHidden: Boolean = false): Boolean {
        snapshotStateList.lastOrNull { (skipHidden && it.visibility.value == ItemVisibility.Visible) || !skipHidden }
            ?.let {
                snapshotStateList.remove(it)
                return true
            }
        return false
    }

    /**
     * Shows the oldest hidden snackbar.
     *
     * @return `true` if a snackbar was shown, `false` otherwise.
     */
    fun showLastHidden(): Boolean {
        snapshotStateList.lastOrNull { it.visibility.value == ItemVisibility.Hidden }?.let {
            it.visibility.value = ItemVisibility.Visible
            return true
        }
        return false
    }

    /**
     * Calculates the combined height of all visible snackbars that appear after a given index.
     *
     * @param index The starting index.
     * @return The combined height in pixels.
     */
    internal fun heightAfterIndex(index: Int): Int {
        var ans = 0
        snapshotStateList.drop(index + 1).forEach {
            if (it.visibility.value == ItemVisibility.Visible) {
                ans += it.contentHeight.intValue
            }
        }
        return ans
    }

    /**
     * Returns the total number of snackbars (visible and hidden) in the stack.
     *
     * @return The total size of the stack.
     */
    fun size(): Int = snapshotStateList.size

    /**
     * Returns the number of currently visible snackbars in the stack.
     *
     * @return The number of visible snackbars.
     */
    fun sizeVisible(): Int =
        snapshotStateList.count { it.visibility.value == ItemVisibility.Visible }
}

/**
 * Creates and remembers a [SnackBarStackState] for managing a stack of snackbars.
 *
 * @param initial The initial list of snackbar models. Defaults to an empty list.
 * @param maxExpandedSize The maximum number of snackbars to show when the stack is expanded.
 * @param maxCollapsedSize The maximum number of snackbars to show when the stack is collapsed.
 * @return A [SnackBarStackState] instance.
 */
@Composable
fun rememberSnackBarStackState(
    initial: List<SnackBarItemModel> = emptyList(),
    maxExpandedSize: Int = DEFAULT_NUMBER_OF_SNACKBARS_EXPANDED,
    maxCollapsedSize: Int = DEFAULT_NUMBER_OF_SNACKBARS_COLLAPSED
): SnackBarStackState {
    return remember {
        SnackBarStackState(
            initialSnackbars = initial.toMutableList(),
            maxExpandedSize = maxExpandedSize,
            maxCollapsedSize = maxCollapsedSize
        )
    }
}

/**
 * Configuration data for the [SnackBarStack] composable.
 *
 * @property snackbarGapWhenExpanded The vertical gap between snackbars when the stack is expanded.
 * @property snackbarHeightWhenCollapsed The fixed height of a snackbar when the stack is collapsed.
 * @property maximumTextLinesWhenCollapsed The maximum number of text lines allowed for a snackbar's message and subtitle when collapsed.
 * @property snackbarPeekHeightWhenCollapsed The vertical distance one snackbar peeks from the one below it when collapsed.
 * @property snackbarStackBottomPadding The padding at the bottom of the entire stack.
 * @property snackbarStackExpandedTopPadding The padding at the top of the stack when it's expanded.
 */
data class SnackBarStackConfig(
    val snackbarGapWhenExpanded: Dp = 10.dp,
    val maxSnackbarHeightWhenCollapsed: Dp = 80.dp,
    val maximumTextLinesWhenCollapsed: Int = 2,
    val snackbarPeekHeightWhenCollapsed: Dp = 8.dp,
    val snackbarStackBottomPadding: Dp = 20.dp,
    val snackbarStackExpandedTopPadding: Dp = 200.dp
)

/**
 * A composable that displays a stack of snackbars. It supports a collapsed state (showing a
 * limited number of items) and an expanded state (showing all items). The stack is
 * managed by the provided [SnackBarStackState].
 *
 * @param state The state object that manages the snackbar stack.
 * @param snackBarStackConfig The configuration for the stack's appearance and behavior.
 * @param enableSwipeToDismiss If `true`, the top visible snackbar can be swiped to dismiss it.
 */
@Composable
fun SnackBarStack(
    state: SnackBarStackState,
    snackBarStackConfig: SnackBarStackConfig = SnackBarStackConfig(),
    enableSwipeToDismiss: Boolean = true,
) {
    val localDensity = LocalDensity.current

    val totalVisibleSnackbars by remember { derivedStateOf { state.sizeVisible() } }
    val targetHeight = if (totalVisibleSnackbars == 0) {
        0.dp
    } else if (state.expanded) {
        with(localDensity) { state.combinedStackHeight.toDp() + snackBarStackConfig.snackbarStackExpandedTopPadding }
    } else {
        snackBarStackConfig.maxSnackbarHeightWhenCollapsed + (totalVisibleSnackbars - 1) * snackBarStackConfig.snackbarPeekHeightWhenCollapsed
    }
    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MS, easing = FastOutSlowInEasing),
        label = "StackHeightAnimation"
    )

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthPx = with(localDensity) { screenWidth.toPx() }

    val scrollState =
        rememberScrollState() //TODO: Keep Focus Anchored To the Bottom when expanded and new snackbar added
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(scrollState, enabled = state.expanded)
            .padding(bottom = snackBarStackConfig.snackbarStackBottomPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedStackHeight),
            contentAlignment = Alignment.BottomCenter
        ) {
            var visibleSnackbarsEncountered = 0
            state.snapshotStateList.forEachIndexed { index, snackBarModel ->
                val visibleIndex = totalVisibleSnackbars - 1 - visibleSnackbarsEncountered
                visibleSnackbarsEncountered += if (snackBarModel.visibility.value == ItemVisibility.Visible) 1 else 0
                key(snackBarModel.model.id) {
                    SnackBarStackItem(
                        state = state,
                        visibleIndex = visibleIndex,
                        trueIndex = index,
                        onSwipedAway = { idToRemove ->
                            state.removeSnackbarById(idToRemove)
                            state.showLastHidden()
                        },
                        snackBarStackConfig = snackBarStackConfig,
                        enableSwipeToDismiss = enableSwipeToDismiss,
                        screenWidthPx = screenWidthPx
                    )
                }
            }
        }
    }
}

/**
 * A private composable for a single snackbar item in the stack. It handles individual
 * animations, styling, and swipe-to-dismiss gestures.
 *
 * @param state The state object managing the snackbar stack.
 * @param visibleIndex The logical index of the snackbar among the visible items.
 * @param trueIndex The actual index of the snackbar in the full list.
 * @param onSwipedAway A callback to be invoked when the snackbar is swiped off-screen.
 * @param snackBarStackConfig The configuration for the stack's appearance.
 * @param enableSwipeToDismiss If `true`, swiping the snackbar horizontally will dismiss it.
 * @param screenWidthPx The width of the screen in pixels, used for swipe animation.
 */
@Composable
private fun SnackBarStackItem(
    state: SnackBarStackState,
    visibleIndex: Int,
    trueIndex: Int,
    onSwipedAway: (String) -> Unit,
    snackBarStackConfig: SnackBarStackConfig,
    enableSwipeToDismiss: Boolean = true,
    screenWidthPx: Float
) {
    val modelWrapper = state.snapshotStateList[trueIndex]
    val model = modelWrapper.model
    val cardHeight = snackBarStackConfig.maxSnackbarHeightWhenCollapsed
    val peekHeight = snackBarStackConfig.snackbarPeekHeightWhenCollapsed

    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val isTop = visibleIndex == 0

    val token = model.snackBarToken
    val snackBarInfo = SnackBarInfo(model.style, false)
    val entryAnimationType = token.entryAnimationType(snackBarInfo)
    val exitAnimationType = token.exitAnimationType(snackBarInfo)

    // Vertical Offset Animation: Related to Stack Expansion/Collapse and Item Position in Stack
    val initialYOffset = when (entryAnimationType) {
        StackableSnackbarEntryAnimationType.SlideInFromAbove -> -with(localDensity) { cardHeight.toPx() }
        StackableSnackbarEntryAnimationType.SlideInFromBelow -> with(localDensity) { cardHeight.toPx() }
        StackableSnackbarEntryAnimationType.FadeIn -> 0f
        StackableSnackbarEntryAnimationType.SlideInFromLeft -> 0f
        StackableSnackbarEntryAnimationType.SlideInFromRight -> 0f
    }
    val animatedYOffset = remember { Animatable(initialYOffset) }
    LaunchedEffect(
        trueIndex,
        state.expanded,
        state.snapshotStateList.size,
        state.heightAfterIndex(trueIndex),
        modelWrapper.visibility.value
    ) {
        if (modelWrapper.visibility.value == ItemVisibility.BeingRemoved) {
            return@LaunchedEffect
        }
        animatedYOffset.animateTo(
            with(localDensity) {
                if (state.expanded) -state.heightAfterIndex(trueIndex)
                    .toFloat() else (visibleIndex * -peekHeight).toPx()
            },
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }

    // Width Scale Animation: Related to Cards Shrinking when stacked
    val stackedWidthScaleFactor =
        token.snackbarWidthScalingFactor(snackBarInfo).coerceIn(0.01f, 2.0f)
    val targetWidthScale = if (state.expanded) 1f else stackedWidthScaleFactor.pow(visibleIndex)
    val animatedWidthScale = animateFloatAsState(
        targetValue = targetWidthScale,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MS, easing = FastOutSlowInEasing),
        label = "WidthScaleAnimation"
    )

    // Opacity Animation: Related to Entry/Exit Fade Animations
    val opacityProgress = remember { Animatable(0f) }
    LaunchedEffect(modelWrapper.visibility.value) {
        val visibility = modelWrapper.visibility.value
        opacityProgress.animateTo(
            if (visibility != ItemVisibility.Visible) 0f else 1f,
            tween(ANIMATION_DURATION_MS)
        )
    }

    // Horizontal Animations: Related to Swipe to Dismiss and Entry/Exit
    val initialXOffset = when (entryAnimationType) {
        StackableSnackbarEntryAnimationType.SlideInFromLeft -> -screenWidthPx
        StackableSnackbarEntryAnimationType.SlideInFromRight -> screenWidthPx
        StackableSnackbarEntryAnimationType.FadeIn -> 0f
        StackableSnackbarEntryAnimationType.SlideInFromAbove -> 0f
        StackableSnackbarEntryAnimationType.SlideInFromBelow -> 0f
    }
    val swipeX = remember { Animatable(initialXOffset) }
    LaunchedEffect(modelWrapper.visibility.value, state.expanded, visibleIndex) {
        if (modelWrapper.visibility.value == ItemVisibility.BeingRemoved) {
            val target = when (exitAnimationType) {
                StackableSnackbarExitAnimationType.SlideOutToLeft -> screenWidthPx * -SWIPE_AWAY_ANIMATION_TARGET_FACTOR
                StackableSnackbarExitAnimationType.SlideOutToRight -> screenWidthPx * SWIPE_AWAY_ANIMATION_TARGET_FACTOR
                StackableSnackbarExitAnimationType.FadeOut -> 0f
            }
            swipeX.animateTo(
                with(localDensity) { target },
                animationSpec = tween(
                    durationMillis = ANIMATION_DURATION_MS,
                    easing = FastOutLinearInEasing
                )
            )
        } else {
            if (isTop) {
                swipeX.animateTo(0f)
            } else {
                swipeX.snapTo(0f)
            }
        }
    }

    val textPaddingValues =
        if (model.actionText == null && model.trailingIcon != null) PaddingValues(
            start = 16.dp,
            top = 12.dp,
            bottom = 12.dp,
            end = 16.dp
        ) else PaddingValues(start = 16.dp, top = 12.dp, bottom = 12.dp)
    Box(
        modifier = Modifier
            .then(
                if (state.expanded) {
                    Modifier.onGloballyPositioned(
                        onGloballyPositioned = { coordinates: LayoutCoordinates ->
                            val contentHeight = coordinates.size.height
                            if (modelWrapper.contentHeight.intValue == contentHeight) {
                                return@onGloballyPositioned
                            }
                            modelWrapper.contentHeight.intValue =
                                contentHeight + with(localDensity) {
                                    snackBarStackConfig.snackbarGapWhenExpanded.toPx().toInt()
                                }
                            state.combinedStackHeight = state.heightAfterIndex(0)
                            return@onGloballyPositioned
                        }
                    )
                } else {
                    Modifier
                }
            )
            .graphicsLayer(
                alpha = opacityProgress.value,
                translationX = swipeX.value,
                translationY = animatedYOffset.value,
                scaleX = animatedWidthScale.value,
                scaleY = animatedWidthScale.value
            )
            .wrapContentHeight()
            .then(
                if (enableSwipeToDismiss && (isTop || state.expanded)) Modifier.pointerInput(model.id) {
                    detectHorizontalDragGestures(
                        onDragStart = {},
                        onDragEnd = {
                            val threshold = screenWidthPx / SWIPE_TO_DISMISS_THRESHOLD_DIVISOR
                            scope.launch {
                                if (abs(swipeX.value) > threshold) {
                                    val target = if (swipeX.value > 0)
                                        screenWidthPx * SWIPE_AWAY_ANIMATION_TARGET_FACTOR
                                    else
                                        screenWidthPx * -SWIPE_AWAY_ANIMATION_TARGET_FACTOR

                                    swipeX.animateTo(
                                        target,
                                        animationSpec = tween(
                                            durationMillis = ANIMATION_DURATION_MS,
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
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .defaultMinSize(minHeight = 52.dp)
                .fillMaxWidth()
                .shadow(
                    elevation = token.shadowElevationValue(snackBarInfo),
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
                .background(token.backgroundBrush(snackBarInfo))
                .semantics {
                    liveRegion = LiveRegionMode.Polite
                }
                .testTag(SnackBarTestTags.SNACK_BAR),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (model.leadingIcon != null && model.leadingIcon.isIconAvailable()) {
                Box(
                    modifier = Modifier
                        .testTag(SnackBarTestTags.SNACK_BAR_ICON)
                        .then(
                            if (model.leadingIcon.onClick != null) {
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    enabled = true,
                                    role = Role.Image,
                                    onClick = model.leadingIcon.onClick!!
                                )
                            } else Modifier
                        )
                ) {
                    Icon(
                        model.leadingIcon,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                            .size(token.leftIconSize(snackBarInfo)),
                        tint = token.iconColor(snackBarInfo)
                    )
                }
            }
            Column(
                Modifier
                    .weight(1F)
                    .padding(textPaddingValues)
            ) {
                val messageMaxLines =
                    if (state.expanded) Int.MAX_VALUE else snackBarStackConfig.maximumTextLinesWhenCollapsed

                Text(
                    text = model.message,
                    style = token.titleTypography(snackBarInfo),
                    maxLines = messageMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
                if (!model.subTitle.isNullOrBlank()) {
                    Text(
                        text = model.subTitle,
                        style = token.subtitleTypography(snackBarInfo),
                        maxLines = messageMaxLines,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.testTag(SnackBarTestTags.SNACK_BAR_SUBTITLE)
                    )
                }

            }

            if (model.actionText != null) {
                Button(
                    onClick = {
                        model.onActionTextClicked()
                    },
                    modifier = Modifier
                        .testTag(SnackBarTestTags.SNACK_BAR_ACTION_BUTTON)
                        .then(
                            if (model.trailingIcon != null)
                                Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            else
                                Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                        ),
                    text = model.actionText,
                    style = ButtonStyle.TextButton,
                    size = ButtonSize.Small,
                    buttonTokens = object : ButtonTokens() {
                        @Composable
                        override fun textColor(buttonInfo: ButtonInfo): StateColor {
                            return StateColor(
                                rest = token.iconColor(snackBarInfo),
                                pressed = token.iconColor(snackBarInfo),
                                focused = token.iconColor(snackBarInfo),
                            )
                        }
                    }
                )
            }

            if (model.trailingIcon != null && model.trailingIcon!!.isIconAvailable()) {
                Box(
                    modifier = Modifier
                        .testTag(SnackBarTestTags.SNACK_BAR_ICON)
                        .then(
                            if (model.trailingIcon!!.onClick != null) {
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    enabled = true,
                                    role = Role.Image,
                                    onClick = model.trailingIcon!!.onClick!!
                                )
                            } else Modifier
                        )
                ) {
                    Icon(
                        model.trailingIcon!!,
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp, end = 16.dp)
                            .size(token.leftIconSize(snackBarInfo)),
                        tint = token.iconColor(snackBarInfo)
                    )
                }
            }
        }
    }
}

private const val SCRIM_DEFAULT_OPACITY = 0.6f

/**
 * A composable that provides a semi-transparent overlay (scrim) that can be used to block user
 * interaction with content behind it. The scrim animates its color and alpha.
 *
 * @param isActivated `true` if the scrim should be visible and opaque, `false` otherwise.
 * @param onDismiss A callback to be invoked when the scrim is clicked.
 * @param modifier The modifier to be applied to the scrim.
 */
@Composable
fun Scrim(
    isActivated: Boolean,
    onDismiss: () -> Unit,
    activatedColor: Color = Color.Black.copy(alpha = SCRIM_DEFAULT_OPACITY),
    modifier: Modifier = Modifier
) {
    val scrimColor by animateColorAsState(
        targetValue = if (isActivated) activatedColor else Color.Transparent,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MS),
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