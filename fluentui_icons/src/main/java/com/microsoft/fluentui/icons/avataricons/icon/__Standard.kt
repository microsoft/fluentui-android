package com.microsoft.fluentui.icons.avataricons.icon

import androidx.compose.ui.graphics.vector.ImageVector
import com.microsoft.fluentui.icons.avataricons.IconGroup
import com.microsoft.fluentui.icons.avataricons.icon.standard.*
import kotlin.collections.List as ____KtList

object StandardGroup

val IconGroup.Standard: StandardGroup
    get() = StandardGroup

private var __AllIcons: ____KtList<ImageVector>? = null

val StandardGroup.AllIcons: ____KtList<ImageVector>
    get() {
        if (__AllIcons != null) {
            return __AllIcons!!
        }
        __AllIcons = listOf(Large, Medium, Small, Xlarge, Xsmall, Xxlarge)
        return __AllIcons!!
    }
