package com.microsoft.fluentui.contextualcommandbar

import android.support.annotation.DrawableRes

interface CommandItem {

    val enabled: Boolean
        get() = true

    val selected: Boolean
        get() = false

    @DrawableRes
    fun getIcon(): Int
}
