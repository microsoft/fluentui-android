package com.microsoft.fluentuidemo.demos

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.controls.AnnouncementCard
import com.microsoft.fluentui.tokenized.controls.BasicCard
import com.microsoft.fluentui.tokenized.controls.FileCard
import com.microsoft.fluentuidemo.R
import org.junit.Rule
import org.junit.Test

class V2CardUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasicCard() {
        composeTestRule.setContent {
            FluentTheme {
                BasicCard(modifier = Modifier.testTag("basicCard")) {
                    Text(text = "Text")
                }
            }
        }
        val basicCard = composeTestRule.onNodeWithTag("basicCard")
        basicCard.assertExists()
        basicCard.assertIsDisplayed()
        basicCard.assertHasNoClickAction()
    }

    @Test
    fun testFileCardWithoutPreview() {
        composeTestRule.setContent {
            FluentTheme {
                FileCard(
                    modifier = Modifier.testTag("fileCard"),
                    text = "Text",
                    subText = "SubText",
                    textIcon = Icons.Outlined.Call,
                    actionOverflowIcon = FluentIcon(
                        Icons.Outlined.MoreVert,
                        contentDescription = "Options"
                    )
                )
            }
        }
        val fileCard = composeTestRule.onNodeWithTag("fileCard")
        fileCard.assertExists()
        fileCard.assertIsDisplayed()
        fileCard.assertHasNoClickAction()
        composeTestRule.onNodeWithContentDescription("Options").assertDoesNotExist()
    }

    @Test
    fun testFileCardWithPreview() {
        composeTestRule.setContent {
            FluentTheme {
                FileCard(
                    modifier = Modifier.testTag("fileCard"),
                    onClick = {},
                    previewImageDrawable = R.drawable.avatar_allan_munger,
                    text = "Text",
                    subText = "SubText",
                    textIcon = Icons.Outlined.Call,
                    actionOverflowIcon = FluentIcon(
                        Icons.Outlined.MoreVert,
                        contentDescription = "Options"
                    )
                )
            }
        }
        val fileCard = composeTestRule.onNodeWithTag("fileCard")
        fileCard.assertExists()
        fileCard.assertIsDisplayed()
        val card = composeTestRule.onNodeWithTag("Card")
        card.assertHasClickAction()
        val optionButton = composeTestRule.onNodeWithContentDescription("Options")
        optionButton.assertExists()
        optionButton.assertIsDisplayed()
        optionButton.assertHasClickAction()
    }

    @Test
    fun testAnnouncementCard() {
        composeTestRule.setContent {
            FluentTheme {
                AnnouncementCard(
                    modifier = Modifier.testTag("announcementCard"),
                    previewImageDrawable = R.drawable.avatar_allan_munger,
                    title = "Title",
                    description = "text",
                    buttonText = "Button",
                    buttonOnClick = {})
            }
        }
        val announcementCard = composeTestRule.onNodeWithTag("announcementCard")
        announcementCard.assertExists()
        announcementCard.assertIsDisplayed()
        announcementCard.assertHasNoClickAction()
        val optionButton = composeTestRule.onNodeWithText("Button")
        optionButton.assertExists()
        optionButton.assertIsDisplayed()
        optionButton.assertHasClickAction()
    }
}