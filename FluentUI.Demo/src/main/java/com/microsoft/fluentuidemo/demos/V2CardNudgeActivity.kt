package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.notification.CardNudge
import com.microsoft.fluentui.tokenized.notification.CardNudgeMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2CardNudgeActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    // Tags used for testing
    private val MODIFIABLE_PARAMETER_SECTION = "Modifiable Parameters"
    private val ICON_PARAM = "Icon Param"
    private val SUBTITLE_PARAM = "Subtitle Param"
    private val ACCENT_ICON_PARAM = "Accent Icon Param"
    private val ACCENT_TEXT_PARAM = "Accent Text Param"
    private val ACTION_BUTTON_PARAM = "Action Button Param"
    private val DISMISS_BUTTON_PARAM = "Dismiss Button Param"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeView = findViewById<ComposeView>(R.id.compose_here)
        val context = this

        composeView.setContent {
            FluentTheme {
                var swipeLeft: Boolean by rememberSaveable { mutableStateOf(false) }
                var swipeRight: Boolean by rememberSaveable { mutableStateOf(false) }

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
                        modifier = Modifier.testTag(MODIFIABLE_PARAMETER_SECTION),
                        enableChevron = true,
                        enableContentOpenCloseTransition = true,
                        chevronOrientation = ChevronOrientation(90f, 0f),
                    ) {
                        LazyColumn(Modifier.fillMaxHeight(0.5F)) {
                            item {
                                ListItem.Item(
                                    text = LocalContext.current.resources.getString(R.string.fluentui_icon),
                                    subText = if (!icon)
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                icon = it
                                            },
                                            modifier = Modifier.testTag(ICON_PARAM),
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
                                            modifier = Modifier.testTag(SUBTITLE_PARAM),
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
                                            modifier = Modifier.testTag(ACCENT_TEXT_PARAM),
                                            checkedState = !accentText.isNullOrBlank()
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = LocalContext.current.resources.getString(R.string.fluentui_accent_icon),
                                    subText = if (accentImage)
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                accentImage = it
                                            },
                                            modifier = Modifier.testTag(ACCENT_ICON_PARAM),
                                            checkedState = accentImage
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = LocalContext.current.resources.getString(R.string.fluentui_action_button),
                                    subText = if (actionButton)
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                actionButton = it
                                            },
                                            modifier = Modifier.testTag(ACTION_BUTTON_PARAM),
                                            checkedState = actionButton
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = LocalContext.current.resources.getString(R.string.fluentui_dismiss_button),
                                    subText = if (!dismissEnabled)
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                dismissEnabled = it
                                            },
                                            modifier = Modifier.testTag(DISMISS_BUTTON_PARAM),
                                            checkedState = dismissEnabled
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = LocalContext.current.resources.getString(R.string.fluentui_outline),
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

                    //Behaviour text
                    val buttonPressed =
                        LocalContext.current.resources.getString(R.string.fluentui_button_pressed)
                    val dismissPressed =
                        LocalContext.current.resources.getString(R.string.fluentui_dismissed)
                    val leftSwiped =
                        LocalContext.current.resources.getString(R.string.fluentui_left_swiped)
                    val rightSwiped =
                        LocalContext.current.resources.getString(R.string.fluentui_right_swiped)

                    Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                        CardNudge(
                            metadata = CardNudgeMetaData(
                                message = LocalContext.current.resources.getString(R.string.fluentui_title),
                                icon = if (icon) FluentIcon(Icons.Outlined.Call) else null,
                                subTitle = subtitle,
                                accentText = accentText,
                                accentIcon = if (accentImage) FluentIcon(Icons.Outlined.LocationOn) else null,
                                actionMetaData = if (actionButton)
                                    PillMetaData(
                                        LocalContext.current.resources.getString(R.string.fluentui_action_button),
                                        onClick = {
                                            Toast.makeText(
                                                context,
                                                buttonPressed,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    ) else null,
                                dismissOnClick = if (dismissEnabled) {
                                    {
                                        Toast.makeText(
                                            context,
                                            dismissPressed,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else null,
                                leftSwipeGesture = {
                                    swipeRight = false
                                    swipeLeft = true
                                },
                                rightSwipeGesture = {
                                    swipeRight = true
                                    swipeLeft = false
                                }

                            ),
                            outlineMode = outlineEnabled
                        )
                        if (swipeLeft)
                            Text(leftSwiped)
                        else if (swipeRight)
                            Text(rightSwiped)
                    }
                }
            }
        }
    }
}