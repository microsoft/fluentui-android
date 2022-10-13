package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Popup
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.SnackbarDuration
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillBar
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.snackbar.Snackbar
import com.microsoft.fluentui.tokenized.snackbar.SnackbarQueue
import com.microsoft.fluentui.tokenized.snackbar.SnackbarResult
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.coroutines.launch
import androidx.compose.material.SnackbarDuration as MaterialDuration
import androidx.compose.material.SnackbarResult as MaterialResult

class V2SnackbarActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeView = findViewById<ComposeView>(R.id.compose_here)
        val context = this

        composeView.setContent {
            FluentTheme {
                val snackbarQueue = remember { SnackbarQueue() }
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var icon: FluentIcon? by rememberSaveable { mutableStateOf(null) }
                    var actionLabel: String? by rememberSaveable { mutableStateOf(null) }
                    var subtitle: String? by rememberSaveable { mutableStateOf(null) }
                    var duration: SnackbarDuration by rememberSaveable { mutableStateOf(SnackbarDuration.SHORT) }
                    var materialDuration: MaterialDuration by rememberSaveable {
                        mutableStateOf(
                            MaterialDuration.Short
                        )
                    }
                    var dismissEnabled by rememberSaveable { mutableStateOf(false) }

                    ListItem.SectionHeader(
                        title = LocalContext.current.resources.getString(R.string.app_modifiable_parameters),
                        enableChevron = true,
                        enableContentOpenCloseTransition = true,
                        chevronOrientation = ChevronOrientation(90f, 0f),
                    ) {
                        Column {
                            PillBar(
                                mutableListOf(
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_indefinite),
                                        onClick = {
                                            duration = SnackbarDuration.INDEFINITE
                                            materialDuration = MaterialDuration.Indefinite
                                        },
                                        selected = duration == SnackbarDuration.INDEFINITE
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_long),
                                        onClick = {
                                            duration = SnackbarDuration.LONG
                                            materialDuration = MaterialDuration.Long
                                        },
                                        selected = duration == SnackbarDuration.LONG
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_short),
                                        onClick = {
                                            duration = SnackbarDuration.SHORT
                                            materialDuration = MaterialDuration.Short
                                        },
                                        selected = duration == SnackbarDuration.SHORT
                                    )
                                ), style = FluentStyle.Neutral,
                                showBackground = true
                            )

                            ListItem.Item(
                                text = "Icon",
                                subText = if (icon == null)
                                    LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                trailingAccessoryView = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            if (icon == null) {
                                                icon = FluentIcon(Icons.Outlined.ShoppingCart)
                                            } else {
                                                icon = null
                                            }
                                        },
                                        checkedState = icon != null
                                    )
                                }
                            )

                            val subTitleText = LocalContext.current.resources.getString(R.string.fluentui_subtitle)
                            ListItem.Item(
                                text = subTitleText,
                                subText = if (subtitle.isNullOrBlank())
                                    LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                trailingAccessoryView = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            if (subtitle.isNullOrBlank()) {
                                                subtitle = subTitleText
                                            } else {
                                                subtitle = null
                                            }
                                        },
                                        checkedState = !subtitle.isNullOrBlank()
                                    )
                                }
                            )

                            ListItem.Item(
                                text = "Action Label",
                                subText = if (actionLabel.isNullOrBlank())
                                    LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                trailingAccessoryView = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            if (actionLabel.isNullOrBlank()) {
                                                actionLabel = "Action"
                                            } else {
                                                actionLabel = null
                                            }
                                        },
                                        checkedState = !actionLabel.isNullOrBlank()
                                    )
                                }
                            )

                            ListItem.Item(
                                text = "Dismiss Button",
                                subText = if (!dismissEnabled)
                                    LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                trailingAccessoryView = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            dismissEnabled = it
                                        },
                                        checkedState = dismissEnabled
                                    )
                                }
                            )
                        }
                    }

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                scope.launch {
                                    val result: SnackbarResult = snackbarQueue.enqueue(
                                        "Hello from Fluent",
                                        icon = icon,
                                        actionText = actionLabel,
                                        subTitle = subtitle,
                                        duration = SnackbarDuration.SHORT,
                                        enableDismiss = dismissEnabled
                                    )

                                    when (result) {
                                        SnackbarResult.TIMEOUT -> Toast.makeText(
                                            context,
                                            "Fluent Snackbar Timed Out",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        SnackbarResult.CLICKED -> Toast.makeText(
                                            context,
                                            "Fluent Snackbar Clicked",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        SnackbarResult.DISMISSED -> Toast.makeText(
                                            context,
                                            "Fluent Snackbar Dismissed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                                scope.launch {
                                    val result: MaterialResult =
                                        snackbarHostState.showSnackbar(
                                            "Hello From Material",
                                            actionLabel,
                                            materialDuration
                                        )
                                    when (result) {
                                        MaterialResult.ActionPerformed -> Toast.makeText(
                                            context,
                                            "Material Snackbar Clicked",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        MaterialResult.Dismissed -> Toast.makeText(
                                            context,
                                            "Material Snackbar Dismissed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            },
                            text = "Show Snackbar",
                            size = ButtonSize.Small,
                            style = ButtonStyle.OutlinedButton
                        )

                        Button(
                            onClick = {
                                snackbarQueue.currentSnackbar?.dismiss()
                                snackbarHostState.currentSnackbarData?.dismiss()
                            },
                            text = "Dismiss Snackbar",
                            size = ButtonSize.Small,
                            style = ButtonStyle.OutlinedButton
                        )
                    }

//                    Popup(Alignment.BottomCenter) {
                        Column {
                            Snackbar(snackbarQueue)
                            SnackbarHost(snackbarHostState)
                        }
//                    }
                }
            }
        }
    }
}