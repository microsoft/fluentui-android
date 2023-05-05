package com.example.theme.token


import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.TokenSet


class MyAliasTokens : AliasTokens() {
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
    override val typography: TokenSet<FluentAliasTokens.TypographyTokens, TextStyle> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.TypographyTokens.Display ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size900),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size900),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
                        letterSpacing = -0.5.sp
                    )
                FluentAliasTokens.TypographyTokens.LargeTitle ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size900),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size900),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
                        letterSpacing = -0.25.sp
                    )
                FluentAliasTokens.TypographyTokens.Title1 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size800),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size800),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Bold),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Title2 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size700),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size700),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Title3 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size600),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size600),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Body1Strong ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size500),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size500),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.SemiBold),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Body1 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size500),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size500),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Body2Strong ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size400),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size400),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Body2 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size400),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size400),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Caption1Strong ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size300),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size300),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Caption1 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size300),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size300),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Caption2 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size200),
                        lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size200),
                        fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular),
                        letterSpacing = 0.sp
                    )
                else -> {
                    super.typography[token]
                }
            }
        }
    }
}


