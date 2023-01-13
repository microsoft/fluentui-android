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

/**
 * Possible values of [BehaviorType].
 */
enum class BehaviorType {
    BOTTOM, TOP, LEFT, RIGHT
}

data class DrawerInfo(val type: BehaviorType = BehaviorType.LEFT) : ControlInfo

@Parcelize
open class DrawerTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(drawerInfo: DrawerInfo): Color =
        FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun handleColor(drawerInfo: DrawerInfo): Color =
        FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun elevation(drawerInfo: DrawerInfo): Dp =
        GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow28)

    @Composable
    open fun borderRadius(drawerInfo: DrawerInfo): Dp {
        return when (drawerInfo.type) {
            BehaviorType.TOP, BehaviorType.BOTTOM -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius120)
            BehaviorType.LEFT, BehaviorType.RIGHT -> GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadiusNone)
        }
    }

    @Composable
    open fun scrimColor(drawerInfo: DrawerInfo): Color =
        GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)

    @Composable
    open fun scrimOpacity(drawerInfo: DrawerInfo): Float =
        GlobalTokens.opacity(GlobalTokens.OpacityTokens.Opacity32)
}