package com.microsoft.fluentuidemo.demos

import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import com.microsoft.fluentui.tokenized.progress.LinearProgressIndicator
import com.microsoft.fluentui.tokenized.progress.ProgressText
import org.junit.Rule
import org.junit.Test

class V2ProgressIndicatorUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLinearProgressIndicatorBounds() {
        composeTestRule.setContent {
            FluentTheme {
                LinearProgressIndicator(
                    modifier = Modifier
                        .testTag("testPI")
                        .width(200.dp)
                )
            }
        }
        composeTestRule.onNodeWithTag("testPI").assertHeightIsEqualTo(2.dp)
            .assertWidthIsEqualTo(200.dp)
    }

    @Test
    fun testCircularProgressIndicatorSize() {
        composeTestRule.setContent {
            FluentTheme {
                CircularProgressIndicator(
                    size = CircularProgressIndicatorSize.XXSmall,
                    modifier = Modifier.testTag("12dp")
                )
                CircularProgressIndicator(
                    size = CircularProgressIndicatorSize.XSmall,
                    modifier = Modifier.testTag("16dp")
                )
                CircularProgressIndicator(
                    size = CircularProgressIndicatorSize.Medium,
                    modifier = Modifier.testTag("24dp")
                )
                CircularProgressIndicator(
                    size = CircularProgressIndicatorSize.Large,
                    modifier = Modifier.testTag("32dp")
                )
                CircularProgressIndicator(
                    size = CircularProgressIndicatorSize.XLarge,
                    modifier = Modifier.testTag("40dp")
                )
            }
        }
        composeTestRule.onNodeWithTag("12dp").assertExists().assertHeightIsEqualTo(12.dp)
        composeTestRule.onNodeWithTag("16dp").assertExists().assertHeightIsEqualTo(16.dp)
        composeTestRule.onNodeWithTag("24dp").assertExists().assertHeightIsEqualTo(24.dp)
        composeTestRule.onNodeWithTag("32dp").assertExists().assertHeightIsEqualTo(32.dp)
        composeTestRule.onNodeWithTag("40dp").assertExists().assertHeightIsEqualTo(40.dp)
    }

    @Test
    fun testLinearDeterminateIndicatorProgress() {
        composeTestRule.setContent {
            FluentTheme {
                LinearProgressIndicator(progress = 0.2f, modifier = Modifier.testTag("testPI"))
            }
        }
        val progressIndicator = composeTestRule.onNodeWithTag("testPI")
        progressIndicator.assertRangeInfoEquals(ProgressBarRangeInfo(0.2f, 0f..1f))
    }

    @Test
    fun testCircularDeterminateIndicatorProgress() {
        composeTestRule.setContent {
            FluentTheme {
                CircularProgressIndicator(progress = 0.2f, modifier = Modifier.testTag("testPI"))
            }
        }
        val progressIndicator = composeTestRule.onNodeWithTag("testPI")
        progressIndicator.assertRangeInfoEquals(ProgressBarRangeInfo(0.2f, 0f..1f))
    }

    @Test
    fun testProgressText() {
        composeTestRule.setContent {
            FluentTheme {
                ProgressText(text = "Loading", progress = 0.5f)
            }
        }
        composeTestRule.onNodeWithText("Loading").assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("progressBar")
            .assertRangeInfoEquals(ProgressBarRangeInfo(0.5f, 0f..1f))
    }

    @Test
    fun testProgressTextIcon() {
        composeTestRule.setContent {
            FluentTheme {
                ProgressText(
                    text = "Loading", progress = 0.5f, leadingIconAccessory = FluentIcon(
                        Icons.Default.Close, contentDescription = "Cancel1", onClick = {})
                )
                ProgressText(
                    text = "Loading", progress = 0.5f, leadingIconAccessory = FluentIcon(
                        Icons.Default.Close, contentDescription = "Cancel2"
                    )
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Cancel1").assertExists().assertIsDisplayed()
            .assertHasClickAction()
        composeTestRule.onNodeWithContentDescription("Cancel2").assertExists().assertIsDisplayed()
            .assertHasNoClickAction()
    }
}