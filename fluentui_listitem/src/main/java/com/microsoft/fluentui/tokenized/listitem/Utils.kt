package com.microsoft.fluentui.listitem

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.StateColor

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
            return stateData.pressed

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return stateData.pressed

        return stateData.rest
    } else
        return stateData.disabled
}

data class ChevronTransition (val enterTransition: Float = 0f, val exitTransition: Float = 0f)