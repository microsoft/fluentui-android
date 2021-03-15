package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class SheetHorizontalGridItemList @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SheetHorizontalItemList(context, attrs, defStyleAttr) {

    override fun getRowWrapper(columnCount: Int): LinearLayout {
        val listContainer = LinearLayout(context)
        listContainer.orientation = LinearLayout.HORIZONTAL
        listContainer.weightSum = columnCount.toFloat()
        listContainer.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        return listContainer
    }

}