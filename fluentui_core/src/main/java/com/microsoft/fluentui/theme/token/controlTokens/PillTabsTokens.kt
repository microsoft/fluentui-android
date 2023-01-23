package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentStyle
import kotlinx.parcelize.Parcelize

data class PillTabsInfo(
    val style: FluentStyle = FluentStyle.Neutral
) : ControlInfo

@Parcelize
open class PillTabsTokens : PillBarTokens(), Parcelable {

    @Composable
    open fun background(pillTabsInfo: PillTabsInfo): Color {
        return when (pillTabsInfo.style) {
            FluentStyle.Neutral -> FluentColor(
                light = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background3].value(
                    ThemeMode.Light
                ),
                dark = Color.Unspecified
            ).value(FluentTheme.themeMode)
            FluentStyle.Brand -> FluentColor(
                light = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    ThemeMode.Light
                ),
                dark = Color.Unspecified
            ).value(FluentTheme.themeMode)
        }
    }

    @Composable
    open fun trackBackground(pillTabsInfo: PillTabsInfo): Color {
        return when (pillTabsInfo.style) {
            FluentStyle.Neutral -> FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                FluentTheme.themeMode
            )
            FluentStyle.Brand -> FluentColor(
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