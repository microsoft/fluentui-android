package com.example.theme.token

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.AppBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.AppBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.FABInfo
import com.microsoft.fluentui.theme.token.controlTokens.FABTokens

private var gradient = Brush.linearGradient(
    0.0f to Color(0xFF464FEB),
    0.7f to Color(0xFF47CFFA),
    0.92f to Color(0xFFB47CF8),
    start = Offset.Zero,
    end = Offset.Infinite
)

class MyAppBarToken : AppBarTokens() {
    @Composable
    override fun backgroundBrush(info: AppBarInfo): Brush {
        return if (info.style == FluentStyle.Brand) gradient else super.backgroundBrush(info)
    }
}

class MyFABToken : FABTokens() {
    @Composable
    override fun backgroundBrush(info: FABInfo): StateBrush {
        return StateBrush(
            rest = gradient,
            pressed = gradient,
        )
    }
}