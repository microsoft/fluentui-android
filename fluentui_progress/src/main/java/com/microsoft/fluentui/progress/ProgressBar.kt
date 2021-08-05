/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.progress

import android.content.Context
import android.util.AttributeSet
import android.widget.ProgressBar
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

/**
 * Use this [ProgressBar] to get access to the FluentUI theme attribute default values via [FluentUIContextThemeWrapper]
 * without needing to extend Theme.FluentUI in your app's theme.
 */
class ProgressBar : ProgressBar {
    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0)
        : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Progress), attrs, defStyleAttr, defStyleRes)
}