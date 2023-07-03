package com.microsoft.fluentui.tokenized.peoplepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.PeoplePickerInfo
import com.microsoft.fluentui.theme.token.controlTokens.PeoplePickerTokens
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.tokenized.persona.*

@Composable
fun PeoplePicker(
    personas: List<Persona>,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    leadingAccessoryIcon: ImageVector? = null,
    trailingAccessoryView: (@Composable () -> Unit)? = null,
    leadingAccessoryIconOnClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    focusByDefault: Boolean = false,
    searchHint: String = "",
    peoplePickerTokens: PeoplePickerTokens? = null
) {
    val token = peoplePickerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PeoplePicker] as PeoplePickerTokens

    CompositionLocalProvider(
        LocalPeoplePickerTokens provides token,
        LocalPeoplePickerInfo provides PeoplePickerInfo(style)
    ) {
        var personaChipSelected by rememberSaveable { mutableStateOf(false) }
        var selectedPersons: List<Person>? = null
        var selectedPerson: Person? = null
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }

        var queryText by rememberSaveable { mutableStateOf("") }
        var searchHasFocus by rememberSaveable { mutableStateOf(false) }
        Column {
            Row(
                modifier = modifier
                    .background(getPeoplePickerTokens().backgroundColor(getPeoplePickerInfo()))
                    .requiredHeight(getPeoplePickerTokens().maxHeight(getPeoplePickerInfo()))
                    .fillMaxWidth()
                    .padding(
                        start = getPeoplePickerTokens()
                            .accessoryPadding(getPeoplePickerInfo())
                            .calculateStartPadding(
                                LocalLayoutDirection.current
                            )
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Left Section
                if (leadingAccessoryIcon != null) {
                    Box(
                        modifier = Modifier.padding(
                            end = getPeoplePickerTokens().accessoryPadding(getPeoplePickerInfo())
                                .calculateEndPadding(
                                    LocalLayoutDirection.current
                                )
                        ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            FluentIcon(
                                leadingAccessoryIcon
                            ),
                            modifier = Modifier
                                .size(getPeoplePickerTokens().leadingIconSize(getPeoplePickerInfo()))
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    enabled = true,
                                    onClick = { leadingAccessoryIconOnClick },
                                    role = Role.Button
                                )
                        )
                    }
                }

                //Center Section
                Row(
                    modifier = Modifier
                        .height(24.dp)
                        .weight(1F)
                        .onKeyEvent {
                            if (it.key == Key.Backspace) {

                                if (personaChipSelected) {
                                    selectedPerson = null
                                    personaChipSelected = false

                                    //onValueChange(queryText, selectedPerson)
                                } else {
                                    personaChipSelected = true
                                }

                            }
                            true
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LaunchedEffect(selectedPerson) {
                        queryText = ""
                        if (personaChipSelected)
                            personaChipSelected = false

                        //onValueChange(queryText, selectedPerson)
                    }

                    if (selectedPerson != null) {
                        PersonaChip(
                            person = selectedPerson!!,
                            modifier = Modifier.padding(end = 8.dp),
                            style = PersonaChipStyle.Neutral,
                            enabled = true,
                            selected = personaChipSelected,
                            onClick = {
                                personaChipSelected = !personaChipSelected
                                //personaChipOnClick?.invoke()
                            },
                            onCloseClick = {
                                selectedPerson = null

                                //onValueChange(queryText, selectedPerson)
                            }
                        )
                    }
                    //OutlinedTextField(value = , onValueChange = )
                    BasicTextField(
                        value = queryText,
                        onValueChange = {
                            queryText = it
                            personaChipSelected = false

                            //onValueChange(queryText, selectedPerson)
                        },
                        singleLine = true,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        modifier = Modifier
                            .weight(1F)
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                when {
                                    focusState.isFocused ->
                                        searchHasFocus = true
                                }
                            }
                            .padding(horizontal = 8.dp)
                            .semantics { contentDescription = searchHint },
                        textStyle = getPeoplePickerTokens().typography(getPeoplePickerInfo()).merge(
                            TextStyle(
                                color = getPeoplePickerTokens().textColor(getPeoplePickerInfo()),
                                textDirection = TextDirection.ContentOrLtr
                            )
                        ),
                        decorationBox = @Composable { innerTextField ->
                            Box(
                                Modifier.fillMaxWidth()
                                    .drawBehind {
                                        drawLine(
                                            Color.Red,
                                            Offset(0f, size.height),
                                            Offset(size.width, size.height),
                                            5 * density
                                        )
                                    },
                                contentAlignment = if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                    Alignment.CenterEnd
                                else
                                    Alignment.CenterStart
                            ) {
                                if (queryText.isEmpty()) {
                                    Text(
                                        searchHint
                                    )
                                }
                            }
                            innerTextField()
                        }
                    )
                }
                LaunchedEffect(Unit) {
                    if (focusByDefault)
                        focusRequester.requestFocus()
                }

                //Right Section
                if (trailingAccessoryView !== null) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        trailingAccessoryView
                    }
                }
            }
            PersonaList(personas)
        }

    }

}

@Composable
private fun getPeoplePickerTokens(): PeoplePickerTokens {
    return LocalPeoplePickerTokens.current
}

@Composable
private fun getPeoplePickerInfo(): PeoplePickerInfo {
    return LocalPeoplePickerInfo.current
}