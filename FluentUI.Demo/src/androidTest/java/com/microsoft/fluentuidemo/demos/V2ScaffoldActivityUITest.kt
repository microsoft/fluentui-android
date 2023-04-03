package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
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
    private val FLOATING_ACTION_BUTTON = "FAB"
    private val MAIN_CONTENT = "Main Content"

    @Test
    fun test(){
        composeTestRule.onNodeWithTag(TOP_BAR).assertExists("Top Bar not shown")
        composeTestRule.onNodeWithTag(BOTTOM_BAR).assertExists("Bottom Bar not shown")
//        composeTestRule.onNodeWithTag(SNACKBAR).assertExists("Top Bar not shown")
        composeTestRule.onNodeWithTag(FLOATING_ACTION_BUTTON).assertExists("FAB not shown")
        composeTestRule.onNodeWithTag(MAIN_CONTENT).assertExists("Main content not shown")
    }
}