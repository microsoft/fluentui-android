package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import android.view.View
import com.microsoft.fluentui.persistentbottomsheet.SheetItem

/**
 * Persistent Bottom sheet layout and item params
 */
class BottomSheetParam {

    internal data class ContentParam(val listOfItemList: ArrayList<ItemTypeList>,
                                     val dividerHeight: Int,
                                     var listener: SheetItem.OnClickListener? = null,
                                     var layoutResId: Int? = null,
                                     var childContent: View? = null) {
        fun add(itemTypeList: ItemTypeList) {
            listOfItemList.add(itemTypeList)
        }
    }


    internal data class ItemLayoutParam(val defaultPeekHeight: Int,
                                        val itemInRow: Int,
                                        @StyleRes val horizontalTextAppearance: Int,
                                        @StyleRes val verticalItemTextAppearance: Int,
                                        @StyleRes val verticalSubTextAppearance: Int,
                                        @StyleRes val headerTextAppearance: Int)

    data class HorizontalItemLayoutParam(val itemsInRow: Int,
                                         @StyleRes val horizontalTextAppearance: Int,
                                         @StyleRes val headerTextAppearance: Int)

    data class VerticalItemLayoutParam(@StyleRes val verticalItemTextAppearance: Int,
                                       @StyleRes val verticalSubTextAppearance: Int,
                                       @StyleRes val headerTextAppearance: Int)

    // marker interface
    internal interface ItemTypeList

    // data class for list of horizontal Items
    internal data class HorizontalItemList(val horizontalItemSheet: List<SheetItem>, val header: String?) : ItemTypeList{
        val count = horizontalItemSheet.size
    }

    // data class for list of horizontal  grid Items
    internal data class HorizontalGridItemList(val horizontalItemSheet: List<SheetItem>, val header: String?) : ItemTypeList

    // data class for list of vertical Items
    internal data class VerticalItemList(val verticalItemSheet: List<SheetItem>, val header: String?) : ItemTypeList

    //data class for div
    internal data class DividerItemType(val pixelHeight: Int, @ColorRes val dividerColor: Int) : ItemTypeList

}

