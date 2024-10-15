package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.theme.token.MyControlTokens
import com.example.theme.token.OneNoteAliasTokens
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.tokenized.controls.CheckBox
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentuidemo.V2DemoActivity
import androidx.compose.ui.platform.testTag

// Tags used for testing
const val BASIC_CONTROLS_TOGGLE_ENABLE = "Basic Controls Toggle Switch Enable"
const val BASIC_CONTROLS_CHECK_BOX = "Basic Controls Check Box"
const val BASIC_CONTROLS_RADIO_1 = "Basic Controls Radio Button 1"
const val BASIC_CONTROLS_RADIO_2 = "Basic Controls Radio Button 2"

class V2BasicControlsActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-5"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-5"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        setActivityContent {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(16.dp)
            ) {

                var checked by remember { mutableStateOf(true) }
                var enabled by remember { mutableStateOf(false) }
                val themes = listOf("Theme 1", "Theme 2")
                val selectedOption = remember { mutableStateOf(themes[0]) }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicText(
                        text = "Toggle Switch enable",
                        modifier = Modifier
                            .weight(1F)
                            .focusable(false),
                        style = TextStyle(
                            color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                themeMode = FluentTheme.themeMode
                            ),
                            fontWeight = FontWeight.Bold
                        )
                    )
                    ToggleSwitch(
                        onValueChange = {
                            enabled = it
                            Toast.makeText(context, "Switch 1 Toggled", Toast.LENGTH_SHORT).show()
                        }, enabledSwitch = true,
                        modifier = Modifier.testTag(BASIC_CONTROLS_TOGGLE_ENABLE),
                        checkedState = enabled
                    )
                }

                Divider()

                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicText(
                        text = "Toggle Global/Alias Theme",
                        modifier = Modifier
                            .weight(1F)
                            .focusable(false),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                themeMode = FluentTheme.themeMode
                            )
                        )
                    )
                    ToggleSwitch(
                        enabledSwitch = enabled,
                        checkedState = checked,
                        onValueChange = {
                            checked = it
                            if (checked) {
                                FluentTheme.updateAliasTokens(AliasTokens())
                                FluentTheme.updateControlTokens(ControlTokens())
                                selectedOption.value = themes[0]
                            } else {
                                FluentTheme.updateAliasTokens(OneNoteAliasTokens())
                                FluentTheme.updateControlTokens(MyControlTokens())
                                selectedOption.value = themes[1]
                            }
                            Toast.makeText(context, "Switch 2 Toggled", Toast.LENGTH_SHORT)
                                .show()
                        })
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BasicText(
                        text = "Toggle Global/Alias Theme",
                        modifier = Modifier
                            .weight(1F)
                            .focusable(false),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                themeMode = FluentTheme.themeMode
                            )
                        )
                    )
                    CheckBox(enabled = enabled, checked = !checked,
                        modifier = Modifier.testTag(BASIC_CONTROLS_CHECK_BOX),
                        onCheckedChanged = {
                            checked = !it
                            if (checked) {
                                FluentTheme.updateAliasTokens(AliasTokens())
                                FluentTheme.updateControlTokens(ControlTokens())
                                selectedOption.value = themes[0]
                            } else {
                                FluentTheme.updateAliasTokens(OneNoteAliasTokens())
                                FluentTheme.updateControlTokens(MyControlTokens())
                                selectedOption.value = themes[1]
                            }
                        })
                }

                themes.forEachIndexed { i, theme ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(30.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (theme == selectedOption.value),
                                onClick = { },
                                role = Role.RadioButton,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                    ) {
                        BasicText(
                            text = theme,
                            modifier = Modifier
                                .weight(1F)
                                .focusable(false),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = FluentTheme.themeMode
                                )
                            )
                        )
                        RadioButton(enabled = enabled,
                            selected = (selectedOption.value == theme),
                            modifier = Modifier.testTag(if (i == 0) BASIC_CONTROLS_RADIO_1 else BASIC_CONTROLS_RADIO_2),
                            onClick = {
                                selectedOption.value = theme
                                checked = if (theme == "Theme 1") {
                                    FluentTheme.updateAliasTokens(AliasTokens())
                                    FluentTheme.updateControlTokens(ControlTokens())
                                    true
                                } else {
                                    FluentTheme.updateAliasTokens(OneNoteAliasTokens())
                                    FluentTheme.updateControlTokens(MyControlTokens())
                                    false
                                }
                                Toast.makeText(
                                    context,
                                    "Radio Button: $theme selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }
            }
        }
    }
}