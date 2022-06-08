package com.microsoft.fluentui.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.StateBorderStroke
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.ButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.FABInfo
import com.microsoft.fluentui.theme.token.controlTokens.FABTokens
import java.security.InvalidParameterException

@Composable
fun getColorByState(
    stateData: StateColor,
    enabled: Boolean,
    interactionSource: InteractionSource
): Color {
    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return stateData.pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (isFocused)
            return stateData.focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return stateData.focused

        return stateData.rest
    } else
        return stateData.disabled
}

@Composable
fun backgroundColor(
    tokens: ControlTokens,
    info: ControlInfo,
    enabled: Boolean,
    interactionSource: InteractionSource
): Color {
    val backgroundColors: StateColor =
        when (tokens) {
            is ButtonTokens -> tokens.backgroundColor(info as ButtonInfo)
            is FABTokens -> tokens.backgroundColor(info as FABInfo)
            else -> throw InvalidParameterException()
        }

    return getColorByState(backgroundColors, enabled, interactionSource)
}

@Composable
fun iconColor(
    tokens: ControlTokens,
    info: ControlInfo,
    enabled: Boolean,
    interactionSource: InteractionSource
): Color {
    val iconColors: StateColor =
        when (tokens) {
            is ButtonTokens -> tokens.iconColor(info as ButtonInfo)
            is FABTokens -> tokens.iconColor(info as FABInfo)
            else -> throw InvalidParameterException()
        }

    return getColorByState(iconColors, enabled, interactionSource)
}

@Composable
fun textColor(
    tokens: ControlTokens,
    info: ControlInfo,
    enabled: Boolean,
    interactionSource: InteractionSource
): Color {
    val textColors: StateColor =
        when (tokens) {
            is ButtonTokens -> tokens.textColor(info as ButtonInfo)
            is FABTokens -> tokens.textColor(info as FABInfo)
            else -> throw InvalidParameterException()
        }

    return getColorByState(textColors, enabled, interactionSource)
}

@Composable
fun borderStroke(
    tokens: ControlTokens,
    info: ControlInfo,
    enabled: Boolean,
    interactionSource: InteractionSource
): List<BorderStroke> {
    val fetchBorderStroke: StateBorderStroke =
        when (tokens) {
            is ButtonTokens -> tokens.borderStroke(info as ButtonInfo)
            is FABTokens -> tokens.borderStroke(info as FABInfo)
            else -> throw InvalidParameterException()
        }

    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return fetchBorderStroke.pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (isFocused)
            return fetchBorderStroke.focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return fetchBorderStroke.focused

        return fetchBorderStroke.rest
    } else
        return fetchBorderStroke.disabled
}
