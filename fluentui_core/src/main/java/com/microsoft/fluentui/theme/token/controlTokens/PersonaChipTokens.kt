package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint
import com.microsoft.fluentui.theme.token.FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint
import com.microsoft.fluentui.theme.token.FluentAliasTokens.ErrorAndStatusColorTokens.*
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background5
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background5Selected
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralForegroundColorTokens.*
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

abstract class PersonaChipControlInfo : ControlInfo {
    abstract val size: PersonaChipSize
    abstract val enabled: Boolean
}

open class PersonaChipInfo(
    val style: PersonaChipStyle = PersonaChipStyle.Neutral,
    override val enabled: Boolean = true,
    override val size: PersonaChipSize = Small
) : PersonaChipControlInfo()

@Parcelize
open class PersonaChipTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(personaChipInfo: PersonaChipControlInfo): StateBrush {
        personaChipInfo as PersonaChipInfo
        when (personaChipInfo.style) {
            PersonaChipStyle.Neutral -> return StateBrush(
                rest = SolidColor(
                    FluentTheme.aliasTokens.neutralBackgroundColor[Background5].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                selected = SolidColor(
                    FluentTheme.aliasTokens.neutralBackgroundColor[Background5Selected].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                disabled = SolidColor(
                    FluentTheme.aliasTokens.neutralBackgroundColor[Background5].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            )
            PersonaChipStyle.Brand -> return StateBrush(
                rest = SolidColor(
                    FluentTheme.aliasTokens.brandBackgroundColor[BrandBackgroundTint].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                selected = SolidColor(
                    FluentTheme.aliasTokens.brandBackgroundColor[BrandBackground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                disabled = SolidColor(
                    FluentTheme.aliasTokens.neutralBackgroundColor[Background5].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            )
            PersonaChipStyle.Danger -> return StateBrush(
                rest = SolidColor(
                    FluentTheme.aliasTokens.errorAndStatusColor[DangerBackground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                selected = SolidColor(
                    FluentTheme.aliasTokens.errorAndStatusColor[DangerBackground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            )
            PersonaChipStyle.SevereWarning -> return StateBrush(
                rest = SolidColor(
                    FluentTheme.aliasTokens.errorAndStatusColor[SevereBackground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                selected = SolidColor(
                    FluentTheme.aliasTokens.errorAndStatusColor[SevereBackground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            )
            PersonaChipStyle.Warning -> return StateBrush(
                rest = SolidColor(
                    FluentTheme.aliasTokens.errorAndStatusColor[WarningBackground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                selected = SolidColor(
                    FluentTheme.aliasTokens.errorAndStatusColor[WarningBackground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            )
            PersonaChipStyle.Success -> return StateBrush(
                rest = SolidColor(
                    FluentTheme.aliasTokens.errorAndStatusColor[SuccessBackground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                selected = SolidColor(
                    FluentTheme.aliasTokens.errorAndStatusColor[SuccessBackground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            )
        }
    }

    @Composable
    open fun textColor(personaChipInfo: PersonaChipControlInfo): StateColor {

        personaChipInfo as PersonaChipInfo
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
                rest = FluentTheme.aliasTokens.errorAndStatusColor[DangerForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundLightStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.SevereWarning -> return StateColor(
                rest = FluentTheme.aliasTokens.errorAndStatusColor[SevereForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundLightStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Warning -> return StateColor(
                rest = FluentTheme.aliasTokens.errorAndStatusColor[WarningForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDarkStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            PersonaChipStyle.Success -> return StateColor(
                rest = FluentTheme.aliasTokens.errorAndStatusColor[SuccessForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundLightStatic].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        }
    }

    @Composable
    open fun cornerRadius(personaChipInfo: PersonaChipControlInfo): Dp {
        return when (personaChipInfo.size) {
            Small -> FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius20)
            Medium -> FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius40)
        }
    }

    @Composable
    open fun typography(personaChipInfo: PersonaChipControlInfo): TextStyle {
        return when (personaChipInfo.size) {
            Small -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]
            Medium -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
        }
    }

    @Composable
    open fun verticalPadding(personaChipInfo: PersonaChipControlInfo): Dp {
        return when (personaChipInfo.size) {
            Small -> FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size20)
            Medium -> FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size20)
        }
    }

    @Composable
    open fun horizontalPadding(personaChipInfo: PersonaChipControlInfo): Dp {
        return when (personaChipInfo.size) {
            Small -> FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
            Medium -> FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
        }
    }

    @Composable
    open fun avatarToTextSpacing(personaChipInfo: PersonaChipControlInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun avatarSize(personaChipInfo: PersonaChipControlInfo): AvatarSize {
        return AvatarSize.Size16
    }
}