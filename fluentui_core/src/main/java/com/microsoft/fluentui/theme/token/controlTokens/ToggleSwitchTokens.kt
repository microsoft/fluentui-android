package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

data class ToggleSwitchInfo(
    val checked: Boolean = true,
) : ControlInfo

@Parcelize
open class ToggleSwitchTokens : ControlToken, Parcelable {

    @Composable
    open fun trackColor(switchInfo: ToggleSwitchInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                themeMode = themeMode
            ),
            disabled = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                themeMode = themeMode
            ),
            selected = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            selectedPressed = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                themeMode = themeMode
            ),
            selectedDisabled = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun knobColor(switchInfo: ToggleSwitchInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.BackgroundLightStatic].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.BackgroundLightStatic].value(
                themeMode = themeMode
            ),
            disabled = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.BackgroundLightStaticDisabled].value(
                themeMode = themeMode
            ),
            selected = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.BackgroundLightStatic].value(
                themeMode = themeMode
            ),
            selectedPressed = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.BackgroundLightStatic].value(
                themeMode = themeMode
            ),
            selectedDisabled = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.BackgroundLightStaticDisabled].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun elevation(switchInfo: ToggleSwitchInfo): StateElevation {
        return StateElevation(
            rest = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow08),
            pressed = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow08),
            selected = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow08),
            focused = GlobalTokens.elevation(GlobalTokens.ShadowTokens.Shadow08),
            disabled = 0.dp
        )
    }

    open val fixedTrackHeight = 32.dp
    open val fixedTrackWidth = 52.dp
    open val restKnobDiameter = 26.dp
    open val pressedKnobDiameter = 24.dp
    open val knobRippleRadius = 24.dp
    open val restPaddingTrack = 3.dp
    open val pressedPaddingTrack = 4.dp
}