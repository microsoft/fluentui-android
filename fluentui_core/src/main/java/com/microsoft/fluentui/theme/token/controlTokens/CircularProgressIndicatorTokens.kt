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

open class CircularProgressIndicatorInfo(
    val circularProgressIndicatorSize: CircularProgressIndicatorSize = CircularProgressIndicatorSize.XSmall,
    val style: FluentStyle = FluentStyle.Neutral
) : ControlInfo

@Parcelize
open class CircularProgressIndicatorTokens : IControlToken, Parcelable {

    @Composable
    open fun size(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Dp {
        return when (circularProgressIndicatorInfo.circularProgressIndicatorSize) {
            CircularProgressIndicatorSize.XXSmall -> FluentGlobalTokens.IconSizeTokens.IconSize120.value
            CircularProgressIndicatorSize.XSmall -> FluentGlobalTokens.IconSizeTokens.IconSize160.value
            CircularProgressIndicatorSize.Medium -> FluentGlobalTokens.IconSizeTokens.IconSize240.value
            CircularProgressIndicatorSize.Large -> 32.dp
            CircularProgressIndicatorSize.XLarge -> FluentGlobalTokens.IconSizeTokens.IconSize400.value
        }
    }

    @Composable
    open fun strokeWidth(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Dp {
        return when (circularProgressIndicatorInfo.circularProgressIndicatorSize) {
            CircularProgressIndicatorSize.XXSmall -> FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10
                .value
            CircularProgressIndicatorSize.XSmall -> FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10
                .value
            CircularProgressIndicatorSize.Medium -> FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20
                .value
            CircularProgressIndicatorSize.Large -> FluentGlobalTokens.StrokeWidthTokens.StrokeWidth30.value
            CircularProgressIndicatorSize.XLarge -> FluentGlobalTokens.StrokeWidthTokens.StrokeWidth40
                .value
        }
    }

    @Composable
    open fun brush(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Brush {
        return SolidColor(
            if (circularProgressIndicatorInfo.style == FluentStyle.Neutral) {
                FluentColor(
                    light = FluentGlobalTokens.NeutralColorTokens.Grey56.value,
                    dark = FluentGlobalTokens.NeutralColorTokens.Grey72.value
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