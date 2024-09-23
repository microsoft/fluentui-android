package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.foundation.background
import com.microsoft.fluentui.compose.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import com.microsoft.fluentui.compose.anchoredDraggable
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
@Composable
fun HorizontalDrawer(
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

        // TODO : think about Infinite max bounds case
        if (!modalDrawerConstraints.hasBoundedWidth) {
            throw IllegalStateException("Drawer shouldn't have infinite width")
        }

        val fullWidth = modalDrawerConstraints.maxWidth.toFloat()
        var drawerWidth by remember(fullWidth) { mutableStateOf(fullWidth) }
        //Hack to get exact drawerHeight wrt to content.
        val visible = remember { mutableStateOf(true) }
        if (visible.value) {
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            drawerWidth = placeable.width.toFloat()
                            visible.value = false
                        }
                    }
            ) {
                drawerContent()
            }
        } else {
            val paddingPx = pxToDp(max(dpToPx(EndDrawerPadding), (fullWidth - drawerWidth)))
            val leftSlide = behaviorType == BehaviorType.LEFT_SLIDE_OVER

            val minValue =
                modalDrawerConstraints.maxWidth.toFloat() * (if (leftSlide) (-1F) else (1F))
            val maxValue = 0f

            val anchors = DraggableAnchors {
                DrawerValue.Closed at minValue
                DrawerValue.Open at maxValue
            }
            drawerState.anchoredDraggableState.updateAnchors(anchors)
            val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
            val offset = drawerState.anchoredDraggableState.offset
            drawerState.positionalThreshold =  { fl: Float -> drawerWidth / 2 }
            val drawerVelocityThreshold = convertDpToFloat(DrawerVelocityThreshold)
            drawerState.velocityThreshold = { drawerVelocityThreshold }
            Scrim(
                open = !drawerState.isClosed,
                onClose = onDismiss,
                fraction = {
                    calculateFraction(minValue, maxValue, offset)
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
                    .offset { IntOffset(offset.roundToInt(), 0) }
                    .padding(
                        start = if (leftSlide) 0.dp else paddingPx,
                        end = if (leftSlide) paddingPx else 0.dp
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
                    .anchoredDraggable(
                        state = drawerState.anchoredDraggableState,
                        orientation = Orientation.Horizontal,
                        enabled = false,
                        reverseDirection = isRtl
                    ),
            ) {
                Column(
                    Modifier
                        .draggable(
                            orientation = Orientation.Horizontal,
                            state = rememberDraggableState { delta ->
                                drawerState.anchoredDraggableState.dispatchRawDelta(delta)
                            },
                            onDragStopped = { velocity ->
                                launch {
                                    drawerState.anchoredDraggableState.settle(
                                        velocity
                                    )
                                    if (drawerState.isClosed) {
                                        onDismiss()
                                    }
                                }
                            },
                        )
                        .testTag(DRAWER_CONTENT_TAG), content = { drawerContent() })
            }
        }
    }
}