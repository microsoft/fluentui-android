//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.ThemeMode

data class StateColor(
    val rest: Color = Color.Unspecified,
    val pressed: Color = Color.Unspecified,
    val selected: Color = Color.Unspecified,
    val focused: Color = Color.Unspecified,
    val disabled: Color = Color.Unspecified,
)

data class FluentColor(
    val light: Color,
    val dark: Color = light,
    var colorful: Color = light
) {

    @Composable
    fun value(themeMode: ThemeMode = com.microsoft.fluentui.theme.FluentTheme.themeMode): Color {
        return when (themeMode) {
            ThemeMode.Light -> light
            ThemeMode.Dark -> dark
            ThemeMode.Colorful -> colorful
            ThemeMode.Auto -> if (isSystemInDarkTheme()) dark else light
            ThemeMode.AutoColorful -> if (isSystemInDarkTheme()) dark else colorful
        }
    }
}