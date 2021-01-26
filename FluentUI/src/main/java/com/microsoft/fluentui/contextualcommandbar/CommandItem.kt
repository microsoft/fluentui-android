package com.microsoft.fluentui.contextualcommandbar

import android.support.annotation.DrawableRes

interface CommandItem {

    fun getId(): String? = null

    @DrawableRes
    fun getIcon(): Int

    fun getContentDescription(): String? = null

    fun isEnabled(): Boolean = true

    fun isSelected(): Boolean = false
}
