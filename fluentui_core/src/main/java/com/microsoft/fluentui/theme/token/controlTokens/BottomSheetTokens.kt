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

@Parcelize
open class BottomSheetTokens : ControlToken, Parcelable {
    companion object {
        const val Type: String = "BottomSheet"
    }

    @Composable
    open fun backgroundColor(): Color =
        FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun handleColor(): Color =
        FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun elevation(): Dp =
        GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow28)

    @Composable
    open fun borderRadius(): Dp =
        GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.XLarge)

    @Composable
    open fun scrimColor(): Color =
        GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)

    @Composable
    open fun scrimOpacity(): Float =
        GlobalTokens.opacity(GlobalTokens.OpacityTokens.Opacity32)
}