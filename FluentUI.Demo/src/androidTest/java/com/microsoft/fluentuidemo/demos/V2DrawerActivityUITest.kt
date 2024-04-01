package com.microsoft.fluentuidemo.demos

import android.content.res.Resources
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.drawer.DRAWER_CONTENT_TAG
import com.microsoft.fluentui.tokenized.drawer.DRAWER_HANDLE_TAG
import com.microsoft.fluentui.tokenized.drawer.DRAWER_SCRIM_TAG
import com.microsoft.fluentuidemo.BaseTest
import com.microsoft.fluentuidemo.R
import org.junit.Before
import org.junit.Test

class V2DrawerActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2DrawerActivity::class.java)
    }

    private val drawerHandle = composeTestRule.onNodeWithTag(DRAWER_HANDLE_TAG)
    private val drawerContent = composeTestRule.onNodeWithTag(DRAWER_CONTENT_TAG)
    private val drawerScrim = composeTestRule.onNodeWithTag(DRAWER_SCRIM_TAG)

    @OptIn(ExperimentalTestApi::class)
    private fun waitForDrawerOpen(){
        composeTestRule.waitUntilExactlyOneExists(hasTestTag(DRAWER_CONTENT_TAG))
    }

    @OptIn(ExperimentalTestApi::class)
    private fun waitForDrawerClose(){
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

    private fun openCheckForHorizontalDrawer() {
        waitForDrawerOpen()
        drawerContent.assertExists("Drawer Content not shown")
        drawerScrim.assertExists("Drawer Scrim not shown")
    }

    private fun closeCheckForHorizontalDrawer() {
        waitForDrawerClose()
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

        //Click on drawer should not close it.
        drawerScrim.performTouchInput {
            click(Offset((0..width).random().toFloat(), (scrimEnd..height).random().toFloat()))
        }
        openCheckForVerticalDrawer()

        //Click on scrim should close it.
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
                startY = drawerHandle.fetchSemanticsNode().positionInRoot.y,
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

        //SwipeDown on drawerContent should close it.

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
                startY = drawerHandle.fetchSemanticsNode().positionInRoot.y,
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
                startY = drawerHandle.fetchSemanticsNode().positionInRoot.y,
                endY = drawerHandle.fetchSemanticsNode().positionInRoot.y + (0..dpToPx(26.dp).toInt()).random()
            )
        }
        composeTestRule.waitForIdle()
        openCheckForVerticalDrawer()

    }

    @Test
    fun testLeftDrawer1() {
        composeTestRule.onNodeWithText("Left Slide Over", useUnmergedTree = true).performClick()
        //TODO: TO open Drawer, "Open Drawer" button needed to be clicked twice.
        // Investigated that the animateTo is not invoked with one click.
        // However, it is invoked 2 time on next click & then 2 times in retry.
        composeTestRule.onNodeWithText("Open Drawer").performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
        openCheckForHorizontalDrawer()

        val drawerEnd = drawerContent.fetchSemanticsNode().boundsInRoot.right.toInt()

        //Click on drawer content should not close drawer
        drawerScrim.performTouchInput {
            click(Offset((0..drawerEnd).random().toFloat(), (0..height).random().toFloat()))
        }
        openCheckForHorizontalDrawer()

        //Click on scrim should close drawer
        drawerScrim.performTouchInput {
            click(Offset((drawerEnd..width).random().toFloat(), (0..height).random().toFloat()))
        }
        closeCheckForHorizontalDrawer()
    }

    @Test
    fun testLeftDrawer2() {
        composeTestRule.onNodeWithText(getString(R.string.drawer_left_slide_over), useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForHorizontalDrawer()

        //Swipe right should not close the drawer
        drawerContent.performTouchInput { swipeRight() }
        openCheckForHorizontalDrawer()

        //Swipe left should not close the drawer
        drawerContent.performTouchInput { swipeLeft() }
        closeCheckForHorizontalDrawer()
    }

    @Test
    fun testRightDrawer1() {
        composeTestRule.onNodeWithText("Right Slide Over", useUnmergedTree = true).performClick()
        //TODO: TO open Drawer, "Open Drawer" button needed to be clicked twice.
        // Investigated that the animateTo is not invoked with one click.
        // However, it is invoked 2 time on next click & then 2 times in retry.
        composeTestRule.onNodeWithText("Open Drawer").performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
        openCheckForHorizontalDrawer()

        val drawerStart = drawerContent.fetchSemanticsNode().boundsInRoot.left.toInt()
        //Click on drawer content should not close drawer
        drawerScrim.performTouchInput {
            click(Offset((drawerStart..width).random().toFloat(), (0..height).random().toFloat()))
        }
        openCheckForHorizontalDrawer()

        //Click on scrim should close drawer
        drawerScrim.performTouchInput {
            click(Offset((0..drawerStart).random().toFloat(), (0..height).random().toFloat()))
        }
        closeCheckForHorizontalDrawer()
    }

    @Test
    fun testRightDrawer2() {
        composeTestRule.onNodeWithText(getString(R.string.drawer_right_slide_over), useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForHorizontalDrawer()

        //Swipe left should not close the drawer
        drawerContent.performTouchInput { swipeLeft() }
        openCheckForHorizontalDrawer()

        //Swipe right should close the drawer
        drawerContent.performTouchInput { swipeRight() }
        closeCheckForHorizontalDrawer()
    }

    @Test
    fun testTopDrawer1() {
        composeTestRule.onNodeWithText(getString(R.string.drawer_top), useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForVerticalDrawer()
        //Click on Drawer area
        drawerScrim.performTouchInput {
            val drawerLength = drawerHandle.fetchSemanticsNode().boundsInRoot.top.toInt()
            click(Offset((0..width).random().toFloat(), (0..drawerLength).random().toFloat()))
        }
        openCheckForVerticalDrawer()
        //Click on Scrim area
        drawerScrim.performTouchInput {
            val scrimStart = drawerHandle.fetchSemanticsNode().boundsInRoot.bottom + dpToPx(8.dp)
            click(
                Offset(
                    (0..width).random().toFloat(),
                    (scrimStart.toInt()..height).random().toFloat()
                )
            )
        }
        closeCheckForVerticalDrawer()
    }

    @Test
    fun testTopDrawer2() {
        composeTestRule.onNodeWithText(getString(R.string.drawer_top), useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText(getString(R.string.drawer_open)).performClick()
        openCheckForVerticalDrawer()

        //Swipe up on content should not close top drawer
        drawerContent.performTouchInput { swipeUp() }
        openCheckForVerticalDrawer()

        //Close Top Drawer by dragging Handle to top.
        drawerHandle.performTouchInput {
            swipeUp(startY = drawerHandle.fetchSemanticsNode().boundsInRoot.top)
        }
        closeCheckForVerticalDrawer()
    }

    @Test
    fun testPreventDrawerDismissalOnScrimClick(){
        composeTestRule.onNodeWithContentDescription(getString(R.string.prevent_scrim_click_dismissal), useUnmergedTree = true, ignoreCase = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
        openCheckForVerticalDrawer()
        drawerScrim.performTouchInput {
            click(Offset((0..width).random().toFloat(), (0..height).random().toFloat()))
        }
        openCheckForVerticalDrawer()
    }
}