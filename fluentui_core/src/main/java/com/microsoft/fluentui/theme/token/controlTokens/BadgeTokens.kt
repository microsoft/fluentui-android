package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
import kotlinx.parcelize.Parcelize

enum class BadgeType {
    Character,
    List
}

class BadgeInfo(val type: BadgeType) : ControlInfo

@Parcelize
open class BadgeTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(badgeInfo: BadgeInfo): Color {
        return FluentTheme.aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.DangerBackground2].value()
    }

    @Composable
    open fun textColor(badgeInfo: BadgeInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
    }

    @Composable
    open fun typography(badgeInfo: BadgeInfo): TextStyle {
        return when (badgeInfo.type) {
            BadgeType.Character -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption2]
            BadgeType.List -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1Strong]
        }
    }

    @Composable
    open fun padding(badgeInfo: BadgeInfo): PaddingValues {
        return when (badgeInfo.type) {
            BadgeType.Character -> PaddingValues(
                horizontal = GlobalTokens.size(GlobalTokens.SizeTokens.Size60) + borderStroke(
                    badgeInfo
                ).width
            )
            BadgeType.List -> PaddingValues(
                start = GlobalTokens.size(GlobalTokens.SizeTokens.Size80) + borderStroke(badgeInfo).width,
                end = GlobalTokens.size(GlobalTokens.SizeTokens.Size80) + borderStroke(badgeInfo).width,
                top = 3.dp + borderStroke(badgeInfo).width,
                bottom = 3.dp + borderStroke(badgeInfo).width
            )
        }
    }

    @Composable
    open fun borderStroke(badgeInfo: BadgeInfo): BorderStroke {
        return BorderStroke(
            GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20),
            FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value()
        )
    }
}