/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.demos.views.Cell
import com.microsoft.fluentuidemo.demos.views.CellOrientation
import kotlinx.android.synthetic.main.activity_template_view.*
import kotlinx.android.synthetic.main.template_cell_vertical.view.*

class TemplateViewActivity : DemoActivity() {
    companion object {
        const val LIST_ITEM_COUNT = 1000
    }

    override val contentLayoutId: Int
        get() = R.layout.activity_template_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        horizontal_cell.setOnClickListener { onCellClicked(it as Cell) }
        vertical_cell.setOnClickListener { onCellClicked(it as Cell) }

        template_list_view.adapter = TemplateListViewAdapter()
        template_list_view.layoutManager =
            LinearLayoutManager(this)
        template_list_view.setHasFixedSize(true)

        regular_list_view.adapter = RegularListViewAdapter()
        regular_list_view.layoutManager =
            LinearLayoutManager(this)
        regular_list_view.setHasFixedSize(true)

        calculate_cells_button.setOnClickListener {
            val t = measureAndLayoutViews(createView = {
                val cell = Cell(this)
                cell.orientation = CellOrientation.VERTICAL
                return@measureAndLayoutViews cell
            })
            println("Cell.M&L: $t")
            calculate_cells_button.text = getString(R.string.calculate_cells) + " = $t ms"
            calculate_cells_button.announceForAccessibility(" took $t ms to load")
        }

        calculate_layouts_button.setOnClickListener {
            val t = measureAndLayoutViews(createView = {
                // Emulation of Cell code without extra ViewGroup (Cell itself)
                val cell = layoutInflater.inflate(R.layout.template_cell_vertical, null)
                /*val titleView = */cell.findViewById(R.id.cell_title) as TextView
                /*val descriptionView = */cell.findViewById(R.id.cell_description) as TextView
                return@measureAndLayoutViews cell
            })
            println("Layout.M&L: $t")
            calculate_layouts_button.text = getString(R.string.calculate_layouts) + " = $t ms"
            calculate_layouts_button.announceForAccessibility(" took $t ms to load")
        }
    }

    private fun measureAndLayoutViews(createView: () -> View): Long {
        val t1 = System.nanoTime()

        for (i in 1..100) {
            val cell = createView()
            cell.requestLayout()
            cell.measure(0, 0)
            cell.layout(0, 0, cell.measuredWidth, cell.measuredHeight)
        }

        val t2 = System.nanoTime()
        return (t2 - t1) / 1000000
    }

    private fun onCellClicked(cell: Cell) {
        cell.orientation = when (cell.orientation) {
            CellOrientation.HORIZONTAL -> {
                cell.announceForAccessibility(getString(R.string.vertical_layout))
                CellOrientation.VERTICAL
            }
            CellOrientation.VERTICAL -> {
                cell.announceForAccessibility(getString(R.string.horizontal_layout))
                CellOrientation.HORIZONTAL
            }
        }
    }

    // Template list view adapter

    private class TemplateListViewAdapter :
        RecyclerView.Adapter<TemplateListViewAdapter.ViewHolder>() {
        override fun getItemCount(): Int = LIST_ITEM_COUNT

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val cell = Cell(parent.context)
            cell.orientation = CellOrientation.VERTICAL
            cell.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            return ViewHolder(cell)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.cell.apply {
                title = "Title $position"
                description = "Description $position"
            }
        }

        class ViewHolder(val cell: Cell) : RecyclerView.ViewHolder(cell)
    }

    // Regular list view adapter

    private class RegularListViewAdapter :
        RecyclerView.Adapter<RegularListViewAdapter.ViewHolder>() {
        override fun getItemCount(): Int = LIST_ITEM_COUNT

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val cell = LayoutInflater.from(parent.context)
                .inflate(R.layout.template_cell_vertical, parent, false)
            cell.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            return ViewHolder(cell)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.apply {
                titleView.text = "Title $position"
                descriptionView.text = "Description $position"
            }
        }

        class ViewHolder(cell: View) : RecyclerView.ViewHolder(cell) {
            val titleView: TextView = cell.cell_title
            val descriptionView: TextView = cell.cell_description
        }
    }
}