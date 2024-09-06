package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.AvatarIcons
import com.microsoft.fluentui.icons.avataricons.Icon
import com.microsoft.fluentui.icons.avataricons.icon.Anonymous
import com.microsoft.fluentui.icons.avataricons.icon.anonymous.Xxlarge
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.segmentedcontrols.*
import com.microsoft.fluentuidemo.V2DemoActivity

// Tags used for testing
const val SEGMENTED_CONTROL_PILL_BUTTON = "Segmented Control Pill Button"
const val SEGMENTED_CONTROL_PILL_BAR = "Segmented Control Pill Bar"
const val SEGMENTED_CONTROL_TABS = "Segmented Control Tabs"
const val SEGMENTED_CONTROL_SWITCH = "Segmented Control Switch"
const val SEGMENTED_CONTROL_PILL_BUTTON_TOGGLE = "Segmented Control Pill Button Toggle"
const val SEGMENTED_CONTROL_PILL_BAR_TOGGLE = "Segmented Control Pill Bar Toggle"
const val SEGMENTED_CONTROL_TABS_TOGGLE = "Segmented Control Tabs Toggle"
const val SEGMENTED_CONTROL_SWITCH_TOGGLE = "Segmented Control Switch Toggle"
const val SEGMENTED_CONTROL_PILL_BUTTON_COMPONENT = "Segmented Control Pill Button Component"
const val SEGMENTED_CONTROL_PILL_BAR_COMPONENT = "Segmented Control Pill Bar Component"
const val SEGMENTED_CONTROL_TABS_COMPONENT = "Segmented Control Tabs Component"
const val SEGMENTED_CONTROL_SWITCH_COMPONENT = "Segmented Control Switch Component"

class V2SegmentedControlActivity : V2DemoActivity() {

    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-30"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-28"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setActivityContent {
            LazyColumn(
                Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    var enabled by rememberSaveable { mutableStateOf(true) }
                    var selected by rememberSaveable { mutableStateOf(false) }
                    var unread by rememberSaveable { mutableStateOf(true) }
                    var selectedIcon by rememberSaveable { mutableStateOf(false) }
                    var selectedBrand by rememberSaveable { mutableStateOf(false) }
                    var selectedBrandIcon by rememberSaveable { mutableStateOf(false) }

                    template(
                        "Pill Button",
                        testTag = SEGMENTED_CONTROL_PILL_BUTTON,
                        enableSwitch = {
                            ToggleSwitch(
                                Modifier
                                    .padding(vertical = 3.dp)
                                    .testTag(SEGMENTED_CONTROL_PILL_BUTTON_TOGGLE),
                                onValueChange = { enabled = it },
                                checkedState = enabled
                            )
                        },
                        neutralContent = {
                            PillButton(
                                PillMetaData(
                                    "Ally 1",
                                    {
                                        selected = !selected
                                        unread = false
                                    },
                                    selected = selected,
                                    enabled = enabled,
                                    notificationDot = unread,
                                )
                            )
                            PillButton(
                                modifier = Modifier.testTag(SEGMENTED_CONTROL_PILL_BUTTON_COMPONENT),
                                pillMetaData = PillMetaData(
                                    "Neutral",
                                    { selectedIcon = !selectedIcon },
                                    icon = AvatarIcons.Icon.Anonymous.Xxlarge,
                                    selected = selectedIcon,
                                    enabled = enabled,
                                )
                            )
                            PillButton(
                                pillMetaData = PillMetaData(
                                    onClick = { selectedIcon = !selectedIcon },
                                    icon = AvatarIcons.Icon.Anonymous.Xxlarge,
                                    selected = selectedIcon,
                                    enabled = enabled,
                                    semanticContentName = "anonymous"
                                )
                            )
                        },
                        brandContent = {
                            PillButton(
                                PillMetaData(
                                    "Brand",
                                    { selectedBrand = !selectedBrand },
                                    selected = selectedBrand,
                                    enabled = enabled
                                ),
                                style = FluentStyle.Brand
                            )
                            PillButton(
                                PillMetaData(
                                    "Brand",
                                    { selectedBrandIcon = !selectedBrandIcon },
                                    icon = AvatarIcons.Icon.Anonymous.Xxlarge,
                                    selected = selectedBrandIcon,
                                    enabled = enabled,
                                    notificationDot = true
                                ),
                                style = FluentStyle.Brand,
                            )
                            PillButton(
                                PillMetaData(
                                    onClick = { selectedBrandIcon = !selectedBrandIcon },
                                    icon = AvatarIcons.Icon.Anonymous.Xxlarge,
                                    selected = selectedBrandIcon,
                                    enabled = enabled,
                                    notificationDot = true,
                                    semanticContentName = "anonymous"
                                ),
                                style = FluentStyle.Brand,
                            )
                        }
                    )
                }

                item {
                    var enableBar by rememberSaveable { mutableStateOf(true) }
                    var selectedList = rememberSaveable(
                        saver = listSaver(
                            save = { stateList ->
                                if (stateList.isNotEmpty()) {
                                    val first = stateList.first()
                                    if (!canBeSaved(first)) {
                                        throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                                    }
                                }
                                stateList.toList()
                            },
                            restore = { it.toMutableStateList() }
                        )) {
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
                        val label = "Ally ${idx + 1}"
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
                                selected = selectedList[idx],
                                notificationDot = !selectedList[idx]
                            )
                        )
                    }


                    template(
                        "Pill Bar",
                        testTag = SEGMENTED_CONTROL_PILL_BAR,
                        enableSwitch = {
                            ToggleSwitch(
                                Modifier
                                    .padding(vertical = 3.dp)
                                    .testTag(SEGMENTED_CONTROL_PILL_BAR_TOGGLE),
                                onValueChange = { enableBar = it },
                                checkedState = enableBar
                            )
                        },
                        neutralContent = {
                            PillBar(
                                modifier = Modifier.testTag(SEGMENTED_CONTROL_PILL_BAR_COMPONENT),
                                metadataList = pillList
                            )
                        },
                        brandContent = {
                            PillBar(
                                pillList,
                                style = FluentStyle.Brand
                            )
                        }
                    )
                }

                item {
                    var enableTabs by rememberSaveable { mutableStateOf(true) }
                    var selectedTab by rememberSaveable { mutableStateOf(0) }

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
                                    selectedTab = idx
                                },
                                enabled = enableTabs,
                                notificationDot = selectedTab != idx
                            )
                        )
                    }

                    template(
                        "Tabs",
                        testTag = SEGMENTED_CONTROL_TABS,
                        enableSwitch = {
                            ToggleSwitch(
                                Modifier
                                    .padding(vertical = 3.dp)
                                    .testTag(SEGMENTED_CONTROL_TABS_TOGGLE),
                                onValueChange = { enableTabs = it },
                                checkedState = enableTabs
                            )
                        },
                        neutralContent = {
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                PillTabs(
                                    modifier = Modifier.testTag(SEGMENTED_CONTROL_TABS_COMPONENT),
                                    metadataList = tabsList.subList(0, 4),
                                    selectedIndex = selectedTab,
                                    scrollable = true
                                )
                                PillTabs(
                                    tabsList.subList(0, 4),
                                    style = FluentStyle.Brand,
                                    selectedIndex = selectedTab,
                                    scrollable = false
                                )
                            }
                        },
                        brandContent = {
                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                PillTabs(
                                    tabsList,
                                    selectedIndex = selectedTab,
                                    scrollable = true
                                )
                                PillTabs(
                                    tabsList,
                                    style = FluentStyle.Brand,
                                    selectedIndex = selectedTab,
                                    scrollable = false
                                )
                            }
                        }
                    )
                }

                item {
                    var enableSwitch by rememberSaveable { mutableStateOf(true) }
                    var selectedSwitch by rememberSaveable { mutableStateOf(0) }

                    var switchList: MutableList<PillMetaData> = mutableListOf()

                    for (idx in 0..7) {
                        val label = "Neutral ${idx + 1}"
                        switchList.add(
                            PillMetaData(
                                text = label,
                                icon = if (idx % 2 == 1) AvatarIcons.Icon.Anonymous.Xxlarge else null,
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        "Button " + (idx + 1).toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    selectedSwitch = idx
                                },
                                enabled = enableSwitch,
                                notificationDot = selectedSwitch != idx
                            )
                        )
                    }

                    template(
                        "Switch",
                        testTag = SEGMENTED_CONTROL_SWITCH,
                        enableSwitch = {
                            ToggleSwitch(
                                Modifier
                                    .padding(vertical = 3.dp)
                                    .testTag(SEGMENTED_CONTROL_SWITCH_TOGGLE),
                                onValueChange = { enableSwitch = it },
                                checkedState = enableSwitch
                            )
                        },
                        neutralContent = {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                            ) {
                                PillSwitch(
                                    switchList.subList(0, 2),
                                    selectedIndex = selectedSwitch
                                )
                                PillSwitch(
                                    modifier = Modifier.testTag(SEGMENTED_CONTROL_SWITCH_COMPONENT),
                                    metadataList = switchList.subList(0, 2),
                                    style = FluentStyle.Brand,
                                    selectedIndex = selectedSwitch
                                )
                            }
                        },
                        brandContent = {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(5.dp),
                            ) {
                                PillSwitch(
                                    switchList,
                                    selectedIndex = selectedSwitch
                                )
                                PillSwitch(
                                    switchList,
                                    style = FluentStyle.Brand,
                                    selectedIndex = selectedSwitch
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
    testTag: String = ""
) {
    ListItem.SectionHeader(
        title = label,
        enableChevron = true,
        enableContentOpenCloseTransition = true,
        chevronOrientation = ChevronOrientation(90f, 0f),
        trailingAccessoryContent = enableSwitch,
        modifier = Modifier.testTag(testTag)
    ) {
        Column(
            modifier = Modifier.background(
                FluentColor(
                    light = FluentGlobalTokens.neutralColor(FluentGlobalTokens.NeutralColorTokens.Grey98),
                    dark = FluentGlobalTokens.neutralColor(FluentGlobalTokens.NeutralColorTokens.Grey8)
                ).value(
                    FluentTheme.themeMode
                )
            )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
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