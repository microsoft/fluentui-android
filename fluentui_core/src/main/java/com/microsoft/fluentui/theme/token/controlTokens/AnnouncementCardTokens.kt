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
import com.microsoft.fluentui.theme.token.StateColor
import kotlinx.parcelize.Parcelize

data class AnnouncementCardInfo(
    val buttonStyle: ButtonStyle = ButtonStyle.TextButton,
    val cardType: CardType = CardType.Elevated
)

@Parcelize
open class AnnouncementCardTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(announcementCardInfo: AnnouncementCardInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun textColor(announcementCardInfo: AnnouncementCardInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun titleColor(announcementCardInfo: AnnouncementCardInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun titleTypography(announcementCardInfo: AnnouncementCardInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]
    }

    @Composable
    open fun descriptionTypography(announcementCardInfo: AnnouncementCardInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun previewTextSPacing(announcementCardInfo: AnnouncementCardInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
    }

    @Composable
    open fun titleTextSpacing(announcementCardInfo: AnnouncementCardInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
    }

    @Composable
    open fun textButtonSpacing(announcementCardInfo: AnnouncementCardInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
    }

    @Composable
    open fun textHorizontalPadding(announcementCardInfo: AnnouncementCardInfo): PaddingValues {
        return PaddingValues(horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80))
    }

    @Composable
    open fun cardPadding(announcementCardInfo: AnnouncementCardInfo): PaddingValues {
        return PaddingValues(all = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80))
    }

    @Composable
    open fun previewCornerRadius(announcementCardInfo: AnnouncementCardInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)
    }

    @Composable
    open fun cornerRadius(announcementCardInfo: AnnouncementCardInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius80)
    }

    @Composable
    open fun elevation(announcementCardInfo: AnnouncementCardInfo): Dp {
        return when (announcementCardInfo.cardType) {
            CardType.Elevated -> FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow64)
            CardType.Outlined -> 0.dp
        }
    }

    @Composable
    open fun borderColor(announcementCardInfo: AnnouncementCardInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun borderStrokeWidth(announcementCardInfo: AnnouncementCardInfo): Dp {
        return FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05)
    }

    @Composable
    open fun buttonTextColor(announcementCardInfo: AnnouncementCardInfo): StateColor {
        return when (announcementCardInfo.buttonStyle) {
            ButtonStyle.Button -> StateColor()
            ButtonStyle.OutlinedButton -> StateColor()
            ButtonStyle.TextButton ->
                StateColor(
                    rest = FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    pressed = FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    selected = FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Selected].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    focused = FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    disabled = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
        }
    }
}