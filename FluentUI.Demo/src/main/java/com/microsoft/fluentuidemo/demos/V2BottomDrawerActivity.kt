package com.microsoft.fluentuidemo.demos

import SearchViewModel
import SearchViewModelFactory
import Searchable
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1Pressed
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1Selected
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.ListItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.tokenized.KeyboardVisibilityObserver
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.BottomDrawer
import com.microsoft.fluentui.tokenized.drawer.DrawerValue
import com.microsoft.fluentui.tokenized.drawer.rememberBottomDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.util.getStringResource
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.PrimarySurfaceContent
import com.microsoft.fluentuidemo.util.getAndroidViewAsContent
import com.microsoft.fluentuidemo.util.getDrawerAsContent
import com.microsoft.fluentuidemo.util.getDynamicListGeneratorAsContent
import generateUniqueId
import kotlinx.coroutines.launch

class V2BottomDrawerActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-9"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-9"
    private val onBackCallback =
        object : OnBackPressedCallback(true) { //callback to end the activity
            override fun handleOnBackPressed() {
                finish()
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent {
            CreateActivityUI()
            LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(
                this,
                onBackCallback
            ) //registering the callback to end the activity when back button is pressed
        }
    }
}

@Composable
private fun CreateActivityUI() {
    var scrimVisible by rememberSaveable { mutableStateOf(true) }
    var dynamicSizeContent by rememberSaveable { mutableStateOf(false) }
    var nestedDrawerContent by rememberSaveable { mutableStateOf(false) }
    var listContent by rememberSaveable { mutableStateOf(true) }
    var searchableDrawerContent by rememberSaveable { mutableStateOf(false) }
    var expandable by rememberSaveable { mutableStateOf(true) }
    var skipOpenState by rememberSaveable { mutableStateOf(false) }
    var selectedContent by rememberSaveable { mutableStateOf(ContentType.FULL_SCREEN_SCROLLABLE_CONTENT) }
    var slideOver by rememberSaveable { mutableStateOf(false) }
    var showHandle by rememberSaveable { mutableStateOf(true) }
    var enableSwipeDismiss by rememberSaveable { mutableStateOf(true) }
    var maxLandscapeWidthFraction by rememberSaveable { mutableFloatStateOf(1F) }
    var preventDismissalOnScrimClick by rememberSaveable { mutableStateOf(false) }
    var isLandscapeOrientation: Boolean =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (searchableDrawerContent) {
            CreateSearchableDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                slideOver = slideOver,
                expandable = expandable,
                skipOpenState = skipOpenState,
                scrimVisible = scrimVisible,
                showHandle = showHandle,
                preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                enableSwipeDismiss = enableSwipeDismiss,
                maxLandscapeWidthFraction = maxLandscapeWidthFraction
            )
        } else {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                slideOver = slideOver,
                scrimVisible = scrimVisible,
                skipOpenState = skipOpenState,
                expandable = expandable,
                showHandle = showHandle,
                preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                enableSwipeDismiss = enableSwipeDismiss,
                maxLandscapeWidthFraction = maxLandscapeWidthFraction,
                drawerContent =
                    if (listContent)
                        getAndroidViewAsContent(selectedContent)
                    else if (nestedDrawerContent) {
                        getDrawerAsContent()
                    } else {
                        getDynamicListGeneratorAsContent()
                    }
            )
        }
        //Other content on Primary surface
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                ListItem.Header(title = stringResource(id = R.string.drawer_select_drawer_type))
                ListItem.Item(
                    text = stringResource(id = R.string.drawer_bottom),
                    subText = stringResource(id = R.string.drawer_bottom_description),
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { slideOver = false },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                slideOver = false
                            },
                            selected = !slideOver
                        )
                    }
                )
                ListItem.Item(
                    text = stringResource(id = R.string.drawer_bottom_slide_over),
                    subText = stringResource(id = R.string.drawer_bottom_slide_over_description),
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { slideOver = true },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                slideOver = true
                            },
                            selected = slideOver
                        )
                    }
                )
            }
            item {
                val scrimVisibleText = stringResource(id = R.string.drawer_scrim_visible)
                ListItem.Header(
                    title = scrimVisibleText, modifier = Modifier
                        .toggleable(
                            value = scrimVisible,
                            role = Role.Switch,
                            onValueChange = { scrimVisible = !scrimVisible }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = scrimVisibleText
                        }, trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = { scrimVisible = !scrimVisible },
                            checkedState = scrimVisible
                        )
                    }
                )
            }
            item {
                val expandableText = stringResource(id = R.string.drawer_expandable)
                ListItem.Header(
                    title = expandableText,
                    modifier = Modifier
                        .toggleable(
                            value = expandable,
                            role = Role.Switch,
                            onValueChange = {
                                expandable = it
                                if (!it) {
                                    skipOpenState = false
                                }
                            }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = expandableText
                        },
                    enabled = !skipOpenState,
                    trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = {
                                expandable = it
                                if (!it) {
                                    skipOpenState = false
                                }
                            },
                            checkedState = expandable,
                            enabledSwitch = !skipOpenState
                        )
                    }
                )
            }
            item {
                val skipOpenStateText = stringResource(id = R.string.skip_open_state)
                ListItem.Header(
                    title = skipOpenStateText,
                    modifier = Modifier
                        .toggleable(
                            value = skipOpenState,
                            role = Role.Switch,
                            onValueChange = {
                                skipOpenState = it
                                if (it) {
                                    expandable = true
                                }
                            }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = skipOpenStateText
                        },
                    enabled = expandable,
                    trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = {
                                skipOpenState = it
                                if (it) {
                                    expandable = true
                                }
                            },
                            checkedState = skipOpenState,
                            enabledSwitch = expandable
                        )
                    }
                )
            }
            item {
                val preventDismissalOnScrimClickText =
                    stringResource(id = R.string.prevent_scrim_click_dismissal)
                ListItem.Header(
                    title = preventDismissalOnScrimClickText,
                    modifier = Modifier
                        .toggleable(
                            value = preventDismissalOnScrimClick,
                            role = Role.Switch,
                            onValueChange = {
                                preventDismissalOnScrimClick = !preventDismissalOnScrimClick
                            }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = preventDismissalOnScrimClickText
                        },
                    trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = {
                                preventDismissalOnScrimClick = !preventDismissalOnScrimClick
                            },
                            checkedState = preventDismissalOnScrimClick
                        )
                    }
                )
            }
            item {
                val showHandleText = stringResource(id = R.string.drawer_show_handle)
                ListItem.Header(
                    title = showHandleText,
                    modifier = Modifier
                        .toggleable(
                            value = showHandle,
                            role = Role.Switch,
                            onValueChange = { showHandle = !showHandle }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = showHandleText
                        },
                    trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = { showHandle = it },
                            checkedState = showHandle,
                        )
                    }
                )
            }
            item {
                val showDismissText = stringResource(id = R.string.drawer_enable_swipe_dismiss)
                ListItem.Header(
                    title = showDismissText,
                    modifier = Modifier
                        .toggleable(
                            value = enableSwipeDismiss,
                            role = Role.Switch,
                            onValueChange = { enableSwipeDismiss = !enableSwipeDismiss }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = showDismissText
                        },
                    trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = { enableSwipeDismiss = it },
                            checkedState = enableSwipeDismiss,
                        )
                    }
                )
            }

            item {
                val maxLandscapeWidthFractionText =
                    stringResource(id = R.string.bottom_drawer_max_width_landscape)
                ListItem.Header(
                    title = maxLandscapeWidthFractionText + if (!isLandscapeOrientation) " (Rotate to landscape Mode to use this)" else "",
                    titleMaxLines = 2,
                    enabled = isLandscapeOrientation,
                    modifier = Modifier
                        .clearAndSetSemantics {
                            this.contentDescription = maxLandscapeWidthFractionText
                        },
                )
                Slider(
                    value = maxLandscapeWidthFraction,
                    onValueChange = { maxLandscapeWidthFraction = it },
                    valueRange = 0F..1F,
                    enabled = isLandscapeOrientation,
                    colors = SliderDefaults.colors(
                        thumbColor = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            FluentTheme.themeMode
                        ),
                        activeTrackColor = FluentTheme.aliasTokens.brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        inactiveTrackColor = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                            FluentTheme.themeMode
                        ),
                        disabledThumbColor = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                            FluentTheme.themeMode
                        ),
                        disabledActiveTrackColor = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                            FluentTheme.themeMode
                        ),
                        disabledInactiveTrackColor = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                            FluentTheme.themeMode
                        )
                    ),
                    steps = 10
                )
            }
            item {
                ListItem.Header(title = stringResource(id = R.string.drawer_select_drawer_content))
                ListItem.Item(
                    text = stringResource(id = R.string.drawer_full_screen_size_scrollable_content),
                    onClick = {
                        selectedContent = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
                        listContent = true
                        nestedDrawerContent = false
                        dynamicSizeContent = false
                        searchableDrawerContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedContent = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
                                listContent = true
                                nestedDrawerContent = false
                                dynamicSizeContent = false
                                searchableDrawerContent = false
                            },
                            selected = selectedContent == ContentType.FULL_SCREEN_SCROLLABLE_CONTENT && listContent
                        )
                    }
                )
                ListItem.Item(
                    text = stringResource(id = R.string.drawer_more_than_half_screen_content),
                    onClick = {
                        selectedContent = ContentType.EXPANDABLE_SIZE_CONTENT
                        listContent = true
                        nestedDrawerContent = false
                        dynamicSizeContent = false
                        searchableDrawerContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedContent = ContentType.EXPANDABLE_SIZE_CONTENT
                                listContent = true
                                nestedDrawerContent = false
                                dynamicSizeContent = false
                                searchableDrawerContent = false
                            },
                            selected = selectedContent == ContentType.EXPANDABLE_SIZE_CONTENT && listContent
                        )
                    }
                )
                ListItem.Item(
                    text = stringResource(id = R.string.drawer_less_than_half_screen_content),
                    onClick = {
                        selectedContent = ContentType.WRAPPED_SIZE_CONTENT
                        listContent = true
                        dynamicSizeContent = false
                        nestedDrawerContent = false
                        searchableDrawerContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedContent = ContentType.WRAPPED_SIZE_CONTENT
                                listContent = true
                                dynamicSizeContent = false
                                nestedDrawerContent = false
                                searchableDrawerContent = false
                            },
                            selected = selectedContent == ContentType.WRAPPED_SIZE_CONTENT && listContent
                        )
                    }
                )
                ListItem.Item(
                    text = stringResource(id = R.string.drawer_dynamic_size_content),
                    onClick = {
                        dynamicSizeContent = true
                        nestedDrawerContent = false
                        listContent = false
                        searchableDrawerContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                dynamicSizeContent = true
                                nestedDrawerContent = false
                                listContent = false
                                searchableDrawerContent = false
                            },
                            selected = dynamicSizeContent
                        )
                    }
                )
                ListItem.Item(
                    text = stringResource(id = R.string.drawer_nested_drawer_content),
                    onClick = {
                        nestedDrawerContent = true
                        dynamicSizeContent = false
                        listContent = false
                        searchableDrawerContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                nestedDrawerContent = true
                                dynamicSizeContent = false
                                listContent = false
                                searchableDrawerContent = false
                            },
                            selected = nestedDrawerContent
                        )
                    }
                )
                ListItem.Item(
                    text = "Searchable Drawer Content",//stringResource(id = R.string.searchable_drawer_content),
                    onClick = {
                        dynamicSizeContent = false
                        nestedDrawerContent = false
                        listContent = false
                        searchableDrawerContent = true
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                dynamicSizeContent = false
                                nestedDrawerContent = false
                                listContent = false
                                searchableDrawerContent = true
                            },
                            selected = searchableDrawerContent
                        )
                    }
                )
            }
        }
    }
}

data class SearchableItem(
    val title: String,
    val subTitle: String? = null,
    val description: String? = null,
    val footer: String? = null,
    val leftAccessory: @Composable (() -> Unit)? = null,
    val rightAccessory: @Composable (() -> Unit)? = null,
    val status: AvatarStatus? = null,
    val onClick: () -> Unit = {},
    val onLongClick: () -> Unit = {},
    val enabled: Boolean = true,
    val id: Any = generateUniqueId()
) : Searchable {
    override fun getSearchKey(): String = title

    override fun getUniqueId(): Any = id
}

@Composable
private fun CreateSearchableDrawerWithButtonOnPrimarySurfaceToInvokeIt(
    slideOver: Boolean,
    expandable: Boolean,
    skipOpenState: Boolean,
    scrimVisible: Boolean,
    showHandle: Boolean,
    preventDismissalOnScrimClick: Boolean,
    enableSwipeDismiss: Boolean,
    maxLandscapeWidthFraction: Float
) {
    val scope = rememberCoroutineScope()
    val viewModel: SearchViewModel<SearchableItem> = viewModel(
        factory = SearchViewModelFactory(initialItems = List(100) { index ->
            SearchableItem(
                title = "Item $index",
                subTitle = "Subtitle for item $index",
                description = "Description for item $index",
                id = index
            )
        })
    )
    val drawerState = rememberBottomDrawerState(
        initialValue = DrawerValue.Closed,
        expandable = expandable,
        skipOpenState = skipOpenState
    )
    val open: () -> Unit = {
        scope.launch {
            viewModel.clearSelection()
            drawerState.open()
        }
    }
    val expand: () -> Unit = {
        scope.launch {
            drawerState.expand()
        }
    }
    val close: () -> Unit = {
        scope.launch {
            drawerState.close()
            viewModel.clearSelection()
        }
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val toggleItemSelection = { item: Searchable ->
        viewModel.toggleSelection(item as SearchableItem)
    }
    Row {
        PrimarySurfaceContent(
            open,
            text = stringResource(id = R.string.drawer_open)
        )
        Spacer(modifier = Modifier.width(10.dp))
        PrimarySurfaceContent(
            expand,
            text = stringResource(id = R.string.drawer_expand)
        )
    }
    BottomDrawer(
        drawerState = drawerState,
        drawerContent = {
            KeyboardVisibilityObserver(
                onKeyboardVisible = {
                    if (drawerState.currentValue == DrawerValue.Open) {
                        expand()
                    }
                },
                onKeyboardHidden = {
                    if (drawerState.currentValue == DrawerValue.Expanded) {
                        open()
                    }
                }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SearchableDrawerHeader(
                        onLeftTextClick = {
                            scope.launch {
                                viewModel.clearSelection()
                            }
                        },
                        onRightTextClick = close,
                        onCenterTextClick = {
                            if (drawerState.currentValue == DrawerValue.Open) {
                                expand()
                            } else {
                                open()
                            }
                        }
                    )

                    if (uiState.selectionSize <= 0) {
                        SearchBar(
                            onValueChange = { query, selectedPerson ->
                                scope.launch {
                                    viewModel.onQueryChanged(query)
                                }
                            }
                        )
                    } else {
                        MultiSelectScreen(uiState.selectionSize)
                    }

                    LazyItemsList(
                        filteredSearchItems = uiState.filteredItems,
                        selectedSearchItems = uiState.selectedItems,
                        inSelectionMode = uiState.selectionSize > 0,
                        toggleItemSelection = toggleItemSelection,
                        border = BorderType.NoBorder,
                        modifier = Modifier,
                    )
                }
            }
        },
        scrimVisible = scrimVisible,
        slideOver = slideOver,
        showHandle = showHandle,
        enableSwipeDismiss = enableSwipeDismiss,
        maxLandscapeWidthFraction = maxLandscapeWidthFraction,
        preventDismissalOnScrimClick = preventDismissalOnScrimClick
    )
}

@Composable
private fun ClickableTextHeader(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    textStyle: TextStyle = TextStyle.Default
) {
    val interactionSource = remember { MutableInteractionSource() }
    val animatedFontSizeStart by animateDpAsState(
        targetValue = if (interactionSource.collectIsPressedAsState().value) 18.5.dp else 17.dp,
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
                    interactionSource = interactionSource
                ) {
                    onClick()
                },
            style = textStyle.merge(fontSize = animatedFontSizeStart.value.sp)
        )
    }
}

@Composable
fun SearchableDrawerHeader(
    onLeftTextClick: () -> Unit = {},
    onCenterTextClick: () -> Unit = {},
    onRightTextClick: () -> Unit = {},
) {
    val textColours = listOf(Color(0xFF616161), Color(0xFF242424), Color(0xFF464FEB))
    val textHeaders = listOf(
        getStringResource(id = R.string.fluentui_clear_text),
        getStringResource(id = R.string.fluentui_title),
        getStringResource(id = R.string.popup_menu_item_share)
    )
    val textOnClicks = listOf(onLeftTextClick, onCenterTextClick, onRightTextClick)
    val textFontStyles = listOf(
        FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1],
        FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2],
        FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
    )
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
                textStyle = textFontStyles.get(i).copy(
                    color = textColours.get(i),
                    textAlign = textAlignments.get(i)
                )
            )
        }
    }
}

@Composable
private fun MultiSelectScreen(numSelected: Int) {
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

@Composable
fun LazyItemsList(
    filteredSearchItems: List<SearchableItem>,// CHECK IF STABLE LIST WILL BE MORE PERFORMANT HERE
    selectedSearchItems: Set<SearchableItem> = setOf(), // CHECK IF STABLE LIST WILL BE MORE PERFORMANT HERE
    inSelectionMode: Boolean = false,
    toggleItemSelection: (SearchableItem) -> Unit = {},
    border: BorderType = BorderType.NoBorder,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    var enableStatus by rememberSaveable { mutableStateOf(false) }
    val lazyListState = rememberLazyListState()
    val positionString: String =
        getStringResource(com.microsoft.fluentui.topappbars.R.string.position_string)
    val statusString: String =
        getStringResource(com.microsoft.fluentui.topappbars.R.string.status_string)
    val listItemTokens: ListItemTokens = object : ListItemTokens() {
        @Composable
        override fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
            return StateBrush(
                rest = SolidColor(
                    FluentTheme.aliasTokens.neutralBackgroundColor[Background1].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                pressed = SolidColor(
                    FluentTheme.aliasTokens.neutralBackgroundColor[Background1Pressed].value(
                        themeMode = FluentTheme.themeMode
                    )
                ),
                selected = SolidColor(
                    FluentTheme.aliasTokens.neutralBackgroundColor[Background1Selected].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            )
        }
    }
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
        itemsIndexed(
            items = filteredSearchItems,
            key = { index, item -> item.getUniqueId() }) { index, item ->  // ensure stable render updates, will prevent recomps
            val isSelected = selectedSearchItems.contains(item)
            ListItem.Item(
                text = item.title,
                modifier = Modifier
                    .clearAndSetSemantics {
                        contentDescription =
                            "${item.title}, ${item.subTitle}" + if (enableStatus) statusString.format(
                                item.status
                            ) else ""
                        stateDescription = if (filteredSearchItems.size > 1) positionString.format(
                            index + 1,
                            filteredSearchItems.size
                        ) else ""
                        role = Role.Button
                    },
                subText = item.subTitle,
                secondarySubText = item.footer,
                onClick = {
                    if (inSelectionMode) {
                        toggleItemSelection(item)
                    } else {
                        item.onClick()
                    }
                },
                onLongClick = {
                    item.onLongClick()
                    toggleItemSelection(item)
                },
                border = border,
                listItemTokens = listItemTokens,
                enabled = item.enabled,
                selected = isSelected,
                leadingAccessoryContent = item.leftAccessory,
                trailingAccessoryContent = item.rightAccessory,
            )
        }
    }
}

@Composable
private fun CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
    slideOver: Boolean,
    expandable: Boolean,
    skipOpenState: Boolean,
    scrimVisible: Boolean,
    showHandle: Boolean,
    preventDismissalOnScrimClick: Boolean,
    enableSwipeDismiss: Boolean,
    maxLandscapeWidthFraction: Float,
    drawerContent: @Composable ((() -> Unit) -> Unit),
) {
    val scope = rememberCoroutineScope()

    val drawerState = rememberBottomDrawerState(
        initialValue = DrawerValue.Closed,
        expandable = expandable,
        skipOpenState = skipOpenState
    )

    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    val expand: () -> Unit = {
        scope.launch { drawerState.expand() }
    }
    val close: () -> Unit = {
        scope.launch { drawerState.close() }
    }
    Row {
        PrimarySurfaceContent(
            open,
            text = stringResource(id = R.string.drawer_open)
        )
        Spacer(modifier = Modifier.width(10.dp))
        PrimarySurfaceContent(
            expand,
            text = stringResource(id = R.string.drawer_expand)
        )
    }

    BottomDrawer(
        drawerState = drawerState,
        drawerContent = { drawerContent(close) },
        scrimVisible = scrimVisible,
        slideOver = slideOver,
        showHandle = showHandle,
        enableSwipeDismiss = enableSwipeDismiss,
        maxLandscapeWidthFraction = maxLandscapeWidthFraction,
        preventDismissalOnScrimClick = preventDismissalOnScrimClick
    )
}



