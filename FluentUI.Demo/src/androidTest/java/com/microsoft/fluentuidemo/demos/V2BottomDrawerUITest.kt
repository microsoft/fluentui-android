package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.UUID

class V2BottomDrawerUITest {
    lateinit var context: Context
    private fun launchActivity() {
        ActivityScenario.launch<V2BottomDrawerActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, V2DrawerActivity::class.java)
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

    //Test case to check BottomDrawer is closed by default
    @Test
    fun testBottomDrawerIsClosedByDefault() {
        drawerHandle.assertDoesNotExist()
        drawerContent.assertDoesNotExist()
        drawerScrim.assertDoesNotExist()
    }

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

    @After
    fun tearDown() {
        Intents.release()
    }

}