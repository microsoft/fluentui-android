/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import android.graphics.Bitmap
import android.view.View
import androidx.annotation.DrawableRes

interface CommandItem {

    interface OnItemClickListener {
        fun onItemClick(item: CommandItem, view: View)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(item: CommandItem, view: View): Boolean
    }

    /**
     * The identifier of command item.
     */
    fun getId(): Int? = null

    /**
     * The tag of command item.
     */
    fun getTag(): String? = null

    /**
     * The drawable based primary content displayed on the CommandItem
     */
    @DrawableRes
    fun getIcon(): Int = 0

    /**
     * The bitmap based primary content displayed on the CommandItem
     */
    fun getBitmapIcon(): Bitmap? = null

    /**
     * The secondary content displayed on the CommandItem if [getIcon] is not set
     */
    fun getLabel(): CharSequence? = null

    /**
     * Sets content description on the View CommandItem bound.
     *
     * A content description briefly describes the view and is primarily used
     * for accessibility support to determine how a view should be presented to
     * the user.
     */
    fun getContentDescription(): String? = null

    /**
     * Returns the enabled status for this view CommandItem bound.
     */
    fun isEnabled(): Boolean = true

    /**
     * Indicates the selection state of this view CommandItem bound.
     */
    fun isSelected(): Boolean = false

    /**
     * Indicates the view for the CommandItem
     */
    fun getView(): View? = null
}
