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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.zIndex


@Composable
private fun AcrylicPane(
    modifier: Modifier = Modifier,
    component: @Composable BoxScope.() -> Unit,
    backgroundContent: @Composable () -> Unit,
    triggerRecomposition: Boolean = false
) {
    val startColor: Color = Color(red = 0xF7, green = 0xF8 , blue = 0xFB, alpha = 0xFF)
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.zIndex(0f)) {
            item {
                backgroundContent()
            }
        }
        Box(
            modifier = modifier
                .zIndex(1f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            startColor,
                            startColor,
                            startColor,
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0f)
                        ),
                        tileMode = TileMode.Decal
                    )
                )
        ){
            component()
        }
    }
}

@Composable
public fun AcrylicPane(modifier: Modifier = Modifier, component: @Composable () -> Unit, backgroundContent: @Composable () -> Unit) {
    AcrylicPane(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        component = {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                component()
            }
        },
        backgroundContent = {
            Column {
                Spacer(modifier = Modifier.height(200.dp))
                backgroundContent()
            }
        },
        triggerRecomposition = false
    )
}