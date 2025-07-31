package com.microsoft.fluentui.util

import android.content.res.Resources
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun pxToDp(value: Float) = (value / Resources
    .getSystem()
    .displayMetrics.density).dp

fun dpToPx(value: Dp) = (value * Resources
    .getSystem()
    .displayMetrics.density).value

@Composable
fun getStringResource(id: Int): String {
    return LocalContext.current.resources.getString(id)
}

@Composable
fun getStringResource(id: Int, vararg formatArgs: Any): String {
    return LocalContext.current.resources.getString(id, *formatArgs)
}

/**
 * A composable function that observes the visibility of the software keyboard and triggers
 * callbacks when the keyboard becomes visible or hidden.
 *
 * @param onKeyboardVisible A lambda function to be executed when the keyboard becomes visible.
 *                          Defaults to an empty lambda.
 * @param onKeyboardHidden A lambda function to be executed when the keyboard becomes hidden.
 *                         Defaults to an empty lambda.
 * @param content A composable content block to be displayed within this observer.
 */
@Composable
fun KeyboardVisibilityObserver(
    onKeyboardVisible: () -> Unit = {},
    onKeyboardHidden: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val imeInsets = WindowInsets.ime
    val density = LocalDensity.current
    val isKeyboardVisible by remember {
        derivedStateOf {
            imeInsets.getBottom(density) > 0
        }
    }

    LaunchedEffect(isKeyboardVisible) {
        if (isKeyboardVisible) {
            onKeyboardVisible()
        } else {
            onKeyboardHidden()
        }
    }
    content()
}