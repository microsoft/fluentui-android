package com.microsoft.fluentui.theme

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.*
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

    class ThemeViewModel : ViewModel() {
        private val _aliasTokens: MutableLiveData<AliasTokens> = MutableLiveData(AliasTokens())

        private val _globalTokens: MutableLiveData<GlobalTokens> = MutableLiveData(GlobalTokens())

        val aliasTokens: LiveData<AliasTokens> = _aliasTokens
        val globalTokens: LiveData<GlobalTokens> = _globalTokens

        fun onAliasChanged(aliasTokens: AliasTokens) {
            _aliasTokens.value = aliasTokens
        }

        fun onGlobalChanged(globalTokens: GlobalTokens) {
            _globalTokens.value = globalTokens
        }

        @Composable
        fun initializeGlobalToken(globalTokens: GlobalTokens): State<GlobalTokens> {
            return this.globalTokens.observeAsState(initial = globalTokens)
        }

        @Composable
        fun initializeAliasToken(aliasTokens: AliasTokens): State<AliasTokens> {
            return this.aliasTokens.observeAsState(initial = aliasTokens)
        }
    }

    fun getViewModel(owner: ViewModelStoreOwner): ThemeViewModel {
        return ViewModelProvider(owner).get(ThemeViewModel::class.java)
    }
}