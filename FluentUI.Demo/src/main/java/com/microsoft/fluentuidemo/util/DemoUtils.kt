package com.microsoft.fluentuidemo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.microsoft.fluentuidemo.R

fun createBitmapFromLayout(view: View): Bitmap {
    view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
    val bitmap =
        Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    view.draw(canvas)
    return bitmap
}

fun invokeToast(string: String, context: Context, action: String? = null) {
    val message = if (action == null) {
        "$string ${context.resources.getString(R.string.common_clicked)}"
    } else {
        "$string $action"
    }
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}