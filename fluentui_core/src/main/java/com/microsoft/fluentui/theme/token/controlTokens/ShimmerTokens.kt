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

enum class ShimmerShape {
    Box,
    Circle
}
data class ShimmerInfo(
    val shape: ShimmerShape = ShimmerShape.Box
):ControlInfo
@Parcelize
open class ShimmerTokens : ControlToken, Parcelable {
    @Composable
    open fun cornerRadius(shimmerInfo: ShimmerInfo): Dp {
        return GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius40)
    }

    @Composable
    open fun knockoutEffectColor(shimmerInfo: ShimmerInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Stencil2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun color(shimmerInfo: ShimmerInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Stencil1].value(
            themeMode = FluentTheme.themeMode
        )
    }
}