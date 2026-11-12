package com.microsoft.fluentuidemo.demos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.click
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.text.AnnotatedString
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.tokenized.controls.Button
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ButtonAccessibilityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textButtonExposesStaticTextAndInvokesClick() {
        var clicks = 0
        composeTestRule.setContent {
            FluentTheme {
                Button(onClick = { clicks++ }, text = "Save a copy")
            }
        }

        composeTestRule.onNodeWithText("Save a copy")
            .assertButtonWithoutEditableText()
            .assertStaticText("Save a copy")
            .assertIsEnabled()
            .assertHasClickAction()
            .performClick()
        composeTestRule.runOnIdle { assertEquals(1, clicks) }
    }

    @Test
    fun disabledTextButtonExposesStaticTextWithoutInvokingClick() {
        var clicks = 0
        composeTestRule.setContent {
            FluentTheme {
                Button(onClick = { clicks++ }, text = "Save a copy", enabled = false)
            }
        }

        composeTestRule.onNodeWithText("Save a copy")
            .assertButtonWithoutEditableText()
            .assertStaticText("Save a copy")
            .assertIsNotEnabled()
            .performTouchInput { click() }
        composeTestRule.runOnIdle { assertEquals(0, clicks) }
    }

    @Test
    fun iconOnlyButtonPreservesDescriptionWithoutEditableText() {
        composeTestRule.setContent {
            FluentTheme {
                Button(
                    onClick = {},
                    icon = Icons.Filled.Email,
                    contentDescription = "Send email"
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("Send email")
            .assertButtonWithoutEditableText()
            .assertIsEnabled()
            .assertHasClickAction()
    }

    private fun SemanticsNodeInteraction.assertButtonWithoutEditableText() =
        assert(SemanticsMatcher.keyNotDefined(SemanticsProperties.EditableText))
            .assert(SemanticsMatcher.expectValue(SemanticsProperties.Role, Role.Button))

    private fun SemanticsNodeInteraction.assertStaticText(label: String) =
        assert(
            SemanticsMatcher.expectValue(
                SemanticsProperties.Text,
                listOf(AnnotatedString(label))
            )
        )
}
