package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.*
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2BasicControlsActivityUITest : BaseTest() {
    @Before
    fun initialize() {
        launchActivity(V2BasicControlsActivity::class.java)
    }

    @Test
    fun testSwitch() {
        val enableSwitch = composeTestRule.onNodeWithTag(BASIC_CONTROLS_TOGGLE_ENABLE)
        val checkbox = composeTestRule.onNodeWithTag(BASIC_CONTROLS_CHECK_BOX)
        checkbox.assertExists("Checkbox is not displayed")
        checkbox.assertIsNotEnabled()
        toggleControlToValue(enableSwitch, true)
        checkbox.assertIsEnabled()
    }

    @Test
    fun testCheckbox() {
        val enableSwitch = composeTestRule.onNodeWithTag(BASIC_CONTROLS_TOGGLE_ENABLE)
        val checkbox = composeTestRule.onNodeWithTag(BASIC_CONTROLS_CHECK_BOX)
        checkbox.assertExists("Checkbox is not displayed")
        toggleControlToValue(enableSwitch, true)
        toggleControlToValue(checkbox, true)
        toggleControlToValue(checkbox, false)
    }

    @Test
    fun testRadio() {
        val enableSwitch = composeTestRule.onNodeWithTag(BASIC_CONTROLS_TOGGLE_ENABLE)
        val radio1 = composeTestRule.onNodeWithTag(BASIC_CONTROLS_RADIO_1)
        val radio2 = composeTestRule.onNodeWithTag(BASIC_CONTROLS_RADIO_2)
        radio1.assertExists("Radio 1 is not displayed")
        radio2.assertExists("Radio 2 is not displayed")
        toggleControlToValue(enableSwitch, true)
        radio1.assertIsSelected()
        radio2.assertIsNotSelected()
        toggleControlToValue(radio2, true)
        radio1.assertIsNotSelected()
        radio2.assertIsSelected()
        toggleControlToValue(radio1, true)
        radio1.assertIsSelected()
        radio2.assertIsNotSelected()
    }

}