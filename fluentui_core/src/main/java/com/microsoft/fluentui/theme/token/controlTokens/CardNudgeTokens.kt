package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
import kotlinx.parcelize.Parcelize

class CardNudgeInfo : ControlInfo


@Parcelize
open class CardNudgeTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(cardNudgeInfo: CardNudgeInfo): Color {
        return aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.CanvasBackground].value()
    }

    @Composable
    open fun iconColor(cardNudgeInfo: CardNudgeInfo): Color {
        return aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
    }

    @Composable
    open fun accentColor(cardNudgeInfo: CardNudgeInfo): Color {
        return aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
    }

    @Composable
    open fun iconBackgroundColor(cardNudgeInfo: CardNudgeInfo): Color {
        return aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value()
    }

    @Composable
    open fun titleTypography(cardNudgeInfo: CardNudgeInfo): TextStyle {
        return aliasTokens.typography[AliasTokens.TypographyTokens.Body2Strong].merge(
            TextStyle(
                color = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
            )
        )
    }

    @Composable
    open fun accentTypography(cardNudgeInfo: CardNudgeInfo): TextStyle {
        return aliasTokens.typography[AliasTokens.TypographyTokens.Caption1].merge(
            TextStyle(
                color = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
            )
        )
    }

    @Composable
    open fun subtitleTypography(cardNudgeInfo: CardNudgeInfo): TextStyle {
        return aliasTokens.typography[AliasTokens.TypographyTokens.Caption1].merge(
            TextStyle(
                color = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()
            )
        )
    }

    @Composable
    open fun borderStrokeColor(cardNudgeInfo: CardNudgeInfo): Color =
        aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke2].value()

    @Composable
    open fun borderSize(cardNudgeInfo: CardNudgeInfo): Dp =
        GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth10)

    @Composable
    open fun leftIconSize(cardNudgeInfo: CardNudgeInfo): Dp = 24.dp

    @Composable
    open fun leftIconBackgroundSize(cardNudgeInfo: CardNudgeInfo): Dp = 40.dp

    @Composable
    open fun dismissIconSize(cardNudgeInfo: CardNudgeInfo): Dp = 20.dp

}