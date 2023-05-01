package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
    open fun actionOverFlowBackgroundColor(fileCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
            themeMode = FluentTheme.themeMode
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
    open fun textSize(fileCardInfo: BasicCardControlInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun subTextSize(fileCardInfo: BasicCardControlInfo): TextStyle {
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
    open fun leadIconPadding(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120)
    }

    @Composable
    open fun iconTextPadding(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120)
    }

    @Composable
    open fun textSubTextPadding(fileCardInfo: BasicCardControlInfo): Dp {
        return 0.dp
    }

    @Composable
    open fun actionOverflowPadding(fileCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun textVerticalPadding(fileCardInfo: BasicCardControlInfo): PaddingValues {
        return PaddingValues(vertical = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
    }
}