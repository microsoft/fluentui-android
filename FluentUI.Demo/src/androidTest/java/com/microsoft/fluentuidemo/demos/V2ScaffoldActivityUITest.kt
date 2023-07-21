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

    //Tag for Test
    private val TOP_BAR = "TopBar"
    private val BOTTOM_BAR = "BottomBar"
    private val SNACKBAR = "SnackBar"
    private val DRAWER = "Drawer"
    private val FLOATING_ACTION_BUTTON = "FAB"
    private val MAIN_CONTENT = "Main Content"
    private val DRAWER_BUTTON = "Drawer Button"
    private val SNACKBAR_BUTTON = "Snackbar Button"

    @Test
    fun testOnScreenComponents() {
        composeTestRule.onNodeWithTag(TOP_BAR).assertExists("Top Bar not shown")
        composeTestRule.onNodeWithTag(BOTTOM_BAR).assertExists("Bottom Bar not shown")
        composeTestRule.onNodeWithTag(FLOATING_ACTION_BUTTON).assertExists("FAB not shown")
        composeTestRule.onNodeWithTag(MAIN_CONTENT).assertExists("Main content not shown")
    }

    @Test
    fun testSnackbar() {
        composeTestRule.onNodeWithTag(SNACKBAR_BUTTON).performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(SNACKBAR).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    @Test
    fun testDrawer() {
        composeTestRule.onNodeWithTag(DRAWER_BUTTON).performClick()
        composeTestRule.waitUntil(1000L) {
            composeTestRule.onAllNodesWithTag(DRAWER).fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

}