package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class ShimmerShape {
    Box,
    Circle
}

data class ShimmerInfo(
    val shape: ShimmerShape = ShimmerShape.Box
) : ControlInfo

@Parcelize
open class ShimmerTokens : IControlToken, Parcelable {
    @Composable
    open fun cornerRadius(shimmerInfo: ShimmerInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)
    }

    @Composable
    open fun knockoutEffectColor(shimmerInfo: ShimmerInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Stencil2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun color(shimmerInfo: ShimmerInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Stencil1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun delay(shimmerInfo: ShimmerInfo): Int {
        return 1000
    }
}