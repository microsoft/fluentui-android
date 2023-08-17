package com.microsoft.fluentuidemo

import android.content.Intent
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Rule
import java.util.*

open class BaseTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var context: android.content.Context

    @Before
    fun initializeIntent() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    fun launchActivity(activity: Class<*>) {
        ActivityScenario.launch<DemoActivity>(setUpIntentForActivity(activity))
    }

    private fun setUpIntentForActivity(activity: Class<*>): Intent {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(context, activity)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        intent.putExtra(V2DemoActivity.DEMO_TITLE, "Demo Test")
        return intent
    }

    // This function is used to RETURN whether a control is enabled or not.
    // As opposed to assertEnabled that just tests for the value.
    fun isEnabled(control: SemanticsNodeInteraction): Boolean {
        control.assertExists()
        for ((key, _) in control.fetchSemanticsNode().config) {
            if (key.name == "Disabled") return false
        }
        return true
    }

    // This function is used to toggle the state of a control to a given value.
    // The control can be a switch, checkbox, or radio button.
    // Also verifies whether the state is achieved.
    // Note - Not all components might be able to reach the state, eg: given radio button cannot be unselected by itself.
    fun toggleControlToValue(control: SemanticsNodeInteraction, state: Boolean) {
        control.assertExists()
        for ((key, value) in control.fetchSemanticsNode().config) {
            if (key.name == "ToggleableState" && value.toString() == if (!state) "On" else "Off") {
                control.performClick()
                assert(control.fetchSemanticsNode().config[key].toString() == if (state) "On" else "Off")
                break
            } else if (key.name == "Selected" && value.toString() == if (!state) "true" else "false") {
                control.performClick()
                assert(control.fetchSemanticsNode().config[key].toString() == if (state) "true" else "false")
                break
            }
        }
    }

    // This function is responsible for verifying that a component is displayed
    // only when a param controller is toggled 'on', and not otherwise.
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

    // This function is responsible for verifying that a component is displayed
    // only when a param controller is toggled 'off', and not otherwise.
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

    fun getString(resId: Int) = context.resources.getString(resId)
}