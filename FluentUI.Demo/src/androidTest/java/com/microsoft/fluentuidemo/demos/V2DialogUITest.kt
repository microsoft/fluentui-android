package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class V2DialogUITest {

    private fun launchActivity() {
        ActivityScenario.launch<V2DialogActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2DialogActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        return intent
    }

    @Before
    fun initialize() {
        Intents.init()
        launchActivity()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDialog() {
        composeTestRule.onNodeWithText("Show Dialog").performClick()
        val dialog = composeTestRule.onNodeWithTag("Dialog")
        dialog.assertExists()
        dialog.assertIsDisplayed()
        composeTestRule.onNodeWithText("Ok").assertExists().assertIsDisplayed()
    }

    @Test
    fun testBackPressDialog() {
        composeTestRule.onNodeWithText("Show Dialog").performClick()
        val dialog = composeTestRule.onNodeWithTag("Dialog")
        dialog.assertExists()
        dialog.assertIsDisplayed()
        Espresso.pressBack()
        dialog.assertExists()
    }

    @Test
    fun testBackPressDismissDialog() {
        composeTestRule.onNodeWithTag("back press").performClick()
        composeTestRule.onNodeWithText("Show Dialog").performClick()
        val dialog = composeTestRule.onNodeWithTag("Dialog")
        dialog.assertExists()
        dialog.assertIsDisplayed()
        Espresso.pressBack()
        dialog.assertDoesNotExist()
    }
}