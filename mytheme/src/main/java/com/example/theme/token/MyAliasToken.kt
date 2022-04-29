package com.example.theme.token


import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.DynamicColor
import com.microsoft.fluentui.theme.token.TokenSet
class MyAliasToken (globalTokens:GlobalTokens = MyGlobalTokens()): AliasTokens(globalTokens) {

    override val strokeColors: TokenSet<StrokeColorsTokens, DynamicColor> by lazy {
        TokenSet { token ->
            when (token) {
                StrokeColorsTokens.neutral1 ->
                    DynamicColor(
                        light = Color.Gray,//globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.gray],
                        dark = Color.Green//globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black],
                    )
                StrokeColorsTokens.neutral2 ->
                    DynamicColor(
                        light = Color.Gray,//globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.gray],
                        dark = Color.Green//globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black],
                    )
            }
        }
    }
    override val backgroundColors: TokenSet<BackgroundColorsTokens, DynamicColor> by lazy {
        TokenSet {
            DynamicColor(
                light = Color.Gray,//globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.gray],
                dark = Color.White//globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black],
            )
        }
    }

    override val foregroundColors: TokenSet<ForegroundColorsTokens, DynamicColor> by lazy {
        TokenSet {
            DynamicColor(
                light = Color.White,//globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.white],
                dark = Color.Black//globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black],
            )
        }
    }
    }


