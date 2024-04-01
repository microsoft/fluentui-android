package com.microsoft.fluentuidemo.demos

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.notification.TOOLTIP_CONTENT_TEST_TAG
import com.microsoft.fluentui.tokenized.notification.TOOLTIP_TIP_TEST_TAG
import com.microsoft.fluentui.tokenized.notification.ToolTipBox
import com.microsoft.fluentui.tokenized.notification.rememberTooltipState
import com.microsoft.fluentui.util.dpToPx
import com.microsoft.fluentuidemo.BaseTest
import com.microsoft.fluentuidemo.R
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class V2ToolTipActivityUITest : BaseTest() {
    @Before
    fun initialize() {
        launchActivity(V2ToolTipActivity::class.java)
    }

    val device = UiDevice.getInstance(getInstrumentation())
    val screenWidth = device.displayWidth
    val screenHeight = device.displayHeight

    @Test
    fun testTooltipBoxNotVisible() {
        //ToolTip should not present
        composeTestRule.onNodeWithTag(TOOLTIP_TIP_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TOOLTIP_CONTENT_TEST_TAG).assertDoesNotExist()
    }

    @Test
    fun testTooltipBoxVisible() {
        //Click on the button to show tooltip
        composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_top_start))
            .performClick()

        //ToolTip should present
        composeTestRule.onNodeWithTag(TOOLTIP_TIP_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TOOLTIP_CONTENT_TEST_TAG).assertIsDisplayed()
    }

    //Test to dismiss toolTip
    @Test
    fun testTooltipBoxDismissOnClickOnScreen() {
        //Click on the button to show tooltip
        composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_top_start))
            .performClick()

        //ToolTip should present
        composeTestRule.onNodeWithTag(TOOLTIP_TIP_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TOOLTIP_CONTENT_TEST_TAG).assertIsDisplayed()

        composeTestRule.waitForIdle()
        //Tap on the center of the screen to dismiss the ToolTip
        device.click(screenWidth / 2, screenHeight / 2)

        //ToolTip should not present
        composeTestRule.onNodeWithTag(TOOLTIP_TIP_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TOOLTIP_CONTENT_TEST_TAG).assertDoesNotExist()
    }

    //Test to check bottom align tooltip
    @Test
    fun testTooltipBottomAlignment() {
        //Click on the button to show tooltip
        composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_top_start))
            .performClick()

        composeTestRule.waitForIdle()
        //find the Tip
        val tip: UiObject2 = device.findObject(By.res(TOOLTIP_TIP_TEST_TAG))
        assertEquals(composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_top_start))
            .fetchSemanticsNode().boundsInWindow.bottom.toDouble(), tip.visibleBounds.top.toDouble(), 2.0)
    }

    //Test to check top align tooltip
    @Test
    fun testTooltipTopAlignment() {
        //Click on the button to show tooltip
        composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_bottom_start))
            .performClick()

        composeTestRule.waitForIdle()

        //find the Tip
        val tip: UiObject2 = device.findObject(By.res(TOOLTIP_TIP_TEST_TAG))

        val isTopAligned =
            composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_bottom_start))
                .fetchSemanticsNode().boundsInWindow.top >= tip.visibleBounds.bottom
        assert(isTopAligned)
    }

    //Test offset
    @Test
    fun testTooltipOffset() {
        //Enter offset values
        val xOffsetTextField =
            composeTestRule.onNodeWithTag(TOOLTIP_ACTIVITY_X_OFFSET_TEXTFIELD_TAG).onChildAt(0) //Text Field has two child composables, one is the actual text field and other is the spacer, hence picking first one

        xOffsetTextField.performTextInput("10")

        val yOffsetTextField =
            composeTestRule.onNodeWithTag(TOOLTIP_ACTIVITY_Y_OFFSET_TEXTFIELD_TAG).onChildAt(0)
        yOffsetTextField.performTextInput("10")

        //Click on the button to show tooltip
        composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_top_start))
            .assertIsDisplayed().performClick()

        composeTestRule.waitForIdle()
        //Find the Tip
        val tip: UiObject2 = device.findObject(By.res(TOOLTIP_TIP_TEST_TAG))

        //Check the position of the tip wrt to the button
        val isYOffsetCorrect =
            composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_top_start))
                .fetchSemanticsNode().boundsInWindow.bottom + dpToPx(10.dp).toInt() <= tip.visibleBounds.top

        assert(isYOffsetCorrect)

        val isXOffsetCorrect =
            composeTestRule.onNodeWithTag(context.resources.getString(R.string.tooltip_top_start))
                .fetchSemanticsNode().boundsInWindow.center.x.toInt() + dpToPx(10.dp).toInt() <= tip.visibleBounds.centerX()

        assert(isXOffsetCorrect)
    }
}

class V2ToolTipUITest {

    val device = UiDevice.getInstance(getInstrumentation())
    val screenWidth = device.displayWidth
    val screenHeight = device.displayHeight

    @get:Rule
    val composeTestRule = createComposeRule()

    //Test onDismiss callback
    @Test
    fun testDissmissCallBack() {
        val dissmissExecuted = mutableStateOf(false)
        composeTestRule.setContent {
            val tooltipState = rememberTooltipState()
            val scope = rememberCoroutineScope()
            ToolTipBox(
                tooltipContent = { /*TODO*/ },
                tooltipState = tooltipState,
                onDismissRequest = { dissmissExecuted.value = true }) {
                Button(onClick = { scope.launch { tooltipState.show() } }, text = "Button")
            }
        }
        composeTestRule.onNodeWithText("Button").assertIsDisplayed().performClick()

        //ToolTip should present
        composeTestRule.onNodeWithTag(TOOLTIP_TIP_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TOOLTIP_CONTENT_TEST_TAG).assertIsDisplayed()

        //Tap on the center of the screen to dismiss the ToolTip
        device.click(screenWidth / 2, screenHeight / 2)

        //ToolTip should not present
        composeTestRule.onNodeWithTag(TOOLTIP_TIP_TEST_TAG).assertDoesNotExist()
        composeTestRule.onNodeWithTag(TOOLTIP_CONTENT_TEST_TAG).assertDoesNotExist()

        assert(dissmissExecuted.value)
    }
}