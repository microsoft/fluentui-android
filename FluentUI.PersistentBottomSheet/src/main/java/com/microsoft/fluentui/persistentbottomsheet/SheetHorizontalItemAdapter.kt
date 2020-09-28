/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.support.annotation.StyleRes
import android.support.v7.widget.RecyclerView
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import com.microsoft.fluentui.core.util.createImageView


/**
 * [SheetHorizontalItemAdapter] is used for horizontal list in bottomSheet
 */
class SheetHorizontalItemAdapter  : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var onSheetItemClickListener: ListItem.OnClickListener? = null
    private val items: ArrayList<ListItem>
    private val themeId: Int
    private val context: Context
    private val marginBetweenView:Int

    constructor(context: Context, items: ArrayList<ListItem>,  @StyleRes themeId: Int=0, marginBetweenView:Int=0) {
        this.items = items
        this.themeId = themeId
        this.context = context
        this.marginBetweenView = marginBetweenView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        if (themeId != 0) {
            val contextThemeWrapper = ContextThemeWrapper(parent.context, themeId)
            inflater = inflater.cloneInContext(contextThemeWrapper)
        }

        val itemView = inflater.inflate(R.layout.view_sheet_horizontal_item_adapter, parent, false)
        val layoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.rightMargin = marginBetweenView
        return PersistentSheetItemViewHolder(itemView as SheetHorizontalItemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PersistentSheetItemViewHolder)?.setBottomSheetItem(items[position])
    }

    private inner class PersistentSheetItemViewHolder : RecyclerView.ViewHolder {
        private val listItemView: SheetHorizontalItemView

        constructor(itemView: SheetHorizontalItemView) : super(itemView) {
            listItemView = itemView
        }

        fun setBottomSheetItem(item: ListItem) {
            listItemView.update(item.title, context.createImageView(item.drawable))
            listItemView.setOnClickListener {
                onSheetItemClickListener?.onSheetItemClick(item)
            }
        }
    }
}