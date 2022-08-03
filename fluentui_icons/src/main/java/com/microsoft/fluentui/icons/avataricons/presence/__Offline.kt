package com.microsoft.fluentui.icons.avataricons.presence

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.PresenceGroup
import com.microsoft.fluentui.icons.avataricons.presence.offline.AllIcons
import com.microsoft.fluentui.icons.avataricons.presence.offline.Large
import com.microsoft.fluentui.icons.avataricons.presence.offline.Medium
import com.microsoft.fluentui.icons.avataricons.presence.offline.Small
import kotlin.collections.List as ____KtList

object OfflineGroup

val PresenceGroup.Offline: OfflineGroup
    get() = OfflineGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val OfflineGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Large.AllIcons + Medium.AllIcons + Small.AllIcons + listOf()
        return __AllIcons!!
    }
