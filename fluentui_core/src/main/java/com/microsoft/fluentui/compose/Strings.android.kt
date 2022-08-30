package com.microsoft.fluentui.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.R
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

@Composable
fun getString(string: Strings): String {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    //TODO Add Strings resource in core module & update String names.
    return when (string) {
        Strings.NavigationMenu -> resources.getString(R.string.navigation_menu)
        Strings.CloseDrawer -> resources.getString(R.string.close_drawer)
        Strings.CloseSheet -> resources.getString(R.string.close_sheet)
        Strings.DefaultErrorMessage -> resources.getString(R.string.default_error_message)
        Strings.ExposedDropdownMenu -> resources.getString(R.string.dropdown_menu)
        Strings.SliderRangeStart -> resources.getString(R.string.range_start)
        Strings.SliderRangeEnd -> resources.getString(R.string.range_end)
        else -> ""
    }
}