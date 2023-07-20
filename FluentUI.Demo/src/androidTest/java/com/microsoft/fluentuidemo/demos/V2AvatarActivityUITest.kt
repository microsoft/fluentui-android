package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

const val IMAGE_TEST_TAG = "Image"
const val ICON_TEST_TAG = "Icon"

class V2AvatarActivityUITest {

    private fun launchActivity() {
        ActivityScenario.launch<V2AvatarActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2AvatarActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        return intent
    }

    @Before
    fun initialize() {
        Intents.init()
        launchActivity()
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testToggleStates() {
        val oooButton = composeTestRule.onNodeWithText("Toggle OOO")
        val activityButton = composeTestRule.onNodeWithText("Toggle Activity")

        val amandaAvatar = composeTestRule.onAllNodesWithContentDescription(
            useUnmergedTree = true,
            label = "Amanda Brady",
            substring = true
        )[0]
        amandaAvatar.onChild().assert(hasTestTag(IMAGE_TEST_TAG))

        //Action
        activityButton.performClick()
        oooButton.performClick()

        //Check
        amandaAvatar.assertContentDescriptionContains("Out Of Office", substring = true)
        amandaAvatar.assertContentDescriptionContains("Inactive", substring = true)

        //Action
        activityButton.performClick()
        oooButton.performClick()

        //Check
        amandaAvatar.assertContentDescriptionContains("Active", substring = true)

        composeTestRule.onAllNodesWithContentDescription("Anonymous")
            .assertAll(hasAnyChild(hasTestTag(ICON_TEST_TAG)))
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}