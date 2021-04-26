/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.util

import android.content.Context
import androidx.annotation.StyleRes

fun Context.getTextSize(@StyleRes textAppearanceResourceId: Int): Float {
    val textAttributes = obtainStyledAttributes(textAppearanceResourceId, intArrayOf(android.R.attr.textSize))
    val textSize = textAttributes.getDimension(0, -1f)
    textAttributes.recycle()
    return textSize
}