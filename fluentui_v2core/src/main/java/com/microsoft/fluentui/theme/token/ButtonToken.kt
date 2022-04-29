//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme.globalTokens
import com.microsoft.fluentui.theme.FluentTheme.aliasToken
enum class ButtonType{
    Button,
    OutlinedButton,
    TextButton
}
enum class ButtonStyle{
    Primary,
    Secondary,
    Tertiary
}
enum class ButtonSize{
    Small,
    Medium,
    Large
}
open class ButtonToken(var style:ButtonStyle =  ButtonStyle.Primary, var size:ButtonSize =  ButtonSize.Medium ) : ControlTokens {

    companion object {
        const val Type:String = "Button"
    }

    @Composable
    open public fun background() : Color {
        return if (isSystemInDarkTheme())
            aliasToken.backgroundColors[AliasTokens.BackgroundColorsTokens.brandRest].dark
        else
            aliasToken.backgroundColors[AliasTokens.BackgroundColorsTokens.brandRest].light
    }

    @Composable
    public fun disabledBackgroundColor() : Color {
        return if (isSystemInDarkTheme())
            aliasToken.backgroundColors[AliasTokens.BackgroundColorsTokens.brandDisabled].dark
        else
            aliasToken.backgroundColors[AliasTokens.BackgroundColorsTokens.brandDisabled].light
    }

    @Composable
    open public fun contentColor() : Color {
        return if (isSystemInDarkTheme())
            aliasToken.foregroundColors[AliasTokens.ForegroundColorsTokens.neutral1].dark
        else
            aliasToken.foregroundColors[AliasTokens.ForegroundColorsTokens.neutral1].light
    }

    @Composable
    public fun disabledContentColor() : Color {
        return if (isSystemInDarkTheme())
            aliasToken.backgroundColors[AliasTokens.BackgroundColorsTokens.brandDisabled].dark
        else
            aliasToken.backgroundColors[AliasTokens.BackgroundColorsTokens.brandDisabled].light
    }

    @Composable
    public fun borderStrokeThickness(): Dp {
        return when (style) {
            ButtonStyle.Primary, ButtonStyle.Secondary -> {
                when (size) {
                    ButtonSize.Small -> globalTokens.borderSize[GlobalTokens.BorderSizeToken.thick]
                    ButtonSize.Medium -> globalTokens.borderSize[GlobalTokens.BorderSizeToken.thicker]
                    ButtonSize.Large -> globalTokens.borderSize[GlobalTokens.BorderSizeToken.thickest]
                }
            }
            ButtonStyle.Tertiary -> {
                when (size) {
                    ButtonSize.Small ->  globalTokens.borderSize[GlobalTokens.BorderSizeToken.none]
                    ButtonSize.Medium -> globalTokens.borderSize[GlobalTokens.BorderSizeToken.none]
                    ButtonSize.Large -> globalTokens.borderSize[GlobalTokens.BorderSizeToken.none]
                }
            }
        }
    }

    @Composable
    public fun borderStrokeColor(): Color{
        return if (isSystemInDarkTheme())
             aliasToken.strokeColors[AliasTokens.StrokeColorsTokens.neutral1].light
        else
            aliasToken.strokeColors[AliasTokens.StrokeColorsTokens.neutral1].dark
    }

        @Composable
        public fun textFont(): FontInfo {
            return when (style) {
                ButtonStyle.Primary, ButtonStyle.Secondary -> {
                    when (size) {
                        ButtonSize.Small, ButtonSize.Medium -> aliasToken.typography[AliasTokens.TypographyTokens.caption1Strong]
                        ButtonSize.Large -> aliasToken.typography[AliasTokens.TypographyTokens.body1Strong]
                    }
                }
                ButtonStyle.Tertiary -> {
                    when (size) {
                        ButtonSize.Small, ButtonSize.Medium -> aliasToken.typography[AliasTokens.TypographyTokens.body2Strong]
                        ButtonSize.Large -> aliasToken.typography[AliasTokens.TypographyTokens.body1Strong]
                    }
                }
            }
        }

}
