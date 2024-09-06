package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

open class FileCardInfo(
    val isPreviewAvailable: Boolean = true,
    val cardType: CardType = CardType.Elevated
)

@Parcelize
open class FileCardTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(fileCardInfo: FileCardInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun cornerRadius(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.CornerRadiusTokens.CornerRadius120.value
    }

    @Composable
    open fun elevation(fileCardInfo: FileCardInfo): Dp {
        return when (fileCardInfo.cardType) {
            CardType.Elevated -> FluentGlobalTokens.ShadowTokens.Shadow02.value
            CardType.Outlined -> 0.dp
        }
    }

    @Composable
    open fun borderColor(fileCardInfo: FileCardInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun borderStrokeWidth(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05.value
    }

    @Composable
    open fun iconColor(fileCardInfo: FileCardInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun textColor(fileCardInfo: FileCardInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun subTextColor(fileCardInfo: FileCardInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun actionOverFlowBackgroundColor(fileCardInfo: FileCardInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                themeMode = FluentTheme.themeMode
            )
        )

    }

    @Composable
    open fun actionOverFlowIconColor(fileCardInfo: FileCardInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun iconSize(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.IconSizeTokens.IconSize240.value
    }

    @Composable
    open fun textTypography(fileCardInfo: FileCardInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun subTextTypography(fileCardInfo: FileCardInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]
    }

    @Composable
    open fun actionOverflowCornerRadius(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
    }

    @Composable
    open fun actionOverflowIconSize(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.IconSizeTokens.IconSize240.value
    }

    @Composable
    open fun iconTextSpacing(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.SizeTokens.Size120.value
    }

    @Composable
    open fun textSubTextSpacing(fileCardInfo: FileCardInfo): Dp {
        return 0.dp
    }

    @Composable
    open fun actionOverflowPadding(fileCardInfo: FileCardInfo): PaddingValues {
        return PaddingValues(
            top = FluentGlobalTokens.SizeTokens.Size80.value,
            end = FluentGlobalTokens.SizeTokens.Size80.value
        )
    }

    @Composable
    open fun textContainerPadding(fileCardInfo: FileCardInfo): PaddingValues {
        return PaddingValues(all = FluentGlobalTokens.SizeTokens.Size120.value)
    }
}