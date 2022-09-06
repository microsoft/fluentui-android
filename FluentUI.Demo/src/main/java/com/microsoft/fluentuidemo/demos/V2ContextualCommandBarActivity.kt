package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.KeyEvent.KEYCODE_DPAD_DOWN
import android.view.KeyEvent.KEYCODE_DPAD_RIGHT
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.contextualcommandbar.ActionButtonPosition
import com.microsoft.fluentui.tokenized.contextualcommandbar.CommandGroup
import com.microsoft.fluentui.tokenized.contextualcommandbar.CommandItem
import com.microsoft.fluentui.tokenized.contextualcommandbar.ContextualCommandBar
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2ContextualCommandBarActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)
        val context = this

        compose_here.setContent {
            val click: (() -> Unit) =
                { Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show() }
            val longClick: (() -> Unit) =
                { Toast.makeText(context, "Long Click", Toast.LENGTH_SHORT).show() }

            var boldSelected by remember { mutableStateOf(false) }
            var boldDisabled by remember { mutableStateOf(false) }

            val commandGroup = listOf(
                CommandGroup(
                    "Group 1", listOf(
                        CommandItem(
                            "Email",
                            { boldSelected = !boldSelected },
                            selected = !boldSelected,
                            icon = Icons.Filled.Email,
                            onLongClick = longClick
                        ),
                        CommandItem(
                            "Refresh",
                            click,
                            enabled = false,
                            icon = Icons.Filled.Refresh,
                            onLongClick = longClick
                        ),
                        CommandItem(
                            "Done",
                            click,
                            icon = Icons.Filled.Done,
                            onLongClick = longClick
                        ),
                        CommandItem(
                            "Add",
                            click,
                            enabled = false,
                            icon = Icons.Filled.Add,
                            onLongClick = longClick
                        ),
                        CommandItem(
                            "Call",
                            click,
                            icon = Icons.Filled.Call,
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
                        CommandItem("Edit", click, icon = Icons.Filled.Edit),
                        CommandItem("Delete", click, icon = Icons.Filled.Delete),
                        CommandItem("Italics", click),
                        CommandItem("Underline", click),
                    )
                ),
                CommandGroup(
                    "Group 4", listOf(
                        CommandItem("Email", click, onLongClick = longClick),
                        CommandItem("Info", click, icon = Icons.Filled.Info),
                        CommandItem("Settings", click, onLongClick = longClick),
                        CommandItem("Favorite", click, icon = Icons.Filled.Favorite)
                    )
                )
            )
            Column(
                modifier = Modifier.padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                var kdState by remember { mutableStateOf(ActionButtonPosition.Start) }
                var text by remember { mutableStateOf("") }

                val focusManager = LocalFocusManager.current

                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    Button(
                        { kdState = if(kdState != ActionButtonPosition.None) ActionButtonPosition.None else ActionButtonPosition.End },
                        text = if (kdState != ActionButtonPosition.None) "Disable Keyboard Dismiss" else "Enable Keyboard Dismiss",
                        style = ButtonStyle.OutlinedButton
                    )
                    if (kdState != ActionButtonPosition.None)
                        Button(
                            { kdState = if(kdState == ActionButtonPosition.Start) ActionButtonPosition.End else ActionButtonPosition.Start },
                            text = if (kdState == ActionButtonPosition.Start) " Move KD to End" else "Move KD to Start",
                            style = ButtonStyle.OutlinedButton
                        )
                }

                Divider()

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.onKeyEvent { keyEvent ->
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
                )

                Spacer(modifier = Modifier.weight(1F))

                ContextualCommandBar(
                    commandGroup,
                    actionButtonPosition = kdState
                )
            }
        }
    }
}