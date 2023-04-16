package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.AppThemeController
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Menu
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding


class V2MenuActivity : DemoActivity() {

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        v2ActivityComposeBinding.composeHere.setContent {
            FluentTheme {
                CreateMenuActivityUI(context)
            }
        }
    }
}

@Composable
fun CreateMenuActivityUI(context: Context) {
    val xOffsetState = remember { mutableStateOf("0") }
    val yOffsetState = remember { mutableStateOf("0") }
    val repeatContentTextCountState = remember { mutableStateOf("4") }
    val contentTextState =
        remember { mutableStateOf(context.getString(R.string.menu_content_text_input)) }
    Column {
        Column {
            ListItem.Header(title = context.getString(R.string.menu_xOffset),
                trailingAccessoryView = {
                    BasicTextField(value = xOffsetState.value,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChange = { xOffsetState.value = it.trim() })
                }
            )
            ListItem.Header(title = context.getString(R.string.menu_yOffset),
                trailingAccessoryView = {
                    BasicTextField(value = yOffsetState.value,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChange = { yOffsetState.value = it.trim() })
                })
            ListItem.Header(title = context.getString(R.string.menu_content_text),
                trailingAccessoryView = {
                    BasicTextField(
                        value = contentTextState.value,
                        onValueChange = { contentTextState.value = it })
                })
            ListItem.Header(title = context.getString(R.string.menu_repeat_content_text),
                trailingAccessoryView = {
                    BasicTextField(value = repeatContentTextCountState.value,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        onValueChange = { repeatContentTextCountState.value = it.trim() })
                })
            ListItem.SectionDescription(description = context.getString(R.string.menu_description))
        }
        val xOffset =
            if (xOffsetState.value == "" || xOffsetState.value.toFloatOrNull() == null) 0.dp else xOffsetState.value.toFloat()
                .toInt().dp
        val yOffset =
            if (yOffsetState.value == "" || yOffsetState.value.toFloatOrNull() == null) 0.dp else yOffsetState.value.toFloat()
                .toInt().dp
        val repeat =
            if (repeatContentTextCountState.value == "" || repeatContentTextCountState.value.toFloatOrNull() == null) 0 else repeatContentTextCountState.value.toFloat()
                .toInt()
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            MenuRow(context, xOffset, yOffset, contentTextState.value, repeat)
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
                MenuRow(context, xOffset, yOffset, contentTextState.value, repeat)
                MenuRow(context, xOffset, yOffset, contentTextState.value, repeat)
            }
        }
    }
}

@Composable
fun MenuRow(context: Context, xOffset: Dp, yOffset: Dp, contentText: String, repeat: Int) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
        Menu(context, xOffset, yOffset, contentText, repeat)
        Menu(context, xOffset, yOffset, contentText, repeat)
        Menu(context, xOffset, yOffset, contentText, repeat)
    }
}

@Composable
fun Menu(context: Context, xOffset: Dp, yOffset: Dp, contentText: String, count: Int) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        com.microsoft.fluentui.tokenized.controls.Button(
            onClick = { expanded = true },
            text = context.getString(R.string.menu_open_menu_button)
        )
        Menu(
            opened = expanded,
            offset = DpOffset(xOffset, yOffset),
            onDismissRequest = { expanded = false }
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                repeat(count) {
                    Text(
                        text = "$contentText ${it + 1}",
                        color = AppThemeController.aliasTokens.value!!.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                }
            }
        }
    }
}