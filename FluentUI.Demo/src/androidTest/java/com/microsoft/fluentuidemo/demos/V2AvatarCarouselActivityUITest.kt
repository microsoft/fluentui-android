package com.microsoft.fluentuidemo.demos

import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.intent.Intents
import androidx.test.platform.app.InstrumentationRegistry
import com.microsoft.fluentuidemo.DemoActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class V2AvatarCarouselActivityUITest {
    private fun launchActivity() {
        ActivityScenario.launch<V2AvatarCarouselActivity>(setUpIntentForActivity())
    }

    private fun setUpIntentForActivity(): Intent {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, V2AvatarCarouselActivity::class.java)
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
    fun testAvatarCarouselBounds(){
        composeTestRule.onNodeWithTag("LargeCarousel").onChildAt(0).assertWidthIsEqualTo(88.dp)
        composeTestRule.onNodeWithTag("SmallCarousel").onChildAt(0).assertWidthIsEqualTo(88.dp)
    }
    @Test
    fun testAvatarCarouselItemEnabledDisabled(){
        composeTestRule.onNode(hasParent(hasTestTag("LargeCarousel")).and(hasTestTag("item 1")), true).assertIsEnabled().assertHasClickAction()
        composeTestRule.onNode(hasParent(hasTestTag("LargeCarousel")).and(hasTestTag("item 2")), true).assertIsNotEnabled().assertHasNoClickAction()
    }
    @Test
    fun testAvatarSize(){
        composeTestRule.onNode(hasParent(hasTestTag("LargeCarousel")).and(hasTestTag("item 1")),true).onChildAt(0).assertWidthIsEqualTo(72.dp)
        composeTestRule.onNode(hasParent(hasTestTag("SmallCarousel")).and(hasTestTag("item 1")),true).onChildAt(0).assertWidthIsEqualTo(56.dp)
    }
    @Test
    fun testHorizontalScroll(){
        composeTestRule.onNodeWithTag("LargeCarousel").assert(hasScrollAction())
        composeTestRule.onNodeWithTag("SmallCarousel").assert(hasScrollAction())
    }
}