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

class BottomSheetInfo() : ControlInfo

@Parcelize
open class BottomSheetTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(bottomSheetInfo: BottomSheetInfo): Color =
        FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun handleColor(bottomSheetInfo: BottomSheetInfo): Color =
        FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun elevation(bottomSheetInfo: BottomSheetInfo): Dp =
        GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow28)

    @Composable
    open fun cornerRadius(bottomSheetInfo: BottomSheetInfo): Dp =
        GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius120)

    @Composable
    open fun scrimColor(bottomSheetInfo: BottomSheetInfo): Color =
        GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)

    @Composable
    open fun scrimOpacity(bottomSheetInfo: BottomSheetInfo): Float =
        GlobalTokens.opacity(GlobalTokens.OpacityTokens.Opacity32)
}