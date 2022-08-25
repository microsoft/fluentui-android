//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import android.os.Parcelable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.parcelize.Parcelize

// Global Tokens represent a unified set of constants to be used by Fluent UI.

@Parcelize
open class GlobalTokens : Parcelable {

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

    enum class NeutralColorTokens {
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

    open val neutralColor: TokenSet<NeutralColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
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

    enum class FontSizeTokens {
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
                    FontSize(12.0.sp, 16.sp)
                FontSizeTokens.Size200 ->
                    FontSize(13.0.sp, 16.sp)
                FontSizeTokens.Size300 ->
                    FontSize(14.0.sp, 18.sp)
                FontSizeTokens.Size400 ->
                    FontSize(16.0.sp, 24.sp)
                FontSizeTokens.Size500 ->
                    FontSize(18.0.sp, 24.sp)
                FontSizeTokens.Size600 ->
                    FontSize(20.0.sp, 24.sp)
                FontSizeTokens.Size700 ->
                    FontSize(24.0.sp, 32.sp)
                FontSizeTokens.Size800 ->
                    FontSize(34.0.sp, 48.sp)
                FontSizeTokens.Size900 ->
                    FontSize(60.0.sp, 72.sp)
            }
        }
    }

    enum class FontWeightTokens {
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

    enum class IconSizeTokens {
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
                    IconSize(72.dp, IconType.Regular)
                IconSizeTokens.XXXLargeSelected ->
                    IconSize(72.dp, IconType.Filled)
            }
        }
    }

    enum class SpacingTokens {
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

    open val shadowColor: TokenSet<ShadowColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                ShadowColorTokens.NeutralAmbient ->
                    FluentColor(
                            light = Color(0, 0, 0, 12),
                            dark = Color(0, 0, 0, 24)
                    )
                ShadowColorTokens.NeutralKey ->
                    FluentColor(
                            light = Color(0, 0, 0, 14),
                            dark = Color(0, 0, 0, 28)
                    )
                ShadowColorTokens.NeutralAmbientLighter ->
                    FluentColor(
                            light = Color(0, 0, 0, 6),
                            dark = Color(0, 0, 0, 12)
                    )
                ShadowColorTokens.NeutralKeyLighter ->
                    FluentColor(
                            light = Color(0, 0, 0, 7),
                            dark = Color(0, 0, 0, 14)
                    )
                ShadowColorTokens.NeutralAmbientDarker ->
                    FluentColor(
                            light = Color(0, 0, 0, 20),
                            dark = Color(0, 0, 0, 40)
                    )
                ShadowColorTokens.NeutralKeyDarker ->
                    FluentColor(
                            light = Color(0, 0, 0, 24),
                            dark = Color(0, 0, 0, 48)
                    )
                ShadowColorTokens.BrandAmbient ->
                    FluentColor(
                            light = Color(0, 0, 0, 30),
                            dark = Color(0, 0, 0, 30)
                    )
                ShadowColorTokens.BrandKey ->
                    FluentColor(
                            light = Color(0, 0, 0, 25),
                            dark = Color(0, 0, 0, 25)
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

    open val elevation: TokenSet<ShadowTokens, Dp> by lazy {
        TokenSet { token ->
            when (token) {
                ShadowTokens.Shadow02 -> 2.dp
                ShadowTokens.Shadow04 -> 4.dp
                ShadowTokens.Shadow08 -> 8.dp
                ShadowTokens.Shadow16 -> 16.dp
                ShadowTokens.Shadow28 -> 28.dp
                ShadowTokens.Shadow64 -> 64.dp
            }
        }
    }

    enum class OpacityTokens {
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

    enum class BorderRadiusTokens {
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

    enum class BorderSizeTokens {
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

    enum class SharedColorSets {
        DarkRed,
        Burgundy,
        Cranberry,
        Red,
        DarkOrange,
        Bronze,
        Pumpkin,
        Orange,
        Peach,
        Marigold,
        Yellow,
        Gold,
        Brass,
        Brown,
        DarkBrown,
        Lime,
        Forest,
        Seafoam,
        LightGreen,
        Green,
        DarkGreen,
        LightTeal,
        Teal,
        DarkTeal,
        Cyan,
        Steel,
        LightBlue,
        Blue,
        RoyalBlue,
        DarkBlue,
        Cornflower,
        Navy,
        Lavender,
        Purple,
        DarkPurple,
        Orchid,
        Grape,
        Berry,
        Lilac,
        Pink,
        HotPink,
        Magenta,
        Plum,
        Beige,
        Mink,
        Silver,
        Platinum,
        Anchor,
        Charcoal
    }

    enum class SharedColorsTokens {
        shade50,
        shade40,
        shade30,
        shade20,
        shade10,
        primary,
        tint10,
        tint20,
        tint30,
        tint40,
        tint50,
        tint60
    }

    open val sharedColors: TokenSet<SharedColorSets, TokenSet<SharedColorsTokens, Color>> by lazy {
        TokenSet { sharedColor ->
            when (sharedColor) {
                SharedColorSets.Anchor ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF394146)
                            SharedColorsTokens.shade10 -> Color(0xFF333A3F)
                            SharedColorsTokens.shade20 -> Color(0xFF2B3135)
                            SharedColorsTokens.shade30 -> Color(0xFF202427)
                            SharedColorsTokens.shade40 -> Color(0xFF111315)
                            SharedColorsTokens.shade50 -> Color(0xFF090A0B)
                            SharedColorsTokens.tint10 -> Color(0xFF4D565C)
                            SharedColorsTokens.tint20 -> Color(0xFF626C72)
                            SharedColorsTokens.tint30 -> Color(0xFF808A90)
                            SharedColorsTokens.tint40 -> Color(0xFFBCC3C7)
                            SharedColorsTokens.tint50 -> Color(0xFFDBDFE1)
                            SharedColorsTokens.tint60 -> Color(0xFFF6F7F8)
                        }
                    }
                SharedColorSets.Beige ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF7A7574)
                            SharedColorsTokens.shade10 -> Color(0xFF6E6968)
                            SharedColorsTokens.shade20 -> Color(0xFF5D5958)
                            SharedColorsTokens.shade30 -> Color(0xFF444241)
                            SharedColorsTokens.shade40 -> Color(0xFF252323)
                            SharedColorsTokens.shade50 -> Color(0xFF141313)
                            SharedColorsTokens.tint10 -> Color(0xFF8A8584)
                            SharedColorsTokens.tint20 -> Color(0xFF9A9594)
                            SharedColorsTokens.tint30 -> Color(0xFFAFABAA)
                            SharedColorsTokens.tint40 -> Color(0xFFD7D4D4)
                            SharedColorsTokens.tint50 -> Color(0xFFEAE8E8)
                            SharedColorsTokens.tint60 -> Color(0xFFFAF9F9)
                        }
                    }
                SharedColorSets.Berry ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFC239B3)
                            SharedColorsTokens.shade10 -> Color(0xFFAF33A1)
                            SharedColorsTokens.shade20 -> Color(0xFF932B88)
                            SharedColorsTokens.shade30 -> Color(0xFF6D2064)
                            SharedColorsTokens.shade40 -> Color(0xFF3A1136)
                            SharedColorsTokens.shade50 -> Color(0xFF1F091D)
                            SharedColorsTokens.tint10 -> Color(0xFFC94CBC)
                            SharedColorsTokens.tint20 -> Color(0xFFD161C4)
                            SharedColorsTokens.tint30 -> Color(0xFFDA7ED0)
                            SharedColorsTokens.tint40 -> Color(0xFFEDBBE7)
                            SharedColorsTokens.tint50 -> Color(0xFFF5DAF2)
                            SharedColorsTokens.tint60 -> Color(0xFFFDF5FC)
                        }
                    }
                SharedColorSets.Blue ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF0078D4)
                            SharedColorsTokens.shade10 -> Color(0xFF006CBF)
                            SharedColorsTokens.shade20 -> Color(0xFF005BA1)
                            SharedColorsTokens.shade30 -> Color(0xFF004377)
                            SharedColorsTokens.shade40 -> Color(0xFF002440)
                            SharedColorsTokens.shade50 -> Color(0xFF001322)
                            SharedColorsTokens.tint10 -> Color(0xFF1A86D9)
                            SharedColorsTokens.tint20 -> Color(0xFF3595DE)
                            SharedColorsTokens.tint30 -> Color(0xFF5CAAE5)
                            SharedColorsTokens.tint40 -> Color(0xFFA9D3F2)
                            SharedColorsTokens.tint50 -> Color(0xFFD0E7F8)
                            SharedColorsTokens.tint60 -> Color(0xFFF3F9FD)
                        }
                    }
                SharedColorSets.Brass ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF986F0B)
                            SharedColorsTokens.shade10 -> Color(0xFF89640A)
                            SharedColorsTokens.shade20 -> Color(0xFF745408)
                            SharedColorsTokens.shade30 -> Color(0xFF553E06)
                            SharedColorsTokens.shade40 -> Color(0xFF2E2103)
                            SharedColorsTokens.shade50 -> Color(0xFF181202)
                            SharedColorsTokens.tint10 -> Color(0xFFA47D1E)
                            SharedColorsTokens.tint20 -> Color(0xFFB18C34)
                            SharedColorsTokens.tint30 -> Color(0xFFC1A256)
                            SharedColorsTokens.tint40 -> Color(0xFFE0CEA2)
                            SharedColorsTokens.tint50 -> Color(0xFFEFE4CB)
                            SharedColorsTokens.tint60 -> Color(0xFFFBF8F2)
                        }
                    }
                SharedColorSets.Bronze ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFA74109)
                            SharedColorsTokens.shade10 -> Color(0xFF963A08)
                            SharedColorsTokens.shade20 -> Color(0xFF7F3107)
                            SharedColorsTokens.shade30 -> Color(0xFF5E2405)
                            SharedColorsTokens.shade40 -> Color(0xFF321303)
                            SharedColorsTokens.shade50 -> Color(0xFF1B0A01)
                            SharedColorsTokens.tint10 -> Color(0xFFB2521E)
                            SharedColorsTokens.tint20 -> Color(0xFFBC6535)
                            SharedColorsTokens.tint30 -> Color(0xFFCA8057)
                            SharedColorsTokens.tint40 -> Color(0xFFE5BBA4)
                            SharedColorsTokens.tint50 -> Color(0xFFF1D9CC)
                            SharedColorsTokens.tint60 -> Color(0xFFFBF5F2)
                        }
                    }
                SharedColorSets.Brown ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF8E562E)
                            SharedColorsTokens.shade10 -> Color(0xFF804D29)
                            SharedColorsTokens.shade20 -> Color(0xFF6C4123)
                            SharedColorsTokens.shade30 -> Color(0xFF50301A)
                            SharedColorsTokens.shade40 -> Color(0xFF2B1A0E)
                            SharedColorsTokens.shade50 -> Color(0xFF170E07)
                            SharedColorsTokens.tint10 -> Color(0xFF9C663F)
                            SharedColorsTokens.tint20 -> Color(0xFFA97652)
                            SharedColorsTokens.tint30 -> Color(0xFFBB8F6F)
                            SharedColorsTokens.tint40 -> Color(0xFFDDC3B0)
                            SharedColorsTokens.tint50 -> Color(0xFFEDDED3)
                            SharedColorsTokens.tint60 -> Color(0xFFFAF7F4)
                        }
                    }
                SharedColorSets.Burgundy ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFA4262C)
                            SharedColorsTokens.shade10 -> Color(0xFF942228)
                            SharedColorsTokens.shade20 -> Color(0xFF7D1D21)
                            SharedColorsTokens.shade30 -> Color(0xFF5C1519)
                            SharedColorsTokens.shade40 -> Color(0xFF310B0D)
                            SharedColorsTokens.shade50 -> Color(0xFF1A0607)
                            SharedColorsTokens.tint10 -> Color(0xFFAF393E)
                            SharedColorsTokens.tint20 -> Color(0xFFBA4D52)
                            SharedColorsTokens.tint30 -> Color(0xFFC86C70)
                            SharedColorsTokens.tint40 -> Color(0xFFE4AFB2)
                            SharedColorsTokens.tint50 -> Color(0xFFF0D3D4)
                            SharedColorsTokens.tint60 -> Color(0xFFFBF4F4)
                        }
                    }
                SharedColorSets.Charcoal ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF393939)
                            SharedColorsTokens.shade10 -> Color(0xFF333333)
                            SharedColorsTokens.shade20 -> Color(0xFF2B2B2B)
                            SharedColorsTokens.shade30 -> Color(0xFF202020)
                            SharedColorsTokens.shade40 -> Color(0xFF111111)
                            SharedColorsTokens.shade50 -> Color(0xFF090909)
                            SharedColorsTokens.tint10 -> Color(0xFF515151)
                            SharedColorsTokens.tint20 -> Color(0xFF686868)
                            SharedColorsTokens.tint30 -> Color(0xFF888888)
                            SharedColorsTokens.tint40 -> Color(0xFFC4C4C4)
                            SharedColorsTokens.tint50 -> Color(0xFFDFDFDF)
                            SharedColorsTokens.tint60 -> Color(0xFFF7F7F7)
                        }
                    }
                SharedColorSets.Cornflower ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF4F6BED)
                            SharedColorsTokens.shade10 -> Color(0xFF4760D5)
                            SharedColorsTokens.shade20 -> Color(0xFF3C51B4)
                            SharedColorsTokens.shade30 -> Color(0xFF2C3C85)
                            SharedColorsTokens.shade40 -> Color(0xFF182047)
                            SharedColorsTokens.shade50 -> Color(0xFF0D1126)
                            SharedColorsTokens.tint10 -> Color(0xFF637CEF)
                            SharedColorsTokens.tint20 -> Color(0xFF778DF1)
                            SharedColorsTokens.tint30 -> Color(0xFF93A4F4)
                            SharedColorsTokens.tint40 -> Color(0xFFC8D1FA)
                            SharedColorsTokens.tint50 -> Color(0xFFE1E6FC)
                            SharedColorsTokens.tint60 -> Color(0xFFF7F9FE)
                        }
                    }
                SharedColorSets.Cranberry ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFC50F1F)
                            SharedColorsTokens.shade10 -> Color(0xFFB10E1C)
                            SharedColorsTokens.shade20 -> Color(0xFF960B18)
                            SharedColorsTokens.shade30 -> Color(0xFF6E0811)
                            SharedColorsTokens.shade40 -> Color(0xFF3B0509)
                            SharedColorsTokens.shade50 -> Color(0xFF200205)
                            SharedColorsTokens.tint10 -> Color(0xFFCC2635)
                            SharedColorsTokens.tint20 -> Color(0xFFD33F4C)
                            SharedColorsTokens.tint30 -> Color(0xFFDC626D)
                            SharedColorsTokens.tint40 -> Color(0xFFEEACB2)
                            SharedColorsTokens.tint50 -> Color(0xFFF6D1D5)
                            SharedColorsTokens.tint60 -> Color(0xFFFDF3F4)
                        }
                    }
                SharedColorSets.Cyan ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF0099BC)
                            SharedColorsTokens.shade10 -> Color(0xFF008AA9)
                            SharedColorsTokens.shade20 -> Color(0xFF00748F)
                            SharedColorsTokens.shade30 -> Color(0xFF005669)
                            SharedColorsTokens.shade40 -> Color(0xFF002E38)
                            SharedColorsTokens.shade50 -> Color(0xFF00181E)
                            SharedColorsTokens.tint10 -> Color(0xFF18A4C4)
                            SharedColorsTokens.tint20 -> Color(0xFF31AFCC)
                            SharedColorsTokens.tint30 -> Color(0xFF56BFD7)
                            SharedColorsTokens.tint40 -> Color(0xFFA4DEEB)
                            SharedColorsTokens.tint50 -> Color(0xFFCDEDF4)
                            SharedColorsTokens.tint60 -> Color(0xFFF2FAFC)
                        }
                    }
                SharedColorSets.DarkBlue ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF003966)
                            SharedColorsTokens.shade10 -> Color(0xFF00335C)
                            SharedColorsTokens.shade20 -> Color(0xFF002B4E)
                            SharedColorsTokens.shade30 -> Color(0xFF002039)
                            SharedColorsTokens.shade40 -> Color(0xFF00111F)
                            SharedColorsTokens.shade50 -> Color(0xFF000910)
                            SharedColorsTokens.tint10 -> Color(0xFF0E4A78)
                            SharedColorsTokens.tint20 -> Color(0xFF215C8B)
                            SharedColorsTokens.tint30 -> Color(0xFF4178A3)
                            SharedColorsTokens.tint40 -> Color(0xFF92B5D1)
                            SharedColorsTokens.tint50 -> Color(0xFFC2D6E7)
                            SharedColorsTokens.tint60 -> Color(0xFFEFF4F9)
                        }
                    }
                SharedColorSets.DarkBrown ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF4D291C)
                            SharedColorsTokens.shade10 -> Color(0xFF452519)
                            SharedColorsTokens.shade20 -> Color(0xFF3A1F15)
                            SharedColorsTokens.shade30 -> Color(0xFF2B1710)
                            SharedColorsTokens.shade40 -> Color(0xFF170C08)
                            SharedColorsTokens.shade50 -> Color(0xFF0C0704)
                            SharedColorsTokens.tint10 -> Color(0xFF623A2B)
                            SharedColorsTokens.tint20 -> Color(0xFF784D3E)
                            SharedColorsTokens.tint30 -> Color(0xFF946B5C)
                            SharedColorsTokens.tint40 -> Color(0xFFCAADA3)
                            SharedColorsTokens.tint50 -> Color(0xFFE3D2CB)
                            SharedColorsTokens.tint60 -> Color(0xFFF8F3F2)
                        }
                    }
                SharedColorSets.DarkGreen ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF0B6A0B)
                            SharedColorsTokens.shade10 -> Color(0xFF0A5F0A)
                            SharedColorsTokens.shade20 -> Color(0xFF085108)
                            SharedColorsTokens.shade30 -> Color(0xFF063B06)
                            SharedColorsTokens.shade40 -> Color(0xFF032003)
                            SharedColorsTokens.shade50 -> Color(0xFF021102)
                            SharedColorsTokens.tint10 -> Color(0xFF1A7C1A)
                            SharedColorsTokens.tint20 -> Color(0xFF2D8E2D)
                            SharedColorsTokens.tint30 -> Color(0xFF4DA64D)
                            SharedColorsTokens.tint40 -> Color(0xFF9AD29A)
                            SharedColorsTokens.tint50 -> Color(0xFFC6E7C6)
                            SharedColorsTokens.tint60 -> Color(0xFFF0F9F0)
                        }
                    }
                SharedColorSets.DarkOrange ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFDA3B01)
                            SharedColorsTokens.shade10 -> Color(0xFFC43501)
                            SharedColorsTokens.shade20 -> Color(0xFFA62D01)
                            SharedColorsTokens.shade30 -> Color(0xFF7A2101)
                            SharedColorsTokens.shade40 -> Color(0xFF411200)
                            SharedColorsTokens.shade50 -> Color(0xFF230900)
                            SharedColorsTokens.tint10 -> Color(0xFFDE501C)
                            SharedColorsTokens.tint20 -> Color(0xFFE36537)
                            SharedColorsTokens.tint30 -> Color(0xFFE9835E)
                            SharedColorsTokens.tint40 -> Color(0xFFF4BFAB)
                            SharedColorsTokens.tint50 -> Color(0xFFF9DCD1)
                            SharedColorsTokens.tint60 -> Color(0xFFFDF6F3)
                        }
                    }
                SharedColorSets.DarkPurple ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF401B6C)
                            SharedColorsTokens.shade10 -> Color(0xFF3A1861)
                            SharedColorsTokens.shade20 -> Color(0xFF311552)
                            SharedColorsTokens.shade30 -> Color(0xFF240F3C)
                            SharedColorsTokens.shade40 -> Color(0xFF130820)
                            SharedColorsTokens.shade50 -> Color(0xFF0A0411)
                            SharedColorsTokens.tint10 -> Color(0xFF512B7E)
                            SharedColorsTokens.tint20 -> Color(0xFF633E8F)
                            SharedColorsTokens.tint30 -> Color(0xFF7E5CA7)
                            SharedColorsTokens.tint40 -> Color(0xFFB9A3D3)
                            SharedColorsTokens.tint50 -> Color(0xFFD8CCE7)
                            SharedColorsTokens.tint60 -> Color(0xFFF5F2F9)
                        }
                    }
                SharedColorSets.DarkRed ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF750B1C)
                            SharedColorsTokens.shade10 -> Color(0xFF690A19)
                            SharedColorsTokens.shade20 -> Color(0xFF590815)
                            SharedColorsTokens.shade30 -> Color(0xFF420610)
                            SharedColorsTokens.shade40 -> Color(0xFF230308)
                            SharedColorsTokens.shade50 -> Color(0xFF130204)
                            SharedColorsTokens.tint10 -> Color(0xFF861B2C)
                            SharedColorsTokens.tint20 -> Color(0xFF962F3F)
                            SharedColorsTokens.tint30 -> Color(0xFFAC4F5E)
                            SharedColorsTokens.tint40 -> Color(0xFFD69CA5)
                            SharedColorsTokens.tint50 -> Color(0xFFE9C7CD)
                            SharedColorsTokens.tint60 -> Color(0xFFF9F0F2)
                        }
                    }
                SharedColorSets.DarkTeal ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF006666)
                            SharedColorsTokens.shade10 -> Color(0xFF005C5C)
                            SharedColorsTokens.shade20 -> Color(0xFF004E4E)
                            SharedColorsTokens.shade30 -> Color(0xFF003939)
                            SharedColorsTokens.shade40 -> Color(0xFF001F1F)
                            SharedColorsTokens.shade50 -> Color(0xFF001010)
                            SharedColorsTokens.tint10 -> Color(0xFF0E7878)
                            SharedColorsTokens.tint20 -> Color(0xFF218B8B)
                            SharedColorsTokens.tint30 -> Color(0xFF41A3A3)
                            SharedColorsTokens.tint40 -> Color(0xFF92D1D1)
                            SharedColorsTokens.tint50 -> Color(0xFFC2E7E7)
                            SharedColorsTokens.tint60 -> Color(0xFFEFF9F9)
                        }
                    }
                SharedColorSets.Forest ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF498205)
                            SharedColorsTokens.shade10 -> Color(0xFF427505)
                            SharedColorsTokens.shade20 -> Color(0xFF376304)
                            SharedColorsTokens.shade30 -> Color(0xFF294903)
                            SharedColorsTokens.shade40 -> Color(0xFF162702)
                            SharedColorsTokens.shade50 -> Color(0xFF0C1501)
                            SharedColorsTokens.tint10 -> Color(0xFF599116)
                            SharedColorsTokens.tint20 -> Color(0xFF6BA02B)
                            SharedColorsTokens.tint30 -> Color(0xFF85B44C)
                            SharedColorsTokens.tint40 -> Color(0xFFBDD99B)
                            SharedColorsTokens.tint50 -> Color(0xFFDBEBC7)
                            SharedColorsTokens.tint60 -> Color(0xFFF6FAF0)
                        }
                    }
                SharedColorSets.Gold ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFC19C00)
                            SharedColorsTokens.shade10 -> Color(0xFFAE8C00)
                            SharedColorsTokens.shade20 -> Color(0xFF937700)
                            SharedColorsTokens.shade30 -> Color(0xFF6C5700)
                            SharedColorsTokens.shade40 -> Color(0xFF3A2F00)
                            SharedColorsTokens.shade50 -> Color(0xFF1F1900)
                            SharedColorsTokens.tint10 -> Color(0xFFC8A718)
                            SharedColorsTokens.tint20 -> Color(0xFFD0B232)
                            SharedColorsTokens.tint30 -> Color(0xFFDAC157)
                            SharedColorsTokens.tint40 -> Color(0xFFECDFA5)
                            SharedColorsTokens.tint50 -> Color(0xFFF5EECE)
                            SharedColorsTokens.tint60 -> Color(0xFFFDFBF2)
                        }
                    }
                SharedColorSets.Grape ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF881798)
                            SharedColorsTokens.shade10 -> Color(0xFF7A1589)
                            SharedColorsTokens.shade20 -> Color(0xFF671174)
                            SharedColorsTokens.shade30 -> Color(0xFF4C0D55)
                            SharedColorsTokens.shade40 -> Color(0xFF29072E)
                            SharedColorsTokens.shade50 -> Color(0xFF160418)
                            SharedColorsTokens.tint10 -> Color(0xFF952AA4)
                            SharedColorsTokens.tint20 -> Color(0xFFA33FB1)
                            SharedColorsTokens.tint30 -> Color(0xFFB55FC1)
                            SharedColorsTokens.tint40 -> Color(0xFFD9A7E0)
                            SharedColorsTokens.tint50 -> Color(0xFFEACEEF)
                            SharedColorsTokens.tint60 -> Color(0xFFFAF2FB)
                        }
                    }
                SharedColorSets.Green ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF107C10)
                            SharedColorsTokens.shade10 -> Color(0xFF0E700E)
                            SharedColorsTokens.shade20 -> Color(0xFF0C5E0C)
                            SharedColorsTokens.shade30 -> Color(0xFF094509)
                            SharedColorsTokens.shade40 -> Color(0xFF052505)
                            SharedColorsTokens.shade50 -> Color(0xFF031403)
                            SharedColorsTokens.tint10 -> Color(0xFF218C21)
                            SharedColorsTokens.tint20 -> Color(0xFF359B35)
                            SharedColorsTokens.tint30 -> Color(0xFF54B054)
                            SharedColorsTokens.tint40 -> Color(0xFF9FD89F)
                            SharedColorsTokens.tint50 -> Color(0xFFC9EAC9)
                            SharedColorsTokens.tint60 -> Color(0xFFF1FAF1)
                        }
                    }
                SharedColorSets.HotPink ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFE3008C)
                            SharedColorsTokens.shade10 -> Color(0xFFCC007E)
                            SharedColorsTokens.shade20 -> Color(0xFFAD006A)
                            SharedColorsTokens.shade30 -> Color(0xFF7F004E)
                            SharedColorsTokens.shade40 -> Color(0xFF44002A)
                            SharedColorsTokens.shade50 -> Color(0xFF240016)
                            SharedColorsTokens.tint10 -> Color(0xFFE61C99)
                            SharedColorsTokens.tint20 -> Color(0xFFEA38A6)
                            SharedColorsTokens.tint30 -> Color(0xFFEE5FB7)
                            SharedColorsTokens.tint40 -> Color(0xFFF7ADDA)
                            SharedColorsTokens.tint50 -> Color(0xFFFBD2EB)
                            SharedColorsTokens.tint60 -> Color(0xFFFEF4FA)
                        }
                    }
                SharedColorSets.Lavender ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF7160E8)
                            SharedColorsTokens.shade10 -> Color(0xFF6656D1)
                            SharedColorsTokens.shade20 -> Color(0xFF5649B0)
                            SharedColorsTokens.shade30 -> Color(0xFF3F3682)
                            SharedColorsTokens.shade40 -> Color(0xFF221D46)
                            SharedColorsTokens.shade50 -> Color(0xFF120F25)
                            SharedColorsTokens.tint10 -> Color(0xFF8172EB)
                            SharedColorsTokens.tint20 -> Color(0xFF9184EE)
                            SharedColorsTokens.tint30 -> Color(0xFFA79CF1)
                            SharedColorsTokens.tint40 -> Color(0xFFD2CCF8)
                            SharedColorsTokens.tint50 -> Color(0xFFE7E4FB)
                            SharedColorsTokens.tint60 -> Color(0xFFF9F8FE)
                        }
                    }
                SharedColorSets.LightBlue ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF3A96DD)
                            SharedColorsTokens.shade10 -> Color(0xFF3487C7)
                            SharedColorsTokens.shade20 -> Color(0xFF2C72A8)
                            SharedColorsTokens.shade30 -> Color(0xFF20547C)
                            SharedColorsTokens.shade40 -> Color(0xFF112D42)
                            SharedColorsTokens.shade50 -> Color(0xFF091823)
                            SharedColorsTokens.tint10 -> Color(0xFF4FA1E1)
                            SharedColorsTokens.tint20 -> Color(0xFF65ADE5)
                            SharedColorsTokens.tint30 -> Color(0xFF83BDEB)
                            SharedColorsTokens.tint40 -> Color(0xFFBFDDF5)
                            SharedColorsTokens.tint50 -> Color(0xFFDCEDFA)
                            SharedColorsTokens.tint60 -> Color(0xFFF6FAFE)
                        }
                    }
                SharedColorSets.LightGreen ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF13A10E)
                            SharedColorsTokens.shade10 -> Color(0xFF11910D)
                            SharedColorsTokens.shade20 -> Color(0xFF0E7A0B)
                            SharedColorsTokens.shade30 -> Color(0xFF0B5A08)
                            SharedColorsTokens.shade40 -> Color(0xFF063004)
                            SharedColorsTokens.shade50 -> Color(0xFF031A02)
                            SharedColorsTokens.tint10 -> Color(0xFF27AC22)
                            SharedColorsTokens.tint20 -> Color(0xFF3DB838)
                            SharedColorsTokens.tint30 -> Color(0xFF5EC75A)
                            SharedColorsTokens.tint40 -> Color(0xFFA7E3A5)
                            SharedColorsTokens.tint50 -> Color(0xFFCEF0CD)
                            SharedColorsTokens.tint60 -> Color(0xFFF2FBF2)
                        }
                    }
                SharedColorSets.LightTeal ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF00B7C3)
                            SharedColorsTokens.shade10 -> Color(0xFF00A5AF)
                            SharedColorsTokens.shade20 -> Color(0xFF008B94)
                            SharedColorsTokens.shade30 -> Color(0xFF00666D)
                            SharedColorsTokens.shade40 -> Color(0xFF00373A)
                            SharedColorsTokens.shade50 -> Color(0xFF001D1F)
                            SharedColorsTokens.tint10 -> Color(0xFF18BFCA)
                            SharedColorsTokens.tint20 -> Color(0xFF32C8D1)
                            SharedColorsTokens.tint30 -> Color(0xFF58D3DB)
                            SharedColorsTokens.tint40 -> Color(0xFFA6E9ED)
                            SharedColorsTokens.tint50 -> Color(0xFFCEF3F5)
                            SharedColorsTokens.tint60 -> Color(0xFFF2FCFD)
                        }
                    }
                SharedColorSets.Lilac ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFB146C2)
                            SharedColorsTokens.shade10 -> Color(0xFF9F3FAF)
                            SharedColorsTokens.shade20 -> Color(0xFF863593)
                            SharedColorsTokens.shade30 -> Color(0xFF63276D)
                            SharedColorsTokens.shade40 -> Color(0xFF35153A)
                            SharedColorsTokens.shade50 -> Color(0xFF1C0B1F)
                            SharedColorsTokens.tint10 -> Color(0xFFBA58C9)
                            SharedColorsTokens.tint20 -> Color(0xFFC36BD1)
                            SharedColorsTokens.tint30 -> Color(0xFFCF87DA)
                            SharedColorsTokens.tint40 -> Color(0xFFE6BFED)
                            SharedColorsTokens.tint50 -> Color(0xFFF2DCF5)
                            SharedColorsTokens.tint60 -> Color(0xFFFCF6FD)
                        }
                    }
                SharedColorSets.Lime ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF73AA24)
                            SharedColorsTokens.shade10 -> Color(0xFF689920)
                            SharedColorsTokens.shade20 -> Color(0xFF57811B)
                            SharedColorsTokens.shade30 -> Color(0xFF405F14)
                            SharedColorsTokens.shade40 -> Color(0xFF23330B)
                            SharedColorsTokens.shade50 -> Color(0xFF121B06)
                            SharedColorsTokens.tint10 -> Color(0xFF81B437)
                            SharedColorsTokens.tint20 -> Color(0xFF90BE4C)
                            SharedColorsTokens.tint30 -> Color(0xFFA4CC6C)
                            SharedColorsTokens.tint40 -> Color(0xFFCFE5AF)
                            SharedColorsTokens.tint50 -> Color(0xFFE5F1D3)
                            SharedColorsTokens.tint60 -> Color(0xFFF8FCF4)
                        }
                    }
                SharedColorSets.Magenta ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFBF0077)
                            SharedColorsTokens.shade10 -> Color(0xFFAC006B)
                            SharedColorsTokens.shade20 -> Color(0xFF91005A)
                            SharedColorsTokens.shade30 -> Color(0xFF6B0043)
                            SharedColorsTokens.shade40 -> Color(0xFF390024)
                            SharedColorsTokens.shade50 -> Color(0xFF1F0013)
                            SharedColorsTokens.tint10 -> Color(0xFFC71885)
                            SharedColorsTokens.tint20 -> Color(0xFFCE3293)
                            SharedColorsTokens.tint30 -> Color(0xFFD957A8)
                            SharedColorsTokens.tint40 -> Color(0xFFECA5D1)
                            SharedColorsTokens.tint50 -> Color(0xFFF5CEE6)
                            SharedColorsTokens.tint60 -> Color(0xFFFCF2F9)
                        }
                    }
                SharedColorSets.Marigold ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFEAA300)
                            SharedColorsTokens.shade10 -> Color(0xFFD39300)
                            SharedColorsTokens.shade20 -> Color(0xFFB27C00)
                            SharedColorsTokens.shade30 -> Color(0xFF835B00)
                            SharedColorsTokens.shade40 -> Color(0xFF463100)
                            SharedColorsTokens.shade50 -> Color(0xFF251A00)
                            SharedColorsTokens.tint10 -> Color(0xFFEDAD1C)
                            SharedColorsTokens.tint20 -> Color(0xFFEFB839)
                            SharedColorsTokens.tint30 -> Color(0xFFF2C661)
                            SharedColorsTokens.tint40 -> Color(0xFFF9E2AE)
                            SharedColorsTokens.tint50 -> Color(0xFFFCEFD3)
                            SharedColorsTokens.tint60 -> Color(0xFFFEFBF4)
                        }
                    }
                SharedColorSets.Mink ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF5D5A58)
                            SharedColorsTokens.shade10 -> Color(0xFF54514F)
                            SharedColorsTokens.shade20 -> Color(0xFF474443)
                            SharedColorsTokens.shade30 -> Color(0xFF343231)
                            SharedColorsTokens.shade40 -> Color(0xFF1C1B1A)
                            SharedColorsTokens.shade50 -> Color(0xFF0F0E0E)
                            SharedColorsTokens.tint10 -> Color(0xFF706D6B)
                            SharedColorsTokens.tint20 -> Color(0xFF84817E)
                            SharedColorsTokens.tint30 -> Color(0xFF9E9B99)
                            SharedColorsTokens.tint40 -> Color(0xFFCECCCB)
                            SharedColorsTokens.tint50 -> Color(0xFFE5E4E3)
                            SharedColorsTokens.tint60 -> Color(0xFFF8F8F8)
                        }
                    }
                SharedColorSets.Navy ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF0027B4)
                            SharedColorsTokens.shade10 -> Color(0xFF0023A2)
                            SharedColorsTokens.shade20 -> Color(0xFF001E89)
                            SharedColorsTokens.shade30 -> Color(0xFF001665)
                            SharedColorsTokens.shade40 -> Color(0xFF000C36)
                            SharedColorsTokens.shade50 -> Color(0xFF00061D)
                            SharedColorsTokens.tint10 -> Color(0xFF173BBD)
                            SharedColorsTokens.tint20 -> Color(0xFF3050C6)
                            SharedColorsTokens.tint30 -> Color(0xFF546FD2)
                            SharedColorsTokens.tint40 -> Color(0xFFA3B2E8)
                            SharedColorsTokens.tint50 -> Color(0xFFCCD5F3)
                            SharedColorsTokens.tint60 -> Color(0xFFF2F4FC)
                        }
                    }
                SharedColorSets.Orange ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFF7630C)
                            SharedColorsTokens.shade10 -> Color(0xFFDE590B)
                            SharedColorsTokens.shade20 -> Color(0xFFBC4B09)
                            SharedColorsTokens.shade30 -> Color(0xFF8A3707)
                            SharedColorsTokens.shade40 -> Color(0xFF4A1E04)
                            SharedColorsTokens.shade50 -> Color(0xFF271002)
                            SharedColorsTokens.tint10 -> Color(0xFFF87528)
                            SharedColorsTokens.tint20 -> Color(0xFFF98845)
                            SharedColorsTokens.tint30 -> Color(0xFFFAA06B)
                            SharedColorsTokens.tint40 -> Color(0xFFFDCFB4)
                            SharedColorsTokens.tint50 -> Color(0xFFFEE5D7)
                            SharedColorsTokens.tint60 -> Color(0xFFFFF9F5)
                        }
                    }
                SharedColorSets.Orchid ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF8764B8)
                            SharedColorsTokens.shade10 -> Color(0xFF795AA6)
                            SharedColorsTokens.shade20 -> Color(0xFF674C8C)
                            SharedColorsTokens.shade30 -> Color(0xFF4C3867)
                            SharedColorsTokens.shade40 -> Color(0xFF281E37)
                            SharedColorsTokens.shade50 -> Color(0xFF16101D)
                            SharedColorsTokens.tint10 -> Color(0xFF9373C0)
                            SharedColorsTokens.tint20 -> Color(0xFFA083C9)
                            SharedColorsTokens.tint30 -> Color(0xFFB29AD4)
                            SharedColorsTokens.tint40 -> Color(0xFFD7CAEA)
                            SharedColorsTokens.tint50 -> Color(0xFFE9E2F4)
                            SharedColorsTokens.tint60 -> Color(0xFFF9F8FC)
                        }
                    }
                SharedColorSets.Peach ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFFF8C00)
                            SharedColorsTokens.shade10 -> Color(0xFFE67E00)
                            SharedColorsTokens.shade20 -> Color(0xFFC26A00)
                            SharedColorsTokens.shade30 -> Color(0xFF8F4E00)
                            SharedColorsTokens.shade40 -> Color(0xFF4D2A00)
                            SharedColorsTokens.shade50 -> Color(0xFF291600)
                            SharedColorsTokens.tint10 -> Color(0xFFFF9A1F)
                            SharedColorsTokens.tint20 -> Color(0xFFFFA83D)
                            SharedColorsTokens.tint30 -> Color(0xFFFFBA66)
                            SharedColorsTokens.tint40 -> Color(0xFFFFDDB3)
                            SharedColorsTokens.tint50 -> Color(0xFFFFEDD6)
                            SharedColorsTokens.tint60 -> Color(0xFFFFFAF5)
                        }
                    }
                SharedColorSets.Pink ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFE43BA6)
                            SharedColorsTokens.shade10 -> Color(0xFFCD3595)
                            SharedColorsTokens.shade20 -> Color(0xFFAD2D7E)
                            SharedColorsTokens.shade30 -> Color(0xFF80215D)
                            SharedColorsTokens.shade40 -> Color(0xFF441232)
                            SharedColorsTokens.shade50 -> Color(0xFF24091B)
                            SharedColorsTokens.tint10 -> Color(0xFFE750B0)
                            SharedColorsTokens.tint20 -> Color(0xFFEA66BA)
                            SharedColorsTokens.tint30 -> Color(0xFFEF85C8)
                            SharedColorsTokens.tint40 -> Color(0xFFF7C0E3)
                            SharedColorsTokens.tint50 -> Color(0xFFFBDDF0)
                            SharedColorsTokens.tint60 -> Color(0xFFFEF6FB)
                        }
                    }
                SharedColorSets.Platinum ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF69797E)
                            SharedColorsTokens.shade10 -> Color(0xFF5F6D71)
                            SharedColorsTokens.shade20 -> Color(0xFF505C60)
                            SharedColorsTokens.shade30 -> Color(0xFF3B4447)
                            SharedColorsTokens.shade40 -> Color(0xFF1F2426)
                            SharedColorsTokens.shade50 -> Color(0xFF111314)
                            SharedColorsTokens.tint10 -> Color(0xFF79898D)
                            SharedColorsTokens.tint20 -> Color(0xFF89989D)
                            SharedColorsTokens.tint30 -> Color(0xFFA0ADB2)
                            SharedColorsTokens.tint40 -> Color(0xFFCDD6D8)
                            SharedColorsTokens.tint50 -> Color(0xFFE4E9EA)
                            SharedColorsTokens.tint60 -> Color(0xFFF8F9FA)
                        }
                    }
                SharedColorSets.Plum ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF77004D)
                            SharedColorsTokens.shade10 -> Color(0xFF6B0045)
                            SharedColorsTokens.shade20 -> Color(0xFF5A003B)
                            SharedColorsTokens.shade30 -> Color(0xFF43002B)
                            SharedColorsTokens.shade40 -> Color(0xFF240017)
                            SharedColorsTokens.shade50 -> Color(0xFF13000C)
                            SharedColorsTokens.tint10 -> Color(0xFF87105D)
                            SharedColorsTokens.tint20 -> Color(0xFF98246F)
                            SharedColorsTokens.tint30 -> Color(0xFFAD4589)
                            SharedColorsTokens.tint40 -> Color(0xFFD696C0)
                            SharedColorsTokens.tint50 -> Color(0xFFE9C4DC)
                            SharedColorsTokens.tint60 -> Color(0xFFFAF0F6)
                        }
                    }
                SharedColorSets.Pumpkin ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFCA5010)
                            SharedColorsTokens.shade10 -> Color(0xFFB6480E)
                            SharedColorsTokens.shade20 -> Color(0xFF9A3D0C)
                            SharedColorsTokens.shade30 -> Color(0xFF712D09)
                            SharedColorsTokens.shade40 -> Color(0xFF3D1805)
                            SharedColorsTokens.shade50 -> Color(0xFF200D03)
                            SharedColorsTokens.tint10 -> Color(0xFFD06228)
                            SharedColorsTokens.tint20 -> Color(0xFFD77440)
                            SharedColorsTokens.tint30 -> Color(0xFFDF8E64)
                            SharedColorsTokens.tint40 -> Color(0xFFEFC4AD)
                            SharedColorsTokens.tint50 -> Color(0xFFF7DFD2)
                            SharedColorsTokens.tint60 -> Color(0xFFFDF7F4)
                        }
                    }
                SharedColorSets.Purple ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF5C2E91)
                            SharedColorsTokens.shade10 -> Color(0xFF532982)
                            SharedColorsTokens.shade20 -> Color(0xFF46236E)
                            SharedColorsTokens.shade30 -> Color(0xFF341A51)
                            SharedColorsTokens.shade40 -> Color(0xFF1C0E2B)
                            SharedColorsTokens.shade50 -> Color(0xFF0F0717)
                            SharedColorsTokens.tint10 -> Color(0xFF6B3F9E)
                            SharedColorsTokens.tint20 -> Color(0xFF7C52AB)
                            SharedColorsTokens.tint30 -> Color(0xFF9470BD)
                            SharedColorsTokens.tint40 -> Color(0xFFC6B1DE)
                            SharedColorsTokens.tint50 -> Color(0xFFE0D3ED)
                            SharedColorsTokens.tint60 -> Color(0xFFF7F4FB)
                        }
                    }
                SharedColorSets.Red ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFD13438)
                            SharedColorsTokens.shade10 -> Color(0xFFBC2F32)
                            SharedColorsTokens.shade20 -> Color(0xFF9F282B)
                            SharedColorsTokens.shade30 -> Color(0xFF751D1F)
                            SharedColorsTokens.shade40 -> Color(0xFF3F1011)
                            SharedColorsTokens.shade50 -> Color(0xFF210809)
                            SharedColorsTokens.tint10 -> Color(0xFFD7494C)
                            SharedColorsTokens.tint20 -> Color(0xFFDC5E62)
                            SharedColorsTokens.tint30 -> Color(0xFFE37D80)
                            SharedColorsTokens.tint40 -> Color(0xFFF1BBBC)
                            SharedColorsTokens.tint50 -> Color(0xFFF8DADB)
                            SharedColorsTokens.tint60 -> Color(0xFFFDF6F6)
                        }
                    }
                SharedColorSets.RoyalBlue ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF004E8C)
                            SharedColorsTokens.shade10 -> Color(0xFF00467E)
                            SharedColorsTokens.shade20 -> Color(0xFF003B6A)
                            SharedColorsTokens.shade30 -> Color(0xFF002C4E)
                            SharedColorsTokens.shade40 -> Color(0xFF00172A)
                            SharedColorsTokens.shade50 -> Color(0xFF000C16)
                            SharedColorsTokens.tint10 -> Color(0xFF125E9A)
                            SharedColorsTokens.tint20 -> Color(0xFF286FA8)
                            SharedColorsTokens.tint30 -> Color(0xFF4A89BA)
                            SharedColorsTokens.tint40 -> Color(0xFF9ABFDC)
                            SharedColorsTokens.tint50 -> Color(0xFFC7DCED)
                            SharedColorsTokens.tint60 -> Color(0xFFF0F6FA)
                        }
                    }
                SharedColorSets.Seafoam ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF00CC6A)
                            SharedColorsTokens.shade10 -> Color(0xFF00B85F)
                            SharedColorsTokens.shade20 -> Color(0xFF009B51)
                            SharedColorsTokens.shade30 -> Color(0xFF00723B)
                            SharedColorsTokens.shade40 -> Color(0xFF003D20)
                            SharedColorsTokens.shade50 -> Color(0xFF002111)
                            SharedColorsTokens.tint10 -> Color(0xFF19D279)
                            SharedColorsTokens.tint20 -> Color(0xFF34D889)
                            SharedColorsTokens.tint30 -> Color(0xFF5AE0A0)
                            SharedColorsTokens.tint40 -> Color(0xFFA8F0CD)
                            SharedColorsTokens.tint50 -> Color(0xFFCFF7E4)
                            SharedColorsTokens.tint60 -> Color(0xFFF3FDF8)
                        }
                    }
                SharedColorSets.Silver ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF859599)
                            SharedColorsTokens.shade10 -> Color(0xFF78868A)
                            SharedColorsTokens.shade20 -> Color(0xFF657174)
                            SharedColorsTokens.shade30 -> Color(0xFF4A5356)
                            SharedColorsTokens.shade40 -> Color(0xFF282D2E)
                            SharedColorsTokens.shade50 -> Color(0xFF151818)
                            SharedColorsTokens.tint10 -> Color(0xFF92A1A5)
                            SharedColorsTokens.tint20 -> Color(0xFFA0AEB1)
                            SharedColorsTokens.tint30 -> Color(0xFFB3BFC2)
                            SharedColorsTokens.tint40 -> Color(0xFFD8DFE0)
                            SharedColorsTokens.tint50 -> Color(0xFFEAEEEF)
                            SharedColorsTokens.tint60 -> Color(0xFFFAFBFB)
                        }
                    }
                SharedColorSets.Steel ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF005B70)
                            SharedColorsTokens.shade10 -> Color(0xFF005265)
                            SharedColorsTokens.shade20 -> Color(0xFF004555)
                            SharedColorsTokens.shade30 -> Color(0xFF00333F)
                            SharedColorsTokens.shade40 -> Color(0xFF001B22)
                            SharedColorsTokens.shade50 -> Color(0xFF000F12)
                            SharedColorsTokens.tint10 -> Color(0xFF0F6C81)
                            SharedColorsTokens.tint20 -> Color(0xFF237D92)
                            SharedColorsTokens.tint30 -> Color(0xFF4496A9)
                            SharedColorsTokens.tint40 -> Color(0xFF94C8D4)
                            SharedColorsTokens.tint50 -> Color(0xFFC3E1E8)
                            SharedColorsTokens.tint60 -> Color(0xFFEFF7F9)
                        }
                    }
                SharedColorSets.Teal ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFF038387)
                            SharedColorsTokens.shade10 -> Color(0xFF037679)
                            SharedColorsTokens.shade20 -> Color(0xFF026467)
                            SharedColorsTokens.shade30 -> Color(0xFF02494C)
                            SharedColorsTokens.shade40 -> Color(0xFF012728)
                            SharedColorsTokens.shade50 -> Color(0xFF001516)
                            SharedColorsTokens.tint10 -> Color(0xFF159195)
                            SharedColorsTokens.tint20 -> Color(0xFF2AA0A4)
                            SharedColorsTokens.tint30 -> Color(0xFF4CB4B7)
                            SharedColorsTokens.tint40 -> Color(0xFF9BD9DB)
                            SharedColorsTokens.tint50 -> Color(0xFFC7EBEC)
                            SharedColorsTokens.tint60 -> Color(0xFFF0FAFA)
                        }
                    }
                SharedColorSets.Yellow ->
                    TokenSet { token ->
                        when (token) {
                            SharedColorsTokens.primary -> Color(0xFFFDE300)
                            SharedColorsTokens.shade10 -> Color(0xFFE4CC00)
                            SharedColorsTokens.shade20 -> Color(0xFFC0AD00)
                            SharedColorsTokens.shade30 -> Color(0xFF8E7F00)
                            SharedColorsTokens.shade40 -> Color(0xFF4C4400)
                            SharedColorsTokens.shade50 -> Color(0xFF282400)
                            SharedColorsTokens.tint10 -> Color(0xFFFDE61E)
                            SharedColorsTokens.tint20 -> Color(0xFFFDEA3D)
                            SharedColorsTokens.tint30 -> Color(0xFFFEEE66)
                            SharedColorsTokens.tint40 -> Color(0xFFFEF7B2)
                            SharedColorsTokens.tint50 -> Color(0xFFFFFAD6)
                            SharedColorsTokens.tint60 -> Color(0xFFFFFEF5)
                        }
                    }

            }
        }
    }
}


internal val LocalGlobalTokens = compositionLocalOf { GlobalTokens() }