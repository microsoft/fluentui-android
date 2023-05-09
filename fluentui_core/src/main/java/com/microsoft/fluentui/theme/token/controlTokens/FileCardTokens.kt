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
import kotlinx.parcelize.Parcelize

data class FileCardInfo(
    val isPreviewAvailable: Boolean = true,
    override val cardType: CardType = CardType.Elevated
) : BasicCardControlInfo()

@Parcelize
open class FileCardTokens : BasicCardTokens(), Parcelable {

    @Composable
    override fun backgroundColor(fileCardInfo: BasicCardControlInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    override fun cornerRadius(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius120)
    }

    @Composable
    override fun elevation(fileCardInfo: BasicCardControlInfo): Dp {
        return when (fileCardInfo.cardType) {
            CardType.Elevated -> FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow02)
            CardType.Outlined -> 0.dp
        }
    }

    @Composable
    override fun borderColor(fileCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    override fun borderStrokeWidth(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05)
    }

    @Composable
    open fun iconColor(fileCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun textColor(fileCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun subTextColor(fileCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun actionOverFlowBackgroundColor(fileCardInfo: BasicCardControlInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                themeMode = FluentTheme.themeMode
            )
        )

    }

    @Composable
    open fun actionOverFlowIconColor(fileCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun iconSize(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)
    }

    @Composable
    open fun textTypography(fileCardInfo: BasicCardControlInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun subTextTypography(fileCardInfo: BasicCardControlInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]
    }

    @Composable
    open fun actionOverflowCornerRadius(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)
    }

    @Composable
    open fun actionOverflowIconSize(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)
    }

    @Composable
    open fun iconTextSpacing(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120)
    }

    @Composable
    open fun textSubTextSpacing(fileCardInfo: BasicCardControlInfo): Dp {
        return 0.dp
    }

    @Composable
    open fun actionOverflowPadding(fileCardInfo: BasicCardControlInfo): PaddingValues {
        return PaddingValues(
            top = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80),
            end = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
        )
    }

    @Composable
    open fun textContainerPadding(fileCardInfo: BasicCardControlInfo): PaddingValues {
        return PaddingValues(all = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
    }
}