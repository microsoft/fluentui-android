package com.microsoft.fluentuidemo.demos

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.google.android.material.internal.ViewUtils.dpToPx
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.tokenized.controls.Label
import org.junit.Rule
import org.junit.Test


class V2LabelUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDisplayLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Display(text = "Display")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Display")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testLargeTitleLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label.LargeTitle(text = "LargeTitle")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("LargeTitle")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testTitle1Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Title1(text = "Title1")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Title1")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }@Test
    fun testTitle2Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Title2(text = "Title2")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Title2")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testTitle3Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Title3(text = "Title3")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Title3")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testBody1StrongLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Body1Strong(text = "Body1Strong")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Body1Strong")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testBody1Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Body1(text = "Body1")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Body1")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }@Test
    fun testBody2StrongLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Body2Strong(text = "Body2Strong")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Body2Strong")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testBody2Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Body2(text = "Body2")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Body2")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testCaption1StrongLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Caption1Strong(text = "Caption1Strong")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Caption1Strong")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testCaption1Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Caption1(text = "Caption1")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Caption1")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
    @Test
    fun testCaption2Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label.Caption2(text = "Caption2")
            }
        }
        composeTestRule.onRoot(true).printToLog("Tree")
        val label = composeTestRule.onNodeWithText("Caption2")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
    }
}