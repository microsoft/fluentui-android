/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import com.microsoft.fluentui.tablayout.TabLayout.TabType.*
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_tab_layout.*

class TabLayoutActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_tab_layout

    private lateinit var viewPager: ViewPager
    private var adapter: TabPagerAdapter? = null
    private lateinit var tabLayout:TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        tabLayout = demo_tab_layout.getTabLayout()
        viewPager = findViewById(R.id.view_pager)
        adapter = TabPagerAdapter()
        adapter!!.setData(createPageList())
        adapter!!.setTitle(createPageTitleList())
        viewPager.adapter = adapter

        show_tab_standard_two_segment.setOnClickListener(this::clickListener)
        show_tab_standard_three_segment.setOnClickListener(this::clickListener)
        show_tab_standard_four_segment.setOnClickListener(this::clickListener)
        show_tab_standard_with_pager.setOnClickListener(this::clickListener)
        show_tab_switch.setOnClickListener(this::clickListener)
        show_tab_pills.setOnClickListener(this::clickListener)
    }

    private fun clickListener(v:View) {
        tabLayout.removeAllTabs()
        tabLayout.setupWithViewPager(null)
        demo_tab_layout.setTabType(STANDARD)

        var numTabs = 0

        when(v.id) {
            R.id.show_tab_standard_two_segment -> { numTabs = 2 }
            R.id.show_tab_standard_three_segment -> { numTabs = 3 }
            R.id.show_tab_standard_four_segment -> { numTabs = 4 }
            R.id.show_tab_switch -> { numTabs = 2
                demo_tab_layout.setTabType(SWITCH)
            }
            R.id.show_tab_pills -> { numTabs = 6
                demo_tab_layout.setTabType(PILLS)
            }
            R.id.show_tab_standard_with_pager -> {
                demo_tab_layout.setPager(viewPager)
            }
        }

        for(i in 0 until numTabs) {
            tabLayout.addTab(tabLayout.newTab().setText("Label"))
        }
        demo_tab_layout.updateTemplate()
    }

    private fun createPageTitleList(): List<String> {
        val titleList: MutableList<String> = ArrayList()
        titleList.add("Page 1")
        titleList.add("Page 2")
        titleList.add("Page 3")
        return titleList
    }
    private fun createPageList(): List<View> {
        val pageList: MutableList<View> = ArrayList()
        pageList.add(createPageView(ContextCompat.getColor(applicationContext, R.color.fluentui_communication_tint_40)))
        pageList.add(createPageView(ContextCompat.getColor(applicationContext, R.color.fluentui_communication_tint_30)))
        pageList.add(createPageView(ContextCompat.getColor(applicationContext, R.color.fluentui_communication_tint_20)))
        return pageList
    }
    private fun createPageView(color: Int): View {
        val view = View(this)
        view.setBackgroundColor(color)
        return view
    }

    class TabPagerAdapter: PagerAdapter() {
        private var viewList:List<View>
        private var viewTitleList:List<String>

        init{
            this.viewList = ArrayList()
            this.viewTitleList = ArrayList()
        }
        override fun instantiateItem(collection: ViewGroup, position:Int):Any {
            val view = viewList.get(position)
            collection.addView(view)
            return view
        }
        override fun destroyItem(collection: ViewGroup, position:Int, view:Any) {
            collection.removeView(view as View)
        }
        override fun isViewFromObject(view:View, obj :Any):Boolean {
            return view === obj
        }
        override fun getCount(): Int {
            return viewList.size
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return viewTitleList[position]
        }
        fun setData(list: List<View>){
            viewList = list
        }
        fun setTitle(list: List<String>) {
            viewTitleList = list
        }
    }
}
