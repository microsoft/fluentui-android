package com.microsoft.fluentuidemo.vNextDemos.list

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.vNext.listitem.IBaseListItem
import com.microsoft.fluentui.vNext.listitem.*
import com.microsoft.fluentuidemo.R

class ListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private enum class ViewType {
        SUB_HEADER, ITEM
    }

    var items: ArrayList<IBaseListItem> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewTypeOrdinal: Int): RecyclerView.ViewHolder {
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return when (ViewType.values()[viewTypeOrdinal]) {
            ViewType.SUB_HEADER -> {
                val subHeaderView = ListSubHeaderView(parent.context)
                subHeaderView.layoutParams = lp
                ListSubHeaderViewHolder(subHeaderView)
            }
            ViewType.ITEM -> {
                val listItemView = ListItemView(parent.context)
                listItemView.layoutParams = lp
                ListItemViewHolder(listItemView)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = items.get(position)
        if (item is ListItem) {
            (viewHolder as ListItemViewHolder).setListItem(item)
        }
        else if(item is ListSubHeaderItem){
            (viewHolder as ListSubHeaderViewHolder).setListSubHeader(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            items[position] is ListSubHeaderItem -> ViewType.SUB_HEADER.ordinal
            else -> ViewType.ITEM.ordinal
        }
    }

    inner class ListItemViewHolder : RecyclerView.ViewHolder {
        private val listItemView: ListItemView

        constructor(view: ListItemView) : super(view) {
            listItemView = view
            listItemView.setOnClickListener {
                Snackbar.make(listItemView, listItemView.resources.getString(R.string.list_item_click), Snackbar.LENGTH_SHORT).show()
            }
        }

        fun setListItem(listItem: ListItem) {
            listItemView.listItem = listItem
        }
    }

    private class ListSubHeaderViewHolder : RecyclerView.ViewHolder {
        private val listSubHeaderView: ListSubHeaderView

        constructor(view: ListSubHeaderView) : super(view) {
            listSubHeaderView = view
        }

        fun setListSubHeader(listSubHeader: ListSubHeaderItem) {
            listSubHeaderView.listSubHeaderItem = listSubHeader
        }
    }
}