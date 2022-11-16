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
        AvatarCarousel,
        AvatarGroup,
        BottomSheet,
        Button,
        CheckBox,
        ContextualCommandBar,
        Drawer,
        FloatingActionButton,
        ListItem,
        PillButton,
        PillBar,
        RadioButton,
        Switch,
        Tabs,
        ToggleSwitch
    }

    val tokens: TokenSet<ControlType, ControlToken> by lazy {
        TokenSet { token ->
            when (token) {
                ControlType.Avatar -> AvatarTokens()
                ControlType.AvatarCarousel -> AvatarCarouselTokens()
                ControlType.AvatarGroup -> AvatarGroupTokens()
                ControlType.BottomSheet -> BottomSheetTokens()
                ControlType.Button -> ButtonTokens()
                ControlType.CheckBox -> CheckBoxTokens()
                ControlType.ContextualCommandBar -> ContextualCommandBarTokens()
                ControlType.Drawer -> DrawerTokens()
                ControlType.FloatingActionButton -> FABTokens()
                ControlType.ListItem -> ListItemTokens()
                ControlType.PillButton -> PillButtonTokens()
                ControlType.PillBar -> PillBarTokens()
                ControlType.RadioButton -> RadioButtonTokens()
                ControlType.Switch -> SwitchTokens()
                ControlType.Tabs -> TabsTokens()
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
