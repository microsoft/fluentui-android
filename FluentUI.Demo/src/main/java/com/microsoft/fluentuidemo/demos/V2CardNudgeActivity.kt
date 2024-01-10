package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.notification.CardNudge
import com.microsoft.fluentui.tokenized.notification.CardNudgeMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlin.math.abs

// Tags used for testing
const val CARD_NUDGE_MODIFIABLE_PARAMETER_SECTION = "Modifiable Parameters"
const val CARD_NUDGE_ICON_PARAM = "Icon Param"
const val CARD_NUDGE_SUBTITLE_PARAM = "Subtitle Param"
const val CARD_NUDGE_ACCENT_ICON_PARAM = "Accent Icon Param"
const val CARD_NUDGE_ACCENT_TEXT_PARAM = "Accent Text Param"
const val CARD_NUDGE_ACTION_BUTTON_PARAM = "Action Button Param"
const val CARD_NUDGE_DISMISS_BUTTON_PARAM = "Dismiss Button Param"

class V2CardNudgeActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-15"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-15"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setActivityContent {
            var swipeLeft: Boolean by rememberSaveable { mutableStateOf(false) }
            var swipeRight: Boolean by rememberSaveable { mutableStateOf(false) }
            var swipeAmount: Float by rememberSaveable { mutableStateOf(0.0F) }

            var icon: Boolean by rememberSaveable { mutableStateOf(true) }
            var actionButton: Boolean by rememberSaveable { mutableStateOf(true) }
            var subtitle: String? by rememberSaveable { mutableStateOf(null) }
            var accentText: String? by rememberSaveable { mutableStateOf(null) }
            var accentImage: Boolean by rememberSaveable { mutableStateOf(false) }
            var outlineEnabled: Boolean by rememberSaveable { mutableStateOf(false) }
            var dismissEnabled by rememberSaveable { mutableStateOf(true) }

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                ListItem.SectionHeader(
                    title = LocalContext.current.resources.getString(R.string.app_modifiable_parameters),
                    modifier = Modifier.testTag(CARD_NUDGE_MODIFIABLE_PARAMETER_SECTION),
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
                                        modifier = Modifier.testTag(CARD_NUDGE_ICON_PARAM),
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
                                trailingAccessoryContent = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            subtitle = if (subtitle.isNullOrBlank()) {
                                                subTitleText
                                            } else {
                                                null
                                            }
                                        },
                                        modifier = Modifier.testTag(CARD_NUDGE_SUBTITLE_PARAM),
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
                                trailingAccessoryContent = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            accentText = if (accentText.isNullOrBlank()) {
                                                accent
                                            } else {
                                                null
                                            }
                                        },
                                        modifier = Modifier.testTag(CARD_NUDGE_ACCENT_TEXT_PARAM),
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
                                trailingAccessoryContent = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            accentImage = it
                                        },
                                        modifier = Modifier.testTag(CARD_NUDGE_ACCENT_ICON_PARAM),
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
                                trailingAccessoryContent = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            actionButton = it
                                        },
                                        modifier = Modifier.testTag(CARD_NUDGE_ACTION_BUTTON_PARAM),
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
                                trailingAccessoryContent = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            dismissEnabled = it
                                        },
                                        modifier = Modifier.testTag(CARD_NUDGE_DISMISS_BUTTON_PARAM),
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
                                trailingAccessoryContent = {
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
            val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Box(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .background(
                            if (abs(swipeAmount) > 0.3)
                                FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.WarningBackground1].value()
                            else
                                Color.Unspecified
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    BasicText(
                        "Hide",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = FluentGlobalTokens.SizeTokens.Size160.value),
                        style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong].merge(
                            TextStyle(
                                textAlign =
                                if (swipeAmount > 0) {
                                    if(!isRtl) TextAlign.Left else TextAlign.Right
                                } else {
                                    if(!isRtl) TextAlign.Right else TextAlign.Left },
                                color = if (abs(swipeAmount) > 0.3)
                                    FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.WarningForeground1].value()
                                else
                                    Color.Black
                            )
                        )
                    )
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
                                if(!isRtl){
                                    swipeRight = false
                                    swipeLeft = true
                                }
                                else{
                                    swipeRight = true
                                    swipeLeft = false
                                }
                                swipeAmount = it
                            },
                            rightSwipeGesture = {
                                if(!isRtl) {
                                    swipeRight = true
                                    swipeLeft = false
                                }
                                else{
                                    swipeRight = false
                                    swipeLeft = true
                                }
                                swipeAmount = it
                            }

                        ),
                        outlineMode = outlineEnabled
                    )

                }
                if (swipeLeft)
                    Label(
                        leftSwiped + ": " + abs(swipeAmount).toString(),
                        textStyle = FluentAliasTokens.TypographyTokens.Caption1Strong
                    )
                else if (swipeRight)
                    Label(
                        rightSwiped + ": " + abs(swipeAmount).toString(),
                        textStyle = FluentAliasTokens.TypographyTokens.Caption1Strong
                    )
            }
        }
    }
}