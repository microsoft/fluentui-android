package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2TabBarActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2TabBarActivity::class.java)
    }

    @Test
    fun testAll() {
        val verticalRadio = composeTestRule.onNodeWithTag(TAB_BAR_VERTICAL_RADIO)
        val horizontalRadio = composeTestRule.onNodeWithTag(TAB_BAR_HORIZONTAL_RADIO)
        val noTextRadio = composeTestRule.onNodeWithTag(TAB_BAR_NO_TEXT_RADIO)
        val addButton = composeTestRule.onNodeWithTag(TAB_BAR_ADD_BUTTON)
        val removeButton = composeTestRule.onNodeWithTag(TAB_BAR_REMOVE_BUTTON)
        val tabBar = composeTestRule.onNodeWithTag(TAB_BAR)

        toggleControlToValue(verticalRadio, true)
        tabBar.assertExists("Vertical TabBar did not render")
        toggleControlToValue(horizontalRadio, true)
        tabBar.assertExists("Horizontal TabBar did not render")
        toggleControlToValue(noTextRadio, true)
        tabBar.assertExists("No Text TabBar did not render")

        while (isEnabled(removeButton))
            removeButton.performClick()
        tabBar.assertExists("Removing all tabs did not work")

        while (isEnabled(addButton))
            addButton.performClick()
        tabBar.assertExists("Adding all tabs did not work")
    }

}