package com.microsoft.fluentui.icons.avataricons.presence

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.PresenceGroup
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.AllIcons
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.Large
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.Medium
import com.microsoft.fluentui.icons.avataricons.presence.awayoof.Small
import kotlin.collections.List as ____KtList

object AwayoofGroup

val PresenceGroup.Awayoof: AwayoofGroup
    get() = AwayoofGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val AwayoofGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Large.AllIcons + Medium.AllIcons + Small.AllIcons + listOf()
        return __AllIcons!!
    }
