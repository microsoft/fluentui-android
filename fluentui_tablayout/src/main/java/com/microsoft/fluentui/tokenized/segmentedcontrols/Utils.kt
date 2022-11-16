package com.microsoft.fluentui.tokenized.segmentedcontrols

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
fun getColorByState(
    stateData: StateColor,
    enabled: Boolean,
    selected: Boolean,
    interactionSource: InteractionSource
): Color {
    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (selected && isPressed)
            return stateData.selectedPressed
        else if (isPressed)
            return stateData.pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (selected && isFocused)
            return stateData.selectedFocused
        else if (isFocused)
            return stateData.focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (selected && isHovered)
            return stateData.selectedFocused
        if (isHovered)
            return stateData.focused

        if(selected)
            return stateData.selected

        return stateData.rest
    }
    else if (selected)
        return stateData.selectedDisabled
    else
        return stateData.disabled
}

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

    return getColorByState(backgroundColors, enabled, selected, interactionSource)
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

    return getColorByState(iconColors, enabled, selected, interactionSource)
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

    return getColorByState(textColors, enabled, selected, interactionSource)
}
