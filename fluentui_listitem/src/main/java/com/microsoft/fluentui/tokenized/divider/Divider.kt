package com.microsoft.fluentui.tokenized.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.DividerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DividerTokens

@Composable
fun Divider(
    height: Dp = 1.dp,
    dividerToken: DividerTokens? = null
) {
    val token =
        dividerToken
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Divider] as DividerTokens
    val dividerInfo = DividerInfo()

    Column(
        Modifier
            .fillMaxWidth()
            .background(token.backgroundBrush(dividerInfo))
            .focusable(false)
            .padding(token.verticalPadding(dividerInfo))
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .requiredHeight(height)
                .background(token.dividerBrush(dividerInfo))
                .padding(start = token.startIndent(dividerInfo))
        )
    }
}