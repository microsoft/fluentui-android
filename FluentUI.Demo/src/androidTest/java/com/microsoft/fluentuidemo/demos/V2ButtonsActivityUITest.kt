package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class V2ButtonsActivityUITest {
    private fun launchActivity() {
        ActivityScenario.launch<V2ButtonsActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2ButtonsActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        return intent
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun initialize() {
        Intents.init()
        launchActivity()
    }

    @Test
    fun testFAB() {
        val fabButton = composeTestRule.onNodeWithTag("FAB", true)
        fabButton.assertExists("FAB did not render")
        fabButton.assertHasClickAction()
        fabButton.performClick()
        fabButton.assertExists("FAB crashed on Click")
    }

    @Test
    fun testButtonOnClickAndDisabled() {
        val testButton = composeTestRule.onAllNodesWithText("Button 0")[0]
        testButton.assertExists("Button did not render")
        testButton.assertHasClickAction()
        testButton.assertIsEnabled()
        composeTestRule.onAllNodesWithText("Click to Disable")[0].performClick()
        testButton.assertIsNotEnabled()
    }

    @Test
    fun testThemeChange() {
        val theme1Button = composeTestRule.onNodeWithText("Theme1")
        val theme2Button = composeTestRule.onNodeWithText("Theme2")
        theme2Button.performClick()
        theme1Button.performClick()
        theme1Button.assertExists("Theme change crashing")
        theme2Button.assertExists("Theme change crashing")
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}