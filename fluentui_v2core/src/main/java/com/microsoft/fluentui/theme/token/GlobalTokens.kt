//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Global Tokens represent a unified set of constants to be used by Fluent UI.

 open class GlobalTokens {
    // MARK: - BrandColors
     enum class BrandColorsTokens{
        primary,
        shade10,
        shade20,
        shade30,
        tint10,
        tint20,
        tint30,
        tint40
    }
     open val brandColors: TokenSet<BrandColorsTokens, DynamicColor> by lazy {
         TokenSet { token ->
             when (token) {
                 BrandColorsTokens.primary -> DynamicColor(
                     light = Color(0xFF0078D4),
                     dark = Color(0xFF0086F0)
                 )
                 BrandColorsTokens.shade10 -> DynamicColor(
                     light = Color(0xFF106EBE),
                     dark = Color(0xFF1890F1)
                 )
                 BrandColorsTokens.shade20 -> DynamicColor(
                     light = Color(0xFF005A9E),
                     dark = Color(0xFF3AA0F3)
                 )
                 BrandColorsTokens.shade30 -> DynamicColor(
                     light = Color(0xFF004578),
                     dark = Color(0xFF6CB8F6)
                 )
                 BrandColorsTokens.tint10 -> DynamicColor(
                     light = Color(0xFF2B88D8),
                     dark = Color(0xFF0074D3)
                 )
                 BrandColorsTokens.tint20 -> DynamicColor(
                     light = Color(0xFFC7E0F4),
                     dark = Color(0xFF004F90)
                 )
                 BrandColorsTokens.tint30 -> DynamicColor(
                     light = Color(0xFFDEECF9),
                     dark = Color(0xFF002848)
                 )
                 BrandColorsTokens.tint40 -> DynamicColor(
                     light = Color(0xFFEFF6FC),
                     dark = Color(0xFF001526)
                 )
             }
         }
     }

    // MARK: - NeutralColors
     public enum class NeutralColorsToken{
        black,
        grey2,
        grey4,
        grey6,
        grey8,
        grey10,
        grey12,
        grey14,
        grey16,
        grey18,
        grey20,
        grey22,
        grey24,
        grey26,
        grey28,
        grey30,
        grey32,
        grey34,
        grey36,
        grey38,
        grey40,
        grey42,
        grey44,
        grey46,
        grey48,
        grey50,
        grey52,
        grey54,
        grey56,
        grey58,
        grey60,
        grey62,
        grey64,
        grey66,
        grey68,
        grey70,
        grey72,
        grey74,
        grey76,
        grey78,
        grey80,
        grey82,
        grey84,
        grey86,
        grey88,
        grey90,
        grey92,
        grey94,
        grey96,
        grey98,
        white
    }
     open val neutralColors: TokenSet<NeutralColorsToken, Color> by lazy{
        TokenSet{ token ->
            when(token){
                NeutralColorsToken.black -> Color(0xFF000000)
                NeutralColorsToken.grey2 -> Color(0xFF050505)
                NeutralColorsToken.grey4 -> Color(0xFF0A0A0A)
                NeutralColorsToken.grey6 -> Color(0xFF0F0F0F)
                NeutralColorsToken.grey8 -> Color(0xFF141414)
                NeutralColorsToken.grey10 -> Color(0xFF1A1A1A)
                NeutralColorsToken.grey12 -> Color(0xFF1F1F1F)
                NeutralColorsToken.grey14 -> Color(0xFF242424)
                NeutralColorsToken.grey16 -> Color(0xFF292929)
                NeutralColorsToken.grey18 -> Color(0xFF2E2E2E)
                NeutralColorsToken.grey20 -> Color(0xFF333333)
                NeutralColorsToken.grey22 -> Color(0xFF383838)
                NeutralColorsToken.grey24 -> Color(0xFF3D3D3D)
                NeutralColorsToken.grey26 -> Color(0xFF424242)
                NeutralColorsToken.grey28 -> Color(0xFF474747)
                NeutralColorsToken.grey30 -> Color(0xFF4D4D4D)
                NeutralColorsToken.grey32 -> Color(0xFF525252)
                NeutralColorsToken.grey34 -> Color(0xFF575757)
                NeutralColorsToken.grey36 -> Color(0xFF5C5C5C)
                NeutralColorsToken.grey38 -> Color(0xFF616161)
                NeutralColorsToken.grey40 -> Color(0xFF666666)
                NeutralColorsToken.grey42 -> Color(0xFF6B6B6B)
                NeutralColorsToken.grey44 -> Color(0xFF707070)
                NeutralColorsToken.grey46 -> Color(0xFF757575)
                NeutralColorsToken.grey48 -> Color(0xFF7A7A7A)
                NeutralColorsToken.grey50 -> Color(0xFF808080)
                NeutralColorsToken.grey52 -> Color(0xFF858585)
                NeutralColorsToken.grey54 -> Color(0xFF8A8A8A)
                NeutralColorsToken.grey56 -> Color(0xFF8F8F8F)
                NeutralColorsToken.grey58 -> Color(0xFF949494)
                NeutralColorsToken.grey60 -> Color(0xFF999999)
                NeutralColorsToken.grey62 -> Color(0xFF9E9E9E)
                NeutralColorsToken.grey64 -> Color(0xFFA3A3A3)
                NeutralColorsToken.grey66 -> Color(0xFFA8A8A8)
                NeutralColorsToken.grey68 -> Color(0xFFADADAD)
                NeutralColorsToken.grey70 -> Color(0xFFB3B3B3)
                NeutralColorsToken.grey72 -> Color(0xFFB8B8B8)
                NeutralColorsToken.grey74 -> Color(0xFFBDBDBD)
                NeutralColorsToken.grey76 -> Color(0xFFC2C2C2)
                NeutralColorsToken.grey78 -> Color(0xFFC7C7C7)
                NeutralColorsToken.grey80 -> Color(0xFFCCCCCC)
                NeutralColorsToken.grey82 -> Color(0xFFD1D1D1)
                NeutralColorsToken.grey84 -> Color(0xFFD6D6D6)
                NeutralColorsToken.grey86 -> Color(0xFFDBDBDB)
                NeutralColorsToken.grey88 -> Color(0xFFE0E0E0)
                NeutralColorsToken.grey90 -> Color(0xFFE6E6E6)
                NeutralColorsToken.grey92 -> Color(0xFFEBEBEB)
                NeutralColorsToken.grey94 -> Color(0xFFF0F0F0)
                NeutralColorsToken.grey96 -> Color(0xFFF5F5F5)
                NeutralColorsToken.grey98 -> Color(0xFFFAFAFA)
                NeutralColorsToken.white -> Color(0xFFFFFFFF)
            }
        }
    }
    
    // MARK: - SharedColors

     enum class SharedColorSets{
        darkRed,
        burgundy,
        cranberry,
        red,
        darkOrange,
        bronze,
        pumpkin,
        orange,
        peach,
        marigold,
        yellow,
        gold,
        brass,
        brown,
        darkBrown,
        lime,
        forest,
        seafoam,
        lightGreen,
        green,
        darkGreen,
        lightTeal,
        teal,
        darkTeal,
        cyan,
        steel,
        lightBlue,
        blue,
        royalBlue,
        darkBlue,
        cornflower,
        navy,
        lavender,
        purple,
        darkPurple,
        orchid,
        grape,
        berry,
        lilac,
        pink,
        hotPink,
        magenta,
        plum,
        beige,
        mink,
        silver,
        platinum,
        anchor,
        charcoal
    }

     enum class SharedColorsTokens{
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
                SharedColorSets.anchor ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF394146)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF333A3F)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF2B3135)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF202427)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF111315)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF090A0B)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF4D565C)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF626C72)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF808A90)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFBCC3C7)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFDBDFE1)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF6F7F8)
                        }
                    }

                SharedColorSets.beige ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                            when (token) {
                                SharedColorsTokens.primary ->
                                    Color(0xFF7A7574)
                                SharedColorsTokens.shade10 ->
                                    Color(0xFF6E6968)
                                SharedColorsTokens.shade20 ->
                                    Color(0xFF5D5958)
                                SharedColorsTokens.shade30 ->
                                    Color(0xFF444241)
                                SharedColorsTokens.shade40 ->
                                    Color(0xFF252323)
                                SharedColorsTokens.shade50 ->
                                    Color(0xFF141313)
                                SharedColorsTokens.tint10 ->
                                    Color(0xFF8A8584)
                                SharedColorsTokens.tint20 ->
                                    Color(0xFF9A9594)
                                SharedColorsTokens.tint30 ->
                                    Color(0xFFAFABAA)
                                SharedColorsTokens.tint40 ->
                                    Color(0xFFD7D4D4)
                                SharedColorsTokens.tint50 ->
                                    Color(0xFFEAE8E8)
                                SharedColorsTokens.tint60 ->
                                    Color(0xFFFAF9F9)
                            }
                        }

                SharedColorSets.berry ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFC239B3)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFAF33A1)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF932B88)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF6D2064)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF3A1136)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF1F091D)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFC94CBC)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFD161C4)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFDA7ED0)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFEDBBE7)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF5DAF2)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFDF5FC)
                        }
                    }

                SharedColorSets.blue ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF0078D4)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF006CBF)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF005BA1)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF004377)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF002440)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF001322)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF1A86D9)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF3595DE)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF5CAAE5)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFA9D3F2)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFD0E7F8)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF3F9FD)
                        }
                    }

                SharedColorSets.brass ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF986F0B)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF89640A)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF745408)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF553E06)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF2E2103)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF181202)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFA47D1E)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFB18C34)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFC1A256)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFE0CEA2)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFEFE4CB)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFBF8F2)
                        }
                    }

                SharedColorSets.bronze ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFA74109)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF963A08)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF7F3107)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF5E2405)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF321303)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF1B0A01)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFB2521E)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFBC6535)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFCA8057)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFE5BBA4)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF1D9CC)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFBF5F2)
                        }
                    }

                SharedColorSets.brown ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF8E562E)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF804D29)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF6C4123)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF50301A)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF2B1A0E)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF170E07)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF9C663F)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFA97652)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFBB8F6F)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFDDC3B0)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFEDDED3)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFAF7F4)
                        }
                    }

                SharedColorSets.burgundy ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFA4262C)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF942228)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF7D1D21)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF5C1519)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF310B0D)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF1A0607)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFAF393E)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFBA4D52)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFC86C70)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFE4AFB2)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF0D3D4)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFBF4F4)
                        }
                    }

                SharedColorSets.charcoal ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF393939)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF333333)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF2B2B2B)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF202020)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF111111)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF090909)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF515151)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF686868)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF888888)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFC4C4C4)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFDFDFDF)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF7F7F7)
                        }
                    }

                SharedColorSets.cornflower ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF4F6BED)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF4760D5)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF3C51B4)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF2C3C85)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF182047)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF0D1126)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF637CEF)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF778DF1)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF93A4F4)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFC8D1FA)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE1E6FC)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF7F9FE)
                        }
                    }

                SharedColorSets.cranberry ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFC50F1F)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFB10E1C)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF960B18)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF6E0811)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF3B0509)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF200205)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFCC2635)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFD33F4C)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFDC626D)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFEEACB2)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF6D1D5)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFDF3F4)
                        }
                    }

                SharedColorSets.cyan ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF0099BC)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF008AA9)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF00748F)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF005669)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF002E38)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF00181E)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF18A4C4)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF31AFCC)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF56BFD7)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFA4DEEB)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFCDEDF4)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF2FAFC)
                        }
                    }

                SharedColorSets.darkBlue ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF003966)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF00335C)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF002B4E)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF002039)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF00111F)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF000910)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF0E4A78)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF215C8B)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF4178A3)
                            SharedColorsTokens.tint40 ->
                                Color(0xFF92B5D1)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFC2D6E7)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFEFF4F9)
                        }
                    }

                SharedColorSets.darkBrown ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF4D291C)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF452519)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF3A1F15)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF2B1710)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF170C08)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF0C0704)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF623A2B)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF784D3E)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF946B5C)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFCAADA3)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE3D2CB)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF8F3F2)
                        }
                    }

                SharedColorSets.darkGreen ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF0B6A0B)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF0A5F0A)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF085108)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF063B06)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF032003)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF021102)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF1A7C1A)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF2D8E2D)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF4DA64D)
                            SharedColorsTokens.tint40 ->
                                Color(0xFF9AD29A)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFC6E7C6)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF0F9F0)
                        }
                    }

                SharedColorSets.darkOrange ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFDA3B01)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFC43501)
                            SharedColorsTokens.shade20 ->
                                Color(0xFFA62D01)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF7A2101)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF411200)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF230900)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFDE501C)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFE36537)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFE9835E)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFF4BFAB)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF9DCD1)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFDF6F3)
                        }
                    }

                SharedColorSets.darkPurple ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF401B6C)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF3A1861)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF311552)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF240F3C)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF130820)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF0A0411)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF512B7E)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF633E8F)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF7E5CA7)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFB9A3D3)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFD8CCE7)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF5F2F9)
                        }
                    }

                SharedColorSets.darkRed ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF750B1C)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF690A19)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF590815)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF420610)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF230308)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF130204)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF861B2C)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF962F3F)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFAC4F5E)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFD69CA5)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE9C7CD)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF9F0F2)
                        }
                    }

                SharedColorSets.darkTeal ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF006666)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF005C5C)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF004E4E)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF003939)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF001F1F)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF001010)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF0E7878)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF218B8B)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF41A3A3)
                            SharedColorsTokens.tint40 ->
                                Color(0xFF92D1D1)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFC2E7E7)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFEFF9F9)
                        }
                    }

                SharedColorSets.forest ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF498205)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF427505)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF376304)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF294903)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF162702)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF0C1501)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF599116)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF6BA02B)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF85B44C)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFBDD99B)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFDBEBC7)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF6FAF0)
                        }
                    }

                SharedColorSets.gold ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFC19C00)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFAE8C00)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF937700)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF6C5700)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF3A2F00)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF1F1900)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFC8A718)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFD0B232)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFDAC157)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFECDFA5)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF5EECE)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFDFBF2)
                        }
                    }

                SharedColorSets.grape ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF881798)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF7A1589)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF671174)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF4C0D55)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF29072E)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF160418)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF952AA4)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFA33FB1)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFB55FC1)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFD9A7E0)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFEACEEF)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFAF2FB)
                        }
                    }

                SharedColorSets.green ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF107C10)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF0E700E)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF0C5E0C)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF094509)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF052505)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF031403)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF218C21)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF359B35)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF54B054)
                            SharedColorsTokens.tint40 ->
                                Color(0xFF9FD89F)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFC9EAC9)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF1FAF1)
                        }
                    }

                SharedColorSets.hotPink ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFE3008C)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFCC007E)
                            SharedColorsTokens.shade20 ->
                                Color(0xFFAD006A)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF7F004E)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF44002A)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF240016)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFE61C99)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFEA38A6)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFEE5FB7)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFF7ADDA)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFFBD2EB)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFEF4FA)
                        }
                    }

                SharedColorSets.lavender ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF7160E8)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF6656D1)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF5649B0)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF3F3682)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF221D46)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF120F25)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF8172EB)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF9184EE)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFA79CF1)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFD2CCF8)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE7E4FB)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF9F8FE)
                        }
                    }

                SharedColorSets.lightBlue ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF3A96DD)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF3487C7)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF2C72A8)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF20547C)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF112D42)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF091823)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF4FA1E1)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF65ADE5)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF83BDEB)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFBFDDF5)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFDCEDFA)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF6FAFE)
                        }
                    }

                SharedColorSets.lightGreen ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF13A10E)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF11910D)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF0E7A0B)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF0B5A08)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF063004)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF031A02)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF27AC22)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF3DB838)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF5EC75A)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFA7E3A5)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFCEF0CD)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF2FBF2)
                        }
                    }

                SharedColorSets.lightTeal ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF00B7C3)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF00A5AF)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF008B94)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF00666D)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF00373A)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF001D1F)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF18BFCA)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF32C8D1)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF58D3DB)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFA6E9ED)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFCEF3F5)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF2FCFD)
                        }
                    }

                SharedColorSets.lilac ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFB146C2)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF9F3FAF)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF863593)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF63276D)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF35153A)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF1C0B1F)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFBA58C9)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFC36BD1)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFCF87DA)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFE6BFED)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF2DCF5)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFCF6FD)
                        }
                    }

                SharedColorSets.lime ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF73AA24)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF689920)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF57811B)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF405F14)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF23330B)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF121B06)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF81B437)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF90BE4C)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFA4CC6C)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFCFE5AF)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE5F1D3)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF8FCF4)
                        }
                    }

                SharedColorSets.magenta ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFBF0077)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFAC006B)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF91005A)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF6B0043)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF390024)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF1F0013)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFC71885)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFCE3293)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFD957A8)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFECA5D1)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF5CEE6)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFCF2F9)
                        }
                    }

                SharedColorSets.marigold ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFEAA300)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFD39300)
                            SharedColorsTokens.shade20 ->
                                Color(0xFFB27C00)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF835B00)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF463100)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF251A00)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFEDAD1C)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFEFB839)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFF2C661)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFF9E2AE)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFFCEFD3)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFEFBF4)
                        }
                    }

                SharedColorSets.mink ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF5D5A58)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF54514F)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF474443)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF343231)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF1C1B1A)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF0F0E0E)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF706D6B)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF84817E)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF9E9B99)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFCECCCB)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE5E4E3)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF8F8F8)
                        }
                    }

                SharedColorSets.navy ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF0027B4)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF0023A2)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF001E89)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF001665)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF000C36)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF00061D)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF173BBD)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF3050C6)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF546FD2)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFA3B2E8)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFCCD5F3)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF2F4FC)
                        }
                    }

                SharedColorSets.orange ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFF7630C)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFDE590B)
                            SharedColorsTokens.shade20 ->
                                Color(0xFFBC4B09)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF8A3707)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF4A1E04)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF271002)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFF87528)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFF98845)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFFAA06B)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFFDCFB4)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFFEE5D7)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFFF9F5)
                        }
                    }

                SharedColorSets.orchid ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF8764B8)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF795AA6)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF674C8C)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF4C3867)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF281E37)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF16101D)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF9373C0)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFA083C9)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFB29AD4)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFD7CAEA)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE9E2F4)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF9F8FC)
                        }
                    }
                SharedColorSets.peach ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFFF8C00)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFE67E00)
                            SharedColorsTokens.shade20 ->
                                Color(0xFFC26A00)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF8F4E00)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF4D2A00)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF291600)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFFF9A1F)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFFFA83D)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFFFBA66)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFFFDDB3)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFFFEDD6)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFFFAF5)
                        }
                    }

                SharedColorSets.pink ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFE43BA6)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFCD3595)
                            SharedColorsTokens.shade20 ->
                                Color(0xFFAD2D7E)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF80215D)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF441232)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF24091B)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFE750B0)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFEA66BA)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFEF85C8)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFF7C0E3)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFFBDDF0)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFEF6FB)
                        }
                    }

                SharedColorSets.platinum ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF69797E)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF5F6D71)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF505C60)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF3B4447)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF1F2426)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF111314)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF79898D)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF89989D)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFA0ADB2)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFCDD6D8)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE4E9EA)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF8F9FA)
                        }
                    }

                SharedColorSets.plum ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF77004D)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF6B0045)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF5A003B)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF43002B)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF240017)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF13000C)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF87105D)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF98246F)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFAD4589)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFD696C0)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE9C4DC)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFAF0F6)
                        }
                    }

                SharedColorSets.pumpkin ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFCA5010)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFB6480E)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF9A3D0C)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF712D09)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF3D1805)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF200D03)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFD06228)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFD77440)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFDF8E64)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFEFC4AD)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF7DFD2)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFDF7F4)
                        }
                    }

                SharedColorSets.purple ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF5C2E91)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF532982)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF46236E)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF341A51)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF1C0E2B)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF0F0717)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF6B3F9E)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF7C52AB)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF9470BD)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFC6B1DE)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFE0D3ED)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF7F4FB)
                        }
                    }

                SharedColorSets.red ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFD13438)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFBC2F32)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF9F282B)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF751D1F)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF3F1011)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF210809)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFD7494C)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFDC5E62)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFE37D80)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFF1BBBC)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFF8DADB)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFDF6F6)
                        }
                    }

                SharedColorSets.royalBlue ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF004E8C)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF00467E)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF003B6A)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF002C4E)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF00172A)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF000C16)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF125E9A)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF286FA8)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF4A89BA)
                            SharedColorsTokens.tint40 ->
                                Color(0xFF9ABFDC)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFC7DCED)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF0F6FA)
                        }
                    }

                SharedColorSets.seafoam ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF00CC6A)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF00B85F)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF009B51)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF00723B)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF003D20)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF002111)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF19D279)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF34D889)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF5AE0A0)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFA8F0CD)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFCFF7E4)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF3FDF8)
                        }
                    }

                SharedColorSets.silver ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF859599)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF78868A)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF657174)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF4A5356)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF282D2E)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF151818)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF92A1A5)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFA0AEB1)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFB3BFC2)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFD8DFE0)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFEAEEEF)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFAFBFB)
                        }
                    }

                SharedColorSets.steel ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF005B70)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF005265)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF004555)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF00333F)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF001B22)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF000F12)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF0F6C81)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF237D92)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF4496A9)
                            SharedColorsTokens.tint40 ->
                                Color(0xFF94C8D4)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFC3E1E8)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFEFF7F9)
                        }
                    }

                SharedColorSets.teal ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFF038387)
                            SharedColorsTokens.shade10 ->
                                Color(0xFF037679)
                            SharedColorsTokens.shade20 ->
                                Color(0xFF026467)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF02494C)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF012728)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF001516)
                            SharedColorsTokens.tint10 ->
                                Color(0xFF159195)
                            SharedColorsTokens.tint20 ->
                                Color(0xFF2AA0A4)
                            SharedColorsTokens.tint30 ->
                                Color(0xFF4CB4B7)
                            SharedColorsTokens.tint40 ->
                                Color(0xFF9BD9DB)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFC7EBEC)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFF0FAFA)
                        }
                    }

                SharedColorSets.yellow ->
                    TokenSet<SharedColorsTokens, Color> { token ->
                        when (token) {
                            SharedColorsTokens.primary ->
                                Color(0xFFFDE300)
                            SharedColorsTokens.shade10 ->
                                Color(0xFFE4CC00)
                            SharedColorsTokens.shade20 ->
                                Color(0xFFC0AD00)
                            SharedColorsTokens.shade30 ->
                                Color(0xFF8E7F00)
                            SharedColorsTokens.shade40 ->
                                Color(0xFF4C4400)
                            SharedColorsTokens.shade50 ->
                                Color(0xFF282400)
                            SharedColorsTokens.tint10 ->
                                Color(0xFFFDE61E)
                            SharedColorsTokens.tint20 ->
                                Color(0xFFFDEA3D)
                            SharedColorsTokens.tint30 ->
                                Color(0xFFFEEE66)
                            SharedColorsTokens.tint40 ->
                                Color(0xFFFEF7B2)
                            SharedColorsTokens.tint50 ->
                                Color(0xFFFFFAD6)
                            SharedColorsTokens.tint60 ->
                                Color(0xFFFFFEF5)
                        }
                    }
            }
        }
    }

    // MARK-> - FontSize

     enum class FontSizeToken{
        size100,
        size200,
        size300,
        size400,
        size500,
        size600,
        size700,
        size800,
        size900
    }
     open val fontSize: TokenSet<FontSizeToken, TextUnit> by lazy {
        TokenSet { token ->
            when (token) {
                FontSizeToken.size100 ->
                    12.0.sp
                FontSizeToken.size200 ->
                    13.0.sp
                FontSizeToken.size300 ->
                    15.0.sp
                FontSizeToken.size400 ->
                    17.0.sp
                FontSizeToken.size500 ->
                    20.0.sp
                FontSizeToken.size600 ->
                    22.0.sp
                FontSizeToken.size700 ->
                    28.0.sp
                FontSizeToken.size800 ->
                    34.0.sp
                FontSizeToken.size900 ->
                    60.0.sp
            }
        }
    }

    // MARK-> - FontWeight

     enum class FontWeightToken{
        regular,
        medium,
        semibold,
        bold
    }
     open val fontWeight: TokenSet<FontWeightToken, FontWeight> by lazy {
        TokenSet { token ->
            when (token) {
                FontWeightToken.regular ->
                    FontWeight.Normal
                FontWeightToken.medium ->
                    FontWeight.Medium
                FontWeightToken.semibold ->
                    FontWeight.SemiBold
                FontWeightToken.bold ->
                    FontWeight.Bold
            }
        }
    }

    // MARK-> - IconSize

     enum class IconSizeToken{
        xxxSmall,
        xxSmall,
        xSmall,
        small,
        medium,
        large,
        xLarge,
        xxLarge,
        xxxLarge
    }
     open val iconSize: TokenSet<IconSizeToken, Int> by lazy {
        TokenSet { token ->
            when (token) {
                IconSizeToken.xxxSmall ->
                    10
                IconSizeToken.xxSmall ->
                    12
                IconSizeToken.xSmall ->
                    16
                IconSizeToken.small ->
                    20
                IconSizeToken.medium ->
                    24
                IconSizeToken.large ->
                    28
                IconSizeToken.xLarge ->
                    36
                IconSizeToken.xxLarge ->
                    40
                IconSizeToken.xxxLarge ->
                    48
            }
        }
    }

    // MARK-> - Spacing

     enum class SpacingToken{
        none,
        xxxSmall,
        xxSmall,
        xSmall,
        small,
        medium,
        large,
        xLarge,
        xxLarge,
        xxxLarge,
        xxxxLarge
    }
     open val spacing: TokenSet<SpacingToken, Int> by lazy {
        TokenSet { token ->
            when (token) {
                SpacingToken.none ->
                    0
                SpacingToken.xxxSmall ->
                    2
                SpacingToken.xxSmall ->
                    4
                SpacingToken.xSmall ->
                    8
                SpacingToken.small ->
                    12
                SpacingToken.medium ->
                    16
                SpacingToken.large ->
                    20
                SpacingToken.xLarge ->
                    24
                SpacingToken.xxLarge ->
                    36
                SpacingToken.xxxLarge ->
                    48
                SpacingToken.xxxxLarge ->
                    72
            }
        }
    }

    // MARK-> - BorderRadius

     enum class BorderRadiusToken{
        none,
        small,
        medium,
        large,
        xLarge,
        circle
    }
     open val borderRadius: TokenSet<BorderRadiusToken, Int> by lazy {
        TokenSet { token ->
            when (token) {
                BorderRadiusToken.none ->
                    0
                BorderRadiusToken.small ->
                    2
                BorderRadiusToken.medium ->
                    4
                BorderRadiusToken.large ->
                    8
                BorderRadiusToken.xLarge ->
                    12
                BorderRadiusToken.circle ->
                    9999
            }
        }
    }

    // MARK-> - BorderSize

     enum class BorderSizeToken{
        none,
        thin,
        thick,
        thicker,
        thickest
    }
     open val borderSize: TokenSet<BorderSizeToken, Dp> by lazy {
        TokenSet { token ->
            when (token) {
                BorderSizeToken.none ->
                    0.dp
                BorderSizeToken.thin ->
                    1.dp
                BorderSizeToken.thick ->
                    2.dp
                BorderSizeToken.thicker ->
                    4.dp
                BorderSizeToken.thickest ->
                    6.dp
            }
        }
    }
}

internal val LocalGlobalToken = compositionLocalOf { GlobalTokens()}