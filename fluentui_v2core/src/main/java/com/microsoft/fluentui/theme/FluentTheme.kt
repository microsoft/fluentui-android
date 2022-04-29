package com.microsoft.fluentui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.LocalAliasToken
import com.microsoft.fluentui.theme.token.LocalGlobalToken

@Composable
fun FluentTheme (globalTokens: GlobalTokens = GlobalTokens(),
                 aliasToken: AliasTokens = AliasTokens(),
                 content: @Composable () -> Unit
){
    val rememberedGlobalToken = remember {globalTokens}
    val rememberedAliasToken = remember {aliasToken}

    CompositionLocalProvider(
        LocalGlobalToken provides rememberedGlobalToken,
        LocalAliasToken provides rememberedAliasToken){
        content()
    }

    var controlTokens : Map<ControlType, ControlTokens> = HashMap()

}

object FluentTheme{

    val globalTokens:GlobalTokens
    @Composable
    @ReadOnlyComposable
    get() = LocalGlobalToken.current

    val aliasToken:AliasTokens
    @Composable
    @ReadOnlyComposable
    get() = LocalAliasToken.current

    val tokens:HashMap<ControlType,Any?> = HashMap()

    fun register(type:ControlType, controlTokens: ControlTokens) {
        tokens[type] = controlTokens
    }
}