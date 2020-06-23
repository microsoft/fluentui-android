package com.microsoft.fluentui.util

import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.Surface
import android.view.View
import com.microsoft.device.dualscreen.layout.ScreenHelper

/**
 * [DuoSupportUtils] provides general variables and functions to provide support for Surface Duo Device.
 * It is based on Surface Duo SDKs ScreenHelper. It requires a reference to the current Activity to get
 * the required values.
 */
class DuoSupportUtils {
    val isDeviceSurfaceDuo: Boolean
        get() = ScreenHelper.isDeviceSurfaceDuo(currentActivity)
    val isDualScreenMode: Boolean
        get() = ScreenHelper.isDualMode(currentActivity) && isDeviceSurfaceDuo
    val rotation: Int
        get() = ScreenHelper.getCurrentRotation(currentActivity)
    val hinge: Rect?
        get() = ScreenHelper.getHinge(currentActivity)
    val screenRect: List<Rect>?
        get() = ScreenHelper.getScreenRectangles(currentActivity)
    val isDeviceHorizontal: Boolean
        get() = (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) && isDeviceSurfaceDuo

    private val currentActivity: Activity

    constructor(activity: Activity) {
        currentActivity = activity
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
    fun moreOnLeft(rect: Rect) = isDeviceHorizontal && ((hinge!!.left - rect.left) >= (rect.right - hinge!!.right))
    fun moreOnLeft(view: View) = moreOnLeft(getRect(view))


    /**
     * Use [moreOnTop] to check if a given [View] or [Rect] is more on the top of Surface Duo hinge or is more on the bottom.
     * @return true if the View or Rect
     */
    fun moreOnTop(rect: Rect) = !isDeviceHorizontal && ((hinge!!.top - rect.top) >= (rect.bottom - hinge!!.bottom))
    fun moreOnTop(view: View) = moreOnTop(getRect(view))

    /**
     * Use [intersectHinge] to check if a given [View] or [Rect] intersects with the Surface Duo hinge.
     * @return true if the View or Rect intersects with the hinge, false otherwise.
     */
    fun intersectHinge(anchorRect: Rect) = isDeviceSurfaceDuo && hinge!!.intersect(anchorRect)
    fun intersectHinge(anchor: View) = intersectHinge(getRect(anchor))
}