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
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.PrimarySurfaceContent
import com.microsoft.fluentuidemo.util.getAndroidViewAsContent
import com.microsoft.fluentuidemo.util.getDrawerAsContent
import com.microsoft.fluentuidemo.util.getDynamicListGeneratorAsContent
import kotlinx.coroutines.launch


class V2DrawerActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-17"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-17"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateActivityUI()
        }
    }
}

enum class ContentType {
    FULL_SCREEN_SCROLLABLE_CONTENT,
    EXPANDABLE_SIZE_CONTENT,
    WRAPPED_SIZE_CONTENT
}

@Composable
private fun CreateActivityUI() {
    var scrimVisible by remember { mutableStateOf(true) }
    var dynamicSizeContent by remember { mutableStateOf(false) }
    var nestedDrawerContent by remember { mutableStateOf(false) }
    var listContent by remember { mutableStateOf(true) }
    var selectedContent by remember { mutableStateOf(ContentType.FULL_SCREEN_SCROLLABLE_CONTENT) }
    var selectedBehaviorType by remember { mutableStateOf(BehaviorType.BOTTOM_SLIDE_OVER) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
            selectedBehaviorType,
            if (listContent)
                getAndroidViewAsContent(selectedContent)
            else if (nestedDrawerContent) {
                getDrawerAsContent()
            } else {
                getDynamicListGeneratorAsContent()
            },
            scrimVisible = scrimVisible
        )
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                ListItem.Header(title = stringResource(id = R.string.drawer_select_drawer_type))
                ListItem.Item(text = stringResource(id = R.string.drawer_top),
                    subText = stringResource(id = R.string.drawer_top_description),
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.TOP },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.TOP
                            },
                            selected = selectedBehaviorType == BehaviorType.TOP
                        )
                    }
                )
                ListItem.Item(text = stringResource(id = R.string.drawer_bottom),
                    subText = stringResource(id = R.string.drawer_bottom_description),
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.BOTTOM },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.BOTTOM
                            },
                            selected = selectedBehaviorType == BehaviorType.BOTTOM
                        )
                    }
                )
                ListItem.Item(text = stringResource(id = R.string.drawer_left_slide_over),
                    subText = stringResource(id = R.string.drawer_left_slide_over_description),
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER
                            },
                            selected = selectedBehaviorType == BehaviorType.LEFT_SLIDE_OVER
                        )
                    }
                )
                ListItem.Item(text = stringResource(id = R.string.drawer_right_slide_over),
                    subText = stringResource(id = R.string.drawer_right_slide_over_description),
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER
                            },
                            selected = selectedBehaviorType == BehaviorType.RIGHT_SLIDE_OVER
                        )
                    }
                )
                ListItem.Item(text = stringResource(id = R.string.drawer_bottom_slide_over),
                    subText = stringResource(id = R.string.drawer_bottom_slide_over_description),
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER },
                    trailingAccessoryContent = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER
                            },
                            selected = selectedBehaviorType == BehaviorType.BOTTOM_SLIDE_OVER
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
    behaviorType: BehaviorType,
    drawerContent: @Composable ((() -> Unit) -> Unit),
    scrimVisible: Boolean = true
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()
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

    Drawer(
        drawerState = drawerState,
        drawerContent = { drawerContent(close) },
        behaviorType = behaviorType,
        scrimVisible = scrimVisible
    )
}