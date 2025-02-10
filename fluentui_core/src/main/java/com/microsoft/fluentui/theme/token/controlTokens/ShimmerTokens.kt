package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

open class ShimmerInfo : ControlInfo

enum class ShimmerOrientation {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    TOPLEFT_TO_BOTTOMRIGHT,
    BOTTOMRIGHT_TO_TOPLEFT
}

@Parcelize
open class ShimmerTokens : IControlToken, Parcelable {
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

    @Composable
    open fun orientation(shimmerInfo: ShimmerInfo): ShimmerOrientation {
        return ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT
    }
}