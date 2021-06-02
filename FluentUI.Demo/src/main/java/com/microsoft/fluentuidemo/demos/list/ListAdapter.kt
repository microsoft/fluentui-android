/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos.list

import android.content.Context
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.listitem.ListSubHeaderView
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.widget.Button
import com.microsoft.fluentuidemo.R
import java.util.*

class ListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private enum class ViewType {
        SUB_HEADER, ITEM, BUTTON_ITEM
    }

    var listItems = ArrayList<IBaseListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewTypeOrdinal: Int): RecyclerView.ViewHolder {
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return when (ViewType.values()[viewTypeOrdinal]) {
            ViewType.SUB_HEADER -> {
                val subHeaderView = ListSubHeaderView(context)
                subHeaderView.layoutParams = lp
                ListSubHeaderViewHolder(subHeaderView)
            }
            ViewType.ITEM -> {
                val listItemView = ListItemView(context)
                listItemView.layoutParams = lp
                ListItemViewHolder(listItemView)
            }
            ViewType.BUTTON_ITEM -> {
                val buttonItemView = FrameLayout(context)
                buttonItemView.layoutParams = lp

                val button = Button(context)
                button.layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    context.resources.getDimension(R.dimen.fluentui_button_min_height).toInt()
                )
                buttonItemView.addView(button)

                val paddingHorizontal = context.resources.getDimension(R.dimen.default_layout_margin).toInt()
                val paddingVertical = context.resources.getDimension(R.dimen.button_list_item_vertical_padding).toInt()
                buttonItemView.setPaddingRelative(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)

                ButtonItemViewHolder(buttonItemView)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val listItem = listItems[position]

        if (listItem is IListSubHeader)
            (holder as? ListSubHeaderViewHolder)?.setListSubHeader(listItem)

        if (listItem is IListItem)
            (holder as? ListItemViewHolder)?.setListItem(listItem)

        if (listItem is IButtonItem)
            (holder as? ButtonItemViewHolder)?.setButtonItem(listItem)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as? ListItemViewHolder)?.clearCustomViews()
    }

    override fun getItemCount(): Int = listItems.size

    override fun getItemViewType(position: Int): Int {
        return when {
            listItems[position] is ListSubHeader -> ViewType.SUB_HEADER.ordinal
            listItems[position] is ButtonItem -> ViewType.BUTTON_ITEM.ordinal
            else -> ViewType.ITEM.ordinal
        }
    }

    private inner class ListItemViewHolder : RecyclerView.ViewHolder {
        private val listItemView: ListItemView

        constructor(view: ListItemView) : super(view) {
            listItemView = view
            listItemView.setOnClickListener {
                Snackbar.make(listItemView, context.resources.getString(R.string.list_item_click), Snackbar.LENGTH_SHORT).show()
            }
        }

        fun setListItem(listItem: IListItem) {
            listItemView.setListItem(listItem)
        }

        fun clearCustomViews() {
            listItemView.customView = null
            listItemView.customAccessoryView = null
        }
    }

    private class ListSubHeaderViewHolder : RecyclerView.ViewHolder {
        private val listSubHeaderView: ListSubHeaderView

        constructor(view: ListSubHeaderView) : super(view) {
            listSubHeaderView = view
        }

        fun setListSubHeader(listSubHeader: IListSubHeader) {
            listSubHeaderView.setListSubHeader(listSubHeader)
        }
    }

    private inner class ButtonItemViewHolder : RecyclerView.ViewHolder {
        private var button: Button? = null

        constructor(view: FrameLayout) : super(view) {
            button = view.getChildAt(0) as? Button ?: return
        }

        fun setButtonItem(buttonItem: IButtonItem) {
            button?.text = buttonItem.buttonText
            button?.id = buttonItem.id
            button?.setOnClickListener(buttonItem.onClickListener)
        }
    }
}

fun ListItemView.setListItem(listItem: IListItem) {
    title = listItem.title
    subtitle = listItem.subtitle
    footer = listItem.footer

    titleMaxLines = listItem.titleMaxLines
    subtitleMaxLines = listItem.subtitleMaxLines
    footerMaxLines = listItem.footerMaxLines

    titleTruncateAt = listItem.titleTruncateAt
    subtitleTruncateAt = listItem.subtitleTruncateAt
    footerTruncateAt = listItem.footerTruncateAt

    customView = listItem.customView
    customViewSize = listItem.customViewSize
    customAccessoryView = listItem.customAccessoryView
    customSecondarySubtitleView = listItem.customSecondarySubtitleView

    layoutDensity = listItem.layoutDensity
}

fun ListSubHeaderView.setListSubHeader(listSubHeader: IListSubHeader) {
    title = listSubHeader.title
    titleColor = listSubHeader.titleColor
    customAccessoryView = listSubHeader.customAccessoryView
}