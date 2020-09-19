/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.microsoft.fluentui.R
import com.microsoft.fluentui.persistentbottomsheet.sheetItem.BottomSheetParam
import com.microsoft.fluentui.util.getMaxItemInRow
import com.microsoft.fluentui.view.TemplateView
import kotlin.math.ceil


/**
* [SheetHorizontalItemList] is used to display a list of menu items in a horizontal fixed list
*/
class SheetHorizontalItemList @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : TemplateView(context, attrs, defStyleAttr), SheetItem.OnClickListener {
    private lateinit var mItemSheet:List<SheetItem>
    private lateinit var itemListContainer:ViewGroup
    private var itemLayoutParam: BottomSheetParam.HorizontalItemLayoutParam

    var sheetItemClickListener: SheetItem.OnClickListener? = null

    init {
        itemLayoutParam = BottomSheetParam.HorizontalItemLayoutParam(
                context.resources.getDimensionPixelSize(R.dimen.fluentui_bottomsheet_horizontalItem_min_width),
                context.resources.getInteger(R.integer.fluentui_persistent_bottomsheet_max_item_row),
                R.style.TextAppearance_FluentUI_PersistentBottomSheetHorizontalItem,
                R.style.TextAppearance_FluentUI_PersistentBottomSheetHeading)
    }

    override val templateId: Int
        get() = R.layout.view_sheet_horizontal_item_list

    fun createHorizontalItemLayout(sheet: List<SheetItem>, itemLayoutParam: BottomSheetParam.HorizontalItemLayoutParam? = null) {
        this.mItemSheet = sheet
        val size = mItemSheet.size

        if (itemLayoutParam != null) {
            this.itemLayoutParam = itemLayoutParam
        }

        createHorizontalView(size)
        setTextAppearance(this.itemLayoutParam.horizontalTextAppearance)
    }

    private fun createHorizontalView(size:Int) {
        itemListContainer.removeAllViews()


        val columnCount = getMaxItemInRow(itemLayoutParam.maxItemInRow, itemLayoutParam.horizontalItemMinWidth)
        val rowCount = ceil(size.toDouble()/columnCount).toInt()

        var index = 0

        for (row in 0 until rowCount) {
            val rowWrapper = getRowWrapper(columnCount)
            for (column in 0 until columnCount) {
                if (index >= size) {
                    itemListContainer.addView(rowWrapper)
                    return
                }
                val itemView = getColumnItem(index++)
                rowWrapper.addView(itemView)
            }
            itemListContainer.addView(rowWrapper)
        }
    }

    private fun getColumnItem(index: Int): SheetHorizontalItemView {
        val itemView = SheetHorizontalItemView(context, mItemSheet[index])
        itemView.updateTextAppearanceResId(itemLayoutParam.horizontalTextAppearance)
        itemView.layoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f)
        itemView.mOnSheetItemClickListener = this
        return itemView
    }

    private fun getRowWrapper(columnCount: Int): LinearLayout {
        val listContainer = LinearLayout(context)
        listContainer.orientation = LinearLayout.HORIZONTAL
        listContainer.weightSum = columnCount.toFloat()
        listContainer.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        return listContainer
    }


    override fun onTemplateLoaded() {
        super.onTemplateLoaded()
        itemListContainer = findViewInTemplateById(R.id.sheet_item_list)!!
    }

    override fun onSheetItemClick(item: SheetItem) {
        sheetItemClickListener?.onSheetItemClick(item)
    }

    fun setTextAppearance(resId: Int) {
        for (i in 0 until itemListContainer.childCount) {
            val wrapperLayout = itemListContainer.getChildAt(i) as ViewGroup
            for (index in 0 until wrapperLayout.childCount) {
                (wrapperLayout.getChildAt(index) as SheetHorizontalItemView).updateTextAppearanceResId(resId)
            }
        }
    }
}

class SheetItem(val id: Int = -1, @DrawableRes val drawable: Int, val title: String, val customImage: ImageView? = null) {
    interface OnClickListener {
        fun onSheetItemClick(item: SheetItem)
    }
}