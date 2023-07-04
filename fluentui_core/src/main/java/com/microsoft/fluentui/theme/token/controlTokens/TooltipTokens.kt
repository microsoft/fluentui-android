package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

class TooltipInfo() : ControlInfo

@Parcelize
open class TooltipTokens(): IControlToken, Parcelable {
    @Composable
    open fun backgroundBrush(tooltipInfo: TooltipInfo): Brush =
        SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundDarkStatic].value(
                themeMode = FluentTheme.themeMode
            )
        )

    @Composable
    open fun cornerRadius(tooltipInfo: TooltipInfo): Dp =
        FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius80)

    open fun elevation(tooltipInfo: TooltipInfo): Dp =
        FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow16)

    open fun bottomMargin(tooltipInfo: TooltipInfo): Dp =
        FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)

    open fun sideMargin(tooltipInfo: TooltipInfo): Dp =
        FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
}