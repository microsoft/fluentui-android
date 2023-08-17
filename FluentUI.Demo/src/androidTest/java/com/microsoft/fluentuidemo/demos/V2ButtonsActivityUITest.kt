package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2ButtonsActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2ButtonsActivity::class.java)
    }

    @Test
    fun testFAB() {
        val fabButton = composeTestRule.onNodeWithTag("FAB", true)
        fabButton.assertExists("FAB did not render")
        fabButton.assertHasClickAction()
        fabButton.performClick()
        fabButton.assertExists("FAB crashed on Click")
    }

    @Test
    fun testButtonOnClickAndDisabled() {
        val testButton = composeTestRule.onAllNodesWithText("Button 0")[0]
        testButton.assertExists("Button did not render")
        testButton.assertHasClickAction()
        testButton.assertIsEnabled()
        composeTestRule.onAllNodesWithText("Click to Disable")[0].performClick()
        testButton.assertIsNotEnabled()
    }

    @Test
    fun testThemeChange() {
        val theme1Button = composeTestRule.onNodeWithText("Theme1")
        val theme2Button = composeTestRule.onNodeWithText("Theme2")
        theme2Button.performClick()
        theme1Button.performClick()
        theme1Button.assertExists("Theme change crashing")
        theme2Button.assertExists("Theme change crashing")
    }

}