/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.tooltip

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.support.v4.view.AccessibilityDelegateCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.microsoft.device.dualscreen.layout.ScreenHelper
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.*
import kotlinx.android.synthetic.main.view_tooltip.view.*

/**
 * Tooltips contain brief helper text shown next to a view that anchors it.
 * [Tooltip] exists within a PopUpWindow and appears above or below its anchor view.
 * Its maximum width is 196dp after which the text wraps.
 */
class Tooltip {
    enum class TouchDismissLocation {
        ANYWHERE,
        INSIDE
    }

    var onDismissListener: OnDismissListener? = null

    val isShowing: Boolean
        get() = popupWindow.isShowing

    private val context: Context
    private val popupWindow: PopupWindow
    private val tooltipView: View
    private val textView: TextView
    private val arrowUpView: ImageView
    private val arrowDownView: ImageView
    private val arrowLeftView: ImageView
    private val arrowRightView: ImageView

    private var isAboveAnchor: Boolean = false
    private var isSideAnchor: Boolean = false

    private val margin: Int
    private var positionX: Int = 0
    private var positionY: Int = 0

    private var contentWidth: Int = 0
    private var contentHeight: Int = 0
    private var isDeviceSurfaceDuo: Boolean = false
    private var isDualScreenMode: Boolean = false
    private var rotation: Int = 0
    private var hinge: Rect? = null
    private var screenRect: List<Rect>? = null

    constructor(context: Context) {
        this.context = context

        // Need the theme wrapper to avoid crashing in Dark theme.
        // TODO Change to inflate(R.layout.view_tooltip, parent, false) and refactor dismiss inside listener accordingly.
        tooltipView = LayoutInflater.from(FluentUIContextThemeWrapper(context)).inflate(R.layout.view_tooltip, null)
        textView = tooltipView.tooltip_text
        arrowUpView = tooltipView.tooltip_arrow_up
        arrowDownView = tooltipView.tooltip_arrow_down
        arrowLeftView = tooltipView.tooltip_arrow_left
        arrowRightView = tooltipView.tooltip_arrow_right

        margin = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_margin)

        popupWindow = PopupWindow(context).apply {
            isClippingEnabled = true
            isFocusable = context.isAccessibilityEnabled
            isOutsideTouchable = true
            width = context.displaySize.x
            height = context.displaySize.y
            contentView = tooltipView
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun loadDuoSupport(){
        val currentActivity = context.activity as Activity
        if(ScreenHelper.isDeviceSurfaceDuo(currentActivity)){
            isDeviceSurfaceDuo = true
            isDualScreenMode = ScreenHelper.isDualMode(currentActivity)
            rotation = ScreenHelper.getCurrentRotation(currentActivity)
            hinge = ScreenHelper.getHinge(currentActivity)
            screenRect = ScreenHelper.getScreenRectangles(currentActivity)
        }
    }

    private fun isDeviceHorizontal(): Boolean{
        val horizantalMode = rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180
        return isDualScreenMode && horizantalMode
    }

    private fun moreOnLeft(anchor: Rect): Boolean = (hinge!!.left - anchor.left) >= (anchor.right - hinge!!.right)

    private fun hideAllArrows(){
        arrowUpView.visibility = View.GONE
        arrowDownView.visibility = View.GONE
        arrowLeftView.visibility = View.GONE
        arrowRightView.visibility = View.GONE
    }
    /**
     * Shows the tooltip
     * @param config the configuration of the tooltip
     */
    fun show(anchor: View, text: String, config: Config = Config()): Tooltip {
        if (!(anchor.isAttachedToWindow && anchor.isVisibleOnScreen))
            return this

        initTextView(text)

        // Get location of anchor view on screen
        val screenPos = IntArray(2)
        anchor.getLocationOnScreen(screenPos)
        // Get rect for anchor view
        val anchorRect = Rect(screenPos[0], screenPos[1], screenPos[0] + anchor.width, screenPos[1] + anchor.height)

        measureContentSize()
        loadDuoSupport()

        setPositionX(anchorRect.centerX(), if (anchor.layoutIsRtl) -config.offsetX else config.offsetX)
        setPositionY(anchorRect, config.offsetY, config.touchDismissLocation)

        if (isDualScreenMode && isDeviceHorizontal()){
            if (anchorRect.intersect(hinge!!)){
                if(moreOnLeft(anchorRect))
                    positionX -= contentWidth / 2 + 44
                else
                    positionX += contentWidth / 2 + 44
            }
        }

        initTooltipArrow(anchorRect, anchor.layoutIsRtl, config.offsetX)

        if (config.touchDismissLocation == TouchDismissLocation.INSIDE) {
            tooltipView.x = if (anchor.layoutIsRtl) resetPositionXForRtl() else positionX.toFloat()
            tooltipView.y = positionY.toFloat()
            anchor.post { popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, 0) }
        } else {
            popupWindow.width = contentWidth
            popupWindow.height = contentHeight
            anchor.post { popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, positionX, positionY)}
        }

        return this
    }

    fun dismiss() {
        tooltipView.announceForAccessibility(context.getString(R.string.tooltip_accessibility_dismiss_announcement))
        popupWindow.dismiss()
        onDismissListener?.onDismiss()
    }

    private fun measureContentSize() {
        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_max_width),
            View.MeasureSpec.AT_MOST
        )
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        tooltipView.measure(widthMeasureSpec, heightMeasureSpec)
        contentWidth = tooltipView.measuredWidth
        contentHeight = tooltipView.measuredHeight
    }

    private fun setPositionX(anchorCenter: Int, offsetX: Int) {
        val displayWidth = context.displaySize.x
        positionX = if (anchorCenter < displayWidth ) anchorCenter - contentWidth / 2 + offsetX
                    else anchorCenter - contentWidth / 2 + offsetX - displayWidth - 84

        // Navigation Bar in Nougat+ can appear on the left on phones at 270 rotation and adds
        // its height to the left of the display creating an offset that needs to be corrected to get
        // accurate horizontal position. Note that the soft navigation bar check returns false in emulators.
        val softNavBarOffsetX = context.softNavBarOffsetX
        if (positionX + contentWidth + margin + context.softNavBarOffsetX > displayWidth)
            positionX = displayWidth - contentWidth - margin + softNavBarOffsetX
        else if (positionX < context.softNavBarOffsetX + margin)
            positionX = margin + softNavBarOffsetX
    }

    // We manually convert position x for Rtl as the space inside the popup window places the 0 on the right
    private fun resetPositionXForRtl(): Float = contentWidth + positionX.toFloat()- context.displaySize.x.toFloat()

    private fun setPositionY(anchor: Rect, offsetY: Int, dismissLocation: TouchDismissLocation) {
        val displayHeight = context.displaySize.y
        val secondScreen = anchor.bottom > displayHeight
        positionY = if (secondScreen) anchor.bottom - displayHeight - 84
                    else anchor.bottom
        isAboveAnchor = if (isDeviceSurfaceDuo) positionY + contentHeight + margin > displayHeight
                        else positionY + contentHeight + margin - context.statusBarHeight > displayHeight
        if (isAboveAnchor) {
            positionY = if(secondScreen) anchor.top - contentHeight - offsetY - displayHeight - 84
                        else anchor.top - contentHeight - offsetY
        }

        if (dismissLocation == TouchDismissLocation.INSIDE)
            positionY -= if(secondScreen) 0 else context.statusBarHeight
    }

    private fun initTextView(text: String) {
        textView.text = text

        textView.setOnClickListener { dismiss() }

        ViewCompat.setAccessibilityDelegate(textView, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                val clickAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    AccessibilityNodeInfoCompat.ACTION_CLICK,
                    context.resources.getString(R.string.tooltip_accessibility_dismiss_action)
                )
                info.addAction(clickAction)
            }
        })
    }

    private fun initTooltipArrow(anchorRect: Rect, isRTL: Boolean, offsetX: Int) {
        hideAllArrows()
        val toolTipArrow: View =
            if (isAboveAnchor && !isSideAnchor) arrowDownView
            else if (!isAboveAnchor && !isSideAnchor) arrowUpView
            else if (isAboveAnchor) arrowLeftView
            else arrowRightView
        toolTipArrow.visibility = View.VISIBLE

        val displayWidth = context.displaySize.x
        val anchorCenterX = if (anchorRect.centerX() > displayWidth) anchorRect.centerX() - displayWidth - 84
                                  else anchorRect.centerX()


        val tooltipArrowWidth = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_arrow_height)
        // In RTL scenario "x" axis is still left to right with 0 being at the left most edge of the display.
        // The offset calculation places the tooltip arrow in the correct position in reference to its anchor.
        val offset = if (isRTL)
            positionX + contentWidth - anchorCenterX - tooltipArrowWidth
        else if(isDualScreenMode && anchorRect.intersect(hinge!!)) {
            if(moreOnLeft(anchorRect))
                anchorCenterX - positionX - tooltipArrowWidth - contentWidth / 4
            else
                anchorCenterX - positionX - tooltipArrowWidth - contentWidth / 4
        }
        else
            (anchorCenterX - positionX - tooltipArrowWidth)

        val layoutParams = toolTipArrow.getLayoutParams() as LinearLayout.LayoutParams
        layoutParams.gravity = Gravity.START
        layoutParams.marginStart = offset + offsetX
    }

    data class Config(var offsetX: Int = 0, var offsetY: Int = 0, var touchDismissLocation: TouchDismissLocation = TouchDismissLocation.ANYWHERE)

    interface OnDismissListener {
        fun onDismiss()
    }
}
