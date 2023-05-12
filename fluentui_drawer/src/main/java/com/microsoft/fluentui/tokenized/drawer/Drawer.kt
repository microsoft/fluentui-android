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
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
class DrawerState(
    private val initialValue: DrawerValue = DrawerValue.Closed,
    confirmStateChange: (DrawerValue) -> Boolean = { true }
) : SwipeableState<DrawerValue>(
    initialValue = initialValue,
    animationSpec = AnimationSpec,
    confirmStateChange = confirmStateChange
) {

    var enable: Boolean by mutableStateOf(false)

    /**
     * Whether the drawer is open.
     */
    val isOpen: Boolean
        get() = currentValue != DrawerValue.Closed

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
        delay(50)
        try {
            animateTo(DrawerValue.Open, AnimationSpec)
        } catch (e: Exception) {
            //TODO: When previous instance of drawer changes its content & closed then on
            // re-triggering the same drawer, it open but stuck to end of screen due to
            // JobCancellationException thrown with message "ScopeCoroutine was cancelled".
            // Hence re-triggering "animateTo". Check for better sol
            animateTo(DrawerValue.Open, AnimationSpec)
        }
        animationInProgress = false
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
        animationInProgress = false
        enable = false
    }

    /**
     * Fully expand the drawer with animation and suspend until it if fully expanded or
     * animation has been cancelled.
     * *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun expand() {
        animationInProgress = true
        val targetValue = when {
            hasExpandedState -> DrawerValue.Expanded
            else -> DrawerValue.Open
        }
        try {
            animateTo(targetValue = targetValue, AnimationSpec)
            animationInProgress = false
        } catch (e: Exception) {
            animateTo(targetValue = targetValue, AnimationSpec)
        }
    }

    val nestedScrollConnection = this.PreUpPostDownNestedScrollConnection

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
fun rememberDrawerState(confirmStateChange: (DrawerValue) -> Boolean = { true }): DrawerState {
    return rememberSaveable(saver = DrawerState.Saver(confirmStateChange)) {
        DrawerState(DrawerValue.Closed, confirmStateChange)
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
    color: Color
) {
    val dismissDrawer = if (open) {
        Modifier.pointerInput(onClose) { detectTapGestures { onClose() } }
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
private const val DRAWER_HANDLE_TAG = "Drawer Handle"
private const val DRAWER_CONTENT_TAG = "Drawer Content"
private const val DRAWER_SCRIM_TAG = "Drawer Scrim"

//Drawer Handle height + padding
private val DrawerHandleHeightOffset = 20.dp

private fun Modifier.drawerHeight(
    expandable: Boolean,
    slideOver: Boolean,
    fixedHeight: Float,
    fullHeight: Float,
    drawerState: DrawerState
): Modifier {
    val modifier = if (slideOver) {
        if (expandable) {
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
    expandable: Boolean,
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
            val anchors = if (drawerHeight < bottomOpenStateY || !expandable) {
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
        val anchors = if (expandable) {
            mapOf(
                fullHeight to DrawerValue.Closed,
                maxOpenHeight to DrawerValue.Open,
                0F to DrawerValue.Expanded
            )
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
    drawerContent: @Composable () -> Unit
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
                open = drawerState.isOpen,
                onClose = onDismiss,
                fraction = {
                    calculateFraction(minValue, maxValue, drawerState.offset.value)
                },
                color = if (scrimVisible) scrimColor else Color.Transparent,
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
                        if (drawerState.isOpen) {
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
            )

            Box(
                drawerConstraints
                    .offset { IntOffset(0, 0) }
                    .semantics {
                        if (drawerState.isOpen) {
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
    expandable: Boolean,
    slideOver: Boolean,
    onDismiss: () -> Unit,
    drawerContent: @Composable () -> Unit
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
                if (drawerState.anchors.isEmpty()
                ) {
                    0.toFloat()
                } else {
                    calculateFraction(
                        drawerState.anchors.entries.firstOrNull { it.value == DrawerValue.Closed }?.key!!,
                        drawerState.anchors.entries.firstOrNull { it.value == DrawerValue.Open }?.key!!,
                        drawerState.offset.value
                    )
                }
            },
            color = if (scrimVisible) scrimColor else Color.Transparent,
        )

        Box(
            drawerConstraints
                .nestedScroll(if (slideOver) drawerState.nestedScrollConnection else drawerState.PostDownNestedScrollConnection)
                .offset {
                    val y = if (drawerState.anchors == null) {
                        fullHeight.roundToInt()
                    } else {
                        drawerState.offset.value.roundToInt()
                    }
                    IntOffset(x = 0, y = y)
                }
                .bottomDrawerSwipeable(
                    drawerState,
                    expandable,
                    slideOver,
                    maxOpenHeight,
                    fullHeight,
                    drawerHeight.value
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
                        drawerHeight.value = if (expandable) {
                            originalSize
                        } else {
                            min(
                                originalSize,
                                maxOpenHeight
                            )
                        }
                    }
                }
                .drawerHeight(expandable, slideOver, maxOpenHeight, fullHeight, drawerState)
                .shadow(drawerElevation)
                .clip(drawerShape)
                .background(drawerBackground)
                .semantics {
                    if (drawerState.isOpen) {
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
                        } else if (drawerState.hasExpandedState) {
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
                                    if (drawerState.hasExpandedState && drawerState.isOpen) LocalContext.current.resources.getString(
                                        R.string.expand
                                    ) else null
                                }
                            ) {
                                if (drawerState.currentValue == DrawerValue.Expanded) {
                                    if (drawerState.confirmStateChange(DrawerValue.Open)) {
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
 * @param expandable if true drawer would expand on drag else drawer open till fixed/wrapped height.
 * The default value is false
 * @param scrimVisible create obscures background when scrim visible set to true when the drawer is open. The default value is true
 * @param drawerTokens tokens to provide appearance values. If not provided then drawer tokens will be picked from [FluentTheme]
 * @param drawerContent composable that represents content inside the drawer
 *
 * @throws IllegalStateException when parent has [Float.POSITIVE_INFINITY] width
 */

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    behaviorType: BehaviorType = BehaviorType.BOTTOM,
    drawerState: DrawerState = rememberDrawerState(),
    expandable: Boolean = false,
    scrimVisible: Boolean = true,
    drawerTokens: DrawerTokens? = null,
    drawerContent: @Composable () -> Unit
) {
    if (drawerState.enable) {
        val tokens = drawerTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Drawer] as DrawerTokens

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
                    expandable = expandable,
                    slideOver = behaviorType == BehaviorType.BOTTOM_SLIDE_OVER,
                    onDismiss = close,
                    drawerContent = drawerContent
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
                    drawerContent = drawerContent
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
                    drawerContent = drawerContent
                )
            }
        }
    }
}