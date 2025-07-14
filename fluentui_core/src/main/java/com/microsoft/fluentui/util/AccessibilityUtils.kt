/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.util

import android.content.Context
import android.view.accessibility.AccessibilityManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import kotlinx.coroutines.delay

/**
 * Utilities for accessibility
 */
val Context.isAccessibilityEnabled: Boolean
    get() = accessibilityManager.isTouchExplorationEnabled

val Context.accessibilityManager: AccessibilityManager
    get() = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager

/**
 * A Modifier that makes a composable clickable, long clickable and displays a tooltip on a long press.
 *
 * This modifier combines click and long-press gesture detection with a tooltip that appears
 * above or below the composable. The tooltip is dismissed after a specified timeout or when
 * the user clicks outside of it.
 *
 * Example usage:
 * ```
 * Box(
 *     modifier = Modifier
 *         .clickableWithTooltip(
 *             tooltipText = "This is a tooltip!",
 *             tooltipEnabled = true,
 *             onClick = { Log.d("TAG", "Clicked!") },
 *             onLongClick = { Log.d("TAG", "Long clicked!") }
 *         ),
 *     contentAlignment = Alignment.Center
 * ) {
 *     Text("Hover or Long Press")
 * }
 * ```
 *
 * @param tooltipText The text to be displayed inside the tooltip.
 * @param tooltipEnabled A boolean to enable or disable the tooltip functionality. Defaults to `false`.
 * @param backgroundColor The background brush for the tooltip. Can be a solid color or a gradient. Defaults to `Color.Unspecified`.
 * @param cornerRadius The corner radius for the tooltip's background shape. Defaults to `8.dp`.
 * @param textStyle The text style for the tooltip's content.
 * @param padding The internal padding around the tooltip's text. Defaults to `12.dp`.
 * @param offset The DpOffset to adjust the tooltip's position relative to the composable. `x` adjusts horizontal position, `y` adjusts vertical. Defaults to `DpOffset(0.dp, 0.dp)`.
 * @param timeout The duration in milliseconds for which the tooltip remains visible before automatically dismissing. Defaults to `2000L`.
 * @param showRippleOnClick If true, a ripple effect will be shown on tap. Defaults to `true`.
 * @param clickRippleColor The color of the ripple effect. Defaults to `Color.Unspecified` which uses the theme's default.
 * @param onClick A lambda to be executed when the composable is tapped.
 * @param onLongClick A lambda to be executed when the composable is long-pressed. This action also triggers the tooltip if `tooltipEnabled` is true.
 * @return Returns a [Modifier] that applies the click and tooltip behavior.
 */

@OptIn(ExperimentalFoundationApi::class)
fun Modifier.clickableWithTooltip(
    tooltipText: String,
    tooltipEnabled: Boolean = false,
    backgroundColor: Brush = SolidColor(Color.Unspecified),
    cornerRadius: Dp = 8.dp,
    textStyle: TextStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Normal),
    padding: Dp = 12.dp,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    timeout: Long = 2000L,
    showRippleOnClick: Boolean = true,
    clickRippleColor: Color = Color.Unspecified,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null
): Modifier = composed {
    var isTooltipVisible by remember { mutableStateOf(false) }
    var anchorBounds by remember { mutableStateOf(IntRect.Zero) }
    val interactionSource = remember { MutableInteractionSource() }

    val currentOnClick by rememberUpdatedState(onClick)
    val currentOnLongClick by rememberUpdatedState(onLongClick)
    val currentTooltipEnabled by rememberUpdatedState(tooltipEnabled)

    val clickableModifier = Modifier.combinedClickable(
        interactionSource = interactionSource,
        indication = if (showRippleOnClick) rememberRipple(color = clickRippleColor) else null,
        onClick = { currentOnClick?.invoke() },
        onLongClick = {
            currentOnLongClick?.invoke()
            if (currentTooltipEnabled) {
                isTooltipVisible = true
            }
        }
    )

    val positionModifier = Modifier.onGloballyPositioned { layoutCoordinates ->
        anchorBounds = IntRect(
            left = layoutCoordinates.positionInWindow().x.toInt(),
            top = layoutCoordinates.positionInWindow().y.toInt(),
            right = (layoutCoordinates.positionInWindow().x + layoutCoordinates.size.width).toInt(),
            bottom = (layoutCoordinates.positionInWindow().y + layoutCoordinates.size.height).toInt()
        )
    }

    if (isTooltipVisible) {
        Tooltip(
            tooltipText = tooltipText,
            backgroundColor = backgroundColor,
            cornerRadius = cornerRadius,
            textStyle = textStyle,
            padding = padding,
            offset = offset,
            onDismissRequest = { isTooltipVisible = false }
        )

        LaunchedEffect(isTooltipVisible, timeout) {
            delay(timeout)
            isTooltipVisible = false
        }
    }

    this
        .then(if (tooltipEnabled) positionModifier else Modifier)
        .then(clickableModifier)
}

@Composable
private fun Tooltip(
    tooltipText: String,
    backgroundColor: Brush,
    cornerRadius: Dp,
    textStyle: TextStyle,
    padding: Dp,
    offset: DpOffset,
    onDismissRequest: () -> Unit
) {
    val density = LocalDensity.current
    val positionProvider = remember(offset) {
        TooltipPositionProvider(
            offset = offset,
            density = density
        )
    }
    Popup(
        popupPositionProvider = positionProvider,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            focusable = false,
        )
    ) {
        Column(
            modifier = Modifier.shadow(4.dp, clip = false)
        ) {
            Box(
                modifier = Modifier
                    .background(backgroundColor, RoundedCornerShape(cornerRadius))
                    .padding(padding)
            ) {
                Text(text = tooltipText, style = textStyle)
            }
        }
    }
}

private class TooltipPositionProvider(
    private val offset: DpOffset,
    private val density: Density
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val offsetX = with(density) { offset.x.roundToPx() }
        val offsetY = with(density) { offset.y.roundToPx() }
        val y =
            if ((anchorBounds.top - popupContentSize.height) > 0) anchorBounds.top - popupContentSize.height + offsetY else anchorBounds.bottom + offsetY
        val x = (anchorBounds.left + (anchorBounds.width - popupContentSize.width) / 2) + offsetX
        return IntOffset(x, y)
    }
}
