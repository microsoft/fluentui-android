package com.microsoft.fluentui.compose

import androidx.compose.runtime.Immutable

@Immutable
@JvmInline
value class Strings private constructor(@Suppress("unused") private val value: Int) {
    companion object {
        val CloseSheet = Strings(0)
        val Selected = Strings(1)
        val NotSelected = Strings(2)
        val Disabled = Strings(3)
        val Enabled = Strings(4)
        val Close = Strings(5)
        val Search = Strings(6)
        val Microphone = Strings(7)
        val ClearText = Strings(8)
        val Back = Strings(9)
    }
}
