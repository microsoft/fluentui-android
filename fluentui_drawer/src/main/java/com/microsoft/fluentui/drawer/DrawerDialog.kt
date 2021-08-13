/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.drawer

import android.content.Context
import android.graphics.Point
import android.os.Handler
import android.view.*
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.appcompat.app.ActionBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.appcompat.app.AppCompatDialog
import com.microsoft.fluentui.drawer.databinding.DialogDrawerBinding
import com.microsoft.fluentui.drawer.databinding.DialogSideDrawerBinding
import com.microsoft.fluentui.drawer.databinding.DialogTopDrawerBinding
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.activity
import com.microsoft.fluentui.util.displaySize
import com.microsoft.fluentui.util.statusBarHeight

/**
 * [DrawerDialog] is used for displaying a modal dialog in the form of an expanding and collapsing sheet
 * to which content is added.
 */
open class DrawerDialog @JvmOverloads constructor(context: Context, val behaviorType: BehaviorType = BehaviorType.BOTTOM, @StyleRes theme: Int = 0) : AppCompatDialog(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Drawer), if (theme == 0) R.style.Drawer_FluentUI else theme) {
    companion object {
        private const val DISMISS_THRESHOLD = 0.005f
    }

    enum class BehaviorType {
        BOTTOM,TOP, LEFT, RIGHT
    }

    enum class TitleBehavior {
        DEFAULT, HIDE_TITLE, BELOW_TITLE
    }

    var onDrawerContentCreatedListener: OnDrawerContentCreatedListener? = null

    private var sheetBehavior: CoordinatorLayout.Behavior<View>? = null

    private val sheetCallback = object : CustomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if(newState == BottomSheetBehavior.STATE_COLLAPSED) // when state is STATE_COLLAPSED
                dismissDialog()
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                if (drawerContent.childCount > 0) {
                    drawerContent.getChildAt(0)?.requestFocus()
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            if (isExpanded && slideOffset < DISMISS_THRESHOLD && slideOffset > 0)
                dismiss()
        }
    }

    private var isExpanded: Boolean = false
    protected val container: View
    protected val drawerContent: ViewGroup
    private val drawer: View

    /**
     * This field [dimValue] contains dim value of background[0.0 -> no fade, 0.5-> dim, 1.0->opaque]
     * This field [anchorView] contains the anchor view  below which the dialog will appear
     * This field [titleBehavior] stores whether the dialog should  be below title bar or hide titler bar.
     * */
    private var dimValue = 0.5f
    private var anchorView: View? = null
    private var titleBehavior: TitleBehavior = TitleBehavior.DEFAULT

    init {
        when(behaviorType){
            BehaviorType.BOTTOM -> {
                val binding = DialogDrawerBinding.inflate(layoutInflater)
                container = binding.root
                drawerContent = binding.drawerContent
                drawer = binding.drawer
            }
            BehaviorType.TOP -> {
                val binding = DialogTopDrawerBinding.inflate(layoutInflater)
                container = binding.root
                drawerContent = binding.drawerContent
                drawer = binding.drawer
            }
            BehaviorType.RIGHT, BehaviorType.LEFT -> {
                val binding = DialogSideDrawerBinding.inflate(layoutInflater)
                container = binding.root
                drawerContent = binding.drawerContent
                drawer = binding.drawer
            }
        }

        container.setOnClickListener {
            collapse()
        }
    }

    constructor(context: Context, behaviorType: BehaviorType=BehaviorType.BOTTOM, dimValue: Float=0.5f, anchorView:View?=null, titleBehavior: TitleBehavior=TitleBehavior.DEFAULT, @StyleRes theme: Int = 0) : this(context, behaviorType, theme) {
        this.dimValue = dimValue
        this.anchorView = anchorView
        this.titleBehavior = titleBehavior
    }

    override fun setContentView(layoutResID: Int) {
        val content = layoutInflater.inflate(layoutResID, drawerContent, false)
        setContentView(content)
        onDrawerContentCreatedListener?.onDrawerContentCreated(content)
        sheetBehavior = when (behaviorType) {
            BehaviorType.BOTTOM -> BottomSheetBehavior.from(drawer)
            BehaviorType.TOP -> TopSheetBehavior.from(drawer)
            BehaviorType.RIGHT, BehaviorType.LEFT -> SideSheetBehavior.from(drawer)
        }
    }

    override fun setContentView(view: View) {
        drawerContent.removeAllViews()
        drawerContent.addView(view)
        sheetBehavior = when (behaviorType) {
            BehaviorType.BOTTOM -> BottomSheetBehavior.from(drawer)
            BehaviorType.TOP -> TopSheetBehavior.from(drawer)
            BehaviorType.RIGHT, BehaviorType.LEFT -> SideSheetBehavior.from(drawer)
        }
    }

    override fun show() {
        super.show()
        val expandDelayMilliseconds = context.resources.getInteger(R.integer.fluentui_drawer_fade_in_milliseconds).toLong()
        Handler().postDelayed(::expand, expandDelayMilliseconds)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var topMargin = 0

        if(anchorView != null || titleBehavior != TitleBehavior.DEFAULT)
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        if(anchorView != null) {
            val screenPos = IntArray(2)
            anchorView?.getLocationOnScreen(screenPos)
            topMargin= screenPos[1]+anchorView!!.height
        }
        else {
            when(titleBehavior) {
                TitleBehavior.HIDE_TITLE -> {
                    topMargin = context.statusBarHeight
                }
                TitleBehavior.BELOW_TITLE -> {
                    val actionBar:ActionBar? = context.activity?.supportActionBar
                    if(actionBar != null)
                        topMargin = context.statusBarHeight+ actionBar.height
                }
                TitleBehavior.DEFAULT -> { }
            }
        }

        val displaySize: Point = context.displaySize
        val layoutParams: WindowManager.LayoutParams? = window?.attributes
        layoutParams?.gravity = Gravity.TOP
        layoutParams?.y = topMargin
        layoutParams?.dimAmount = this.dimValue
        window?.attributes = layoutParams
        window?.setLayout(displaySize.x, displaySize.y-topMargin)

        super.setContentView(container)
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).setBottomSheetCallback(sheetCallback)
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setTopSheetCallback(sheetCallback)
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
            is BottomSheetBehavior -> {
                (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_COLLAPSED
                if((sheetBehavior as BottomSheetBehavior<View>).state == BottomSheetBehavior.STATE_COLLAPSED)
                    dismissDialog()
            }
            is TopSheetBehavior -> {
                (sheetBehavior as TopSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_COLLAPSED)
                if((sheetBehavior as TopSheetBehavior<View>).getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    dismissDialog()
            }
            is SideSheetBehavior -> {
                (sheetBehavior as SideSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_COLLAPSED)
                if((sheetBehavior as SideSheetBehavior<View>).getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    dismissDialog()
            }
        }
    }

    fun dismissDialog() {
        super.dismiss()
    }

    private fun expand() {
        // For persistent instances calling requestLayout fixes incorrect positioning of the drawer
        // after rotation from landscape to portrait
        drawer.requestLayout()
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_EXPANDED
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_EXPANDED)
            is SideSheetBehavior -> (sheetBehavior as SideSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_EXPANDED)
        }
        isExpanded = true
    }

    protected fun collapse() {
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_COLLAPSED
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_COLLAPSED)
            is SideSheetBehavior -> (sheetBehavior as SideSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_COLLAPSED)
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

abstract class CustomSheetCallback: BottomSheetBehavior.BottomSheetCallback()
