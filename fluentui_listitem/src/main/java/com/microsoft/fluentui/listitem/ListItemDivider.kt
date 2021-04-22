/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.listitem

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.microsoft.fluentui.view.BaseDividerItemDecoration
import kotlin.math.ceil

/**
 * This [DividerItemDecoration] is designed to be used with [RecyclerView]s containing [ListItemView]s.
 * [ListItemDivider] will have an inset matching the text views' inset,
 * which increases at the start of the list item view when a [ListItemView.customView] is present.
 * Dividers after [ListSubHeaderView]s are removed.
 * If your view has multiple [ListSubHeaderView]s, this class draws a section divider with no inset
 * between the last list item view in the section and next section's sub header view.
 */
class ListItemDivider(context: Context, orientation: Int) : BaseDividerItemDecoration(context, orientation) {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val viewPosition = parent.getChildAdapterPosition(view)
        val previousView = parent.getChildAt(viewPosition - 1)

        outRect.top = when {
            viewPosition == 0 && view is ListSubHeaderView -> subHeaderDividerPadding.toInt()
            view is ListSubHeaderView -> ((subHeaderDividerPadding * 2) + dividerHeight).toInt()
            previousView is ListSubHeaderView -> 0
            else -> ceil(dividerHeight).toInt()
        }

        outRect.bottom = 0
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        for (listItemIndex in 1 until parent.childCount) {
            val itemView = parent.getChildAt(listItemIndex)
            val listItemView = itemView as? ListItemView
            val previousView = parent.getChildAt(listItemIndex - 1)

            // SubHeaders have no dividers.
            if (previousView is ListSubHeaderView)
                continue

            val useSectionDivider = itemView is ListSubHeaderView

            val spacerLeft = itemView.left.toFloat()
            val spacerRight = itemView.right.toFloat()

            val leftOfDivider = if (useSectionDivider) 0f else listItemView?.textAreaStartInset ?: 0f
            val rightOfDivider = itemView.right - if (useSectionDivider) 0f else listItemView?.textAreaEndInset ?: 0f

            if (useSectionDivider)
                drawTopSpacer(canvas, itemView, spacerLeft, spacerRight)

            drawDivider(canvas, itemView, leftOfDivider, rightOfDivider, useSectionDivider)

            if (useSectionDivider)
                drawBottomSpacer(canvas, itemView, spacerLeft, spacerRight)
        }
    }
}