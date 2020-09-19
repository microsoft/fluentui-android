package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

internal class PersistentBottomSheetContentViewProvider(private val context: Context,
                                                        private val contentParam: BottomSheetParam.ContentParam) {

    fun getSheetContentView(container: LinearLayout, itemLayoutParam: BottomSheetParam.ItemLayoutParam): View {
        if (contentParam.layoutResId != null) {
            val view = LayoutInflater.from(context).inflate(contentParam.layoutResId!!, container, false)
            container.addView(view)
            return container
        }
        if (contentParam.listOfItemList.isEmpty()) {
            return container
        }
        contentParam.listOfItemList.forEach {
            container.addView(getProvider(context, it).getContentView(it,itemLayoutParam,contentParam))
        }
        return container
    }

    companion object {

        internal fun getProvider(context: Context, itemList: BottomSheetParam.ItemTypeList): IViewProvider {
            if (itemList is BottomSheetParam.HorizontalItemList) {
                return HorizontalViewProvider(context)
            }
            if (itemList is BottomSheetParam.VerticalItemList) {
                return VerticalViewProvider(context)
            }
            if (itemList is BottomSheetParam.DividerItemType) {
                return DividerViewProvider(context)
            }
            throw UnsupportedOperationException("Type of item is not supported for : " + itemList.javaClass.canonicalName)
        }

    }
}


internal interface IViewProvider {
    fun getContentView(itemTypeList: BottomSheetParam.ItemTypeList,
                       itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                       contentParam: BottomSheetParam.ContentParam): View
}