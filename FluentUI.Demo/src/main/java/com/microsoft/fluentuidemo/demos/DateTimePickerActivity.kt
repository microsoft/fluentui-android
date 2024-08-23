/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.jakewharton.threetenabp.AndroidThreeTen
import com.microsoft.fluentui.datetimepicker.DateTimePicker
import com.microsoft.fluentui.datetimepicker.DateTimePickerDialog
import com.microsoft.fluentui.datetimepicker.DateTimePickerDialog.DateRangeMode
import com.microsoft.fluentui.datetimepicker.DateTimePickerDialog.Mode
import com.microsoft.fluentui.util.DateStringUtils
import com.microsoft.fluentui.util.accessibilityManager
import com.microsoft.fluentui.util.isAccessibilityEnabled
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityDateTimePickerBinding
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

class DateTimePickerActivity : DemoActivity(), DateTimePickerDialog.OnDateTimePickedListener {
    companion object {
        private const val TAG_DATE_PICKER = "datePicker"
        private const val TAG_DATE_TIME_PICKER = "dateTimePicker"
        private const val TAG_START_DATE_PICKER = "startDatePicker"
        private const val TAG_END_DATE_PICKER = "endDatePicker"
        private const val TAG_DATE_TIME_RANGE_PICKER = "dateTimeRangePicker"

        private const val DATE_TIME = "dateTime"
        private const val DURATION_DATE = "durationDate"
        private const val DURATION_DATE_TIME = "durationDateTime"
        private const val START_DATE = "startDate"
        private const val START_DATE_TIME = "startDateTime"

        private const val FRAGMENT_TAG = "fragmentTag"
        private const val SINGLE_MODE_TAG = "singleModeTag"

        private const val DIALOG_DATE_TIME = "dialogDateTime"
        private const val DIALOG_MODE = "dialogMode"
        private const val IS_DIALOG_SHOWING = "isDialogShowing"
    }

    enum class DatePickerType(
        val buttonId: Int,
        val tag: String,
        val mode: Mode,
        val dateRangeMode: DateRangeMode
    ) {
        DATE(R.id.date_picker_button, TAG_DATE_PICKER, Mode.DATE, DateRangeMode.NONE),
        DATE_TIME(
            R.id.date_time_picker_date_selected_button,
            TAG_DATE_TIME_PICKER,
            Mode.DATE_TIME,
            DateRangeMode.NONE
        ),
        TIME_DATE(
            R.id.date_time_picker_time_selected_button,
            TAG_DATE_TIME_PICKER,
            Mode.TIME_DATE,
            DateRangeMode.NONE
        ),
        START_DATE(
            R.id.date_range_start_button,
            TAG_START_DATE_PICKER,
            Mode.DATE,
            DateRangeMode.START
        ),
        END_DATE(R.id.date_range_end_button, TAG_END_DATE_PICKER, Mode.DATE, DateRangeMode.END),
        START_DATE_TIME(
            R.id.date_time_range_start_button,
            TAG_DATE_TIME_RANGE_PICKER,
            Mode.DATE_TIME,
            DateRangeMode.START
        )
    }

    private var dateTimePickerDialog: DateTimePickerDialog? = null
    private var isDialogShowing: Boolean = false

    private lateinit var dateTimeBinding: ActivityDateTimePickerBinding

    // Date and time
    private var dateTime: ZonedDateTime? = null
        set(value) {
            if (value == null)
                return
            field = value
            val tag = singleModeTag ?: return
            if (tag == TAG_DATE_PICKER)
                dateTimeBinding.dateTextView.text =
                    DateStringUtils.formatDateWithWeekDay(this, value)
            else
                dateTimeBinding.dateTextView.text = DateStringUtils.formatFullDateTime(this, value)
        }

    // Date range
    private var startDate: ZonedDateTime? = null
        set(value) {
            if (value == null)
                return
            field = value
            dateTimeBinding.startDateTextView.text =
                DateStringUtils.formatDateWithWeekDay(this, value)
        }
    private var durationDate: Duration = Duration.ZERO
        set(value) {
            val startDate = startDate ?: return
            field = value
            val endDate = startDate.plus(value)
            dateTimeBinding.endDateTextView.text =
                DateStringUtils.formatDateWithWeekDay(this, endDate)
        }

    // Date and time range
    private var startDateTime: ZonedDateTime? = null
        set(value) {
            if (value == null)
                return
            field = value
            dateTimeBinding.startDateTimeTextView.text =
                DateStringUtils.formatFullDateTime(this, value)
        }
    private var durationDateTime: Duration = Duration.ZERO
        set(value) {
            val startDateTime = startDateTime ?: return
            field = value
            val endDateTime = startDateTime.plus(value)
            dateTimeBinding.endDateTimeTextView.text =
                DateStringUtils.formatFullDateTime(this, endDateTime)
        }

    // Dialog date and time
    private var dialogDateTime: ZonedDateTime? = null
        set(value) {
            if (value == null)
                return
            field = value
            val mode = dialogMode ?: getDialogMode()
            if (mode == Mode.DATE_TIME)
                dateTimeBinding.dateTimePickerDialogDateTextView.text =
                    DateStringUtils.formatDateWithWeekDay(this@DateTimePickerActivity, value)
            else
                dateTimeBinding.dateTimePickerDialogDateTextView.text =
                    DateStringUtils.formatFullDateTime(this@DateTimePickerActivity, value)
        }

    private var dateRangeMode: DateRangeMode = DateRangeMode.NONE
    private var singleModeTag: String? = null
    private var fragmentTag: String? = null
        set(value) {
            if (dateRangeMode == DateRangeMode.NONE)
                singleModeTag = value

            field = value
        }

    private var dialogMode: Mode? = null

    init {
        // Initialization of ThreeTenABP required for ZoneDateTime
        AndroidThreeTen.init(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        dateTimeBinding = ActivityDateTimePickerBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        savedInstanceState?.let {
            fragmentTag = it.getString(FRAGMENT_TAG)
            dateTime = it.getSerializable(DATE_TIME) as? ZonedDateTime
            startDate = it.getSerializable(START_DATE) as? ZonedDateTime
            durationDate = it.getSerializable(DURATION_DATE) as Duration
            startDateTime = it.getSerializable(START_DATE_TIME) as? ZonedDateTime
            durationDateTime = it.getSerializable(DURATION_DATE_TIME) as Duration
            singleModeTag = it.getString(SINGLE_MODE_TAG)
            dialogMode = it.getSerializable(DIALOG_MODE) as? Mode
            dialogDateTime = it.getSerializable(DIALOG_DATE_TIME) as? ZonedDateTime
            isDialogShowing = savedInstanceState.getBoolean(IS_DIALOG_SHOWING)
        }

        // DateTimePickers
        DatePickerType.values().forEach { picker ->
            findViewById<Button>(picker.buttonId).setOnClickListener {
                createDateTimePicker(picker)
            }
        }

        // DateTimePickerDialog
        dateTimeBinding.dateTimePickerDialogButton.setOnClickListener {
            createDateTimePickerDialog()
        }

        updateButtonsForAccessibility(isAccessibilityEnabled)

        accessibilityManager.addAccessibilityStateChangeListener {
            updateButtonsForAccessibility(it)
        }
        if(isDialogShowing) {
            dateTimePickerDialog?.dismiss()
            createDateTimePickerDialog()
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(DATE_TIME, dateTime)
        outState.putSerializable(DURATION_DATE, durationDate)
        outState.putSerializable(DURATION_DATE_TIME, durationDateTime)
        outState.putSerializable(START_DATE, startDate)
        outState.putSerializable(START_DATE_TIME, startDateTime)
        outState.putString(SINGLE_MODE_TAG, singleModeTag)
        outState.putString(FRAGMENT_TAG, fragmentTag)
        outState.putSerializable(DIALOG_MODE, dialogMode)
        outState.putSerializable(DIALOG_DATE_TIME, dialogDateTime)
        outState.putBoolean(IS_DIALOG_SHOWING, dateTimePickerDialog?.isShowing ?: false)
    }

    override fun onDestroy() {
        super.onDestroy()
        dateTimePickerDialog?.dismiss()
        dateTimePickerDialog = null
    }

    // DateTimePicker

    override fun onDateTimePicked(dateTime: ZonedDateTime, duration: Duration) {
        when (fragmentTag) {
            TAG_DATE_PICKER, TAG_DATE_TIME_PICKER -> {
                this.dateTime = dateTime
            }
            TAG_START_DATE_PICKER, TAG_END_DATE_PICKER -> {
                startDate = dateTime
                durationDate = duration
            }
            TAG_DATE_TIME_RANGE_PICKER -> {
                startDateTime = dateTime
                durationDateTime = duration
            }
        }
    }

    private fun createDateTimePicker(picker: DatePickerType) {
        dateRangeMode = picker.dateRangeMode
        fragmentTag = picker.tag
        val dateTimePicker = DateTimePicker.newInstance(
            this,
            picker.mode,
            picker.dateRangeMode,
            getFragmentDateTime(),
            getFragmentDuration()
        )
        dateTimePickerDialog?.onDateTimePickedListener = this
        dateTimePicker.show(supportFragmentManager, picker.tag)
    }

    // DateTimePickerDialog

    private fun createDateTimePickerDialog() {
        dateTimePickerDialog = DateTimePickerDialog(
            this,
            dialogMode ?: Mode.DATE_TIME,
            DateRangeMode.NONE,
            dialogDateTime ?: ZonedDateTime.now(),
            Duration.ZERO
        )

        dateTimePickerDialog?.onDateTimePickedListener =
            object : DateTimePickerDialog.OnDateTimePickedListener {
                override fun onDateTimePicked(dateTime: ZonedDateTime, duration: Duration) {
                    dialogMode = getDialogMode()
                    dialogDateTime = dateTime
                }
            }
        dateTimePickerDialog?.onDateTimeSelectedListener =
            object : DateTimePickerDialog.OnDateTimeSelectedListener{
                override fun onDateTimeSelected(dateTime: ZonedDateTime, duration: Duration) {
                    dialogMode = getDialogMode()
                    dialogDateTime = dateTime
                }
            }

        dateTimePickerDialog?.show()
    }

    // Accessibility

    private fun updateButtonsForAccessibility(accessibilityEnabled: Boolean) {
        if (accessibilityEnabled) {
            dateTimeBinding.dateTimePickerTimeSelectedButton.visibility = View.GONE
            dateTimeBinding.dateTimePickerDateSelectedButton.setText(R.string.date_time_picker_date_time_button)
        } else {
            dateTimeBinding.dateTimePickerTimeSelectedButton.visibility = View.VISIBLE
            dateTimeBinding.dateTimePickerDateSelectedButton.setText(R.string.date_time_picker_calendar_date_time_button)
        }
    }

    // Helpers

    private fun getFragmentDateTime(): ZonedDateTime =
        when (fragmentTag) {
            TAG_DATE_PICKER, TAG_DATE_TIME_PICKER -> dateTime ?: ZonedDateTime.now()
            TAG_START_DATE_PICKER, TAG_END_DATE_PICKER -> startDate ?: ZonedDateTime.now()
            TAG_DATE_TIME_RANGE_PICKER -> startDateTime ?: ZonedDateTime.now()
            else -> throw IllegalStateException("dialogTag expected")
        }

    private fun getFragmentDuration(): Duration =
        when (fragmentTag) {
            TAG_START_DATE_PICKER, TAG_END_DATE_PICKER -> durationDate
            TAG_DATE_TIME_RANGE_PICKER -> durationDateTime
            else -> Duration.ZERO
        }

    private fun getDialogMode(): Mode? {
        val dialog = dateTimePickerDialog ?: return null
        return if (dialog.pickerTab == DateTimePickerDialog.PickerTab.CALENDAR_VIEW)
            Mode.DATE_TIME
        else
            Mode.TIME_DATE
    }
}