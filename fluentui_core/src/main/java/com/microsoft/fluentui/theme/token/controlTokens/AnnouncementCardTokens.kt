package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.StateColor
import kotlinx.parcelize.Parcelize

data class AnnouncementCardInfo(
    override val cardType: CardType = CardType.Elevated,
    val buttonType: ButtonStyle = ButtonStyle.TextButton
) : BasicCardControlInfo()

@Parcelize
open class AnnouncementCardTokens : BasicCardTokens(), Parcelable {
    @Composable
    open fun textColor(announcementCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun titleColor(announcementCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun titleSize(announcementCardInfo: BasicCardControlInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1Strong]
    }

    @Composable
    open fun textSize(announcementCardInfo: BasicCardControlInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun previewTitlePadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size160)
    }

    @Composable
    open fun titleTextPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size40)
    }

    @Composable
    open fun textButtonPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size40)
    }

    @Composable
    open fun textHorizontalPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size160)
    }

    @Composable
    open fun previewPadding(announcementCardInfo: BasicCardControlInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun previewCornerRadius(announcementCardInfo: BasicCardControlInfo): Dp {
        return GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius40)
    }

    @Composable
    override fun cornerRadius(announcementCardInfo: BasicCardControlInfo): Dp {
        return GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius80)
    }

    @Composable
    override fun elevation(announcementCardInfo: BasicCardControlInfo): Dp {
        return when (announcementCardInfo.cardType) {
            CardType.Elevated -> GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow64)
            CardType.Outlined -> 0.dp
        }
    }

    @Composable
    open fun buttonTextColor(announcementCardInfo: BasicCardControlInfo): StateColor {
        announcementCardInfo as AnnouncementCardInfo
        return when (announcementCardInfo.buttonType) {
            ButtonStyle.Button -> StateColor()
            ButtonStyle.OutlinedButton -> StateColor()
            ButtonStyle.TextButton ->
                StateColor(
                    rest = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    pressed = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    selected = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1Selected].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    focused = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
        }
    }
}