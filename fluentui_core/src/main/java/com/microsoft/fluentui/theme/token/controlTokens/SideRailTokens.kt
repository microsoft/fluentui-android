package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize


open class SideRailInfo: ControlInfo
@Parcelize
open class SideRailTokens : IControlToken, Parcelable {

    @Composable
    open fun borderColor(sideRailInfo: SideRailInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value()
    }
    @Composable
    open fun topMargin(sideRailInfo: SideRailInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size240)
    }
    @Composable
    open fun bottomMargin(sideRailInfo: SideRailInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
    }
    @Composable
    open fun borderWidth(sideRailInfo: SideRailInfo): Dp {
        return FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05)
    }
    @Composable
    open fun headerPadding(sideRailInfo: SideRailInfo): PaddingValues {
        return PaddingValues(16.dp)
    }
    @Composable
    open fun backgroundColor(sideRailInfo: SideRailInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value()
    }
}
