/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import android.content.Context
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import com.microsoft.fluentui.ccb.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.isVisible

class ContextualCommandBar @JvmOverloads constructor(
        appContext: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_ContextualCommandBar), attrs, defStyleAttr) {

    private var dismissButtonContainer: ViewGroup? = null
    private var commandItemAdapter: CommandItemAdapter
    private var commandItemRecyclerView: RecyclerView

    var dismissCommandItem: DismissCommandItem? = null
        set(value) {
            field = value

            updateDismissButton()
        }

    init {
        var groupSpace = resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_group_space)
        var itemSpace = resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_item_space)
        attrs?.let {
            val styledAttributes = appContext.theme.obtainStyledAttributes(
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
            } finally {
                styledAttributes.recycle()
            }
        }

        commandItemAdapter = CommandItemAdapter(
                CommandItemAdapter.CommandListOptions(groupSpace, itemSpace)
        )
        commandItemRecyclerView = RecyclerView(
            context
        ).apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = commandItemAdapter
            overScrollMode = View.OVER_SCROLL_NEVER
        }
        addView(commandItemRecyclerView)
    }

    fun setItemOnClickListener(listener: CommandItem.OnItemClickListener) {
        commandItemAdapter.itemClickListener = listener
    }

    fun setItemGroups(itemGroups: ArrayList<CommandItemGroup>) {
        commandItemAdapter.commandItemGroups = itemGroups
        commandItemAdapter.notifyDataSetChanged()
    }

    fun addItemGroup(itemGroup: CommandItemGroup) {
        commandItemAdapter.addItemGroup(itemGroup)
        commandItemAdapter.notifyDataSetChanged()
    }

    /**
     * Set the space between each [CommandItemGroup]
     */
    fun setCommandGroupSpace(@Dimension(unit = Dimension.PX) space: Int) {
        commandItemAdapter.setGroupSpace(space)
        commandItemAdapter.notifyDataSetChanged()
    }

    /**
     * Set the space between each [CommandItem]
     */
    fun setCommandItemSpace(@Dimension(unit = Dimension.PX) space: Int) {
        commandItemAdapter.setItemSpace(space)
        commandItemAdapter.notifyDataSetChanged()
    }

    /**
     * Set dismiss button position, see [DismissItemPosition]
     */
    fun setDismissButtonPosition(position: DismissItemPosition) {
        dismissCommandItem?.position = position
        updateDismissButton()
    }

    /**
     * Notify any registered observers that the data set has changed, including
     * Command item list and dismiss button
     */
    fun notifyDataSetChanged() {
        commandItemAdapter.notifyDataSetChanged()
        updateDismissButton()
    }

    private fun updateDismissButton() {
        dismissCommandItem ?: return
        val icon = dismissCommandItem!!.getIcon()
        if (icon == 0) {
            return
        }
        val dismissItemVisible = dismissCommandItem!!.visible
        val dismissItemGravity = dismissCommandItem!!.position

        if (dismissButtonContainer == null) {
            dismissButtonContainer = LayoutInflater.from(context)
                    .inflate(R.layout.view_dismiss_command_item, null) as ViewGroup
            addView(dismissButtonContainer)
        }
        val dismissButton: ImageView = dismissButtonContainer!!.findViewById(R.id.dismiss_command_item_button)
        val dismissButtonDivider: View = dismissButtonContainer!!.findViewById(R.id.dismiss_command_item_divider)
        (dismissButtonContainer!!.layoutParams as LayoutParams).apply {
            height = MATCH_PARENT
            width = WRAP_CONTENT

            gravity = when (dismissCommandItem!!.position) {
                DismissItemPosition.START -> Gravity.START
                DismissItemPosition.END -> Gravity.END
            }
        }

        // Set the position of DismissItemPosition
        if (dismissItemGravity == DismissItemPosition.START) {
            dismissButtonDivider.setBackgroundResource(
                    R.drawable.contextual_command_bar_dismiss_button_divider_start_background
            )
            dismissButtonContainer!!.removeAllViews()
            dismissButtonContainer!!.addView(dismissButton)
            dismissButtonContainer!!.addView(dismissButtonDivider)
        } else if (dismissItemGravity == DismissItemPosition.END) {
            dismissButtonDivider.setBackgroundResource(
                    R.drawable.contextual_command_bar_dismiss_button_divider_end_background
            )
            dismissButtonContainer!!.removeAllViews()
            dismissButtonContainer!!.addView(dismissButtonDivider)
            dismissButtonContainer!!.addView(dismissButton)
        }

        dismissButton.setImageResource(icon)
        dismissButton.contentDescription = dismissCommandItem!!.getContentDescription()
        dismissButtonContainer!!.isVisible = dismissItemVisible
        dismissButtonContainer!!.setOnClickListener {
            dismissCommandItem!!.dismissListener?.invoke()
        }

        // Adjust RecyclerView's position to adapt dismiss button
        val dismissButtonPlaceholder = if (dismissItemVisible) {
            resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_dismiss_button_width
            ) + resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_dismiss_gap_width
            )
        } else 0
        commandItemRecyclerView.setPaddingRelative(
                if (dismissItemGravity == DismissItemPosition.START) dismissButtonPlaceholder else 0,
                0,
                if (dismissItemGravity == DismissItemPosition.END) dismissButtonPlaceholder else 0,
                0)
        commandItemRecyclerView.clipToPadding = false

        // Bring this dismiss button on the front of command list
        bringChildToFront(dismissButtonContainer)
    }

    class DismissCommandItem(
            @DrawableRes private var icon: Int = 0,
            private var contentDescription: String? = null,
            var visible: Boolean = true,
            var position: DismissItemPosition = DismissItemPosition.END,
            var dismissListener: (() -> Unit)? = null
    ) : CommandItem {

        override fun getIcon(): Int {
            return icon
        }

        override fun getContentDescription(): String? {
            return contentDescription
        }
    }

    enum class DismissItemPosition {
        START, END
    }
}
