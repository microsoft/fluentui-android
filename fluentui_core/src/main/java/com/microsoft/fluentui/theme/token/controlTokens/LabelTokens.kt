package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class TextType {
    Display,
    LargeTitle,
    Title1,
    Title2,
    Title3,
    Body1Strong,
    Body1,
    Body2Strong,
    Body2,
    Caption1Strong,
    Caption1,
    Caption2
}

enum class ColorStyle {
    Primary,
    Secondary,
    White,
    Brand,
    Error
}

data class LabelInfo(
    val labelType: TextType,
    val colorStyle: ColorStyle
) : ControlInfo

@Parcelize
open class LabelTokens : IControlToken, Parcelable {
    @Composable
    fun typography(labelInfo: LabelInfo): TextStyle {
        return when (labelInfo.labelType) {
            TextType.Display -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Display]

            TextType.LargeTitle -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.LargeTitle]

            TextType.Title1 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1]

            TextType.Title2 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]

            TextType.Title3 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title3]

            TextType.Body1Strong -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]

            TextType.Body1 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]

            TextType.Body2Strong -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong]

            TextType.Body2 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]

            TextType.Caption1Strong -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1Strong]

            TextType.Caption1 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]

            TextType.Caption2 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2]

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