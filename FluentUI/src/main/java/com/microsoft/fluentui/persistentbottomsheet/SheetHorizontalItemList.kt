/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.microsoft.fluentui.R
import com.microsoft.fluentui.view.TemplateView

/**
* [SheetHorizontalItemList] is used to display a list of menu items in a horizontal fixed list
*/
class SheetHorizontalItemList: TemplateView, ListItem.OnClickListener {
    lateinit var itemList:ArrayList<ListItem>
    lateinit var itemListContainer:ViewGroup

    var onSheetItemClickListener: ListItem.OnClickListener? = null

    override val templateId: Int
        get() = R.layout.view_sheet_horizontal_item_list

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    fun updateTemplate(list: ArrayList<ListItem>) {
        this.itemList = list
        val size = itemList.size

        itemListContainer.removeAllViews()
        for(i in 0 until size) {
            val itemView = SheetHorizontalItemView(context, itemList[i])
            itemView.layoutParams = LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f)
            itemView.onSheetItemClickListener = this
            itemListContainer.addView(itemView)
        }
    }

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()
        itemListContainer = findViewInTemplateById(R.id.sheet_item_list)!!
    }

    override fun onSheetItemClick(item: ListItem) {
        onSheetItemClickListener?.onSheetItemClick(item)
    }

    fun setTextAppearance(resId:Int) {
        for(i in 0 until itemListContainer.childCount) {
            (itemListContainer.getChildAt(i) as SheetHorizontalItemView).updateTextAppearanceResId(resId)
        }
    }
}

class ListItem(val id:Int = -1, @DrawableRes val drawable: Int, val title:String) {
    interface OnClickListener {
        fun onSheetItemClick(item: ListItem)
    }
}