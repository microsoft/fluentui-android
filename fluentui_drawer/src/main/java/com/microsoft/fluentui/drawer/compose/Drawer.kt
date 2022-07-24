package com.microsoft.fluentui.drawer.compose

import android.content.res.Resources
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import com.microsoft.fluentui.compose.*
import com.microsoft.fluentui.compose.FractionalThreshold
import com.microsoft.fluentui.compose.SwipeableState
import com.microsoft.fluentui.compose.Strings
import com.microsoft.fluentui.drawer.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Possible values of [BehaviorType].
 */
enum class BehaviorType {
    BOTTOM, TOP, LEFT, RIGHT
}

/**
 * Possible values of [DrawerState].
 */
enum class DrawerValue {
    /**
     * The state of the drawer when it is closed.
     */
    Closed,

    /**
     * The state of the drawer when it is open.
     */
    Open,

    /**
     * The state of the bottom drawer when it is expanded (i.e. at 100% height).
     */
    Expanded
}

/**
 * State of the [HorizontalDrawer] composable.
 *
 * @param initialValue The initial value of the state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Suppress("NotCloseable")
@OptIn(ExperimentalMaterialApi::class)
@Stable
class DrawerState(
        private val initialValue: DrawerValue = DrawerValue.Closed,
        confirmStateChange: (DrawerValue) -> Boolean = { true }
) {

    internal val swipeableState = SwipeableState(
            initialValue = initialValue,
            animationSpec = AnimationSpec,
            confirmStateChange = confirmStateChange
    )
    var enable: Boolean by mutableStateOf(false)

    /**
     * Whether the drawer is open.
     */
    val isOpen: Boolean
        get() = currentValue == DrawerValue.Open

    /**
     * Whether the drawer is closed.
     */
    val isClosed: Boolean
        get() = currentValue == DrawerValue.Closed

    /**
     * The current value of the state.
     *
     * If no swipe or animation is in progress, this corresponds to the start the drawer
     * currently in. If a swipe or an animation is in progress, this corresponds the state drawer
     * was in before the swipe or animation started.
     */
    val currentValue: DrawerValue
        get() {
            return swipeableState.currentValue
        }

    /**
     * Whether the state is currently animating.
     */
    val isAnimationRunning: Boolean
        get() {
            return swipeableState.isAnimationRunning
        }

    var openTriggered: Boolean = false

    /**
     * Open the drawer with animation and suspend until it if fully opened or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the open animation ended
     */
    suspend fun open() {
        enable = true
        openTriggered = true
        animateTo(DrawerValue.Open, AnimationSpec)
        openTriggered = false
    }

    /**
     * Close the drawer with animation and suspend until it if fully closed or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the close animation ended
     */
    suspend fun close() {
        enable = false
        animateTo(DrawerValue.Closed, AnimationSpec)
    }

    /**
     * Set the state of the drawer with specific animation
     *
     * @param targetValue The new value to animate to.
     * @param anim The animation that will be used to animate to the new value.
     */
    @ExperimentalMaterialApi
    suspend fun animateTo(targetValue: DrawerValue, anim: AnimationSpec<Float>) =
            swipeableState.animateTo(targetValue, anim)

    /**
     * Set the state without any animation and suspend until it's set
     *
     * @param targetValue The new target value
     */
    @ExperimentalMaterialApi
    suspend fun snapTo(targetValue: DrawerValue) {
        swipeableState.snapTo(targetValue)
    }

    /**
     * The target value of the drawer state.
     *
     * If a swipe is in progress, this is the value that the Drawer would animate to if the
     * swipe finishes. If an animation is running, this is the target value of that animation.
     * Finally, if no swipe or animation is in progress, this is the same as the [currentValue].
     */
    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    //@ExperimentalMaterialApi
    //@get:ExperimentalMaterialApi
    val targetValue: DrawerValue
        get() = swipeableState.targetValue

    /**
     * The current position (in pixels) of the drawer sheet.
     */
    @Suppress("OPT_IN_MARKER_ON_WRONG_TARGET")
    //@ExperimentalMaterialApi
    //@get:ExperimentalMaterialApi
    val offset: State<Float>
        get() = swipeableState.offset

    fun performDrag(drag: Float): Float = swipeableState.performDrag(drag)

    suspend fun performFling(velocity: Float) = swipeableState.performFling(velocity)

    val nestedScrollConnection = swipeableState.PreUpPostDownNestedScrollConnection

    companion object {
        /**
         * The default [Saver] implementation for [DrawerState].
         */
        fun Saver(confirmStateChange: (DrawerValue) -> Boolean) =
                Saver<DrawerState, DrawerValue>(
                        save = { it.currentValue },
                        restore = { DrawerState(it, confirmStateChange) }
                )
    }
}

/**
 * Create and [remember] a [DrawerState].
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Composable
fun rememberDrawerState(
        confirmStateChange: (DrawerValue) -> Boolean = { true }
): DrawerState {
    return rememberSaveable(saver = DrawerState.Saver(confirmStateChange)) {
        DrawerState(DrawerValue.Closed, confirmStateChange)
    }
}

/**
 * Object to hold default values for [HorizontalDrawer] and [VerticalDrawer]
 */
object DrawerDefaults {

    /**
     * Default Elevation for drawer sheet as specified in material specs
     */
    val Elevation = 16.dp

    val scrimColor: Color
        @Composable
        get() = MaterialTheme.colors.onSurface.copy(alpha = ScrimOpacity)

    /**
     * Default alpha for scrim color
     */
    const val ScrimOpacity = 0.32f
}

class DrawerPositionProvider : PopupPositionProvider {
    override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(0, 0)
    }
}

private fun calculateFraction(a: Float, b: Float, pos: Float) =
        ((pos - a) / (b - a)).coerceIn(0f, 1f)

@Composable
private fun DrawerScrim(
        color: Color,
        onDismiss: () -> Unit,
        visible: Boolean
) {
    if (color.isSpecified) {
        val alpha by animateFloatAsState(
                targetValue = if (visible) 1f else 0f,
                animationSpec = TweenSpec()
        )
        val closeDrawer = getString(Strings.CloseDrawer)
        val dismissModifier = if (visible) {
            Modifier
                    .pointerInput(onDismiss) {
                        detectTapGestures { onDismiss() }
                    }
                    .semantics(mergeDescendants = true) {
                        contentDescription = closeDrawer
                        onClick { onDismiss(); true }
                    }
        } else {
            Modifier
        }

        Canvas(
                Modifier
                        .fillMaxSize()
                        .then(dismissModifier)
        ) {
            drawRect(color = color, alpha = alpha)
        }
    }
}

@Composable
private fun Scrim(
        open: Boolean,
        onClose: () -> Unit,
        fraction: () -> Float,
        color: Color
) {
    val closeDrawer = getString(Strings.CloseDrawer)
    val dismissDrawer = if (open) {
        Modifier
                .pointerInput(onClose) { detectTapGestures { onClose() } }
                .semantics(mergeDescendants = true) {
                    contentDescription = closeDrawer
                    onClick { onClose(); true }
                }
    } else {
        Modifier
    }

    Canvas(
            Modifier
                    .fillMaxSize()
                    .then(dismissDrawer)
    ) {
        drawRect(color, alpha = fraction())
    }
}

private val EndDrawerPadding = 56.dp
private val DrawerVelocityThreshold = 400.dp

// TODO: b/177571613 this should be a proper decay settling
// this is taken from the DrawerLayout's DragViewHelper as a min duration.
private val AnimationSpec = TweenSpec<Float>(durationMillis = 256)

private const val BottomDrawerOpenFraction = 0.5f
private const val DrawerScimFraction = 0.4f

@Composable
private fun pxToDp(value: Float) = (value / Resources
        .getSystem()
        .displayMetrics.density).dp

@Composable
private fun dpToPx(value: Dp) = (value * Resources
        .getSystem()
        .displayMetrics.density).value

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
 * @param drawerBackgroundColor background color to be used for the drawer sheet
 * @param drawerContentColor color of the content to use inside the drawer sheet. Defaults to
 * either the matching content color for [drawerBackgroundColor], or, if it is not a color from
 * the theme, this will keep the same value set above this Surface.
 * @param scrimColor color of the scrim that obscures content when the drawer is open
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 */
@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun HorizontalDrawer(
        modifier: Modifier = Modifier,
        behaviorType: BehaviorType = BehaviorType.LEFT,
        drawerState: DrawerState = rememberDrawerState(),
        drawerShape: Shape = MaterialTheme.shapes.large,
        drawerElevation: Dp = DrawerDefaults.Elevation,
        drawerBackgroundColor: Color = MaterialTheme.colors.surface,
        drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
        scrimColor: Color = DrawerDefaults.scrimColor,
        onDismiss: () -> Unit,
        drawerContent: @Composable () -> Unit
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        val modalDrawerConstraints = constraints
        val fullWidth = constraints.maxWidth.toFloat()
        var drawerWidth by remember(fullWidth) { mutableStateOf(fullWidth) }
        val paddingPx = pxToDp(max(dpToPx(EndDrawerPadding), (fullWidth - drawerWidth)))

        // TODO : think about Infinite max bounds case
        if (!modalDrawerConstraints.hasBoundedWidth) {
            throw IllegalStateException("Drawer shouldn't have infinite width")
        }
        val leftSlide = behaviorType == BehaviorType.LEFT

        val minValue = modalDrawerConstraints.maxWidth.toFloat() * (if (leftSlide) (-1F) else (1F))
        val maxValue = 0f

        val anchors = mapOf(minValue to DrawerValue.Closed, maxValue to DrawerValue.Open)
        val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
        Box(
                Modifier.swipeable(
                        state = drawerState.swipeableState,
                        anchors = anchors,
                        thresholds = { _, _ -> FractionalThreshold(0.5f) },
                        orientation = Orientation.Horizontal,
                        enabled = false,
                        reverseDirection = isRtl,
                        velocityThreshold = DrawerVelocityThreshold,
                        resistance = null
                )
        ) {
            Scrim(
                    open = drawerState.isOpen,
                    onClose = onDismiss,
                    fraction = {
                        calculateFraction(minValue, maxValue, drawerState.offset.value)
                    },
                    color = scrimColor
            )
            val navigationMenu = getString(Strings.NavigationMenu)

            //Hack to get exact drawerHeight wrt to content.
            val visible = remember { mutableStateOf(true) }
            if (visible.value) {
                Box(modifier = Modifier.onPlaced { layoutCoordinates ->
                    drawerWidth = layoutCoordinates.size.width.toFloat()
                    visible.value = false
                }) {
                    drawerContent()
                }
            }
            val yOffset = remember { mutableStateOf(0) }

            Surface(
                    modifier = with(LocalDensity.current) {
                        Modifier
                                .sizeIn(
                                        minWidth = modalDrawerConstraints.minWidth.toDp(),
                                        minHeight = modalDrawerConstraints.minHeight.toDp(),
                                        maxWidth = modalDrawerConstraints.maxWidth.toDp(),
                                        maxHeight = modalDrawerConstraints.maxHeight.toDp()
                                )

                    }
                            .offset { IntOffset(drawerState.offset.value.roundToInt(), yOffset.value) }
                            .padding(
                                    start = if (leftSlide) 0.dp else paddingPx,
                                    end = if (leftSlide) paddingPx else 0.dp
                            )
                            .semantics {
                                paneTitle = navigationMenu
                                if (drawerState.isOpen) {
                                    dismiss {
                                        if (
                                                drawerState.swipeableState
                                                        .confirmStateChange(DrawerValue.Closed)
                                        ) {
                                            onDismiss()
                                        }; true
                                    }
                                }
                            }
                            .requiredHeight(pxToDp(value = Resources.getSystem().displayMetrics.heightPixels.toFloat())),
                    shape = drawerShape,
                    color = drawerBackgroundColor,
                    contentColor = drawerContentColor,
                    elevation = drawerElevation
            ) {
                Column(Modifier
                        .draggable(
                                orientation = Orientation.Horizontal,
                                state = rememberDraggableState { delta ->
                                    drawerState.performDrag(delta)
                                },
                                onDragStopped = { velocity ->
                                    launch {
                                        drawerState.performFling(
                                                velocity
                                        )
                                        if (drawerState.isClosed) {
                                            onDismiss()
                                        }
                                    }
                                },
                        ), content = { drawerContent() })
            }
        }
    }
}

@Composable
@ExperimentalMaterialApi
private fun VerticalDrawer(
        modifier: Modifier = Modifier,
        behaviorType: BehaviorType = BehaviorType.BOTTOM,
        drawerState: DrawerState = rememberDrawerState(),
        drawerShape: Shape = MaterialTheme.shapes.large,
        drawerElevation: Dp = DrawerDefaults.Elevation,
        drawerBackgroundColor: Color = MaterialTheme.colors.surface,
        drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
        expandable: Boolean = false,
        scrimColor: Color = DrawerDefaults.scrimColor,
        onDismiss: () -> Unit,
        drawerContent: @Composable () -> Unit
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        val fullHeight = constraints.maxHeight.toFloat()
        var drawerHeight by remember(fullHeight) { mutableStateOf(fullHeight) }

        //Get exact drawerHeight first.
        val visible = remember { mutableStateOf(true) }

        if (visible.value) {
            Box(
                    modifier = Modifier
                            .layout { measurable, constraints ->
                                val placeable = measurable.measure(constraints)
                                layout(placeable.width, placeable.height) {
                                    visible.value = false
                                    drawerHeight = placeable.height.toFloat() + 32 //Added drawer height
                                }
                            }
            ) {
                drawerContent()
            }
        } else {
            val minHeight = 0f
            val topCloseHeight = minHeight
            val topOpenHeight = min(fullHeight * BottomDrawerOpenFraction, drawerHeight)

            val bottomOpenStartY =
                    max(fullHeight * BottomDrawerOpenFraction, fullHeight - drawerHeight)
            val bottomExpandedStartY = max(minHeight, fullHeight - drawerHeight)

            val bottomRequiredHeight = if (expandable)
                max(fullHeight * BottomDrawerOpenFraction, drawerHeight)
            else
                min(fullHeight * BottomDrawerOpenFraction, drawerHeight)

            val anchors = if (behaviorType == BehaviorType.TOP) {
                mapOf(
                        topCloseHeight to DrawerValue.Closed,
                        topOpenHeight to DrawerValue.Open
                )
            } else {
                if (drawerHeight < bottomOpenStartY || !expandable) {
                    mapOf(
                            fullHeight to DrawerValue.Closed,
                            bottomOpenStartY to DrawerValue.Open
                    )
                } else {
                    mapOf(
                            fullHeight to DrawerValue.Closed,
                            bottomOpenStartY to DrawerValue.Open,
                            bottomExpandedStartY to DrawerValue.Expanded
                    )
                }
            }

            val drawerConstraints = with(LocalDensity.current) {
                Modifier
                        .sizeIn(
                                maxWidth = constraints.maxWidth.toDp(),
                                maxHeight = constraints.maxHeight.toDp()
                        )
            }
            val nestedScroll = if (behaviorType == BehaviorType.BOTTOM) {
                Modifier.nestedScroll(drawerState.nestedScrollConnection)
            } else {
                Modifier
            }

            val swipeable = Modifier
                    .then(nestedScroll)
                    .swipeable(
                            state = drawerState.swipeableState,
                            anchors = anchors,
                            orientation = Orientation.Vertical,
                            enabled = false,
                            resistance = null
                    )

            Box(swipeable) {
                DrawerScrim(
                        color = scrimColor,
                        onDismiss = onDismiss,
                        visible = drawerState.targetValue != DrawerValue.Closed
                )
                val navigationMenu = getString(Strings.NavigationMenu)

                if (behaviorType == BehaviorType.BOTTOM) {
                    Surface(
                            drawerConstraints
                                    .offset { IntOffset(x = 0, y = drawerState.offset.value.roundToInt()) }
                                    .semantics {
                                        paneTitle = navigationMenu
                                        if (drawerState.isOpen) {
                                            // TODO(b/180101663) The action currently doesn't return the correct results
                                            dismiss {
                                                if (drawerState.swipeableState.confirmStateChange(
                                                                DrawerValue.Closed
                                                        )
                                                ) {
                                                    onDismiss()
                                                }; true
                                            }
                                        }
                                    }
                                    .height(
                                            if (drawerState.isClosed) {
                                                pxToDp(minHeight)
                                            } else {
                                                pxToDp(bottomRequiredHeight)
                                            }
                                    )
                                    .onGloballyPositioned { sizeC ->
                                        if (!drawerState.openTriggered && sizeC.size.height == 0
                                        ) {
                                            onDismiss()
                                        }
                                    },
                            shape = drawerShape,
                            color = drawerBackgroundColor,
                            contentColor = drawerContentColor,
                            elevation = drawerElevation
                    ) {
                        ConstraintLayout {
                            val (drawerContentConstrain, drawerHandleConstrain) = createRefs()
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                            .constrainAs(drawerHandleConstrain) {
                                                top.linkTo(parent.top, margin = 16.dp)
                                                bottom.linkTo(drawerContentConstrain.top)
                                            }
                                            .fillMaxWidth()
                                            .draggable(
                                                    orientation = Orientation.Vertical,
                                                    state = rememberDraggableState { delta ->
                                                        drawerState.performDrag(delta)
                                                    },
                                                    onDragStopped = { velocity ->
                                                        launch {
                                                            drawerState.performFling(
                                                                    velocity
                                                            )
                                                            if (drawerState.isClosed) {
                                                                onDismiss()
                                                            }
                                                        }
                                                    },
                                            )
                            ) {
                                Icon(
                                        painterResource(id = R.drawable.ic_drawer_handle),
                                        contentDescription = "Localized description"
                                )
                            }
                            Column(modifier = Modifier.constrainAs(drawerContentConstrain) {
                                top.linkTo(drawerHandleConstrain.top)
                                bottom.linkTo(parent.bottom)
                            }, content = { drawerContent() })
                        }
                    }
                } else {
                    Surface(
                            drawerConstraints
                                    .offset {
                                        IntOffset(
                                                0,
                                                0
                                        )
                                    }
                                    .semantics {
                                        paneTitle = navigationMenu
                                        if (drawerState.isOpen) {
                                            // TODO(b/180101663) The action currently doesn't return the correct results
                                            dismiss {
                                                if (drawerState.swipeableState.confirmStateChange(
                                                                DrawerValue.Closed
                                                        )
                                                ) {
                                                    onDismiss()
                                                };true
                                            }
                                        }
                                    }
                                    .height(
                                            pxToDp(drawerState.offset.value)
                                    ),
                            shape = drawerShape,
                            color = drawerBackgroundColor,
                            contentColor = drawerContentColor,
                            elevation = drawerElevation
                    ) {
                        ConstraintLayout {
                            val (drawerContentConstrain, drawerHandleConstrain) = createRefs()
                            Column(modifier = Modifier.constrainAs(drawerContentConstrain) {
                                top.linkTo(parent.top)
                                bottom.linkTo(drawerHandleConstrain.top)
                            }, content = { drawerContent() }
                            )

                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                            .constrainAs(drawerHandleConstrain) {
                                                top.linkTo(drawerContentConstrain.bottom)
                                                bottom.linkTo(parent.bottom, margin = 16.dp)
                                            }
                                            .fillMaxWidth()
                                            .height(32.dp)
                                            .draggable(
                                                    orientation = Orientation.Vertical,
                                                    state = rememberDraggableState { delta ->
                                                        drawerState.performDrag(delta)
                                                    },
                                                    onDragStopped = { velocity ->
                                                        launch {
                                                            drawerState.performFling(
                                                                    velocity
                                                            )
                                                            if (drawerState.isClosed) {
                                                                onDismiss()
                                                            }
                                                        }
                                                    },
                                            )
                            ) {
                                Icon(
                                        painterResource(id = R.drawable.ic_drawer_handle),
                                        contentDescription = "Localized description"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Drawer(
        modifier: Modifier = Modifier,
        behaviorType: BehaviorType = BehaviorType.BOTTOM,
        drawerState: DrawerState = rememberDrawerState(),
        drawerShape: Shape = MaterialTheme.shapes.large,
        drawerElevation: Dp = DrawerDefaults.Elevation,
        drawerBackgroundColor: Color = MaterialTheme.colors.surface,
        drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
        expandable: Boolean = false,
        scrimColor: Color = DrawerDefaults.scrimColor,
        drawerContent: @Composable () -> Unit
) {
    if (drawerState.enable) {
        val popupPositionProvider = DrawerPositionProvider()
        val scope = rememberCoroutineScope()
        val close: () -> Unit = {
            scope.launch { drawerState.close() }
        }

        Popup(
                onDismissRequest = close,
                popupPositionProvider = popupPositionProvider,
                properties = PopupProperties(focusable = true)
        )
        {
            when (behaviorType) {
                BehaviorType.BOTTOM, BehaviorType.TOP -> VerticalDrawer(
                        behaviorType = behaviorType,
                        modifier = modifier,
                        drawerState = drawerState,
                        drawerShape = drawerShape,
                        drawerElevation = drawerElevation,
                        drawerBackgroundColor = drawerBackgroundColor,
                        drawerContentColor = drawerContentColor,
                        expandable = expandable,
                        onDismiss = close,
                        scrimColor = scrimColor,
                        drawerContent = drawerContent
                )

                BehaviorType.LEFT, BehaviorType.RIGHT -> HorizontalDrawer(
                        behaviorType = behaviorType,
                        modifier = modifier,
                        drawerState = drawerState,
                        drawerShape = drawerShape,
                        drawerElevation = drawerElevation,
                        drawerBackgroundColor = drawerBackgroundColor,
                        drawerContentColor = drawerContentColor,
                        onDismiss = close,
                        scrimColor = scrimColor,
                        drawerContent = drawerContent
                )
            }
        }
    }
}
