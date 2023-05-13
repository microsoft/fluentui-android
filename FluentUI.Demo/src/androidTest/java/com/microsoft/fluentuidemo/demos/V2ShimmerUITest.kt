package com.microsoft.fluentuidemo.demos

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerShape
import com.microsoft.fluentui.tokenized.shimmer.Shimmer
import org.junit.Rule
import org.junit.Test

class V2ShimmerUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testShimmerBounds(){
        composeTestRule.setContent {
            FluentTheme {
                Shimmer(shape = ShimmerShape.Box, modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .testTag("shimmer"))
                Shimmer(shape= ShimmerShape.Circle, modifier = Modifier.size(50.dp).testTag("circleShimmer"))
            }
        }
        composeTestRule.onNodeWithTag("shimmer").assertHeightIsEqualTo(50.dp).assertWidthIsEqualTo(50.dp)
        composeTestRule.onNodeWithTag("circleShimmer").assertHeightIsEqualTo(50.dp).assertWidthIsEqualTo(50.dp)
    }
}