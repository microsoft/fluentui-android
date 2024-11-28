/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.popupmenu

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import com.microsoft.fluentui.menus.R
import kotlin.math.max

internal class PopupMenuAdapter : BaseAdapter {
    private val context: Context
    private val items: ArrayList<PopupMenuItem>
    private val itemCheckableBehavior: PopupMenu.ItemCheckableBehavior
    private val onItemClickListener: PopupMenuItem.OnClickListener

    constructor(
        context: Context,
        items: ArrayList<PopupMenuItem>,
        itemCheckableBehavior: PopupMenu.ItemCheckableBehavior,
        onItemClickListener: PopupMenuItem.OnClickListener
    ) : super() {
        this.context = context
        this.items = items
        this.itemCheckableBehavior = itemCheckableBehavior
        this.onItemClickListener = onItemClickListener
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): PopupMenuItem? = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView as? PopupMenuItemView ?: PopupMenuItemView(context)
        val item = getItem(position) ?: return view

        view.itemCheckableBehavior = itemCheckableBehavior
        view.setMenuItem(item)
        view.setOnClickListener {
            onItemClickListener.onPopupMenuItemClicked(item)
            announceItemStateForAccessibility(item, it)
        }

        return view
    }

    fun calculateWidth(): Int {
        var maxWidth = 0
        var minWidth = context.resources.getDimension(R.dimen.fluentui_popup_menu_item_min_width_no_icon).toInt()
        val minWidthWithIcon = context.resources.getDimension(R.dimen.fluentui_popup_menu_item_min_width_icon).toInt()
        val listView = ListView(context)

        for (itemViewIndex in 0 until count) {
            val itemView = getView(itemViewIndex, null, listView)

            if (itemView is PopupMenuItemView && itemView.iconResourceId != null)
                minWidth = minWidthWithIcon

            itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            maxWidth = max(maxWidth, itemView.measuredWidth)
        }

        return max(minWidth, maxWidth)
    }

    private fun announceItemStateForAccessibility(item: PopupMenuItem, itemView: View) {
        val announcementResourceId = if (itemCheckableBehavior == PopupMenu.ItemCheckableBehavior.NONE)
            R.string.popup_menu_accessibility_item_click_selected
        else
            if (item.isChecked)
                R.string.popup_menu_accessibility_item_click_checked
            else
                R.string.popup_menu_accessibility_item_click_unchecked

        itemView.announceForAccessibility(context.resources.getString(announcementResourceId, item.title))
    }
}