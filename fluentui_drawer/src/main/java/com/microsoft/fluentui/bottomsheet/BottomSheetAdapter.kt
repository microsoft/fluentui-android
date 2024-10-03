/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.bottomsheet

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.StyleRes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.fluentui.bottomsheet.BottomSheetItem.Companion.NO_ID
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.createImageView

class BottomSheetAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    var onBottomSheetItemClickListener: BottomSheetItem.OnClickListener? = null

    private val context: Context
    private val items: MutableList<BottomSheetItem>
    private val themeId: Int
    @StyleRes private val textAppearance: Int
    @StyleRes private val subTextAppearance: Int

    constructor(context: Context,
                items: MutableList<BottomSheetItem>,
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
        (holder as? BottomSheetItemViewHolder)?.setBottomSheetItem(items[position], position, items.size)
    }

    /**
     * updates data in this adapter with help of diff utils
     */
    fun updateDataList(newDataList:MutableList<BottomSheetItem>){
        val result = DiffUtil.calculateDiff(SheetItemsDiffCallback(items, newDataList))
        items.clear()
        items.addAll(newDataList)
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = items.size

    private inner class BottomSheetItemViewHolder : RecyclerView.ViewHolder {
        private val listItemView: ListItemView

        constructor(itemView: ListItemView) : super(itemView) {
            listItemView = itemView
        }

        fun setBottomSheetItem(item: BottomSheetItem, position: Int, size: Int) {
            listItemView.title = item.title
            listItemView.subtitle = item.subtitle
            listItemView.setTag(R.id.fluentui_bottom_sheet_item_divider, item.useDivider)
            listItemView.layoutDensity = ListItemView.LayoutDensity.COMPACT
            listItemView.background = R.drawable.bottom_sheet_item_ripple_background
            listItemView.disabled = item.disabled
            if (textAppearance != 0) {
                listItemView.titleStyleRes = textAppearance
            }
            if (subTextAppearance != 0) {
                listItemView.subTitleStyleRes = subTextAppearance
            }

            var image: ImageView ?= null
            if (item.customBitmap != null) {
                image = context.createImageView(item.customBitmap)
            } else if (item.imageId != NO_ID) {
                image = context.createImageView(item.imageId, item.getImageTint(context))
            }
            if (image != null && item.disabled)
                image.imageAlpha = ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context, R.style.Theme_FluentUI_Drawer), R.attr.fluentuiBottomSheetDisabledIconColor)
            listItemView.customView = image

            var accessoryView: View ?= null
            var accessoryImageView: ImageView ?= null
            if (item.customAccessoryView != null) {
                accessoryView = item.customAccessoryView
            } else if (item.accessoryBitmap != null) {
                accessoryImageView = context.createImageView(item.accessoryBitmap)
            } else if (item.accessoryImageId != NO_ID) {
                accessoryImageView = context.createImageView(item.accessoryImageId, item.getImageTint(context))
            }
            if (accessoryView != null) {
                accessoryView.isEnabled = !item.disabled
            } else if (accessoryImageView != null && item.disabled) {
                accessoryImageView.imageAlpha =
                    ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context, R.style.Theme_FluentUI_Drawer), R.attr.fluentuiBottomSheetDisabledIconColor)
            }
            listItemView.customAccessoryView = accessoryView ?: accessoryImageView

            listItemView.setOnClickListener {
                onBottomSheetItemClickListener?.onBottomSheetItemClick(item)
            }
            ViewCompat.setAccessibilityDelegate(listItemView,
                object : AccessibilityDelegateCompat() {
                    override fun onInitializeAccessibilityNodeInfo(
                        v: View,
                        info: AccessibilityNodeInfoCompat
                    ) {
                        super.onInitializeAccessibilityNodeInfo(v, info)
                        info.roleDescription = item.roleDescription
                        if(size>1) {
                            info.hintText = (position + 1).toString() + " of " + size
                        }
                    }
                }
            )
        }
    }

    /**
     * class compares old and new list
     * considers items same when id is matched
     * considers item's content same when each property is matched in BottomSheet
     * see equals method of BottomSheet
     */
    class SheetItemsDiffCallback(private val oldList:List<BottomSheetItem>, private val newList:List<BottomSheetItem>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }
}