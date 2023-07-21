package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentui.appbarlayout.AppBarLayout
import com.microsoft.fluentui.toolbar.Toolbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R.id
import org.hamcrest.Matchers.*
import org.junit.*
import java.util.*
import com.microsoft.fluentui.search.Searchbar
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.demos.list.ListSubHeader

class AppBarLayoutActivityUITest {

    private fun launchActivity(){
        ActivityScenario.launch<ActionBarLayoutActivity>(setUpIntentForActivity())
    }
    private fun setUpIntentForActivity(): Intent {
        var targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        var intent = Intent(targetContext, AppBarLayoutActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        intent.putExtra(V2DemoActivity.DEMO_TITLE, "Demo Test")
        return intent
    }

    @Before
    fun initialize(){
        Intents.init()
        launchActivity()
    }

    @Test
    fun testScrollBehaviorCollapseToolbar(){
        onView(allOf(isAssignableFrom(Searchbar::class.java), isDescendantOfA(isAssignableFrom(AppBarLayout::class.java)))).perform(ViewActions.click())

        onView(isAssignableFrom(AppBarLayout::class.java)).check(ViewAssertions.matches(
            not(isCompletelyDisplayed())
        ))
    }

    @Test
    fun testScrollBehaviorPin(){
        onView(withId(id.app_bar_layout_toggle_scroll_behavior_button)).perform(ViewActions.click())
        onView(withId(id.app_bar_layout_list)).perform(ViewActions.swipeUp())

        onView(withId(id.app_bar)).check(ViewAssertions.matches(isCompletelyDisplayed()))

        onView(withId(id.app_bar_layout_list)).perform(ViewActions.swipeDown())
        onView(allOf(isAssignableFrom(Searchbar::class.java), isDescendantOfA(isAssignableFrom(AppBarLayout::class.java)))).perform(ViewActions.click())

        onView(withId(id.app_bar)).check(ViewAssertions.matches(isCompletelyDisplayed()))
    }

    @Test
    fun testScrollBehaviorNone(){
        onView(withId(id.app_bar_layout_toggle_scroll_behavior_button)).perform(ViewActions.click(), ViewActions.click())
        onView(withId(id.app_bar_layout_list)).perform(ViewActions.swipeUp())

        onView(withId(id.app_bar)).check(ViewAssertions.matches(isCompletelyDisplayed()))

        onView(withId(id.app_bar_layout_list)).perform(ViewActions.swipeDown())
        onView(allOf(isAssignableFrom(Searchbar::class.java), isDescendantOfA(isAssignableFrom(AppBarLayout::class.java)))).perform(ViewActions.click())

        onView(withId(id.app_bar)).check(ViewAssertions.matches(isCompletelyDisplayed()))
    }

    @Test
    fun testToggleNavigationIcon(){
        //back arrow
        onView(allOf(isAssignableFrom(AppCompatImageButton::class.java), isDescendantOfA(isAssignableFrom(Toolbar::class.java)))).check(ViewAssertions.matches(
            withEffectiveVisibility(VISIBLE)))

        onView(withId(id.app_bar_layout_toggle_navigation_icon_button)).perform(ViewActions.click())
        //No icon
        onView(isAssignableFrom(Toolbar::class.java)).check(ViewAssertions.matches(
            not(withChild(isAssignableFrom(AppCompatImageButton::class.java)))))

        onView(withId(id.app_bar_layout_toggle_navigation_icon_button)).perform(ViewActions.click())
        //Avatar
        onView(allOf(isAssignableFrom(AppCompatImageButton::class.java), isDescendantOfA(isAssignableFrom(Toolbar::class.java)))).check(ViewAssertions.matches(
            withEffectiveVisibility(VISIBLE)))
    }

    @Test
    fun testToggleSearchbarLayoutStyle(){
        onView(allOf(isAssignableFrom(Searchbar::class.java), isDescendantOfA(isAssignableFrom(AppBarLayout::class.java)))).check(ViewAssertions.matches(withEffectiveVisibility(VISIBLE)))

        onView(withId(id.app_bar_layout_toggle_searchbar_type_button)).perform(ViewActions.click())

        onView(isAssignableFrom(AppBarLayout::class.java)).check(ViewAssertions.matches(not(withChild(isAssignableFrom(Searchbar::class.java)))))
    }

    @Test
    fun testToggleTheme(){

    }

    @After
    fun tearDown(){
        Intents.release()
    }
}