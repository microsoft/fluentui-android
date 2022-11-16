package com.microsoft.fluentui.tokenized.segmentedcontrols

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import kotlinx.coroutines.launch
import kotlin.math.max

val LocalSwitchTokens = compositionLocalOf { SwitchTokens() }
val LocalSwitchInfo = compositionLocalOf { SwitchInfo() }

@Composable
fun Switch(
    metadataList: MutableList<PillMetaData>,
    modifier: Modifier = Modifier,
    selected: String = "",
    style: PillButtonStyle = PillButtonStyle.Neutral,
    pillButtonTokens: PillButtonTokens? = null,
    switchTokens: SwitchTokens? = null
) {
    if (metadataList.size == 0)
        return

    val token = switchTokens ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Switch] as SwitchTokens

    CompositionLocalProvider(
        LocalSwitchTokens provides token,
        LocalSwitchInfo provides SwitchInfo(style)
    ) {
        var selectedTab: String? = selected
        if (selectedTab.isNullOrBlank())
            selectedTab = metadataList[0].text

        val shape = RoundedCornerShape(50)

        val lazyListState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        LazyRow(
            modifier = Modifier
                .wrapContentWidth()
                .focusable(enabled = false)
                .clip(shape)
                .background(getSwitchTokens().background(getSwitchInfo()), shape)
                .then(modifier),
            state = lazyListState
        ) {
            metadataList.forEachIndexed { index, pillMetadata ->
                item(index.toString()) {
                    pillMetadata.selected = (selectedTab == pillMetadata.text)
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
                                } },
                        style = style,
                        pillButtonTokens = pillButtonTokens
                    )
                }
            }
        }
    }
}

@Composable
fun getSwitchTokens(): SwitchTokens {
    return LocalSwitchTokens.current
}

@Composable
fun getSwitchInfo(): SwitchInfo {
    return LocalSwitchInfo.current
}
