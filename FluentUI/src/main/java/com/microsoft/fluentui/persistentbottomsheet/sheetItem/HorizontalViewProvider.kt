package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.microsoft.fluentui.R
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalItemList

/**
 * horizontal view provider for persistent bottom sheet
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
                        itemLayoutParam.horizontalItemMinWidth,
                        itemLayoutParam.maxItemInRow,
                        itemLayoutParam.horizontalTextAppearance,
                        itemLayoutParam.headerTextAppearance))
        horizontalListView.sheetItemClickListener = contentParam.listener
        return view
    }
}