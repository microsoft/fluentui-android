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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import com.microsoft.fluentui.theme.ThemeMode

private val unspecifiedColor = Color.Unspecified
private val unspecifiedBrush = SolidColor(Color.Unspecified)

class StateColor(
    val rest: Color = unspecifiedColor,
    val pressed: Color = unspecifiedColor,
    val selected: Color = unspecifiedColor,
    val focused: Color = unspecifiedColor,
    val selectedPressed: Color = unspecifiedColor,
    val selectedFocused: Color = unspecifiedColor,
    val selectedDisabled: Color = unspecifiedColor,
    val disabled: Color = unspecifiedColor
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

class StateBrush(
    val rest: Brush = unspecifiedBrush,
    val pressed: Brush = unspecifiedBrush,
    val selected: Brush = unspecifiedBrush,
    val focused: Brush = unspecifiedBrush,
    val selectedPressed: Brush = unspecifiedBrush,
    val selectedFocused: Brush = unspecifiedBrush,
    val selectedDisabled: Brush = unspecifiedBrush,
    val disabled: Brush = unspecifiedBrush,
) {

    @Composable
    fun getBrushByState(
        enabled: Boolean,
        selected: Boolean,
        interactionSource: InteractionSource
    ): Brush {
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

data class FluentBrush(
    val light: Brush,
    val dark: Brush = light,
) {

    @Composable
    fun value(themeMode: ThemeMode = com.microsoft.fluentui.theme.FluentTheme.themeMode): Brush {
        return when (themeMode) {
            ThemeMode.Light -> light
            ThemeMode.Dark -> dark
            ThemeMode.Auto -> if (isSystemInDarkTheme()) dark else light
        }
    }
}