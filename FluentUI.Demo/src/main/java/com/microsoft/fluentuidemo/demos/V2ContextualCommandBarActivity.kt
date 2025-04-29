package com.microsoft.fluentuidemo.demos

import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralForegroundColorTokens.Foreground2
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.contextualcommandbar.ActionButtonPosition
import com.microsoft.fluentui.tokenized.contextualcommandbar.CommandGroup
import com.microsoft.fluentui.tokenized.contextualcommandbar.CommandItem
import com.microsoft.fluentui.tokenized.contextualcommandbar.ContextualCommandBar
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.BottomDrawer
import com.microsoft.fluentui.tokenized.drawer.rememberBottomDrawerState
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlinx.coroutines.launch

class V2ContextualCommandBarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-17"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-17"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        setActivityContent {
            val click: (() -> Unit) =
                { Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show() }
            val longClick: (() -> Unit) =
                { Toast.makeText(context, "Long Click", Toast.LENGTH_SHORT).show() }

            var boldSelected by remember { mutableStateOf(false) }
            var boldDisabled by remember { mutableStateOf(false) }

            val commandGroup2 = CommandGroup(
                "Group 1", listOf(
                    CommandItem(
                        "Email",
                        click,
                        icon = FluentIcon(Icons.Outlined.Email),
                        onLongClick = longClick
                    ),
                    CommandItem(
                        "Build",
                        click,
                        icon = FluentIcon(Icons.Outlined.Build),
                        onLongClick = longClick
                    ),
                    CommandItem(
                        "Done",
                        click,
                        icon = FluentIcon(Icons.Filled.Done),
                        onLongClick = longClick
                    ),
                    CommandItem(
                        "Add",
                        click,
                        icon = FluentIcon(Icons.Filled.Add),
                        onLongClick = longClick
                    )
                )
            )
            val commandGroup3 = CommandGroup(
                "Group 1", listOf(
                    CommandItem(
                        "Menu",
                        click,
                        icon = FluentIcon(Icons.Outlined.Menu),
                        onLongClick = longClick
                    ),
                    CommandItem(
                        "Home",
                        click,
                        icon = FluentIcon(Icons.Outlined.Home),
                        onLongClick = longClick
                    ),
                    CommandItem(
                        "Create",
                        click,
                        icon = FluentIcon(Icons.Filled.Create),
                        onLongClick = longClick
                    ),
                    CommandItem(
                        "Call",
                        click,
                        icon = FluentIcon(Icons.Filled.Call),
                        onLongClick = longClick
                    )
                )
            )
            val commandGroup4 = CommandGroup(
                "Group 1", listOf(
                    CommandItem(
                        "KeyboardArrowLeft",
                        click,
                        icon = FluentIcon(Icons.Filled.KeyboardArrowLeft),
                        onLongClick = longClick
                    ),
                    CommandItem(
                        "KeyboardArrowRight",
                        click,
                        icon = FluentIcon(Icons.Filled.KeyboardArrowRight),
                        onLongClick = longClick
                    )
                )
            )
            val commandGroup5 = CommandGroup(
                "Group 1", listOf(
                    CommandItem(
                        "DateRange",
                        click,
                        icon = FluentIcon(Icons.Filled.DateRange),
                        onLongClick = longClick
                    )
                ), weight = 2f
            )
            val commandGroup = listOf(
                CommandGroup(
                    "Group 1", listOf(
                        CommandItem(
                            "Email",
                            { boldSelected = !boldSelected },
                            selected = !boldSelected,
                            icon = FluentIcon(Icons.Filled.Email),
                            onLongClick = longClick
                        ),
                        CommandItem(
                            "Refresh",
                            click,
                            enabled = false,
                            icon = FluentIcon(Icons.Filled.Refresh),
                            onLongClick = longClick
                        ),
                        CommandItem(
                            "Done",
                            click,
                            icon = FluentIcon(Icons.Filled.Done, tint = Color.Green),
                            onLongClick = longClick
                        ),
                        CommandItem(
                            "Add",
                            click,
                            enabled = false,
                            icon = FluentIcon(Icons.Filled.Add),
                            onLongClick = longClick
                        ),
                        CommandItem(
                            "Call",
                            click,
                            icon = FluentIcon(Icons.Filled.Call),
                            onLongClick = longClick
                        )
                    )
                ),
                CommandGroup(
                    "Group 2", listOf(
                        CommandItem("Bold", { boldSelected = !boldSelected },
                            enabled = !boldDisabled,
                            selected = boldSelected,
                            onLongClick = { boldDisabled = !boldDisabled }),
                    )
                ),
                CommandGroup(
                    "Group 3", listOf(
                        CommandItem("Edit", click, icon = FluentIcon(Icons.Filled.Edit)),
                        CommandItem("Delete", click, icon = FluentIcon(Icons.Filled.Delete)),
                        CommandItem("Italics", click),
                        CommandItem("Underline", click),
                    )
                ),
                CommandGroup(
                    "Group 4", listOf(
                        CommandItem("Email", click, onLongClick = longClick),
                        CommandItem("Info", click, icon = FluentIcon(Icons.Filled.Info)),
                        CommandItem("Settings", click, onLongClick = longClick),
                        CommandItem("Favorite", click, icon = FluentIcon(Icons.Filled.Favorite))
                    )
                )
            )
            Column(
                modifier = Modifier.padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    CenterVertically
                )
            ) {
                var kdState by remember { mutableStateOf(ActionButtonPosition.Start) }
                var text by remember { mutableStateOf("") }
                var actionButtonEnabled by remember { mutableStateOf(true) }

                val focusManager = LocalFocusManager.current
                val scope = rememberCoroutineScope()
                val drawerState = rememberBottomDrawerState(expandable = false)
                val open: () -> Unit = {
                    scope.launch { drawerState.open() }
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        CenterHorizontally
                    ), verticalAlignment = CenterVertically
                ) {
                    BasicText(
                        text = "Action Button",
                        style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1].merge(
                            TextStyle(color = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                                themeMode = FluentTheme.themeMode
                            ))
                        ),
                    )
                    ToggleSwitch(
                        onValueChange =
                        {
                            actionButtonEnabled = it
                            if (!actionButtonEnabled)
                                kdState = ActionButtonPosition.None
                            else
                                kdState = ActionButtonPosition.Start
                        }, enabledSwitch = true, checkedState = actionButtonEnabled
                    )
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        CenterHorizontally
                    ), verticalAlignment = CenterVertically
                ) {
                    Button(
                        {
                            kdState =
                                if (kdState == ActionButtonPosition.Start) ActionButtonPosition.End else ActionButtonPosition.Start
                        },
                        text = "Toggle action button position",
                        style = ButtonStyle.OutlinedButton,
                        enabled = kdState != ActionButtonPosition.None
                    )
                }
                Row(
                    modifier = Modifier.padding(start = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        CenterHorizontally
                    ), verticalAlignment = CenterVertically
                ) {
                    Button(
                        style = ButtonStyle.OutlinedButton,
                        text = "Multiline CCB on Drawer",
                        onClick = open
                    )
                }

                Divider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = { BasicText("Type your text here") },
                        modifier = Modifier
                            .onKeyEvent { keyEvent ->
                                when (keyEvent.nativeKeyEvent.keyCode) {
                                    KEYCODE_DPAD_DOWN, KEYCODE_DPAD_RIGHT -> {
                                        focusManager.moveFocus(FocusDirection.Down)
                                        true
                                    }

                                    else -> {
                                        false
                                    }
                                }
                            }
                            .padding(start = 8.dp)
                            .background(Color.White),
                    )
                }


                Spacer(modifier = Modifier.weight(1F))

                ContextualCommandBar(
                    commandGroup,
                    actionButtonPosition = kdState
                )
                val configuration = LocalConfiguration.current
                if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    BottomDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    verticalAlignment = CenterVertically
                                ) {
                                    item {
                                        BasicText(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            text = "Heading1",
                                            style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1]
                                        )
                                    }
                                    item {

                                        BasicText(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            text = "Heading2",
                                            style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]
                                        )
                                    }
                                    item {
                                        BasicText(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            text = "Heading3",
                                            style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title3]
                                        )
                                    }
                                    item {
                                        BasicText(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            text = "Paragraph",
                                            style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
                                        )
                                    }
                                }
                                LazyColumn{
                                    item {
                                        ContextualCommandBar(
                                            listOf(commandGroup2),
                                            scrollable = false,
                                            actionButtonPosition = ActionButtonPosition.None
                                        )
                                        ContextualCommandBar(
                                            listOf(commandGroup3),
                                            scrollable = false,
                                            actionButtonPosition = ActionButtonPosition.None
                                        )
                                        ContextualCommandBar(
                                            listOf(commandGroup4, commandGroup5),
                                            scrollable = false,
                                            actionButtonPosition = ActionButtonPosition.None
                                        )
                                    }
                                }
                            }

                        },
                        slideOver = false,
                        scrimVisible = false
                    )
                } else {
                    BottomDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            Column(horizontalAlignment = CenterHorizontally) {
                                LazyRow(
                                    modifier = Modifier.padding(horizontal = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    verticalAlignment = CenterVertically
                                ) {
                                    item {
                                        BasicText(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            text = "Heading1",
                                            style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1]
                                        )
                                    }
                                    item {
                                        BasicText(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            text = "Heading2",
                                            style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]
                                        )
                                    }
                                    item {
                                        BasicText(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            text = "Heading3",
                                            style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title3]
                                        )
                                    }
                                    item {
                                        BasicText(
                                            modifier = Modifier.padding(
                                                horizontal = 8.dp,
                                                vertical = 4.dp
                                            ),
                                            text = "Paragraph",
                                            style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
                                        )
                                    }
                                }
                                Row {
                                    ContextualCommandBar(
                                        listOf(
                                            commandGroup2,
                                            commandGroup3,
                                            commandGroup4,
                                            commandGroup5
                                        ),
                                        scrollable = false,
                                        actionButtonPosition = ActionButtonPosition.None
                                    )

                                }
                            }

                        },
                        slideOver = false,
                        scrimVisible = false
                    )
                }

            }
        }
    }
}