package com.microsoft.fluentui.tokenized

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.Strings
import com.microsoft.fluentui.compose.getString
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.icons.searchbaricons.Dismisscircle
import com.microsoft.fluentui.icons.searchbaricons.Microphone
import com.microsoft.fluentui.icons.searchbaricons.Search
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarTokens
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.SearchBarPersonaChip
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import kotlinx.coroutines.launch

private val LocalSearchBarTokens = compositionLocalOf { SearchBarTokens() }
private val LocalSearchBarInfo = compositionLocalOf { SearchBarInfo(FluentStyle.Neutral) }

//         AnimatedContent                        Backspace Key
@OptIn(ExperimentalComposeUiApi::class, ExperimentalAnimationApi::class)
@Composable
fun SearchBar(
    onValueChange: (String, Person?) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: FluentStyle = FluentStyle.Neutral,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    searchHint: String = getString(Strings.Search),
    selectedPerson: Person? = null,
    personaChipOnClick: (() -> Unit)? = null,
    microphoneCallback: (() -> Unit)? = null,
    rightAccessoryIcon: ImageVector? = null,
    rightAccessoryIconDescription: String = "",
    rightAccessoryViewOnClick: (() -> Unit)? = null,
    searchBarTokens: SearchBarTokens? = null
) {

    val token = searchBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.SearchBar] as SearchBarTokens

    CompositionLocalProvider(
        LocalSearchBarTokens provides token,
        LocalSearchBarInfo provides SearchBarInfo(style)
    ) {
        val focusManager = LocalFocusManager.current
        val focusRequester = remember { FocusRequester() }

        var queryText by rememberSaveable { mutableStateOf("") }
        var searching by rememberSaveable { mutableStateOf(false) }
        var searchHasFocus by rememberSaveable { mutableStateOf(false) }

        var personaChipSelected by rememberSaveable { mutableStateOf(false) }
        var selectedPerson: Person? = selectedPerson

        val scope = rememberCoroutineScope()

        Row(
            modifier
                .requiredHeight(getSearchBarTokens().height(getSearchBarInfo()))
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(
                    getSearchBarTokens().inputBackgroundColor(getSearchBarInfo()),
                    RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            //Left Section
            AnimatedContent(searchHasFocus) {
                var onClick: (() -> Unit)? = null
                var icon: ImageVector? = null
                var contentDescription: String? = null

                when (it) {
                    true -> {
                        onClick = {
                            scope.launch {
                                queryText = ""
                                selectedPerson = null

                                searching = true
                                onValueChange(queryText, selectedPerson)
                                searching = false

                                focusManager.clearFocus()
                                searchHasFocus = false
                            }
                        }
                        icon = SearchBarIcons.Arrowback
                        contentDescription = getString(Strings.Back)
                    }
                    false -> {
                        onClick = {
                            scope.launch {
                                focusRequester.requestFocus()
                            }
                        }
                        icon = SearchBarIcons.Search
                        contentDescription = getString(Strings.Search)
                    }

                }
                Box(
                    modifier = Modifier
                        .size(44.dp, 40.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            enabled = enabled,
                            onClick = onClick,
                            role = Role.Button
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription,
                        modifier = Modifier
                            .size(getSearchBarTokens().leftIconSize(getSearchBarInfo()).size),
                        tint = getSearchBarTokens().leftIconColor(getSearchBarInfo())
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
                            scope.launch {
                                if (personaChipSelected) {
                                    selectedPerson = null
                                    personaChipSelected = false

                                    searching = true
                                    onValueChange(queryText, selectedPerson)
                                    searching = false
                                } else {
                                    personaChipSelected = true
                                }
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

                    searching = true
                    onValueChange(queryText, selectedPerson)
                    searching = false
                }

                if (selectedPerson != null) {
                    SearchBarPersonaChip(
                        person = selectedPerson!!,
                        modifier = Modifier.padding(end = 8.dp),
                        style = style,
                        enabled = enabled,
                        selected = personaChipSelected,
                        onClick = {
                            personaChipSelected = !personaChipSelected
                            personaChipOnClick?.invoke()
                        },
                        onCloseClick = {
                            selectedPerson = null

                            searching = true
                            onValueChange(queryText, selectedPerson)
                            searching = false
                        }
                    )
                }

                BasicTextField(
                    value = queryText,
                    onValueChange = {
                        scope.launch {
                            queryText = it
                            personaChipSelected = false

                            searching = true
                            onValueChange(queryText, selectedPerson)
                            searching = false
                        }
                    },
                    singleLine = true,
                    keyboardOptions = keyboardOptions,
                    keyboardActions = keyboardActions,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            when {
                                focusState.isFocused ->
                                    searchHasFocus = true
                            }
                        },
                    textStyle = TextStyle(
                        fontSize = getSearchBarTokens().typography(getSearchBarInfo()).fontSize.size,
                        lineHeight = getSearchBarTokens().typography(getSearchBarInfo()).fontSize.size,
                        color = getSearchBarTokens().textColor(getSearchBarInfo())
                    ),
                    decorationBox = @Composable { innerTextField ->
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (queryText.isEmpty()) {
                                Text(
                                    searchHint,
                                    style = TextStyle(
                                        fontSize = getSearchBarTokens().typography(getSearchBarInfo()).fontSize.size,
                                        lineHeight = getSearchBarTokens().typography(
                                            getSearchBarInfo()
                                        ).fontSize.size,
                                        color = getSearchBarTokens().textColor(getSearchBarInfo()),
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            //Right Section
            AnimatedContent((queryText.isBlank() && selectedPerson == null)) {
                when (it) {
                    true ->
                        if (microphoneCallback != null) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp, 40.dp)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple(),
                                        enabled = enabled,
                                        onClick = microphoneCallback,
                                        role = Role.Button
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    SearchBarIcons.Microphone,
                                    getString(Strings.Microphone),
                                    modifier = Modifier
                                        .size(getSearchBarTokens().rightIconSize(getSearchBarInfo()).size),
                                    tint = getSearchBarTokens().rightIconColor(getSearchBarInfo())
                                )
                            }
                        }
                    false ->
                        Box(
                            modifier = Modifier
                                .size(44.dp, 40.dp)
                                .padding(
                                    getSearchBarTokens().progressIndicatorRightPadding(
                                        getSearchBarInfo()
                                    )
                                )
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    enabled = enabled,
                                    onClick = {
                                        scope.launch {
                                            queryText = ""
                                            selectedPerson = null

                                            searching = true
                                            onValueChange(queryText, selectedPerson)
                                            searching = false
                                        }
                                    },
                                    role = Role.Button
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (searching)
                                CircularProgressIndicator(
                                    size = getSearchBarTokens().circularProgressIndicatorSize(
                                        getSearchBarInfo()
                                    )
                                )
                            Icon(
                                SearchBarIcons.Dismisscircle,
                                getString(Strings.ClearText),
                                modifier = Modifier
                                    .size(getSearchBarTokens().rightIconSize(getSearchBarInfo()).size),
                                tint = getSearchBarTokens().rightIconColor(getSearchBarInfo())
                            )
                        }
                }
            }

            if (rightAccessoryIcon != null && rightAccessoryViewOnClick != null) {
                Row(
                    modifier = Modifier
                        .size(44.dp, 40.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            enabled = enabled,
                            onClick = rightAccessoryViewOnClick,
                            role = Role.Button
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        rightAccessoryIcon,
                        rightAccessoryIconDescription,
                        modifier = Modifier
                            .size(getSearchBarTokens().rightIconSize(getSearchBarInfo()).size),
                        tint = getSearchBarTokens().rightIconColor(getSearchBarInfo())
                    )
                    Icon(
                        ListItemIcons.Chevron,
                        getString(Strings.Chevron),
                        Modifier.rotate(90F)
                    )
                }
            }
        }
    }
}

@Composable
private fun getSearchBarTokens(): SearchBarTokens {
    return LocalSearchBarTokens.current
}

@Composable
private fun getSearchBarInfo(): SearchBarInfo {
    return LocalSearchBarInfo.current
}