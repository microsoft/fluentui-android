package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.*
import com.microsoft.fluentui.tokenized.notification.*
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2SnackbarActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2SnackbarActivity::class.java)
    }


    private val showSnackBarButton = composeTestRule.onNodeWithTag(SNACK_BAR_SHOW_SNACKBAR)
    private val dismissSnackbarButton = composeTestRule.onNodeWithTag(SNACK_BAR_DISMISS_SNACKBAR)
    private val modifiableParametersButton =
        composeTestRule.onNodeWithTag(SNACK_BAR_MODIFIABLE_PARAMETER_SECTION)

    @Test
    fun testSnackbarDisplay() {
        showSnackBarButton.assertExists().performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACK_BAR).fetchSemanticsNodes()
                .isNotEmpty()
        }
        dismissSnackbarButton.assertExists().performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACK_BAR).fetchSemanticsNodes().isEmpty()
        }
    }

    @Test
    fun testSnackbarIconDisplay() {
        modifiableParametersButton.performClick()
        composeTestRule.onNodeWithTag(SNACK_BAR_ICON_PARAM).performClick()
        showSnackBarButton.performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACK_BAR_ICON).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testSnackbarSubtitleDisplay() {
        modifiableParametersButton.performClick()
        composeTestRule.onNodeWithTag(SNACK_BAR_SUBTITLE_PARAM).performClick()
        showSnackBarButton.performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACK_BAR_SUBTITLE).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testSnackbarActionDisplay() {
        modifiableParametersButton.performClick()
        composeTestRule.onNodeWithTag(SNACK_BAR_ACTION_BUTTON_PARAM).performClick()
        showSnackBarButton.performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACK_BAR_ACTION_BUTTON).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testSnackbarDismissDisplay() {
        modifiableParametersButton.performClick()
        modifiableParametersButton.performTouchInput { swipeUp(durationMillis = 1000L) }
        composeTestRule.onNodeWithTag(SNACK_BAR_DISMISS_BUTTON_PARAM).performClick()
        showSnackBarButton.performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACK_BAR_DISMISS_BUTTON).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

}