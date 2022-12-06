package com.microsoft.fluentui.tokenized.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.DividerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DividerTokens

val LocalDividerTokens = compositionLocalOf { DividerTokens() }
val LocalDividerInfo = compositionLocalOf { DividerInfo() }

@Composable
fun getDividerTokens(): DividerTokens {
    return LocalDividerTokens.current
}

@Composable
fun getDividerInfo(): DividerInfo {
    return LocalDividerInfo.current
}

@Composable
fun Divider(
    height: Dp = 1.dp,
    dividerToken: DividerTokens? = null
) {
    val token =
        dividerToken
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Divider] as DividerTokens
    CompositionLocalProvider(
        LocalDividerTokens provides token,
        LocalDividerInfo provides DividerInfo()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(getDividerTokens().background(dividerInfo = getDividerInfo()))
                .focusable(false)
                .padding(vertical = 8.dp)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .requiredHeight(height)
                    .background(getDividerTokens().dividerColor(dividerInfo = getDividerInfo()))
            )
        }
    }
}