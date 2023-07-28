package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.espresso.Espresso
import com.microsoft.fluentui.tokenized.menu.DIALOG_TEST_TAG
import com.microsoft.fluentuidemo.BaseTest
import com.microsoft.fluentuidemo.R
import org.junit.Before
import org.junit.Test

class V2DialogActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2DialogActivity::class.java)
    }

    @Test
    fun testDialog() {
        composeTestRule.onNodeWithText(getString(R.string.show_dialog)).performClick()
        val dialog = composeTestRule.onNodeWithTag(DIALOG_TEST_TAG)
        dialog.assertExists()
        dialog.assertIsDisplayed()
        composeTestRule.onNodeWithText(getString(R.string.ok)).assertExists().assertIsDisplayed()
    }

    @Test
    fun testBackPressDialog() {
        composeTestRule.onNodeWithText(getString(R.string.show_dialog)).performClick()
        val dialog = composeTestRule.onNodeWithTag(DIALOG_TEST_TAG)
        dialog.assertExists()
        dialog.assertIsDisplayed()
        Espresso.pressBack()
        dialog.assertExists()
    }

    @Test
    fun testBackPressDismissDialog() {
        composeTestRule.onNodeWithTag("back press").performClick()
        composeTestRule.onNodeWithText(getString(R.string.show_dialog)).performClick()
        val dialog = composeTestRule.onNodeWithTag(DIALOG_TEST_TAG)
        dialog.assertExists()
        dialog.assertIsDisplayed()
        Espresso.pressBack()
        dialog.assertDoesNotExist()
    }
}