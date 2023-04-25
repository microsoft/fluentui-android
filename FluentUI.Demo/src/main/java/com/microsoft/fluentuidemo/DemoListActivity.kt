/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.search.Searchbar
import com.microsoft.fluentui.util.DuoSupportUtils
import com.microsoft.fluentuidemo.databinding.ActivityDemoListBinding
import java.util.*
import kotlin.collections.ArrayList

/**
 * This activity presents a list of [Demo]s, which when touched,
 * lead to a subclass of [DemoActivity] representing demo details.
 */
class DemoListActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var searchbar: Searchbar
    var dualScreenMode = false
    private lateinit var demoListBinding: ActivityDemoListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Launch Screen: Setting the theme here removes the launch screen, which was added to this activity
        // by setting the theme to App.Launcher in the manifest.
        setTheme(R.style.AppTheme)
        demoListBinding = ActivityDemoListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        dualScreenMode = DuoSupportUtils.isDualScreenMode(this)

        setContentView(demoListBinding.root)

        setupAppBar()

        demoListBinding.demoList.adapter = DemoListAdapter()
        demoListBinding.demoList.addItemDecoration(ListItemDivider(this, DividerItemDecoration.VERTICAL))

        Initializer.init(application)
    }

    private fun setupAppBar() {
        demoListBinding.appBar.toolbar.subtitle = "SDK Version: ${BuildConfig.VERSION_NAME}"

        searchbar = Searchbar(this)
        searchbar.onQueryTextListener = this
        demoListBinding.appBar.accessoryView = searchbar
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String): Boolean {
        val userInput = query.lowercase(Locale.getDefault())
        val demoList: ArrayList<Demo> = if (dualScreenMode) DUO_DEMOS else DEMOS
        val filteredDemoList = demoList.filter { it.title.lowercase(Locale.getDefault()).contains(userInput) }

        searchbar.showSearchProgress = true

        Handler().postDelayed({
            (demoListBinding.demoList.adapter as DemoListAdapter).demos = filteredDemoList as ArrayList<Demo>
            searchbar.showSearchProgress = false
        }, 500)
        return true
    }

    private inner class DemoListAdapter : RecyclerView.Adapter<DemoListAdapter.ViewHolder>() {
        var demos = if (dualScreenMode) DUO_DEMOS else DEMOS
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        private val onClickListener = View.OnClickListener { view ->
            val demo = view.tag as Demo
            val intent = Intent(view.context, demo.demoClass.java)
            intent.putExtra(DemoActivity.DEMO_ID, demo.id)
            view.context.startActivity(intent)
        }

        override fun getItemCount(): Int = demos.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val listItemView = ListItemView(parent.context)
            listItemView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            return ViewHolder(listItemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val demo = demos[position]
            holder.listItem.title = demo.title
            with(holder.itemView) {
                tag = demo
                setOnClickListener(onClickListener)
            }
        }

        private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val listItem: ListItemView = view as ListItemView
        }
    }
}