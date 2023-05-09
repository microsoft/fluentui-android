package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class SnackbarStyle {
    Neutral,
    Contrast,
    Accent,
    Warning,
    Danger
}

data class SnackBarInfo(
    val style: SnackbarStyle = SnackbarStyle.Neutral,
    val subTitleAvailable: Boolean = false
) : ControlInfo

@Parcelize
open class SnackBarTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundColor(snackBarInfo: SnackBarInfo): Brush {
        return SolidColor(
            when (snackBarInfo.style) {
                SnackbarStyle.Neutral -> aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background4].value()
                SnackbarStyle.Contrast -> aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundDarkStatic].value()
                SnackbarStyle.Accent -> aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value()
                SnackbarStyle.Warning -> aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.WarningBackground1].value()
                SnackbarStyle.Danger -> aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerBackground1].value()
            }
        )
    }

    @Composable
    open fun iconColor(snackBarInfo: SnackBarInfo): Color {
        return when (snackBarInfo.style) {
            SnackbarStyle.Neutral -> aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
            SnackbarStyle.Contrast -> aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
            SnackbarStyle.Accent -> aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
            SnackbarStyle.Warning -> aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.WarningForeground1].value()
            SnackbarStyle.Danger -> aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        }
    }

    @Composable
    open fun titleTypography(snackBarInfo: SnackBarInfo): TextStyle {
        val color: Color = when (snackBarInfo.style) {
            SnackbarStyle.Neutral -> aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
            SnackbarStyle.Contrast -> aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
            SnackbarStyle.Accent -> aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
            SnackbarStyle.Warning -> aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.WarningForeground1].value()
            SnackbarStyle.Danger -> aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        }

        return if (snackBarInfo.subTitleAvailable)
            aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong].merge(
                TextStyle(
                    color = color
                )
            )
        else
            aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2].merge(TextStyle(color = color))
    }

    @Composable
    open fun subtitleTypography(snackBarInfo: SnackBarInfo): TextStyle {
        val color: Color = when (snackBarInfo.style) {
            SnackbarStyle.Neutral -> aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
            SnackbarStyle.Contrast -> aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
            SnackbarStyle.Accent -> aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
            SnackbarStyle.Warning -> aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.WarningForeground1].value()
            SnackbarStyle.Danger -> aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        }

        return aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2].merge(
            TextStyle(
                color = color
            )
        )
    }

    @Composable
    open fun leftIconSize(snackBarInfo: SnackBarInfo): Dp = 24.dp

    @Composable
    open fun dismissIconSize(snackBarInfo: SnackBarInfo): Dp = 20.dp
}