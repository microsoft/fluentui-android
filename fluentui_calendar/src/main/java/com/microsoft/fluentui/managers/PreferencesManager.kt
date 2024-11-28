/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.managers

import android.content.Context
import java.time.DayOfWeek

/**
 * [PreferencesManager] helper methods dealing with device SharedPreferences
 */
object PreferencesManager {
    private const val PREF_NAME = "prefs"
    private const val PREF_KEY_WEEK_START = "weekStart"

    /**
     * Gets the local first day of the week
     */
    @JvmStatic
    fun getWeekStart(context: Context): DayOfWeek {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val weekStart = sharedPreferences.getInt(PREF_KEY_WEEK_START, DayOfWeek.SUNDAY.value)
        return DayOfWeek.of(weekStart)
    }
}
