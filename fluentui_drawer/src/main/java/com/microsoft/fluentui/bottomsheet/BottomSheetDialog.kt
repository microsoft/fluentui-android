/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.bottomsheet

import android.content.Context
import androidx.annotation.StyleRes
import android.view.View
import android.view.Window
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.createImageView
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.listitem.ListSubHeaderView
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.drawer.DrawerDialog
import com.microsoft.fluentui.drawer.databinding.ViewBottomSheetBinding
import com.microsoft.fluentui.util.isVisible

/**
 * [BottomSheetDialog] is used to display a list of menu items in a modal dialog.
 */
class BottomSheetDialog : DrawerDialog, BottomSheetItem.OnClickListener {
    var onItemClickListener: BottomSheetItem.OnClickListener? = null

    private var clickedItem: BottomSheetItem? = null

    @JvmOverloads
    constructor(context: Context, items: ArrayList<BottomSheetItem>, headerItem: BottomSheetItem? = null, @StyleRes theme: Int = 0) : super(context, BehaviorType.BOTTOM, theme) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        val binding = ViewBottomSheetBinding.inflate(layoutInflater, drawerContent, false)

        val adapter = BottomSheetAdapter(this.context, items, theme)
        adapter.onBottomSheetItemClickListener = this
        binding.bottomSheetItems.adapter = adapter
        binding.bottomSheetItems.addItemDecoration(BottomSheetItemDivider(context))

        headerItem?.let {
            binding.bottomSheetHeaderContent.addView(createHeader(it))
            binding.bottomSheetHeaderContent.visibility = View.VISIBLE
            binding.bottomSheetHeaderDivider.isVisible = !isSingleLineHeader(it)
        }

        setContentView(binding.root)
    }

    private fun createHeader(headerItem: BottomSheetItem): View {
        return if (isSingleLineHeader(headerItem))
            createSingleLineHeader(headerItem)
        else
            createDoubleLineHeader(headerItem)
    }

    private fun createSingleLineHeader(headerItem: BottomSheetItem): View {
        val headerView = ListSubHeaderView(context)
        headerView.titleColor = ListSubHeaderView.TitleColor.SECONDARY
        headerView.title = headerItem.title
        headerView.background = ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiBottomSheetBackgroundColor)
        return headerView
    }

    private fun createDoubleLineHeader(headerItem: BottomSheetItem): View {
        val headerView = ListItemView(context)
        if (headerItem.imageId != BottomSheetItem.NO_ID)
            headerView.customView = context.createImageView(headerItem.imageId, headerItem.getImageTint(context))
        headerView.title = headerItem.title
        headerView.subtitle = headerItem.subtitle
        headerView.background = R.drawable.bottom_sheet_header_background
        return headerView
    }

    private fun isSingleLineHeader(headerItem: BottomSheetItem): Boolean {
        return headerItem.imageId == BottomSheetItem.NO_ID && headerItem.subtitle.isEmpty()
    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        clickedItem = item
        collapse()
    }

    override fun dismiss() {
        clickedItem?.let {
            onItemClickListener?.onBottomSheetItemClick(it)
            clickedItem = null
        }

        super.dismiss()
    }
}