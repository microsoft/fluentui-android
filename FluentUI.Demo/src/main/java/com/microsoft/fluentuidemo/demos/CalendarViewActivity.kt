/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.View.TEXT_ALIGNMENT_TEXT_START
import com.microsoft.fluentui.calendar.OnDateSelectedListener
import com.microsoft.fluentui.util.DateStringUtils
import com.microsoft.fluentui.util.DuoSupportUtils
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_calendar_view.*
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

class CalendarViewActivity : DemoActivity() {
    companion object {
        private const val DATE = "date"
    }

    override val contentLayoutId: Int
        get() = R.layout.activity_calendar_view

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private var savedDate: ZonedDateTime? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (DuoSupportUtils.isDualScreenMode(this)) {
            example_date_title.textAlignment = TEXT_ALIGNMENT_TEXT_START
        }
        calendar_view.onDateSelectedListener = object : OnDateSelectedListener {
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
        example_date_title.text = DateStringUtils.formatDateWithWeekDay(this, date)
        calendar_view.setSelectedDateRange(date.toLocalDate(), Duration.ZERO, false)
    }
}