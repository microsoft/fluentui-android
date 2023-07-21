package com.example.theme.token


import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
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

    override val brandBackgroundColor: TokenSet<FluentAliasTokens.BrandBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color100]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color10],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color150]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color50],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color30],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Selected ->
                    FluentColor(
                        light = Color.Black,
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground3 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color140],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color30]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color120],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color20]
                    )
            }
        }
    }

    override val brandForegroundColor: TokenSet<FluentAliasTokens.BrandForegroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color100]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color10],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color150]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color100]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color130],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color110],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color30]
                    )
            }
        }
    }

    override val brandStroke: TokenSet<FluentAliasTokens.BrandStrokeColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color100]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color10],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color150]
                    )
            }
        }
    }
}

class WordAliasTokens : AliasTokens() {
    override val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandColorTokens.Color10 -> Color(0xFF071225)
                FluentAliasTokens.BrandColorTokens.Color20 -> Color(0xFF0C2145)
                FluentAliasTokens.BrandColorTokens.Color30 -> Color(0xFF0E336A)
                FluentAliasTokens.BrandColorTokens.Color40 -> Color(0xFF19428A)
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

    override val brandBackgroundColor: TokenSet<FluentAliasTokens.BrandBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color120]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color20],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground3 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color140],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color30]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color160],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color30]
                    )
            }
        }
    }

    override val brandForegroundColor: TokenSet<FluentAliasTokens.BrandForegroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color120]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color110],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color90]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color150],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color40]
                    )
            }
        }
    }

    override val brandStroke: TokenSet<FluentAliasTokens.BrandStrokeColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color120]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )
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
                FluentAliasTokens.BrandColorTokens.Color160 -> Color(0xFFCAEAD8)
                else -> {
                    super.brandColor[token]
                }
            }
        }
    }

    override val brandBackgroundColor: TokenSet<FluentAliasTokens.BrandBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color20],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground3 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color160],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color30]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color150],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color40]
                    )
            }
        }
    }

    override val brandForegroundColor: TokenSet<FluentAliasTokens.BrandForegroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color50],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color120],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color90]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color160],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color60]
                    )
            }
        }
    }

    override val brandStroke: TokenSet<FluentAliasTokens.BrandStrokeColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )
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

    override val brandBackgroundColor: TokenSet<FluentAliasTokens.BrandBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color20],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground3 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color160],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color40]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color150],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color40]
                    )
            }
        }
    }

    override val brandForegroundColor: TokenSet<FluentAliasTokens.BrandForegroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color150]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color130],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color90]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color150],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color40]
                    )
            }
        }
    }

    override val brandStroke: TokenSet<FluentAliasTokens.BrandStrokeColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color160]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color140]
                    )
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

class TeamsAliasTokens : AliasTokens() {
    override val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandColorTokens.Color10 -> Color(0xFF2B2B40)
                FluentAliasTokens.BrandColorTokens.Color20 -> Color(0xFF2F2F4A)
                FluentAliasTokens.BrandColorTokens.Color30 -> Color(0xFF333357)
                FluentAliasTokens.BrandColorTokens.Color40 -> Color(0xFF383966)
                FluentAliasTokens.BrandColorTokens.Color50 -> Color(0xFF3D3E78)
                FluentAliasTokens.BrandColorTokens.Color60 -> Color(0xFF444791)
                FluentAliasTokens.BrandColorTokens.Color70 -> Color(0xFF4F52B2)
                FluentAliasTokens.BrandColorTokens.Color80 -> Color(0xFF5B5FC7)
                FluentAliasTokens.BrandColorTokens.Color90 -> Color(0xFF7579EB)
                FluentAliasTokens.BrandColorTokens.Color100 -> Color(0xFF7F85F5)
                FluentAliasTokens.BrandColorTokens.Color110 -> Color(0xFF9299F7)
                FluentAliasTokens.BrandColorTokens.Color120 -> Color(0xFFAAB1FA)
                FluentAliasTokens.BrandColorTokens.Color130 -> Color(0xFFB6BCFA)
                FluentAliasTokens.BrandColorTokens.Color140 -> Color(0xFFC5CBFA)
                FluentAliasTokens.BrandColorTokens.Color150 -> Color(0xFFDCE0FA)
                FluentAliasTokens.BrandColorTokens.Color160 -> Color(0xFFE8EBFA)
                else -> {
                    super.brandColor[token]
                }
            }
        }
    }
}