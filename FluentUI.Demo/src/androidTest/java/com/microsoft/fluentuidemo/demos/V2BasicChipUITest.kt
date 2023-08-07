package com.microsoft.fluentuidemo.demos

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.tokenized.controls.BasicChip
import org.junit.Rule
import org.junit.Test

class V2BasicChipActivityUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBasicChip() {
        composeTestRule.setContent {
            BasicChip(modifier = Modifier.testTag("basic chip"), label = "Label")
        }
        val chip = composeTestRule.onNodeWithTag("basic chip")
        chip.assertExists()
        chip.assertIsDisplayed()
        chip.assertHasNoClickAction()
    }

    @Test
    fun testBasicChipText() {
        composeTestRule.setContent {
            BasicChip(label = "Label")
        }
        val text = composeTestRule.onNodeWithText("Label", useUnmergedTree = true)
        text.assertExists()
        text.assertIsDisplayed()
    }

    @Test
    fun testBasicChipClick() {
        composeTestRule.setContent {
            BasicChip(modifier = Modifier.testTag("basic chip"), label = "Label", onClick = {})
        }
        val chip = composeTestRule.onNodeWithTag("basic chip")
        chip.assertExists()
        chip.assertIsDisplayed()
        chip.assertHasClickAction()
    }

    @Test
    fun testBasicChipAccessoryViews(){
        composeTestRule.setContent {
            BasicChip(label = "Label", onClick = {}, trailingAccessory = { Icon(modifier = Modifier.testTag("Icon1"), icon = FluentIcon(Icons.Outlined.ShoppingCart)) }, leadingAccessory = {Icon(modifier = Modifier.testTag("Icon2"), icon = FluentIcon(Icons.Outlined.Add))})
        }
        composeTestRule.onNodeWithTag("Icon1", true).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag("Icon2", true).assertExists().assertIsDisplayed()
    }

}