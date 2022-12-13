package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode.Dark
import com.microsoft.fluentui.theme.ThemeMode.Light
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.AliasTokens.BrandBackgroundColorTokens.BrandBackground1
import com.microsoft.fluentui.theme.token.AliasTokens.BrandBackgroundColorTokens.BrandBackground2
import com.microsoft.fluentui.theme.token.AliasTokens.BrandBackgroundColorTokens.BrandBackground2Pressed
import com.microsoft.fluentui.theme.token.AliasTokens.BrandBackgroundColorTokens.BrandBackground2Selected
import com.microsoft.fluentui.theme.token.AliasTokens.BrandBackgroundColorTokens.BrandBackground3
import com.microsoft.fluentui.theme.token.AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint
import com.microsoft.fluentui.theme.token.AliasTokens.BrandForegroundColorTokens.BrandForegroundTint
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.DangerBackground1
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.DangerBackground2
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.DangerForeground1
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.SevereBackground1
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.SevereBackground2
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.SevereForeground1
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.SuccessBackground1
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.SuccessBackground2
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.SuccessForeground1
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.WarningBackground1
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.WarningBackground2
import com.microsoft.fluentui.theme.token.AliasTokens.ErrorAndStatusColorTokens.WarningForeground1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background5
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background5Pressed
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background5Selected
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background6
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.BackgroundInverted
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground2
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.ForegroundDarkStatic
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentStyle.Brand
import com.microsoft.fluentui.theme.token.FluentStyle.Neutral
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize.Medium
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize.Small
import kotlinx.parcelize.Parcelize

enum class PersonaChipStyle {
    Neutral,
    Brand,
    Danger,
    SevereWarning,
    Warning,
    Success
}

enum class PersonaChipSize {
    Small,
    Medium
}

data class PersonaChipInfo(
    val style: PersonaChipStyle = PersonaChipStyle.Neutral,
    val enabled: Boolean = true,
    val size: PersonaChipSize = Small
) : ControlInfo

@Parcelize
open class PersonaChipTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(personaChipInfo: PersonaChipInfo): StateColor {
        when (personaChipInfo.style) {
            PersonaChipStyle.Neutral -> return StateColor(
                rest = FluentTheme.aliasTokens.neutralBackgroundColor[Background5].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralBackgroundColor[Background5Selected].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralBackgroundColor[Background5].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Brand -> return StateColor(
                rest = FluentTheme.aliasTokens.brandBackgroundColor[BrandBackgroundTint].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.brandBackgroundColor[BrandBackground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralBackgroundColor[Background5].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Danger -> return StateColor(
                rest = FluentTheme.aliasTokens.ErrorAndStatusColor[DangerBackground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.ErrorAndStatusColor[DangerBackground2].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.SevereWarning -> return StateColor(
                rest = FluentTheme.aliasTokens.ErrorAndStatusColor[SevereBackground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.ErrorAndStatusColor[SevereBackground2].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Warning -> return StateColor(
                rest = FluentTheme.aliasTokens.ErrorAndStatusColor[WarningBackground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.ErrorAndStatusColor[WarningBackground2].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Success -> return StateColor(
                rest = FluentTheme.aliasTokens.ErrorAndStatusColor[SuccessBackground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.ErrorAndStatusColor[SuccessBackground2].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        }
    }

    @Composable
    open fun textColor(personaChipInfo: PersonaChipInfo): StateColor {
        when (personaChipInfo.style) {
            PersonaChipStyle.Neutral -> return StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[Foreground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDisable1].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Brand -> return StateColor(
                rest = FluentTheme.aliasTokens.brandForegroundColor[BrandForegroundTint].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundOnColor].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDisable1].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Danger -> return StateColor(
                rest = FluentTheme.aliasTokens.ErrorAndStatusColor[DangerForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundLightStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.SevereWarning -> return StateColor(
                rest = FluentTheme.aliasTokens.ErrorAndStatusColor[SevereForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundLightStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Warning -> return StateColor(
                rest = FluentTheme.aliasTokens.ErrorAndStatusColor[WarningForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDarkStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Success -> return StateColor(
                rest = FluentTheme.aliasTokens.ErrorAndStatusColor[SuccessForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundLightStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        }
    }

    @Composable
    open fun borderRadius(personaChipInfo: PersonaChipInfo):Dp{
        return when (personaChipInfo.size) {
            Small -> GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Small)
            Medium -> GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Medium)
        }
    }

    @Composable
    open fun fontSize(personaChipInfo: PersonaChipInfo): FontInfo {
        return when (personaChipInfo.size) {
            Small -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
            Medium -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body2]
        }
    }

    @Composable
    open fun verticalPadding(personaChipInfo: PersonaChipInfo): Dp{
        return when (personaChipInfo.size) {
            Small -> 2.dp
            Medium -> 2.dp
        }
    }

    @Composable
    open fun horizontalPadding(personaChipInfo: PersonaChipInfo):Dp{
        return when (personaChipInfo.size) {
            Small -> 4.dp
            Medium -> 8.dp
        }
    }

    @Composable
    open fun avatarToTextSpacing(personaChipInfo: PersonaChipInfo): Dp{
        return 8.dp
    }

    @Composable
    open fun avatarSize(personaChipInfo: PersonaChipInfo):AvatarSize{
        return AvatarSize.Size16
    }
}