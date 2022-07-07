package com.microsoft.fluentuidemo

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowActivity
import org.robolectric.shadows.ShadowIntent

@RunWith(RobolectricTestRunner::class)
class DemoListActivityTest {

    private var activity: Activity? = null
    private var shadowActivity: ShadowActivity? = null

    @Before
    fun setup() {
        activity = Robolectric.buildActivity(DemoListActivity::class.java).create().start().visible().get()
        shadowActivity = shadowOf(activity)
    }

    @Test
    fun testDemosCountInActivity(){
        val recycler = activity?.findViewById<RecyclerView>(R.id.demo_list)
        assertEquals(recycler?.adapter?.itemCount, DEMOS.size)
    }

    @Test
    fun testDemoList(){
        val recycler = activity?.findViewById<RecyclerView>(R.id.demo_list)
        for((position,demo) in DEMOS.withIndex()){
            if(position == 6){
                break
            }
            val item = recycler?.findViewHolderForAdapterPosition(position)
            item?.itemView?.performClick()
            val shadowIntent: ShadowIntent = shadowOf(shadowActivity?.nextStartedActivity)
            assertEquals(shadowIntent.intentClass.name, demo.demoClass.java.name)
        }
    }
}