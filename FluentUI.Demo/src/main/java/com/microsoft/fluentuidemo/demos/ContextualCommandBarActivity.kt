/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.microsoft.fluentui.contextualcommandbar.CommandItem
import com.microsoft.fluentui.contextualcommandbar.CommandItemGroup
import com.microsoft.fluentui.contextualcommandbar.ContextualCommandBar
import com.microsoft.fluentui.contextualcommandbar.DefaultCommandItem
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityContextualCommandBarBinding


class ContextualCommandBarActivity : DemoActivity() {

    private lateinit var ccbBinding: ActivityContextualCommandBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        ccbBinding = ActivityContextualCommandBarBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        val itemGroups = arrayListOf<CommandItemGroup>()

        // Used to test bitmap support
        var drawable = AppCompatResources.getDrawable(this, R.drawable.ic_fluent_add_24_regular)
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable!!.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)


        itemGroups.add(
            CommandItemGroup()
                .addItem(
                    DefaultCommandItem(
                        bitmap = bitmap,
                        contentDescription = getString(R.string.contextual_command_accessibility_add)
                    )
                )
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_mention_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_mention),
                        enabled = false
                    )
                )
        )

        itemGroups.add(
            CommandItemGroup()
                .addItem(
                    DefaultCommandItem(
                        label = getString(R.string.contextual_command_accessibility_bold),
                        contentDescription = getString(R.string.contextual_command_accessibility_bold),
                        selected = true
                    )
                )
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_text_italic_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_italic)
                    )
                )
                .addItem(
                    DefaultCommandItem(
                        label = getString(R.string.contextual_command_accessibility_underline),
                        contentDescription = getString(R.string.contextual_command_accessibility_underline)
                    )
                )
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_text_strikethrough_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_strikethrough)
                    )
                )
        )

        itemGroups.add(
            CommandItemGroup()
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_arrow_undo_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_undo),
                        selected = true
                    )
                )
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_arrow_redo_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_redo)
                    )
                )
        )

        itemGroups.add(
            CommandItemGroup()
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_text_bullet_list_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_bullet)
                    )
                )
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_text_number_list_ltr_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_list)
                    )
                )
        )

        itemGroups.add(
            CommandItemGroup()
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_link_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_link),
                        selected = true
                    )
                )
        )

        with(ccbBinding.contextualCommandBarDefault) {
            setItemGroups(itemGroups)
            setItemOnClickListener(object : CommandItem.OnItemClickListener {
                override fun onItemClick(item: CommandItem, view: View) {
                    Toast.makeText(
                        this@ContextualCommandBarActivity,
                        getString(
                            R.string.contextual_command_prompt_click_item,
                            item.getContentDescription()
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

            setItemLongClickListener(object : CommandItem.OnItemLongClickListener {
                override fun onItemLongClick(item: CommandItem, view: View): Boolean {
                    Toast.makeText(
                        this@ContextualCommandBarActivity,
                        getString(
                            R.string.contextual_command_prompt_long_click_item,
                            item.getContentDescription()
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                    return true
                }
            })

            dismissCommandItem = ContextualCommandBar.DismissCommandItem(
                icon = R.drawable.ic_fluent_keyboard_dock_24_regular,
                contentDescription = getString(R.string.contextual_command_accessibility_dismiss),
                visible = true,
                position = ContextualCommandBar.DismissItemPosition.START,
                dismissListener = {
                    Toast.makeText(
                        this@ContextualCommandBarActivity,
                        getString(R.string.contextual_command_prompt_click_dismiss),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        // Item update
        ccbBinding.insertItem.setOnClickListener {
            ccbBinding.contextualCommandBarDefault.addItemGroup(
                CommandItemGroup()
                    .addItem(
                        DefaultCommandItem(
                            icon = R.drawable.ic_fluent_add_24_regular,
                            contentDescription = getString(R.string.contextual_command_accessibility_add)
                        )
                    )
                    .addItem(
                        DefaultCommandItem(
                            icon = R.drawable.ic_fluent_mention_24_regular,
                            contentDescription = getString(R.string.contextual_command_accessibility_mention),
                            enabled = false
                        )
                    )
            )
        }
        ccbBinding.updateItem.setOnClickListener {
            val updatedItemGroup = CommandItemGroup()
                .addItem(
                    DefaultCommandItem(
                        R.drawable.ic_fluent_mention_24_regular,
                        contentDescription = getString(R.string.contextual_command_accessibility_mention),
                        enabled = false
                    )
                )
                .addItem(
                    DefaultCommandItem(
                        bitmap = bitmap,
                        contentDescription = getString(R.string.contextual_command_accessibility_add)
                    )
                )
            itemGroups[0] = updatedItemGroup
            ccbBinding.contextualCommandBarDefault.setItemGroups(itemGroups)

            (itemGroups[0].items[0] as DefaultCommandItem).setEnabled(false)
            (itemGroups[0].items[1] as DefaultCommandItem).setEnabled(true)
            (itemGroups[0].items[1] as DefaultCommandItem).setSelected(true)
            (itemGroups[1].items[2] as DefaultCommandItem).getView()?.alpha = 0.5F
            ccbBinding.contextualCommandBarDefault.notifyDataSetChanged()
        }

        // Spacing setting
        ccbBinding.contextualCommandBarGroupSpaceSeekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    ccbBinding.contextualCommandBarGroupSpaceValue.text =
                        resources.getString(R.string.contextual_command_bar_space_value, progress)

                    if (!fromUser) {
                        return
                    }

                    ccbBinding.contextualCommandBarDefault.setCommandGroupSpace(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, progress.toFloat(),
                            resources.displayMetrics
                        ).toInt()
                    )
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
        ccbBinding.contextualCommandBarItemSpaceSeekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    ccbBinding.contextualCommandBarItemSpaceValue.text =
                        resources.getString(R.string.contextual_command_bar_space_value, progress)

                    if (!fromUser) {
                        return
                    }

                    ccbBinding.contextualCommandBarDefault.setCommandItemSpace(
                        TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, progress.toFloat(),
                            resources.displayMetrics
                        ).toInt()
                    )
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }

            })
        ccbBinding.contextualCommandBarGroupSpaceSeekbar.progress = 16
        ccbBinding.contextualCommandBarItemSpaceSeekbar.progress = 2

        // Dismiss button setting
        ccbBinding.contextualCommandBarDismissPositionGroup.setOnCheckedChangeListener { _, checkedId ->
            ccbBinding.contextualCommandBarDefault.setDismissButtonPosition(
                when (checkedId) {
                    R.id.contextual_command_bar_dismiss_position_start -> ContextualCommandBar.DismissItemPosition.START
                    R.id.contextual_command_bar_dismiss_position_end -> ContextualCommandBar.DismissItemPosition.END
                    else -> ContextualCommandBar.DismissItemPosition.END
                }
            )
        }
        ccbBinding.contextualCommandBarDismissPositionEnd.isChecked = true
    }
}
