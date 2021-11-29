package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalGridItemList
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalItemList
import com.microsoft.fluentui.persistentbottomsheet.SheetItem

/**
 * A horizontal grid view provider returns a horizontal view which
 * has a fix grid size of items and it populates them in each line
 * equally spaced
 *
 *  ex.  8 items in a list and itemsInRow count is 5
 *   if(** is a view) then will be shown like
 *
 *        **  **  **  **  **  **
 *           **     **     **
 *
 */
internal class HorizontalViewProvider(val context: Context) : IViewProvider {

    override fun getContentView(itemTypeList: BottomSheetParam.ItemTypeList,
                                itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                contentParam: BottomSheetParam.ContentParam): View {
        val list = itemTypeList as BottomSheetParam.HorizontalItemList
        val view = LayoutInflater.from(context).inflate(R.layout.horizontal_bottomsheet_content, null)
        val headerText = view.findViewById<TextView>(R.id.header_text)
        val horizontalListView = view.findViewById<SheetHorizontalItemList>(R.id.horizontal_list)

        if (itemTypeList.header.isNullOrEmpty()) {
            headerText.visibility = View.GONE
        } else {
            headerText.visibility = View.VISIBLE
            headerText.text = itemTypeList.header
        }
        horizontalListView.createHorizontalItemLayout(getHorizontalItemList(list),
                BottomSheetParam.HorizontalItemLayoutParam(
                        itemLayoutParam.itemInRow,
                        itemLayoutParam.horizontalTextAppearance,
                        itemLayoutParam.headerTextAppearance))
        horizontalListView.sheetItemClickListener = object : SheetItem.OnClickListener {
            override fun onSheetItemClick(item: SheetItem) {
                val originalItem = list.horizontalItemSheet.find {
                    it.id == item.id
                } ?: throw IllegalStateException("Sheet Item data has been changed and refresh was not called on BottoomSheet")
                contentParam.listener?.onSheetItemClick(originalItem)
            }

        }
        return view
    }

    /**
     * updates previously built component
     * component is self capable of updating only items which have changed
     */
    override fun updateComponentView(itemTypeList: BottomSheetParam.ItemTypeList, view: View) {
        val horizontalGridView = view.findViewById<SheetHorizontalItemList>(R.id.horizontal_list)
        val newList = getHorizontalItemList(itemTypeList as BottomSheetParam.HorizontalItemList)
        horizontalGridView.refreshHorizontalItems(newList)
    }
    override fun isSingleLineContent(itemTypeList: BottomSheetParam.ItemTypeList,
                                     itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                     contentParam: BottomSheetParam.ContentParam): Boolean {
        val list = itemTypeList as BottomSheetParam.HorizontalItemList
        return list.horizontalItemSheet.size <= itemLayoutParam.itemInRow
    }

    /**
     * creates a copy of list to be used to compare to existing list to change only required items
     */
    private fun getHorizontalItemList(itemTypeList: BottomSheetParam.HorizontalItemList): List<SheetItem> {
        return itemTypeList.horizontalItemSheet.filter {
            it.id != 0
        }.map {
            SheetItem(it.id, it.title, it.drawable, it.tint, it.bitmap, it.contentDescription)
        }
    }
}