package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.AliasTokens.BrandForegroundColorTokens.BrandForeground1
import com.microsoft.fluentui.theme.token.AliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed
import com.microsoft.fluentui.theme.token.AliasTokens.BrandForegroundColorTokens.BrandForeground1Selected
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1Pressed
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground2
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground3
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralStrokeColorTokens.Stroke2
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Body1
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Body2
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Caption1
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.GlobalTokens.BorderSizeTokens.Thick
import com.microsoft.fluentui.theme.token.GlobalTokens.BorderSizeTokens.Thin
import com.microsoft.fluentui.theme.token.GlobalTokens.IconSizeTokens
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.Large
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.Medium
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.Small
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.XSmall
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.XXSmall
import com.microsoft.fluentui.theme.token.IconSize
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.AccessoryText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.LabelText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.SubLabelText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.Text
import kotlinx.parcelize.Parcelize

enum class ListTextType {
    Text,
    AccessoryText,
    LabelText,
    SubLabelText
}
enum class ListAccessoryType {
    Button,
    RadioButton,
    Checkbox,
    ToggleSwitch,
    Icon,
    Avatar
}

@Parcelize
open class ListItemTokens : ControlToken, Parcelable {
    @Composable
    open fun backgroundColor(): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralBackgroundColor[Background1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralBackgroundColor[Background1Pressed].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun borderColor(): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralStrokeColor[Stroke2].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralStrokeColor[Stroke2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun iconColor(): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground3].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun textColor(textType: ListTextType): StateColor {
        return when (textType) {
            Text ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                )

            AccessoryText ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            LabelText ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            SubLabelText ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
        }
    }

    @Composable
    open fun padding(): PaddingValues {
        return PaddingValues(
                    horizontal = FluentTheme.globalTokens.spacing[Medium],
                    vertical = FluentTheme.globalTokens.spacing[Small]
                )

    }

    @Composable
    open fun borderSize(): Dp {
        return FluentTheme.globalTokens.borderSize[Thin]
    }

    @Composable
    open fun textSize(textType: ListTextType): FontInfo {
        return when (textType) {
            Text -> FluentTheme.aliasTokens.typography[Body1]
            AccessoryText -> FluentTheme.aliasTokens.typography[Body1]
            SubLabelText -> FluentTheme.aliasTokens.typography[Caption1]
            //Recheck
            LabelText -> FluentTheme.aliasTokens.typography[Body2]
        }
    }

    @Composable
    open fun iconSize(): IconSize {
        return FluentTheme.globalTokens.iconSize[IconSizeTokens.Medium]
    }

    @Composable
    open fun spacing(accessoryType: ListAccessoryType): Dp {
        return when (accessoryType) {
            ListAccessoryType.Button -> FluentTheme.globalTokens.spacing[XSmall]
            ListAccessoryType.Checkbox -> FluentTheme.globalTokens.spacing[Medium]
            ListAccessoryType.ToggleSwitch -> FluentTheme.globalTokens.spacing[Medium]
            ListAccessoryType.RadioButton -> FluentTheme.globalTokens.spacing[Medium]
            ListAccessoryType.Icon -> FluentTheme.globalTokens.spacing[Medium]
            ListAccessoryType.Avatar -> FluentTheme.globalTokens.spacing[Medium]
        }
    }
}