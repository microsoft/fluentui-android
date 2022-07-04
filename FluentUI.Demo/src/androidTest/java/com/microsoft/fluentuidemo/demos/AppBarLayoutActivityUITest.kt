package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R.id
import org.junit.*
import java.util.*

class AppBarLayoutActivityUITest {

    private fun launchActivity(){
        ActivityScenario.launch<ActionBarLayoutActivity>(setUpIntentForActivity())
    }
    private fun setUpIntentForActivity(): Intent {
        var targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        var intent = Intent(targetContext, AppBarLayoutActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        return intent
    }

    @Before
    fun initialize(){
        Intents.init()
    }

    @Test
    fun testScrollBehaviorCollapseToolbar(){
        launchActivity()

        onView(ViewMatchers.withId(id.app_bar_layout_toggle_scroll_behavior_button)).perform(ViewActions.click(), ViewActions.click(), ViewActions.click())
        onView(ViewMatchers.withId(id.app_bar_layout_list)).perform(ViewActions.scrollTo())

        onView(ViewMatchers.withId(id.app_bar_layout_action_search)).check(PositionAssertions.isTopAlignedWith(
            ViewMatchers.withId(id.parent)))
    }

    @Test
    fun testScrollBehaviorPin(){
        launchActivity()

        onView(ViewMatchers.withId(id.app_bar_layout_toggle_scroll_behavior_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(id.app_bar_layout_list)).perform(ViewActions.swipeUp())

        onView(ViewMatchers.withId(id.app_bar)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun testScrollBehaviorNone(){
        launchActivity()

        onView(ViewMatchers.withId(id.app_bar_layout_toggle_scroll_behavior_button)).perform(ViewActions.click(), ViewActions.click())
        onView(ViewMatchers.withId(id.app_bar_layout_list)).perform(ViewActions.swipeUp())

        onView(ViewMatchers.withId(id.app_bar)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @After
    fun tearDown(){
        Intents.release()
    }
}