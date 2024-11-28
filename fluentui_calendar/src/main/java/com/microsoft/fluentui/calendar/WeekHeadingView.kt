/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.calendar

import android.content.Context
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.microsoft.fluentui.calendar.CalendarView.Companion.WEEK_MID
import com.microsoft.fluentui.managers.PreferencesManager
import com.microsoft.fluentui.util.activity
import java.time.DayOfWeek

/**
 * [WeekHeadingView] is a LinearLayout holding the [CalendarView] header with views for
 * the week day letters, S, M, T, W, T, F, S
 */
internal class WeekHeadingView : LinearLayout {
    private lateinit var config: CalendarView.Config

    constructor(context: Context, calendarConfig: CalendarView.Config) : super(context) {
        config = calendarConfig
        setBackgroundColor(config.weekHeadingBackgroundColor)

        var dayOfWeek = PreferencesManager.getWeekStart(context)

        val headingTextAppearance = R.style.TextAppearance_FluentUI_WeekDayHeader

        val weekDayHeadingColor = config.weekdayHeadingTextColor
        val weekendHeadingColor = config.weekendHeadingTextColor

        val strDayOfWeek = resources.getStringArray(R.array.weekday_initial)
        for (currentDay in 1..CalendarView.DAYS_IN_WEEK) {
            val textView = TextView(context)
            TextViewCompat.setTextAppearance(textView, headingTextAppearance)
            textView.text = strDayOfWeek[dayOfWeek.value - 1]

            if (DayOfWeek.SATURDAY == dayOfWeek || DayOfWeek.SUNDAY == dayOfWeek)
                textView.setTextColor(weekendHeadingColor)
            else
                textView.setTextColor(weekDayHeadingColor)

            textView.gravity = Gravity.CENTER
            post {
                context.activity?.let {
                    addView(textView, LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f))
                }
            }

            dayOfWeek = dayOfWeek.plus(1)
        }

        ViewCompat.setImportantForAccessibility(this, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS)
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec(config.weekHeadingHeight, View.MeasureSpec.EXACTLY)
        )
    }
}
