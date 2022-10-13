package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.notification.CardNudge
import com.microsoft.fluentui.tokenized.notification.CardNudgeMetaData
import com.microsoft.fluentui.tokenized.notification.SnackbarQueue
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2CardNudgeActivity : DemoActivity() {
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
                    var icon: Boolean by rememberSaveable { mutableStateOf(true) }
                    var actionButton: Boolean by rememberSaveable { mutableStateOf(true) }
                    var subtitle: String? by rememberSaveable { mutableStateOf(null) }
                    var accentText: String? by rememberSaveable { mutableStateOf(null) }
                    var accentImage: Boolean by rememberSaveable { mutableStateOf(false) }
                    var outlineEnabled: Boolean by rememberSaveable { mutableStateOf(false) }
                    var dismissEnabled by rememberSaveable { mutableStateOf(true) }

                    ListItem.SectionHeader(
                        title = LocalContext.current.resources.getString(R.string.app_modifiable_parameters),
                        enableChevron = true,
                        enableContentOpenCloseTransition = true,
                        chevronOrientation = ChevronOrientation(90f, 0f),
                    ) {
                        LazyColumn(Modifier.fillMaxHeight(0.5F)) {
                            item {
                                ListItem.Item(
                                    text = "Icon",
                                    subText = if (!icon)
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                icon = it
                                            },
                                            checkedState = icon
                                        )
                                    }
                                )
                            }

                            item {
                                val subTitleText =
                                    LocalContext.current.resources.getString(R.string.fluentui_subtitle)
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
                            }

                            item {
                                val accent =
                                    LocalContext.current.resources.getString(R.string.fluentui_accent)
                                ListItem.Item(
                                    text = accent,
                                    subText = if (accentText.isNullOrBlank())
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                if (accentText.isNullOrBlank()) {
                                                    accentText = accent
                                                } else {
                                                    accentText = null
                                                }
                                            },
                                            checkedState = !accentText.isNullOrBlank()
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = "Accent Image",
                                    subText = if (accentImage)
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                accentImage = it
                                            },
                                            checkedState = accentImage
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = "Action Button",
                                    subText = if (actionButton)
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                actionButton = it
                                            },
                                            checkedState = actionButton
                                        )
                                    }
                                )
                            }

                            item {
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

                            item {
                                ListItem.Item(
                                    text = "Outline Enabled",
                                    subText = if (!outlineEnabled)
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                outlineEnabled = it
                                            },
                                            checkedState = outlineEnabled
                                        )
                                    }
                                )
                            }
                        }
                    }

                    Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                        CardNudge(
                            metadata = CardNudgeMetaData(
                                message = "Title",
                                icon = if (icon) FluentIcon(Icons.Outlined.Call) else null,
                                subTitle = subtitle,
                                accentText = accentText,
                                accentIcon = if (accentImage) FluentIcon(Icons.Outlined.LocationOn) else null,
                                actionMetaData = if (actionButton)
                                    PillMetaData(
                                        "Action",
                                        onClick = {
                                            Toast.makeText(
                                                context,
                                                "Action Pressed",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    ) else null,
                                dismissOnClick = if (dismissEnabled) {
                                    {
                                        Toast.makeText(
                                            context,
                                            "Dismiss Pressed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else null,
                                leftSwipeGesture = {
                                    Toast.makeText(
                                        context,
                                        "Left Swiped",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                rightSwipeGesture = {
                                    Toast.makeText(
                                        context,
                                        "Right Swiped",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            ),
                            outlineMode = outlineEnabled
                        )
                    }
                }
            }
        }
    }
}