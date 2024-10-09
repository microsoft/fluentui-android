/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.RecyclerView
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.util.createImageView


/**
 * [SheetHorizontalItemAdapter] is used for horizontal list in bottomSheet
 */
class SheetHorizontalItemAdapter(private val context: Context, items: ArrayList<SheetItem>, @StyleRes private val themeId: Int = R.style.Theme_FluentUI_Drawer, private val marginBetweenView: Int = 0, @ColorInt private val drawerTint: Int? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mOnSheetItemClickListener: SheetItem.OnClickListener? = null
    private val mItems: ArrayList<SheetItem> = items

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

    override fun getItemCount(): Int = mItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? PersistentSheetItemViewHolder)?.setBottomSheetItem(mItems[position])
    }

    private inner class PersistentSheetItemViewHolder(itemView: SheetHorizontalItemView) : RecyclerView.ViewHolder(itemView) {
        private val listItemView: SheetHorizontalItemView = itemView

        fun setBottomSheetItem(item: SheetItem) {
            item.bitmap.let {
                if (it != null) {
                    listItemView.update(item.title, context.createImageView(it), item.disabled)
                } else {
                    listItemView.update(item.title, context.createImageView(item.drawable, imageTint = drawerTint), item.disabled)
                }
            }
            listItemView.setOnClickListener {
                mOnSheetItemClickListener?.onSheetItemClick(item)
            }
        }
    }
}