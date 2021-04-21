/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.drawer

import android.content.Context
import android.content.res.TypedArray
import android.os.Parcel
import android.os.Parcelable
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
import kotlin.math.max
import kotlin.math.min

class SideSheetBehavior<V: View> : CoordinatorLayout.Behavior<V> {
    companion object {
        const val HIDE_THRESHOLD = 0.5F
        const val HIDE_FRICTION = 0.1F

        enum class BehaviorType {
            RIGHT, LEFT
        }

        fun<V : View>from(view: V): SideSheetBehavior<V> {
            val params: ViewGroup.LayoutParams = view.layoutParams
            if(params !is CoordinatorLayout.LayoutParams) {
                throw IllegalArgumentException("This view is not a child of Coordinator Layout")
            }
            else {
                val behavior = params.behavior

                if(behavior !is SideSheetBehavior<*>) {
                    throw IllegalArgumentException("the view is not associated with SideSheetBehavior")
                }
                else {
                    return behavior as SideSheetBehavior<V>
                }
            }
        }
    }

    private var initialX: Int = 0
    private var parentWidth: Int = 0
    private var ignoreEvents: Boolean= false
    private var lastNestedScrollDx: Int =  0
    private var nestedScrolled: Boolean = false
    private var activePointerID: Int = MotionEvent.INVALID_POINTER_ID

    var hideable: Boolean = true
    var skipCollapsed: Boolean = false
    var peekWidth: Int = 0
    private var state: Int = STATE_COLLAPSED

    private var viewDragHelper: ViewDragHelper? = null
    private var maximumVelocity: Float? = null
    private var viewRef: WeakReference<V>? = null
    private var nestedScrollingChildRef: WeakReference<View>? = null
    private var velocityTracker: VelocityTracker? = null
    private var touchingScrollingChild: Boolean? = null
    private var callback: CustomSheetCallback? = null


    private var fitToContents: Boolean = true
    private var fitToContentsOffset: Int = 0
    private var collapsedOffset: Int = 0
    private var halfExpandedOffset: Int = 0
    var behaviorType: BehaviorType = BehaviorType.LEFT

    constructor(context: Context, attrs: AttributeSet? = null): super(context, attrs) {
        val a:TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SheetBehaviorLayout)
        setFitToContents(a.getBoolean(
                R.styleable.SheetBehaviorLayout_behaviorFitToContents, true))
        hideable = a.getBoolean(
                R.styleable.SheetBehaviorLayout_behaviorHideable, false)
        skipCollapsed = a.getBoolean(
                R.styleable.SheetBehaviorLayout_behaviorSkipCollapsed, false)
        peekWidth = a.getDimensionPixelSize(R.styleable.SheetBehaviorLayout_behaviorPeekWidth, 0)
        behaviorType = BehaviorType.valueOf(a.getString(R.styleable.SheetBehaviorLayout_behaviorType) ?: "RIGHT")
        a.recycle()

        val configuration: ViewConfiguration = ViewConfiguration.get(context)
        maximumVelocity = (configuration.scaledMaximumFlingVelocity).toFloat()
    }

    override fun onSaveInstanceState(parent: CoordinatorLayout, child: V): Parcelable? {
        return SavedState(super.onSaveInstanceState(parent, child)!!, state)
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, child: V, state: Parcelable) {
        val ss : SavedState = state as SavedState
        super.onRestoreInstanceState(parent, child, ss.superState!!)

        this.state = if(ss.state != STATE_DRAGGING && ss.state != STATE_SETTLING) ss.state else STATE_COLLAPSED
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: V, layoutDirection: Int): Boolean {
        if (ViewCompat.getFitsSystemWindows(parent) && !ViewCompat.getFitsSystemWindows(child)) {
            child.fitsSystemWindows = true
        }

        val savedLeft: Int = child.left
        parent.onLayoutChild(child, layoutDirection)
        parentWidth = parent.width
        viewRef = WeakReference(child)

        when(behaviorType) {
            BehaviorType.RIGHT -> {
                fitToContentsOffset = Math.max(0, this.parentWidth- child.width)
                halfExpandedOffset = parentWidth/2
            }
            BehaviorType.LEFT -> {
                fitToContentsOffset = 0
                halfExpandedOffset = -(child.width- parentWidth/2)
            }
        }

        calculateCollapsedOffset()

        when(state) {
            STATE_EXPANDED -> { ViewCompat.offsetLeftAndRight(child, getExpandedOffset()) }
            STATE_HALF_EXPANDED -> { ViewCompat.offsetLeftAndRight(child, halfExpandedOffset) }
            STATE_HIDDEN -> { if(hideable)
                                    { ViewCompat.offsetLeftAndRight(child, if(behaviorType == BehaviorType.RIGHT ) parentWidth else -child.width) }}
            STATE_COLLAPSED -> { ViewCompat.offsetLeftAndRight(child, collapsedOffset) }
            STATE_SETTLING, STATE_DRAGGING -> { ViewCompat.offsetLeftAndRight(child, savedLeft-child.left) }
        }

        if(viewDragHelper == null) {
            viewDragHelper = ViewDragHelper.create(parent, dragCallback);
        }
        nestedScrollingChildRef = if(findScrollingChild(child) != null) WeakReference(this.findScrollingChild(child)!!) else null
        return true
    }

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        if(!child.isShown) {
            ignoreEvents = true
            return false
        }

        val action: Int = event.actionMasked
        if(action == 0) {
            reset()
        }

        if(velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker!!.addMovement(event)

        when(action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                touchingScrollingChild = false
                activePointerID = MotionEvent.INVALID_POINTER_ID

                if(ignoreEvents) {
                    ignoreEvents = false
                    return false
                }
            }
            MotionEvent.ACTION_DOWN -> {
                val initialY: Int = event.y.toInt()
                initialX = event.x.toInt()

                val scroll: View? = nestedScrollingChildRef?.get()
                if (scroll != null && parent.isPointInChildBounds(scroll, initialX, initialY)) {
                    activePointerID = event.getPointerId(event.actionIndex)
                    touchingScrollingChild = true
                }
                ignoreEvents = activePointerID == MotionEvent.INVALID_POINTER_ID &&
                        !parent.isPointInChildBounds(child, initialX, initialY)
            }
        }

        if(!ignoreEvents && viewDragHelper!= null && viewDragHelper!!.shouldInterceptTouchEvent(event)) {
            return true
        }

        val scroll:View? = nestedScrollingChildRef?.get()

        return action == MotionEvent.ACTION_MOVE && scroll != null &&
                !ignoreEvents && state != STATE_DRAGGING &&
                !parent.isPointInChildBounds(scroll, event.x.toInt(), event.y.toInt()) &&
                viewDragHelper != null && abs(initialX.minus(event.x)) > viewDragHelper!!.touchSlop
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        if(!child.isShown) {
            return false
        }

        val action: Int = event.actionMasked
        if(state == STATE_DRAGGING && action == MotionEvent.ACTION_DOWN) {
            return true
        }

        viewDragHelper?.processTouchEvent(event)

        if(action == MotionEvent.ACTION_MOVE && !ignoreEvents) {
            if (Math.abs(initialX.minus(event.x).toFloat()) > viewDragHelper!!.touchSlop.toFloat()) {
                viewDragHelper!!.captureChildView(child, event.getPointerId(event.actionIndex))
            }
        }
        return  !ignoreEvents
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        lastNestedScrollDx = 0
        nestedScrolled = false

        return (axes and ViewCompat.SCROLL_AXIS_HORIZONTAL) != 0
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View,
                                   dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if(type != 1) {
            val scrollingChild: View? = this.nestedScrollingChildRef?.get()

            if(target == scrollingChild) {
                val currentLeft: Int = child.left
                val newLeft: Int = currentLeft - dx

                when(behaviorType) {
                    BehaviorType.RIGHT -> {
                        if(dx > 0) {
                            if(newLeft < getExpandedOffset()) {
                                consumed[1] = currentLeft - getExpandedOffset()
                                ViewCompat.offsetLeftAndRight(child, -consumed[1]);
                                setStateInternal(STATE_EXPANDED)
                            }
                            else {
                                consumed[1] = dx
                                ViewCompat.offsetLeftAndRight(child, -consumed[1])
                                setStateInternal(STATE_DRAGGING)
                            }
                        }
                        else if(dx <0 && !target.canScrollHorizontally(-1)) {
                            if(newLeft > collapsedOffset && !hideable) {
                                consumed[1] = currentLeft - collapsedOffset
                                ViewCompat.offsetLeftAndRight(child, -consumed[1])
                                setStateInternal(STATE_COLLAPSED)
                            }
                            else {
                                consumed[1] = dx
                                ViewCompat.offsetLeftAndRight(child, -consumed[1])
                                setStateInternal(STATE_DRAGGING)
                            }
                        }
                    }

                    BehaviorType.LEFT -> {
                        if(dx < 0) {
                            if(newLeft < getExpandedOffset()) {
                                consumed[1] = dx
                                ViewCompat.offsetLeftAndRight(child, -dx)
                                setStateInternal(STATE_DRAGGING)
                            }
                            else {
                                consumed[1] = currentLeft - getExpandedOffset()
                                ViewCompat.offsetLeftAndRight(child, -consumed[1])
                                setStateInternal(STATE_EXPANDED)
                            }
                        }
                        else if(dx > 0 && !target.canScrollHorizontally(1)) {
                            if(newLeft >= collapsedOffset || hideable) {
                                consumed[1] = dy
                                ViewCompat.offsetLeftAndRight(child, -dy)
                                setStateInternal(STATE_DRAGGING)
                            }
                            else {
                                consumed[1] = currentLeft - collapsedOffset
                                ViewCompat.offsetLeftAndRight(child, -consumed[1])
                                setStateInternal(STATE_COLLAPSED)
                            }
                        }
                    }
                }

                dispatchOnSlide(child.left)
                lastNestedScrollDx = dx
                nestedScrolled = true
            }
        }
    }

    override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: V, target: View, type: Int) {
        if(child.left == getExpandedOffset()) {
            setStateInternal(STATE_EXPANDED)
        }
        else if(target == nestedScrollingChildRef?.get() && nestedScrolled) {
            val left:Int
            val targetState:Int

            when(behaviorType) {
                BehaviorType.RIGHT -> {
                    if(lastNestedScrollDx > 0) {
                        left = getExpandedOffset()
                        targetState = STATE_EXPANDED
                    }
                    else if(hideable && shouldHide(child, getXVelocity())) {
                        left = parentWidth
                        targetState = STATE_HIDDEN
                    }
                    else if(lastNestedScrollDx == 0) {
                        val currentLeft: Int = child.left
                        if(fitToContents) {
                            if(abs(currentLeft.minus(fitToContentsOffset)) < abs(currentLeft.minus(collapsedOffset))) {
                                left = fitToContentsOffset
                                targetState = STATE_EXPANDED
                            }
                            else {
                                left = collapsedOffset
                                targetState = STATE_COLLAPSED
                            }
                        }
                        else if(currentLeft < halfExpandedOffset) {
                            if(currentLeft < abs(currentLeft.minus(collapsedOffset))) {
                                left = getExpandedOffset()
                                targetState = STATE_EXPANDED
                            }
                            else {
                                left = halfExpandedOffset
                                targetState = STATE_HALF_EXPANDED
                            }
                        }
                        else if(abs(currentLeft.minus(halfExpandedOffset)) < abs(currentLeft.minus(collapsedOffset))) {
                            left = halfExpandedOffset
                            targetState = STATE_HALF_EXPANDED
                        }
                        else {
                            left = collapsedOffset
                            targetState = STATE_DRAGGING
                        }
                    }
                    else {
                        left = collapsedOffset
                        targetState = STATE_COLLAPSED
                    }
                }
                // BehaviorType.LEFT
                else -> {
                    if(lastNestedScrollDx < 0) {
                        left = getExpandedOffset()
                        targetState = STATE_EXPANDED
                    }
                    else if(hideable && shouldHide(child,getXVelocity())) {
                        left = -child.width
                        targetState = STATE_HIDDEN
                    }
                    else if(lastNestedScrollDx == 0) {
                        val currentLeft: Int = child.left
                        val currentRight: Int = child.right

                        if(fitToContents) {
                            if (abs(currentLeft.minus(collapsedOffset)) > abs(currentLeft.minus(fitToContentsOffset))) {
                                left = fitToContentsOffset
                                targetState = STATE_EXPANDED
                            } else {
                                left = collapsedOffset
                                targetState = STATE_COLLAPSED
                            }
                        }
                        else if(currentRight > parentWidth/2) {
                            if(abs(currentRight.minus(parentWidth)) > abs(currentRight.minus(parentWidth/2.0)) ) {
                                left = halfExpandedOffset
                                targetState = STATE_HALF_EXPANDED
                            }
                            else {
                                left = getExpandedOffset()
                                targetState = STATE_EXPANDED
                            }
                        }
                        else if(abs(currentRight.minus(parentWidth/2)) < abs(currentRight.minus(peekWidth))) {
                            left = halfExpandedOffset
                            targetState = STATE_HALF_EXPANDED
                        }
                        else {
                            left = collapsedOffset
                            targetState = STATE_COLLAPSED
                        }
                    }
                    else {
                        left = collapsedOffset
                        targetState = STATE_COLLAPSED
                    }
                }
            }

            if(viewDragHelper!!.smoothSlideViewTo(child, left, child.top)) {
                setStateInternal(STATE_SETTLING)
                ViewCompat.postOnAnimation(child, SettleRunnable(child, targetState))
            }
            else {
                setStateInternal(targetState)
            }
            nestedScrolled = false
        }
    }

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: V, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return target == nestedScrollingChildRef?.get() && (state != STATE_EXPANDED ||
                super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY))
    }

    fun setFitToContents(fitToContents:Boolean) {
        if(this.fitToContents != fitToContents) {
            this.fitToContents = fitToContents

            if(viewRef != null) {
                calculateCollapsedOffset()
            }
            setStateInternal(if(fitToContents && state == 6) 3 else state)
        }
    }

    fun getState(): Int {
        return this.state
    }

    private fun calculateCollapsedOffset() {
        collapsedOffset = when(behaviorType) {
            BehaviorType.RIGHT -> {
                if(fitToContents) max(parentWidth-peekWidth, fitToContentsOffset) else (parentWidth-peekWidth)
            }
            BehaviorType.LEFT -> {
                if(viewRef == null)
                    return
                if(fitToContents) {
                    min(-(viewRef!!.get()!!.width-peekWidth), fitToContentsOffset)
                }
                else {
                    -(viewRef!!.get()!!.width-peekWidth)
                }
            }
        }
    }

    fun getExpandedOffset(): Int {
        return when(behaviorType) {
            BehaviorType.RIGHT -> { if(fitToContents) fitToContentsOffset else 0 }
            else -> { if(fitToContents) fitToContentsOffset else {if(viewRef!= null && viewRef!!.get()!!.width > parentWidth) 0 else  (parentWidth- viewRef!!.get()!!.width)} }
        }
    }

    fun isHideable(): Boolean {
        return hideable
    }

    fun setSideSheetCallBack(callback: CustomSheetCallback)  {
        this.callback = callback
    }

    fun setStateInternal(st:Int) {
        if(state == st)
            return
        state = st
        val sideSheet: View? = viewRef?.get() as View

        if(sideSheet != null && callback != null) {
            callback!!.onStateChanged(sideSheet, state)
        }
    }

    private fun reset() {
        activePointerID = ViewDragHelper.INVALID_POINTER
        if (velocityTracker != null) {
            velocityTracker!!.recycle()
            velocityTracker = null
        }
    }

    fun shouldHide(child: View, xvel: Float): Boolean {
        if(skipCollapsed) {
            return true
        }

        when(behaviorType) {
            BehaviorType.RIGHT -> {
                if (child.left < collapsedOffset) {
                    return false
                }
            }
            BehaviorType.LEFT -> {
                if (child.left > collapsedOffset) {
                    return false
                }
            }
        }
        val newLeft: Float = child.left.toFloat() + xvel * HIDE_FRICTION
        return abs(newLeft.minus(collapsedOffset)).div(peekWidth) > HIDE_THRESHOLD
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

    private val dragCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerID: Int): Boolean {
            if (state == STATE_DRAGGING) {
                return false
            }
            if (touchingScrollingChild!!) {
                return false
            }
            if (state == STATE_EXPANDED && activePointerID == pointerID) {
                val scroll: View? = nestedScrollingChildRef?.get()

                if(scroll != null) {
                    when(behaviorType) {
                        BehaviorType.RIGHT -> { if(scroll.canScrollHorizontally(-1)) return false }
                        BehaviorType.LEFT -> { if(scroll.canScrollHorizontally(1)) return  false}
                    }
                }
            }
            return viewRef != null && viewRef?.get() == child
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            dispatchOnSlide(left)
        }

        override fun onViewDragStateChanged(st: Int) {
            if (st == ViewDragHelper.STATE_DRAGGING) {
                setStateInternal(STATE_DRAGGING)
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val left: Int
            val targetState: Int
            val currentLeft: Int = releasedChild.left
            val currentRight: Int = releasedChild.right

            when(behaviorType) {
                BehaviorType.RIGHT -> {
                    if (xvel < 0.0F) {
                        if(fitToContents) {
                            left = fitToContentsOffset
                            targetState = STATE_EXPANDED
                        }
                        else {
                            if(currentLeft > halfExpandedOffset) {
                                left = halfExpandedOffset
                                targetState = STATE_HALF_EXPANDED
                            }
                            else {
                                left = 0
                                targetState = STATE_EXPANDED
                            }
                        }
                    }
                    else if (!hideable || !shouldHide(releasedChild, xvel) || releasedChild.left <= collapsedOffset && abs(yvel) >= abs(xvel)) {
                        if(xvel != 0.0F && abs(yvel) <= abs(xvel)) {
                            left = collapsedOffset
                            targetState = STATE_COLLAPSED
                        }
                        else {
                            if(fitToContents) {
                                if (abs(currentLeft.minus(fitToContentsOffset)) < abs(currentLeft.minus(collapsedOffset))) {
                                    left = fitToContentsOffset
                                    targetState = STATE_EXPANDED
                                } else {
                                    left  = collapsedOffset
                                    targetState = STATE_COLLAPSED
                                }
                            }
                            else if(currentLeft < halfExpandedOffset){
                                if(currentLeft < abs(currentLeft.minus(collapsedOffset)) ) {
                                    left = 0
                                    targetState = STATE_EXPANDED
                                }
                                else {
                                    left = halfExpandedOffset
                                    targetState = STATE_HALF_EXPANDED
                                }
                            }
                            else if(abs(currentLeft.minus(halfExpandedOffset)) < abs(currentLeft.minus(collapsedOffset))) {
                                left = halfExpandedOffset
                                targetState = STATE_HALF_EXPANDED
                            }
                            else {
                                left = collapsedOffset
                                targetState = STATE_COLLAPSED
                            }
                        }
                    }
                    else {
                        left = parentWidth
                        targetState = STATE_HIDDEN
                    }
                }
                //BehaviorType.LEFT
                else -> {
                    if (xvel > 0.0F) {
                        if(fitToContents) {
                            left = fitToContentsOffset
                            targetState = STATE_EXPANDED
                        }
                        else {
                            if(abs(currentRight.minus(parentWidth)) > abs(currentRight.minus(parentWidth/2.0)) ) {
                                left = halfExpandedOffset
                                targetState = STATE_HALF_EXPANDED
                            }
                            else {
                                left = getExpandedOffset()
                                targetState = STATE_EXPANDED
                            }
                        }
                    }
                    else if (!hideable || !shouldHide(releasedChild, xvel) || releasedChild.left >= collapsedOffset && abs(yvel) >= abs(xvel)) {
                        if(xvel != 0.0F && abs(yvel) <= abs(xvel)) {
                            left = collapsedOffset
                            targetState = STATE_COLLAPSED
                        }
                        else {
                            if(fitToContents) {
                                if (abs(currentLeft.minus(collapsedOffset)) > abs(currentLeft.minus(getExpandedOffset()))) {
                                    left = fitToContentsOffset
                                    targetState = STATE_EXPANDED
                                } else {
                                    left  = collapsedOffset
                                    targetState = STATE_COLLAPSED
                                }
                            }
                            else if(currentLeft > parentWidth/2.0){
                                if(abs(currentLeft.minus(parentWidth)) > abs(currentLeft.minus(parentWidth/2.0)) ) {
                                    left = halfExpandedOffset
                                    targetState = STATE_HALF_EXPANDED
                                }
                                else {
                                    left = getExpandedOffset()
                                    targetState = STATE_EXPANDED
                                }
                            }
                            else if(abs(currentLeft.minus(parentWidth/2.0)) < abs(currentLeft.minus(peekWidth))) {
                                left = halfExpandedOffset
                                targetState = STATE_HALF_EXPANDED
                            }
                            else {
                                left = collapsedOffset
                                targetState = STATE_COLLAPSED
                            }
                        }
                    }
                    else {
                        left = -viewRef!!.get()!!.width
                        targetState = STATE_HIDDEN
                    }
                }
            }

            if (viewDragHelper!!.settleCapturedViewAt(left, releasedChild.top)) {
                setStateInternal(STATE_SETTLING)
                ViewCompat.postOnAnimation(releasedChild, SettleRunnable(releasedChild, targetState))
            } else {
                setStateInternal(targetState)
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return child.top
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return when(behaviorType) {
                BehaviorType.RIGHT -> { MathUtils.clamp(left, getExpandedOffset(), if(hideable) parentWidth else collapsedOffset) }
                else -> { MathUtils.clamp(left, if (hideable) -child.width else collapsedOffset, getExpandedOffset()) }
            }
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return when(behaviorType) {
                BehaviorType.RIGHT -> { if(hideable) parentWidth else collapsedOffset }
                else -> { if(hideable) child.width else getExpandedOffset().minus(collapsedOffset)}
            }
        }

    }

    private fun dispatchOnSlide(left: Int) {
        val sideSheet: View? = viewRef?.get() as View

        if (sideSheet != null && callback != null) {
            when(behaviorType) {
                BehaviorType.RIGHT -> {
                    if (left > collapsedOffset) {
                        callback!!.onSlide(sideSheet, (collapsedOffset - left).div((parentWidth-collapsedOffset).toFloat()))
                    } else {
                        callback!!.onSlide(sideSheet, (collapsedOffset - left).div((collapsedOffset- getExpandedOffset()).toFloat()))
                    }
                }

                BehaviorType.LEFT -> {
                    if (left < collapsedOffset) {
                        callback!!.onSlide(sideSheet, (left-collapsedOffset).div(peekWidth.toFloat()))
                    } else {
                        callback!!.onSlide(sideSheet, (left-collapsedOffset).div((getExpandedOffset()-collapsedOffset).toFloat()))
                    }
                }
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

    private fun getXVelocity(): Float {
        return if(velocityTracker == null) { 0.0F }
            else {
                velocityTracker!!.computeCurrentVelocity(1000, maximumVelocity!!)
                velocityTracker!!.getXVelocity(activePointerID)
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
            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.ClassLoaderCreator<SavedState> {
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
        var left:Int

        when(st) {
            STATE_COLLAPSED -> { left = collapsedOffset }
            STATE_HALF_EXPANDED -> {
                left = halfExpandedOffset

                if(fitToContents) {
                    if((behaviorType == BehaviorType.RIGHT && left <= fitToContentsOffset) || (behaviorType == BehaviorType.LEFT && left >= fitToContentsOffset)) {
                        state = STATE_EXPANDED
                        left = fitToContentsOffset
                    }
                }
            }
            STATE_EXPANDED -> { left = getExpandedOffset() }
            else -> {
                if(!hideable || state != STATE_HIDDEN) {
                    throw IllegalArgumentException("Illegal state argument: $st")
                }

                left = when(behaviorType) {
                    BehaviorType.RIGHT -> { parentWidth }
                    else -> { -child.width }
                }
            }
        }

        if(viewDragHelper!!.smoothSlideViewTo(child, left, child.top)) {
            setStateInternal(STATE_SETTLING)
            ViewCompat.postOnAnimation(child, SettleRunnable(child, st))
        }
        else {
            setStateInternal(st)
        }
    }

    fun setStateOuter(value: Int) {
        if(viewRef == null) {
            if (value == STATE_COLLAPSED || value == STATE_EXPANDED || value == STATE_HIDDEN) {
                state = value
            }
        }
        else {
            val child:V? = viewRef?.get() ?: return
            startSettlingAnimation(child!!, value)
        }
    }
}