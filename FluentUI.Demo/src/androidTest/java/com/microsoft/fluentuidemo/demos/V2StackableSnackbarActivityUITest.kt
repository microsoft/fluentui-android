package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.*
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2StackableSnackbarActivityUITest : BaseTest() {

    // These tags are private to StackableSnackbar.
    private val snackBarTag = "snack_bar"
    private val snackBarSubtitleTag = "snack_bar_subtitle"
    private val snackBarIconTag = "snack_bar_icon"

    @Before
    fun initialize() {
        launchActivity(V2StackableSnackbarActivity::class.java)
    }

    private fun addLongSubtitleSnackbar() {
        composeTestRule.onNodeWithText("Add Long Subtitle Snackbar").performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(snackBarTag).fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun testSubtitleIsDisplayed() {
        addLongSubtitleSnackbar()
        composeTestRule.onNodeWithTag(snackBarSubtitleTag).assertExists()
    }

    @Test
    fun testSubtitleIsIncludedInAccessibilityDescription() {
        addLongSubtitleSnackbar()
        val description = composeTestRule.onNodeWithTag(snackBarTag)
            .fetchSemanticsNode().config[SemanticsProperties.ContentDescription].joinToString()
        assert(description.contains("Short title #0")) { "Missing title in: $description" }
        assert(description.contains("Long subtitle")) { "Missing subtitle in: $description" }
    }

    @Test
    fun testOverflowingSubtitleShowsChevron() {
        addLongSubtitleSnackbar()
        // The demo sets the chevron trailing icon from onTextOverflowChange.
        composeTestRule.waitUntil(2000L) {
            composeTestRule.onAllNodesWithTag(snackBarIconTag).fetchSemanticsNodes().isNotEmpty()
        }
    }
}
