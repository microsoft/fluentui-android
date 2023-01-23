//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.ThemeMode

data class StateColor(
    val rest: Color = Color.Unspecified,
    val pressed: Color = Color.Unspecified,
    val selected: Color = Color.Unspecified,
    val focused: Color = Color.Unspecified,
    val selectedPressed: Color = Color.Unspecified,
    val selectedFocused: Color = Color.Unspecified,
    val selectedDisabled: Color = Color.Unspecified,
    val disabled: Color = Color.Unspecified
) {
    @Composable
    fun getColorByState(
        enabled: Boolean,
        selected: Boolean,
        interactionSource: InteractionSource
    ): Color {
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

data class FluentColor(
    val light: Color,
    val dark: Color = light,
) {

    @Composable
    fun value(themeMode: ThemeMode = com.microsoft.fluentui.theme.FluentTheme.themeMode): Color {
        return when (themeMode) {
            ThemeMode.Light -> light
            ThemeMode.Dark -> dark
            ThemeMode.Auto -> if (isSystemInDarkTheme()) dark else light
        }
    }
}