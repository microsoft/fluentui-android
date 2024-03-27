package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

open class CitationInfo : ControlInfo

@Parcelize
open class CitationTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(citationInfo: CitationInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun cornerRadius(citationInfo: CitationInfo): Dp {
        return FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
    }

    @Composable
    open fun borderBrush(citationInfo: CitationInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun borderStrokeWidth(citationInfo: CitationInfo): Dp {
        return FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10.value
    }

    @Composable
    open fun textColor(citationInfo: CitationInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun textTypography(citationInfo: CitationInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2]
    }

    @Composable
    open fun textPadding(citationInfo: CitationInfo): PaddingValues {
        return PaddingValues(
            horizontal = FluentGlobalTokens.SizeTokens.Size40.value,
            vertical = FluentGlobalTokens.SizeTokens.Size20.value
        )
    }
}