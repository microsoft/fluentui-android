package com.microsoft.fluentui.icons.avataricons.presence.available

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.presence.AvailableGroup
import com.microsoft.fluentui.icons.avataricons.presence.available.medium.Dark
import com.microsoft.fluentui.icons.avataricons.presence.available.medium.Light
import kotlin.collections.List as ____KtList

object MediumGroup

val AvailableGroup.Medium: MediumGroup
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
