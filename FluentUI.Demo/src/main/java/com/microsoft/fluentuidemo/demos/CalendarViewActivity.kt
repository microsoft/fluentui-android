/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View.TEXT_ALIGNMENT_TEXT_START
import com.microsoft.fluentui.calendar.OnDateSelectedListener
import com.microsoft.fluentui.util.DateStringUtils
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.databinding.ActivityCalendarViewBinding
import java.time.Duration
import java.time.ZonedDateTime

class CalendarViewActivity : DemoActivity() {
    companion object {
        private const val DATE = "date"
    }


    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private var savedDate: ZonedDateTime? = null

    private lateinit var calenderBinding: ActivityCalendarViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        calenderBinding = ActivityCalendarViewBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        calenderBinding.calendarView.onDateSelectedListener = object : OnDateSelectedListener {
            override fun onDateSelected(date: ZonedDateTime) {
                setExampleDate(date)
            }
        }

        (savedInstanceState?.getSerializable(DATE) as? ZonedDateTime)?.let {
            setExampleDate(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(DATE, savedDate)
    }

    private fun setExampleDate(date: ZonedDateTime) {
        savedDate = date
        calenderBinding.exampleDateTitle.text = DateStringUtils.formatDateWithWeekDay(this, date)
        calenderBinding.calendarView.setSelectedDateRange(date.toLocalDate(), Duration.ZERO, false)
    }

    /**
     * This function allows the user to leave the calendar focus by pressing the tab key.
     * @param  keyCode The value in event.getKeyCode().
     * @param  event The KeyEvent object.
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_TAB  && calenderBinding.calendarView.hasFocus()) {
            // Find the currently focused view
            val focusedView = currentFocus
            // Remove focus from the currently focused view
            focusedView?.clearFocus()

            return true // Consume the event
        }
        return super.onKeyDown(keyCode, event)
    }

}