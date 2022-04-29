package com.example.theme.token

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.ButtonSize
import com.microsoft.fluentui.theme.token.ButtonStyle
import com.microsoft.fluentui.theme.token.ButtonToken

open class MyButtonToken(style: ButtonStyle =  ButtonStyle.Primary, size: ButtonSize =  ButtonSize.Medium) : ButtonToken(style,size) {

    @Composable
    public override fun background() : Color {
        return Color.Red
    }

    @Composable
    public override fun contentColor() : Color {
        return Color.Black
    }
}