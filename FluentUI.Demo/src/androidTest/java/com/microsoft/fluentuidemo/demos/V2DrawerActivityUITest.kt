package com.microsoft.fluentuidemo.demos

import android.content.Intent
import android.content.res.Resources
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

//Below tag name used in Drawer component
const val DRAWER_HANDLE_TAG = "Drawer Handle"
const val DRAWER_CONTENT_TAG = "Drawer Content"
const val DRAWER_SCRIM_TAG = "Drawer Scrim"


class V2DrawerActivityUITest {
    private fun launchActivity() {
        ActivityScenario.launch<V2DrawerActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2DrawerActivity::class.java)
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

    private val drawerHandle = composeTestRule.onNodeWithTag(DRAWER_HANDLE_TAG)
    private val drawerContent = composeTestRule.onNodeWithTag(DRAWER_CONTENT_TAG)
    private val drawerScrim = composeTestRule.onNodeWithTag(DRAWER_SCRIM_TAG)

    private fun openCheckForVerticalDrawer() {
        drawerHandle.assertExists("Drawer Handle not shown")
        drawerContent.assertExists("Drawer Content not shown")
        drawerScrim.assertExists("Drawer Scrim not shown")
    }

    private fun closeCheckForVerticalDrawer() {
        drawerHandle.assertDoesNotExist()
        drawerScrim.assertDoesNotExist()
        drawerContent.assertDoesNotExist()
    }

    private fun openCheckForHorizontalDrawer() {
        drawerContent.assertExists("Drawer Content not shown")
        drawerScrim.assertExists("Drawer Scrim not shown")
    }

    private fun closeCheckForHorizontalDrawer() {
        drawerScrim.assertDoesNotExist()
        drawerContent.assertDoesNotExist()
    }

    private fun dpToPx(value: Dp) = (value * Resources
        .getSystem()
        .displayMetrics.density).value

    @Test
    fun testBottomDrawer1() {
        composeTestRule.onNodeWithText("Bottom Slide Over", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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
        composeTestRule.onNodeWithText("Bottom Slide Over", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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
        composeTestRule.onNodeWithText("Bottom Slide Over", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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
        composeTestRule.onNodeWithText("Bottom Slide Over", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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
        composeTestRule.onNodeWithText("Bottom Slide Over", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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
        composeTestRule.onNodeWithText("Left Slide Over", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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
        composeTestRule.onNodeWithText("Right Slide Over", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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
        composeTestRule.onNodeWithText("Top", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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
        composeTestRule.onNodeWithText("Top", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Open Drawer").performClick()
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

    @After
    fun tearDown() {
        Intents.release()
    }
}