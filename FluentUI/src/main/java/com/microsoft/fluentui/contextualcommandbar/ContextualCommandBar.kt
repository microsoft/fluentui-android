/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import android.content.Context
import android.content.res.ColorStateList
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
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
    private var itemPaddingVertical = 0
    private var itemPaddingHorizontal = 0

    private var dismissButton: ImageView? = null
    private var dismissButtonContainer: LinearLayout? = null
    private var commandItemAdapter: CommandItemAdapter? = null
    private var commandItemRecyclerView: RecyclerView? = null

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

    fun setItemOnClickListener(listener: CommandItemAdapter.OnItemClickListener) {
        commandItemAdapter?.itemClickListener = listener
    }

    private fun addDismissButton() {
        // Container
        dismissButtonContainer = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
        }
        addView(dismissButtonContainer)
        (dismissButtonContainer!!.layoutParams as LayoutParams).apply {
            height = MATCH_PARENT
            width = WRAP_CONTENT
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
            height = MATCH_PARENT
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
            height = MATCH_PARENT
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

        val dismissButtonPlaceholder = if (visible) {
            resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_dismiss_button_width
            ) + resources.getDimensionPixelSize(
                    R.dimen.fluentui_contextual_command_bar_dismiss_gap_width
            )
        } else 0
        commandItemRecyclerView?.setPaddingRelative(0, 0, dismissButtonPlaceholder, 0)
        commandItemRecyclerView?.clipToPadding = false

        dismissButtonContainer?.isVisible = visible
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
    }
}