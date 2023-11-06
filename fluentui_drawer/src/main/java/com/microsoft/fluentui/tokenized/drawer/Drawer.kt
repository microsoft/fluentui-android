package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.view.WindowInsetsCompat
import com.microsoft.fluentui.compose.*
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.DrawerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DrawerTokens
import com.microsoft.fluentui.tokenized.calculateFraction
import com.microsoft.fluentui.util.dpToPx
import com.microsoft.fluentui.util.pxToDp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
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
 * State of the [Drawer] composable.
 *
 * @param initialValue The initial value of the state.
 * @param expandable defines if the drawer is allowed to take the Expanded state.
 * @param skipOpenState defines if the drawer is allowed to take the Open state. (Open State is skipped in case of true)
 * expandable = true & skipOpenState = false -> Drawer can take all the three states.
 * expandable = true & skipOpenState = true -> Drawer can take only Closed & Expanded states.
 * expandable = false & skipOpenState = false -> Drawer can take only Closed & Open states.
 * expandable = false & skipOpenState = true -> Invalid state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
class DrawerState(
    private val initialValue: DrawerValue = DrawerValue.Closed,
    internal val expandable: Boolean = true,
    internal val skipOpenState: Boolean = false,
    confirmStateChange: (DrawerValue) -> Boolean = { true }
) : SwipeableState<DrawerValue>(
    initialValue = initialValue,
    animationSpec = AnimationSpec,
    confirmStateChange = confirmStateChange
) {
    init {
        if (skipOpenState) {
            require(initialValue != DrawerValue.Open) {
                "The initial value must not be set to Open if skipOpenState is set to" +
                        " true."
            }
            require(expandable) {
                "Invalid state: expandable = false & skipOpenState = true"
            }
        }
        if (!expandable) {
            require(initialValue != DrawerValue.Expanded) {
                "The initial value must not be set to Expanded if expandable is set to" +
                        " false."
            }
        }
    }
    var enable: Boolean by mutableStateOf(false)
    /**
     * Whether drawer has Open state.
     * It is false in case of skipOpenState is true.
     */
    internal val hasOpenedState: Boolean
        get() = anchors.values.contains(DrawerValue.Open)

    /**
     * Whether the drawer is closed.
     */
    val isClosed: Boolean
        get() = currentValue == DrawerValue.Closed

    /**
     * Whether drawer has expanded state.
     */
    internal val hasExpandedState: Boolean
        get() = anchors.values.contains(DrawerValue.Expanded)

    var animationInProgress: Boolean = false

    /**
     * Open the drawer with animation and suspend until it if fully opened or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the open animation ended
     */
    suspend fun open() {
        enable = true
        animationInProgress = true
        do {
            delay(50)
        } while (!anchorsFilled)
        /*
        * first try to open the drawer
        * if not possible then try to expand the drawer
         */
        var targetValue = when {
            hasOpenedState -> DrawerValue.Open
            hasExpandedState -> DrawerValue.Expanded
            else -> DrawerValue.Closed
        }
        if (targetValue != currentValue) {
            try {
                animateTo(targetValue = targetValue, AnimationSpec)

            } catch (e: Exception) {
                //TODO: When previous instance of drawer changes its content & closed then on
                // re-triggering the same drawer, it open but stuck to end of screen due to
                // JobCancellationException thrown with message "ScopeCoroutine was cancelled".
                // Hence re-triggering "animateTo". Check for better sol
                animateTo(targetValue = targetValue, AnimationSpec)
            } finally {
                animationInProgress = false
            }
        }
        else {
            animationInProgress = false
        }
    }

    /**
     * Close the drawer with animation and suspend until it if fully closed or animation has been
     * cancelled. This method will throw [CancellationException] if the animation is
     * interrupted
     *
     * @return the reason the close animation ended
     */
    suspend fun close() {
        animationInProgress = true
        try {
            animateTo(DrawerValue.Closed, AnimationSpec)
        } catch (e: Exception) {
            animateTo(DrawerValue.Closed, AnimationSpec)
        }
        finally {
            animationInProgress = false
            enable = false
            anchors = emptyMap()
            anchorsFilled = false
        }
    }

    /**
     * Fully expand the drawer with animation and suspend until it if fully expanded or
     * animation has been cancelled.
     * *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun expand() {
        enable = true
        animationInProgress = true
        do {
            delay(50)
        } while (!anchorsFilled)
        /*
        * first try to expand the drawer
        * if not possible then try to open the drawer
         */
        val targetValue = when {
            hasExpandedState -> DrawerValue.Expanded
            hasOpenedState -> DrawerValue.Open
            else -> DrawerValue.Closed
        }
        if (targetValue != currentValue) {
            try {
                animateTo(targetValue = targetValue, AnimationSpec)
            } catch (e: Exception) {
                animateTo(targetValue = targetValue, AnimationSpec)
            } finally {
                animationInProgress = false
            }
        } else {
            animationInProgress = false
        }
    }

    val nestedScrollConnection = this.PreUpPostDownNestedScrollConnection

    companion object {
        /**
         * The default [Saver] implementation for [DrawerState].
         */
        fun Saver(expandable: Boolean, skipOpenState: Boolean, confirmStateChange: (DrawerValue) -> Boolean) =
            Saver<DrawerState, DrawerValue>(
                save = { it.currentValue },
                restore = {
                    DrawerState(initialValue = it, expandable = expandable, skipOpenState = skipOpenState, confirmStateChange = confirmStateChange)
                }
            )
        /**
         * The default [Saver] implementation for [DrawerState].
         */
        @Deprecated(
            message = "Please specify the expandable And/Or skipOpenState parameter",
            replaceWith = ReplaceWith(
                "DrawerState.Saver(" +
                        "expandable = ," +
                        "skipOpenState = ," +
                        "confirmStateChange = confirmStateChange" +
                        ")"
            )
        )
        fun Saver(confirmStateChange: (DrawerValue) -> Boolean): Saver<DrawerState, DrawerValue> =
            Saver(expandable = true , skipOpenState = false, confirmStateChange = confirmStateChange)

    }
}

/**
 * Create and [remember] a [DrawerState].
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Composable
fun rememberDrawerState(confirmStateChange: (DrawerValue) -> Boolean = { true }): DrawerState {
    return rememberSaveable(saver = DrawerState.Saver(expandable = true, skipOpenState = false, confirmStateChange = confirmStateChange)) {
        DrawerState(initialValue = DrawerValue.Closed, expandable = true, skipOpenState = false, confirmStateChange)
    }
}
@Composable
fun rememberBottomDrawerState(expandable: Boolean = true, skipOpenState: Boolean = false, confirmStateChange: (DrawerValue) -> Boolean = { true }): DrawerState {
    return rememberSaveable(
        confirmStateChange, expandable, skipOpenState,
        saver = DrawerState.Saver(expandable, skipOpenState, confirmStateChange)) {
        DrawerState(DrawerValue.Closed, expandable, skipOpenState , confirmStateChange)
    }
}
private class DrawerPositionProvider : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return IntOffset(0, 0)
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
) {
    val dismissDrawer = if (open) {
        Modifier.pointerInput(onClose) {
            detectTapGestures {
                if(!preventDismissalOnScrimClick) {
                    onClose()
                }
                onScrimClick() //this function runs post onClose() so that the drawer is closed before the callback is invoked
            }
        }
    } else {
        Modifier
    }

    Canvas(
        Modifier
            .fillMaxSize()
            .then(dismissDrawer)
            .testTag(DRAWER_SCRIM_TAG)
    ) {
        drawRect(color, alpha = fraction())
    }
}

private val EndDrawerPadding = 56.dp
private val DrawerVelocityThreshold = 400.dp

private val AnimationSpec = TweenSpec<Float>(durationMillis = 256)

private const val DrawerOpenFraction = 0.5f

//Tag use for testing
const val DRAWER_HANDLE_TAG = "Fluent Drawer Handle"
const val DRAWER_CONTENT_TAG = "Fluent Drawer Content"
const val DRAWER_SCRIM_TAG = "Fluent Drawer Scrim"

//Drawer Handle height + padding
private val DrawerHandleHeightOffset = 20.dp

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
                            fullHeight to DrawerValue.Closed,
                            bottomOpenStateY to DrawerValue.Expanded
                        )
                    } else {
                        mapOf(
                            fullHeight to DrawerValue.Closed,
                            bottomOpenStateY to DrawerValue.Open
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
                                    fullHeight to DrawerValue.Closed,
                                    bottomExpandedStateY to DrawerValue.Open // when drawerHeight is greater than maxOpenHeight but less than fullHeight, then Expanded state starts from fullHeight-drawerHeight
                                )
                            } else {
                                mapOf(
                                    fullHeight to DrawerValue.Closed,
                                    bottomExpandedStateY to DrawerValue.Expanded // when drawerHeight is greater than maxOpenHeight but less than fullHeight, then Expanded state starts from fullHeight-drawerHeight
                                )
                            }
                        } else {
                            mapOf(
                                fullHeight to DrawerValue.Closed,
                                maxOpenHeight to DrawerValue.Open,
                                bottomExpandedStateY to DrawerValue.Expanded
                            )
                        }
                    } else {
                        mapOf(
                            fullHeight to DrawerValue.Closed,
                            maxOpenHeight to DrawerValue.Open
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
            if(drawerState.skipOpenState){
                mapOf(
                    fullHeight to DrawerValue.Closed,
                    0F to DrawerValue.Expanded
                )
            }
            else {
                mapOf(
                    fullHeight to DrawerValue.Closed,
                    maxOpenHeight to DrawerValue.Open,
                    0F to DrawerValue.Expanded
                )
            }
        } else {
            mapOf(
                fullHeight to DrawerValue.Closed,
                maxOpenHeight to DrawerValue.Open
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
private fun HorizontalDrawer(
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

            val anchors = mapOf(minValue to DrawerValue.Closed, maxValue to DrawerValue.Open)
            val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
            Scrim(
                open = !drawerState.isClosed,
                onClose = onDismiss,
                fraction = {
                    calculateFraction(minValue, maxValue, drawerState.offset.value)
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
                    .swipeable(
                        state = drawerState,
                        anchors = anchors,
                        thresholds = { _, _ -> FixedThreshold(pxToDp(value = drawerWidth / 2)) },
                        orientation = Orientation.Horizontal,
                        enabled = false,
                        reverseDirection = isRtl,
                        velocityThreshold = DrawerVelocityThreshold,
                        resistance = null
                    ),
            ) {
                Column(
                    Modifier
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
                        )
                        .testTag(DRAWER_CONTENT_TAG), content = { drawerContent() })
            }
        }
    }
}

@Composable
private fun TopDrawer(
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

        //Get exact drawerHeight first.
        val visible = remember { mutableStateOf(true) }
        if (visible.value) {
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            visible.value = false
                            drawerHeight =
                                placeable.height.toFloat() + dpToPx(DrawerHandleHeightOffset)
                        }
                    }
            ) {
                drawerContent()
            }
        } else {
            val maxOpenHeight = fullHeight * DrawerOpenFraction
            val minHeight = 0f
            val topCloseHeight = minHeight
            val topOpenHeight = min(maxOpenHeight, drawerHeight)

            val minValue: Float = topCloseHeight
            val maxValue: Float = topOpenHeight

            val anchors = mapOf(
                topCloseHeight to DrawerValue.Closed,
                topOpenHeight to DrawerValue.Open
            )

            val drawerConstraints = with(LocalDensity.current) {
                Modifier
                    .sizeIn(
                        maxWidth = constraints.maxWidth.toDp(),
                        maxHeight = constraints.maxHeight.toDp()
                    )
            }

            Scrim(
                open = !drawerState.isClosed,
                onClose = onDismiss,
                fraction = {
                    calculateFraction(minValue, maxValue, drawerState.offset.value)
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
                        pxToDp(drawerState.offset.value)
                    )
                    .shadow(drawerElevation)
                    .clip(drawerShape)
                    .background(drawerBackground)
                    .swipeable(
                        state = drawerState,
                        anchors = anchors,
                        orientation = Orientation.Vertical,
                        enabled = false,
                        resistance = null
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
}

@Composable
private fun BottomDrawer(
    modifier: Modifier,
    drawerState: DrawerState,
    drawerShape: Shape,
    drawerElevation: Dp,
    drawerBackground: Brush,
    drawerHandleColor: Color,
    scrimColor: Color,
    scrimVisible: Boolean,
    slideOver: Boolean,
    showHandle: Boolean,
    onDismiss: () -> Unit,
    drawerContent: @Composable () -> Unit,
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
            open = !drawerState.isClosed,
            onClose = onDismiss,
            fraction = {
                if (drawerState.anchors.isEmpty()) {
                    0.toFloat()
                } else {
                    var targetValue: DrawerValue = if(slideOver){
                        drawerState.anchors.maxBy { it.value }?.value!!
                    }
                    else if (drawerState.skipOpenState) {
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

        Box(
            drawerConstraints
                .fillMaxWidth()
                .nestedScroll(if (slideOver) drawerState.nestedScrollConnection else drawerState.PostDownNestedScrollConnection)
                .offset {
                    val y = if (drawerState.anchors == null) {
                        fullHeight.roundToInt()
                    } else {
                        drawerState.offset.value.roundToInt()
                    }
                    IntOffset(x = 0, y = y)
                }
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
                            .testTag(DRAWER_HANDLE_TAG)
                    ) {
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
                                        if (drawerState.hasOpenedState && drawerState.confirmStateChange(DrawerValue.Open)) {
                                            scope.launch { drawerState.open() }
                                        }
                                    } else if (drawerState.hasExpandedState) {
                                        if (drawerState.confirmStateChange(DrawerValue.Expanded)) {
                                            scope.launch { drawerState.expand() }
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

/**
 *
 * Drawer block interaction with the rest of an app’s content with a scrim.
 * They are elevated above most of the app’s UI and don’t affect the screen’s layout grid.
 *
 * @param modifier optional modifier for the drawer
 * @param behaviorType opening behaviour of drawer. Default is BOTTOM
 * @param drawerState state of the drawer
 * @param scrimVisible create obscures background when scrim visible set to true when the drawer is open. The default value is true
 * @param drawerTokens tokens to provide appearance values. If not provided then drawer tokens will be picked from [FluentTheme]
 * @param drawerContent composable that represents content inside the drawer
 * @param preventDismissalOnScrimClick when true, the drawer will not be dismissed when the scrim is clicked
 * @param onScrimClick callback to be invoked when the scrim is clicked
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 */

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    behaviorType: BehaviorType = BehaviorType.BOTTOM,
    drawerState: DrawerState = rememberDrawerState(),
    scrimVisible: Boolean = true,
    drawerTokens: DrawerTokens? = null,
    drawerContent: @Composable () -> Unit,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {}
) {
    if (drawerState.enable) {
        val themeID =
            FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
        val tokens = drawerTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.DrawerControlType] as DrawerTokens

        val popupPositionProvider = DrawerPositionProvider()
        val scope = rememberCoroutineScope()
        val close: () -> Unit = {
            if (drawerState.confirmStateChange(DrawerValue.Closed)) {
                scope.launch { drawerState.close() }
            }
        }
        val drawerInfo = DrawerInfo(type = behaviorType)
        Popup(
            onDismissRequest = close,
            popupPositionProvider = popupPositionProvider,
            properties = PopupProperties(focusable = true)
        )
        {
            val drawerShape: Shape =
                when (behaviorType) {
                    BehaviorType.BOTTOM, BehaviorType.BOTTOM_SLIDE_OVER -> RoundedCornerShape(
                        topStart = tokens.borderRadius(drawerInfo),
                        topEnd = tokens.borderRadius(drawerInfo)
                    )
                    BehaviorType.TOP -> RoundedCornerShape(
                        bottomStart = tokens.borderRadius(drawerInfo),
                        bottomEnd = tokens.borderRadius(drawerInfo)
                    )
                    else -> RoundedCornerShape(tokens.borderRadius(drawerInfo))
                }
            val drawerElevation: Dp = tokens.elevation(drawerInfo)
            val drawerBackgroundColor: Brush =
                tokens.backgroundBrush(drawerInfo)
            val drawerHandleColor: Color = tokens.handleColor(drawerInfo)
            val scrimOpacity: Float = tokens.scrimOpacity(drawerInfo)
            val scrimColor: Color =
                tokens.scrimColor(drawerInfo).copy(alpha = scrimOpacity)

            when (behaviorType) {
                BehaviorType.BOTTOM, BehaviorType.BOTTOM_SLIDE_OVER -> BottomDrawer(
                    modifier = modifier,
                    drawerState = drawerState,
                    drawerShape = drawerShape,
                    drawerElevation = drawerElevation,
                    drawerBackground = drawerBackgroundColor,
                    drawerHandleColor = drawerHandleColor,
                    scrimColor = scrimColor,
                    scrimVisible = scrimVisible,
                    slideOver = behaviorType == BehaviorType.BOTTOM_SLIDE_OVER,
                    showHandle = true,
                    onDismiss = close,
                    drawerContent = drawerContent,
                    preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                    onScrimClick = onScrimClick
                )
                BehaviorType.TOP -> TopDrawer(
                    modifier = modifier,
                    drawerState = drawerState,
                    drawerShape = drawerShape,
                    drawerElevation = drawerElevation,
                    drawerBackground = drawerBackgroundColor,
                    drawerHandleColor = drawerHandleColor,
                    scrimColor = scrimColor,
                    scrimVisible = scrimVisible,
                    onDismiss = close,
                    drawerContent = drawerContent,
                    preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                    onScrimClick = onScrimClick
                )

                BehaviorType.LEFT_SLIDE_OVER, BehaviorType.RIGHT_SLIDE_OVER -> HorizontalDrawer(
                    behaviorType = behaviorType,
                    modifier = modifier,
                    drawerState = drawerState,
                    drawerShape = drawerShape,
                    drawerElevation = drawerElevation,
                    drawerBackground = drawerBackgroundColor,
                    scrimColor = scrimColor,
                    scrimVisible = scrimVisible,
                    onDismiss = close,
                    drawerContent = drawerContent,
                    preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                    onScrimClick = onScrimClick
                )
            }
        }
    }
}


/**
 *
 * BottomDrawer block interaction with the rest of an app’s content with a scrim.
 * They are elevated above most of the app’s UI and don’t affect the screen’s layout grid.
 *
 * @param modifier optional modifier for the drawer
 * @param drawerState state of the drawer
 * @param slideOver if true, then BottomDrawer would be drawn in full length & only covering up to half screen when open & it get slided more
 * in the visible region on expand. If false then, the BottomDrawer end at the bottom & hence the content get only the visible region height to draw itself.The default value is true
 * @param scrimVisible create obscures background when scrim visible set to true when the drawer is open. The default value is true
 * @param showHandle if true drawer handle would be visible. The default value is true
 * @param windowInsetsType Type window insets to be passed to the bottom drawer window via PaddingValues params. The default value is WindowInsetsCompat.Type.systemBars()
 * @param drawerTokens tokens to provide appearance values. If not provided then drawer tokens will be picked from [FluentTheme]
 * @param drawerContent composable that represents content inside the drawer
 * @param preventDismissalOnScrimClick when true, the drawer will not be dismissed when the scrim is clicked
 * @param onScrimClick callback to be invoked when the scrim is clicked
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 */

@Composable
fun BottomDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(),
    slideOver: Boolean = true,
    scrimVisible: Boolean = true,
    showHandle: Boolean = true,
    windowInsetsType: Int = WindowInsetsCompat.Type.systemBars(),
    drawerTokens: DrawerTokens? = null,
    drawerContent: @Composable () -> Unit,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {},
) {
    if (drawerState.enable) {
        val themeID =
            FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
        val tokens = drawerTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.DrawerControlType] as DrawerTokens

        val scope = rememberCoroutineScope()
        val close: () -> Unit = {
            if (drawerState.confirmStateChange(DrawerValue.Closed)) {
                scope.launch { drawerState.close() }
            }
        }
        val behaviorType =
            if (slideOver) BehaviorType.BOTTOM_SLIDE_OVER else BehaviorType.BOTTOM
        val drawerInfo = DrawerInfo(type = behaviorType)
        ModalPopup(
            onDismissRequest = close,
            windowInsetsType = windowInsetsType
        )
        {
            val drawerShape: Shape =
                RoundedCornerShape(
                    topStart = tokens.borderRadius(drawerInfo),
                    topEnd = tokens.borderRadius(drawerInfo)
                )

            val drawerElevation: Dp = tokens.elevation(drawerInfo)
            val drawerBackgroundColor: Brush =
                tokens.backgroundBrush(drawerInfo)
            val drawerHandleColor: Color = tokens.handleColor(drawerInfo)
            val scrimOpacity: Float = tokens.scrimOpacity(drawerInfo)
            val scrimColor: Color =
                tokens.scrimColor(drawerInfo).copy(alpha = scrimOpacity)
            BottomDrawer(
                modifier = modifier,
                drawerState = drawerState,
                drawerShape = drawerShape,
                drawerElevation = drawerElevation,
                drawerBackground = drawerBackgroundColor,
                drawerHandleColor = drawerHandleColor,
                scrimColor = scrimColor,
                scrimVisible = scrimVisible,
                slideOver = slideOver,
                showHandle = showHandle,
                onDismiss = close,
                drawerContent = drawerContent,
                preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                onScrimClick = onScrimClick
            )
        }
    }
}