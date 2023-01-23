package com.microsoft.fluentui.tokenized.listitem

import androidx.compose.runtime.Composable

data class ChevronOrientation(val enterTransition: Float = 0f, val exitTransition: Float = 0f)

data class TextIcons(
    val icon1: (@Composable () -> Unit),
    val icon2: (@Composable () -> Unit)? = null
)