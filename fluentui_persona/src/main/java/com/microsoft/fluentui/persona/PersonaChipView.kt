/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persona

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.AttrRes
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.persona.databinding.ViewPersonaChipBinding
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.isVisible
import com.microsoft.fluentui.view.TemplateView

class PersonaChipView : TemplateView {
    companion object {
        const val DISABLED_BACKGROUND_OPACITY = .6f
        const val ENABLED_BACKGROUND_OPACITY = 1.0f
    }

    var name: String = ""
        set(value) {
            field = value
            updateViews()
        }
    var email: String = ""
        set(value) {
            field = value
            updateViews()
        }
    var avatarImageBitmap: Bitmap? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateViews()
        }
    var avatarImageDrawable: Drawable? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateViews()
        }
    var avatarImageResourceId: Int? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateViews()
        }
    var avatarImageUri: Uri? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateViews()
        }
    @ColorInt
    var avatarBackgroundColor: Int? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateViews()
        }
    /**
     * Flag for setting the chip's error state
     */
    var hasError: Boolean = false
        set(value) {
            if (field == value)
                return
            field = value
            updateViews()
        }
    /**
     * Determines whether the [closeIcon] is shown in place of the [avatarView]
     * when the [PersonaChipView] is selected.
     */
    var showCloseIconWhenSelected: Boolean = true

    /**
     * When a chip is selected, the next touch will fire the [listener]'s [onClicked] method.
     */
    var listener: Listener? = null

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Persona), attrs, defStyleAttr) {
        if (attrs == null)
            return
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.PersonaChipView)
        name = styledAttrs.getString(R.styleable.PersonaChipView_name) ?: ""
        email = styledAttrs.getString(R.styleable.PersonaChipView_email) ?: ""
        showCloseIconWhenSelected = styledAttrs.getBoolean(R.styleable.PersonaChipView_showCloseIconWhenSelected, true)

        val avatarImageResourceId = styledAttrs.getResourceId(R.styleable.PersonaChipView_avatarImageDrawable, 0)
        if (avatarImageResourceId > 0 && resources.getResourceTypeName(avatarImageResourceId) == "drawable")
            avatarImageDrawable = styledAttrs.getDrawable(R.styleable.PersonaChipView_avatarImageDrawable)

        styledAttrs.recycle()
        contentDescription = name
    }

    // Template

    override val templateId: Int = R.layout.view_persona_chip
    private var avatarView: AvatarView? = null
    private var textView: TextView? = null
    private var closeIcon: ImageView? = null

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()
        textView = findViewInTemplateById(R.id.persona_chip_text)
        avatarView = findViewInTemplateById(R.id.persona_chip_avatar)
        closeIcon = findViewInTemplateById(R.id.persona_chip_close)
        updateState()
        updateViews()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateState()
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        updateState()
        listener?.onSelected(selected)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isEnabled)
            return false

        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isPressed = true
                return true
            }
            MotionEvent.ACTION_UP -> {
                performClick()
                isPressed = false
                isSelected = !isSelected
                return true
            }
            MotionEvent.ACTION_CANCEL -> {
                isPressed = false
                return true
            }
            else -> return false
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_ENTER -> {
                performClick()
                isSelected = !isSelected
                true
            }
            else ->
                super.onKeyUp(keyCode, event)
        }
    }

    override fun performClick(): Boolean {
        super.performClick()

        if (isSelected)
            listener?.onClicked()

        return true
    }

    private fun updateState() {
        isActivated = isSelected
        textView?.isEnabled = isEnabled
        avatarView?.alpha = if (isEnabled) ENABLED_BACKGROUND_OPACITY else DISABLED_BACKGROUND_OPACITY

        val showCloseIcon = showCloseIconWhenSelected && isSelected
        closeIcon?.isVisible = showCloseIcon
        avatarView?.isVisible = !showCloseIcon
        isFocusable = true
    }

    private fun updateViews() {
        textView?.text = when {
            name.isNotEmpty() -> name
            email.isNotEmpty() -> email
            else -> context.getString(R.string.persona_title_placeholder)
        }

        avatarView?.apply {
            name = this@PersonaChipView.name
            email = this@PersonaChipView.email
            avatarImageDrawable = this@PersonaChipView.avatarImageDrawable
            avatarImageBitmap = this@PersonaChipView.avatarImageBitmap
            avatarImageUri = this@PersonaChipView.avatarImageUri
            avatarBackgroundColor  = this@PersonaChipView.avatarBackgroundColor
        }

        if (hasError)
            updateStyles(R.drawable.persona_chip_background_error, R.attr.fluentuiPersonaChipTextErrorColor)
        else
            updateStyles(R.drawable.persona_chip_background_normal, R.attr.fluentuiPersonaChipTextNormalColor)
    }

    override fun getAccessibilityClassName(): CharSequence {
        return CheckBox::class.java.name
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo?) {
        super.onInitializeAccessibilityNodeInfo(info)
        info?.apply {
            isEnabled = isEnabled
            isCheckable = true
            isChecked = isSelected
            text = name
        }
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent?) {
        super.onInitializeAccessibilityEvent(event)
        event?.apply {
            isEnabled = isEnabled
            isChecked = isSelected
        }
    }

    override fun onPopulateAccessibilityEvent(event: AccessibilityEvent?) {
        super.onPopulateAccessibilityEvent(event)
        event?.text?.add(name)
    }

    private fun updateStyles(backgroundDrawableId: Int, @AttrRes defaultTextColor: Int) {
        background = ContextCompat.getDrawable(context, backgroundDrawableId)
        textView?.setTextColor(createColorStateList(defaultTextColor))
    }

    // Create this in code instead of xml to support Lollipop, which does not allow attributes in xml selectors.
    private fun createColorStateList(@AttrRes defaultTextColor: Int): ColorStateList {
        return ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_activated),
                intArrayOf()
            ),
            intArrayOf(
                ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiPersonaChipTextDisabledColor),
                ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiPersonaChipForegroundActiveColor),
                ThemeUtil.getThemeAttrColor(context, defaultTextColor)
            )
        )
    }

    interface Listener {
        fun onClicked()
        fun onSelected(selected: Boolean)
    }
}

fun PersonaChipView.setPersona(persona: IPersona) {
    name = persona.name
    email = persona.email
    avatarImageBitmap = persona.avatarImageBitmap
    avatarImageDrawable = persona.avatarImageDrawable
    avatarImageResourceId = persona.avatarImageResourceId
    avatarImageUri = persona.avatarImageUri
    avatarBackgroundColor = persona.avatarBackgroundColor
}