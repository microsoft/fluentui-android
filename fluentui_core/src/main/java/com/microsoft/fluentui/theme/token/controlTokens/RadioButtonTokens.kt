package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.IControlToken
import com.microsoft.fluentui.theme.token.StateColor
import kotlinx.parcelize.Parcelize

data class RadioButtonInfo(
    val selected: Boolean = false,
) : ControlInfo

@Parcelize
open class RadioButtonTokens : IControlToken, Parcelable {

    open var innerCircleRadius = 5.dp
    open var outerCircleRadius = 10.dp
    open var strokeWidthInwards = 1.5.dp

    @Composable
    open fun backgroundColor(radioButtonInfo: RadioButtonInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                themeMode = themeMode
            ),
            focused = aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                themeMode = themeMode
            ),
            selectedFocused = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            selected = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            selectedPressed = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            selectedDisabled = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                themeMode = themeMode
            ),
            disabled = aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun iconColor(radioButtonInfo: RadioButtonInfo): StateColor {
        return StateColor(
            selected = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                themeMode = themeMode
            ),
            selectedFocused = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                themeMode = themeMode
            ),
            selectedPressed = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                themeMode = themeMode
            ),
            selectedDisabled = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled2].value(
                themeMode = themeMode
            )
        )
    }
}