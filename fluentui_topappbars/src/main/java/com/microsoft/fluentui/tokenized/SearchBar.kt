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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.icons.searchbaricons.Dismisscircle
import com.microsoft.fluentui.icons.searchbaricons.Microphone
import com.microsoft.fluentui.icons.searchbaricons.Search
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarTokens
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.SearchBarPersonaChip
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import com.microsoft.fluentui.topappbars.R
import kotlinx.coroutines.launch

private val LocalSearchBarTokens = compositionLocalOf { SearchBarTokens() }
private val LocalSearchBarInfo = compositionLocalOf { SearchBarInfo(FluentStyle.Neutral) }

/**
 * API to create a searchbar. This control takes input from user's keyboard and runs it against a lambda
 * function provided by user to generate results. It allows user to select a person and display in the form
 * of a persona chip.
 *
 * @param onValueChange Lambda function against which the input text is run.
 * @param modifier Optional modifier for Searchbar.
 * @param enabled Boolean to enable/disable the CTAs on Searchbar. Default: [false]
 * @param style Color Scheme to be applied to Searchbar. Default: [FluentStyle.Neutral]
 * @param keyboardOptions Keyboard Configuration Options for Input Text Field. Default: [KeyboardOptions]
 * @param keyboardActions Configures keyboards response w.r.t. user interactions. Default: [KeyboardActions]
 * @param searchHint String provided as hint on SearchBar. Default: "Search"
 * @param focusByDefault Boolean which allows Searchbar to be initially composed in focused state. Default: [false]
 * @param selectedPerson Person object which has to be displayed as a Persona Chip. Default: [null]
 * @param personaChipOnClick OnClick Behaviour for above persona chip. Default: [null]
 * @param microphoneCallback Callback to be provided to microphone icon, available at right side. Default: [null]
 * @param navigationIconCallback Callback to be provided to navigation icon, present at left side. Default: [null]
 * @param rightAccessoryIcon [FluentIcon] Object which is displayed on the right side of microphone. Default: [null]
 * @param searchBarTokens Tokens which help in customizing appearance of search bar. Default: [null]
 */
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
    searchHint: String = LocalContext.current.resources.getString(R.string.fluentui_search),
    focusByDefault: Boolean = false,
    selectedPerson: Person? = null,
    personaChipOnClick: (() -> Unit)? = null,
    microphoneCallback: (() -> Unit)? = null,
    navigationIconCallback: (() -> Unit)? = null,
    rightAccessoryIcon: FluentIcon? = null,
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
            modifier = modifier
                .background(getSearchBarTokens().backgroundColor(getSearchBarInfo()))
                .padding(getSearchBarTokens().searchBarPadding(getSearchBarInfo()))
        ) {
            Row(
                Modifier
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

                    var mirrorImage = false

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
                                navigationIconCallback?.invoke()
                            }
                            icon = SearchBarIcons.Arrowback
                            contentDescription =
                                LocalContext.current.resources.getString(R.string.fluentui_back)
                            if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                mirrorImage = true
                        }
                        false -> {
                            onClick = {
                                scope.launch {
                                    focusRequester.requestFocus()
                                }
                            }
                            icon = SearchBarIcons.Search
                            contentDescription =
                                LocalContext.current.resources.getString(R.string.fluentui_search)
                            mirrorImage = false
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
                            FluentIcon(
                                icon,
                                contentDescription = contentDescription,
                                tint = getSearchBarTokens().leftIconColor(getSearchBarInfo()),
                                flipOnRtl = mirrorImage
                            ),
                            modifier = Modifier
                                .size(getSearchBarTokens().leftIconSize(getSearchBarInfo())),
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
                            }
                            .padding(horizontal = 8.dp),
                        textStyle = getSearchBarTokens().typography(getSearchBarInfo()).merge(
                            TextStyle(
                                color = getSearchBarTokens().textColor(getSearchBarInfo()),
                                textDirection = TextDirection.ContentOrLtr
                            )
                        ),
                        decorationBox = @Composable { innerTextField ->
                            Box(
                                Modifier.fillMaxWidth(),
                                contentAlignment = if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                    Alignment.CenterEnd
                                else
                                    Alignment.CenterStart
                            ) {
                                if (queryText.isEmpty()) {
                                    Text(
                                        searchHint,
                                        style = getSearchBarTokens().typography(getSearchBarInfo()),
                                        color = getSearchBarTokens().textColor(getSearchBarInfo())
                                    )
                                }
                            }
                            innerTextField()
                        },
                        cursorBrush = getSearchBarTokens().cursorColor(getSearchBarInfo())
                    )
                }
                LaunchedEffect(Unit) {
                    if (focusByDefault)
                        focusRequester.requestFocus()
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
                                        FluentIcon(
                                            SearchBarIcons.Microphone,
                                            contentDescription = LocalContext.current.resources.getString(
                                                R.string.fluentui_microphone
                                            ),
                                            tint = getSearchBarTokens().rightIconColor(
                                                getSearchBarInfo()
                                            )
                                        ),
                                        modifier = Modifier
                                            .size(
                                                getSearchBarTokens().rightIconSize(
                                                    getSearchBarInfo()
                                                )
                                            )
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
                                    FluentIcon(
                                        SearchBarIcons.Dismisscircle,
                                        contentDescription = LocalContext.current.resources.getString(
                                            R.string.fluentui_clear_text
                                        ),
                                        tint = getSearchBarTokens().rightIconColor(getSearchBarInfo())
                                    ),
                                    modifier = Modifier
                                        .size(getSearchBarTokens().rightIconSize(getSearchBarInfo()))
                                )
                            }
                    }
                }

                if (rightAccessoryIcon?.isIconAvailable() == true && rightAccessoryIcon.onClick != null) {
                    Row(
                        modifier = Modifier
                            .size(44.dp, 40.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(),
                                enabled = enabled,
                                onClick = rightAccessoryIcon.onClick!!,
                                role = Role.Button
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            rightAccessoryIcon,
                            modifier = Modifier
                                .size(getSearchBarTokens().rightIconSize(getSearchBarInfo())),
                            tint = getSearchBarTokens().rightIconColor(getSearchBarInfo())
                        )
                        Icon(
                            FluentIcon(
                                ListItemIcons.Chevron,
                                contentDescription = LocalContext.current.resources.getString(R.string.fluentui_chevron),
                                tint = getSearchBarTokens().rightIconColor(getSearchBarInfo())
                            ),
                            Modifier.rotate(90F)
                        )
                    }
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