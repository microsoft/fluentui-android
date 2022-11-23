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

data class SwitchInfo(
    val style: PillButtonStyle = PillButtonStyle.Neutral
) : ControlInfo

@Parcelize
open class SwitchTokens : PillBarTokens(), Parcelable {

    companion object {
        const val Type: String = "SwitchControl"
    }

    @Composable
    open fun background(tabsInfo: SwitchInfo): Color {
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