import com.microsoft.fluentui.tokenized.drawer.DRAWER_CONTENT_TAG
import com.microsoft.fluentui.tokenized.drawer.DrawerState
import com.microsoft.fluentui.tokenized.drawer.DrawerValue
import com.microsoft.fluentui.tokenized.drawer.DrawerVelocityThreshold
import com.microsoft.fluentui.tokenized.drawer.EndDrawerPadding
import com.microsoft.fluentui.tokenized.drawer.Scrim
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.FixedThreshold
import com.microsoft.fluentui.compose.swipeable
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.tokenized.calculateFraction
import com.microsoft.fluentui.util.dpToPx
import com.microsoft.fluentui.util.pxToDp
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.roundToInt

/**
 *
 *
 * Side drawers block interaction with the rest of an app’s content with a scrim.
 * They are elevated above most of the app’s UI and don’t affect the screen’s layout grid.
 *
 * @param drawerContent composable that represents content inside the drawer
 * @param modifier optional modifier for the drawer
 * @param drawerState state of the drawer
 * @param drawerShape shape of the drawer sheet
 * @param drawerElevation drawer sheet elevation. This controls the size of the shadow below the
 * drawer sheet
 * @param drawerBackground background color to be used for the drawer sheet
 * @param scrimColor color of the scrim that obscures content when the drawer is open
 * @param preventDismissalOnScrimClick when true, the drawer will not be dismissed when the scrim is clicked
 * @param onScrimClick callback to be invoked when the scrim is clicked
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 */
// package com.microsoft.fluentui.tokenized.drawer
// ... imports ...

@Composable
internal fun HorizontalDrawer(
    modifier: Modifier,
    behaviorType: BehaviorType,
    drawerState: DrawerState,
    drawerShape: Shape,
    drawerElevation: Dp,
    drawerBackground: Brush,
    scrimColor: Color,
    scrimVisible: Boolean,
    onDismiss: () -> Unit,
    drawerContent: @Composable () -> Unit,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {}
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        val modalDrawerConstraints = constraints
        val scope = rememberCoroutineScope()
        if (!modalDrawerConstraints.hasBoundedWidth) {
            throw IllegalStateException("Drawer shouldn't have infinite width")
        }

        val fullWidth = modalDrawerConstraints.maxWidth.toFloat()
        var drawerWidth by remember(fullWidth) { mutableStateOf(fullWidth) } // Initially fullWidth
        val visible = remember { mutableStateOf(true) }

        if (visible.value) {
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            drawerWidth = placeable.width.toFloat() // Correctly measured drawer content width
                            visible.value = false
                        }
                    }
            ) {
                drawerContent()
            }
        } else {
            val leftSlide = behaviorType == BehaviorType.LEFT_SLIDE_OVER
            val density = LocalDensity.current
            val endDrawerPaddingPx = with(density) { EndDrawerPadding.toPx() } // Assuming EndDrawerPadding is a Dp value like 16.dp or 0.dp
            // This padding is used to ensure the content box has the correct size when drawer is open
            val paddingPxValue = max(endDrawerPaddingPx, (fullWidth - drawerWidth))


            // --- MODIFIED ANCHOR LOGIC ---
            val (closedAnchorOffset, openAnchorOffset) = if (leftSlide) {
                // For a left drawer, its left edge is at offset.
                // Closed: left edge at -drawerWidth (just off-screen to the left).
                // Open: left edge at 0f (aligned with the screen's left edge).
                -drawerWidth to 0f
            } else {
                // For a right drawer, its left edge is at offset.
                // Closed: left edge at fullWidth (drawer is [fullWidth, fullWidth + drawerWidth], so off-screen to the right).
                // Open: left edge at fullWidth - drawerWidth (drawer is [fullWidth - drawerWidth, fullWidth]).
                fullWidth to (fullWidth - drawerWidth)
            }

            val anchors = mapOf(
                closedAnchorOffset to DrawerValue.Closed,
                openAnchorOffset to DrawerValue.Open
            )
            // --- END OF MODIFIED ANCHOR LOGIC ---

            val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
            Scrim(
                open = !drawerState.isClosed,
                onClose = onDismiss,
                fraction = {
                    // calculateFraction needs to be aware of the new anchor range
                    // Old: calculateFraction(minValue, maxValue, drawerState.offset.value)
                    // where minValue was -fullWidth or fullWidth, maxValue was 0 or fullWidth - drawerWidth
                    // New:
                    val minAnchor = anchors.keys.minOrNull() ?: 0f
                    val maxAnchor = anchors.keys.maxOrNull() ?: 0f
                    calculateFraction(minAnchor, maxAnchor, drawerState.offset.value)
                },
                color = if (scrimVisible) scrimColor else Color.Transparent,
                preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                onScrimClick = onScrimClick
            )

            Box(
                modifier = with(LocalDensity.current) {
                    Modifier
                        .sizeIn(
                            minWidth = modalDrawerConstraints.minWidth.toDp(),
                            minHeight = modalDrawerConstraints.minHeight.toDp(),
                            maxWidth = modalDrawerConstraints.maxWidth.toDp(),
                            maxHeight = modalDrawerConstraints.maxHeight.toDp()
                        )
                }
                    .offset { IntOffset(drawerState.offset.value.roundToInt(), 0) }
                    .padding(
                        start = if (leftSlide) 0.dp else pxToDp(paddingPxValue),
                        end = if (leftSlide) pxToDp(paddingPxValue) else 0.dp
                    )
                    .semantics {
                        if (!drawerState.isClosed) {
                            dismiss {
                                onDismiss()
                                true
                            }
                        }
                    }
                    .shadow(drawerElevation)
                    .clip(drawerShape)
                    .background(drawerBackground)
                    .swipeable(
                        state = drawerState,
                        anchors = anchors, // Use the new anchors
                        thresholds = { _, _ -> FixedThreshold(pxToDp(value = drawerWidth / 2)) },
                        orientation = Orientation.Horizontal,
                        enabled = false, // This is for the drawer's own surface swipe, not edge
                        reverseDirection = isRtl,
                        velocityThreshold = DrawerVelocityThreshold,
                        resistance = null // Or SwipeableDefaults.resistanceConfig(anchors.keys) for overdrag
                    ),
            ) {
                Column(
                    Modifier
                        .draggable( // This draggable is for the content itself (e.g. a handle)
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                drawerState.performDrag(delta)
                            },
                            onDragStopped = { velocity ->
                                scope.launch { // Use the existing scope from rememberCoroutineScope if needed, or remember a new one
                                    drawerState.performFling(
                                        velocity
                                    )
                                    if (drawerState.isClosed) {
                                        onDismiss()
                                    }
                                }
                            },
                        )
                        .testTag(DRAWER_CONTENT_TAG),
                    content = { drawerContent() }
                )
            }
        }
    }
}