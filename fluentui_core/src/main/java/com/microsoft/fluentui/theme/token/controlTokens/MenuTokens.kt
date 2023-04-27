package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
import kotlinx.parcelize.Parcelize

class MenuInfo : ControlInfo

@Parcelize
open class MenuTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(menuInfo: MenuInfo): Color =
        FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun cornerRadius(menuInfo: MenuInfo): Dp =
        GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius80)

    open fun elevation(menuInfo: MenuInfo): Dp =
        GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow16)

    open fun bottomMargin(menuInfo: MenuInfo): Dp =
        GlobalTokens.size(GlobalTokens.SizeTokens.Size160)

    open fun sideMargin(menuInfo: MenuInfo): Dp =
        GlobalTokens.size(GlobalTokens.SizeTokens.Size160)
}