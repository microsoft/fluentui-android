/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.util

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView

/**
 * Adds a given [view] to a [ViewGroup]. Especially useful when you need a custom view in a control.
 * Use [updateLayout] to update any part of the view's layout before adding it to the [ViewGroup].
 */
fun ViewGroup.setContentAndUpdateVisibility(view: View?, updateLayout: (() -> Unit)? = null) {
    // We need to remove the view each time so that RecyclerViews can properly recycle the view.
    removeAllViews()

    if (view == null) {
        visibility = View.GONE
        return
    }

    // Make sure the custom view isn't already in a ViewGroup.
    // With RecyclerView reusing ViewHolders, it could have a different parent than the current container.
    (view.parent as? ViewGroup)?.removeView(view)

    updateLayout?.invoke()
    addView(view)
    visibility = View.VISIBLE
}

/**
 * Returns an ImageView containing a Drawable.
 * @param imageId a Drawable resource id.
 * @param imageTint a color integer that will be applied as tint to the drawable. Default is transparent.
 */
fun Context.createImageView(@DrawableRes imageId: Int, @ColorInt imageTint: Int? = null): ImageView {
    val drawable = if (imageTint != null)
        getTintedDrawable(imageId, imageTint)
    else
        ContextCompat.getDrawable(this, imageId)

    val imageView = ImageView(this)
    imageView.setImageDrawable(drawable)
    return imageView
}

/**
 * Returns an ImageView containing a Drawable.
 * @param bitmap - expected a compressed bitmap which consumer wants to show
 */
fun Context.createImageView(bitmap: Bitmap): ImageView {
    val imageView = ImageView(this)
    imageView.setImageBitmap(bitmap)
    return imageView
}

/**
 * Sets a view's visibility based on a boolean [isVisible].
 */
var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }

/**
 * Shows the soft keyboard.
 */
fun View.toggleKeyboardVisibility() {
    context.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

/**
 * Retrieves the current activity from the context.
 */
val Context.activity: AppCompatActivity?
    get() {
        if (this is ContextWrapper)
            return if (this is AppCompatActivity)
                this
            else
                baseContext.activity

        return null
    }