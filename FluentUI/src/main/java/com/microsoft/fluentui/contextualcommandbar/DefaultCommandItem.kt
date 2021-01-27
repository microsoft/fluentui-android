package com.microsoft.fluentui.contextualcommandbar

import android.support.annotation.DrawableRes

open class DefaultCommandItem(
        @DrawableRes private var icon: Int,
        private var description: String? = null,
        private var enabled: Boolean = true,
        private var selected: Boolean = false
) : CommandItem {

    override fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun setSelected(selected: Boolean) {
        this.selected = selected
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