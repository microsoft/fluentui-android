package com.microsoft.fluentui.theme.token.controlTokens

import androidx.annotation.FloatRange
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import kotlinx.parcelize.Parcelize


enum class StackableSnackbarEntryAnimationType {
    SlideInFromAbove,
    SlideInFromBelow,
    FadeIn,
    SlideInFromLeft,
    SlideInFromRight
}

enum class StackableSnackbarExitAnimationType {
    FadeOut,
    SlideOutToLeft,
    SlideOutToRight
}

@Parcelize
open class StackableSnackBarTokens : SnackBarTokens() {
    @Composable
    override fun shadowElevationValue(snackBarInfo: SnackBarInfo): Dp {
        return FluentGlobalTokens.ShadowTokens.Shadow08.value
    }

    @FloatRange(from = 0.0, to = 2.0, fromInclusive = false, toInclusive = true)
    @Composable
    fun snackbarWidthScalingFactor(snackBarInfo: SnackBarInfo): Float {
        return 0.95f
    }

    @Composable
    fun entryAnimationType(snackBarInfo: SnackBarInfo): StackableSnackbarEntryAnimationType {
        return StackableSnackbarEntryAnimationType.SlideInFromBelow
    }

    @Composable
    fun exitAnimationType(snackBarInfo: SnackBarInfo): StackableSnackbarExitAnimationType {
        return StackableSnackbarExitAnimationType.SlideOutToLeft
    }
}