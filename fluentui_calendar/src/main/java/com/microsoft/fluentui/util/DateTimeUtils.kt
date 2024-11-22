/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.util

import java.time.DayOfWeek
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * [DateTimeUtils] contains helper methods for manipulating and parsing dates
 */
object DateTimeUtils {
    /**
     * It's unsafe to directly call DateTime.parse for a given date due to DST changes.
     * For example, Brazil changes time at midnight, which means there is 1 hour a year that's invalid.
     * It happens to be at midnight, so parsing just a date (say, 2015-10-18) will crash, because
     * there IS no midnight on 2015-10-18 in the America/Sao Paulo timezone. We detect those gaps
     * and do a local->UTC->local conversion to work around this.
     */
    @JvmStatic
    fun safelyParse(s: String, formatter: DateTimeFormatter): ZonedDateTime {
        val dtz: ZoneId
        try {
            dtz = ZoneId.systemDefault()
        } catch (e: IllegalArgumentException) {
            // We were unable to safely parse, try the default and hope for the best
            return ZonedDateTime.parse(s, formatter)
        }

        return safelyParse(s, formatter, dtz)
    }

    @JvmStatic
    fun safelyParse(s: String, formatter: DateTimeFormatter, dtz: ZoneId): ZonedDateTime {
        try {
            return ZonedDateTime.parse(s, formatter)
        } catch (e: DateTimeParseException) {
            // try again, with a time zone
        }

        try {
            return ZonedDateTime.parse(s, formatter.withZone(dtz))
        } catch (e: DateTimeParseException) {
            // try again, parsing just a date
        }

        // Apparently we have a lot of these... as it leads to blowing up for dates west of UTC
        if (s == "0000-00-00") {
            return ZonedDateTime.of(1, 1, 1, 1, 1, 1, 1, ZoneId.systemDefault())
        }

        // If this isn't a yyyy-mm-dd string, I don't know what else we are trying to do.
        val date = LocalDate.parse(s, formatter)
        return date.atStartOfDay(dtz)
    }

    @JvmStatic
    fun safelyParseMillis(s: String, formatter: DateTimeFormatter): Long =
        safelyParse(s, formatter).toInstant().toEpochMilli()

    @JvmStatic
    fun safelyParseMillis(s: String, formatter: DateTimeFormatter, dtz: ZoneId): Long =
        safelyParse(s, formatter, dtz).toInstant().toEpochMilli()

    @JvmStatic
    fun isToday(now: Long, `when`: Long): Boolean {
        val nowDate = Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault()).toLocalDate()
        val thenDate = Instant.ofEpochMilli(`when`).atZone(ZoneId.systemDefault()).toLocalDate()
        return nowDate == thenDate
    }

    @JvmStatic
    fun isTomorrow(now: Long, `when`: Long): Boolean {
        val nowDt = Instant.ofEpochMilli(now).atZone(ZoneId.systemDefault())
        val thenDt = Instant.ofEpochMilli(`when`).atZone(ZoneId.systemDefault())
        return isTomorrow(nowDt, thenDt)
    }

    @JvmStatic
    fun isTomorrow(now: ZonedDateTime, `when`: ZonedDateTime): Boolean =
        now.toLocalDate().plusDays(1) == `when`.toLocalDate()

    @JvmStatic
    fun isTomorrow(now: LocalDate, `when`: LocalDate): Boolean = now.plusDays(1) == `when`

    /**
     * @return true if @left and @right are the same year and the same day of year.
     */
    @JvmStatic
    fun isSameDay(left: ZonedDateTime, right: ZonedDateTime): Boolean =
        left.year == right.year && left.dayOfYear == right.dayOfYear

    /**
     * @return true if @left and @right are the same year and the same day of year.
     */
    @JvmStatic
    fun isSameDay(left: LocalDate, right: LocalDate): Boolean =
        isSameYear(left, right) && left.dayOfYear == right.dayOfYear

    @JvmStatic
    fun isSameDay(left: LocalDate?, right: ZonedDateTime): Boolean =
        if (left != null) {
            left.year == right.year && left.dayOfYear == right.dayOfYear
        } else {
            false
        }

    @JvmStatic
    fun isSameYear(left: LocalDate, right: LocalDate): Boolean = left.year == right.year

    @JvmStatic
    fun isBetween(dateTime: ZonedDateTime, start: ZonedDateTime, end: ZonedDateTime): Boolean =
        !dateTime.isBefore(start) && !dateTime.isAfter(end)

    @JvmStatic
    fun isSameDayOrBetween(dateTime: ZonedDateTime, start: ZonedDateTime, end: ZonedDateTime): Boolean =
        isSameDay(dateTime, start) || isSameDay(dateTime, end) || isBetween(dateTime, start, end)

    @JvmStatic
    fun isBetween(date: LocalDate, start: LocalDate, end: LocalDate): Boolean =
        !date.isBefore(start) && !date.isAfter(end)

    @JvmStatic
    fun roundToNextWeekend(date: LocalDate, firstDayOfWeek: DayOfWeek): LocalDate =
        date.plusDays(((firstDayOfWeek.value + 6 - date.dayOfWeek.value) % 7).toLong())

    @JvmStatic
    fun roundToLastWeekend(date: LocalDate, firstDayOfWeek: DayOfWeek): LocalDate =
        date.minusDays(((date.dayOfWeek.value + 7 - firstDayOfWeek.value) % 7).toLong())

    @JvmStatic
    fun getUtcTimeInMsForTtlInSeconds(ttlInSeconds: Long): Long =
        ttlInSeconds * 1000L + System.currentTimeMillis()

    /**
     * @return LocalDate of the first date of the localDate's Month
     */
    @JvmStatic
    fun getMonthStartDate(localDate: LocalDate): LocalDate =
        LocalDate.of(localDate.year, localDate.month, 1)

    /**
     * @return LocalDate of the last date of the localDate's Month
     */
    @JvmStatic
    fun getMonthEndDate(localDate: LocalDate): LocalDate =
        LocalDate.of(localDate.year, localDate.month, localDate.lengthOfMonth())

    /**
     * @return if date1 and date2 have same month and year.
     */
    @JvmStatic
    fun isSameMonthYear(date1: LocalDate, date2: LocalDate): Boolean =
        date1.monthValue == date2.monthValue && date1.year == date2.year

    /**
     * @return absolute number of days from day to the startDayOfWeek
     *
     * For example: If day = 25-June-2018 (dayOfWeek = Monday),
     * startDayOfWeek = Sunday  => noOfDays = 1
     * startDayOfWeek = Saturday  => noOfDays = 2
     */
    @JvmStatic
    fun getDaysFromStartOfWeek(day: LocalDate, startDayOfWeek: DayOfWeek): Int {
        var noOfDays = day.dayOfWeek.value - startDayOfWeek.value
        if (noOfDays < 0) {
            noOfDays += 7
        }
        return noOfDays
    }

    /**
     * @return if day is start dayOfWeek
     */
    @JvmStatic
    fun isStartDayOfWeek(startDayOfWeek: DayOfWeek, day: LocalDate): Boolean =
        startDayOfWeek.value == day.dayOfWeek.value
}

fun ZonedDateTime.getNumberOfDaysFrom(startDateTime: ZonedDateTime): Duration {
    // Reassigns instance's time to the startDateTime's time. This has the effect of normalizing the time and
    // is useful for getting whole days between ZonedDateTimes when a start time is later than an end time
    // ie. start date time: 1/1/2019 1pm and end date time is 1/2/2019 10am. Duration is less than 24
    // hours so the calendar will only represent one day; however, when the time is normalized, the
    // representation on a calendar will cover two days, 1/1/2019 and 1/2/2019.
    val normalizedDateTime = this.with(startDateTime.toLocalTime())
    return Duration.between(startDateTime, normalizedDateTime)
}