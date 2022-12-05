/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import android.graphics.Bitmap
import android.view.View
import androidx.annotation.DrawableRes

open class DefaultCommandItem(
    @DrawableRes private var icon: Int = 0,
    private var label: String? = null,
    private var contentDescription: String? = null,
    private var enabled: Boolean = true,
    private var selected: Boolean = false,
    private var bitmap: Bitmap? = null,
    private var view: View? = null
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

    override fun getBitmapIcon(): Bitmap? {
        return bitmap
    }

    override fun getContentDescription(): String? {
        return contentDescription
    }

    override fun setView(view: View) {
        this.view = view
    }

    override fun getView(): View? {
        return view
    }
}
