package com.microsoft.fluentuidemo

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import org.junit.*
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
        activity = Robolectric.buildActivity(DemoListActivity::class.java).create().get()
        shadowActivity = shadowOf(activity)
    }

    @Test
    fun testDemoList(){
        val recycler = activity?.findViewById<RecyclerView>(R.id.demo_list)
        for((position,demo) in DEMOS.withIndex()){
            recycler?.findViewHolderForAdapterPosition(position)?.itemView?.performClick()
            val startedIntent: Intent? = shadowActivity?.nextStartedActivity
            val shadowIntent: ShadowIntent = shadowOf(startedIntent)
            Assert.assertEquals(shadowIntent.intentClass.name, demo.demoClass.java.name)
        }
    }
}