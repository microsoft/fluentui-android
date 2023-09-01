package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.onNodeWithTag
import org.junit.Before
import com.microsoft.fluentuidemo.BaseTest
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import com.microsoft.fluentui.tokenized.bottomsheet.BOTTOMSHEET_CONTENT_TAG
import com.microsoft.fluentui.tokenized.bottomsheet.BOTTOMSHEET_HANDLE_TAG
import com.microsoft.fluentui.tokenized.bottomsheet.BOTTOMSHEET_SCRIM_TAG
import org.junit.Test


class V2BottomSheetActivityUITest(): BaseTest(){
    @Before
    fun initialize() {
        BaseTest().launchActivity(V2BottomSheetActivity::class.java)
    }
    //Tag use for testing
    private val bottomSheetHandle = composeTestRule.onNodeWithTag(BOTTOMSHEET_HANDLE_TAG, useUnmergedTree = true)
    private val bottomSheetContent = composeTestRule.onNodeWithTag(BOTTOMSHEET_CONTENT_TAG, useUnmergedTree = true)
    private val bottomSheetScrim = composeTestRule.onNodeWithTag(BOTTOMSHEET_SCRIM_TAG, useUnmergedTree = true)

    private fun openCheckForBottomSheet() {
        composeTestRule.waitForIdle()
        bottomSheetHandle.assertExists()
        bottomSheetScrim.assertExists()
        bottomSheetContent.assertExists()
    }
    @Test
    fun testBottomSheetDismissDisabledSwipeDown() {
        composeTestRule.onNodeWithTag(BOTTOM_SHEET_ENABLE_SWIPE_DISMISS_TEST_TAG, useUnmergedTree = true).performClick()
        //SwipeDown on bottomSheetContent should close it.
        bottomSheetContent.performTouchInput {
            swipeDown()
        }
        bottomSheetHandle.performTouchInput {
            swipeDown()
        }
        openCheckForBottomSheet()
    }



}