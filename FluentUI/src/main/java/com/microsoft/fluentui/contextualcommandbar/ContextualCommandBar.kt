package com.microsoft.fluentui.contextualcommandbar

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.microsoft.fluentui.R

class ContextualCommandBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var commandContainer: LinearLayout = LinearLayout(context)

    private var groupSpace = 0
    private var itemSpace = 0
    private var itemPadding = 0
    var itemClickListener: OnItemClickListener? = null

    init {
        with(commandContainer) {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val horizontalScrollView = HorizontalScrollView(context)
        with(horizontalScrollView) {
            addView(commandContainer)
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
        }

        addView(horizontalScrollView)

        attrs?.let {
            val styledAttributes = context.theme.obtainStyledAttributes(
                    it,
                    R.styleable.ContextualCommandBar2,
                    0,
                    0
            )

            try {
                groupSpace = styledAttributes.getDimensionPixelSize(
                        R.styleable.ContextualCommandBar2_groupSpace,
                        resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_group_space)
                )

                itemSpace = styledAttributes.getDimensionPixelSize(
                        R.styleable.ContextualCommandBar2_itemSpace,
                        resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_item_space)
                )
            } finally {
                styledAttributes.recycle()
            }

            itemPadding = resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_item_padding)
        }
    }

    fun setItemGroups(itemGroups: List<CommandItemGroup>) {
        commandContainer.removeAllViews()
        setDividerSpace(commandContainer, groupSpace)

        for (itemGroup in itemGroups) {
            val items = itemGroup.items
            if (items.isEmpty()) {
                continue
            }

            val groupContainer = LinearLayout(context)
            setDividerSpace(groupContainer, itemSpace)
            commandContainer.addView(groupContainer)

            for ((idx, item) in items.withIndex()) {
                val itemView = ImageView(context).apply {
                    setPaddingRelative(itemPadding, itemPadding, itemPadding, itemPadding)
                    isSelected = item.isSelected() && item.isEnabled()
                    isEnabled = item.isEnabled()
                    contentDescription = item.getContentDescription()
                }

                itemView.setOnClickListener {
                    itemClickListener?.onItemClick(item, it)
                }

                itemView.setImageResource(item.getIcon())
                groupContainer.addView(itemView)

                itemView.background = when {
                    items.size == 1 -> {
                        ContextCompat.getDrawable(context, R.drawable.contextual_command_bar_single_item_background)
                    }
                    idx == 0 -> {
                        ContextCompat.getDrawable(context, R.drawable.contextual_command_bar_start_item_background)
                    }
                    idx == items.size - 1 -> {
                        ContextCompat.getDrawable(context, R.drawable.contextual_command_bar_end_item_background)
                    }
                    else -> {
                        ContextCompat.getDrawable(context, R.drawable.contextual_command_bar_middle_item_background)
                    }
                }
                itemView.imageTintList = AppCompatResources.getColorStateList(context, R.color.contextual_command_bar_icon_tint)
            }
        }
    }

    private fun setDividerSpace(container: LinearLayout, space: Int) {
        with(container) {
            dividerDrawable = GradientDrawable().apply {
                setSize(space, 0)
            }
            showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: CommandItem, view: View)
    }
}