/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.widget

import android.content.Context
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.AttributeSet
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

/**
 * Use this [BottomNavigationView] to get access to the FluentUI theme attribute default values via [FluentUIContextThemeWrapper]
 * without needing to extend Theme.FluentUI in your app's theme.
 */
class BottomNavigationView :
    BottomNavigationView {
    private var userIconSize = 0
    private var defaultIconSize = 0

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
        : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Components), attrs, defStyleAttr)

    override fun setLabelVisibilityMode(labelVisibilityMode: Int) {
        // The super call in the constructor runs setLabelVisibilityMode() before the constructor.
        // We capture the value for itemIconSize set by user during this call. If the value differs
        // from the default value of 24dp, the icon size set by user is used for icons. It remains
        // constant for labeled and unlabeled view.
        if(userIconSize == 0){
            // sets initial values for userIconSize and defaultIconSize
            defaultIconSize = adjustIconSize()
            userIconSize = this.itemIconSize
        }
        super.setLabelVisibilityMode(labelVisibilityMode)
        this.itemIconSize = adjustIconSize()
    }

    private fun adjustIconSize(): Int {
        // Icon Size Behavior
        // itemIconSize not set -> labeled = 24x24, unlabeled = 28x28.
        // itemIconSize is set  -> labeled, unlabeled = set value.
        if(userIconSize != defaultIconSize)
            return userIconSize
        else if(this.labelVisibilityMode == LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED)
            return context.resources.getDimensionPixelSize(R.dimen.fluentui_bottom_navigation_icon_unlabeled).toInt()
        else
            return context.resources.getDimensionPixelSize(R.dimen.fluentui_bottom_navigation_icon_labeled).toInt()
    }
}