package com.microsoft.fluentui.util

import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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