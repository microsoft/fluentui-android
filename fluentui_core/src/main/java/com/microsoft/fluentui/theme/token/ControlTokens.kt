//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.runtime.compositionLocalOf
import com.microsoft.fluentui.theme.token.controlTokens.*

interface ControlInfo

interface ControlToken

class ControlTokens {

    enum class ControlType {
        Button,
        FloatingActionButton,
        ToggleSwitch,
        CheckBox,
        RadioButton
    }

    val tokens: TokenSet<ControlType, ControlToken> by lazy {
        TokenSet { token ->
            when (token) {
                ControlType.Button -> ButtonTokens()
                ControlType.FloatingActionButton -> FABTokens()
                ControlType.ToggleSwitch -> ToggleSwitchTokens()
                ControlType.CheckBox -> CheckBoxTokens()
                ControlType.RadioButton -> RadioButtonTokens()
            }
        }
    }

    fun updateTokens(type: ControlType, updatedToken: ControlToken): ControlTokens {
        tokens[type] = updatedToken
        return this
    }

}

internal val LocalControlTokens = compositionLocalOf { ControlTokens() }
