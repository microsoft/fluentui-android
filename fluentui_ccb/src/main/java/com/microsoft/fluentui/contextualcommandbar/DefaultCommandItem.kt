/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import androidx.annotation.DrawableRes

open class DefaultCommandItem(
        @DrawableRes private var icon: Int = 0,
        private var label: String? = null,
        private var contentDescription: String? = null,
        private var enabled: Boolean = true,
        private var selected: Boolean = false
) : CommandItem {

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    fun setSelected(selected: Boolean) {
        this.selected = selected
    }

    override fun isSelected(): Boolean {
        return selected
    }

    override fun getLabel(): String? {
        return label
    }

    override fun getIcon(): Int {
        return icon
    }

    override fun getContentDescription(): String? {
        return contentDescription
    }
}
