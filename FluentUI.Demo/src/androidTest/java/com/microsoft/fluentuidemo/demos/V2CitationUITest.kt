package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.microsoft.fluentui.tokenized.notification.Citation
import org.junit.Rule
import org.junit.Test

class V2CitationUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testCitation() {
        composeTestRule.setContent {
            Citation(text = "1")
        }
        composeTestRule.onNodeWithText("1").assertExists().assertIsDisplayed()
    }

    @Test
    fun testCitationClickAction() {
        composeTestRule.setContent {
            Citation(text = "1")
            Citation(text = "2", onClick = {})
        }
        composeTestRule.onNodeWithText("1").assertHasNoClickAction()
        composeTestRule.onNodeWithText("2").assertHasClickAction()
    }
}