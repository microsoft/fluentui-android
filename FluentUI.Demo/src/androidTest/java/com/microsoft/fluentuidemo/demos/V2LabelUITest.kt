package com.microsoft.fluentuidemo.demos

import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.height
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens
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
                Label(text = "Display", textStyle = TypographyTokens.Display)
                BasicText(
                    text = "Display Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Display],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Display")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Display Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testLargeTitleLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "LargeTitle", textStyle = TypographyTokens.LargeTitle)
                BasicText(
                    text = "LargeTitle Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.LargeTitle],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("LargeTitle")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("LargeTitle Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testTitle1Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Title1", textStyle = TypographyTokens.Title1)
                BasicText(
                    text = "Title1 Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Title1],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Title1")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Title1 Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testTitle2Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Title2", textStyle = TypographyTokens.Title2)
                BasicText(
                    text = "Title2 Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Title2],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Title2")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Title2 Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testTitle3Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Title3", textStyle = TypographyTokens.Title3)
                BasicText(
                    text = "Title3 Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Title3],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Title3")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Title3 Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testBody1StrongLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Body1Strong", textStyle = TypographyTokens.Body1Strong)
                BasicText(
                    text = "Body1Strong Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Body1Strong],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Body1Strong")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Body1Strong Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testBody1Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Body1", textStyle = TypographyTokens.Body1)
                BasicText(
                    text = "Body1 Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Body1],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Body1")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Body1 Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testBody2StrongLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Body2Strong", textStyle = TypographyTokens.Body2Strong)
                BasicText(
                    text = "Body2Strong Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Body2Strong],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Body2Strong")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Body2Strong Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testBody2Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Body2", textStyle = TypographyTokens.Body2)
                BasicText(
                    text = "Body2 Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Body2],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Body2")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Body2 Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testCaption1StrongLabel() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Caption1Strong", textStyle = TypographyTokens.Caption1Strong)
                BasicText(
                    text = "Caption1Strong Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Caption1Strong],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Caption1Strong")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Caption1Strong Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testCaption1Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Caption1", textStyle = TypographyTokens.Caption1)
                BasicText(
                    text = "Caption1 Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Caption1],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Caption1")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Caption1 Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }

    @Test
    fun testCaption2Label() {
        composeTestRule.setContent {
            FluentTheme {
                Label(text = "Caption2", textStyle = TypographyTokens.Caption2)
                BasicText(
                    text = "Caption2 Basic text",
                    style = FluentTheme.aliasTokens.typography[TypographyTokens.Caption2],
                    softWrap = false
                )
            }
        }
        val label = composeTestRule.onNodeWithText("Caption2")
        label.assertExists()
        label.assertIsDisplayed()
        label.assertHasNoClickAction()
        val refText = composeTestRule.onNodeWithText("Caption2 Basic text")
        label.assertHeightIsEqualTo(refText.getBoundsInRoot().height)
    }
}