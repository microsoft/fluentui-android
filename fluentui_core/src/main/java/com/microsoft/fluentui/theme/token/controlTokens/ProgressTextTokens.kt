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

open class ProgressTextInfo : ControlInfo

@Parcelize
open class ProgressTextTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(progressTextInfo: ProgressTextInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun borderWidth(progressTextInfo: ProgressTextInfo): Dp {
        return FluentGlobalTokens.strokeWidth(
            FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10
        )
    }

    @Composable
    open fun padding(progressTextInfo: ProgressTextInfo): PaddingValues {
        return PaddingValues(
            horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160),
            vertical = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120)
        )
    }

    @Composable
    open fun iconTextSpacing(progressTextInfo: ProgressTextInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun borderColor(progressTextInfo: ProgressTextInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun typography(progressTextInfo: ProgressTextInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun textColor(progressTextInfo: ProgressTextInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun iconSize(progressTextInfo: ProgressTextInfo): Dp {
        return FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)
    }

    @Composable
    open fun iconColor(progressTextInfo: ProgressTextInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun progressbarHeight(progressTextInfo: ProgressTextInfo): Dp {
        return FluentGlobalTokens.strokeWidth(
            FluentGlobalTokens.StrokeWidthTokens.StrokeWidth40
        )
    }

    @Composable
    open fun progressbarBackgroundColor(progressTextInfo: ProgressTextInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun progressbarBrush(progressTextInfo: ProgressTextInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

}