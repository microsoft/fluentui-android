package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.AppThemeController
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.BadgeType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.TabTextAlignment
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.navigation.TabBar
import com.microsoft.fluentui.tokenized.navigation.TabData
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import com.microsoft.fluentuidemo.util.invokeToast

class V2TabBarActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(LayoutInflater.from(container.context), container,true)
        v2ActivityComposeBinding.composeHere.setContent {
            var selectedIndex by rememberSaveable { mutableStateOf(0) }
            var showHomeBadge by rememberSaveable { mutableStateOf(true) }
            val tabDataList = arrayListOf(
                TabData(
                    title = resources.getString(R.string.tabBar_home),
                    icon = Icons.Outlined.Home,
                    selectedIcon = Icons.Filled.Home,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_home), context)
                        selectedIndex = 0
                        showHomeBadge = false
                    },
                    badge = { if (selectedIndex == 0 && showHomeBadge) Badge() else null }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_mail),
                    icon = Icons.Outlined.Email,
                    selectedIcon = Icons.Filled.Email,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_mail), context)
                        selectedIndex = 1
                    },
                    badge = { Badge("123+", badgeType = BadgeType.Character) }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_settings),
                    icon = Icons.Outlined.Settings,
                    selectedIcon = Icons.Filled.Settings,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_settings), context)
                        selectedIndex = 2
                    }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_notification),
                    icon = Icons.Outlined.Notifications,
                    selectedIcon = Icons.Filled.Notifications,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_notification), context)
                        selectedIndex = 3
                    },
                    badge = { Badge("10", badgeType = BadgeType.Character) }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_more),
                    icon = Icons.Outlined.List,
                    selectedIcon = Icons.Filled.List,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_more), context)
                        selectedIndex = 4
                    },
                    badge = { Badge() }
                )
            )
            FluentTheme {
                val content = listOf(0, 1, 2)
                var selectedOption by rememberSaveable { mutableStateOf(content[0]) }
                var tabTextAlignment by rememberSaveable { mutableStateOf(TabTextAlignment.VERTICAL) }
                var tabItemsCount by rememberSaveable { mutableStateOf(5) }

                Column {
                    ListItem.Header(title = resources.getString(R.string.tabBar_text_alignment))
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = resources.getString(R.string.tabBar_vertical),
                                modifier = Modifier.weight(1F),
                                color = AppThemeController.aliasTokens.value!!.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                            RadioButton(
                                selected = (selectedOption == content[0]),
                                onClick = {
                                    selectedOption = content[0]
                                    tabTextAlignment = TabTextAlignment.VERTICAL
                                }
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = resources.getString(R.string.tabBar_horizontal),
                                modifier = Modifier.weight(1F),
                                color = AppThemeController.aliasTokens.value!!.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                            RadioButton(
                                selected = (selectedOption == content[1]),
                                onClick = {
                                    selectedOption = content[1]
                                    tabTextAlignment = TabTextAlignment.HORIZONTAL
                                }
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = resources.getString(R.string.tabBar_no_text),
                                modifier = Modifier.weight(1F),
                                color = AppThemeController.aliasTokens.value!!.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                            RadioButton(
                                selected = (selectedOption == content[2]),
                                onClick = {
                                    selectedOption = content[2]
                                    tabTextAlignment = TabTextAlignment.NO_TEXT
                                }
                            )
                        }

                    }
                    ListItem.Header(title = resources.getString(R.string.tabBar_tab_items),
                        trailingAccessoryView =
                        {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    style = ButtonStyle.Button,
                                    size = ButtonSize.Medium,
                                    text = "+",
                                    enabled = tabItemsCount < 5,
                                    onClick = { tabItemsCount++ })

                                Button(
                                    style = ButtonStyle.Button,
                                    size = ButtonSize.Medium,
                                    text = "-",
                                    enabled = tabItemsCount > 1,
                                    onClick = { tabItemsCount-- })

                            }
                        }
                    )
                }
                Column(verticalArrangement = Arrangement.Bottom) {
                    TabBar(
                        tabDataList = tabDataList.take(tabItemsCount),
                        selectedIndex = selectedIndex,
                        tabTextAlignment = tabTextAlignment
                    )
                }
            }
        }
    }
}