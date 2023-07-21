package com.microsoft.fluentuidemo

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.performClick


// TODO: Replace this everywhere else
fun toggleControlToValue(control: SemanticsNodeInteraction, state: Boolean) {
    control.assertExists()
    for ((key, value) in control.fetchSemanticsNode().config) {
        if (key.name == "ToggleableState" && value.toString() == if (!state) "On" else "Off") {
            control.performClick()
            break
        }
    }
}

fun assertExistsAfterToggleOnly(
    control: SemanticsNodeInteraction,
    component: SemanticsNodeInteraction,
    errorMessage: String
) {
    // Make sure control is turned off initially
    toggleControlToValue(control, false)
    // Verify component is not displayed
    component.assertDoesNotExist()
    // Turn control on
    toggleControlToValue(control, true)
    // Verify component is displayed
    component.assertExists(errorMessage)
}

fun assertExistsBeforeToggleOnly(
    control: SemanticsNodeInteraction,
    component: SemanticsNodeInteraction,
    errorMessage: String
) {
    // Make sure control is turned on initially
    toggleControlToValue(control, true)
    // Verify component is not displayed
    component.assertDoesNotExist()
    // Turn control off
    toggleControlToValue(control, false)
    // Verify component is displayed
    component.assertExists(errorMessage)
}