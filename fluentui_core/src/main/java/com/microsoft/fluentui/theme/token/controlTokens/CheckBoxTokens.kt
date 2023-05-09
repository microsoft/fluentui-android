package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

data class CheckBoxInfo(
    val checked: Boolean = false,
) : ControlInfo

@Parcelize
open class CheckBoxTokens : IControlToken, Parcelable {

    @IgnoredOnParcel
    val fixedSize: Dp = 20.dp

    @IgnoredOnParcel
    val fixedIconSize: Dp = 12.dp

    @IgnoredOnParcel
    val fixedBorderRadius: Dp = 4.dp

    @Composable
    open fun backgroundColor(checkBoxInfo: CheckBoxInfo): StateBrush {
        return StateBrush(
            selected = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                    themeMode = themeMode
                )
            ),
            selectedDisabled = SolidColor(
                aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                    themeMode = themeMode
                )
            )
        )
    }

    @Composable
    open fun iconColor(checkBoxInfo: CheckBoxInfo): StateColor {
        return StateColor(
            selected = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            ),
            selectedDisabled = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun borderStroke(checkBoxInfo: CheckBoxInfo): StateBorderStroke {
        return StateBorderStroke(
            rest = listOf(
                BorderStroke(
                    1.5.dp,
                    aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                        themeMode = themeMode
                    )
                )
            ),
            pressed = listOf(
                BorderStroke(
                    1.5.dp,
                    aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                        themeMode = themeMode
                    )
                )
            ),
            focused = listOf(
                BorderStroke(
                    1.5.dp,
                    aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                        themeMode = themeMode
                    )
                )
            ),
            selectedFocused = listOf(
                BorderStroke(
                    1.5.dp,
                    aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                        themeMode = themeMode
                    )
                )
            ),
            disabled = listOf(
                BorderStroke(
                    1.5.dp,
                    aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                        themeMode = themeMode
                    )
                )
            )
        )
    }
}