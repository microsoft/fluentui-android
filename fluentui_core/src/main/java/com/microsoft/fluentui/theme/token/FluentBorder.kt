package com.microsoft.fluentui.theme.token

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class StateBorderStroke(
        val rest: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
        val pressed: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
        val selected: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
        val focused: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
        val disabled: List<BorderStroke> = listOf(BorderStroke(0.dp, Color(0, 0, 0, 0))),
)