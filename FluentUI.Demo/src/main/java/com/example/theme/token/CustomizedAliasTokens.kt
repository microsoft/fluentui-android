package com.example.theme.token


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.TokenSet


class OneNoteAliasTokens : AliasTokens() {
    override val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandColorTokens.Color10 -> Color(0xFF443168)
                FluentAliasTokens.BrandColorTokens.Color20 -> Color(0xFF584183)
                FluentAliasTokens.BrandColorTokens.Color30 -> Color(0xFF430E60)
                FluentAliasTokens.BrandColorTokens.Color40 -> Color(0xFF7B64A3)
                FluentAliasTokens.BrandColorTokens.Color50 -> Color(0xFF5B1382)
                FluentAliasTokens.BrandColorTokens.Color60 -> Color(0xFF6C179A)
                FluentAliasTokens.BrandColorTokens.Color70 -> Color(0xFF9D87C4)
                FluentAliasTokens.BrandColorTokens.Color80 -> Color(0xFF7719AA)
                FluentAliasTokens.BrandColorTokens.Color90 -> Color(0xFF862EB5)
                FluentAliasTokens.BrandColorTokens.Color100 -> Color(0xFFC0AAE4)
                FluentAliasTokens.BrandColorTokens.Color110 -> Color(0xFFCEBBED)
                FluentAliasTokens.BrandColorTokens.Color120 -> Color(0xFFDCCDF6)
                FluentAliasTokens.BrandColorTokens.Color130 -> Color(0xFFA864CD)
                FluentAliasTokens.BrandColorTokens.Color140 -> Color(0xFFEADEFF)
                FluentAliasTokens.BrandColorTokens.Color150 -> Color(0xFFD1ABE6)
                FluentAliasTokens.BrandColorTokens.Color160 -> Color(0xFFE6D1F2)
                else -> {
                    super.brandColor[token]
                }
            }
        }
    }
//    override val typography: TokenSet<FluentAliasTokens.TypographyTokens, TextStyle> by lazy {
//        TokenSet { token ->
//            when (token) {
//                FluentAliasTokens.TypographyTokens.Display ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size900),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size900),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
//                        letterSpacing = -0.5.sp
//                    )
//                FluentAliasTokens.TypographyTokens.LargeTitle ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size900),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size900),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
//                        letterSpacing = -0.25.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Title1 ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size800),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size800),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Bold),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Title2 ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size700),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size700),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Title3 ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size600),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size600),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Body1Strong ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size500),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size500),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.SemiBold),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Body1 ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size500),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size500),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Body2Strong ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size400),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size400),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Body2 ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size400),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size400),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Caption1Strong ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size300),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size300),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Caption1 ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size300),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size300),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
//                        letterSpacing = 0.sp
//                    )
//                FluentAliasTokens.TypographyTokens.Caption2 ->
//                    TextStyle(
//                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size200),
//                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size200),
//                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
//                        letterSpacing = 0.sp
//                    )
//                else -> {
//                    super.typography[token]
//                }
//            }
//        }
//    }
}


class WordAliasTokens : AliasTokens() {
    override val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandColorTokens.Color10 -> Color(0xFF071225)
                FluentAliasTokens.BrandColorTokens.Color20 -> Color(0xFF0C2145)
                FluentAliasTokens.BrandColorTokens.Color30 -> Color(0xFF0E336A)
                FluentAliasTokens.BrandColorTokens.Color40 -> Color(0xFF1942BA)
                FluentAliasTokens.BrandColorTokens.Color50 -> Color(0xFF13458F)
                FluentAliasTokens.BrandColorTokens.Color60 -> Color(0xFF1651AA)
                FluentAliasTokens.BrandColorTokens.Color70 -> Color(0xFF2461CA)
                FluentAliasTokens.BrandColorTokens.Color80 -> Color(0xFF185ABD)
                FluentAliasTokens.BrandColorTokens.Color90 -> Color(0xFF2E6AC5)
                FluentAliasTokens.BrandColorTokens.Color100 -> Color(0xFF296FE6)
                FluentAliasTokens.BrandColorTokens.Color110 -> Color(0xFF3D7CEB)
                FluentAliasTokens.BrandColorTokens.Color120 -> Color(0xFF598FEC)
                FluentAliasTokens.BrandColorTokens.Color130 -> Color(0xFF6794D7)
                FluentAliasTokens.BrandColorTokens.Color140 -> Color(0xFF82ABF1)
                FluentAliasTokens.BrandColorTokens.Color150 -> Color(0xFFAEC6EB)
                FluentAliasTokens.BrandColorTokens.Color160 -> Color(0xFFD2E0F4)
                else -> {
                    super.brandColor[token]
                }
            }
        }
    }
}

class ExcelAliasTokens : AliasTokens() {
    override val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandColorTokens.Color10 -> Color(0xFF03160A)
                FluentAliasTokens.BrandColorTokens.Color20 -> Color(0xFF052912)
                FluentAliasTokens.BrandColorTokens.Color30 -> Color(0xFF094624)
                FluentAliasTokens.BrandColorTokens.Color40 -> Color(0xFF0A5325)
                FluentAliasTokens.BrandColorTokens.Color50 -> Color(0xFF0C5F32)
                FluentAliasTokens.BrandColorTokens.Color60 -> Color(0xFF0F703B)
                FluentAliasTokens.BrandColorTokens.Color70 -> Color(0xFF0F7937)
                FluentAliasTokens.BrandColorTokens.Color80 -> Color(0xFF107C41)
                FluentAliasTokens.BrandColorTokens.Color90 -> Color(0xFF218D51)
                FluentAliasTokens.BrandColorTokens.Color100 -> Color(0xFF10893C)
                FluentAliasTokens.BrandColorTokens.Color110 -> Color(0xFF1F954A)
                FluentAliasTokens.BrandColorTokens.Color120 -> Color(0xFF37A660)
                FluentAliasTokens.BrandColorTokens.Color130 -> Color(0xFF55B17E)
                FluentAliasTokens.BrandColorTokens.Color140 -> Color(0xFF60BD82)
                FluentAliasTokens.BrandColorTokens.Color150 -> Color(0xFFA0D8B9)
                FluentAliasTokens.BrandColorTokens.Color160 -> Color(0xFFCAEADB)
                else -> {
                    super.brandColor[token]
                }
            }
        }
    }
}

class PowerPointAliasTokens : AliasTokens() {
    override val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandColorTokens.Color10 -> Color(0xFF200D03)
                FluentAliasTokens.BrandColorTokens.Color20 -> Color(0xFF3C1805)
                FluentAliasTokens.BrandColorTokens.Color30 -> Color(0xFF6E220F)
                FluentAliasTokens.BrandColorTokens.Color40 -> Color(0xFF79310A)
                FluentAliasTokens.BrandColorTokens.Color50 -> Color(0xFF952F15)
                FluentAliasTokens.BrandColorTokens.Color60 -> Color(0xFFB13719)
                FluentAliasTokens.BrandColorTokens.Color70 -> Color(0xFFB1470E)
                FluentAliasTokens.BrandColorTokens.Color80 -> Color(0xFFC43E1C)
                FluentAliasTokens.BrandColorTokens.Color90 -> Color(0xFFCB5031)
                FluentAliasTokens.BrandColorTokens.Color100 -> Color(0xFFCA5010)
                FluentAliasTokens.BrandColorTokens.Color110 -> Color(0xFFCF6024)
                FluentAliasTokens.BrandColorTokens.Color120 -> Color(0xFFD67540)
                FluentAliasTokens.BrandColorTokens.Color130 -> Color(0xFFDC816A)
                FluentAliasTokens.BrandColorTokens.Color140 -> Color(0xFFE1966D)
                FluentAliasTokens.BrandColorTokens.Color150 -> Color(0xFFEDBCB0)
                FluentAliasTokens.BrandColorTokens.Color160 -> Color(0xFFF6DBD4)
                else -> {
                    super.brandColor[token]
                }
            }
        }
    }
}

class M365AliasTokens : AliasTokens() {
    override val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandColorTokens.Color10 -> Color(0xFF16152B)
                FluentAliasTokens.BrandColorTokens.Color20 -> Color(0xFF1E1D40)
                FluentAliasTokens.BrandColorTokens.Color30 -> Color(0xFF27265C)
                FluentAliasTokens.BrandColorTokens.Color40 -> Color(0xFF2E2E78)
                FluentAliasTokens.BrandColorTokens.Color50 -> Color(0xFF353696)
                FluentAliasTokens.BrandColorTokens.Color60 -> Color(0xFF3B3FB2)
                FluentAliasTokens.BrandColorTokens.Color70 -> Color(0xFF3E45C9)
                FluentAliasTokens.BrandColorTokens.Color80 -> Color(0xFF464FEB)
                FluentAliasTokens.BrandColorTokens.Color90 -> Color(0xFF515EF5)
                FluentAliasTokens.BrandColorTokens.Color100 -> Color(0xFF5F71FA)
                FluentAliasTokens.BrandColorTokens.Color110 -> Color(0xFF7385FF)
                FluentAliasTokens.BrandColorTokens.Color120 -> Color(0xFF8295FF)
                FluentAliasTokens.BrandColorTokens.Color130 -> Color(0xFF96A8FF)
                FluentAliasTokens.BrandColorTokens.Color140 -> Color(0xFFB0BEFF)
                FluentAliasTokens.BrandColorTokens.Color150 -> Color(0xFFCCD6FF)
                FluentAliasTokens.BrandColorTokens.Color160 -> Color(0xFFEBEFFF)
                else -> {
                    super.brandColor[token]
                }
            }
        }
    }
}

