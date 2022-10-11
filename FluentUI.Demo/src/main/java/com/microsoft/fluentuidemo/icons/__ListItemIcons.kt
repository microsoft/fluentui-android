package com.microsoft.fluentuidemo.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentuidemo.icons.listitemicons.Folder40
import kotlin.collections.List as ____KtList

public object ListItemIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val ListItemIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Folder40)
    return __AllIcons!!
  }
