package com.example.theme.token

import androidx.compose.foundation.Image
import com.microsoft.fluentui.theme.token.*

class MyControlTokens : ControlTokens(){
    enum class MyControlType : IType {
        MyButtonTokens
    }

    override val tokens: TokenSet<IType, IControlToken> by lazy {
        TokenSet { type ->
            when(type){
                MyControlType.MyButtonTokens -> MyButtonTokens()
                else -> {
                    super.tokens[type]
                }
            }
        }
    }
}