package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.FluentColor
import kotlinx.parcelize.Parcelize

data class PillBarInfo(
    val style: PillButtonStyle = PillButtonStyle.Neutral
) : ControlInfo

@Parcelize
open class PillBarTokens : ControlToken, Parcelable {

    companion object {
        const val Type: String = "PillBarControl"
    }

    @Composable
    open fun background(pillBarInfo: PillBarInfo): Color {
        return when (pillBarInfo.style) {
            PillButtonStyle.Neutral -> FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background3].value(FluentTheme.themeMode)
            PillButtonStyle.Brand -> FluentColor(
                light = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(ThemeMode.Light),
                dark = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background3].value(ThemeMode.Dark)
            ).value(FluentTheme.themeMode)
        }
    }
}