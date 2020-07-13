/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.design.widget.BottomSheetBehavior
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.microsoft.fluentui.R
import com.microsoft.fluentui.drawer.OnDrawerContentCreatedListener
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.view.TemplateView
import kotlinx.android.synthetic.main.view_persistent_sheet.view.*

/**
 * [PersistentBottomSheet] is used to display a bottomSheet with a persistent collapsed state of some peekHeight
 */
class PersistentBottomSheet: TemplateView {
    companion object {
        private const val FADE_OUT_THRESHOLD = 0.010f
        private const val FADE_IN_THRESHOLD = 0.005f
    }

    lateinit var persistentSheetBehavior: BottomSheetBehavior<View>
    lateinit var persistentSheet: LinearLayout
    private var shouldInterceptTouch = false
    private var defaultPeekHeight:Int = 0
    var onDrawerContentCreatedListener: OnDrawerContentCreatedListener? = null

    private val persistentSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {

            when(newState) {
                BottomSheetBehavior.STATE_EXPANDED, BottomSheetBehavior.STATE_SETTLING -> {
                    persistent_bottom_sheet_outlined.setBackgroundColor( if(Build.VERSION.SDK_INT >= 23) context.getColor(R.color.fluentui_dim_background_color)
                                                                         else context.resources.getColor(R.color.fluentui_dim_background_color) )
                }
                BottomSheetBehavior.STATE_DRAGGING -> { }
                else -> {
                    persistent_bottom_sheet_outlined.setBackgroundColor( if(Build.VERSION.SDK_INT >= 23) context.getColor(R.color.fluentui_transparent)
                                                                         else context.resources.getColor(R.color.fluentui_transparent) )
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            if(slideOffset > FADE_OUT_THRESHOLD)
                persistent_bottom_sheet_outlined.setBackgroundColor( if(Build.VERSION.SDK_INT >= 23) context.getColor(R.color.fluentui_dim_background_color)
                                                                     else context.resources.getColor(R.color.fluentui_dim_background_color) )
            else if(slideOffset < FADE_IN_THRESHOLD)
                persistent_bottom_sheet_outlined.setBackgroundColor( if(Build.VERSION.SDK_INT >= 23) context.getColor(R.color.fluentui_transparent)
                                                                     else context.resources.getColor(R.color.fluentui_transparent) )
        }
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context), attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BottomSheetBehavior_Layout)
        defaultPeekHeight = a.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, 0)
        a.recycle()
    }

    override val templateId: Int
        get() = R.layout.view_persistent_sheet

    override fun onTemplateLoaded() {
        persistentSheet = findViewInTemplateById(R.id.persistent_bottom_sheet)!!
        persistentSheetBehavior = BottomSheetBehavior.from(persistentSheet)
        persistentSheetBehavior.setBottomSheetCallback(persistentSheetCallback)
        persistentSheetBehavior.peekHeight = defaultPeekHeight

        super.onTemplateLoaded()
    }

    fun addSheetContent(layoutResID: Int) {
        val content = LayoutInflater.from(context).inflate(layoutResID, persistentSheet, false)
        onDrawerContentCreatedListener?.onDrawerContentCreated(content)
        addSheetContent(content)
    }

    fun addSheetContent(view:View) {
        persistentSheet.removeAllViews()
        persistentSheet.addView(view)
    }

    fun incrementHeight(dx:Int) {
        val transition = ChangeBounds()
        transition.duration = context.resources.getInteger(R.integer.fluentui_persistent_bottomsheet_fade_in_milliseconds).toLong()
        TransitionManager.beginDelayedTransition(persistentSheet, transition)
        val newX = persistentSheetBehavior.peekHeight + dx
        persistentSheetBehavior.peekHeight = newX
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val viewRect = Rect()
        persistentSheet.getGlobalVisibleRect(viewRect)

        if(persistentSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            if(!viewRect.contains(ev!!.rawX.toInt(), ev.rawY.toInt())) {
                persistentSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                shouldInterceptTouch = true
                return true
            }
        }
        shouldInterceptTouch =false
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(shouldInterceptTouch)
            return true
        return super.onTouchEvent(event)
    }

    fun getSheetBehavior(): BottomSheetBehavior<View>? {
        return persistentSheetBehavior
    }

    fun addView(parent:ViewGroup, child:View, index:Int) {
        parent.addView(child, index)
        child.post {
            incrementHeight(child.height)
        }
    }

    fun removeView(parent: ViewGroup, index:Int) {
        val childHeight = parent.getChildAt(index).height
        parent.removeViewAt(index)
        incrementHeight(-childHeight)
    }

    fun removeView(parent: ViewGroup, child:View) {
        val childHeight = child.height
        parent.removeView(child)
        incrementHeight(-childHeight)
    }
}