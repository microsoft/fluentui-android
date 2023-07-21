package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
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

class V2PersonaListActivityUITest {
    private fun launchActivity() {
        ActivityScenario.launch<V2PersonaListActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2PersonaListActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, UUID.randomUUID())
        intent.putExtra(V2DemoActivity.DEMO_TITLE, "Demo Test")
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
    fun testPersonaListBounds(){
        val personaList = composeTestRule.onNodeWithTag("list")
        personaList.onChildAt(0).assertHeightIsAtLeast(48.dp)
    }
    @Test
    fun testPersonaList(){
        composeTestRule.onRoot(true).printToLog("tree")
        val personaList = composeTestRule.onNodeWithTag("list")
        personaList.assertExists()
        personaList.assertIsDisplayed()
        personaList.assert(hasScrollAction())
        personaList.onChildAt(0).assertHasClickAction()
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}