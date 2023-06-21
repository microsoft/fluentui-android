package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Dialog
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding

class V2DialogActivity : V2DemoActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
                CreateDialogActivityUI(this)
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun CreateDialogActivityUI(context: Context) {
        var showDialog by remember { mutableStateOf(false) }
        var dismissOnClickOutside by remember { mutableStateOf(false) }
        var dismissOnBackPress by remember { mutableStateOf(false) }
        val resources = LocalContext.current.resources
        Column(
            modifier = Modifier.padding(all = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ListItem.Item(
                text = resources.getString(R.string.dismiss_dialog_outside),
                subText = if (dismissOnClickOutside)
                    resources.getString(R.string.fluentui_enabled)
                else
                    resources.getString(R.string.fluentui_disabled),
                trailingAccessoryContent = {
                    ToggleSwitch(
                        modifier = Modifier.testTag("outside press"),
                        onValueChange = {
                            dismissOnClickOutside = it
                        },
                        checkedState = dismissOnClickOutside
                    )
                }
            )
            ListItem.Item(
                text = resources.getString(R.string.dismiss_dialog_back),
                subText = if (dismissOnBackPress)
                    resources.getString(R.string.fluentui_enabled)
                else
                    resources.getString(R.string.fluentui_disabled),
                trailingAccessoryContent = {
                    ToggleSwitch(
                        modifier = Modifier.testTag("back press"),
                        onValueChange = {
                            dismissOnBackPress = it
                        },
                        checkedState = dismissOnBackPress
                    )
                }
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Button(
                    style = ButtonStyle.OutlinedButton,
                    text = resources.getString(R.string.show_dialog),
                    onClick = { showDialog = !showDialog }
                )
            }
            var count by remember { mutableStateOf(1) }
            if (showDialog) {
                Dialog(
                    onDismiss = {
                        showDialog = !showDialog
                        Toast.makeText(
                            context,
                            resources.getString(R.string.dismiss_dialog),
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    dialogProperties = DialogProperties(
                        dismissOnClickOutside = dismissOnClickOutside,
                        dismissOnBackPress = dismissOnBackPress,
                        usePlatformDefaultWidth = false
                    )
                ) {
                    Column(Modifier.padding(all = 16.dp)) {
                        for (i in 0..count) {
                            Label(
                                text = resources.getString(R.string.dialog_description),
                                textStyle = FluentAliasTokens.TypographyTokens.Body1
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            Button(
                                style = ButtonStyle.TextButton,
                                text = "Add Item",
                                onClick = {
                                    count++
                                })
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                style = ButtonStyle.TextButton,
                                text = resources.getString(R.string.ok),
                                onClick = {
                                    showDialog = false
                                    Toast.makeText(
                                        context,
                                        resources.getString(R.string.ok),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        }
                    }
                }
            }
        }

    }
}