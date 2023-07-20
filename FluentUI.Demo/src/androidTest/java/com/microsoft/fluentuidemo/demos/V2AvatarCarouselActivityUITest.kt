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

private const val LARGE_CAROUSEL_TAG = "LargeCarousel"
private const val SMALL_CAROUSEL_TAG = "SmallCarousel"
private const val ITEM_1_TAG = "item 1"
private const val ITEM_2_TAG = "item 2"
private const val LARGE_AVATAR_SIZE = 72
private const val SMALL_AVATAR_SIZE = 56

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
    fun testAvatarCarouselBounds() {
        composeTestRule.onNodeWithTag(LARGE_CAROUSEL_TAG).onChildAt(0).assertWidthIsEqualTo(88.dp)
        composeTestRule.onNodeWithTag(SMALL_CAROUSEL_TAG).onChildAt(0).assertWidthIsEqualTo(88.dp)
    }

    @Test
    fun testAvatarCarouselItemEnabledDisabled() {
        composeTestRule.onNode(
            hasParent(hasTestTag(LARGE_CAROUSEL_TAG)).and(hasTestTag(ITEM_1_TAG)),
            true
        ).assertIsEnabled()
        composeTestRule.onNode(
            hasParent(hasTestTag(LARGE_CAROUSEL_TAG)).and(hasTestTag(ITEM_2_TAG)),
            true
        ).assertIsNotEnabled()
        composeTestRule.onNode(
            hasParent(hasTestTag(SMALL_CAROUSEL_TAG)).and(hasTestTag(ITEM_1_TAG)),
            true
        ).assertIsEnabled()
        composeTestRule.onNode(
            hasParent(hasTestTag(SMALL_CAROUSEL_TAG)).and(hasTestTag(ITEM_2_TAG)),
            true
        ).assertIsNotEnabled()
    }

    @Test
    fun testAvatarSize() {
        composeTestRule.onNode(
            hasParent(hasTestTag(LARGE_CAROUSEL_TAG)).and(hasTestTag(ITEM_1_TAG)),
            true
        ).onChildAt(0).assertWidthIsEqualTo(
            LARGE_AVATAR_SIZE.dp
        )
        composeTestRule.onNode(
            hasParent(hasTestTag(SMALL_CAROUSEL_TAG)).and(hasTestTag(ITEM_1_TAG)),
            true
        ).onChildAt(0).assertWidthIsEqualTo(
            SMALL_AVATAR_SIZE.dp
        )
    }

    @Test
    fun testHorizontalScroll() {
        composeTestRule.onNodeWithTag(LARGE_CAROUSEL_TAG).assert(hasScrollAction())
        composeTestRule.onNodeWithTag(SMALL_CAROUSEL_TAG).assert(hasScrollAction())
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}