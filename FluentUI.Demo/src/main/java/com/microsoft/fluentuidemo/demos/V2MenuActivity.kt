package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Menu
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity

val DefaultMenuInputWidthFraction = 0.35f

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
    val xOffsetState = rememberSaveable { mutableStateOf("0") }
    val yOffsetState = rememberSaveable { mutableStateOf("0") }
    val repeatContentTextCountState = rememberSaveable { mutableStateOf("4") }
    val contentTextState =
        rememberSaveable { mutableStateOf(context.getString(R.string.menu_content_text_input)) }
    Column {
        Column {
            ListItem.Header(title = context.getString(R.string.menu_xOffset),
                titleMaxLines = 2,
                trailingAccessoryContent = {
                    BasicTextField(value = xOffsetState.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { xOffsetState.value = it.trim() },
                        modifier = Modifier.background(Color.White).fillMaxWidth(fraction = DefaultMenuInputWidthFraction))
                }
            )
            ListItem.Header(title = context.getString(R.string.menu_yOffset),
                titleMaxLines = 2,
                trailingAccessoryContent = {
                    BasicTextField(value = yOffsetState.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { yOffsetState.value = it.trim() },
                        modifier = Modifier.background(Color.White).fillMaxWidth(fraction = DefaultMenuInputWidthFraction))
                })
            ListItem.Header(title = context.getString(R.string.menu_content_text),
                titleMaxLines = 2,
                trailingAccessoryContent = {
                    BasicTextField(
                        value = contentTextState.value,
                        onValueChange = { contentTextState.value = it },
                        modifier = Modifier.background(Color.White).fillMaxWidth(fraction = DefaultMenuInputWidthFraction))
                })
            ListItem.Header(title = context.getString(R.string.menu_repeat_content_text),
                titleMaxLines = 2,
                trailingAccessoryContent = {
                    BasicTextField(value = repeatContentTextCountState.value,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { repeatContentTextCountState.value = it.trim() },
                        modifier = Modifier.background(Color.White).fillMaxWidth(fraction = DefaultMenuInputWidthFraction))
                })
            ListItem.SectionDescription(description = context.getString(R.string.menu_description))
        }
        val xOffset =
            if (xOffsetState.value.toFloatOrNull() == null) 0.dp else xOffsetState.value.toFloat().dp
        val yOffset =
            if (yOffsetState.value.toFloatOrNull() == null) 0.dp else yOffsetState.value.toFloat().dp
        val repeat =
            if (repeatContentTextCountState.value == "" || repeatContentTextCountState.value.toIntOrNull() == null) 0 else repeatContentTextCountState.value.toInt()

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
                    BasicText(
                        text = "$contentText ${it + 1}",
                        style = TextStyle(
                            color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                themeMode = FluentTheme.themeMode
                            )
                        )
                    )
                }
            }
        }
    }
}