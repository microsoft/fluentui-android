package com.microsoft.fluentuidemo.demos

import android.view.KeyEvent.KEYCODE_BACK
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performKeyPress
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.tokenized.peoplepicker.PeoplePicker
import com.microsoft.fluentui.tokenized.peoplepicker.PeoplePickerItemData
import com.microsoft.fluentui.tokenized.peoplepicker.rememberPeoplePickerItemDataList
import com.microsoft.fluentui.tokenized.persona.Person
import org.junit.Rule
import org.junit.Test

class V2PeoplePickerUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testV2PeoplePicker() {
        composeTestRule.setContent {
            PeoplePicker(modifier = Modifier.testTag("V2PeoplePicker"), onValueChange = { _, _ ->

            })
        }
        val peoplePicker = composeTestRule.onNodeWithTag("V2PeoplePicker")
        peoplePicker.assertExists()
        peoplePicker.assertIsDisplayed()
    }

    @Test
    fun testV2PeoplePickerTextField() {
        composeTestRule.setContent {
            PeoplePicker(modifier = Modifier.testTag("V2PeoplePicker"), onValueChange = { _, _ ->

            })
        }
        val textField = composeTestRule.onNodeWithTag("V2PeoplePicker").onChild()
        textField.assertExists()
        textField.assertIsDisplayed()
        textField.assertIsNotFocused()
        textField.performClick()
        textField.assertIsFocused()
    }

    @Test
    fun testPeoplePickerChip() {

        composeTestRule.setContent {
            PeoplePicker(
                modifier = Modifier.testTag("V2PeoplePicker"),
                onValueChange = { _, _ ->
                },
                selectedPeopleList = mutableListOf(
                    PeoplePickerItemData(
                        Person("firstName"),
                        mutableStateOf(false)
                    )
                ),
            )
        }
        val peoplePickerChip =
            composeTestRule.onAllNodesWithContentDescription("firstName", true)[0]
        peoplePickerChip.assertExists()
        peoplePickerChip.assertIsDisplayed()
        peoplePickerChip.assertHasClickAction()
    }

    @Test
    fun testPeoplePickerChipActions() {
        lateinit var closeString: String
        composeTestRule.setContent {
            var selectedPeopleList = rememberPeoplePickerItemDataList()
            closeString = stringResource(R.string.fluentui_close)
            selectedPeopleList.add(
                PeoplePickerItemData(
                    Person("firstName"),
                    mutableStateOf(false)
                )
            )
            PeoplePicker(
                modifier = Modifier.testTag("V2PeoplePicker"),
                onValueChange = { _, _ ->
                },
                selectedPeopleList = selectedPeopleList,
                onChipClick = {
                    it.selected.value = !it.selected.value
                },
                onChipCloseClick = {
                    selectedPeopleList.remove(it)
                }
            )
        }

        val peoplePickerChip =
            composeTestRule.onNodeWithText("firstName", true, useUnmergedTree = true)
        peoplePickerChip.performClick()
        val cancelButton =
            composeTestRule.onNodeWithContentDescription(closeString, useUnmergedTree = true)
        cancelButton.assertExists()
        cancelButton.assertIsDisplayed()
        cancelButton.assertHasClickAction()
        cancelButton.performClick()
        peoplePickerChip.assertDoesNotExist()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun testPeoplePickerBackPress() {
        composeTestRule.setContent {
            var selectedPeopleList = rememberPeoplePickerItemDataList()
            selectedPeopleList.add(
                PeoplePickerItemData(
                    Person("firstName"),
                    mutableStateOf(false)
                )
            )
            PeoplePicker(
                modifier = Modifier.testTag("V2PeoplePicker"),
                onValueChange = { _, _ ->
                },
                selectedPeopleList = selectedPeopleList,
                onBackPress = { _, it ->
                    selectedPeopleList.remove(it)
                }
            )
        }
        val textField = composeTestRule.onNodeWithTag("V2PeoplePicker")
        textField.assertExists()
        textField.assertIsDisplayed()
        val peoplePickerChip =
            composeTestRule.onNodeWithText("firstName", true, useUnmergedTree = true)
        peoplePickerChip.assertExists()
        peoplePickerChip.assertIsDisplayed()
        textField.performClick()
        textField.performKeyPress(
            KeyEvent(
                NativeKeyEvent(
                    KEYCODE_BACK,
                    Key.Backspace.nativeKeyCode
                )
            )
        )
        peoplePickerChip.assertDoesNotExist()
    }
}