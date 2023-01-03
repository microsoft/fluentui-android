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
        Strings.Search -> resources.getString(R.string.fluentui_search)
        Strings.Microphone -> resources.getString(R.string.fluentui_microphone)
        Strings.ClearText ->  resources.getString(R.string.fluentui_clear_text)
        Strings.Back ->  resources.getString(R.string.fluentui_back)
        Strings.Activated -> resources.getString(R.string.fluentui_activated)
        Strings.DeActivated -> resources.getString(R.string.fluentui_deactivated)
        Strings.Neutral -> resources.getString(R.string.fluentui_neutral)
        Strings.Brand -> resources.getString(R.string.fluentui_brand)
        Strings.Chevron -> resources.getString(R.string.fluentui_chevron)
        else -> ""
    }
}