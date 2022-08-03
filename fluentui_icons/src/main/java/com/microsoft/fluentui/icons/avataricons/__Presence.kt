package com.microsoft.fluentui.icons.avataricons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.AvatarIcons
import com.microsoft.fluentui.icons.avataricons.presence.*
import kotlin.collections.List as ____KtList

object PresenceGroup

val AvatarIcons.Presence: PresenceGroup
    get() = PresenceGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val PresenceGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Available.AllIcons + Availableoof.AllIcons + Away.AllIcons + Awayoof.AllIcons +
                Blocked.AllIcons + Busy.AllIcons + Busyoof.AllIcons + Dark.AllIcons + Dnd.AllIcons +
                Dndoof.AllIcons + Offline.AllIcons + Oof.AllIcons + Unknown.AllIcons + listOf()
        return __AllIcons!!
    }
