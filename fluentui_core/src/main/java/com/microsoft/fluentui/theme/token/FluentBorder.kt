package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class StateBorderStroke(
    val rest: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
    val pressed: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
    val selected: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
    val selectedPressed: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
    val selectedFocused: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
    val selectedDisabled: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
    val focused: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
    val disabled: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
){
    @Composable
    fun getColorByState(
        enabled: Boolean,
        selected: Boolean,
        interactionSource: InteractionSource
    ): List<BorderStroke> {
        if (enabled) {
            val isPressed by interactionSource.collectIsPressedAsState()
            if (selected && isPressed)
                return this.selectedPressed
            else if (isPressed)
                return this.pressed

            val isFocused by interactionSource.collectIsFocusedAsState()
            if (selected && isFocused)
                return this.selectedFocused
            else if (isFocused)
                return this.focused

            val isHovered by interactionSource.collectIsHoveredAsState()
            if (selected && isHovered)
                return this.selectedFocused
            if (isHovered)
                return this.focused

            if (selected)
                return this.selected

            return this.rest
        } else if (selected)
            return this.selectedDisabled
        else
            return this.disabled
    }
}