package com.microsoft.fluentui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.progresstexticons.DismissCircle
import kotlin.collections.List as ____KtList

public object ProgressTextIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val ProgressTextIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(DismissCircle)
    return __AllIcons!!
  }
