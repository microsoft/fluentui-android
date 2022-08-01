package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
import kotlinx.parcelize.Parcelize

/**
 * Possible values of [BehaviorType].
 */
enum class BehaviorType {
    BOTTOM, TOP, LEFT, RIGHT
}

@Parcelize
class DrawerTokens : ControlToken, Parcelable {
    companion object {
        const val Type: String = "Drawer"
    }

    @Composable
    open fun backgroundColor(type: BehaviorType): Color = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
            themeMode = FluentTheme.themeMode)

    @Composable
    open fun handleColor(type: BehaviorType): Color = FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode)

    @Composable
    open fun elevation(type: BehaviorType): Dp = FluentTheme.globalTokens.elevation[GlobalTokens.ShadowTokens.Shadow28]

    @Composable
    open fun borderRadius(type: BehaviorType): Dp {
        return when (type) {
            BehaviorType.TOP, BehaviorType.BOTTOM -> FluentTheme.globalTokens.borderRadius[GlobalTokens.BorderRadiusTokens.Medium]
            BehaviorType.LEFT, BehaviorType.RIGHT -> FluentTheme.globalTokens.borderRadius[GlobalTokens.BorderRadiusTokens.None]
        }
    }
    @Composable
    open fun scrimColor(type: BehaviorType): Color = FluentTheme.globalTokens.neutralColor[GlobalTokens.NeutralColorTokens.Black]

    @Composable
    open fun scrimOpacity(type: BehaviorType): Float = FluentTheme.globalTokens.opacity[GlobalTokens.OpacityTokens.Opacity32]
}