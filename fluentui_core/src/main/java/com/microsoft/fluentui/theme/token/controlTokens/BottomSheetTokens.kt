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

open class BottomSheetInfo : ControlInfo

data class SheetAccessibilityAnnouncement(
    var expandedToShown: String = "",
    var expandedToCollapsed: String = "Bottom Sheet Collapsed",
    var shownToExpanded: String = "Bottom Sheet Expanded",
    var shownToCollapsed: String = "Bottom Sheet Collapsed",
    var collapsedToExpanded: String = "Bottom Sheet Expanded",
    var collapsedToShown: String = "Bottom Sheet Opened",
)

@Parcelize
open class BottomSheetTokens : IControlToken, Parcelable {
    @Composable
    open fun backgroundBrush(bottomSheetInfo: BottomSheetInfo): Brush =
        SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )

    @Composable
    open fun handleColor(bottomSheetInfo: BottomSheetInfo): Color =
        FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )

    @Composable
    open fun elevation(bottomSheetInfo: BottomSheetInfo): Dp =
        FluentGlobalTokens.ShadowTokens.Shadow02.value

    @Composable
    open fun cornerRadius(bottomSheetInfo: BottomSheetInfo): Dp =
        FluentGlobalTokens.CornerRadiusTokens.CornerRadius120.value

    @Composable
    open fun scrimColor(bottomSheetInfo: BottomSheetInfo): Color =
        FluentGlobalTokens.NeutralColorTokens.Black.value

    @Composable
    open fun scrimOpacity(bottomSheetInfo: BottomSheetInfo): Float = 0.32F

    @Composable
    open fun maxLandscapeWidth (bottomSheetInfo: BottomSheetInfo): Float = 1F

    open fun additionalOffset (bottomSheetInfo: BottomSheetInfo): Int = 0
}