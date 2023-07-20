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

class V2BadgeActivityUITest {
    private fun launchActivity() {
        ActivityScenario.launch<V2BadgeActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2BadgeActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        return intent
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun initialize() {
        Intents.init()
        launchActivity()
    }

    @Test
    fun testRender() {
        val dotBadge = composeTestRule.onNodeWithTag("Dot Badge")
        dotBadge.assertExists("Badge did not load")

        val characterBadge = composeTestRule.onNodeWithTag("Dot Badge")
        characterBadge.assertExists("Badge did not load")

        val listBadge = composeTestRule.onNodeWithTag("Dot Badge")
        listBadge.assertExists("Badge did not load")
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}