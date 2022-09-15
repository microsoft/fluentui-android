package com.microsoft.fluentui.util

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun pxToDp(value: Float) = (value / Resources
        .getSystem()
        .displayMetrics.density).dp

fun dpToPx(value: Dp) = (value * Resources
        .getSystem()
        .displayMetrics.density).value

