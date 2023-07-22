package com.microsoft.fluentuidemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.ListItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens

object CustomizedTokens {
    val listItemTokens = object : ListItemTokens() {
        @Composable
        override fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
            return StateBrush(
                rest = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value()),
                pressed = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2Pressed].value())
            )
        }
    }
}