//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//
package com.microsoft.fluentui.theme.token

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

open class AliasTokens(val globalTokens: GlobalTokens = GlobalTokens()) {

    // MARK= ForegroundColors

    enum class ForegroundColorsTokens {
        neutral1,
        neutral2,
        neutral3,
        neutral4,
        neutralDisabled,
        neutralInverted,
        brandRest,
        brandHover,
        brandPressed,
        brandSelected,
        brandDisabled
    }
    open val foregroundColors: TokenSet<ForegroundColorsTokens, DynamicColor> by lazy{
        TokenSet { token ->
            when (token) {
                ForegroundColorsTokens.neutral1 ->
                    DynamicColor(
                        light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey14],
                        lightHighContrast = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black],
                        dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.white],
                        darkHighContrast = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.white]
                    )
                ForegroundColorsTokens.neutral2 ->
                     DynamicColor(
                        light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey26],
                        lightHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black],
                        dark= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey84],
                        darkHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.white]
                    )
                ForegroundColorsTokens.neutral3 ->
                     DynamicColor(
                        light= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey38],
                        lightHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey14],
                        dark= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey68],
                        darkHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey84]
                    )
                ForegroundColorsTokens.neutral4 ->
                     DynamicColor(
                        light= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey50],
                        lightHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey26],
                        dark= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey52],
                        darkHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey84]
                    )
                ForegroundColorsTokens.neutralDisabled ->
                     DynamicColor(
                        light= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey74],
                        lightHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey38],
                        dark= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey36],
                        darkHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey62]
                    )
                ForegroundColorsTokens.neutralInverted ->
                     DynamicColor(
                        light= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.white],
                        lightHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.white],
                        dark= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black],
                        darkHighContrast= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black]
                    )
                ForegroundColorsTokens.brandRest ->
                     DynamicColor(
                        light= globalTokens.brandColors[GlobalTokens.BrandColorsTokens.primary].light,
                        lightHighContrast= globalTokens.brandColors[GlobalTokens.BrandColorsTokens.shade20].light,
                        dark= globalTokens.brandColors[GlobalTokens.BrandColorsTokens.primary].dark,
                        darkHighContrast= globalTokens.brandColors[GlobalTokens.BrandColorsTokens.tint20].dark
                    )
                ForegroundColorsTokens.brandHover ->
                     globalTokens.brandColors[GlobalTokens.BrandColorsTokens.shade10]
                ForegroundColorsTokens.brandPressed ->
                     globalTokens.brandColors[GlobalTokens.BrandColorsTokens.shade30]
                ForegroundColorsTokens.brandSelected ->
                     globalTokens.brandColors[GlobalTokens.BrandColorsTokens.shade20]
                ForegroundColorsTokens.brandDisabled ->
                     DynamicColor(
                        light= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey74],
                        dark= globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey36]
                    )
            }
        }
}

// MARK= BackgroundColors

enum class BackgroundColorsTokens {
    neutral1,
    neutral2,
    neutral3,
    neutral4,
    neutral5,
    neutralDisabled,
    brandRest,
    brandHover,
    brandPressed,
    brandSelected,
    brandDisabled,
    surfaceQuaternary
}
open val backgroundColors: TokenSet<BackgroundColorsTokens, DynamicColor> by lazy{
    TokenSet { token ->
        when (token) {
            BackgroundColorsTokens.neutral1 ->
                DynamicColor(
                    light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.white],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.black],
                    darkElevated = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey4]
                )
            BackgroundColorsTokens.neutral2 ->
                DynamicColor (
                    light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey98],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey4],
                    darkElevated = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey8]
                ) 
            BackgroundColorsTokens.neutral3 ->
                DynamicColor(
                    light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey96],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey8],
                    darkElevated = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey12]
                )
            BackgroundColorsTokens.neutral4 ->
                DynamicColor(
                    light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey94],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey12],
                    darkElevated = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey16]
                )
            BackgroundColorsTokens.neutral5 ->
                DynamicColor (
                    light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey92],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey36],
                    darkElevated = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey36]
                )
            BackgroundColorsTokens.neutralDisabled ->
                DynamicColor(
                    light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey88],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey84],
                    darkElevated = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey84]
                )
            BackgroundColorsTokens.brandRest ->
                globalTokens.brandColors[GlobalTokens.BrandColorsTokens.primary]
            BackgroundColorsTokens.brandHover ->
                        globalTokens.brandColors [GlobalTokens.BrandColorsTokens.shade10] 
            BackgroundColorsTokens.brandPressed ->
                        globalTokens.brandColors [GlobalTokens.BrandColorsTokens.shade30]
            BackgroundColorsTokens.brandSelected ->
                        globalTokens.brandColors [GlobalTokens.BrandColorsTokens.shade20]
            BackgroundColorsTokens.brandDisabled ->
                DynamicColor (
                    light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey88],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey84]
                )
            BackgroundColorsTokens.surfaceQuaternary ->
                DynamicColor(
                        light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey88],
                        dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey26]
                )
        }
    }
}

// MARK= StrokeColors

enum class StrokeColorsTokens {
    neutral1,
    neutral2
}
open val strokeColors: TokenSet<StrokeColorsTokens, DynamicColor> by lazy{
    TokenSet { token ->
        when (token) {
            StrokeColorsTokens.neutral1 ->
                DynamicColor(
                    light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey94],
                    lightHighContrast = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey38],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey24],
                    darkHighContrast = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey68],
                    darkElevated = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey32]
                )
            StrokeColorsTokens.neutral2 ->
                DynamicColor (light = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey88],
                    lightHighContrast = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey38],
                    dark = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey32],
                    darkHighContrast = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey68],
                    darkElevated = globalTokens.neutralColors[GlobalTokens.NeutralColorsToken.grey36])
        }
    }
}

// MARK= - ShadowColors

enum class ShadowColorsTokens {
    neutralAmbient,
    neutralKey,
    neutralAmbientLighter,
    neutralKeyLighter,
    neutralAmbientDarker,
    neutralKeyDarker,
    brandAmbient,
    brandKey
}
open  val shadowColors: TokenSet<ShadowColorsTokens, DynamicColor> by lazy{
    TokenSet { token ->
        when (token) {
            ShadowColorsTokens.neutralAmbient ->
                DynamicColor(
                    light = Color( 0.0f, 0.0f,  0.0f, 12.0f),
                    dark = Color(0.0f,  0.0f,  0.0f,  24.0f)
                )
            ShadowColorsTokens.neutralKey ->
                DynamicColor (
                    light = Color(0.0f,  0.0f,  0.0f,  14.0f),
                    dark = Color(0.0f,  0.0f,  0.0f,  28.0f))
            ShadowColorsTokens.neutralAmbientLighter ->
                DynamicColor(
                    light = Color(0.0f,  0.0f,  0.0f,  6.0f),
                    dark = Color(0.0f, 0.0f,  0.0f,  12.0f)
                )
            ShadowColorsTokens.neutralKeyLighter ->
                DynamicColor(
                    light = Color(0.0f,  0.0f,  0.0f,  7.0f),
                    dark = Color(0.0f,  0.0f,  0.0f,  14.0f)
                )
            ShadowColorsTokens.neutralAmbientDarker ->
                DynamicColor (
                    light = Color(0.0f,  0.0f,  0.0f,  20.0f),
                    dark = Color(0.0f,  0.0f,  0.0f,  40.0f)
                )
            ShadowColorsTokens.neutralKeyDarker ->
                DynamicColor(
                    light = Color(0.0f,  0.0f,  0.0f,  24.0f),
                    dark = Color(0.0f,  0.0f,  0.0f,  48.0f)
                )
            ShadowColorsTokens.brandAmbient ->
                DynamicColor(
                    light = Color(0.0f,  0.0f,  0.0f,  30.0f),
                    dark = Color(0.0f,  0.0f,  0.0f,  30.0f)
                )
            ShadowColorsTokens.brandKey ->
                DynamicColor (
                    light = Color(0.0f,  0.0f,  0.0f,  25.0f),
                    dark = Color(0.0f,  0.0f,  0.0f,  25.0f)
                )
        }
    }
}

// MARK= - Typography

enum class TypographyTokens {
    display,
    largeTitle,
    title1,
    title2,
    title3,
    body1Strong,
    body1,
    body2Strong,
    body2,
    caption1Strong,
    caption1,
    caption2
}
open  val typography: TokenSet<TypographyTokens, FontInfo> by lazy{
    TokenSet { token ->
        when (token) {
            TypographyTokens.display ->
                FontInfo(
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size900],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.bold]
                )
            TypographyTokens.largeTitle ->
                FontInfo (
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size800],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.bold]
                )
            TypographyTokens.title1 ->
                FontInfo(
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size700],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.bold]
                    )
            TypographyTokens.title2 ->
                FontInfo(
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size600],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.semibold]
                )
            TypographyTokens.title3 ->
                FontInfo (
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size500],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.semibold]
                )
            TypographyTokens.body1Strong ->
                FontInfo(
                        size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size400],
                        weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.semibold]
                )
            TypographyTokens.body1 ->
                FontInfo(
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size400],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.regular]
                )
            TypographyTokens.body2Strong ->
                FontInfo (
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size300],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.semibold]
                )
            TypographyTokens.body2 ->
                FontInfo(
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size300],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.regular]
                )
            TypographyTokens.caption1Strong ->
                FontInfo(
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size200],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.semibold]
                )
            TypographyTokens.caption1 ->
                FontInfo (
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size200],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.regular]
                )
            TypographyTokens.caption2 ->
                FontInfo(
                    size = globalTokens.fontSize[GlobalTokens.FontSizeToken.size100],
                    weight = globalTokens.fontWeight[GlobalTokens.FontWeightToken.regular]
                    )
        }
    }
}

// MARK= - Shadow

enum class ShadowTokens {
    shadow02,
    shadow04,
    shadow08,
    shadow16,
    shadow28,
    shadow64,
}
open  val shadow: TokenSet<ShadowTokens, ShadowInfo>by lazy{
    TokenSet { token ->
        when (token) {
            ShadowTokens.shadow02 ->
                ShadowInfo(
                    colorOne = shadowColors[ShadowColorsTokens.neutralKey],
                    blurOne = 1,
                    xOne = 0,
                    yOne = 1,
                    colorTwo = shadowColors[ShadowColorsTokens.neutralAmbient],
                    blurTwo = 1,
                    xTwo = 0,
                    yTwo = 0
                )
            ShadowTokens.shadow04 ->
                ShadowInfo (colorOne = shadowColors[ShadowColorsTokens.neutralKey],
                    blurOne = 2,
                    xOne = 0,
                    yOne = 2,
                    colorTwo = shadowColors[ShadowColorsTokens.neutralAmbient],
                    blurTwo = 1,
                    xTwo = 0,
                    yTwo = 0
                )
            ShadowTokens.shadow08 ->
                ShadowInfo(
                    colorOne = shadowColors[ShadowColorsTokens.neutralKey],
                    blurOne = 4,
                    xOne = 0,
                    yOne = 4,
                    colorTwo = shadowColors[ShadowColorsTokens.neutralAmbient],
                    blurTwo = 1,
                    xTwo = 0,
                    yTwo = 0
                 )
            ShadowTokens.shadow16 ->
                ShadowInfo(
                    colorOne = shadowColors[ShadowColorsTokens.neutralKey],
                    blurOne = 8,
                    xOne = 0,
                    yOne = 8,
                    colorTwo = shadowColors[ShadowColorsTokens.neutralAmbient],
                    blurTwo = 1,
                    xTwo = 0,
                    yTwo = 0
                )
            ShadowTokens.shadow28 ->
                ShadowInfo (
                    colorOne = shadowColors[ShadowColorsTokens.neutralKeyDarker],
                    blurOne = 14,
                    xOne = 0,
                    yOne = 14,
                    colorTwo = shadowColors[ShadowColorsTokens.neutralAmbientDarker],
                    blurTwo = 4,
                    xTwo = 0,
                    yTwo = 0)
            ShadowTokens.shadow64 ->
                ShadowInfo(
                    colorOne = shadowColors[ShadowColorsTokens.neutralKeyDarker],
                    blurOne = 32,
                    xOne = 0,
                    yOne = 32,
                    colorTwo = shadowColors[ShadowColorsTokens.neutralAmbientDarker],
                    blurTwo = 4,
                    xTwo = 0,
                    yTwo = 0
                )
        }
    }
}

// MARK= Elevation

enum class ElevationTokens {
    interactiveElevation1Rest,
    interactiveElevation1Hover,
    interactiveElevation1Pressed,
    interactiveElevation1Selected,
    interactiveElevation1Disabled
}
open  val elevation: TokenSet<ElevationTokens, ShadowInfo> by lazy{
    TokenSet { token ->
        when (token) {
            ElevationTokens.interactiveElevation1Rest ->
                shadow[ShadowTokens.shadow08]
            ElevationTokens.interactiveElevation1Hover ->
                shadow[ShadowTokens.shadow02]
            ElevationTokens.interactiveElevation1Pressed ->
                shadow[ShadowTokens.shadow02]
            ElevationTokens.interactiveElevation1Selected ->
                shadow[ShadowTokens.shadow02]
            ElevationTokens.interactiveElevation1Disabled ->
                shadow[ShadowTokens.shadow02]
        }
    }
}


//open val globalTokens= GlobalTokens = FluentTheme.shared.globalTokens
}

internal val LocalAliasToken = compositionLocalOf { AliasTokens()}