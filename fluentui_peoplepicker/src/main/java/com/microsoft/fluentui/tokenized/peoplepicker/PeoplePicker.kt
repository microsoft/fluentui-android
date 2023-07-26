package com.microsoft.fluentui.tokenized.peoplepicker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.DividerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DividerTokens
import com.microsoft.fluentui.theme.token.controlTokens.PeoplePickerInfo
import com.microsoft.fluentui.theme.token.controlTokens.PeoplePickerTokens
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.tokenized.divider.Divider
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaChip

/**
 * API to create a customized PeoplePicker for users to add a list of PersonaChips
 *
 * Whenever the user edits the text or a new PersonaChip is added onValueChange is called with the most up to date data
 * with which developer is expected to update their state.
 *
 * PeoplePicker uses [PeoplePickerItemData] to represent a PersonaChip. This is a wrapper around [Person].
 *
 * Note: Use rememberPeoplePickerState function on selectedPeople list to create a rememberSaveable state for PeoplePicker.
 *
 * @param selectedPeopleList List of PersonaChips to be shown in PeoplePicker.
 * @param onValueChange The callback that is triggered when the input service updates the text or [selectedPeopleList].
 * An updated text and List of selectedPeople comes as a parameter of the callback
 * @param modifier Optional modifier for the TextField
 * @param onBackPress The callback that is triggered when the back button is pressed.
 * @param onChipClick The callback that is triggered when a PersonaChip is clicked.
 * @param onChipCloseClick The callback that is triggered when the close button of a PersonaChip is clicked.
 * Note: use this callback to show the cancel button for a persona chip. To disable/not show the close button leave this callback as null.
 * @param chipValidation The callback that is triggered when a PersonaChip is added. This callback is used to validate
 * the PersonaChip before adding it to the list of selectedPeople.
 * @param onTextEntered The callback that is triggered when the user clicks done on the keyboard.
 * @param leadingAccessoryContent The content to be placed towards the start of PeoplePicker.
 * @param trailingAccessoryContent The content to be placed towards the end of PeoplePicker.
 * @param label String which acts as a description for the PeoplePicker.
 * @param assistiveText String which assists users with the PeoplePicker
 * @param errorString String to describe the error. PeoplePicker goes in error mode if this is provided.
 * @param searchHint String to be shown as hint when the PeoplePicker is in rest state.
 * @param peoplePickerTokens Customization options for the PeoplePicker.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun PeoplePicker(
    selectedPeopleList: MutableList<PeoplePickerItemData> = mutableStateListOf(),
    onValueChange: (String, MutableList<PeoplePickerItemData>) -> Unit,
    modifier: Modifier = Modifier,
    onBackPress: ((String, PeoplePickerItemData?) -> Unit)? = null,
    onChipClick: ((PeoplePickerItemData) -> Unit)? = null,
    onChipCloseClick: ((PeoplePickerItemData) -> Unit)? = null,
    chipValidation: (Person) -> PersonaChipStyle = { PersonaChipStyle.Neutral },
    onTextEntered: ((String) -> Unit)? = null,
    leadingAccessoryContent: (@Composable () -> Unit)? = null,
    trailingAccessoryContent: (@Composable () -> Unit)? = null,
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

    var isFocused: Boolean by rememberSaveable { mutableStateOf(false) }
    val peoplePickerInfo = PeoplePickerInfo(
        isStatusError = !errorString.isNullOrBlank(),
        isFocused = isFocused,
    )
    val leadingAccessorySpacing = token.leadingAccessorySpacing(peoplePickerInfo = peoplePickerInfo)
    val trailingAccessoryPadding =
        token.trailingAccessoryPadding(peoplePickerInfo = peoplePickerInfo)
    val chipSpacing = token.chipSpacing(peoplePickerInfo = peoplePickerInfo)
    val textTypography = token.textFieldTypography(peoplePickerInfo)
    val textColor = token.textColor(peoplePickerInfo = peoplePickerInfo)
    val cursorBrush = token.cursorBrush(peoplePickerInfo = peoplePickerInfo)
    val focusRequester = remember { FocusRequester() }
    var queryText by rememberSaveable { mutableStateOf("") }
    var selectedPeopleListSize by rememberSaveable { mutableStateOf(0) }

    Row(
        modifier = modifier
            .onFocusChanged { focusState ->
                when {
                    focusState.isFocused -> {
                        focusRequester.requestFocus()
                    }
                }
            }, verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingAccessoryContent != null) {
            leadingAccessoryContent()
            Spacer(modifier = Modifier.width(leadingAccessorySpacing))
        }
        Column {
            if (!label.isNullOrBlank()) {
                Spacer(Modifier.requiredHeight(12.dp))
                BasicText(
                    label,
                    style = token.labelTypography(peoplePickerInfo).merge(
                        TextStyle(
                            color = token.labelColor(peoplePickerInfo)
                        )
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(token.minHeight(peoplePickerInfo = peoplePickerInfo)),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(rememberScrollState())
                        .onKeyEvent {
                            if (it.key == Key.Backspace) {
                                if (onBackPress != null) {
                                    onBackPress.invoke(queryText, selectedPeopleList.lastOrNull())
                                    onValueChange(queryText, selectedPeopleList)
                                }
                            }
                            true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (selectedPeopleList.isNotEmpty()) {
                        selectedPeopleList.forEach {
                            PersonaChip(person = it.person, selected = it.selected.value,
                                onCloseClick = if (onChipCloseClick != null) {
                                    {
                                        onChipCloseClick.invoke(it)
                                        onValueChange(queryText, selectedPeopleList)
                                    }
                                } else {
                                    null
                                },
                                onClick = {
                                    onChipClick?.invoke(it)
                                    onValueChange(queryText, selectedPeopleList)
                                },
                                style = chipValidation(it.person)
                            )
                            Spacer(modifier = Modifier.width(chipSpacing))
                        }
                    }
                    if (selectedPeopleListSize < selectedPeopleList.size) {
                        queryText = ""
                        onValueChange(queryText, selectedPeopleList)
                    }
                    selectedPeopleListSize = selectedPeopleList.size
                    BasicTextField(
                        value = queryText,
                        onValueChange = {
                            queryText = it
                            onValueChange(queryText, selectedPeopleList)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(),
                        keyboardActions = KeyboardActions(onDone = {
                            if (onTextEntered != null) {
                                onTextEntered.invoke(queryText)
                                queryText = ""
                                onValueChange(queryText, selectedPeopleList)
                            }
                        }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .semantics { contentDescription = searchHint ?: "" }
                            .testTag("Text field"),
                        textStyle = textTypography.merge(
                            TextStyle(
                                color = textColor,
                                textDirection = TextDirection.ContentOrLtr
                            )
                        ),
                        decorationBox = @Composable { innerTextField ->
                            Box(
                                Modifier
                                    .fillMaxWidth(),
                                contentAlignment = if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                    Alignment.CenterEnd
                                else
                                    Alignment.CenterStart
                            ) {
                                if (queryText.isEmpty()) {
                                    BasicText(
                                        searchHint ?: "",
                                        style = token.hintTextTypography(peoplePickerInfo)
                                            .merge(
                                                TextStyle(
                                                    color = token.hintColor(peoplePickerInfo)
                                                )
                                            )
                                    )
                                }

                            }
                            innerTextField()
                        },
                        cursorBrush = cursorBrush
                    )
                }
                if (trailingAccessoryContent != null) {
                    Box(modifier = Modifier.padding(trailingAccessoryPadding)) {
                        trailingAccessoryContent()
                    }
                }
            }
            Divider(
                height = token.strokeWidth(peoplePickerInfo),
                dividerToken = object : DividerTokens() {
                    @Composable
                    override fun verticalPadding(dividerInfo: DividerInfo): PaddingValues {
                        return PaddingValues(0.dp)
                    }

                    @Composable
                    override fun dividerBrush(dividerInfo: DividerInfo): Brush =
                        token.dividerBrush(peoplePickerInfo)
                }
            )
            if (!assistiveText.isNullOrBlank() || !errorString.isNullOrBlank()) {
                BasicText(
                    errorString ?: assistiveText!!,
                    style = token.assistiveTextTypography(peoplePickerInfo).merge(
                        TextStyle(
                            color = token.assistiveTextColor(peoplePickerInfo)
                        )
                    ),
                    modifier = Modifier.padding(token.assistiveTextPadding(peoplePickerInfo))
                )
            }
        }
    }
}

data class PeoplePickerItemData(
    val person: Person,
    var selected: MutableState<Boolean> = mutableStateOf(false)
)

@Composable
fun rememberPeoplePickerItemDataList(
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