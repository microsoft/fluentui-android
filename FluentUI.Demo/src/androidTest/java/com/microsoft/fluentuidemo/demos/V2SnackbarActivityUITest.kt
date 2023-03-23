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

class V2SnackbarActivityUITest {

    // TAGS FOR TESTING
    private val MODIFIABLE_PARAMETER_SECTION = "Modifiable Parameters"
    private val ICON_PARAM = "Icon Param"
    private val SUBTITLE_PARAM = "Subtitle Param"
    private val ACTION_BUTTON_PARAM = "Action Button Param"
    private val DISMISS_BUTTON_PARAM = "Dismiss Button Param"
    private val SHOW_SNACKBAR = "Show Snackbar"
    private val DISMISS_SNACKBAR = "Dismiss Snackbar"
    private val SNACKBAR = "Snackbar"
    private val ICON = "ICON"
    private val SUBTITLE = "Subtitle"
    private val ACTION_BUTTON = "Action Button"
    private val DISMISS_BUTTON = "Dismiss Button"

    private fun launchActivity() {
        ActivityScenario.launch<V2SnackbarActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2SnackbarActivity::class.java)
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
    val showSnackBarButton = composeTestRule.onNodeWithTag(SHOW_SNACKBAR)
    val dismissSnackbarButton = composeTestRule.onNodeWithTag(DISMISS_SNACKBAR)
    val modifiableParametersButton = composeTestRule.onAllNodesWithTag(MODIFIABLE_PARAMETER_SECTION)

    @Test
    fun testSnackbarDisplay() {
        showSnackBarButton.assertExists().performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACKBAR).fetchSemanticsNodes()
                .isNotEmpty()
        }
        dismissSnackbarButton.assertExists().performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACKBAR).fetchSemanticsNodes().isEmpty()
        }
    }

    @Test
    fun testSnackbarIconDisplay() {
        modifiableParametersButton[0].performClick()
        composeTestRule.onNodeWithTag(ICON_PARAM).performClick()
        showSnackBarButton.performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(ICON).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testSnackbarSubtitleDisplay() {
        modifiableParametersButton[0].performClick()
        composeTestRule.onNodeWithTag(SUBTITLE_PARAM).performClick()
        showSnackBarButton.performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SUBTITLE).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testSnackbarActionDisplay() {
        modifiableParametersButton[0].performClick()
        composeTestRule.onNodeWithTag(ACTION_BUTTON_PARAM).performClick()
        showSnackBarButton.performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(ACTION_BUTTON).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testSnackbarDismissDisplay() {
        modifiableParametersButton[0].performClick()
        composeTestRule.onNodeWithTag(ICON_PARAM)
            .performTouchInput { swipeUp(durationMillis = 1000L) }
        composeTestRule.onNodeWithTag(DISMISS_BUTTON_PARAM).performClick()
        showSnackBarButton.performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(DISMISS_BUTTON).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}