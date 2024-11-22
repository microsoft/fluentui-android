/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.datetimepicker

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.microsoft.fluentui.datetimepicker.DateTimePickerDialog.*
import com.microsoft.fluentui.util.DateTimeUtils
import com.microsoft.fluentui.util.isAccessibilityEnabled
import java.time.Duration
import java.time.ZonedDateTime

/**
 * [DateTimePicker] houses a [DateTimePickerDialog] and provides state management for the dialog.
 */
class DateTimePicker : AppCompatDialogFragment(), OnDateTimeSelectedListener, OnDateTimePickedListener {
    companion object {
        private const val DISPLAY_MODE = "displayMode"
        private const val DATE_RANGE_MODE = "dateRangeMode"
        private const val DATE_TIME = "dateTime"
        private const val DURATION = "duration"

        fun newInstance(
            context: Context,
            mode: Mode,
            dateRangeMode: DateRangeMode = DateRangeMode.NONE,
            dateTime: ZonedDateTime = ZonedDateTime.now(),
            duration: Duration = Duration.ZERO
        ): DateTimePicker {
            val dateTimePicker = DateTimePicker()
            val bundle = Bundle()
            bundle.putSerializable(DISPLAY_MODE, getDisplayMode(context, dateTime, duration, mode))
            bundle.putSerializable(DATE_RANGE_MODE, dateRangeMode)
            bundle.putSerializable(DATE_TIME, dateTime)
            bundle.putSerializable(DURATION, duration)
            dateTimePicker.arguments = bundle
            return dateTimePicker
        }

        private fun getDisplayMode(context: Context, dateTime: ZonedDateTime, duration: Duration, mode: Mode): DisplayMode =
            if (context.isAccessibilityEnabled)  {
                mode.accessibleMode
            } else {
                val endTime = dateTime.plus(duration)
                val isSameDayEvent = DateTimeUtils.isSameDay(dateTime, endTime)
                if (isSameDayEvent || mode == Mode.DATE)
                    mode.defaultMode
                else
                    DisplayMode.TIME
            }
    }

    private lateinit var displayMode: DisplayMode
    private lateinit var dateRangeMode: DateRangeMode
    private lateinit var dateTime: ZonedDateTime
    private lateinit var duration: Duration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = savedInstanceState ?: arguments ?: return
        displayMode = bundle.getSerializable(DISPLAY_MODE) as DisplayMode
        dateRangeMode = bundle.getSerializable(DATE_RANGE_MODE) as DateRangeMode
        dateTime = bundle.getSerializable(DATE_TIME) as ZonedDateTime
        duration = bundle.getSerializable(DURATION) as Duration
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(DISPLAY_MODE, displayMode)
        outState.putSerializable(DATE_RANGE_MODE, getDateRangeMode())
        outState.putSerializable(DATE_TIME, dateTime)
        outState.putSerializable(DURATION, duration)
    }

    override fun onCreateDialog(bundle: Bundle?): Dialog {
        val dialog = DateTimePickerDialog(
            context as Context,
            displayMode,
            dateRangeMode,
            dateTime,
            duration
        )
        dialog.onDateTimeSelectedListener = this
        dialog.onDateTimePickedListener = this

        return dialog
    }

    override fun onDateTimeSelected(dateTime: ZonedDateTime, duration: Duration) {
        this.dateTime = dateTime
        this.duration = duration
    }

    override fun onDateTimePicked(dateTime: ZonedDateTime, duration: Duration) {
        (activity as? OnDateTimePickedListener)?.onDateTimePicked(dateTime, duration)
    }

    private fun getDateRangeMode(): DateRangeMode {
        val dialog = dialog as DateTimePickerDialog
        if (dateRangeMode != DateRangeMode.NONE)
            return if (dialog.dateTimeRangeTab == DateTimeRangeTab.START)
                DateRangeMode.START
            else
                DateRangeMode.END

        return DateRangeMode.NONE
    }
}