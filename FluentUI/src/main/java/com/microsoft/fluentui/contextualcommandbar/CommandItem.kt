package com.microsoft.fluentui.contextualcommandbar

import android.support.annotation.DrawableRes

interface CommandItem {

    val enabled: Boolean
        get() = true

    val selected: Boolean
        get() = false

    val contentDescription: String?
        get() = null

    @DrawableRes
    fun getIcon(): Int
}
