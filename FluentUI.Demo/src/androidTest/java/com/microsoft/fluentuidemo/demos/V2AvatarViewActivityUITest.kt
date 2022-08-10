package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class V2AvatarViewActivityUITest {

    private fun launchActivity() {
        ActivityScenario.launch<V2AvatarViewActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2AvatarViewActivity::class.java)
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
        val allanAvatar = composeTestRule.onAllNodesWithContentDescription("Allan Munger", substring = true)[0]

        //Action
        activityButton.performClick()
        oooButton.performClick()

        //Check
        allanAvatar.assertContentDescriptionContains("Out Of Office", substring = true)
        allanAvatar.assertContentDescriptionContains("Inactive", substring = true)

        //Action
        activityButton.performClick()
        oooButton.performClick()

        //Check
        allanAvatar.assertContentDescriptionContains("Active", substring = true)
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}