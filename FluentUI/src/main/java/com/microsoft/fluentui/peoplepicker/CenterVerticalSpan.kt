/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.peoplepicker

import android.graphics.Rect
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

/**
 * [CenterVerticalSpan] shifts the baseline of a substring to the center of the text paint bounds.
 * This class comes in handy when you have a substring that is taller or shorter than the rest of your text
 * and you need to center it vertically. It compares the [substringBounds] of your substring to the text paint bounds
 * and shifts the baseline accordingly.
 */
internal class CenterVerticalSpan(private val substringBounds: Rect) : MetricAffectingSpan() {
    override fun updateDrawState(tp: TextPaint) {
        shiftBaselineToCenter(tp)
    }

    override fun updateMeasureState(tp: TextPaint) {
        shiftBaselineToCenter(tp)
    }

    private fun shiftBaselineToCenter(tp: TextPaint) {
        val topDifference = tp.fontMetrics.top - substringBounds.top
        tp.baselineShift += (topDifference / 2).toInt()
    }
}