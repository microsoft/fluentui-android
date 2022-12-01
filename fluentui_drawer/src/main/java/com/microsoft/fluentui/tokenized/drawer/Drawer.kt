package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.dismiss
import androidx.compose.ui.semantics.semantics
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
import com.microsoft.fluentui.theme.token.controlTokens.DrawerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DrawerTokens
import com.microsoft.fluentui.tokenized.calculateFraction
import com.microsoft.fluentui.util.*
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
        get() = currentValue == DrawerValue.Open

    /**
     * Whether the drawer is closed.
     */
    val isClosed: Boolean
        get() = currentValue == DrawerValue.Closed

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

private fun Modifier.drawerHeight(expandable: Boolean, fixedHeight: Float): Modifier {
    val modifier = if (expandable) {
        Modifier
    } else {
        Modifier.heightIn(
            0.dp,
            pxToDp(fixedHeight)
        )
    }
    return this.then(modifier)
}

private fun Modifier.bottomDrawerSwipeable(
    drawerState: DrawerState,
    expandable: Boolean,
    maxOpenHeight: Float,
    fullHeight: Float,
    drawerHeight: Float?
): Modifier {
    val modifier = if (drawerHeight != null) {
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
 * @param drawerBackgroundColor background color to be used for the drawer sheet
 * @param drawerContentColor color of the content to use inside the drawer sheet. Defaults to
 * either the matching content color for [drawerBackgroundColor], or, if it is not a color from
 * the theme, this will keep the same value set above this Surface.
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
    drawerBackgroundColor: Color,
    drawerContentColor: Color,
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
            val leftSlide = behaviorType == BehaviorType.LEFT

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
                        if (drawerState.isOpen) {
                            dismiss {
                                onDismiss()
                                true
                            }
                        }
                    }
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
                shape = drawerShape,
                color = drawerBackgroundColor,
                contentColor = drawerContentColor,
                elevation = drawerElevation
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
    drawerBackgroundColor: Color,
    drawerContentColor: Color,
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

            Surface(
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
                    .swipeable(
                        state = drawerState,
                        anchors = anchors,
                        orientation = Orientation.Vertical,
                        enabled = false,
                        resistance = null
                    )
                    .focusable(false),
                shape = drawerShape,
                color = drawerBackgroundColor,
                contentColor = drawerContentColor,
                elevation = drawerElevation
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
    drawerBackgroundColor: Color,
    drawerContentColor: Color,
    drawerHandleColor: Color,
    scrimColor: Color,
    scrimVisible: Boolean,
    expandable: Boolean,
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

        Surface(
            drawerConstraints
                .offset {
                    val y = if (drawerHeight.value == null) {
                        fullHeight.roundToInt()
                    } else {
                        drawerState.offset.value.roundToInt()
                    }
                    IntOffset(x = 0, y = y)
                }
                .semantics {
                    if (drawerState.isOpen) {
                        dismiss {
                            onDismiss()
                            true
                        }
                    }
                }
                .bottomDrawerSwipeable(
                    drawerState,
                    expandable,
                    maxOpenHeight,
                    fullHeight,
                    drawerHeight.value
                )
                .onGloballyPositioned { layoutCoordinates ->
                    if (!drawerState.animationInProgress
                        && drawerState.currentValue == DrawerValue.Closed
                        && drawerState.targetValue == DrawerValue.Closed) {
                        onDismiss()
                    }

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
                .nestedScroll(drawerState.nestedScrollConnection)
                .drawerHeight(expandable, maxOpenHeight)
                .focusable(false),
            shape = drawerShape,
            color = drawerBackgroundColor,
            contentColor = drawerContentColor,
            elevation = drawerElevation
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
                        contentDescription = null,
                        tint = drawerHandleColor
                    )
                }
                Column(modifier = Modifier
                    .testTag(DRAWER_CONTENT_TAG), content = { drawerContent() })
            }
        }
    }
}

internal val LocalDrawerTokens = compositionLocalOf { DrawerTokens() }
internal val LocalDrawerInfo = compositionLocalOf { DrawerInfo() }

@Composable
private fun getDrawerTokens(): DrawerTokens {
    return LocalDrawerTokens.current
}

@Composable
private fun getDrawerInfo(): DrawerInfo {
    return LocalDrawerInfo.current
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
 * @param drawerTokens tokens to provide appearance values. If not provided then drawer tokens will be picked from [AppThemeController]
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

        CompositionLocalProvider(
            LocalDrawerTokens provides tokens,
            LocalDrawerInfo provides DrawerInfo(type = behaviorType)
        ) {
            Popup(
                onDismissRequest = close,
                popupPositionProvider = popupPositionProvider,
                properties = PopupProperties(focusable = true)
            )
            {
                val drawerShape: Shape =
                    when (behaviorType) {
                        BehaviorType.BOTTOM -> RoundedCornerShape(
                            topStart = getDrawerTokens().borderRadius(getDrawerInfo()),
                            topEnd = getDrawerTokens().borderRadius(getDrawerInfo())
                        )
                        BehaviorType.TOP -> RoundedCornerShape(
                            bottomStart = getDrawerTokens().borderRadius(getDrawerInfo()),
                            bottomEnd = getDrawerTokens().borderRadius(getDrawerInfo())
                        )
                        else -> RoundedCornerShape(getDrawerTokens().borderRadius(getDrawerInfo()))
                    }
                val drawerElevation: Dp = getDrawerTokens().elevation(getDrawerInfo())
                val drawerBackgroundColor: Color =
                    getDrawerTokens().backgroundColor(getDrawerInfo())
                val drawerContentColor: Color = Color.Transparent
                val drawerHandleColor: Color = getDrawerTokens().handleColor(getDrawerInfo())
                val scrimOpacity: Float = getDrawerTokens().scrimOpacity(getDrawerInfo())
                val scrimColor: Color =
                    getDrawerTokens().scrimColor(getDrawerInfo()).copy(alpha = scrimOpacity)

                when (behaviorType) {
                    BehaviorType.BOTTOM -> BottomDrawer(
                        modifier = modifier,
                        drawerState = drawerState,
                        drawerShape = drawerShape,
                        drawerElevation = drawerElevation,
                        drawerBackgroundColor = drawerBackgroundColor,
                        drawerContentColor = drawerContentColor,
                        drawerHandleColor = drawerHandleColor,
                        scrimColor = scrimColor,
                        scrimVisible = scrimVisible,
                        expandable = expandable,
                        onDismiss = close,
                        drawerContent = drawerContent
                    )
                    BehaviorType.TOP -> TopDrawer(
                        modifier = modifier,
                        drawerState = drawerState,
                        drawerShape = drawerShape,
                        drawerElevation = drawerElevation,
                        drawerBackgroundColor = drawerBackgroundColor,
                        drawerContentColor = drawerContentColor,
                        drawerHandleColor = drawerHandleColor,
                        scrimColor = scrimColor,
                        scrimVisible = scrimVisible,
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
                        scrimVisible = scrimVisible,
                        onDismiss = close,
                        drawerContent = drawerContent
                    )
                }
            }
        }
    }
}