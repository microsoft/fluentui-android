package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2SegmentedControlActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2SegmentedControlActivity::class.java)
    }

    @Test
    fun testPill() {
        val section = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_PILL_BUTTON)
        val toggle = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_PILL_BUTTON_TOGGLE)
        val component = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_PILL_BUTTON_COMPONENT)
        section.assertExists("Pill button section did not render")
        toggle.assertExists("Pill button toggle did not render")
        section.performClick()
        component.assertExists("Pill button component did not render")
        component.assertIsEnabled()
        toggleControlToValue(toggle, false)
        component.assertIsNotEnabled()
    }

    @Test
    fun testPillBar() {
        val section = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_PILL_BAR)
        val toggle = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_PILL_BAR_TOGGLE)
        val component = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_PILL_BAR_COMPONENT, true)
        section.assertExists("Pill bar section did not render")
        toggle.assertExists("Pill bar toggle did not render")
        section.performClick()
        component.assertExists("Pill bar component did not render")
        toggleControlToValue(toggle, false)
        component.assertExists("Pill bar component did not render after toggle")

    }

    @Test
    fun testTabs() {
        val section = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_TABS)
        val toggle = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_TABS_TOGGLE)
        section.assertExists("Tab button section did not render")
        toggle.assertExists("Tab button toggle did not render")
        section.performClick()
        toggleControlToValue(toggle, false)
    }

    @Test
    fun testSwitch() {
        val section = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_SWITCH)
        val toggle = composeTestRule.onNodeWithTag(SEGMENTED_CONTROL_SWITCH_TOGGLE)
        section.assertExists("Switch section did not render")
        toggle.assertExists("Switch toggle did not render")
        section.performClick()
        toggleControlToValue(toggle, false)
    }
}