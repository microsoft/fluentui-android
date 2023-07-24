package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.*
import com.microsoft.fluentui.tokenized.controls.*
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2TextFieldActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2TextFieldActivity::class.java)
    }

    private val modifiableParametersButton =
        composeTestRule.onNodeWithTag(TEXT_FIELD_MODIFIABLE_PARAMETER_SECTION)

    @Test
    fun testInput() {
        val textField = composeTestRule.onNodeWithTag(TEXT_FIELD)
        textField.assertExists("Text field did not render properly")
        textField.performTextInput("Test")
        textField.assertTextEquals("Test")
    }

    @Test
    fun testIcon() {
        val control = composeTestRule.onNodeWithTag(TEXT_FIELD_ICON_PARAM)
        val component = composeTestRule.onNodeWithTag(TEXT_FIELD_ICON, true)
        modifiableParametersButton.performClick()
        assertExistsAfterToggleOnly(control, component, "Icon did not render properly")
    }

    @Test
    fun testLabel() {
        val control = composeTestRule.onNodeWithTag(TEXT_FIELD_LABEL_PARAM)
        val component = composeTestRule.onNodeWithTag(TEXT_FIELD_LABEL, true)
        modifiableParametersButton.performClick()
        assertExistsAfterToggleOnly(control, component, "Label did not render properly")
    }

    @Test
    fun testHintText() {
        val control = composeTestRule.onNodeWithTag(TEXT_FIELD_HINT_TEXT_PARAM)
        val component = composeTestRule.onNodeWithTag(TEXT_FIELD_HINT_TEXT, true)
        modifiableParametersButton.performClick()
        assertExistsAfterToggleOnly(control, component, "Hint text did not render properly")
    }

    @Test
    fun testAssistiveText() {
        val control = composeTestRule.onNodeWithTag(TEXT_FIELD_ASSISTIVE_TEXT_PARAM)
        val component = composeTestRule.onNodeWithTag(TEXT_FIELD_ASSISTIVE_TEXT, true)
        modifiableParametersButton.performClick()
        assertExistsAfterToggleOnly(control, component, "Assistive text did not render properly")
    }

    @Test
    fun testSecondaryText() {
        val control = composeTestRule.onNodeWithTag(TEXT_FIELD_SECONDARY_TEXT_PARAM)
        val component = composeTestRule.onNodeWithTag(TEXT_FIELD_SECONDARY_TEXT, true)
        modifiableParametersButton.performClick()
        modifiableParametersButton.performTouchInput { swipeUp(durationMillis = 1000) }
        assertExistsAfterToggleOnly(control, component, "Secondary text did not render properly")
    }

    @Test
    fun testErrorState() {
        val control = composeTestRule.onNodeWithTag(TEXT_FIELD_ERROR_PARAM)
        val component = composeTestRule.onNodeWithTag(TEXT_FIELD, true)
        modifiableParametersButton.performClick()
        modifiableParametersButton.performTouchInput { swipeUp(durationMillis = 1000) }
        component.assertExists("Text field did not render properly")
        toggleControlToValue(control, true)
        component.assertExists("Error state did not render properly")
    }

    @Test
    fun testPasswordMode() {
        val control = composeTestRule.onNodeWithTag(TEXT_FIELD_PASSWORD_MODE_PARAM)
        val component = composeTestRule.onNodeWithTag(TEXT_FIELD, true)
        modifiableParametersButton.performClick()
        modifiableParametersButton.performTouchInput { swipeUp(durationMillis = 1000) }
        toggleControlToValue(control, true)
        component.assertExists("Text field did not render properly")
        component.performTextInput("Test")
        component.assertExists("Password mode did not render properly")
    }
}