package com.microsoft.fluentui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import com.microsoft.fluentui.theme.token.*

enum class ThemeMode {
    Light,
    Dark,
    Colorful,
    Auto,
    AutoColorful
}

internal val LocalThemeMode = compositionLocalOf { ThemeMode.Auto }

@Composable
fun FluentTheme(
    globalTokens: GlobalTokens = GlobalTokens(),
    aliasTokens: AliasTokens = AliasTokens(),
    themeMode: ThemeMode = ThemeMode.Auto,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalGlobalTokens provides globalTokens,
        LocalAliasTokens provides aliasTokens,
        LocalThemeMode provides themeMode
    ) {
        content()
    }
}

object FluentTheme {

    val globalTokens: GlobalTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalGlobalTokens.current

    val aliasToken: AliasTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalAliasTokens.current

    val tokens: HashMap<ControlType, Any?> = HashMap()

    fun register(type: ControlType, controlTokens: ControlTokens) {
        tokens[type] = controlTokens
    }

    val themeMode: ThemeMode
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeMode.current
}