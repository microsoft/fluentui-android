/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import android.support.annotation.DrawableRes

interface CommandItem {

    fun getId(): String? = null

    @DrawableRes
    fun getIcon(): Int

    fun getContentDescription(): String? = null

    fun setEnabled(enabled: Boolean)

    fun isEnabled(): Boolean = true

    fun setSelected(selected: Boolean)

    fun isSelected(): Boolean = false
}
