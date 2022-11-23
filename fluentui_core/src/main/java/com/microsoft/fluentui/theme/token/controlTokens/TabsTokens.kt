package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentColor
import kotlinx.parcelize.Parcelize

data class TabsInfo(
    val style: PillButtonStyle = PillButtonStyle.Neutral
) : ControlInfo

@Parcelize
open class TabsTokens : PillBarTokens(), Parcelable {

    companion object {
        const val Type: String = "TabsControl"
    }

    @Composable
    open fun background(tabsInfo: TabsInfo): Color {
        return when (tabsInfo.style) {
            PillButtonStyle.Neutral -> FluentColor(
                light = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background3].value(
                    ThemeMode.Light
                ),
                dark = Color.Unspecified
            ).value(FluentTheme.themeMode)
            PillButtonStyle.Brand -> FluentColor(
                light = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    ThemeMode.Light
                ),
                dark = Color.Unspecified
            ).value(FluentTheme.themeMode)
        }
    }

    @Composable
    open fun trackBackground(tabsInfo: TabsInfo): Color {
        return when (tabsInfo.style) {
            PillButtonStyle.Neutral -> FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                FluentTheme.themeMode
            )
            PillButtonStyle.Brand -> FluentColor(
                light = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground2].value(
                    ThemeMode.Light
                ),
                dark = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    ThemeMode.Dark
                )
            ).value(FluentTheme.themeMode)
        }
    }
}