package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasToken
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.StateColor
import kotlinx.parcelize.Parcelize

data class ToggleSwitchInfo(
        val checked: Boolean = true,
) : ControlInfo

@Parcelize
open class ToggleSwitchTokens : ControlToken, Parcelable {

    companion object {
        const val Type: String = "ToggleSwitch"
    }

    @Composable
    open fun TrackColor(switchInfo: ToggleSwitchInfo): StateColor {
        return when (switchInfo.checked) {
            true -> StateColor(
                    rest = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            themeMode = themeMode
                    ),
                    pressed = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            themeMode = themeMode
                    ),
                    disabled = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundDisabled].value(
                            themeMode = themeMode
                    )
            )
            false -> StateColor(
                    rest = aliasToken.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                            themeMode = themeMode
                    ),
                    pressed = aliasToken.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                            themeMode = themeMode
                    ),
                    disabled = aliasToken.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                            themeMode = themeMode
                    )
            )
        }
    }

    @Composable
    open fun KnobColor(switchInfo: ToggleSwitchInfo): StateColor {
        return when (switchInfo.checked) {
            true -> StateColor(
                    rest = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundInverted].value(
                            themeMode = themeMode
                    ),
                    pressed = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundInverted].value(
                            themeMode = themeMode
                    ),
                    disabled = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundInvertedDisabled].value(
                            themeMode = themeMode
                    )
            )
            false -> StateColor(
                    rest = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundInverted].value(
                            themeMode = themeMode
                    ),
                    pressed = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundInverted].value(
                            themeMode = themeMode
                    ),
                    disabled = aliasToken.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundInvertedDisabled].value(
                            themeMode = themeMode
                    )
            )
        }
    }

    open val fixedTrackHeight = 32.dp
    open val fixedTrackWidth = 52.dp
    open val fixedKnobDiameter = 26.dp
    open val knobRippleRadius = 24.dp
    open val paddingTrack = 3.dp
}