package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

open class PillBarInfo(
    val style: FluentStyle = FluentStyle.Neutral
) : ControlInfo

@Parcelize
open class PillBarTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(pillBarInfo: PillBarInfo): Brush {
        return when (pillBarInfo.style) {
            FluentStyle.Neutral -> SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                    FluentTheme.themeMode
                )
            )
            FluentStyle.Brand -> SolidColor(
                FluentColor(
                    light = FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                        ThemeMode.Dark
                    )
                ).value(FluentTheme.themeMode)
            )
        }
    }

    @Composable
    open fun padding(pillBarInfo: PillBarInfo): Dp =
        FluentGlobalTokens.SizeTokens.Size160.value
}