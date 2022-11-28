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
enum class CircularProgressIndicatorColor {
    Neutral,
    Brand
}

data class CircularProgressIndicatorInfo(
    val circularProgressIndicatorSize: CircularProgressIndicatorSize = CircularProgressIndicatorSize.XSmall,
    val neutralColor: Boolean = true
) : ControlInfo

@Parcelize
open class CircularProgressIndicatorTokens : ControlToken, Parcelable {

    @Composable
    open fun getCircularProgressIndicatorSize(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Dp {
        return when (circularProgressIndicatorInfo.circularProgressIndicatorSize) {
            CircularProgressIndicatorSize.XXSmall -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XXSmall).size
            CircularProgressIndicatorSize.XSmall -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XSmall).size
            CircularProgressIndicatorSize.Medium -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Medium).size
            CircularProgressIndicatorSize.Large -> 32.dp
            CircularProgressIndicatorSize.XLarge -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XLarge).size
        }
    }

    @Composable
    open fun getCircularProgressIndicatorStrokeWidth(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Dp {
        return when (circularProgressIndicatorInfo.circularProgressIndicatorSize) {
            CircularProgressIndicatorSize.XXSmall -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thinner)
            CircularProgressIndicatorSize.XSmall -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thinner)
            CircularProgressIndicatorSize.Medium -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thick)
            CircularProgressIndicatorSize.Large -> 3.dp
            CircularProgressIndicatorSize.XLarge -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thicker)
        }
    }

    @Composable
    open fun getCircularProgressIndicatorColor(circularProgressIndicatorInfo: CircularProgressIndicatorInfo): Color {
        return if (circularProgressIndicatorInfo.neutralColor) {
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