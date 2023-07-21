package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2ScaffoldActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2ScaffoldActivity::class.java)
    }

    @Test
    fun testOnScreenComponents() {
        composeTestRule.onNodeWithTag(SCAFFOLD_TOP_BAR).assertExists("Top Bar not shown")
        composeTestRule.onNodeWithTag(SCAFFOLD_BOTTOM_BAR).assertExists("Bottom Bar not shown")
        composeTestRule.onNodeWithTag(SCAFFOLD_FLOATING_ACTION_BUTTON).assertExists("FAB not shown")
        composeTestRule.onNodeWithTag(SCAFFOLD_MAIN_CONTENT).assertExists("Main content not shown")
    }

    @Test
    fun testSnackbar() {
        composeTestRule.onNodeWithTag(SCAFFOLD_SNACKBAR_BUTTON).performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SCAFFOLD_SNACKBAR).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testDrawer() {
        composeTestRule.onNodeWithTag(SCAFFOLD_DRAWER_BUTTON).performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SCAFFOLD_DRAWER).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

}