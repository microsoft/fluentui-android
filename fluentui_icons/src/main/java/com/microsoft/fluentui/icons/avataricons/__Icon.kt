package com.microsoft.fluentui.icons.avataricons

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.AvatarIcons
import com.microsoft.fluentui.icons.avataricons.icon.AllIcons
import com.microsoft.fluentui.icons.avataricons.icon.Anonymous
import com.microsoft.fluentui.icons.avataricons.icon.Standard
import kotlin.collections.List as ____KtList

object IconGroup

val AvatarIcons.Icon: IconGroup
    get() = IconGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val IconGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = Anonymous.AllIcons + Standard.AllIcons + listOf()
        return __AllIcons!!
    }
