package com.microsoft.fluentui.icons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.AllIcons
import com.microsoft.fluentui.icons.avataricons.Icon
import com.microsoft.fluentui.icons.avataricons.Presence
import kotlin.collections.List as ____KtList

object AvatarIcons

private var __AllIcons: ____KtList<ImageVector>? = null

val AvatarIcons.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Icon.AllIcons + Presence.AllIcons + listOf()
        return __AllIcons!!
    }
