package com.microsoft.fluentui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.listitemicons.Folder
import kotlin.collections.List as ____KtList

public object ListItemIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val ListItemIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Folder)
    return __AllIcons!!
  }
