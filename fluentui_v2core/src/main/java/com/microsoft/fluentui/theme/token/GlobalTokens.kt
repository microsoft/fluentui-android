//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Global Tokens represent a unified set of constants to be used by Fluent UI.

 open class GlobalTokens {

     enum class BrandColorTokens{
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

     enum class NeutralColorTokens{
        Black,
        Grey2,
        Grey4,
        Grey6,
        Grey8,
        Grey10,
        Grey12,
        Grey14,
        Grey16,
        Grey18,
        Grey20,
        Grey22,
        Grey24,
        Grey26,
        Grey28,
        Grey30,
        Grey32,
        Grey34,
        Grey36,
        Grey38,
        Grey40,
        Grey42,
        Grey44,
        Grey46,
        Grey48,
        Grey50,
        Grey52,
        Grey54,
        Grey56,
        Grey58,
        Grey60,
        Grey62,
        Grey64,
        Grey66,
        Grey68,
        Grey70,
        Grey72,
        Grey74,
        Grey76,
        Grey78,
        Grey80,
        Grey82,
        Grey84,
        Grey86,
        Grey88,
        Grey90,
        Grey92,
        Grey94,
        Grey96,
        Grey98,
        White
    }
     open val neutralColor: TokenSet<NeutralColorTokens, Color> by lazy{
        TokenSet{ token ->
            when(token){
                NeutralColorTokens.Black -> Color(0xFF000000)
                NeutralColorTokens.Grey2 -> Color(0xFF050505)
                NeutralColorTokens.Grey4 -> Color(0xFF0A0A0A)
                NeutralColorTokens.Grey6 -> Color(0xFF0F0F0F)
                NeutralColorTokens.Grey8 -> Color(0xFF141414)
                NeutralColorTokens.Grey10 -> Color(0xFF1A1A1A)
                NeutralColorTokens.Grey12 -> Color(0xFF1F1F1F)
                NeutralColorTokens.Grey14 -> Color(0xFF242424)
                NeutralColorTokens.Grey16 -> Color(0xFF292929)
                NeutralColorTokens.Grey18 -> Color(0xFF2E2E2E)
                NeutralColorTokens.Grey20 -> Color(0xFF333333)
                NeutralColorTokens.Grey22 -> Color(0xFF383838)
                NeutralColorTokens.Grey24 -> Color(0xFF3D3D3D)
                NeutralColorTokens.Grey26 -> Color(0xFF424242)
                NeutralColorTokens.Grey28 -> Color(0xFF474747)
                NeutralColorTokens.Grey30 -> Color(0xFF4D4D4D)
                NeutralColorTokens.Grey32 -> Color(0xFF525252)
                NeutralColorTokens.Grey34 -> Color(0xFF575757)
                NeutralColorTokens.Grey36 -> Color(0xFF5C5C5C)
                NeutralColorTokens.Grey38 -> Color(0xFF616161)
                NeutralColorTokens.Grey40 -> Color(0xFF666666)
                NeutralColorTokens.Grey42 -> Color(0xFF6B6B6B)
                NeutralColorTokens.Grey44 -> Color(0xFF707070)
                NeutralColorTokens.Grey46 -> Color(0xFF757575)
                NeutralColorTokens.Grey48 -> Color(0xFF7A7A7A)
                NeutralColorTokens.Grey50 -> Color(0xFF808080)
                NeutralColorTokens.Grey52 -> Color(0xFF858585)
                NeutralColorTokens.Grey54 -> Color(0xFF8A8A8A)
                NeutralColorTokens.Grey56 -> Color(0xFF8F8F8F)
                NeutralColorTokens.Grey58 -> Color(0xFF949494)
                NeutralColorTokens.Grey60 -> Color(0xFF999999)
                NeutralColorTokens.Grey62 -> Color(0xFF9E9E9E)
                NeutralColorTokens.Grey64 -> Color(0xFFA3A3A3)
                NeutralColorTokens.Grey66 -> Color(0xFFA8A8A8)
                NeutralColorTokens.Grey68 -> Color(0xFFADADAD)
                NeutralColorTokens.Grey70 -> Color(0xFFB3B3B3)
                NeutralColorTokens.Grey72 -> Color(0xFFB8B8B8)
                NeutralColorTokens.Grey74 -> Color(0xFFBDBDBD)
                NeutralColorTokens.Grey76 -> Color(0xFFC2C2C2)
                NeutralColorTokens.Grey78 -> Color(0xFFC7C7C7)
                NeutralColorTokens.Grey80 -> Color(0xFFCCCCCC)
                NeutralColorTokens.Grey82 -> Color(0xFFD1D1D1)
                NeutralColorTokens.Grey84 -> Color(0xFFD6D6D6)
                NeutralColorTokens.Grey86 -> Color(0xFFDBDBDB)
                NeutralColorTokens.Grey88 -> Color(0xFFE0E0E0)
                NeutralColorTokens.Grey90 -> Color(0xFFE6E6E6)
                NeutralColorTokens.Grey92 -> Color(0xFFEBEBEB)
                NeutralColorTokens.Grey94 -> Color(0xFFF0F0F0)
                NeutralColorTokens.Grey96 -> Color(0xFFF5F5F5)
                NeutralColorTokens.Grey98 -> Color(0xFFFAFAFA)
                NeutralColorTokens.White -> Color(0xFFFFFFFF)
            }
        }
    }

     enum class FontSizeTokens{
        Size100,
        Size200,
        Size300,
        Size400,
        Size500,
        Size600,
        Size700,
        Size800,
        Size900
    }

     open val fontSize: TokenSet<FontSizeTokens, FontSize> by lazy {
        TokenSet { token ->
            when (token) {
                FontSizeTokens.Size100 ->
                    FontSize(12.0.sp,16.sp)
                FontSizeTokens.Size200 ->
                    FontSize(13.0.sp,16.sp)
                FontSizeTokens.Size300 ->
                    FontSize(14.0.sp,18.sp)
                FontSizeTokens.Size400 ->
                    FontSize(16.0.sp,24.sp)
                FontSizeTokens.Size500 ->
                    FontSize(18.0.sp,24.sp)
                FontSizeTokens.Size600 ->
                    FontSize(20.0.sp,24.sp)
                FontSizeTokens.Size700 ->
                    FontSize(24.0.sp,32.sp)
                FontSizeTokens.Size800 ->
                    FontSize(34.0.sp,48.sp)
                FontSizeTokens.Size900 ->
                    FontSize(60.0.sp,72.sp)
            }
        }
    }

     enum class FontWeightTokens{
         Light,
         SemiLight,
         Regular,
         Medium,
         SemiBold,
         Bold
    }

     open val fontWeight: TokenSet<FontWeightTokens, FontWeight> by lazy {
        TokenSet { token ->
            when (token) {
                FontWeightTokens.Light ->
                    FontWeight.Light
                FontWeightTokens.SemiLight ->
                    FontWeight.ExtraLight
                FontWeightTokens.Regular ->
                    FontWeight.Normal
                FontWeightTokens.Medium ->
                    FontWeight.Medium
                FontWeightTokens.SemiBold ->
                    FontWeight.SemiBold
                FontWeightTokens.Bold ->
                    FontWeight.Bold
            }
        }
    }

     enum class IconSizeTokens{
         XXXSmall,
         XXXSmallSelected,
         XXSmall,
         XXSmallSelected,
         XSmall,
         XSmallSelected,
         Small,
         SmallSelected,
         Medium,
         MediumSelected,
         Large,
         LargeSelected,
         XLarge,
         XLargeSelected,
         XXLarge,
         XXLargeSelected,
         XXXLarge,
         XXXLargeSelected
    }

     open val iconSize: TokenSet<IconSizeTokens, IconSize> by lazy {
        TokenSet { token ->
            when (token) {
                IconSizeTokens.XXXSmall ->
                    IconSize(10.dp, IconType.Regular)
                IconSizeTokens.XXXSmallSelected ->
                    IconSize(10.dp, IconType.Filled)
                IconSizeTokens.XXSmall ->
                    IconSize(12.dp, IconType.Regular)
                IconSizeTokens.XXSmallSelected ->
                    IconSize(12.dp, IconType.Filled)
                IconSizeTokens.XSmall ->
                    IconSize(16.dp, IconType.Regular)
                IconSizeTokens.XSmallSelected ->
                    IconSize(16.dp, IconType.Filled)
                IconSizeTokens.Small ->
                    IconSize(20.dp, IconType.Regular)
                IconSizeTokens.SmallSelected ->
                    IconSize(20.dp, IconType.Filled)
                IconSizeTokens.Medium ->
                    IconSize(24.dp, IconType.Regular)
                IconSizeTokens.MediumSelected ->
                    IconSize(24.dp, IconType.Filled)
                IconSizeTokens.Large ->
                    IconSize(28.dp, IconType.Regular)
                IconSizeTokens.LargeSelected ->
                    IconSize(28.dp, IconType.Filled)
                IconSizeTokens.XLarge ->
                    IconSize(36.dp, IconType.Regular)
                IconSizeTokens.XLargeSelected ->
                    IconSize(36.dp, IconType.Filled)
                IconSizeTokens.XXLarge ->
                    IconSize(40.dp, IconType.Regular)
                IconSizeTokens.XXLargeSelected ->
                    IconSize(40.dp, IconType.Filled)
                IconSizeTokens.XXXLarge ->
                    IconSize(48.dp, IconType.Regular)
                IconSizeTokens.XXXLargeSelected ->
                    IconSize(48.dp, IconType.Filled)
            }
        }
    }

     enum class SpacingTokens{
        None,
        XXXSmall,
        XXSmall,
        XSmall,
        Small,
        Medium,
        Large,
        XLarge,
        XXLarge,
        XXXLarge,
        XXXXLarge
    }

     open val spacing: TokenSet<SpacingTokens, Dp> by lazy {
        TokenSet { token ->
            when (token) {
                SpacingTokens.None ->
                    0.dp
                SpacingTokens.XXXSmall ->
                    2.dp
                SpacingTokens.XXSmall ->
                    4.dp
                SpacingTokens.XSmall ->
                    8.dp
                SpacingTokens.Small ->
                    12.dp
                SpacingTokens.Medium ->
                    16.dp
                SpacingTokens.Large ->
                    20.dp
                SpacingTokens.XLarge ->
                    24.dp
                SpacingTokens.XXLarge ->
                    36.dp
                SpacingTokens.XXXLarge ->
                    48.dp
                SpacingTokens.XXXXLarge ->
                    72.dp
            }
        }
    }

     enum class ShadowColorTokens {
         NeutralAmbient,
         NeutralKey,
         NeutralAmbientLighter,
         NeutralKeyLighter,
         NeutralAmbientDarker,
         NeutralKeyDarker,
         BrandAmbient,
         BrandKey
     }

     open  val shadowColor: TokenSet<ShadowColorTokens, FluentColor> by lazy{
         TokenSet { token ->
             when (token) {
                 ShadowColorTokens.NeutralAmbient ->
                     FluentColor(
                         light = Color( 0.0f, 0.0f,  0.0f, 12.0f),
                         dark = Color(0.0f,  0.0f,  0.0f,  24.0f)
                     )
                 ShadowColorTokens.NeutralKey ->
                     FluentColor (
                         light = Color(0.0f,  0.0f,  0.0f,  14.0f),
                         dark = Color(0.0f,  0.0f,  0.0f,  28.0f))
                 ShadowColorTokens.NeutralAmbientLighter ->
                     FluentColor(
                         light = Color(0.0f,  0.0f,  0.0f,  6.0f),
                         dark = Color(0.0f, 0.0f,  0.0f,  12.0f)
                     )
                 ShadowColorTokens.NeutralKeyLighter ->
                     FluentColor(
                         light = Color(0.0f,  0.0f,  0.0f,  7.0f),
                         dark = Color(0.0f,  0.0f,  0.0f,  14.0f)
                     )
                 ShadowColorTokens.NeutralAmbientDarker ->
                     FluentColor (
                         light = Color(0.0f,  0.0f,  0.0f,  20.0f),
                         dark = Color(0.0f,  0.0f,  0.0f,  40.0f)
                     )
                 ShadowColorTokens.NeutralKeyDarker ->
                     FluentColor(
                         light = Color(0.0f,  0.0f,  0.0f,  24.0f),
                         dark = Color(0.0f,  0.0f,  0.0f,  48.0f)
                     )
                 ShadowColorTokens.BrandAmbient ->
                     FluentColor(
                         light = Color(0.0f,  0.0f,  0.0f,  30.0f),
                         dark = Color(0.0f,  0.0f,  0.0f,  30.0f)
                     )
                 ShadowColorTokens.BrandKey ->
                     FluentColor (
                         light = Color(0.0f,  0.0f,  0.0f,  25.0f),
                         dark = Color(0.0f,  0.0f,  0.0f,  25.0f)
                     )
             }
         }
     }

     enum class ShadowTokens {
         Shadow02,
         Shadow04,
         Shadow08,
         Shadow16,
         Shadow28,
         Shadow64,
     }
     open val shadow: TokenSet<ShadowTokens, ShadowInfo>by lazy{
         TokenSet { token ->
             when (token) {
                 ShadowTokens.Shadow02 ->
                     ShadowInfo(
                         colorOne = shadowColor[ShadowColorTokens.NeutralKey],
                         blurOne = 1,
                         xOne = 0,
                         yOne = 1,
                         colorTwo = shadowColor[ShadowColorTokens.NeutralAmbient],
                         blurTwo = 1,
                         xTwo = 0,
                         yTwo = 0
                     )
                 ShadowTokens.Shadow04 ->
                     ShadowInfo (colorOne = shadowColor[ShadowColorTokens.NeutralKey],
                         blurOne = 2,
                         xOne = 0,
                         yOne = 2,
                         colorTwo = shadowColor[ShadowColorTokens.NeutralAmbient],
                         blurTwo = 1,
                         xTwo = 0,
                         yTwo = 0
                     )
                 ShadowTokens.Shadow08 ->
                     ShadowInfo(
                         colorOne = shadowColor[ShadowColorTokens.NeutralKey],
                         blurOne = 4,
                         xOne = 0,
                         yOne = 4,
                         colorTwo = shadowColor[ShadowColorTokens.NeutralAmbient],
                         blurTwo = 1,
                         xTwo = 0,
                         yTwo = 0
                     )
                 ShadowTokens.Shadow16 ->
                     ShadowInfo(
                         colorOne = shadowColor[ShadowColorTokens.NeutralKey],
                         blurOne = 8,
                         xOne = 0,
                         yOne = 8,
                         colorTwo = shadowColor[ShadowColorTokens.NeutralAmbient],
                         blurTwo = 1,
                         xTwo = 0,
                         yTwo = 0
                     )
                 ShadowTokens.Shadow28 ->
                     ShadowInfo (
                         colorOne = shadowColor[ShadowColorTokens.NeutralKeyDarker],
                         blurOne = 14,
                         xOne = 0,
                         yOne = 14,
                         colorTwo = shadowColor[ShadowColorTokens.NeutralAmbientDarker],
                         blurTwo = 4,
                         xTwo = 0,
                         yTwo = 0)
                 ShadowTokens.Shadow64 ->
                     ShadowInfo(
                         colorOne = shadowColor[ShadowColorTokens.NeutralKeyDarker],
                         blurOne = 32,
                         xOne = 0,
                         yOne = 32,
                         colorTwo = shadowColor[ShadowColorTokens.NeutralAmbientDarker],
                         blurTwo = 4,
                         xTwo = 0,
                         yTwo = 0
                     )
             }
         }
     }

     enum class ElevationTokens {
         InteractiveElevation1Rest,
         InteractiveElevation1Hover,
         InteractiveElevation1Pressed,
         InteractiveElevation1Selected,
         InteractiveElevation1Disabled
     }

     open val elevation: TokenSet<ElevationTokens, ShadowInfo> by lazy{
         TokenSet { token ->
             when (token) {
                 ElevationTokens.InteractiveElevation1Rest ->
                     shadow[ShadowTokens.Shadow08]
                 ElevationTokens.InteractiveElevation1Hover ->
                     shadow[ShadowTokens.Shadow02]
                 ElevationTokens.InteractiveElevation1Pressed ->
                     shadow[ShadowTokens.Shadow02]
                 ElevationTokens.InteractiveElevation1Selected ->
                     shadow[ShadowTokens.Shadow02]
                 ElevationTokens.InteractiveElevation1Disabled ->
                     shadow[ShadowTokens.Shadow02]
             }
         }
     }

     enum class OpacityTokens{
         Opacity8,
         Opacity16,
         Opacity24,
         Opacity32,
         Opacity64,
         Opacity72,
         Opacity88,
         Opacity96
     }

     open val opacity: TokenSet<OpacityTokens, Float> by lazy {
         TokenSet { token ->
             when (token) {
                 OpacityTokens.Opacity8 -> 0.8f
                 OpacityTokens.Opacity16 -> 0.16f
                 OpacityTokens.Opacity24 -> 0.24f
                 OpacityTokens.Opacity32 -> 0.32f
                 OpacityTokens.Opacity64 -> 0.64f
                 OpacityTokens.Opacity72 -> 0.72f
                 OpacityTokens.Opacity88 -> 0.88f
                 OpacityTokens.Opacity96 -> 0.96f
             }
         }
     }

     enum class BorderRadiusTokens{
         None,
         Small,
         Medium,
         Large,
         XLarge,
         Circle
     }
     open val borderRadius: TokenSet<BorderRadiusTokens, Dp> by lazy {
         TokenSet { token ->
             when (token) {
                 BorderRadiusTokens.None ->
                     0.dp
                 BorderRadiusTokens.Small ->
                     2.dp
                 BorderRadiusTokens.Medium ->
                     4.dp
                 BorderRadiusTokens.Large ->
                     8.dp
                 BorderRadiusTokens.XLarge ->
                     12.dp
                 BorderRadiusTokens.Circle ->
                     9999.dp
             }
         }
     }

     enum class BorderSizeTokens{
        None,
        Thin,
        Thick,
        Thicker,
        Thickest
    }
     open val borderSize: TokenSet<BorderSizeTokens, Dp> by lazy {
        TokenSet { token ->
            when (token) {
                BorderSizeTokens.None ->
                    0.dp
                BorderSizeTokens.Thin ->
                    1.dp
                BorderSizeTokens.Thick ->
                    2.dp
                BorderSizeTokens.Thicker ->
                    4.dp
                BorderSizeTokens.Thickest ->
                    6.dp
            }
        }
    }
}

internal val LocalGlobalTokens = compositionLocalOf { GlobalTokens()}