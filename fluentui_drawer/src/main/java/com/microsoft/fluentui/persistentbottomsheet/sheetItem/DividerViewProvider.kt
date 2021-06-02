package com.microsoft.fluentui.persistentbottomsheet.sheetItem

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.LinearLayout
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.util.ThemeUtil

/**
 * class provides a view for divider height and color configurable
 */
internal class DividerViewProvider(val context: Context) : IViewProvider {

    override fun getContentView(itemTypeList: BottomSheetParam.ItemTypeList,
                                itemLayoutParam: BottomSheetParam.ItemLayoutParam,
                                contentParam: BottomSheetParam.ContentParam): View {
        val divider = itemTypeList as BottomSheetParam.DividerItemType
        val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, divider.pixelHeight)
        param.topMargin = context.resources.getDimensionPixelSize(R.dimen.fluentui_persistent_bottomsheet_divider_margin_vertical)
        param.bottomMargin = context.resources.getDimensionPixelSize(R.dimen.fluentui_persistent_bottomsheet_divider_margin_vertical)
        val view = View(context)
        view.layoutParams = param
        if (divider.dividerColor == 0) {
            view.setBackgroundColor(ThemeUtil.getColor(context, R.attr.fluentuiBottomSheetDividerColor))
        } else {
            view.setBackgroundColor(ContextCompat.getColor(context, divider.dividerColor))
        }
        return view
    }

}