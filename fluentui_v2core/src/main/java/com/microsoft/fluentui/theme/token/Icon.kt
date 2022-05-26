package com.microsoft.fluentui.theme.token

import androidx.compose.ui.unit.Dp
enum class IconType{
    Regular,
    Filled
}

data class IconSize( val size: Dp, val type:IconType )