package com.microsoft.fluentui.theme.token.controlTokens

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.StateColor
import kotlinx.parcelize.Parcelize

@Parcelize
open class TabBarTabItemsTokens : TabItemTokens() {

    @Composable
    override fun backgroundColor(tabItemInfo: TabItemInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value()
    }

    @Composable
    override fun iconColor(tabItemInfo: TabItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
            selected = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
        )
    }

    @Composable
    override fun textColor(tabItemInfo: TabItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
            selected = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
        )
    }
}