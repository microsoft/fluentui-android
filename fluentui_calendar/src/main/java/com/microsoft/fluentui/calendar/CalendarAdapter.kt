/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.calendar

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.collection.SimpleArrayMap
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.microsoft.fluentui.calendar.CalendarDaySelectionDrawable.Mode
import com.microsoft.fluentui.managers.PreferencesManager
import com.microsoft.fluentui.util.DateTimeUtils
import java.lang.StringBuilder
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit

/**
 * [CalendarAdapter] is the adapter for the [CalendarView]
 */
internal class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarDayViewHolder>, View.OnClickListener {
    companion object {
        private const val MONTH_LIMIT = 1200L
        private val DAY_IN_SECONDS = TimeUnit.DAYS.toSeconds(1).toInt()
    }

    /**
     * @return [LocalDate] the earliest date displayed
     */
    var minDate: LocalDate
        private set

    /**
     * @return [LocalDate] the selected date
     */
    var selectedDate: LocalDate? = null
        private set

    /**
     * @return [Int] the today's position
     */
    val todayPosition: Int
        get() = ChronoUnit.DAYS.between(minDate, ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)).toInt() + 1

    private val LocalDate.getLocalDateToZonedDateTime: ZonedDateTime
        get() = ZonedDateTime.of(this, LocalTime.MIDNIGHT, ZoneId.systemDefault())

    private val firstDayOfWeekIndices = SimpleArrayMap<DayOfWeek, Int>(DayOfWeek.values().size)
    private val lastDayOfWeekIndices = SimpleArrayMap<DayOfWeek, Int>(DayOfWeek.values().size)

    private val context: Context
    private val config: CalendarView.Config
    private val onDateSelectedListener: OnDateSelectedListener
    private var selectedDuration: Duration? = null

    private val selectionDrawableCircle: CalendarDaySelectionDrawable
    private val selectionDrawableStart: CalendarDaySelectionDrawable
    private val selectionDrawableMiddle: CalendarDaySelectionDrawable
    private val selectionDrawableEnd: CalendarDaySelectionDrawable

    private val dayViewAccessibilityDelegate = DayViewAccessibilityDelegate()

    private var firstDayOfWeek: DayOfWeek? = null
    private var dayCount: Int
    private var viewHeight: Int = 0

    constructor(context: Context, config: CalendarView.Config, onDateSelectedListener: OnDateSelectedListener) {
        this.context = context
        this.config = config
        this.onDateSelectedListener = onDateSelectedListener

        selectionDrawableCircle = CalendarDaySelectionDrawable(this.context, Mode.SINGLE)
        selectionDrawableStart = CalendarDaySelectionDrawable(this.context, Mode.START)
        selectionDrawableMiddle = CalendarDaySelectionDrawable(this.context, Mode.MIDDLE)
        selectionDrawableEnd = CalendarDaySelectionDrawable(this.context, Mode.END)

        updateDayIndicesAndHeading()

        val today = LocalDate.now()
        minDate = today.minusMonths(MONTH_LIMIT)
        minDate = minDate.minusDays(firstDayOfWeekIndices.get(minDate.dayOfWeek)!!.toLong())

        var maxDate = today.plusMonths(MONTH_LIMIT)
        maxDate = maxDate.plusDays(lastDayOfWeekIndices.get(maxDate.dayOfWeek)!!.toLong())

        dayCount = ChronoUnit.DAYS.between(minDate, maxDate).toInt() + 1
    }

    /**
     * Sets the selected date range
     */
    fun setSelectedDateRange(date: LocalDate?, duration: Duration) {
        if (selectedDate != null && selectedDuration != null && selectedDate == date && selectedDuration == duration)
            return

        val previousSelectedDate = selectedDate
        val previousSelectedDuration = selectedDuration

        selectedDate = date
        selectedDuration = duration

        if (date == null) {
            notifyDataSetChanged()
            return
        }

        val selectedDatePosition = ChronoUnit.DAYS.between(minDate, selectedDate).toInt()
        val selectedDateCount = (duration.seconds / DAY_IN_SECONDS).toInt() + 1
        notifyItemRangeChanged(selectedDatePosition, selectedDateCount)

        if (previousSelectedDuration != null && previousSelectedDate != null) {
            val datePosition = ChronoUnit.DAYS.between(minDate, previousSelectedDate).toInt()
            val dateCount = (previousSelectedDuration.seconds / DAY_IN_SECONDS).toInt() + 1
            notifyItemRangeChanged(datePosition, dateCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayViewHolder {
        val dayView = CalendarDayView(parent.context, config)
        dayView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, viewHeight)
        dayView.setOnClickListener(this)
        ViewCompat.setAccessibilityDelegate(dayView, dayViewAccessibilityDelegate)
        return CalendarDayViewHolder(dayView)
    }

    override fun onBindViewHolder(holder: CalendarDayViewHolder, position: Int) {
        val date = minDate.plusDays(position.toLong())
        holder.date = date

        val selectedDate = selectedDate ?: return
        val selectedDuration = selectedDuration ?: return

        val selectedDateEnd = LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT).plus(selectedDuration).toLocalDate()
        holder.isSelected = DateTimeUtils.isBetween(date, selectedDate, selectedDateEnd)

        holder.selectedDrawable = when {
            date == null -> null
            date.isEqual(selectedDate) -> if (selectedDuration.toDays() < 1) selectionDrawableCircle else selectionDrawableStart
            date.isEqual(selectedDateEnd) -> selectionDrawableEnd
            else -> selectionDrawableMiddle
        }
    }

    override fun getItemCount() = dayCount

    override fun onClick(v: View) {
        onDateSelectedListener.onDateSelected((v as CalendarDayView).date.getLocalDateToZonedDateTime)
        v.announceForAccessibility(StringBuilder(v.contentDescription).append(" ").append(context.getString(R.string.calendar_adapter_accessibility_item_selected)))
    }

    private fun updateDayIndicesAndHeading() {
        val weekStart = PreferencesManager.getWeekStart(context)
        if (weekStart == firstDayOfWeek)
            return

        firstDayOfWeek = weekStart

        var dayOfWeek = weekStart
        var i = 0
        while (i < 7) {
            firstDayOfWeekIndices.put(dayOfWeek, i)
            lastDayOfWeekIndices.put(dayOfWeek, 6 - i)
            dayOfWeek = dayOfWeek.plus(1)
            ++i
        }
    }

    fun setViewHeight(viewHeight: Int) {
        this.viewHeight = viewHeight
    }

    /**
     * ViewHolder for the [CalendarDayView]
     */
    inner class CalendarDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /**
         * Sets and gets the selected date in the [CalendarDayView]
         */
        var date: LocalDate
            get() =  calendarDayView.date
            set(value) {  calendarDayView.date = value }

        /**
         * Sets and gets the selected Drawable in the [CalendarDayView]
         */
        var selectedDrawable: Drawable?
            get() =  calendarDayView.selectedDrawable
            set(value) { calendarDayView.selectedDrawable = value }

        /**
         * Sets and gets the selected state of the [CalendarDayView]
         */
        var isSelected: Boolean
            get() = calendarDayView.isChecked
            set(value) { calendarDayView.isChecked = value }

        private val calendarDayView = itemView as CalendarDayView
    }

    private inner class DayViewAccessibilityDelegate : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            info.addAction(
                AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    R.id.fluentui_calendar_view_action_goto_next_week,
                    host.resources.getString(R.string.accessibility_goto_next_week)
                )
            )
            info.addAction(
                AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    R.id.fluentui_calendar_view_action_goto_previous_week,
                    host.resources.getString(R.string.accessibility_goto_previous_week)
                )
            )
        }

        override fun performAccessibilityAction(host: View, action: Int, args: Bundle?): Boolean {
            val selectedDate = selectedDate ?: return super.performAccessibilityAction(host, action, args)
            val date: LocalDate = when(action) {
                R.id.fluentui_calendar_view_action_goto_next_week -> selectedDate.plusDays(7)
                R.id.fluentui_calendar_view_action_goto_previous_week -> selectedDate.minusDays(7)
                else -> return super.performAccessibilityAction(host, action, args)
            }

            onDateSelectedListener.onDateSelected(date.getLocalDateToZonedDateTime)
            return true
        }
    }
}
