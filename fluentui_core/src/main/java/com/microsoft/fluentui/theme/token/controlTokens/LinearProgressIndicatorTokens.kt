package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
import kotlinx.parcelize.Parcelize

enum class LinearProgressIndicatorHeight{
    XXXSmall
}
data class LinearProgressIndicatorInfo(
    val linearProgressIndicatorHeight: LinearProgressIndicatorHeight = LinearProgressIndicatorHeight.XXXSmall,
): ControlInfo

@Parcelize
open class LinearProgressIndicatorTokens: ControlToken, Parcelable{

    @Composable
    open fun strokeWidth(linearProgressIndicatorInfo: LinearProgressIndicatorInfo):Dp{
        return when(linearProgressIndicatorInfo.linearProgressIndicatorHeight){
            LinearProgressIndicatorHeight.XXXSmall -> GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.Thick)
        }
    }

    @Composable
    open fun backgroundColor(linearProgressIndicatorInfo: LinearProgressIndicatorInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )
    }
    @Composable
    open fun color(linearProgressIndicatorInfo: LinearProgressIndicatorInfo): Color {
        return FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
        themeMode = FluentTheme.themeMode
        )
    }

}