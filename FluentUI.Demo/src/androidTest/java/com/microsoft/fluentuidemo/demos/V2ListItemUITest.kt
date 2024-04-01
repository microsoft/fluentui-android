package com.microsoft.fluentuidemo.demos

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.R
import org.junit.Rule
import org.junit.Test

private const val PRIMARY_TEXT = "text"
private const val SECONDARY_TEXT = "subText"
private const val TERTIARY_TEXT = "secondarySubText"
private const val ONE_LINE_ITEM_TAG = "oneLineItem"
private const val TWO_LINE_ITEM_TAG = "twoLineItem"
private const val THREE_LINE_ITEM_TAG = "threeLineItem"
private const val SECTION_DESCRIPTION_TAG = "SectionDescription"
private const val SECTION_DESCRIPTION_BOTTOM_TAG = "BottomSectionDescription"
private const val SECTION_HEADER_TAG = "SectionHeader"
private const val HEADER_TAG = "Header"
private const val BUTTON_TEXT = "button"
private const val LEFT_VIEW_TAG = "leftView"
private const val RIGHT_VIEW_TAG = "rightView"
private const val MORE_ACTION_TEXT = "More"
private const val ACTION_TEXT = "Action"
private const val CONTENT_TAG = "content"
private const val BOLD_TAG = "Bold"
private const val SUBTLE_TAG = "Subtle"
private const val CHEVRON_TAG = "Chevron"

class V2ListItemUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testOneLineListItem() {
        composeTestRule.setContent {
            ListItem.Item(text = PRIMARY_TEXT, modifier = Modifier.testTag(ONE_LINE_ITEM_TAG))
        }
        val listItem = composeTestRule.onNodeWithTag(ONE_LINE_ITEM_TAG)
        listItem.assertHeightIsAtLeast(43.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsEnabled()
    }

    @Test
    fun testOneLineDisabledListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = PRIMARY_TEXT,
                modifier = Modifier.testTag(ONE_LINE_ITEM_TAG),
                enabled = false
            )
        }
        val listItem = composeTestRule.onNodeWithTag(ONE_LINE_ITEM_TAG)
        listItem.assertHeightIsAtLeast(43.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHasNoClickAction()
    }

    @Test
    fun testTwoLineListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = PRIMARY_TEXT,
                subText = SECONDARY_TEXT,
                modifier = Modifier.testTag(TWO_LINE_ITEM_TAG)
            )
        }
        val listItem = composeTestRule.onNodeWithTag(TWO_LINE_ITEM_TAG)
        listItem.assertHeightIsAtLeast(63.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsEnabled()
    }

    @Test
    fun testTwoLineDisabledListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = PRIMARY_TEXT,
                subText = SECONDARY_TEXT,
                modifier = Modifier.testTag(TWO_LINE_ITEM_TAG),
                enabled = false
            )
        }
        val listItem = composeTestRule.onNodeWithTag(TWO_LINE_ITEM_TAG)
        listItem.assertHeightIsAtLeast(63.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHasNoClickAction()
    }

    @Test
    fun testThreeLineListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = PRIMARY_TEXT,
                subText = SECONDARY_TEXT,
                secondarySubText = TERTIARY_TEXT,
                modifier = Modifier.testTag(THREE_LINE_ITEM_TAG)
            )
        }
        val listItem = composeTestRule.onNodeWithTag(THREE_LINE_ITEM_TAG)
        listItem.assertHeightIsAtLeast(82.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsEnabled()
    }

    @Test
    fun testThreeLineDisabledListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = PRIMARY_TEXT,
                subText = SECONDARY_TEXT,
                secondarySubText = TERTIARY_TEXT,
                modifier = Modifier.testTag(THREE_LINE_ITEM_TAG),
                enabled = false
            )
        }
        val listItem = composeTestRule.onNodeWithTag(THREE_LINE_ITEM_TAG)
        listItem.assertHeightIsAtLeast(80.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHasNoClickAction()
    }

    @Test
    fun testLeadingAccessoryViewInListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = PRIMARY_TEXT,
                modifier = Modifier.testTag(ONE_LINE_ITEM_TAG),
                leadingAccessoryContent = {
                    Row(modifier = Modifier.testTag(LEFT_VIEW_TAG)) {
                        Button(onClick = { }, text = BUTTON_TEXT)
                    }
                })
        }
        composeTestRule.onNodeWithText(BUTTON_TEXT, true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(BUTTON_TEXT, true)
            .assert(hasAnyAncestor(hasTestTag(ONE_LINE_ITEM_TAG)))
        composeTestRule.onNodeWithTag(LEFT_VIEW_TAG, true).assertLeftPositionInRootIsEqualTo(16.dp)
    }

    @Test
    fun testTrailingAccessoryViewInListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = PRIMARY_TEXT,
                modifier = Modifier.testTag(ONE_LINE_ITEM_TAG),
                trailingAccessoryContent = {
                    Row(modifier = Modifier.testTag(RIGHT_VIEW_TAG)) {
                        Button(onClick = { }, text = BUTTON_TEXT)
                    }
                })
        }
        val rootWidth = composeTestRule.onNodeWithTag(ONE_LINE_ITEM_TAG).getBoundsInRoot().width
        composeTestRule.onNodeWithText(BUTTON_TEXT, true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText(BUTTON_TEXT, true)
            .assert(hasAnyAncestor(hasTestTag(ONE_LINE_ITEM_TAG)))
        composeTestRule.onNodeWithTag(RIGHT_VIEW_TAG, true).getBoundsInRoot().right.assertIsEqualTo(
            rootWidth - 16.dp,
            "Right"
        )
    }

    @Test
    fun testSectionDescription() {
        composeTestRule.setContent {
            ListItem.SectionDescription(
                description = SECTION_DESCRIPTION_TAG,
                modifier = Modifier.testTag(SECTION_DESCRIPTION_TAG),
                descriptionPlacement = TextPlacement.Top
            )
        }
        val listItem = composeTestRule.onNodeWithTag(SECTION_DESCRIPTION_TAG)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHeightIsAtLeast(64.dp)
        listItem.assertIsEnabled()
        composeTestRule.onNodeWithText(SECTION_DESCRIPTION_TAG, useUnmergedTree = true).assert(
            hasAnyAncestor(
                hasTestTag(SECTION_DESCRIPTION_TAG)
            )
        )
    }

    @Test
    fun testSectionDescriptionTextPlacement() {
        composeTestRule.setContent {
            ListItem.SectionDescription(
                description = "Top $SECTION_DESCRIPTION_TAG",
                modifier = Modifier.testTag(SECTION_DESCRIPTION_TAG),
                descriptionPlacement = TextPlacement.Top
            )
            ListItem.SectionDescription(
                description = SECTION_DESCRIPTION_BOTTOM_TAG,
                modifier = Modifier.testTag(SECTION_DESCRIPTION_BOTTOM_TAG),
                descriptionPlacement = TextPlacement.Bottom
            )
        }
        composeTestRule.onNodeWithText("Top $SECTION_DESCRIPTION_TAG", useUnmergedTree = true)
            .assertTopPositionInRootIsEqualTo(8.dp)
        val text =
            composeTestRule.onNodeWithText(SECTION_DESCRIPTION_BOTTOM_TAG, useUnmergedTree = true)
        val rootHeight =
            composeTestRule.onNodeWithTag(SECTION_DESCRIPTION_BOTTOM_TAG).getBoundsInRoot().height
        (rootHeight - text.getBoundsInRoot().bottom).assertIsEqualTo(8.dp, "Bottom")
    }

    @Test
    fun testSectionDescriptionActionButton() {
        composeTestRule.setContent {
            ListItem.SectionDescription(
                description = SECTION_DESCRIPTION_TAG,
                modifier = Modifier.testTag(SECTION_DESCRIPTION_TAG),
                actionText = MORE_ACTION_TEXT,
                onActionClick = {})
        }
        val actionButton =
            composeTestRule.onNodeWithText(
                "$SECTION_DESCRIPTION_TAG $MORE_ACTION_TEXT",
                useUnmergedTree = true
            )
                .onChild()
        actionButton.onChild().assert(hasText(MORE_ACTION_TEXT))
        actionButton.assertExists()
        actionButton.assertIsDisplayed()
        actionButton.assertHasClickAction()
        actionButton.assert(isFocusable())
    }

    @Test
    fun testSectionHeader() {
        composeTestRule.setContent {
            ListItem.SectionHeader(
                title = SECTION_HEADER_TAG, modifier = Modifier.testTag(
                    SECTION_HEADER_TAG
                )
            )
        }
        val listItem = composeTestRule.onNodeWithTag(SECTION_HEADER_TAG, true)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHeightIsEqualTo(48.dp)
        listItem.assertIsEnabled()
    }

    @Test
    fun sectionHeaderActionButton() {
        composeTestRule.setContent {
            ListItem.SectionHeader(
                title = SECTION_HEADER_TAG,
                accessoryTextTitle = ACTION_TEXT,
                accessoryTextOnClick = {})
        }
        composeTestRule.onRoot().printToLog("listTestTag")
        val actionButton = composeTestRule.onNodeWithContentDescription(ACTION_TEXT)
        actionButton.assertExists()
        actionButton.assertIsDisplayed()
        actionButton.assertHasClickAction()
        actionButton.assert(isFocusable())
    }

    @Test
    fun testSectionHeaderStyles() {
        composeTestRule.setContent {
            ListItem.SectionHeader(title = SECTION_HEADER_TAG,
                modifier = Modifier.testTag(BOLD_TAG),
                style = SectionHeaderStyle.Bold,
                content =
                {
                    Row(modifier = Modifier.testTag(CONTENT_TAG)) {
                        Button(onClick = {})
                    }
                }
            )
            ListItem.SectionHeader(
                title = SECTION_HEADER_TAG,
                modifier = Modifier.testTag(SUBTLE_TAG),
                style = SectionHeaderStyle.Subtle,
                enableChevron = false
            )
        }
        composeTestRule.onNodeWithTag(CONTENT_TAG, useUnmergedTree = true).assertExists()
            .assertIsDisplayed()
        composeTestRule.onNode(
            hasTestTag(CHEVRON_TAG).and(hasAnyAncestor(hasTestTag(BOLD_TAG))),
            true
        ).assertIsDisplayed()
        composeTestRule.onNode(
            hasTestTag(CHEVRON_TAG).and(hasAnyAncestor(hasTestTag(SUBTLE_TAG))),
            true
        ).assertDoesNotExist()
    }

    @Test
    fun testHeaders() {
        composeTestRule.setContent {
            ListItem.Header(title = HEADER_TAG, modifier = Modifier.testTag(HEADER_TAG))
        }
        val listItem = composeTestRule.onNodeWithTag(HEADER_TAG)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHeightIsEqualTo(48.dp)
        listItem.assertIsEnabled()
        listItem.assertHasNoClickAction()
    }

    @Test
    fun testUnclickableOneLineListAccessoryContentContent(){
        composeTestRule.setContent {
            ListItem.Item(text = PRIMARY_TEXT,
            leadingAccessoryContent = {
                Avatar(
                    person = Person(firstName = "", lastName = "", image = R.drawable.avatar_amanda_brady),
                    size = AvatarSize.Size24, enablePresence = false
                )
             },
            border = BorderType.Bottom,
            borderInset = BorderInset.XXLarge
            )
        }
        val listItem = composeTestRule.onNodeWithText(PRIMARY_TEXT)
        listItem.assertHasNoClickAction()
    }
}