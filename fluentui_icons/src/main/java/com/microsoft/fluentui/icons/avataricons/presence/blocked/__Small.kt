package com.microsoft.fluentui.icons.avataricons.presence.blocked

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.presence.BlockedGroup
import com.microsoft.fluentui.icons.avataricons.presence.blocked.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.blocked.small.Light
import kotlin.collections.List as ____KtList

object SmallGroup

val BlockedGroup.Small: SmallGroup
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
