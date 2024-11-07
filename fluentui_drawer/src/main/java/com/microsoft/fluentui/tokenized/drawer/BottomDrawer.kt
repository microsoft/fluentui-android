package com.microsoft.fluentui.tokenized.drawer

import android.content.Context
import android.content.res.Configuration
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.collapse
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.NonDismissiblePreUpPostDownNestedScrollConnection
import com.microsoft.fluentui.compose.PostDownNestedScrollConnection
import com.microsoft.fluentui.compose.swipeable
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.tokenized.calculateFraction
import com.microsoft.fluentui.util.pxToDp
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt


@Composable
internal fun BottomDrawer(
    modifier: Modifier,
    drawerState: DrawerState,
    drawerShape: Shape,
    drawerElevation: Dp,
    drawerBackground: Brush,
    drawerHandleColor: Color,
    scrimColor: Color,
    scrimVisible: Boolean,
    slideOver: Boolean,
    enableSwipeDismiss: Boolean = true,
    showHandle: Boolean,
    onDismiss: () -> Unit,
    drawerContent: @Composable () -> Unit,
    maxLandscapeWidthFraction : Float = 1F,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {}
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        val fullHeight = constraints.maxHeight.toFloat()
        val drawerHeight =
            remember(drawerContent.hashCode()) { mutableStateOf<Float?>(null) }
        val maxOpenHeight = fullHeight * DrawerOpenFraction

        val drawerConstraints = with(LocalDensity.current) {
            Modifier
                .sizeIn(
                    maxWidth = constraints.maxWidth.toDp(),
                    maxHeight = constraints.maxHeight.toDp()
                )
        }
        val scope = rememberCoroutineScope()

        Scrim(
            open = !drawerState.isClosed || (drawerHeight != null && drawerHeight.value == 0f),
            onClose = onDismiss,
            fraction = {
                if (drawerState.anchors.isEmpty() || (drawerHeight != null && drawerHeight.value == 0f)) {
                    0.toFloat()
                } else {
                    var targetValue: DrawerValue = if (slideOver) {
                        drawerState.anchors.maxBy { it.value }.value
                    } else if (drawerState.skipOpenState) {
                        DrawerValue.Expanded
                    } else {
                        DrawerValue.Open
                    }
                    calculateFraction(
                        drawerState.anchors.entries.firstOrNull { it.value == DrawerValue.Closed }?.key!!,
                        drawerState.anchors.entries.firstOrNull { it.value == targetValue }?.key!!,
                        drawerState.offset.value
                    )
                }
            },
            color = if (scrimVisible) scrimColor else Color.Transparent,
            preventDismissalOnScrimClick = preventDismissalOnScrimClick,
            onScrimClick = onScrimClick
        )
        val configuration = LocalConfiguration.current
        Box(
            drawerConstraints
                .fillMaxWidth(
                    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) maxLandscapeWidthFraction
                    else 1F
                )
                .nestedScroll(
                    if (!enableSwipeDismiss && drawerState.offset.value >= maxOpenHeight) drawerState.NonDismissiblePreUpPostDownNestedScrollConnection else
                        if (slideOver) drawerState.nestedScrollConnection else drawerState.PostDownNestedScrollConnection
                )
                .offset {
                    val y = if (drawerState.anchors == null) {
                        fullHeight.roundToInt()
                    } else {
                        drawerState.offset.value.roundToInt()
                    }
                    IntOffset(x = 0, y = y)
                }
                .then(
                    if (maxLandscapeWidthFraction != 1F
                        && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                    ) Modifier.align(Alignment.TopCenter)
                    else Modifier
                )
                .onGloballyPositioned { layoutCoordinates ->
                    if (!drawerState.animationInProgress
                        && drawerState.currentValue == DrawerValue.Closed
                        && drawerState.targetValue == DrawerValue.Closed
                    ) {
                        onDismiss()
                    }

                    if (slideOver) {
                        val originalSize = layoutCoordinates.size.height.toFloat()
                        drawerHeight.value = if (drawerState.expandable) {
                            originalSize
                        } else {
                            min(
                                originalSize,
                                maxOpenHeight
                            )
                        }
                    }
                }
                .bottomDrawerSwipeable(
                    drawerState,
                    slideOver,
                    maxOpenHeight,
                    fullHeight,
                    drawerHeight.value
                )
                .drawerHeight(
                    slideOver,
                    maxOpenHeight,
                    fullHeight,
                    drawerState
                )
                .shadow(drawerElevation)
                .clip(drawerShape)
                .background(drawerBackground)
                .semantics {
                    if (!drawerState.isClosed) {
                        dismiss {
                            onDismiss()
                            true
                        }
                        if (drawerState.currentValue == DrawerValue.Open && drawerState.hasExpandedState) {
                            expand {
                                if (drawerState.confirmStateChange(DrawerValue.Expanded)) {
                                    scope.launch { drawerState.expand() }
                                }
                                true
                            }
                        } else if (drawerState.hasExpandedState && drawerState.hasOpenedState) {
                            collapse {
                                if (drawerState.confirmStateChange(DrawerValue.Open)) {
                                    scope.launch { drawerState.open() }
                                }
                                true
                            }
                        }
                    }
                }
                .focusable(false),
        ) {
            Column {
                if (showHandle) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .draggable(
                                orientation = Orientation.Vertical,
                                state = rememberDraggableState { delta ->
                                    if (!enableSwipeDismiss && drawerState.offset.value >= maxOpenHeight) {
                                        if (delta < 0) {
                                            drawerState.performDrag(delta)
                                        }
                                    } else {
                                        drawerState.performDrag(delta)
                                    }
                                },
                                onDragStopped = { velocity ->
                                    launch {
                                        drawerState.performFling(
                                            velocity
                                        )
                                        if (drawerState.isClosed) {
                                            if (enableSwipeDismiss)
                                                onDismiss()
                                            else
                                                scope.launch { drawerState.open() }
                                        }
                                    }
                                },
                            )
                            .testTag(DRAWER_HANDLE_TAG)
                    ) {
                        val collapsed = LocalContext.current.resources.getString(R.string.collapsed)
                        val expanded = LocalContext.current.resources.getString(R.string.expanded)
                        val accessibilityManager  = LocalContext.current.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
                        Icon(
                            painterResource(id = R.drawable.ic_drawer_handle),
                            contentDescription = LocalContext.current.resources.getString(R.string.drag_handle),
                            tint = drawerHandleColor,
                            modifier = Modifier
                                .clickable(
                                    enabled = drawerState.hasExpandedState,
                                    role = Role.Button,
                                    onClickLabel =
                                    if (drawerState.currentValue == DrawerValue.Expanded) {
                                        LocalContext.current.resources.getString(R.string.collapse)
                                    } else {
                                        if (drawerState.hasExpandedState && !drawerState.isClosed) LocalContext.current.resources.getString(
                                            R.string.expand
                                        ) else null
                                    }
                                ) {
                                    if (drawerState.currentValue == DrawerValue.Expanded) {
                                        if (drawerState.hasOpenedState && drawerState.confirmStateChange(
                                                DrawerValue.Open
                                            )
                                        ) {
                                            scope.launch { drawerState.open() }
                                            accessibilityManager?.let { manager ->
                                                if(manager.isEnabled){
                                                    val event = AccessibilityEvent.obtain(
                                                        AccessibilityEvent.TYPE_ANNOUNCEMENT).apply {
                                                        text.add(collapsed)
                                                    }
                                                    manager.sendAccessibilityEvent(event)
                                                }
                                            }
                                        }
                                    } else if (drawerState.hasExpandedState) {
                                        if (drawerState.confirmStateChange(DrawerValue.Expanded)) {
                                            scope.launch { drawerState.expand() }
                                            accessibilityManager?.let { manager ->
                                                if(manager.isEnabled){
                                                    val event = AccessibilityEvent.obtain(
                                                        AccessibilityEvent.TYPE_ANNOUNCEMENT).apply {
                                                        text.add(expanded)
                                                    }
                                                    manager.sendAccessibilityEvent(event)
                                                }
                                            }
                                        }
                                    }
                                }
                        )
                    }
                }
                Column(modifier = Modifier
                    .testTag(DRAWER_CONTENT_TAG), content = { drawerContent() })
            }
        }
    }
}


private fun Modifier.bottomDrawerSwipeable(
    drawerState: DrawerState,
    slideOver: Boolean,
    maxOpenHeight: Float,
    fullHeight: Float,
    drawerHeight: Float?
): Modifier {
    val modifier = if (slideOver) {
        if (drawerHeight != null) {
            val minHeight = 0f
            val bottomOpenStateY = max(maxOpenHeight, fullHeight - drawerHeight)
            val bottomExpandedStateY = max(minHeight, fullHeight - drawerHeight)
            val anchors =
                if (drawerHeight <= maxOpenHeight) {  // when contentHeight is less than maxOpenHeight
                    if (drawerState.anchors.containsValue(DrawerValue.Expanded)) {
                        /*
                        *For dynamic content when drawerHeight was previously greater than maxOpenHeight and now less than maxOpenHEight
                        *The old anchors won't have Open state, so we need to continue with Expanded state.
                        */
                        mapOf(
                            bottomOpenStateY to DrawerValue.Expanded,
                            fullHeight to DrawerValue.Closed,
                        )
                    } else {
                        mapOf(
                            bottomOpenStateY to DrawerValue.Open,
                            fullHeight to DrawerValue.Closed
                        )
                    }
                } else {
                    if (drawerState.expandable) {
                        if (drawerState.skipOpenState) {
                            if (drawerState.anchors.containsValue(DrawerValue.Open)) {
                                /*
                                *For dynamic content when drawerHeight was previously less than maxOpenHeight and now greater than maxOpenHEight
                                *The old anchors won't have Expanded state, so we need to continue with Open state.
                                */
                                mapOf(
                                    bottomExpandedStateY to DrawerValue.Open, // when drawerHeight is greater than maxOpenHeight but less than fullHeight, then Expanded state starts from fullHeight-drawerHeight
                                    fullHeight to DrawerValue.Closed
                                )
                            } else {
                                mapOf(
                                    bottomExpandedStateY to DrawerValue.Expanded, // when drawerHeight is greater than maxOpenHeight but less than fullHeight, then Expanded state starts from fullHeight-drawerHeight
                                    fullHeight to DrawerValue.Closed,
                                )
                            }
                        } else {
                            mapOf(
                                maxOpenHeight to DrawerValue.Open,
                                bottomExpandedStateY to DrawerValue.Expanded,
                                fullHeight to DrawerValue.Closed
                            )
                        }
                    } else {
                        mapOf(
                            maxOpenHeight to DrawerValue.Open,
                            fullHeight to DrawerValue.Closed
                        )
                    }
                }
            Modifier.swipeable(
                state = drawerState,
                anchors = anchors,
                orientation = Orientation.Vertical,
                enabled = false,
                resistance = null
            )
        } else {
            Modifier
        }
    } else {
        val anchors = if (drawerState.expandable) {
            if (drawerState.skipOpenState) {
                mapOf(
                    0F to DrawerValue.Expanded,
                    fullHeight to DrawerValue.Closed,
                )
            } else {
                mapOf(
                    maxOpenHeight to DrawerValue.Open,
                    0F to DrawerValue.Expanded,
                    fullHeight to DrawerValue.Closed
                )
            }
        } else {
            mapOf(
                maxOpenHeight to DrawerValue.Open,
                fullHeight to DrawerValue.Closed
            )
        }
        Modifier.swipeable(
            state = drawerState,
            anchors = anchors,
            orientation = Orientation.Vertical,
            enabled = false,
            resistance = null
        )
    }
    return this.then(modifier)
}


private fun Modifier.drawerHeight(
    slideOver: Boolean,
    fixedHeight: Float,
    fullHeight: Float,
    drawerState: DrawerState
): Modifier {
    val modifier = if (slideOver) {
        if (drawerState.expandable) {
            Modifier
        } else {
            Modifier.heightIn(
                0.dp,
                pxToDp(fixedHeight)
            )
        }
    } else {
        Modifier.height(pxToDp(fullHeight - drawerState.offset.value))
    }

    return this.then(modifier)
}
