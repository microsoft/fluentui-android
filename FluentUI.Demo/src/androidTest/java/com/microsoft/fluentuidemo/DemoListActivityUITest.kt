package com.microsoft.fluentuidemo

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.*

class DemoListActivityUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(DemoListActivity::class.java)

    @Test
    fun testDemoMainActivity(){
        onView(ViewMatchers.withId(R.id.demo_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(8))
    }
}