package com.microsoft.fluentui.icons.avataricons.presence.available

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.presence.AvailableGroup
import com.microsoft.fluentui.icons.avataricons.presence.available.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.available.large.Light
import kotlin.collections.List as ____KtList

object LargeGroup

val AvailableGroup.Large: LargeGroup
    get() = LargeGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val LargeGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Dark, Light)
        return __AllIcons!!
    }
