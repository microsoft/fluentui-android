package com.microsoft.fluentuidemo.demos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.notification.Banner
import org.junit.Rule
import org.junit.Test

class V2BannerUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHappyBanner() {
        composeTestRule.setContent {
            Banner(
                text = "Banner",
                leadingIcon = FluentIcon(Icons.Outlined.Info, contentDescription = "Info"),
                actionButtonOnClick = {},
                actionButtonText = "Action",
                accessoryTextButton1 = "Accessory 1",
                accessoryTextButton2 = "Accessory 2",
                accessoryTextButton1OnClick = {},
                accessoryTextButton2OnClick = {}
            )
        }
        val bannerText = composeTestRule.onNodeWithText("Banner")
        val bannerIcon = composeTestRule.onNodeWithContentDescription("Info")
        val bannerButton = composeTestRule.onNodeWithText("Action")
        val bannerAccessory1 = composeTestRule.onNodeWithText("Accessory 1")
        val bannerAccessory2 = composeTestRule.onNodeWithText("Accessory 2")
        bannerText.assertExists()
        bannerText.assertIsDisplayed()
        bannerIcon.assertExists()
        bannerIcon.assertIsDisplayed()
        bannerButton.assertExists()
        bannerButton.assertIsDisplayed()
        bannerAccessory1.assertExists()
        bannerAccessory1.assertIsDisplayed()
        bannerAccessory2.assertExists()
        bannerAccessory2.assertIsDisplayed()
    }

    @Test
    fun testNoAccessoryButtonBanner(){
        composeTestRule.setContent {
            Banner(
                text = "Banner",
                leadingIcon = FluentIcon(Icons.Outlined.Info, contentDescription = "Info"),
                actionButtonOnClick = {},
                actionButtonText = "Action",
                accessoryTextButton1 = "Accessory 1",
                accessoryTextButton2 = "Accessory 2",
            )
        }
        composeTestRule.onNodeWithText("Accessory 1").assertDoesNotExist()
        composeTestRule.onNodeWithText("Accessory 2").assertDoesNotExist()
    }

    @Test
    fun testNoActionButtonBanner(){
        composeTestRule.setContent {
            Banner(
                text = "Banner",
                leadingIcon = FluentIcon(Icons.Outlined.Info, contentDescription = "Info"),
                actionButtonText = "Action",
            )
        }
        composeTestRule.onNodeWithText("Action").assertDoesNotExist()
    }
}
