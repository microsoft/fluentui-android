package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
import kotlinx.parcelize.Parcelize

enum class ProgressBarType{
    LinearProgressBar,
    CircularProgressBar
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
data class ProgressBarInfo(
    val progressBarType: ProgressBarType = ProgressBarType.LinearProgressBar,
    val circularProgressBarIndicatorSize: CircularProgressBarIndicatorSize = CircularProgressBarIndicatorSize.XSmall,
    val linearProgressBarHeight: LinearProgressBarHeight = LinearProgressBarHeight.XXXSmall
): ControlInfo

@Parcelize
open class ProgressBarTokens: ControlToken, Parcelable{

    @Composable
    open fun getCircularProgressBarIndicatorSize(progressBarInfo: ProgressBarInfo):Dp{
        return when(progressBarInfo.circularProgressBarIndicatorSize){
            CircularProgressBarIndicatorSize.XXSmall -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XXSmall).size
            CircularProgressBarIndicatorSize.XSmall -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XSmall).size
            CircularProgressBarIndicatorSize.Medium -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Medium).size
            CircularProgressBarIndicatorSize.Large -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Large).size
            CircularProgressBarIndicatorSize.XLarge -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.XLarge).size
        }
    }
    @Composable
    open fun getLinearProgressBarHeight(progressBarInfo: ProgressBarInfo):Dp{
        return when(progressBarInfo.linearProgressBarHeight){
            LinearProgressBarHeight.XXXSmall -> 2.dp
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
            ProgressBarType.CircularProgressBar -> FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                themeMode = FluentTheme.themeMode
            )
        }

    }
}