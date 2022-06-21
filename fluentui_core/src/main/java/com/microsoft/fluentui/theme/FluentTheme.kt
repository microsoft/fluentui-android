package com.microsoft.fluentui.theme

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        controlTokens: ControlTokens = ControlTokens(),
        themeMode: ThemeMode = ThemeMode.Auto,
        content: @Composable () -> Unit
) {
    CompositionLocalProvider(
            LocalGlobalTokens provides globalTokens,
            LocalAliasTokens provides aliasTokens,
            LocalControlTokens provides controlTokens,
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

    val controlTokens: ControlTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalControlTokens.current

    val themeMode: ThemeMode
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeMode.current


}

object AppThemeController : ViewModel() {
    var globalTokens: MutableLiveData<GlobalTokens> = MutableLiveData(GlobalTokens())
    var aliasTokens: MutableLiveData<AliasTokens> = MutableLiveData(AliasTokens())
    var controlTokens: MutableLiveData<ControlTokens> = MutableLiveData(ControlTokens())

    fun onGlobalChanged(overrideGlobalTokens: GlobalTokens) {
        globalTokens.value = overrideGlobalTokens
    }

    fun onAliasChanged(overrideAliasTokens: AliasTokens) {
        aliasTokens.value = overrideAliasTokens
    }

    fun onControlChanged(overrideControlTokens: ControlTokens) {
        controlTokens.value = overrideControlTokens
    }

    @Composable
    fun observeGlobalToken(initial: GlobalTokens): State<GlobalTokens> {
        return this.globalTokens.observeAsState(initial)
    }

    @Composable
    fun observeAliasToken(initial: AliasTokens): State<AliasTokens> {
        return this.aliasTokens.observeAsState(initial)
    }

    @Composable
    fun observeControlToken(initial: ControlTokens): State<ControlTokens> {
        return this.controlTokens.observeAsState(initial)
    }
}