package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.Toast
import com.microsoft.fluentui.contextualcommandbar.CommandItem
import com.microsoft.fluentui.contextualcommandbar.CommandItemGroup
import com.microsoft.fluentui.contextualcommandbar.ContextualCommandBar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_contextual_command_bar.contextual_command_bar

class ContextualCommandBarActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_contextual_command_bar

    enum class FormatCommand(@DrawableRes private val icon: Int) : CommandItem {
        ADD(R.drawable.ic_fluent_add_24_regular),
        MENTION(R.drawable.ic_fluent_mention_24_regular),

        BOLD(R.drawable.ic_fluent_text_bold_24_filled) {
            override val enabled: Boolean
                get() = false
        },
        ITALIC(R.drawable.ic_fluent_text_italic_24_regular),
        UNDERLINE(R.drawable.ic_fluent_text_underline_24_regular) {
            override val selected: Boolean
                get() = true
        },
        STRIKETHROUGH(R.drawable.ic_fluent_text_strikethrough_24_regular),

        UNDO(R.drawable.ic_fluent_arrow_undo_24_regular),
        REDO(R.drawable.ic_fluent_arrow_redo_24_regular),

        BULLET(R.drawable.ic_fluent_text_bullet_list_24_regular),
        NUMBERING(R.drawable.ic_fluent_text_number_list_ltr_24_regular),
        LINK(R.drawable.ic_fluent_link_24_regular);

        override fun getIcon(): Int {
            return this.icon
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val itemGroups = arrayListOf<CommandItemGroup>()

        itemGroups.add(CommandItemGroup()
                .addItem(FormatCommand.ADD)
                .addItem(FormatCommand.MENTION)
        )

        itemGroups.add(CommandItemGroup()
                .addItem(FormatCommand.BOLD)
                .addItem(FormatCommand.ITALIC)
                .addItem(FormatCommand.UNDERLINE)
                .addItem(FormatCommand.STRIKETHROUGH)
        )

        itemGroups.add(CommandItemGroup()
                .addItem(FormatCommand.UNDO)
                .addItem(FormatCommand.REDO)
        )

        itemGroups.add(CommandItemGroup()
                .addItem(FormatCommand.BULLET)
                .addItem(FormatCommand.NUMBERING)
        )

        itemGroups.add(CommandItemGroup()
                .addItem(FormatCommand.LINK)
        )

        contextual_command_bar.setItemGroups(itemGroups)
        contextual_command_bar.itemClickListener = object : ContextualCommandBar.OnItemClickListener {
            override fun onItemClick(item: CommandItem, view: View) {
                if (item is FormatCommand) {
                    Toast.makeText(this@ContextualCommandBarActivity, "Click ${item.name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}