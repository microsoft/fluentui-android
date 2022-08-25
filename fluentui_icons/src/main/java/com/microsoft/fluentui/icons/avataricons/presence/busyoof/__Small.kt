package com.microsoft.fluentui.icons.avataricons.presence.busyoof

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.presence.BusyoofGroup
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.small.Light
import kotlin.collections.List as ____KtList

object SmallGroup

val BusyoofGroup.Small: SmallGroup
    get() = SmallGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val SmallGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Dark, Light)
        return __AllIcons!!
    }
