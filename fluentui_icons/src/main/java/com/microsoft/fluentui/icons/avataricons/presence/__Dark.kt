package com.microsoft.fluentui.icons.avataricons.presence

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.PresenceGroup
import com.microsoft.fluentui.icons.avataricons.presence.dark.AllIcons
import com.microsoft.fluentui.icons.avataricons.presence.dark.Large
import com.microsoft.fluentui.icons.avataricons.presence.dark.Medium
import com.microsoft.fluentui.icons.avataricons.presence.dark.Small
import kotlin.collections.List as ____KtList

object DarkGroup

val PresenceGroup.Dark: DarkGroup
    get() = DarkGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val DarkGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Large.AllIcons + Medium.AllIcons + Small.AllIcons + listOf()
        return __AllIcons!!
    }
