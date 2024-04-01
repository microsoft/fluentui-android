package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.*
import androidx.compose.ui.unit.dp
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2PersonaListActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2PersonaListActivity::class.java)
    }

    @Test
    fun testPersonaListBounds() {
        val personaList = composeTestRule.onNodeWithTag(PERSONA_LIST)
        personaList.onChildAt(0).assertHeightIsAtLeast(48.dp)
    }

    @Test
    fun testPersonaList() {
        val personaList = composeTestRule.onNodeWithTag(PERSONA_LIST)
        personaList.assertExists()
        personaList.assertIsDisplayed()
        personaList.assert(hasScrollAction())
    }

}