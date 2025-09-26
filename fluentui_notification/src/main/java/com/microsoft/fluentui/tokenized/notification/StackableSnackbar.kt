package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.times
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
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
import com.microsoft.fluentui.tokenized.controls.Button
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.pow

private const val FADE_OUT_DURATION = 350
private const val STACKED_WIDTH_SCALE_FACTOR = 0.95f

//TODO: Add accessibility support for the stack and individual cards
//TODO: Perf, reduce recompositions, make stable, minimize launch effect tracked variables

enum class ItemVisibility {
    Visible,
    Hidden,
    BeingRemoved
}

private val DEFAULT_SNACKBAR_TOKENS = object : StackableSnackBarTokens() {
    @Composable
    override fun shadowElevationValue(snackBarInfo: SnackBarInfo): Dp {
        return FluentGlobalTokens.ShadowTokens.Shadow08.value
    }
}

@Stable
data class SnackBarItemModel(
    val message: String,
    val id: String = java.util.UUID.randomUUID().toString(),
    val style: SnackbarStyle = SnackbarStyle.Neutral,
    val leadingIcon: FluentIcon? = null,
    val trailingIcon: FluentIcon? = null,
    val subTitle: String? = null,
    val actionText: String? = null,
    val snackBarToken: StackableSnackBarTokens = DEFAULT_SNACKBAR_TOKENS,
    val onActionTextClicked: () -> Unit = {}
)

class SnackBarStackState(
    internal val cards: MutableList<SnackBarItemModel>,
    internal var maxCollapsedSize: Int = 5,
    internal var maxExpandedSize: Int = 10
) {
    val contentHeightMap =
        mutableStateMapOf<String, Int>().apply { putAll(cards.associate { it.id to 0 }) }
    val itemVisibilityMap =
        mutableStateMapOf<String, ItemVisibility>().apply { putAll(cards.associate { it.id to ItemVisibility.Visible }) }

    val snapshotStateList: MutableList<SnackBarItemModel> =
        mutableStateListOf<SnackBarItemModel>().apply { addAll(cards) }

    var expanded by mutableStateOf(false)
        private set

    internal var maxCurrentSize = maxCollapsedSize

    fun addCard(card: SnackBarItemModel) {
        if (snapshotStateList.any { it.id == card.id }) {
            return
        }
        maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize
        snapshotStateList.add(card)
        contentHeightMap[card.id] = 0
        itemVisibilityMap[card.id] = ItemVisibility.Visible
        if (sizeVisible() > maxCurrentSize) {
            hideBack()
        }
    }

    fun removeCardById(id: String): Boolean {
        snapshotStateList.firstOrNull { it.id == id }?.let {
            contentHeightMap.remove(it.id)
            itemVisibilityMap.remove(it.id)
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    suspend fun removeCardByIdWithAnimation(
        id: String,
        showLastHiddenCardOnRemove: Boolean = true,
        onRemoveCompleteCallback: () -> Unit = {}
    ) {
        val card = snapshotStateList.firstOrNull { it.id == id } ?: return
        itemVisibilityMap[card.id] = ItemVisibility.BeingRemoved
        delay(FADE_OUT_DURATION.toLong())
        contentHeightMap.remove(card.id)
        snapshotStateList.remove(card)
        itemVisibilityMap.remove(card.id)
        if (showLastHiddenCardOnRemove) {
            onVisibleSizeChange()
        }
        onRemoveCompleteCallback()
    }

    fun toggleExpanded() {
        expanded = !expanded
        maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize
        onVisibleSizeChange()
    }

    private fun onVisibleSizeChange() {
        val currentSize =
            snapshotStateList.count { itemVisibilityMap[it.id] == ItemVisibility.Visible }
        val (count, sequence, targetHidden) =
            if (currentSize > maxCurrentSize) {
                Triple(currentSize - maxCurrentSize, snapshotStateList, ItemVisibility.Hidden)
            } else {
                Triple(
                    maxCurrentSize - currentSize,
                    snapshotStateList.asReversed(),
                    ItemVisibility.Visible
                )
            }

        var slots = count
        sequence.forEach {
            if (slots <= 0) return@forEach
            if (itemVisibilityMap[it.id] != targetHidden) {
                itemVisibilityMap[it.id] = targetHidden
                slots--
            }
        }
    }

    fun hideBack(): Boolean {
        snapshotStateList.firstOrNull { itemVisibilityMap[it.id] == ItemVisibility.Visible }?.let {
            itemVisibilityMap[it.id] = ItemVisibility.Hidden
            return true
        }
        return false
    }

    fun hideFront(): Boolean {
        snapshotStateList.lastOrNull { itemVisibilityMap[it.id] == ItemVisibility.Visible }?.let {
            itemVisibilityMap[it.id] = ItemVisibility.Hidden
            return true
        }
        return false
    }

    fun removeFront(skipHidden: Boolean = false): Boolean {
        snapshotStateList.lastOrNull { (skipHidden && itemVisibilityMap[it.id] == ItemVisibility.Visible) || !skipHidden }
            ?.let {
                contentHeightMap.remove(it.id)
                snapshotStateList.remove(it)
                itemVisibilityMap.remove(it.id)
                return true
            }
        return false
    }

    fun showBack(): Boolean {
        snapshotStateList.lastOrNull { itemVisibilityMap[it.id] == ItemVisibility.Hidden }?.let {
            itemVisibilityMap[it.id] = ItemVisibility.Visible
            return true
        }
        return false
    }

    internal fun heightAfterIndex(index: Int): Int {
        var ans = 0
        snapshotStateList.drop(index + 1).forEach {
            if (itemVisibilityMap[it.id] == ItemVisibility.Visible) {
                ans += (contentHeightMap[it.id] ?: 0)
            }
        }
        return ans
    }

    internal fun visibleCountAfterIndex(index: Int): Int {
        return snapshotStateList.drop(index + 1).filterIndexed { i, _ ->
            itemVisibilityMap[snapshotStateList[i + index + 1].id] == ItemVisibility.Visible
        }.size
    }

    fun size(): Int = snapshotStateList.size

    fun sizeVisible(): Int =
        snapshotStateList.count { itemVisibilityMap[it.id] == ItemVisibility.Visible }
}

/**
 * Creates and remembers a [SnackbarStackState] instance.
 *
 * @param initial The initial list of [SnackBarItemModel]s to populate the stack.
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
    internal val stackAbove: Boolean = true, //TODO: Fix Stack Above option, working for true, disabling for now
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
    val localDensity = LocalDensity.current

    val visibleCardsCount by remember { derivedStateOf { state.sizeVisible() } }
    val targetHeight = if (visibleCardsCount == 0) {
        snackBarStackConfig.cardHeightCollapsed
    } else if (state.expanded) {
        with(localDensity) { state.heightAfterIndex(0).toDp() + 200.dp }
    } else {
        snackBarStackConfig.cardHeightCollapsed + (visibleCardsCount - 1) * snackBarStackConfig.cardGapCollapsed
    }
    val animatedStackHeight by animateDpAsState( //TODO: CHANGE TO LAUNCHED EFFECT WITH ONLY A TRIGGER ON VISIBLE CARDS TOTAL HEIGHT CHANGE
        targetValue = targetHeight,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    val scrollState =
        rememberScrollState() //TODO: Keep Focus Anchored To the Bottom when expanded and new card added
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
            state.snapshotStateList.forEachIndexed { index, snackBarModel ->
                val logicalIndex = totalVisibleCards - 1 - visibleIndex
                visibleIndex += if (state.itemVisibilityMap[snackBarModel.id] == ItemVisibility.Visible) 1 else 0
                key(snackBarModel.id) {
                    SnackBarStackItem(
                        state = state,
                        visibleIndex = logicalIndex,
                        trueIndex = index,
                        onSwipedAway = { idToRemove ->
                            state.removeCardById(idToRemove)
                            state.showBack()
                        },
                        snackBarStackConfig = snackBarStackConfig,
                        enableSwipeToDismiss = enableSwipeToDismiss
                    )
                }
            }
        }
    }
}

@Composable
private fun SnackBarStackItem(
    state: SnackBarStackState,
    visibleIndex: Int,
    trueIndex: Int,
    stackedWidthScaleFactor: Float = STACKED_WIDTH_SCALE_FACTOR,
    onSwipedAway: (String) -> Unit,
    snackBarStackConfig: SnackBarStackConfig,
    enableSwipeToDismiss: Boolean = true,
) {
    val model = state.snapshotStateList[trueIndex]
    val cardHeight = snackBarStackConfig.cardHeightCollapsed
    val peekHeight = snackBarStackConfig.cardGapCollapsed
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val isTop = visibleIndex == 0

    val animatedYOffset = remember { Animatable(with(localDensity) { cardHeight.toPx() }) }
    LaunchedEffect(
        trueIndex,
        state.expanded,
        state.snapshotStateList.size,
        state.heightAfterIndex(trueIndex),
        state.itemVisibilityMap[model.id]
    ) {
        if (state.itemVisibilityMap[model.id] == ItemVisibility.BeingRemoved) {
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

    val targetWidthScale = if (state.expanded) 1f else stackedWidthScaleFactor.pow(visibleIndex)
    val animatedWidthScale = animateFloatAsState(
        targetValue = targetWidthScale,
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
    )

    val opacityProgress = remember { Animatable(0f) }
    LaunchedEffect(state.itemVisibilityMap[model.id]) {
        val visibility = state.itemVisibilityMap[model.id] ?: ItemVisibility.Visible
        opacityProgress.animateTo(
            if (visibility != ItemVisibility.Visible) 0f else 1f,
            tween(FADE_OUT_DURATION)
        )
    }

    val swipeX = remember { Animatable(0f) }
    LaunchedEffect(state.itemVisibilityMap[model.id], state.expanded) {
        if (state.itemVisibilityMap[model.id] == ItemVisibility.BeingRemoved) {
            swipeX.animateTo(
                with(localDensity) { -screenWidth.toPx() * 1.2f },
                animationSpec = tween(durationMillis = 240, easing = FastOutLinearInEasing)
            )
        } else {
            swipeX.snapTo(0f)
        }
    }

    val token = model.snackBarToken
    val snackBarInfo = SnackBarInfo(model.style, false)
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
                            state.contentHeightMap[model.id] =
                                contentHeight + with(localDensity) {
                                    20.dp.toPx().toInt()
                                }
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
            .then(
                if (state.expanded) {
                    Modifier.wrapContentSize()
                } else {
                    Modifier
                        .height(cardHeight)
                }
            )
            .padding(horizontal = 0.dp)
            .then(
                if (enableSwipeToDismiss && (isTop || state.expanded)) Modifier.pointerInput(model.id) {
                    detectHorizontalDragGestures(
                        onDragStart = {},
                        onDragEnd = {
                            val threshold = with(localDensity) { (screenWidth / 4).toPx() }
                            scope.launch {
                                if (abs(swipeX.value) > threshold) {
                                    val target = if (swipeX.value > 0)
                                        with(localDensity) { screenWidth.toPx() * 1.2f }
                                    else
                                        -with(localDensity) { screenWidth.toPx() * 1.2f }

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
                .testTag(SNACK_BAR),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (model.leadingIcon != null && model.leadingIcon.isIconAvailable()) {
                Box(
                    modifier = Modifier
                        .testTag(SNACK_BAR_ICON)
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
                BasicText(
                    text = model.message,
                    style = token.titleTypography(snackBarInfo)
                )
                if (!model.subTitle.isNullOrBlank()) {
                    BasicText(
                        text = model.subTitle,
                        style = token.subtitleTypography(snackBarInfo),
                        modifier = Modifier.testTag(SNACK_BAR_SUBTITLE)
                    )
                }

            }

            if (model.actionText != null) {
                Button(
                    onClick = {
                        model.onActionTextClicked()
                    },
                    modifier = Modifier
                        .testTag(SNACK_BAR_ACTION_BUTTON)
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

            if (model.trailingIcon != null && model.trailingIcon.isIconAvailable()) {
                Box(
                    modifier = Modifier
                        .testTag(SNACK_BAR_ICON)
                        .then(
                            if (model.trailingIcon.onClick != null) {
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    enabled = true,
                                    role = Role.Image,
                                    onClick = model.trailingIcon.onClick!!
                                )
                            } else Modifier
                        )
                ) {
                    Icon(
                        model.trailingIcon,
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
