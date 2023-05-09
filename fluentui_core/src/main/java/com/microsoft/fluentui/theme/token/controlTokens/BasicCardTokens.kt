package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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

abstract class BasicCardControlInfo : ControlInfo {
    abstract val cardType: CardType
}

data class BasicCardInfo(override val cardType: CardType = CardType.Elevated) :
    BasicCardControlInfo()

@Parcelize
open class BasicCardTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundColor(basicCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun cornerRadius(basicCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius120)
    }

    @Composable
    open fun elevation(basicCardInfo: BasicCardControlInfo): Dp {
        return when (basicCardInfo.cardType) {
            CardType.Elevated -> FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow02)
            CardType.Outlined -> 0.dp
        }
    }

    @Composable
    open fun borderColor(basicCardInfo: BasicCardControlInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun borderStrokeWidth(basicCardInfo: BasicCardControlInfo): Dp {
        return FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05)
    }
}