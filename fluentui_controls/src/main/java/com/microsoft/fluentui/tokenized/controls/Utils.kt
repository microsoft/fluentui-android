package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.controlTokens.*
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
            is ButtonTokens -> tokens.backgroundColor(info as ButtonInfo)
            is FABTokens -> tokens.backgroundColor(info as FABInfo)
            is ToggleSwitchTokens -> tokens.trackColor(info as ToggleSwitchInfo)
            is CheckBoxTokens -> tokens.backgroundColor(info as CheckBoxInfo)
            is RadioButtonTokens -> tokens.backgroundColor(info as RadioButtonInfo)
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
            is ButtonTokens -> tokens.iconColor(info as ButtonInfo)
            is FABTokens -> tokens.iconColor(info as FABInfo)
            is ToggleSwitchTokens -> tokens.knobColor(info as ToggleSwitchInfo)
            is CheckBoxTokens -> tokens.iconColor(info as CheckBoxInfo)
            is RadioButtonTokens -> tokens.iconColor(info as RadioButtonInfo)
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
            is ButtonTokens -> tokens.textColor(info as ButtonInfo)
            is FABTokens -> tokens.textColor(info as FABInfo)
            else -> throw InvalidParameterException()
        }

    return textColors.getColorByState(enabled, selected, interactionSource)
}

@Composable
fun borderStroke(
    tokens: ControlToken,
    info: ControlInfo,
    enabled: Boolean,
    selected: Boolean,
    interactionSource: InteractionSource
): List<BorderStroke> {
    val fetchBorderStroke: StateBorderStroke =
        when (tokens) {
            is ButtonTokens -> tokens.borderStroke(info as ButtonInfo)
            is FABTokens -> tokens.borderStroke(info as FABInfo)
            is CheckBoxTokens -> tokens.borderStroke(info as CheckBoxInfo)
            else -> throw InvalidParameterException()
        }

    return fetchBorderStroke.getColorByState(
        enabled = enabled,
        selected = selected,
        interactionSource = interactionSource
    )
}

@Composable
fun elevation(
    tokens: ControlToken,
    info: ControlInfo,
    enabled: Boolean,
    selected: Boolean,
    interactionSource: InteractionSource
): Dp {
    val elevationState: StateElevation =
        when (tokens) {
            is FABTokens -> tokens.elevation(info as FABInfo)
            is ToggleSwitchTokens -> tokens.elevation(info as ToggleSwitchInfo)
            else -> throw InvalidParameterException()
        }
    return elevationState.getColorByState(
        enabled = enabled,
        selected = selected,
        interactionSource = interactionSource
    )
}
