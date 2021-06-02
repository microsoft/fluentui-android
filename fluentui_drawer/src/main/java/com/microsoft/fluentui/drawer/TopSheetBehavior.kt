/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.drawer

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.ClassLoaderCreator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import androidx.customview.view.AbsSavedState
import androidx.customview.widget.ViewDragHelper
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import java.lang.ref.WeakReference
import kotlin.math.abs
import kotlin.math.min

class TopSheetBehavior<V : View> : CoordinatorLayout.Behavior<V> {
    companion object {
        const val HIDE_THRESHOLD = 0.5F
        const val HIDE_FRICTION = 0.1F

        fun <V : View> from(view: V): TopSheetBehavior<V> {
            val params: ViewGroup.LayoutParams = view.layoutParams
            if(params !is CoordinatorLayout.LayoutParams) {
                throw IllegalArgumentException("This view is not a child of Coordinator Layout")
            }
            else {
                val behavior = params.behavior

                if(!(behavior is TopSheetBehavior<*>)) {
                    throw  IllegalArgumentException("The view is not associated with TopSheetBehavior")
                }
                else {
                    return behavior as TopSheetBehavior<V>
                }
            }
        }
    }

    private var initialY: Int = 0
    private var parentHeight: Int = 0
    private var ignoreEvents: Boolean = false
    private var lastNestedScrollDy: Int = 0
    private var nestedScrolled: Boolean = false
    private var activePointerId: Int = MotionEvent.INVALID_POINTER_ID

    var hideable: Boolean = true
    var skipCollapsed: Boolean = false

    private var state: Int = STATE_COLLAPSED

    private var viewDragHelper: ViewDragHelper? = null
    private var maximumVelocity: Float? = null
    private var viewRef: WeakReference<V>? = null
    private var nestedScrollingChildRef: WeakReference<View>? = null
    private var velocityTracker: VelocityTracker? = null
    private var touchingScrollingChild: Boolean? = null
    private var callback: CustomSheetCallback? = null

    private var peekHeight: Int = -1
    private var peekHeightAuto: Boolean = false
    private var lastPeekHeight: Int = 0
    private var fitToContents: Boolean = false
    private var fitToContentsOffset: Int = 0
    private var collapsedOffset: Int = 0
    private var halfExpandedOffset: Int = 0

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        val a: TypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SheetBehaviorLayout)

        setPeekHeight(a.getDimensionPixelSize(
                R.styleable.SheetBehaviorLayout_behaviorPeekHeight, -1))
        setFitToContents(a.getBoolean(
                R.styleable.SheetBehaviorLayout_behaviorFitToContents, true))
        hideable = a.getBoolean(
                R.styleable.SheetBehaviorLayout_behaviorHideable, false)
        skipCollapsed = a.getBoolean(
                R.styleable.SheetBehaviorLayout_behaviorSkipCollapsed, false)
        a.recycle()

        val configuration: ViewConfiguration = ViewConfiguration.get(context)
        maximumVelocity = (configuration.scaledMaximumFlingVelocity).toFloat()
    }

    override fun onSaveInstanceState(parent: CoordinatorLayout, child: V): Parcelable? {
        return SavedState(super.onSaveInstanceState(parent, child)!!, state);
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, child: V, st: Parcelable) {
        val ss : SavedState = st as SavedState
        super.onRestoreInstanceState(parent, child, ss.superState!!)

        if(ss.state != STATE_DRAGGING && ss.state != STATE_SETTLING) {
            this.state = ss.state
        }
        else {
            this.state = STATE_COLLAPSED
        }
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            child.fitsSystemWindows = true
        }

        val savedTop: Int = child.top
        parent.onLayoutChild(child, layoutDirection)
        parentHeight = parent.height
        viewRef = WeakReference(child)

        lastPeekHeight = if(peekHeightAuto) { parentHeight - parent.width * 9 / 16 } else peekHeight
        fitToContentsOffset = 0
        halfExpandedOffset = -(child.height - parentHeight/2)
        calculateCollapsedOffset()

        when(state) {
            STATE_EXPANDED -> { ViewCompat.offsetTopAndBottom(child, getExpandedOffset()) }
            STATE_HALF_EXPANDED -> { ViewCompat.offsetTopAndBottom(child, halfExpandedOffset) }
            STATE_HIDDEN -> { if(hideable) { ViewCompat.offsetTopAndBottom(child, -child.height) }}
            STATE_COLLAPSED -> { ViewCompat.offsetTopAndBottom(child, collapsedOffset) }
            STATE_SETTLING, STATE_DRAGGING -> { ViewCompat.offsetTopAndBottom(child,savedTop - child.top) }
        }

        if (viewDragHelper == null) {
            viewDragHelper = ViewDragHelper.create(parent, dragCallback);
        }
        nestedScrollingChildRef = if(findScrollingChild(child) != null) WeakReference(this.findScrollingChild(child)!!) else null
        return true
    }

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        if (!child.isShown) {
            this.ignoreEvents = true
            return false
        }
        else {
            val action: Int = event.actionMasked
            if (action == 0) {
                this.reset()
            }

            if (this.velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain()
            }
            this.velocityTracker!!.addMovement(event)

            when (action) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL-> {
                    this.touchingScrollingChild = false
                    activePointerId = MotionEvent.INVALID_POINTER_ID

                    if (ignoreEvents) {
                        ignoreEvents = false
                        return false
                    }
                }

                MotionEvent.ACTION_DOWN -> {
                    val initialX: Int = event.x.toInt()
                    initialY = event.y.toInt()

                    val scroll: View? = nestedScrollingChildRef?.get()

                    if (scroll != null && parent.isPointInChildBounds(scroll, initialX, initialY)) {
                        activePointerId = event.getPointerId(event.actionIndex)
                        touchingScrollingChild = true
                    }
                    ignoreEvents = activePointerId == MotionEvent.INVALID_POINTER_ID &&
                            !parent.isPointInChildBounds(child, initialX, initialY)
                }

            }

            if (!ignoreEvents && viewDragHelper!= null && viewDragHelper?.shouldInterceptTouchEvent(event)!!) {
                return true
            }

            val scroll: View? = nestedScrollingChildRef?.get()

            return action == MotionEvent.ACTION_MOVE && scroll != null &&
                    !ignoreEvents && state != STATE_DRAGGING &&
                    !parent.isPointInChildBounds(scroll, event.x.toInt(), event.y.toInt()) &&
                    viewDragHelper != null && abs(initialY.minus(event.y)) > viewDragHelper!!.touchSlop
        }
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        if (!child.isShown) {
            return false
        }
        else {
            val action: Int = event.actionMasked
            if (state == STATE_DRAGGING && action == MotionEvent.ACTION_DOWN) {
                return true
            }

            viewDragHelper?.processTouchEvent(event)

            if (action == MotionEvent.ACTION_DOWN) {
                reset()
            }

            if (velocityTracker == null) {
                velocityTracker = VelocityTracker.obtain()
            }
            velocityTracker!!.addMovement(event)

            if (action == MotionEvent.ACTION_MOVE && !ignoreEvents) {
                if (abs(initialY.minus(event.y.toFloat())) > viewDragHelper!!.touchSlop.toFloat()) {
                    viewDragHelper!!.captureChildView(child, event.getPointerId(event.actionIndex))
                }
            }
            return !ignoreEvents
        }
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        lastNestedScrollDy = 0
        nestedScrolled = false

        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View,
                                   dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (type != 1) {
            val scrollingChild: View? = this.nestedScrollingChildRef?.get()

            if (target == scrollingChild) {
                val currentTop: Int = child.top
                val newTop: Int = currentTop - dy;

                if (dy < 0) {
                    if (newTop < getExpandedOffset()) {
                        consumed[1] = dy
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        setStateInternal(STATE_DRAGGING)
                    } else {
                        consumed[1] = currentTop - getExpandedOffset()
                        ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                        setStateInternal(STATE_EXPANDED)
                    }
                }
                else if (dy > 0 && !target.canScrollVertically(1)) {
                        if (newTop >= collapsedOffset || hideable) {
                            consumed[1] = dy
                            ViewCompat.offsetTopAndBottom(child, -dy)
                            setStateInternal(STATE_DRAGGING)
                        } else {
                            consumed[1] = currentTop - collapsedOffset
                            ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                            setStateInternal(STATE_COLLAPSED)
                        }
                }

                dispatchOnSlide(child.top)
                lastNestedScrollDy = dy
                nestedScrolled = true
            }
        }
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View) {
        if (child.top == getExpandedOffset()) {
            setStateInternal(STATE_EXPANDED)
        }
        else if (target == nestedScrollingChildRef?.get() && nestedScrolled) {
            val top: Int
            val targetState: Int

            if (lastNestedScrollDy < 0) {
                top = getExpandedOffset()
                targetState = STATE_EXPANDED
            } else if (hideable && shouldHide(child, getYVelocity())) {
                top = -child.height
                targetState = STATE_HIDDEN
            } else if (lastNestedScrollDy == 0) {
                val currentTop: Int = child.top
                val currentBottom: Int = child.bottom

                if (this.fitToContents) {
                    if (abs(currentTop.minus(collapsedOffset)) > abs(currentTop.minus(fitToContentsOffset))) {
                        top = fitToContentsOffset
                        targetState = STATE_EXPANDED
                    } else {
                        top = collapsedOffset
                        targetState = STATE_COLLAPSED
                    }
                }
                else if(currentBottom > parentHeight/2){
                    if(abs(currentBottom.minus(parentHeight)) > abs(currentBottom.minus(parentHeight/2.0)) ) {
                        top = halfExpandedOffset
                        targetState = STATE_HALF_EXPANDED
                    }
                    else {
                        top = getExpandedOffset()
                        targetState = STATE_EXPANDED
                    }
                }
                else if(abs(currentBottom.minus(parentHeight/2)) < abs(currentBottom.minus(peekHeight))) {
                    top = halfExpandedOffset
                    targetState = STATE_HALF_EXPANDED
                }
                else {
                    top = collapsedOffset
                    targetState = STATE_COLLAPSED
                }
            } else {
                top = collapsedOffset
                targetState = STATE_COLLAPSED
            }

            if (viewDragHelper!!.smoothSlideViewTo(child, child.left, top)) {
                setStateInternal(STATE_SETTLING)
                ViewCompat.postOnAnimation(child, SettleRunnable(child, targetState))
            } else {
                setStateInternal(targetState)
            }
            nestedScrolled = false
        }
    }

    override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: V,
                                  target: View, velocityX: Float, velocityY: Float): Boolean {
        return target == nestedScrollingChildRef!!.get() && (state != STATE_EXPANDED ||
                super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY))
    }

    fun getPeekHeight(): Int {
        return peekHeight
    }

    fun isHideable(): Boolean {
        return hideable
    }

    fun setFitToContents(fitToContents: Boolean) {
        if(this.fitToContents != fitToContents) {
            this.fitToContents = fitToContents

            if(viewRef != null) {
                calculateCollapsedOffset()
            }
            setStateInternal(if(fitToContents && state == 6) 3 else state)
        }
    }

    fun setPeekHeight(peekHeight: Int) {
        var layout = false
        if (peekHeight == -1) {
            if (!peekHeightAuto) {
                peekHeightAuto = true
                layout = true
            }
        } else if (peekHeightAuto || this.peekHeight != peekHeight) {
            peekHeightAuto = false
            this.peekHeight = Math.max(0, peekHeight)
            if(viewRef != null)
                collapsedOffset = -(viewRef!!.get()!!.height-peekHeight)
            layout = true
        }

        if (layout && state == 4 && viewRef != null) {
            viewRef!!.get()?.requestLayout()
        }
    }

    fun setTopSheetCallback(callback: CustomSheetCallback) {
        this.callback = callback
    }

    fun setStateInternal(st: Int) {
        if (state == st)
            return
        state = st
        val topSheet: View? = viewRef?.get()  as View

        if (topSheet != null && callback != null) {
            callback!!.onStateChanged(topSheet, state)
        }
    }

    private fun reset() {
        activePointerId = ViewDragHelper.INVALID_POINTER
        if (velocityTracker != null) {
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }

    fun getState(): Int {
        return this.state
    }

    private fun calculateCollapsedOffset() {
        if(viewRef == null)
            return
        collapsedOffset = if(fitToContents)  min(-(viewRef!!.get()!!.height-lastPeekHeight), fitToContentsOffset)
                            else  -(viewRef!!.get()!!.height-lastPeekHeight)

    }

    fun shouldHide(child: View, yvel: Float): Boolean {
        if(skipCollapsed) {
            return true
        }
        if (child.top > collapsedOffset) {
            return false
        }
        val newTop: Float = child.top.toFloat() + yvel * HIDE_FRICTION
        return abs(newTop.minus(collapsedOffset)).div(peekHeight) > HIDE_THRESHOLD
    }

    private fun findScrollingChild(view: View?): View? {
        if(view == null)
            return null

        if (ViewCompat.isNestedScrollingEnabled(view)) {
            return view
        } else {
            if (view is ViewGroup) {
                val group: ViewGroup = view

                for (i in 0..group.childCount) {
                    val scrollingChild: View? = findScrollingChild(group.getChildAt((i)))

                    if (scrollingChild != null) {
                        return scrollingChild
                    }
                }
            }
        }
        return null
    }

    fun getExpandedOffset(): Int {
        return if(fitToContents) fitToContentsOffset else {if(viewRef!= null && viewRef!!.get()!!.height > parentHeight) 0 else  (parentHeight- viewRef!!.get()!!.height)}
    }

    private val dragCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            if (state == STATE_DRAGGING) {
                return false
            }
            if (touchingScrollingChild!!) {
                return false
            }
            if (state == STATE_EXPANDED && activePointerId == pointerId) {
                val scroll: View? = nestedScrollingChildRef?.get()

                if (scroll != null && scroll.canScrollVertically(1)) {
                    return false
                }
            }
            return viewRef != null && viewRef?.get() == child
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            dispatchOnSlide(top)
        }

        override fun onViewDragStateChanged(st: Int) {
            if (st == ViewDragHelper.STATE_DRAGGING) {
                setStateInternal(STATE_DRAGGING)
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val top: Int
            val targetState: Int
            val currentTop: Int = releasedChild.top
            val currentBottom: Int = releasedChild.bottom

            if (yvel > 0.0F) {
                if(fitToContents) {
                    top = fitToContentsOffset
                    targetState = STATE_EXPANDED
                }
                else {
                    if(abs(currentBottom.minus(parentHeight)) > abs(currentBottom.minus(parentHeight/2.0)) ) {
                        top = halfExpandedOffset
                        targetState = STATE_HALF_EXPANDED
                    }
                    else {
                        top = getExpandedOffset()
                        targetState = STATE_EXPANDED
                    }
                }
            }
            else if (!hideable || !shouldHide(releasedChild, yvel) || releasedChild.top >= collapsedOffset && abs(xvel) >= abs(yvel)) {
                if(yvel != 0.0F && abs(xvel) <= abs(yvel)) {
                    top = collapsedOffset
                    targetState = STATE_COLLAPSED
                }
                else {
                    if(fitToContents) {
                        if (abs(currentTop.minus(collapsedOffset)) > abs(currentTop.minus(getExpandedOffset()))) {
                            top = fitToContentsOffset
                            targetState = STATE_EXPANDED
                        } else {
                            top  = collapsedOffset
                            targetState = STATE_COLLAPSED
                        }
                    }
                    else if(currentBottom > parentHeight/2.0){
                        if(abs(currentBottom.minus(parentHeight)) > abs(currentBottom.minus(parentHeight/2.0)) ) {
                            top = halfExpandedOffset
                            targetState = STATE_HALF_EXPANDED
                        }
                        else {
                            top = getExpandedOffset()
                            targetState = STATE_EXPANDED
                        }
                    }
                    else if(abs(currentBottom.minus(parentHeight/2.0)) < abs(currentBottom.minus(lastPeekHeight))) {
                        top = halfExpandedOffset
                        targetState = STATE_HALF_EXPANDED
                    }
                    else {
                        top = collapsedOffset
                        targetState = STATE_COLLAPSED
                    }
                }
            }
            else {
                top = -viewRef!!.get()!!.height
                targetState = STATE_HIDDEN
            }

            if (viewDragHelper!!.settleCapturedViewAt(releasedChild.left, top)) {
                setStateInternal(STATE_SETTLING)
                ViewCompat.postOnAnimation(releasedChild, SettleRunnable(releasedChild, targetState))
            } else {
                setStateInternal(targetState)
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return MathUtils.clamp(top, if (hideable) -child.height else collapsedOffset, getExpandedOffset())
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return child.left
        }

        override fun getViewVerticalDragRange(child: View): Int {
            if (hideable) {
                return child.height
            }
            else {
                return  getExpandedOffset().minus(collapsedOffset)
            }
        }
    }

    private fun dispatchOnSlide(top: Int) {
        val topSheet: View? = viewRef?.get() as View

        if (topSheet != null && callback != null) {
            if (top < collapsedOffset) {
                callback!!.onSlide(topSheet, (top-collapsedOffset).div(lastPeekHeight.toFloat()))
            } else {
                callback!!.onSlide(topSheet, (top-collapsedOffset).div((getExpandedOffset()-collapsedOffset).toFloat()))
            }
        }

    }

    private inner class SettleRunnable(val view: View, val TargetState: Int) : Runnable {

        override fun run() {
            if (viewDragHelper != null && viewDragHelper!!.continueSettling(true)) {
                ViewCompat.postOnAnimation(view, this)
            } else {
                setStateInternal(TargetState)
            }
        }
    }

    private fun getYVelocity(): Float {
        return if (velocityTracker == null) { 0.0F }
            else {
                velocityTracker!!.computeCurrentVelocity(1000, maximumVelocity!!)
                velocityTracker!!.getYVelocity(activePointerId)
            }
    }

    internal class SavedState : AbsSavedState {
        val state: Int

        constructor(parcel: Parcel, loader: ClassLoader?) : super(parcel, loader) {
            state = parcel.readInt()
        }
        constructor(superState: Parcelable, state: Int) : super(superState) {
            this.state = state
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(state)
        }

        companion object {
            @JvmField
            val CREATOR: Parcelable.Creator<SavedState> = object : ClassLoaderCreator<SavedState> {
                override fun createFromParcel(parcel : Parcel, loader: ClassLoader): SavedState {
                    return SavedState(parcel, loader)
                }
                override fun createFromParcel(parcel: Parcel): SavedState {
                    return SavedState(parcel, null as ClassLoader?)
                }
                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    private fun startSettlingAnimation(child: View, st: Int) {
        var top: Int

        when(st) {
            STATE_COLLAPSED -> { top = collapsedOffset }
            STATE_HALF_EXPANDED-> {
                top = halfExpandedOffset
                if(fitToContents && top >= fitToContentsOffset) {
                    state = 3
                    top = fitToContentsOffset
                }
            }
            STATE_EXPANDED -> { top = getExpandedOffset() }
            else -> {
                if (!hideable || state != 5) {
                    throw IllegalArgumentException("Illegal state arguemnt: $st")
                }
                top = -child.height
            }
        }

        if (viewDragHelper!!.smoothSlideViewTo(child, child.left, top)) {
            setStateInternal(STATE_SETTLING)
            ViewCompat.postOnAnimation(child, SettleRunnable(child, st))
        } else {
            setStateInternal(st)
        }
    }

    fun setStateOuter(value: Int) {
        if (viewRef == null) {
            if (value == STATE_EXPANDED || value == STATE_COLLAPSED || (value == STATE_HIDDEN && hideable)) {
                state = value
            }
        }
        else {
            val child:V? = viewRef?.get() ?: return
            startSettlingAnimation(child!!, value)
        }
    }
}
