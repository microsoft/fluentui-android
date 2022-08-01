package com.microsoft.fluentui.tokenized.drawer

import android.content.res.Resources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
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
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.DrawerTokens
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt


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

private const val DrawerOpenFraction = 0.5f

private fun pxToDp(value: Float) = (value / Resources
        .getSystem()
        .displayMetrics.density).dp

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
        modifier: Modifier,
        behaviorType: BehaviorType,
        drawerState: DrawerState,
        drawerShape: Shape,
        drawerElevation: Dp,
        drawerBackgroundColor: Color,
        drawerContentColor: Color,
        scrimColor: Color,
        enableScrim: Boolean,
        onDismiss: () -> Unit,
        drawerContent: @Composable () -> Unit
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        val modalDrawerConstraints = constraints
        val fullWidth = constraints.maxWidth.toFloat()
        var drawerWidth by remember(fullWidth) { mutableStateOf(fullWidth) }
        //Hack to get exact drawerHeight wrt to content.
        val visible = remember { mutableStateOf(true) }
        if (visible.value) {
            Box(modifier = Modifier.onPlaced { layoutCoordinates ->
                drawerWidth = layoutCoordinates.size.width.toFloat()
                visible.value = false
            }) {
                drawerContent()
            }
        } else {
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
                            thresholds = { _, _ -> FixedThreshold(pxToDp(value = drawerWidth / 2)) },
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
                        color = if (enableScrim) scrimColor else Color.Transparent,
                )

                val navigationMenu = getString(Strings.NavigationMenu)

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
                                .offset { IntOffset(drawerState.offset.value.roundToInt(), 0) }
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
                                },
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
}

@Composable
@ExperimentalMaterialApi
private fun VerticalDrawer(
        modifier: Modifier,
        behaviorType: BehaviorType,
        drawerState: DrawerState,
        drawerShape: Shape,
        drawerElevation: Dp,
        drawerBackgroundColor: Color,
        drawerContentColor: Color,
        drawerHandleColor: Color,
        scrimColor: Color,
        enableScrim: Boolean,
        expandable: Boolean,
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
                                    drawerHeight = placeable.height.toFloat() + dpToPx(44.dp) //Add Handle height + padding
                                }
                            }
            ) {
                drawerContent()
            }
        } else {
            val allowedHeight = fullHeight * DrawerOpenFraction
            val minHeight = 0f
            val topCloseHeight = minHeight
            val topOpenHeight = min(allowedHeight, drawerHeight)

            val bottomOpenStateY = max(allowedHeight, fullHeight - drawerHeight)
            val bottomExpandedStateY = max(minHeight, fullHeight - drawerHeight)

            val bottomDrawerHeight = if (expandable) drawerHeight else min(allowedHeight, drawerHeight)

            val scrimMinValue: Float
            val scrimMaxValue: Float

            val anchors = if (behaviorType == BehaviorType.TOP) {
                scrimMinValue = topCloseHeight
                scrimMaxValue = topOpenHeight
                mapOf(
                        topCloseHeight to DrawerValue.Closed,
                        topOpenHeight to DrawerValue.Open
                )
            } else {
                scrimMinValue = fullHeight
                scrimMaxValue = bottomOpenStateY
                if (drawerHeight < bottomOpenStateY || !expandable) {
                    mapOf(
                            fullHeight to DrawerValue.Closed,
                            bottomOpenStateY to DrawerValue.Open
                    )
                } else {
                    mapOf(
                            fullHeight to DrawerValue.Closed,
                            bottomOpenStateY to DrawerValue.Open,
                            bottomExpandedStateY to DrawerValue.Expanded
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
                Scrim(
                        open = drawerState.isOpen,
                        onClose = onDismiss,
                        fraction = {
                            calculateFraction(scrimMinValue, scrimMaxValue, drawerState.offset.value)
                        },
                        color = if (enableScrim) scrimColor else Color.Transparent,
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
                                    .height(pxToDp(bottomDrawerHeight))
                                    .onGloballyPositioned { layoutCoordinates ->
                                        if (!drawerState.openTriggered && (drawerState.isClosed || layoutCoordinates.size.height == 0)
                                        ) {
                                            onDismiss()
                                        }
                                    },
                            shape = drawerShape,
                            color = drawerBackgroundColor,
                            contentColor = drawerContentColor,
                            elevation = drawerElevation
                    ) {
                        Column {
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                            .padding(vertical = 8.dp)
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
                                        contentDescription = "Localized description",
                                        tint = drawerHandleColor
                                )
                            }
                            Column(content = { drawerContent() })
                        }
                    }
                } else {
                    Surface(
                            drawerConstraints
                                    .offset { IntOffset(0, 0) }
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
                            Column(modifier = Modifier
                                    .offset { IntOffset(0, 0) }
                                    .padding(top = 21.dp)
                                    .constrainAs(drawerContentConstrain) {
                                        top.linkTo(parent.top)
                                        bottom.linkTo(drawerHandleConstrain.top)
                                    }, content = { drawerContent() }
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                            .constrainAs(drawerHandleConstrain) {
                                                top.linkTo(drawerContentConstrain.bottom, margin = 8.dp)
                                                bottom.linkTo(parent.bottom, margin = 32.dp)
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
                                        contentDescription = "Localized description",
                                        tint = drawerHandleColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

internal val LocalDrawerTokens = compositionLocalOf { DrawerTokens() }

@Composable
fun getDrawerTokens(): DrawerTokens {
    return LocalDrawerTokens.current
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Drawer(
        modifier: Modifier = Modifier,
        drawerTokens: DrawerTokens? = null,
        behaviorType: BehaviorType = BehaviorType.BOTTOM,
        drawerState: DrawerState = rememberDrawerState(),
        expandable: Boolean = false,
        enableScrim: Boolean = true,
        drawerContent: @Composable () -> Unit
) {
    AnimatedVisibility(drawerState.enable) {
        val tokens = drawerTokens
                ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Drawer] as DrawerTokens

        val popupPositionProvider = DrawerPositionProvider()
        val scope = rememberCoroutineScope()
        val close: () -> Unit = {
            scope.launch { drawerState.close() }
        }

        CompositionLocalProvider(LocalDrawerTokens provides tokens) {
            Popup(
                    onDismissRequest = close,
                    popupPositionProvider = popupPositionProvider,
                    properties = PopupProperties(focusable = true)
            )
            {
                val drawerShape: Shape = RoundedCornerShape(getDrawerTokens().borderRadius(type = behaviorType))
                val drawerElevation: Dp = getDrawerTokens().elevation(type = behaviorType)
                val drawerBackgroundColor: Color = getDrawerTokens().backgroundColor(type = behaviorType)
                val drawerContentColor: Color = Color.Transparent
                val drawerHandleColor: Color = getDrawerTokens().handleColor(type = behaviorType)
                val scrimOpacity: Float = getDrawerTokens().scrimOpacity(type = behaviorType)
                val scrimColor: Color = getDrawerTokens().scrimColor(type = behaviorType).copy(alpha = scrimOpacity)

                when (behaviorType) {
                    BehaviorType.BOTTOM, BehaviorType.TOP -> VerticalDrawer(
                            behaviorType = behaviorType,
                            modifier = modifier,
                            drawerState = drawerState,
                            drawerShape = drawerShape,
                            drawerElevation = drawerElevation,
                            drawerBackgroundColor = drawerBackgroundColor,
                            drawerContentColor = drawerContentColor,
                            drawerHandleColor = drawerHandleColor,
                            scrimColor = scrimColor,
                            enableScrim = enableScrim,
                            expandable = expandable,
                            onDismiss = close,
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
                            scrimColor = scrimColor,
                            enableScrim = enableScrim,
                            onDismiss = close,
                            drawerContent = drawerContent
                    )
                }
            }
        }
    }
}