package com.microsoft.fluentui.contextualcommandbar

import android.support.annotation.DrawableRes

open class DefaultCommandItem(
        @DrawableRes private val icon: Int,
        private val description: String? = null,
        private val enabled: Boolean = true,
        private val selected: Boolean = false
) : CommandItem {

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun isSelected(): Boolean {
        return selected
    }

    override fun getContentDescription(): String? {
        return description
    }

    override fun getIcon(): Int {
        return icon
    }
}