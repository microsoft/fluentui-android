package com.microsoft.fluentuidemo.demos

import android.content.res.Resources
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.drawer.DRAWER_CONTENT_TAG
import com.microsoft.fluentui.tokenized.drawer.DRAWER_HANDLE_TAG
import com.microsoft.fluentui.tokenized.drawer.DRAWER_SCRIM_TAG
import com.microsoft.fluentuidemo.BaseTest
import com.microsoft.fluentuidemo.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import kotlin.concurrent.thread

class V2BottomDrawerUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2BottomDrawerActivity::class.java)
    }

    private val drawerHandle = composeTestRule.onNodeWithTag(DRAWER_HANDLE_TAG)
    private val drawerContent = composeTestRule.onNodeWithTag(DRAWER_CONTENT_TAG)
    private val drawerScrim = composeTestRule.onNodeWithTag(DRAWER_SCRIM_TAG)

    @OptIn(ExperimentalTestApi::class)
    private fun waitForDrawerOpen() {
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(DRAWER_CONTENT_TAG))
    }

    @OptIn(ExperimentalTestApi::class)
    private fun waitForDrawerClose() {
        composeTestRule.waitUntilDoesNotExist(hasTestTag(DRAWER_CONTENT_TAG))
    }

    private fun openCheckForVerticalDrawer() {
        waitForDrawerOpen()
        drawerHandle.assertExists("Drawer Handle not shown")
        drawerContent.assertExists("Drawer Content not shown")
        drawerScrim.assertExists("Drawer Scrim not shown")
    }

    private fun closeCheckForVerticalDrawer() {
        waitForDrawerClose()
        drawerHandle.assertDoesNotExist()
        drawerScrim.assertDoesNotExist()
        drawerContent.assertDoesNotExist()
    }

    private fun dpToPx(value: Dp) = (value * Resources
        .getSystem()
        .displayMetrics.density).value

    @Test
    fun testBottomDrawer1() {
        composeTestRule.onNodeWithText(
            getString(R.string.drawer_bottom_slide_over),
            useUnmergedTree = true
        ).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForVerticalDrawer()

        val scrimEnd = drawerHandle.fetchSemanticsNode().positionInRoot.y.toInt()

        //Click on scrim covered by drawer should not close it.
        drawerScrim.performTouchInput {
            click(Offset((0..width).random().toFloat(), (scrimEnd..height).random().toFloat()))
        }
        openCheckForVerticalDrawer()

        //Click on scrim not covered by drawer should close it.
        drawerScrim.performTouchInput {
            click(Offset((0..width).random().toFloat(), (0..scrimEnd).random().toFloat()))
        }
        closeCheckForVerticalDrawer()
    }

    @Test
    fun testBottomDrawer2() {
        composeTestRule.onNodeWithText(
            getString(R.string.drawer_bottom_slide_over),
            useUnmergedTree = true
        ).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForVerticalDrawer()
        //SwipeDown on drawerHandle should close it.
        drawerHandle.performTouchInput {
            swipeDown(
                endY = drawerScrim.fetchSemanticsNode().size.height.toFloat()
            )
        }
        closeCheckForVerticalDrawer()
    }

    @Test
    fun testBottomDrawer3() {
        composeTestRule.onNodeWithText(
            getString(R.string.drawer_bottom_slide_over),
            useUnmergedTree = true
        ).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForVerticalDrawer()

        val drawerStart = drawerHandle.fetchSemanticsNode().positionInRoot.y.toInt()

        //SwipeDown on scrollable drawerContent should close it.

        val swipeEnd = drawerScrim.fetchSemanticsNode().size.height
        val swipeStart = (drawerStart..((swipeEnd + drawerStart) / 2)).random()
        drawerContent.performTouchInput {
            swipeDown(
                startY = swipeStart.toFloat(),
                endY = swipeEnd.toFloat()
            )
        }
        closeCheckForVerticalDrawer()
    }

    @Test
    fun testBottomDrawer4() {
        composeTestRule.onNodeWithText(
            getString(R.string.drawer_bottom_slide_over),
            useUnmergedTree = true
        ).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForVerticalDrawer()

        val scrimEnd = drawerHandle.fetchSemanticsNode().positionInRoot.y.toInt()

        //SwipeUp on drawer should not close it. Instead it expand the drawer,if possible
        drawerContent.performTouchInput { swipeUp(startY = (scrimEnd..height).random().toFloat()) }
        openCheckForVerticalDrawer()

        //SwipeDown of drawerHandle to bottom should close it.
        drawerHandle.performTouchInput {
            swipeDown(
                endY = drawerScrim.fetchSemanticsNode().size.height.toFloat()
            )
        }
        closeCheckForVerticalDrawer()
    }

    @Test
    fun testBottomDrawer5() {
        composeTestRule.onNodeWithText(
            getString(R.string.drawer_bottom_slide_over),
            useUnmergedTree = true
        ).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForVerticalDrawer()

        //SwipeDown a little should not close the drawer
        drawerHandle.performTouchInput {
            swipeDown(
                endY = top + (0..dpToPx(26.dp).toInt()).random()
            )
        }
        openCheckForVerticalDrawer()

    }

    @Test
    fun testBottomDrawer6() {
        composeTestRule.onNodeWithText(
            getString(R.string.drawer_bottom_slide_over),
            useUnmergedTree = true
        ).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_expand)).performClick()
        openCheckForVerticalDrawer()

        //SwipeDown on drawerHandle should close it.
        drawerHandle.performTouchInput {
            swipeDown(
                endY = drawerScrim.fetchSemanticsNode().size.height.toFloat()
            )
        }
        closeCheckForVerticalDrawer()
    }

    @Test
    fun testBottomDrawer7() {
        composeTestRule.onNodeWithText(
            getString(R.string.drawer_bottom_slide_over),
            useUnmergedTree = true
        ).performClick()
        composeTestRule.onNodeWithText("Show Handle", useUnmergedTree = true).performClick()
        //TODO: TO open Drawer, "Open Drawer" button needed to be clicked twice.
        // Investigated that the animateTo is not invoked with one click.
        // However, it is invoked 2 time on next click & then 2 times in retry.
        composeTestRule.onNodeWithText("Open Drawer").performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()

        //Content should be visible without handle
        drawerContent.assertExists("Drawer Content not shown")
        drawerHandle.assertDoesNotExist()

        drawerContent.printToLog("temp")
        drawerScrim.printToLog("temp")
        //SwipeDown on drawerContent should close it.
        drawerContent.performTouchInput {
            swipeDown()
        }
        closeCheckForVerticalDrawer()
    }

    @Test
    fun testBottomDrawer8() {
        composeTestRule.onNodeWithText("Bottom", useUnmergedTree = true).performClick()
        //TODO: TO open Bottom Drawer, "Open Drawer" button needed to be clicked twice.
        // Investigated that the animateTo is not invoked with one click.
        // However, it is invoked 2 time on next click & then 2 times in retry.

        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        //SwipeDown on drawerHandle should close it.
        drawerHandle.performTouchInput {
            swipeDown(
                endY = drawerScrim.fetchSemanticsNode().size.height.toFloat()
            )
        }
        composeTestRule.waitForIdle()
        if (composeTestRule.onAllNodesWithTag(DRAWER_HANDLE_TAG).fetchSemanticsNodes()
                .isNotEmpty()
        ) {
            drawerHandle.performTouchInput {
                swipeDown(
                    endY = drawerScrim.fetchSemanticsNode().size.height.toFloat()
                )
            }
        }
        closeCheckForVerticalDrawer()
    }
    @Test
    fun testNonExpandableAndExpandDrawerClicked() {
        // Set expandable = false
        composeTestRule.onNodeWithText(getString(R.string.drawer_expandable), useUnmergedTree = true).performClick()
        // Click on the "Open Drawer" button
        composeTestRule.onNodeWithText(getString(R.string.drawer_expand)).performClick()
        //Skip Open State Button must be disabled
        composeTestRule.onNodeWithText(getString(R.string.skip_open_state), useUnmergedTree = true).assertHasNoClickAction()
        // Click on the "Open Drawer" button
        composeTestRule.onNodeWithText(getString(R.string.drawer_expand)).performClick()
        composeTestRule.waitForIdle()

        openCheckForVerticalDrawer()
    }
}