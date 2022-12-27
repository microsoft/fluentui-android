package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

data class SearchBarInfo(
    val style: FluentStyle
) : ControlInfo

@Parcelize
open class SearchBarTokens : ControlToken, Parcelable {

    companion object {
        const val Type: String = "SearchBar"
    }

    @Composable
    open fun inputBackground(info: SearchBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground2].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun background(info: SearchBarInfo): Color {
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
    open fun textColor(info: SearchBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun leftIconColor(info: SearchBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun rightIconColor(info: SearchBarInfo): Color {
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
    open fun textSize(info: SearchBarInfo): FontInfo {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun leftIconSize(info: SearchBarInfo): IconSize {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Small)
    }

    @Composable
    open fun rightIconSize(info: SearchBarInfo): IconSize {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Small)
    }

    @Composable
    open fun circularProgressIndicatorSize(info: SearchBarInfo): CircularProgressIndicatorSize {
        return CircularProgressIndicatorSize.Medium
    }

    @Composable
    open fun padding(info: SearchBarInfo): PaddingValues {
        return PaddingValues(GlobalTokens.size(GlobalTokens.SizeTokens.Size80))
    }

    @Composable
    open fun progressIndicatorRightPadding(info: SearchBarInfo): PaddingValues {
        return PaddingValues(end = GlobalTokens.size(GlobalTokens.SizeTokens.Size160))
    }

    @Composable
    open fun fixedHeight(info: SearchBarInfo): Dp {
        return 40.dp
    }
}