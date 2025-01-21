//
//  Copyright (c) Microsoft Corporation. All rights reserved.
//  Licensed under the MIT License.
//

package com.microsoft.fluentui.theme.token

import androidx.compose.runtime.compositionLocalOf
import com.microsoft.fluentui.theme.token.controlTokens.*
import java.util.ResourceBundle.Control

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

object UndefinedControlToken: IControlToken

/**
 * Extend the ControlToken to add token for custom control or providing new token to existing Fluent Control. *
 */
open class ControlTokens : IControlTokens {
    enum class ControlType : IType {
        AcrylicPaneControlType,
        ActionBarControlType,
        AnnouncementCardControlType,
        AppBarControlType,
        AvatarControlType,
        AvatarCarouselControlType,
        AvatarGroupControlType,
        BadgeControlType,
        BannerControlType,
        BasicCardControlType,
        BasicChipControlType,
        BottomSheetControlType,
        ButtonControlType,
        CardNudgeControlType,
        CheckBoxControlType,
        CircularProgressIndicatorControlType,
        CitationControlType,
        ContextualCommandBarControlType,
        DialogControlType,
        DrawerControlType,
        DividerControlType,
        FileCardControlType,
        FloatingActionButtonControlType,
        LabelControlType,
        LinearProgressIndicatorControlType,
        ListItemControlType,
        MenuControlType,
        PeoplePickerControlType,
        PersonaChipControlType,
        PillButtonControlType,
        PillBarControlType,
        PillSwitchControlType,
        PillTabsControlType,
        ProgressTextControlType,
        RadioButtonControlType,
        SearchBarPersonaChipControlType,
        SearchBarControlType,
        ShimmerControlType,
        SideRailControlType,
        SnackbarControlType,
        TabBarControlType,
        TabItemControlType,
        TextFieldControlType,
        ToggleSwitchControlType,
        TooltipControlType,
        ViewPagerControlType
    }

    override val tokens: TokenSet<IType, IControlToken> by lazy {
        TokenSet { type ->
            when (type) {
                ControlType.AcrylicPaneControlType -> AcrylicPaneTokens()
                ControlType.ActionBarControlType -> ActionBarTokens()
                ControlType.AnnouncementCardControlType -> AnnouncementCardTokens()
                ControlType.AppBarControlType -> AppBarTokens()
                ControlType.AvatarControlType -> AvatarTokens()
                ControlType.AvatarCarouselControlType -> AvatarCarouselTokens()
                ControlType.AvatarGroupControlType -> AvatarGroupTokens()
                ControlType.BadgeControlType -> BadgeTokens()
                ControlType.BannerControlType -> BannerTokens()
                ControlType.BasicCardControlType -> BasicCardTokens()
                ControlType.BasicChipControlType -> BasicChipTokens()
                ControlType.BottomSheetControlType -> BottomSheetTokens()
                ControlType.ButtonControlType -> ButtonTokens()
                ControlType.CardNudgeControlType -> CardNudgeTokens()
                ControlType.CheckBoxControlType -> CheckBoxTokens()
                ControlType.CircularProgressIndicatorControlType -> CircularProgressIndicatorTokens()
                ControlType.CitationControlType -> CitationTokens()
                ControlType.ContextualCommandBarControlType -> ContextualCommandBarTokens()
                ControlType.DialogControlType -> DialogTokens()
                ControlType.DrawerControlType -> DrawerTokens()
                ControlType.DividerControlType -> DividerTokens()
                ControlType.FileCardControlType -> FileCardTokens()
                ControlType.FloatingActionButtonControlType -> FABTokens()
                ControlType.LabelControlType -> LabelTokens()
                ControlType.LinearProgressIndicatorControlType -> LinearProgressIndicatorTokens()
                ControlType.ListItemControlType -> ListItemTokens()
                ControlType.MenuControlType -> MenuTokens()
                ControlType.PersonaChipControlType -> PersonaChipTokens()
                ControlType.PeoplePickerControlType -> PeoplePickerTokens()
                ControlType.PillButtonControlType -> PillButtonTokens()
                ControlType.PillBarControlType -> PillBarTokens()
                ControlType.PillSwitchControlType -> PillSwitchTokens()
                ControlType.PillTabsControlType -> PillTabsTokens()
                ControlType.ProgressTextControlType -> ProgressTextTokens()
                ControlType.RadioButtonControlType -> RadioButtonTokens()
                ControlType.SearchBarPersonaChipControlType -> SearchBarPersonaChipTokens()
                ControlType.SearchBarControlType -> SearchBarTokens()
                ControlType.ShimmerControlType -> ShimmerTokens()
                ControlType.SideRailControlType -> SideRailTokens()
                ControlType.SnackbarControlType -> SnackBarTokens()
                ControlType.TabBarControlType -> TabBarTokens()
                ControlType.TabItemControlType -> TabItemTokens()
                ControlType.TextFieldControlType -> TextFieldTokens()
                ControlType.ToggleSwitchControlType -> ToggleSwitchTokens()
                ControlType.TooltipControlType -> TooltipTokens()
                ControlType.ViewPagerControlType -> ViewPagerTokens()
                else -> {
                    UndefinedControlToken
                }
            }
        }
    }
}

internal val LocalControlTokens = compositionLocalOf<IControlTokens> { ControlTokens() }
