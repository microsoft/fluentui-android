package com.example.theme.token


import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.TokenSet

class MyAliasTokens : AliasTokens() {
    override val brandColor: TokenSet<BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                BrandColorTokens.Color10 -> Color(0xFF443168)
                BrandColorTokens.Color20 -> Color(0xFF584183)
                BrandColorTokens.Color30 -> Color(0xFF430E60)
                BrandColorTokens.Color40 -> Color(0xFF7B64A3)
                BrandColorTokens.Color50 -> Color(0xFF5B1382)
                BrandColorTokens.Color60 -> Color(0xFF6C179A)
                BrandColorTokens.Color70 -> Color(0xFF9D87C4)
                BrandColorTokens.Color80 -> Color(0xFF7719AA)
                BrandColorTokens.Color90 -> Color(0xFF862EB5)
                BrandColorTokens.Color100 -> Color(0xFFC0AAE4)
                BrandColorTokens.Color110 -> Color(0xFFCEBBED)
                BrandColorTokens.Color120 -> Color(0xFFDCCDF6)
                BrandColorTokens.Color130 -> Color(0xFFA864CD)
                BrandColorTokens.Color140 -> Color(0xFFEADEFF)
                BrandColorTokens.Color150 -> Color(0xFFD1ABE6)
                BrandColorTokens.Color160 -> Color(0xFFE6D1F2)
            }
        }
    }
    override val typography: TokenSet<TypographyTokens, FontInfo> by lazy {
        TokenSet { token ->
            when (token) {
                TypographyTokens.Display ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size900),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Bold)
                    )
                TypographyTokens.LargeTitle ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size900),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Title1 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size800),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Title2 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size700),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Title3 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size600),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Body1Strong ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size500),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Body1 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size500),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Body2Strong ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size400),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Body2 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size400),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Caption1Strong ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size300),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Caption1 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size300),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Caption2 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size200),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
            }
        }
    }
}


