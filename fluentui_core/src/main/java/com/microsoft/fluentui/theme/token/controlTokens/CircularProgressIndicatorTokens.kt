package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class CircularProgressIndicatorSize {
    XXSmall,
    XSmall,
    Medium,
    Large,
    XLarge
}

data class CircularProgressIndicatorInfo(
    val circularProgressIndicatorSize: CircularProgressIndicatorSize = CircularProgressIndicatorSize.XSmall,
    val style: FluentStyle = FluentStyle.Neutral
) : ControlInfo

@Parcelize
open class CircularProgressIndicatorTokens : ControlToken, Parcelable {

    @Composable
    open fun size(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Dp {
        return when (circularProgressIndicatorInfo.circularProgressIndicatorSize) {
            CircularProgressIndicatorSize.XXSmall -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize120)
            CircularProgressIndicatorSize.XSmall -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize160)
            CircularProgressIndicatorSize.Medium -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize240)
            CircularProgressIndicatorSize.Large -> 32.dp
            CircularProgressIndicatorSize.XLarge -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize400)
        }
    }

    @Composable
    open fun strokeWidth(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Dp {
        return when (circularProgressIndicatorInfo.circularProgressIndicatorSize) {
            CircularProgressIndicatorSize.XXSmall -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth10)
            CircularProgressIndicatorSize.XSmall -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth10)
            CircularProgressIndicatorSize.Medium -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth20)
            CircularProgressIndicatorSize.Large -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth30)
            CircularProgressIndicatorSize.XLarge -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth40)
        }
    }

    @Composable
    open fun color(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Color {
        return if (circularProgressIndicatorInfo.style == FluentStyle.Neutral) {
            FluentColor(
                light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey56),
                dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey72)
            ).value(
                themeMode = FluentTheme.themeMode
            )
        } else {
            FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = FluentTheme.themeMode
            )
        }
    }
}