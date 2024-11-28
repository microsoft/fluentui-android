/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.calendar

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.util.Property
import android.view.View
import android.widget.LinearLayout
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime

// TODO: Convert to TemplateView along with other things that extend LinearLayout
// TODO: implement ability to add icon to CalendarDayView
// TODO: implement ability to change background color of CalendarDayView cell
// TODO: ability to change month differentiation behavior through View attributes

/**
 * [CalendarView] is a custom LinearLayout that groups together views used to display
 * calendar dates and allows a user to select a date
 */
class CalendarView : LinearLayout, OnDateSelectedListener {
    companion object {
        const val DAYS_IN_WEEK = 7
        const val WEEK_MID = 3
        private const val VIEW_MODE_CHANGE_ANIMATION_DURATION = 300L
        private const val HEIGHT = "height"
    }

    /**
     * Enums for visible rows in DisplayModes
     * @param [visibleRows] number of rows to show
     */
    enum class DisplayMode(val visibleRows: Int) {
        NONE_MODE(0),
        NORMAL_MODE(2),
        PREVIEW_MODE(3),
        FULL_MODE(5),
        LENGTHY_MODE(15)
    }

    /**
     * Callback implementation for date picking onDateTimePickedListener
     */
    var onDateSelectedListener: OnDateSelectedListener? = null

    /**
     * Integer returning the calendar width for tablet
     */
    var calendarViewWidthForTablet: Int = 0
        private set

    /**
     * Integer returning the fullModeHeight
     */
    val fullModeHeight: Int
        get() = computeHeight(DisplayMode.FULL_MODE)

    /**
     * LocalDate used to set the selected date
     */
    private var date: LocalDate?
        get() = weeksView.selectedDate
        set(value) {
            setSelectedDateRange(value, Duration.ZERO, false)
        }

    private var dividerHeight = 0
    private val config: Config

    private lateinit var weekHeading: WeekHeadingView
    private lateinit var weeksView: WeeksView

    private var rowHeight = 0
    private var isViewModeChanging = false
    private var resizeAnimator: ObjectAnimator? = null
    private var displayMode: DisplayMode = DisplayMode.FULL_MODE

    private val heightProperty: Property<View, Int> = object : Property<View, Int>(Int::class.java, HEIGHT) {
        override fun get(`object`: View): Int {
            return `object`.measuredHeight
        }

        override fun set(`object`: View, value: Int?) {
            val value = value ?: return
            val lp = `object`.layoutParams
            lp.height = value
            `object`.layoutParams = lp
        }
    }

    private val viewModeChangeAnimationListener = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            weeksView.ensureDateVisible(date, displayMode, rowHeight, dividerHeight)
            isViewModeChanging = false
        }
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Calendar), attrs, defStyleAttr) {
        dividerHeight = Math.round(resources.getDimension(R.dimen.fluentui_divider_height))
        calendarViewWidthForTablet = Math.round(resources.getDimension(R.dimen.fluentui_calendar_weeks_max_width))

        config = Config()

        orientation = VERTICAL
        setBackgroundColor(config.backgroundColor)

        initSubViews()
    }

    /**
     * Sets the [DisplayMode] with a flag to animate the resize of the [CalendarView]
     */
    @JvmOverloads
    fun setDisplayMode(mode: DisplayMode, animateResize: Boolean = true) {
        if (mode == displayMode)
            return

        displayMode = mode

        resizeAnimator?.cancel()
        resizeAnimator = null

        if (animateResize) {
            resizeAnimator = ObjectAnimator.ofInt(this, heightProperty, heightProperty.get(this), computeHeight(displayMode))
            resizeAnimator?.addListener(viewModeChangeAnimationListener)
            resizeAnimator?.duration = VIEW_MODE_CHANGE_ANIMATION_DURATION
            resizeAnimator?.start()
        }

        isViewModeChanging = true
    }

    fun setSelectedDateRange(startDate: LocalDate?, duration: Duration, scrollToEnd: Boolean) {
        weeksView.setSelectedDateRange(startDate, duration)
        val date = if (scrollToEnd)
            LocalDateTime.of(startDate, LocalTime.MIDNIGHT).plus(duration).toLocalDate()
        else
            startDate
        weeksView.ensureDateVisible(date, displayMode, rowHeight, dividerHeight)
    }

    fun leaveLengthyMode() {
        if (displayMode != DisplayMode.LENGTHY_MODE)
            return

        // Change height without animation
        displayMode = DisplayMode.FULL_MODE
        val lp = layoutParams
        lp.height = computeHeight(displayMode)
        layoutParams = lp

        weeksView.ensureDateVisible(date, displayMode, rowHeight, dividerHeight)
    }

    /**
     * Reset the views to load latest settings, eg. Start of Week
     */
    fun reset() {
        removeView(weekHeading)
        removeView(weeksView)
        initSubViews()
    }

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpec
        val viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        rowHeight = viewWidth / DAYS_IN_WEEK
        weeksView.setRowHeight(rowHeight)
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(rowHeight * DAYS_IN_WEEK, View.MeasureSpec.EXACTLY)
        resizeAnimator?.let {
            if (it.isRunning) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
                return
            }
        }

        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(computeHeight(displayMode), View.MeasureSpec.EXACTLY))
    }

    override fun onDateSelected(dateTime: ZonedDateTime) {
        this.date = dateTime.toLocalDate()
        onDateSelectedListener?.onDateSelected(dateTime)
    }

    private fun computeHeight(mode: DisplayMode): Int {
        val visibleRows = mode.visibleRows
        val visibleRowsHeight = rowHeight * visibleRows
        val visibleDividersHeight = dividerHeight * visibleRows - 1
        return config.weekHeadingHeight + visibleRowsHeight + visibleDividersHeight
    }

    private fun canExpand(): Boolean {
        return displayMode != DisplayMode.FULL_MODE && displayMode != DisplayMode.LENGTHY_MODE && weeksView.isUserTouchOccurring
    }

    private fun initSubViews() {
        weekHeading = WeekHeadingView(context, config)
        addView(weekHeading)

        weeksView = WeeksView(context, config, this)
        weeksView.isSnappingEnabled = true
        weeksView.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
        addView(weeksView)
        weeksView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (canExpand())
                    displayMode = DisplayMode.FULL_MODE
            }
        })

        dividerDrawable = ContextCompat.getDrawable(context, R.drawable.ms_row_divider)
        showDividers = SHOW_DIVIDER_MIDDLE
    }

    /**
     * The [Config] contains attributes allowing for objects down the line to consume them
     */
    inner class Config {
        val fluentuiContext = FluentUIContextThemeWrapper(context, R.style.Theme_FluentUI_Calendar)
        val backgroundColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarBackgroundColor)
        val weekHeadingBackgroundColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarWeekHeadingBackgroundColor)
        val weekdayHeadingTextColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarWeekHeadingWeekDayTextColor)
        val weekendHeadingTextColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarWeekHeadingWeekendTextColor)
        val weekHeadingHeight = context.resources.getDimensionPixelSize(R.dimen.fluentui_calendar_week_heading_height)

        val selectionAccentColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarSelectedColor)

        val monthOverlayBackgroundColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarMonthOverlayBackgroundColor, 0.7f)
        val monthOverlayTextSize = context.resources.getDimensionPixelSize(R.dimen.fluentui_calendar_month_overlay_text_size)
        val monthOverlayTextColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarMonthOverlayTextColor)

        val differentiateOddEvenMonth = true
        val otherMonthBackgroundColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarOtherMonthBackgroundColor)
        val calendarDayMonthYearTextSize = context.resources.getDimensionPixelSize(R.dimen.fluentui_calendar_month_year_font_size)
        val calendarDayTextSize = context.resources.getDimensionPixelSize(R.dimen.fluentui_calendar_week_day_font_size)

        // Create this in code instead of xml to support Lollipop, which does not allow attributes in xml selectors.
        val calendarDayTextColor = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_activated, -android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_activated, android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_activated, android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_focused, -android.R.attr.state_checked),
                intArrayOf()
            ),
            intArrayOf(
                ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarDayTextActiveColor),
                ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarDayTextActiveCheckedColor),
                ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarDayTextInactiveCheckedColor),
                ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarDayKeyboardFocusTextColor),
                ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiCalendarDayTextDefaultColor)
            )
        )
    }
}

interface OnDateSelectedListener {
    /**
     * Method called when a user selects a date
     * @param [dateTime] the selected date
     */
    fun onDateSelected(dateTime: ZonedDateTime)
}