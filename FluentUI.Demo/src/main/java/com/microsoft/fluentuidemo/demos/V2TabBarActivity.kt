package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.navigation.TabBar
import com.microsoft.fluentui.tokenized.navigation.TabData
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.invokeToast

const val TAB_BAR_VERTICAL_RADIO = "tabBarVerticalRadio"
const val TAB_BAR_HORIZONTAL_RADIO = "tabBarHorizontalRadio"
const val TAB_BAR_NO_TEXT_RADIO = "tabBarItemsNoTextRadio"
const val TAB_BAR_ADD_BUTTON = "tabBarAddButton"
const val TAB_BAR_REMOVE_BUTTON = "tabBarRemoveButton"
const val TAB_BAR = "tabBar"

class V2TabBarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-37"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-35"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        val _tabTextAlignment: MutableLiveData<TabTextAlignment> =
            MutableLiveData(TabTextAlignment.VERTICAL)
        val _tabShowIndicator: MutableLiveData<Boolean> =
            MutableLiveData(false)
        val _tabItemsCount: MutableLiveData<Int> = MutableLiveData(5)

        setActivityContent {
            val content = listOf(0, 1, 2)
            var selectedOption by rememberSaveable { mutableIntStateOf(content[0]) }
            val tabItemsCount = _tabItemsCount.observeAsState(initial = 5)
            var showIndicator by rememberSaveable {
                mutableStateOf(false)
            }

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
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                        )
                        RadioButton(
                            modifier = Modifier.testTag(TAB_BAR_VERTICAL_RADIO),
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
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                        )
                        RadioButton(
                            modifier = Modifier.testTag(TAB_BAR_HORIZONTAL_RADIO),
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
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                        )
                        RadioButton(
                            modifier = Modifier.testTag(TAB_BAR_NO_TEXT_RADIO),
                            selected = (selectedOption == content[2]),
                            onClick = {
                                selectedOption = content[2]
                                _tabTextAlignment.value = TabTextAlignment.NO_TEXT
                            }
                        )
                    }

                }
                ListItem.Header(title = "Show Indicator",
                    trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = {
                                showIndicator = it
                                _tabShowIndicator.value = showIndicator
                            },
                            modifier = Modifier.testTag(APP_BAR_SUBTITLE_PARAM),
                            checkedState = showIndicator
                        )
                    }
                )
                ListItem.Header(title = resources.getString(R.string.tabBar_tab_items),
                    trailingAccessoryContent =
                    {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                modifier = Modifier.testTag(TAB_BAR_ADD_BUTTON),
                                style = ButtonStyle.Button,
                                size = ButtonSize.Medium,
                                text = "+",
                                enabled = tabItemsCount.value < 5,
                                onClick = { _tabItemsCount.value = tabItemsCount.value + 1 })

                            Button(
                                modifier = Modifier.testTag(TAB_BAR_REMOVE_BUTTON),
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
            val showIndicator = _tabShowIndicator.observeAsState(initial = false)

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
                    badge = { if (selectedIndex == 0 && showHomeBadge) Badge() },
                    accessibilityDescription = resources.getString(R.string.tabBar_home) + ": " + if(selectedIndex == 0) {resources.getString(R.string.Active)} else {resources.getString(R.string.Inactive)}
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_mail),
                    icon = Icons.Outlined.Email,
                    selectedIcon = Icons.Filled.Email,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_mail), context)
                        selectedIndex = 1
                    },
                    badge = { Badge(text = "123+", badgeType = BadgeType.Character) },
                    accessibilityDescription = resources.getString(R.string.tabBar_mail) + ": " + if(selectedIndex == 1) {resources.getString(R.string.Active)} else {resources.getString(R.string.Inactive)}
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_settings),
                    icon = Icons.Outlined.Settings,
                    selectedIcon = Icons.Filled.Settings,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_settings), context)
                        selectedIndex = 2
                    },
                    accessibilityDescription = resources.getString(R.string.tabBar_settings) + ": " + if(selectedIndex == 2) {resources.getString(R.string.Active)} else {resources.getString(R.string.Inactive)}
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_notification),
                    icon = Icons.Outlined.Notifications,
                    selectedIcon = Icons.Filled.Notifications,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_notification), context)
                        selectedIndex = 3
                    },
                    badge = { Badge(text = "10", badgeType = BadgeType.Character) },
                    accessibilityDescription = resources.getString(R.string.tabBar_notification) + ": " + if(selectedIndex == 3) {resources.getString(R.string.Active)} else {resources.getString(R.string.Inactive)}
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_more),
                    icon = Icons.Outlined.List,
                    selectedIcon = Icons.Filled.List,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_more), context)
                        selectedIndex = 4
                    },
                    badge = { Badge() },
                    accessibilityDescription = resources.getString(R.string.tabBar_more) + ": " + if(selectedIndex == 4) {resources.getString(R.string.Active)} else {resources.getString(R.string.Inactive)}
                )
            )

            TabBar(
                modifier = Modifier.testTag(TAB_BAR),
                tabDataList = tabDataList.take(tabItemsCount.value),
                selectedIndex = selectedIndex,
                tabTextAlignment = tabTextAlignment.value,
                showIndicator = showIndicator.value
            )
        }
    }
}