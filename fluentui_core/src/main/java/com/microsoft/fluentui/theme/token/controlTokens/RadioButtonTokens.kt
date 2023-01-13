package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.StateColor
import kotlinx.parcelize.Parcelize

data class RadioButtonInfo(
    val selected: Boolean = false,
) : ControlInfo

@Parcelize
open class RadioButtonTokens : ControlToken, Parcelable {

    open var innerCircleRadius = 5.dp
    open var outerCircleRadius = 10.dp
    open var strokeWidthInwards = 1.5.dp

    @Composable
    open fun backgroundColor(radioButtonInfo: RadioButtonInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                themeMode = themeMode
            ),
            selected = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            selectedDisabled = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                themeMode = themeMode
            ),
            disabled = aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun iconColor(radioButtonInfo: RadioButtonInfo): StateColor {
        return StateColor(
            selected = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                themeMode = themeMode
            ),
            selectedDisabled = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                themeMode = themeMode
            )
        )
    }
}