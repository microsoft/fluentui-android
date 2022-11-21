package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class ProgressBarType{
    LinearProgressBar,
    CircularProgressBar,
    Shimmer
}
enum class CircularProgressBarIndicatorSize{
    XXSmall,
    XSmall,
    Medium,
    Large,
    XLarge
}
enum class LinearProgressBarHeight{
    XXXSmall
}
enum class ShimmerShape {
    Box,
    Circle
}
data class ProgressBarInfo(
    val progressBarType: ProgressBarType = ProgressBarType.LinearProgressBar,
    val circularProgressBarIndicatorSize: CircularProgressBarIndicatorSize = CircularProgressBarIndicatorSize.XSmall,
    val linearProgressBarHeight: LinearProgressBarHeight = LinearProgressBarHeight.XXXSmall,
    val neutralColor: Boolean = true
): ControlInfo

@Parcelize
open class ProgressBarTokens: ControlToken, Parcelable{

    @Composable
    open fun getCircularProgressBarIndicatorSize(progressBarInfo: ProgressBarInfo):Dp{
        return when(progressBarInfo.circularProgressBarIndicatorSize){
            CircularProgressBarIndicatorSize.XXSmall -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XXSmall).size
            CircularProgressBarIndicatorSize.XSmall -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XSmall).size
            CircularProgressBarIndicatorSize.Medium -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Medium).size
            CircularProgressBarIndicatorSize.Large -> 32.dp
            CircularProgressBarIndicatorSize.XLarge -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XLarge).size
        }
    }
    @Composable
    open fun getLinearProgressbarStrokeWidth(progressBarInfo: ProgressBarInfo):Dp{
        return when(progressBarInfo.linearProgressBarHeight){
            LinearProgressBarHeight.XXXSmall -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thick)
        }
    }
    @Composable
    open fun getCircularProgressbarStrokeWidth(progressBarInfo: ProgressBarInfo):Dp{
        return when(progressBarInfo.circularProgressBarIndicatorSize){
            CircularProgressBarIndicatorSize.XXSmall -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thinner)
            CircularProgressBarIndicatorSize.XSmall -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thinner)
            CircularProgressBarIndicatorSize.Medium -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thick)
            CircularProgressBarIndicatorSize.Large -> 3.dp
            CircularProgressBarIndicatorSize.XLarge -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thicker)
        }
    }
    @Composable
    open fun getProgressBarBackgroundColor(progressBarInfo: ProgressBarInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )
    }
    @Composable
    open fun getProgressBarIndicatorColor(progressBarInfo: ProgressBarInfo): Color {
        return when(progressBarInfo.progressBarType){
            ProgressBarType.LinearProgressBar -> FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = FluentTheme.themeMode
            )
            ProgressBarType.CircularProgressBar -> {
                if(progressBarInfo.neutralColor){
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey56),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey72)
                    ).value(
                        themeMode = FluentTheme.themeMode
                    )
                }else{
                    FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                }

            }
            ProgressBarType.Shimmer -> FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Stencil1].value(
                themeMode = FluentTheme.themeMode
            )
        }

    }
    @Composable
    open fun getShimmerCornerRadius(progressBarInfo: ProgressBarInfo): Dp{
        return GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Medium)
    }
    @Composable
    open fun getShimmerKnockoutEffectColor(progressBarInfo: ProgressBarInfo): Color{
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Stencil2].value(
            themeMode = FluentTheme.themeMode
        )
    }
}