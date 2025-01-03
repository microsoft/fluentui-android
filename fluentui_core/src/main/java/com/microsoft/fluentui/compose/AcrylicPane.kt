package com.microsoft.fluentui.compose

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.zIndex


@Composable
private fun AcrylicPane(
    modifier: Modifier = Modifier,
    component: @Composable BoxScope.() -> Unit,
    backgroundContent: @Composable () -> Unit,
    triggerRecomposition: Boolean = false
) {
    Box(
        modifier = Modifier.fillMaxSize().border(width = 0.dp, color = Color.Transparent)
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
public fun AcrylicPane(modifier: Modifier = Modifier, paneHeight: Dp = 300.dp, component: @Composable () -> Unit, backgroundContent: @Composable () -> Unit) {
    val startColor: Color = Color(red = 0xF7, green = 0xF8 , blue = 0xFB, alpha = 0xFF)
    AcrylicPane(
        modifier = modifier
        .fillMaxWidth()
        .height(paneHeight)
        .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        startColor,
                        startColor,
                        startColor,
                        Color.White.copy(alpha = 0.5f),
                        Color.White.copy(alpha = 0.2f),
                        Color.White.copy(alpha = 0.0f),
                    )
                )
        )
        .border(width = 0.dp, color = Color(0x00FFFFFF)),
        component = {
            component()
        },
        backgroundContent = {
            backgroundContent()
        },
        triggerRecomposition = false
    )
}