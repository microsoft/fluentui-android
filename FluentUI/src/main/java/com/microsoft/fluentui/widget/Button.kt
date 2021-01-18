/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.widget

import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import com.microsoft.fluentui.core.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

/**
 * Use this [Button] to get access to the FluentUI theme attribute default values via [FluentUIContextThemeWrapper]
 * without needing to extend Theme.FluentUI in your app's theme.
 */
class Button : AppCompatButton {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.attr.buttonStyle)
            : super(FluentUIContextThemeWrapper(context), attrs, defStyleAttr)
}