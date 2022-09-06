package com.microsoft.fluentui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.ccbicons.Keyboarddismiss
import kotlin.collections.List as ____KtList

public object CCBIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val CCBIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Keyboarddismiss)
    return __AllIcons!!
  }
