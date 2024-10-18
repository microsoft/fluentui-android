package com.microsoft.fluentui.materialized

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.BadgedBox
import androidx.compose.material.LeadingIconTab
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.TabBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.TabBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.TabItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabTextAlignment
import com.microsoft.fluentui.tokenized.navigation.TabData

@Composable
fun TabBarV2(
    tabDataList: List<TabData>,
    selectedIndex: Int,
    tabItemTokens: TabItemTokens? = null,
    tabBarTokens: TabBarTokens? = null,
    tabTextAlignment: TabTextAlignment = TabTextAlignment.VERTICAL,
    showIndicator: Boolean
) {
    val interactionSource = remember { MutableInteractionSource() }
    val token = tabBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabBarControlType] as TabBarTokens

    val tabBarInfo = TabBarInfo()
    val tabItemToken =
        tabItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabItemControlType] as TabItemTokens
    TabRow(
        selectedTabIndex = selectedIndex,
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = token.backgroundColor(tabBarInfo),
        contentColor = token.contentColor(tabBarInfo),
        indicator = { tabPositions ->
            if(showIndicator) {
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedIndex]).padding(4.dp).size(2.dp),
                    color = token.indicatorColor(tabBarInfo).getColorByState(
                        enabled = true,
                        selected = true,
                        interactionSource = interactionSource
                    ),
                )
            }
        }
    ) {
        tabDataList.forEachIndexed { index, tabData ->
            tabData.selected = index == selectedIndex
            val style = if (tabData.selected) FluentStyle.Brand else FluentStyle.Neutral
            val tabItemInfo = TabItemInfo(tabTextAlignment, style)
            val iconColorBrush: Brush = tabItemToken.iconColor(tabItemInfo = tabItemInfo).getBrushByState(
                enabled = true,
                selected = tabData.selected,
                interactionSource =  interactionSource
            )
            val textColor by animateColorAsState(
                tabItemToken.textColor(tabItemInfo = tabItemInfo).getColorByState(
                    enabled = true,
                    selected = tabData.selected,
                    interactionSource = interactionSource
                ),
                animationSpec = tween(durationMillis = 300)
            )
            if(tabTextAlignment == TabTextAlignment.VERTICAL) {
            Tab(
                selected = tabData.selected,
                onClick = tabData.onClick,
                text = { if(tabTextAlignment != TabTextAlignment.NO_TEXT) Text(text = tabData.title, maxLines = 1, overflow = TextOverflow.Ellipsis ) },
                modifier = Modifier
                        .fillMaxWidth(),
                icon = {
                    IconContent(
                        icon = if (tabData.selected) tabData.selectedIcon else tabData.icon,
                        textAlignment = tabTextAlignment,
                        iconColorBrush = iconColorBrush,
                        badgeContent = tabData.badge ?: {},
                        title = tabData.title
                    )
                },
                selectedContentColor = textColor,
                unselectedContentColor = textColor,
                interactionSource = interactionSource
            ) }
            else {
                LeadingIconTab(
                    selected = tabData.selected,
                    onClick = tabData.onClick,
                    text = { if(tabTextAlignment != TabTextAlignment.NO_TEXT) Text(text = tabData.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    icon = {
                        IconContent(
                            icon = if (tabData.selected) tabData.selectedIcon else tabData.icon,
                            textAlignment = tabTextAlignment,
                            iconColorBrush = iconColorBrush,
                            badgeContent = tabData.badge ?: {},
                            title = tabData.title
                        )
                    },
                    selectedContentColor = textColor,
                    unselectedContentColor = textColor,
                    interactionSource = interactionSource
                )
            }

        }
    }
}

@Composable
fun IconContent(
    icon: ImageVector,
    textAlignment: TabTextAlignment,
    iconColorBrush: Brush,
    badgeContent: @Composable (() -> Unit),
    title: String
) {
    BadgedBox(
        badge = {
            badgeContent()
        }
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.size(if (textAlignment == TabTextAlignment.NO_TEXT) 28.dp else 24.dp)
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush = iconColorBrush, blendMode = BlendMode.SrcAtop)
                    }
                },
            contentDescription = if (textAlignment == TabTextAlignment.NO_TEXT) title else null,
        )
    }

}