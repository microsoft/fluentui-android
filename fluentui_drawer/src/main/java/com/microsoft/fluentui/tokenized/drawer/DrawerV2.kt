package com.microsoft.fluentui.tokenized.drawer

import com.microsoft.fluentui.compose.AnchoredDraggableState
import com.microsoft.fluentui.compose.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import com.microsoft.fluentui.compose.anchoredDraggable
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.WindowInsetsCompat
import com.microsoft.fluentui.compose.ModalPopup
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.DrawerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DrawerTokens
import com.microsoft.fluentui.tokenized.drawer.DraggableDefaults.AnimationSpec
import com.microsoft.fluentui.tokenized.drawer.DraggableDefaults.PositionalThreshold
import com.microsoft.fluentui.tokenized.drawer.DraggableDefaults.VelocityThreshold
import com.microsoft.fluentui.tokenized.drawer.DrawerStateV2.Companion.Saver
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max

class DrawerStateV2(
    private val initialValue: DrawerValue = DrawerValue.Closed,
    internal val confirmValueChange: (DrawerValue) -> Boolean = { true },
    internal val expandable: Boolean = true,
    internal val skipOpenState: Boolean = false
) {
    internal val anchoredDraggableState: AnchoredDraggableState<DrawerValue> = AnchoredDraggableState(
        initialValue,
        anchors = DraggableAnchors {},
        positionalThreshold = { PositionalThreshold },
        velocityThreshold = { VelocityThreshold },
        animationSpec = AnimationSpec,
        confirmValueChange = confirmValueChange
    )

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
        get() = anchoredDraggableState.anchors.hasAnchorFor(DrawerValue.Open)

    /**
     * Whether the drawer is closed.
     */
    val isClosed: Boolean
        get() = anchoredDraggableState.currentValue == DrawerValue.Closed

    /**
     * Whether drawer has expanded state.
     */
    internal val hasExpandedState: Boolean
        get() = anchoredDraggableState.anchors.hasAnchorFor(DrawerValue.Expanded)


    var animationInProgress: Boolean = false

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
            anchoredDraggableState.animateTo(targetValue, VelocityThreshold)
            animationInProgress = false
        } else {
            animationInProgress = false
        }
    }

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
        }
    }

    suspend fun expand() {
        enable = true
        animationInProgress = true
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
            anchoredDraggableState.animateTo(targetValue = targetValue, VelocityThreshold)
            animationInProgress = false
        } else {
            animationInProgress = false
        }
    }

    companion object {
        /**
         * The default [Saver] implementation for [DrawerStateV2].
         */
        fun Saver(
            expandable: Boolean,
            skipOpenState: Boolean,
            confirmValueChange: (DrawerValue) -> Boolean
        ) =
            Saver<DrawerStateV2, DrawerValue>(
                save = { it.anchoredDraggableState.currentValue },
                restore = {
                    DrawerStateV2(
                        initialValue = it,
                        expandable = expandable,
                        skipOpenState = skipOpenState,
                        confirmValueChange = confirmValueChange
                    )
                }
            )
    }
}

@Composable
fun rememberDrawerStateV2(confirmValueChange: (DrawerValue) -> Boolean = { true }): DrawerStateV2 {
    return rememberSaveable(
        saver = Saver(
            expandable = true,
            skipOpenState = false,
            confirmValueChange = confirmValueChange
        )
    ) {
        DrawerStateV2(
            initialValue = DrawerValue.Closed,
            expandable = true,
            skipOpenState = false,
            confirmValueChange = confirmValueChange
        )
    }
}

@Composable
fun rememberBottomDrawerStateV2(
    expandable: Boolean = true,
    skipOpenState: Boolean = false,
    confirmValueChange: (DrawerValue) -> Boolean = { true }
): DrawerStateV2 {
    return rememberSaveable(
        confirmValueChange, expandable, skipOpenState,
        saver = Saver(expandable, skipOpenState, confirmValueChange)
    ) {
        DrawerStateV2(DrawerValue.Closed, confirmValueChange, expandable, skipOpenState)
    }
}

fun Modifier.bottomDrawerAnchoredDraggable(
    drawerState: DrawerStateV2,
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
            val drawerStateAnchors = drawerState.anchoredDraggableState.anchors
            val anchors: DraggableAnchors<DrawerValue> =
                if (drawerHeight <= maxOpenHeight) {  // when contentHeight is less than maxOpenHeight
                    if (drawerStateAnchors.hasAnchorFor(DrawerValue.Expanded)) {
                        /*
                        *For dynamic content when drawerHeight was previously greater than maxOpenHeight and now less than maxOpenHEight
                        *The old anchors won't have Open state, so we need to continue with Expanded state.
                        */
                        DraggableAnchors {
                             DrawerValue.Expanded at bottomOpenStateY
                             DrawerValue.Closed at fullHeight
                        }
                    } else {
                        DraggableAnchors {
                             DrawerValue.Open at bottomOpenStateY
                             DrawerValue.Closed at fullHeight
                        }
                    }
                } else {
                    if (drawerState.expandable) {
                        if (drawerState.skipOpenState) {
                            if (drawerStateAnchors.hasAnchorFor(DrawerValue.Open)) {
                                /*
                                *For dynamic content when drawerHeight was previously less than maxOpenHeight and now greater than maxOpenHEight
                                *The old anchors won't have Expanded state, so we need to continue with Open state.
                                */
                                DraggableAnchors {
                                    DrawerValue.Open at bottomExpandedStateY // when drawerHeight is greater than maxOpenHeight but less than fullHeight, then Expanded state starts from fullHeight-drawerHeight
                                    DrawerValue.Closed at fullHeight
                                }
                            } else {
                                DraggableAnchors {
                                     DrawerValue.Expanded at bottomExpandedStateY // when drawerHeight is greater than maxOpenHeight but less than fullHeight, then Expanded state starts from fullHeight-drawerHeight
                                     DrawerValue.Closed at fullHeight
                                }
                            }
                        } else {
                            DraggableAnchors {
                                 DrawerValue.Open at maxOpenHeight
                                 DrawerValue.Expanded at bottomExpandedStateY
                                 DrawerValue.Closed at fullHeight
                            }
                        }
                    } else {
                        DraggableAnchors {
                             DrawerValue.Open at maxOpenHeight
                             DrawerValue.Closed at fullHeight
                        }
                    }
                }
            drawerState.anchoredDraggableState.updateAnchors(anchors)
            Modifier.anchoredDraggable(
                state = drawerState.anchoredDraggableState,
                orientation = Orientation.Vertical,
                enabled = false
            )
        } else {
            Modifier
        }
    } else {
        val anchors: DraggableAnchors<DrawerValue> = if (drawerState.expandable) {
            if (drawerState.skipOpenState) {
                DraggableAnchors {
                    DrawerValue.Expanded at 0f
                    DrawerValue.Closed at fullHeight
                }
            } else {
                DraggableAnchors {
                    DrawerValue.Open at maxOpenHeight
                    DrawerValue.Expanded at 0F
                    DrawerValue.Closed at fullHeight
                }
            }
        } else {
            DraggableAnchors {
                DrawerValue.Open at maxOpenHeight
                DrawerValue.Closed at fullHeight
            }
        }
        drawerState.anchoredDraggableState.updateAnchors(anchors)
        Modifier.anchoredDraggable(
            state = drawerState.anchoredDraggableState,
            orientation = Orientation.Vertical,
            enabled = false
        )
    }
    return this.then(modifier)
}


@Composable
fun DrawerV2(
    modifier: Modifier = Modifier,
    behaviorType: BehaviorType = BehaviorType.BOTTOM,
    drawerState: DrawerStateV2 = rememberDrawerStateV2(),
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
                BehaviorType.BOTTOM, BehaviorType.BOTTOM_SLIDE_OVER -> BottomDrawerV2(
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

                BehaviorType.TOP -> TopDrawerV2(
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

                BehaviorType.LEFT_SLIDE_OVER, BehaviorType.RIGHT_SLIDE_OVER -> HorizontalDrawerV2(
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

@Composable
fun BottomDrawerV2(
    modifier: Modifier = Modifier,
    drawerState: DrawerStateV2 = rememberDrawerStateV2(),
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
            BottomDrawerV2(
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
