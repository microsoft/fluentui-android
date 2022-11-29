package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import kotlinx.parcelize.Parcelize

class DividerInfo : ControlInfo

@Parcelize
open class DividerTokens : ControlToken, Parcelable {
    @Composable
    open fun background(dividerInfo: DividerInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun dividerColor(dividerInfo: DividerInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            FluentTheme.themeMode
        )
    }
}