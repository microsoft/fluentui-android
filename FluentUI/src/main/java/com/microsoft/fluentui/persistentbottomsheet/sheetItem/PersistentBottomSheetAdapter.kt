/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import android.support.annotation.StyleRes
import android.support.v7.widget.RecyclerView
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.microsoft.fluentui.R
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import com.microsoft.fluentui.bottomsheet.BottomSheetItem.Companion.NO_ID
import com.microsoft.fluentui.bottomsheet.getImageTint
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.util.createImageView

class PersistentBottomSheetAdapter(private val context: Context, private val items: List<BottomSheetItem>, @StyleRes private val themeId: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onBottomSheetItemClickListener: BottomSheetItem.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        if (themeId != 0) {
            val contextThemeWrapper = ContextThemeWrapper(parent.context, themeId)
            inflater = inflater.cloneInContext(contextThemeWrapper)
        }

        val itemView = inflater.inflate(R.layout.view_bottom_sheet_item, parent, false)
        return SheetItemViewHolder(itemView as ListItemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? SheetItemViewHolder)?.setBottomSheetItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    private inner class SheetItemViewHolder(itemView: ListItemView) : RecyclerView.ViewHolder(itemView) {
        private val listItemView: ListItemView = itemView

        fun setBottomSheetItem(item: BottomSheetItem) {

            listItemView.title = item.title
            listItemView.subtitle = item.subtitle
            listItemView.setTag(R.id.fluentui_bottom_sheet_item_divider, item.useDivider)
            listItemView.layoutDensity = ListItemView.LayoutDensity.REGULAR
            listItemView.background = R.drawable.bottom_sheet_item_ripple_background
            listItemView.titleStyleRes = item.titleStyleId
            listItemView.subTitleStyleRes = item.subtitleStyleId
            if (item.customImage != null) {
                listItemView.customView = item.customImage
            } else if (item.imageId != NO_ID) {
                listItemView.customView = context.createImageView(item.imageId, item.getImageTint(context))
            }

            listItemView.setOnClickListener {
                onBottomSheetItemClickListener?.onBottomSheetItemClick(item)
            }
        }
    }
}