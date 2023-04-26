package com.microsoft.fluentui.tokenized.menu

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.MenuInfo
import com.microsoft.fluentui.theme.token.controlTokens.MenuTokens

/**
 * Menu behaves similarly to a Popup, and will use the position of the parent layout to position
 * itself on screen. Commonly a Menu will be placed in a Box with a sibling that will be used as
 * the 'anchor'. Note that a Menu by itself will not take up any space in a layout, as the menu
 * is displayed in a separate window, on top of other content.
 *
 * onDismissRequest will be called when the menu should close - for example when there is a tap
 * outside the menu, or when the back key is pressed
 *
 * Menu changes its positioning depending on the available space, always trying to be fully visible.
 * It will try to expand horizontally, depending on layout direction, to the end of its parent,
 * then to the start of its parent, and then screen end-aligned. When it is screen end-aligned,
 * sideMargin values applied to have gap from screen edge. Vertically, it will try to expand
 * to the bottom of its parent, then from the top of its parent, then covering parent at center,
 * and then screen top-aligned.
 * An offset can be provided to adjust the positioning of the menu for cases when the layout bounds
 * of its parent do not coincide with its visual bounds. Note the offset will be applied in the
 * direction in which the menu will decide to expand.
 *
 * @param opened Whether the menu is currently open and visible to the user
 * @param onDismissRequest Called when the user requests to dismiss the menu, such as by
 * tapping outside the menu's bounds
 * @param modifier Optional modifier for Menu
 * @param offset [DpOffset] to be added to the position of the menu
 * @param menuTokens tokens to provide appearance values. If not provided then menu token will be
 * picked from [FluentTheme.controlTokens]
 * @param content composable that represents content inside the Menu
 * */
@Composable
fun Menu(
    opened: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    menuTokens: MenuTokens? = null,
    content: @Composable () -> Unit
) {
    val openedStates = remember { MutableTransitionState(false) }
    openedStates.targetState = opened

    if (openedStates.currentState || openedStates.targetState) {
        val token =
            menuTokens
                ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Menu] as MenuTokens

        val menuInfo = MenuInfo()

        val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }
        val applyWidthModifier = remember { mutableStateOf(false) }
        val restrictWidth = remember { mutableStateOf(false) }
        val density = LocalDensity.current
        val screenWidth = with(density) { LocalConfiguration.current.screenWidthDp.dp.roundToPx() }
        val popupPositionProvider = MenuPositionProvider(
            offset,
            density,
            token.bottomMargin(menuInfo),
            token.sideMargin(menuInfo)
        ) { parentBounds, menuBounds ->
            transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
            restrictWidth.value = menuBounds.size.width >= screenWidth * 0.75f
        }

        Popup(
            onDismissRequest = onDismissRequest,
            popupPositionProvider = popupPositionProvider,
            properties = properties
        ) {
            MenuContent(
                expandedStates = openedStates,
                transformOriginState = transformOriginState,
                modifier = modifier.then(
                    if (applyWidthModifier.value) Modifier
                        .fillMaxWidth(0.75f)
                    else Modifier
                ),
                content = content,
                elevation = token.elevation(menuInfo),
                cornerRadius = token.cornerRadius(menuInfo),
                color = token.backgroundColor(menuInfo)
            )
        }

        LaunchedEffect(Unit) {
            applyWidthModifier.value = restrictWidth.value
        }
    }
}

internal const val InTransitionDuration = 120
internal const val OutTransitionDuration = 75
internal val MenuVerticalPadding = 8.dp

@Composable
internal fun MenuContent(
    expandedStates: MutableTransitionState<Boolean>,
    transformOriginState: MutableState<TransformOrigin>,
    modifier: Modifier = Modifier,
    elevation: Dp,
    color: Color,
    cornerRadius: Dp,
    content: @Composable () -> Unit
) {
    // Menu open/close animation.
    val transition = updateTransition(expandedStates, "Menu")

    val scale by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // Dismissed to expanded
                tween(
                    durationMillis = InTransitionDuration,
                    easing = LinearOutSlowInEasing
                )
            } else {
                // Expanded to dismissed.
                tween(
                    durationMillis = 1,
                    delayMillis = OutTransitionDuration - 1
                )
            }
        }, label = "MenuScale"
    ) {
        if (it) {
            // Menu is expanded.
            1f
        } else {
            // Menu is dismissed.
            0.8f
        }
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // Dismissed to expanded
                tween(durationMillis = 30)
            } else {
                // Expanded to dismissed.
                tween(durationMillis = OutTransitionDuration)
            }
        }, label = "MenuAlpha"
    ) {
        if (it) {
            // Menu is expanded.
            1f
        } else {
            // Menu is dismissed.
            0f
        }
    }
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
                transformOrigin = transformOriginState.value
            }
            .shadow(elevation, shape, clip = false)
            .background(color = color, shape = shape)
            .clip(shape)
            .semantics(mergeDescendants = false) {}
            .pointerInput(Unit) {}
            .padding(vertical = MenuVerticalPadding)
            .width(IntrinsicSize.Max)
    ) {
        content()
    }
}

/**
 * Calculates the position of a [Menu].
 */
@Immutable
internal data class MenuPositionProvider(
    val contentOffset: DpOffset,
    val density: Density,
    val bottomMargin: Dp,
    val sideMargin: Dp,
    val onPositionCalculated: (IntRect, IntRect) -> Unit = { _, _ -> }
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        // The min margin below the menu, relative to the screen.
        val bottomMarginPx = with(density) { bottomMargin.roundToPx() }
        // The min margin to side of the menu, relative to the screen.
        val horizontalMargin = with(density) { sideMargin.roundToPx() }
        // The content offset specified using the dropdown offset parameter.
        val contentOffsetX = with(density) { contentOffset.x.roundToPx() }
        val contentOffsetY = with(density) { contentOffset.y.roundToPx() }

        // Compute horizontal position.
        val toRight = maxOf(anchorBounds.left + contentOffsetX, horizontalMargin)
        val toLeft = anchorBounds.right - contentOffsetX - popupContentSize.width
        val toDisplayRight = windowSize.width - popupContentSize.width - horizontalMargin
        val toDisplayLeft = horizontalMargin
        val x = if (layoutDirection == LayoutDirection.Ltr) {
            sequenceOf(
                toRight,
                toLeft,
                // If the anchor gets outside of the window on the left, we want to position
                // toDisplayLeft for proximity to the anchor. Otherwise, toDisplayRight.
                if (anchorBounds.left >= 0) toDisplayRight else toDisplayLeft
            )
        } else {
            sequenceOf(
                toLeft,
                toRight,
                // If the anchor gets outside of the window on the right, we want to position
                // toDisplayRight for proximity to the anchor. Otherwise, toDisplayLeft.
                if (anchorBounds.right <= windowSize.width) toDisplayLeft else toDisplayRight
            )
        }.firstOrNull {
            it >= horizontalMargin && it + popupContentSize.width + horizontalMargin <= windowSize.width
        } ?: (if (layoutDirection == LayoutDirection.Ltr) toRight else toLeft)

        // Compute vertical position.
        val toBottom = anchorBounds.bottom + contentOffsetY
        val toTop = anchorBounds.top - contentOffsetY - popupContentSize.height
        val toCenter = anchorBounds.top - popupContentSize.height / 2
        val y = sequenceOf(toBottom, toTop, toCenter).firstOrNull {
            it >= 0 && it + popupContentSize.height <= windowSize.height - bottomMarginPx
        } ?: toTop

        onPositionCalculated(
            anchorBounds,
            IntRect(x, y, x + popupContentSize.width, y + popupContentSize.height)
        )
        return IntOffset(x, y)
    }
}

internal fun calculateTransformOrigin(
    parentBounds: IntRect,
    menuBounds: IntRect
): TransformOrigin {
    val pivotX = when {
        menuBounds.left >= parentBounds.right -> 0f
        menuBounds.right <= parentBounds.left -> 1f
        menuBounds.width == 0 -> 0f
        else -> {
            val intersectionCenter =
                (
                        kotlin.math.max(parentBounds.left, menuBounds.left) +
                                kotlin.math.min(parentBounds.right, menuBounds.right)
                        ) / 2
            (intersectionCenter - menuBounds.left).toFloat() / menuBounds.width
        }
    }
    val pivotY = when {
        menuBounds.top >= parentBounds.bottom -> 0f
        menuBounds.bottom <= parentBounds.top -> 1f
        menuBounds.height == 0 -> 0f
        else -> {
            val intersectionCenter =
                (
                        kotlin.math.max(parentBounds.top, menuBounds.top) +
                                kotlin.math.min(parentBounds.bottom, menuBounds.bottom)
                        ) / 2
            (intersectionCenter - menuBounds.top).toFloat() / menuBounds.height
        }
    }
    return TransformOrigin(pivotX, pivotY)
}