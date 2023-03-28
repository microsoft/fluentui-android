package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertRangeInfoEquals
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.services.events.AnnotationInfo
import androidx.test.services.events.TestCaseInfo
import androidx.test.services.events.TestRunInfo
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerShape
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import com.microsoft.fluentui.tokenized.progress.LinearProgressIndicator
import com.microsoft.fluentui.tokenized.progress.Shimmer
import com.microsoft.fluentuidemo.DemoActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class V2ProgressNoActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLinearProgressIndicatorBounds(){
        composeTestRule.setContent {
            FluentTheme {
                LinearProgressIndicator(modifier = Modifier.testTag("testPI").width(200.dp))
            }
        }
        composeTestRule.onNodeWithTag("testPI").assertHeightIsEqualTo(2.dp).assertWidthIsEqualTo(200.dp)
    }
    @Test
    fun testCircularProgressIndicatorSize(){
        composeTestRule.setContent {
            FluentTheme {
                CircularProgressIndicator(size = CircularProgressIndicatorSize.XXSmall, modifier = Modifier.testTag("12dp"))
                CircularProgressIndicator(size = CircularProgressIndicatorSize.XSmall, modifier = Modifier.testTag("16dp"))
                CircularProgressIndicator(size = CircularProgressIndicatorSize.Medium, modifier = Modifier.testTag("24dp"))
                CircularProgressIndicator(size = CircularProgressIndicatorSize.Large, modifier = Modifier.testTag("32dp"))
                CircularProgressIndicator(size = CircularProgressIndicatorSize.XLarge, modifier = Modifier.testTag("40dp"))
            }
        }
        composeTestRule.onNodeWithTag("12dp").assertExists().assertHeightIsEqualTo(12.dp)
        composeTestRule.onNodeWithTag("16dp").assertExists().assertHeightIsEqualTo(16.dp)
        composeTestRule.onNodeWithTag("24dp").assertExists().assertHeightIsEqualTo(24.dp)
        composeTestRule.onNodeWithTag("32dp").assertExists().assertHeightIsEqualTo(32.dp)
        composeTestRule.onNodeWithTag("40dp").assertExists().assertHeightIsEqualTo(40.dp)
    }
    @Test
    fun testLinearDeterminateIndicatorProgress(){
        composeTestRule.setContent {
            FluentTheme {
                LinearProgressIndicator(progress = 0.2f, modifier = Modifier.testTag("testPI"))
            }
        }
        val progressIndicator = composeTestRule.onNodeWithTag("testPI")
        progressIndicator.assertRangeInfoEquals(ProgressBarRangeInfo(0.2f,0f..1f))
    }
    @Test
    fun testCircularDeterminateIndicatorProgress(){
        composeTestRule.setContent {
            FluentTheme {
                CircularProgressIndicator(progress = 0.2f, modifier = Modifier.testTag("testPI"))
            }
        }
        val progressIndicator = composeTestRule.onNodeWithTag("testPI")
        progressIndicator.assertRangeInfoEquals(ProgressBarRangeInfo(0.2f,0f..1f))
    }
    @Test
    fun testShimmerBounds(){
        composeTestRule.setContent {
            FluentTheme {
                Shimmer(ShimmerShape.Box, modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .testTag("shimmer"))
                Shimmer(ShimmerShape.Circle, modifier = Modifier.size(50.dp).testTag("circleShimmer"))
            }
        }
        composeTestRule.onNodeWithTag("shimmer").assertHeightIsEqualTo(50.dp).assertWidthIsEqualTo(50.dp)
        composeTestRule.onNodeWithTag("circleShimmer").assertHeightIsEqualTo(50.dp).assertWidthIsEqualTo(50.dp)
    }
}