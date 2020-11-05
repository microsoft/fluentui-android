/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.*
import java.util.*
import kotlin.reflect.KClass

const val ACTION_BAR_LAYOUT = "ActionBarLayout"
const val APP_BAR_LAYOUT = "AppBarLayout"
const val AVATAR_VIEW = "AvatarView"
const val BASIC_INPUTS = "Basic Inputs"
const val BOTTOM_NAVIGATION = "BottomNavigation"
const val BOTTOM_SHEET = "BottomSheet"
const val CALENDAR_VIEW = "CalendarView"
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
const val TAB_LAYOUT = "TabLayout"
const val TEMPLATE_VIEW = "TemplateView"
const val TOOLTIP = "Tooltip"
const val TYPOGRAPHY = "Typography"

val DEMOS = arrayListOf(
    Demo(ACTION_BAR_LAYOUT, ActionBarLayoutActivity::class),
    Demo(APP_BAR_LAYOUT, AppBarLayoutActivity::class),
    Demo(AVATAR_VIEW, AvatarViewActivity::class),
    Demo(BASIC_INPUTS, BasicInputsActivity::class),
    Demo(BOTTOM_NAVIGATION, BottomNavigationActivity::class),
    Demo(BOTTOM_SHEET, BottomSheetActivity::class),
    Demo(CALENDAR_VIEW, CalendarViewActivity::class),
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