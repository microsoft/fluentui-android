package com.microsoft.fluentui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.LocalAliasToken
import com.microsoft.fluentui.theme.token.LocalGlobalTokens

enum class ThemeMode{
    Light,
    Dark,
    Colorful,
    Auto,
    AutoColorful
}

internal val LocalThemeMode = compositionLocalOf { ThemeMode.Auto }

@Composable
fun FluentTheme (globalTokens: GlobalTokens = GlobalTokens(),
                 aliasToken: AliasTokens = AliasTokens(),
                 themeMode: ThemeMode = ThemeMode.Auto,
                 content: @Composable () -> Unit
){
    val rememberedGlobalToken = remember {globalTokens}
    val rememberedAliasToken = remember {aliasToken}

    CompositionLocalProvider(
        LocalGlobalTokens provides rememberedGlobalToken,
        LocalAliasToken provides rememberedAliasToken,
        LocalThemeMode provides themeMode){
        content()
    }
}

object FluentTheme{

    val globalTokens:GlobalTokens
    @Composable
    @ReadOnlyComposable
    get() = LocalGlobalTokens.current

    val aliasToken:AliasTokens
    @Composable
    @ReadOnlyComposable
    get() = LocalAliasToken.current

    val tokens:HashMap<ControlType,Any?> = HashMap()

    fun register(type:ControlType, controlTokens: ControlTokens) {
        tokens[type] = controlTokens
    }

    val themeMode: ThemeMode
    @Composable
    @ReadOnlyComposable
    get() = LocalThemeMode.current
}