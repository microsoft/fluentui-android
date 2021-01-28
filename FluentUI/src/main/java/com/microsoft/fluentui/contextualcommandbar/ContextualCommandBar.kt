/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import com.microsoft.fluentui.R
import com.microsoft.fluentui.util.isVisible

class ContextualCommandBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var dismissButtonContainer: ViewGroup? = null
    private var commandItemAdapter: CommandItemAdapter? = null
    private var commandItemRecyclerView: RecyclerView? = null

    var dismissCommandItem: DismissCommandItem? = null
        set(value) {
            field = value

            updateDismissButton()
        }

    fun setItemOnClickListener(listener: CommandItemAdapter.OnItemClickListener) {
        commandItemAdapter?.itemClickListener = listener
    }

    private fun updateDismissButton() {
        dismissCommandItem ?: return
        val icon = dismissCommandItem!!.getIcon()
        if (icon == 0) {
            return
        }
        val dismissItemVisible = dismissCommandItem!!.visible
        val dismissItemGravity = dismissCommandItem!!.position

        var dismissButton: ImageView? = null
        var dismissButtonDivider: View? = null

        if (dismissButtonContainer == null) {
            dismissButtonContainer = LayoutInflater.from(context)
                    .inflate(R.layout.view_dismiss_command_item, null) as ViewGroup
            addView(dismissButtonContainer)
            dismissButton = dismissButtonContainer!!.findViewById(R.id.dismiss_command_item_button)
            dismissButtonDivider = dismissButtonContainer!!.findViewById(R.id.dismiss_command_item_divider)
        }
        (dismissButtonContainer!!.layoutParams as LayoutParams).apply {
            height = MATCH_PARENT
            width = WRAP_CONTENT

            gravity = when (dismissCommandItem!!.position) {
                DismissItemPosition.START -> Gravity.START
                DismissItemPosition.END -> Gravity.END
            }
        }
        if (dismissItemGravity == DismissItemPosition.START) {
            dismissButtonDivider!!.setBackgroundResource(
                    R.drawable.contextual_command_bar_dismiss_button_divider_start_background
            )
            dismissButtonContainer!!.removeAllViews()
            dismissButtonContainer!!.addView(dismissButton)
            dismissButtonContainer!!.addView(dismissButtonDivider)
        } else if (dismissItemGravity == DismissItemPosition.END) {
            dismissButtonDivider!!.setBackgroundResource(
                    R.drawable.contextual_command_bar_dismiss_button_divider_end_background
            )
            dismissButtonContainer!!.removeAllViews()
            dismissButtonContainer!!.addView(dismissButtonDivider)
            dismissButtonContainer!!.addView(dismissButton)
        }

        dismissButton!!.setImageResource(icon)
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
        commandItemRecyclerView?.setPaddingRelative(
                if (dismissItemGravity == DismissItemPosition.START) dismissButtonPlaceholder else 0,
                0,
                if (dismissItemGravity == DismissItemPosition.END) dismissButtonPlaceholder else 0,
                0)
        commandItemRecyclerView?.clipToPadding = false
        bringChildToFront(dismissButtonContainer)
    }

    private fun initializeCommandItemRecyclerViewIfNeed() {
        if (commandItemRecyclerView != null) {
            return
        }

        commandItemRecyclerView = RecyclerView(context)
        commandItemAdapter = CommandItemAdapter(context)
        commandItemRecyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        commandItemRecyclerView?.adapter = commandItemAdapter
        addView(commandItemRecyclerView)
    }

    fun setItemGroups(itemGroups: ArrayList<CommandItemGroup>) {
        initializeCommandItemRecyclerViewIfNeed()

        commandItemAdapter?.commandItemGroups = itemGroups
        commandItemAdapter?.notifyDataSetChanged()
    }

    fun addItemGroup(itemGroup: CommandItemGroup) {
        initializeCommandItemRecyclerViewIfNeed()

        commandItemAdapter?.addItemGroup(itemGroup)
        commandItemAdapter?.notifyDataSetChanged()
    }

    fun notifyDataSetChanged() {
        commandItemAdapter?.notifyDataSetChanged()
        updateDismissButton()
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
