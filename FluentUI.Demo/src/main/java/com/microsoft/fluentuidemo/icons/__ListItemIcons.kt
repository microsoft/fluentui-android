package com.microsoft.fluentuidemo.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentuidemo.icons.listitemicons.*
import kotlin.collections.List as ____KtList

object ListItemIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val ListItemIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Folder40, Chevron)
    return __AllIcons!!
  }
