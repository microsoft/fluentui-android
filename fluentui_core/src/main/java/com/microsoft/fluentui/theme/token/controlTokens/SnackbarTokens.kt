package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
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
open class SnackBarTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(snackBarInfo: SnackBarInfo): Color {
        return when (snackBarInfo.style) {
            SnackbarStyle.Neutral -> aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background4].value()
            SnackbarStyle.Contrast -> aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.BackgroundDarkStatic].value()
            SnackbarStyle.Accent -> aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value()
            SnackbarStyle.Warning -> aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.WarningBackground1].value()
            SnackbarStyle.Danger -> aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.DangerBackground1].value()
        }
    }

    @Composable
    open fun iconColor(snackBarInfo: SnackBarInfo): Color {
        return when (snackBarInfo.style) {
            SnackbarStyle.Neutral -> aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()
            SnackbarStyle.Contrast -> aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
            SnackbarStyle.Accent -> aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
            SnackbarStyle.Warning -> aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.WarningForeground1].value()
            SnackbarStyle.Danger -> aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        }
    }

    @Composable
    open fun titleTypography(snackBarInfo: SnackBarInfo): TextStyle {
        val color: Color = when (snackBarInfo.style) {
            SnackbarStyle.Neutral -> aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()
            SnackbarStyle.Contrast -> aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
            SnackbarStyle.Accent -> aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
            SnackbarStyle.Warning -> aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.WarningForeground1].value()
            SnackbarStyle.Danger -> aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        }

        return if (snackBarInfo.subTitleAvailable)
            aliasTokens.typography[AliasTokens.TypographyTokens.Body2Strong].merge(
                TextStyle(
                    color = color
                )
            )
        else
            aliasTokens.typography[AliasTokens.TypographyTokens.Body2].merge(TextStyle(color = color))
    }

    @Composable
    open fun subtitleTypography(snackBarInfo: SnackBarInfo): TextStyle {
        val color: Color = when (snackBarInfo.style) {
            SnackbarStyle.Neutral -> aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()
            SnackbarStyle.Contrast -> aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
            SnackbarStyle.Accent -> aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
            SnackbarStyle.Warning -> aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.WarningForeground1].value()
            SnackbarStyle.Danger -> aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        }

        return aliasTokens.typography[AliasTokens.TypographyTokens.Body2].merge(TextStyle(color = color))
    }

    @Composable
    open fun leftIconSize(snackBarInfo: SnackBarInfo): Dp = 24.dp

    @Composable
    open fun dismissIconSize(snackBarInfo: SnackBarInfo): Dp = 20.dp
}