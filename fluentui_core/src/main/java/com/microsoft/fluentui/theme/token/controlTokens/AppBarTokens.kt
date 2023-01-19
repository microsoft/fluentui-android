package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class AppBarSize {
    Large,
    Medium,
    Small
}

data class AppBarInfo(
    val style: FluentStyle = FluentStyle.Neutral,
    val appBarSize: AppBarSize = AppBarSize.Medium
) : ControlInfo

@Parcelize
open class AppBarTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background3].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background3].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun navigationIconColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun titleIconColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun subtitleIconColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }


    @Composable
    open fun titleTextColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun subtitleTextColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun titleTypography(info: AppBarInfo): FontInfo {
        return when (info.appBarSize) {
            AppBarSize.Large -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Title1]
            AppBarSize.Medium -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Title2]
            AppBarSize.Small -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1Strong]
            else -> FontInfo(fontSize = FontSize(0.sp, 0.sp))
        }
    }

    @Composable
    open fun subtitleTypography(info: AppBarInfo): FontInfo {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
    }

    @Composable
    fun leftIconSize(info: AppBarInfo): Dp {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Medium)
    }

    @Composable
    fun titleIconSize(info: AppBarInfo): Dp {
        return when (info.appBarSize) {
            AppBarSize.Small -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XSmall)
            else -> 0.dp
        }
    }

    @Composable
    fun subtitleIconSize(info: AppBarInfo): Dp {
        return when (info.appBarSize) {
            AppBarSize.Small -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XXSmall)
            AppBarSize.Medium -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XXSmall)
            else -> 0.dp
        }
    }

    @Composable
    open fun navigationIconPadding(info: AppBarInfo): PaddingValues {
        return when (info.appBarSize) {
            AppBarSize.Large -> PaddingValues()
            AppBarSize.Medium -> PaddingValues(16.dp)
            AppBarSize.Small -> PaddingValues(16.dp)
        }
    }

    @Composable
    open fun textPadding(info: AppBarInfo): PaddingValues {
        return when (info.appBarSize) {
            AppBarSize.Large -> PaddingValues(start = 12.dp)
            AppBarSize.Medium -> PaddingValues()
            AppBarSize.Small -> PaddingValues(start = 8.dp)
        }
    }

    @Composable
    open fun height(info: AppBarInfo): Dp {
        return 40.dp
    }
}