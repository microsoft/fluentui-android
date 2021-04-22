package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.bottomsheet.BottomSheetAdapter
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import com.microsoft.fluentui.bottomsheet.BottomSheetItem.*
import com.microsoft.fluentui.bottomsheet.BottomSheetItemDivider
import com.microsoft.fluentui.persistentbottomsheet.SheetItem

internal class VerticalViewProvider(val context: Context) : IViewProvider {

    override fun getContentView(itemTypeList: BottomSheetParam.ItemTypeList,
                                itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                contentParam: BottomSheetParam.ContentParam): View {
        val verticalItemList = itemTypeList as BottomSheetParam.VerticalItemList
        val view = LayoutInflater.from(context).inflate(R.layout.vertical_bottomsheet_content, null)
        val headerText = view.findViewById<TextView>(R.id.header_text)
        val recyclerView = view.findViewById<RecyclerView>(R.id.vertical_list)

        if (itemTypeList.header.isNullOrEmpty()) {
            headerText.visibility = View.GONE
        } else {
            headerText.visibility = View.VISIBLE
            headerText.text = itemTypeList.header
        }
        recyclerView.layoutManager =
            LinearLayoutManager(context)
        val verticalItemAdapter = BottomSheetAdapter(context, getVerticalItemList(verticalItemList, itemLayoutParam), R.style.Theme_FluentUI_Drawer)

        contentParam.listener?.apply {
            val listener = this
            verticalItemAdapter.onBottomSheetItemClickListener = object : OnClickListener {
                override fun onBottomSheetItemClick(item: BottomSheetItem) {
                    listener.onSheetItemClick(SheetItem(item.id, item.title, item.imageId, item.imageTint, item.customBitmap))
                }
            }
        }

        recyclerView.adapter = verticalItemAdapter
        recyclerView.addItemDecoration(BottomSheetItemDivider(context))
        return view
    }

    private fun getVerticalItemList(itemTypeList: BottomSheetParam.VerticalItemList,
                                    itemLayoutParam: BottomSheetParam.ItemLayoutParam): List<BottomSheetItem> {
        return itemTypeList.verticalItemSheet.filter {
            it.id != 0
        }.map {
            if (it.tint!=null) {
                BottomSheetItem(it.id, it.drawable, it.title, customBitmap = it.bitmap, imageTint = it.tint, imageTintType = ImageTintType.CUSTOM)
            }else{
                BottomSheetItem(it.id, it.drawable, it.title, customBitmap = it.bitmap, imageTintType = ImageTintType.NONE)
            }
        }
    }

    override fun isSingleLineContent(itemTypeList: BottomSheetParam.ItemTypeList,
                                     itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                     contentParam: BottomSheetParam.ContentParam): Boolean {
        val verticalItemList = itemTypeList as BottomSheetParam.VerticalItemList
        return verticalItemList.verticalItemSheet.size == 1
    }


}