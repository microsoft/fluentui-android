package com.microsoft.fluentui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.icons.searchbaricons.Dismisscircle
import com.microsoft.fluentui.icons.searchbaricons.Microphone
import com.microsoft.fluentui.icons.searchbaricons.Search
import kotlin.collections.List as ____KtList

public object SearchBarIcons

private var __AllIcons: ____KtList<ImageVector>? = null

public val SearchBarIcons.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(Arrowback, Dismisscircle, Microphone, Search)
    return __AllIcons!!
  }
