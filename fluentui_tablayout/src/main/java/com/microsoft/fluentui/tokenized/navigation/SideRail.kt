package com.microsoft.fluentui.tokenized.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.SideRailInfo
import com.microsoft.fluentui.theme.token.controlTokens.SideRailTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.TabItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabTextAlignment
import com.microsoft.fluentui.tokenized.tabItem.TabItem

/**
 * Side rails provide access to multiple destinations in your app. Used mainly in tablets and wide screen devices
 *
 * @param topTabDataList provide list of [TabData] to create top tabs.
 * @param modifier the [Modifier] to be applied to this item
 * @param topTabSelectedIndex Index of selected top tab.
 * @param bottomTabSelectedIndex Index of selected bottom tab.
 * @param header [Composable] to provide header view.
 * @param showIconText whether to show text under icons in top and bottom tabs.
 * @param bottomTabDataList provide list of [TabData] to create bottom tabs.
 * @param tabItemTokens [TabItemTokens] to apply on tabs.
 * @param sideRailTokens provide appearance values. If not provided then tokens will be picked from AppThemeController
 *
 */
@Composable
fun SideRail(
    topTabDataList: List<TabData>,
    modifier: Modifier = Modifier,
    topTabSelectedIndex: Int = 0,
    bottomTabSelectedIndex: Int = -1,
    header: @Composable (() -> Unit)? = null,
    showIconText: Boolean = false,
    bottomTabDataList: List<TabData>? = null,
    tabItemTokens: TabItemTokens? = null,
    sideRailTokens: SideRailTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = sideRailTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.SideRailControlType] as SideRailTokens
    val sideRailInfo = SideRailInfo()

    val borderColor = token.borderColor(sideRailInfo)
    val borderWidth = token.borderWidth(sideRailInfo)
    val topMargin = token.topMargin(sideRailInfo)
    val bottomMargin = token.bottomMargin(sideRailInfo)
    val headerPadding = token.headerPadding(sideRailInfo)

    Box(
        modifier = modifier
            .fillMaxHeight()
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .width(borderWidth)
                .background(borderColor)
                .align(Alignment.CenterEnd)
        )
        Column(
            modifier = Modifier.padding(end = borderWidth),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(topMargin))
            Box(modifier = Modifier.padding(headerPadding)) {
                header?.invoke()
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f).selectableGroup(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SideRailTabs(
                    tabDataList = topTabDataList,
                    selectedIndex = topTabSelectedIndex,
                    showIconText = showIconText,
                    tabItemTokens = tabItemTokens
                )
            }
            if (bottomTabDataList != null) {
                Column(modifier = Modifier.selectableGroup(), horizontalAlignment = Alignment.CenterHorizontally) {
                    SideRailTabs(
                        tabDataList = bottomTabDataList,
                        selectedIndex = bottomTabSelectedIndex,
                        showIconText = showIconText,
                        tabItemTokens = tabItemTokens
                    )
                }
                Spacer(modifier = Modifier.height(bottomMargin))
            }

        }
    }

}

@Composable
private fun SideRailTabs(
    tabDataList: List<TabData>,
    selectedIndex: Int,
    showIconText: Boolean,
    tabItemTokens: TabItemTokens? = null
) {
    class TabItemCustomTokens : TabItemTokens() {
        @Composable
        override fun padding(tabItemInfo: TabItemInfo): PaddingValues {
            return PaddingValues(top = 18.dp, bottom = 18.dp)
        }
    }
    tabDataList.forEachIndexed { index, tabData ->
        tabData.selected = index == selectedIndex
        TabItem(
            title = tabData.title,
            icon = if (tabData.selected) tabData.selectedIcon else tabData.icon,
            textAlignment = if (showIconText) TabTextAlignment.VERTICAL else TabTextAlignment.NO_TEXT,
            selected = tabData.selected,
            onClick = tabData.onClick,
            accessory = tabData.badge,
            fixedWidth = true,
            tabItemTokens = tabItemTokens
                ?: TabItemCustomTokens(),
            style = if (tabData.selected) FluentStyle.Brand else FluentStyle.Neutral
        )
    }
}