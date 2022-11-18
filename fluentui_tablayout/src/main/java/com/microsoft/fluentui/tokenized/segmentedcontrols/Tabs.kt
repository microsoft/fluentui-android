package com.microsoft.fluentui.tokenized.segmentedcontrols

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*

val LocalTabsTokens = compositionLocalOf { TabsTokens() }
val LocalTabsInfo = compositionLocalOf { TabsInfo() }

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
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape)
                    .background(getTabsTokens().background(getTabsInfo()), shape)
                    .then(modifier)
            ) {
                metadataList.forEachIndexed { index, pillMetadata ->
                    item {
                        pillMetadata.selected = (selectedIndex == index)
                        PillButton(
                            pillMetadata,
                            modifier = Modifier.fillParentMaxWidth(0.22f),
                            style = style,
                            pillButtonTokens = pillButtonTokens
                        )
                    }
                }
            }
        }
        else {
            Row(
                modifier = Modifier
                    .clip(shape)
                    .background(getTabsTokens().background(getTabsInfo()), shape)
                    .then(modifier)
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
