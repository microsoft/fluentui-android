package com.microsoft.fluentui.tokenized.peoplepicker

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.DividerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DividerTokens
import com.microsoft.fluentui.theme.token.controlTokens.PeoplePickerInfo
import com.microsoft.fluentui.theme.token.controlTokens.PeoplePickerTokens
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.tokenized.divider.Divider
import com.microsoft.fluentui.tokenized.persona.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PeoplePicker(
    onValueChange: (String, MutableList<PeoplePickerItemData>) -> Unit,
    modifier: Modifier = Modifier,
    selectedPeople: MutableList<PeoplePickerItemData> = mutableStateListOf(),
    onBackPress: ((PeoplePickerItemData) -> Unit)? = null,
    onChipClick: ((PeoplePickerItemData) -> Unit)? = null,
    onChipCloseClick: ((PeoplePickerItemData) -> Unit)? = null,
    chipValidation: (Person) -> PersonaChipStyle = { PersonaChipStyle.Neutral },
    onTextEntered: ((String) -> Unit)? = null,
    leadingAccessoryContent: (@Composable () -> Unit)? = null,
    trailingAccessoryContent: (@Composable () -> Unit)? = null,
    label: String? = null,
    assistiveText: String? = null,
    errorString: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
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
    val textTypography = token.typography(peoplePickerInfo)
    val textColor = token.textColor(peoplePickerInfo = peoplePickerInfo)
    val cursorColor = token.cursorColor(peoplePickerInfo = peoplePickerInfo)
    val focusRequester = remember { FocusRequester() }
    var searchHasFocus by rememberSaveable { mutableStateOf(false) }
    var queryText by rememberSaveable { mutableStateOf("") }

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
                            if (it.key == Key.Backspace && queryText.isEmpty() && selectedPeople.isNotEmpty()) {
                                if (onBackPress != null) {
                                    onBackPress.invoke(selectedPeople.last())
                                    onValueChange(queryText, selectedPeople)
                                }
                            }
                            if (it.key == Key.Enter && queryText.isNotBlank() && queryText.isNotEmpty()) {
                                if (onTextEntered != null) {
                                    onTextEntered.invoke(queryText)
                                    queryText = ""
                                    onValueChange(queryText, selectedPeople)
                                }
                            }
                            true
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (selectedPeople.isNotEmpty()) {
                        selectedPeople.forEach {
                            PersonaChip(person = it.person, selected = it.selected,
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
                    BasicTextField(
                        value = queryText,
                        onValueChange = {
                            queryText = it
                            onValueChange(queryText, selectedPeople)
                        },
                        singleLine = true,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                when {
                                    focusState.isFocused ->
                                        searchHasFocus = true
                                }
                            }
                            .semantics { contentDescription = searchHint ?: "" },
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
                        cursorBrush = cursorColor
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
                        token.dividerColor(peoplePickerInfo)
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

data class PeoplePickerItemData(val person: Person, var initialSelected: Boolean = false) {
    var selected by mutableStateOf(initialSelected)
}