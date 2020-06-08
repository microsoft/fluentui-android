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
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.displaySize
import com.microsoft.fluentui.util.statusBarHeight
import kotlinx.android.synthetic.main.dialog_drawer.*
import kotlinx.android.synthetic.main.dialog_drawer.view.*
import java.lang.reflect.Array.get

/**
 * [DrawerDialog] is used for displaying a modal dialog in the form of an expanding and collapsing bottom sheet
 * to which content is added.
 */
open class DrawerDialog : AppCompatDialog {
    companion object {
        private const val DISMISS_THRESHOLD = 0.005f
    }

    enum class BehaviorType {
        BOTTOM, LEFT, RIGHT
    }

    val behaviorType:BehaviorType;

    var onDrawerContentCreatedListener: OnDrawerContentCreatedListener? = null

    private var sheetBehavior: CoordinatorLayout.Behavior<View>? = null

    private val sheetCallback = object : SideSheetBehavior.Companion.SideSheetCallback() {
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

    @JvmOverloads
    constructor(context: Context, behaviorType: BehaviorType = BehaviorType.BOTTOM, @StyleRes theme: Int = 0) : super(FluentUIContextThemeWrapper(context), if (theme == 0) R.style.Drawer_FluentUI else theme) {
        this.behaviorType = behaviorType
        this.container = when(behaviorType){
            BehaviorType.BOTTOM -> layoutInflater.inflate(R.layout.dialog_drawer,null)
            BehaviorType.RIGHT, BehaviorType.LEFT -> layoutInflater.inflate(R.layout.dialog_side_drawer, null)
        }
        container.setOnClickListener {
            collapse()
        }
    }

    override fun setContentView(layoutResID: Int) {
        val content = layoutInflater.inflate(layoutResID, container.drawer_content, false)
        setContentView(content)
        onDrawerContentCreatedListener?.onDrawerContentCreated(content)
        sheetBehavior = when (behaviorType) {
            BehaviorType.BOTTOM -> BottomSheetBehavior.from(container.drawer as View)
            BehaviorType.RIGHT, BehaviorType.LEFT -> SideSheetBehavior.from(container.drawer as View)
        }
    }

    override fun setContentView(view: View) {
        container.drawer_content.removeAllViews()
        container.drawer_content.addView(view)
        sheetBehavior = when (behaviorType) {
            BehaviorType.BOTTOM -> BottomSheetBehavior.from(container.drawer as View)
            BehaviorType.RIGHT, BehaviorType.LEFT -> SideSheetBehavior.from(container.drawer as View)
        }
    }

    override fun show() {
        super.show()
        val expandDelayMilliseconds = context.resources.getInteger(R.integer.fluentui_drawer_fade_in_milliseconds).toLong()
        Handler().postDelayed(::expand, expandDelayMilliseconds)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        super.setContentView(container)
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).setBottomSheetCallback(sheetCallback)
            is SideSheetBehavior -> {
                (sheetBehavior as SideSheetBehavior<View>).setSideSheetCallBack(sheetCallback)
                (sheetBehavior as SideSheetBehavior<View>).behaviorType = when(behaviorType) {
                        BehaviorType.RIGHT -> SideSheetBehavior.Companion.BehaviorType.RIGHT
                        else -> SideSheetBehavior.Companion.BehaviorType.LEFT
                }
            }
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
            is SideSheetBehavior -> (sheetBehavior as SideSheetBehavior<View>).setStateOuter(SideSheetBehavior.STATE_COLLAPSED)
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
            is SideSheetBehavior -> (sheetBehavior as SideSheetBehavior<View>).setStateOuter(SideSheetBehavior.STATE_EXPANDED)
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
            is SideSheetBehavior -> (sheetBehavior as SideSheetBehavior<View>).setStateOuter(SideSheetBehavior.STATE_COLLAPSED)
        }
    }
}

interface OnDrawerContentCreatedListener {
    fun onDrawerContentCreated(drawerContents: View)
}
