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

class TabBarInfo : ControlInfo

@Parcelize
open class TabBarTokens : ControlToken, Parcelable {

    @Composable
    open fun topBorderColor(tabBarInfo: TabBarInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke2].value()
    }

    @Composable
    open fun topBorderWidth(tabBarInfo: TabBarInfo): Dp {
        return GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth05)
    }
}