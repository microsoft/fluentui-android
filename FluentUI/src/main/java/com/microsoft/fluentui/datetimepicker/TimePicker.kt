/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.datetimepicker

import android.content.Context
import android.support.design.widget.TabLayout
import android.support.v4.view.AccessibilityDelegateCompat
import android.support.v4.view.ViewCompat
import android.text.format.DateFormat
import com.microsoft.fluentui.util.DateTimeUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.widget.LinearLayout
import com.microsoft.fluentui.R
import com.microsoft.fluentui.util.DateStringUtils
import com.microsoft.fluentui.datetimepicker.TimePicker.PickerMode
import com.microsoft.fluentui.managers.PreferencesManager
import kotlinx.android.synthetic.main.view_time_picker.view.*
import org.threeten.bp.*
import org.threeten.bp.temporal.ChronoUnit
import java.text.DateFormatSymbols

/**
 * [TimePicker] houses [NumberPicker]s that allow users to pick dates, times and periods (12 hour clocks).
 * When [PickerMode.DATE] is the pickerMode months, days and years are shown.
 */
internal class TimePicker : LinearLayout, NumberPicker.OnValueChangeListener {
    companion object {
        private const val MONTH_LIMIT = 1200L
        private const val MIN_DAYS = 1
        private const val MAX_HOURS_24_CLOCK = 23
        private const val MAX_HOURS_12_CLOCK = 12
        private const val MIN_HOURS_24_CLOCK = 0
        private const val MIN_HOURS_12_CLOCK = 1
        private const val MAX_MINUTES = 59
        private const val MIN_MINUTES = 0
        private const val MIN_MONTHS = 1
        private const val MIN_PERIOD = 0
        private const val MAX_PERIOD = 1
        private const val HOUR_BEFORE_PERIOD_CHANGE = 11
    }

    enum class PickerMode {
        DATE, DATE_TIME
    }

    private enum class AmPmPeriod {
        AM, PM
    }

    val selectedTab: DateTimeRangeTab
        get() = DateTimeRangeTab.values()[start_end_tabs.selectedTabPosition]

    var timeSlot: TimeSlot = TimeSlot(ZonedDateTime.now(), Duration.ZERO)
        get() {
            val updatedTime = pickerValue

            if (selectedTab == DateTimeRangeTab.START)
                dateTime = updatedTime
            else
                duration = if (updatedTime.isBefore(dateTime)) Duration.ZERO else Duration.between(dateTime, updatedTime)

            return TimeSlot(dateTime, duration)
        }
        set(value) {
            field = value
            dateTime = value.start.truncatedTo(ChronoUnit.MINUTES)
            duration = value.duration
            setPickerValues(selectedTab == DateTimeRangeTab.END, false)
        }

    /**
     * Determines whether date and time are shown or just date.
     */
    var pickerMode: PickerMode = PickerMode.DATE_TIME
        set(value) {
            field = value
            when (pickerMode) {
                PickerMode.DATE -> initDateNumberPickers()
                PickerMode.DATE_TIME -> initDateTimeNumberPickers()
            }
        }

    var onTimeSlotSelectedListener: OnTimeSlotSelectedListener? = null

    private val pickerValue: ZonedDateTime
        get() = when (pickerMode) {
            PickerMode.DATE -> datePickerValue
            PickerMode.DATE_TIME -> dateTimePickerValue
        }

    private var dateTime: ZonedDateTime = ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES)
    private var duration: Duration = Duration.ZERO
    private val is24Hour: Boolean
    private var daysBack: Int = 0
    private var daysForward: Int = 0
    private var lastSelectedHour: Int = 0

    private var shouldAnnounceHints: Boolean = true

    private val shouldToggleAmPmPeriod: Boolean
        get() = lastSelectedHour == HOUR_BEFORE_PERIOD_CHANGE && hour_picker.value == MAX_HOURS_12_CLOCK ||
            lastSelectedHour == MAX_HOURS_12_CLOCK && hour_picker.value == HOUR_BEFORE_PERIOD_CHANGE

    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            // Adjust start time and duration when switching tabs
            shouldAnnounceHints = false
            setPickerValues(tab.tag === DateTimeRangeTab.END, true)
        }

        override fun onTabUnselected(tab: TabLayout.Tab) { }

        override fun onTabReselected(tab: TabLayout.Tab) { }
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_time_picker, this, true)
        is24Hour = DateFormat.is24HourFormat(context)

        with(start_end_tabs) {
            addTab(newTab())
            addTab(newTab())
            addOnTabSelectedListener(onTabSelectedListener)
        }

        getTab(DateTimeRangeTab.START)?.tag = DateTimeRangeTab.START
        getTab(DateTimeRangeTab.END)?.tag = DateTimeRangeTab.END
    }

    /**
     * Selects either [DateTimeRangeTab.START] or [DateTimeRangeTab.END] tab or in the case of [DateTimeRangeTab.NONE] hides the tabs.
     */
    fun selectTab(dateTimeRangeTab: DateTimeRangeTab) {
        if (dateTimeRangeTab == DateTimeRangeTab.NONE) {
            start_end_tabs.visibility = View.GONE
        } else {
            with(start_end_tabs) {
                removeOnTabSelectedListener(onTabSelectedListener)
                getTabAt(dateTimeRangeTab.ordinal)?.select()
                addOnTabSelectedListener(onTabSelectedListener)
                visibility = View.VISIBLE
            }
        }
    }

    /**
     * Sets the pickers values
     * @param showEndTime is a flag that sets the [NumberPicker]s' end date / time, start date / time or,
     * in the case of no duration, a selected date / time.
     * @param animate is a flag that sets whether the [NumberPicker]s animate to their set values.
     */
    fun setPickerValues(showEndTime: Boolean, animate: Boolean) {
        when (pickerMode) {
            PickerMode.DATE -> setDatePickerValues(showEndTime, animate)
            PickerMode.DATE_TIME -> setDateTimePickerValues(showEndTime, animate)
        }

        updateRangeContentDescription()
    }

    override fun onValueChange(picker: NumberPicker, oldVal: Int, newVal: Int) {
        if (pickerMode == PickerMode.DATE)
            updateDaysPerMonth(timeSlot.start)

        // Switch AM PM period according to the hour and direction of movement
        if (!is24Hour && picker.id == R.id.hour_picker)
            updateAmPmPeriod()

        onTimeSlotSelectedListener?.onTimeSlotSelected(timeSlot)

        if (shouldAnnounceHints)
            announceNumberPickerValue(picker)

        updateRangeContentDescription()

        when (pickerMode) {
            PickerMode.DATE -> updateDatePickerHints()
            PickerMode.DATE_TIME -> updateDateTimePickerHints()
        }
    }

    private fun getHour(time: ZonedDateTime): Int =
        if (is24Hour || dateTimePickerValue.hour == MAX_HOURS_12_CLOCK )
            time.hour
        else
            time.hour % MAX_HOURS_12_CLOCK

    private fun getDate(dateValue: Int, dateTime: ZonedDateTime): String {
        when (dateValue) {
            daysBack -> return context.getString(R.string.today)
            daysBack + 1 -> return context.getString(R.string.tomorrow)
            daysBack - 1 -> return context.getString(R.string.yesterday)
            else -> {
                if (DateTimeUtils.isSameYear(LocalDate.now(), dateTime.toLocalDate()))
                    return DateStringUtils.formatDateAbbrevAll(context, dateTime)
                else
                    return DateStringUtils.formatWeekdayDateYearAbbrev(context, dateTime)
            }
        }
    }

    private fun getTab(dateTimeRangeTab: DateTimeRangeTab): TabLayout.Tab? = start_end_tabs.getTabAt(dateTimeRangeTab.ordinal)

    // Date Time NumberPickers

    private val dateTimePickerValue: ZonedDateTime
        get() {
            val now = ZonedDateTime.now().truncatedTo(ChronoUnit.MINUTES)
            val dayDiff = date_picker.value - daysBack
            var hour = hour_picker.value
            val minute = minute_picker.value

            if (!is24Hour) {
                val isMorning = period_picker.value == 0
                val periodStartHour = if (isMorning) 0 else MAX_HOURS_12_CLOCK
                if (hour == MAX_HOURS_12_CLOCK)
                    hour = periodStartHour
                else
                    hour += periodStartHour
            }

            return now.plusDays(dayDiff.toLong()).withHour(hour).withMinute(minute)
        }

    private fun initDateTimeNumberPickers() {
        getTab(DateTimeRangeTab.START)?.setText(R.string.date_time_picker_start_time)
        getTab(DateTimeRangeTab.END)?.setText(R.string.date_time_picker_end_time)
        getTab(DateTimeRangeTab.START)?.contentDescription = resources.getString(R.string.date_time_picker_accessiblility_start_time)
        getTab(DateTimeRangeTab.END)?.contentDescription = resources.getString(R.string.date_time_picker_accessiblility_end_time)

        date_time_pickers.visibility = View.VISIBLE

        initDatePicker()
        initHourPicker()
        initMinutePicker()
        initPeriodPicker()

        setNumberPickerGroupAccessibilityFocusChangeListener(date_time_pickers)
    }

    private fun initDatePicker() {
        val firstDayOfWeek = PreferencesManager.getWeekStart(context)
        val today = LocalDate.now()
        var minWindowRange = today.minusMonths(MONTH_LIMIT)
        minWindowRange = DateTimeUtils.roundToLastWeekend(minWindowRange, firstDayOfWeek)
        var maxWindowRange = today.plusMonths(MONTH_LIMIT)
        maxWindowRange = DateTimeUtils.roundToNextWeekend(maxWindowRange, firstDayOfWeek)

        daysBack = ChronoUnit.DAYS.between(minWindowRange, today).toInt()
        daysForward = ChronoUnit.DAYS.between(today, maxWindowRange).toInt()

        with(date_picker) {
            minValue = 0
            maxValue = daysBack + daysForward
            value = daysBack
            virtualIncrementButtonDescription = resources.getString(R.string.date_time_picker_accessibility_increment_date_button)
            virtualDecrementButtonDescription = resources.getString(R.string.date_time_picker_accessibility_decrement_date_button)
            virtualIncrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_next_date_click_action)
            virtualDecrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_previous_date_click_action)
            setFormatter(DateFormatter(today.atStartOfDay(ZoneId.systemDefault())))
            setOnValueChangedListener(this@TimePicker)
        }
    }

    private fun initHourPicker() {
        with(hour_picker) {
            minValue = if (is24Hour) MIN_HOURS_24_CLOCK else MIN_HOURS_12_CLOCK
            maxValue = if (is24Hour) MAX_HOURS_24_CLOCK else MAX_HOURS_12_CLOCK
            virtualIncrementButtonDescription = resources.getString(R.string.date_time_picker_accessibility_increment_hour_button)
            virtualDecrementButtonDescription = resources.getString(R.string.date_time_picker_accessibility_decrement_hour_button)
            virtualIncrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_next_hour_click_action)
            virtualDecrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_previous_hour_click_action)
            setOnValueChangedListener(this@TimePicker)
        }
    }

    private fun initMinutePicker() {
        with(minute_picker) {
            minValue = MIN_MINUTES
            maxValue = MAX_MINUTES
            virtualIncrementButtonDescription = resources.getString(R.string.date_time_picker_accessibility_increment_minute_button)
            virtualDecrementButtonDescription = resources.getString(R.string.date_time_picker_accessibility_decrement_minute_button)
            virtualIncrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_next_minute_click_action)
            virtualDecrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_previous_minute_click_action)
            setFormatter(NumberPicker.twoDigitFormatter)
            setOnValueChangedListener(this@TimePicker)
        }
    }

    private fun initPeriodPicker() {
        with(period_picker) {
            minValue = MIN_PERIOD
            maxValue = MAX_PERIOD
            displayedValues = DateStringUtils.amPmStrings
            visibility = if (is24Hour) View.GONE else View.VISIBLE
            virtualToggleDescription = resources.getString(R.string.date_time_picker_accessibility_period_toggle_button)
            virtualToggleClickActionAnnouncement = resources.getString(R.string.date_time_picker_accessibility_period_toggle_click_action)
            setOnValueChangedListener(this@TimePicker)
        }
    }

    private fun setDateTimePickerValues(showEndTime: Boolean, animate: Boolean) {
        val time = if (showEndTime) dateTime.plus(duration) else dateTime
        val today = LocalDate.now()
        val daysBetween = ChronoUnit.DAYS.between(today, time).toInt()
        val dateValue = daysBack + daysBetween
        val hourValue = getHour(time)
        val minuteValue = time.minute
        val ampmValue = if (time.hour < 12) 0 else 1

        if (animate) {
            date_picker.quicklyAnimateValueTo(dateValue)
            hour_picker.animateValueTo(hourValue)
            minute_picker.quicklyAnimateValueTo(minuteValue)
            if (!is24Hour)
                period_picker.animateValueTo(ampmValue)
        } else {
            date_picker.value = dateValue
            hour_picker.value = hourValue
            minute_picker.value = minuteValue
            if (!is24Hour)
                period_picker.value = ampmValue
        }

        lastSelectedHour = hourValue
        updateDateTimePickerHints()
    }

    private fun updateAmPmPeriod() {
        if (shouldToggleAmPmPeriod) {
            val periodValue = AmPmPeriod.values()[period_picker.value]
            val period = if (periodValue == AmPmPeriod.AM) AmPmPeriod.PM else AmPmPeriod.AM
            period_picker.animateValueTo(period.ordinal)
        }

        lastSelectedHour = hour_picker.value
    }

    // Date NumberPickers

    private val datePickerValue: ZonedDateTime
        get() = ZonedDateTime.now().withYear(year_picker.value).withMonth(month_picker.value).withDayOfMonth(day_picker.value)

    private fun initDateNumberPickers() {
        getTab(DateTimeRangeTab.START)?.setText(R.string.date_time_picker_start_date)
        getTab(DateTimeRangeTab.END)?.setText(R.string.date_time_picker_end_date)
        getTab(DateTimeRangeTab.START)?.contentDescription = resources.getString(R.string.date_picker_accessiblility_start_date)
        getTab(DateTimeRangeTab.END)?.contentDescription = resources.getString(R.string.date_picker_accessiblility_end_date)

        date_pickers.visibility = View.VISIBLE

        initMonthPicker()
        initDayPicker()
        initYearPicker()

        setNumberPickerGroupAccessibilityFocusChangeListener(date_pickers)
    }

    private fun initMonthPicker() {
        val months = DateFormatSymbols().months
        with(month_picker) {
            minValue = MIN_MONTHS
            maxValue = months.size
            displayedValues = months
            virtualIncrementButtonDescription = resources.getString(R.string.date_picker_accessibility_increment_month_button)
            virtualDecrementButtonDescription = resources.getString(R.string.date_picker_accessibility_decrement_month_button)
            virtualIncrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_next_month_click_action)
            virtualDecrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_previous_month_click_action)
            setOnValueChangedListener(this@TimePicker)
        }
    }

    private fun initDayPicker() {
        // set default min and max
        updateDaysPerMonth(ZonedDateTime.now())
        with(day_picker) {
            virtualIncrementButtonDescription = resources.getString(R.string.date_picker_accessibility_increment_day_button)
            virtualDecrementButtonDescription = resources.getString(R.string.date_picker_accessibility_decrement_day_button)
            virtualIncrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_next_day_click_action)
            virtualDecrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_previous_day_click_action)
            setOnValueChangedListener(this@TimePicker)
        }
    }

    private fun initYearPicker() {
        val today = LocalDate.now()
        with(year_picker) {
            minValue = today.minusMonths(MONTH_LIMIT).year
            maxValue = today.plusMonths(MONTH_LIMIT).year
            wrapSelectorWheel = false
            virtualIncrementButtonDescription = resources.getString(R.string.date_picker_accessibility_increment_year_button)
            virtualDecrementButtonDescription = resources.getString(R.string.date_picker_accessibility_decrement_year_button)
            virtualIncrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_next_year_click_action)
            virtualDecrementClickActionAnnouncement = resources.getString(R.string.date_picker_accessibility_previous_year_click_action)
            setOnValueChangedListener(this@TimePicker)
        }
    }

    private fun setDatePickerValues(showEndTime: Boolean, animate: Boolean) {
        val time = if (showEndTime) dateTime.plus(duration) else dateTime

        if (animate) {
            month_picker.quicklyAnimateValueTo(time.monthValue)
            year_picker.animateValueTo(time.year)
            day_picker.quicklyAnimateValueTo(time.dayOfMonth)
        } else {
            month_picker.value = time.monthValue
            year_picker.value = time.year
            day_picker.value = time.dayOfMonth
        }

        updateDaysPerMonth(time)
        updateDatePickerHints()
    }

    private fun updateDaysPerMonth(time: ZonedDateTime) {
        val time = if (selectedTab == DateTimeRangeTab.END) time.plus(duration) else time
        val yearMonth = YearMonth.of(time.year, time.month)
        with(day_picker) {
            minValue = MIN_DAYS
            maxValue = yearMonth.lengthOfMonth()
        }
    }

    // Accessibility

    private fun updateRangeContentDescription() {
        if (start_end_tabs.visibility == View.VISIBLE)
            when (pickerMode) {
                PickerMode.DATE -> date_pickers.contentDescription = getAccessibilityDescription(true)
                PickerMode.DATE_TIME -> date_time_pickers.contentDescription = getAccessibilityDescription(false)
            }
    }

    private fun getAccessibilityDescription(dateOnly: Boolean): String {
        val time = if (selectedTab === DateTimeRangeTab.END) dateTime.plus(duration) else dateTime
        if (dateOnly)
            return getSelectedValueString(DateStringUtils.formatMonthDayYear(context, time))

        val label = when (selectedTab) {
            DateTimeRangeTab.START -> context.getString(R.string.date_time_picker_start_time)
            DateTimeRangeTab.END -> context.getString(R.string.date_time_picker_end_time)
            else -> ""
        }

        val daysBetween = ChronoUnit.DAYS.between(LocalDate.now(), time).toInt()
        val dateValue = daysBack + daysBetween
        val dateDescription = getDate(dateValue, time)
        val timeDescription = DateStringUtils.formatAbbrevTime(context, time)
        return getSelectedValueString("$label $dateDescription $timeDescription")
    }

    private fun announceNumberPickerValue(picker: NumberPicker) {
        val timePeriod = when(picker.id) {
            R.id.date_picker -> getDate(date_picker.value, dateTimePickerValue)
            R.id.hour_picker -> "${getHour(dateTimePickerValue)}"
            R.id.minute_picker -> "${dateTimePickerValue.minute}"
            R.id.period_picker -> DateStringUtils.amPmStrings[period_picker.value]
            R.id.month_picker -> "${datePickerValue.month}"
            R.id.day_picker -> "${datePickerValue.dayOfMonth}"
            R.id.year_picker -> "${datePickerValue.year}"
            else -> ""
        }

        announceForAccessibility(resources.getString(R.string.date_time_picker_accessibility_selected_date, timePeriod))
    }

    private fun updateDateTimePickerHints() {
        setVirtualButtonHint(date_picker, getDate(date_picker.value, dateTimePickerValue))
        setVirtualButtonHint(hour_picker, "${getHour(dateTimePickerValue)}")
        setVirtualButtonHint(minute_picker, "${dateTimePickerValue.minute}")

        val periodValue = DateStringUtils.amPmStrings[period_picker.value]
        period_picker.virtualToggleHint = getSelectedValueString(periodValue)
    }

    private fun updateDatePickerHints() {
        setVirtualButtonHint(month_picker,"${datePickerValue.month}")
        setVirtualButtonHint(day_picker, "${datePickerValue.dayOfMonth}")
        setVirtualButtonHint(year_picker, "${datePickerValue.year}")
    }

    private fun setVirtualButtonHint(picker: NumberPicker, value: String) {
        picker.virtualIncrementHint = getSelectedValueString(value)
        picker.virtualDecrementHint = picker.virtualIncrementHint
    }

    private fun setNumberPickerGroupAccessibilityFocusChangeListener(numberPickerGroup: LinearLayout) {
        ViewCompat.setAccessibilityDelegate(numberPickerGroup, object : AccessibilityDelegateCompat() {
            override fun onPopulateAccessibilityEvent(host: View, event: AccessibilityEvent) {
                if (event.eventType == AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
                    shouldAnnounceHints = true
            }
        })
    }

    private fun getSelectedValueString(value: String): String =
        resources.getString(R.string.date_time_picker_accessibility_selected_date, value)

    private inner class DateFormatter(private val today: ZonedDateTime) : android.widget.NumberPicker.Formatter {
        override fun format(value: Int): String =
            getDate(value, today.plusDays((value - daysBack).toLong()))
    }

    interface OnTimeSlotSelectedListener {
        /**
         * Method called when a user selects a date time range
         * @param [timeSlot] the selected TimeSlot
         *
         */
        fun onTimeSlotSelected(timeSlot: TimeSlot)
    }
}

enum class DateTimeRangeTab {
    START, END, NONE
}