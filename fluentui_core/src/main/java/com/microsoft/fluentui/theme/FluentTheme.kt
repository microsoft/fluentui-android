package com.microsoft.fluentui.theme

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.microsoft.fluentui.theme.token.*
import kotlin.random.Random

enum class ThemeMode {
    Light,
    Dark,
    Auto
}

internal val LocalThemeMode = compositionLocalOf { ThemeMode.Auto }
internal val LocalThemeID = compositionLocalOf { 1 }

/**
 * FluentTheme function is a entry point for UI created using Fluent Control. Provide your UI Logic
 * as content to FluentTheme function. This way it provide a scope where its parameter aliasTokens,
 * controlTokens and themeMode would be applicable. Internal UI part could wrap again itself to FluentTheme
 * to get different values for that scope.
 *
 * If the aliasTokens, controlTokens is not provided then Fluent Control uses SDK provided
 * [AliasTokens], [ControlTokens] values. This could be updated at runtime by using [FluentTheme]
 * object to provide new theme at runtime.
 *
 * If explicit aliasTokens & controlTokens is provided then [FluentTheme] will not trigger any
 * update for the Fluent Control.
 *
 * @param aliasTokens AliasTokens provide tokens having semantic meaning.
 * @param controlTokens ControlTokens provide control tokens for all Fluent control, which is used by FLuent Control
 * when explicit token is not provided to it.
 * @param themeMode It define mode to be picked from [ThemeMode.Light] for light theme,
 * [ThemeMode.Dark] for Dark theme, [ThemeMode.Auto] for system driven theme. Default is [ThemeMode.Auto]
 *
 */
@Composable
fun FluentTheme(
    aliasTokens: IAliasTokens? = null,
    controlTokens: IControlTokens? = null,
    themeMode: ThemeMode? = null,
    content: @Composable () -> Unit
) {
    val appAliasTokens by FluentTheme.observeAliasToken(initial = AliasTokens())
    val appControlTokens by FluentTheme.observeControlToken(initial = ControlTokens())
    val appThemeMode by FluentTheme.observeThemeMode(initial = ThemeMode.Auto)
    val appThemeID by FluentTheme.observeThemeID(initial = 1)

    CompositionLocalProvider(
        LocalAliasTokens provides (aliasTokens ?: appAliasTokens),
        LocalControlTokens provides (controlTokens ?: appControlTokens),
        LocalThemeMode provides (themeMode ?: appThemeMode),
        LocalThemeID provides appThemeID
    ) {
        content()
    }
}

/**
 * FluentTheme singleton class to update theme across the app.
 */
object FluentTheme : ViewModel() {
    /**
     * Provide AliasTokens in use across app
     */
    val aliasTokens: IAliasTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalAliasTokens.current

    /**
     * Provide ControlTokens in use across app in FluentTheme scope where explicit values is not provided to it.
     */
    val controlTokens: IControlTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalControlTokens.current

    /**
     * Provide themeMode in use across app in FluentTheme scope where explicit values is not provided to it.
     */
    val themeMode: ThemeMode
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeMode.current


    val themeID: Int
        @Composable
        @ReadOnlyComposable
        get() = LocalThemeID.current

    private var aliasTokens_: MutableLiveData<IAliasTokens> = MutableLiveData(AliasTokens())
    private var controlTokens_: MutableLiveData<IControlTokens> = MutableLiveData(ControlTokens())
    private var themeMode_: MutableLiveData<ThemeMode> = MutableLiveData(ThemeMode.Auto)
    private var themeID_: MutableLiveData<Int> = MutableLiveData(1)

    /**
     * Update aliasTokens across all FluentTheme scope where explicit values is not provided to it.
     */
    fun updateAliasTokens(overrideAliasTokens: IAliasTokens) {
        aliasTokens_.value = overrideAliasTokens
        updateThemeID()
    }

    /**
     * Update controlTokens across all FluentTheme scope where explicit values is not provided to it.
     */
    fun updateControlTokens(overrideControlTokens: IControlTokens) {
        controlTokens_.value = overrideControlTokens
        updateThemeID()
    }

    /**
     * Update themeMode across all FluentTheme scope where explicit values is not provided to it.
     */
    fun updateThemeMode(overrideThemeMode: ThemeMode) {
        themeMode_.value = overrideThemeMode
        updateThemeID()
    }

    private fun updateThemeID() {
        var temp = Random.nextInt()
        while (temp == themeID_.value)
            temp = Random.nextInt()
        themeID_.value = temp
    }

    @Composable
    internal fun observeAliasToken(initial: IAliasTokens): State<IAliasTokens> {
        return this.aliasTokens_.observeAsState(initial)
    }

    @Composable
    internal fun observeControlToken(initial: IControlTokens): State<IControlTokens> {
        return this.controlTokens_.observeAsState(initial)
    }

    @Composable
    internal fun observeThemeMode(initial: ThemeMode): State<ThemeMode> {
        return this.themeMode_.observeAsState(initial = initial)
    }

    @Composable
    internal fun observeThemeID(initial: Int): State<Int> {
        return this.themeID_.observeAsState(initial = initial)
    }
}