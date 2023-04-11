package com.microsoft.fluentuidemo.demos.actionbar

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import com.microsoft.fluentui.actionbar.ActionBarLayout
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.DemoActionBarActivityBinding

class ActionBarDemoActivity : DemoActivity() {

    private lateinit var actionbarBinding: DemoActionBarActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        actionbarBinding = DemoActionBarActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(actionbarBinding.root)

        val position = intent.getIntExtra("POSITION", 0)
        val mode = intent.getIntExtra("TYPE", ActionBarLayout.Type.BASIC.ordinal)

        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = if (position == 1) Gravity.TOP else Gravity.BOTTOM

        actionbarBinding.actionbar1.layoutParams = params
        actionbarBinding.actionbar1.setMode(mode)

        val pagerAdapter = DummyPagerAdapter()
        pagerAdapter.setData(createPageList())
        actionbarBinding.viewpager1.adapter = pagerAdapter
        actionbarBinding.actionbar1.setPager(actionbarBinding.viewpager1)
        actionbarBinding.actionbar1.setLaunchMainScreen { finish() }

    }

    private fun createPageList(): List<View> {
        val pageList: MutableList<View> = ArrayList()
        pageList.add(
            createPageView(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.fluentui_communication_tint_20
                )
            )
        )
        pageList.add(
            createPageView(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.fluentui_communication_tint_30
                )
            )
        )
        pageList.add(
            createPageView(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.fluentui_communication_tint_40
                )
            )
        )

        return pageList
    }

    private fun createPageView(color: Int): View {
        val view = View(this)
        view.setBackgroundColor(color)
        return view
    }

    class DummyPagerAdapter : PagerAdapter() {
        private var viewList: List<View>

        init {
            this.viewList = ArrayList()
        }

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val view = viewList.get(position)
            collection.addView(view)
            return view
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }

        override fun getCount(): Int {
            return viewList.size
        }

        fun setData(list: List<View>) {
            viewList = list
        }
    }
}