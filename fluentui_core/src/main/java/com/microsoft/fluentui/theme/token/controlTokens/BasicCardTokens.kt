package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class CardType {
    Elevated,
    Outlined
}

open class BasicCardInfo(val cardType: CardType = CardType.Elevated) :
    ControlInfo

@Parcelize
open class BasicCardTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(basicCardInfo: BasicCardInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun cornerRadius(basicCardInfo: BasicCardInfo): Dp {
        return FluentGlobalTokens.CornerRadiusTokens.CornerRadius120.value
    }

    @Composable
    open fun elevation(basicCardInfo: BasicCardInfo): Dp {
        return when (basicCardInfo.cardType) {
            CardType.Elevated -> FluentGlobalTokens.ShadowTokens.Shadow02.value
            CardType.Outlined -> 0.dp
        }
    }

    @Composable
    open fun borderColor(basicCardInfo: BasicCardInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun borderStrokeWidth(basicCardInfo: BasicCardInfo): Dp {
        return FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05.value
    }
}