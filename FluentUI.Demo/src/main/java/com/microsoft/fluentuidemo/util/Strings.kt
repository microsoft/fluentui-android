package com.microsoft.fluentuidemo.util

import androidx.compose.runtime.Immutable

@Immutable
@JvmInline
value class DemoAppStrings private constructor(@Suppress("unused") private val value: Int) {
    companion object {
        val ModifiableParameters = DemoAppStrings(0)
        val MicrophoneCallback = DemoAppStrings(1)
        val AutoCorrect = DemoAppStrings(2)
        val Style = DemoAppStrings(3)
        val RightAccessoryView = DemoAppStrings(4)
        val MicrophonePressed = DemoAppStrings(5)
        val RightViewPressed = DemoAppStrings(6)
        val KeyboardSearchPressed = DemoAppStrings(7)
        val Presence = DemoAppStrings(8)
        val AppBarStyle = DemoAppStrings(9)
        val Subtitle = DemoAppStrings(10)
        val SearchBar = DemoAppStrings(11)
        val ButtonBar = DemoAppStrings(12)
    }
}
