package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.V2DemoActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class V2AvatarGroupActivityUITest {

    private fun launchActivity() {
        ActivityScenario.launch<V2AvatarGroupActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2AvatarGroupActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        intent.putExtra(V2DemoActivity.DEMO_TITLE, "Demo Test")
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
    fun testIncreaseDecreaseMaxAvatar() {
        val plusButton = composeTestRule.onNodeWithText("+")
        val minusButton = composeTestRule.onNodeWithText("-")
        val layout = composeTestRule.onAllNodesWithContentDescription("Group Name", substring = true, useUnmergedTree = true)[0]

        layout.onChildren().assertCountEquals(2)
        plusButton.performClick()
        layout.onChildren().assertCountEquals(3)
        plusButton.performClick()
        layout.onChildren().assertCountEquals(4)
        plusButton.performClick()
        layout.onChildren().assertCountEquals(5)
        minusButton.performClick()
        layout.onChildren().assertCountEquals(4)
        minusButton.performClick()
        layout.onChildren().assertCountEquals(3)
        minusButton.performClick()
        layout.onChildren().assertCountEquals(2)
    }

    @Test
    fun testOverflowAvatar() {
        val totalPeople = 7
        var maxAvatar = 1

        val plusButton = composeTestRule.onNodeWithText("+")
        val minusButton = composeTestRule.onNodeWithText("-")
        val layout = composeTestRule.onAllNodesWithContentDescription("Group Name", substring = true, useUnmergedTree = true)[0]

        layout.onChildren().onLast().printToLog("Test")

        if (maxAvatar < totalPeople)
            layout.onChildren().onLast().assertContentDescriptionContains((totalPeople - maxAvatar).toString(), substring = true)

        for (i in 1..(2..(totalPeople - 2)).random()) {
            plusButton.performClick()
            maxAvatar++
        }

        if (maxAvatar < totalPeople)
            layout.onChildren().onLast().assertContentDescriptionContains((totalPeople - maxAvatar).toString(), substring = true)

        for (i in 1..(2..maxAvatar).random()) {
            minusButton.performClick()
            maxAvatar--
        }

        if (maxAvatar < totalPeople)
            layout.onChildren().onLast().assertContentDescriptionContains((totalPeople - maxAvatar).toString(), substring = true)

    }

    @After
    fun tearDown() {
        Intents.release()
    }
}