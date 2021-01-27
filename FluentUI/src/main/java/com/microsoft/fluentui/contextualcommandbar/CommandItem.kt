/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.contextualcommandbar

import android.support.annotation.DrawableRes

interface CommandItem {

    fun getId(): String? = null

    @DrawableRes
    fun getIcon(): Int = 0

    fun getLabel(): String? = null

    fun isEnabled(): Boolean = true

    fun isSelected(): Boolean = false
}
