/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import android.util.Log
import android.util.TypedValue

/**
 *[ThemeUtil] came from client-android. Fluent UI currently uses this to generate background colors for
 * initials based on an array of colors defined in the app's theme.
 * //TODO: Trim this code down to what Fluent UI will actually need to use.
 */
object ThemeUtil {
    private const val TAG = "ThemeUtil"

    private val TYPED_VALUE_THREAD_LOCAL = object : ThreadLocal<TypedValue>() {
        override fun initialValue(): TypedValue {
            return TypedValue()
        }
    }

    private val TEMP_ARRAY = object : ThreadLocal<IntArray>() {
        override fun initialValue(): IntArray {
            return IntArray(1)
        }
    }

    private val DISABLED_STATE_SET = intArrayOf(-android.R.attr.state_enabled)

    /**
     * Get a themed color based on an attribute.
     *
     * @param context       a context
     * @param attrId        an attribute ID (e.g. "R.attr.outlookBlue")
     * @return              the themed color of the attribute
     */
    @ColorInt
    fun getColor(context: Context, @AttrRes attrId: Int): Int {
        val typedValue = TYPED_VALUE_THREAD_LOCAL.get()
        val theme = context.theme

        if (!theme.resolveAttribute(attrId, typedValue, true)) {
            val themeName = getThemeName(context)
            throw IllegalArgumentException("Theme (" + themeName + ") doesn't contain given attribute "
                    + context.resources.getResourceEntryName(attrId))
        }

        return typedValue.data
    }

    fun getResId(context: Context, @AttrRes attrId: Int): Int {
        val typedValue = TYPED_VALUE_THREAD_LOCAL.get()
        val theme = context.theme

        if (!theme.resolveAttribute(attrId, typedValue, false)) {
            val themeName = getThemeName(context)
            throw IllegalArgumentException("Theme (" + themeName + ") doesn't contain given attribute "
                    + context.resources.getResourceEntryName(attrId))
        }

        return typedValue.data
    }

    fun getColors(context: Context, @ArrayRes arrayId: Int): IntArray {
        val array = context.resources.obtainTypedArray(arrayId)
        try {
            val results = IntArray(array.length())
            var i = 0
            val size = results.size
            while (i < size) {
                results[i] = array.getColor(i, 0)
                ++i
            }
            return results
        } finally {
            array.recycle()
        }
    }

    fun getThemeAttrColorStateList(context: Context, @AttrRes attr: Int): ColorStateList? {
        TEMP_ARRAY.get()[0] = attr
        val a = context.obtainStyledAttributes(null, TEMP_ARRAY.get())
        try {
            return a.getColorStateList(0)
        } finally {
            a.recycle()
        }
    }

    @ColorInt
    fun getDisabledThemeAttrColor(context: Context, @AttrRes attr: Int): Int {
        val csl = getThemeAttrColorStateList(context, attr)
        if (csl != null && csl.isStateful) {
            // If the CSL is stateful, we'll assume it has a disabled state and use it
            return csl.getColorForState(DISABLED_STATE_SET, csl.defaultColor)
        } else {
            // Else, we'll generate the color using disabledAlpha from the theme
            val tv = TYPED_VALUE_THREAD_LOCAL.get()
            // Now retrieve the disabledAlpha value from the theme
            context.theme.resolveAttribute(android.R.attr.disabledAlpha, tv, true)
            val disabledAlpha = tv.float
            return getThemeAttrColor(context, attr, disabledAlpha)
        }
    }

    @ColorInt
    @JvmOverloads
    fun getThemeAttrColor(context: Context, @AttrRes attr: Int, alpha: Float = 1f): Int {
        TEMP_ARRAY.get()[0] = attr
        val a = context.obtainStyledAttributes(null, TEMP_ARRAY.get())
        try {
            val color = a.getColor(0, 0)
            val originalAlpha = Color.alpha(color)
            return ColorUtils.setAlphaComponent(color, Math.round(originalAlpha * alpha))
        } finally {
            a.recycle()
        }
    }

    private fun getThemeName(context: Context): String? {
        try {
            val wrapper = Context::class.java
            val method = wrapper.getMethod("getThemeResId")
            method.isAccessible = true
            val themeId = method.invoke(context) as Int
            return context.resources.getResourceName(themeId)

        } catch (e: Exception) {
            Log.e(TAG, "Failed to get theme name.", e)
        }

        return null
    }
}

fun Context.getTintedDrawable(@DrawableRes drawableId: Int, @ColorInt tint: Int): Drawable? {
    val drawable = ContextCompat.getDrawable(this, drawableId) ?: return null
    drawable.mutate().setTint(tint)
    return drawable
}
