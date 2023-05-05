package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
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
open class CircularProgressIndicatorTokens : IControlToken, Parcelable {

    @Composable
    open fun size(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Dp {
        return when (circularProgressIndicatorInfo.circularProgressIndicatorSize) {
            CircularProgressIndicatorSize.XXSmall -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize120)
            CircularProgressIndicatorSize.XSmall -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize160)
            CircularProgressIndicatorSize.Medium -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)
            CircularProgressIndicatorSize.Large -> 32.dp
            CircularProgressIndicatorSize.XLarge -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize400)
        }
    }

    @Composable
    open fun strokeWidth(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Dp {
        return when (circularProgressIndicatorInfo.circularProgressIndicatorSize) {
            CircularProgressIndicatorSize.XXSmall -> FluentGlobalTokens.strokeWidth(
                FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10
            )
            CircularProgressIndicatorSize.XSmall -> FluentGlobalTokens.strokeWidth(
                FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10
            )
            CircularProgressIndicatorSize.Medium -> FluentGlobalTokens.strokeWidth(
                FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20
            )
            CircularProgressIndicatorSize.Large -> FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth30)
            CircularProgressIndicatorSize.XLarge -> FluentGlobalTokens.strokeWidth(
                FluentGlobalTokens.StrokeWidthTokens.StrokeWidth40
            )
        }
    }

    @Composable
    open fun color(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Brush {
        return SolidColor(
            if (circularProgressIndicatorInfo.style == FluentStyle.Neutral) {
                FluentColor(
                    light = FluentGlobalTokens.neutralColor(FluentGlobalTokens.NeutralColorTokens.Grey56),
                    dark = FluentGlobalTokens.neutralColor(FluentGlobalTokens.NeutralColorTokens.Grey72)
                ).value(
                    themeMode = FluentTheme.themeMode
                )
            } else {
                FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = FluentTheme.themeMode
                )
            }
        )
    }
}