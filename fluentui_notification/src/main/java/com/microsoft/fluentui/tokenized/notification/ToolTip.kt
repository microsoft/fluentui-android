package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.microsoft.fluentui.icons.ToolTipIcons
import com.microsoft.fluentui.icons.tooltipicons.Tip
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.TooltipInfo
import com.microsoft.fluentui.theme.token.controlTokens.TooltipTokens
import com.microsoft.fluentui.util.dpToPx
import com.microsoft.fluentui.util.pxToDp
import com.microsoft.fluentui.util.softNavBarOffsetX
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine


/**
 * The state that is associated with an instance of a tooltip.
 * Each instance of tooltips should have its own [TooltipState].
 */
@Stable
interface TooltipState {
    /**
     * [Boolean] that will be used to update the visibility
     * state of the associated tooltip.
     */
    val isVisible: Boolean

    /**
     * Show the tooltip associated with the current [TooltipState].
     * When this method is called all of the other tooltips currently
     * being shown will dismiss.
     */
    suspend fun show()

    /**
     * Dismiss the tooltip associated with
     * this [TooltipState] if it's currently being shown.
     */
    fun dismiss()

    /**
     * Clean up when the this state leaves Composition.
     */
    fun onDispose()
}

@Stable
internal class TooltipStateImpl(private val mutatorMutex: MutatorMutex) : TooltipState {

    /**
     * [Boolean] that will be used to update the visibility
     * state of the associated tooltip.
     */
    override var isVisible by mutableStateOf(false)
        private set

    /**
     * continuation used to clean up
     */
    private var job: (CancellableContinuation<Unit>)? = null

    /**
     * Show the tooltip associated with the current [TooltipState].
     * It will dismiss after a short duration. When this method is called,
     * all of the other tooltips currently being shown will dismiss.
     */
    override suspend fun show() {
        mutatorMutex.mutate {
            try {
                suspendCancellableCoroutine { continuation: CancellableContinuation<Unit> ->
                    isVisible = true
                    job = continuation
                }
            } finally {
//                 cancellation has occurred
//                 and we close out the current tooltip.
                isVisible = false
            }
        }
    }

    /**
     * Dismiss the tooltip associated with
     * this [TooltipState] if it's currently being shown.
     */
    override fun dismiss() {
        isVisible = false
    }

    /**
     * Cleans up [MutatorMutex] when the tooltip associated
     * with this state leaves Composition.
     */
    override fun onDispose() {
        job?.cancel()
    }
}

object TooltipDefaults {
    /**
     * The global/default [MutatorMutex] used to sync Tooltips.
     */
    val GlobalMutatorMutex = MutatorMutex()
}

val TOOLTIP_TIP_TEST_TAG = "tooltip_tip_test_tag"
val TOOLTIP_CONTENT_TEST_TAG = "tooltip_content_test_tag"

/**
 * Create and remember the default [TooltipState].
 *
 * @param mutatorMutex [MutatorMutex] used to ensure that for all of the tooltips associated
 * with the mutator mutex, only one will be shown on the screen at any time.
 */
@Composable
fun rememberTooltipState(
    mutatorMutex: MutatorMutex = TooltipDefaults.GlobalMutatorMutex
): TooltipState =
    remember { TooltipStateImpl(mutatorMutex) }

/**
 *  ToolTipBox is a composable that shows a tooltip box with a title and a text anchor to its content.
 *  @param title The title of the tooltip box.
 *  @param text The text of the tooltip box.
 *  @param tooltipState The state of the tooltip box.
 *  @param modifier The modifier to be applied to the tooltip box.
 *  @param focusable Whether the tooltip box is focusable.
 *  @param offset The offset of the tooltip box.
 *  @param onDismissRequest The callback to be invoked when the tooltip box is dismissed.
 *  @param tooltipTokens The tooltip tokens that are used to customize the tooltip box.
 *  @param content The content of the tooltip box.*
 */
@Composable
fun ToolTipBox(
    title: String?,
    text: String,
    tooltipState: TooltipState,
    modifier: Modifier = Modifier,
    focusable: Boolean = true,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    onDismissRequest: (() -> Unit)? = null,
    tooltipTokens: TooltipTokens? = null,
    content: @Composable () -> Unit,
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = tooltipTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Tooltip] as TooltipTokens
    val tooltipInfo = TooltipInfo()
    ToolTipBox(
        tooltipContent = {
            Column(
                modifier = Modifier
                    .semantics(mergeDescendants = true) {}
                    .padding(token.padding(tooltipInfo = tooltipInfo))
                    .width(IntrinsicSize.Max),
            ) {
                if (!title.isNullOrBlank()) {
                    BasicText(
                        text = title,
                        style = token.titleTypography(tooltipInfo = tooltipInfo).merge(
                            TextStyle(color = token.titleColor(tooltipInfo = tooltipInfo))
                        )
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(
                                token.titleTextSpacing(
                                    tooltipInfo = tooltipInfo
                                )
                            )
                    )
                }
                BasicText(
                    text = text, style = token.textTypography(tooltipInfo = tooltipInfo).merge(
                        TextStyle(color = token.textColor(tooltipInfo = tooltipInfo))
                    )
                )
            }
        },
        tooltipState = tooltipState,
        modifier = modifier,
        focusable = focusable,
        offset = offset,
        onDismissRequest = onDismissRequest,
        tooltipTokens = token,
        content = content
    )
}

/**
 * ToolTipBox is a composable that shows a tooltipContent anchored to its content.
 * @param tooltipContent The content of the tooltip box.
 * @param tooltipState The state of the tooltip box.
 * @param modifier The modifier to be applied to the tooltip box.
 * @param focusable Whether the tooltip box is focusable.
 * @param offset The offset of the tooltip box.
 * @param onDismissRequest The callback to be invoked when the tooltip box is dismissed.
 * @param tooltipTokens The tooltip tokens that are used to customize the tooltip box.
 * @param content The content of the tooltip box.*
 */
@Composable
fun ToolTipBox(
    tooltipContent: @Composable () -> Unit,
    tooltipState: TooltipState,
    modifier: Modifier = Modifier,
    focusable: Boolean = true,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    onDismissRequest: (() -> Unit)? = null,
    tooltipTokens: TooltipTokens? = null,
    content: @Composable () -> Unit,
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    Box {
        content()
        if (tooltipState.isVisible) {
            Tooltip(
                tooltipContent = tooltipContent,
                tooltipState = tooltipState,
                modifier = modifier,
                focusable = focusable,
                offset = offset,
                onDismissRequest = onDismissRequest,
                tooltipTokens = tooltipTokens
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Tooltip(
    tooltipContent: @Composable () -> Unit,
    tooltipState: TooltipState,
    modifier: Modifier = Modifier,
    focusable: Boolean = true,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    onDismissRequest: (() -> Unit)? = null,
    tooltipTokens: TooltipTokens? = null,
) {
    val token = tooltipTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Tooltip] as TooltipTokens
    val tooltipInfo = TooltipInfo()
    var tipAlignment: Alignment = Alignment.TopCenter
    var tipOffsetX = 0.0f
    val isRTL = LocalLayoutDirection.current == LayoutDirection.Rtl

    val tooltipPositionProvider =
        TooltipPositionProvider(
            offset,
            token.margin(tooltipInfo),
            LocalContext.current.softNavBarOffsetX,
        ) { parentBounds, tooltipContentBounds ->
            tipAlignment =
                if (parentBounds.top + parentBounds.height / 2 <= tooltipContentBounds.top) {
                    Alignment.TopCenter
                } else {
                    Alignment.BottomCenter
                }
            val parentCenter = parentBounds.left + parentBounds.width / 2
            val tooltipCenter = tooltipContentBounds.left + tooltipContentBounds.width / 2
            tipOffsetX = if (isRTL) (tooltipCenter - parentCenter).toFloat() else
                (parentCenter - tooltipCenter).toFloat()

        }

    val coroutineScope = rememberCoroutineScope()

    val transition = updateTransition(tooltipState.isVisible, label = "Tooltip transition")
    if (transition.currentState || transition.targetState) {
        val tooltipPaneDescription = "ToolTip"
        Popup(
            popupPositionProvider = tooltipPositionProvider,
            properties = PopupProperties(
                focusable = focusable,
                dismissOnClickOutside = true
            ),
            onDismissRequest = {
                if (tooltipState.isVisible) {
                    coroutineScope.launch { tooltipState.dismiss() }
                    if (onDismissRequest != null) {
                        onDismissRequest()
                    }
                }
            }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.semantics {
                    testTagsAsResourceId = true
                }) {
                if (tipAlignment == Alignment.TopCenter) {
                    Icon(
                        imageVector = ToolTipIcons.Tip,
                        contentDescription = null,
                        tint = token.tipColor(tooltipInfo),
                        modifier = Modifier
                            .offset(y = 0.dp)
                            .testTag(TOOLTIP_TIP_TEST_TAG)
                    )
                }
                Box(
                    modifier = modifier
                        .sizeIn(
                            minWidth = 40.dp,
                            maxWidth = token.maxWidth(tooltipInfo),
                            minHeight = 24.dp
                        )
                        .animateTooltip(transition)
                        .semantics { paneTitle = tooltipPaneDescription }
                        .background(
                            token.backgroundBrush(tooltipInfo),
                            shape = RoundedCornerShape(token.cornerRadius(tooltipInfo))
                        )
                        .testTag(TOOLTIP_CONTENT_TEST_TAG)
                )
                {
                    tooltipContent()
                }
                if (tipAlignment == Alignment.BottomCenter) {
                    Icon(
                        imageVector = ToolTipIcons.Tip, contentDescription = null,
                        tint = token.tipColor(tooltipInfo),
                        modifier = Modifier
                            .offset(x = offset.x + pxToDp(tipOffsetX), y = 0.dp)
                            .rotate(180f)
                            .testTag(TOOLTIP_TIP_TEST_TAG)
                    )
                }
            }
        }
    }
    DisposableEffect(tooltipState) {
        onDispose {
            tooltipState.onDispose()
        }
    }
}

private class TooltipPositionProvider(
    val tooltipAnchorOffset: DpOffset,
    val margin: Dp = 0.dp,
    val softNavBarOffsetX: Int,
    val onPositionCalculated: (IntRect, IntRect) -> Unit = { _, _ -> }
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val marginPx = dpToPx(margin).toInt()
        var x = anchorBounds.left + ((anchorBounds.width - popupContentSize.width) / 2) + dpToPx(
            tooltipAnchorOffset.x
        ).toInt()
        // If the tooltip goes out of window then we align it to the left or right
        if (x < marginPx + softNavBarOffsetX) {
            x = marginPx + softNavBarOffsetX //Left Align
        } else if (x + popupContentSize.width + marginPx - softNavBarOffsetX > windowSize.width) {
            x =
                windowSize.width - marginPx - popupContentSize.width + softNavBarOffsetX //Right Align
        }
        // Tooltip prefers to be below the anchor,
        // but if this causes the tooltip to out from window bounds,
        // then we place it above the anchor
        var y = anchorBounds.bottom + dpToPx(tooltipAnchorOffset.y).toInt()
        if (y + popupContentSize.height + marginPx > windowSize.height)
            y = anchorBounds.top - popupContentSize.height - dpToPx(tooltipAnchorOffset.y).toInt()
        onPositionCalculated(
            anchorBounds,
            IntRect(x, y, x + popupContentSize.width, y + popupContentSize.height)
        )
        return IntOffset(x, y)
    }
}

private const val TooltipFadeInDuration = 150
private const val TooltipFadeOutDuration = 75
private fun Modifier.animateTooltip(
    transition: Transition<Boolean>
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "animateTooltip"
        properties["transition"] = transition
    }
) {
    val scale by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // show tooltip
                tween(
                    durationMillis = TooltipFadeInDuration,
                    easing = LinearOutSlowInEasing
                )
            } else {
                // dismiss tooltip
                tween(
                    durationMillis = TooltipFadeOutDuration,
                    easing = LinearOutSlowInEasing
                )
            }
        },
        label = "tooltip transition: scaling"
    ) { if (it) 1f else 0.8f }

    val alpha by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // show tooltip
                tween(
                    durationMillis = TooltipFadeInDuration,
                    easing = LinearEasing
                )
            } else {
                // dismiss tooltip
                tween(
                    durationMillis = TooltipFadeOutDuration,
                    easing = LinearEasing
                )
            }
        },
        label = "tooltip transition: alpha"
    ) { if (it) 1f else 0f }

    return@composed this.graphicsLayer(
        scaleX = scale,
        scaleY = scale,
        alpha = alpha
    )
}