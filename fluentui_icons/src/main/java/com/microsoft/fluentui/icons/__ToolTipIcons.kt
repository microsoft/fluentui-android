package com.microsoft.fluentui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.tooltipicons.Tip
import kotlin.collections.List as ____KtList

public object ToolTipIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val ToolTipIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Tip)
    return __AllIcons!!
  }
