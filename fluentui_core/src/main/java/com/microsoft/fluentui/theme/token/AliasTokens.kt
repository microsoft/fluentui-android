//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//
package com.microsoft.fluentui.theme.token

import android.os.Parcelable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize

@Parcelize
open class AliasTokens : Parcelable {
    enum class BrandColorTokens {
        Color10,
        Color20,
        Color30,
        Color40,
        Color50,
        Color60,
        Color70,
        Color80,
        Color90,
        Color100,
        Color110,
        Color120,
        Color130,
        Color140,
        Color150,
        Color160
    }

    open val brandColor: TokenSet<BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                BrandColorTokens.Color10 -> Color(0xFF061724)
                BrandColorTokens.Color20 -> Color(0xFF082338)
                BrandColorTokens.Color30 -> Color(0xFF0A2E4A)
                BrandColorTokens.Color40 -> Color(0xFF0C3B5E)
                BrandColorTokens.Color50 -> Color(0xFF0E4775)
                BrandColorTokens.Color60 -> Color(0xFF0F548C)
                BrandColorTokens.Color70 -> Color(0xFF115EA3)
                BrandColorTokens.Color80 -> Color(0xFF0F6CBD)
                BrandColorTokens.Color90 -> Color(0xFF2886DE)
                BrandColorTokens.Color100 -> Color(0xFF479EF5)
                BrandColorTokens.Color110 -> Color(0xFF62ABF5)
                BrandColorTokens.Color120 -> Color(0xFF77B7F7)
                BrandColorTokens.Color130 -> Color(0xFF96C6FA)
                BrandColorTokens.Color140 -> Color(0xFFB4D6FA)
                BrandColorTokens.Color150 -> Color(0xFFCFE4FA)
                BrandColorTokens.Color160 -> Color(0xFFEBF3FC)
            }
        }
    }


    enum class NeutralBackgroundColorTokens {
        Background1,
        Background1Pressed,
        Background1Selected,
        Background2,
        Background2Pressed,
        Background2Selected,
        Background3,
        Background3Pressed,
        Background3Selected,
        Background4,
        Background4Pressed,
        Background4Selected,
        Background5,
        Background5Pressed,
        Background5Selected,
        Background5SelectedBrandFilled,
        Background5SelectedBrandTint,
        Background6,
        Background6Pressed,
        Background6Selected,
        BackgroundInverted,
        BackgroundDisabled,
        Stencil1,
        Stencil2
    }

    open val neutralBackgroundColor: TokenSet<NeutralBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                NeutralBackgroundColorTokens.Background1 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)
                    )

                NeutralBackgroundColorTokens.Background1Pressed ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey88),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey18)
                    )

                NeutralBackgroundColorTokens.Background1Selected ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey14)
                    )

                NeutralBackgroundColorTokens.Background2 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey12)
                    )

                NeutralBackgroundColorTokens.Background2Pressed ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey88),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey30)
                    )

                NeutralBackgroundColorTokens.Background2Selected ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey92),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey26)
                    )

                NeutralBackgroundColorTokens.Background3 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey16)
                    )

                NeutralBackgroundColorTokens.Background3Pressed ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey88),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey34)
                    )

                NeutralBackgroundColorTokens.Background3Selected ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey92),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey30)
                    )

                NeutralBackgroundColorTokens.Background4 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey98),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey20)
                    )

                NeutralBackgroundColorTokens.Background4Pressed ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey86),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey38)
                    )

                NeutralBackgroundColorTokens.Background4Selected ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey90),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey34)
                    )

                NeutralBackgroundColorTokens.Background5 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey94),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey24)
                    )

                NeutralBackgroundColorTokens.Background5Pressed ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey82),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey42)
                    )

                NeutralBackgroundColorTokens.Background5Selected ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey86),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey38)
                    )

                NeutralBackgroundColorTokens.Background5SelectedBrandFilled ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color80],
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey38)
                    )

                NeutralBackgroundColorTokens.Background5SelectedBrandTint ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color160],
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey38)
                    )

                NeutralBackgroundColorTokens.Background6 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey82),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey36)
                    )

                NeutralBackgroundColorTokens.Background6Pressed ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey70),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey54)
                    )

                NeutralBackgroundColorTokens.Background6Selected ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey74),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey50)
                    )

                NeutralBackgroundColorTokens.BackgroundInverted ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey14),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey34)
                    )

                NeutralBackgroundColorTokens.BackgroundDisabled ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey88),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey32)
                    )

                NeutralBackgroundColorTokens.Stencil1 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey90),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey34)
                    )

                NeutralBackgroundColorTokens.Stencil2 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey98),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey20)
                    )
            }
        }
    }

    enum class NeutralForegroundColorTokens {
        Foreground1,
        Foreground2,
        Foreground3,
        ForegroundDisable1,
        ForegroundDisable2,
        ForegroundContrast,
        ForegroundOnColor,
        ForegroundInverted1,
        ForegroundInverted2
    }

    open val neutralForegroundColor: TokenSet<NeutralForegroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                NeutralForegroundColorTokens.Foreground1 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey14),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White)
                    )
                NeutralForegroundColorTokens.Foreground2 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey38),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey84)
                    )
                NeutralForegroundColorTokens.Foreground3 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey50),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey68)
                    )
                NeutralForegroundColorTokens.ForegroundDisable1 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey74),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey36)
                    )
                NeutralForegroundColorTokens.ForegroundDisable2 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey24)
                    )
                NeutralForegroundColorTokens.ForegroundContrast ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)
                    )
                NeutralForegroundColorTokens.ForegroundOnColor ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)
                    )
                NeutralForegroundColorTokens.ForegroundInverted1 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White)
                    )
                NeutralForegroundColorTokens.ForegroundInverted2 ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color80],
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White)
                    )
            }
        }
    }

    enum class NeutralStrokeColorTokens {
        Stroke1,
        Stroke2,
        StrokeDisabled,
        StrokeAccessible,
        StrokeFocus1,
        StrokeFocus2
    }

    open val neutralStrokeColor: TokenSet<NeutralStrokeColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                NeutralStrokeColorTokens.Stroke1 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey82),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey32)
                    )

                NeutralStrokeColorTokens.Stroke2 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey88),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey24)
                    )

                NeutralStrokeColorTokens.StrokeDisabled ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey88),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey26)
                    )

                NeutralStrokeColorTokens.StrokeAccessible ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey38),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey62)
                    )

                NeutralStrokeColorTokens.StrokeFocus1 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)
                    )

                NeutralStrokeColorTokens.StrokeFocus2 ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White)
                    )
            }
        }
    }

    enum class BrandBackgroundColorTokens {
        BrandBackground1,
        BrandBackground1Pressed,
        BrandBackground1Selected,
        BrandBackground2,
        BrandBackground2Pressed,
        BrandBackground2Selected,
        BrandBackground3,
        BrandBackground3Pressed,
        BrandBackground3Selected,
        BrandBackground4,
        BrandBackgroundDisabled,
        BrandBackgroundInverted,
        BrandBackgroundInvertedDisabled,
    }

    open val brandBackgroundColor: TokenSet<BrandBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                BrandBackgroundColorTokens.BrandBackground1 ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color80],
                            dark = brandColor[BrandColorTokens.Color100]
                    )

                BrandBackgroundColorTokens.BrandBackground1Pressed ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color50],
                            dark = brandColor[BrandColorTokens.Color140]
                    )

                BrandBackgroundColorTokens.BrandBackground1Selected ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color60],
                            dark = brandColor[BrandColorTokens.Color120]
                    )

                BrandBackgroundColorTokens.BrandBackground2 ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color70],
                            dark = Color.Unspecified
                    )

                BrandBackgroundColorTokens.BrandBackground2Pressed ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color40],
                            dark = Color.Unspecified
                    )

                BrandBackgroundColorTokens.BrandBackground2Selected ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color50],
                            dark = Color.Unspecified
                    )

                BrandBackgroundColorTokens.BrandBackground3 ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color60],
                            dark = brandColor[BrandColorTokens.Color120]
                    )

                BrandBackgroundColorTokens.BrandBackground3Pressed ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color30],
                            dark = brandColor[BrandColorTokens.Color160]
                    )

                BrandBackgroundColorTokens.BrandBackground3Selected ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color150],
                            dark = Color.Unspecified
                    )

                BrandBackgroundColorTokens.BrandBackground4 ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color160],
                            dark = brandColor[BrandColorTokens.Color20]
                    )

                BrandBackgroundColorTokens.BrandBackgroundDisabled ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color140],
                            dark = brandColor[BrandColorTokens.Color40]
                    )

                BrandBackgroundColorTokens.BrandBackgroundInverted ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White)
                    )

                BrandBackgroundColorTokens.BrandBackgroundInvertedDisabled ->
                    FluentColor(
                            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey98)
                    )
            }
        }
    }

    enum class BrandForegroundColorTokens {
        BrandForeground1,
        BrandForeground1Pressed,
        BrandForeground1Selected
    }

    open val brandForegroundColor: TokenSet<BrandForegroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                BrandForegroundColorTokens.BrandForeground1 ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color80],
                            dark = brandColor[BrandColorTokens.Color100]
                    )

                BrandForegroundColorTokens.BrandForeground1Pressed ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color50],
                            dark = brandColor[BrandColorTokens.Color140]
                    )

                BrandForegroundColorTokens.BrandForeground1Selected ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color60],
                            dark = brandColor[BrandColorTokens.Color120]
                    )
            }
        }
    }

    enum class BrandStrokeColorTokens {
        BrandStroke1,
        BrandStroke1Pressed,
        BrandStroke1Selected,
    }

    open val brandStroke: TokenSet<BrandStrokeColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                BrandStrokeColorTokens.BrandStroke1 ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color80],
                            dark = brandColor[BrandColorTokens.Color100]
                    )

                BrandStrokeColorTokens.BrandStroke1Pressed ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color50],
                            dark = brandColor[BrandColorTokens.Color140]
                    )

                BrandStrokeColorTokens.BrandStroke1Selected ->
                    FluentColor(
                            light = brandColor[BrandColorTokens.Color60],
                            dark = brandColor[BrandColorTokens.Color120]
                    )
            }
        }
    }

    enum class TypographyTokens {
        Display,
        LargeTitle,
        Title1,
        Title2,
        Title3,
        Body1Strong,
        Body1,
        Body2Strong,
        Body2,
        Caption1Strong,
        Caption1,
        Caption2
    }

    open val typography: TokenSet<TypographyTokens, FontInfo> by lazy {
        TokenSet { token ->
            when (token) {
                TypographyTokens.Display ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size900),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Bold)
                    )
                TypographyTokens.LargeTitle ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size800),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Title1 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size700),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Title2 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size600),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Title3 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size500),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Body1Strong ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size400),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Body1 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size400),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Body2Strong ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size300),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Body2 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size300),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Caption1Strong ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size200),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
                    )
                TypographyTokens.Caption1 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size200),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Caption2 ->
                    FontInfo(
                            fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size100),
                            weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
            }
        }
    }
}

internal val LocalAliasTokens = compositionLocalOf { AliasTokens() }