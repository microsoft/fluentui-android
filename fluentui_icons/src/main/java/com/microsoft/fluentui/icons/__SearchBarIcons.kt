package com.microsoft.fluentui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.searchbaricons.*
import kotlin.collections.List as ____KtList

object SearchBarIcons

private var __AllIcons: ____KtList<ImageVector>? = null

val SearchBarIcons.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Arrowback, Dismisscircle, Microphone, Office, Search)
        return __AllIcons!!
    }
