/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.popupmenu

import android.content.Context
import android.graphics.PorterDuff
import androidx.annotation.DrawableRes
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import com.microsoft.fluentui.menus.R
import com.microsoft.fluentui.popupmenu.PopupMenu.Companion.DEFAULT_ITEM_CHECKABLE_BEHAVIOR
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.isVisible
import com.microsoft.fluentui.view.TemplateView

internal class PopupMenuItemView : TemplateView {
    var itemCheckableBehavior: PopupMenu.ItemCheckableBehavior = DEFAULT_ITEM_CHECKABLE_BEHAVIOR
        set(value) {
            if (field == value)
                return
            field = value

            when (itemCheckableBehavior) {
                PopupMenu.ItemCheckableBehavior.SINGLE -> {
                    showRadioButton = true
                    showCheckBox = false
                }
                PopupMenu.ItemCheckableBehavior.ALL -> {
                    showRadioButton = false
                    showCheckBox = true
                }
                PopupMenu.ItemCheckableBehavior.NONE -> {
                    showRadioButton = false
                    showCheckBox = false
                }
            }

            updateViews()
        }

    private var title: String = ""
    @DrawableRes
    internal var iconResourceId: Int? = null
    private var isChecked: Boolean = false
    private var showDividerBelow: Boolean = false
    private var showRadioButton: Boolean = false
    private var showCheckBox: Boolean = false

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Menus), attrs, defStyleAttr)

    fun setMenuItem(popupMenuItem: PopupMenuItem) {
        title = popupMenuItem.title
        iconResourceId = popupMenuItem.iconResourceId
        isChecked = popupMenuItem.isChecked
        showDividerBelow = popupMenuItem.showDividerBelow

        updateViews()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                setPressedState(true)
            }
            MotionEvent.ACTION_UP -> {
                performClick()
                setPressedState(false)
            }
            MotionEvent.ACTION_CANCEL -> {
                setPressedState(false)
            }
            else -> return false
        }
        return true
    }

    // Template

    override val templateId: Int
        get() = R.layout.view_popup_menu_item

    private var iconImageView: ImageView? = null
    private var titleView: TextView? = null
    private var radioButton: RadioButton? = null
    private var checkBox: CheckBox? = null
    private var dividerView: View? = null

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()

        iconImageView = findViewInTemplateById(R.id.icon)
        titleView = findViewInTemplateById(R.id.title)
        radioButton = findViewInTemplateById(R.id.radio_button)
        checkBox = findViewInTemplateById(R.id.check_box)
        dividerView = findViewInTemplateById(R.id.divider)

        updateViews()
    }

    private fun updateViews() {
        titleView?.text = title

        iconResourceId?.let { iconImageView?.setImageResource(it) }
        iconImageView?.isVisible = iconResourceId != null

        radioButton?.isVisible = showRadioButton
        checkBox?.isVisible = showCheckBox
        dividerView?.isVisible = showDividerBelow

        updateCheckedState(isChecked)
        updateAccessibilityClickAction()
    }

    private fun setPressedState(isPressed: Boolean) {
        this.isPressed = isPressed
        radioButton?.isPressed = isPressed
        checkBox?.isPressed = isPressed
    }

    private fun updateCheckedState(isChecked: Boolean) {
        radioButton?.isChecked = isChecked
        checkBox?.isChecked = isChecked

        // Update text and icon color

        if (isChecked) {
            val foregroundSelectedColor = ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiPopupMenuItemForegroundSelectedColor)
            titleView?.setTextColor(foregroundSelectedColor)
            // Using post helps ensure that the color filter is applied to the correct image in API <= Lollipop.
            iconImageView?.post {
                iconImageView?.setColorFilter(foregroundSelectedColor, PorterDuff.Mode.SRC_IN)
                iconImageView?.invalidate()
            }
        } else {
            titleView?.setTextColor(ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiPopupMenuItemTitleColor))
            iconImageView?.post {
                iconImageView?.clearColorFilter()
                iconImageView?.invalidate()
            }
        }

        // Update content description

        val checkViewType = when {
            showRadioButton -> context.getString(R.string.popup_menu_accessibility_item_radio_button)
            showCheckBox -> context.getString(R.string.popup_menu_accessibility_item_check_box)
            else -> ""
        }

        val checkedState = if (isChecked)
            context.getString(R.string.popup_menu_accessibility_item_state_checked)
        else
            context.getString(R.string.popup_menu_accessibility_item_state_not_checked)

        contentDescription = if (showRadioButton || showCheckBox)
            "$title, $checkViewType $checkedState"
        else
            title
    }

    private fun updateAccessibilityClickAction() {
        ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)

                val clickLabel = if (itemCheckableBehavior == PopupMenu.ItemCheckableBehavior.NONE)
                    R.string.popup_menu_accessibility_item_select
                else
                    R.string.popup_menu_accessibility_item_toggle

                info.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    context.getString(clickLabel)
                ))
            }
        })
    }
}