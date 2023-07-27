package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class BadgeType {
    Character,
    List
}

class BadgeInfo(val type: BadgeType) : ControlInfo

@Parcelize
open class BadgeTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(badgeInfo: BadgeInfo): Brush {
        return SolidColor(FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerBackground2].value())
    }

    @Composable
    open fun textColor(badgeInfo: BadgeInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
    }

    @Composable
    open fun typography(badgeInfo: BadgeInfo): TextStyle {
        return when (badgeInfo.type) {
            BadgeType.Character -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2]
            BadgeType.List -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1Strong]
        }
    }

    @Composable
    open fun padding(badgeInfo: BadgeInfo): PaddingValues {
        return when (badgeInfo.type) {
            BadgeType.Character -> PaddingValues(
                horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size60) + borderStroke(
                    badgeInfo
                ).width
            )

            BadgeType.List -> PaddingValues(
                start = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80) + borderStroke(
                    badgeInfo
                ).width,
                end = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80) + borderStroke(
                    badgeInfo
                ).width,
                top = 3.dp + borderStroke(badgeInfo).width,
                bottom = 3.dp + borderStroke(badgeInfo).width
            )
        }
    }

    @Composable
    open fun borderStroke(badgeInfo: BadgeInfo): BorderStroke {
        return BorderStroke(
            FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20),
            FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value()
        )
    }

    @Composable
    open fun cornerRadius(badgeInfo: BadgeInfo): Dp = 100.dp
}