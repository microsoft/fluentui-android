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

enum class AppBarStyle {
    Large,
    Medium,
    Small
}

data class AppBarInfo(
    val style: FluentStyle = FluentStyle.Neutral,
    val appBarStyle: AppBarStyle = AppBarStyle.Medium
) : ControlInfo

@Parcelize
open class AppBarTokens : ControlToken, Parcelable {

    companion object {
        const val Type: String = "AppBar"
    }

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
        return when (info.appBarStyle) {
            AppBarStyle.Large -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Title1]
            AppBarStyle.Medium -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Title2]
            AppBarStyle.Small -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1Strong]
            else -> FontInfo(fontSize = FontSize(0.sp, 0.sp))
        }
    }

    @Composable
    open fun subtitleTypography(info: AppBarInfo): FontInfo {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
    }

    @Composable
    fun leftIconSize(info: AppBarInfo): IconSize {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Medium)
    }

    @Composable
    fun logoSize(info: AppBarInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size320)
    }

    @Composable
    fun titleIconSize(info: AppBarInfo): IconSize {
        return when (info.appBarStyle) {
            AppBarStyle.Small -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XSmall)
            else -> IconSize(0.dp, IconType.Regular)
        }
    }

    @Composable
    fun subtitleIconSize(info: AppBarInfo): IconSize {
        return when (info.appBarStyle) {
            AppBarStyle.Small -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XXSmall)
            AppBarStyle.Medium -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XXSmall)
            else -> IconSize(0.dp, IconType.Regular)
        }
    }

    @Composable
    open fun textPadding(info: AppBarInfo): PaddingValues {
        return when (info.appBarStyle) {
            AppBarStyle.Large -> PaddingValues(vertical = 12.dp)
            AppBarStyle.Medium -> PaddingValues(vertical = 16.dp)
            AppBarStyle.Small -> PaddingValues(vertical = 8.dp)
        }
    }

    @Composable
    open fun navigationIconPadding(info: AppBarInfo): PaddingValues {
        return when (info.appBarStyle) {
            AppBarStyle.Large -> PaddingValues()
            AppBarStyle.Medium -> PaddingValues(16.dp)
            AppBarStyle.Small -> PaddingValues(16.dp)
        }
    }

    @Composable
    open fun logoPadding(info: AppBarInfo): PaddingValues {
        return when (info.appBarStyle) {
            AppBarStyle.Large -> PaddingValues(start = 12.dp)
            AppBarStyle.Medium -> PaddingValues()
            AppBarStyle.Small -> PaddingValues(start = 8.dp)
        }
    }

    @Composable
    open fun height(info: AppBarInfo): Dp {
        return 40.dp
    }
}