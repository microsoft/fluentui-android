package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalGridItemList
import com.microsoft.fluentui.persistentbottomsheet.SheetItem

/**
 * A horizontal grid view provider returns a horizontal view which
 * has a fix grid size of items and it populates them from start to end.
 *   ex.  8 items in a grid when itemsinRow count is 5
 *   if(** is a view) then will be shown like
 *
 *        **  **  **  **  **  **
 *        **  **  **
 *
 */
internal class HorizontalGridProvider(val context: Context) : IViewProvider {

    override fun getContentView(itemTypeList: BottomSheetParam.ItemTypeList,
                                itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                contentParam: BottomSheetParam.ContentParam): View {
        val list = itemTypeList as BottomSheetParam.HorizontalGridItemList
        val view = LayoutInflater.from(context).inflate(R.layout.fluent_ui_persistent_bottomsheet_horizontal_grid, null)
        val headerText = view.findViewById<TextView>(R.id.fluent_ui_header_text)
        val horizontalGridView = view.findViewById<SheetHorizontalGridItemList>(R.id.fluent_ui_horizontal_grid)

        if (itemTypeList.header.isNullOrEmpty()) {
            headerText.visibility = View.GONE
        } else {
            headerText.visibility = View.VISIBLE
            headerText.text = itemTypeList.header
        }
        horizontalGridView.createHorizontalItemLayout(getHorizontalItemList(list),
                BottomSheetParam.HorizontalItemLayoutParam(
                        itemLayoutParam.itemInRow,
                        itemLayoutParam.horizontalTextAppearance,
                        itemLayoutParam.headerTextAppearance))

        horizontalGridView.sheetItemClickListener = object : SheetItem.OnClickListener {
            override fun onSheetItemClick(item: SheetItem) {
                val originalItem = list.horizontalItemSheet.find {
                    it.id == item.id
                }
                        ?: throw IllegalStateException("Sheet Item data has been changed and refresh was not called")
                contentParam.listener?.onSheetItemClick(originalItem)
            }
        }

        return view

    }

    /**
     * updates previusly built compoonent
     * component is self capable of updating only items which have changed
     */
    override fun updateComponentView(itemTypeList: BottomSheetParam.ItemTypeList, view: View) {
        val horizontalGridView = view.findViewById<SheetHorizontalGridItemList>(R.id.fluent_ui_horizontal_grid)
        val newList = getHorizontalItemList(itemTypeList as BottomSheetParam.HorizontalGridItemList)
        horizontalGridView.refreshHorizontalItems(newList)
    }

    override fun isSingleLineContent(itemTypeList: BottomSheetParam.ItemTypeList,
                                     itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                     contentParam: BottomSheetParam.ContentParam): Boolean {
        val list = itemTypeList as BottomSheetParam.HorizontalGridItemList
        return list.horizontalItemSheet.size <= itemLayoutParam.itemInRow
    }

    /**
     * creates a copy of list to be used to compare to existing list to change only required items
     */
    private fun getHorizontalItemList(itemTypeList: BottomSheetParam.HorizontalGridItemList): List<SheetItem> {
        return itemTypeList.horizontalItemSheet.filter {
            it.id != 0
        }.map {
            SheetItem(it.id, it.title, it.drawable, it.tint, it.bitmap)
        }
    }

}