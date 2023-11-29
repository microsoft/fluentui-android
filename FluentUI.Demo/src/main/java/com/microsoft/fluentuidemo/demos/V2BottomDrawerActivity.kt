package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.BottomDrawer
import com.microsoft.fluentui.tokenized.drawer.rememberBottomDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.PrimarySurfaceContent
import com.microsoft.fluentuidemo.util.getAndroidViewAsContent
import com.microsoft.fluentuidemo.util.getDrawerAsContent
import com.microsoft.fluentuidemo.util.getDynamicListGeneratorAsContent
import kotlinx.coroutines.launch

class V2BottomDrawerActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-9"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-9"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent {
            CreateActivityUI()
        }
    }
}

@Composable
private fun CreateActivityUI() {
    var scrimVisible by remember { mutableStateOf(true) }
    var dynamicSizeContent by remember { mutableStateOf(false) }
    var nestedDrawerContent by remember { mutableStateOf(false) }
    var listContent by remember { mutableStateOf(true) }
    var expandable by remember { mutableStateOf(true) }
    var skipOpenState by remember { mutableStateOf(false) }
    var selectedContent by remember { mutableStateOf(ContentType.FULL_SCREEN_SCROLLABLE_CONTENT) }
    var slideOver by remember { mutableStateOf(false) }
    var showHandle by remember { mutableStateOf(true) }
    var preventDismissalOnScrimClick by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
            slideOver = slideOver,
            scrimVisible = scrimVisible,
            skipOpenState = skipOpenState,
            expandable = expandable,
            showHandle = showHandle,
            preventDismissalOnScrimClick = preventDismissalOnScrimClick,
            drawerContent =
            if (listContent)
                getAndroidViewAsContent(selectedContent)
            else if (nestedDrawerContent) {
                getDrawerAsContent()
            } else {
                getDynamicListGeneratorAsContent()
            }
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
    drawerContent: @Composable ((() -> Unit) -> Unit),
) {
    val scope = rememberCoroutineScope()

    val drawerState = rememberBottomDrawerState(expandable = expandable, skipOpenState = skipOpenState)

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
        preventDismissalOnScrimClick = preventDismissalOnScrimClick
    )
}



