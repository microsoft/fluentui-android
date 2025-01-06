package com.microsoft.fluentui.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AcrylicPaneInfo
import com.microsoft.fluentui.theme.token.controlTokens.AcrylicPaneTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabBarTokens


@Composable
private fun AcrylicPane(
    modifier: Modifier = Modifier,
    component: @Composable BoxScope.() -> Unit,
    backgroundContent: @Composable () -> Unit,
    triggerRecomposition: Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxSize().border(BorderStroke(width = 0.dp, color = Color.Transparent))
    ) {
        backgroundContent()

        Box(
            modifier = modifier
        ) {
            component()
        }
    }
}

@Composable
public fun AcrylicPane(modifier: Modifier = Modifier, paneHeight: Dp = 300.dp, acrylicPaneStyle:FluentStyle = FluentStyle.Neutral, component: @Composable () -> Unit, backgroundContent: @Composable () -> Unit, acrylicPaneTokens: AcrylicPaneTokens? = null) {
    val paneInfo: AcrylicPaneInfo = AcrylicPaneInfo(style = acrylicPaneStyle)
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = acrylicPaneTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AcrylicPaneControlType] as AcrylicPaneTokens
    AcrylicPane(
        modifier = modifier
        .fillMaxWidth()
        .height(paneHeight)
        .background(
            token.acrylicPaneGradient(acrylicPaneInfo = paneInfo)
        )
        .border(BorderStroke(width = 0.dp, color = Color.Transparent)),
        component = {
            component()
        },
        backgroundContent = {
            backgroundContent()
        },
        triggerRecomposition = false
    )
}