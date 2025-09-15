package com.microsoft.fluentui.theme.token.controlTokens

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize

@Parcelize
open class StackableSnackBarTokens : SnackBarTokens() {
    @Composable
    fun cardShape(snackBarInfo: SnackBarInfo): Shape {
        return RoundedCornerShape(12.dp)
    }

    @Composable
    fun contentPadding(snackBarInfo: SnackBarInfo): Dp {
        return 0.dp
    }
}