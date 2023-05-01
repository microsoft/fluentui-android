package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import kotlinx.parcelize.Parcelize

data class AnnouncementCardInfo(override val cardType: CardType = CardType.Elevated) :
    BasicCardControlInfo()

@Parcelize
open class AnnouncementCardTokens : BasicCardTokens(), Parcelable {
    @Composable
    open fun textColor(announcementCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun titleColor(announcementCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun titleSize(announcementCardInfo: BasicCardControlInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]
    }

    @Composable
    open fun textSize(announcementCardInfo: BasicCardControlInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun buttonColor(announcementCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun previewTitlePadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
    }

    @Composable
    open fun titleTextPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
    }

    @Composable
    open fun textButtonPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
    }

    @Composable
    open fun buttonPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun textHorizontalPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
    }

    @Composable
    open fun previewPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun previewCornerRadius(announcementCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)
    }

    @Composable
    override fun cornerRadius(announcementCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius80)
    }

    @Composable
    override fun elevation(announcementCardInfo: BasicCardControlInfo): Dp {
        return when (announcementCardInfo.cardType) {
            CardType.Elevated -> FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow64)
            CardType.Outlined -> 0.dp
        }
    }
}