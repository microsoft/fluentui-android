package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentuidemo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val SMALL_PERSONA_TAG = "small-persona"
private const val LARGE_PERSONA_TAG = "large-persona"
private const val XLARGE_PERSONA_TAG = "xlarge-persona"
private const val ONE_LINE_HEIGHT = 43
private const val TWO_LINE_HEIGHT = 63
private const val THREE_LINE_HEIGHT = 82
private const val SMALL_AVATAR_SIZE = 24
private const val LARGE_AVATAR_SIZE = 40
private const val XLARGE_AVATAR_SIZE = 56

class V2PersonaUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun initialize() {
        composeTestRule.setContent {
            val person1 = Person(
                "Allan", "Munger",
                image = R.drawable.avatar_allan_munger, isActive = true,
                status = AvatarStatus.Available, isOOO = false
            )
            Persona(
                person = person1,
                primaryText = person1.firstName,
                modifier = Modifier.testTag(SMALL_PERSONA_TAG)
            )
            Persona(
                person = person1,
                primaryText = person1.firstName,
                secondaryText = person1.lastName,
                modifier = Modifier.testTag(LARGE_PERSONA_TAG)
            )
            Persona(
                person = person1,
                primaryText = person1.firstName,
                secondaryText = person1.lastName,
                tertiaryText = "Microsoft",
                modifier = Modifier.testTag(XLARGE_PERSONA_TAG)
            )
        }
    }

    @Test
    fun testPersonaBounds() {
        composeTestRule.onNodeWithTag(SMALL_PERSONA_TAG).assertHeightIsAtLeast(ONE_LINE_HEIGHT.dp)
        composeTestRule.onNodeWithTag(LARGE_PERSONA_TAG).assertHeightIsAtLeast(TWO_LINE_HEIGHT.dp)
        composeTestRule.onNodeWithTag(XLARGE_PERSONA_TAG)
            .assertHeightIsAtLeast(THREE_LINE_HEIGHT.dp)
    }

    @Test
    fun testPersonaAvatarSize() {
        composeTestRule.onNodeWithTag(SMALL_PERSONA_TAG).onChildAt(0).assertHeightIsEqualTo(
            SMALL_AVATAR_SIZE.dp
        )
        composeTestRule.onNodeWithTag(LARGE_PERSONA_TAG).onChildAt(0).assertHeightIsEqualTo(
            LARGE_AVATAR_SIZE.dp
        )
        composeTestRule.onNodeWithTag(XLARGE_PERSONA_TAG).onChildAt(0).assertHeightIsEqualTo(
            XLARGE_AVATAR_SIZE.dp
        )
    }
}