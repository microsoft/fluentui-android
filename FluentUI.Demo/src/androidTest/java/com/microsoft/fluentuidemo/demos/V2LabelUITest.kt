package com.microsoft.fluentuidemo.demos

import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.sp
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
                BasicText(
                    text = "Display Basic text", style = TextStyle(
                        fontSize = 60.sp,
                        lineHeight = 72.sp,
                        fontWeight = FontWeight(400),
                        letterSpacing = (-0.5).sp
                    ),
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
                Label.LargeTitle(text = "LargeTitle")
                BasicText(
                    text = "LargeTitle Basic text", style = TextStyle(
                        fontSize = 34.sp,
                        lineHeight = 48.sp,
                        fontWeight = FontWeight(400),
                        letterSpacing = (-0.25).sp
                    ),
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
                Label.Title1(text = "Title1")
                BasicText(
                    text = "Title1 Basic text", style = TextStyle(
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        fontWeight = FontWeight(700),
                        letterSpacing = 0.sp
                    ),
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
                Label.Title2(text = "Title2")
                BasicText(
                    text = "Title2 Basic text", style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(500),
                        letterSpacing = 0.sp
                    ),
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
                Label.Title3(text = "Title3")
                BasicText(
                    text = "Title3 Basic text", style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(500),
                        letterSpacing = 0.sp
                    ),
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
                Label.Body1Strong(text = "Body1Strong")
                BasicText(
                    text = "Body1Strong Basic text", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(600),
                        letterSpacing = 0.sp
                    ),
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
                Label.Body1(text = "Body1")
                BasicText(
                    text = "Body1 Basic text", style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        letterSpacing = 0.sp
                    ),
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
                Label.Body2Strong(text = "Body2Strong")
                BasicText(
                    text = "Body2Strong Basic text", style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(500),
                        letterSpacing = 0.sp
                    ),
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
                Label.Body2(text = "Body2")
                BasicText(
                    text = "Body2 Basic text", style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(400),
                        letterSpacing = 0.sp
                    ),
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
                Label.Caption1Strong(text = "Caption1Strong")
                BasicText(
                    text = "Caption1Strong Basic text", style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(500),
                        letterSpacing = 0.sp
                    ),
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
                Label.Caption1(text = "Caption1")
                BasicText(
                    text = "Caption1 Basic text", style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(400),
                        letterSpacing = 0.sp
                    ),
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
                Label.Caption2(text = "Caption2")
                BasicText(
                    text = "Caption2 Basic text", style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight(400),
                        letterSpacing = 0.sp
                    ),
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