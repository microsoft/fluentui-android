package com.microsoft.fluentui.icons.avataricons.presence

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.PresenceGroup
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.AllIcons
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.Large
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.Medium
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.Small
import kotlin.collections.List as ____KtList

object AvailableoofGroup

val PresenceGroup.Availableoof: AvailableoofGroup
    get() = AvailableoofGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val AvailableoofGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Large.AllIcons + Medium.AllIcons + Small.AllIcons + listOf()
        return __AllIcons!!
    }
