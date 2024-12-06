package com.microsoft.fluentui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.actionbaricons.Arrowright
import com.microsoft.fluentui.icons.actionbaricons.Chevron
import kotlin.collections.List as ____KtList

object ActionBarIcons

private var __AllIcons: ____KtList<ImageVector>? = null

val ActionBarIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Arrowright, Chevron)
    return __AllIcons!!
  }
