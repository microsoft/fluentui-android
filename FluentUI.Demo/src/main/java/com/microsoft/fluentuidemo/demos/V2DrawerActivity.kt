package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.fluentui.persona.IPersona
import com.microsoft.fluentui.persona.PersonaListView
import com.microsoft.fluentui.theme.AppThemeController
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.util.activity
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import com.microsoft.fluentuidemo.util.createPersonaList
import kotlinx.coroutines.launch


class V2DrawerActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        v2ActivityComposeBinding.composeHere.setContent {
            FluentTheme {
                CreateActivityUI()
            }
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
    var expandable by remember { mutableStateOf(true) }
    var selectedContent by remember { mutableStateOf(ContentType.FULL_SCREEN_SCROLLABLE_CONTENT) }
    var selectedBehaviorType by remember { mutableStateOf(BehaviorType.BOTTOM_SLIDE_OVER) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
            "Open Drawer",
            selectedBehaviorType,
            if (listContent)
                getDrawerContent(selectedContent)
            else if (nestedDrawerContent) {
                getDrawerInDrawerContent()
            } else {
                getDynamicDrawerContent()
            },
            scrimVisible = scrimVisible,
            expandable = expandable
        )
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                ListItem.Header(title = "Select Drawer Type")
                ListItem.Item(text = "Top",
                    subText = "Whole drawer shows in the visible region.",
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.TOP },
                    trailingAccessoryView = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.TOP
                            },
                            selected = selectedBehaviorType == BehaviorType.TOP
                        )
                    }
                )
                ListItem.Item(text = "Bottom",
                    subText = "Whole drawer shows in the visible region. Swipe up motion scroll content. Expandable drawer expand via drag handle.",
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.BOTTOM },
                    trailingAccessoryView = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.BOTTOM
                            },
                            selected = selectedBehaviorType == BehaviorType.BOTTOM
                        )
                    }
                )
                ListItem.Item(text = "Left Slide Over",
                    subText = "Drawer slide over to visible region from left side.",
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER },
                    trailingAccessoryView = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER
                            },
                            selected = selectedBehaviorType == BehaviorType.LEFT_SLIDE_OVER
                        )
                    }
                )
                ListItem.Item(text = "Right Slide Over",
                    subText = "Drawer slide over to visible region from right side.",
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER },
                    trailingAccessoryView = {
                        RadioButton(
                            onClick = {
                                selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER
                            },
                            selected = selectedBehaviorType == BehaviorType.RIGHT_SLIDE_OVER
                        )
                    }
                )
                ListItem.Item(text = "Bottom Slide Over",
                    subText = "Drawer slide over to visible region from bottom of screen. Swipe up motion on expandable drawer bring its rest of part to visible region & then scroll.",
                    subTextMaxLines = Int.MAX_VALUE,
                    onClick = { selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER },
                    trailingAccessoryView = {
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
                ListItem.Header(title = "Scrim Visible", modifier = Modifier
                    .toggleable(
                        value = scrimVisible,
                        role = Role.Switch,
                        onValueChange = { scrimVisible = !scrimVisible }
                    )
                    .clearAndSetSemantics {
                        this.contentDescription = "Scrim Visible"
                    }, trailingAccessoryView = {
                    ToggleSwitch(
                        onValueChange = { scrimVisible = !scrimVisible },
                        checkedState = scrimVisible
                    )
                }
                )
            }
            item {
                ListItem.Header(title = "Expandable Bottom Drawer",
                    enabled = selectedBehaviorType == BehaviorType.BOTTOM_SLIDE_OVER || selectedBehaviorType == BehaviorType.BOTTOM,
                    modifier = Modifier
                        .toggleable(
                            value = expandable,
                            role = Role.Switch,
                            enabled = selectedBehaviorType == BehaviorType.BOTTOM_SLIDE_OVER || selectedBehaviorType == BehaviorType.BOTTOM,
                            onValueChange = { expandable = !expandable }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = "Expandable Bottom Drawer"
                        },
                    trailingAccessoryView = {
                        ToggleSwitch(
                            onValueChange = { expandable = it },
                            checkedState = expandable,
                            enabledSwitch = selectedBehaviorType == BehaviorType.BOTTOM_SLIDE_OVER || selectedBehaviorType == BehaviorType.BOTTOM
                        )
                    }
                )
            }
            item {
                ListItem.Header(title = "Select Drawer Content")
                ListItem.Item(text = "Full screen size scrollable content",
                    onClick = {
                        selectedContent = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
                        listContent = true
                        nestedDrawerContent = false
                        dynamicSizeContent = false
                    },
                    trailingAccessoryView = {
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
                ListItem.Item(text = "More than half screen content",
                    onClick = {
                        selectedContent = ContentType.EXPANDABLE_SIZE_CONTENT
                        listContent = true
                        nestedDrawerContent = false
                        dynamicSizeContent = false
                    },
                    trailingAccessoryView = {
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
                ListItem.Item(text = "Less than half screen content",
                    onClick = {
                        selectedContent = ContentType.WRAPPED_SIZE_CONTENT
                        listContent = true
                        dynamicSizeContent = false
                        nestedDrawerContent = false
                    },
                    trailingAccessoryView = {
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
                ListItem.Item(text = "Dynamic size content",
                    onClick = {
                        dynamicSizeContent = true
                        nestedDrawerContent = false
                        listContent = false
                    },
                    trailingAccessoryView = {
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
                ListItem.Item(text = "Nested drawer content",
                    onClick = {
                        nestedDrawerContent = true
                        dynamicSizeContent = false
                        listContent = false
                    },
                    trailingAccessoryView = {
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
    primaryScreenButtonText: String,
    behaviorType: BehaviorType,
    drawerContent: @Composable ((() -> Unit) -> Unit),
    expandable: Boolean = true,
    scrimVisible: Boolean = true
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()
    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    val close: () -> Unit = {
        scope.launch { drawerState.close() }
    }
    PrimarySurfaceContent(
        open,
        text = primaryScreenButtonText
    )
    Drawer(
        drawerState = drawerState,
        drawerContent = { drawerContent(close) },
        behaviorType = behaviorType,
        expandable = expandable,
        scrimVisible = scrimVisible
    )
}

@Composable
private fun PrimarySurfaceContent(
    onClick: () -> Unit,
    text: String,
    height: Dp = 20.dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(height))
        com.microsoft.fluentui.tokenized.controls.Button(
            style = ButtonStyle.Button,
            size = ButtonSize.Medium,
            text = text,
            onClick = onClick
        )
    }
}

@Composable
private fun getDynamicDrawerContent(): @Composable ((close: () -> Unit) -> Unit) {
    return { _ ->
        val no = remember { mutableStateOf(0) }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                com.microsoft.fluentui.tokenized.controls.Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    text = "Click to create random size list",
                    onClick = { no.value = (40 * Math.random()).toInt() })
            }
            repeat(no.value) {
                item {
                    Spacer(Modifier.height(10.dp))
                    BasicText(
                        text = "Item $it",
                        style = TextStyle(
                            color = AppThemeController.aliasTokens.value!!.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                themeMode = ThemeMode.Auto
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun getDrawerContent(
    contentType: ContentType = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
): @Composable ((close: () -> Unit) -> Unit) {
    return { _ ->
        lateinit var context: Context
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            factory = {
                context = it
                val view = it.activity!!.layoutInflater.inflate(
                    R.layout.demo_drawer_content,
                    null
                )!!.rootView
                val personaList = createPersonaList(context)
                (view as PersonaListView).personas = when (contentType) {
                    ContentType.FULL_SCREEN_SCROLLABLE_CONTENT -> personaList
                    ContentType.EXPANDABLE_SIZE_CONTENT -> personaList.take(9) as ArrayList<IPersona>
                    ContentType.WRAPPED_SIZE_CONTENT -> personaList.take(2) as ArrayList<IPersona>
                }
                view
            }
        ) {}
    }
}

@Composable
private fun getDrawerInDrawerContent(): @Composable ((() -> Unit) -> Unit) {
    return { close ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            com.microsoft.fluentui.tokenized.controls.Button(
                style = ButtonStyle.Button,
                size = ButtonSize.Medium,
                text = "Close Drawer",
                onClick = close
            )

            val scopeB = rememberCoroutineScope()
            val drawerStateB = rememberDrawerState()

            var selectedBehaviorType by remember { mutableStateOf(BehaviorType.BOTTOM_SLIDE_OVER) }
            BasicText("Select Drawer Type", style = TextStyle(fontWeight = FontWeight.Bold))
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.TOP }
                .clearAndSetSemantics { contentDescription = "Top" }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.TOP
                    },
                    selected = selectedBehaviorType == BehaviorType.TOP
                )
                BasicText(text = "Top")
            }
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.BOTTOM }
                .clearAndSetSemantics { contentDescription = "Bottom" }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.BOTTOM
                    },
                    selected = selectedBehaviorType == BehaviorType.BOTTOM
                )
                BasicText(text = "Bottom")
            }
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER }
                .clearAndSetSemantics { contentDescription = "Left Slide Over" }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER
                    },
                    selected = selectedBehaviorType == BehaviorType.LEFT_SLIDE_OVER
                )
                BasicText(text = "Left Slide Over")
            }
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER }
                .clearAndSetSemantics { contentDescription = "Right Slide Over" }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER
                    },
                    selected = selectedBehaviorType == BehaviorType.RIGHT_SLIDE_OVER
                )
                BasicText(text = "Right Slide Over")
            }
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER }
                .clearAndSetSemantics { contentDescription = "Bottom Slide Over" }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER
                    },
                    selected = selectedBehaviorType == BehaviorType.BOTTOM_SLIDE_OVER
                )
                BasicText(text = "Bottom Slide Over")
            }

            //Button on Outer Drawer Surface
            PrimarySurfaceContent(
                onClick = {
                    scopeB.launch {
                        drawerStateB.open()
                    }
                },
                text = "Open Inner Drawer"
            )
            Drawer(
                drawerState = drawerStateB,
                behaviorType = selectedBehaviorType,
                drawerContent = {
                    getDrawerContent()()
                    {
                        scopeB.launch {
                            drawerStateB.close()
                        }
                    }

                },
                expandable = true
            )
        }
    }
}