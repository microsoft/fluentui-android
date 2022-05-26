package com.example.theme.token

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.ButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens

open class MyButtonTokens : ButtonTokens() {

    @Composable
    override fun backgroundColor(buttonInfo: ButtonInfo): StateColor {
        return StateColor(
            rest = Color.Red,
            selected = Color.Yellow,
            pressed = Color.Green,
            disabled = Color.LightGray
        )
    }

    @Composable
    override fun textColor(buttonInfo: ButtonInfo): StateColor {
        return StateColor(rest = Color.Black)
    }
}