package com.microsoft.fluentuidemo.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.microsoft.fluentuidemo.R

@Composable
fun getDemoAppString(string: DemoAppStrings): String {
    LocalConfiguration.current
    val resources = LocalContext.current.resources
    //TODO Add Strings resource in core module & update String names.
    return when (string) {
        DemoAppStrings.ModifiableParameters -> resources.getString(R.string.app_modifiable_parameters)
        DemoAppStrings.MicrophoneCallback -> resources.getString(R.string.searchbar_microphone_callback)
        DemoAppStrings.Style -> resources.getString(R.string.app_style)
        DemoAppStrings.AutoCorrect -> resources.getString(R.string.searchbar_autocorrect)
        DemoAppStrings.RightAccessoryView -> resources.getString(R.string.app_right_accessory_view)
        DemoAppStrings.MicrophonePressed -> resources.getString(R.string.searchbar_microphone_pressed)
        DemoAppStrings.RightViewPressed -> resources.getString(R.string.searchbar_right_view_pressed)
        DemoAppStrings.KeyboardSearchPressed -> resources.getString(R.string.searchbar_keyboard_search_pressed)
        else -> ""
    }
}