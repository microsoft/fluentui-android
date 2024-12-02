package com.microsoft.fluentui.tokenized

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
 * @param loading Boolean to display progress indicator on SearchBar. Default: [false]
 * @param selectedPerson Person object which has to be displayed as a Persona Chip. Default: [null]
 * @param personaChipOnClick OnClick Behaviour for above persona chip. Default: [null]
 * @param microphoneCallback Callback to be provided to microphone icon, available at right side. Default: [null]
 * @param navigationIconCallback Callback to be provided to navigation icon, present at left side. Default: [null]
 * @param rightAccessoryIcon [FluentIcon] Object which is displayed on the right side of microphone. Default: [null]
 * @param searchBarTokens Tokens which help in customizing appearance of search bar. Default: [null]
 */
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
    loading: Boolean = false,
    selectedPerson: Person? = null,
    personaChipOnClick: (() -> Unit)? = null,
    microphoneCallback: (() -> Unit)? = null,
    navigationIconCallback: (() -> Unit)? = null,
    leftAccessoryIcon: ImageVector? = SearchBarIcons.Search,
    rightAccessoryIcon: FluentIcon? = null,
    searchBarTokens: SearchBarTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = searchBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.SearchBarControlType] as SearchBarTokens
    val searchBarInfo = SearchBarInfo(style)
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var queryText by rememberSaveable { mutableStateOf("") }
    var searchHasFocus by rememberSaveable { mutableStateOf(false) }

    var personaChipSelected by rememberSaveable { mutableStateOf(false) }
    var selectedPerson: Person? = selectedPerson
    val borderWidth = token.borderWidth(searchBarInfo)
    val elevation = token.elevation(searchBarInfo)
    val height = token.height(searchBarInfo)

    val scope = rememberCoroutineScope()
    val borderModifier = if (borderWidth > 0.dp) {
        Modifier.border(
            width = borderWidth,
            color = token.borderColor(searchBarInfo),
            shape = RoundedCornerShape(token.cornerRadius(searchBarInfo))
        )
    } else Modifier
    val shadowModifier = if (elevation > 0.dp) Modifier.shadow(
        elevation = token.elevation(searchBarInfo),
        shape = RoundedCornerShape(token.cornerRadius(searchBarInfo)),
        spotColor = token.shadowColor(searchBarInfo)
    ) else Modifier

    Row(
        modifier = modifier
            .background(token.backgroundBrush(searchBarInfo))
            .padding(token.searchBarPadding(searchBarInfo))
    ) {
        Row(
            Modifier
                .requiredHeightIn(min = height)
                .then(borderModifier)
                .then(shadowModifier)
                .fillMaxWidth()
                .clip(RoundedCornerShape(token.cornerRadius(searchBarInfo)))
                .background(
                    token.inputBackgroundBrush(searchBarInfo),
                    RoundedCornerShape(token.cornerRadius(searchBarInfo))
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
                            queryText = ""
                            selectedPerson = null

                            onValueChange(queryText, selectedPerson)

                            focusManager.clearFocus()
                            searchHasFocus = false

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
                            focusRequester.requestFocus()
                        }
                        icon = leftAccessoryIcon ?: SearchBarIcons.Search
                        contentDescription =
                            LocalContext.current.resources.getString(R.string.fluentui_search)
                        mirrorImage = false
                    }
                }

                Icon(
                    icon,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .padding(
                            (44.dp - token.leftIconSize(searchBarInfo)) / 2,
                            (40.dp - token.leftIconSize(searchBarInfo)) / 2
                        )
                        .size(token.leftIconSize(searchBarInfo)),
                    tint = token.leftIconColor(searchBarInfo),
                    flipOnRtl = mirrorImage,
                    enabled = enabled,
                    onClick = onClick
                )
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

                                onValueChange(queryText, selectedPerson)
                            } else {
                                personaChipSelected = true
                            }

                        }
                        false
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                LaunchedEffect(selectedPerson) {
                    queryText = ""
                    if (personaChipSelected)
                        personaChipSelected = false

                    onValueChange(queryText, selectedPerson)
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

                            onValueChange(queryText, selectedPerson)
                        }
                    )
                }

                BasicTextField(
                    value = queryText,
                    onValueChange = {
                        queryText = it
                        personaChipSelected = false

                        onValueChange(queryText, selectedPerson)
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
                    textStyle = token.typography(searchBarInfo).merge(
                        TextStyle(
                            color = token.textColor(searchBarInfo),
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
                                BasicText(
                                    searchHint,
                                    style = token.typography(searchBarInfo)
                                        .merge(TextStyle(color = token.textColor(searchBarInfo)))
                                )
                            }
                        }
                        innerTextField()
                    },
                    cursorBrush = token.cursorColor(searchBarInfo)
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
                            Icon(
                                SearchBarIcons.Microphone,
                                contentDescription = LocalContext.current.resources.getString(
                                    R.string.fluentui_microphone
                                ),
                                modifier = Modifier
                                    .padding(
                                        (44.dp - token.rightIconSize(searchBarInfo)) / 2,
                                        (40.dp - token.rightIconSize(searchBarInfo)) / 2
                                    )
                                    .size(
                                        token.rightIconSize(
                                            searchBarInfo
                                        )
                                    ),
                                tint = token.rightIconColor(searchBarInfo),
                                onClick = microphoneCallback
                            )
                        }

                    false ->
                        Box(
                            modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    enabled = enabled,
                                    onClick = {
                                        queryText = ""
                                        selectedPerson = null

                                        onValueChange(queryText, selectedPerson)
                                    },
                                    role = Role.Button
                                )
                                .size(44.dp, 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            if (loading) {
                                CircularProgressIndicator(
                                    size = token.circularProgressIndicatorSize(
                                        searchBarInfo
                                    )
                                )
                            }
                            Icon(
                                SearchBarIcons.Dismisscircle,
                                contentDescription = LocalContext.current.resources.getString(
                                    R.string.fluentui_clear_text
                                ),
                                modifier = Modifier
                                    .size(token.rightIconSize(searchBarInfo)),
                                tint = token.rightIconColor(searchBarInfo)
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
                        rightAccessoryIcon.value(),
                        contentDescription = rightAccessoryIcon.contentDescription,
                        modifier = Modifier
                            .size(token.rightIconSize(searchBarInfo)),
                        tint = token.rightIconColor(searchBarInfo)
                    )
                    Icon(
                        ListItemIcons.Chevron,
                        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_chevron),
                        Modifier.rotate(90F),
                        tint = token.rightIconColor(searchBarInfo)
                    )
                }
            }
        }
    }
}