/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.microsoft.fluentui.tablayout.TabLayout
import com.microsoft.fluentui.tablayout.TabLayout.TabType.*
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityTabLayoutBinding

class TabLayoutActivity : DemoActivity() {

    private var adapter: TabPagerAdapter? = null
    private lateinit var tabLayout: com.google.android.material.tabs.TabLayout
    private lateinit var tabLayoutBinding: ActivityTabLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabLayoutBinding = ActivityTabLayoutBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        tabLayout = tabLayoutBinding.demoTabLayout.tabLayout ?: return
        adapter = TabPagerAdapter()
        adapter?.setData(createPageList())
        adapter?.setTitle(createPageTitleList())
        tabLayoutBinding.viewPager.adapter = adapter

        tabLayoutBinding.showTabStandardTwoSegment.setOnClickListener(this::clickListener)
        tabLayoutBinding.showTabStandardThreeSegment.setOnClickListener(this::clickListener)
        tabLayoutBinding.showTabStandardFourSegment.setOnClickListener(this::clickListener)
        tabLayoutBinding.showTabStandardWithPager.setOnClickListener(this::clickListener)
        tabLayoutBinding.showTabSwitch.setOnClickListener(this::clickListener)
        tabLayoutBinding.showTabPills.setOnClickListener(this::clickListener)
    }

    private fun clickListener(v: View) {
        tabLayoutBinding.demoTabLayout.visibility = View.VISIBLE
        tabLayout.removeAllTabs()
        tabLayout.setupWithViewPager(null)

        var tabType: TabLayout.TabType = STANDARD
        when (v.id) {
            R.id.show_tab_standard_two_segment -> {
                setTabs(2)
            }
            R.id.show_tab_standard_three_segment -> {
                setTabs(3)
            }
            R.id.show_tab_standard_four_segment -> {
                setTabs(4)
            }
            R.id.show_tab_switch -> {
                setTabs(2)
                tabType = SWITCH
            }
            R.id.show_tab_pills -> {
                setTabs(6)
                tabType = PILLS
            }
            R.id.show_tab_standard_with_pager -> {
                tabLayout.setupWithViewPager(tabLayoutBinding.viewPager)
            }
        }
        tabLayoutBinding.demoTabLayout.tabType = tabType
    }

    private fun setTabs(numTabs: Int) {
        for (i in 0 until numTabs) {
            tabLayout.addTab(tabLayout.newTab().setText("Label " + (i + 1)))
        }
    }

    private fun createPageTitleList(): List<String> {
        return listOf("Page 1", "Page 2", "Page 3")
    }

    private fun createPageList(): List<View> {
        return listOf(
            createPageView(R.color.fluentui_communication_tint_40),
            createPageView(R.color.fluentui_communication_tint_30),
            createPageView(R.color.fluentui_communication_tint_20)
        )
    }

    private fun createPageView(color: Int): View {
        val view = View(this)
        view.setBackgroundColor(ContextCompat.getColor(this, color))
        return view
    }

    class TabPagerAdapter : PagerAdapter() {
        private var viewList: List<View> = ArrayList()
        private var viewTitleList: List<String> = ArrayList()

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val view = viewList[position]
            collection.addView(view)
            return view
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj

        override fun getCount(): Int = viewList.size

        override fun getPageTitle(position: Int): CharSequence? = viewTitleList[position]

        fun setData(list: List<View>) {
            viewList = list
        }

        fun setTitle(list: List<String>) {
            viewTitleList = list
        }
    }
}