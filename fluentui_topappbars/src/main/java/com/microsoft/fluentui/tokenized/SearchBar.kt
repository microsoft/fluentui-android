package com.microsoft.fluentui.tokenized

import Searchable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.icons.searchbaricons.Dismisscircle
import com.microsoft.fluentui.icons.searchbaricons.Microphone
import com.microsoft.fluentui.icons.searchbaricons.Search
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1Pressed
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1Selected
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.ListItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarTokens
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.SearchBarPersonaChip
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import com.microsoft.fluentui.topappbars.R
import kotlinx.coroutines.launch

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



class SearchableDrawerTokens {
    @Composable
    fun topRowTextColours(): List<Color> {
        return listOf(
            Color(0xFF616161),
            Color(0xFF242424),
            Color(0xFF464FEB)
        )
    }

    @Composable
    fun drawerTokens() {

    }
}

@Composable
private fun ClickableTextHeader(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    textStyle: TextStyle = TextStyle(
        color = Color(0xFF242424),
        fontSize = 17.sp,
        lineHeight = 22.sp,
        letterSpacing = -0.43.sp,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight(400)
    )
) {
    val interactionSourceStart = remember { MutableInteractionSource() }
    val animatedFontSizeStart by animateDpAsState(
        targetValue = if (interactionSourceStart.collectIsPressedAsState().value) 18.5.dp else 17.dp,
        label = "FontSizeAnimation"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        BasicText(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = true,
                    indication = null,
                    interactionSource = interactionSourceStart
                ) {
                    onClick()
                },
            style = TextStyle(
                color = textStyle.color,
                fontSize = animatedFontSizeStart.value.sp,
                lineHeight = textStyle.lineHeight,
                letterSpacing = textStyle.letterSpacing,
                textAlign = textStyle.textAlign,
                fontWeight = textStyle.fontWeight
            )
        )
    }
}

@Composable
fun SearchableDrawerHeader(
    tokens: SearchableDrawerTokens = SearchableDrawerTokens(),
    onLeftTextClick: () -> Unit = {},
    onCenterTextClick: () -> Unit = {},
    onRightTextClick: () -> Unit = {},
) {
    val textColours = tokens.topRowTextColours()
    val textHeaders = listOf("Clear", "Person", "Done")
    val textOnClicks = listOf(onLeftTextClick, onCenterTextClick, onRightTextClick)
    val textFontWeights = listOf(FontWeight(400), FontWeight(600), FontWeight(400))
    val textAlignments = listOf(TextAlign.Start, TextAlign.Center, TextAlign.End)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0..2) {
            ClickableTextHeader(
                text = textHeaders[i],
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                onClick = textOnClicks.get(i),
                textStyle = TextStyle(
                    color = textColours.get(i),
                    lineHeight = 22.sp,
                    letterSpacing = -0.43.sp,
                    textAlign = textAlignments.get(i),
                    fontWeight = textFontWeights.get(i),
                )
            )
        }
    }
}

@Composable
private fun KeyboardPopupCallbacks(
    onKeyboardVisible: () -> Unit = {},
    onKeyboardHidden: () -> Unit = {}
) {
    val imeInsets = WindowInsets.ime
    val density = LocalDensity.current
    val isKeyboardVisible by remember {
        derivedStateOf {
            imeInsets.getBottom(density) > 0
        }
    }

    LaunchedEffect(isKeyboardVisible) {
        if (isKeyboardVisible) {
            onKeyboardVisible()
        } else {
            onKeyboardHidden()
        }
    }
}

class SearchItem(
    val title: String,
    val subTitle: String? = null,
    val footer: String? = null,
    val leftAccessory: @Composable (() -> Unit)? = null,
    val rightAccessory: @Composable (() -> Unit)? = null,
    val status: AvatarStatus? = null,
    val onClick: () -> Unit = {},
    val onLongClick: () -> Unit = {},
    val enabled: Boolean = true,
    val getUniqueId: () -> String = { title },
) {}

@Composable
fun SearchHeader(
    SearchBarComposable: @Composable () -> Unit,
    numSelected: Int,
) {

    if (numSelected == 0) {
        SearchBarComposable()
    } else {
        BasicText(
            "Selected Items: ${numSelected}",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            style = TextStyle(
                color = Color(0xFF242424),
                fontSize = 17.sp,
                lineHeight = 22.sp,
                letterSpacing = -0.43.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight(400)
            )
        )
    }
}

@Composable
fun BottomDrawerSearchableList(
    onTitleClick: () -> Unit = {},
    onLeftTextClick: () -> Unit = {},
    onRightTextClick: () -> Unit = {},
    openDrawer: () -> Unit = {},
    expandDrawer: () -> Unit = {},
    closeDrawer: () -> Unit = {},
    selectItem: (Searchable) -> Unit = {},
    deselectItem: (Searchable) -> Unit = {},
    filteredSearchItems: List<Searchable> = listOf(), // PASS ALL ITEMS IF NOTHING SEARCHED , WILL STABLE LISTS HELP HERE?
    selectedSearchItems: MutableList<Searchable> = mutableListOf(),
    SearchBarComposable: @Composable () -> Unit,
    SelectedItemScreenComposable: @Composable (numSelected: Int) -> Unit,
    ListItemComposable: @Composable (index: Int, item: Searchable) -> Unit, // This is a placeholder for the ListItem composable, can be replaced with actual implementation
) {
    KeyboardPopupCallbacks(
        onKeyboardVisible = expandDrawer,
        onKeyboardHidden = openDrawer
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchableDrawerHeader(
            onLeftTextClick = onLeftTextClick,
            onRightTextClick = onRightTextClick,
            onCenterTextClick = onTitleClick
        )
        SearchHeader(
            SearchBarComposable = { SearchBarComposable() },
            numSelected = selectedSearchItems.size
        )
        AllItemsList(
            filteredSearchItems = filteredSearchItems,
            inSelectionMode = selectedSearchItems.size > 0,
            selectedSearchItems = selectedSearchItems,
            selectItem = selectItem,
            deselectItem = deselectItem,
            ListItemComposable = ListItemComposable,
            border = BorderType.NoBorder
        )
    }
}


@Composable
fun AllItemsList(
    modifier: Modifier = Modifier,
    filteredSearchItems: List<Searchable>,// CHECK IF STABLE LIST WILL BE MORE PERFORMANT HERE
    inSelectionMode: Boolean = false,
    selectedSearchItems: MutableList<Searchable> = mutableListOf(), // CHECK IF STABLE LIST WILL BE MORE PERFORMANT HERE
    selectItem: (Searchable) -> Unit = {},
    deselectItem: (Searchable) -> Unit = {},
    ListItemComposable: @Composable (index:Int, item: Searchable) -> Unit, // This is a placeholder for the ListItem composable, can be replaced with actual implementation
    border: BorderType = BorderType.NoBorder,
) {
    val scope = rememberCoroutineScope()
    var enableStatus by rememberSaveable { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val positionString: String = LocalContext.current.resources.getString(R.string.position_string)
    val statusString: String = LocalContext.current.resources.getString(R.string.status_string)
    LazyColumn(
        state = lazyListState, modifier = modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scope.launch {
                    lazyListState.scrollBy(-delta)
                }
            },
        )
    ) {
        itemsIndexed(items = filteredSearchItems, key = {index, item -> item.getUniqueId()}) { index, item ->  // Unique key for each item to ensure stable list updates, will prevent recomps
            var isSelectedLocal by remember { mutableStateOf(false) } // Local state to track selection for each item, DOES NOT UPDATE THE VIEWMODEL
            val listItemTokens: ListItemTokens = object : ListItemTokens() {
                @Composable
                override fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
                    return StateBrush(
                        rest = if (!isSelectedLocal) {
                            SolidColor(
                                FluentTheme.aliasTokens.neutralBackgroundColor[Background1].value(
                                    themeMode = FluentTheme.themeMode
                                )
                            )
                        } else {
                            SolidColor(
                                FluentTheme.aliasTokens.neutralBackgroundColor[Background1Selected].value(
                                    themeMode = FluentTheme.themeMode
                                )
                            )
                        },
                        pressed = SolidColor(
                            FluentTheme.aliasTokens.neutralBackgroundColor[Background1Pressed].value(
                                themeMode = FluentTheme.themeMode
                            )
                        )
                    )
                }

                @Composable
                override fun primaryTextTypography(listItemInfo: ListItemInfo): TextStyle {
                    return TextStyle(
                        color = Color(0xFF242424),
                        fontSize = 17.sp,
                        lineHeight = 22.sp,
                        letterSpacing = -0.43.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight(400)
                    )
                }
            } // ****** INSTEAD OF PASSING MULTIPLE VALUES, PASS A LISTITEM OBJECT HERE WHICH WILL CREATE THE LISTITEM, CAN PASS EVERYTHING LIKE INDEX AND POSITIONAL STRING INSIDE IT
            ListItemComposable(index = index, item = item) // I CAN ALSO ADD A DEPENDENCY ON FLUENTUI_LISTITEM INSIDE FLUENTUI_TOPAPPBARS
//            ListItem.Item(
//                text = item.title,
//                modifier = Modifier
//                    .clearAndSetSemantics {
//                        contentDescription =
//                            "${item.title}, ${item.subTitle}" + if (enableStatus) statusString.format(
//                                item.status
//                            ) else ""
//                        stateDescription = if (filteredSearchItems.size > 1) positionString.format(
//                            index + 1,
//                            filteredSearchItems.size
//                        ) else ""
//                        role = Role.Button
//                    },
//                subText = item.subTitle,
//                secondarySubText = item.footer,
//                onClick = {
//                    item.onClick
//                    if (inSelectionMode) { // CHANGE THE LOGIC TO CHECK IF ANY ITEM IS SELECTED OR NOT
//                        if (isSelectedLocal) {
//                            selectedSearchItems.remove(item)
//                            deselectItem(item)
//                            isSelectedLocal = false
//                        } else {
//                            selectedSearchItems.add(item)
//                            selectItem(item)
//                            isSelectedLocal = true
//                        }
//                    }
//                },
//                onLongClick = {
//                    item.onLongClick()
//                    if (isSelectedLocal) {
//                        selectedSearchItems.remove(item)
//                        deselectItem(item)
//                        isSelectedLocal = false
//                    } else {
//                        selectedSearchItems.add(item)
//                        selectItem(item)
//                        isSelectedLocal = true
//                    }
//                },
//                border = border,
//                //borderInset = borderInset,
//                listItemTokens = listItemTokens,
//                enabled = item.enabled,
//                leadingAccessoryContent = item.leftAccessory,
//                trailingAccessoryContent = item.rightAccessory,
//                //textAccessibilityProperties = textAccessibilityProperties,
//            )
        }
    }
}