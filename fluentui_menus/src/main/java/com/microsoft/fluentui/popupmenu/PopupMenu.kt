/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.popupmenu

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.ListPopupWindow
import android.view.KeyEvent
import android.view.View
import com.microsoft.fluentui.menus.R
import com.microsoft.fluentui.popupmenu.PopupMenu.ItemCheckableBehavior
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

/**
 * [PopupMenu] is a transient UI that displays a list of options. The popup appears from a view that
 * it's anchored to and over other content. The popup will appear below the anchor view
 * if there is room, or above it if there is not. The layout grows to fit the longest item in the list.
 *
 * [PopupMenu] supports [PopupMenuItem]s with a title, icon, divider, and [ItemCheckableBehavior],
 * and handles their click listener.
 *
 * TODO potential future work:
 * - Single item is checkable
 * - Specific groups are checkable
 * - Construct a menu from an inflated menu layout
 */

class PopupMenu : ListPopupWindow, PopupMenuItem.OnClickListener {
    companion object {
        internal val DEFAULT_ITEM_CHECKABLE_BEHAVIOR = ItemCheckableBehavior.NONE
    }

    /**
     *  [ItemCheckableBehavior] defines how you turn options on and off, using a checkbox for
     *  stand-alone options, or radio buttons for groups of mutually exclusive options.
     */
    enum class ItemCheckableBehavior {
        // No items are checkable
        NONE,
        // Only one item from the group can be checked (radio buttons)
        SINGLE,
        // All items can be checked (checkboxes)
        ALL
    }

    /**
     * Click listener for the [PopupMenuItem].
     */
    var onItemClickListener: PopupMenuItem.OnClickListener? = null

    private val context: Context
    private val adapter: PopupMenuAdapter
    private val items: ArrayList<PopupMenuItem>
    private val itemCheckableBehavior: ItemCheckableBehavior

    constructor(
        context: Context,
        anchorView: View,
        items: ArrayList<PopupMenuItem>,
        itemCheckableBehavior: ItemCheckableBehavior = DEFAULT_ITEM_CHECKABLE_BEHAVIOR
    ) : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Menus)) {
        this.context = context
        this.anchorView = anchorView
        this.items = items
        this.itemCheckableBehavior = itemCheckableBehavior

        adapter = PopupMenuAdapter(context, items, itemCheckableBehavior, this)
        setAdapter(adapter)

        setBackgroundDrawable(ContextCompat.getDrawable(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Menus), R.drawable.popup_menu_background))

        isModal = true
        width = adapter.calculateWidth()
    }

    override fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem) {
        when (itemCheckableBehavior) {
            ItemCheckableBehavior.NONE -> { }
            ItemCheckableBehavior.SINGLE -> setSingleChecked(popupMenuItem)
            ItemCheckableBehavior.ALL -> setChecked(popupMenuItem)
        }

        onItemClickListener?.onPopupMenuItemClicked(popupMenuItem)
        (context as? PopupMenuItem.OnClickListener)?.onPopupMenuItemClicked(popupMenuItem)

        if (itemCheckableBehavior != ItemCheckableBehavior.ALL)
            dismiss()
    }

    private fun setChecked(item: PopupMenuItem) {
        item.isChecked = !item.isChecked
        adapter.notifyDataSetChanged()
    }

    private fun setSingleChecked(item: PopupMenuItem) {
        items.forEach {
            it.isChecked = it == item
        }

        adapter.notifyDataSetChanged()
    }

    override fun show() {
        super.show()
        listView?.apply {
            isFocusableInTouchMode = true
            setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    selectedView?.performClick()
                    return@setOnKeyListener true
                } else if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
                    dismiss()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener onKeyDown(keyCode, event)
            }
        }
    }
}