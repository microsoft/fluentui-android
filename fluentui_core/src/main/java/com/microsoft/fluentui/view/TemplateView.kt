/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.view

import android.content.Context
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * [TemplateView] is an abstract class designed for building views that have their UI defined in one or more layout files.
 * These layout files are called templates. Once template is loaded it's added to the view as the only child.
 * After that the view becomes a proxy and forwards [onMeasure]/[onLayout] calls to its template root view.
 * This approach allows view to hide its UI implementation details from consuming developer and
 * easily switch from one UI to another.
 *
 * Subclasses must override [templateId] and return resource id for the layout file(s) that will be used as templates.
 *
 * [onTemplateLoaded] can be used to get and store references to views inside template.
 * Use [findViewInTemplateById] for this.
 * The reference to the root view of template is stored in [templateRoot].
 *
 * Call [invalidateTemplate] to tell your view that current template is not valid anymore and should be reloaded.
 * If template needs to be reloaded then this will happen after attachment of the view to the window,
 * after inflation of the view, or at the next layout pass (in [onMeasure]).
 * Reloading of the template can be forced by calling [reloadTemplateIfInvalid].
 *
 * Performance: [TemplateView] adds about 0.035-0.05ms to full measure/layout pass (on Google Pixel 2 XL).
 *
 * Note: All addView* and removeView* methods are blocked and throw Unsupported Operation exception.
 */
abstract class TemplateView : ViewGroup {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)
    override fun addView(child: View) {
        throw UnsupportedOperationException("addView(View) is not supported in TemplateView")
    }

    override fun addView(child: View, index: Int) {
        throw UnsupportedOperationException("addView(View, int) is not supported in TemplateView")
    }

    override fun addView(child: View, params: ViewGroup.LayoutParams) {
        throw UnsupportedOperationException("addView(View, LayoutParams) is not supported in TemplateView")
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        throw UnsupportedOperationException("addView(View, int, LayoutParams) is not supported in TemplateView")
    }

    override fun removeView(child: View) {
        throw UnsupportedOperationException("removeView(View) is not supported in TemplateView")
    }

    override fun removeViewAt(index: Int) {
        throw UnsupportedOperationException("removeViewAt(int) is not supported in TemplateView")
    }

    override fun removeAllViews() {
        throw UnsupportedOperationException("removeAllViews() is not supported in TemplateView")
    }

    override fun shouldDelayChildPressedState(): Boolean = false

    // Template

    protected abstract val templateId: Int
        @LayoutRes get
    protected var templateRoot: View? = null
        private set

    private var isTemplateValid: Boolean = false

    protected fun <T : View> findViewInTemplateById(@IdRes id: Int): T? {
        return templateRoot?.findViewById(id)
    }

    protected fun invalidateTemplate() {
        isTemplateValid = false
        requestLayout()
    }

    protected open fun onTemplateLoaded() { }

    protected fun reloadTemplateIfInvalid() {
        if (!isTemplateValid)
            reloadTemplate()
    }

    private fun reloadTemplate() {
        templateRoot?.let {
            removeInternalView(it)
            templateRoot = null
        }

        templateRoot = LayoutInflater.from(context).inflate(templateId, this, false)

        templateRoot?.let {
            addInternalView(it)
        }

        isTemplateValid = true

        if (templateRoot != null)
            onTemplateLoaded()
    }

    // Internal view management

    protected fun addInternalView(view: View) {
        super.addView(view, -1, view.layoutParams ?: generateDefaultLayoutParams())
    }

    protected fun removeInternalView(view: View) {
        super.removeView(view)
    }

    // Lifecycle

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        reloadTemplateIfInvalid()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        reloadTemplateIfInvalid()
    }

    // Layout

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        reloadTemplateIfInvalid()
        val templateRoot = templateRoot
        if (templateRoot == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        measureChild(templateRoot, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(
            resolveSizeAndState(templateRoot.measuredWidth, widthMeasureSpec, templateRoot.measuredState),
            resolveSizeAndState(templateRoot.measuredHeight, heightMeasureSpec, templateRoot.measuredState shl MEASURED_HEIGHT_STATE_SHIFT)
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        templateRoot?.layout(0, 0, right - left, bottom - top)
    }
}