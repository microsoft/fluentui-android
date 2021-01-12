package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.microsoft.fluentui.contextualcommandbar.CommandItem
import com.microsoft.fluentui.contextualcommandbar.CommandItemGroup
import com.microsoft.fluentui.contextualcommandbar.ContextualCommandBar
import com.microsoft.fluentui.contextualcommandbar.DefaultCommandItem
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_contextual_command_bar.contextual_command_bar_default
import kotlinx.android.synthetic.main.activity_contextual_command_bar.contextual_command_bar_with_dismiss

class ContextualCommandBarActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_contextual_command_bar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemGroups = arrayListOf<CommandItemGroup>()

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_add_24_regular, "Add"))
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_mention_24_regular, "Mention", enabled = false))
        )

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_text_bold_24_filled, "Bold", selected = true))
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_text_italic_24_regular, "Italic"))
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_text_underline_24_regular, "Underline"))
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_text_strikethrough_24_regular, "Strikethrough"))
        )

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_arrow_undo_24_regular, "Undo", selected = true))
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_arrow_redo_24_regular, "Redo"))
        )

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_text_bullet_list_24_regular, "Bullet"))
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_text_number_list_ltr_24_regular, "List"))
        )

        itemGroups.add(CommandItemGroup()
                .addItem(DefaultCommandItem(R.drawable.ic_fluent_link_24_regular, "Link", false))
        )

        with(contextual_command_bar_default) {
            setItemGroups(itemGroups)
            itemClickListener = object : ContextualCommandBar.OnItemClickListener {
                override fun onItemClick(item: CommandItem, view: View) {
                    Toast.makeText(this@ContextualCommandBarActivity, "Click ${item.getContentDescription()}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        with(contextual_command_bar_with_dismiss) {
            setItemGroups(itemGroups)
            showDismiss = true
            dismissIcon = R.drawable.ic_fluent_keyboard_dock_24_regular
            dismissListener = {
                Toast.makeText(this@ContextualCommandBarActivity, "Dismiss", Toast.LENGTH_SHORT).show()
            }
            itemClickListener = object : ContextualCommandBar.OnItemClickListener {
                override fun onItemClick(item: CommandItem, view: View) {
                    Toast.makeText(this@ContextualCommandBarActivity, "Click ${item.getContentDescription()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}