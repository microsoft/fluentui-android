package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

data class CheckBoxInfo(
        val checked: Boolean = false,
) : ControlInfo

@Parcelize
open class CheckBoxTokens : ControlToken, Parcelable {

    companion object {
        const val Type: String = "Checkbox"
    }

    val fixedSize: Dp = 20.dp
    val fixedIconSize: Dp = 12.dp
    val fixedBorderRadius: Dp = 4.dp

    @Composable
    open fun backgroundColor(checkBoxInfo: CheckBoxInfo): StateColor {
        return when (checkBoxInfo.checked) {
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
            false -> StateColor()
        }
    }

    @Composable
    open fun iconColor(checkBoxInfo: CheckBoxInfo): StateColor {
        return when (checkBoxInfo.checked) {
            true -> StateColor(
                    rest = FluentTheme.aliasToken.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = FluentTheme.themeMode
                    ),
                    pressed = FluentTheme.aliasToken.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = FluentTheme.themeMode
                    ),
                    disabled = FluentTheme.aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundInvertedDisabled].value(
                            themeMode = FluentTheme.themeMode
                    )
            )
            false -> StateColor()
        }
    }

    @Composable
    open fun borderStroke(checkBoxInfo: CheckBoxInfo): StateBorderStroke {
        return when (checkBoxInfo.checked) {
            true -> StateBorderStroke()
            false -> StateBorderStroke(
                    rest = listOf(
                            BorderStroke(
                                    1.5.dp,
                                    FluentTheme.aliasToken.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                                            themeMode = FluentTheme.themeMode
                                    )
                            )
                    ),
                    pressed = listOf(
                            BorderStroke(
                                    1.5.dp,
                                    FluentTheme.aliasToken.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                                            themeMode = FluentTheme.themeMode
                                    )
                            )
                    ),
                    disabled = listOf(
                            BorderStroke(
                                    1.5.dp,
                                    FluentTheme.aliasToken.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                                            themeMode = FluentTheme.themeMode
                                    )
                            )
                    )
            )
        }
    }
}