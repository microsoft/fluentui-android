package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

data class RadioButtonInfo(
    val selected: Boolean = false,
) : ControlInfo

@Parcelize
open class RadioButtonTokens : IControlToken, Parcelable {

    @IgnoredOnParcel
    open var innerCircleRadius = 5.dp

    @IgnoredOnParcel
    open var outerCircleRadius = 10.dp

    @IgnoredOnParcel
    open var strokeWidthInwards = 1.5.dp

    @Composable
    open fun backgroundColor(radioButtonInfo: RadioButtonInfo): StateBrush {
        return StateBrush(
            rest = SolidColor(
                aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                    themeMode = themeMode
                )
            ),
            focused = SolidColor(
                aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                    themeMode = themeMode
                )
            ),
            selectedFocused = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = themeMode
                )
            ),
            selected = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = themeMode
                )
            ),
            selectedPressed = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = themeMode
                )
            ),
            pressed = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = themeMode
                )
            ),
            selectedDisabled = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                    themeMode = themeMode
                )
            ),
            disabled = SolidColor(
                aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                    themeMode = themeMode
                )
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