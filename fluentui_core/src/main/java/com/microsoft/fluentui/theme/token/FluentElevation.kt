package com.microsoft.fluentui.theme.token

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class StateElevation(
        val rest: Dp = 0.dp,
        val pressed: Dp = 0.dp,
        val selected: Dp = 0.dp,
        val focused: Dp = 0.dp,
        val disabled: Dp = 0.dp,
)
