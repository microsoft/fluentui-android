package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.notification.Banner
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity

class V2BannerActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-8"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateBannerActivityUI(this)
        }
    }

    @Composable
    private fun CreateBannerActivityUI(context: Context) {
        var icon: Boolean by rememberSaveable { mutableStateOf(true) }
        var actionButtonText: Boolean by rememberSaveable { mutableStateOf(true) }
        var actionButtonIcon: Boolean by rememberSaveable { mutableStateOf(false) }
        var accessoryActionButtons: Boolean by rememberSaveable { mutableStateOf(false) }
        var centerText: Boolean by rememberSaveable { mutableStateOf(false) }
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ListItem.SectionHeader(
                title = LocalContext.current.resources.getString(R.string.app_modifiable_parameters),
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
                            trailingAccessoryContent = {
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
                        ListItem.Item(
                            text = LocalContext.current.resources.getString(R.string.fluentui_action_button),
                            subText = if (!actionButtonText)
                                LocalContext.current.resources.getString(R.string.fluentui_disabled)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_enabled),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        actionButtonText = it
                                        if (actionButtonText) {
                                            actionButtonIcon = false
                                        }
                                    },
                                    checkedState = actionButtonText
                                )
                            }
                        )
                    }
                    item {
                        ListItem.Item(
                            text = LocalContext.current.resources.getString(R.string.fluentui_action_button_icon),
                            subText = if (!actionButtonIcon)
                                LocalContext.current.resources.getString(R.string.fluentui_disabled)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_enabled),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        actionButtonIcon = it
                                        if (actionButtonIcon) {
                                            actionButtonText = false
                                        }
                                    },
                                    checkedState = actionButtonIcon
                                )
                            }
                        )
                    }
                    item {
                        ListItem.Item(
                            text = LocalContext.current.resources.getString(R.string.fluentui_accessory_button),
                            subText = if (!accessoryActionButtons)
                                LocalContext.current.resources.getString(R.string.fluentui_disabled)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_enabled),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        accessoryActionButtons = it
                                    },
                                    checkedState = accessoryActionButtons
                                )
                            }
                        )
                    }
                    item {
                        ListItem.Item(
                            text = LocalContext.current.resources.getString(R.string.fluentui_center),
                            subText = if (!centerText)
                                LocalContext.current.resources.getString(R.string.fluentui_disabled)
                            else
                                LocalContext.current.resources.getString(R.string.fluentui_enabled),
                            trailingAccessoryContent = {
                                ToggleSwitch(
                                    onValueChange = {
                                        centerText = it
                                        if (centerText) {
                                            icon = false
                                            actionButtonText = false
                                            actionButtonIcon = false
                                            accessoryActionButtons = false
                                        }
                                    },
                                    checkedState = centerText
                                )
                            }
                        )
                    }
                }
            }
            Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                Banner(
                    text = "Uploading 1 file",
                    isTextCentered = centerText,
                    leadingIcon = if (icon) {
                        FluentIcon(light = Icons.Outlined.Info)
                    } else null,
                    actionButtonOnClick = { Toast.makeText(
                        context,
                        "Action button clicked",
                        Toast.LENGTH_SHORT
                    ).show() },
                    actionButtonText = if (actionButtonText) "Review" else null,
                    actionButtonIcon = if (actionButtonIcon) FluentIcon(light = Icons.Outlined.Clear) else null,
                    accessoryTextButton1 = if (accessoryActionButtons) "Action1" else null,
                    accessoryTextButton2 = if (accessoryActionButtons) "Action2" else null,
                    accessoryTextButton1OnClick = { Toast.makeText(
                        context,
                        "Accessory button1 clicked",
                        Toast.LENGTH_SHORT
                    ).show() },
                    accessoryTextButton2OnClick = { Toast.makeText(
                        context,
                        "Accessory button2 clicked",
                        Toast.LENGTH_SHORT
                    ).show() }
                )
            }
        }

    }
}