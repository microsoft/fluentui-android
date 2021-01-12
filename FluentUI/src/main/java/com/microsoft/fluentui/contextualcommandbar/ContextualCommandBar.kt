package com.microsoft.fluentui.contextualcommandbar

import android.content.Context
import android.content.res.ColorStateList
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import com.microsoft.fluentui.R
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.isVisible

class ContextualCommandBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    // Layout configurations
    private var groupSpace = 0
    private var itemSpace = 0
    private var itemPaddingVertical = 0
    private var itemPaddingHorizontal = 0

    private var horizontalScrollView = HorizontalScrollView(context)
    private var commandContainer = LinearLayout(context)
    private var dismissButton: ImageView? = null
    private var dismissButtonContainer: LinearLayout? = null

    // Dismiss button configurations
    var showDismiss = false
        set(value) {
            field = value
            setDismissButtonVisible(value)
        }
    @DrawableRes
    var dismissIcon: Int = 0
        set(value) {
            if (value == 0) {
                return
            }

            field = value
            setDismissButtonVisible(showDismiss)
            dismissButton?.setImageResource(dismissIcon)
        }

    var dismissListener: (() -> Unit)? = null
    var itemClickListener: OnItemClickListener? = null

    init {
        removeAllViews()
        addView(horizontalScrollView)
        horizontalScrollView.addView(commandContainer)

        with(commandContainer) {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        with(horizontalScrollView) {
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
        }

        attrs?.let {
            val styledAttributes = context.theme.obtainStyledAttributes(
                    it,
                    R.styleable.ContextualCommandBar,
                    0,
                    0
            )

            try {
                groupSpace = styledAttributes.getDimensionPixelSize(
                        R.styleable.ContextualCommandBar_groupSpace,
                        resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_group_space)
                )
                itemSpace = styledAttributes.getDimensionPixelSize(
                        R.styleable.ContextualCommandBar_itemSpace,
                        resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_item_space)
                )
                showDismiss = styledAttributes.getBoolean(
                        R.styleable.ContextualCommandBar_showDismiss,
                        false
                )
            } finally {
                styledAttributes.recycle()
            }

            itemPaddingVertical = resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_default_item_padding_vertical
            )
            itemPaddingHorizontal = resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_default_item_padding_horizontal
            )
        }

        setDismissButtonVisible(showDismiss)
    }

    private fun addDismissButton() {
        // Container
        dismissButtonContainer = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        addView(dismissButtonContainer)
        (dismissButtonContainer!!.layoutParams as LayoutParams).apply {
            height = ViewGroup.LayoutParams.MATCH_PARENT
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            gravity = Gravity.END
        }

        // Dismiss gradient gap
        val gradientGap = View(context).apply {
            background = ContextCompat.getDrawable(
                    context,
                    R.drawable.contextual_command_bar_dismiss_button_gap_background
            )
        }
        dismissButtonContainer!!.addView(gradientGap)
        gradientGap.layoutParams.apply {
            width = resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_dismiss_gap_width)
            height = ViewGroup.LayoutParams.MATCH_PARENT
        }

        // Dismiss button
        itemPaddingVertical = resources.getDimensionPixelSize(
                R.dimen.fluentui_contextual_command_bar_default_item_padding_vertical
        )
        itemPaddingHorizontal = resources.getDimensionPixelSize(
                R.dimen.fluentui_contextual_command_bar_default_item_padding_horizontal
        )
        dismissButton = ImageView(context).apply {
            setPaddingRelative(
                    itemPaddingHorizontal,
                    itemPaddingVertical,
                    itemPaddingHorizontal,
                    itemPaddingVertical
            )
            setBackgroundColor(ThemeUtil.getColor(
                    context,
                    R.attr.fluentuiContextualCommandBarDismissBackgroundColor
            ))
            imageTintList = ColorStateList.valueOf(ThemeUtil.getColor(
                    context,
                    R.attr.fluentuiContextualCommandBarDismissIconTintColor
            ))

            setOnClickListener {
                dismissListener?.invoke()
            }
        }
        dismissButtonContainer!!.addView(dismissButton)
        dismissButton!!.layoutParams.apply {
            height = ViewGroup.LayoutParams.MATCH_PARENT
            width = resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_dismiss_button_width
            )
        }

        bringChildToFront(dismissButtonContainer)
    }

    private fun setDismissButtonVisible(visible: Boolean) {
        if (dismissIcon == 0) {
            // Skip if there is no icon
            return
        }
        if (visible && dismissButton == null) {
            addDismissButton()
        }

        val paddingEnd = if (visible) {
            resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_dismiss_button_width
            ) + resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_dismiss_gap_width
            )
        } else 0
        commandContainer.setPaddingRelative(0, 0, paddingEnd, 0)
        dismissButtonContainer?.isVisible = visible
    }

    fun setItemGroups(itemGroups: List<CommandItemGroup>) {
        commandContainer.removeAllViews()

        for ((groupIdx, itemGroup) in itemGroups.withIndex()) {
            val items = itemGroup.items
            if (items.isEmpty()) {
                continue
            }

            val groupContainer = LinearLayout(context)
            commandContainer.addView(groupContainer)
            if (groupIdx != itemGroups.size - 1) {
                (groupContainer.layoutParams as LinearLayout.LayoutParams).apply {
                    marginEnd = groupSpace
                }
            }

            addItemViewsToGroup(items, groupContainer)
        }
    }

    private fun addItemViewsToGroup(items: List<CommandItem>, groupContainer: ViewGroup) {
        for ((idx, item) in items.withIndex()) {
            val itemView = ImageView(context).apply {
                setPaddingRelative(
                        itemPaddingHorizontal,
                        itemPaddingVertical,
                        itemPaddingHorizontal,
                        itemPaddingVertical
                )
                setImageResource(item.getIcon())
                isSelected = item.isSelected() && item.isEnabled()
                isEnabled = item.isEnabled()
                contentDescription = item.getContentDescription()

                background = when {
                    items.size == 1 -> {
                        ContextCompat.getDrawable(
                                context,
                                R.drawable.contextual_command_bar_single_item_background
                        )
                    }
                    idx == 0 -> {
                        ContextCompat.getDrawable(
                                context,
                                R.drawable.contextual_command_bar_start_item_background
                        )
                    }
                    idx == items.size - 1 -> {
                        ContextCompat.getDrawable(
                                context,
                                R.drawable.contextual_command_bar_end_item_background
                        )
                    }
                    else -> {
                        ContextCompat.getDrawable(
                                context,
                                R.drawable.contextual_command_bar_middle_item_background
                        )
                    }
                }
                imageTintList = AppCompatResources.getColorStateList(
                        context,
                        R.color.contextual_command_bar_icon_tint
                )

                setOnClickListener {
                    itemClickListener?.onItemClick(item, it)
                }
            }

            groupContainer.addView(itemView)

            if (idx != items.size - 1) {
                (itemView.layoutParams as LinearLayout.LayoutParams).apply {
                    marginEnd = itemSpace
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: CommandItem, view: View)
    }
}