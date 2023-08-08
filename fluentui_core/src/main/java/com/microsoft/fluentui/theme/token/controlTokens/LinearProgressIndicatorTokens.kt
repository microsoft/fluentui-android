package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class LinearProgressIndicatorHeight {
    XXXSmall
}

open class LinearProgressIndicatorInfo(
    val linearProgressIndicatorHeight: LinearProgressIndicatorHeight = LinearProgressIndicatorHeight.XXXSmall,
) : ControlInfo

@Parcelize
open class LinearProgressIndicatorTokens : IControlToken, Parcelable {

    @Composable
    open fun strokeWidth(linearProgressIndicatorInfo: LinearProgressIndicatorInfo): Dp {
        return when (linearProgressIndicatorInfo.linearProgressIndicatorHeight) {
            LinearProgressIndicatorHeight.XXXSmall -> FluentGlobalTokens.strokeWidth(
                FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20
            )
        }
    }

    @Composable
    open fun backgroundColor(linearProgressIndicatorInfo: LinearProgressIndicatorInfo): Color {
        return FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun color(linearProgressIndicatorInfo: LinearProgressIndicatorInfo): Color {
        return FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

}