package com.microsoft.fluentui.icons.avataricons.presence

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.PresenceGroup
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.AllIcons
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.Large
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.Medium
import com.microsoft.fluentui.icons.avataricons.presence.busyoof.Small
import kotlin.collections.List as ____KtList

object BusyoofGroup

val PresenceGroup.Busyoof: BusyoofGroup
    get() = BusyoofGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val BusyoofGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Large.AllIcons + Medium.AllIcons + Small.AllIcons + listOf()
        return __AllIcons!!
    }
