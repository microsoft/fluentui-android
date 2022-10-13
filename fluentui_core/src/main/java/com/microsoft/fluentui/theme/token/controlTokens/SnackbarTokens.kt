package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import com.microsoft.fluentui.theme.token.ControlToken
import kotlinx.parcelize.Parcelize

enum class SnackbarDuration {
    SHORT,
    LONG,
    INDEFINITE;

    fun convertToMillis(): Long {
        return when (this) {
            INDEFINITE -> Long.MAX_VALUE
            LONG -> 10000L
            SHORT -> 4000L
        }
    }
}

@Parcelize
open class SnackbarTokens : ControlToken, Parcelable {

}