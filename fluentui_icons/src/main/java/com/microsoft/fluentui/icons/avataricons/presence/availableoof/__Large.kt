package com.microsoft.fluentui.icons.avataricons.presence.availableoof

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.presence.AvailableoofGroup
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.large.Dark
import com.microsoft.fluentui.icons.avataricons.presence.availableoof.large.Light
import kotlin.collections.List as ____KtList

object LargeGroup

val AvailableoofGroup.Large: LargeGroup
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
