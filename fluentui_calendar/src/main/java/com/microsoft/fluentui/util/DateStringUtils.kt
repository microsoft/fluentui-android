/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.util

import android.content.Context
import android.text.format.DateUtils.*
import com.microsoft.fluentui.calendar.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.TemporalAccessor
import java.util.*

/**
 * [DateStringUtils] is a helper object for formatting and dealing with time Strings
 */
object DateStringUtils {
    /**
     * @return an array of strings depending on 12 hour period
     */
    @JvmStatic
    val amPmStrings: Array<String>
        get() {
            val format = SimpleDateFormat("a")
            val calendar = GregorianCalendar.getInstance()

            calendar.set(Calendar.AM_PM, Calendar.AM)
            val am = format.format(calendar.time)
            calendar.set(Calendar.AM_PM, Calendar.PM)
            val pm = format.format(calendar.time)

            return arrayOf(am, pm)
        }

    /**
     * Formats a date with the weekday. It will auto-append the year if the date is from a different
     * year than now.
     *
     * Example:
     * - Sunday, January 3
     * - Sunday, January 3, 1982
     */
    @JvmStatic
    fun formatDateWithWeekDay(context: Context, date: TemporalAccessor): String = formatDateWithWeekDay(context, date.epochMillis)

    /**
     * @see .formatDateWithWeekDay
     */
    @JvmStatic
    fun formatDateWithWeekDay(context: Context, date: Long): String =
        formatDateTime(context, date,FORMAT_SHOW_DATE or FORMAT_SHOW_WEEKDAY)

    /**
     * Formats a date with the abbreviated weekday + month + day
     *
     * Example:
     * - Mon, Mar 9
     *
     * @param date Time to format
     */
    @JvmStatic
    fun formatDateAbbrevAll(context: Context, date: TemporalAccessor): String =
        formatDateAbbrevAll(context, date.epochMillis)

    /**
     * @see .formatDateAbbrevAll
     */
    @JvmStatic
    fun formatDateAbbrevAll(context: Context, time: Long): String =
        formatDateTime(context, time, FORMAT_ABBREV_ALL or FORMAT_SHOW_DATE or FORMAT_SHOW_WEEKDAY)

    /**
     * Formats the month day and year
     * Example:
     * - April 19
     * - April 19, 2020
     */
    @JvmStatic
    fun formatMonthDayYear(context: Context, date: TemporalAccessor): String =
        formatDateTime(context, date.epochMillis, 0)

    /**
     * Formats a date with the weekday + month + day + Time.  The year is optionally formatted if it
     * is not the current year.
     *
     * Example:
     * - Tuesday, November 25, 2014, 3:55AM
     * - Tuesday, March 3, 16:22
     *
     * @param time   Time to format (in millis since the epoch in UTC)
     */
    @JvmStatic
    fun formatFullDateTime(context: Context, time: Long): String =
        formatDateTime(context, time, FORMAT_SHOW_DATE or FORMAT_SHOW_WEEKDAY or FORMAT_SHOW_TIME)

    /**
     * @see .formatFullDateTime
     */
    @JvmStatic
    fun formatFullDateTime(context: Context, date: TemporalAccessor?): String =
        if (date == null) "" else formatFullDateTime(context, date.epochMillis)

    /**
     * Formats a date with the abbreviated weekday + month + day + year + ',' + time.
     *
     * Example:
     * - Tue, Nov 25, 2014, 3:55AM
     * - Tue, Mar 3, 2016, 16:22
     *
     * @param date Time to format
     */
    @JvmStatic
    fun formatAbbrevDateTime(context: Context, date: TemporalAccessor?): String =
        if (date == null) "" else formatAbbrevDateTime(context, date.epochMillis, R.string.date_time)

    /**
     * Formats a time.
     *
     * Example:
     * - 3:55AM
     * - 3PM
     * - 16:22
     *
     * @param dateTime Time to format
     */
    @JvmStatic
    fun formatAbbrevTime(context: Context, dateTime: TemporalAccessor): String =
        formatDateTime(context, dateTime.epochMillis, FORMAT_SHOW_TIME or FORMAT_ABBREV_TIME)

    /**
     * Formats a date with abbreviated Weekday + Date + Year
     *
     * Example:
     * - Fri, Mar 20, 2015
     *
     * @param  date Time to format
     */
    @JvmStatic
    fun formatWeekdayDateYearAbbrev(context: Context, date: TemporalAccessor): String =
        formatDateTime(
            context,
            date.epochMillis,
            FORMAT_ABBREV_WEEKDAY or FORMAT_ABBREV_MONTH or FORMAT_SHOW_WEEKDAY or FORMAT_SHOW_DATE or FORMAT_SHOW_YEAR
        )

    private fun formatAbbrevDateTime(context: Context, timestamp: Long, stringResource: Int): String {
        var flags = FORMAT_ABBREV_MONTH or FORMAT_ABBREV_WEEKDAY or FORMAT_SHOW_DATE or FORMAT_SHOW_WEEKDAY

        // Only show Year when it's not current year
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        calendar.timeInMillis = timestamp
        if (calendar.get(Calendar.YEAR) != currentYear)
            flags = flags or FORMAT_SHOW_YEAR

        val date = formatDateTime(context, timestamp, flags)
        val time = formatDateTime(context, timestamp, FORMAT_SHOW_TIME)

        return context.getString(stringResource, date, time)
    }

    /**
     * Converts date to the number of milliseconds from the epoch of 1970-01-01T00:00:00Z.
     */
    private val TemporalAccessor.epochMillis: Long
        get() = when (this) {
            is ZonedDateTime -> this.toInstant().toEpochMilli()
            is LocalDate -> this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            is LocalDateTime -> this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            else -> {
                throw Exception("Invalid date")
            }
        }
}
