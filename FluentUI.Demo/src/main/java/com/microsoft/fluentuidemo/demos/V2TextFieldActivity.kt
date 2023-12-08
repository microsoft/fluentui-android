package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Dismisscircle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.controls.TextField
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillBar
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.DemoAppStrings
import com.microsoft.fluentuidemo.util.getDemoAppString
import java.io.Console

// Tags used for testing
const val TEXT_FIELD_MODIFIABLE_PARAMETER_SECTION = "textFieldModifiableParameterSection"
const val TEXT_FIELD_ICON_PARAM = "textFieldIconParam"
const val TEXT_FIELD_HINT_TEXT_PARAM = "textFieldHintTextParam"
const val TEXT_FIELD_LABEL_PARAM = "textFieldLabelParam"
const val TEXT_FIELD_ASSISTIVE_TEXT_PARAM = "textFieldAssistiveTextParam"
const val TEXT_FIELD_SECONDARY_TEXT_PARAM = "textFieldSecondaryTextParam"
const val TEXT_FIELD_ERROR_PARAM = "textFieldErrorTextParam"
const val TEXT_FIELD_PASSWORD_MODE_PARAM = "textFieldPasswordModeParam"
const val TEXT_FIELD_READONLY_PARAM = "textFieldReadOnlyParam"
const val TEXT_FIELD_ENABLED_PARAM = "textFieldEnabledParam"


class V2TextFieldActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-38"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-36"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            var value by rememberSaveable { mutableStateOf("") }
            var leftIcon by rememberSaveable { mutableStateOf(true) }
            var hintText by rememberSaveable { mutableStateOf(true) }
            var label by rememberSaveable { mutableStateOf(true) }
            var assistiveText by rememberSaveable { mutableStateOf(true) }
            var secondaryText by rememberSaveable { mutableStateOf(true) }
            var errorText by rememberSaveable { mutableStateOf(false) }
            var passwordMode by rememberSaveable { mutableStateOf(false) }
            var keyboardType by remember { mutableStateOf(KeyboardType.Text) }
            var readOnly by rememberSaveable { mutableStateOf(false) }
            var enabled by rememberSaveable { mutableStateOf(true) }

            val resources = LocalContext.current.resources

            FluentTheme {
                Column {
                    ListItem.SectionHeader(
                        modifier = Modifier.testTag(TEXT_FIELD_MODIFIABLE_PARAMETER_SECTION),
                        title = getDemoAppString(DemoAppStrings.ModifiableParameters),
                        enableChevron = true,
                        enableContentOpenCloseTransition = true,
                        chevronOrientation = ChevronOrientation(90f, 0f)
                    ) {
                        LazyColumn(Modifier.fillMaxHeight(0.5F)) {
                            item {
                                PillBar(
                                    mutableListOf(
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_text),
                                            onClick = { keyboardType = KeyboardType.Text },
                                            selected = keyboardType == KeyboardType.Text
                                        ),
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_ascii),
                                            onClick = { keyboardType = KeyboardType.Ascii },
                                            selected = keyboardType == KeyboardType.Ascii
                                        ),
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_number),
                                            onClick = { keyboardType = KeyboardType.Number },
                                            selected = keyboardType == KeyboardType.Number
                                        ),
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_phone),
                                            onClick = { keyboardType = KeyboardType.Phone },
                                            selected = keyboardType == KeyboardType.Phone
                                        ),
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_uri),
                                            onClick = { keyboardType = KeyboardType.Uri },
                                            selected = keyboardType == KeyboardType.Uri
                                        ),
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_email),
                                            onClick = { keyboardType = KeyboardType.Email },
                                            selected = keyboardType == KeyboardType.Email
                                        ),
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_password),
                                            onClick = { keyboardType = KeyboardType.Password },
                                            selected = keyboardType == KeyboardType.Password
                                        ),
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_number_password),
                                            onClick = {
                                                keyboardType = KeyboardType.NumberPassword
                                            },
                                            selected = keyboardType == KeyboardType.NumberPassword
                                        ),
                                        PillMetaData(
                                            text = resources.getString(R.string.fluentui_keyboard_decimal),
                                            onClick = { keyboardType = KeyboardType.Decimal },
                                            selected = keyboardType == KeyboardType.Decimal
                                        )
                                    )
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = resources.getString(R.string.fluentui_icon),
                                    subText = if (leftIcon)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(TEXT_FIELD_ICON_PARAM),
                                            onValueChange = {
                                                leftIcon = it
                                            },
                                            checkedState = leftIcon
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = resources.getString(R.string.fluentui_hint),
                                    subText = if (hintText)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(TEXT_FIELD_HINT_TEXT_PARAM),
                                            onValueChange = {
                                                hintText = it
                                            },
                                            checkedState = hintText
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = resources.getString(R.string.fluentui_label),
                                    subText = if (label)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(TEXT_FIELD_LABEL_PARAM),
                                            onValueChange = {
                                                label = it
                                            },
                                            checkedState = label
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = resources.getString(R.string.fluentui_assistive_text),
                                    subText = if (assistiveText)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(
                                                TEXT_FIELD_ASSISTIVE_TEXT_PARAM
                                            ),
                                            onValueChange = {
                                                assistiveText = it
                                            },
                                            checkedState = assistiveText
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = resources.getString(R.string.fluentui_secondary),
                                    subText = if (secondaryText)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(
                                                TEXT_FIELD_SECONDARY_TEXT_PARAM
                                            ),
                                            onValueChange = {
                                                secondaryText = it
                                            },
                                            checkedState = secondaryText
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = resources.getString(R.string.fluentui_password_mode),
                                    subText = if (passwordMode)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(
                                                TEXT_FIELD_PASSWORD_MODE_PARAM
                                            ),
                                            onValueChange = {
                                                passwordMode = it
                                            },
                                            checkedState = passwordMode
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = resources.getString(R.string.fluentui_error),
                                    subText = if (errorText)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(TEXT_FIELD_ERROR_PARAM),
                                            onValueChange = {
                                                errorText = it
                                            },
                                            checkedState = errorText
                                        )
                                    }
                                )
                            }
                            item {
                                ListItem.Item(
                                    text = "Read Only",
                                    subText = if (readOnly)
                                        "Enabled"
                                    else
                                        "Disabled",
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(TEXT_FIELD_READONLY_PARAM),
                                            onValueChange = {
                                                readOnly = it
                                            },
                                            checkedState = readOnly
                                        )
                                    }
                                )
                            }
                            item {
                                ListItem.Item(
                                    text = "Enabled",
                                    subText = if (enabled)
                                        "Enabled"
                                    else
                                        "Disabled",
                                    trailingAccessoryContent = {
                                        ToggleSwitch(
                                            modifier = Modifier.testTag(TEXT_FIELD_ENABLED_PARAM),
                                            onValueChange = {
                                                enabled = it
                                            },
                                            checkedState = enabled
                                        )
                                    }
                                )
                            }
                        }
                    }

                    val focusManager = LocalFocusManager.current
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { focusManager.clearFocus() }) {
                        TextField(
                            value,
                            { value = it },
                            readOnly = readOnly,
                            enabled = enabled,
                            hintText = if (hintText) resources.getString(R.string.fluentui_hint) else null,
                            label = if (label) resources.getString(R.string.fluentui_label) else null,
                            assistiveText = if (assistiveText) resources.getString(R.string.fluentui_assistive_text) else null,
                            leadingRestIcon = if (leftIcon) Icons.Outlined.Email else null,
                            leadingFocusIcon = if (leftIcon) Icons.Filled.Email else null,
                            trailingAccessoryText = if (secondaryText) resources.getString(R.string.fluentui_secondary) else null,
                            errorString = if (errorText) resources.getString(R.string.fluentui_error_string) else null,
                            trailingAccessoryIcon = FluentIcon(
                                SearchBarIcons.Dismisscircle,
                                contentDescription = resources.getString(R.string.fluentui_clear_text)
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                            keyboardActions = KeyboardActions(onAny = { focusManager.clearFocus() }),
                            visualTransformation = if (passwordMode) PasswordVisualTransformation() else VisualTransformation.None
                        )
                    }
                }
            }
        }
    }
}