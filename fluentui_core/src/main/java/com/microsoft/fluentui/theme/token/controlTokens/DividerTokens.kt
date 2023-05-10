package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

class DividerInfo : ControlInfo

@Parcelize
open class DividerTokens : IControlToken, Parcelable {
    @Composable
    open fun backgroundBrush(dividerInfo: DividerInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun dividerBrush(dividerInfo: DividerInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun verticalPadding(dividerInfo: DividerInfo): PaddingValues {
        return PaddingValues(vertical = 8.dp)
    }

    @Composable
    open fun startIndent(dividerInfo: DividerInfo): Dp = 0.dp
}