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

enum class ShimmerShape {
    Box,
    Circle
}

@Parcelize
open class ShimmerTokens : ControlToken, Parcelable {
    @Composable
    open fun cornerRadius(): Dp {
        return GlobalTokens.borderRadius(GlobalTokens.CornerRadiusTokens.CornerRadius40)
    }

    @Composable
    open fun knockoutEffectColor(): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Stencil2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun color(): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Stencil1].value(
            themeMode = FluentTheme.themeMode
        )
    }
}