/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationBarView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityBottomNavigationBinding

// TODO Replace outlined icons with fill icons when selected.
class BottomNavigationActivity : DemoActivity() {

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private lateinit var bottomNavigationBinding: ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        bottomNavigationBinding = ActivityBottomNavigationBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        bottomNavigationBinding.toggleLabelButton.setOnClickListener {
            var labelsState: String = ""
            // You can also achieve unlabeled items via @style/Widget.FluentUI.BottomNavigation.Unlabeled
            if (bottomNavigationBinding.bottomNavigation.labelVisibilityMode == LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED) {
                bottomNavigationBinding.bottomNavigation.labelVisibilityMode =
                    NavigationBarView.LABEL_VISIBILITY_LABELED
                labelsState = "On"
            } else {
                bottomNavigationBinding.bottomNavigation.labelVisibilityMode =
                    NavigationBarView.LABEL_VISIBILITY_UNLABELED
                labelsState = "Off"
            }
            it.announceForAccessibility(
                resources.getString(
                    R.string.bottom_navigation_accessibility_labels_state,
                    labelsState
                )
            )
        }

        bottomNavigationBinding.threeMenuItemsButton.setOnClickListener {
            bottomNavigationBinding.bottomNavigation.menu.removeItem(R.id.action_calendar)
            bottomNavigationBinding.bottomNavigation.menu.removeItem(R.id.action_team)
            it.announceForAccessibility(
                resources.getString(
                    R.string.app_accessibility_selected,
                    resources.getString(R.string.bottom_navigation_three_menu_items_button)
                )
            )
        }

        bottomNavigationBinding.fourMenuItemsButton.setOnClickListener {
            bottomNavigationBinding.bottomNavigation.menu.removeItem(R.id.action_calendar)
            bottomNavigationBinding.bottomNavigation.menu.removeItem(R.id.action_team)

            bottomNavigationBinding.bottomNavigation.menu.add(
                R.id.bottom_navigation,
                R.id.action_calendar,
                3,
                resources.getString(R.string.bottom_navigation_menu_item_calendar)
            ).setIcon(R.drawable.ic_calendar_28_regular)
            it.announceForAccessibility(
                resources.getString(
                    R.string.app_accessibility_selected,
                    resources.getString(R.string.bottom_navigation_four_menu_items_button)
                )
            )
        }

        bottomNavigationBinding.fiveMenuItemsButton.setOnClickListener {
            bottomNavigationBinding.bottomNavigation.menu.removeItem(R.id.action_calendar)
            bottomNavigationBinding.bottomNavigation.menu.removeItem(R.id.action_team)

            bottomNavigationBinding.bottomNavigation.menu.add(
                R.id.bottom_navigation,
                R.id.action_calendar,
                3,
                resources.getString(R.string.bottom_navigation_menu_item_calendar)
            ).setIcon(R.drawable.ic_calendar_28_regular)
            bottomNavigationBinding.bottomNavigation.menu.add(
                R.id.bottom_navigation,
                R.id.action_team,
                4,
                resources.getString(R.string.bottom_navigation_menu_item_team)
            ).setIcon(R.drawable.ic_people_team_28_regular)
            it.announceForAccessibility(
                resources.getString(
                    R.string.app_accessibility_selected,
                    resources.getString(R.string.bottom_navigation_five_menu_items_button)
                )
            )
        }
    }
}