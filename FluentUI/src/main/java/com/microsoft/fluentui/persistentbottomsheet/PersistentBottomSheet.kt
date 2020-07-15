/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.annotation.ColorInt
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.ColorUtils
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
        private const val FADE_OUT_THRESHOLD = 160
    }

    lateinit var persistentSheetBehavior: BottomSheetBehavior<View>
    lateinit var persistentSheet: ViewGroup
    lateinit var persistentSheetContainer: LinearLayout
    var onDrawerContentCreatedListener: OnDrawerContentCreatedListener? = null

    private var colorBackground = ContextCompat.getColor(context, android.R.color.transparent)
    private var shouldInterceptTouch = false
    private var defaultPeekHeight = 0
    private var isDrawerHandleVisible = true

    private val persistentSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if(newState == BottomSheetBehavior.STATE_EXPANDED) {
                persistentSheetBehavior.peekHeight = defaultPeekHeight
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            persistent_bottom_sheet_outlined.setBackgroundColor(ColorUtils.setAlphaComponent(colorBackground, (slideOffset* FADE_OUT_THRESHOLD).toInt()))
        }

    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context), attrs, defStyleAttr) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PersistentBottomSheet)
        defaultPeekHeight = a.getDimensionPixelSize(R.styleable.PersistentBottomSheet_peekHeight, 0)
        isDrawerHandleVisible = a.getBoolean(R.styleable.PersistentBottomSheet_isDrawerHandleVisible, true)
        a.recycle()
    }

    override val templateId: Int
        get() = R.layout.view_persistent_sheet

    override fun onTemplateLoaded() {
        persistentSheet = findViewInTemplateById(R.id.persistent_bottom_sheet)!!
        persistentSheetContainer = findViewInTemplateById(R.id.persistent_sheet_container)!!
        persistentSheetBehavior = BottomSheetBehavior.from(persistentSheet)
        persistentSheetBehavior.setBottomSheetCallback(persistentSheetCallback)
        persistentSheetBehavior.peekHeight = defaultPeekHeight

        if(!isDrawerHandleVisible)
            sheet_drawer_handle.visibility = View.GONE
        super.onTemplateLoaded()
    }

    fun addSheetContent(layoutResID: Int) {
        val content = LayoutInflater.from(context).inflate(layoutResID, persistentSheetContainer, false)
        onDrawerContentCreatedListener?.onDrawerContentCreated(content)
        addSheetContent(content)
    }

    fun addSheetContent(view:View) {
        persistentSheetContainer.removeAllViews()
        persistentSheetContainer.addView(view)
    }

    fun changePeekHeight(dx:Int) {
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

    fun getSheetBehavior(): BottomSheetBehavior<View> {
        return persistentSheetBehavior
    }

    override fun addView(child: View, index: Int) {
        addView(child, index, persistentSheetContainer)
    }

    fun addView(child:View, index:Int=0, parentViewGroup: ViewGroup) {
        parentViewGroup.addView(child, index)
        child.post {
            changePeekHeight(child.height)
        }
    }

    override fun removeViewAt(index: Int) {
        removeViewAt(index, persistentSheetContainer)
    }

    fun removeViewAt(index:Int, parentViewGroup: ViewGroup) {
        val childHeight = parentViewGroup.getChildAt(index).height
        parentViewGroup.removeViewAt(index)
        if(persistentSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
            changePeekHeight(-childHeight)
    }

    override fun removeView(child: View) {
        removeView(child, persistentSheetContainer)
    }

    fun removeView(child:View, parentViewGroup: ViewGroup) {
        val childHeight = child.height
        parentViewGroup.removeView(child)
        if(persistentSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
            changePeekHeight(-childHeight)
    }

    fun collapsePersistentSheet() {
        changePeekHeight(-persistentSheetBehavior.peekHeight)
    }

    fun showPersistentSheet() {
        changePeekHeight(-persistentSheetBehavior.peekHeight+defaultPeekHeight)
    }
}