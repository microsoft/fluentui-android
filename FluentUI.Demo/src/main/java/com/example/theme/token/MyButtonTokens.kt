package com.example.theme.token

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.controlTokens.ButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens

open class MyButtonTokens : ButtonTokens() {

    @Composable
    override fun fixedHeight(buttonInfo: ButtonInfo): Dp {
        return when (buttonInfo.size) {
            ButtonSize.Small -> 40.dp
            ButtonSize.Medium -> 48.dp
            ButtonSize.Large -> 60.dp
        }
    }

    @Composable
    override fun cornerRadius(buttonInfo: ButtonInfo): Dp {
        return 0.dp
    }
}