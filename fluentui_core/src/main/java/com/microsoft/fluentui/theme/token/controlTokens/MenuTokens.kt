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

open class MenuInfo : ControlInfo

@Parcelize
open class MenuTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(menuInfo: MenuInfo): Brush =
        SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )

    @Composable
    open fun cornerRadius(menuInfo: MenuInfo): Dp =
        FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius80)

    open fun elevation(menuInfo: MenuInfo): Dp =
        FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow16)

    open fun bottomMargin(menuInfo: MenuInfo): Dp =
        FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)

    open fun sideMargin(menuInfo: MenuInfo): Dp =
        FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
}