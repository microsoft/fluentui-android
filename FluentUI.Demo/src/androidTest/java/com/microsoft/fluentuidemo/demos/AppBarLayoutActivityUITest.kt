package com.microsoft.fluentuidemo.demos

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.microsoft.fluentuidemo.R.id
import org.junit.*

class AppBarLayoutActivityUITest {
    @get:Rule
    val activityRule = ActivityScenarioRule(AppBarLayoutActivity::class.java)

    @Test
    fun testAppBar(){
        Espresso.onView(ViewMatchers.withId(id.action_bar_position_top)).perform(ViewActions.click())
    }
}