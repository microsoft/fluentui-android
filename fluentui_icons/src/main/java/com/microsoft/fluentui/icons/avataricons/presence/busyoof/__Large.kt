package com.microsoft.fluentui.icons.avataricons.presence.busyoof

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.presence.BusyoofGroup
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.large.Light
import kotlin.collections.List as ____KtList

object LargeGroup

val BusyoofGroup.Large: LargeGroup
    get() = LargeGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val LargeGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Dark, Light)
        return __AllIcons!!
    }
