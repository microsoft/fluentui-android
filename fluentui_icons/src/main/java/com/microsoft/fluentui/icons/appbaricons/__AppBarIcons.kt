package com.microsoft.fluentui.icons.appbaricons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.appbaricons.appbaricons.Arrowback
import kotlin.collections.List as ____KtList

public object AppBarIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val AppBarIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Arrowback)
    return __AllIcons!!
  }
