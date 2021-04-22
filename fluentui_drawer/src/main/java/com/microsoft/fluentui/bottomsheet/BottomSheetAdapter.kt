/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.bottomsheet

import android.content.Context
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.RecyclerView
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.microsoft.fluentui.bottomsheet.BottomSheetItem.Companion.NO_ID
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.util.createImageView

class BottomSheetAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var onBottomSheetItemClickListener: BottomSheetItem.OnClickListener? = null

    private val context: Context
    private val items: List<BottomSheetItem>
    private val themeId: Int
    @StyleRes private val textAppearance: Int
    @StyleRes private val subTextAppearance: Int

    constructor(context: Context,
                items: List<BottomSheetItem>,
                @StyleRes themeId: Int,
                @StyleRes textAppearance: Int = 0,
                @StyleRes subTextAppearance: Int = 0) {
        this.context = context
        this.items = items
        this.themeId = themeId
        this.textAppearance = textAppearance
        this.subTextAppearance = subTextAppearance
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        if (themeId != 0) {
            val contextThemeWrapper = ContextThemeWrapper(parent.context, themeId)
            inflater = inflater.cloneInContext(contextThemeWrapper)
        }

        val itemView = inflater.inflate(R.layout.view_bottom_sheet_item, parent, false)
        return BottomSheetItemViewHolder(itemView as ListItemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? BottomSheetItemViewHolder)?.setBottomSheetItem(items[position])
    }

    override fun getItemCount(): Int = items.size

    private inner class BottomSheetItemViewHolder : RecyclerView.ViewHolder {
        private val listItemView: ListItemView

        constructor(itemView: ListItemView) : super(itemView) {
            listItemView = itemView
        }

        fun setBottomSheetItem(item: BottomSheetItem) {
            listItemView.title = item.title
            listItemView.subtitle = item.subtitle
            listItemView.setTag(R.id.fluentui_bottom_sheet_item_divider, item.useDivider)
            listItemView.layoutDensity = ListItemView.LayoutDensity.COMPACT
            listItemView.background = R.drawable.bottom_sheet_item_ripple_background
            if (textAppearance != 0) {
                listItemView.titleStyleRes = textAppearance
            }
            if (subTextAppearance != 0) {
                listItemView.subTitleStyleRes = subTextAppearance
            }
            if (item.customBitmap != null) {
                listItemView.customView = context.createImageView(item.customBitmap)
            } else if (item.imageId != NO_ID) {
                listItemView.customView = context.createImageView(item.imageId, item.getImageTint(context))
            }
            listItemView.setOnClickListener {
                onBottomSheetItemClickListener?.onBottomSheetItemClick(item)
            }
        }
    }
}