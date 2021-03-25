/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.view.ViewGroup

/**
 * [WrapContentViewPager] sets and handles changes to the layout height property of ViewPager content
 */
class WrapContentViewPager : ViewPager {
    companion object {
        private val heightProperty = object : Property<View, Int>(Int::class.java, "height") {
            override fun set(`object`: View, value: Int?) {
                value?.let {
                    val lp = `object`.layoutParams
                    lp.height = value
                    `object`.layoutParams = lp
                }
            }

            override fun get(`object`: View): Int = `object`.measuredHeight
        }
    }

    var currentObject: Any? = null
    var shouldWrapContent: Boolean = true
        set(value) {
            if (field != value) {
                field = value

                if (shouldWrapContent && layoutParams != null) {
                    val lp = layoutParams
                    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    layoutParams = lp
                }
            }
        }

    private var animator: ObjectAnimator? = null

    private val currentView: View?
        get() {
            val adapter = adapter ?: return null
            val currentObject = currentObject ?: return null
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                if (adapter.isViewFromObject(child, currentObject))
                    return child
            }
            return null
        }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs)

    fun smoothlyResize(targetHeight: Int, listener: Animator.AnimatorListener?) {
        animator?.removeAllListeners()
        animator?.cancel()

        shouldWrapContent = false
        animator = ObjectAnimator.ofInt(this, heightProperty, heightProperty.get(this), targetHeight)
        if (listener != null)
            animator?.addListener(listener)

        animator?.start()
    }

    override fun onScrollChanged(newX: Int, newY: Int, oldX: Int, oldY: Int) {
        if (newX > oldX)
            animator?.cancel()

        super.onScrollChanged(newX, newY, oldX, oldY)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        if (shouldWrapContent) {
            val mode = View.MeasureSpec.getMode(heightMeasureSpec)
            val currentView = currentView
            if (currentView != null && mode != View.MeasureSpec.EXACTLY) {
                if (mode == View.MeasureSpec.UNSPECIFIED) {
                    currentView.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
                } else {
                    val size = View.MeasureSpec.getSize(heightMeasureSpec)
                    currentView.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.AT_MOST))
                }
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(currentView.measuredHeight, View.MeasureSpec.EXACTLY)
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}
