package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.assertExistsAfterToggleOnly
import com.microsoft.fluentuidemo.toggleControlToValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@SmallTest
class V2AppBarLayoutActivityUITest {

    // Tags used for testing
    private val MODIFIABLE_PARAMETER_SECTION = "Modifiable Parameters"
    private val SUBTITLE_PARAM = "Subtitle Param"
    private val APPBAR_STYLE_PARAM = "AppBar Style Param"
    private val BUTTONBAR_PARAM = "ButtonBar Param"
    private val SEARCHBAR_PARAM = "SearchBar Param"
    private val APP_BAR = "App bar"
    private val SUBTITLE = "Subtitle"
    private val BOTTOM_BAR = "Bottom bar"
    private val SEARCH_BAR = "Search bar"

    private fun launchActivity() {
        ActivityScenario.launch<V2AppBarLayoutActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2AppBarLayoutActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        return intent
    }

    @Before
    fun initialize() {
        Intents.init()
        launchActivity()
    }

    @get:Rule
    val composeTestRule = createComposeRule()
    private val modifiableParametersButton = composeTestRule.onAllNodesWithTag(MODIFIABLE_PARAMETER_SECTION)[0]

    @Test
    fun testAppBarDisplay() {
        composeTestRule.onNodeWithTag(APP_BAR).assertExists()
    }

    @Test
    fun testAppBarSubtitleToggle() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(SUBTITLE_PARAM)
        val component = composeTestRule.onNodeWithTag(SUBTITLE)
        assertExistsAfterToggleOnly(control, component, "Subtitle is not displayed")
    }

    @Test
    fun testAppBarBottomBarToggle() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(BUTTONBAR_PARAM)
        val component = composeTestRule.onNodeWithTag(BOTTOM_BAR)
        assertExistsAfterToggleOnly(control, component, "Bottom bar is not displayed")
    }

    @Test
    fun testAppBarSearchBarToggle() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(SEARCHBAR_PARAM)
        val component = composeTestRule.onNodeWithTag(SEARCH_BAR)
        assertExistsAfterToggleOnly(control, component, "Search bar is not displayed")
    }

    @Test
    fun testAppBarStyleToggle() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(APPBAR_STYLE_PARAM)
        val component = composeTestRule.onNodeWithTag(APP_BAR)
        toggleControlToValue(control,true)
        component.assertExists("App bar is not displayed in accent")
    }


    @After
    fun tearDown() {
        Intents.release()
    }
}