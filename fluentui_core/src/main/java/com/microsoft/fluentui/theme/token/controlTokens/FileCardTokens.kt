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

data class FileCardInfo(
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
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius120)
    }

    @Composable
    open fun elevation(fileCardInfo: FileCardInfo): Dp {
        return when (fileCardInfo.cardType) {
            CardType.Elevated -> FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow02)
            CardType.Outlined -> 0.dp
        }
    }

    @Composable
    open fun borderColor(fileCardInfo: FileCardInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun borderStrokeWidth(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05)
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
        return FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)
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
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)
    }

    @Composable
    open fun actionOverflowIconSize(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)
    }

    @Composable
    open fun iconTextSpacing(fileCardInfo: FileCardInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120)
    }

    @Composable
    open fun textSubTextSpacing(fileCardInfo: FileCardInfo): Dp {
        return 0.dp
    }

    @Composable
    open fun actionOverflowPadding(fileCardInfo: FileCardInfo): PaddingValues {
        return PaddingValues(
            top = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80),
            end = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
        )
    }

    @Composable
    open fun textContainerPadding(fileCardInfo: FileCardInfo): PaddingValues {
        return PaddingValues(all = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
    }
}