package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.ui.platform.ComposeView
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.theme.token.MyAliasToken
import com.example.theme.token.MyButtonToken
import com.example.theme.token.MyGlobalTokens
import com.microsoft.fluentui.button.CreateButton
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2BasicInputsActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_basic_inputs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val buttons = findViewById<ComposeView>(R.id.buttons)
        buttons.setContent {
            Column {

                FluentTheme.register(ControlType.Button, ButtonToken())
                Text("Default Button from provided base token & Auto theme")
                FluentTheme {
                    CreateButton()
                }

                FluentTheme(themeMode = ThemeMode.Colorful) {
                    CreateButton()
                }

                val myGlobalTokens = MyGlobalTokens()
                val myAliasTokens = MyAliasToken(globalTokens = myGlobalTokens)
                //Setting another theme with
                FluentTheme(myGlobalTokens, myAliasTokens) {
                    Text("Button within scope of new Alias, Global token applied theme")
                    CreateButton()

                    Text("Button with new buttonToken provided as parameter for different style")
                    CreateButton(MyButtonToken())

                    Text("Button with existing default token values")
                    CreateButton()

                    Text("Button with different style applied by registering new buttonToken as default")
                    FluentTheme.register(ControlType.Button, MyButtonToken())
                    CreateButton()
                }
            }
        }
    }

    @Composable
    fun icon(enabled:Boolean) : @Composable (RowScope.() -> Unit) {
        return {
            if (enabled)
                Icon(
                    Icons.Outlined.Favorite,
                    contentDescription = "Localized description",
                    tint = Color.Green
                )
            else
                Icon(
                    Icons.Outlined.ThumbUp,
                    contentDescription = "Localized description",
                    tint = Color.Green
                )
        }
    }

    @Composable
    fun CreateButton2(buttonToken: ButtonToken? = null) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            CreateButton(
                style = ButtonStyle.OutlinedButton,
                size = ButtonSize.Large,
                buttonToken = buttonToken,
                onClick = {},
                text = "test button"
            )
        }
    }

    @Composable
    fun CreateButton(buttonToken: ButtonToken? = null) {
        var enabled by rememberSaveable { mutableStateOf(true) }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)){
            CreateButton(
                style = ButtonStyle.Button,
                size = ButtonSize.Large,
                buttonToken = buttonToken,
                onClick = {enabled = !enabled},
                text = if(enabled) "Click to Disable" else "Click to Enable"
            )
        }

        Spacer(Modifier.height(20.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize(1.0F),horizontalAlignment = Alignment.CenterHorizontally) {
            var clicks by rememberSaveable { mutableStateOf(0) }
            val onClickLambda: () -> Unit = { clicks++ }
            val text = "Button $clicks"
            val toggleIcon = clicks%2 == 0

            if (clicks < 3) {
                CreateButton(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Large,
                    enabled = enabled,
                    buttonToken = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    text = text
                )
            }
            else{
                CreateButton(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Large,
                    enabled = enabled,
                    buttonToken = ButtonToken(),
                    icon = icon(toggleIcon),
                    onClick = onClickLambda,
                    text = text
                )
            }

            CreateButton(
                style = if(clicks < 3) ButtonStyle.Button else ButtonStyle.TextButton,
                size = if(clicks < 3) ButtonSize.Large else ButtonSize.Small,
                enabled = enabled,
                buttonToken = if(clicks < 3) buttonToken else ButtonToken(),
                onClick = onClickLambda,
                icon = icon(toggleIcon),
                text = text
            )

            CreateButton(
                style = ButtonStyle.Button,
                size = ButtonSize.Large,
                enabled = enabled,
                buttonToken = if(clicks < 3) buttonToken else ButtonToken(),
                onClick = onClickLambda,
                icon = icon(toggleIcon),
                text = "Large $text"
            )

            CreateButton(
                style = ButtonStyle.OutlinedButton,
                size = ButtonSize.Medium,
                enabled = enabled,
                buttonToken = buttonToken,
                onClick = onClickLambda,
                icon = icon(toggleIcon),
                text = "Outlined $text"
            )
            CreateButton(
                style = ButtonStyle.TextButton,
                size = ButtonSize.Small,
                enabled = enabled,
                buttonToken = buttonToken,
                onClick = onClickLambda,
                icon = icon(clicks%2 == 0),
                text = "Text $text"
            )
        }
    }
}