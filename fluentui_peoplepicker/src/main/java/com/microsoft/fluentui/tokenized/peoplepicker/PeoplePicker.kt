package com.microsoft.fluentui.tokenized.peoplepicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.PeoplePickerInfo
import com.microsoft.fluentui.theme.token.controlTokens.PeoplePickerTokens
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.tokenized.controls.TextField
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaChip

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun PeoplePicker(
    onValueChange: (String, MutableList<PeoplePickerItemData>) -> Unit,
    modifier: Modifier = Modifier,
    selectedPeople: MutableList<PeoplePickerItemData> = mutableStateListOf(),
    onBackPress: ((String, PeoplePickerItemData?) -> Unit)? = null,
    onChipClick: ((PeoplePickerItemData) -> Unit)? = null,
    onChipCloseClick: ((PeoplePickerItemData) -> Unit)? = null,
    chipValidation: (Person) -> PersonaChipStyle = { PersonaChipStyle.Neutral },
    onTextEntered: ((String) -> Unit)? = null,
    leadingRestIcon: ImageVector? = null,
    leadingFocusIcon: ImageVector? = null,
    leadingIconContentDescription: String? = null,
    trailingAccessoryIcon: FluentIcon? = null,
    label: String? = null,
    assistiveText: String? = null,
    errorString: String? = null,
    searchHint: String? = null,
    peoplePickerTokens: PeoplePickerTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = peoplePickerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PeoplePicker] as PeoplePickerTokens

    val peoplePickerInfo = PeoplePickerInfo()
    val chipSpacing = token.chipSpacing(peoplePickerInfo = peoplePickerInfo)
    var queryText by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onKeyEvent {
                    if (it.key == Key.Backspace) {
                        if (onBackPress != null) {
                            onBackPress.invoke(queryText, selectedPeople.lastOrNull())
                            onValueChange(queryText, selectedPeople)
                        }
                    }
                    true
                }.background(token.backgroundBrush(peoplePickerInfo = peoplePickerInfo)),
            leadingTextFieldComposable = {
                Row {
                    if (selectedPeople.isNotEmpty()) {
                        selectedPeople.forEach {
                            PersonaChip(person = it.person, selected = it.selected.value,
                                onCloseClick = if (onChipCloseClick != null) {
                                    {
                                        onChipCloseClick.invoke(it)
                                        onValueChange(queryText, selectedPeople)
                                    }
                                } else {
                                    null
                                },
                                onClick = {
                                    onChipClick?.invoke(it)
                                    onValueChange(queryText, selectedPeople)
                                },
                                style = chipValidation(it.person)
                            )
                            Spacer(modifier = Modifier.width(chipSpacing))
                        }
                    }
                }
            },
            value = queryText,
            onValueChange = {
                queryText = it
                onValueChange(queryText, selectedPeople)
            },
            label = label,
            assistiveText = assistiveText,
            errorString = errorString,
            hintText = searchHint,
            leadingRestIcon = leadingRestIcon,
            leadingFocusIcon = leadingFocusIcon,
            leadingIconContentDescription = leadingIconContentDescription,
            trailingAccessoryIcon = trailingAccessoryIcon,
            keyboardOptions = KeyboardOptions(),
            keyboardActions = KeyboardActions(onDone = {
                if (onTextEntered != null) {
                    onTextEntered.invoke(queryText)
                    queryText = ""
                    onValueChange(queryText, selectedPeople)
                }
            }),
        )
    }
}

data class PeoplePickerItemData(
    val person: Person,
    var selected: MutableState<Boolean> = mutableStateOf(false)
)

@Composable
fun rememberPeoplePickerState(
    initialValue: SnapshotStateList<PeoplePickerItemData> = mutableStateListOf(),
): SnapshotStateList<PeoplePickerItemData> {
    return rememberSaveable(
        saver = Saver(
            save = {
                var saved = mutableListOf<Map<String, Any?>>()
                it.forEach { itemData ->
                    saved.add(
                        mapOf(
                            "selectedKey" to itemData.selected.value,
                            "firstName" to itemData.person.firstName,
                            "lastName" to itemData.person.lastName,
                            "email" to itemData.person.email,
                            "image" to itemData.person.image,
                            "imageBitmap" to itemData.person.imageBitmap,
                            "isActive" to itemData.person.isActive,
                            "isOOO" to itemData.person.isOOO,
                            "status" to itemData.person.status
                        )
                    )
                }
                saved
            },
            restore = { restored ->
                val list = mutableStateListOf<PeoplePickerItemData>()
                restored.forEach { item ->
                    list.add(
                        PeoplePickerItemData(
                            person = Person(
                                firstName = item["firstName"] as String,
                                lastName = item["lastName"] as String,
                                email = item["email"] as String?,
                                image = item["image"] as Int?,
                                imageBitmap = item["imageBitmap"] as ImageBitmap?,
                                isActive = item["isActive"] as Boolean,
                                isOOO = item["isOOO"] as Boolean,
                                status = item["status"] as AvatarStatus
                            ),
                            selected = mutableStateOf(item["selectedKey"] as Boolean)
                        )
                    )
                }
                list
            }
        )
    ) {
        initialValue
    }
}