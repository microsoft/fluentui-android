package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.foundation.Canvas
import com.microsoft.fluentui.compose.AnchoredDraggableState
import com.microsoft.fluentui.compose.DraggableAnchors
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import com.microsoft.fluentui.compose.animateTo
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.WindowInsetsCompat
import com.microsoft.fluentui.compose.ModalPopup
import com.microsoft.fluentui.compose.PreUpPostDownNestedScrollConnection
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.DrawerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DrawerTokens
import com.microsoft.fluentui.tokenized.drawer.DraggableDefaults.AnimationSpec
import com.microsoft.fluentui.tokenized.drawer.DraggableDefaults.PositionalThreshold
import com.microsoft.fluentui.tokenized.drawer.DraggableDefaults.VelocityThreshold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max

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
 * @param confirmValueChange Optional callback invoked to confirm or veto a pending state change.
 */
class DrawerState(
    private val initialValue: DrawerValue = DrawerValue.Closed,
    internal val confirmValueChange: (DrawerValue) -> Boolean = { true },
    internal val expandable: Boolean = true,
    internal val skipOpenState: Boolean = false,
) {
    internal var velocityThreshold: () -> Float = { VelocityThreshold }
    internal var positionalThreshold: (Float) -> Float = { PositionalThreshold }

    internal val anchoredDraggableState: AnchoredDraggableState<DrawerValue> = AnchoredDraggableState(
        initialValue,
        anchors = DraggableAnchors {},
        positionalThreshold,
        velocityThreshold,
        animationSpec = AnimationSpec,
        confirmValueChange = confirmValueChange
    )

    init {
        if (skipOpenState) {
            require(anchoredDraggableState.currentValue != DrawerValue.Open) {
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

    /**
     * Whether drawer is enabled on initialization
     * It is false if drawer should not initially be opened.
     */
    var enable: Boolean by mutableStateOf(initialValue != DrawerValue.Closed)

    /**
     * Whether drawer has Open state.
     * It is false in case of skipOpenState is true.
     */
    internal val hasOpenedState: Boolean
        get() = !skipOpenState && anchoredDraggableState.anchors.hasAnchorFor(DrawerValue.Open)

    /**
     * Whether the drawer is closed.
     */
    val isClosed: Boolean
        get() = anchoredDraggableState.currentValue == DrawerValue.Closed

    /**
     * Whether drawer has expanded state.
     */
    internal val hasExpandedState: Boolean
        get() = expandable && anchoredDraggableState.anchors.hasAnchorFor(DrawerValue.Expanded)


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
        } while (!anchoredDraggableState.anchorsFilled)
        /*
       * first try to open the drawer
       * if not possible then try to expand the drawer
        */
        val targetValue = when {
            hasOpenedState -> DrawerValue.Open
            hasExpandedState -> DrawerValue.Expanded
            else -> DrawerValue.Closed
        }
        if (targetValue != anchoredDraggableState.currentValue) {
            try {
                anchoredDraggableState.animateTo(targetValue, velocityThreshold())
            } catch(e: Exception) {
                anchoredDraggableState.animateTo(targetValue = targetValue, VelocityThreshold)
            }
            finally {
                    animationInProgress = false
            }
        } else {
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
            anchoredDraggableState.animateTo(DrawerValue.Closed, VelocityThreshold)
        } catch (e: Exception) {
            anchoredDraggableState.animateTo(DrawerValue.Closed, VelocityThreshold)
        } finally {
            animationInProgress = false
            enable = false
            anchoredDraggableState.updateAnchors(DraggableAnchors { })
            anchoredDraggableState.anchorsFilled = false
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
        } while (!anchoredDraggableState.anchorsFilled)
        /*
        * first try to expand the drawer
        * if not possible then try to open the drawer
         */
        val targetValue = when {
            hasExpandedState -> DrawerValue.Expanded
            hasOpenedState -> DrawerValue.Open
            else -> DrawerValue.Closed
        }
        if (targetValue != anchoredDraggableState.currentValue) {
            try {
                anchoredDraggableState.animateTo(targetValue = targetValue, VelocityThreshold)
            } catch (e: Exception) {
                anchoredDraggableState.animateTo(targetValue = targetValue, VelocityThreshold)
            } finally {
                animationInProgress = false
            }
        } else {
            animationInProgress = false
        }
    }

    val nestedScrollConnection = this.anchoredDraggableState.PreUpPostDownNestedScrollConnection

    companion object {
        /**
         * The default [Saver] implementation for [DrawerState].
         */
        fun Saver(
            expandable: Boolean,
            skipOpenState: Boolean,
            confirmValueChange: (DrawerValue) -> Boolean
        ) =
            Saver<DrawerState, DrawerValue>(
                save = { it.anchoredDraggableState.currentValue },
                restore = {
                    DrawerState(
                        initialValue = it,
                        expandable = expandable,
                        skipOpenState = skipOpenState,
                        confirmValueChange = confirmValueChange
                    )
                }
            )
    }
}

/**
 * Create and [remember] a [DrawerState].
 * @param confirmValueChange Optional callback invoked to confirm or veto a pending state change.
 */
@Composable
fun rememberDrawerState(confirmValueChange: (DrawerValue) -> Boolean = { true }): DrawerState {
    return rememberSaveable(
        saver = DrawerState.Saver(
            expandable = true,
            skipOpenState = false,
            confirmValueChange = confirmValueChange
        )
    ) {
        DrawerState(
            initialValue = DrawerValue.Closed,
            expandable = true,
            skipOpenState = false,
            confirmValueChange = confirmValueChange
        )
    }
}

@Composable
fun rememberBottomDrawerState(
    initialValue: DrawerValue = DrawerValue.Closed,
    expandable: Boolean = true,
    skipOpenState: Boolean = false,
    confirmValueChange: (DrawerValue) -> Boolean = { true }
): DrawerState {
    return rememberSaveable(
        initialValue, confirmValueChange, expandable, skipOpenState,
        saver = DrawerState.Saver(expandable, skipOpenState, confirmValueChange)
    ) {
        DrawerState(initialValue, confirmValueChange, expandable, skipOpenState)
    }
}

class DrawerPositionProvider(val offset: IntOffset?) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        if (offset != null) {
            return IntOffset(anchorBounds.left + offset.x, anchorBounds.top + offset.y)
        }
        return IntOffset(0, 0)
    }
}

@Composable
fun Scrim(
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
                if (!preventDismissalOnScrimClick) {
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

/**
 *
 * Drawer block interaction with the rest of an app’s content with a scrim.
 * They are elevated above most of the app’s UI and don’t affect the screen’s layout grid.
 *
 * @param modifier optional modifier for the drawer
 * @param behaviorType opening behaviour of drawer. Default is BOTTOM
 * @param drawerState state of the drawer
 * @param scrimVisible create obscures background when scrim visible set to true when the drawer is open. The default value is true
 * @param offset offset of the drawer from the anchor. The default value is (0,0).
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
    offset: IntOffset? = null,
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

        val popupPositionProvider = DrawerPositionProvider(offset)
        val scope = rememberCoroutineScope()
        val close: () -> Unit = {
            if (drawerState.confirmValueChange(DrawerValue.Closed)) {
                scope.launch { drawerState.close() }
            }
        }
        val drawerInfo = DrawerInfo(type = behaviorType)
        Popup(
            onDismissRequest = close,
            popupPositionProvider = popupPositionProvider,
            properties = PopupProperties(focusable = true, clippingEnabled = (offset == null))
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
 * @param maxLandscapeWidthFraction max width of bottomDrawer wrt to screen width in landscape mode. The default value is 1F
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
    enableSwipeDismiss: Boolean = true,
    windowInsetsType: Int = WindowInsetsCompat.Type.systemBars(),
    drawerTokens: DrawerTokens? = null,
    drawerContent: @Composable () -> Unit,
    maxLandscapeWidthFraction: Float = 1F,
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
            if (drawerState.confirmValueChange(DrawerValue.Closed)) {
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
                enableSwipeDismiss = enableSwipeDismiss,
                onDismiss = close,
                drawerContent = drawerContent,
                preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                maxLandscapeWidthFraction = maxLandscapeWidthFraction,
                onScrimClick = onScrimClick
            )
        }
    }
}
