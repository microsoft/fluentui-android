package com.microsoft.fluentuidemo.demos

import androidx.compose.foundation.layout.Row
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.height
import androidx.compose.ui.unit.width
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.listitem.ListItem
import org.junit.Rule
import org.junit.Test


class V2ListItemActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testOneLineListItem() {
        composeTestRule.setContent {
            ListItem.Item(text = "primarytext", modifier = Modifier.testTag("oneLineItem"))
        }
        composeTestRule.onRoot(true).printToLog("tree")
        val listItem = composeTestRule.onNodeWithTag("oneLineItem")
        listItem.assertHeightIsAtLeast(48.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsEnabled()
    }

    @Test
    fun testOneLineDisabledListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = "primarytext",
                modifier = Modifier.testTag("oneLineItem"),
                enabled = false
            )
        }
        composeTestRule.onRoot(true).printToLog("tree")
        val listItem = composeTestRule.onNodeWithTag("oneLineItem")
        listItem.assertHeightIsAtLeast(48.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsNotEnabled()
    }

    @Test
    fun testTneLineListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = "primaryText",
                subText = "subText",
                modifier = Modifier.testTag("twoLineItem")
            )
        }
        val listItem = composeTestRule.onNodeWithTag("twoLineItem")
        listItem.assertHeightIsAtLeast(68.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsEnabled()
    }

    @Test
    fun testTneLineDisabledListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = "primaryText",
                subText = "subText",
                modifier = Modifier.testTag("twoLineItem"),
                enabled = false
            )
        }
        val listItem = composeTestRule.onNodeWithTag("twoLineItem")
        listItem.assertHeightIsAtLeast(68.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsNotEnabled()
    }

    @Test
    fun testThreeLineListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = "primaryText",
                subText = "subText",
                secondarySubText = "secondarySubText",
                modifier = Modifier.testTag("threeLineItem")
            )
        }
        val listItem = composeTestRule.onNodeWithTag("threeLineItem")
        listItem.assertHeightIsAtLeast(88.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsEnabled()
    }

    @Test
    fun testThreeLineDisabledListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = "primaryText",
                subText = "subText",
                secondarySubText = "secondarySubText",
                modifier = Modifier.testTag("threeLineItem"),
                enabled = false
            )
        }
        val listItem = composeTestRule.onNodeWithTag("threeLineItem")
        listItem.assertHeightIsAtLeast(88.dp)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertIsNotEnabled()
    }

    @Test
    fun testLeadingAccessoryViewInListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = "primaryText",
                modifier = Modifier.testTag("oneLineItem"),
                leadingAccessoryView = {
                    Row(modifier = Modifier.testTag("leftView")) {
                        Button(onClick = { }, text = "button")
                    }
                })
        }
        composeTestRule.onNodeWithText("button", true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText("button", true)
            .assert(hasAnyAncestor(hasTestTag("oneLineItem")))
        composeTestRule.onNodeWithTag("leftView", true).assertLeftPositionInRootIsEqualTo(16.dp)
    }

    @Test
    fun testTrailingAccessoryViewInListItem() {
        composeTestRule.setContent {
            ListItem.Item(
                text = "primaryText",
                modifier = Modifier.testTag("oneLineItem"),
                trailingAccessoryView = {
                    Row(modifier = Modifier.testTag("rightView")) {
                        Button(onClick = { }, text = "button")
                    }
                })
        }
        val rootWidth = composeTestRule.onNodeWithTag("oneLineItem").getBoundsInRoot().width
        composeTestRule.onNodeWithText("button", true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithText("button", true)
            .assert(hasAnyAncestor(hasTestTag("oneLineItem")))
        composeTestRule.onNodeWithTag("rightView", true).getBoundsInRoot().right.assertIsEqualTo(
            rootWidth - 16.dp,
            "Right"
        )
    }

    @Test
    fun testSectionDescription() {
        composeTestRule.setContent {
            ListItem.SectionDescription(
                description = "Top Section Description",
                modifier = Modifier.testTag("SD"),
                descriptionPlacement = TextPlacement.Top
            )
        }
        val listItem = composeTestRule.onNodeWithTag("SD")
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHeightIsAtLeast(64.dp)
        listItem.assertIsEnabled()
        composeTestRule.onNodeWithText("Top Section Description", useUnmergedTree = true).assert(
            hasAnyAncestor(
                hasTestTag("SD")
            )
        )
    }

    @Test
    fun testSectionDescriptionTextPlacement() {
        composeTestRule.setContent {
            ListItem.SectionDescription(
                description = "Top Section Description",
                modifier = Modifier.testTag("SD"),
                descriptionPlacement = TextPlacement.Top
            )
            ListItem.SectionDescription(
                description = "Bottom Section Description",
                modifier = Modifier.testTag("BSD"),
                descriptionPlacement = TextPlacement.Bottom
            )
        }
        composeTestRule.onNodeWithText("Top Section Description", useUnmergedTree = true)
            .assertTopPositionInRootIsEqualTo(8.dp)
        val text =
            composeTestRule.onNodeWithText("Bottom Section Description", useUnmergedTree = true)
        val rootHeight = composeTestRule.onNodeWithTag("BSD").getBoundsInRoot().height
        (rootHeight - text.getBoundsInRoot().bottom).assertIsEqualTo(8.dp, "Bottom")
    }

    @Test
    fun testSectionDescriptionActionButton() {
        composeTestRule.setContent {
            ListItem.SectionDescription(
                description = "Section Description",
                modifier = Modifier.testTag("SD"),
                actionText = "More",
                onActionClick = {})
        }
        composeTestRule.onRoot(true).printToLog("tree")
        val actionButton =
            composeTestRule.onNodeWithText("Section Description More", useUnmergedTree = true)
                .onChild()
        actionButton.onChild().assert(hasText("More"))
        actionButton.assertExists()
        actionButton.assertIsDisplayed()
        actionButton.assertHasClickAction()
        actionButton.assert(isFocusable())
    }

    @Test
    fun testSectionHeader() {
        composeTestRule.setContent {
            ListItem.SectionHeader(title = "Header", modifier = Modifier.testTag("SH"))
        }
        val listItem = composeTestRule.onNodeWithTag("SH", true)
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHeightIsEqualTo(48.dp)
        listItem.assertIsEnabled()
    }

    @Test
    fun sectionHeaderActionButton() {
        composeTestRule.setContent {
            ListItem.SectionHeader(
                title = "Header",
                accessoryTextTitle = "Action",
                accessoryTextOnClick = {})
        }
        val actionButton = composeTestRule.onNodeWithText("Action")
        actionButton.assertExists()
        actionButton.assertIsDisplayed()
        actionButton.assertHasClickAction()
        actionButton.assert(isFocusable())
    }

    @Test
    fun testSectionHeaderStyles() {
        composeTestRule.setContent {
            ListItem.SectionHeader(title = "SectionHeader",
                modifier = Modifier.testTag("Bold"),
                style = SectionHeaderStyle.Bold,
                content =
                {
                    Row(modifier = Modifier.testTag("content")) {
                        Button(onClick = {})
                    }
                }
            )
            ListItem.SectionHeader(
                title = "SectionHeader",
                modifier = Modifier.testTag("Subtle"),
                style = SectionHeaderStyle.Subtle,
                enableChevron = false
            )
        }
        composeTestRule.onNodeWithTag("content", useUnmergedTree = true).assertExists()
            .assertIsDisplayed()
        composeTestRule.onNode(
            hasContentDescription("Chevron").and(hasAnyAncestor(hasTestTag("Bold"))),
            true
        ).assertIsDisplayed()
        composeTestRule.onNode(
            hasContentDescription("Chevron").and(hasAnyAncestor(hasTestTag("Subtle"))),
            true
        ).assertDoesNotExist()
    }

    @Test
    fun testHeaders() {
        composeTestRule.setContent {
            ListItem.Header(title = "Header", modifier = Modifier.testTag("Header"))
        }
        val listItem = composeTestRule.onNodeWithTag("Header")
        listItem.assertExists()
        listItem.assertIsDisplayed()
        listItem.assertHeightIsEqualTo(48.dp)
        listItem.assertIsEnabled()
        composeTestRule.onRoot(true).printToLog("tree")
        listItem.assertHasNoClickAction()
    }
}