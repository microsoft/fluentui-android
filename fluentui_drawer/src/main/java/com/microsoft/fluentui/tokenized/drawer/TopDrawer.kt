package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import com.microsoft.fluentui.compose.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import com.microsoft.fluentui.compose.anchoredDraggable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.tokenized.calculateFraction
import com.microsoft.fluentui.util.dpToPx
import com.microsoft.fluentui.util.pxToDp
import kotlinx.coroutines.launch
import kotlin.math.min

@Composable
fun TopDrawer(
    modifier: Modifier,
    drawerState: DrawerState,
    drawerShape: Shape,
    drawerElevation: Dp,
    drawerBackground: Brush,
    drawerHandleColor: Color,
    scrimColor: Color,
    scrimVisible: Boolean,
    onDismiss: () -> Unit,
    drawerContent: @Composable () -> Unit,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {}
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        val fullHeight = constraints.maxHeight.toFloat()
        var drawerHeight by remember(fullHeight) { mutableStateOf(fullHeight) }

        Box(
            modifier = Modifier
                .alpha(0f)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        drawerHeight =
                            placeable.height.toFloat() + dpToPx(DrawerHandleHeightOffset)
                    }
                }
        ) {
            drawerContent()
        }
        val maxOpenHeight = fullHeight * DrawerOpenFraction
        val minHeight = 0f
        val topCloseHeight = minHeight
        val topOpenHeight = min(maxOpenHeight, drawerHeight)

        val minValue: Float = topCloseHeight
        val maxValue: Float = topOpenHeight

        val anchors = DraggableAnchors {
            DrawerValue.Closed at topCloseHeight
            DrawerValue.Open at topOpenHeight
        }
        drawerState.anchoredDraggableState.updateAnchors(anchors)

        val drawerConstraints = with(LocalDensity.current) {
            Modifier
                .sizeIn(
                    maxWidth = constraints.maxWidth.toDp(),
                    maxHeight = constraints.maxHeight.toDp()
                )
        }
        val drawerStateOffset = drawerState.anchoredDraggableState.offset

        Scrim(
            open = !drawerState.isClosed,
            onClose = onDismiss,
            fraction = {
                calculateFraction(minValue, maxValue, drawerStateOffset)
            },
            color = if (scrimVisible) scrimColor else Color.Transparent,
            preventDismissalOnScrimClick = preventDismissalOnScrimClick,
            onScrimClick = onScrimClick
        )

        Box(
            drawerConstraints
                .offset { IntOffset(0, 0) }
                .semantics {
                    if (!drawerState.isClosed) {
                        dismiss {
                            onDismiss()
                            true
                        }
                    }
                }
                .height(
                    pxToDp(drawerStateOffset)
                )
                .shadow(drawerElevation)
                .clip(drawerShape)
                .background(drawerBackground)
                .anchoredDraggable(
                    state = drawerState.anchoredDraggableState,
                    orientation = Orientation.Vertical,
                    enabled = false
                )
                .focusable(false),
        ) {
            ConstraintLayout(modifier = Modifier.padding(bottom = 8.dp)) {
                val (drawerContentConstrain, drawerHandleConstrain) = createRefs()
                Column(modifier = Modifier
                    .offset { IntOffset(0, 0) }
                    .padding(bottom = 8.dp)
                    .constrainAs(drawerContentConstrain) {
                        top.linkTo(parent.top)
                        bottom.linkTo(drawerHandleConstrain.top)
                    }
                    .focusTarget()
                    .testTag(DRAWER_CONTENT_TAG), content = { drawerContent() }
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .constrainAs(drawerHandleConstrain) {
                            top.linkTo(drawerContentConstrain.bottom)
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxWidth()
                        .draggable(
                            orientation = Orientation.Vertical,
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
                        .testTag(DRAWER_HANDLE_TAG)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_drawer_handle),
                        contentDescription = null,
                        tint = drawerHandleColor
                    )
                }
            }
        }
    }
}