package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class ColorStyle {
    Primary,
    Secondary,
    White,
    Brand,
    Error
}

open class LabelInfo(
    val labelType: TypographyTokens,
    val colorStyle: ColorStyle
) : ControlInfo

@Parcelize
open class LabelTokens : IControlToken, Parcelable {
    @Composable
    fun typography(labelInfo: LabelInfo): TextStyle {
        return when (labelInfo.labelType) {
            TypographyTokens.Display -> FluentTheme.aliasTokens.typography[TypographyTokens.Display]

            TypographyTokens.LargeTitle -> FluentTheme.aliasTokens.typography[TypographyTokens.LargeTitle]

            TypographyTokens.Title1 -> FluentTheme.aliasTokens.typography[TypographyTokens.Title1]

            TypographyTokens.Title2 -> FluentTheme.aliasTokens.typography[TypographyTokens.Title2]

            TypographyTokens.Title3 -> FluentTheme.aliasTokens.typography[TypographyTokens.Title3]

            TypographyTokens.Body1Strong -> FluentTheme.aliasTokens.typography[TypographyTokens.Body1Strong]

            TypographyTokens.Body1 -> FluentTheme.aliasTokens.typography[TypographyTokens.Body1]

            TypographyTokens.Body2Strong -> FluentTheme.aliasTokens.typography[TypographyTokens.Body2Strong]

            TypographyTokens.Body2 -> FluentTheme.aliasTokens.typography[TypographyTokens.Body2]

            TypographyTokens.Caption1Strong -> FluentTheme.aliasTokens.typography[TypographyTokens.Caption1Strong]

            TypographyTokens.Caption1 -> FluentTheme.aliasTokens.typography[TypographyTokens.Caption1]

            TypographyTokens.Caption2 -> FluentTheme.aliasTokens.typography[TypographyTokens.Caption2]

        }
    }

    @Composable
    fun textColor(labelInfo: LabelInfo): Color {
        return when (labelInfo.colorStyle) {
            ColorStyle.Primary -> FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            )
            ColorStyle.Secondary -> FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                themeMode = FluentTheme.themeMode
            )
            ColorStyle.White -> FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
                themeMode = FluentTheme.themeMode
            )
            ColorStyle.Brand -> FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                themeMode = FluentTheme.themeMode
            )
            ColorStyle.Error -> FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value(
                themeMode = FluentTheme.themeMode
            )
        }
    }
}