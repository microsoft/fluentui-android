/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.view

import android.content.Context
import android.hardware.SensorManager
import android.os.SystemClock
import androidx.annotation.RestrictTo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration

/**
 * Extension of RecyclerView which offers:
 * - row / column snapping for LayoutManagers that inherit from LinearLayoutManager
 * - user touch tracking
 * - minimum fling velocity adjustment
 * - maximum fling velocity adjustment
 * - scroll velocity tracking
 *
 * Note: The crazy math part has been taken from Android framework Scroller.java and modified
 * to match our needs.
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
open class MSRecyclerView : RecyclerView {
    companion object {
        // This is going to be multiplied by the density of the device
        private const val MAX_SCROLL_VELOCITY = 3500
        private const val INFLEXION = 0.35f // Tension lines cross at (INFLEXION, 1)
        private const val METER_INCH_RATIO = 39.37f
        private const val FRICTION = 0.84f
        private const val BASELINE_DPI = 160.0f
        private const val VELOCITY_DIFF_MINIMUM = 33L

        private val DECELERATION_RATE = (Math.log(0.78) / Math.log(0.9)).toFloat()

        private fun LinearLayoutManager.getChildStartOffset(childView: View): Int =
            if (orientation == HORIZONTAL)
                childView.left - getLeftDecorationWidth(childView)
            else
                childView.top - getTopDecorationHeight(childView)

        private fun LinearLayoutManager.getChildSize(childView: View): Int =
            if (orientation == HORIZONTAL)
                childView.width + getLeftDecorationWidth(childView) + getRightDecorationWidth(childView)
            else
                childView.height + getTopDecorationHeight(childView) + getBottomDecorationHeight(childView)


        private val ITEM_VIEWS_TOUCH_INPUTS_BLOCKER = object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                return true
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) { }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) { }
        }
    }

    /**
     * Fine grained user touch tracking which is not based on the scrolling state.
     */
    var isUserTouchOccurring: Boolean = false
        private set

    var isSnappingEnabled: Boolean = false

    /**
     * Enable or not scroll velocity tracking. When the scroll reaches maxScrollVelocity
     * OnScrollVelocityListener is called.
     */
    var enableScrollVelocityTracking: Boolean = false
    var onScrollVelocityListener: OnScrollVelocityListener? = null

    private var ppi = 0f

    // A context-specific coefficient adjusted to physical values.
    private var physicalCoefficient = 0f
    private val flingFriction = ViewConfiguration.getScrollFriction()

    private var flingRequested = false

    // appended with an underscore to prevent naming clashes with parent class
    private var _minFlingVelocity = 0
    private var _maxFlingVelocity = 0
    private var maxScrollVelocity = 0

    private var lastVelocityUpdateTime = -1L

    private var currentScrollVelocity = 0
    private var firstVisiblePosition = 0
    private var firstVisibleViewStartOffset = 0
    private var lastVisiblePosition = 0
    private var lastVisibleViewStartOffset = 0

    var itemViewsEnabled: Boolean = false
        set(value) {
            if (itemViewsEnabled == value)
                return

            field = value

            if (value) {
                removeOnItemTouchListener(ITEM_VIEWS_TOUCH_INPUTS_BLOCKER)
            } else {
                addOnItemTouchListener(ITEM_VIEWS_TOUCH_INPUTS_BLOCKER)
            }
        }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : super(context, attrs, defStyle) {
        _minFlingVelocity = super.getMinFlingVelocity()
        _maxFlingVelocity = super.getMaxFlingVelocity()

        ppi = resources.displayMetrics.density * BASELINE_DPI
        physicalCoefficient = computeDeceleration(FRICTION) // look and feel tuning
        maxScrollVelocity = (MAX_SCROLL_VELOCITY * resources.displayMetrics.density).toInt()
    }

    /**
     * Change the minimum fling velocity value
     */
    fun setMinFlingVelocity(velocity: Int) {
        _minFlingVelocity = velocity
    }

    /**
     * Returns the minimum velocity to start a fling.
     */
    override fun getMinFlingVelocity(): Int = _minFlingVelocity

    /**
     * Change the maximum fling velocity value
     */
    fun setMaxFlingVelocity(velocity: Int) {
        _maxFlingVelocity = velocity
    }

    /**
     * Returns the maximum fling velocity used by this RecyclerView.
     */
    override fun getMaxFlingVelocity(): Int = _maxFlingVelocity

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val action = motionEvent.action and MotionEvent.ACTION_MASK

        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> isUserTouchOccurring = true
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> isUserTouchOccurring = false
        }

        return super.onTouchEvent(motionEvent)
    }

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

        if (flingRequested)
            return

        trackScrollVelocity()
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        if (SCROLL_STATE_IDLE == state)
            resetScrollVelocityTracking()

        if (!isSnappingEnabled || !hasFixedSize())
            return

        val linearLayoutManager = layoutManager as? LinearLayoutManager
            ?: return

        if (SCROLL_STATE_IDLE == state && !flingRequested) {
            val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
            if (NO_POSITION == firstPosition)
                return

            val firstView = linearLayoutManager.findViewByPosition(firstPosition)!!

            var dx = 0
            var dy = 0

            if (LinearLayoutManager.HORIZONTAL == linearLayoutManager.orientation) {
                val right = firstView.right
                dx = if (right > firstView.width / 2) firstView.left else right
            } else {
                val bottom = firstView.bottom
                dy = if (bottom > firstView.height / 2) firstView.top else bottom
            }

            smoothScrollBy(dx, dy)
        }

        flingRequested = false
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        var velocityX = velocityX
        var velocityY = velocityY
        if (!isSnappingEnabled || !hasFixedSize())
            return super.fling(velocityX, velocityY)

        val linearLayoutManager = layoutManager as? LinearLayoutManager
            ?: return super.fling(velocityX, velocityY)

        if (isLayoutFrozen)
            return false

        val canScrollHorizontally = linearLayoutManager.canScrollHorizontally()
        val canScrollVertically = linearLayoutManager.canScrollVertically()

        if (!canScrollHorizontally || Math.abs(velocityX) < _minFlingVelocity)
            velocityX = 0

        if (!canScrollVertically || Math.abs(velocityY) < _minFlingVelocity)
            velocityY = 0

        // If we don't have any velocity, return false
        if (velocityX == 0 && velocityY == 0)
            return false

        if (!dispatchNestedPreFling(velocityX.toFloat(), velocityY.toFloat())) {
            val canScroll = canScrollHorizontally || canScrollVertically
            dispatchNestedFling(velocityX.toFloat(), velocityY.toFloat(), canScroll)

            val maxFlingVelocity = _maxFlingVelocity

            if (canScroll) {
                velocityX = Math.max(-maxFlingVelocity, Math.min(velocityX, maxFlingVelocity))
                velocityY = Math.max(-maxFlingVelocity, Math.min(velocityY, maxFlingVelocity))

                flingAndSnap(velocityX, velocityY)
                return true
            }
        }

        return false
    }

    private fun flingAndSnap(velocityX: Int, velocityY: Int) {
        val linearLayoutManager = layoutManager as LinearLayoutManager
        val firstViewPosition = linearLayoutManager.findFirstVisibleItemPosition()
        if (firstViewPosition == NO_POSITION)
            return
        val firstView = linearLayoutManager.findViewByPosition(firstViewPosition)!!
        val velocity = Math.hypot(velocityX.toDouble(), velocityY.toDouble()).toFloat()
        val totalDistance = getSplineFlingDistance(velocity)

        var dx = 0
        var dy = 0

        if (LinearLayoutManager.HORIZONTAL == linearLayoutManager.orientation) {
            val coeffX = if (velocity == 0f) 1.0f else velocityX / velocity
            val adjustedDistanceX = Math.abs(Math.round(totalDistance * coeffX).toInt())
            val viewWidth = firstView.width +
                linearLayoutManager.getLeftDecorationWidth(firstView) +
                linearLayoutManager.getRightDecorationWidth(firstView)
            var distanceX = adjustedDistanceX + (viewWidth - adjustedDistanceX % viewWidth)

            if (velocityX < 0)
                distanceX *= -1

            dx = firstView.left + distanceX
        } else {
            val coeffY = if (velocity == 0f) 1.0f else velocityY / velocity
            val adjustedDistanceY = Math.abs(Math.round(totalDistance * coeffY).toInt())
            val viewHeight = firstView.height +
                linearLayoutManager.getTopDecorationHeight(firstView) +
                linearLayoutManager.getBottomDecorationHeight(firstView)
            var distanceY = adjustedDistanceY + (viewHeight - adjustedDistanceY % viewHeight)

            if (velocityY < 0)
                distanceY *= -1

            dy = firstView.top + distanceY
        }

        flingRequested = true

        smoothScrollBy(dx, dy)
    }

    private fun getSplineFlingDistance(velocity: Float): Double {
        val splineDeceleration = Math.log((INFLEXION * Math.abs(velocity) / (flingFriction * physicalCoefficient)).toDouble())
        val decelMinusOne = DECELERATION_RATE - 1.0
        return flingFriction.toDouble() * physicalCoefficient.toDouble() * Math.exp(DECELERATION_RATE / decelMinusOne * splineDeceleration)
    }

    // g (m/s^2)
    private fun computeDeceleration(friction: Float): Float =
        SensorManager.GRAVITY_EARTH * METER_INCH_RATIO * ppi * friction

    private fun resetScrollVelocityTracking() {
        lastVelocityUpdateTime = -1
        currentScrollVelocity = 0
    }

    private fun trackScrollVelocity() {
        layoutManager as? LinearLayoutManager ?: return

        if (!enableScrollVelocityTracking)
            return

        val linearLayoutManager = layoutManager as LinearLayoutManager

        val firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
        if (NO_POSITION == firstPosition)
            return

        val lastPosition = linearLayoutManager.findLastVisibleItemPosition()
        if (NO_POSITION == lastPosition)
            return

        val now = SystemClock.uptimeMillis()

        if (lastVelocityUpdateTime != -1L) {
            val diff = now - lastVelocityUpdateTime

            if (diff > VELOCITY_DIFF_MINIMUM) {
                val distance: Int = when {
                    firstVisiblePosition in firstPosition..lastPosition ->
                        linearLayoutManager.getChildStartOffset(getChildAt(firstVisiblePosition - firstPosition)) - firstVisibleViewStartOffset
                    lastVisiblePosition in firstPosition..lastPosition ->
                        linearLayoutManager.getChildStartOffset(getChildAt(lastVisiblePosition - firstPosition)) - lastVisibleViewStartOffset
                    else -> {
                        var totalSize = 0
                        for (i in 0 until childCount)
                            totalSize += linearLayoutManager.getChildSize(getChildAt(i))

                        totalSize / childCount * (firstVisiblePosition - firstPosition)
                    }
                }

                currentScrollVelocity = (1000 * distance / diff).toInt()
            }
        }

        firstVisiblePosition = firstPosition
        firstVisibleViewStartOffset = linearLayoutManager.getChildStartOffset(getChildAt(0))
        lastVisiblePosition = lastPosition
        lastVisibleViewStartOffset = linearLayoutManager.getChildStartOffset(getChildAt(childCount - 1))
        lastVelocityUpdateTime = now

        if (Math.abs(currentScrollVelocity) > maxScrollVelocity && onScrollVelocityListener != null)
            onScrollVelocityListener?.onMaxScrollVelocityReached()
    }

    /**
     * Interface definition for a callback to be invoked when the scrolling of a RecyclerView
     * is meeting the max velocity.
     */
    interface OnScrollVelocityListener {
        fun onMaxScrollVelocityReached()
    }
}
