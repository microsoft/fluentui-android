package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

class SuggestionPromptInfo : ControlInfo

@Parcelize
open class SuggestionPromptTokens : IControlToken, Parcelable {

    @Composable
    open fun borderStroke(suggestionPromptInfo: SuggestionPromptInfo): StateBorderStroke {
        return StateBorderStroke(
            rest = listOf(
                BorderStroke(
                    FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10),
                    FluentTheme.aliasTokens.brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                )
            ),
            pressed = listOf(
                BorderStroke(
                    FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10),
                    FluentTheme.aliasTokens.brandColor[FluentAliasTokens.BrandColorTokens.Color120]
                )
            ),
        )
    }

    @Composable
    open fun actionIconColor(suggestionPromptInfo: SuggestionPromptInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun backgroundBrush(suggestionPromptInfo: SuggestionPromptInfo): StateBrush {
        return StateBrush(
            pressed = SolidColor(FluentTheme.aliasTokens.brandColor[FluentAliasTokens.BrandColorTokens.Color120])
        )
    }

    @Composable
    open fun imageSize(suggestionPromptInfo: SuggestionPromptInfo): Dp {
        return FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize200)
    }

    @Composable
    open fun textColor(suggestionPromptInfo: SuggestionPromptInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
    }

    @Composable
    open fun typography(suggestionPromptInfo: SuggestionPromptInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun contentPadding(suggestionPromptInfo: SuggestionPromptInfo): PaddingValues {
        return PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    }

    @Composable
    open fun cornerRadius(suggestionPromptInfo: SuggestionPromptInfo): Dp =
        FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)

    @Composable
    open fun maxWidth(suggestionPromptInfo: SuggestionPromptInfo): Dp = 200.dp
}