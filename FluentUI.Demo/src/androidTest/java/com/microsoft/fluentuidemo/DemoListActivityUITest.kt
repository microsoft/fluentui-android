package com.microsoft.fluentuidemo

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.microsoft.fluentuidemo.demos.ActionBarLayoutActivity
import org.hamcrest.*
import org.junit.*

class DemoListActivityUITest {

    var activities = DEMOS

    @get:Rule
    val activityRule = ActivityScenarioRule(DemoListActivity::class.java)

    @Before
    fun initialize(){
        Intents.init()
    }

    @Test
    fun testDemoMainActivity(){
        for((position, activity) in activities.withIndex()){
//            onView(ViewMatchers.withId(R.id.demo_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
//            onView(isRoot()).perform(waitFor(500))
            onView(ViewMatchers.withId(R.id.demo_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click()))
            intended(hasComponent(activity.demoClass.qualifiedName))
            pressBack()
            if(activity.title == PEOPLE_PICKER_VIEW){
                pressBack()
            }
        }
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    @After
    fun tearDown(){
        Intents.release()
    }
}