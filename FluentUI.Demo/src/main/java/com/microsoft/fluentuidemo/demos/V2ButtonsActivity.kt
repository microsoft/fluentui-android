package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.theme.token.MyAppBarToken
import com.example.theme.token.MyButtonTokens
import com.example.theme.token.MyFABToken
import com.example.theme.token.OneNoteAliasTokens
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.FABSize
import com.microsoft.fluentui.theme.token.controlTokens.FABState
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlinx.coroutines.selects.select

class V2ButtonsActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-11"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-11"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        setActivityContent {
            val controlTokens = ControlTokens()
            var fabState by rememberSaveable { mutableStateOf(FABState.Expanded) }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                FluentTheme {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        BasicText(
                            "Button to update Theme via Global & Alias token",
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode
                                )
                            )
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                5.dp,
                                Alignment.CenterHorizontally
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                style = ButtonStyle.OutlinedButton,
                                size = ButtonSize.Medium,
                                onClick = {
                                    FluentTheme.updateAliasTokens(AliasTokens())
                                    FluentTheme.updateControlTokens(
                                        controlTokens.updateToken(
                                            ControlTokens.ControlType.ButtonControlType,
                                            ButtonTokens()
                                        )
                                    )
                                },
                                text = "Theme 1"
                            )

                            Button(
                                style = ButtonStyle.OutlinedButton,
                                size = ButtonSize.Medium,
                                onClick = {
                                    FluentTheme.updateAliasTokens(OneNoteAliasTokens())
                                    FluentTheme.updateControlTokens(
                                        controlTokens.updateToken(
                                            ControlTokens.ControlType.ButtonControlType,
                                            MyButtonTokens()
                                        )
                                    )
                                },
                                text = "Theme 2"
                            )

                            Button(
                                style = ButtonStyle.OutlinedButton,
                                size = ButtonSize.Medium,
                                onClick = {
                                    FluentTheme.updateControlTokens(
                                        controlTokens.updateToken(
                                            ControlTokens.ControlType.AppBarControlType,
                                            MyAppBarToken()
                                        ).updateToken(
                                            ControlTokens.ControlType.FloatingActionButtonControlType,
                                            MyFABToken()
                                        )
                                    )
                                },
                                text = "Theme 3"
                            )
                        }
                    }
                }

                Divider()

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        BasicText(
                            "Activity level customization with Auto theme",
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode
                                )
                            )
                        )

                        // TODO Investigate better ways to save activity Theme state
                        // TODO One possible way is to use State Holders
                        var aliasTokens by rememberSaveable { mutableStateOf(AliasTokens()) }

                        FluentTheme(aliasTokens = aliasTokens, controlTokens = ControlTokens()) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    5.dp,
                                    Alignment.CenterVertically
                                )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        5.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Button(
                                        onClick = { aliasTokens = AliasTokens() },
                                        text = "Theme1"
                                    )
                                    Button(
                                        onClick = { aliasTokens = OneNoteAliasTokens() },
                                        text = "Theme2"
                                    )
                                }
                                CreateButtons()
                            }
                        }
                    }
                    item {
                        Divider()
                        FluentTheme {
                            BasicText(
                                "Button with selected theme, auto mode and overridden control token",
                                style = TextStyle(
                                    color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                        themeMode
                                    )
                                )
                            )
                            CreateButtons(MyButtonTokens())
                        }
                    }
                    item {
                        Divider()
                        var checkBoxSelectedValues = List(4) { rememberSaveable { mutableStateOf(false) } }
                        FluentTheme {
                            BasicText(
                                "Radio Button Group with selected theme",
                                style = TextStyle(
                                    color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                        themeMode
                                    )
                                )
                            )
                            for(i in 0..3) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)) {
                                    BasicText(
                                        "Text",
                                        style = TextStyle(
                                            color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                                themeMode
                                            )
                                        )
                                    )
                                    Spacer(Modifier.width(20.dp))
                                    RadioButton(
                                        onClick = {
                                            selectRadioGroupButton(i, checkBoxSelectedValues)
                                        },
                                        selected = checkBoxSelectedValues[i].value
                                    )
                                }
                            }
                        }
                    }
                }
            }
            FluentTheme {
                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .fillMaxSize()
                        .focusable(false)
                ) {
                    val fabText = "FAB Text"
                    FloatingActionButton(
                        size = FABSize.Small,
                        state = fabState,
                        onClick = {
                            val toastText: String
                            if (fabState == FABState.Expanded) {
                                toastText = "FAB Collapsed"
                                fabState = FABState.Collapsed
                            } else {
                                toastText = "FAB Expanded"
                                fabState = FABState.Expanded
                            }
                            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
                        },
                        icon = Icons.Filled.Email,
                        modifier = Modifier
                            .padding(16.dp)
                            .testTag("FAB"),
                        text = fabText,
                    )
                }
            }
        }
    }

    @Composable
    fun icon(enabled: Boolean): ImageVector {
        return if (enabled)
            Icons.Outlined.Favorite
        else
            Icons.Outlined.ThumbUp
    }

    @Composable
    fun CreateButtons(buttonToken: ButtonTokens? = null) {
        var enabled by rememberSaveable { mutableStateOf(true) }
        Column {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    modifier = Modifier.testTag("testButton"),
                    style = ButtonStyle.Button,
                    size = ButtonSize.Large,
                    buttonTokens = buttonToken,
                    onClick = { enabled = !enabled },
                    text = if (enabled) "Click to Disable" else "Click to Enable"
                )
            }

            Spacer(Modifier.height(20.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize(1.0F),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var clicks by rememberSaveable { mutableStateOf(0) }
                val onClickLambda: () -> Unit = { clicks++ }
                val text = "Button $clicks"
                val toggleIcon = clicks % 2 == 0

                Button(
                    style = if (clicks < 3) ButtonStyle.Button else ButtonStyle.TextButton,
                    size = if (clicks < 3) ButtonSize.Large else ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = if (clicks < 3) buttonToken else ButtonTokens(),
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    trailingIcon = icon(enabled = toggleIcon),
                    text = text
                )

                Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    trailingIcon = icon(enabled = toggleIcon),
                    text = "Long text displayed on this button. This Is long text."
                )

                Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    trailingIcon = icon(enabled = toggleIcon),
                    text = "Outlined $text"
                )
                Button(
                    style = ButtonStyle.TextButton,
                    size = ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(clicks % 2 == 0),
                    trailingIcon = icon( clicks %2 != 0 ),
                    text = "Text $text"
                )
            }
        }
    }
}

fun selectRadioGroupButton(buttonNumber: Int, saveableCheckbox: List<MutableState<Boolean>>){
    saveableCheckbox.forEachIndexed { index, mutableState ->
        mutableState.value = index == buttonNumber
    }
}
