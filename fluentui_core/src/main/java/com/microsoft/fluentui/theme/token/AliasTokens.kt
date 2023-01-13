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
        Background6,
        CanvasBackground,
        BackgroundLightStatic,
        BackgroundLightStaticDisabled,
        BackgroundDarkStatic,
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
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey92),
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

                NeutralBackgroundColorTokens.Background6 ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey82),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey36)
                    )

                NeutralBackgroundColorTokens.CanvasBackground ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey96),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey8)
                    )

                NeutralBackgroundColorTokens.BackgroundLightStatic ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White)
                    )

                NeutralBackgroundColorTokens.BackgroundLightStaticDisabled ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey68)
                    )

                NeutralBackgroundColorTokens.BackgroundDarkStatic ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey14),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey24)
                    )

                NeutralBackgroundColorTokens.BackgroundInverted ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey46),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey72)
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
        ForegroundOnColor,
        ForegroundDarkStatic,
        ForegroundLightStatic
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
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey18)
                    )
                NeutralForegroundColorTokens.ForegroundOnColor ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)
                    )
                NeutralForegroundColorTokens.ForegroundDarkStatic ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black),
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black)
                    )
                NeutralForegroundColorTokens.ForegroundLightStatic ->
                    FluentColor(
                        light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White),
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
                        dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Grey30)
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
        BrandBackgroundTint,
        BrandBackgroundDisabled
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
                        dark = brandColor[BrandColorTokens.Color130]
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
                        dark = Color.Unspecified
                    )

                BrandBackgroundColorTokens.BrandBackgroundTint ->
                    FluentColor(
                        light = brandColor[BrandColorTokens.Color150],
                        dark = brandColor[BrandColorTokens.Color30]
                    )

                BrandBackgroundColorTokens.BrandBackgroundDisabled ->
                    FluentColor(
                        light = brandColor[BrandColorTokens.Color140],
                        dark = brandColor[BrandColorTokens.Color40]
                    )
            }
        }
    }

    enum class BrandForegroundColorTokens {
        BrandForeground1,
        BrandForeground1Pressed,
        BrandForeground1Selected,
        BrandForegroundTint,
        BrandForegroundDisabled1,
        BrandForegroundDisabled2
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
                        dark = brandColor[BrandColorTokens.Color130]
                    )

                BrandForegroundColorTokens.BrandForeground1Selected ->
                    FluentColor(
                        light = brandColor[BrandColorTokens.Color60],
                        dark = brandColor[BrandColorTokens.Color120]
                    )

                BrandForegroundColorTokens.BrandForegroundTint ->
                    FluentColor(
                        light = brandColor[BrandColorTokens.Color60],
                        dark = brandColor[BrandColorTokens.Color130]
                    )

                BrandForegroundColorTokens.BrandForegroundDisabled1 ->
                    FluentColor(
                        light = brandColor[BrandColorTokens.Color90],
                        dark = brandColor[BrandColorTokens.Color90]
                    )

                BrandForegroundColorTokens.BrandForegroundDisabled2 ->
                    FluentColor(
                        light = brandColor[BrandColorTokens.Color140],
                        dark = brandColor[BrandColorTokens.Color40]
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
                        dark = brandColor[BrandColorTokens.Color130]
                    )

                BrandStrokeColorTokens.BrandStroke1Selected ->
                    FluentColor(
                        light = brandColor[BrandColorTokens.Color60],
                        dark = brandColor[BrandColorTokens.Color120]
                    )
            }
        }
    }

    enum class ErrorAndStatusColorTokens {
        DangerBackground1,
        DangerBackground2,
        DangerForeground1,
        DangerForeground2,
        SuccessBackground1,
        SuccessBackground2,
        SuccessForeground1,
        SuccessForeground2,
        WarningBackground1,
        WarningBackground2,
        WarningForeground1,
        WarningForeground2,
        SevereBackground1,
        SevereBackground2,
        SevereForeground1,
        SevereForeground2
    }

    open val errorAndStatusColor: TokenSet<ErrorAndStatusColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                ErrorAndStatusColorTokens.DangerBackground1 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Tint60
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Shade40
                        )
                    )

                ErrorAndStatusColorTokens.DangerBackground2 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Shade10
                        )
                    )
                ErrorAndStatusColorTokens.DangerForeground1 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Shade10
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Tint30
                        )
                    )
                ErrorAndStatusColorTokens.DangerForeground2 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Tint30
                        )
                    )
                ErrorAndStatusColorTokens.SuccessBackground1 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Tint60
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Shade40
                        )
                    )

                ErrorAndStatusColorTokens.SuccessBackground2 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Shade10
                        )
                    )
                ErrorAndStatusColorTokens.SuccessForeground1 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Shade10
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Tint30
                        )
                    )
                ErrorAndStatusColorTokens.SuccessForeground2 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Tint30
                        )
                    )
                ErrorAndStatusColorTokens.WarningBackground1 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Yellow,
                            GlobalTokens.SharedColorsTokens.Tint60
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Yellow,
                            GlobalTokens.SharedColorsTokens.Shade40
                        )
                    )

                ErrorAndStatusColorTokens.WarningBackground2 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Yellow,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Yellow,
                            GlobalTokens.SharedColorsTokens.Shade10
                        )
                    )
                ErrorAndStatusColorTokens.WarningForeground1 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Yellow,
                            GlobalTokens.SharedColorsTokens.Shade30
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Yellow,
                            GlobalTokens.SharedColorsTokens.Tint30
                        )
                    )
                ErrorAndStatusColorTokens.WarningForeground2 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Yellow,
                            GlobalTokens.SharedColorsTokens.Shade30
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Yellow,
                            GlobalTokens.SharedColorsTokens.Tint30
                        )
                    )
                ErrorAndStatusColorTokens.SevereBackground1 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Orange,
                            GlobalTokens.SharedColorsTokens.Tint60
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Shade40
                        )
                    )

                ErrorAndStatusColorTokens.SevereBackground2 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Orange,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Orange,
                            GlobalTokens.SharedColorsTokens.Shade10
                        )
                    )
                ErrorAndStatusColorTokens.SevereForeground1 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Orange,
                            GlobalTokens.SharedColorsTokens.Shade10
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Orange,
                            GlobalTokens.SharedColorsTokens.Tint30
                        )
                    )
                ErrorAndStatusColorTokens.SevereForeground2 ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Orange,
                            GlobalTokens.SharedColorsTokens.Shade20
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Orange,
                            GlobalTokens.SharedColorsTokens.Tint30
                        )
                    )
            }
        }
    }

    enum class PresenceColorTokens {
        Away,
        DND,
        Busy,
        Available,
        OutOfOffice
    }

    open val presenceColor: TokenSet<PresenceColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                PresenceColorTokens.Away ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Marigold,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Marigold,
                            GlobalTokens.SharedColorsTokens.Primary
                        )
                    )
                PresenceColorTokens.Busy, PresenceColorTokens.DND ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Red,
                            GlobalTokens.SharedColorsTokens.Tint10
                        )
                    )
                PresenceColorTokens.Available ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Green,
                            GlobalTokens.SharedColorsTokens.Tint20
                        )
                    )
                PresenceColorTokens.OutOfOffice ->
                    FluentColor(
                        light = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Berry,
                            GlobalTokens.SharedColorsTokens.Primary
                        ),
                        dark = GlobalTokens.sharedColors(
                            GlobalTokens.SharedColorSets.Berry,
                            GlobalTokens.SharedColorsTokens.Tint20
                        )
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
                        weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.LargeTitle ->
                    FontInfo(
                        fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size800),
                        weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
                    )
                TypographyTokens.Title1 ->
                    FontInfo(
                        fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size700),
                        weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Bold)
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
                        weight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.SemiBold)
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