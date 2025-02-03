//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//
package com.microsoft.fluentui.theme.token

import android.os.Parcelable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

object FluentAliasTokens {
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

    enum class NeutralStrokeColorTokens {
        Stroke1,
        Stroke2,
        StrokeDisabled,
        StrokeAccessible,
        StrokeFocus1,
        StrokeFocus2
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

    enum class BrandForegroundColorTokens {
        BrandForeground1,
        BrandForeground1Pressed,
        BrandForeground1Selected,
        BrandForegroundTint,
        BrandForegroundDisabled1,
        BrandForegroundDisabled2
    }

    enum class BrandStrokeColorTokens {
        BrandStroke1,
        BrandStroke1Pressed,
        BrandStroke1Selected,
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

    enum class PresenceColorTokens {
        Away,
        DND,
        Busy,
        Available,
        OutOfOffice
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
}

interface IAliasTokens {
    val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color>
    val neutralBackgroundColor: TokenSet<FluentAliasTokens.NeutralBackgroundColorTokens, FluentColor>
    val neutralForegroundColor: TokenSet<FluentAliasTokens.NeutralForegroundColorTokens, FluentColor>
    val neutralStrokeColor: TokenSet<FluentAliasTokens.NeutralStrokeColorTokens, FluentColor>
    val brandBackgroundColor: TokenSet<FluentAliasTokens.BrandBackgroundColorTokens, FluentColor>
    val brandForegroundColor: TokenSet<FluentAliasTokens.BrandForegroundColorTokens, FluentColor>
    val brandStroke: TokenSet<FluentAliasTokens.BrandStrokeColorTokens, FluentColor>
    val errorAndStatusColor: TokenSet<FluentAliasTokens.ErrorAndStatusColorTokens, FluentColor>
    val presenceColor: TokenSet<FluentAliasTokens.PresenceColorTokens, FluentColor>
    val typography: TokenSet<FluentAliasTokens.TypographyTokens, TextStyle>
}
/**
 * Extend the AliasToken to add custom token or providing new values.*
 */
@Parcelize
open class AliasTokens : IAliasTokens, Parcelable {

    @IgnoredOnParcel
    override val brandColor: TokenSet<FluentAliasTokens.BrandColorTokens, Color> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.BrandColorTokens.Color10 -> Color(0xFF061724)
                FluentAliasTokens.BrandColorTokens.Color20 -> Color(0xFF082338)
                FluentAliasTokens.BrandColorTokens.Color30 -> Color(0xFF0A2E4A)
                FluentAliasTokens.BrandColorTokens.Color40 -> Color(0xFF0C3B5E)
                FluentAliasTokens.BrandColorTokens.Color50 -> Color(0xFF0E4775)
                FluentAliasTokens.BrandColorTokens.Color60 -> Color(0xFF0F548C)
                FluentAliasTokens.BrandColorTokens.Color70 -> Color(0xFF115EA3)
                FluentAliasTokens.BrandColorTokens.Color80 -> Color(0xFF0F6CBD)
                FluentAliasTokens.BrandColorTokens.Color90 -> Color(0xFF2886DE)
                FluentAliasTokens.BrandColorTokens.Color100 -> Color(0xFF479EF5)
                FluentAliasTokens.BrandColorTokens.Color110 -> Color(0xFF62ABF5)
                FluentAliasTokens.BrandColorTokens.Color120 -> Color(0xFF77B7F7)
                FluentAliasTokens.BrandColorTokens.Color130 -> Color(0xFF96C6FA)
                FluentAliasTokens.BrandColorTokens.Color140 -> Color(0xFFB4D6FA)
                FluentAliasTokens.BrandColorTokens.Color150 -> Color(0xFFCFE4FA)
                FluentAliasTokens.BrandColorTokens.Color160 -> Color(0xFFEBF3FC)
            }
        }
    }

    @IgnoredOnParcel
    override val neutralBackgroundColor: TokenSet<FluentAliasTokens.NeutralBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.NeutralBackgroundColorTokens.Background1 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Black.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background1Pressed ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey88.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey18.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background1Selected ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey92.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey14.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background2 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey12.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background2Pressed ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey88.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey30.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background2Selected ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey92.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey26.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background3 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey16.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background3Pressed ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey88.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey34.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background3Selected ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey92.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey30.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background4 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey98.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey20.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background4Pressed ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey86.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey38.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background4Selected ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey90.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey34.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background5 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey94.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey24.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background5Pressed ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey82.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey42.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background5Selected ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey86.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey38.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Background6 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey82.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey36.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.CanvasBackground ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey96.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey8.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundLightStatic ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.White.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundLightStaticDisabled ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey68.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundDarkStatic ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey14.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey24.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundInverted ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey46.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey72.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundDisabled ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey88.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey32.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Stencil1 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey90.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey34.value
                    )

                FluentAliasTokens.NeutralBackgroundColorTokens.Stencil2 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey98.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey20.value
                    )
            }
        }
    }


    @IgnoredOnParcel
    override val neutralForegroundColor: TokenSet<FluentAliasTokens.NeutralForegroundColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.NeutralForegroundColorTokens.Foreground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey14.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.White.value
                    )
                FluentAliasTokens.NeutralForegroundColorTokens.Foreground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey38.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey84.value
                    )
                FluentAliasTokens.NeutralForegroundColorTokens.Foreground3 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey50.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey68.value
                    )
                FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey74.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey36.value
                    )
                FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable2 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey18.value
                    )
                FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Black.value
                    )
                FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDarkStatic ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Black.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Black.value
                    )
                FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.White.value
                    )
            }
        }
    }


    @IgnoredOnParcel
    override val neutralStrokeColor: TokenSet<FluentAliasTokens.NeutralStrokeColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.NeutralStrokeColorTokens.Stroke1 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey82.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey30.value
                    )

                FluentAliasTokens.NeutralStrokeColorTokens.Stroke2 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey88.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey24.value
                    )

                FluentAliasTokens.NeutralStrokeColorTokens.StrokeDisabled ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey88.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey26.value
                    )

                FluentAliasTokens.NeutralStrokeColorTokens.StrokeAccessible ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Grey38.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Grey62.value
                    )

                FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus1 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.White.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.Black.value
                    )

                FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus2 ->
                    FluentColor(
                        light = FluentGlobalTokens.NeutralColorTokens.Black.value,
                        dark = FluentGlobalTokens.NeutralColorTokens.White.value
                    )
            }
        }
    }


    @IgnoredOnParcel
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
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color50],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color120]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color70],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Pressed ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color40],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color50],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground3 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = Color.Unspecified
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color150],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color30]
                    )

                FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color140],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color40]
                    )
            }
        }
    }


    @IgnoredOnParcel
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
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color50],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color120]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled1 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color90],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color90]
                    )

                FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled2 ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color140],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color40]
                    )
            }
        }
    }

    @IgnoredOnParcel
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
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color50],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color130]
                    )

                FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1Selected ->
                    FluentColor(
                        light = brandColor[FluentAliasTokens.BrandColorTokens.Color60],
                        dark = brandColor[FluentAliasTokens.BrandColorTokens.Color120]
                    )
            }
        }
    }

    @IgnoredOnParcel
    override val errorAndStatusColor: TokenSet<FluentAliasTokens.ErrorAndStatusColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.ErrorAndStatusColorTokens.DangerBackground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Red.tint60,
                        dark = FluentGlobalTokens.SharedColorSets.Red.shade40
                    )

                FluentAliasTokens.ErrorAndStatusColorTokens.DangerBackground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Red.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Red.shade10
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Red.shade10,
                        dark = FluentGlobalTokens.SharedColorSets.Red.tint30
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Red.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Red.tint30
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.SuccessBackground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Green.tint60,
                        dark = FluentGlobalTokens.SharedColorSets.Green.shade40
                    )

                FluentAliasTokens.ErrorAndStatusColorTokens.SuccessBackground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Green.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Green.shade10
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.SuccessForeground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Green.shade10,
                        dark = FluentGlobalTokens.SharedColorSets.Green.tint30
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.SuccessForeground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Green.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Green.tint30
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.WarningBackground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Yellow.tint60,
                        dark = FluentGlobalTokens.SharedColorSets.Yellow.shade40
                    )

                FluentAliasTokens.ErrorAndStatusColorTokens.WarningBackground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Yellow.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Yellow.shade10
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.WarningForeground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Yellow.shade30,
                        dark = FluentGlobalTokens.SharedColorSets.Yellow.tint30
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.WarningForeground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Yellow.shade30,
                        dark = FluentGlobalTokens.SharedColorSets.Yellow.tint30
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.SevereBackground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Orange.tint60,
                        dark = FluentGlobalTokens.SharedColorSets.Orange.shade40
                    )

                FluentAliasTokens.ErrorAndStatusColorTokens.SevereBackground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Orange.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Orange.shade10
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.SevereForeground1 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Orange.shade10,
                        dark = FluentGlobalTokens.SharedColorSets.Orange.tint30
                    )
                FluentAliasTokens.ErrorAndStatusColorTokens.SevereForeground2 ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Orange.shade20,
                        dark = FluentGlobalTokens.SharedColorSets.Orange.tint30
                    )
            }
        }
    }

    @IgnoredOnParcel
    override val presenceColor: TokenSet<FluentAliasTokens.PresenceColorTokens, FluentColor> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.PresenceColorTokens.Away ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Marigold.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Marigold.primary
                    )
                FluentAliasTokens.PresenceColorTokens.Busy, FluentAliasTokens.PresenceColorTokens.DND ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Red.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Red.tint10
                    )
                FluentAliasTokens.PresenceColorTokens.Available ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Green.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Green.tint20
                    )
                FluentAliasTokens.PresenceColorTokens.OutOfOffice ->
                    FluentColor(
                        light = FluentGlobalTokens.SharedColorSets.Berry.primary,
                        dark = FluentGlobalTokens.SharedColorSets.Berry.tint20
                    )
            }
        }
    }


    @IgnoredOnParcel
    override val typography: TokenSet<FluentAliasTokens.TypographyTokens, TextStyle> by lazy {
        TokenSet { token ->
            when (token) {
                FluentAliasTokens.TypographyTokens.Display ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size900.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size900.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value,
                        letterSpacing = (-0.5).sp
                    )
                FluentAliasTokens.TypographyTokens.LargeTitle ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size800.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size800.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value,
                        letterSpacing = (-0.25).sp
                    )
                FluentAliasTokens.TypographyTokens.Title1 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size700.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size700.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Bold.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Title2 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size600.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size600.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Medium.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Title3 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size500.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size500.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Medium.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Body1Strong ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size400.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size400.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.SemiBold.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Body1 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size400.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size400.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Body2Strong ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size300.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size300.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Medium.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Body2 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size300.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size300.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Caption1Strong ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size200.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size200.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Medium.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Caption1 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size200.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size200.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value,
                        letterSpacing = 0.sp
                    )
                FluentAliasTokens.TypographyTokens.Caption2 ->
                    TextStyle(
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size100.value,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.Size100.value,
                        fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value,
                        letterSpacing = 0.sp
                    )
            }
        }
    }
}

internal val LocalAliasTokens = compositionLocalOf<IAliasTokens> { AliasTokens() }