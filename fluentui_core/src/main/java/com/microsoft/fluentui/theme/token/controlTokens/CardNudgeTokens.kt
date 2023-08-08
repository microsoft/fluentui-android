package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

open class CardNudgeInfo : ControlInfo


@Parcelize
open class CardNudgeTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(cardNudgeInfo: CardNudgeInfo): Brush {
        return SolidColor(aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.CanvasBackground].value())
    }

    @Composable
    open fun iconColor(cardNudgeInfo: CardNudgeInfo): Color {
        return aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
    }

    @Composable
    open fun accentColor(cardNudgeInfo: CardNudgeInfo): Color {
        return aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
    }

    @Composable
    open fun iconBackgroundBrush(cardNudgeInfo: CardNudgeInfo): Brush {
        return SolidColor(aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value())
    }

    @Composable
    open fun titleTypography(cardNudgeInfo: CardNudgeInfo): TextStyle {
        return aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong].merge(
            TextStyle(
                color = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
            )
        )
    }

    @Composable
    open fun accentTypography(cardNudgeInfo: CardNudgeInfo): TextStyle {
        return aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1].merge(
            TextStyle(
                color = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
            )
        )
    }

    @Composable
    open fun subtitleTypography(cardNudgeInfo: CardNudgeInfo): TextStyle {
        return aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1].merge(
            TextStyle(
                color = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
            )
        )
    }

    @Composable
    open fun borderStrokeColor(cardNudgeInfo: CardNudgeInfo): Color =
        aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value()

    @Composable
    open fun borderSize(cardNudgeInfo: CardNudgeInfo): Dp =
        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10)

    @Composable
    open fun leftIconSize(cardNudgeInfo: CardNudgeInfo): Dp = 24.dp

    @Composable
    open fun leftIconBackgroundSize(cardNudgeInfo: CardNudgeInfo): Dp = 40.dp

    @Composable
    open fun dismissIconSize(cardNudgeInfo: CardNudgeInfo): Dp = 20.dp

}