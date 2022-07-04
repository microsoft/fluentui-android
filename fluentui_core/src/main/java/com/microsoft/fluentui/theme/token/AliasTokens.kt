//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//
package com.microsoft.fluentui.theme.token

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.FluentTheme

open class AliasTokens() {

    protected var globalToken: GlobalTokens = GlobalTokens()

    @Composable
    open fun updateGlobalToken() {
        this.globalToken = FluentTheme.globalTokens
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
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Black],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White]
                    )

                NeutralBackgroundColorTokens.Background1Pressed ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey18],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88]
                    )

                NeutralBackgroundColorTokens.Background1Selected ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey14],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White]
                    )

                NeutralBackgroundColorTokens.Background2 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey12],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White]
                    )

                NeutralBackgroundColorTokens.Background2Pressed ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey30],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88]
                    )

                NeutralBackgroundColorTokens.Background2Selected ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey92],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey26],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey92]
                    )

                NeutralBackgroundColorTokens.Background3 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey16],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color80]
                    )

                NeutralBackgroundColorTokens.Background3Pressed ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey34],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color50]
                    )

                NeutralBackgroundColorTokens.Background3Selected ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey92],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey30],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color60]
                    )

                NeutralBackgroundColorTokens.Background4 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey98],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey20],
                            colorful = Color.Unspecified
                    )

                NeutralBackgroundColorTokens.Background4Pressed ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey86],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey38],
                            colorful = Color.Unspecified
                    )

                NeutralBackgroundColorTokens.Background4Selected ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey90],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey34],
                            colorful = Color.Unspecified
                    )

                NeutralBackgroundColorTokens.Background5 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey94],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey24],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color70]
                    )

                NeutralBackgroundColorTokens.Background5Pressed ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey82],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey42],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color40]
                    )

                NeutralBackgroundColorTokens.Background5Selected ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey86],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey38],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color50]
                    )

                NeutralBackgroundColorTokens.Background5SelectedBrandFilled ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color80],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey38],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White]
                    )

                NeutralBackgroundColorTokens.Background5SelectedBrandTint ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color160],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey38],
                            colorful = Color.Unspecified
                    )

                NeutralBackgroundColorTokens.Background6 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey82],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey36],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color60]
                    )

                NeutralBackgroundColorTokens.Background6Pressed ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey70],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey54],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color30]
                    )

                NeutralBackgroundColorTokens.Background6Selected ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey74],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey50],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color150]
                    )

                NeutralBackgroundColorTokens.BackgroundInverted ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey14],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey34],
                            colorful = Color.Unspecified
                    )

                NeutralBackgroundColorTokens.BackgroundDisabled ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey32],
                            colorful = Color.Unspecified
                    )

                NeutralBackgroundColorTokens.Stencil1 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey90],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey34],
                            colorful = Color.Unspecified
                    )

                NeutralBackgroundColorTokens.Stencil2 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey98],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey20],
                            colorful = Color.Unspecified
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
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey14],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White]
                    )
                NeutralForegroundColorTokens.Foreground2 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey38],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey84],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color160]
                    )
                NeutralForegroundColorTokens.Foreground3 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey50],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey68],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color150]
                    )
                NeutralForegroundColorTokens.ForegroundDisable1 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey74],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey36],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color90]
                    )
                NeutralForegroundColorTokens.ForegroundDisable2 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey24],
                            colorful = Color.Unspecified
                    )
                NeutralForegroundColorTokens.ForegroundContrast ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Black],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color70]
                    )
                NeutralForegroundColorTokens.ForegroundOnColor ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Black],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color80]
                    )
                NeutralForegroundColorTokens.ForegroundInverted1 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color80]
                    )
                NeutralForegroundColorTokens.ForegroundInverted2 ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color80],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White]
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
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey82],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey32],
                            colorful = Color.Unspecified
                    )

                NeutralStrokeColorTokens.Stroke2 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey24],
                            colorful = Color.Unspecified
                    )

                NeutralStrokeColorTokens.StrokeDisabled ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey26],
                            colorful = Color.Unspecified
                    )

                NeutralStrokeColorTokens.StrokeAccessible ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey38],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey62],
                            colorful = Color.Unspecified
                    )

                NeutralStrokeColorTokens.StrokeFocus1 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Black],
                            colorful = Color.Unspecified
                    )

                NeutralStrokeColorTokens.StrokeFocus2 ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Black],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            colorful = Color.Unspecified
                    )
            }
        }
    }

    enum class BrandBackgroundColorTokens {
        BrandBackground1,
        BrandBackground1Pressed,
        BrandBackground1Selected,
        BrandBackground2,
        BrandBackgroundDisabled,
        BrandBackgroundInverted,
        BrandBackgroundInvertedDisabled,
    }

    open val brandBackgroundColor: TokenSet<BrandBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                BrandBackgroundColorTokens.BrandBackground1 ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color80],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color100],
                            colorful = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color160]
                    )

                BrandBackgroundColorTokens.BrandBackground1Pressed ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color50],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color140],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88]
                    )

                BrandBackgroundColorTokens.BrandBackground1Selected ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color60],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color120],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey92]
                    )

                BrandBackgroundColorTokens.BrandBackground2 ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color160],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color20],
                            colorful = Color.Unspecified
                    )

                BrandBackgroundColorTokens.BrandBackgroundDisabled ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color140],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color40],
                            colorful = Color.Unspecified
                    )

                BrandBackgroundColorTokens.BrandBackgroundInverted ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            colorful = Color.Unspecified
                    )

                BrandBackgroundColorTokens.BrandBackgroundInvertedDisabled ->
                    FluentColor(
                            light = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White],
                            dark = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey98],
                            colorful = Color.Unspecified
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
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color80],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color100],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White]
                    )

                BrandForegroundColorTokens.BrandForeground1Pressed ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color50],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color140],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88]
                    )

                BrandForegroundColorTokens.BrandForeground1Selected ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color60],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color120],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey92]
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
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color80],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color100],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.White]
                    )

                BrandStrokeColorTokens.BrandStroke1Pressed ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color50],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color140],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey88]
                    )

                BrandStrokeColorTokens.BrandStroke1Selected ->
                    FluentColor(
                            light = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color60],
                            dark = this.globalToken.brandColor[GlobalTokens.BrandColorTokens.Color120],
                            colorful = this.globalToken.neutralColor[GlobalTokens.NeutralColorTokens.Grey92]
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
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size900],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Bold]
                    )
                TypographyTokens.LargeTitle ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size800],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Title1 ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size700],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Title2 ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size600],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Title3 ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size500],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Body1Strong ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size400],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Body1 ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size400],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Body2Strong ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size300],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Body2 ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size300],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Caption1Strong ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size200],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Medium]
                    )
                TypographyTokens.Caption1 ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size200],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
                TypographyTokens.Caption2 ->
                    FontInfo(
                            fontSize = this.globalToken.fontSize[GlobalTokens.FontSizeTokens.Size100],
                            weight = this.globalToken.fontWeight[GlobalTokens.FontWeightTokens.Regular]
                    )
            }
        }
    }
}

internal val LocalAliasTokens = compositionLocalOf { AliasTokens() }