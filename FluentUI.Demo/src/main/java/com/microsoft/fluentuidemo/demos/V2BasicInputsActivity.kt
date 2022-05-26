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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.theme.token.MyAliasTokens
import com.example.theme.token.MyButtonTokens
import com.example.theme.token.MyGlobalTokens
import com.microsoft.fluentui.button.CreateButton
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2BasicInputsActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_basic_inputs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(ThemeViewModel::class.java)

        val buttons = findViewById<ComposeView>(R.id.buttons)
        buttons.setContent {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                val globalTokens : GlobalTokens by viewModel.globalTokens.observeAsState(initial = GlobalTokens())
                val aliasTokens : AliasTokens by viewModel.aliasTokens.observeAsState(initial = AliasTokens())

                FluentTheme.register(ControlType.Button, ButtonTokens())
                Text("Button to update Theme via Global & Alias token")

                FluentTheme(globalTokens = globalTokens, aliasTokens = aliasTokens) {
                    Row (horizontalArrangement = Arrangement.spacedBy(5.dp)){
                        CreateButton(
                            style = ButtonStyle.OutlinedButton,
                            size = ButtonSize.Large,
                            buttonTokens = ButtonTokens(),
                            onClick = {
                                viewModel.onAliasChanged(AliasTokens())
                                viewModel.onGlobalChanged(GlobalTokens())
                            },
                            text = "Set Default Theme"
                        )

                        CreateButton(
                            style = ButtonStyle.OutlinedButton,
                            size = ButtonSize.Large,
                            buttonTokens = ButtonTokens(),
                            onClick = {
                                viewModel.onAliasChanged(MyAliasTokens(MyGlobalTokens()))
                                viewModel.onGlobalChanged(MyGlobalTokens())
                            },
                            text = "Set New Theme"
                        )
                    }
                }

                FluentTheme(globalTokens = globalTokens, aliasTokens = aliasTokens, themeMode = ThemeMode.Colorful) {
                    Text("Button with Colorful mode")
                    CreateButton()
                }

                FluentTheme(globalTokens = globalTokens, aliasTokens = aliasTokens) {
                    Text("Button with existing default token values")
                    CreateButton()

                    Text("Button with new buttonToken provided as parameter for different style")
                    CreateButton(MyButtonTokens())

                    Text("Button with different style applied by registering new buttonToken as default")
                    FluentTheme.register(ControlType.Button, MyButtonTokens())
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
    fun CreateButton2(buttonToken: ButtonTokens? = null) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            CreateButton(
                style = ButtonStyle.OutlinedButton,
                size = ButtonSize.Large,
                buttonTokens = buttonToken,
                onClick = {},
                text = "test button"
            )
        }
    }

    @Composable
    fun CreateButton(buttonToken: ButtonTokens? = null) {
        var enabled by rememberSaveable { mutableStateOf(true) }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)){
            CreateButton(
                style = ButtonStyle.Button,
                size = ButtonSize.Large,
                buttonTokens = buttonToken,
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
                    buttonTokens = buttonToken,
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
                    buttonTokens = ButtonTokens(),
                    icon = icon(toggleIcon),
                    onClick = onClickLambda,
                    text = text
                )
            }

            CreateButton(
                style = if(clicks < 3) ButtonStyle.Button else ButtonStyle.TextButton,
                size = if(clicks < 3) ButtonSize.Large else ButtonSize.Small,
                enabled = enabled,
                buttonTokens = if(clicks < 3) buttonToken else ButtonTokens(),
                onClick = onClickLambda,
                icon = icon(toggleIcon),
                text = text
            )

            CreateButton(
                style = ButtonStyle.Button,
                size = ButtonSize.Large,
                enabled = enabled,
                buttonTokens = if(clicks < 3) buttonToken else ButtonTokens(),
                onClick = onClickLambda,
                icon = icon(toggleIcon),
                text = "Large $text"
            )

            CreateButton(
                style = ButtonStyle.OutlinedButton,
                size = ButtonSize.Medium,
                enabled = enabled,
                buttonTokens = buttonToken,
                onClick = onClickLambda,
                icon = icon(toggleIcon),
                text = "Outlined $text"
            )
            CreateButton(
                style = ButtonStyle.TextButton,
                size = ButtonSize.Small,
                enabled = enabled,
                buttonTokens = buttonToken,
                onClick = onClickLambda,
                icon = icon(clicks%2 == 0),
                text = "Text $text"
            )
        }
    }
}

class ThemeViewModel : ViewModel(){
    private val _aliasTokens: MutableLiveData<AliasTokens> = MutableLiveData(AliasTokens())

    private val _globalTokens: MutableLiveData<GlobalTokens> = MutableLiveData(GlobalTokens())

    val aliasTokens : LiveData<AliasTokens> = _aliasTokens
    val globalTokens : LiveData<GlobalTokens> = _globalTokens

    fun onAliasChanged(aliasTokens: AliasTokens){
        _aliasTokens.value = aliasTokens
    }
    fun onGlobalChanged(globalTokens: GlobalTokens){
        _globalTokens.value = globalTokens
    }
}