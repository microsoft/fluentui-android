package com.microsoft.fluentui.icons.avataricons.icon

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.IconGroup
import com.microsoft.fluentui.icons.avataricons.icon.anonymous.*
import kotlin.collections.List as ____KtList

object AnonymousGroup

val IconGroup.Anonymous: AnonymousGroup
    get() = AnonymousGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val AnonymousGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Large, Medium, Small, Xlarge, Xsmall, Xxlarge)
        return __AllIcons!!
    }
