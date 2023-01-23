package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.ThemeMode

data class FluentIcon(
    val light: ImageVector = ImageVector.Builder("", 0.dp, 0.dp, 0F, 0F).build(),
    val dark: ImageVector = light,
    val contentDescription: String = "",
    val onClick: (() -> Unit)? = null
) {
    @Composable
    fun value(themeMode: ThemeMode = com.microsoft.fluentui.theme.FluentTheme.themeMode): ImageVector {
        return when (themeMode) {
            ThemeMode.Light -> light
            ThemeMode.Dark -> dark
            ThemeMode.Auto -> if (isSystemInDarkTheme()) dark else light
        }
    }

    fun isIconAvailable(): Boolean {
        return (this.light.defaultWidth > 0.dp && this.light.defaultHeight > 0.dp) ||
                (this.dark.defaultWidth > 0.dp && this.dark.defaultHeight > 0.dp)
    }
}