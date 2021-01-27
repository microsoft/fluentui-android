/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.microsoft.fluentui.contextualcommandbar.CommandItem
import com.microsoft.fluentui.contextualcommandbar.CommandItemAdapter
import com.microsoft.fluentui.contextualcommandbar.CommandItemGroup
import com.microsoft.fluentui.contextualcommandbar.ContextualCommandBar
import com.microsoft.fluentui.contextualcommandbar.DefaultCommandItem
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_contextual_command_bar.contextual_command_bar_default
import kotlinx.android.synthetic.main.activity_contextual_command_bar.contextual_command_bar_with_dismiss
import kotlinx.android.synthetic.main.activity_contextual_command_bar.insert_item
import kotlinx.android.synthetic.main.activity_contextual_command_bar.update_item

class ContextualCommandBarActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_contextual_command_bar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemGroups = arrayListOf<CommandItemGroup>()

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_add_24_regular,
                        getString(R.string.contextual_command_accessibility_add)
                ))
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_mention_24_regular,
                        getString(R.string.contextual_command_accessibility_mention),
                        enabled = false
                ))
        )

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(
                        label = getString(R.string.contextual_command_accessibility_bold),
                        selected = true
                ))
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_text_italic_24_regular,
                        getString(R.string.contextual_command_accessibility_italic)
                ))
                .addItem(DefaultCommandItem(
                        label = getString(R.string.contextual_command_accessibility_underline)
                ))
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_text_strikethrough_24_regular,
                        getString(R.string.contextual_command_accessibility_strikethrough)
                ))
        )

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_arrow_undo_24_regular,
                        getString(R.string.contextual_command_accessibility_undo),
                        selected = true
                ))
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_arrow_redo_24_regular,
                        getString(R.string.contextual_command_accessibility_redo)
                ))
        )

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_text_bullet_list_24_regular,
                        getString(R.string.contextual_command_accessibility_bullet)
                ))
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_text_number_list_ltr_24_regular,
                        getString(R.string.contextual_command_accessibility_list)
                ))
        )

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(
                        R.drawable.ic_fluent_link_24_regular,
                        getString(R.string.contextual_command_accessibility_link),
                        false
                ))
        )

        with(contextual_command_bar_default) {
            setItemGroups(itemGroups)
            setItemOnClickListener(object : CommandItemAdapter.OnItemClickListener {
                override fun onItemClick(item: CommandItem, view: View) {
                    Toast.makeText(
                            this@ContextualCommandBarActivity,
                            getString(R.string.contextual_command_prompt_click_item, item.getLabel()),
                            Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        with(contextual_command_bar_with_dismiss) {
            setItemGroups(itemGroups)
            dismissCommandItem = ContextualCommandBar.DismissCommandItem(
                    icon = R.drawable.ic_fluent_keyboard_dock_24_regular,
                    label = getString(R.string.contextual_command_accessibility_dismiss),
                    visible = true,
                    gravity = ContextualCommandBar.DismissItemGravity.START,
                    dismissListener = {
                        Toast.makeText(
                                this@ContextualCommandBarActivity,
                                getString(R.string.contextual_command_prompt_click_dismiss),
                                Toast.LENGTH_SHORT
                        ).show()
                    }
            )
            setItemOnClickListener(object : CommandItemAdapter.OnItemClickListener {
                override fun onItemClick(item: CommandItem, view: View) {
                    Toast.makeText(
                            this@ContextualCommandBarActivity,
                            getString(R.string.contextual_command_prompt_click_item, item.getLabel()),
                            Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }

        insert_item.setOnClickListener {
            contextual_command_bar_default.addItemGroup(CommandItemGroup()
                    .addItem(DefaultCommandItem(
                            R.drawable.ic_fluent_add_24_regular,
                            getString(R.string.contextual_command_accessibility_add)
                    ))
                    .addItem(DefaultCommandItem(
                            R.drawable.ic_fluent_mention_24_regular,
                            getString(R.string.contextual_command_accessibility_mention),
                            enabled = false
                    ))
            )
        }

        update_item.setOnClickListener {
            (itemGroups[0].items[0] as DefaultCommandItem).setEnabled(false)
            (itemGroups[0].items[1] as DefaultCommandItem).setEnabled(true)
            (itemGroups[0].items[1] as DefaultCommandItem).setSelected(true)
            contextual_command_bar_default.notifyDataSetChanged()
        }
    }
}
