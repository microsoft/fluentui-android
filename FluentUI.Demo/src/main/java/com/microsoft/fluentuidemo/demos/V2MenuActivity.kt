package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Menu
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity


class V2MenuActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-21"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-21"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setActivityContent {
            CreateMenuActivityUI(context)
        }
    }
}

@Composable
fun CreateMenuActivityUI(context: Context) {
    showMenuView()
}


@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    borderRadius: Float = 8f // Default radius
) {
    Box(
        modifier = modifier
            .border(
                width = 2.dp,
                color = if (checked) Color.Blue else Color.Gray,
                shape = RoundedCornerShape(borderRadius.dp)
            )
            .background(
                color = if (checked) Color.Blue else Color.Transparent,
                shape = RoundedCornerShape(borderRadius.dp)
            )
            .clickable {
                onCheckedChange?.invoke(!checked)
            }
    ) {
        if (checked) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checked",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun rememberSaveableCheckboxes(count: Int): List<MutableState<Boolean>> {
    return List(count) { rememberSaveable { mutableStateOf(false) } }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showMenuView(){
    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
        Column {
            TopAppBar(
                title = { Text("Material3 Based Menu POC", modifier = Modifier.padding(horizontal = 30.dp)) },
                colors = TopAppBarColors(
                    Color(0xff0f6cbd), Color(0xff0f6cbd), Color(0xff0f6cbd),
                    Color.White,
                    Color(0xff0f6cbd)
                )
            )
            Spacer(modifier = Modifier.height(150.dp))
            Text("Checkbox Menu", fontSize = 14.sp, textAlign = TextAlign.Start, color = Color.Gray, modifier = Modifier.padding(start = 35.dp))
            Row (modifier = Modifier.padding(horizontal = 30.dp)) {
                var enable by rememberSaveable {mutableStateOf(false)}
                var checkBoxSelectedValues = rememberSaveableCheckboxes(4)
                var displayText by remember { mutableStateOf("Text")}
                Button(
                    onClick = { enable = true },
                    colors = ButtonDefaults.buttonColors(Color(0xff0f6cbd))
                ) {
                    Text("Show Checkbox Menu")
                }
                DropdownMenu(
                    expanded = enable,
                    onDismissRequest = { enable = false },
                    modifier = Modifier.width(200.dp).background(
                        Color.White
                    )
                ) {
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            checkBoxSelectedValues[0].value = !checkBoxSelectedValues[0].value
                        },
                        leadingIcon = {
                            Checkbox(checked = checkBoxSelectedValues[0].value, onCheckedChange = {
                                checkBoxSelectedValues[0].value = it
                            }, colors = CheckboxDefaults.colors(checkedColor = Color(0xff0f6cbd)))
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            checkBoxSelectedValues[1].value = !checkBoxSelectedValues[1].value
                        },
                        leadingIcon = {
                            Checkbox(checked = checkBoxSelectedValues[1].value, onCheckedChange = {
                                checkBoxSelectedValues[1].value = it
                            }, colors = CheckboxDefaults.colors(checkedColor = Color(0xff0f6cbd)))
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            checkBoxSelectedValues[2].value = !checkBoxSelectedValues[2].value
                        },
                        leadingIcon = {
                            Checkbox(checked = checkBoxSelectedValues[2].value, onCheckedChange = {
                                checkBoxSelectedValues[2].value = it
                            }, colors = CheckboxDefaults.colors(checkedColor = Color(0xff0f6cbd)))
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            checkBoxSelectedValues[3].value = !checkBoxSelectedValues[3].value
                        },
                        leadingIcon = {
                            Checkbox(checked = checkBoxSelectedValues[3].value, onCheckedChange = {
                                checkBoxSelectedValues[3].value = it
                            }, colors = CheckboxDefaults.colors(checkedColor = Color(0xff0f6cbd)))
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.height(200.dp))
            Text("Radio Button Menu", fontSize = 14.sp, textAlign = TextAlign.Start, color = Color.Gray, modifier = Modifier.padding(start = 35.dp))
            Row(modifier = Modifier.padding(horizontal = 30.dp)) {
                var enable by rememberSaveable {mutableStateOf(false)}
                var checkBoxSelectedValues = rememberSaveableCheckboxes(5)
                var displayText by remember { mutableStateOf("Text")}
                var displayTextAlt by remember { mutableStateOf("Text")}
                Button(
                    onClick = { enable = true },
                    colors = ButtonDefaults.buttonColors(Color(0xff0f6cbd))
                ) {
                    Text("Show Radio Button Menu")
                }
                DropdownMenu(
                    expanded = enable,
                    onDismissRequest = { enable = false },
                    modifier = Modifier.width(220.dp).background(
                        Color.White
                    )
                ) {
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            checkBoxSelectedValues[0].value = !checkBoxSelectedValues[0].value
                        },
                        leadingIcon = {
                            RadioButton(selected = checkBoxSelectedValues[0].value, onClick = {
                                checkBoxSelectedValues[0].value = !checkBoxSelectedValues[0].value
                            }, colors = RadioButtonDefaults.colors(selectedColor = Color(0xff0f6cbd)))
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            checkBoxSelectedValues[1].value = !checkBoxSelectedValues[1].value
                        },
                        leadingIcon = {
                            RadioButton(selected = checkBoxSelectedValues[1].value, onClick = {
                                checkBoxSelectedValues[1].value = !checkBoxSelectedValues[1].value
                            }, colors = RadioButtonDefaults.colors(selectedColor = Color(0xff0f6cbd)))
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(displayText) },
                        onClick = {
                            checkBoxSelectedValues[2].value = !checkBoxSelectedValues[2].value
                        },
                        leadingIcon = {
                            RadioButton(selected = checkBoxSelectedValues[2].value, onClick = {
                                checkBoxSelectedValues[2].value = !checkBoxSelectedValues[2].value
                            }, colors = RadioButtonDefaults.colors(selectedColor = Color(0xff0f6cbd)))
                        },
                        trailingIcon = {
                            Icon(Icons.Outlined.Info, contentDescription = "Info", tint = Color(0xff0f6cbd))
                        }
                    )
                    var iconColor by rememberSaveable { mutableLongStateOf(0xff0f6cbd) }
                    DropdownMenuItem(
                        text = { Text(displayTextAlt) },
                        onClick = {
                            displayTextAlt = if(displayTextAlt == "Text-1")
                                "Text-2"
                            else
                                "Text-1"
                        },
                        leadingIcon = {
                            Row() {
                                RadioButton(
                                    selected = checkBoxSelectedValues[3].value,
                                    onClick = {
                                        checkBoxSelectedValues[3].value =
                                            !checkBoxSelectedValues[3].value
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xff0f6cbd)
                                    )
                                )
                                Checkbox(checked = checkBoxSelectedValues[4].value, onCheckedChange = {
                                    checkBoxSelectedValues[4].value = it
                                }, colors = CheckboxDefaults.colors(checkedColor = Color(0xff0f6cbd)))

                            }
                        },
                        trailingIcon = {
                            Icon(Icons.Outlined.Info, contentDescription = "Info", tint = Color(iconColor), modifier = Modifier.clickable(onClick = {
                                if(iconColor == 0xffff6cbd)
                                    iconColor = 0xff0f6cbd
                                else
                                    iconColor = 0xffff6cbd
                            }))
                        }
                    )
                }
            }
        }
    }
}
