package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
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
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.invokeToast

class V2TabBarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        val _tabTextAlignment: MutableLiveData<TabTextAlignment> =
            MutableLiveData(TabTextAlignment.VERTICAL)
        val _tabItemsCount: MutableLiveData<Int> = MutableLiveData(5)

        setActivityContent {
            val content = listOf(0, 1, 2)
            var selectedOption by rememberSaveable { mutableStateOf(content[0]) }
            val tabItemsCount = _tabItemsCount.observeAsState(initial = 5)

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
                        BasicText(
                            text = resources.getString(R.string.tabBar_vertical),
                            modifier = Modifier.weight(1F),
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                        )
                        RadioButton(
                            selected = (selectedOption == content[0]),
                            onClick = {
                                selectedOption = content[0]
                                _tabTextAlignment.value = TabTextAlignment.VERTICAL
                            }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BasicText(
                            text = resources.getString(R.string.tabBar_horizontal),
                            modifier = Modifier.weight(1F),
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                        )
                        RadioButton(
                            selected = (selectedOption == content[1]),
                            onClick = {
                                selectedOption = content[1]
                                _tabTextAlignment.value = TabTextAlignment.HORIZONTAL
                            }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BasicText(
                            text = resources.getString(R.string.tabBar_no_text),
                            modifier = Modifier.weight(1F),
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                        )
                        RadioButton(
                            selected = (selectedOption == content[2]),
                            onClick = {
                                selectedOption = content[2]
                                _tabTextAlignment.value = TabTextAlignment.NO_TEXT
                            }
                        )
                    }

                }
                ListItem.Header(title = resources.getString(R.string.tabBar_tab_items),
                    trailingAccessoryContent =
                    {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                style = ButtonStyle.Button,
                                size = ButtonSize.Medium,
                                text = "+",
                                enabled = tabItemsCount.value < 5,
                                onClick = { _tabItemsCount.value = tabItemsCount.value + 1 })

                            Button(
                                style = ButtonStyle.Button,
                                size = ButtonSize.Medium,
                                text = "-",
                                enabled = tabItemsCount.value > 1,
                                onClick = { _tabItemsCount.value = tabItemsCount.value - 1 }
                            )
                        }
                    }
                )
            }
        }

        setBottomBar {
            var selectedIndex by rememberSaveable { mutableStateOf(0) }
            var showHomeBadge by rememberSaveable { mutableStateOf(true) }
            val tabTextAlignment =
                _tabTextAlignment.observeAsState(initial = TabTextAlignment.VERTICAL)
            val tabItemsCount = _tabItemsCount.observeAsState(initial = 5)

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
                    badge = { if (selectedIndex == 0 && showHomeBadge) Badge() }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_mail),
                    icon = Icons.Outlined.Email,
                    selectedIcon = Icons.Filled.Email,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_mail), context)
                        selectedIndex = 1
                    },
                    badge = { Badge(text = "123+", badgeType = BadgeType.Character) }
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
                    badge = { Badge(text = "10", badgeType = BadgeType.Character) }
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

            TabBar(
                tabDataList = tabDataList.take(tabItemsCount.value),
                selectedIndex = selectedIndex,
                tabTextAlignment = tabTextAlignment.value
            )
        }

        setBottomSheetContent {
            val mUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls"
            BottomSheetWebView(mUrl)
        }
    }
}