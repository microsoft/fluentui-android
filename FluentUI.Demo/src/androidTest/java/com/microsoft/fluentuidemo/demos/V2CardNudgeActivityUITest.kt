package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.microsoft.fluentui.tokenized.notification.*
import com.microsoft.fluentuidemo.BaseTest
import com.microsoft.fluentuidemo.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class V2CardNudgeActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2CardNudgeActivity::class.java)
    }

    private val modifiableParametersButton =
        composeTestRule.onNodeWithTag(CARD_NUDGE_MODIFIABLE_PARAMETER_SECTION)

    @Test
    fun testCardNudgeDisplay() {
        composeTestRule.onNodeWithTag(CARD_NUDGE).assertExists()
    }

    @Test
    fun testCardNudgeIconDisplay() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(CARD_NUDGE_ICON_PARAM)
        val component = composeTestRule.onNodeWithTag(CARD_NUDGE_ICON)
        assertExistsAfterToggleOnly(control, component, "Icon did not render properly")
    }

    @Test
    fun testCardNudgeSubtitleDisplay() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(CARD_NUDGE_SUBTITLE_PARAM)
        val component = composeTestRule.onNodeWithTag(CARD_NUDGE_SUBTITLE)
        assertExistsAfterToggleOnly(control, component, "Subtitle did not render properly")
    }

    @Test
    fun testCardNudgeAccentIconDisplay() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(CARD_NUDGE_ACCENT_ICON_PARAM)
        val component = composeTestRule.onNodeWithTag(CARD_NUDGE_ACCENT_ICON)
        assertExistsAfterToggleOnly(control, component, "Accent icon did not render properly")
    }

    @Test
    fun testCardNudgeAccentTextDisplay() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(CARD_NUDGE_ACCENT_TEXT_PARAM)
        val component = composeTestRule.onNodeWithTag(CARD_NUDGE_ACCENT_TEXT)
        assertExistsAfterToggleOnly(control, component, "Accent text did not render properly")
    }

    @Test
    fun testCardNudgeActionDisplay() {
        modifiableParametersButton.performClick()
        modifiableParametersButton.performTouchInput { swipeUp(durationMillis = 1000) }
        val control = composeTestRule.onNodeWithTag(CARD_NUDGE_ACTION_BUTTON_PARAM)
        val component = composeTestRule.onNodeWithTag(CARD_NUDGE_ACTION_BUTTON)
        assertExistsAfterToggleOnly(control, component, "Action button did not render properly")
    }

    @Test
    fun testCardNudgeDismissDisplay() {
        modifiableParametersButton.performClick()
        modifiableParametersButton.performTouchInput { swipeUp(durationMillis = 1000) }
        val control = composeTestRule.onNodeWithTag(CARD_NUDGE_DISMISS_BUTTON_PARAM)
        val component = composeTestRule.onNodeWithTag(CARD_NUDGE_DISMISS_BUTTON)
        assertExistsAfterToggleOnly(control, component, "Dismiss button did not render properly")
    }

    @Test
    fun testLeftSwipe() {
        composeTestRule.onNodeWithTag(CARD_NUDGE).performTouchInput { swipeLeft() }
        composeTestRule.onNodeWithTag(CARD_NUDGE).performClick()
        composeTestRule.onNodeWithText(getString(R.string.fluentui_left_swiped), substring = true)
            .assertExists()
    }

    @Test
    fun testRightSwipe() {
        composeTestRule.onNodeWithTag(CARD_NUDGE).performTouchInput { swipeRight() }
        composeTestRule.onNodeWithTag(CARD_NUDGE).performClick()
        composeTestRule.onNodeWithText(getString(R.string.fluentui_right_swiped), substring = true)
            .assertExists()
    }

}