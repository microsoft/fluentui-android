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
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@SmallTest
class V2CardNudgeActivityUITest {

    // TAGS FOR TESTING
    private val MODIFIABLE_PARAMETER_SECTION = "Modifiable Parameters"
    private val ICON_PARAM = "Icon Param"
    private val SUBTITLE_PARAM = "Subtitle Param"
    private val ACCENT_ICON_PARAM = "Accent Icon Param"
    private val ACCENT_TEXT_PARAM = "Accent Text Param"
    private val ACTION_BUTTON_PARAM = "Action Button Param"
    private val DISMISS_BUTTON_PARAM = "Dismiss Button Param"
    private val CARDNUDGE = "CardNudge"
    private val ICON = "ICON"
    private val ACCENT_ICON = "Accent Icon"
    private val ACCENT_TEXT = "Accent Text"
    private val SUBTITLE = "Subtitle"
    private val ACTION_BUTTON = "Action Button"
    private val DISMISS_BUTTON = "Dismiss Button"

    private fun launchActivity() {
        ActivityScenario.launch<V2CardNudgeActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2CardNudgeActivity::class.java)
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
    val modifiableParametersButton = composeTestRule.onAllNodesWithTag(MODIFIABLE_PARAMETER_SECTION)

    @Test
    fun testCardNudgeDisplay() {
        composeTestRule.onNodeWithTag(CARDNUDGE).assertExists()
    }

    @Test
    fun testCardNudgeIconDisplay() {
        modifiableParametersButton[0].performClick()
        val control = composeTestRule.onNodeWithTag(ICON_PARAM)
        control.assertExists()
        for ((key, value) in control.fetchSemanticsNode().config) {
            if (key.name == "ToggleableState" && value.toString() == "Off") {
                control.performClick()
                break
            }
        }
        composeTestRule.onNodeWithTag(ICON).assertExists()
    }

    @Test
    fun testCardNudgeSubtitleDisplay() {
        modifiableParametersButton[0].performClick()
        val control = composeTestRule.onNodeWithTag(SUBTITLE_PARAM)
        control.assertExists()
        for ((key, value) in control.fetchSemanticsNode().config) {
            if (key.name == "ToggleableState" && value.toString() == "Off") {
                control.performClick()
                break
            }
        }
        composeTestRule.onNodeWithTag(SUBTITLE).assertExists()
    }

    @Test
    fun testCardNudgeAccentIconDisplay() {
        modifiableParametersButton[0].performClick()
        val control = composeTestRule.onNodeWithTag(ACCENT_ICON_PARAM)
        control.assertExists()
        for ((key, value) in control.fetchSemanticsNode().config) {
            if (key.name == "ToggleableState" && value.toString() == "Off") {
                control.performClick()
                break
            }
        }
        composeTestRule.onNodeWithTag(ACCENT_ICON).assertExists()
    }

    @Test
    fun testCardNudgeAccentTextDisplay() {
        modifiableParametersButton[0].performClick()
        val control = composeTestRule.onNodeWithTag(ACCENT_TEXT_PARAM)
        control.assertExists()
        for ((key, value) in control.fetchSemanticsNode().config) {
            if (key.name == "ToggleableState" && value.toString() == "Off") {
                control.performClick()
                break
            }
        }
        composeTestRule.onNodeWithTag(ACCENT_TEXT).assertExists()
    }

    @Test
    fun testCardNudgeActionDisplay() {
        modifiableParametersButton[0].performClick()
        composeTestRule.onNodeWithTag(ICON_PARAM)
            .performTouchInput { swipeUp(durationMillis = 1000) }
        val control = composeTestRule.onNodeWithTag(ACTION_BUTTON_PARAM)
        control.assertExists()
        for ((key, value) in control.fetchSemanticsNode().config) {
            if (key.name == "ToggleableState" && value.toString() == "Off") {
                control.performClick()
                break
            }
        }
        composeTestRule.onNodeWithTag(ACTION_BUTTON).assertExists()
    }

    @Test
    fun testCardNudgeDismissDisplay() {
        modifiableParametersButton[0].performClick()
        composeTestRule.onNodeWithTag(ICON_PARAM)
            .performTouchInput { swipeUp(durationMillis = 1000) }
        val control = composeTestRule.onNodeWithTag(DISMISS_BUTTON_PARAM)
        control.assertExists()
        for ((key, value) in control.fetchSemanticsNode().config) {
            if (key.name == "ToggleableState" && value.toString() == "Off") {
                control.performClick()
                break
            }
        }
        composeTestRule.onNodeWithTag(DISMISS_BUTTON).assertExists()
    }

    @Test
    fun testLeftSwipe() {
        composeTestRule.onNodeWithTag(CARDNUDGE).performTouchInput { swipeLeft() }
        composeTestRule.onNodeWithTag(CARDNUDGE).performClick()
        composeTestRule.onNodeWithText("Left Swiped", substring = true).assertExists()
    }

    @Test
    fun testRightSwipe() {
        composeTestRule.onNodeWithTag(CARDNUDGE).performTouchInput { swipeRight() }
        composeTestRule.onNodeWithTag(CARDNUDGE).performClick()
        composeTestRule.onNodeWithText("Right Swiped", substring = true).assertExists()
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}