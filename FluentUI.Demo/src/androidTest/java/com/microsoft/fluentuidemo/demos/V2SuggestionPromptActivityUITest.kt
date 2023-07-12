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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class V2SuggestionPromptActivityUITest {
    // TAGS FOR TESTING
    private val MODIFIABLE_PARAMETER_SECTION = "Modifiable Parameters"
    private val ACTION_ICON_PARAM = "Action Icon Param"
    private val ACTION_ICON = "Action Icon"

    private fun launchActivity() {
        ActivityScenario.launch<V2SuggestionPromptActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2SuggestionPromptActivity::class.java)
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
    private val modifiableParametersButton =
        composeTestRule.onAllNodesWithTag(MODIFIABLE_PARAMETER_SECTION)

    @Test
    fun testActionIconDisplay() {
        composeTestRule.onNodeWithTag(ACTION_ICON, true).assertExists()
        modifiableParametersButton[0].assertExists().performClick()
        composeTestRule.onNodeWithTag(ACTION_ICON_PARAM).performClick()
        composeTestRule.onNodeWithTag(ACTION_ICON, true).assertDoesNotExist()
    }
}