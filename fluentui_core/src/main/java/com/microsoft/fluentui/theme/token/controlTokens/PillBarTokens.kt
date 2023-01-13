package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

data class PillBarInfo(
    val style: FluentStyle = FluentStyle.Neutral
) : ControlInfo

@Parcelize
open class PillBarTokens : ControlToken, Parcelable {

    @Composable
    open fun background(pillBarInfo: PillBarInfo): Color {
        return when (pillBarInfo.style) {
            FluentStyle.Neutral -> FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background3].value(
                FluentTheme.themeMode
            )
            FluentStyle.Brand -> FluentColor(
                light = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    ThemeMode.Light
                ),
                dark = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background3].value(
                    ThemeMode.Dark
                )
            ).value(FluentTheme.themeMode)
        }
    }
}