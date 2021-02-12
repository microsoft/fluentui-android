package com.microsoft.fluentui.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.microsoft.device.dualscreen.layout.ScreenHelper
import java.lang.Exception

/**
 * [DuoSupportUtils] is helper object for Surface Duo Device Support
 */
object DuoSupportUtils {
    const val DUO_HINGE_WIDTH = 84
    const val COLUMNS_IN_START_DUO_MODE = 3
    const val COLUMNS_IN_END_DUO_MODE = 4

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
    fun isWindowDoublePortrait(activity: Activity): Boolean {
        return activity.isLandscape && isDualScreenMode(activity)
    }

    /**
     * Use [isWindowDoubleLandscape] to check if the device is in portrait mode and app is spanned.
     */
    @JvmStatic
    fun isWindowDoubleLandscape(activity: Activity): Boolean {
        return activity.isPortrait && isDualScreenMode(activity)
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
    fun moreOnLeft(activity: Activity, rect: Rect) = isWindowDoublePortrait(activity) && ((getHinge(activity)!!.left - rect.left) >= (rect.right - getHinge(activity)!!.right))

    @JvmStatic
    fun moreOnLeft(activity: Activity, view: View) = moreOnLeft(activity, getRect(view))


    /**
     * Use [moreOnTop] to check if a given [View] or [Rect] is more on the top of Surface Duo hinge or is more on the bottom.
     * @return true if the View or Rect is more on top of the hinge, false otherwise. (false implies more on the bottom of the hinge)
     */
    @JvmStatic
    fun moreOnTop(activity: Activity, rect: Rect) = isWindowDoubleLandscape(activity) && ((getHinge(activity)!!.top - rect.top) >= (rect.bottom - getHinge(activity)!!.bottom))

    @JvmStatic
    fun moreOnTop(activity: Activity, view: View) = moreOnTop(activity, getRect(view))

    /**
     * Use [intersectHinge] to check if a given [View] or [Rect] intersects with the Surface Duo hinge.
     * @return true if the View or Rect intersects with the hinge, false otherwise.
     */
    @JvmStatic
    fun intersectHinge(activity: Activity, anchorRect: Rect): Boolean {
        return isDeviceSurfaceDuo(activity) && getHinge(activity)!!.intersect(anchorRect)
    }

    @JvmStatic
    fun intersectHinge(activity: Activity, anchor: View) = intersectHinge(activity, getRect(anchor))

    /**
     * Returns the width of hinge/display mask.
     */
    @JvmStatic
    fun getHingeWidth(activity: Activity): Int {
        if (!isDeviceSurfaceDuo(activity)) return 0
        return if (activity.isLandscape)
            getHinge(activity)!!.width()
        else
            getHinge(activity)!!.height()
    }

    /**
     * Returns the width of hinge/display mask.
     */
    @JvmStatic
    fun getHalfScreenWidth(activity: Activity): Int {
        if (!isDeviceSurfaceDuo(activity)) return activity.baseContext.displaySize.x/2
        return (activity.baseContext.displaySize.x - getHingeWidth(activity))/2
    }

    fun getSpanSizeLookup(activity: Activity): GridLayoutManager.SpanSizeLookup {
        val span: Int = getHalfScreenWidth(activity) / COLUMNS_IN_START_DUO_MODE
        val spanMid: Int = getHalfScreenWidth(activity) / COLUMNS_IN_START_DUO_MODE + getHingeWidth(activity)
        val spanEnd: Int = getHalfScreenWidth(activity) / COLUMNS_IN_END_DUO_MODE

        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position % (COLUMNS_IN_START_DUO_MODE+ COLUMNS_IN_END_DUO_MODE) < 2) {
                    return span
                } else if (position % (COLUMNS_IN_START_DUO_MODE+ COLUMNS_IN_END_DUO_MODE) == 2) {
                    return spanMid
                } else {
                    return spanEnd
                }
            }
        }
    }

    /**
     * Use [getSingleScreenWidthPixels] to get the pixels of a single screen on Surface Duo device
     */
    @JvmStatic
    fun getSingleScreenWidthPixels(activity: Activity) = if (isWindowDoublePortrait(activity)) (activity.displaySize.x - getHingeWidth(activity)) / 2 else activity.displaySize.x

    /**
     * Exception thrown when the [Context] is not activity context.
     */
    class ActivityContextNotFoundException : Exception("Activity Context is required.")
}