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