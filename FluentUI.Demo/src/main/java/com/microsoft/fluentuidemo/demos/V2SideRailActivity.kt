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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.BadgeType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.navigation.SideRail
import com.microsoft.fluentui.tokenized.navigation.TabData
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.invokeToast

class V2SideRailActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-35"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-33"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        val _topTabItemsCount: MutableLiveData<Int> = MutableLiveData(4)
        val _bottomTabItemsCount: MutableLiveData<Int> = MutableLiveData(3)
        val _enableText: MutableLiveData<Boolean> = MutableLiveData(true)

        setActivityContent {
            val topTabItemsCount = _topTabItemsCount.observeAsState(initial = 3)
            val bottomTabItemsCount = _bottomTabItemsCount.observeAsState(initial = 2)
            var selected by rememberSaveable { mutableStateOf(true) }

            Column {
                ListItem.Header(title = "SideRail Style")
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
                            text = "Icon Only",
                            modifier = Modifier.weight(1F),
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                        )
                        RadioButton(
                            modifier = Modifier.testTag(TAB_BAR_VERTICAL_RADIO),
                            selected = !selected,
                            onClick = {
                                selected = false
                                _enableText.value = false
                            }
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BasicText(
                            text = "With Text",
                            modifier = Modifier.weight(1F),
                            style = TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = ThemeMode.Auto
                                )
                            )
                        )
                        RadioButton(
                            modifier = Modifier.testTag(TAB_BAR_VERTICAL_RADIO),
                            selected = selected,
                            onClick = {
                                selected = true
                                _enableText.value = true
                            }
                        )
                    }

                }
                ListItem.Header(title = "Top Tab Items",
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
                                enabled = topTabItemsCount.value < 4,
                                onClick = { _topTabItemsCount.value = topTabItemsCount.value + 1 })

                            Button(
                                modifier = Modifier.testTag(TAB_BAR_REMOVE_BUTTON),
                                style = ButtonStyle.Button,
                                size = ButtonSize.Medium,
                                text = "-",
                                enabled = topTabItemsCount.value > 1,
                                onClick = { _topTabItemsCount.value = topTabItemsCount.value - 1 }
                            )
                        }
                    }
                )
                ListItem.Header(title = "Bottom Tab Items",
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
                                enabled = bottomTabItemsCount.value < 3,
                                onClick = {
                                    _bottomTabItemsCount.value = bottomTabItemsCount.value + 1
                                })

                            Button(
                                modifier = Modifier.testTag(TAB_BAR_REMOVE_BUTTON),
                                style = ButtonStyle.Button,
                                size = ButtonSize.Medium,
                                text = "-",
                                enabled = bottomTabItemsCount.value > 1,
                                onClick = {
                                    _bottomTabItemsCount.value = bottomTabItemsCount.value - 1
                                }
                            )
                        }
                    }
                )
            }
        }
        setSideBar {
            var topSelectedIndex by rememberSaveable { mutableStateOf(0) }
            var bottomSelectedIndex by rememberSaveable { mutableStateOf(0) }
            var showHomeBadge by rememberSaveable { mutableStateOf(true) }
            val topTabItemsCount = _topTabItemsCount.observeAsState(initial = 3)
            val bottomTabItemsCount = _bottomTabItemsCount.observeAsState(initial = 2)
            val enableText = _enableText.observeAsState(initial = false)
            val topTabDataList = arrayListOf(
                TabData(
                    title = resources.getString(R.string.tabBar_home),
                    icon = Icons.Outlined.Home,
                    selectedIcon = Icons.Filled.Home,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_home), context)
                        topSelectedIndex = 0
                        showHomeBadge = false
                    },
                    badge = { if (topSelectedIndex == 0 && showHomeBadge) Badge() }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_mail),
                    icon = Icons.Outlined.Email,
                    selectedIcon = Icons.Filled.Email,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_mail), context)
                        topSelectedIndex = 1
                    },
                    badge = { Badge(text = "123+", badgeType = BadgeType.Character) }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_settings),
                    icon = Icons.Outlined.Settings,
                    selectedIcon = Icons.Filled.Settings,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_settings), context)
                        topSelectedIndex = 2
                    }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_more),
                    icon = Icons.Outlined.MoreVert,
                    selectedIcon = Icons.Filled.MoreVert,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_more), context)
                        topSelectedIndex = 3
                    },
                    badge = { Badge(text = "10", badgeType = BadgeType.Character) }
                )
            )
            val bottomTabDataList = arrayListOf(
                TabData(
                    title = resources.getString(R.string.tabBar_home),
                    icon = Icons.Outlined.Home,
                    selectedIcon = Icons.Filled.Home,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_home), context)
                        bottomSelectedIndex = 0
                        showHomeBadge = false
                    },
                    badge = { if (topSelectedIndex == 0 && showHomeBadge) Badge() }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_mail),
                    icon = Icons.Outlined.Email,
                    selectedIcon = Icons.Filled.Email,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_mail), context)
                        bottomSelectedIndex = 1
                    },
                    badge = { Badge(text = "123+", badgeType = BadgeType.Character) }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_settings),
                    icon = Icons.Outlined.Settings,
                    selectedIcon = Icons.Filled.Settings,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_settings), context)
                        bottomSelectedIndex = 2
                    }
                )
            )

            SideRail(
                modifier = Modifier.testTag(TAB_BAR),
                header = {
                    Avatar(
                        person = Person(
                            "Amanda", "Brady",
                            image = R.drawable.avatar_amanda_brady,
                            status = AvatarStatus.Available
                        )
                    )
                },
                topTabDataList = topTabDataList.take(topTabItemsCount.value),
                bottomTabDataList = bottomTabDataList.take(bottomTabItemsCount.value),
                topTabSelectedIndex = topSelectedIndex,
                bottomTabSelectedIndex = bottomSelectedIndex,
                showIconText = enableText.value,
            )
        }
    }
}