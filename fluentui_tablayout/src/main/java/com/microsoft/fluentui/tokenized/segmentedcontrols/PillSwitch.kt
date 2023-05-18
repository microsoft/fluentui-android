package com.microsoft.fluentui.tokenized.segmentedcontrols

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.PillSwitchInfo
import com.microsoft.fluentui.theme.token.controlTokens.PillSwitchTokens
import kotlinx.coroutines.launch
import kotlin.math.max

/**
 * API to create PillSwitches. The PillSwitch control is a linear set of two or more PillButton, each of which functions as a mutually exclusive button.
 * PillSwitches are used to toggling between two views.
 *
 * @param metadataList List of [PillMetaData] which contains information for all buttons in Tab
 * @param modifier Optional Modifier for Tabs
 * @param selectedIndex Index of the PillButton to be selected. Default: [0]
 * @param style Style of Tabs and inherent PillButtons. Default: [FluentStyle.Neutral]
 * @param pillButtonTokens Tokens to provide appearance value to PillButton
 * @param pillSwitchTokens Tokens to provide appearance value to PillSwitch
 */
@Composable
fun PillSwitch(
    metadataList: MutableList<PillMetaData>,
    modifier: Modifier = Modifier,
    selectedIndex: Int = 0,
    style: FluentStyle = FluentStyle.Neutral,
    pillButtonTokens: PillButtonTokens? = null,
    pillSwitchTokens: PillSwitchTokens? = null
) {
    if (metadataList.size == 0)
        return

    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = pillSwitchTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PillSwitch] as PillSwitchTokens

    val pillSwitchInfo = PillSwitchInfo(style)
    val shape = RoundedCornerShape(50)

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 16.dp)
            .background(Color.Transparent)
            .focusable(false)
    ) {
        LazyRow(
            modifier = modifier
                .wrapContentWidth()
                .padding(horizontal = 16.dp)
                .focusable(enabled = false)
                .clip(shape)
                .background(token.backgroundBrush(pillSwitchInfo), shape),
            state = lazyListState
        ) {
            metadataList.forEachIndexed { index, pillMetadata ->
                item(index.toString()) {
                    pillMetadata.selected = (selectedIndex == index)
                    PillButton(
                        pillMetadata,
                        modifier = Modifier
                            .wrapContentWidth()
                            .onFocusEvent { focusState ->
                                if (focusState.isFocused) {
                                    scope.launch {
                                        lazyListState.animateScrollToItem(
                                            max(0, index - 2)
                                        )
                                    }
                                }
                            },
                        style = style,
                        pillButtonTokens = pillButtonTokens
                    )
                }
            }
        }
    }
}
