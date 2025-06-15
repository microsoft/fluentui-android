package com.microsoft.fluentui.tokenized.drawer

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.WindowInsetsCompat
import com.microsoft.fluentui.compose.ModalPopup
import com.microsoft.fluentui.compose.PreUpPostDownNestedScrollConnection
import com.microsoft.fluentui.compose.SwipeableState
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1Pressed
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1Selected
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorInfo
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorTokens
import com.microsoft.fluentui.theme.token.controlTokens.DrawerAccessibilityAnnouncement
import com.microsoft.fluentui.theme.token.controlTokens.DrawerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DrawerTokens
import com.microsoft.fluentui.theme.token.controlTokens.ListItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarTokens
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.util.dpToPx
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    var enable: Boolean by mutableStateOf(initialValue != DrawerValue.Closed)

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
            animateTo(DrawerValue.Closed, AnimationSpec)
        } catch (e: Exception) {
            animateTo(DrawerValue.Closed, AnimationSpec)
        } finally {
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
        fun Saver(
            expandable: Boolean,
            skipOpenState: Boolean,
            confirmStateChange: (DrawerValue) -> Boolean
        ) =
            Saver<DrawerState, DrawerValue>(
                save = { it.currentValue },
                restore = {
                    DrawerState(
                        initialValue = it,
                        expandable = expandable,
                        skipOpenState = skipOpenState,
                        confirmStateChange = confirmStateChange
                    )
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
            Saver(expandable = true, skipOpenState = false, confirmStateChange = confirmStateChange)

    }
}

/**
 * Create and [remember] a [DrawerState].
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Composable
fun rememberDrawerState(confirmStateChange: (DrawerValue) -> Boolean = { true }): DrawerState {
    return rememberSaveable(
        saver = DrawerState.Saver(
            expandable = true,
            skipOpenState = false,
            confirmStateChange = confirmStateChange
        )
    ) {
        DrawerState(
            initialValue = DrawerValue.Closed,
            expandable = true,
            skipOpenState = false,
            confirmStateChange
        )
    }
}

@Composable
fun rememberBottomDrawerState(
    initialValue: DrawerValue = DrawerValue.Closed,
    expandable: Boolean = true,
    skipOpenState: Boolean = false,
    confirmStateChange: (DrawerValue) -> Boolean = { true }
): DrawerState {
    return rememberSaveable(
        initialValue, confirmStateChange, expandable, skipOpenState,
        saver = DrawerState.Saver(expandable, skipOpenState, confirmStateChange)
    ) {
        DrawerState(initialValue, expandable, skipOpenState, confirmStateChange)
    }
}

private class DrawerPositionProvider(val offset: IntOffset?) : PopupPositionProvider {
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
internal fun Scrim(
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

@Composable
internal fun AnnounceDrawerActions(drawerState: DrawerState, talkbackAnnouncement: DrawerAccessibilityAnnouncement){ // Announces actions for drawer through Talkback
    val view = LocalView.current
    var previousState by remember { mutableStateOf(drawerState.enable) }

    LaunchedEffect(drawerState.enable) {
        if (drawerState.enable != previousState) {
            if (drawerState.enable) {
                view.announceForAccessibility(talkbackAnnouncement.opened)
            } else {
                view.announceForAccessibility(talkbackAnnouncement.closed)
            }
            previousState = drawerState.enable
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
    talkbackAnnouncement: DrawerAccessibilityAnnouncement = DrawerAccessibilityAnnouncement(),
    drawerTokens: DrawerTokens? = null,
    drawerContent: @Composable () -> Unit,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {}
) {
    val tokens = drawerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.DrawerControlType] as DrawerTokens
    val drawerInfo = DrawerInfo(type = behaviorType)
    AnnounceDrawerActions(drawerState, talkbackAnnouncement = talkbackAnnouncement)

    if (drawerState.enable) {
        val themeID =
            FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
        val popupPositionProvider = DrawerPositionProvider(offset)
        val scope = rememberCoroutineScope()
        val close: () -> Unit = {
            if (drawerState.confirmStateChange(DrawerValue.Closed)) {
                scope.launch { drawerState.close() }
            }
        }
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
    talkbackAnnouncement: DrawerAccessibilityAnnouncement = DrawerAccessibilityAnnouncement(),
    drawerTokens: DrawerTokens? = null,
    drawerContent: @Composable () -> Unit,
    maxLandscapeWidthFraction: Float = 1F,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {},
) {
    val behaviorType =
        if (slideOver) BehaviorType.BOTTOM_SLIDE_OVER else BehaviorType.BOTTOM
    val drawerInfo = DrawerInfo(type = behaviorType)
    val tokens = drawerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.DrawerControlType] as DrawerTokens
    AnnounceDrawerActions(drawerState, talkbackAnnouncement = talkbackAnnouncement)
    if (drawerState.enable) {
        val themeID =
            FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
        val scope = rememberCoroutineScope()
        val close: () -> Unit = {
            if (drawerState.confirmStateChange(DrawerValue.Closed)) {
                scope.launch { drawerState.close() }
            }
        }
        BackHandler { //TODO: Add pull down animation with predictive back
            close()
        }
        ModalPopup(
            windowInsetsType = windowInsetsType,
            onDismissRequest = close,
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

class SearchableDrawerTokens{
    @Composable
    fun topRowTextColours(): List<Brush> {
        return listOf(
            Brush.linearGradient(
                colors = listOf(Color(0xFFFAFAFA), Color(0xFFF5F5F5)),
            )
        )
    }

    @Composable
    fun drawerTokens(){

    }
}
@Composable
fun TopOptionsRow(
    tokens: SearchableDrawerTokens = SearchableDrawerTokens(),
    onLeftTextClick: () -> Unit = {},
    onCenterTextClick: () -> Unit = {},
    onRightTextClick: () -> Unit = {},
){
    val textColours = tokens.topRowTextColours()
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val interactionSourceStart = remember { MutableInteractionSource() }
        val animatedFontSizeStart by animateDpAsState(
            targetValue = if (interactionSourceStart.collectIsPressedAsState().value) 18.5.dp else 17.dp,
            label = "FontSizeAnimation"
        )
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            BasicText(
                text = "Clear",
                modifier = Modifier.fillMaxWidth().clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = interactionSourceStart
                ) {
                    onLeftTextClick()
                },
                style = TextStyle(
                    color = Color(0xFF616161),
                    fontSize = animatedFontSizeStart.value.sp,
                    lineHeight = 22.sp,
                    letterSpacing = -0.43.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight(400)
                )
            )
        }
        val interactionSourceCenter = remember { MutableInteractionSource() }
        val animatedFontSizeCenter by animateDpAsState(
            targetValue = if (interactionSourceCenter.collectIsPressedAsState().value) 18.5.dp else 17.dp,
            label = "FontSizeAnimation"
        )
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            BasicText(
                text = "Person",
                modifier = Modifier.fillMaxWidth().clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = interactionSourceCenter
                ) {
                    //close()
                },
                style = TextStyle(
                    color = Color(0xFF242424),
                    fontSize = animatedFontSizeCenter.value.sp,
                    lineHeight = 22.sp,
                    letterSpacing = -0.43.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(600)
                )
            )
        }
        val interactionSourceEnd = remember { MutableInteractionSource() }
        val animatedFontSizeEnd by animateDpAsState(
            targetValue = if (interactionSourceEnd.collectIsPressedAsState().value) 18.5.dp else 17.dp,
            label = "FontSizeAnimation"
        )
        Box(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            BasicText(
                text = "Apply",
                modifier = Modifier.fillMaxWidth().clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = interactionSourceEnd
                ) {
                    // close()
                },
                style = TextStyle(
                    color = Color(0xFF464FEB),
                    fontSize = animatedFontSizeEnd.value.sp,
                    lineHeight = 22.sp,
                    letterSpacing = -0.43.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight(400)
                )
            )
        }
    }
}

@Composable
fun KeyboardDetection(
    onKeyboardVisible: () -> Unit = {},
    onKeyboardHidden: () -> Unit = {}
) {
    val imeInsets = WindowInsets.ime
    val density = LocalDensity.current
    val isKeyboardVisible by remember {
        derivedStateOf {
            imeInsets.getBottom(density) > 0
        }
    }

    LaunchedEffect(isKeyboardVisible) {
        if (isKeyboardVisible) {
            onKeyboardVisible()
        } else {
            onKeyboardHidden()
        }
    }
}

/**
 * Create an Indeterminate Circular Progress indicator
 *
 * @param size Optional size of the circular progress indicator
 * @param modifier Modifier for circular progress indicator
 * @param style Style of progress indicator. Default: [FluentStyle.Neutral]
 * @param circularProgressIndicatorTokens Token values for circular progress indicator
 *
 */
@Composable
fun CircularProgressIndicator(
    size: CircularProgressIndicatorSize = CircularProgressIndicatorSize.XXSmall,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    circularProgressIndicatorTokens: CircularProgressIndicatorTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val tokens = circularProgressIndicatorTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.CircularProgressIndicatorControlType] as CircularProgressIndicatorTokens
    val circularProgressIndicatorInfo = CircularProgressIndicatorInfo(
        circularProgressIndicatorSize = size,
        style = style
    )
    val circularProgressIndicatorColor =
        tokens.brush(
            circularProgressIndicatorInfo
        )
    val circularProgressIndicatorSize =
        tokens.size(
            circularProgressIndicatorInfo
        )
    val circularProgressIndicatorStrokeWidth =
        tokens.strokeWidth(
            circularProgressIndicatorInfo
        )
    val infiniteTransition = rememberInfiniteTransition()
    val startAngle by infiniteTransition.animateFloat(
        0f,
        360f,
        infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )
    val indicatorSizeInPx = dpToPx(circularProgressIndicatorSize)
    Canvas(
        modifier = modifier
            .requiredSize(circularProgressIndicatorSize)
            .progressSemantics()
    ) {
        drawArc(
            circularProgressIndicatorColor,
            startAngle,
            270f,
            false,
            size = Size(
                indicatorSizeInPx, indicatorSizeInPx
            ),
            style = Stroke(dpToPx(circularProgressIndicatorStrokeWidth), cap = StrokeCap.Round)
        )
    }
}

class SearchItem(
    val title: String,
    val subTitle: String? = null,
    val footer: String? = null,
    val leftAccessory: @Composable (() -> Unit)? = null,
    val rightAccessory: @Composable (() -> Unit)? = null,
    val status: AvatarStatus? = null,
    val onClick: () -> Unit = {},
    val onLongClick: () -> Unit = {},
    val enabled: Boolean = true,
){}

@Composable
fun SearchBar(
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: FluentStyle = FluentStyle.Neutral,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    searchHint: String = LocalContext.current.resources.getString(R.string.fluentui_search),
    focusByDefault: Boolean = false,
    loading: Boolean = false,
    microphoneCallback: (() -> Unit)? = null,
    navigationIconCallback: (() -> Unit)? = null,
    leftAccessoryIcon: ImageVector? = Icons.Outlined.Search,
    rightAccessoryIcon: FluentIcon? = null,
    searchBarTokens: SearchBarTokens? = null
) {
    val themeID = FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = searchBarTokens ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.SearchBarControlType] as SearchBarTokens
    val searchBarInfo = SearchBarInfo(style)
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var queryText by rememberSaveable { mutableStateOf("") }
    var searchHasFocus by rememberSaveable { mutableStateOf(false) }

    val borderWidth = token.borderWidth(searchBarInfo)
    val elevation = token.elevation(searchBarInfo)
    val height = token.height(searchBarInfo)

    val borderModifier = if (borderWidth > 0.dp) {
        Modifier.border(
            width = borderWidth,
            color = token.borderColor(searchBarInfo),
            shape = RoundedCornerShape(token.cornerRadius(searchBarInfo))
        )
    } else Modifier
    val shadowModifier = if (elevation > 0.dp) Modifier.shadow(
        elevation = token.elevation(searchBarInfo),
        shape = RoundedCornerShape(token.cornerRadius(searchBarInfo)),
        spotColor = token.shadowColor(searchBarInfo)
    ) else Modifier
    Row(
        modifier = modifier
            .background(token.backgroundBrush(searchBarInfo))
            .padding(token.searchBarPadding(searchBarInfo))
    ) {
        Row(
            Modifier
                .requiredHeightIn(min = height)
                .then(borderModifier)
                .then(shadowModifier)
                .fillMaxWidth()
                .clip(RoundedCornerShape(token.cornerRadius(searchBarInfo)))
                .background(
                    token.inputBackgroundBrush(searchBarInfo),
                    RoundedCornerShape(token.cornerRadius(searchBarInfo))
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(searchHasFocus) {
                var onClick: (() -> Unit)? = null
                var icon: ImageVector? = null
                var contentDescription: String? = null
                var mirrorImage = false
                when (it) {
                    true -> {
                        onClick = {
                            queryText = ""
                            onValueChange(queryText)
                            focusManager.clearFocus()
                            searchHasFocus = false
                            navigationIconCallback?.invoke()
                        }
                        icon = Icons.Outlined.ArrowBack
                        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_back)
                        if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                            mirrorImage = true
                    }
                    false -> {
                        onClick = {
                            focusRequester.requestFocus()
                        }
                        icon = leftAccessoryIcon ?: Icons.Outlined.Search
                        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_search)
                        mirrorImage = false
                    }
                }
                Icon(
                    icon,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .padding(
                            (44.dp - token.leftIconSize(searchBarInfo)) / 2,
                            (40.dp - token.leftIconSize(searchBarInfo)) / 2
                        )
                        .size(token.leftIconSize(searchBarInfo)),
                    tint = token.leftIconColor(searchBarInfo),
                    flipOnRtl = mirrorImage,
                    enabled = enabled,
                    onClick = onClick
                )
            }
            Row(
                modifier = Modifier
                    .height(24.dp)
                    .weight(1F)
                    .onKeyEvent {
                        false
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = queryText,
                    onValueChange = {
                        queryText = it
                        onValueChange(queryText)
                    },
                    singleLine = true,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    modifier = Modifier
                        .weight(1F)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            when {
                                focusState.isFocused ->
                                    searchHasFocus = true
                            }
                        }
                        .padding(horizontal = 8.dp)
                        .semantics { contentDescription = searchHint },
                    textStyle = token.typography(searchBarInfo).merge(
                        TextStyle(
                            color = token.textColor(searchBarInfo),
                            textDirection = TextDirection.ContentOrLtr
                        )
                    ),
                    decorationBox = @Composable { innerTextField ->
                        Box(
                            Modifier.fillMaxWidth(),
                            contentAlignment = if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                Alignment.CenterEnd
                            else
                                Alignment.CenterStart
                        ) {
                            if (queryText.isEmpty()) {
                                BasicText(
                                    searchHint,
                                    style = token.typography(searchBarInfo)
                                        .merge(TextStyle(color = token.textColor(searchBarInfo)))
                                )
                            }
                        }
                        innerTextField()
                    },
                    cursorBrush = token.cursorColor(searchBarInfo)
                )
            }
            LaunchedEffect(Unit) {
                if (focusByDefault)
                    focusRequester.requestFocus()
            }
            AnimatedContent((queryText.isBlank())) {
                when (it) {
                    true ->
                        if (microphoneCallback != null) {
                            Icon(
                                Icons.Outlined.Star,
                                contentDescription = LocalContext.current.resources.getString( R.string.fluentui_microphone),
                                modifier = Modifier
                                    .padding(
                                        (44.dp - token.rightIconSize(searchBarInfo)) / 2,
                                        (40.dp - token.rightIconSize(searchBarInfo)) / 2
                                    )
                                    .size(
                                        token.rightIconSize(
                                            searchBarInfo
                                        )
                                    ),
                                tint = token.rightIconColor(searchBarInfo),
                                onClick = microphoneCallback
                            )
                        }
                    false ->
                        Box(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    enabled = enabled,
                                    onClick = {
                                        queryText = ""
                                        onValueChange(queryText)
                                    },
                                    role = Role.Button
                                )
                                .size(44.dp, 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (loading) {
                                CircularProgressIndicator(
                                    size = token.circularProgressIndicatorSize(
                                        searchBarInfo
                                    )
                                )
                            }
                            Icon(
                                Icons.Outlined.Warning,
                                contentDescription = LocalContext.current.resources.getString(R.string.fluentui_clear_text),
                                modifier = Modifier
                                    .size(token.rightIconSize(searchBarInfo)),
                                tint = token.rightIconColor(searchBarInfo)
                            )
                        }
                }
            }
            if (rightAccessoryIcon?.isIconAvailable() == true && rightAccessoryIcon.onClick != null) {
                Row(
                    modifier = Modifier
                        .size(44.dp, 40.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            enabled = enabled,
                            onClick = rightAccessoryIcon.onClick!!,
                            role = Role.Button
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        rightAccessoryIcon.value(),
                        contentDescription = rightAccessoryIcon.contentDescription,
                        modifier = Modifier
                            .size(token.rightIconSize(searchBarInfo)),
                        tint = token.rightIconColor(searchBarInfo)
                    )
                    Icon(
                        Icons.Outlined.AccountBox,
                        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_chevron),
                        Modifier.rotate(90F),
                        tint = token.rightIconColor(searchBarInfo)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomDrawerSearchableList(
    onTitleClick: () -> Unit = {},
    onLeftAccesoryClick: () -> Unit = {},
    onRightAccesoryClick: () -> Unit = {},
    open: () -> Unit = {},
    expand: () -> Unit = {},
    close: () -> Unit = {},
    autoCorrectEnabled : Boolean = false,
    searchBarStyle: FluentStyle = FluentStyle.Neutral,
    induceDelay: Boolean = false,
    itemsList: List<SearchItem> = emptyList(),
    searchBarTokens: SearchBarTokens = SearchBarTokens(),
    searchBarHintText: String = LocalContext.current.resources.getString(R.string.fluentui_search)
) {
    val scope = rememberCoroutineScope()
    var loading by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var isSelected by remember { mutableStateOf(false) }
    val filteredSearchItems = mutableListOf<SearchItem>()
    val selectedSearchItems = remember { mutableStateListOf<SearchItem>() }
    val inSearchView by remember { derivedStateOf { selectedSearchItems.isEmpty() } }
    KeyboardDetection(
        onKeyboardVisible = expand,
        onKeyboardHidden = open
    )
    var filteredItems by rememberSaveable { mutableStateOf(itemsList.toMutableList()) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopOptionsRow()

        if(inSearchView) {
            SearchBar(
                onValueChange = { query ->
                    scope.launch {
                        loading = true
                        if (induceDelay)
                            delay(2000)
                        filteredItems = itemsList.filter {
                            it.title.lowercase().contains(query.lowercase())
                        } as MutableList<SearchItem>
                        loading = false
                    }
                },
                style = searchBarStyle,
                loading = loading,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = autoCorrectEnabled,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                    }
                ),
                searchBarTokens = searchBarTokens,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                searchHint = searchBarHintText
            )
        }
        else{
            BasicText("Selected Items: ${selectedSearchItems.size}",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                style = TextStyle(
                    color = Color(0xFF242424),
                    fontSize = 17.sp,
                    lineHeight = 22.sp,
                    letterSpacing = -0.43.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight(400)
                )
            )
        }
        filteredItems.forEach {
            filteredSearchItems.add(
                SearchItem(
                    it.title,
                    leftAccessory = it.leftAccessory,
                    rightAccessory = it.rightAccessory,
                    status = it.status
                )
            )
        }
        AllItemsList(
            searchItems = filteredSearchItems,
            selectedSearchItems = selectedSearchItems,
            border = BorderType.NoBorder
        )
    }
}

@Composable
fun AllItemsList(
    modifier: Modifier = Modifier,
    searchItems: List<SearchItem>,
    selectedSearchItems: MutableList<SearchItem> = mutableListOf(),
    border: BorderType = BorderType.NoBorder,
){
    val scope = rememberCoroutineScope()
    var enableStatus by rememberSaveable { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val positionString: String = LocalContext.current.resources.getString(R.string.position_string)
    val statusString: String = LocalContext.current.resources.getString(R.string.status_string)
    LazyColumn(
        state = lazyListState, modifier = modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scope.launch {
                    lazyListState.scrollBy(-delta)
                }
            },
        )
    ) {
        itemsIndexed(searchItems) { index, item ->
            var isSelected by remember { mutableStateOf(selectedSearchItems.contains(item))}
            val listItemTokens: ListItemTokens = object: ListItemTokens(){
                @Composable
                override fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
                    return StateBrush(
                        rest =
                        if(!isSelected) {
                            SolidColor(
                                FluentTheme.aliasTokens.neutralBackgroundColor[Background1].value(
                                    themeMode = FluentTheme.themeMode
                                )
                            )
                        }
                        else{
                            SolidColor(
                                FluentTheme.aliasTokens.neutralBackgroundColor[Background1Selected].value(
                                    themeMode = FluentTheme.themeMode
                                )
                            )
                        },
                        pressed = SolidColor(
                            FluentTheme.aliasTokens.neutralBackgroundColor[Background1Pressed].value(
                                themeMode = FluentTheme.themeMode
                            )
                        )
                    )
                }

                @Composable
                override fun primaryTextTypography(listItemInfo: ListItemInfo): TextStyle {
                    return TextStyle(
                        color = Color(0xFF242424),
                        fontSize = 17.sp,
                        lineHeight = 22.sp,
                        letterSpacing = -0.43.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight(400)
                    )
                }
            }
            ListItem.Item(
                text = item.title,
                modifier = Modifier
                    .clearAndSetSemantics {
                        contentDescription = "${item.title}, ${item.subTitle}" + if(enableStatus) statusString.format( item.status )else ""
                        stateDescription = if (searchItems.size > 1) positionString.format(index+1, searchItems.size ) else ""
                        role = Role.Button
                    },
                subText = item.subTitle,
                secondarySubText = item.footer,
                onClick = {item.onClick
                          if(!selectedSearchItems.isEmpty()){
                                if(isSelected){ selectedSearchItems.remove(item)
                                isSelected = false }
                                else { selectedSearchItems.add(item)
                                isSelected = true }}},
                onLongClick = {
                    println("###  "+ isSelected)
                    item.onLongClick()
                    if(isSelected){ selectedSearchItems.remove(item)
                        isSelected = false }
                    else { selectedSearchItems.add(item)
                        isSelected = true }
                              println("###  "+ selectedSearchItems)},
                border = border,
                //borderInset = borderInset,
                listItemTokens = listItemTokens,
                enabled = item.enabled,
                leadingAccessoryContent = item.leftAccessory,
                trailingAccessoryContent = item.rightAccessory,
                //textAccessibilityProperties = textAccessibilityProperties,
            )
        }
    }
}