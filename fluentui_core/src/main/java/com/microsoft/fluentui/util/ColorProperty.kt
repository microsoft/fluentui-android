/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.util

import androidx.core.view.ViewCompat
import android.view.View

/**
 * [ColorProperty] a class to manipulate colors where performance is a concern
 */
class ColorProperty(name: String, private val startColor: Int, private val endColor: Int) : FloatProperty<View>(name) {
    var color: Int = 0
        private set

    private var value: Float = 0f

    override fun setValue(view: View, value: Float) {
        this.value = value

        val startA = startColor shr 24 and 0xFF
        val startR = startColor shr 16 and 0xFF
        val startG = startColor shr 8 and 0xFF
        val startB = startColor and 0xFF

        val endA = endColor shr 24 and 0xFF
        val endR = endColor shr 16 and 0xFF
        val endG = endColor shr 8 and 0xFF
        val endB = endColor and 0xff

        color = startA + (value * (endA - startA)).toInt() shl 24 or
                (startR + (value * (endR - startR)).toInt() shl 16) or
                (startG + (value * (endG - startG)).toInt() shl 8) or
                startB + (value * (endB - startB)).toInt()

        ViewCompat.postInvalidateOnAnimation(view)
    }

    override fun get(`object`: View) = value
}
