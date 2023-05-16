//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.runtime.compositionLocalOf
import com.microsoft.fluentui.theme.token.controlTokens.*

interface ControlInfo

interface IType

interface IControlToken

interface IControlTokens {
    /**
     * Token set contains control token for each control used in scope of FluentTheme composable to support theming.
     */
    val tokens: TokenSet<IType, IControlToken>
    fun updateToken(type: IType, updatedToken: IControlToken): IControlTokens {
        tokens[type] = updatedToken
        return this
    }
}

/**
 * Extend the ControlToken to add token for custom control or providing new token to existing Fluent Control. *
 */
open class ControlTokens : IControlTokens {
    enum class ControlType : IType {
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
        Citation,
        ContextualCommandBar,
        Dialog,
        Drawer,
        Divider,
        FileCard,
        FloatingActionButton,
        Label,
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

    override val tokens: TokenSet<IType, IControlToken> by lazy {
        TokenSet { type ->
            when (type) {
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
                ControlType.Citation -> CitationTokens()
                ControlType.ContextualCommandBar -> ContextualCommandBarTokens()
                ControlType.Dialog -> DialogTokens()
                ControlType.Drawer -> DrawerTokens()
                ControlType.Divider -> DividerTokens()
                ControlType.FileCard -> FileCardTokens()
                ControlType.FloatingActionButton -> FABTokens()
                ControlType.Label -> LabelTokens()
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
                else -> {
                    throw java.lang.RuntimeException("$type not defined")
                }
            }
        }
    }
}

internal val LocalControlTokens = compositionLocalOf<IControlTokens> { ControlTokens() }
