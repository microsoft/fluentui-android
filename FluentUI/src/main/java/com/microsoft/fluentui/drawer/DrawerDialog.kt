/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.drawer

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.annotation.StyleRes
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.support.v7.app.AppCompatDialog
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.displaySize
import com.microsoft.fluentui.util.statusBarHeight
import kotlinx.android.synthetic.main.dialog_drawer.*
import kotlinx.android.synthetic.main.dialog_drawer.view.*
import java.lang.reflect.Array.get

/**
 * [DrawerDialog] is used for displaying a modal dialog in the form of an expanding and collapsing sheet
 * to which content is added.
 */
open class DrawerDialog @JvmOverloads constructor(context: Context, val behaviorType: BehaviorType = BehaviorType.BOTTOM, @StyleRes theme: Int = 0) : AppCompatDialog(FluentUIContextThemeWrapper(context), if (theme == 0) R.style.Drawer_FluentUI else theme) {
    companion object {
        private const val DISMISS_THRESHOLD = 0.005f
    }

    enum class BehaviorType {
        BOTTOM,TOP
    }

    /**
     * This field contains dim value of background[0.0 -> no fade, 0.5-> dim, 1.0->opaque]
     */

    var onDrawerContentCreatedListener: OnDrawerContentCreatedListener? = null

    private var sheetBehavior: CoordinatorLayout.Behavior<View>? = null

    private val sheetCallback = object : TopSheetBehavior.Companion.TopSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            // No op
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            if (isExpanded && slideOffset < DISMISS_THRESHOLD && slideOffset > 0)
                dismiss()
        }
    }

    private var isExpanded: Boolean = false
    protected val container: View
    private var dimValue = 0.5f

    init {
        this.container = when(behaviorType){
            BehaviorType.BOTTOM -> layoutInflater.inflate(R.layout.dialog_drawer,null)
            BehaviorType.TOP -> layoutInflater.inflate(R.layout.dialog_top_drawer,null)
        }
        container.setOnClickListener {
            collapse()
        }
    }

    constructor(context: Context, behaviorType: BehaviorType, dimValue: Float, @StyleRes theme: Int = 0) : this(context, behaviorType, theme) {
        this.dimValue = dimValue
    }

    override fun setContentView(layoutResID: Int) {
        val content = layoutInflater.inflate(layoutResID, container.drawer_content, false)
        setContentView(content)
        onDrawerContentCreatedListener?.onDrawerContentCreated(content)
        sheetBehavior = when (behaviorType) {
            BehaviorType.BOTTOM -> BottomSheetBehavior.from(container.drawer as View)
            BehaviorType.TOP -> TopSheetBehavior.from(container.drawer as View)
        }
    }

    override fun setContentView(view: View) {
        container.drawer_content.removeAllViews()
        container.drawer_content.addView(view)
        sheetBehavior = when (behaviorType) {
            BehaviorType.BOTTOM -> BottomSheetBehavior.from(container.drawer as View)
            BehaviorType.TOP -> TopSheetBehavior.from(container.drawer as View)
        }
    }

    override fun show() {
        super.show()
        val expandDelayMilliseconds = context.resources.getInteger(R.integer.fluentui_drawer_fade_in_milliseconds).toLong()
        Handler().postDelayed(::expand, expandDelayMilliseconds)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        val layoutParams: WindowManager.LayoutParams? = window?.attributes
        layoutParams?.dimAmount = this.dimValue
        window?.attributes = layoutParams
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        super.setContentView(container)
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).setBottomSheetCallback(sheetCallback)
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setTopSheetCallback(sheetCallback)
        }
    }

    override fun onBackPressed() {
        collapse()
    }

    override fun dismiss() {
        isExpanded = false
        // Dismiss may be called by external objects so state is set to STATE_COLLAPSED in order for
        // the drawer to animate up
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_COLLAPSED
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setStateOuter(TopSheetBehavior.STATE_COLLAPSED)
        }
        super.dismiss()
    }

    private fun expand() {
        // For persistent instances calling requestLayout fixes incorrect positioning of the drawer
        // after rotation from landscape to portrait
        drawer.requestLayout()
        updateHeight()
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_EXPANDED
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setStateOuter(TopSheetBehavior.STATE_EXPANDED)
        }
        isExpanded = true
    }

    // Make sure dialog is not under status bar and is fully visible
    private fun updateHeight() {
        val maxHeight = context.displaySize.y - context.statusBarHeight
        if (drawer.height > maxHeight) {
            val layoutParams = drawer.layoutParams
            layoutParams.height = maxHeight
            drawer.layoutParams = layoutParams
        }
    }

    protected fun collapse() {
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_COLLAPSED
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setStateOuter(TopSheetBehavior.STATE_COLLAPSED)
        }
    }

    // external method to set dimValue
    fun setFade(x: Float) {
        this.dimValue = x
        this.onAttachedToWindow()
    }
}

interface OnDrawerContentCreatedListener {
    fun onDrawerContentCreated(drawerContents: View)
}
