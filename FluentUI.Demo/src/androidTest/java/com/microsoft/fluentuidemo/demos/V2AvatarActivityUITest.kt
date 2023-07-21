package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.*
import com.microsoft.fluentui.tokenized.persona.ANONYMOUS
import com.microsoft.fluentui.tokenized.persona.AVATAR_ICON
import com.microsoft.fluentui.tokenized.persona.AVATAR_IMAGE
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2AvatarActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2AvatarActivity::class.java)
    }

    @Test
    fun testToggleStates() {
        val oooButton = composeTestRule.onNodeWithText("Toggle OOO")
        val activityButton = composeTestRule.onNodeWithText("Toggle Activity")

        val amandaAvatar = composeTestRule.onAllNodesWithContentDescription(
            useUnmergedTree = true,
            label = "Amanda Brady",
            substring = true
        )[0]
        amandaAvatar.onChild().assert(hasTestTag(AVATAR_IMAGE))

        //Action
        activityButton.performClick()
        oooButton.performClick()

        //Check
        amandaAvatar.assertContentDescriptionContains("Out Of Office", substring = true)
        amandaAvatar.assertContentDescriptionContains("Inactive", substring = true)

        //Action
        activityButton.performClick()
        oooButton.performClick()

        //Check
        amandaAvatar.assertContentDescriptionContains("Active", substring = true)

        composeTestRule.onAllNodesWithContentDescription(ANONYMOUS)
            .assertAll(hasAnyChild(hasTestTag(AVATAR_ICON)))
    }

}