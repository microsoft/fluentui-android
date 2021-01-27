/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import com.microsoft.fluentui.R

class CommandItemAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemSpace = context.resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_item_space)
    private var groupSpace = context.resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_group_space)
    private var itemPaddingVertical = context.resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_item_padding_vertical)
    private var itemPaddingHorizontal = context.resources.getDimensionPixelSize(R.dimen.fluentui_contextual_command_bar_default_item_padding_horizontal)

    private var flattenCommandItems = arrayListOf<CommandItem>()
    var commandItemGroups = arrayListOf<CommandItemGroup>()
        set(value) {
            field = value

            flatItemGroup()
        }

    var itemClickListener: OnItemClickListener? = null

    private fun flatItemGroup() {
        flattenCommandItems.clear()
        commandItemGroups.forEach {
            flattenCommandItems.addAll(it.items)
        }
    }

    fun addItemGroup(itemGroup: CommandItemGroup) {
        commandItemGroups.add(itemGroup)

        flatItemGroup()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ImageView(parent.context))
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        val viewHolder = vh as ViewHolder
        val commandItem = flattenCommandItems[position]

        with(viewHolder.item) {
            setImageResource(commandItem.getIcon())
            setPaddingRelative(
                    itemPaddingHorizontal,
                    itemPaddingVertical,
                    itemPaddingHorizontal,
                    itemPaddingVertical
            )

            imageTintList = AppCompatResources.getColorStateList(
                    context,
                    R.color.contextual_command_bar_icon_tint
            )
            isSelected = commandItem.isSelected() && commandItem.isEnabled()
            isEnabled = commandItem.isEnabled()
            contentDescription = commandItem.getContentDescription()
            when (viewType) {
                VIEW_TYPE_GROUP_CENTER_ITEM -> {
                    layoutParams = RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        marginEnd = itemSpace
                    }

                    background = ContextCompat.getDrawable(
                            context,
                            R.drawable.contextual_command_bar_center_item_background
                    )
                }

                VIEW_TYPE_GROUP_START_ITEM -> {
                    layoutParams = RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        marginEnd = itemSpace
                    }
                    background = ContextCompat.getDrawable(
                            context,
                            R.drawable.contextual_command_bar_start_item_background
                    )
                }

                VIEW_TYPE_GROUP_END_ITEM -> {
                    layoutParams = RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        marginEnd =  if (position == flattenCommandItems.size - 1) 0 else groupSpace
                    }

                    background = ContextCompat.getDrawable(
                            context,
                            R.drawable.contextual_command_bar_end_item_background
                    )
                }

                VIEW_TYPE_GROUP_SINGLE_ITEM -> {
                    layoutParams = RecyclerView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                        marginEnd =  if (position == flattenCommandItems.size - 1) 0 else groupSpace
                    }
                    background = ContextCompat.getDrawable(
                            context,
                            R.drawable.contextual_command_bar_single_item_background
                    )
                }
            }
        }

        viewHolder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(commandItem, viewHolder.itemView)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (flattenCommandItems.size == 0) {
            return super.getItemViewType(position)
        }
        var pendingSearch = position + 1
        for (group in commandItemGroups) {
            val itemsSize = group.items.size
            if (itemsSize == 0) {
                continue
            }

            if (pendingSearch > itemsSize) {
                pendingSearch -= itemsSize
                continue
            } else {
                return when {
                    itemsSize == 1 -> {
                        VIEW_TYPE_GROUP_SINGLE_ITEM
                    }
                    pendingSearch == 1 -> {
                        VIEW_TYPE_GROUP_START_ITEM
                    }
                    pendingSearch == itemsSize -> {
                        VIEW_TYPE_GROUP_END_ITEM
                    }
                    else -> {
                        VIEW_TYPE_GROUP_CENTER_ITEM
                    }
                }
            }
        }

        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return flattenCommandItems.size
    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: ImageView = itemView as ImageView
    }

    interface OnItemClickListener {
        fun onItemClick(item: CommandItem, view: View)
    }

    companion object {
        const val VIEW_TYPE_GROUP_CENTER_ITEM = 0
        const val VIEW_TYPE_GROUP_START_ITEM = 1
        const val VIEW_TYPE_GROUP_END_ITEM = 2
        const val VIEW_TYPE_GROUP_SINGLE_ITEM = 3
    }
}