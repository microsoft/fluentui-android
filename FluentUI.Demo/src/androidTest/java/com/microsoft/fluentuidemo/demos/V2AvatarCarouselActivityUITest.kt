package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.*
import androidx.compose.ui.unit.dp
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

private const val ITEM_1_TAG = "item 1"
private const val ITEM_2_TAG = "item 2"
private const val LARGE_AVATAR_SIZE = 72
private const val SMALL_AVATAR_SIZE = 56

class V2AvatarCarouselActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2AvatarCarouselActivity::class.java)
    }

    @Test
    fun testAvatarCarouselBounds() {
        composeTestRule.onNodeWithTag(AVATAR_CAROUSEL_LARGE_CAROUSEL).onChildAt(0)
            .assertWidthIsEqualTo(88.dp)
        composeTestRule.onNodeWithTag(AVATAR_CAROUSEL_SMALL_CAROUSEL).onChildAt(0)
            .assertWidthIsEqualTo(88.dp)
    }

    @Test
    fun testAvatarCarouselItemEnabledDisabled() {
        composeTestRule.onNode(
            hasParent(hasTestTag(AVATAR_CAROUSEL_LARGE_CAROUSEL)).and(hasTestTag(ITEM_1_TAG)),
            true
        ).assertIsEnabled()
        composeTestRule.onNode(
            hasParent(hasTestTag(AVATAR_CAROUSEL_LARGE_CAROUSEL)).and(hasTestTag(ITEM_2_TAG)),
            true
        ).assertIsNotEnabled()
        composeTestRule.onNode(
            hasParent(hasTestTag(AVATAR_CAROUSEL_SMALL_CAROUSEL)).and(hasTestTag(ITEM_1_TAG)),
            true
        ).assertIsEnabled()
        composeTestRule.onNode(
            hasParent(hasTestTag(AVATAR_CAROUSEL_SMALL_CAROUSEL)).and(hasTestTag(ITEM_2_TAG)),
            true
        ).assertIsNotEnabled()
    }

    @Test
    fun testAvatarSize() {
        composeTestRule.onNode(
            hasParent(hasTestTag(AVATAR_CAROUSEL_LARGE_CAROUSEL)).and(hasTestTag(ITEM_1_TAG)),
            true
        ).onChildAt(0).assertWidthIsEqualTo(
            LARGE_AVATAR_SIZE.dp
        )
        composeTestRule.onNode(
            hasParent(hasTestTag(AVATAR_CAROUSEL_SMALL_CAROUSEL)).and(hasTestTag(ITEM_1_TAG)),
            true
        ).onChildAt(0).assertWidthIsEqualTo(
            SMALL_AVATAR_SIZE.dp
        )
    }

    @Test
    fun testHorizontalScroll() {
        composeTestRule.onNodeWithTag(AVATAR_CAROUSEL_LARGE_CAROUSEL).assert(hasScrollAction())
        composeTestRule.onNodeWithTag(AVATAR_CAROUSEL_SMALL_CAROUSEL).assert(hasScrollAction())
    }

}