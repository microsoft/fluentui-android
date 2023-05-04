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
        AnnouncementCard,
        AppBar,
        Avatar,
        AvatarCarousel,
        AvatarGroup,
        Badge,
        BasicCard,
        BottomSheet,
        Button,
        CardNudge,
        CheckBox,
        CircularProgressIndicator,
        ContextualCommandBar,
        Drawer,
        Divider,
        FileCard,
        FloatingActionButton,
        LinearProgressIndicator,
        ListItem,
        Menu,
        PersonaChip,
        PillButton,
        PillBar,
        PillSwitch,
        PillTabs,
        RadioButton,
        SearchBarPersonaChip,
        SearchBar,
        Shimmer,
        Snackbar,
        TabBar,
        TabItem,
        TextField,
        ToggleSwitch,
    }

    val tokens: TokenSet<ControlType, ControlToken> by lazy {
        TokenSet { token ->
            when (token) {
                ControlType.AnnouncementCard -> AnnouncementCardTokens()
                ControlType.AppBar -> AppBarTokens()
                ControlType.Avatar -> AvatarTokens()
                ControlType.AvatarCarousel -> AvatarCarouselTokens()
                ControlType.AvatarGroup -> AvatarGroupTokens()
                ControlType.Badge -> BadgeTokens()
                ControlType.BasicCard -> BasicCardTokens()
                ControlType.BottomSheet -> BottomSheetTokens()
                ControlType.Button -> ButtonTokens()
                ControlType.CardNudge -> CardNudgeTokens()
                ControlType.CheckBox -> CheckBoxTokens()
                ControlType.CircularProgressIndicator -> CircularProgressIndicatorTokens()
                ControlType.ContextualCommandBar -> ContextualCommandBarTokens()
                ControlType.Drawer -> DrawerTokens()
                ControlType.Divider -> DividerTokens()
                ControlType.FileCard -> FileCardTokens()
                ControlType.FloatingActionButton -> FABTokens()
                ControlType.LinearProgressIndicator -> LinearProgressIndicatorTokens()
                ControlType.ListItem -> ListItemTokens()
                ControlType.Menu -> MenuTokens()
                ControlType.PersonaChip -> PersonaChipTokens()
                ControlType.PillButton -> PillButtonTokens()
                ControlType.PillBar -> PillBarTokens()
                ControlType.PillSwitch -> PillSwitchTokens()
                ControlType.PillTabs -> PillTabsTokens()
                ControlType.RadioButton -> RadioButtonTokens()
                ControlType.SearchBarPersonaChip -> SearchBarPersonaChipTokens()
                ControlType.SearchBar -> SearchBarTokens()
                ControlType.Shimmer -> ShimmerTokens()
                ControlType.Snackbar -> SnackBarTokens()
                ControlType.TabBar -> TabBarTokens()
                ControlType.TabItem -> TabItemTokens()
                ControlType.TextField -> TextFieldTokens()
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
