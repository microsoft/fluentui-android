package com.microsoft.fluentuidemo.demos.actionbar

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.PagerAdapter
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.microsoft.fluentui.actionbar.ActionBarLayout
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.demo_action_bar_activity.*
import java.util.*

class ActionBarDemoActivity: DemoActivity() {

    override val contentLayoutId: Int
        get() = R.layout.demo_action_bar_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.demo_action_bar_activity)

        val position = intent.getIntExtra("POSITION", 0)
        val mode = intent.getIntExtra("TYPE", ActionBarLayout.Type.BASIC.ordinal)

        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        params.gravity = if(position == 1) Gravity.TOP else Gravity.BOTTOM

        actionbar1.layoutParams = params
        actionbar1.setMode(mode)

        val pagerAdapter = DummyPagerAdapter()
        pagerAdapter.setData(createPageList())
        viewpager1.adapter = pagerAdapter
        actionbar1.setPager(viewpager1)
        actionbar1.setLaunchMainScreen { finish() }

    }

    private fun createPageList(): List<View> {
        val pageList: MutableList<View> = ArrayList()
        pageList.add(createPageView(ContextCompat.getColor(applicationContext, R.color.fluentui_communication_tint_20)))
        pageList.add(createPageView(ContextCompat.getColor(applicationContext, R.color.fluentui_communication_tint_30)))
        pageList.add(createPageView(ContextCompat.getColor(applicationContext, R.color.fluentui_communication_tint_40)))

        return pageList
    }

    private fun createPageView(color: Int): View {
        val view = View(this)
        view.setBackgroundColor(color)
        return view
    }

    class DummyPagerAdapter: PagerAdapter() {
        private var viewList:List<View>

        init{
            this.viewList = ArrayList()
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

        fun setData(list: List<View>){
            viewList = list
        }
    }
}