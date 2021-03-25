/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.bottomsheet

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.view.BaseDividerItemDecoration
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

internal class BottomSheetItemDivider(context: Context) : BaseDividerItemDecoration(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Drawer), HORIZONTAL) {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val useDivider = view.getTag(R.id.fluentui_bottom_sheet_item_divider) as? Boolean ?: false
        if (useDivider)
            outRect.set(0, dividerHeight.toInt() + subHeaderDividerPadding.toInt() * 2, 0, 0)
        else
            outRect.setEmpty()
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (index in 0 until parent.childCount) {
            val itemView = parent.getChildAt(index)
            if (itemView is ListItemView) {
                val useDivider = itemView.getTag(R.id.fluentui_bottom_sheet_item_divider) as Boolean
                if (useDivider) {
                    val left =  itemView.left.toFloat()
                    val right = itemView.right.toFloat()
                    drawTopSpacer(canvas, itemView, left, right)
                    drawDivider(
                        canvas,
                        itemView,
                        left,
                        right,
                        true,
                        ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiBottomSheetDividerColor)
                    )
                    drawBottomSpacer(canvas, itemView, left, right)
                }
            }
        }
    }
}