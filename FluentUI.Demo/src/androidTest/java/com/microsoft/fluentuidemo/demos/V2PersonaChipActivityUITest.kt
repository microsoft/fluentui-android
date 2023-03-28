package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

private const val MEDIUM_PERSONA_CHIP_TAG = "medium persona chip"
private const val SMALL_PERSONA_CHIP_TAG = "small persona chip"
private const val ANONYMOUS_PERSONA_CHIP_TAG = "ann persona chip"
private const val DISABLED_PERSONA_CHIP_TAG = "disabled persona chip"
private const val ANONYMOUS = "Anonymous"
private const val AVATAR_TAG = "Image"
private const val SWITCH_TAG = "switch"
private const val CLOSE_ICON_CONTENT_DESCRIPTION = "Close"
private const val PERSONA_CHIP_HEIGHT = 22
private const val AVATAR_ICON_SIZE = 16

class V2PersonaChipActivityUITest {

    private fun launchActivity() {
        ActivityScenario.launch<V2PersonaChipActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2PersonaChipActivity::class.java)
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
    fun testPersonaChipBounds(){
        composeTestRule.onNodeWithTag(MEDIUM_PERSONA_CHIP_TAG).assertHeightIsAtLeast(
            PERSONA_CHIP_HEIGHT.dp)
        composeTestRule.onNodeWithTag(SMALL_PERSONA_CHIP_TAG).assertHeightIsAtLeast(
            PERSONA_CHIP_HEIGHT.dp)
    }
    @Test
    fun testPersonaChipAvatarAndIconSize(){
        composeTestRule.onNode(hasTestTag(AVATAR_TAG).and(hasAnyAncestor(hasTestTag(
            MEDIUM_PERSONA_CHIP_TAG))), true).assertHeightIsEqualTo(AVATAR_ICON_SIZE.dp)
        composeTestRule.onNodeWithTag(SWITCH_TAG).performClick()
        composeTestRule.onNodeWithTag(MEDIUM_PERSONA_CHIP_TAG).performClick()
        composeTestRule.onNode(hasContentDescription(CLOSE_ICON_CONTENT_DESCRIPTION).and(hasAnyAncestor(hasTestTag(
            MEDIUM_PERSONA_CHIP_TAG))), true).assertHeightIsEqualTo(AVATAR_ICON_SIZE.dp)
    }
    @Test
    fun testMediumPersonaChip(){
        composeTestRule.onNode(hasTestTag(AVATAR_TAG).and(hasAnyAncestor(hasTestTag(
            MEDIUM_PERSONA_CHIP_TAG))), true).assertExists()
        composeTestRule.onNodeWithTag(SWITCH_TAG).performClick()
        composeTestRule.onNodeWithTag(MEDIUM_PERSONA_CHIP_TAG).performClick()
        composeTestRule.onNode(hasTestTag(AVATAR_TAG).and(hasAnyAncestor(hasTestTag(
            MEDIUM_PERSONA_CHIP_TAG))), true).assertDoesNotExist()
        composeTestRule.onNode(hasContentDescription(CLOSE_ICON_CONTENT_DESCRIPTION).and(hasAnyAncestor(hasTestTag(
            MEDIUM_PERSONA_CHIP_TAG))), true).assertExists()
    }
    @Test
    fun testSmallPersonaChip(){
        composeTestRule.onNode(hasTestTag(AVATAR_TAG).and(hasAnyAncestor(hasTestTag(
            SMALL_PERSONA_CHIP_TAG))), true).assertDoesNotExist()
        composeTestRule.onNodeWithTag(SWITCH_TAG).performClick()
        composeTestRule.onNodeWithTag(SMALL_PERSONA_CHIP_TAG).performClick()
        composeTestRule.onNode(hasTestTag(AVATAR_TAG).and(hasAnyAncestor(hasTestTag(
            SMALL_PERSONA_CHIP_TAG))), true).assertDoesNotExist()
        composeTestRule.onNode(hasContentDescription(CLOSE_ICON_CONTENT_DESCRIPTION).and(hasAnyAncestor(hasTestTag(
            MEDIUM_PERSONA_CHIP_TAG))), true).assertDoesNotExist()
    }
    @Test
    fun testAnonymousPersonaChip(){
        composeTestRule.onNode(hasParent(hasTestTag(ANONYMOUS_PERSONA_CHIP_TAG)), true).assertTextContains(
            ANONYMOUS)
    }
    @Test
    fun testDisabledPersonaChip(){
        composeTestRule.onNodeWithTag(DISABLED_PERSONA_CHIP_TAG).assertIsNotEnabled()
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}