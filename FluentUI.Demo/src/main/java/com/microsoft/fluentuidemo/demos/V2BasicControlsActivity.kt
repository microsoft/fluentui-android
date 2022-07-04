package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.theme.token.MyAliasTokens
import com.example.theme.token.MyGlobalTokens
import com.microsoft.fluentui.theme.AppThemeController
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.controls.CheckBox
import com.microsoft.fluentui.controls.RadioButton
import com.microsoft.fluentui.controls.ToggleSwitch
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2BasicControlsActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        val compose_here = findViewById<ComposeView>(R.id.compose_here)
        compose_here.setContent {
            FluentTheme {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(16.dp)) {

                    var checked by remember { mutableStateOf(true) }
                    var enabled by remember { mutableStateOf(false) }
                    val themes = listOf("Theme 1", "Theme 2")
                    val selectedOption = remember { mutableStateOf(themes[0]) }

                    Row(horizontalArrangement = Arrangement.spacedBy(30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Toggle Switch enable",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1F),
                                color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(themeMode = ThemeMode.Auto))
                        ToggleSwitch(true, enabled, {
                            enabled = it
                            Toast.makeText(context, "Switch 1 Toggled", Toast.LENGTH_SHORT).show()
                        })
                    }

                    Divider()

                    Row(horizontalArrangement = Arrangement.spacedBy(30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Toggle Global/Alias Theme",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1F),
                                color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(themeMode = ThemeMode.Auto))
                        ToggleSwitch(enabled, checked, {
                            checked = it
                            if (checked) {
                                AppThemeController.updateGlobalTokens(GlobalTokens())
                                AppThemeController.updateAliasTokens(AliasTokens())
                                selectedOption.value = themes[0]
                            } else {
                                AppThemeController.updateGlobalTokens(MyGlobalTokens())
                                AppThemeController.updateAliasTokens(MyAliasTokens())
                                selectedOption.value = themes[1]
                            }
                            Toast.makeText(context, "Switch 2 Toggled", Toast.LENGTH_SHORT).show()
                        })
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Toggle Global/Alias Theme",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1F),
                                color = AppThemeController.aliasTokens.value!!.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(themeMode = ThemeMode.Auto))
                        CheckBox(enabled = enabled, checked = !checked, onCheckedChanged = {
                            checked = !it
                            if (checked) {
                                AppThemeController.updateAliasTokens(AliasTokens())
                                AppThemeController.updateGlobalTokens(GlobalTokens())
                                selectedOption.value = themes[0]
                            } else {
                                AppThemeController.updateAliasTokens(MyAliasTokens())
                                AppThemeController.updateGlobalTokens(MyGlobalTokens())
                                selectedOption.value = themes[1]
                            }
                        })
                    }

                    themes.forEach { theme ->
                        Row(horizontalArrangement = Arrangement.spacedBy(30.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .selectable(
                                                selected = (theme == selectedOption.value),
                                                onClick = { },
                                                role = Role.RadioButton,
                                                interactionSource = MutableInteractionSource(),
                                                indication = null
                                        )) {
                            Text(text = theme,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1F),
                                    color = AppThemeController.aliasTokens.value!!.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(themeMode = ThemeMode.Auto))
                            RadioButton(enabled = enabled,
                                    selected = (selectedOption.value == theme),
                                    onClick = {
                                        selectedOption.value = theme
                                        if (theme == "Theme 1") {
                                            AppThemeController.updateAliasTokens(AliasTokens())
                                            AppThemeController.updateGlobalTokens(GlobalTokens())
                                            checked = true
                                        } else {
                                            AppThemeController.updateAliasTokens(MyAliasTokens())
                                            AppThemeController.updateGlobalTokens(MyGlobalTokens())
                                            checked = false
                                        }
                                        Toast.makeText(context, "Radio Button: ${theme} selected", Toast.LENGTH_SHORT).show()
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}