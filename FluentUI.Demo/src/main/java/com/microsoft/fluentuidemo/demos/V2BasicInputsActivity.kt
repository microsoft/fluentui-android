package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.theme.token.MyAliasToken
import com.example.theme.token.MyButtonToken
import com.example.theme.token.MyGlobalTokens
import com.microsoft.fluentui.button.CreateButton
import com.microsoft.fluentui.theme.FluentTheme
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
                Text("Default Button from provided base theme")
                FluentTheme() {
                    CreateDefaultButton()
                }

                val myGlobalTokens = MyGlobalTokens()
                val myAliasTokens = MyAliasToken(globalTokens = myGlobalTokens)
                //Setting another theme with
                FluentTheme(myGlobalTokens, myAliasTokens) {
                    Text("Button after theme Applied with different Alias, Global token")
                    CreateDefaultButton()

                    Text("Button after another style Applied on consuming buttonToken:")
                    CreateControlTokenAppliedButton(MyButtonToken())

//                    Text("Button with new Theme Applied provides default values")
//                    CreateDefaultButton()
//
//                    Text("Button with different style applied by registering new buttonToken as default")
//                    FluentTheme.register(ControlType.Button, MyButtonToken())
//                    CreateDefaultButton()
//
//                    Text("Button with Override value of default token")
//                    CreateControlTokenAppliedButton(object : MyButtonToken(){
//                        @Composable
//                        public override fun background() : Color {
//                            return Color.White
//                        }
//
//                        @Composable
//                        public override fun contentColor() : Color {
//                            return Color.Black
//                        }
//                    })
                }
            }
        }
    }

    @Composable
    fun CreateDefaultButton() {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            var clicks by remember { mutableStateOf(0) }
            CreateButton(
                type = ButtonType.Button,
                style = ButtonStyle.Primary,
                size = ButtonSize.Medium,
                onClick = { clicks++ }) {
                Text(text = "Button $clicks")
            }
        }
    }

    @Composable
    fun CreateControlTokenAppliedButton(buttonToken: ButtonToken) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            var clicks by remember { mutableStateOf(0) }
                CreateButton(
                    type = ButtonType.Button,
                    style = ButtonStyle.Primary,
                    size = ButtonSize.Medium,
                    buttonToken = buttonToken,
                    onClick = { clicks++ }) {
                    Text(text = "Button $clicks")
                }
            }
    }
}