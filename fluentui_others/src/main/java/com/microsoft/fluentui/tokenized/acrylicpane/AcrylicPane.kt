package com.microsoft.fluentui.tokenized.acrylicpane

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AcrylicPaneInfo
import com.microsoft.fluentui.theme.token.controlTokens.AcrylicPaneTokens


@Composable
private fun AcrylicPane(
    modifier: Modifier = Modifier,
    component: @Composable BoxScope.() -> Unit,
    backgroundContent: @Composable () -> Unit,
    triggerRecomposition: Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        backgroundContent()

        Box(
            modifier = modifier
        ) {
            component()
        }
    }
}

fun roundToNearestTen(value: Int): Int { // Added for anti-aliasing
    return ((value + 5) / 10) * 10
}
/**
 * A composable function that creates an AcrylicPane with specified properties and content.
 *
 * @param modifier The modifier to be applied to the AcrylicPane.
 * @param paneHeight The height of the pane, default is 300.dp.
 * @param acrylicPaneStyle The style of the pane, default is FluentStyle.Neutral.
 * @param component The main composable content to be displayed within the pane.
 * @param backgroundContent The composable content to be displayed as the background of the pane.
 * @param acrylicPaneTokens Optional tokens to customize the appearance of the AcrylicPane.
 */

@Composable
public fun AcrylicPane(modifier: Modifier = Modifier, paneHeight: Dp = 300.dp, acrylicPaneStyle:FluentStyle = FluentStyle.Neutral, component: @Composable () -> Unit, backgroundContent: @Composable () -> Unit, acrylicPaneTokens: AcrylicPaneTokens? = null) {
    val paneInfo: AcrylicPaneInfo = AcrylicPaneInfo(style = acrylicPaneStyle)
    val newPaneHeight = roundToNearestTen(paneHeight.value.toInt()).dp
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = acrylicPaneTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AcrylicPaneControlType] as AcrylicPaneTokens
    AcrylicPane(
        modifier = modifier
        .fillMaxWidth()
        .height(newPaneHeight)
        .background(
            token.acrylicPaneGradient(acrylicPaneInfo = paneInfo)
        ),
        component = {
            component()
        },
        backgroundContent = {
            backgroundContent()
        },
        triggerRecomposition = false
    )
}
