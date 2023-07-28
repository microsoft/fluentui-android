package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.assertHeightIsEqualTo
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.persona.ANONYMOUS
import com.microsoft.fluentui.tokenized.persona.AVATAR_IMAGE
import com.microsoft.fluentuidemo.BaseTest
import com.microsoft.fluentuidemo.R
import org.junit.Before
import org.junit.Test

private const val AVATAR_ICON_SIZE = 16

class V2PersonaChipActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2PersonaChipActivity::class.java)
    }

    @Test
    fun testPersonaChipAvatarAndIconSize() {
        composeTestRule.onNode(
            hasTestTag(AVATAR_IMAGE).and(
                hasAnyAncestor(
                    hasTestTag(
                        PERSONA_CHIP_MEDIUM_CHIP
                    )
                )
            ), true
        ).assertHeightIsEqualTo(AVATAR_ICON_SIZE.dp)
        toggleControlToValue(composeTestRule.onNodeWithTag(PERSONA_CHIP_SWITCH), true)
        composeTestRule.onNodeWithTag(PERSONA_CHIP_MEDIUM_CHIP).performClick()
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.fluentui_close)).and(
                hasAnyAncestor(
                    hasTestTag(
                        PERSONA_CHIP_MEDIUM_CHIP
                    )
                )
            ), true
        ).assertHeightIsEqualTo(AVATAR_ICON_SIZE.dp)
    }

    @Test
    fun testMediumPersonaChip() {
        composeTestRule.onNode(
            hasTestTag(AVATAR_IMAGE).and(
                hasAnyAncestor(
                    hasTestTag(
                        PERSONA_CHIP_MEDIUM_CHIP
                    )
                )
            ), true
        ).assertExists()
        toggleControlToValue(composeTestRule.onNodeWithTag(PERSONA_CHIP_SWITCH), true)
        composeTestRule.onNodeWithTag(PERSONA_CHIP_MEDIUM_CHIP).performClick()
        composeTestRule.onNode(
            hasTestTag(AVATAR_IMAGE).and(
                hasAnyAncestor(
                    hasTestTag(
                        PERSONA_CHIP_MEDIUM_CHIP
                    )
                )
            ), true
        ).assertDoesNotExist()
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.fluentui_close)).and(
                hasAnyAncestor(
                    hasTestTag(
                        PERSONA_CHIP_MEDIUM_CHIP
                    )
                )
            ), true
        ).assertExists()
    }

    @Test
    fun testSmallPersonaChip() {
        composeTestRule.onNode(
            hasTestTag(AVATAR_IMAGE).and(
                hasAnyAncestor(
                    hasTestTag(
                        PERSONA_CHIP_SMALL_CHIP
                    )
                )
            ), true
        ).assertDoesNotExist()
        toggleControlToValue(composeTestRule.onNodeWithTag(PERSONA_CHIP_SWITCH), true)
        composeTestRule.onNodeWithTag(PERSONA_CHIP_SMALL_CHIP).performClick()
        composeTestRule.onNode(
            hasTestTag(AVATAR_IMAGE).and(
                hasAnyAncestor(
                    hasTestTag(
                        PERSONA_CHIP_SMALL_CHIP
                    )
                )
            ), true
        ).assertDoesNotExist()
        composeTestRule.onNode(
            hasContentDescription(getString(R.string.fluentui_close)).and(
                hasAnyAncestor(
                    hasTestTag(
                        PERSONA_CHIP_MEDIUM_CHIP
                    )
                )
            ), true
        ).assertDoesNotExist()
    }

    @Test
    fun testAnonymousPersonaChip() {
        composeTestRule.onNode(hasParent(hasTestTag(PERSONA_CHIP_ANONYMOUS)), true)
            .assertTextContains(
                ANONYMOUS
            )
    }

    @Test
    fun testDisabledPersonaChip() {
        composeTestRule.onNodeWithTag(PERSONA_CHIP_DISABLED).assertIsNotEnabled()
    }

}