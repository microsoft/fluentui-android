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
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat.CollectionInfoCompat.SELECTION_MODE_NONE
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

    /**
     * public api to refresh horizontal views
     * this is a best effort based API it checks and
     * updates items which are required to be updated.
     * Also it adds oonly neccessary new view or remoove just extra view
     */
    fun refreshHorizontalItems(itemSheet: List<SheetItem>) {
        if (this.itemSheet.size != itemSheet.size) {
            addRemoveExtraSize(itemSheet.size - this.itemSheet.size)
            this.itemSheet = itemSheet
            updateAllItems(itemSheet)
            return
        } else {
            val oldSheetItems = this.itemSheet
            this.itemSheet = itemSheet
            for (index in itemSheet.indices) {
                if (oldSheetItems[index] != this.itemSheet[index]) {
                    updateItem(index)
                }
            }
        }
    }


    /**
     * add / remove extra items of size @param - size - if > 0 add else <0 remove
     */
    private fun addRemoveExtraSize(size: Int) {
        if (size > 0) {
            addPlaceHolderItems(size)
        } else if (size < 0) {
            removeExtraItems(size)
        }
    }

    private fun updateAllItems(newSheetItems: List<SheetItem>) {
        for (index in newSheetItems.indices){
            updateItem(index)
        }
    }

    private fun updateItem(sheetIndex: Int) {
        val rowColumnPair = getRowColumn(sheetIndex)
        val rowWrapper = itemListContainer.getChildAt(rowColumnPair.first) as ViewGroup
        (rowWrapper.getChildAt(rowColumnPair.second) as SheetHorizontalItemView).update(itemSheet[sheetIndex])
    }

    /**
     * adds place holder views when more items to be added via update.
     * This needs a follow up call to update items afterwards
     */
    private fun addPlaceHolderItems(size: Int) {
        var rowWrapper = itemListContainer.getChildAt(itemListContainer.childCount - 1) as ViewGroup
        var counter = 0
        while (counter++ < size) {
            if (rowWrapper.childCount == columnCount) {
                // when child in a row reaches column count(which is max)
                // add a new row to the upper container first
                rowWrapper = getRowWrapper(columnCount)
                itemListContainer.addView(rowWrapper)
            }
            // adds a view in row
            rowWrapper.addView(getColumnItem(itemListContainer.childCount - 1, 0))
        }
        // update row count
        rowCount = itemListContainer.childCount
    }

    private fun removeExtraItems(size: Int) {
        var rowWrapper = itemListContainer.getChildAt(itemListContainer.childCount - 1) as ViewGroup
        var counter = size
        while (counter++ < 0) {
            if (rowWrapper.childCount == 0) {
                // delete from previous row
                val previousRowWrapper = itemListContainer.getChildAt(itemListContainer.indexOfChild(rowWrapper) - 1) as ViewGroup
                // remove  row which is exhausted
                itemListContainer.removeView(rowWrapper)
                rowWrapper = previousRowWrapper
            }
            rowWrapper.removeView(rowWrapper.getChildAt(rowWrapper.childCount - 1))
        }
        // if at the end of removing children row does not have any child remove it
        if (rowWrapper.childCount == 0) {
            itemListContainer.removeView(rowWrapper)
        }
        // update row count
        rowCount = itemListContainer.childCount
    }

    private fun getRowColumn(index: Int): Pair<Int, Int> {
        return Pair(index / columnCount, index % columnCount)
    }

    private fun createHorizontalView(size: Int) {
        itemListContainer.removeAllViews()


        columnCount = itemLayoutParam.itemsInRow
        rowCount = ceil(size.toDouble() / columnCount).toInt()

        var index = 0

        setCollectionAccessibility(itemListContainer,size)

        for (row in 0 until rowCount) {
            val rowWrapper = getRowWrapper(columnCount)
            for (column in 0 until columnCount) {
                if (index >= size) {
                    itemListContainer.addView(rowWrapper)
                    return
                }
                val itemView = getColumnItem(row, index++)
                rowWrapper.addView(itemView)
            }
            itemListContainer.addView(rowWrapper)
        }
    }

    /**
     * sets collection accessibility for list or Grid
     */
    private fun setCollectionAccessibility(view: ViewGroup, size: Int) {
        ViewCompat.setAccessibilityDelegate(view, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat?) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val collectionInfo = AccessibilityNodeInfoCompat.CollectionInfoCompat
                        .obtain(rowCount,
                                minOf(columnCount, size),
                                false,
                                SELECTION_MODE_NONE)
                info?.setCollectionInfo(collectionInfo)
            }
        })
    }

    /**
     * set collection item info of the children
     * for accessibility
     */
    private fun setChildAccessibilityCollectionItemInfo(container: View, rowIndex: Int, columnIndex: Int) {
        ViewCompat.setAccessibilityDelegate(container, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfoCompat?) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info?.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(
                        rowIndex,
                        /*row span*/1,
                        columnIndex,
                        /*column span*/1,
                        /*heading*/false,
                        /*selected*/false
                ))
            }
        })
    }

    private fun getColumnItem(rowIndex: Int, index: Int): SheetHorizontalItemView {
        val itemView = SheetHorizontalItemView(context, itemSheet[index])
        itemView.updateTextAppearanceResId(itemLayoutParam.horizontalTextAppearance)
        itemView.layoutParams = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f)
        itemView.onSheetItemClickListener = this
        itemView.addTemplateLoadListener(object : SheetHorizontalItemView.ChildItemInteractionListener {
            override fun onChildTemplateLoaded(container: View) {
                setChildAccessibilityCollectionItemInfo(container,rowIndex, index)
            }
        })
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


class SheetItem @JvmOverloads constructor(val id: Int, var title: String = "", @DrawableRes var drawable: Int, @ColorInt var tint: Int? = null, var bitmap: Bitmap? = null) {

    // just a  convenient constructor
    @JvmOverloads
    constructor(id: Int, title: String = "", bitmap: Bitmap) : this(id, title, NO_ID, null, bitmap)

    interface OnClickListener {
        fun onSheetItemClick(item: SheetItem)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SheetItem

        if (id != other.id) return false
        if (title != other.title) return false
        if (drawable != other.drawable) return false
        if (tint != other.tint) return false
        if (bitmap != other.bitmap) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + drawable
        result = 31 * result + (tint ?: 0)
        result = 31 * result + (bitmap?.hashCode() ?: 0)
        return result
    }
}