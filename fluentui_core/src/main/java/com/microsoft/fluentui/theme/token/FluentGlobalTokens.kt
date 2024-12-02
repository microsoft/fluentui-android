//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Global Tokens represent a unified set of constants to be used by Fluent UI.

object FluentGlobalTokens {

    /**
     * The set of neutral colors used by Fluent UI.
     * @param value The color value.
     */
    enum class NeutralColorTokens(val value: Color) {
        Black(Color(0xFF000000)),
        Grey2(Color(0xFF050505)),
        Grey4(Color(0xFF0A0A0A)),
        Grey6(Color(0xFF0F0F0F)),
        Grey8(Color(0xFF141414)),
        Grey10(Color(0xFF1A1A1A)),
        Grey12(Color(0xFF1F1F1F)),
        Grey14(Color(0xFF242424)),
        Grey16(Color(0xFF292929)),
        Grey18(Color(0xFF2E2E2E)),
        Grey20(Color(0xFF333333)),
        Grey22(Color(0xFF383838)),
        Grey24(Color(0xFF3D3D3D)),
        Grey26(Color(0xFF424242)),
        Grey28(Color(0xFF474747)),
        Grey30(Color(0xFF4D4D4D)),
        Grey32(Color(0xFF525252)),
        Grey34(Color(0xFF575757)),
        Grey36(Color(0xFF5C5C5C)),
        Grey38(Color(0xFF616161)),
        Grey40(Color(0xFF666666)),
        Grey42(Color(0xFF6B6B6B)),
        Grey44(Color(0xFF707070)),
        Grey46(Color(0xFF757575)),
        Grey48(Color(0xFF7A7A7A)),
        Grey50(Color(0xFF808080)),
        Grey52(Color(0xFF858585)),
        Grey54(Color(0xFF8A8A8A)),
        Grey56(Color(0xFF8F8F8F)),
        Grey58(Color(0xFF949494)),
        Grey60(Color(0xFF999999)),
        Grey62(Color(0xFF9E9E9E)),
        Grey64(Color(0xFFA3A3A3)),
        Grey66(Color(0xFFA8A8A8)),
        Grey68(Color(0xFFADADAD)),
        Grey70(Color(0xFFB3B3B3)),
        Grey72(Color(0xFFB8B8B8)),
        Grey74(Color(0xFFBDBDBD)),
        Grey76(Color(0xFFC2C2C2)),
        Grey78(Color(0xFFC7C7C7)),
        Grey80(Color(0xFFCCCCCC)),
        Grey82(Color(0xFFD1D1D1)),
        Grey84(Color(0xFFD6D6D6)),
        Grey86(Color(0xFFDBDBDB)),
        Grey88(Color(0xFFE0E0E0)),
        Grey90(Color(0xFFE6E6E6)),
        Grey92(Color(0xFFEBEBEB)),
        Grey94(Color(0xFFF0F0F0)),
        Grey96(Color(0xFFF5F5F5)),
        Grey98(Color(0xFFFAFAFA)),
        White(Color(0xFFFFFFFF)),
    }

    /**
     * The set of neutral colors used by Fluent UI.
     * Maintained for backwards compatibility.
     * @param token [NeutralColorTokens]
     * @return [Color]
     */
    @Deprecated(
        "Use the property syntax on NeutralColorTokens instead e.g. NeutralColorTokens.Gray90.value ",
        ReplaceWith("token.value"))
    fun neutralColor(token: NeutralColorTokens): Color = token.value

    enum class FontSizeTokens(val value: TextUnit) {
        Size100(12.0.sp),
        Size200(13.0.sp),
        Size300(14.0.sp),
        Size400(16.0.sp),
        Size500(18.0.sp),
        Size600(20.0.sp),
        Size700(24.0.sp),
        Size800(34.0.sp),
        Size900(60.0.sp)
    }

    /**
     * The set of font sizes used by Fluent UI.
     * Maintained for backwards compatibility.
     * @param token [FontSizeTokens]
     * @return [TextUnit]
     */
    @Deprecated(
        "Use the property syntax on FontSizeTokens instead e.g. FontSizeTokens.Size100.value ",
        ReplaceWith("token.value"))
    fun fontSize(token: FontSizeTokens): TextUnit = token.value

    enum class LineHeightTokens(val value: TextUnit) {
        Size100(16.0.sp),
        Size200(16.0.sp),
        Size300(20.0.sp),
        Size400(24.0.sp),
        Size500(24.0.sp),
        Size600(24.0.sp),
        Size700(32.0.sp),
        Size800(44.0.sp),
        Size900(72.0.sp)
    }

    /**
     * The set of line heights used by Fluent UI.
     * Maintained for backwards compatibility.
     * @param token [LineHeightTokens]
     * @return [TextUnit]
     */
    @Deprecated(
        "Use the property syntax on LineHeightTokens instead e.g. LineHeightTokens.Size100.value ",
        ReplaceWith("token.value"))
    fun lineHeight(token: LineHeightTokens): TextUnit = token.value

    enum class FontWeightTokens(val value: FontWeight) {
        Regular(FontWeight(400)),
        Medium(FontWeight(500)),
        SemiBold(FontWeight(600)),
        Bold(FontWeight(700))
    }

    /**
     * The set of font weights used by Fluent UI.
     * @param token [FontWeightTokens]
     * @return [FontWeight]
     */
    @Deprecated("Use the property syntax on FontWeightTokens instead e.g. FontWeightTokens.Regular.value ",
        ReplaceWith("token.value"))
    fun fontWeight(token: FontWeightTokens): FontWeight = token.value

    enum class IconSizeTokens(val value: Dp) {
        IconSize100(10.dp),
        IconSize120(12.dp),
        IconSize160(16.dp),
        IconSize200(20.dp),
        IconSize240(24.dp),
        IconSize280(28.dp),
        IconSize360(36.dp),
        IconSize400(40.dp),
        IconSize480(48.dp)
    }

    /**
     * The set of icon sizes used by Fluent UI.
     * @param token [IconSizeTokens]
     * @return [Dp]
     */
    @Deprecated("Use the property syntax on IconSizeTokens instead e.g. IconSizeTokens.IconSize100.value ",
        ReplaceWith("token.value"))
    fun iconSize(token: IconSizeTokens): Dp = token.value

    enum class SizeTokens(val value: Dp) {
        SizeNone(0.dp),
        Size20(2.dp),
        Size40(4.dp),
        Size60(6.dp),
        Size80(8.dp),
        Size100(10.dp),
        Size120(12.dp),
        Size140(14.dp),
        Size160(16.dp),
        Size180(18.dp),
        Size200(20.dp),
        Size240(24.dp),
        Size280(28.dp),
        Size320(32.dp),
        Size360(36.dp),
        Size400(40.dp),
        Size480(48.dp),
        Size520(52.dp),
        Size560(56.dp)
    }

    /**
     * The set of sizes used by Fluent UI.
     * @param token [SizeTokens]
     * @return [Dp]
     */
    @Deprecated("Use the property syntax on SizeTokens instead e.g. SizeTokens.SizeNone.value ",
        ReplaceWith("token.value"))
    fun size(token: SizeTokens): Dp = token.value

    enum class ShadowTokens(val value: Dp) {
        Shadow00(0.dp),
        Shadow02(2.dp),
        Shadow04(4.dp),
        Shadow08(8.dp),
        Shadow16(16.dp),
        Shadow28(28.dp),
        Shadow64(40.dp)
    }

    /**
     * The set of shadows used by Fluent UI.
     * @param token [ShadowTokens]
     * @return [Dp]
     */
    @Deprecated("Use the property syntax on ShadowTokens instead e.g. ShadowTokens.Shadow00.value ",
        ReplaceWith("token.value"))
    fun elevation(token: ShadowTokens): Dp = token.value

    enum class CornerRadiusTokens(val value: Dp) {
        CornerRadiusNone(0.dp),
        CornerRadius20(2.dp),
        CornerRadius40(4.dp),
        CornerRadius80(8.dp),
        CornerRadius120(12.dp),
        CornerRadius160(16.dp),
        CornerRadiusCircle(9999.dp)
    }

    /**
     * The set of corner radii used by Fluent UI.
     * @param token [CornerRadiusTokens]
     * @return [Dp]
     */
    @Deprecated("Use the property syntax on CornerRadiusTokens instead e.g. CornerRadiusTokens.CornerRadiusNone.value ",
        ReplaceWith("token.value"))
    fun cornerRadius(token: CornerRadiusTokens): Dp = token.value

    enum class StrokeWidthTokens(val value: Dp) {
        StrokeWidthNone(0.dp),
        StrokeWidth05(0.5.dp),
        StrokeWidth10(1.dp),
        StrokeWidth15(1.5.dp),
        StrokeWidth20(2.dp),
        StrokeWidth30(3.dp),
        StrokeWidth40(4.dp),
        StrokeWidth60(6.dp)
    }

    /**
     * The set of stroke widths used by Fluent UI.
     * @param token [StrokeWidthTokens]
     * @return [Dp]
     */
    @Deprecated("Use the property syntax on StrokeWidthTokens instead e.g. StrokeWidthTokens.StrokeWidthNone.value ",
        ReplaceWith("token.value"))
    fun strokeWidth(token: StrokeWidthTokens): Dp = token.value

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

    enum class SharedColorSets(
        val primary: Color,
        val shade10: Color,
        val shade20: Color,
        val shade30: Color,
        val shade40: Color,
        val shade50: Color,
        val tint10: Color,
        val tint20: Color,
        val tint30: Color,
        val tint40: Color,
        val tint50: Color,
        val tint60: Color
    ) {
        Anchor(
            primary = Color(0xFF394146),
            shade10 = Color(0xFF333A3F),
            shade20 = Color(0xFF2B3135),
            shade30 = Color(0xFF202427),
            shade40 = Color(0xFF111315),
            shade50 = Color(0xFF090A0B),
            tint10 = Color(0xFF4D565C),
            tint20 = Color(0xFF626C72),
            tint30 = Color(0xFF808A90),
            tint40 = Color(0xFFBCC3C7),
            tint50 = Color(0xFFDBDFE1),
            tint60 = Color(0xFFF6F7F8),
        ),
        Beige(
            primary = Color(0xFF7A7574),
            shade10 = Color(0xFF6E6968),
            shade20 = Color(0xFF5D5958),
            shade30 = Color(0xFF444241),
            shade40 = Color(0xFF252323),
            shade50 = Color(0xFF141313),
            tint10 = Color(0xFF8A8584),
            tint20 = Color(0xFF9A9594),
            tint30 = Color(0xFFAFABAA),
            tint40 = Color(0xFFD7D4D4),
            tint50 = Color(0xFFEAE8E8),
            tint60 = Color(0xFFFAF9F9),
        ),
        Berry(
            primary = Color(0xFFC239B3),
            shade10 = Color(0xFFAF33A1),
            shade20 = Color(0xFF932B88),
            shade30 = Color(0xFF6D2064),
            shade40 = Color(0xFF3A1136),
            shade50 = Color(0xFF1F091D),
            tint10 = Color(0xFFC94CBC),
            tint20 = Color(0xFFD161C4),
            tint30 = Color(0xFFDA7ED0),
            tint40 = Color(0xFFEDBBE7),
            tint50 = Color(0xFFF5DAF2),
            tint60 = Color(0xFFFDF5FC),
        ),
        Blue(
            primary = Color(0xFF0078D4),
            shade10 = Color(0xFF006CBF),
            shade20 = Color(0xFF005BA1),
            shade30 = Color(0xFF004377),
            shade40 = Color(0xFF002440),
            shade50 = Color(0xFF001322),
            tint10 = Color(0xFF1A86D9),
            tint20 = Color(0xFF3595DE),
            tint30 = Color(0xFF5CAAE5),
            tint40 = Color(0xFFA9D3F2),
            tint50 = Color(0xFFD0E7F8),
            tint60 = Color(0xFFF3F9FD),
        ),
        Brass(
            primary = Color(0xFF986F0B),
            shade10 = Color(0xFF89640A),
            shade20 = Color(0xFF745408),
            shade30 = Color(0xFF553E06),
            shade40 = Color(0xFF2E2103),
            shade50 = Color(0xFF181202),
            tint10 = Color(0xFFA47D1E),
            tint20 = Color(0xFFB18C34),
            tint30 = Color(0xFFC1A256),
            tint40 = Color(0xFFE0CEA2),
            tint50 = Color(0xFFEFE4CB),
            tint60 = Color(0xFFFBF8F2),
        ),
        Bronze(
            primary = Color(0xFFA74109),
            shade10 = Color(0xFF963A08),
            shade20 = Color(0xFF7F3107),
            shade30 = Color(0xFF5E2405),
            shade40 = Color(0xFF321303),
            shade50 = Color(0xFF1B0A01),
            tint10 = Color(0xFFB2521E),
            tint20 = Color(0xFFBC6535),
            tint30 = Color(0xFFCA8057),
            tint40 = Color(0xFFE5BBA4),
            tint50 = Color(0xFFF1D9CC),
            tint60 = Color(0xFFFBF5F2),
        ),
        Brown(
            primary = Color(0xFF8E562E),
            shade10 = Color(0xFF804D29),
            shade20 = Color(0xFF6C4123),
            shade30 = Color(0xFF50301A),
            shade40 = Color(0xFF2B1A0E),
            shade50 = Color(0xFF170E07),
            tint10 = Color(0xFF9C663F),
            tint20 = Color(0xFFA97652),
            tint30 = Color(0xFFBB8F6F),
            tint40 = Color(0xFFDDC3B0),
            tint50 = Color(0xFFEDDED3),
            tint60 = Color(0xFFFAF7F4),
        ),
        Burgundy(
            primary = Color(0xFFA4262C),
            shade10 = Color(0xFF942228),
            shade20 = Color(0xFF7D1D21),
            shade30 = Color(0xFF5C1519),
            shade40 = Color(0xFF310B0D),
            shade50 = Color(0xFF1A0607),
            tint10 = Color(0xFFAF393E),
            tint20 = Color(0xFFBA4D52),
            tint30 = Color(0xFFC86C70),
            tint40 = Color(0xFFE4AFB2),
            tint50 = Color(0xFFF0D3D4),
            tint60 = Color(0xFFFBF4F4),
        ),
        Charcoal(
            primary = Color(0xFF393939),
            shade10 = Color(0xFF333333),
            shade20 = Color(0xFF2B2B2B),
            shade30 = Color(0xFF202020),
            shade40 = Color(0xFF111111),
            shade50 = Color(0xFF090909),
            tint10 = Color(0xFF515151),
            tint20 = Color(0xFF686868),
            tint30 = Color(0xFF888888),
            tint40 = Color(0xFFC4C4C4),
            tint50 = Color(0xFFDFDFDF),
            tint60 = Color(0xFFF7F7F7),
        ),
        Cornflower(
            primary = Color(0xFF4F6BED),
            shade10 = Color(0xFF4760D5),
            shade20 = Color(0xFF3C51B4),
            shade30 = Color(0xFF2C3C85),
            shade40 = Color(0xFF182047),
            shade50 = Color(0xFF0D1126),
            tint10 = Color(0xFF637CEF),
            tint20 = Color(0xFF778DF1),
            tint30 = Color(0xFF93A4F4),
            tint40 = Color(0xFFC8D1FA),
            tint50 = Color(0xFFE1E6FC),
            tint60 = Color(0xFFF7F9FE),
        ),
        Cranberry(
            primary = Color(0xFFC50F1F),
            shade10 = Color(0xFFB10E1C),
            shade20 = Color(0xFF960B18),
            shade30 = Color(0xFF6E0811),
            shade40 = Color(0xFF3B0509),
            shade50 = Color(0xFF200205),
            tint10 = Color(0xFFCC2635),
            tint20 = Color(0xFFD33F4C),
            tint30 = Color(0xFFDC626D),
            tint40 = Color(0xFFEEACB2),
            tint50 = Color(0xFFF6D1D5),
            tint60 = Color(0xFFFDF3F4),
        ),
        Cyan(
            primary = Color(0xFF0099BC),
            shade10 = Color(0xFF008AA9),
            shade20 = Color(0xFF00748F),
            shade30 = Color(0xFF005669),
            shade40 = Color(0xFF002E38),
            shade50 = Color(0xFF00181E),
            tint10 = Color(0xFF18A4C4),
            tint20 = Color(0xFF31AFCC),
            tint30 = Color(0xFF56BFD7),
            tint40 = Color(0xFFA4DEEB),
            tint50 = Color(0xFFCDEDF4),
            tint60 = Color(0xFFF2FAFC),
        ),
        DarkBlue(
            primary = Color(0xFF003966),
            shade10 = Color(0xFF00335C),
            shade20 = Color(0xFF002B4E),
            shade30 = Color(0xFF002039),
            shade40 = Color(0xFF00111F),
            shade50 = Color(0xFF000910),
            tint10 = Color(0xFF0E4A78),
            tint20 = Color(0xFF215C8B),
            tint30 = Color(0xFF4178A3),
            tint40 = Color(0xFF92B5D1),
            tint50 = Color(0xFFC2D6E7),
            tint60 = Color(0xFFEFF4F9),
        ),
        DarkBrown(
            primary = Color(0xFF4D291C),
            shade10 = Color(0xFF452519),
            shade20 = Color(0xFF3A1F15),
            shade30 = Color(0xFF2B1710),
            shade40 = Color(0xFF170C08),
            shade50 = Color(0xFF0C0704),
            tint10 = Color(0xFF623A2B),
            tint20 = Color(0xFF784D3E),
            tint30 = Color(0xFF946B5C),
            tint40 = Color(0xFFCAADA3),
            tint50 = Color(0xFFE3D2CB),
            tint60 = Color(0xFFF8F3F2),
        ),
        DarkGreen(
            primary = Color(0xFF0B6A0B),
            shade10 = Color(0xFF0A5F0A),
            shade20 = Color(0xFF085108),
            shade30 = Color(0xFF063B06),
            shade40 = Color(0xFF032003),
            shade50 = Color(0xFF021102),
            tint10 = Color(0xFF1A7C1A),
            tint20 = Color(0xFF2D8E2D),
            tint30 = Color(0xFF4DA64D),
            tint40 = Color(0xFF9AD29A),
            tint50 = Color(0xFFC6E7C6),
            tint60 = Color(0xFFF0F9F0),
        ),
        DarkOrange(
            primary = Color(0xFFDA3B01),
            shade10 = Color(0xFFC43501),
            shade20 = Color(0xFFA62D01),
            shade30 = Color(0xFF7A2101),
            shade40 = Color(0xFF411200),
            shade50 = Color(0xFF230900),
            tint10 = Color(0xFFDE501C),
            tint20 = Color(0xFFE36537),
            tint30 = Color(0xFFE9835E),
            tint40 = Color(0xFFF4BFAB),
            tint50 = Color(0xFFF9DCD1),
            tint60 = Color(0xFFFDF6F3),
        ),
        DarkPurple(
            primary = Color(0xFF401B6C),
            shade10 = Color(0xFF3A1861),
            shade20 = Color(0xFF311552),
            shade30 = Color(0xFF240F3C),
            shade40 = Color(0xFF130820),
            shade50 = Color(0xFF0A0411),
            tint10 = Color(0xFF512B7E),
            tint20 = Color(0xFF633E8F),
            tint30 = Color(0xFF7E5CA7),
            tint40 = Color(0xFFB9A3D3),
            tint50 = Color(0xFFD8CCE7),
            tint60 = Color(0xFFF5F2F9),
        ),
        DarkRed(
            primary = Color(0xFF750B1C),
            shade10 = Color(0xFF690A19),
            shade20 = Color(0xFF590815),
            shade30 = Color(0xFF420610),
            shade40 = Color(0xFF230308),
            shade50 = Color(0xFF130204),
            tint10 = Color(0xFF861B2C),
            tint20 = Color(0xFF962F3F),
            tint30 = Color(0xFFAC4F5E),
            tint40 = Color(0xFFD69CA5),
            tint50 = Color(0xFFE9C7CD),
            tint60 = Color(0xFFF9F0F2),
        ),
        DarkTeal(
            primary = Color(0xFF006666),
            shade10 = Color(0xFF005C5C),
            shade20 = Color(0xFF004E4E),
            shade30 = Color(0xFF003939),
            shade40 = Color(0xFF001F1F),
            shade50 = Color(0xFF001010),
            tint10 = Color(0xFF0E7878),
            tint20 = Color(0xFF218B8B),
            tint30 = Color(0xFF41A3A3),
            tint40 = Color(0xFF92D1D1),
            tint50 = Color(0xFFC2E7E7),
            tint60 = Color(0xFFEFF9F9),
        ),
        Forest(
            primary = Color(0xFF498205),
            shade10 = Color(0xFF427505),
            shade20 = Color(0xFF376304),
            shade30 = Color(0xFF294903),
            shade40 = Color(0xFF162702),
            shade50 = Color(0xFF0C1501),
            tint10 = Color(0xFF599116),
            tint20 = Color(0xFF6BA02B),
            tint30 = Color(0xFF85B44C),
            tint40 = Color(0xFFBDD99B),
            tint50 = Color(0xFFDBEBC7),
            tint60 = Color(0xFFF6FAF0),
        ),
        Gold(
            primary = Color(0xFFC19C00),
            shade10 = Color(0xFFAE8C00),
            shade20 = Color(0xFF937700),
            shade30 = Color(0xFF6C5700),
            shade40 = Color(0xFF3A2F00),
            shade50 = Color(0xFF1F1900),
            tint10 = Color(0xFFC8A718),
            tint20 = Color(0xFFD0B232),
            tint30 = Color(0xFFDAC157),
            tint40 = Color(0xFFECDFA5),
            tint50 = Color(0xFFF5EECE),
            tint60 = Color(0xFFFDFBF2),
        ),
        Grape(
            primary = Color(0xFF881798),
            shade10 = Color(0xFF7A1589),
            shade20 = Color(0xFF671174),
            shade30 = Color(0xFF4C0D55),
            shade40 = Color(0xFF29072E),
            shade50 = Color(0xFF160418),
            tint10 = Color(0xFF952AA4),
            tint20 = Color(0xFFA33FB1),
            tint30 = Color(0xFFB55FC1),
            tint40 = Color(0xFFD9A7E0),
            tint50 = Color(0xFFEACEEF),
            tint60 = Color(0xFFFAF2FB),
        ),
        Green(
            primary = Color(0xFF107C10),
            shade10 = Color(0xFF0E700E),
            shade20 = Color(0xFF0C5E0C),
            shade30 = Color(0xFF094509),
            shade40 = Color(0xFF052505),
            shade50 = Color(0xFF031403),
            tint10 = Color(0xFF218C21),
            tint20 = Color(0xFF359B35),
            tint30 = Color(0xFF54B054),
            tint40 = Color(0xFF9FD89F),
            tint50 = Color(0xFFC9EAC9),
            tint60 = Color(0xFFF1FAF1),
        ),
        HotPink(
            primary = Color(0xFFE3008C),
            shade10 = Color(0xFFCC007E),
            shade20 = Color(0xFFAD006A),
            shade30 = Color(0xFF7F004E),
            shade40 = Color(0xFF44002A),
            shade50 = Color(0xFF240016),
            tint10 = Color(0xFFE61C99),
            tint20 = Color(0xFFEA38A6),
            tint30 = Color(0xFFEE5FB7),
            tint40 = Color(0xFFF7ADDA),
            tint50 = Color(0xFFFBD2EB),
            tint60 = Color(0xFFFEF4FA),
        ),
        Lavender(
            primary = Color(0xFF7160E8),
            shade10 = Color(0xFF6656D1),
            shade20 = Color(0xFF5649B0),
            shade30 = Color(0xFF3F3682),
            shade40 = Color(0xFF221D46),
            shade50 = Color(0xFF120F25),
            tint10 = Color(0xFF8172EB),
            tint20 = Color(0xFF9184EE),
            tint30 = Color(0xFFA79CF1),
            tint40 = Color(0xFFD2CCF8),
            tint50 = Color(0xFFE7E4FB),
            tint60 = Color(0xFFF9F8FE),
        ),
        LightBlue(
            primary = Color(0xFF3A96DD),
            shade10 = Color(0xFF3487C7),
            shade20 = Color(0xFF2C72A8),
            shade30 = Color(0xFF20547C),
            shade40 = Color(0xFF112D42),
            shade50 = Color(0xFF091823),
            tint10 = Color(0xFF4FA1E1),
            tint20 = Color(0xFF65ADE5),
            tint30 = Color(0xFF83BDEB),
            tint40 = Color(0xFFBFDDF5),
            tint50 = Color(0xFFDCEDFA),
            tint60 = Color(0xFFF6FAFE),
        ),
        LightGreen(
            primary = Color(0xFF13A10E),
            shade10 = Color(0xFF11910D),
            shade20 = Color(0xFF0E7A0B),
            shade30 = Color(0xFF0B5A08),
            shade40 = Color(0xFF063004),
            shade50 = Color(0xFF031A02),
            tint10 = Color(0xFF27AC22),
            tint20 = Color(0xFF3DB838),
            tint30 = Color(0xFF5EC75A),
            tint40 = Color(0xFFA7E3A5),
            tint50 = Color(0xFFCEF0CD),
            tint60 = Color(0xFFF2FBF2),
        ),
        LightTeal(
            primary = Color(0xFF00B7C3),
            shade10 = Color(0xFF00A5AF),
            shade20 = Color(0xFF008B94),
            shade30 = Color(0xFF00666D),
            shade40 = Color(0xFF00373A),
            shade50 = Color(0xFF001D1F),
            tint10 = Color(0xFF18BFCA),
            tint20 = Color(0xFF32C8D1),
            tint30 = Color(0xFF58D3DB),
            tint40 = Color(0xFFA6E9ED),
            tint50 = Color(0xFFCEF3F5),
            tint60 = Color(0xFFF2FCFD),
        ),
        Lilac(
            primary = Color(0xFFB146C2),
            shade10 = Color(0xFF9F3FAF),
            shade20 = Color(0xFF863593),
            shade30 = Color(0xFF63276D),
            shade40 = Color(0xFF35153A),
            shade50 = Color(0xFF1C0B1F),
            tint10 = Color(0xFFBA58C9),
            tint20 = Color(0xFFC36BD1),
            tint30 = Color(0xFFCF87DA),
            tint40 = Color(0xFFE6BFED),
            tint50 = Color(0xFFF2DCF5),
            tint60 = Color(0xFFFCF6FD),
        ),
        Lime(
            primary = Color(0xFF73AA24),
            shade10 = Color(0xFF689920),
            shade20 = Color(0xFF57811B),
            shade30 = Color(0xFF405F14),
            shade40 = Color(0xFF23330B),
            shade50 = Color(0xFF121B06),
            tint10 = Color(0xFF81B437),
            tint20 = Color(0xFF90BE4C),
            tint30 = Color(0xFFA4CC6C),
            tint40 = Color(0xFFCFE5AF),
            tint50 = Color(0xFFE5F1D3),
            tint60 = Color(0xFFF8FCF4),
        ),
        Magenta(
            primary = Color(0xFFBF0077),
            shade10 = Color(0xFFAC006B),
            shade20 = Color(0xFF91005A),
            shade30 = Color(0xFF6B0043),
            shade40 = Color(0xFF390024),
            shade50 = Color(0xFF1F0013),
            tint10 = Color(0xFFC71885),
            tint20 = Color(0xFFCE3293),
            tint30 = Color(0xFFD957A8),
            tint40 = Color(0xFFECA5D1),
            tint50 = Color(0xFFF5CEE6),
            tint60 = Color(0xFFFCF2F9),
        ),
        Marigold(
            primary = Color(0xFFEAA300),
            shade10 = Color(0xFFD39300),
            shade20 = Color(0xFFB27C00),
            shade30 = Color(0xFF835B00),
            shade40 = Color(0xFF463100),
            shade50 = Color(0xFF251A00),
            tint10 = Color(0xFFEDAD1C),
            tint20 = Color(0xFFEFB839),
            tint30 = Color(0xFFF2C661),
            tint40 = Color(0xFFF9E2AE),
            tint50 = Color(0xFFFCEFD3),
            tint60 = Color(0xFFFEFBF4),
        ),
        Mink(
            primary = Color(0xFF5D5A58),
            shade10 = Color(0xFF54514F),
            shade20 = Color(0xFF474443),
            shade30 = Color(0xFF343231),
            shade40 = Color(0xFF1C1B1A),
            shade50 = Color(0xFF0F0E0E),
            tint10 = Color(0xFF706D6B),
            tint20 = Color(0xFF84817E),
            tint30 = Color(0xFF9E9B99),
            tint40 = Color(0xFFCECCCB),
            tint50 = Color(0xFFE5E4E3),
            tint60 = Color(0xFFF8F8F8),
        ),
        Navy(
            primary = Color(0xFF0027B4),
            shade10 = Color(0xFF0023A2),
            shade20 = Color(0xFF001E89),
            shade30 = Color(0xFF001665),
            shade40 = Color(0xFF000C36),
            shade50 = Color(0xFF00061D),
            tint10 = Color(0xFF173BBD),
            tint20 = Color(0xFF3050C6),
            tint30 = Color(0xFF546FD2),
            tint40 = Color(0xFFA3B2E8),
            tint50 = Color(0xFFCCD5F3),
            tint60 = Color(0xFFF2F4FC),
        ),
        Orange(
            primary = Color(0xFFF7630C),
            shade10 = Color(0xFFDE590B),
            shade20 = Color(0xFFBC4B09),
            shade30 = Color(0xFF8A3707),
            shade40 = Color(0xFF4A1E04),
            shade50 = Color(0xFF271002),
            tint10 = Color(0xFFF87528),
            tint20 = Color(0xFFF98845),
            tint30 = Color(0xFFFAA06B),
            tint40 = Color(0xFFFDCFB4),
            tint50 = Color(0xFFFEE5D7),
            tint60 = Color(0xFFFFF9F5),
        ),
        Orchid(
            primary = Color(0xFF8764B8),
            shade10 = Color(0xFF795AA6),
            shade20 = Color(0xFF674C8C),
            shade30 = Color(0xFF4C3867),
            shade40 = Color(0xFF281E37),
            shade50 = Color(0xFF16101D),
            tint10 = Color(0xFF9373C0),
            tint20 = Color(0xFFA083C9),
            tint30 = Color(0xFFB29AD4),
            tint40 = Color(0xFFD7CAEA),
            tint50 = Color(0xFFE9E2F4),
            tint60 = Color(0xFFF9F8FC),
        ),
        Peach(
            primary = Color(0xFFFF8C00),
            shade10 = Color(0xFFE67E00),
            shade20 = Color(0xFFC26A00),
            shade30 = Color(0xFF8F4E00),
            shade40 = Color(0xFF4D2A00),
            shade50 = Color(0xFF291600),
            tint10 = Color(0xFFFF9A1F),
            tint20 = Color(0xFFFFA83D),
            tint30 = Color(0xFFFFBA66),
            tint40 = Color(0xFFFFDDB3),
            tint50 = Color(0xFFFFEDD6),
            tint60 = Color(0xFFFFFAF5),
        ),
        Pink(
            primary = Color(0xFFE43BA6),
            shade10 = Color(0xFFCD3595),
            shade20 = Color(0xFFAD2D7E),
            shade30 = Color(0xFF80215D),
            shade40 = Color(0xFF441232),
            shade50 = Color(0xFF24091B),
            tint10 = Color(0xFFE750B0),
            tint20 = Color(0xFFEA66BA),
            tint30 = Color(0xFFEF85C8),
            tint40 = Color(0xFFF7C0E3),
            tint50 = Color(0xFFFBDDF0),
            tint60 = Color(0xFFFEF6FB),
        ),
        Platinum(
            primary = Color(0xFF69797E),
            shade10 = Color(0xFF5F6D71),
            shade20 = Color(0xFF505C60),
            shade30 = Color(0xFF3B4447),
            shade40 = Color(0xFF1F2426),
            shade50 = Color(0xFF111314),
            tint10 = Color(0xFF79898D),
            tint20 = Color(0xFF89989D),
            tint30 = Color(0xFFA0ADB2),
            tint40 = Color(0xFFCDD6D8),
            tint50 = Color(0xFFE4E9EA),
            tint60 = Color(0xFFF8F9FA),
        ),
        Plum(
            primary = Color(0xFF77004D),
            shade10 = Color(0xFF6B0045),
            shade20 = Color(0xFF5A003B),
            shade30 = Color(0xFF43002B),
            shade40 = Color(0xFF240017),
            shade50 = Color(0xFF13000C),
            tint10 = Color(0xFF87105D),
            tint20 = Color(0xFF98246F),
            tint30 = Color(0xFFAD4589),
            tint40 = Color(0xFFD696C0),
            tint50 = Color(0xFFE9C4DC),
            tint60 = Color(0xFFFAF0F6),
        ),
        Pumpkin(
            primary = Color(0xFFCA5010),
            shade10 = Color(0xFFB6480E),
            shade20 = Color(0xFF9A3D0C),
            shade30 = Color(0xFF712D09),
            shade40 = Color(0xFF3D1805),
            shade50 = Color(0xFF200D03),
            tint10 = Color(0xFFD06228),
            tint20 = Color(0xFFD77440),
            tint30 = Color(0xFFDF8E64),
            tint40 = Color(0xFFEFC4AD),
            tint50 = Color(0xFFF7DFD2),
            tint60 = Color(0xFFFDF7F4),
        ),
        Purple(
            primary = Color(0xFF5C2E91),
            shade10 = Color(0xFF532982),
            shade20 = Color(0xFF46236E),
            shade30 = Color(0xFF341A51),
            shade40 = Color(0xFF1C0E2B),
            shade50 = Color(0xFF0F0717),
            tint10 = Color(0xFF6B3F9E),
            tint20 = Color(0xFF7C52AB),
            tint30 = Color(0xFF9470BD),
            tint40 = Color(0xFFC6B1DE),
            tint50 = Color(0xFFE0D3ED),
            tint60 = Color(0xFFF7F4FB),
        ),
        Red(
            primary = Color(0xFFD13438),
            shade10 = Color(0xFFBC2F32),
            shade20 = Color(0xFF9F282B),
            shade30 = Color(0xFF751D1F),
            shade40 = Color(0xFF3F1011),
            shade50 = Color(0xFF210809),
            tint10 = Color(0xFFD7494C),
            tint20 = Color(0xFFDC5E62),
            tint30 = Color(0xFFE37D80),
            tint40 = Color(0xFFF1BBBC),
            tint50 = Color(0xFFF8DADB),
            tint60 = Color(0xFFFDF6F6),
        ),
        RoyalBlue(
            primary = Color(0xFF004E8C),
            shade10 = Color(0xFF00467E),
            shade20 = Color(0xFF003B6A),
            shade30 = Color(0xFF002C4E),
            shade40 = Color(0xFF00172A),
            shade50 = Color(0xFF000C16),
            tint10 = Color(0xFF125E9A),
            tint20 = Color(0xFF286FA8),
            tint30 = Color(0xFF4A89BA),
            tint40 = Color(0xFF9ABFDC),
            tint50 = Color(0xFFC7DCED),
            tint60 = Color(0xFFF0F6FA),
        ),
        Seafoam(
            primary = Color(0xFF00CC6A),
            shade10 = Color(0xFF00B85F),
            shade20 = Color(0xFF009B51),
            shade30 = Color(0xFF00723B),
            shade40 = Color(0xFF003D20),
            shade50 = Color(0xFF002111),
            tint10 = Color(0xFF19D279),
            tint20 = Color(0xFF34D889),
            tint30 = Color(0xFF5AE0A0),
            tint40 = Color(0xFFA8F0CD),
            tint50 = Color(0xFFCFF7E4),
            tint60 = Color(0xFFF3FDF8),
        ),
        Silver(
            primary = Color(0xFF859599),
            shade10 = Color(0xFF78868A),
            shade20 = Color(0xFF657174),
            shade30 = Color(0xFF4A5356),
            shade40 = Color(0xFF282D2E),
            shade50 = Color(0xFF151818),
            tint10 = Color(0xFF92A1A5),
            tint20 = Color(0xFFA0AEB1),
            tint30 = Color(0xFFB3BFC2),
            tint40 = Color(0xFFD8DFE0),
            tint50 = Color(0xFFEAEEEF),
            tint60 = Color(0xFFFAFBFB),
        ),
        Steel(
            primary = Color(0xFF005B70),
            shade10 = Color(0xFF005265),
            shade20 = Color(0xFF004555),
            shade30 = Color(0xFF00333F),
            shade40 = Color(0xFF001B22),
            shade50 = Color(0xFF000F12),
            tint10 = Color(0xFF0F6C81),
            tint20 = Color(0xFF237D92),
            tint30 = Color(0xFF4496A9),
            tint40 = Color(0xFF94C8D4),
            tint50 = Color(0xFFC3E1E8),
            tint60 = Color(0xFFEFF7F9),
        ),
        Teal(
            primary = Color(0xFF038387),
            shade10 = Color(0xFF037679),
            shade20 = Color(0xFF026467),
            shade30 = Color(0xFF02494C),
            shade40 = Color(0xFF012728),
            shade50 = Color(0xFF001516),
            tint10 = Color(0xFF159195),
            tint20 = Color(0xFF2AA0A4),
            tint30 = Color(0xFF4CB4B7),
            tint40 = Color(0xFF9BD9DB),
            tint50 = Color(0xFFC7EBEC),
            tint60 = Color(0xFFF0FAFA),
        ),
        Yellow(
            primary = Color(0xFFFDE300),
            shade10 = Color(0xFFE4CC00),
            shade20 = Color(0xFFC0AD00),
            shade30 = Color(0xFF817400),
            shade40 = Color(0xFF4C4400),
            shade50 = Color(0xFF282400),
            tint10 = Color(0xFFFDE61E),
            tint20 = Color(0xFFFDEA3D),
            tint30 = Color(0xFFFEEE66),
            tint40 = Color(0xFFFEF7B2),
            tint50 = Color(0xFFFFFAD6),
            tint60 = Color(0xFFFFFEF5),
        )
    }

    /**
     * The shared colors in the design system.
     * @param sharedColorSet the [SharedColorSets]
     * @param token the [SharedColorsTokens]
     * @return the [Color] for the [SharedColorsTokens] in the [SharedColorSets]
     */
    @Deprecated("Use the property syntax on SharedColorSets instead, e.g., SharedColorSets.Anchor.primary")
    fun sharedColors(sharedColorSet: SharedColorSets, token: SharedColorsTokens): Color {
        return when (token) {
            SharedColorsTokens.Primary -> sharedColorSet.primary
            SharedColorsTokens.Shade10 -> sharedColorSet.shade10
            SharedColorsTokens.Shade20 -> sharedColorSet.shade20
            SharedColorsTokens.Shade30 -> sharedColorSet.shade30
            SharedColorsTokens.Shade40 -> sharedColorSet.shade40
            SharedColorsTokens.Shade50 -> sharedColorSet.shade50
            SharedColorsTokens.Tint10 -> sharedColorSet.tint10
            SharedColorsTokens.Tint20 -> sharedColorSet.tint20
            SharedColorsTokens.Tint30 -> sharedColorSet.tint30
            SharedColorsTokens.Tint40 -> sharedColorSet.tint40
            SharedColorsTokens.Tint50 -> sharedColorSet.tint50
            SharedColorsTokens.Tint60 -> sharedColorSet.tint60
        }
    }
}