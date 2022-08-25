package com.microsoft.fluentui.theme

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.fluentui.theme.token.*

enum class ThemeMode {
    Light,
    Dark,
    Auto
}

internal val LocalThemeMode = compositionLocalOf { ThemeMode.Auto }

@Composable
fun FluentTheme(
        globalTokens: GlobalTokens? = null,
        aliasTokens: AliasTokens? = null,
        controlTokens: ControlTokens? = null,
        themeMode: ThemeMode = ThemeMode.Auto,
        content: @Composable () -> Unit
) {
    val appGlobalToken by AppThemeController.observeGlobalToken(initial = GlobalTokens())
    val appAliasTokens by AppThemeController.observeAliasToken(initial = AliasTokens())
    val appControlTokens by AppThemeController.observeControlToken(initial = ControlTokens())

    CompositionLocalProvider(
            LocalGlobalTokens provides (globalTokens ?: appGlobalToken),
            LocalAliasTokens provides (aliasTokens ?: appAliasTokens),
            LocalControlTokens provides (controlTokens ?: appControlTokens),
            LocalThemeMode provides themeMode
    ) {
        LocalAliasTokens.current.updateGlobalToken()
        content()
    }
}

object FluentTheme {
    val globalTokens: GlobalTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalGlobalTokens.current

    val aliasTokens: AliasTokens
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

    fun updateGlobalTokens(overrideGlobalTokens: GlobalTokens) {
        globalTokens.value = overrideGlobalTokens
    }

    fun updateAliasTokens(overrideAliasTokens: AliasTokens) {
        aliasTokens.value = overrideAliasTokens
    }

    fun updateControlTokens(overrideControlTokens: ControlTokens) {
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