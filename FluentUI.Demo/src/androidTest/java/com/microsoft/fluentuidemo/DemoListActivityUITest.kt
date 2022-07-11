package com.microsoft.fluentuidemo

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.*
import org.junit.runner.*

@RunWith(AndroidJUnit4::class)
class DemoListActivityUITest {

    var activities = DEMOS

    @get:Rule
    val activityRule = ActivityScenarioRule(DemoListActivity::class.java)

//    @Before
//    fun initialize(){
//        Intents.init()
//    }

//    @Test
//    fun testDemoMainActivity(){
//        for((position, activity) in activities.withIndex()){
//            onView(ViewMatchers.withId(R.id.demo_list)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(position, ViewActions.click()))
//            intended(hasComponent(activity.demoClass.qualifiedName))
//            pressBack()
//            if(activity.title == PEOPLE_PICKER_VIEW){
//                pressBack()
//            }
//        }
//    }

//    @After
//    fun tearDown(){
//        Intents.release()
//    }
}