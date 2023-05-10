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

enum class TypographyTokens {
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

data class LabelInfo(
    val labelType: TypographyTokens
) : ControlInfo

@Parcelize
open class LabelTokens : IControlToken, Parcelable {
    @Composable
    fun typography(labelInfo: LabelInfo): TextStyle {
        return when (labelInfo.labelType) {
            TypographyTokens.Display -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Display]

            TypographyTokens.LargeTitle -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.LargeTitle]

            TypographyTokens.Title1 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1]

            TypographyTokens.Title2 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]

            TypographyTokens.Title3 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title3]

            TypographyTokens.Body1Strong -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]

            TypographyTokens.Body1 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]

            TypographyTokens.Body2Strong -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong]

            TypographyTokens.Body2 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]

            TypographyTokens.Caption1Strong -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1Strong]

            TypographyTokens.Caption1 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]

            TypographyTokens.Caption2 -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2]

        }
    }

    @Composable
    fun textColor(labelInfo: LabelInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }
}