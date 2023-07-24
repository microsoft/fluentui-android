package com.microsoft.fluentuidemo

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DemoListActivityUITest {

    var activities = V2DEMO

    @get:Rule
    val activityRule = ActivityScenarioRule(V2DemoListActivity::class.java)

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