/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.*
import java.util.*
import kotlin.reflect.KClass

const val V2AVATAR = "V2 Avatar"
const val V2AVATAR_CAROUSEL = "V2 Avatar Carousel"
const val V2AVATAR_GROUP = "V2 Avatar Group"
const val V2Badge = "V2 Badge"
const val V2BASIC_CONTROLS = "V2 Basic Controls"
const val V2BOTTOM_SHEET = "V2 BottomSheet"
const val V2BUTTON = "V2 Buttons"
const val V2CARD_NUDGE = "V2 Card Nudge"
const val V2CONTEXTUAL_COMMAND_BAR = "V2 ContextualCommandBar"
const val V2DRAWER = "V2 Drawer"
const val V2LIST_ITEM = "V2 ListItem"
const val V2PERSONA = "V2 Persona"
const val V2PERSONA_CHIP = "V2 PersonaChip"
const val V2PERSONA_LIST = "V2 PersonaList"
const val V2PROGRESS = "V2 Progress"
const val V2SEARCHBAR = "V2 SearchBar"
const val V2SEGMENTED_CONTROL = "V2 SegmentedControl"
const val V2TABBAR = "V2 TabBar"
const val ACTION_BAR_LAYOUT = "ActionBarLayout"
const val APP_BAR_LAYOUT = "AppBarLayout"
const val V2APP_BAR_LAYOUT = "V2 AppBarLayout"
const val AVATAR_VIEW = "AvatarView"
const val AVATAR_GROUP_VIEW = "AvatarGroupView"
const val BASIC_INPUTS = "Basic Inputs"
const val BOTTOM_NAVIGATION = "BottomNavigation"
const val BOTTOM_SHEET = "BottomSheet"
const val CALENDAR_VIEW = "CalendarView"
const val CONTEXTUAL_COMMAND_BAR = "ContextualCommandBar"
const val DATE_TIME_PICKER = "DateTimePicker"
const val DRAWER = "Drawer"
const val LIST_ITEM_VIEW = "ListItemView"
const val PEOPLE_PICKER_VIEW = "PeoplePickerView"
const val PERSISTENT_BOTTOM_SHEET = "PersistentBottomSheet"
const val PERSONA_CHIP_VIEW = "PersonaChipView"
const val PERSONA_LIST_VIEW = "PersonaListView"
const val PERSONA_VIEW = "PersonaView"
const val POPUP_MENU = "PopupMenu"
const val PROGRESS = "Progress"
const val SNACKBAR = "Snackbar"
const val V2SNACKBAR = "V2 Snackbar"
const val TAB_LAYOUT = "TabLayout"
const val TEMPLATE_VIEW = "TemplateView"
const val TOOLTIP = "Tooltip"
const val TYPOGRAPHY = "Typography"

val DEMOS = arrayListOf(
    Demo(V2APP_BAR_LAYOUT, V2AppBarLayoutActivity::class),
    Demo(V2AVATAR, V2AvatarActivity::class),
    Demo(V2AVATAR_CAROUSEL, V2AvatarCarouselActivity::class),
    Demo(V2AVATAR_GROUP, V2AvatarGroupActivity::class),
    Demo(V2Badge, V2BadgeActivity::class),
    Demo(V2BASIC_CONTROLS, V2BasicControlsActivity::class),
    Demo(V2BOTTOM_SHEET, V2BottomSheetActivity::class),
    Demo(V2BUTTON, V2ButtonsActivity::class),
    Demo(V2CARD_NUDGE, V2CardNudgeActivity::class),
    Demo(V2CONTEXTUAL_COMMAND_BAR, V2ContextualCommandBarActivity::class),
    Demo(V2DRAWER, V2DrawerActivity::class),
    Demo(V2LIST_ITEM, V2ListItemActivity::class),
    Demo(V2PERSONA, V2PersonaActivity::class),
    Demo(V2PERSONA_CHIP, V2PersonaChipActivity::class),
    Demo(V2PERSONA_LIST, V2PersonaListActivity::class),
    Demo(V2PROGRESS, V2ProgressActivity::class),
    Demo(V2SEARCHBAR, V2SearchBarActivity::class),
    Demo(V2SEGMENTED_CONTROL, V2SegmentedControlActivity::class),
    Demo(V2SNACKBAR, V2SnackbarActivity::class),
    Demo(V2TABBAR, V2TabBarActivity::class),
    Demo(ACTION_BAR_LAYOUT, ActionBarLayoutActivity::class),
    Demo(APP_BAR_LAYOUT, AppBarLayoutActivity::class),
    Demo(AVATAR_VIEW, AvatarViewActivity::class),
    Demo(AVATAR_GROUP_VIEW, AvatarGroupViewActivity::class),
    Demo(BASIC_INPUTS, BasicInputsActivity::class),
    Demo(BOTTOM_NAVIGATION, BottomNavigationActivity::class),
    Demo(BOTTOM_SHEET, BottomSheetActivity::class),
    Demo(CALENDAR_VIEW, CalendarViewActivity::class),
    Demo(CONTEXTUAL_COMMAND_BAR, ContextualCommandBarActivity::class),
    Demo(DATE_TIME_PICKER, DateTimePickerActivity::class),
    Demo(DRAWER, DrawerActivity::class),
    Demo(LIST_ITEM_VIEW, ListItemViewActivity::class),
    Demo(PEOPLE_PICKER_VIEW, PeoplePickerViewActivity::class),
    Demo(PERSISTENT_BOTTOM_SHEET, PersistentBottomSheetActivity::class),
    Demo(PERSONA_CHIP_VIEW, PersonaChipViewActivity::class),
    Demo(PERSONA_LIST_VIEW, PersonaListViewActivity::class),
    Demo(PERSONA_VIEW, PersonaViewActivity::class),
    Demo(POPUP_MENU, PopupMenuActivity::class),
    Demo(PROGRESS, ProgressActivity::class),
    Demo(SNACKBAR, SnackbarActivity::class),
    Demo(TAB_LAYOUT, TabLayoutActivity::class),
    Demo(TEMPLATE_VIEW, TemplateViewActivity::class),
    Demo(TOOLTIP, TooltipActivity::class),
    Demo(TYPOGRAPHY, TypographyActivity::class)
)

val DUO_DEMOS = arrayListOf(
    Demo(APP_BAR_LAYOUT, AppBarLayoutActivity::class),
    Demo(BOTTOM_SHEET, BottomSheetActivity::class),
    Demo(CALENDAR_VIEW, CalendarViewActivity::class),
    Demo(DRAWER, DrawerActivity::class),
    Demo(PEOPLE_PICKER_VIEW, PeoplePickerViewActivity::class),
    Demo(DATE_TIME_PICKER, DateTimePickerActivity::class),
    Demo(POPUP_MENU, PopupMenuActivity::class),
    Demo(SNACKBAR, SnackbarActivity::class),
    Demo(TOOLTIP, TooltipActivity::class)
)

data class Demo(val title: String, val demoClass: KClass<out DemoActivity>) {
    val id: UUID = UUID.randomUUID()
}