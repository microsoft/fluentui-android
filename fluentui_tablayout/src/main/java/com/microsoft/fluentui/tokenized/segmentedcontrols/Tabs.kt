package com.microsoft.fluentui.tokenized.segmentedcontrols

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabsInfo
import com.microsoft.fluentui.theme.token.controlTokens.TabsTokens

val LocalTabsTokens = compositionLocalOf { TabsTokens() }
val LocalTabsInfo = compositionLocalOf { TabsInfo() }

/**
 * API to create Tabs. The Tabs control is a linear set of two or more PillButton, each of which functions as a mutually exclusive button.
 * Within the control, all PillButton are equal in width.
 * Tabs are often used to display different views.

View Documentation
 *
 * @param metadataList List of [PillMetaData] which contains information for all buttons in Tab
 * @param modifier Optional Modifier for Tabs
 * @param selectedIndex Index of the PillButton to be selected. Default: [0]
 * @param scrollable Boolean to make Tab scrollable. Only used if more than 4 items in [metadataList]. Tabs start working as PillBar in case conditions meet
 * @param style Style of Tabs and inherent PillButtons. Default: [PillButtonStyle.Neutral]
 * @param pillButtonTokens Tokens to provide appearance value to PillButton
 * @param tabsTokens Tokens to provide appearance value to Tabs
 */
@Composable
fun Tabs(
    metadataList: MutableList<PillMetaData>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    scrollable: Boolean = false,
    style: PillButtonStyle = PillButtonStyle.Neutral,
    pillButtonTokens: PillButtonTokens? = null,
    tabsTokens: TabsTokens? = null
) {
    if (metadataList.size == 0)
        return

    val token =
        tabsTokens ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Tabs] as TabsTokens

    CompositionLocalProvider(
        LocalTabsTokens provides token,
        LocalTabsInfo provides TabsInfo(style)
    ) {
        val shape = RoundedCornerShape(50)

        if (scrollable && metadataList.size > 4) {
            metadataList.forEachIndexed { index, pillMetaData ->
                pillMetaData.selected = index == selectedIndex
            }
            PillBar(
                metadataList,
                modifier = modifier,
                style = style,
                showBackground = false,
                pillButtonTokens = pillButtonTokens,
                pillBarTokens = tabsTokens
            )
        } else {
            Row(
                modifier = modifier
                    .clip(shape)
                    .padding(horizontal = 16.dp)
                    .background(getTabsTokens().trackBackground(getTabsInfo()), shape)
            ) {
                metadataList.forEachIndexed { index, pillMetadata ->
                    pillMetadata.selected = (selectedIndex == index)
                    PillButton(
                        pillMetadata,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        style = style,
                        pillButtonTokens = pillButtonTokens
                    )
                }
            }
        }
    }
}

@Composable
fun getTabsTokens(): TabsTokens {
    return LocalTabsTokens.current
}

@Composable
fun getTabsInfo(): TabsInfo {
    return LocalTabsInfo.current
}
