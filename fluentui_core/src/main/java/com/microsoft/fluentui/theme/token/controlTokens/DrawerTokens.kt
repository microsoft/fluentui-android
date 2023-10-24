package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

/**
 * Possible values of [BehaviorType].
 */
enum class BehaviorType {
    TOP, BOTTOM, LEFT_SLIDE_OVER, RIGHT_SLIDE_OVER, BOTTOM_SLIDE_OVER
}

open class DrawerInfo(val type: BehaviorType = BehaviorType.LEFT_SLIDE_OVER) : ControlInfo

@Parcelize
open class DrawerTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(drawerInfo: DrawerInfo): Brush =
        SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )

    @Composable
    open fun handleColor(drawerInfo: DrawerInfo): Color =
        FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun elevation(drawerInfo: DrawerInfo): Dp = FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow02)

    @Composable
    open fun borderRadius(drawerInfo: DrawerInfo): Dp {
        return when (drawerInfo.type) {
            BehaviorType.TOP, BehaviorType.BOTTOM, BehaviorType.BOTTOM_SLIDE_OVER -> FluentGlobalTokens.cornerRadius(
                FluentGlobalTokens.CornerRadiusTokens.CornerRadius120
            )
            BehaviorType.LEFT_SLIDE_OVER, BehaviorType.RIGHT_SLIDE_OVER -> FluentGlobalTokens.cornerRadius(
                FluentGlobalTokens.CornerRadiusTokens.CornerRadiusNone
            )
        }
    }

    @Composable
    open fun scrimColor(drawerInfo: DrawerInfo): Color =
        FluentGlobalTokens.neutralColor(FluentGlobalTokens.NeutralColorTokens.Black)

    @Composable
    open fun scrimOpacity(drawerInfo: DrawerInfo): Float = 0.32F
}