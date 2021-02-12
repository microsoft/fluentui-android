/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.tooltip

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.support.v4.view.AccessibilityDelegateCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
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

    private val displayWidth: Int
    private val displayHeight: Int
    private var requireReadjustment = false
    private var requireReinit = false
    private lateinit var toolTipArrow: View

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

        displayWidth = context.displaySize.x
        displayHeight = context.displaySize.y
    }

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

        setPositionX(anchorRect.centerX(), if (anchor.layoutIsRtl) -config.offsetX else config.offsetX)
        setPositionY(anchorRect, config.offsetY, config.touchDismissLocation)

        initTooltipArrow(anchorRect, anchor.layoutIsRtl, config.offsetX)
        checkEdgeCase(anchorRect)
        hingeSupport(anchorRect, config.touchDismissLocation)
        if (requireReinit ) initTooltipArrow(anchorRect, anchor.layoutIsRtl, config.offsetX)
        if (requireReadjustment) readjustTooltip(anchorRect, anchor.layoutIsRtl, config)

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
        positionX = anchorCenter - contentWidth / 2 + offsetX

        // Duo Second Screen Support
        val secondScreen = anchorCenter > displayWidth
        if (secondScreen) positionX -= displayWidth + DuoSupportUtils.DUO_HINGE_WIDTH

        // Navigation Bar in Nougat+ can appear on the left on phones at 270 rotation and adds
        // its height to the left of the display creating an offset that needs to be corrected to get
        // accurate horizontal position. Note that the soft navigation bar check returns false in emulators.
        val softNavBarOffsetX = context.softNavBarOffsetX
        if (positionX + contentWidth + margin + softNavBarOffsetX > displayWidth)
            positionX = displayWidth - contentWidth - margin + softNavBarOffsetX
        else if (positionX < softNavBarOffsetX + margin)
            positionX = margin + softNavBarOffsetX
    }

    // We manually convert position x for Rtl as the space inside the popup window places the 0 on the right
    private fun resetPositionXForRtl(): Float = contentWidth + positionX.toFloat()- context.displaySize.x.toFloat()

    private fun setPositionY(anchor: Rect, offsetY: Int, dismissLocation: TouchDismissLocation) {
        positionY = anchor.bottom

        // Duo Second Screen Support
        val secondScreen = anchor.bottom > displayHeight
        if(secondScreen) positionY -= displayHeight + DuoSupportUtils.DUO_HINGE_WIDTH

        isAboveAnchor = context.activity?.let {
            if (DuoSupportUtils.isDeviceSurfaceDuo(it)) positionY + contentHeight + margin > displayHeight
            else positionY + contentHeight + margin - context.statusBarHeight > displayHeight
        } ?: false
        if (isAboveAnchor) {
            positionY = anchor.top - contentHeight - offsetY
            if(secondScreen) positionY -= displayHeight + DuoSupportUtils.DUO_HINGE_WIDTH
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

        toolTipArrow =
            if (isAboveAnchor  && !isSideAnchor) arrowDownView else
            if (!isAboveAnchor && !isSideAnchor) arrowUpView   else
            if (!isAboveAnchor) arrowLeftView
            else arrowRightView

        toolTipArrow.visibility = View.VISIBLE

        val tooltipArrowWidth = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_arrow_height)

        // In RTL scenario "x" axis is still left to right with 0 being at the left most edge of the display.
        // The offset calculation places the tooltip arrow in the correct position in reference to its anchor.
        val layoutParams = toolTipArrow.layoutParams as LinearLayout.LayoutParams
        val cornerRadius = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_radius)
        if(!isSideAnchor){ // Normal Top/Bottom arrow
            val anchorCenterX = if (anchorRect.centerX() > displayWidth) anchorRect.centerX() - displayWidth - DuoSupportUtils.DUO_HINGE_WIDTH
                                     else anchorRect.centerX()

            val offset = if (isRTL)
                positionX + contentWidth - anchorCenterX - tooltipArrowWidth
            else
                (anchorCenterX - positionX - tooltipArrowWidth)
            layoutParams.gravity = Gravity.START
            layoutParams.marginStart = offset + offsetX
        }
        else{// Edge Case Left/Right arrow
            layoutParams.gravity = Gravity.TOP
            var topMargin = anchorRect.centerY() - positionY - tooltipArrowWidth
            val secondScreen = anchorRect.top > displayHeight
            topMargin -= if (secondScreen) displayHeight else 0
            if(positionY + contentHeight >= displayHeight) topMargin -= cornerRadius
            layoutParams.topMargin = topMargin
        }
    }

    private fun checkEdgeCase(anchorRect: Rect) {
        // It is used to check if the set positionX leads to a cut in the tooltip arrow wrt display size.
        // If the tooltip or the triangular arrow will get cut then the tooltip is readjusted with a side arrow.
        isAboveAnchor = false // Enables left arrow

        val layoutParams = toolTipArrow.layoutParams as LinearLayout.LayoutParams
        val upArrowWidth = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_arrow_width)
        val cornerRadius = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_radius)
        val startPosition = positionX + layoutParams.marginStart
        val topBarHeight = context.statusBarHeight + ( context.activity?.supportActionBar?.height ?: 0 )
        val doesNotFitAboveOrBelow = (positionY < topBarHeight) || (positionY + contentHeight > displayHeight)
        val rightSpace = displayWidth - anchorRect.right - context.softNavBarOffsetX
        val rightEdge = ( startPosition + upArrowWidth + cornerRadius + margin > displayWidth ) || (doesNotFitAboveOrBelow && anchorRect.left > rightSpace)
        val leftEdge = ( startPosition - upArrowWidth - cornerRadius - margin - context.softNavBarOffsetX < 0 ) || (doesNotFitAboveOrBelow && anchorRect.left < rightSpace)

        // Duo Support
        val secondScreen = anchorRect.left > displayWidth
        if (leftEdge ) { // checks if the arrow is cut by the left edge of the screen and sets positionX to the left of the anchor with proper width.
            positionX = anchorRect.right
            if (secondScreen) positionX -= displayWidth + DuoSupportUtils.DUO_HINGE_WIDTH
        }

        if (rightEdge) { // checks if the arrow is cut by the right edge of the screen and sets positionX to the left of the anchor with proper width.
            isAboveAnchor = true // Enables right arrow
            positionX = anchorRect.left - contentWidth - upArrowWidth / 2
            if (secondScreen) positionX -= displayWidth + DuoSupportUtils.DUO_HINGE_WIDTH
        }

        if(leftEdge || rightEdge)
            requireReadjustment = true
    }

    private fun hingeSupport(anchorRect: Rect, dismissLocation: TouchDismissLocation) {
        context.activity?.let {
            val upArrowWidth = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_arrow_width)
            val tooltipRect = Rect(positionX, positionY, positionX + contentWidth, positionY + contentHeight - upArrowWidth / 2)
            val anchorIntersects = DuoSupportUtils.intersectHinge(it, anchorRect)
            val tooltipIntersects = DuoSupportUtils.intersectHinge(it, tooltipRect)

            if (anchorIntersects || tooltipIntersects) {
                if (DuoSupportUtils.isWindowDoublePortrait(it)) {
                    isAboveAnchor = false // Enables left arrow
                    if (DuoSupportUtils.moreOnLeft(it, anchorRect)) {
                        isAboveAnchor = true // Enables right arrow
                        positionX = anchorRect.left - contentWidth - upArrowWidth / 2
                    } else {
                        positionX = anchorRect.right
                    }
                    requireReadjustment = true
                } else { // Device is in vertical orientation
                    // Usually the tooltip will occur below the anchor, so the tooltip will intersect only in case its in top screen
                    // In such case, we make tooltip on the top of the anchor.
                    if (tooltipIntersects) {
                        isAboveAnchor = true
                        isSideAnchor = false
                        positionY = anchorRect.top - contentHeight
                        if (dismissLocation == TouchDismissLocation.INSIDE) positionY -= context.statusBarHeight
                        requireReinit = true
                    }
                }
            }
        }
    }

    private fun readjustTooltip(anchorRect: Rect, isRTL: Boolean, config: Config){
        val upArrowWidth = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_arrow_width)
        val cornerRadius = context.resources.getDimensionPixelSize(R.dimen.fluentui_tooltip_radius)
        val topBarHeight = context.activity!!.supportActionBar?.height ?: 0

        isSideAnchor =  true // Enables side arrow

        // As the arrow on Top/Bottom is hidden, content height decreases
        contentHeight -= upArrowWidth / 2

        // As the arrow on Left/Right is visible, content width increases
        contentWidth += upArrowWidth / 2

        positionY =
                // Sets positionY such that the tooltip is symmetric about the anchor if there is enough space above and below the anchor
                if (anchorRect.centerY() + contentHeight/2 + margin < displayHeight && anchorRect.centerY() - contentHeight / 2 - margin > topBarHeight + context.statusBarHeight)
                    anchorRect.centerY() - contentHeight / 2

                // Otherwise sets positionY as the top of the anchor if enough space is available below for the content
                else if (anchorRect.top + contentHeight < displayHeight)
                    anchorRect.top

                // Otherwise sets positionY such that the content ends at the bottom of anchor
                else anchorRect.bottom - contentHeight

        val secondScreen = anchorRect.top > displayHeight
        positionY -= if (secondScreen) displayHeight else 0

        // Readjusts positionY if it crosses AppBar on the top
        if (positionY < topBarHeight + context.statusBarHeight)
            positionY = topBarHeight + margin + context.statusBarHeight
        if (config.touchDismissLocation == TouchDismissLocation.INSIDE)
            positionY -= context.statusBarHeight

        // Readjustment for Duo hinge
        val tooltipRect = Rect(positionX, positionY, positionX + contentWidth, positionY + contentHeight)
        context.activity?.let {
            if (DuoSupportUtils.intersectHinge(it, tooltipRect)) {
                positionY = if (DuoSupportUtils.moreOnTop(it, anchorRect)) {
                    DuoSupportUtils.getHinge(it)!!.top - contentHeight - margin + cornerRadius
                } else {
                    DuoSupportUtils.getHinge(it)!!.bottom + margin - cornerRadius
                }
                isAboveAnchor = tooltipRect.left < anchorRect.left
            }
        }

        // Reinitialize tooltip with side arrow
        initTooltipArrow(anchorRect, isRTL, config.offsetX)
        if (config.touchDismissLocation == TouchDismissLocation.INSIDE)
            (toolTipArrow.layoutParams as LinearLayout.LayoutParams).topMargin -= context.statusBarHeight
        requireReadjustment = false
    }

    data class Config(var offsetX: Int = 0, var offsetY: Int = 0, var touchDismissLocation: TouchDismissLocation = TouchDismissLocation.ANYWHERE)

    interface OnDismissListener {
        fun onDismiss()
    }
}
