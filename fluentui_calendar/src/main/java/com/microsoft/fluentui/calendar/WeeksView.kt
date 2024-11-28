/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.calendar

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.util.Pools
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.TextPaint
import android.text.format.DateUtils
import android.text.format.DateUtils.FORMAT_NO_MONTH_DAY
import android.text.format.DateUtils.FORMAT_SHOW_DATE
import android.util.AttributeSet
import android.view.View

import com.microsoft.fluentui.util.ColorProperty
import com.microsoft.fluentui.util.DateTimeUtils
import com.microsoft.fluentui.view.MSRecyclerView
import java.time.Duration
import java.time.LocalDate
import java.time.Month
import java.time.ZonedDateTime
import java.time.chrono.IsoChronology
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * [WeeksView] is a RecyclerView for week days
 */
internal class WeeksView : MSRecyclerView {
    companion object {
        private const val OVERLAY_TRANSITION_DURATION = 200L

        private const val MONTH_OVERLAY_BACKGROUND_COLOR = "monthOverlayBackgroundColor"
        private const val MONTH_OVERLAY_FONT_COLOR = "monthOverlayFontColor"
        private const val FONT_FAMILY = "sans-serif-medium"

        private const val MONTH_DESCRIPTORS_CAPACITY = 4
        private const val DAYS_IN_WEEK = 7
    }

    enum class OverlayState {
        IS_BEING_DISPLAYED,
        DISPLAYED,
        IS_BEING_HIDDEN,
        HIDDEN
    }

    /**
     * @return [LocalDate] the earliest date displayed
     */
    val minDate: LocalDate
        get() = pickerAdapter.minDate

    val selectedDate: LocalDate?
        get() = pickerAdapter.selectedDate

    val firstVisibleItemPosition: Int
        get() = (layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

    private lateinit var config: CalendarView.Config

    private lateinit var pickerAdapter: CalendarAdapter

    private var overlayDisplayState = OverlayState.HIDDEN
    private val overlayTransitionAnimator = AnimatorSet()

    private val showingOverlayAnimationListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            overlayDisplayState = OverlayState.DISPLAYED
        }
    }

    private val hidingOverlayAnimationListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            overlayDisplayState = OverlayState.HIDDEN
        }
    }

    private lateinit var overlayBackgroundColorProperty: ColorProperty
    private lateinit var overlayFontColorProperty: ColorProperty
    private lateinit var onDateSelectedListener: OnDateSelectedListener
    private lateinit var paint: TextPaint

    constructor(context: Context, config: CalendarView.Config, onDateSelectedListener: OnDateSelectedListener) : super(context) {
        this.config = config
        this.onDateSelectedListener = onDateSelectedListener
        setWillNotDraw(false)

        ContextCompat.getDrawable(context, R.drawable.ms_row_divider)?.let {
            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            divider.setDrawable(it)
            addItemDecoration(divider)
        }

        pickerAdapter = CalendarAdapter(context, config, this.onDateSelectedListener)
        adapter = pickerAdapter

        setHasFixedSize(true)
        layoutManager = GridLayoutManager(context, DAYS_IN_WEEK, LinearLayoutManager.VERTICAL, false)
        layoutManager?.scrollToPosition(pickerAdapter.todayPosition)

        itemAnimator = null

        paint = TextPaint()
        paint.density = resources.displayMetrics.density
        paint.isAntiAlias = true
        paint.isSubpixelText = true
        paint.typeface = Typeface.create(FONT_FAMILY, Typeface.NORMAL)
        paint.textSize = config.monthOverlayTextSize.toFloat()

        val startColor = ColorUtils.setAlphaComponent(config.monthOverlayBackgroundColor, 0)
        overlayBackgroundColorProperty = ColorProperty(MONTH_OVERLAY_BACKGROUND_COLOR, startColor, config.monthOverlayBackgroundColor)
        overlayFontColorProperty = ColorProperty(MONTH_OVERLAY_FONT_COLOR, startColor, config.monthOverlayTextColor)
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    fun ensureDateVisible(date: LocalDate?, displayMode: CalendarView.DisplayMode, rowHeight: Int, dividerHeight: Int) {
        val date = date ?: LocalDate.now()
        smoothScrollBy(0, 0)

        val datePosition = ChronoUnit.DAYS.between(minDate, date).toInt()
        val visibleRows = displayMode.visibleRows
        val firstVisiblePosition = firstVisibleItemPosition
        val lastVisiblePosition = firstVisiblePosition + CalendarView.DAYS_IN_WEEK * visibleRows

        if (NO_POSITION == firstVisiblePosition || datePosition < firstVisiblePosition || DateTimeUtils.isSameDay(date, ZonedDateTime.now())) {
            scrollToPositionWithOffset(datePosition, 0)
        } else if (datePosition >= lastVisiblePosition) {
            val offset = (visibleRows - 1) * (rowHeight + dividerHeight)
            scrollToPositionWithOffset(datePosition, offset)
        }
    }

    fun scrollToPositionWithOffset(position: Int, offset: Int) {
        (layoutManager as GridLayoutManager).scrollToPositionWithOffset(position, offset)
    }

    fun setSelectedDateRange(localDate: LocalDate?, duration: Duration) {
        pickerAdapter.setSelectedDateRange(localDate, duration)
    }

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        when (state) {
            SCROLL_STATE_DRAGGING -> showOverlay()
            SCROLL_STATE_IDLE -> hideOverlay()
        }
    }

    public override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)

        if (pickerAdapter.itemCount == 0 || OverlayState.HIDDEN == overlayDisplayState)
            return

        // draw overlay background
        paint.color = overlayBackgroundColorProperty.color
        canvas.drawRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), paint)

        computeVisibleMonths()

        val textBounds = Rect()
        val monthDescriptors = computeVisibleMonths()
        for (monthDescriptor in monthDescriptors) {
            val text = DateUtils.formatDateTime(context, monthDescriptor.timestamp, FORMAT_SHOW_DATE or FORMAT_NO_MONTH_DAY)

            paint.getTextBounds(text, 0, text.length, textBounds)
            paint.color = overlayFontColorProperty.color
            canvas.drawText(text,
                    ((measuredWidth - textBounds.width()) / 2).toFloat(),
                    (((monthDescriptor.bottom + monthDescriptor.top)- textBounds.height()) / 2).toFloat(),
                    paint
            )

            monthDescriptor.recycle()
        }
        monthDescriptors.clear()
    }

    private fun computeVisibleMonths(): ArrayList<MonthDescriptor> {
        var previousMonth: Month? = null
        var previousYear = -1

        val now = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)
        val monthDescriptors = ArrayList<MonthDescriptor>(MONTH_DESCRIPTORS_CAPACITY)
        for (i in 0 until childCount step DAYS_IN_WEEK) {
            val calendarDayView = getChildAt(i) as CalendarDayView
            val date = calendarDayView.date
            val month = date.month
            if (previousMonth == month)
                continue
            createMonthDescriptor(monthDescriptors, now, previousYear, previousMonth)
            previousMonth = month
            previousYear = date.year
        }

        createMonthDescriptor(monthDescriptors, now, previousYear, previousMonth)

        return monthDescriptors
    }

    private fun createMonthDescriptor(monthDescriptors: ArrayList<MonthDescriptor>, now: ZonedDateTime, previousYear: Int, previousMonth: Month?) {
        if (previousMonth == null)
            return
        var c = now.withYear(previousYear).withMonth(previousMonth.value).withDayOfMonth(1)
        val firstDayOfPreviousMonthRowPosition = getRowPositionForDate(c)
        val isLeapYear = IsoChronology.INSTANCE.isLeapYear(c.year.toLong())
        c = c.withDayOfMonth(previousMonth.length(isLeapYear))
        val lastDayOfPreviousMonthRowPosition = getRowPositionForDate(c)

        val monthDescriptor = MonthDescriptor.obtain()
        monthDescriptor.timestamp = c.toInstant().toEpochMilli()
        monthDescriptor.top = rowToScreenPosition(firstDayOfPreviousMonthRowPosition)
        monthDescriptor.bottom = rowToScreenPosition(lastDayOfPreviousMonthRowPosition)
        monthDescriptors.add(monthDescriptor)
    }

    private fun rowToScreenPosition(rowPosition: Int): Int {
        val glm = layoutManager as GridLayoutManager
        val firstVisibleRowPosition = glm.findFirstVisibleItemPosition() / DAYS_IN_WEEK
        val view = getChildAt(0)
        val rowHeight = view.measuredHeight
        return view.top + rowHeight * (rowPosition - firstVisibleRowPosition)
    }

    private fun getRowPositionForDate(zonedDateTime: ZonedDateTime): Int {
        val date = zonedDateTime.truncatedTo(ChronoUnit.DAYS)
        return (ChronoUnit.DAYS.between(minDate, date) / DAYS_IN_WEEK.toLong()).toInt()
    }

    private fun showOverlay() {
        if (overlayDisplayState == OverlayState.IS_BEING_DISPLAYED || overlayDisplayState == OverlayState.DISPLAYED)
            return

        overlayTransitionAnimator.cancel()
        overlayTransitionAnimator.removeAllListeners()

        overlayDisplayState = OverlayState.IS_BEING_DISPLAYED

        overlayTransitionAnimator.playTogether(
            ObjectAnimator.ofFloat(this, overlayBackgroundColorProperty, overlayBackgroundColorProperty.get(this), 1.0f),
            ObjectAnimator.ofFloat(this, overlayFontColorProperty,overlayFontColorProperty.get(this), 1.0f)
        )
        overlayTransitionAnimator.duration = OVERLAY_TRANSITION_DURATION
        overlayTransitionAnimator.addListener(showingOverlayAnimationListener)
        overlayTransitionAnimator.start()
    }

    private fun hideOverlay() {
        if (overlayDisplayState == OverlayState.IS_BEING_HIDDEN || overlayDisplayState == OverlayState.HIDDEN)
            return

        overlayTransitionAnimator.cancel()
        overlayTransitionAnimator.removeAllListeners()

        overlayDisplayState = OverlayState.IS_BEING_HIDDEN

        overlayTransitionAnimator.playTogether(
            ObjectAnimator.ofFloat(this, overlayBackgroundColorProperty, overlayBackgroundColorProperty.get(this), 0.0f),
            ObjectAnimator.ofFloat(this, overlayFontColorProperty, overlayFontColorProperty.get(this), 0.0f)
        )
        overlayTransitionAnimator.duration = OVERLAY_TRANSITION_DURATION
        overlayTransitionAnimator.addListener(hidingOverlayAnimationListener)
        overlayTransitionAnimator.start()
    }

    fun setRowHeight(rowHeight: Int) {
        pickerAdapter.setViewHeight(rowHeight)
    }

    private class MonthDescriptor {
        companion object {
            private val MONTH_DESCRIPTOR_POOL = Pools.SimplePool<MonthDescriptor>(3)

            fun obtain(): MonthDescriptor {
                return MONTH_DESCRIPTOR_POOL.acquire() ?: MonthDescriptor()
            }
        }

        var top: Int = 0
        var bottom: Int = 0
        var timestamp: Long = 0

        fun recycle() {
            MONTH_DESCRIPTOR_POOL.release(this)
        }
    }

    private class HingeItemDecoration internal constructor(private val mMaskSize: Int) : ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
            val rightOffset = if (parent.getChildAdapterPosition(view) % DAYS_IN_WEEK == 2) {
                mMaskSize
            } else {
                0
            }
            outRect[0, 0, rightOffset] = 0
        }
    }
}
