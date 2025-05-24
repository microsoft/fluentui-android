package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.paneTitle
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.AnchoredDraggableState
import com.microsoft.fluentui.compose.DraggableAnchors
import com.microsoft.fluentui.compose.anchoredDraggable
import com.microsoft.fluentui.compose.animateTo
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.DrawerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DrawerTokens
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val DrawerPositionalThresholdFactor = 0.5f
private val DrawerVelocityThresholdModal = 125.dp
private val DrawerAnimationSpec: AnimationSpec<Float> = SpringSpec(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMedium
)
private val EdgeDragWidth = 20.dp
private val MinDrawerWidth = 256.dp
private val MaxDrawerWidth = 360.dp

const val DRAWER_SHEET_TAG = "FluentDrawerSheet"

enum class FluentModalDrawerValue {
    Closed, Open
}

@Stable
class FluentModalDrawerState(
    initialValue: FluentModalDrawerValue,
    confirmValueChange: (FluentModalDrawerValue) -> Boolean = { true },
    positionalThresholdFactor: Float,
    velocityThresholdPx: () -> Float
) {
    internal val anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        animationSpec = DrawerAnimationSpec,
        confirmValueChange = confirmValueChange,
        positionalThreshold = { totalDistance -> totalDistance * positionalThresholdFactor },
        velocityThreshold = velocityThresholdPx
    )

    val currentValue: FluentModalDrawerValue
        get() = anchoredDraggableState.currentValue

    val targetValue: FluentModalDrawerValue
        get() = anchoredDraggableState.targetValue

    val isOpen: Boolean
        get() = currentValue == FluentModalDrawerValue.Open

    /**
     * The current offset of the drawer sheet from its initial position, in pixels.
     * This value is typically 0f when the drawer is 'Open' and -drawerWidth when 'Closed'.
     * It will be Float.NaN before the first anchor update.
     */
    val offset: State<Float> = derivedStateOf { anchoredDraggableState.offset }


    suspend fun open() {
        animateTo(FluentModalDrawerValue.Open)
    }

    suspend fun close() {
        animateTo(FluentModalDrawerValue.Closed)
    }

    internal suspend fun settle(velocity: Float) {
        anchoredDraggableState.settle(velocity)
    }

    private suspend fun animateTo(target: FluentModalDrawerValue, velocity: Float = anchoredDraggableState.lastVelocity) {
        anchoredDraggableState.animateTo(target, velocity)
    }

    companion object {
        fun Saver(
            confirmValueChange: (FluentModalDrawerValue) -> Boolean,
            positionalThresholdFactor: Float,
            velocityThresholdPx: () -> Float
        ): Saver<FluentModalDrawerState, FluentModalDrawerValue> = Saver(
            save = { it.currentValue },
            restore = { savedValue ->
                FluentModalDrawerState(
                    initialValue = savedValue,
                    confirmValueChange = confirmValueChange,
                    positionalThresholdFactor = positionalThresholdFactor,
                    velocityThresholdPx = velocityThresholdPx
                )
            }
        )
    }
}

@Composable
fun rememberFluentModalDrawerState(
    initialValue: FluentModalDrawerValue = FluentModalDrawerValue.Closed,
    confirmValueChange: (FluentModalDrawerValue) -> Boolean = { true }
): FluentModalDrawerState {
    val density = LocalDensity.current
    val velocityThresholdProvider = remember(density, DrawerVelocityThresholdModal) {
        { with(density) { DrawerVelocityThresholdModal.toPx() } }
    }
    return rememberSaveable(
        confirmValueChange,
        saver = FluentModalDrawerState.Saver(
            confirmValueChange = confirmValueChange,
            positionalThresholdFactor = DrawerPositionalThresholdFactor,
            velocityThresholdPx = velocityThresholdProvider
        )
    ) {
        FluentModalDrawerState(
            initialValue = initialValue,
            confirmValueChange = confirmValueChange,
            positionalThresholdFactor = DrawerPositionalThresholdFactor,
            velocityThresholdPx = velocityThresholdProvider
        )
    }
}

@Composable
fun FluentModalNavigationDrawer(
    drawerContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    drawerState: FluentModalDrawerState = rememberFluentModalDrawerState(),
    gesturesEnabled: Boolean = true,
    drawerTokens: DrawerTokens = DrawerTokens(),
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl
    val density = LocalDensity.current

    val tokens = drawerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.DrawerControlType] as DrawerTokens
    val drawerInfo = remember { DrawerInfo(type = BehaviorType.LEFT_SLIDE_OVER) } // Assuming similar styling

    val resolvedShape = RoundedCornerShape(tokens.borderRadius(drawerInfo))
    val resolvedElevation = tokens.elevation(drawerInfo)
    val resolvedBackground = tokens.backgroundBrush(drawerInfo)
    val resolvedScrimColor = tokens.scrimColor(drawerInfo).copy(alpha = tokens.scrimOpacity(drawerInfo))

    var drawerWidthPx by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(drawerState, drawerWidthPx) {
        if (drawerWidthPx > 0f) { // Reminder!! : Avoid updating anchors if width is not set
            val newAnchors = DraggableAnchors {
                FluentModalDrawerValue.Closed at -drawerWidthPx
                FluentModalDrawerValue.Open at 0f
            }
            drawerState.anchoredDraggableState.updateAnchors(newAnchors, drawerState.anchoredDraggableState.targetValue)
        }
    }


    val anchoredDraggableModifier = if (gesturesEnabled) {
        Modifier.anchoredDraggable(
            state = drawerState.anchoredDraggableState,
            orientation = Orientation.Horizontal,
            reverseDirection = isRtl
        )
    } else {
        Modifier
    }

    val edgeSwipeModifier = if (gesturesEnabled && drawerState.anchoredDraggableState.confirmValueChange(FluentModalDrawerValue.Open)) {
        Modifier.pointerInput(drawerState.anchoredDraggableState, isRtl, drawerWidthPx) { // Add drawerWidthPx as key
            if (drawerWidthPx == 0f) return@pointerInput // Do not detect if width is not measured
            val edgeWidthPx = with(density) { EdgeDragWidth.toPx() }
            val velocityTracker = VelocityTracker()

            awaitEachGesture {
                val down = awaitFirstDown(requireUnconsumed = false)
                velocityTracker.resetTracking()
                var isDragFromEdge = false

                if (drawerState.currentValue == FluentModalDrawerValue.Closed) {
                    val touchX = if (isRtl) size.width - down.position.x else down.position.x
                    if (touchX < edgeWidthPx) {
                        isDragFromEdge = true
                    }
                }

                if (isDragFromEdge) {
                    horizontalDrag(down.id) { change ->
                        // Check currentValue inside drag to ensure we only dispatch if it's still closed
                        if (drawerState.anchoredDraggableState.currentValue == FluentModalDrawerValue.Closed) {
                            val delta = change.positionChange().x
                            val adjustedDelta = if (isRtl) -delta else delta
                            scope.launch {
                                drawerState.anchoredDraggableState.dispatchRawDelta(adjustedDelta)
                            }
                            velocityTracker.addPosition(change.uptimeMillis, change.position)
                            change.consume()
                        } else {
                            // If drawer opened by other means during this gesture, stop special handling
                            change.consume() // Consume to prevent interference
                        }
                    }
                    val velocity = velocityTracker.calculateVelocity().x
                    val adjustedVelocity = if (isRtl) -velocity else velocity
                    scope.launch {
                        // Only settle if we were actually dragging from edge
                        // and the drawer is still in a draggable state (not fully open/closed by other means)
                        if (drawerState.anchoredDraggableState.currentValue == FluentModalDrawerValue.Closed && drawerState.anchoredDraggableState.offset != 0f) {
                            drawerState.settle(adjustedVelocity)
                        } else if (drawerState.anchoredDraggableState.offset != -drawerWidthPx && drawerState.anchoredDraggableState.offset != 0f) {
                            // If it's between states (e.g. partially opened by edge swipe)
                            drawerState.settle(adjustedVelocity)
                        }
                    }
                }
            }
        }
    } else {
        Modifier
    }

    BoxWithConstraints(modifier.fillMaxSize()) {
        val constraints = this.constraints
        Box(
            Modifier
                .fillMaxSize()
                .then(edgeSwipeModifier)
        ) {
            content()
        }

        val scrimAlpha by remember(drawerState.offset, drawerWidthPx) {
            derivedStateOf {
                if (drawerWidthPx == 0f || drawerState.offset.value.isNaN()) 0f
                else (drawerState.offset.value / drawerWidthPx + 1f).coerceIn(0f, 1f)
            }
        }

        if (scrimAlpha > 0f) {
            Scrim(
                open = true,
                onClose = {
                    if (drawerState.anchoredDraggableState.confirmValueChange(FluentModalDrawerValue.Closed)) {
                        scope.launch { drawerState.close() }
                    }
                },
                fraction = { scrimAlpha },
                color = resolvedScrimColor,
                preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                onScrimClick = onScrimClick,
                modifier = Modifier.testTag(DRAWER_SCRIM_TAG)
            )
        }

        val drawerOffsetModifier = Modifier.offset {
            val currentOffsetStateValue = drawerState.offset.value
            val currentDrawerWidthPx = drawerWidthPx // Capture for consistent use in this block

            // Determine a fallback offset if the state's offset is NaN (initial state before anchors are fully set)
            val logicalFallbackOffset = if (drawerState.currentValue == FluentModalDrawerValue.Open) {
                0f
            } else {
                // If closed, its logical offset is -drawerWidth. If drawerWidth is 0, this is 0.
                // This means visually it's at the edge but with 0 width, which is fine for initial frame.
                -currentDrawerWidthPx
            }

            val resolvedOffset = if (currentOffsetStateValue.isNaN()) {
                logicalFallbackOffset
            } else {
                currentOffsetStateValue
            }

            val xOffsetRounded = resolvedOffset.roundToInt()
            val drawerWidthRounded = currentDrawerWidthPx.roundToInt()

            IntOffset(
                x = if (isRtl) {
                    // In RTL, the drawer slides from the right edge.
                    // 'resolvedOffset' (0 for open, -width for closed) refers to its conceptual LTR position.
                    // To place its left edge: (parent_width - drawer_width) is the open position of the left edge.
                    // Add resolvedOffset to this to slide it.
                    // When open (offset=0): (maxWidth - drawerWidth) + 0 = left edge is at open pos.
                    // When closed (offset=-drawerWidth): (maxWidth - drawerWidth) + (-drawerWidth) = left edge is further left.
                    // This is not right.
                    // Correct RTL: drawer's *right* edge is at `maxWidth + resolvedOffset`.
                    // So its *left* edge is at `(maxWidth + resolvedOffset) - drawerWidth`.
                    // `x = maxWidth + xOffsetRounded - drawerWidthRounded`
                    //
                    // Let's test the logic: (maxWidth - drawerWidth) + (resolvedOffset * -1)
                    // When open (offset=0): x = (maxWidth - drawerWidth). Correct.
                    // When closed (offset=-drawerWidth): x = (maxWidth - drawerWidth) + drawerWidth = maxWidth. Correct.
                    (constraints.maxWidth - drawerWidthRounded) + (resolvedOffset * -1f).roundToInt()
                } else {
                    // In LTR, drawer slides from left. resolvedOffset is its direct X position.
                    xOffsetRounded
                },
                y = 0
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(min = MinDrawerWidth, max = MaxDrawerWidth)
                .onSizeChanged { size ->
                    drawerWidthPx = size.width.toFloat()
                }
                .then(drawerOffsetModifier) // Apply the calculated offset
                .shadow(resolvedElevation, resolvedShape, clip = false)
                .clip(resolvedShape)
                .background(resolvedBackground)
                .semantics {
                    paneTitle = "FluentModalNavigationDrawer" // TODO: Consider making this localizable
                    if (drawerState.isOpen) {
                        dismiss {
                            if (drawerState.anchoredDraggableState.confirmValueChange(FluentModalDrawerValue.Closed)) {
                                scope.launch { drawerState.close() }
                            }
                            true
                        }
                    }
                }
                .then(anchoredDraggableModifier)
                .testTag(DRAWER_SHEET_TAG)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                drawerContent()
            }
        }
    }
}

@Composable
private fun Scrim(
    open: Boolean,
    onClose: () -> Unit,
    fraction: () -> Float,
    color: Color,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (color.alpha == 0f && fraction() == 0f) return

    val dismissModifier = if (open) {
        Modifier.pointerInput(onClose, preventDismissalOnScrimClick, onScrimClick) {
            detectTapGestures {
                if (!preventDismissalOnScrimClick) {
                    onClose()
                }
                onScrimClick()
            }
        }
    } else {
        Modifier
    }

    Canvas(
        modifier
            .fillMaxSize()
            .then(dismissModifier)
    ) {
        drawRect(color = color, alpha = fraction())
    }
}