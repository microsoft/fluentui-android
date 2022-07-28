package com.microsoft.fluentui.icons.avataricons.presence.blocked

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.presence.BlockedGroup
import com.microsoft.fluentui.icons.avataricons.presence.blocked.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.blocked.medium.Light
import kotlin.collections.List as ____KtList

object MediumGroup

val BlockedGroup.Medium: MediumGroup
    get() = MediumGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val MediumGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Dark, Light)
        return __AllIcons!!
    }
