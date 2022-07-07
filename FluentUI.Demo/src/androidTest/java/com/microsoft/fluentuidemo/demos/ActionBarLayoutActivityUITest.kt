package com.microsoft.fluentuidemo.demos

import android.content.Intent
import android.widget.ImageView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.Visibility.GONE
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentui.actionbar.IndicatorView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R.id
import com.microsoft.fluentuidemo.demos.actionbar.ActionBarDemoActivity
import org.junit.*
import org.junit.runner.*
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class ActionBarLayoutActivityUITest {

    companion object {
        private const val POPUP_STRING = "Please select position and type"
        private const val ACTIONBAR_TEXT = "Next"
    }

    @Before
    fun initialize(){
        Intents.init()
    }

    @Test
    fun testNoActionBarPositionNoActionBarType(){

        onView(withId(id.start_demo_btn)).perform(ViewActions.click())

        onView(withText(POPUP_STRING)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testActionBarPositionTopActionBarTypeBasic(){

        onView(withId(id.action_bar_position_top)).perform(ViewActions.click())
        onView(withId(id.action_bar_type_basic)).perform(ViewActions.click())
        onView(withId(id.start_demo_btn)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ActionBarDemoActivity::class.qualifiedName))

        onView(withId(id.actionbar1)).check(PositionAssertions.isTopAlignedWith(withId(id.viewpager1)))
        onView(withText(ACTIONBAR_TEXT)).check(ViewAssertions.matches(isDisplayed()))
        onView(isAssignableFrom(ImageView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(GONE)))
        onView(isAssignableFrom(IndicatorView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(GONE)))
    }


    @Test
    fun testActionBarPositionTopActionBarTypeIcon(){

        onView(withId(id.action_bar_position_top)).perform(ViewActions.click())
        onView(withId(id.action_bar_type_icon)).perform(ViewActions.click())
        onView(withId(id.start_demo_btn)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ActionBarDemoActivity::class.qualifiedName))

        onView(withId(id.actionbar1)).check(PositionAssertions.isTopAlignedWith(withId(id.viewpager1)))
        onView(withText(ACTIONBAR_TEXT)).check(ViewAssertions.matches(isDisplayed()))
        onView(isAssignableFrom(ImageView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(VISIBLE)))
        onView(isAssignableFrom(IndicatorView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(GONE)))
    }

    @Test
    fun testActionBarPositionTopActionBarTypeCarousel(){

        onView(withId(id.action_bar_position_top)).perform(ViewActions.click())
        onView(withId(id.action_bar_type_carousel)).perform(ViewActions.click())
        onView(withId(id.start_demo_btn)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ActionBarDemoActivity::class.qualifiedName))

        onView(withId(id.actionbar1)).check(PositionAssertions.isTopAlignedWith(withId(id.viewpager1)))
        onView(withText(ACTIONBAR_TEXT)).check(ViewAssertions.matches(withEffectiveVisibility(GONE)))
        onView(isAssignableFrom(ImageView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(VISIBLE)))
        onView(isAssignableFrom(IndicatorView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(VISIBLE)))
    }

    @Test
    fun testActionBarPositionBottomActionBarTypeBasic(){

        onView(withId(id.action_bar_position_bottom)).perform(ViewActions.click())
        onView(withId(id.action_bar_type_basic)).perform(ViewActions.click())
        onView(withId(id.start_demo_btn)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ActionBarDemoActivity::class.qualifiedName))

        onView(withId(id.actionbar1)).check(PositionAssertions.isBottomAlignedWith(withId(id.viewpager1)))
        onView(withText(ACTIONBAR_TEXT)).check(ViewAssertions.matches(isDisplayed()))
        onView(isAssignableFrom(ImageView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(GONE)))
        onView(isAssignableFrom(IndicatorView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(GONE)))
    }

    @Test
    fun testActionBarPositionBottomActionBarTypeIcon(){

        onView(withId(id.action_bar_position_bottom)).perform(ViewActions.click())
        onView(withId(id.action_bar_type_icon)).perform(ViewActions.click())
        onView(withId(id.start_demo_btn)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ActionBarDemoActivity::class.qualifiedName))

        onView(withId(id.actionbar1)).check(PositionAssertions.isBottomAlignedWith(withId(id.viewpager1)))
        onView(withText(ACTIONBAR_TEXT)).check(ViewAssertions.matches(isDisplayed()))
        onView(isAssignableFrom(ImageView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(VISIBLE)))
        onView(isAssignableFrom(IndicatorView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(GONE)))
    }

    @Test
    fun testActionBarPositionBottomActionBarTypeCarousel(){

        onView(withId(id.action_bar_position_bottom)).perform(ViewActions.click())
        onView(withId(id.action_bar_type_carousel)).perform(ViewActions.click())
        onView(withId(id.start_demo_btn)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ActionBarDemoActivity::class.qualifiedName))

        onView(withId(id.actionbar1)).check(PositionAssertions.isBottomAlignedWith(withId(id.viewpager1)))
        onView(withText(ACTIONBAR_TEXT)).check(ViewAssertions.matches(withEffectiveVisibility(GONE)))
        onView(isAssignableFrom(ImageView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(VISIBLE)))
        onView(isAssignableFrom(IndicatorView::class.java)).check(ViewAssertions.matches(withEffectiveVisibility(VISIBLE)))
    }

    @After
    fun tearDown(){
        Intents.release()
    }

    private fun launchActivity(){
        ActivityScenario.launch<ActionBarLayoutActivity>(setUpIntentForActivity())
    }
    private fun setUpIntentForActivity(): Intent{
        var targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        var intent = Intent(targetContext, ActionBarLayoutActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        return intent
    }
}