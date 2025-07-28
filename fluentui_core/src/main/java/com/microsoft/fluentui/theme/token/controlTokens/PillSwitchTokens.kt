package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentStyle
import kotlinx.parcelize.Parcelize

open class PillSwitchInfo(
    val style: FluentStyle = FluentStyle.Neutral
) : ControlInfo

@Parcelize
open class PillSwitchTokens : PillBarTokens(), Parcelable {

    @Composable
    open fun backgroundBrush(pillSwitchInfo: PillSwitchInfo): Brush {
        return when (pillSwitchInfo.style) {
            FluentStyle.Neutral -> SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    FluentTheme.themeMode
                )
            )
            FluentStyle.Brand -> SolidColor(
                FluentColor(
                    light = FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                        ThemeMode.Dark
                    )
                ).value(FluentTheme.themeMode)
            )
        }
    }

    @Composable
    open fun pillSwitchRowPadding(pillSwitchInfo: PillSwitchInfo): Dp {
        return when (pillSwitchInfo.style) {
            FluentStyle.Neutral -> 16.dp
            FluentStyle.Brand -> 16.dp
        }
    }
}