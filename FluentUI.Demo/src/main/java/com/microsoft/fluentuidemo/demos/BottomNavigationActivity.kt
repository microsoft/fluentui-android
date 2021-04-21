/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_bottom_navigation.*

// TODO Replace outlined icons with fill icons when selected.
class BottomNavigationActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_bottom_navigation

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toggle_label_button.setOnClickListener {
            // You can also achieve unlabeled items via @style/Widget.FluentUI.BottomNavigation.Unlabeled
            bottom_navigation.labelVisibilityMode =
                if (bottom_navigation.labelVisibilityMode == LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED)
                    LabelVisibilityMode.LABEL_VISIBILITY_LABELED
                else
                    LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED
        }

        three_menu_items_button.setOnClickListener {
            bottom_navigation.menu.removeItem(R.id.action_calendar)
            bottom_navigation.menu.removeItem(R.id.action_team)
        }

        four_menu_items_button.setOnClickListener {
            bottom_navigation.menu.removeItem(R.id.action_calendar)
            bottom_navigation.menu.removeItem(R.id.action_team)

            bottom_navigation.menu.add(
                R.id.bottom_navigation,
                R.id.action_calendar,
                3,
                resources.getString(R.string.bottom_navigation_menu_item_calendar)
            ).setIcon(R.drawable.ic_calendar_28_regular)
        }

        five_menu_items_button.setOnClickListener {
            bottom_navigation.menu.removeItem(R.id.action_calendar)
            bottom_navigation.menu.removeItem(R.id.action_team)

            bottom_navigation.menu.add(
                R.id.bottom_navigation,
                R.id.action_calendar,
                3,
                resources.getString(R.string.bottom_navigation_menu_item_calendar)
            ).setIcon(R.drawable.ic_calendar_28_regular)
            bottom_navigation.menu.add(
                R.id.bottom_navigation,
                R.id.action_team,
                4,
                resources.getString(R.string.bottom_navigation_menu_item_team)
            ).setIcon(R.drawable.ic_people_team_28_regular)
        }
    }
}