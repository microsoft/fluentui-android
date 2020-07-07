package com.microsoft.fluentui.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import com.microsoft.device.dualscreen.layout.ScreenHelper
import java.lang.Exception

/**
 * [DuoSupportUtils] is helper object for Surface Duo Device Support
 */
object DuoSupportUtils {
    const val DUO_HINGE_WIDTH = 84

    @JvmStatic
    fun isDeviceSurfaceDuo(activity: Activity) = ScreenHelper.isDeviceSurfaceDuo(activity)

    @JvmStatic
    fun isDualScreenMode(activity: Activity) = ScreenHelper.isDualMode(activity)

    @JvmStatic
    fun getRotation(activity: Activity) = ScreenHelper.getCurrentRotation(activity)

    @JvmStatic
    fun getHinge(activity: Activity) = ScreenHelper.getHinge(activity)

    @JvmStatic
    fun getScreenRectangles(activity: Activity) = ScreenHelper.getScreenRectangles(activity)

    /**
     * Use [isWindowDoublePortrait] to check if the device is in landscape mode and app is spanned.
     */
    @JvmStatic
    fun isWindowDoublePortrait(context: Context): Boolean {
        if (context !is Activity) throw ActivityContextNotFoundException()
        return context.isLandscape && isDualScreenMode(context)
    }

    /**
     * Use [isWindowDoubleLandscape] to check if the device is in portrait mode and app is spanned.
     */
    @JvmStatic
    fun isWindowDoubleLandscape(context: Context): Boolean {
        if (context !is Activity) throw ActivityContextNotFoundException()
        return context.isPortrait && isDualScreenMode(context)
    }

    private fun getRect(view: View): Rect {
        val screenPos = IntArray(2)
        view.getLocationOnScreen(screenPos)
        return Rect(screenPos[0], screenPos[1], view.width, view.height)
    }

    /**
     * Use [moreOnLeft] to check if a given [View] or [Rect] is more to the left of the Surface Duo hinge or is more to the right.
     * @return true if the View or Rect is more on left of the hinge, false otherwise. (false implies more to the right of the hinge)
     */
    @JvmStatic
    fun moreOnLeft(context: Context, rect: Rect) = isWindowDoublePortrait(context) && ((getHinge(context as Activity)!!.left - rect.left) >= (rect.right - getHinge(context)!!.right))

    @JvmStatic
    fun moreOnLeft(context: Context, view: View) = moreOnLeft(context, getRect(view))


    /**
     * Use [moreOnTop] to check if a given [View] or [Rect] is more on the top of Surface Duo hinge or is more on the bottom.
     * @return true if the View or Rect is more on top of the hinge, false otherwise. (false implies more on the bottom of the hinge)
     */
    @JvmStatic
    fun moreOnTop(context: Context, rect: Rect) = isWindowDoubleLandscape(context) && ((getHinge(context as Activity)!!.top - rect.top) >= (rect.bottom - getHinge(context)!!.bottom))

    @JvmStatic
    fun moreOnTop(context: Context, view: View) = moreOnTop(context, getRect(view))

    /**
     * Use [intersectHinge] to check if a given [View] or [Rect] intersects with the Surface Duo hinge.
     * @return true if the View or Rect intersects with the hinge, false otherwise.
     */
    @JvmStatic
    fun intersectHinge(context: Context, anchorRect: Rect): Boolean {
        if (context !is Activity) throw ActivityContextNotFoundException()
        return isDeviceSurfaceDuo(context) && getHinge(context)!!.intersect(anchorRect)
    }

    @JvmStatic
    fun intersectHinge(context: Context, anchor: View) = intersectHinge(context, getRect(anchor))

    /**
     * Returns the width of hinge/display mask.
     */
    @JvmStatic
    fun getHingeWidth(context: Context): Int {
        if (context !is Activity) throw ActivityContextNotFoundException()
        if (!isDeviceSurfaceDuo(context)) return 0
        return if (context.isLandscape)
            getHinge(context)!!.width()
        else
            getHinge(context)!!.height()
    }

    /**
     * Use [getSingleScreenWidthPixels] to get the pixels of a single screen on Surface Duo device
     */
    @JvmStatic
    fun getSingleScreenWidthPixels(context: Context) = if (isWindowDoublePortrait(context)) (context.displaySize.x - getHingeWidth(context)) / 2 else context.displaySize.x

    /**
     * Exception thrown when the [Context] is not activity context.
     */
    class ActivityContextNotFoundException : Exception("Activity Context is required.")
}