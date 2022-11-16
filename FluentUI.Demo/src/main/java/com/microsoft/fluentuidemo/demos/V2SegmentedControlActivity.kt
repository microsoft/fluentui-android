package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import com.microsoft.fluentui.tokenized.listitem.ListItem
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.AllIcons
import com.microsoft.fluentui.icons.AvatarIcons
import com.microsoft.fluentui.icons.avataricons.Icon
import com.microsoft.fluentui.icons.avataricons.icon.Anonymous
import com.microsoft.fluentui.icons.avataricons.icon.anonymous.Large
import com.microsoft.fluentui.icons.avataricons.icon.anonymous.Medium
import com.microsoft.fluentui.icons.avataricons.icon.anonymous.Xxlarge
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonStyle
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.segmentedcontrols.*
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2SegmentedControlActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)
        val context = this

        compose_here.setContent {
            FluentTheme {
                LazyColumn (
                    Modifier
                        .fillMaxWidth()
                        .background(
                            FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                FluentTheme.themeMode
                            )
                        ),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        var enabled by remember { mutableStateOf(true) }
                        var selected by remember { mutableStateOf(false) }
                        var selectedIcon by remember { mutableStateOf(false) }
                        var selectedBrand by remember { mutableStateOf(false) }
                        var selectedBrandIcon by remember { mutableStateOf(false) }

                        template(
                            "Pill Button",
                            enableSwitch = {
                                ToggleSwitch(
                                    Modifier.padding(vertical = 3.dp),
                                    onValueChange = { enabled = it },
                                    checkedState = enabled
                                )
                            },
                            neutralBackgroundColor = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                                FluentTheme.themeMode
                            ),
                            neutralContent = {
                                PillButton(
                                    PillMetaData(
                                        "Neutral 1",
                                        { selected = !selected },
                                        selected = selected,
                                        enabled = enabled
                                    )
                                )
                                PillButton(
                                    PillMetaData(
                                        "Neutral",
                                        { selectedIcon = !selectedIcon },
                                        icon = AvatarIcons.Icon.Anonymous.Xxlarge,
                                        selected = selectedIcon,
                                        enabled = enabled
                                    )
                                )
                            },
                            brandBackgroundColor = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                                FluentTheme.themeMode
                            ),
                            brandContent = {
                                PillButton(
                                    PillMetaData(
                                        "Brand",
                                        { selectedBrand = !selectedBrand },
                                        selected = selectedBrand,
                                        enabled = enabled
                                    ),
                                    style = PillButtonStyle.Brand
                                )
                                PillButton(
                                    PillMetaData(
                                        "Brand",
                                        { selectedBrandIcon = !selectedBrandIcon },
                                        icon = AvatarIcons.Icon.Anonymous.Xxlarge,
                                        selected = selectedBrandIcon,
                                        enabled = enabled
                                    ),
                                    style = PillButtonStyle.Brand,
                                )
                            }
                        )
                    }

                    item {
                        var enableBar by remember { mutableStateOf(true) }
                        var selectedList = remember {
                            mutableStateListOf(
                                false,
                                false,
                                false,
                                false,
                                false,
                                false
                            )
                        }

                        var pillList: MutableList<PillMetaData> = mutableListOf()

                        for (idx in 0..5) {
                            val label = "Neutral ${idx + 1}"
                            pillList.add(
                                PillMetaData(
                                    text = label,
                                    icon = if (idx % 2 == 1) AvatarIcons.Icon.Anonymous.Xxlarge else null,
                                    onClick = {
                                        Toast.makeText(
                                            context,
                                            "Button " + (idx + 1).toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        selectedList[idx] = !selectedList[idx]
                                    },
                                    enabled = enableBar,
                                    selected = selectedList[idx]
                                )
                            )
                        }


                        template(
                            "Pill Bar",
                            enableSwitch = {
                                ToggleSwitch(
                                    Modifier.padding(vertical = 3.dp),
                                    onValueChange = { enableBar = it },
                                    checkedState = enableBar
                                )
                            },
                            neutralContent = {
                                PillBar(
                                    pillList
                                )
                            },
                            brandContent = {
                                PillBar(
                                    pillList,
                                    style = PillButtonStyle.Brand
                                )
                            }
                        )
                    }

                    item {
                        var enableTabs by remember { mutableStateOf(true) }
                        var selectedTab by remember { mutableStateOf("") }

                        var tabsList: MutableList<PillMetaData> = mutableListOf()

                        for (idx in 0..5) {
                            val label = "Neutral ${idx + 1}"
                            tabsList.add(
                                PillMetaData(
                                    text = label,
                                    icon = if (idx % 2 == 1) AvatarIcons.Icon.Anonymous.Xxlarge else null,
                                    onClick = {
                                        Toast.makeText(
                                            context,
                                            "Button " + (idx + 1).toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        selectedTab = label
                                    },
                                    enabled = enableTabs,
                                )
                            )
                        }

                        template(
                            "Tabs",
                            enableSwitch = {
                                ToggleSwitch(
                                    Modifier.padding(vertical = 3.dp),
                                    onValueChange = { enableTabs = it },
                                    checkedState = enableTabs
                                )
                            },
                            neutralContent = {
                                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Tabs(
                                        tabsList.subList(0, 4),
                                        selected = selectedTab,
                                        scrollable = true
                                    )
                                    Tabs(
                                        tabsList.subList(0, 4),
                                        style = PillButtonStyle.Brand,
                                        selected = selectedTab,
                                        scrollable = false
                                    )
                                }
                            },
                            brandContent = {
                                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Tabs(
                                        tabsList,
                                        selected = selectedTab,
                                        scrollable = true
                                    )
                                    Tabs(
                                        tabsList,
                                        style = PillButtonStyle.Brand,
                                        selected = selectedTab,
                                        scrollable = false
                                    )
                                }
                            }
                        )
                    }

                    item {
                        var enableSwitch by remember { mutableStateOf(true) }
                        var selectedSwitch by remember { mutableStateOf("") }

                        var switchList: MutableList<PillMetaData> = mutableListOf()

                        for (idx in 0..2) {
                            val label = "Neutral ${idx + 1}"
                            switchList.add(
                                PillMetaData(
                                    text = label,
                                    icon = if(idx % 2 == 1) AvatarIcons.Icon.Anonymous.Xxlarge else null,
                                    onClick = {
                                        Toast.makeText(
                                            context,
                                            "Button " + (idx + 1).toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        selectedSwitch = label
                                    },
                                    enabled = enableSwitch,
                                )
                            )
                        }

                        template(
                            "Switch",
                            enableSwitch = {
                                ToggleSwitch(
                                    Modifier.padding(vertical = 3.dp),
                                    onValueChange = { enableSwitch = it },
                                    checkedState = enableSwitch
                                )
                            },
                            neutralContent = {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(5.dp),
                                ) {
                                    Switch(
                                        switchList.subList(0, 2),
                                        selected = selectedSwitch
                                    )
                                    Switch(
                                        switchList.subList(0, 2),
                                        style = PillButtonStyle.Brand,
                                        selected = selectedSwitch
                                    )
                                }
                            },
                            brandContent = {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(5.dp),
                                ) {
                                    Switch(
                                        switchList,
                                        selected = selectedSwitch
                                    )
                                    Switch(
                                        switchList,
                                        style = PillButtonStyle.Brand,
                                        selected = selectedSwitch
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }

    }

    @Composable
    private fun template(
        label: String,
        enableSwitch: (@Composable () -> Unit),
        neutralContent: (@Composable RowScope.() -> Unit),
        brandContent: (@Composable RowScope.() -> Unit),
        neutralBackgroundColor: Color = Color.Transparent,
        brandBackgroundColor: Color = Color.Transparent
    ) {
        ListItem.SectionHeader(
            title = label,
            enableChevron = true,
            enableContentOpenCloseTransition = true,
            chevronOrientation = ChevronOrientation(90f, 0f),
            trailingAccessoryView = enableSwitch
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(neutralBackgroundColor)
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    content = neutralContent
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(brandBackgroundColor)
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    ),
                    content = brandContent
                )
            }
        }
    }
}