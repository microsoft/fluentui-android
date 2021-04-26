package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

internal class PersistentBottomSheetContentViewProvider(private val context: Context,
                                                        private val contentParam: BottomSheetParam.ContentParam) {

    fun getSheetContentView(container: LinearLayout, itemLayoutParam: BottomSheetParam.ItemLayoutParam): SheetContainerInfo {
        if (contentParam.layoutResId != null) {
            val view = LayoutInflater.from(context).inflate(contentParam.layoutResId!!, container, false)
            container.addView(view)
            return SheetContainerInfo(container, false)
        }
        if (contentParam.childContent != null) {
            container.addView(contentParam.childContent)
            return SheetContainerInfo(container, false)
        }
        if (contentParam.listOfItemList.isEmpty()) {
            return SheetContainerInfo(container, false)
        }
        var isSingleLineItem = false
        contentParam.listOfItemList.forEachIndexed { index, it ->
            val provider = getProvider(context, it)
            container.addView(provider.getContentView(it, itemLayoutParam, contentParam))
            isSingleLineItem = index == 0 && provider.isSingleLineContent(it, itemLayoutParam, contentParam)
        }
        return SheetContainerInfo(container, isSingleLineItem)
    }

    data class SheetContainerInfo(val Container: ViewGroup, val isSingleLineItem: Boolean)

    companion object {

        internal fun getProvider(context: Context, itemTypeList: BottomSheetParam.ItemTypeList): IViewProvider {
            return when (itemTypeList) {
                is BottomSheetParam.HorizontalItemList -> HorizontalViewProvider(context)
                is BottomSheetParam.HorizontalGridItemList -> HorizontalGridProvider(context)
                is BottomSheetParam.DividerItemType -> DividerViewProvider(context)
                is BottomSheetParam.VerticalItemList -> VerticalViewProvider(context)
                else -> throw UnsupportedOperationException("Type of item is not supported for : " + itemTypeList.javaClass.canonicalName)
            }

        }

    }
}


internal interface IViewProvider {
    fun getContentView(itemTypeList: BottomSheetParam.ItemTypeList,
                       itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                       contentParam: BottomSheetParam.ContentParam): View

    fun isSingleLineContent(itemTypeList: BottomSheetParam.ItemTypeList,
                            itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                            contentParam: BottomSheetParam.ContentParam): Boolean {
        return false
    }
}