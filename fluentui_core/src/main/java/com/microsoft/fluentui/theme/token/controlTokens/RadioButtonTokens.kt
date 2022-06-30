package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
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

    companion object {
        const val Type: String = "Checkbox"
    }

    open var innerCircleRadius = 5.dp
    open var outerCircleRadius = 10.dp
    open var strokeWidthInwards = 1.5.dp

    @Composable
    open fun backgroundColor(radioButtonInfo: RadioButtonInfo): StateColor {
        return when (radioButtonInfo.selected) {
            true -> StateColor(
                    rest = FluentTheme.aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            themeMode = FluentTheme.themeMode
                    ),
                    pressed = FluentTheme.aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            themeMode = FluentTheme.themeMode
                    ),
                    disabled = FluentTheme.aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                            themeMode = FluentTheme.themeMode
                    )
            )
            false -> StateColor(
                    rest = FluentTheme.aliasToken.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                            themeMode = FluentTheme.themeMode
                    ),
                    disabled = FluentTheme.aliasToken.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                            themeMode = FluentTheme.themeMode
                    )
            )
        }
    }

    @Composable
    open fun iconColor(radioButtonInfo: RadioButtonInfo): StateColor {
        return when (radioButtonInfo.selected) {
            true -> StateColor(
                    rest = FluentTheme.aliasToken.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                            themeMode = FluentTheme.themeMode
                    ),
                    disabled = FluentTheme.aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                            themeMode = FluentTheme.themeMode
                    )
            )
            false -> StateColor()
        }
    }
}