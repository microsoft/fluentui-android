package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class AppBarSize {
    Large,
    Medium,
    Small
}

open class TooltipControls(
    var enableTitleTooltip: Boolean = false,
    var enableSubtitleTooltip: Boolean = false,
    var enableNavigationIconTooltip: Boolean = false
) {}

open class AppBarInfo(
    val style: FluentStyle = FluentStyle.Neutral,
    val appBarSize: AppBarSize = AppBarSize.Medium
) : ControlInfo

@Parcelize
open class AppBarTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(info: AppBarInfo): Brush {
        return SolidColor(
            when (info.style) {
                FluentStyle.Neutral ->
                    FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                        themeMode = FluentTheme.themeMode
                    )

                FluentStyle.Brand ->
                    FluentColor(
                        light = FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            ThemeMode.Light
                        ),
                        dark = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                            ThemeMode.Dark
                        )
                    ).value(themeMode = FluentTheme.themeMode)
            }
        )
    }

    @Composable
    open fun navigationIconColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = FluentTheme.themeMode
                )

            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun navigationIconRippleColor(): Color {
        return Color.Unspecified
    }

    @Composable
    open fun titleIconColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = FluentTheme.themeMode
                )

            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun subtitleIconColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = FluentTheme.themeMode
                )

            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun titleTextColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = FluentTheme.themeMode
                )

            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun tooltipVisibilityControls(info: AppBarInfo): TooltipControls {
        return TooltipControls(
            enableTitleTooltip = false,
            enableSubtitleTooltip = false,
            enableNavigationIconTooltip = false
        )
    }

    @Composable
    open fun tooltipTextStyle(info: AppBarInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2].merge(
            TextStyle(
                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        )
    }

    @Composable
    open fun tooltipBackgroundBrush(info: AppBarInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundDarkStatic].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun tooltipCornerRadius(info: AppBarInfo): Dp {
        return FluentGlobalTokens.CornerRadiusTokens.CornerRadius80.value
    }

    @Composable
    open fun tooltipRippleColor(info: AppBarInfo): Color {
        return Color.Unspecified
    }

    @Composable
    open fun tooltipOffset(info: AppBarInfo): DpOffset {
        return DpOffset(x = 0.dp, y = 0.dp)
    }

    @Composable
    open fun tooltipTimeout(info: AppBarInfo): Long {
        return 2000L // Default timeout for tooltip in milliseconds
    }

    @Composable
    open fun subtitleTextColor(info: AppBarInfo): Color {
        return when (info.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = FluentTheme.themeMode
                )

            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }

    @Composable
    open fun titleTypography(info: AppBarInfo): TextStyle {
        return when (info.appBarSize) {
            AppBarSize.Large -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1]
            AppBarSize.Medium -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]
            AppBarSize.Small -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]
        }
    }

    @Composable
    open fun subtitleTypography(info: AppBarInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]
    }

    @Composable
    fun leftIconSize(info: AppBarInfo): Dp {
        return FluentGlobalTokens.IconSizeTokens.IconSize240.value
    }

    @Composable
    fun titleIconSize(info: AppBarInfo): Dp {
        return when (info.appBarSize) {
            AppBarSize.Small -> FluentGlobalTokens.IconSizeTokens.IconSize160.value
            else -> 0.dp
        }
    }

    @Composable
    fun subtitleIconSize(info: AppBarInfo): Dp {
        return when (info.appBarSize) {
            AppBarSize.Small -> FluentGlobalTokens.IconSizeTokens.IconSize120.value
            AppBarSize.Medium -> FluentGlobalTokens.IconSizeTokens.IconSize120.value
            else -> 0.dp
        }
    }

    @Composable
    open fun navigationIconPadding(info: AppBarInfo): PaddingValues {
        return when (info.appBarSize) {
            AppBarSize.Large -> PaddingValues(16.dp)
            AppBarSize.Medium -> PaddingValues(16.dp)
            AppBarSize.Small -> PaddingValues(16.dp)
        }
    }

    @Composable
    open fun textPadding(info: AppBarInfo): PaddingValues {
        return when (info.appBarSize) {
            AppBarSize.Large -> PaddingValues(start = 12.dp)
            AppBarSize.Medium -> PaddingValues(start = 8.dp)
            AppBarSize.Small -> PaddingValues(start = 8.dp)
        }
    }

    @Composable
    open fun borderStroke(info: AppBarInfo): BorderStroke {
        return when (info.style) {
            FluentStyle.Neutral ->
                if (FluentTheme.themeMode == ThemeMode.Dark || (FluentTheme.themeMode == ThemeMode.Auto && isSystemInDarkTheme())) {
                    BorderStroke(
                        FluentGlobalTokens.StrokeWidthTokens.StrokeWidthNone.value,
                        Color.Unspecified
                    )
                } else {
                    BorderStroke(
                        FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05.value,
                        FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value()
                    )
                }

            else -> BorderStroke(
                FluentGlobalTokens.StrokeWidthTokens.StrokeWidthNone.value,
                Color.Unspecified
            )
        }
    }

    @Composable
    open fun height(info: AppBarInfo): Dp {
        return 40.dp
    }
}