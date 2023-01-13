//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.parcelize.Parcelize

// Global Tokens represent a unified set of constants to be used by Fluent UI.

@Parcelize
object GlobalTokens : Parcelable {

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

    fun neutralColor(token: NeutralColorTokens): Color {
        return when (token) {
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

    fun fontSize(token: FontSizeTokens): FontSize {
        return when (token) {
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

    enum class FontWeightTokens {
        Light,
        SemiLight,
        Regular,
        Medium,
        SemiBold,
        Bold
    }

    fun fontWeight(token: FontWeightTokens): FontWeight {
        return when (token) {
            FontWeightTokens.Light -> FontWeight(0)
            FontWeightTokens.SemiLight -> FontWeight(0)
            FontWeightTokens.Regular -> FontWeight(400)
            FontWeightTokens.Medium -> FontWeight(500)
            FontWeightTokens.SemiBold -> FontWeight(600)
            FontWeightTokens.Bold -> FontWeight(700)
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

    fun iconSize(token: IconSizeTokens): IconSize {
        return when (token) {
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

    fun spacing(token: SpacingTokens): Dp {
        return when (token) {
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

    enum class SizeTokens {
        SizeNone,
        Size20,
        Size40,
        Size60,
        Size80,
        Size100,
        Size120,
        Size140,
        Size160,
        Size180,
        Size200,
        Size240,
        Size280,
        Size320,
        Size360,
        Size400,
        Size480,
        Size520,
        Size560
    }

    fun size(token: SizeTokens): Dp {
        return when (token) {
            SizeTokens.SizeNone -> 0.dp
            SizeTokens.Size20 -> 2.dp
            SizeTokens.Size40 -> 4.dp
            SizeTokens.Size60 -> 6.dp
            SizeTokens.Size80 -> 8.dp
            SizeTokens.Size100 -> 10.dp
            SizeTokens.Size120 -> 12.dp
            SizeTokens.Size140 -> 14.dp
            SizeTokens.Size160 -> 16.dp
            SizeTokens.Size180 -> 18.dp
            SizeTokens.Size200 -> 20.dp
            SizeTokens.Size240 -> 24.dp
            SizeTokens.Size280 -> 28.dp
            SizeTokens.Size320 -> 32.dp
            SizeTokens.Size360 -> 36.dp
            SizeTokens.Size400 -> 40.dp
            SizeTokens.Size480 -> 48.dp
            SizeTokens.Size520 -> 52.dp
            SizeTokens.Size560 -> 56.dp
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

    fun elevation(token: ShadowTokens): Dp {
        return when (token) {
            ShadowTokens.Shadow02 -> 2.dp
            ShadowTokens.Shadow04 -> 4.dp
            ShadowTokens.Shadow08 -> 8.dp
            ShadowTokens.Shadow16 -> 16.dp
            ShadowTokens.Shadow28 -> 28.dp
            ShadowTokens.Shadow64 -> 40.dp
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

    fun opacity(token: OpacityTokens): Float {
        return when (token) {
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

    enum class CornerRadiusTokens {
        CornerRadiusNone,
        CornerRadius20,
        CornerRadius40,
        CornerRadius80,
        CornerRadius120,
        CornerRadiusCircle
    }

    fun borderRadius(token: CornerRadiusTokens): Dp {
        return when (token) {
            CornerRadiusTokens.CornerRadiusNone ->
                0.dp
            CornerRadiusTokens.CornerRadius20 ->
                2.dp
            CornerRadiusTokens.CornerRadius40 ->
                4.dp
            CornerRadiusTokens.CornerRadius80 ->
                8.dp
            CornerRadiusTokens.CornerRadius120 ->
                12.dp
            CornerRadiusTokens.CornerRadiusCircle ->
                9999.dp
        }
    }

    enum class StrokeWidthTokens {
        StrokeWidthNone,
        StrokeWidth05,
        StrokeWidth10,
        StrokeWidth15,
        StrokeWidth20,
        StrokeWidth40,
        StrokeWidth60
    }

    fun strokeWidth(token: StrokeWidthTokens): Dp {
        return when (token) {
            StrokeWidthTokens.StrokeWidthNone ->
                0.dp
            StrokeWidthTokens.StrokeWidth05 ->
                0.5.dp
            StrokeWidthTokens.StrokeWidth10 ->
                1.dp
            StrokeWidthTokens.StrokeWidth15 ->
                1.5.dp
            StrokeWidthTokens.StrokeWidth20 ->
                2.dp
            StrokeWidthTokens.StrokeWidth40 ->
                4.dp
            StrokeWidthTokens.StrokeWidth60 ->
                6.dp
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
        Shade50,
        Shade40,
        Shade30,
        Shade20,
        Shade10,
        Primary,
        Tint10,
        Tint20,
        Tint30,
        Tint40,
        Tint50,
        Tint60
    }

    fun sharedColors(sharedColor: SharedColorSets, token: SharedColorsTokens): Color {
        return when (sharedColor) {
            SharedColorSets.Anchor ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF394146)
                    SharedColorsTokens.Shade10 -> Color(0xFF333A3F)
                    SharedColorsTokens.Shade20 -> Color(0xFF2B3135)
                    SharedColorsTokens.Shade30 -> Color(0xFF202427)
                    SharedColorsTokens.Shade40 -> Color(0xFF111315)
                    SharedColorsTokens.Shade50 -> Color(0xFF090A0B)
                    SharedColorsTokens.Tint10 -> Color(0xFF4D565C)
                    SharedColorsTokens.Tint20 -> Color(0xFF626C72)
                    SharedColorsTokens.Tint30 -> Color(0xFF808A90)
                    SharedColorsTokens.Tint40 -> Color(0xFFBCC3C7)
                    SharedColorsTokens.Tint50 -> Color(0xFFDBDFE1)
                    SharedColorsTokens.Tint60 -> Color(0xFFF6F7F8)

                }
            SharedColorSets.Beige ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF7A7574)
                    SharedColorsTokens.Shade10 -> Color(0xFF6E6968)
                    SharedColorsTokens.Shade20 -> Color(0xFF5D5958)
                    SharedColorsTokens.Shade30 -> Color(0xFF444241)
                    SharedColorsTokens.Shade40 -> Color(0xFF252323)
                    SharedColorsTokens.Shade50 -> Color(0xFF141313)
                    SharedColorsTokens.Tint10 -> Color(0xFF8A8584)
                    SharedColorsTokens.Tint20 -> Color(0xFF9A9594)
                    SharedColorsTokens.Tint30 -> Color(0xFFAFABAA)
                    SharedColorsTokens.Tint40 -> Color(0xFFD7D4D4)
                    SharedColorsTokens.Tint50 -> Color(0xFFEAE8E8)
                    SharedColorsTokens.Tint60 -> Color(0xFFFAF9F9)
                }

            SharedColorSets.Berry ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFC239B3)
                    SharedColorsTokens.Shade10 -> Color(0xFFAF33A1)
                    SharedColorsTokens.Shade20 -> Color(0xFF932B88)
                    SharedColorsTokens.Shade30 -> Color(0xFF6D2064)
                    SharedColorsTokens.Shade40 -> Color(0xFF3A1136)
                    SharedColorsTokens.Shade50 -> Color(0xFF1F091D)
                    SharedColorsTokens.Tint10 -> Color(0xFFC94CBC)
                    SharedColorsTokens.Tint20 -> Color(0xFFD161C4)
                    SharedColorsTokens.Tint30 -> Color(0xFFDA7ED0)
                    SharedColorsTokens.Tint40 -> Color(0xFFEDBBE7)
                    SharedColorsTokens.Tint50 -> Color(0xFFF5DAF2)
                    SharedColorsTokens.Tint60 -> Color(0xFFFDF5FC)
                }

            SharedColorSets.Blue ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF0078D4)
                    SharedColorsTokens.Shade10 -> Color(0xFF006CBF)
                    SharedColorsTokens.Shade20 -> Color(0xFF005BA1)
                    SharedColorsTokens.Shade30 -> Color(0xFF004377)
                    SharedColorsTokens.Shade40 -> Color(0xFF002440)
                    SharedColorsTokens.Shade50 -> Color(0xFF001322)
                    SharedColorsTokens.Tint10 -> Color(0xFF1A86D9)
                    SharedColorsTokens.Tint20 -> Color(0xFF3595DE)
                    SharedColorsTokens.Tint30 -> Color(0xFF5CAAE5)
                    SharedColorsTokens.Tint40 -> Color(0xFFA9D3F2)
                    SharedColorsTokens.Tint50 -> Color(0xFFD0E7F8)
                    SharedColorsTokens.Tint60 -> Color(0xFFF3F9FD)
                }

            SharedColorSets.Brass ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF986F0B)
                    SharedColorsTokens.Shade10 -> Color(0xFF89640A)
                    SharedColorsTokens.Shade20 -> Color(0xFF745408)
                    SharedColorsTokens.Shade30 -> Color(0xFF553E06)
                    SharedColorsTokens.Shade40 -> Color(0xFF2E2103)
                    SharedColorsTokens.Shade50 -> Color(0xFF181202)
                    SharedColorsTokens.Tint10 -> Color(0xFFA47D1E)
                    SharedColorsTokens.Tint20 -> Color(0xFFB18C34)
                    SharedColorsTokens.Tint30 -> Color(0xFFC1A256)
                    SharedColorsTokens.Tint40 -> Color(0xFFE0CEA2)
                    SharedColorsTokens.Tint50 -> Color(0xFFEFE4CB)
                    SharedColorsTokens.Tint60 -> Color(0xFFFBF8F2)
                }

            SharedColorSets.Bronze ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFA74109)
                    SharedColorsTokens.Shade10 -> Color(0xFF963A08)
                    SharedColorsTokens.Shade20 -> Color(0xFF7F3107)
                    SharedColorsTokens.Shade30 -> Color(0xFF5E2405)
                    SharedColorsTokens.Shade40 -> Color(0xFF321303)
                    SharedColorsTokens.Shade50 -> Color(0xFF1B0A01)
                    SharedColorsTokens.Tint10 -> Color(0xFFB2521E)
                    SharedColorsTokens.Tint20 -> Color(0xFFBC6535)
                    SharedColorsTokens.Tint30 -> Color(0xFFCA8057)
                    SharedColorsTokens.Tint40 -> Color(0xFFE5BBA4)
                    SharedColorsTokens.Tint50 -> Color(0xFFF1D9CC)
                    SharedColorsTokens.Tint60 -> Color(0xFFFBF5F2)
                }

            SharedColorSets.Brown ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF8E562E)
                    SharedColorsTokens.Shade10 -> Color(0xFF804D29)
                    SharedColorsTokens.Shade20 -> Color(0xFF6C4123)
                    SharedColorsTokens.Shade30 -> Color(0xFF50301A)
                    SharedColorsTokens.Shade40 -> Color(0xFF2B1A0E)
                    SharedColorsTokens.Shade50 -> Color(0xFF170E07)
                    SharedColorsTokens.Tint10 -> Color(0xFF9C663F)
                    SharedColorsTokens.Tint20 -> Color(0xFFA97652)
                    SharedColorsTokens.Tint30 -> Color(0xFFBB8F6F)
                    SharedColorsTokens.Tint40 -> Color(0xFFDDC3B0)
                    SharedColorsTokens.Tint50 -> Color(0xFFEDDED3)
                    SharedColorsTokens.Tint60 -> Color(0xFFFAF7F4)
                }

            SharedColorSets.Burgundy ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFA4262C)
                    SharedColorsTokens.Shade10 -> Color(0xFF942228)
                    SharedColorsTokens.Shade20 -> Color(0xFF7D1D21)
                    SharedColorsTokens.Shade30 -> Color(0xFF5C1519)
                    SharedColorsTokens.Shade40 -> Color(0xFF310B0D)
                    SharedColorsTokens.Shade50 -> Color(0xFF1A0607)
                    SharedColorsTokens.Tint10 -> Color(0xFFAF393E)
                    SharedColorsTokens.Tint20 -> Color(0xFFBA4D52)
                    SharedColorsTokens.Tint30 -> Color(0xFFC86C70)
                    SharedColorsTokens.Tint40 -> Color(0xFFE4AFB2)
                    SharedColorsTokens.Tint50 -> Color(0xFFF0D3D4)
                    SharedColorsTokens.Tint60 -> Color(0xFFFBF4F4)
                }

            SharedColorSets.Charcoal ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF393939)
                    SharedColorsTokens.Shade10 -> Color(0xFF333333)
                    SharedColorsTokens.Shade20 -> Color(0xFF2B2B2B)
                    SharedColorsTokens.Shade30 -> Color(0xFF202020)
                    SharedColorsTokens.Shade40 -> Color(0xFF111111)
                    SharedColorsTokens.Shade50 -> Color(0xFF090909)
                    SharedColorsTokens.Tint10 -> Color(0xFF515151)
                    SharedColorsTokens.Tint20 -> Color(0xFF686868)
                    SharedColorsTokens.Tint30 -> Color(0xFF888888)
                    SharedColorsTokens.Tint40 -> Color(0xFFC4C4C4)
                    SharedColorsTokens.Tint50 -> Color(0xFFDFDFDF)
                    SharedColorsTokens.Tint60 -> Color(0xFFF7F7F7)
                }

            SharedColorSets.Cornflower ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF4F6BED)
                    SharedColorsTokens.Shade10 -> Color(0xFF4760D5)
                    SharedColorsTokens.Shade20 -> Color(0xFF3C51B4)
                    SharedColorsTokens.Shade30 -> Color(0xFF2C3C85)
                    SharedColorsTokens.Shade40 -> Color(0xFF182047)
                    SharedColorsTokens.Shade50 -> Color(0xFF0D1126)
                    SharedColorsTokens.Tint10 -> Color(0xFF637CEF)
                    SharedColorsTokens.Tint20 -> Color(0xFF778DF1)
                    SharedColorsTokens.Tint30 -> Color(0xFF93A4F4)
                    SharedColorsTokens.Tint40 -> Color(0xFFC8D1FA)
                    SharedColorsTokens.Tint50 -> Color(0xFFE1E6FC)
                    SharedColorsTokens.Tint60 -> Color(0xFFF7F9FE)
                }

            SharedColorSets.Cranberry ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFC50F1F)
                    SharedColorsTokens.Shade10 -> Color(0xFFB10E1C)
                    SharedColorsTokens.Shade20 -> Color(0xFF960B18)
                    SharedColorsTokens.Shade30 -> Color(0xFF6E0811)
                    SharedColorsTokens.Shade40 -> Color(0xFF3B0509)
                    SharedColorsTokens.Shade50 -> Color(0xFF200205)
                    SharedColorsTokens.Tint10 -> Color(0xFFCC2635)
                    SharedColorsTokens.Tint20 -> Color(0xFFD33F4C)
                    SharedColorsTokens.Tint30 -> Color(0xFFDC626D)
                    SharedColorsTokens.Tint40 -> Color(0xFFEEACB2)
                    SharedColorsTokens.Tint50 -> Color(0xFFF6D1D5)
                    SharedColorsTokens.Tint60 -> Color(0xFFFDF3F4)
                }

            SharedColorSets.Cyan ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF0099BC)
                    SharedColorsTokens.Shade10 -> Color(0xFF008AA9)
                    SharedColorsTokens.Shade20 -> Color(0xFF00748F)
                    SharedColorsTokens.Shade30 -> Color(0xFF005669)
                    SharedColorsTokens.Shade40 -> Color(0xFF002E38)
                    SharedColorsTokens.Shade50 -> Color(0xFF00181E)
                    SharedColorsTokens.Tint10 -> Color(0xFF18A4C4)
                    SharedColorsTokens.Tint20 -> Color(0xFF31AFCC)
                    SharedColorsTokens.Tint30 -> Color(0xFF56BFD7)
                    SharedColorsTokens.Tint40 -> Color(0xFFA4DEEB)
                    SharedColorsTokens.Tint50 -> Color(0xFFCDEDF4)
                    SharedColorsTokens.Tint60 -> Color(0xFFF2FAFC)
                }

            SharedColorSets.DarkBlue ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF003966)
                    SharedColorsTokens.Shade10 -> Color(0xFF00335C)
                    SharedColorsTokens.Shade20 -> Color(0xFF002B4E)
                    SharedColorsTokens.Shade30 -> Color(0xFF002039)
                    SharedColorsTokens.Shade40 -> Color(0xFF00111F)
                    SharedColorsTokens.Shade50 -> Color(0xFF000910)
                    SharedColorsTokens.Tint10 -> Color(0xFF0E4A78)
                    SharedColorsTokens.Tint20 -> Color(0xFF215C8B)
                    SharedColorsTokens.Tint30 -> Color(0xFF4178A3)
                    SharedColorsTokens.Tint40 -> Color(0xFF92B5D1)
                    SharedColorsTokens.Tint50 -> Color(0xFFC2D6E7)
                    SharedColorsTokens.Tint60 -> Color(0xFFEFF4F9)
                }

            SharedColorSets.DarkBrown ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF4D291C)
                    SharedColorsTokens.Shade10 -> Color(0xFF452519)
                    SharedColorsTokens.Shade20 -> Color(0xFF3A1F15)
                    SharedColorsTokens.Shade30 -> Color(0xFF2B1710)
                    SharedColorsTokens.Shade40 -> Color(0xFF170C08)
                    SharedColorsTokens.Shade50 -> Color(0xFF0C0704)
                    SharedColorsTokens.Tint10 -> Color(0xFF623A2B)
                    SharedColorsTokens.Tint20 -> Color(0xFF784D3E)
                    SharedColorsTokens.Tint30 -> Color(0xFF946B5C)
                    SharedColorsTokens.Tint40 -> Color(0xFFCAADA3)
                    SharedColorsTokens.Tint50 -> Color(0xFFE3D2CB)
                    SharedColorsTokens.Tint60 -> Color(0xFFF8F3F2)
                }

            SharedColorSets.DarkGreen ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF0B6A0B)
                    SharedColorsTokens.Shade10 -> Color(0xFF0A5F0A)
                    SharedColorsTokens.Shade20 -> Color(0xFF085108)
                    SharedColorsTokens.Shade30 -> Color(0xFF063B06)
                    SharedColorsTokens.Shade40 -> Color(0xFF032003)
                    SharedColorsTokens.Shade50 -> Color(0xFF021102)
                    SharedColorsTokens.Tint10 -> Color(0xFF1A7C1A)
                    SharedColorsTokens.Tint20 -> Color(0xFF2D8E2D)
                    SharedColorsTokens.Tint30 -> Color(0xFF4DA64D)
                    SharedColorsTokens.Tint40 -> Color(0xFF9AD29A)
                    SharedColorsTokens.Tint50 -> Color(0xFFC6E7C6)
                    SharedColorsTokens.Tint60 -> Color(0xFFF0F9F0)
                }

            SharedColorSets.DarkOrange ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFDA3B01)
                    SharedColorsTokens.Shade10 -> Color(0xFFC43501)
                    SharedColorsTokens.Shade20 -> Color(0xFFA62D01)
                    SharedColorsTokens.Shade30 -> Color(0xFF7A2101)
                    SharedColorsTokens.Shade40 -> Color(0xFF411200)
                    SharedColorsTokens.Shade50 -> Color(0xFF230900)
                    SharedColorsTokens.Tint10 -> Color(0xFFDE501C)
                    SharedColorsTokens.Tint20 -> Color(0xFFE36537)
                    SharedColorsTokens.Tint30 -> Color(0xFFE9835E)
                    SharedColorsTokens.Tint40 -> Color(0xFFF4BFAB)
                    SharedColorsTokens.Tint50 -> Color(0xFFF9DCD1)
                    SharedColorsTokens.Tint60 -> Color(0xFFFDF6F3)
                }

            SharedColorSets.DarkPurple ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF401B6C)
                    SharedColorsTokens.Shade10 -> Color(0xFF3A1861)
                    SharedColorsTokens.Shade20 -> Color(0xFF311552)
                    SharedColorsTokens.Shade30 -> Color(0xFF240F3C)
                    SharedColorsTokens.Shade40 -> Color(0xFF130820)
                    SharedColorsTokens.Shade50 -> Color(0xFF0A0411)
                    SharedColorsTokens.Tint10 -> Color(0xFF512B7E)
                    SharedColorsTokens.Tint20 -> Color(0xFF633E8F)
                    SharedColorsTokens.Tint30 -> Color(0xFF7E5CA7)
                    SharedColorsTokens.Tint40 -> Color(0xFFB9A3D3)
                    SharedColorsTokens.Tint50 -> Color(0xFFD8CCE7)
                    SharedColorsTokens.Tint60 -> Color(0xFFF5F2F9)
                }

            SharedColorSets.DarkRed ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF750B1C)
                    SharedColorsTokens.Shade10 -> Color(0xFF690A19)
                    SharedColorsTokens.Shade20 -> Color(0xFF590815)
                    SharedColorsTokens.Shade30 -> Color(0xFF420610)
                    SharedColorsTokens.Shade40 -> Color(0xFF230308)
                    SharedColorsTokens.Shade50 -> Color(0xFF130204)
                    SharedColorsTokens.Tint10 -> Color(0xFF861B2C)
                    SharedColorsTokens.Tint20 -> Color(0xFF962F3F)
                    SharedColorsTokens.Tint30 -> Color(0xFFAC4F5E)
                    SharedColorsTokens.Tint40 -> Color(0xFFD69CA5)
                    SharedColorsTokens.Tint50 -> Color(0xFFE9C7CD)
                    SharedColorsTokens.Tint60 -> Color(0xFFF9F0F2)
                }

            SharedColorSets.DarkTeal ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF006666)
                    SharedColorsTokens.Shade10 -> Color(0xFF005C5C)
                    SharedColorsTokens.Shade20 -> Color(0xFF004E4E)
                    SharedColorsTokens.Shade30 -> Color(0xFF003939)
                    SharedColorsTokens.Shade40 -> Color(0xFF001F1F)
                    SharedColorsTokens.Shade50 -> Color(0xFF001010)
                    SharedColorsTokens.Tint10 -> Color(0xFF0E7878)
                    SharedColorsTokens.Tint20 -> Color(0xFF218B8B)
                    SharedColorsTokens.Tint30 -> Color(0xFF41A3A3)
                    SharedColorsTokens.Tint40 -> Color(0xFF92D1D1)
                    SharedColorsTokens.Tint50 -> Color(0xFFC2E7E7)
                    SharedColorsTokens.Tint60 -> Color(0xFFEFF9F9)
                }

            SharedColorSets.Forest ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF498205)
                    SharedColorsTokens.Shade10 -> Color(0xFF427505)
                    SharedColorsTokens.Shade20 -> Color(0xFF376304)
                    SharedColorsTokens.Shade30 -> Color(0xFF294903)
                    SharedColorsTokens.Shade40 -> Color(0xFF162702)
                    SharedColorsTokens.Shade50 -> Color(0xFF0C1501)
                    SharedColorsTokens.Tint10 -> Color(0xFF599116)
                    SharedColorsTokens.Tint20 -> Color(0xFF6BA02B)
                    SharedColorsTokens.Tint30 -> Color(0xFF85B44C)
                    SharedColorsTokens.Tint40 -> Color(0xFFBDD99B)
                    SharedColorsTokens.Tint50 -> Color(0xFFDBEBC7)
                    SharedColorsTokens.Tint60 -> Color(0xFFF6FAF0)
                }

            SharedColorSets.Gold ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFC19C00)
                    SharedColorsTokens.Shade10 -> Color(0xFFAE8C00)
                    SharedColorsTokens.Shade20 -> Color(0xFF937700)
                    SharedColorsTokens.Shade30 -> Color(0xFF6C5700)
                    SharedColorsTokens.Shade40 -> Color(0xFF3A2F00)
                    SharedColorsTokens.Shade50 -> Color(0xFF1F1900)
                    SharedColorsTokens.Tint10 -> Color(0xFFC8A718)
                    SharedColorsTokens.Tint20 -> Color(0xFFD0B232)
                    SharedColorsTokens.Tint30 -> Color(0xFFDAC157)
                    SharedColorsTokens.Tint40 -> Color(0xFFECDFA5)
                    SharedColorsTokens.Tint50 -> Color(0xFFF5EECE)
                    SharedColorsTokens.Tint60 -> Color(0xFFFDFBF2)
                }

            SharedColorSets.Grape ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF881798)
                    SharedColorsTokens.Shade10 -> Color(0xFF7A1589)
                    SharedColorsTokens.Shade20 -> Color(0xFF671174)
                    SharedColorsTokens.Shade30 -> Color(0xFF4C0D55)
                    SharedColorsTokens.Shade40 -> Color(0xFF29072E)
                    SharedColorsTokens.Shade50 -> Color(0xFF160418)
                    SharedColorsTokens.Tint10 -> Color(0xFF952AA4)
                    SharedColorsTokens.Tint20 -> Color(0xFFA33FB1)
                    SharedColorsTokens.Tint30 -> Color(0xFFB55FC1)
                    SharedColorsTokens.Tint40 -> Color(0xFFD9A7E0)
                    SharedColorsTokens.Tint50 -> Color(0xFFEACEEF)
                    SharedColorsTokens.Tint60 -> Color(0xFFFAF2FB)
                }

            SharedColorSets.Green ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF107C10)
                    SharedColorsTokens.Shade10 -> Color(0xFF0E700E)
                    SharedColorsTokens.Shade20 -> Color(0xFF0C5E0C)
                    SharedColorsTokens.Shade30 -> Color(0xFF094509)
                    SharedColorsTokens.Shade40 -> Color(0xFF052505)
                    SharedColorsTokens.Shade50 -> Color(0xFF031403)
                    SharedColorsTokens.Tint10 -> Color(0xFF218C21)
                    SharedColorsTokens.Tint20 -> Color(0xFF359B35)
                    SharedColorsTokens.Tint30 -> Color(0xFF54B054)
                    SharedColorsTokens.Tint40 -> Color(0xFF9FD89F)
                    SharedColorsTokens.Tint50 -> Color(0xFFC9EAC9)
                    SharedColorsTokens.Tint60 -> Color(0xFFF1FAF1)
                }

            SharedColorSets.HotPink ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFE3008C)
                    SharedColorsTokens.Shade10 -> Color(0xFFCC007E)
                    SharedColorsTokens.Shade20 -> Color(0xFFAD006A)
                    SharedColorsTokens.Shade30 -> Color(0xFF7F004E)
                    SharedColorsTokens.Shade40 -> Color(0xFF44002A)
                    SharedColorsTokens.Shade50 -> Color(0xFF240016)
                    SharedColorsTokens.Tint10 -> Color(0xFFE61C99)
                    SharedColorsTokens.Tint20 -> Color(0xFFEA38A6)
                    SharedColorsTokens.Tint30 -> Color(0xFFEE5FB7)
                    SharedColorsTokens.Tint40 -> Color(0xFFF7ADDA)
                    SharedColorsTokens.Tint50 -> Color(0xFFFBD2EB)
                    SharedColorsTokens.Tint60 -> Color(0xFFFEF4FA)
                }

            SharedColorSets.Lavender ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF7160E8)
                    SharedColorsTokens.Shade10 -> Color(0xFF6656D1)
                    SharedColorsTokens.Shade20 -> Color(0xFF5649B0)
                    SharedColorsTokens.Shade30 -> Color(0xFF3F3682)
                    SharedColorsTokens.Shade40 -> Color(0xFF221D46)
                    SharedColorsTokens.Shade50 -> Color(0xFF120F25)
                    SharedColorsTokens.Tint10 -> Color(0xFF8172EB)
                    SharedColorsTokens.Tint20 -> Color(0xFF9184EE)
                    SharedColorsTokens.Tint30 -> Color(0xFFA79CF1)
                    SharedColorsTokens.Tint40 -> Color(0xFFD2CCF8)
                    SharedColorsTokens.Tint50 -> Color(0xFFE7E4FB)
                    SharedColorsTokens.Tint60 -> Color(0xFFF9F8FE)
                }

            SharedColorSets.LightBlue ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF3A96DD)
                    SharedColorsTokens.Shade10 -> Color(0xFF3487C7)
                    SharedColorsTokens.Shade20 -> Color(0xFF2C72A8)
                    SharedColorsTokens.Shade30 -> Color(0xFF20547C)
                    SharedColorsTokens.Shade40 -> Color(0xFF112D42)
                    SharedColorsTokens.Shade50 -> Color(0xFF091823)
                    SharedColorsTokens.Tint10 -> Color(0xFF4FA1E1)
                    SharedColorsTokens.Tint20 -> Color(0xFF65ADE5)
                    SharedColorsTokens.Tint30 -> Color(0xFF83BDEB)
                    SharedColorsTokens.Tint40 -> Color(0xFFBFDDF5)
                    SharedColorsTokens.Tint50 -> Color(0xFFDCEDFA)
                    SharedColorsTokens.Tint60 -> Color(0xFFF6FAFE)
                }

            SharedColorSets.LightGreen ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF13A10E)
                    SharedColorsTokens.Shade10 -> Color(0xFF11910D)
                    SharedColorsTokens.Shade20 -> Color(0xFF0E7A0B)
                    SharedColorsTokens.Shade30 -> Color(0xFF0B5A08)
                    SharedColorsTokens.Shade40 -> Color(0xFF063004)
                    SharedColorsTokens.Shade50 -> Color(0xFF031A02)
                    SharedColorsTokens.Tint10 -> Color(0xFF27AC22)
                    SharedColorsTokens.Tint20 -> Color(0xFF3DB838)
                    SharedColorsTokens.Tint30 -> Color(0xFF5EC75A)
                    SharedColorsTokens.Tint40 -> Color(0xFFA7E3A5)
                    SharedColorsTokens.Tint50 -> Color(0xFFCEF0CD)
                    SharedColorsTokens.Tint60 -> Color(0xFFF2FBF2)
                }

            SharedColorSets.LightTeal ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF00B7C3)
                    SharedColorsTokens.Shade10 -> Color(0xFF00A5AF)
                    SharedColorsTokens.Shade20 -> Color(0xFF008B94)
                    SharedColorsTokens.Shade30 -> Color(0xFF00666D)
                    SharedColorsTokens.Shade40 -> Color(0xFF00373A)
                    SharedColorsTokens.Shade50 -> Color(0xFF001D1F)
                    SharedColorsTokens.Tint10 -> Color(0xFF18BFCA)
                    SharedColorsTokens.Tint20 -> Color(0xFF32C8D1)
                    SharedColorsTokens.Tint30 -> Color(0xFF58D3DB)
                    SharedColorsTokens.Tint40 -> Color(0xFFA6E9ED)
                    SharedColorsTokens.Tint50 -> Color(0xFFCEF3F5)
                    SharedColorsTokens.Tint60 -> Color(0xFFF2FCFD)
                }

            SharedColorSets.Lilac ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFB146C2)
                    SharedColorsTokens.Shade10 -> Color(0xFF9F3FAF)
                    SharedColorsTokens.Shade20 -> Color(0xFF863593)
                    SharedColorsTokens.Shade30 -> Color(0xFF63276D)
                    SharedColorsTokens.Shade40 -> Color(0xFF35153A)
                    SharedColorsTokens.Shade50 -> Color(0xFF1C0B1F)
                    SharedColorsTokens.Tint10 -> Color(0xFFBA58C9)
                    SharedColorsTokens.Tint20 -> Color(0xFFC36BD1)
                    SharedColorsTokens.Tint30 -> Color(0xFFCF87DA)
                    SharedColorsTokens.Tint40 -> Color(0xFFE6BFED)
                    SharedColorsTokens.Tint50 -> Color(0xFFF2DCF5)
                    SharedColorsTokens.Tint60 -> Color(0xFFFCF6FD)
                }

            SharedColorSets.Lime ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF73AA24)
                    SharedColorsTokens.Shade10 -> Color(0xFF689920)
                    SharedColorsTokens.Shade20 -> Color(0xFF57811B)
                    SharedColorsTokens.Shade30 -> Color(0xFF405F14)
                    SharedColorsTokens.Shade40 -> Color(0xFF23330B)
                    SharedColorsTokens.Shade50 -> Color(0xFF121B06)
                    SharedColorsTokens.Tint10 -> Color(0xFF81B437)
                    SharedColorsTokens.Tint20 -> Color(0xFF90BE4C)
                    SharedColorsTokens.Tint30 -> Color(0xFFA4CC6C)
                    SharedColorsTokens.Tint40 -> Color(0xFFCFE5AF)
                    SharedColorsTokens.Tint50 -> Color(0xFFE5F1D3)
                    SharedColorsTokens.Tint60 -> Color(0xFFF8FCF4)
                }

            SharedColorSets.Magenta ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFBF0077)
                    SharedColorsTokens.Shade10 -> Color(0xFFAC006B)
                    SharedColorsTokens.Shade20 -> Color(0xFF91005A)
                    SharedColorsTokens.Shade30 -> Color(0xFF6B0043)
                    SharedColorsTokens.Shade40 -> Color(0xFF390024)
                    SharedColorsTokens.Shade50 -> Color(0xFF1F0013)
                    SharedColorsTokens.Tint10 -> Color(0xFFC71885)
                    SharedColorsTokens.Tint20 -> Color(0xFFCE3293)
                    SharedColorsTokens.Tint30 -> Color(0xFFD957A8)
                    SharedColorsTokens.Tint40 -> Color(0xFFECA5D1)
                    SharedColorsTokens.Tint50 -> Color(0xFFF5CEE6)
                    SharedColorsTokens.Tint60 -> Color(0xFFFCF2F9)
                }

            SharedColorSets.Marigold ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFEAA300)
                    SharedColorsTokens.Shade10 -> Color(0xFFD39300)
                    SharedColorsTokens.Shade20 -> Color(0xFFB27C00)
                    SharedColorsTokens.Shade30 -> Color(0xFF835B00)
                    SharedColorsTokens.Shade40 -> Color(0xFF463100)
                    SharedColorsTokens.Shade50 -> Color(0xFF251A00)
                    SharedColorsTokens.Tint10 -> Color(0xFFEDAD1C)
                    SharedColorsTokens.Tint20 -> Color(0xFFEFB839)
                    SharedColorsTokens.Tint30 -> Color(0xFFF2C661)
                    SharedColorsTokens.Tint40 -> Color(0xFFF9E2AE)
                    SharedColorsTokens.Tint50 -> Color(0xFFFCEFD3)
                    SharedColorsTokens.Tint60 -> Color(0xFFFEFBF4)
                }

            SharedColorSets.Mink ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF5D5A58)
                    SharedColorsTokens.Shade10 -> Color(0xFF54514F)
                    SharedColorsTokens.Shade20 -> Color(0xFF474443)
                    SharedColorsTokens.Shade30 -> Color(0xFF343231)
                    SharedColorsTokens.Shade40 -> Color(0xFF1C1B1A)
                    SharedColorsTokens.Shade50 -> Color(0xFF0F0E0E)
                    SharedColorsTokens.Tint10 -> Color(0xFF706D6B)
                    SharedColorsTokens.Tint20 -> Color(0xFF84817E)
                    SharedColorsTokens.Tint30 -> Color(0xFF9E9B99)
                    SharedColorsTokens.Tint40 -> Color(0xFFCECCCB)
                    SharedColorsTokens.Tint50 -> Color(0xFFE5E4E3)
                    SharedColorsTokens.Tint60 -> Color(0xFFF8F8F8)
                }

            SharedColorSets.Navy ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF0027B4)
                    SharedColorsTokens.Shade10 -> Color(0xFF0023A2)
                    SharedColorsTokens.Shade20 -> Color(0xFF001E89)
                    SharedColorsTokens.Shade30 -> Color(0xFF001665)
                    SharedColorsTokens.Shade40 -> Color(0xFF000C36)
                    SharedColorsTokens.Shade50 -> Color(0xFF00061D)
                    SharedColorsTokens.Tint10 -> Color(0xFF173BBD)
                    SharedColorsTokens.Tint20 -> Color(0xFF3050C6)
                    SharedColorsTokens.Tint30 -> Color(0xFF546FD2)
                    SharedColorsTokens.Tint40 -> Color(0xFFA3B2E8)
                    SharedColorsTokens.Tint50 -> Color(0xFFCCD5F3)
                    SharedColorsTokens.Tint60 -> Color(0xFFF2F4FC)
                }

            SharedColorSets.Orange ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFF7630C)
                    SharedColorsTokens.Shade10 -> Color(0xFFDE590B)
                    SharedColorsTokens.Shade20 -> Color(0xFFBC4B09)
                    SharedColorsTokens.Shade30 -> Color(0xFF8A3707)
                    SharedColorsTokens.Shade40 -> Color(0xFF4A1E04)
                    SharedColorsTokens.Shade50 -> Color(0xFF271002)
                    SharedColorsTokens.Tint10 -> Color(0xFFF87528)
                    SharedColorsTokens.Tint20 -> Color(0xFFF98845)
                    SharedColorsTokens.Tint30 -> Color(0xFFFAA06B)
                    SharedColorsTokens.Tint40 -> Color(0xFFFDCFB4)
                    SharedColorsTokens.Tint50 -> Color(0xFFFEE5D7)
                    SharedColorsTokens.Tint60 -> Color(0xFFFFF9F5)
                }

            SharedColorSets.Orchid ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF8764B8)
                    SharedColorsTokens.Shade10 -> Color(0xFF795AA6)
                    SharedColorsTokens.Shade20 -> Color(0xFF674C8C)
                    SharedColorsTokens.Shade30 -> Color(0xFF4C3867)
                    SharedColorsTokens.Shade40 -> Color(0xFF281E37)
                    SharedColorsTokens.Shade50 -> Color(0xFF16101D)
                    SharedColorsTokens.Tint10 -> Color(0xFF9373C0)
                    SharedColorsTokens.Tint20 -> Color(0xFFA083C9)
                    SharedColorsTokens.Tint30 -> Color(0xFFB29AD4)
                    SharedColorsTokens.Tint40 -> Color(0xFFD7CAEA)
                    SharedColorsTokens.Tint50 -> Color(0xFFE9E2F4)
                    SharedColorsTokens.Tint60 -> Color(0xFFF9F8FC)
                }

            SharedColorSets.Peach ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFFF8C00)
                    SharedColorsTokens.Shade10 -> Color(0xFFE67E00)
                    SharedColorsTokens.Shade20 -> Color(0xFFC26A00)
                    SharedColorsTokens.Shade30 -> Color(0xFF8F4E00)
                    SharedColorsTokens.Shade40 -> Color(0xFF4D2A00)
                    SharedColorsTokens.Shade50 -> Color(0xFF291600)
                    SharedColorsTokens.Tint10 -> Color(0xFFFF9A1F)
                    SharedColorsTokens.Tint20 -> Color(0xFFFFA83D)
                    SharedColorsTokens.Tint30 -> Color(0xFFFFBA66)
                    SharedColorsTokens.Tint40 -> Color(0xFFFFDDB3)
                    SharedColorsTokens.Tint50 -> Color(0xFFFFEDD6)
                    SharedColorsTokens.Tint60 -> Color(0xFFFFFAF5)
                }

            SharedColorSets.Pink ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFE43BA6)
                    SharedColorsTokens.Shade10 -> Color(0xFFCD3595)
                    SharedColorsTokens.Shade20 -> Color(0xFFAD2D7E)
                    SharedColorsTokens.Shade30 -> Color(0xFF80215D)
                    SharedColorsTokens.Shade40 -> Color(0xFF441232)
                    SharedColorsTokens.Shade50 -> Color(0xFF24091B)
                    SharedColorsTokens.Tint10 -> Color(0xFFE750B0)
                    SharedColorsTokens.Tint20 -> Color(0xFFEA66BA)
                    SharedColorsTokens.Tint30 -> Color(0xFFEF85C8)
                    SharedColorsTokens.Tint40 -> Color(0xFFF7C0E3)
                    SharedColorsTokens.Tint50 -> Color(0xFFFBDDF0)
                    SharedColorsTokens.Tint60 -> Color(0xFFFEF6FB)
                }

            SharedColorSets.Platinum ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF69797E)
                    SharedColorsTokens.Shade10 -> Color(0xFF5F6D71)
                    SharedColorsTokens.Shade20 -> Color(0xFF505C60)
                    SharedColorsTokens.Shade30 -> Color(0xFF3B4447)
                    SharedColorsTokens.Shade40 -> Color(0xFF1F2426)
                    SharedColorsTokens.Shade50 -> Color(0xFF111314)
                    SharedColorsTokens.Tint10 -> Color(0xFF79898D)
                    SharedColorsTokens.Tint20 -> Color(0xFF89989D)
                    SharedColorsTokens.Tint30 -> Color(0xFFA0ADB2)
                    SharedColorsTokens.Tint40 -> Color(0xFFCDD6D8)
                    SharedColorsTokens.Tint50 -> Color(0xFFE4E9EA)
                    SharedColorsTokens.Tint60 -> Color(0xFFF8F9FA)
                }

            SharedColorSets.Plum ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF77004D)
                    SharedColorsTokens.Shade10 -> Color(0xFF6B0045)
                    SharedColorsTokens.Shade20 -> Color(0xFF5A003B)
                    SharedColorsTokens.Shade30 -> Color(0xFF43002B)
                    SharedColorsTokens.Shade40 -> Color(0xFF240017)
                    SharedColorsTokens.Shade50 -> Color(0xFF13000C)
                    SharedColorsTokens.Tint10 -> Color(0xFF87105D)
                    SharedColorsTokens.Tint20 -> Color(0xFF98246F)
                    SharedColorsTokens.Tint30 -> Color(0xFFAD4589)
                    SharedColorsTokens.Tint40 -> Color(0xFFD696C0)
                    SharedColorsTokens.Tint50 -> Color(0xFFE9C4DC)
                    SharedColorsTokens.Tint60 -> Color(0xFFFAF0F6)
                }

            SharedColorSets.Pumpkin ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFCA5010)
                    SharedColorsTokens.Shade10 -> Color(0xFFB6480E)
                    SharedColorsTokens.Shade20 -> Color(0xFF9A3D0C)
                    SharedColorsTokens.Shade30 -> Color(0xFF712D09)
                    SharedColorsTokens.Shade40 -> Color(0xFF3D1805)
                    SharedColorsTokens.Shade50 -> Color(0xFF200D03)
                    SharedColorsTokens.Tint10 -> Color(0xFFD06228)
                    SharedColorsTokens.Tint20 -> Color(0xFFD77440)
                    SharedColorsTokens.Tint30 -> Color(0xFFDF8E64)
                    SharedColorsTokens.Tint40 -> Color(0xFFEFC4AD)
                    SharedColorsTokens.Tint50 -> Color(0xFFF7DFD2)
                    SharedColorsTokens.Tint60 -> Color(0xFFFDF7F4)
                }

            SharedColorSets.Purple ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF5C2E91)
                    SharedColorsTokens.Shade10 -> Color(0xFF532982)
                    SharedColorsTokens.Shade20 -> Color(0xFF46236E)
                    SharedColorsTokens.Shade30 -> Color(0xFF341A51)
                    SharedColorsTokens.Shade40 -> Color(0xFF1C0E2B)
                    SharedColorsTokens.Shade50 -> Color(0xFF0F0717)
                    SharedColorsTokens.Tint10 -> Color(0xFF6B3F9E)
                    SharedColorsTokens.Tint20 -> Color(0xFF7C52AB)
                    SharedColorsTokens.Tint30 -> Color(0xFF9470BD)
                    SharedColorsTokens.Tint40 -> Color(0xFFC6B1DE)
                    SharedColorsTokens.Tint50 -> Color(0xFFE0D3ED)
                    SharedColorsTokens.Tint60 -> Color(0xFFF7F4FB)
                }

            SharedColorSets.Red ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFD13438)
                    SharedColorsTokens.Shade10 -> Color(0xFFBC2F32)
                    SharedColorsTokens.Shade20 -> Color(0xFF9F282B)
                    SharedColorsTokens.Shade30 -> Color(0xFF751D1F)
                    SharedColorsTokens.Shade40 -> Color(0xFF3F1011)
                    SharedColorsTokens.Shade50 -> Color(0xFF210809)
                    SharedColorsTokens.Tint10 -> Color(0xFFD7494C)
                    SharedColorsTokens.Tint20 -> Color(0xFFDC5E62)
                    SharedColorsTokens.Tint30 -> Color(0xFFE37D80)
                    SharedColorsTokens.Tint40 -> Color(0xFFF1BBBC)
                    SharedColorsTokens.Tint50 -> Color(0xFFF8DADB)
                    SharedColorsTokens.Tint60 -> Color(0xFFFDF6F6)
                }

            SharedColorSets.RoyalBlue ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF004E8C)
                    SharedColorsTokens.Shade10 -> Color(0xFF00467E)
                    SharedColorsTokens.Shade20 -> Color(0xFF003B6A)
                    SharedColorsTokens.Shade30 -> Color(0xFF002C4E)
                    SharedColorsTokens.Shade40 -> Color(0xFF00172A)
                    SharedColorsTokens.Shade50 -> Color(0xFF000C16)
                    SharedColorsTokens.Tint10 -> Color(0xFF125E9A)
                    SharedColorsTokens.Tint20 -> Color(0xFF286FA8)
                    SharedColorsTokens.Tint30 -> Color(0xFF4A89BA)
                    SharedColorsTokens.Tint40 -> Color(0xFF9ABFDC)
                    SharedColorsTokens.Tint50 -> Color(0xFFC7DCED)
                    SharedColorsTokens.Tint60 -> Color(0xFFF0F6FA)
                }

            SharedColorSets.Seafoam ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF00CC6A)
                    SharedColorsTokens.Shade10 -> Color(0xFF00B85F)
                    SharedColorsTokens.Shade20 -> Color(0xFF009B51)
                    SharedColorsTokens.Shade30 -> Color(0xFF00723B)
                    SharedColorsTokens.Shade40 -> Color(0xFF003D20)
                    SharedColorsTokens.Shade50 -> Color(0xFF002111)
                    SharedColorsTokens.Tint10 -> Color(0xFF19D279)
                    SharedColorsTokens.Tint20 -> Color(0xFF34D889)
                    SharedColorsTokens.Tint30 -> Color(0xFF5AE0A0)
                    SharedColorsTokens.Tint40 -> Color(0xFFA8F0CD)
                    SharedColorsTokens.Tint50 -> Color(0xFFCFF7E4)
                    SharedColorsTokens.Tint60 -> Color(0xFFF3FDF8)
                }

            SharedColorSets.Silver ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF859599)
                    SharedColorsTokens.Shade10 -> Color(0xFF78868A)
                    SharedColorsTokens.Shade20 -> Color(0xFF657174)
                    SharedColorsTokens.Shade30 -> Color(0xFF4A5356)
                    SharedColorsTokens.Shade40 -> Color(0xFF282D2E)
                    SharedColorsTokens.Shade50 -> Color(0xFF151818)
                    SharedColorsTokens.Tint10 -> Color(0xFF92A1A5)
                    SharedColorsTokens.Tint20 -> Color(0xFFA0AEB1)
                    SharedColorsTokens.Tint30 -> Color(0xFFB3BFC2)
                    SharedColorsTokens.Tint40 -> Color(0xFFD8DFE0)
                    SharedColorsTokens.Tint50 -> Color(0xFFEAEEEF)
                    SharedColorsTokens.Tint60 -> Color(0xFFFAFBFB)
                }

            SharedColorSets.Steel ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF005B70)
                    SharedColorsTokens.Shade10 -> Color(0xFF005265)
                    SharedColorsTokens.Shade20 -> Color(0xFF004555)
                    SharedColorsTokens.Shade30 -> Color(0xFF00333F)
                    SharedColorsTokens.Shade40 -> Color(0xFF001B22)
                    SharedColorsTokens.Shade50 -> Color(0xFF000F12)
                    SharedColorsTokens.Tint10 -> Color(0xFF0F6C81)
                    SharedColorsTokens.Tint20 -> Color(0xFF237D92)
                    SharedColorsTokens.Tint30 -> Color(0xFF4496A9)
                    SharedColorsTokens.Tint40 -> Color(0xFF94C8D4)
                    SharedColorsTokens.Tint50 -> Color(0xFFC3E1E8)
                    SharedColorsTokens.Tint60 -> Color(0xFFEFF7F9)
                }

            SharedColorSets.Teal ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFF038387)
                    SharedColorsTokens.Shade10 -> Color(0xFF037679)
                    SharedColorsTokens.Shade20 -> Color(0xFF026467)
                    SharedColorsTokens.Shade30 -> Color(0xFF02494C)
                    SharedColorsTokens.Shade40 -> Color(0xFF012728)
                    SharedColorsTokens.Shade50 -> Color(0xFF001516)
                    SharedColorsTokens.Tint10 -> Color(0xFF159195)
                    SharedColorsTokens.Tint20 -> Color(0xFF2AA0A4)
                    SharedColorsTokens.Tint30 -> Color(0xFF4CB4B7)
                    SharedColorsTokens.Tint40 -> Color(0xFF9BD9DB)
                    SharedColorsTokens.Tint50 -> Color(0xFFC7EBEC)
                    SharedColorsTokens.Tint60 -> Color(0xFFF0FAFA)
                }

            SharedColorSets.Yellow ->
                when (token) {
                    SharedColorsTokens.Primary -> Color(0xFFFDE300)
                    SharedColorsTokens.Shade10 -> Color(0xFFE4CC00)
                    SharedColorsTokens.Shade20 -> Color(0xFFC0AD00)
                    SharedColorsTokens.Shade30 -> Color(0xFF8E7F00)
                    SharedColorsTokens.Shade40 -> Color(0xFF4C4400)
                    SharedColorsTokens.Shade50 -> Color(0xFF282400)
                    SharedColorsTokens.Tint10 -> Color(0xFFFDE61E)
                    SharedColorsTokens.Tint20 -> Color(0xFFFDEA3D)
                    SharedColorsTokens.Tint30 -> Color(0xFFFEEE66)
                    SharedColorsTokens.Tint40 -> Color(0xFFFEF7B2)
                    SharedColorsTokens.Tint50 -> Color(0xFFFFFAD6)
                    SharedColorsTokens.Tint60 -> Color(0xFFFFFEF5)
                }
        }
    }
}