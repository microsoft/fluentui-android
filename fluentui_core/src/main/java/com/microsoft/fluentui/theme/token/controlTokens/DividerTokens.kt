package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
        return FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun verticalPadding(dividerInfo: DividerInfo): PaddingValues {
        return PaddingValues(vertical = 8.dp)
    }

    @Composable
    open fun startIndent(dividerInfo: DividerInfo): Dp = 0.dp
}