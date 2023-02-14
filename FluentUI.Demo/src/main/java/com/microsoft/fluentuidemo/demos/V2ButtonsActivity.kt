package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.example.theme.token.MyAliasTokens
import com.example.theme.token.MyButtonTokens
import com.microsoft.fluentui.theme.AppThemeController
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2ButtonsActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)
        val context = this

        compose_here.setContent {
            var fabState by rememberSaveable { mutableStateOf(FABState.Expanded) }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                FluentTheme {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Button to update Theme via Global & Alias token",
                            color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                themeMode
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
                                    AppThemeController.updateAliasTokens(AliasTokens())
                                    AppThemeController.updateControlTokens(
                                        ControlTokens().updateTokens(
                                            ControlTokens.ControlType.Button,
                                            ButtonTokens()
                                        )
                                    )
                                },
                                text = "Set Default Theme"
                            )

                            Button(
                                style = ButtonStyle.OutlinedButton,
                                size = ButtonSize.Medium,
                                onClick = {
                                    AppThemeController.updateAliasTokens(MyAliasTokens())
                                    AppThemeController.updateControlTokens(
                                        ControlTokens().updateTokens(
                                            ControlTokens.ControlType.Button,
                                            MyButtonTokens()
                                        )
                                    )
                                },
                                text = "Set New Theme"
                            )
                        }
                    }
                }

                Divider()

                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        Text(
                            "Activity level customization with Auto theme",
                            color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                themeMode
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
                                        onClick = { aliasTokens = MyAliasTokens() },
                                        text = "Theme2"
                                    )
                                }
                                CreateButtons()
                            }
                        }
                    }

                    item {
                        FluentTheme {
                            Text(
                                "Button with selected theme, auto mode and overridden control token",
                                color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode
                                )
                            )
                            CreateButtons(MyButtonTokens())
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
                    val fabText: String? = "FAB Text"
                    FloatingActionButton(
                        size = FABSize.Small,
                        state = fabState,
                        onClick = {
                            var toastText = "No Text"
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
                        modifier = Modifier.padding(16.dp),
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

                // ButtonToken will not update here since it's a RememberSaveable parameter.
                Button(
                    style = if (clicks < 3) ButtonStyle.Button else ButtonStyle.TextButton,
                    size = if (clicks < 3) ButtonSize.Large else ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = if (clicks < 3) buttonToken else ButtonTokens(),
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    text = text
                )

                Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    text = "Long text displayed on this button. This Is long text."
                )

                Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(toggleIcon),
                    text = "Outlined $text"
                )
                Button(
                    style = ButtonStyle.TextButton,
                    size = ButtonSize.Small,
                    enabled = enabled,
                    buttonTokens = buttonToken,
                    onClick = onClickLambda,
                    icon = icon(clicks % 2 == 0),
                    text = "Text $text"
                )
            }
        }
    }
}
