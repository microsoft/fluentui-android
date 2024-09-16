/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo

import androidx.core.app.ComponentActivity
import com.microsoft.fluentuidemo.demos.ActionBarLayoutActivity
import com.microsoft.fluentuidemo.demos.AppBarLayoutActivity
import com.microsoft.fluentuidemo.demos.AvatarGroupViewActivity
import com.microsoft.fluentuidemo.demos.AvatarViewActivity
import com.microsoft.fluentuidemo.demos.BasicInputsActivity
import com.microsoft.fluentuidemo.demos.BottomNavigationActivity
import com.microsoft.fluentuidemo.demos.BottomSheetActivity
import com.microsoft.fluentuidemo.demos.CalendarViewActivity
import com.microsoft.fluentuidemo.demos.ContextualCommandBarActivity
import com.microsoft.fluentuidemo.demos.DateTimePickerActivity
import com.microsoft.fluentuidemo.demos.DrawerActivity
import com.microsoft.fluentuidemo.demos.ListItemViewActivity
import com.microsoft.fluentuidemo.demos.PeoplePickerViewActivity
import com.microsoft.fluentuidemo.demos.PersistentBottomSheetActivity
import com.microsoft.fluentuidemo.demos.PersonaChipViewActivity
import com.microsoft.fluentuidemo.demos.PersonaListViewActivity
import com.microsoft.fluentuidemo.demos.PersonaViewActivity
import com.microsoft.fluentuidemo.demos.PopupMenuActivity
import com.microsoft.fluentuidemo.demos.ProgressActivity
import com.microsoft.fluentuidemo.demos.SnackbarActivity
import com.microsoft.fluentuidemo.demos.TabLayoutActivity
import com.microsoft.fluentuidemo.demos.TemplateViewActivity
import com.microsoft.fluentuidemo.demos.TooltipActivity
import com.microsoft.fluentuidemo.demos.TypographyActivity
import com.microsoft.fluentuidemo.demos.V2AppBarActivity
import com.microsoft.fluentuidemo.demos.V2AvatarActivity
import com.microsoft.fluentuidemo.demos.V2AvatarCarouselActivity
import com.microsoft.fluentuidemo.demos.V2AvatarGroupActivity
import com.microsoft.fluentuidemo.demos.V2BadgeActivity
import com.microsoft.fluentuidemo.demos.V2BannerActivity
import com.microsoft.fluentuidemo.demos.V2BasicChipActivity
import com.microsoft.fluentuidemo.demos.V2BasicControlsActivity
import com.microsoft.fluentuidemo.demos.V2BottomDrawerActivity
import com.microsoft.fluentuidemo.demos.V2BottomSheetActivity
import com.microsoft.fluentuidemo.demos.V2ButtonsActivity
import com.microsoft.fluentuidemo.demos.V2CardActivity
import com.microsoft.fluentuidemo.demos.V2CardNudgeActivity
import com.microsoft.fluentuidemo.demos.V2CitationActivity
import com.microsoft.fluentuidemo.demos.V2ContextualCommandBarActivity
import com.microsoft.fluentuidemo.demos.V2DialogActivity
import com.microsoft.fluentuidemo.demos.V2DrawerActivity
import com.microsoft.fluentuidemo.demos.V2LabelActivity
import com.microsoft.fluentuidemo.demos.V2ListItemActivity
import com.microsoft.fluentuidemo.demos.V2MenuActivity
import com.microsoft.fluentuidemo.demos.V2PeoplePickerActivity
import com.microsoft.fluentuidemo.demos.V2PersonaActivity
import com.microsoft.fluentuidemo.demos.V2PersonaChipActivity
import com.microsoft.fluentuidemo.demos.V2PersonaListActivity
import com.microsoft.fluentuidemo.demos.V2ProgressActivity
import com.microsoft.fluentuidemo.demos.V2ScaffoldActivity
import com.microsoft.fluentuidemo.demos.V2SearchBarActivity
import com.microsoft.fluentuidemo.demos.V2SegmentedControlActivity
import com.microsoft.fluentuidemo.demos.V2ShimmerActivity
import com.microsoft.fluentuidemo.demos.V2SideRailActivity
import com.microsoft.fluentuidemo.demos.V2SnackbarActivity
import com.microsoft.fluentuidemo.demos.V2TabBarActivity
import com.microsoft.fluentuidemo.demos.V2TextFieldActivity
import com.microsoft.fluentuidemo.demos.V2ToolTipActivity
import java.util.UUID
import kotlin.reflect.KClass

enum class Badge {
    None,
    New,
    Modified,
    APIBreak
}

const val V2AVATAR = "V2 Avatar"
const val V2AVATAR_CAROUSEL = "V2 Avatar Carousel"
const val V2AVATAR_GROUP = "V2 Avatar Group"
const val V2BADGE = "V2 Badge"
const val V2BANNER = "V2 Banner"
const val V2BASIC_CHIP = "V2 Basic Chip"
const val V2BASIC_CONTROLS = "V2 Basic Controls"
const val V2BOTTOM_DRAWER = "V2 Bottom Drawer"
const val V2BOTTOM_SHEET = "V2 BottomSheet"
const val V2BUTTON = "V2 Buttons"
const val V2CARD = "V2 Card"
const val V2CARD_NUDGE = "V2 Card Nudge"
const val V2CITATION = "V2 Citation"
const val V2CONTEXTUAL_COMMAND_BAR = "V2 ContextualCommandBar"
const val V2DIALOG = "V2 Dialog"
const val V2DRAWER = "V2 Drawer"
const val V2LIST_ITEM = "V2 ListItem"
const val V2MENU = "V2 Menu"
const val V2PEOPLE_PICKER = "V2 People Picker"
const val V2PERSONA = "V2 Persona"
const val V2PERSONA_CHIP = "V2 PersonaChip"
const val V2PERSONA_LIST = "V2 PersonaList"
const val V2PROGRESS = "V2 Progress"
const val V2SCAFFOLD = "V2 Scaffold"
const val V2SEARCHBAR = "V2 SearchBar"
const val V2SEGMENTED_CONTROL = "V2 SegmentedControl"
const val V2SHIMMER = "V2 Shimmer"
const val V2SIDE_RAIL = "V2 SideRail"
const val V2SNACKBAR = "V2 Snackbar"
const val V2TAB_BAR = "V2 TabBar"
const val V2TEXT_FIELD = "V2 TextField"
const val V2TOOL_TIP = "V2 ToolTip"
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
const val V2LABEL = "V2 Label"
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

val V1DEMO = arrayListOf(
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

val V2DEMO = arrayListOf(
    Demo(V2APP_BAR_LAYOUT, V2AppBarActivity::class),
    Demo(V2AVATAR, V2AvatarActivity::class),
    Demo(V2AVATAR_CAROUSEL, V2AvatarCarouselActivity::class),
    Demo(V2AVATAR_GROUP, V2AvatarGroupActivity::class),
    Demo(V2BADGE, V2BadgeActivity::class),
    Demo(V2BANNER, V2BannerActivity::class),
    Demo(V2BASIC_CHIP, V2BasicChipActivity::class),
    Demo(V2BASIC_CONTROLS, V2BasicControlsActivity::class),
    Demo(V2BOTTOM_DRAWER, V2BottomDrawerActivity::class),
    Demo(V2BOTTOM_SHEET, V2BottomSheetActivity::class),
    Demo(V2BUTTON, V2ButtonsActivity::class),
    Demo(V2CARD, V2CardActivity::class),
    Demo(V2CARD_NUDGE, V2CardNudgeActivity::class),
    Demo(V2CITATION, V2CitationActivity::class),
    Demo(V2CONTEXTUAL_COMMAND_BAR, V2ContextualCommandBarActivity::class),
    Demo(V2DIALOG, V2DialogActivity::class),
    Demo(V2DRAWER, V2DrawerActivity::class),
    Demo(V2LABEL, V2LabelActivity::class, Badge.Modified),
    Demo(V2LIST_ITEM, V2ListItemActivity::class),
    Demo(V2MENU, V2MenuActivity::class),
    Demo(V2PEOPLE_PICKER, V2PeoplePickerActivity::class),
    Demo(V2PERSONA, V2PersonaActivity::class),
    Demo(V2PERSONA_CHIP, V2PersonaChipActivity::class),
    Demo(V2PERSONA_LIST, V2PersonaListActivity::class),
    Demo(V2PROGRESS, V2ProgressActivity::class),
    Demo(V2SCAFFOLD, V2ScaffoldActivity::class),
    Demo(V2SEARCHBAR, V2SearchBarActivity::class),
    Demo(V2SEGMENTED_CONTROL, V2SegmentedControlActivity::class, Badge.Modified),
    Demo(V2SHIMMER, V2ShimmerActivity::class),
    Demo(V2SIDE_RAIL, V2SideRailActivity::class),
    Demo(V2SNACKBAR, V2SnackbarActivity::class),
    Demo(V2TAB_BAR, V2TabBarActivity::class),
    Demo(V2TEXT_FIELD, V2TextFieldActivity::class),
    Demo(V2TOOL_TIP, V2ToolTipActivity::class),
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

data class Demo(
    val title: String, val demoClass: KClass<out ComponentActivity>,
    val badge: Badge = Badge.None
) {
    val id: UUID = UUID.randomUUID()
}