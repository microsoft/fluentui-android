package com.example.theme.token


import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.TokenSet

class MyAliasTokens(globalTokens: GlobalTokens = MyGlobalTokens()) : AliasTokens(globalTokens) {

    override val typography: TokenSet<TypographyTokens, FontInfo> by lazy {
        TokenSet { token ->
            when (token) {
                TypographyTokens.Display ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size900],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Bold]
                    )
                TypographyTokens.LargeTitle ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size900],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Title1 ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size800],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Title2 ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size700],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Title3 ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size600],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Body1Strong ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size500],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Body1 ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size500],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Body2Strong ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size400],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Body2 ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size400],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Caption1Strong ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size300],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Caption1 ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size300],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Caption2 ->
                    FontInfo(
                        fontSize = globalTokens.fontSize[GlobalTokens.FontSizeTokens.Size200],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
            }
        }
    }
}


