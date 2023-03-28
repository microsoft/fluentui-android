package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import androidx.test.espresso.accessibility.AccessibilityChecks
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheck
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentuidemo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class V2PersonaUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun initialize() {
        AccessibilityChecks.enable()
        composeTestRule.setContent {
            val person1 = Person(
                "Allan", "Munger",
                image = R.drawable.avatar_allan_munger, isActive = true,
                status = AvatarStatus.Available, isOOO = false
            )
            Persona(
                person = person1,
                primaryText = person1.firstName,
                modifier = Modifier.testTag("small-persona")
            )
            Persona(
                person = person1,
                primaryText = person1.firstName,
                secondaryText = person1.lastName,
                modifier = Modifier.testTag("large-persona")
            )
            Persona(
                person = person1,
                primaryText = person1.firstName,
                secondaryText = person1.lastName,
                tertiaryText = "Microsoft",
                modifier = Modifier.testTag("xlarge-persona")
            )
        }
    }

    @Test
    fun testPersonaBounds() {
        composeTestRule.onNodeWithTag("small-persona").assertHeightIsAtLeast(48.dp)
        composeTestRule.onNodeWithTag("large-persona").assertHeightIsAtLeast(68.dp)
        composeTestRule.onNodeWithTag("xlarge-persona").assertHeightIsAtLeast(88.dp)
    }

    @Test
    fun testPersonaAvatarSize() {
        composeTestRule.onNodeWithTag("small-persona").onChildAt(0).assertHeightIsEqualTo(24.dp)
        composeTestRule.onNodeWithTag("large-persona").onChildAt(0).assertHeightIsEqualTo(40.dp)
        composeTestRule.onNodeWithTag("xlarge-persona").onChildAt(0).assertHeightIsEqualTo(56.dp)
    }
}