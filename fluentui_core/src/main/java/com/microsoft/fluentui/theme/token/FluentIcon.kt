package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.ThemeMode

data class FluentIcon(
    val light: ImageVector = ImageVector.Builder("", 0.dp, 0.dp, 0F, 0F).build(),
    val dark: ImageVector = light,
    val contentDescription: String? = null,
    val onClick: (() -> Unit)? = null,
    val tint: Color? = null
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

@Composable
fun Icon(
    icon: FluentIcon,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified
) {
    Icon(
        icon.value(),
        icon.contentDescription,
        modifier,
        tint = icon.tint ?: tint
    )
}