package com.example.theme.token


import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.TokenSet

class MyAliasTokens(globalTokens: GlobalTokens = MyGlobalTokens()) : AliasTokens(globalTokens) {

    override val neutralForegroundColor: TokenSet<NeutralForegroundColorTokens, FluentColor> by lazy {
        TokenSet { FluentColor(light = Color.White) }
    }

    override val brandBackgroundColor: TokenSet<BrandBackgroundColorTokens, FluentColor> by lazy {
        TokenSet { FluentColor(light = Color.DarkGray) }
    }
}


