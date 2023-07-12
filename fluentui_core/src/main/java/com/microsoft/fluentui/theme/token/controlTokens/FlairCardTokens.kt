package com.microsoft.fluentui.theme.token.controlTokens

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentGlobalTokens

open class FlairCardInfo : ControlInfo
open class FlairCardTokens : BasicCardTokens() {
    @Composable
    open fun borderStrokeWidth(flairCardInfo: FlairCardInfo): Dp {
        return FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth20)
    }
}