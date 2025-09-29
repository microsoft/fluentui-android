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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
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
import kotlin.math.abs
import kotlin.math.pow

private const val ANIMATION_DURATION_MS = 250
//TODO: Add accessibility support for the stack and individual snackbars
//TODO: Perf, reduce recompositions, make stable, minimize launch effect tracked variables

enum class ItemVisibility {
    Visible,
    Hidden,
    BeingRemoved
}

private val DEFAULT_SNACKBAR_TOKENS = StackableSnackBarTokens()

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
    internal val initialSnackbars: MutableList<SnackBarItemModel> = mutableListOf(),
    internal var maxCollapsedSize: Int = 5,
    internal var maxExpandedSize: Int = 10
) {
    val snapshotStateList: MutableList<SnackBarItemModel> = mutableStateListOf<SnackBarItemModel>().apply { addAll(initialSnackbars) }
    val contentHeightMap = mutableStateMapOf<String, Int>().apply { putAll(initialSnackbars.associate { it.id to 0 }) }
    val itemVisibilityMap = mutableStateMapOf<String, ItemVisibility>().apply { putAll(initialSnackbars.associate { it.id to ItemVisibility.Visible }) }

    var expanded by mutableStateOf(false)
        private set
    internal var maxCurrentSize = maxCollapsedSize

    internal var combinedStackHeight by mutableStateOf(0)

    fun addSnackbar(snackbar: SnackBarItemModel) {
        if (snapshotStateList.any { it.id == snackbar.id }) {
            return
        }
        maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize
        snapshotStateList.add(snackbar)
        contentHeightMap[snackbar.id] = 0
        itemVisibilityMap[snackbar.id] = ItemVisibility.Visible
        if (sizeVisible() > maxCurrentSize) {
            hideBack()
        }
    }

    fun removeSnackbarById(id: String): Boolean {
        snapshotStateList.firstOrNull { it.id == id }?.let {
            contentHeightMap.remove(it.id)
            itemVisibilityMap.remove(it.id)
            snapshotStateList.remove(it)
            return true
        }
        return false
    }

    suspend fun removeSnackbarByIdWithAnimation(
        id: String,
        showLastHiddenSnackbarOnRemove: Boolean = true,
        onRemoveCompleteCallback: () -> Unit = {}
    ) {
        val snackbar = snapshotStateList.firstOrNull { it.id == id } ?: return
        itemVisibilityMap[snackbar.id] = ItemVisibility.BeingRemoved
        delay(ANIMATION_DURATION_MS.toLong())
        contentHeightMap.remove(snackbar.id)
        snapshotStateList.remove(snackbar)
        itemVisibilityMap.remove(snackbar.id)
        if (showLastHiddenSnackbarOnRemove) {
            onVisibleSizeChange()
        }
        onRemoveCompleteCallback()
    }

    fun toggleExpandedState() {
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

    fun size(): Int = snapshotStateList.size

    fun sizeVisible(): Int =
        snapshotStateList.count { itemVisibilityMap[it.id] == ItemVisibility.Visible }
}

@Composable
fun rememberSnackBarStackState(
    initial: List<SnackBarItemModel> = emptyList(),
    maxExpandedSize: Int = 10,
    maxCollapsedSize: Int = 5
): SnackBarStackState {
    return remember {
        SnackBarStackState(
            initialSnackbars = initial.toMutableList(),
            maxExpandedSize = maxExpandedSize,
            maxCollapsedSize = maxCollapsedSize
        )
    }
}

data class SnackBarStackConfig(
    val snackbarGapWhenExpanded: Dp = 10.dp,
    val snackbarHeightWhenCollapsed: Dp = 80.dp,
    val maximumTextLinesWhenCollapsed: Int = 2,
    val snackbarPeekHeightWhenCollapsed: Dp = 8.dp,
    val snackbarStackBottomPadding: Dp = 20.dp,
    val snackbarStackExpandedTopPadding: Dp = 200.dp
)

@Composable
fun SnackBarStack(
    state: SnackBarStackState,
    snackBarStackConfig: SnackBarStackConfig = SnackBarStackConfig(),
    enableSwipeToDismiss: Boolean = true,
) {
    val localDensity = LocalDensity.current

    val visibleSnackbarsCount by remember { derivedStateOf { state.sizeVisible() } }
    val targetHeight = if (visibleSnackbarsCount == 0) {
        0.dp
    } else if (state.expanded) {
        with(localDensity) { state.combinedStackHeight.toDp() + snackBarStackConfig.snackbarStackExpandedTopPadding }
    } else {
        snackBarStackConfig.snackbarHeightWhenCollapsed + (visibleSnackbarsCount - 1) * snackBarStackConfig.snackbarPeekHeightWhenCollapsed
    }
    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MS, easing = FastOutSlowInEasing)
    )

    val scrollState = rememberScrollState() //TODO: Keep Focus Anchored To the Bottom when expanded and new snackbar added
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
            var visibleIndex = 0
            state.snapshotStateList.forEachIndexed { index, snackBarModel ->
                val logicalIndex = visibleSnackbarsCount - 1 - visibleIndex
                visibleIndex += if (state.itemVisibilityMap[snackBarModel.id] == ItemVisibility.Visible) 1 else 0
                key(snackBarModel.id) {
                    SnackBarStackItem(
                        state = state,
                        visibleIndex = logicalIndex,
                        trueIndex = index,
                        onSwipedAway = { idToRemove ->
                            state.removeSnackbarById(idToRemove)
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
    onSwipedAway: (String) -> Unit,
    snackBarStackConfig: SnackBarStackConfig,
    enableSwipeToDismiss: Boolean = true
) {
    val model = state.snapshotStateList[trueIndex]
    val cardHeight = snackBarStackConfig.snackbarHeightWhenCollapsed
    val peekHeight = snackBarStackConfig.snackbarPeekHeightWhenCollapsed
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val isTop = visibleIndex == 0

    val token = model.snackBarToken
    val snackBarInfo = SnackBarInfo(model.style, false)
    val entryAnimationType = token.entryAnimationType(snackBarInfo)
    val exitAnimationType = token.exitAnimationType(snackBarInfo)

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

    val stackedWidthScaleFactor = token.snackbarWidthScalingFactor(snackBarInfo).coerceIn(0.01f, 2.0f)
    val targetWidthScale = if (state.expanded) 1f else stackedWidthScaleFactor.pow(visibleIndex)
    val animatedWidthScale = animateFloatAsState(
        targetValue = targetWidthScale,
        animationSpec = tween(durationMillis = ANIMATION_DURATION_MS, easing = FastOutSlowInEasing)
    )

    val opacityProgress = remember { Animatable(0f) }
    LaunchedEffect(state.itemVisibilityMap[model.id]) {
        val visibility = state.itemVisibilityMap[model.id] ?: ItemVisibility.Visible
        opacityProgress.animateTo(
            if (visibility != ItemVisibility.Visible) 0f else 1f,
            tween(ANIMATION_DURATION_MS)
        )
    }

    val initialXOffset = when (entryAnimationType) {
        StackableSnackbarEntryAnimationType.SlideInFromLeft -> -with(localDensity) { screenWidth.toPx() }
        StackableSnackbarEntryAnimationType.SlideInFromRight -> with(localDensity) { screenWidth.toPx() }
        StackableSnackbarEntryAnimationType.FadeIn -> 0f
        StackableSnackbarEntryAnimationType.SlideInFromAbove -> 0f
        StackableSnackbarEntryAnimationType.SlideInFromBelow -> 0f
    }
    val swipeX = remember { Animatable(initialXOffset) }
    LaunchedEffect(state.itemVisibilityMap[model.id], state.expanded, visibleIndex) {
        if (state.itemVisibilityMap[model.id] == ItemVisibility.BeingRemoved) {
            val target = when (exitAnimationType) {
                StackableSnackbarExitAnimationType.SlideOutToLeft -> -with(localDensity) { screenWidth.toPx() * 1.2f }
                StackableSnackbarExitAnimationType.SlideOutToRight -> with(localDensity) { screenWidth.toPx() * 1.2f }
                StackableSnackbarExitAnimationType.FadeOut -> 0f
            }
            swipeX.animateTo(
                with(localDensity) { target },
                animationSpec = tween(durationMillis = ANIMATION_DURATION_MS, easing = FastOutLinearInEasing)
            )
        } else {
            if(isTop) {
                swipeX.animateTo(0f)
            }
            else {
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
                            if( model.id in state.contentHeightMap && state.contentHeightMap[model.id] == contentHeight) {
                                return@onGloballyPositioned
                            }
                            state.contentHeightMap[model.id] =
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
                val messageMaxLines = if (state.expanded) Int.MAX_VALUE else snackBarStackConfig.maximumTextLinesWhenCollapsed

                BasicText(
                    text = model.message,
                    style = token.titleTypography(snackBarInfo),
                    maxLines = messageMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
                if (!model.subTitle.isNullOrBlank()) {
                    BasicText(
                        text = model.subTitle,
                        style = token.subtitleTypography(snackBarInfo),
                        maxLines = messageMaxLines,
                        overflow = TextOverflow.Ellipsis,
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
