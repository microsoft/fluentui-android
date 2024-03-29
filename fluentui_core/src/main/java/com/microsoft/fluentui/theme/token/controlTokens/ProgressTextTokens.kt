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

open class ProgressTextInfo(val progress: Float) : ControlInfo

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
        return FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10
            .value
    }

    @Composable
    open fun padding(progressTextInfo: ProgressTextInfo): PaddingValues {
        return PaddingValues(
            horizontal = FluentGlobalTokens.SizeTokens.Size160.value,
            vertical = FluentGlobalTokens.SizeTokens.Size120.value
        )
    }

    @Composable
    open fun iconTextSpacing(progressTextInfo: ProgressTextInfo): Dp {
        return FluentGlobalTokens.SizeTokens.Size80.value
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
        return FluentGlobalTokens.IconSizeTokens.IconSize240.value
    }

    @Composable
    open fun iconColor(progressTextInfo: ProgressTextInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun progressbarHeight(progressTextInfo: ProgressTextInfo): Dp {
        return FluentGlobalTokens.StrokeWidthTokens.StrokeWidth40.value
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