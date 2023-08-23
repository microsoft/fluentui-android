package com.microsoft.fluentui.icons.appbaricons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.appbaricons.appbarnavigationicon.Appbarnavigationicon
import kotlin.collections.List as ____KtList

public object AppBarNavigationIcon

private var __AllIcons: ____KtList<ImageVector>? = null

public val AppBarNavigationIcon.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Appbarnavigationicon)
    return __AllIcons!!
  }
