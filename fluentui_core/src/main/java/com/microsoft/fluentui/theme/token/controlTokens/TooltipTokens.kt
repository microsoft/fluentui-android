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
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

class TooltipInfo : ControlInfo

@Parcelize
open class TooltipTokens : IControlToken, Parcelable {
    @Composable
    open fun backgroundBrush(tooltipInfo: TooltipInfo): Brush =
        SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundDarkStatic].value(
                themeMode = FluentTheme.themeMode
            )
        )

    @Composable
    open fun tipColor(tooltipInfo: TooltipInfo): Color =
        FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundDarkStatic].value(
            themeMode = FluentTheme.themeMode
        )


    @Composable
    open fun textColor(tooltipInfo: TooltipInfo): Color =
        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun textTypography(tooltipInfo: TooltipInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun titleColor(tooltipInfo: TooltipInfo): Color =
        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun titleTextSpacing(tooltipInfo: TooltipInfo): Dp =
        FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)

    @Composable
    open fun titleTypography(tooltipInfo: TooltipInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]
    }

    @Composable
    open fun cornerRadius(tooltipInfo: TooltipInfo): Dp =
        FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius80)

    @Composable
    open fun padding(tooltipInfo: TooltipInfo): PaddingValues =
        PaddingValues(
            horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80),
            vertical = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120)
        )

    open fun elevation(tooltipInfo: TooltipInfo): Dp =
        FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow16)

    open fun margin(tooltipInfo: TooltipInfo): Dp =
        FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size20)

    open fun maxWidth(tooltipInfo: TooltipInfo): Dp = 300.dp

}
