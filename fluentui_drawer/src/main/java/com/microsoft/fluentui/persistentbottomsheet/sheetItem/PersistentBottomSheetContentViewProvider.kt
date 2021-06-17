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
            val partContainer = provider.getContentView(it, itemLayoutParam, contentParam)
            container.addView(partContainer)
            isSingleLineItem = index == 0 && provider.isSingleLineContent(it, itemLayoutParam, contentParam)
        }
        return SheetContainerInfo(container, isSingleLineItem)
    }

    /**
     * method iterates over available components in bottomsheets
     * and checks which compoenent needs a change
     */
    fun updateSheetContentView(sheetContainer: SheetContainerInfo?, componentIndex: Int = -1) {
        if (contentParam.layoutResId != null || contentParam.childContent != null) {
            return
        }
        if (componentIndex != -1) {
            updateComponent(sheetContainer, componentIndex)
        } else {
            contentParam.listOfItemList.forEachIndexed { index, it ->
                updateComponent(sheetContainer, index)
            }
        }
    }

    /**
     * ask particular viewprovider too check and update if required
     */
    private fun updateComponent(sheetContainer: SheetContainerInfo?, componentIndex: Int) {
        val component = sheetContainer?.Container?.getChildAt(componentIndex)
        component?.apply {
            val itemTypeList = contentParam.listOfItemList[componentIndex]
            val provider = getProvider(component.context, itemTypeList)
            provider.updateComponentView(itemTypeList, this)
        }
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

    fun updateComponentView(itemTypeList: BottomSheetParam.ItemTypeList, view: View);

    fun isSingleLineContent(itemTypeList: BottomSheetParam.ItemTypeList,
                            itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                            contentParam: BottomSheetParam.ContentParam): Boolean {
        return false
    }
}