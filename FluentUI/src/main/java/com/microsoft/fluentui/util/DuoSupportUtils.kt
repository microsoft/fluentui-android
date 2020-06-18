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
    var isDeviceSurfaceDuo: Boolean = false
    var isDualScreenMode: Boolean = false
    var rotation: Int = 0
    var hinge: Rect? = null
    var screenRect: List<Rect>? = null
    var isDeviceHorizontal: Boolean = true

    private val currentActivity: Activity

    constructor(activity: Activity) {
        currentActivity = activity
        loadDuoSupport()
    }

    private fun loadDuoSupport() {
        if (ScreenHelper.isDeviceSurfaceDuo(currentActivity)) {
            isDeviceSurfaceDuo = true
            isDualScreenMode = ScreenHelper.isDualMode(currentActivity)
            rotation = ScreenHelper.getCurrentRotation(currentActivity)
            hinge = ScreenHelper.getHinge(currentActivity)
            screenRect = ScreenHelper.getScreenRectangles(currentActivity)
            checkDeviceHorizontal()
        }
    }

    private fun checkDeviceHorizontal(): Boolean = (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)

    private fun getRect(view: View): Rect {
        val screenPos = IntArray(2)
        view.getLocationOnScreen(screenPos)
        return Rect(screenPos[0], screenPos[1], view.width, view.height)
    }

    /**
     * Use [moreOnLeft] to check if a given [View] or [Rect] is more to the left of the Surface Duo hinge or is more to the right.
     * @return true if the View or Rect is more on left of the hinge, false otherwise. (false implies more to the right of the hinge)
     */
    fun moreOnLeft(anchorRect: Rect) = (hinge!!.left - anchorRect.left) >= (anchorRect.right - hinge!!.right)
    fun moreOnLeft(anchor: View) = moreOnLeft(getRect(anchor))

    /**
     * Use [intersectHinge] to check if a given [View] or [Rect] intersects with the Surface Duo hinge
     * @return true if the View or Rect intersects with the hinge, false otherwise.
     */
    fun intersectHinge(anchorRect: Rect) = hinge!!.intersect(anchorRect)
    fun intersectHinge(anchor: View) = intersectHinge(getRect(anchor))
}