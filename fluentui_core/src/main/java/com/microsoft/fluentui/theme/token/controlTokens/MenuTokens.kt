package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
        FluentGlobalTokens.CornerRadiusTokens.CornerRadius80.value

    open fun elevation(menuInfo: MenuInfo): Dp =
        FluentGlobalTokens.ShadowTokens.Shadow08.value

    open fun bottomMargin(menuInfo: MenuInfo): Dp =
        FluentGlobalTokens.SizeTokens.Size160.value

    open fun sideMargin(menuInfo: MenuInfo): Dp =
        FluentGlobalTokens.SizeTokens.Size160.value
}