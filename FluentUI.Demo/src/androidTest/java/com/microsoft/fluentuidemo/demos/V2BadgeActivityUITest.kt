package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.onNodeWithTag
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2BadgeActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2BadgeActivity::class.java)
    }

    @Test
    fun testRender() {
        val dotBadge = composeTestRule.onNodeWithTag(BADGE_DOT)
        dotBadge.assertExists("Badge did not load")

        val characterBadge = composeTestRule.onNodeWithTag(BADGE_CHARACTER)
        characterBadge.assertExists("Badge did not load")

        val listBadge = composeTestRule.onNodeWithTag(BADGE_LIST)
        listBadge.assertExists("Badge did not load")
    }

}