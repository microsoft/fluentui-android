package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.V2DemoActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class V2ScaffoldActivityUITest {

    private fun launchActivity() {
        ActivityScenario.launch<V2ScaffoldActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2ScaffoldActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        intent.putExtra(V2DemoActivity.DEMO_TITLE, "Demo Test")
        return intent
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun initialize() {
        Intents.init()
        launchActivity()
    }

    //Tag for Test
    private val TOP_BAR = "TopBar"
    private val BOTTOM_BAR = "BottomBar"
    private val SNACKBAR = "SnackBar"
    private val DRAWER = "Drawer"
    private val FLOATING_ACTION_BUTTON = "FAB"
    private val MAIN_CONTENT = "Main Content"
    private val DRAWER_BUTTON = "Drawer Button"
    private val SNACKBAR_BUTTON = "Snackbar Button"

    @Test
    fun testOnScreenComponents() {
        composeTestRule.onNodeWithTag(TOP_BAR).assertExists("Top Bar not shown")
        composeTestRule.onNodeWithTag(BOTTOM_BAR).assertExists("Bottom Bar not shown")
        composeTestRule.onNodeWithTag(FLOATING_ACTION_BUTTON).assertExists("FAB not shown")
        composeTestRule.onNodeWithTag(MAIN_CONTENT).assertExists("Main content not shown")
    }

    @Test
    fun testSnackbar() {
        composeTestRule.onNodeWithTag(SNACKBAR_BUTTON).performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACKBAR).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testDrawer() {
        composeTestRule.onNodeWithTag(DRAWER_BUTTON).performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(DRAWER).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}