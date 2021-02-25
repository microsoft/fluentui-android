package com.microsoft.fluentui.vNext

import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

/**
 * Use this [Button] to get access to the FluentUI theme attribute default values via [FluentUIContextThemeWrapper]
 * without needing to extend Theme.FluentUI in your app's theme.
 */
class Button : AppCompatButton {
    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.style.Widget_FluentUI_primarybuttontokensview_medium)
            : super(FluentUIContextThemeWrapper(context), attrs, defStyleAttr)
}