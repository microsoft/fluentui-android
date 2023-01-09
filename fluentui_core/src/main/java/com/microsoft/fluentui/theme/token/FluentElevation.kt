package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class StateElevation(
    val rest: Dp = 0.dp,
    val pressed: Dp = 0.dp,
    val selected: Dp = 0.dp,
    val selectedPressed: Dp = 0.dp,
    val selectedFocused: Dp = 0.dp,
    val selectedDisabled: Dp = 0.dp,
    val focused: Dp = 0.dp,
    val disabled: Dp = 0.dp,
){
    @Composable
    fun getColorByState(
        enabled: Boolean,
        selected: Boolean,
        interactionSource: InteractionSource
    ): Dp {
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
