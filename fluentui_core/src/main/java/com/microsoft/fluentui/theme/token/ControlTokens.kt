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
        Avatar,
        AvatarGroup,
        Button,
        CheckBox,
        Drawer,
        FloatingActionButton,
        RadioButton,
        ToggleSwitch
    }

    val tokens: TokenSet<ControlType, ControlToken> by lazy {
        TokenSet { token ->
            when (token) {
                ControlType.Avatar -> AvatarTokens()
                ControlType.AvatarGroup -> AvatarGroupTokens()
                ControlType.Button -> ButtonTokens()
                ControlType.CheckBox -> CheckBoxTokens()
                ControlType.Drawer -> DrawerTokens()
                ControlType.FloatingActionButton -> FABTokens()
                ControlType.RadioButton -> RadioButtonTokens()
                ControlType.ToggleSwitch -> ToggleSwitchTokens()
            }
        }
    }

    fun updateTokens(type: ControlType, updatedToken: ControlToken): ControlTokens {
        tokens[type] = updatedToken
        return this
    }

}

internal val LocalControlTokens = compositionLocalOf { ControlTokens() }
