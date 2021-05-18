/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.view.View.NO_ID
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat.SELECTION_MODE_SINGLE
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.persistentbottomsheet.sheetItem.BottomSheetParam
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.view.TemplateView
import kotlin.math.ceil


/**
* [SheetHorizontalItemList] is used to display a list of menu items in a horizontal fixed list
*/
open class SheetHorizontalItemList @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : TemplateView(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Drawer), attrs, defStyleAttr), SheetItem.OnClickListener {
    private lateinit var itemSheet:List<SheetItem>
    private lateinit var itemListContainer:ViewGroup
    private var rowCount: Int = 0
    private var columnCount: Int = 0
    private var itemLayoutParam: BottomSheetParam.HorizontalItemLayoutParam

    var sheetItemClickListener: SheetItem.OnClickListener? = null

    init {
        itemLayoutParam = BottomSheetParam.HorizontalItemLayoutParam(
                context.resources.getInteger(R.integer.fluentui_persistent_bottomsheet_max_item_row),
                R.style.TextAppearance_FluentUI_PersistentBottomSheetHorizontalItem,
                R.style.TextAppearance_FluentUI_PersistentBottomSheetHeading)
    }

    override val templateId: Int
        get() = R.layout.view_sheet_horizontal_item_list

    fun createHorizontalItemLayout(sheet: List<SheetItem>, itemLayoutParam: BottomSheetParam.HorizontalItemLayoutParam? = null) {
        this.itemSheet = sheet
        val size = itemSheet.size

        if (itemLayoutParam != null) {
            this.itemLayoutParam = itemLayoutParam
        }

        createHorizontalView(size)
        setTextAppearance(this.itemLayoutParam.horizontalTextAppearance)
    }

    private fun createHorizontalView(size: Int) {
        itemListContainer.removeAllViews()


        columnCount = itemLayoutParam.itemsInRow
        rowCount = ceil(size.toDouble() / columnCount).toInt()

        var index = 0

        setCollectionAccessibility(size)

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

    /**
     * sets collection accessibility for list or Grid
     */
    private fun setCollectionAccessibility(size: Int) {
        itemListContainer.setAccessibilityDelegate(object : AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val collectionInfo = AccessibilityNodeInfo.CollectionInfo
                        .obtain(rowCount,
                                minOf(columnCount, size),
                                false,
                                SELECTION_MODE_SINGLE)
                info?.collectionInfo = collectionInfo
            }
        })
    }

    private fun getColumnItem(index: Int): SheetHorizontalItemView {
        val itemView = SheetHorizontalItemView(context, itemSheet[index])
        itemView.updateTextAppearanceResId(itemLayoutParam.horizontalTextAppearance)
        itemView.layoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f)
        itemView.onSheetItemClickListener = this
        return itemView
    }

    protected open fun getRowWrapper(columnCount: Int): LinearLayout {
        val listContainer = LinearLayout(context)
        listContainer.orientation = LinearLayout.HORIZONTAL
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


class SheetItem @JvmOverloads constructor(val id: Int, val title: String = "", @DrawableRes val drawable: Int, @ColorInt val tint: Int? = null, val bitmap: Bitmap? = null) {

    // just a  convenient constructor
    @JvmOverloads
    constructor(id: Int, title: String = "", bitmap: Bitmap) : this(id, title, NO_ID, null, bitmap)

    interface OnClickListener {
        fun onSheetItemClick(item: SheetItem)
    }
}