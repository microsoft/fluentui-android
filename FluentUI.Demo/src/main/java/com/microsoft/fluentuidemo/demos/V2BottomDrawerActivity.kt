package com.microsoft.fluentuidemo.demos

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarTokens
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.BottomDrawer
import com.microsoft.fluentui.tokenized.drawer.BottomDrawerMain
import com.microsoft.fluentui.tokenized.drawer.BottomDrawerSearchableList
import com.microsoft.fluentui.tokenized.drawer.DrawerValue
import com.microsoft.fluentui.tokenized.drawer.rememberBottomDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentui.tokenized.persona.PersonaList
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.PrimarySurfaceContent
import com.microsoft.fluentuidemo.util.getDrawerAsContent
import com.microsoft.fluentuidemo.util.getDynamicListGeneratorAsContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class V2BottomDrawerActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-9"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-9"
    private val onBackCallback = object: OnBackPressedCallback(true) { //callback to end the activity
        override fun handleOnBackPressed() {
            finish()
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent {
            BottomDrawerMain()
            //CreateActivityUI()
            //LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(this, onBackCallback) //registering the callback to end the activity when back button is pressed
        }
    }
}

@Composable
private fun searchItemActivity(): @Composable ((close: () -> Unit, expand: () -> Unit, open: () -> Unit) -> Unit) {
    return { close, expand, open ->
        val imeInsets = WindowInsets.ime
        val density = LocalDensity.current
        val isKeyboardVisible by remember {
            derivedStateOf {
                imeInsets.getBottom(density) > 0
            }
        }
        LaunchedEffect(isKeyboardVisible) {
            if (isKeyboardVisible) {
                expand()
            } else {
                open()
            }
        }
        var autoCorrectEnabled: Boolean by rememberSaveable { mutableStateOf(false) }
        var searchBarStyle: FluentStyle by rememberSaveable { mutableStateOf(FluentStyle.Neutral) }
        var induceDelay: Boolean by rememberSaveable { mutableStateOf(false) }
        var selectedPeople: Person? by rememberSaveable { mutableStateOf(null) }
        val listofPeople = listOf(
            Person(
                "Allan", "Munger",
                image = R.drawable.avatar_allan_munger,
                isActive = true
            ),
            Person(
                "Amanda", "Brady",
                isActive = false, status = AvatarStatus.Offline
            ),
//            Person(
//                "Abhay", "Singh",
//                isActive = true, status = AvatarStatus.DND, isOOO = true
//            ),
//            Person(
//                "Carlos", "Slathery",
//                isActive = false, status = AvatarStatus.Busy, isOOO = true
//            ),
//            Person(
//                "Celeste", "Burton",
//                image = R.drawable.avatar_celeste_burton,
//                isActive = true, status = AvatarStatus.Away
//            ),
//            Person(
//                "Ankit", "Gupta",
//                isActive = true, status = AvatarStatus.Unknown
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),
//            Person(
//                "Miguel", "Garcia",
//                image = R.drawable.avatar_miguel_garcia,
//                isActive = true, status = AvatarStatus.Blocked
//            ),

            )
        var filteredPeople by rememberSaveable { mutableStateOf(listofPeople.toMutableList()) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val scope = rememberCoroutineScope()
            var loading by rememberSaveable { mutableStateOf(false) }
            val keyboardController = LocalSoftwareKeyboardController.current
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val interactionSourceStart = remember { MutableInteractionSource() }
                val animatedFontSizeStart by animateDpAsState(
                    targetValue = if (interactionSourceStart.collectIsPressedAsState().value) 18.5.dp else 17.dp,
                    label = "FontSizeAnimation"
                )
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    BasicText(
                        text = "Clear",
                        modifier = Modifier.fillMaxWidth().clickable(
                            enabled = true,
                            indication = null,
                            interactionSource = interactionSourceStart
                        ) {
                            close()
                        },
                        style = TextStyle(
                            color = Color(0xFF616161),
                            fontSize = animatedFontSizeStart.value.sp,
                            lineHeight = 22.sp,
                            letterSpacing = -0.43.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight(400)
                        )
                    )
                }
                val interactionSourceCenter = remember { MutableInteractionSource() }
                val animatedFontSizeCenter by animateDpAsState(
                    targetValue = if (interactionSourceCenter.collectIsPressedAsState().value) 18.5.dp else 17.dp,
                    label = "FontSizeAnimation"
                )
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    BasicText(
                        text = "Person",
                        modifier = Modifier.fillMaxWidth().clickable(
                            enabled = true,
                            indication = null,
                            interactionSource = interactionSourceCenter
                        ) {
                            close()
                        },
                        style = TextStyle(
                            color = Color(0xFF242424),
                            fontSize = animatedFontSizeCenter.value.sp,
                            lineHeight = 22.sp,
                            letterSpacing = -0.43.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(600)
                        )
                    )
                }
                val interactionSourceEnd = remember { MutableInteractionSource() }
                val animatedFontSizeEnd by animateDpAsState(
                    targetValue = if (interactionSourceEnd.collectIsPressedAsState().value) 18.5.dp else 17.dp,
                    label = "FontSizeAnimation"
                )
                Box(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) {
                    BasicText(
                        text = "Apply",
                        modifier = Modifier.fillMaxWidth().clickable(
                            enabled = true,
                            indication = null,
                            interactionSource = interactionSourceEnd
                        ) {
                            close()
                        },
                        style = TextStyle(
                            color = Color(0xFF464FEB),
                            fontSize = animatedFontSizeEnd.value.sp,
                            lineHeight = 22.sp,
                            letterSpacing = -0.43.sp,
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight(400)
                        )
                    )
                }
            }
            SearchBar(
                onValueChange = { query, selectedPerson ->
                    scope.launch {
                        loading = true

                        if (induceDelay)
                            delay(2000)

                        filteredPeople = listofPeople.filter {
                            it.firstName.lowercase().contains(query.lowercase()) ||
                                    it.lastName.lowercase().contains(query.lowercase())
                        } as MutableList<Person>
                        selectedPeople = selectedPerson

                        loading = false
                    }
                },
                style = searchBarStyle,
                loading = loading,
                selectedPerson = selectedPeople,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = autoCorrectEnabled,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                    }
                ),
                searchBarTokens = object : SearchBarTokens() {
                    @Composable
                    override fun cornerRadius(searchBarInfo: SearchBarInfo): Dp {
                        return FluentGlobalTokens.CornerRadiusTokens.CornerRadius120.value
                    }
                },
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                searchHint = "Filter by person"
            )

            val filteredPersona = mutableListOf<Persona>()
            val selectedPeopleList = mutableListOf<Boolean>()
            filteredPeople.forEach {
                var isSelected by remember { mutableStateOf(false) }
                filteredPersona.add(
                    Persona(
                        it,
                        "${it.firstName} ${it.lastName}",
                        subTitle = it.email,
                        onClick = { selectedPeople = it },
                        onLongClick = {
                            isSelected = !isSelected
                        },
                        trailingIcon = {
                            if(isSelected) {
                                Icon(FluentIcon(Icons.Outlined.Check))
                            }
                        }
                    )
                )
                selectedPeopleList.add(isSelected)
            }
            PersonaList(
                personas = filteredPersona,
                border = BorderType.NoBorder
            )
        }
    }
}

@Composable
private fun CreateActivityUI() {
    var scrimVisible by rememberSaveable { mutableStateOf(true) }
    var dynamicSizeContent by rememberSaveable { mutableStateOf(false) }
    var nestedDrawerContent by rememberSaveable { mutableStateOf(false) }
    var listContent by rememberSaveable { mutableStateOf(true) }
    var expandable by rememberSaveable { mutableStateOf(true) }
    var skipOpenState by rememberSaveable { mutableStateOf(false) }
    var selectedContent by rememberSaveable { mutableStateOf(ContentType.FULL_SCREEN_SCROLLABLE_CONTENT) }
    var slideOver by rememberSaveable { mutableStateOf(false) }
    var showHandle by rememberSaveable { mutableStateOf(true) }
    var enableSwipeDismiss by rememberSaveable { mutableStateOf(true) }
    var maxLandscapeWidthFraction by rememberSaveable { mutableFloatStateOf(1F) }
    var preventDismissalOnScrimClick by rememberSaveable { mutableStateOf(false) }
    var isLandscapeOrientation: Boolean = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
            slideOver = slideOver,
            scrimVisible = scrimVisible,
            skipOpenState = skipOpenState,
            expandable = expandable,
            showHandle = showHandle,
            preventDismissalOnScrimClick = preventDismissalOnScrimClick,
            enableSwipeDismiss = enableSwipeDismiss,
            maxLandscapeWidthFraction = maxLandscapeWidthFraction,
            drawerContent = searchItemActivity()
        )
        //Other content on Primary surface
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                ListItem.Header(title = stringResource(id = R.string.drawer_select_drawer_type))
                ListItem.Item(text = stringResource(id = R.string.drawer_bottom),
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
                ListItem.Item(text = stringResource(id = R.string.drawer_bottom_slide_over),
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
                ListItem.Header(title = scrimVisibleText, modifier = Modifier
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
                ListItem.Header(title = expandableText,
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
                                if(!it) {
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
                ListItem.Header(title = skipOpenStateText,
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
                val preventDismissalOnScrimClickText = stringResource(id = R.string.prevent_scrim_click_dismissal)
                ListItem.Header(title = preventDismissalOnScrimClickText,
                    modifier = Modifier
                        .toggleable(
                            value = preventDismissalOnScrimClick,
                            role = Role.Switch,
                            onValueChange = { preventDismissalOnScrimClick = !preventDismissalOnScrimClick }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = preventDismissalOnScrimClickText
                        },
                    trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = { preventDismissalOnScrimClick = !preventDismissalOnScrimClick },
                            checkedState = preventDismissalOnScrimClick
                        )
                    }
                )
            }
            item {
                val showHandleText = stringResource(id = R.string.drawer_show_handle)
                ListItem.Header(title = showHandleText,
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
                ListItem.Header(title = showDismissText,
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
                val maxLandscapeWidthFractionText = stringResource(id = R.string.bottom_drawer_max_width_landscape)
                ListItem.Header(title = maxLandscapeWidthFractionText + if(!isLandscapeOrientation) " (Rotate to landscape Mode to use this)" else "",
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
                ListItem.Item(text = stringResource(id = R.string.drawer_full_screen_size_scrollable_content),
                    onClick = {
                        selectedContent = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
                        listContent = true
                        nestedDrawerContent = false
                        dynamicSizeContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedContent = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
                                listContent = true
                                nestedDrawerContent = false
                                dynamicSizeContent = false
                            },
                            selected = selectedContent == ContentType.FULL_SCREEN_SCROLLABLE_CONTENT && listContent
                        )
                    }
                )
                ListItem.Item(text = stringResource(id = R.string.drawer_more_than_half_screen_content),
                    onClick = {
                        selectedContent = ContentType.EXPANDABLE_SIZE_CONTENT
                        listContent = true
                        nestedDrawerContent = false
                        dynamicSizeContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedContent = ContentType.EXPANDABLE_SIZE_CONTENT
                                listContent = true
                                nestedDrawerContent = false
                                dynamicSizeContent = false
                            },
                            selected = selectedContent == ContentType.EXPANDABLE_SIZE_CONTENT && listContent
                        )
                    }
                )
                ListItem.Item(text = stringResource(id = R.string.drawer_less_than_half_screen_content),
                    onClick = {
                        selectedContent = ContentType.WRAPPED_SIZE_CONTENT
                        listContent = true
                        dynamicSizeContent = false
                        nestedDrawerContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedContent = ContentType.WRAPPED_SIZE_CONTENT
                                listContent = true
                                dynamicSizeContent = false
                                nestedDrawerContent = false
                            },
                            selected = selectedContent == ContentType.WRAPPED_SIZE_CONTENT && listContent
                        )
                    }
                )
                ListItem.Item(text = stringResource(id = R.string.drawer_dynamic_size_content),
                    onClick = {
                        dynamicSizeContent = true
                        nestedDrawerContent = false
                        listContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                dynamicSizeContent = true
                                nestedDrawerContent = false
                                listContent = false
                            },
                            selected = dynamicSizeContent
                        )
                    }
                )
                ListItem.Item(text = stringResource(id = R.string.drawer_nested_drawer_content),
                    onClick = {
                        nestedDrawerContent = true
                        dynamicSizeContent = false
                        listContent = false
                    },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                nestedDrawerContent = true
                                dynamicSizeContent = false
                                listContent = false
                            },
                            selected = nestedDrawerContent
                        )
                    }
                )
            }
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
    drawerContent: @Composable ((() -> Unit, ()->Unit, ()->Unit) -> Unit),
) {
    val scope = rememberCoroutineScope()

    val drawerState = rememberBottomDrawerState(initialValue = DrawerValue.Closed, expandable = expandable, skipOpenState = skipOpenState)

    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    val expand: () -> Unit = {
        scope.launch {
         //   delay(50)
            drawerState.expand()
        }
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
        drawerContent = { drawerContent(close, expand, open) },
        scrimVisible = scrimVisible,
        slideOver = slideOver,
        showHandle = showHandle,
        enableSwipeDismiss = enableSwipeDismiss,
        maxLandscapeWidthFraction = maxLandscapeWidthFraction,
        preventDismissalOnScrimClick = preventDismissalOnScrimClick
    )
}



