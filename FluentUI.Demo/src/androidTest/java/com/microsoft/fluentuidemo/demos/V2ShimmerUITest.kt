package com.microsoft.fluentuidemo.demos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.shimmer.Shimmer
import org.junit.Rule
import org.junit.Test

// Tags used for testing
const val SHIMMER = "shimmer"
const val SHIMMER_CIRCLE = "circleShimmer"

class V2ShimmerUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testShimmerBounds() {
        composeTestRule.setContent {
            FluentTheme {
                Shimmer(
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .testTag(SHIMMER)
                )
                Shimmer(
                    modifier = Modifier
                        .size(50.dp)
                        .testTag(SHIMMER_CIRCLE)
                )
            }
        }
        composeTestRule.onNodeWithTag(SHIMMER).assertHeightIsEqualTo(50.dp)
            .assertWidthIsEqualTo(50.dp)
        composeTestRule.onNodeWithTag(SHIMMER_CIRCLE).assertHeightIsEqualTo(50.dp)
            .assertWidthIsEqualTo(50.dp)
    }

    @Test
    fun testShimmerContent() {
        composeTestRule.setContent {
            FluentTheme {
                Shimmer(
                    modifier = Modifier
                        .testTag(SHIMMER),
                    content = {
                        Avatar(modifier = Modifier.testTag("Avatar").clickable {  }, person = Person("TestName"), size = AvatarSize.Size40)
                    }, cornerRadius = 50.dp
                )
            }
        }
        val shimmer = composeTestRule.onNodeWithTag(SHIMMER)
        shimmer.assertHeightIsEqualTo(40.dp).assertWidthIsEqualTo(40.dp)
        val content = composeTestRule.onNodeWithTag("Avatar")
        content.assertExists()
        content.assertIsDisplayed()
        content.assertHasClickAction()
    }
}