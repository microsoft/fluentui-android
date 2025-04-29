package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.SnackbarStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.notification.AnimationBehavior
import com.microsoft.fluentui.tokenized.notification.AnimationVariables
import com.microsoft.fluentui.tokenized.notification.NotificationDuration
import com.microsoft.fluentui.tokenized.notification.NotificationResult
import com.microsoft.fluentui.tokenized.notification.Snackbar
import com.microsoft.fluentui.tokenized.notification.SnackbarState
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillBar
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlinx.coroutines.launch

// Tags used for testing
const val SNACK_BAR_MODIFIABLE_PARAMETER_SECTION = "Snack bar Modifiable Parameters"
const val SNACK_BAR_ICON_PARAM = "Snack bar Icon Param"
const val SNACK_BAR_SUBTITLE_PARAM = "Snack bar Subtitle Param"
const val SNACK_BAR_ACTION_BUTTON_PARAM = "Snack bar Action Button Param"
const val SNACK_BAR_DISMISS_BUTTON_PARAM = "Snack bar Dismiss Button Param"
const val SNACK_BAR_SHOW_SNACKBAR = "Snack bar Show Snackbar"
const val SNACK_BAR_DISMISS_SNACKBAR = "Snack bar Dismiss Snackbar"

class V2SnackbarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-36"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-34"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setActivityContent {
            val snackbarState = remember { SnackbarState() }

            val scope = rememberCoroutineScope()
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var icon: Boolean by rememberSaveable { mutableStateOf(false) }
                var actionLabel: Boolean by rememberSaveable { mutableStateOf(false) }
                var subtitle: String? by rememberSaveable { mutableStateOf(null) }
                var style: SnackbarStyle by rememberSaveable { mutableStateOf(SnackbarStyle.Neutral) }
                var duration: NotificationDuration by rememberSaveable {
                    mutableStateOf(
                        NotificationDuration.SHORT
                    )
                }
                var dismissEnabled by rememberSaveable { mutableStateOf(false) }

                ListItem.SectionHeader(
                    title = LocalContext.current.resources.getString(R.string.app_modifiable_parameters),
                    enableChevron = true,
                    enableContentOpenCloseTransition = true,
                    chevronOrientation = ChevronOrientation(90f, 0f),
                    modifier = Modifier.testTag(SNACK_BAR_MODIFIABLE_PARAMETER_SECTION)
                ) {
                    LazyColumn(Modifier.fillMaxHeight(0.5F)) {
                        item {
                            PillBar(
                                mutableListOf(
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_indefinite),
                                        onClick = {
                                            duration = NotificationDuration.INDEFINITE
                                        },
                                        selected = duration == NotificationDuration.INDEFINITE
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_long),
                                        onClick = {
                                            duration = NotificationDuration.LONG
                                        },
                                        selected = duration == NotificationDuration.LONG
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_short),
                                        onClick = {
                                            duration = NotificationDuration.SHORT
                                        },
                                        selected = duration == NotificationDuration.SHORT
                                    )
                                ), style = FluentStyle.Neutral,
                                showBackground = true
                            )
                        }

                        item {
                            Spacer(
                                Modifier
                                    .height(8.dp)
                                    .fillMaxWidth()
                                    .background(aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value())
                            )
                        }

                        item {
                            PillBar(
                                mutableListOf(
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_neutral),
                                        onClick = {
                                            style = SnackbarStyle.Neutral
                                        },
                                        selected = style == SnackbarStyle.Neutral
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_contrast),
                                        onClick = {
                                            style = SnackbarStyle.Contrast
                                        },
                                        selected = style == SnackbarStyle.Contrast
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_accent),
                                        onClick = {
                                            style = SnackbarStyle.Accent
                                        },
                                        selected = style == SnackbarStyle.Accent
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_warning),
                                        onClick = {
                                            style = SnackbarStyle.Warning
                                        },
                                        selected = style == SnackbarStyle.Warning
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_danger),
                                        onClick = {
                                            style = SnackbarStyle.Danger
                                        },
                                        selected = style == SnackbarStyle.Danger
                                    )
                                ), style = FluentStyle.Neutral,
                                showBackground = true
                            )
                        }

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
                                        checkedState = icon,
                                        modifier = Modifier.testTag(SNACK_BAR_ICON_PARAM)
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
                                            if (subtitle.isNullOrBlank()) {
                                                subtitle = subTitleText
                                            } else {
                                                subtitle = null
                                            }
                                        },
                                        checkedState = !subtitle.isNullOrBlank(),
                                        modifier = Modifier.testTag(SNACK_BAR_SUBTITLE_PARAM)
                                    )
                                }
                            )
                        }

                        item {
                            ListItem.Item(
                                text = LocalContext.current.resources.getString(R.string.fluentui_action_button),
                                subText = if (actionLabel)
                                    LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                trailingAccessoryContent = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            actionLabel = it
                                        },
                                        checkedState = actionLabel,
                                        modifier = Modifier.testTag(SNACK_BAR_ACTION_BUTTON_PARAM)
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
                                        checkedState = dismissEnabled,
                                        modifier = Modifier.testTag(SNACK_BAR_DISMISS_BUTTON_PARAM)
                                    )
                                }
                            )
                        }
                    }
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val actionButtonString =
                        LocalContext.current.resources.getString(R.string.fluentui_action_button)
                    val dismissedString =
                        LocalContext.current.resources.getString(R.string.fluentui_dismissed)
                    val pressedString =
                        LocalContext.current.resources.getString(R.string.fluentui_button_pressed)
                    val timeoutString =
                        LocalContext.current.resources.getString(R.string.fluentui_timeout)
                    Button(
                        onClick = {
                            scope.launch {
                                val result: NotificationResult = snackbarState.showSnackbar(
                                    "Hello from Fluent",
                                    style = style,
                                    icon = if (icon) FluentIcon(Icons.Outlined.ShoppingCart) else null,
                                    actionText = if (actionLabel) actionButtonString else null,
                                    subTitle = subtitle,
                                    duration = duration,
                                    enableDismiss = dismissEnabled,
                                    animationBehavior = customizedAnimationBehavior
                                )

                                when (result) {
                                    NotificationResult.TIMEOUT -> Toast.makeText(
                                        context,
                                        timeoutString,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    NotificationResult.CLICKED -> Toast.makeText(
                                        context,
                                        pressedString,
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    NotificationResult.DISMISSED -> Toast.makeText(
                                        context,
                                        dismissedString,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        text = LocalContext.current.resources.getString(R.string.fluentui_show_snackbar),
                        size = ButtonSize.Small,
                        style = ButtonStyle.OutlinedButton,
                        modifier = Modifier.testTag(SNACK_BAR_SHOW_SNACKBAR)
                    )

                    Button(
                        onClick = {
                            snackbarState.currentSnackbar?.dismiss(scope)
                        },
                        text = LocalContext.current.resources.getString(R.string.fluentui_dismiss_snackbar),
                        size = ButtonSize.Small,
                        style = ButtonStyle.OutlinedButton,
                        modifier = Modifier.testTag(SNACK_BAR_DISMISS_SNACKBAR)
                    )
                }
                Box(Modifier.fillMaxHeight(), contentAlignment = Alignment.Center) {
                    Snackbar(snackbarState, Modifier.padding(bottom = 12.dp), null, true)
                }
            }
        }
    }
}

// Customized animation behavior for Snackbar
val customizedAnimationBehavior: AnimationBehavior = object : AnimationBehavior() {
    override var animationVariables: AnimationVariables = object : AnimationVariables() {
        override var scale = Animatable(1F)
        override var offsetY = Animatable(50F)
    }

    override suspend fun onShowAnimation() {
        // pop from bottom
        animationVariables.alpha.snapTo(1F)
        animationVariables.offsetX.snapTo(0F)
        animationVariables.offsetY.snapTo(50F)
        animationVariables.offsetY.animateTo(
            0F,
            animationSpec = tween(
                easing = LinearOutSlowInEasing,
                durationMillis = 500,
            )
        )
    }

    override suspend fun onDismissAnimation() {
        // slide out from left
        animationVariables.offsetX.animateTo(
            targetValue = -2000f,
            animationSpec = tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        )
    }
}