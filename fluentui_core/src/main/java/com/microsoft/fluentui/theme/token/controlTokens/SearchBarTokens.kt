package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
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

    @Composable
    open fun inputBackgroundColor(searchBarInfo: SearchBarInfo): Color {
        return when (searchBarInfo.style) {
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
    open fun backgroundColor(searchBarInfo: SearchBarInfo): Color {
        return when (searchBarInfo.style) {
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
    open fun textColor(searchBarInfo: SearchBarInfo): Color {
        return when (searchBarInfo.style) {
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
    open fun leftIconColor(searchBarInfo: SearchBarInfo): Color {
        return when (searchBarInfo.style) {
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
    open fun cursorColor(searchBarInfo: SearchBarInfo): Brush {
        return SolidColor(
            when (searchBarInfo.style) {
                FluentStyle.Neutral ->
                    FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value()
                FluentStyle.Brand ->
                    FluentColor(
                        light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            ThemeMode.Light
                        ),
                        dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                            ThemeMode.Dark
                        )
                    ).value()
            }
        )
    }

    @Composable
    open fun rightIconColor(searchBarInfo: SearchBarInfo): Color {
        return when (searchBarInfo.style) {
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
    open fun typography(searchBarInfo: SearchBarInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun leftIconSize(searchBarInfo: SearchBarInfo): Dp {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize200)
    }

    @Composable
    open fun rightIconSize(searchBarInfo: SearchBarInfo): Dp {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize200)
    }

    @Composable
    open fun circularProgressIndicatorSize(searchBarInfo: SearchBarInfo): CircularProgressIndicatorSize {
        return CircularProgressIndicatorSize.Medium
    }

    @Composable
    open fun searchBarPadding(info: SearchBarInfo): PaddingValues {
        return PaddingValues(horizontal = GlobalTokens.size(GlobalTokens.SizeTokens.Size80))
    }

    @Composable
    open fun progressIndicatorRightPadding(searchBarInfo: SearchBarInfo): PaddingValues {
        return PaddingValues(end = GlobalTokens.size(GlobalTokens.SizeTokens.Size160))
    }

    @Composable
    open fun height(searchBarInfo: SearchBarInfo): Dp {
        return 40.dp
    }
}