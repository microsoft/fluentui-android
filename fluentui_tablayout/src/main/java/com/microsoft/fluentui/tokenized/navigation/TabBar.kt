package com.microsoft.fluentui.tokenized.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.TabBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.TabBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabTextAlignment
import com.microsoft.fluentui.tokenized.tabItem.TabItem
import com.microsoft.fluentui.tablayout.R

data class TabData(
    var title: String,
    var icon: ImageVector,
    var selectedIcon: ImageVector = icon,
    var selected: Boolean = false,
    var onClick: () -> Unit,
    var badge: @Composable (() -> Unit)? = null,
    var accessibilityDescription: String? = null,  //Custom announcement for Talkback
)

/**
 * TabBar displays tabs that are arranged horizontally.
 *
 *  @param tabDataList provide list of [TabData] to create tabs.
 *  @param modifier the [Modifier] to be applied to this item
 *  @param selectedIndex Index of selected tax. This should be updated onClick of TabData & provide back to TabBar.
 *  @param tabTextAlignment Placement of text in Tab
 *  @param showIndicator Add an indicator under the selected icon
 *  @param tabItemTokens [TabItemTokens] to apply on tabs.
 *  @param tabBarTokens provide appearance values. If not provided then tokens will be picked from AppThemeController
 *
 */
@Composable
fun TabBar(
    tabDataList: List<TabData>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    tabTextAlignment: TabTextAlignment = TabTextAlignment.VERTICAL,
    showIndicator: Boolean = false,
    tabItemTokens: TabItemTokens? = null,
    tabBarTokens: TabBarTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = tabBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabBarControlType] as TabBarTokens
    val resources = LocalContext.current.resources

    Column(modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(token.topBorderWidth(tabBarInfo = TabBarInfo()))
                .background(color = token.topBorderColor(tabBarInfo = TabBarInfo()))
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            tabDataList.forEachIndexed { index, tabData ->
                tabData.selected = index == selectedIndex
                var accessibilityDescriptionValue = if(tabData.accessibilityDescription != null) { tabData.accessibilityDescription }
                                               else{ tabData.title + if(tabData.selected) resources.getString(R.string.tab_active).prependIndent(": ") else resources.getString(R.string.tab_inactive).prependIndent(": ") }
                TabItem(
                    title = tabData.title,
                    modifier = Modifier
                        .semantics {
                            if (accessibilityDescriptionValue != null) {
                                contentDescription = accessibilityDescriptionValue
                            }
                        }
                        .fillMaxWidth()
                        .weight(1F),
                    icon = if (tabData.selected) tabData.selectedIcon else tabData.icon,
                    textAlignment = tabTextAlignment,
                    selected = tabData.selected,
                    onClick = tabData.onClick,
                    accessory = tabData.badge,
                    showIndicator = showIndicator,
                    tabItemTokens = tabItemTokens
                        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabItemControlType] as TabItemTokens,
                    style = if (tabData.selected) FluentStyle.Brand else FluentStyle.Neutral
                )
            }
        }
    }
}