package com.microsoft.fluentui.tokenized.segmentedcontrols

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonTokens
import java.security.InvalidParameterException

@Composable
fun backgroundColor(
    tokens: ControlToken,
    info: ControlInfo,
    enabled: Boolean,
    selected: Boolean,
    interactionSource: InteractionSource
): Color {
    val backgroundColors: StateColor =
        when (tokens) {
            is PillButtonTokens -> tokens.backgroundColor(info as PillButtonInfo)
            else -> throw InvalidParameterException()
        }

    return backgroundColors.getColorByState(enabled, selected, interactionSource)
}

@Composable
fun iconColor(
    tokens: ControlToken,
    info: ControlInfo,
    enabled: Boolean,
    selected: Boolean,
    interactionSource: InteractionSource
): Color {
    val iconColors: StateColor =
        when (tokens) {
            is PillButtonTokens -> tokens.iconColor(info as PillButtonInfo)
            else -> throw InvalidParameterException()
        }

    return iconColors.getColorByState(enabled, selected, interactionSource)
}

@Composable
fun textColor(
    tokens: ControlToken,
    info: ControlInfo,
    enabled: Boolean,
    selected: Boolean,
    interactionSource: InteractionSource
): Color {
    val textColors: StateColor =
        when (tokens) {
            is PillButtonTokens -> tokens.textColor(info as PillButtonInfo)
            else -> throw InvalidParameterException()
        }

    return textColors.getColorByState(enabled, selected, interactionSource)
}
