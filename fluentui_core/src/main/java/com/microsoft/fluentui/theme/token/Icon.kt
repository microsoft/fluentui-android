package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.ThemeMode

enum class IconType {
    Regular,
    Filled
}

data class IconSize(val size: Dp, val type: IconType)

data class Icon(
        val light: ImageVector = ImageVector.Builder("", 0.dp, 0.dp, 0F, 0F).build(),
        val dark: ImageVector = ImageVector.Builder("", 0.dp, 0.dp, 0F, 0F).build(),
) {
    @Composable
    fun value(themeMode: ThemeMode = com.microsoft.fluentui.theme.FluentTheme.themeMode): ImageVector {
        return when (themeMode) {
            ThemeMode.Light -> light
            ThemeMode.Dark -> dark
            ThemeMode.Auto -> if (isSystemInDarkTheme()) dark else light
            else -> ImageVector.Builder("", 0.dp, 0.dp, 0F, 0F).build()
        }
    }
}