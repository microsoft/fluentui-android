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

    @Before
    fun initializeIntent() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    // TODO: Replace this everywhere else
    fun launchActivity(activity: Class<*>) {
        ActivityScenario.launch<DemoActivity>(setUpIntentForActivity(activity))
    }

    private fun setUpIntentForActivity(activity: Class<*>): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, activity)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        return intent
    }

    // TODO: Replace this everywhere else
    fun toggleControlToValue(control: SemanticsNodeInteraction, state: Boolean) {
        control.assertExists()
        for ((key, value) in control.fetchSemanticsNode().config) {
            if (key.name == "ToggleableState" && value.toString() == if (!state) "On" else "Off") {
                control.performClick()
                assert(control.fetchSemanticsNode().config[key].toString() == if (state) "On" else "Off")
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
}