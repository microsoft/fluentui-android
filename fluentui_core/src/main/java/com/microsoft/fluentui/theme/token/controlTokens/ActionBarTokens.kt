package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken

import kotlinx.parcelize.Parcelize

enum class ACTIONBARTYPE {
    BASIC,
    ICON,
    CAROUSEL
}

open class ActionBarInfo: ControlInfo

@Parcelize
open class ActionBarTokens : IControlToken, Parcelable {

    @Composable
    open fun actionBarHeight(actionBarInfo: ActionBarInfo): Dp {
        return FluentGlobalTokens.SizeTokens.Size480.value
    }

    @Composable
    open fun actionBarColor(actionBarInfo: ActionBarInfo): Color {
        return aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
            themeMode = themeMode
        )
    }
}
