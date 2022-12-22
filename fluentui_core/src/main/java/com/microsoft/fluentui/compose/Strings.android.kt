package com.microsoft.fluentui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.microsoft.fluentui.core.R

@Composable
fun getString(string: Strings): String {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    //TODO Add Strings resource in core module & update String names.
    return when (string) {
        Strings.CloseSheet -> resources.getString(R.string.fluentui_close_sheet)
        Strings.Selected -> resources.getString(R.string.fluentui_selected)
        Strings.NotSelected -> resources.getString(R.string.fluentui_not_selected)
        Strings.Disabled -> resources.getString(R.string.fluentui_disabled)
        Strings.Enabled -> resources.getString(R.string.fluentui_enabled)
        Strings.Close -> resources.getString(R.string.fluentui_close)
        else -> ""
    }
}