package com.microsoft.fluentui.icons.avataricons.presence.unknown

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.presence.UnknownGroup
import com.microsoft.fluentui.icons.avataricons.presence.unknown.small.Dark
import com.microsoft.fluentui.icons.avataricons.presence.unknown.small.Light
import kotlin.collections.List as ____KtList

object SmallGroup

val UnknownGroup.Small: SmallGroup
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
