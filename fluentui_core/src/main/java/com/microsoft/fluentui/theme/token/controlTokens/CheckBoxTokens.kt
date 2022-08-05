package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
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
                    rest = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            themeMode = themeMode
                    ),
                    pressed = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            themeMode = themeMode
                    ),
                    disabled = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                            themeMode = themeMode
                    )
            )
            false -> StateColor()
        }
    }

    @Composable
    open fun iconColor(checkBoxInfo: CheckBoxInfo): StateColor {
        return when (checkBoxInfo.checked) {
            true -> StateColor(
                    rest = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = themeMode
                    ),
                    pressed = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                            themeMode = themeMode
                    ),
                    disabled = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundInvertedDisabled].value(
                            themeMode = themeMode
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
                                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                                            themeMode = themeMode
                                    )
                            )
                    ),
                    pressed = listOf(
                            BorderStroke(
                                    1.5.dp,
                                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeAccessible].value(
                                            themeMode = themeMode
                                    )
                            )
                    ),
                    disabled = listOf(
                            BorderStroke(
                                    1.5.dp,
                                    aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeDisabled].value(
                                            themeMode = themeMode
                                    )
                            )
                    )
            )
        }
    }
}