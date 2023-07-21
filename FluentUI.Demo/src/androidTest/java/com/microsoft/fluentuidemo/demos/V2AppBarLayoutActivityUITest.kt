package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class V2AppBarLayoutActivityUITest : BaseTest() {

    // Tags used for testing
    private val MODIFIABLE_PARAMETER_SECTION = "Modifiable Parameters"
    private val SUBTITLE_PARAM = "Subtitle Param"
    private val APPBAR_STYLE_PARAM = "AppBar Style Param"
    private val BUTTONBAR_PARAM = "ButtonBar Param"
    private val SEARCHBAR_PARAM = "SearchBar Param"
    private val APP_BAR = "App bar"
    private val SUBTITLE = "Subtitle"
    private val BOTTOM_BAR = "Bottom bar"
    private val SEARCH_BAR = "Search bar"

    @Before
    fun initialize() {
        launchActivity(V2AppBarLayoutActivity::class.java)
    }

    private val modifiableParametersButton =
        composeTestRule.onAllNodesWithTag(MODIFIABLE_PARAMETER_SECTION)[0]

    @Test
    fun testAppBarDisplay() {
        composeTestRule.onNodeWithTag(APP_BAR).assertExists()
    }

    @Test
    fun testAppBarSubtitleToggle() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(SUBTITLE_PARAM)
        val component = composeTestRule.onNodeWithTag(SUBTITLE)
        assertExistsAfterToggleOnly(control, component, "Subtitle is not displayed")
    }

    @Test
    fun testAppBarBottomBarToggle() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(BUTTONBAR_PARAM)
        val component = composeTestRule.onNodeWithTag(BOTTOM_BAR)
        assertExistsAfterToggleOnly(control, component, "Bottom bar is not displayed")
    }

    @Test
    fun testAppBarSearchBarToggle() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(SEARCHBAR_PARAM)
        val component = composeTestRule.onNodeWithTag(SEARCH_BAR)
        assertExistsAfterToggleOnly(control, component, "Search bar is not displayed")
    }

    @Test
    fun testAppBarStyleToggle() {
        modifiableParametersButton.performClick()
        val control = composeTestRule.onNodeWithTag(APPBAR_STYLE_PARAM)
        val component = composeTestRule.onNodeWithTag(APP_BAR)
        toggleControlToValue(control, true)
        component.assertExists("App bar is not displayed in accent")
    }
}