package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalItemList

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
        horizontalListView.createHorizontalItemLayout(list.horizontalItemSheet,
                BottomSheetParam.HorizontalItemLayoutParam(
                        itemLayoutParam.itemInRow,
                        itemLayoutParam.horizontalTextAppearance,
                        itemLayoutParam.headerTextAppearance))
        horizontalListView.sheetItemClickListener = contentParam.listener
        return view
    }

    override fun isSingleLineContent(itemTypeList: BottomSheetParam.ItemTypeList,
                                     itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                     contentParam: BottomSheetParam.ContentParam): Boolean {
        val list = itemTypeList as BottomSheetParam.HorizontalItemList
        return list.horizontalItemSheet.size <= itemLayoutParam.itemInRow
    }
}