package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalGridItemList

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
        val view = LayoutInflater.from(context).inflate(R.layout.persistent_bottomsheet_horizontal_grid, null)
        val headerText = view.findViewById<TextView>(R.id.header_text)
        val horizontalGridView = view.findViewById<SheetHorizontalGridItemList>(R.id.horizontal_grid)

        if (itemTypeList.header.isNullOrEmpty()) {
            headerText.visibility = View.GONE
        } else {
            headerText.visibility = View.VISIBLE
            headerText.text = itemTypeList.header
        }
        horizontalGridView.createHorizontalItemLayout(list.horizontalItemSheet,
                BottomSheetParam.HorizontalItemLayoutParam(
                        itemLayoutParam.itemInRow,
                        itemLayoutParam.horizontalTextAppearance,
                        itemLayoutParam.headerTextAppearance))
        horizontalGridView.sheetItemClickListener = contentParam.listener
        return view

    }

    override fun isSingleLineContent(itemTypeList: BottomSheetParam.ItemTypeList,
                                     itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                     contentParam: BottomSheetParam.ContentParam): Boolean {
        val list = itemTypeList as BottomSheetParam.HorizontalGridItemList
        return list.horizontalItemSheet.size <= itemLayoutParam.itemInRow
    }

}