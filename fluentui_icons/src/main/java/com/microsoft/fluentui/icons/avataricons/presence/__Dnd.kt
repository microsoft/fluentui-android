package com.microsoft.fluentui.icons.avataricons.presence

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.PresenceGroup
import com.microsoft.fluentui.icons.avataricons.presence.dnd.AllIcons
import com.microsoft.fluentui.icons.avataricons.presence.dnd.Large
import com.microsoft.fluentui.icons.avataricons.presence.dnd.Medium
import com.microsoft.fluentui.icons.avataricons.presence.dnd.Small
import kotlin.collections.List as ____KtList

object DndGroup

val PresenceGroup.Dnd: DndGroup
    get() = DndGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val DndGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Large.AllIcons + Medium.AllIcons + Small.AllIcons + listOf()
        return __AllIcons!!
    }
