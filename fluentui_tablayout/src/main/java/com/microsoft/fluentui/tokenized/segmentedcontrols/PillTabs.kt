package com.microsoft.fluentui.tokenized.segmentedcontrols

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.PillTabsInfo
import com.microsoft.fluentui.theme.token.controlTokens.PillTabsTokens

/**
 * API to create PillTabs. The PillTabs control is a linear set of two or more PillButton, each of which functions as a mutually exclusive button.
 * Within the control, all PillButton are equal in width.
 * PillTabs are often used to display different views.

View Documentation
 *
 * @param metadataList List of [PillMetaData] which contains information for all buttons in Tab
 * @param modifier Optional Modifier for PillTabs
 * @param selectedIndex Index of the PillButton to be selected. Default: [0]
 * @param scrollable Boolean to make Tab scrollable. Only used if more than 4 items in [metadataList]. PillTabs start working as PillBar in case conditions meet
 * @param style Style of PillTabs and inherent PillButtons. Default: [FluentStyle.Neutral]
 * @param pillButtonTokens Tokens to provide appearance value to PillButton
 * @param tabsTokens Tokens to provide appearance value to PillTabs
 */
@Composable
fun PillTabs(
    metadataList: MutableList<PillMetaData>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    scrollable: Boolean = false,
    style: FluentStyle = FluentStyle.Neutral,
    pillButtonTokens: PillButtonTokens? = null,
    tabsTokens: PillTabsTokens? = null
) {
    if (metadataList.size == 0)
        return

    val themeID = FluentTheme.themeID
    val token =
        tabsTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PillTabs] as PillTabsTokens

    val pillTabsInfo = PillTabsInfo(style)
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
                .background(token.trackBackgroundBrush(pillTabsInfo), shape)
        ) {
            metadataList.forEachIndexed { index, pillMetadata ->
                pillMetadata.selected = (selectedIndex == index)
                PillButton(
                    pillMetadata,
                    modifier = Modifier
                        .weight(1F),
                    style = style,
                    pillButtonTokens = pillButtonTokens
                )
            }
        }
    }
}
