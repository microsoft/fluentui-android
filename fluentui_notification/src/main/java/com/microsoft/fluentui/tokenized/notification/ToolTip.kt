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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.TooltipInfo
import com.microsoft.fluentui.theme.token.controlTokens.TooltipTokens
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout


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
//         Show associated tooltip for [TooltipDuration] amount of time.
        mutatorMutex.mutate {
            try {
                suspendCancellableCoroutine { continuation: CancellableContinuation<Unit> ->
                    isVisible = true
                    job = continuation
                }
            } finally {
//                 timeout or cancellation has occurred
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


@Composable
fun ToolTipBox(
    tooltipContent: @Composable () -> Unit,
    tooltipState: TooltipState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    tooltipTokens: TooltipTokens? = null,
    content: @Composable () -> Unit,
) {
    Box {
        content()
        if (tooltipState.isVisible) {
            Tooltip(
                tooltipContent = tooltipContent,
                tooltipState =tooltipState,
                onDismissRequest = onDismissRequest,
                modifier = modifier,
                enabled = enabled,
                offset = offset,
                tooltipTokens = tooltipTokens
            )
        }
    }
}

@Composable
fun Tooltip(
    tooltipContent: @Composable () -> Unit,
    tooltipState: TooltipState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    tooltipTokens: TooltipTokens? = null,
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = tooltipTokens ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Tooltip] as TooltipTokens
    val tooltipInfo = TooltipInfo()

    val coroutineScope = rememberCoroutineScope()
    val tooltipPositionProvider = remember { TooltipPositionProvider(8) }
    Box {
        val transition = updateTransition(tooltipState.isVisible, label = "Tooltip transition")
        if (transition.currentState || transition.targetState) {
            val tooltipPaneDescription = "ToolTip"
            Popup(
                popupPositionProvider = tooltipPositionProvider,
                onDismissRequest = {
                    if (tooltipState.isVisible) {
                        coroutineScope.launch { tooltipState.dismiss() }
                    }
                }
            ) {
                Column {
                    Box(
                        modifier = modifier
                            .sizeIn(
                                minWidth = 40.dp,
                                maxWidth = 200.dp,
                                minHeight = 24.dp
                            )
                            .animateTooltip(transition)
                            .semantics { paneTitle = tooltipPaneDescription }
                            .background(
                                token.backgroundBrush(tooltipInfo),
                                shape = RoundedCornerShape(token.cornerRadius(tooltipInfo))
                            )
                            .padding(8.dp)
                    )
                    {
                        tooltipContent()
                    }
                }
            }
        }
    }

    DisposableEffect(tooltipState) {
        onDispose { tooltipState.onDispose() }
    }
}

private class TooltipPositionProvider(
    val tooltipAnchorPadding: Int
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val x = anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2

        // Tooltip prefers to be above the anchor,
        // but if this causes the tooltip to overlap with the anchor
        // then we place it below the anchor
        var y = anchorBounds.top - popupContentSize.height - tooltipAnchorPadding
        if (y < 0)
            y = anchorBounds.bottom + tooltipAnchorPadding
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

