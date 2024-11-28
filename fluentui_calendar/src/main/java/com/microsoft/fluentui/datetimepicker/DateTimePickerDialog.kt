/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.datetimepicker

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.LayoutTransition
import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatDialog
import androidx.appcompat.widget.Toolbar
import android.view.*
import android.view.accessibility.AccessibilityEvent
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.microsoft.fluentui.calendar.R
import com.microsoft.fluentui.calendar.CalendarView
import com.microsoft.fluentui.calendar.OnDateSelectedListener
import com.microsoft.fluentui.calendar.databinding.DialogDateTimePickerBinding
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.*
import com.microsoft.fluentui.calendar.databinding.DialogResizableBinding
import java.time.Duration
import java.time.ZonedDateTime

// TODO consider merging PickerMode and DateRangeMode since not all combinations will work
/**
 * [DateTimePickerDialog] provides a dialog view housing both a [CalendarView] and a view allowing
 * the picking of dates and times in a [WrapContentViewPager] as well as toolbar UI and menu buttons to
 * dismiss the dialog and accept a date/ time.
 */
class DateTimePickerDialog : AppCompatDialog, Toolbar.OnMenuItemClickListener, OnDateSelectedListener, TimePicker.OnTimeSlotSelectedListener {
    enum class PickerTab {
        CALENDAR_VIEW, DATE_TIME_PICKER
    }

    enum class Mode(internal val defaultMode: DisplayMode, internal val accessibleMode: DisplayMode) {
        DATE(DisplayMode.DATE, DisplayMode.ACCESSIBLE_DATE),
        DATE_TIME(DisplayMode.DATE_TIME, DisplayMode.ACCESSIBLE_DATE_TIME),
        TIME_DATE(DisplayMode.TIME_DATE, DisplayMode.ACCESSIBLE_DATE_TIME)
    }

    enum class DateRangeMode {
        NONE, START, END
    }

    internal enum class DisplayMode(val dateTabIndex: Int, val dateTimeTabIndex: Int) {
        ACCESSIBLE_DATE(0, -1),
        ACCESSIBLE_DATE_TIME(-1, 0),
        DATE(0, -1),
        DATE_TIME(0, 1),
        TIME(-1, 0),
        TIME_DATE(0, 1)
    }

    /**
     * Returns the selected [PickerTab] tab.
     */
    val pickerTab: PickerTab?
        get() = if (dialogContainerBinding.tabs.selectedTabPosition == -1) null else PickerTab.values()[dialogContainerBinding.tabs.selectedTabPosition]
    /**
     * Returns the selected [DateTimeRangeTab] tab.
     */
    val dateTimeRangeTab: DateTimeRangeTab = DateTimeRangeTab.START
        get() = pagerAdapter.timePicker?.selectedTab ?: field
    /**
     * Register a callback for when a [ZonedDateTime] and [Duration] are picked.
     */
    var onDateTimePickedListener: OnDateTimePickedListener? = null
    /**
     * Register a callback for when a [ZonedDateTime] and [Duration] are selected.
     */
    var onDateTimeSelectedListener: OnDateTimeSelectedListener? = null

    private val animatorListener = object : AnimatorListenerAdapter() {
        override fun onAnimationCancel(animation: Animator) {
            super.onAnimationCancel(animation)
            pagerAdapter.calendarView?.leaveLengthyMode()
        }

        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            pagerAdapter.calendarView?.leaveLengthyMode()
        }
    }

    private val pageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            updateTitles()
            val calendarView = pagerAdapter.calendarView
            val timePicker = pagerAdapter.timePicker
            if (position == displayMode.dateTabIndex && calendarView != null) {
                dialogContainerBinding.viewPager.currentObject = calendarView
                // We're switching from the tall time picker to the short date picker. Layout transition
                // leaves blank white area below date picker. So manual animation is used here instead to avoid this.
                enableLayoutTransition(false)
                dialogContainerBinding.viewPager.smoothlyResize(calendarView.fullModeHeight, animatorListener)
            } else if (position == displayMode.dateTimeTabIndex && timePicker != null) {
                dialogContainerBinding.viewPager.currentObject = timePicker
                calendarView?.setDisplayMode(CalendarView.DisplayMode.LENGTHY_MODE, true)
                enableLayoutTransition(true)
                dialogContainerBinding.viewPager.shouldWrapContent = true
            }
        }
    }

    private val displayMode: DisplayMode
    private val dateRangeMode: DateRangeMode
    private val fluentuiContext: Context
    private val layoutTransition = LayoutTransition()
    private var dateTime: ZonedDateTime
    private var duration: Duration
    private lateinit var dialogBinding: DialogResizableBinding
    private lateinit var dialogContainerBinding: DialogDateTimePickerBinding
    private lateinit var pagerAdapter: DateTimePagerAdapter

    @JvmOverloads
    constructor(
        context: Context,
        mode: Mode,
        dateRangeMode: DateRangeMode = DateRangeMode.NONE,
        dateTime: ZonedDateTime = ZonedDateTime.now(),
        duration: Duration = Duration.ZERO
    ) : this(
        context,
        if (context.isAccessibilityEnabled) mode.accessibleMode else mode.defaultMode,
        dateRangeMode,
        dateTime,
        duration
    )

    @JvmOverloads
    internal constructor(
        context: Context,
        displayMode: DisplayMode,
        dateRangeMode: DateRangeMode = DateRangeMode.NONE,
        dateTime: ZonedDateTime = ZonedDateTime.now(),
        duration: Duration = Duration.ZERO
    ) : super(context, R.style.Dialog_FluentUI) {
        fluentuiContext = FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Calendar)

        this.dateRangeMode = dateRangeMode
        this.dateTime = dateTime
        this.duration = duration
        this.displayMode = displayMode

        initDialog()
        initDialogContents()
    }

    private fun enableLayoutTransition(enabled: Boolean) {
        (dialogBinding.root as ViewGroup).layoutTransition = if (enabled) layoutTransition else null
    }

    private fun initDialog() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogBinding = DialogResizableBinding.inflate(LayoutInflater.from(fluentuiContext))

        // Resize animation
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        enableLayoutTransition(true)

        // Dismiss after tapping outside the dialog
        (dialogBinding.root as View).setOnTouchListener(object : View.OnTouchListener {
            private val rect = Rect()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                dialogBinding.root.performClick()
                dialogBinding.cardViewContainer.getHitRect(rect)
                if (!rect.contains(event.x.toInt(), event.y.toInt())) {
                    cancel()
                    return true
                }
                return false
            }
        })

        setContentView(dialogBinding.root)
    }

    private fun initDialogContents() {
        dialogContainerBinding = DialogDateTimePickerBinding.inflate(LayoutInflater.from(fluentuiContext), dialogBinding.cardViewContainer, true)

        dialogContainerBinding.toolbar.inflateMenu(R.menu.menu_time_picker)
        dialogContainerBinding.toolbar.setOnMenuItemClickListener(this)

        val closeIconColor = ThemeUtil.getColor(fluentuiContext, R.attr.fluentuiDialogCloseIconColor)
        dialogContainerBinding.toolbar.navigationIcon = context.getTintedDrawable(R.drawable.ms_ic_dismiss_24_filled, closeIconColor)
        dialogContainerBinding.toolbar.navigationContentDescription = context.resources.getString(R.string.date_time_picker_accessibility_close_dialog_button)
        dialogContainerBinding.toolbar.setNavigationOnClickListener { cancel() }

        val doneIconColor = ThemeUtil.getThemeAttrColor(fluentuiContext, R.attr.fluentuiDateTimePickerToolbarIconColor)
        dialogContainerBinding.toolbar.menu.findItem(R.id.action_done).icon = context.getTintedDrawable(R.drawable.ms_ic_checkmark_24_filled, doneIconColor)

        pagerAdapter = DateTimePagerAdapter()
        dialogContainerBinding.viewPager.adapter = pagerAdapter
        dialogContainerBinding.viewPager.addOnPageChangeListener(pageChangeListener)

        if (displayMode == DisplayMode.TIME_DATE)
            dialogContainerBinding.viewPager.currentItem = displayMode.dateTimeTabIndex

        if (pagerAdapter.count < 2)
            dialogContainerBinding.tabContainer.visibility = View.GONE
        else
            dialogContainerBinding.tabs.setupWithViewPager(dialogContainerBinding.viewPager)

        updateTitles()
    }

    override fun onStart() {
        super.onStart()
        window?.setLayout(context.desiredDialogSize[0], WindowManager.LayoutParams.MATCH_PARENT)
    }

    override fun dismiss() {
        // For single instance dialogs this prevents a dialog from animating into its final size when
        // shown after a rotation, since its dimensions in portrait are different than in landscape.
        enableLayoutTransition(false)
        super.dismiss()
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        onDateTimePickedListener?.onDateTimePicked(dateTime, duration)
        dismiss()
        return false
    }

    override fun onDateSelected(dateTime: ZonedDateTime) {
        when (dateRangeMode) {
            DateRangeMode.NONE -> {
                this.dateTime = this.dateTime.with(dateTime.toLocalDate())
                duration = Duration.ZERO
            }
            DateRangeMode.START -> {
                this.dateTime = this.dateTime.with(dateTime.toLocalDate())
            }
            DateRangeMode.END -> {
                if (dateTime.isBefore(this.dateTime))
                    this.dateTime = dateTime.minus(duration)
                else
                    duration = dateTime.getNumberOfDaysFrom(this.dateTime)
            }
        }

        updateCalendarSelectedDateRange()
        updateTitles()
        updateTimePicker()

        onDateTimeSelectedListener?.onDateTimeSelected(this.dateTime, duration)
    }

    override fun onTimeSlotSelected(timeSlot: TimeSlot) {
        this.dateTime = timeSlot.start
        this.duration = timeSlot.duration

        updateCalendarSelectedDateRange()
        onDateTimeSelectedListener?.onDateTimeSelected(this.dateTime, this.duration)
        updateTitles()
    }

    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent): Boolean {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val announcement = when (displayMode) {
                DisplayMode.DATE, DisplayMode.ACCESSIBLE_DATE -> {
                    if (dateRangeMode != DateRangeMode.NONE)
                        R.string.date_picker_range_accessibility_dialog_title
                    else
                        R.string.date_picker_accessibility_dialog_title
                }
                else -> {
                    if (dateRangeMode != DateRangeMode.NONE)
                        R.string.date_time_picker_range_accessibility_dialog_title
                    else
                        R.string.date_time_picker_accessibility_dialog_title
                }
            }

            event.text.add(context.resources.getString(announcement))
            return true
        }
        return super.dispatchPopulateAccessibilityEvent(event)
    }

    private fun updateTimePicker() {
        val timeSlot = pagerAdapter.timePicker?.timeSlot ?: return
        if (DateTimeUtils.isSameDay(dateTime, timeSlot.start))
            return

        pagerAdapter.timePicker?.timeSlot = TimeSlot(timeSlot.start.with(dateTime.toLocalDate()), timeSlot.duration)
    }

    private fun updateCalendarSelectedDateRange() {
        pagerAdapter.calendarView?.setSelectedDateRange(dateTime.toLocalDate(), duration, dateRangeMode == DateRangeMode.END)
    }

    private fun updateTitles() {
        when (displayMode) {
            DisplayMode.DATE -> {
                dialogContainerBinding.toolbar.title = if (dateRangeMode == DateRangeMode.START)
                    DateStringUtils.formatDateAbbrevAll(context, dateTime)
                else
                    DateStringUtils.formatDateAbbrevAll(context, dateTime.plus(duration))
            }
            DisplayMode.ACCESSIBLE_DATE -> {
                dialogContainerBinding.toolbar.title = if (dateRangeMode != DateRangeMode.NONE)
                    context.resources.getString(R.string.date_time_picker_choose_date)
                else
                    DateStringUtils.formatMonthDayYear(context, dateTime.plus(duration))
            }
            DisplayMode.TIME, DisplayMode.ACCESSIBLE_DATE_TIME -> {
                dialogContainerBinding.toolbar.title = if (dateRangeMode != DateRangeMode.NONE)
                    context.resources.getString(R.string.date_time_picker_choose_time)
                else
                    DateStringUtils.formatAbbrevDateTime(context, dateTime.plus(duration))
            }
            else -> {
                val currentTab = dialogContainerBinding.viewPager.currentItem
                dialogContainerBinding.toolbar.setTitle(if (currentTab == displayMode.dateTabIndex) R.string.date_time_picker_choose_date else R.string.date_time_picker_choose_time)

                val tabDate = if (dateTimeRangeTab == DateTimeRangeTab.END) dateTime.plus(duration) else dateTime

                // Set tab titles
                if (displayMode.dateTabIndex != -1)
                    dialogContainerBinding.tabs.getTabAt(displayMode.dateTabIndex)?.text = DateStringUtils.formatDateWithWeekDay(
                        context,
                        if (currentTab == displayMode.dateTabIndex) dateTime else tabDate
                    )

                if (displayMode.dateTimeTabIndex != -1)
                    dialogContainerBinding.tabs.getTabAt(displayMode.dateTimeTabIndex)?.text = DateStringUtils.formatAbbrevTime(context, tabDate)
            }
        }
    }

    private inner class DateTimePagerAdapter : PagerAdapter() {
        var calendarView: CalendarView? = null
        var timePicker: TimePicker? = null

        override fun getCount(): Int =
            if (displayMode == DisplayMode.DATE_TIME || displayMode == DisplayMode.TIME_DATE) 2 else 1

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = if (position == 0 && useCalendarView(position))
                initCalendarView()
            else
                initTimePicker()

            if (position == dialogContainerBinding.viewPager.currentItem)
                dialogContainerBinding.viewPager.currentObject = view

            container.addView(view)

            return view
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

        override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
            container.removeView(view as View)
        }

        private fun initCalendarView(): CalendarView {
            val calendarView = CalendarView(fluentuiContext)
            this.calendarView = calendarView
            calendarView.onDateSelectedListener = this@DateTimePickerDialog
            updateCalendarSelectedDateRange()

            return calendarView
        }

        private fun initTimePicker(): TimePicker {
            val timePicker = TimePicker(fluentuiContext)
            this.timePicker = timePicker
            timePicker.timeSlot = TimeSlot(dateTime, duration)
            timePicker.pickerMode = if (displayMode == DisplayMode.ACCESSIBLE_DATE)
                TimePicker.PickerMode.DATE
            else
                TimePicker.PickerMode.DATE_TIME
            timePicker.onTimeSlotSelectedListener = this@DateTimePickerDialog
            initTimePickerUI()

            return timePicker
        }

        private fun initTimePickerUI() {
            when (dateRangeMode) {
                DateRangeMode.START -> {
                    timePicker?.selectTab(DateTimeRangeTab.START)
                    timePicker?.setPickerValues(showEndTime = false, animate = false)
                }
                DateRangeMode.END -> {
                    timePicker?.selectTab(DateTimeRangeTab.END)
                    timePicker?.setPickerValues(showEndTime = true, animate = false)
                }
                DateRangeMode.NONE -> {
                    timePicker?.selectTab(DateTimeRangeTab.NONE)
                    timePicker?.setPickerValues(showEndTime = false, animate = false)
                }
            }
        }

        private fun useCalendarView(position: Int): Boolean =
            position == displayMode.dateTabIndex &&
                (displayMode == DisplayMode.DATE || displayMode == DisplayMode.TIME_DATE || displayMode == DisplayMode.DATE_TIME)
    }

    /**
     * Interface definition for a callback to be invoked when a date and time are picked.
     */
    interface OnDateTimePickedListener {
        /**
         * Method called when a user picks / completes a date, date and time, or date range start/ end selection.
         * @param [dateTime] the picked date or date and time
         * @param [duration] the picked duration of a date range
         */
        fun onDateTimePicked(dateTime: ZonedDateTime, duration: Duration)
    }

    /**
     * Interface definition for a callback to be invoked when a date and time are selected.
     */
    interface OnDateTimeSelectedListener {
        /**
         * Method called when a user selects a date, date and time, or date range start/ end.
         * @param [dateTime] the selected date or date and time
         * @param [duration] the selected duration of a date range
         */
        fun onDateTimeSelected(dateTime: ZonedDateTime, duration: Duration)
    }
}