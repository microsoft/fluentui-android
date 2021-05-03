/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.fluentui.vNext.widget

import android.content.Context
import android.graphics.drawable.Drawable

import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import com.microsoft.fluentui.R
import com.microsoft.fluentui.generator.ButtonSize
import com.microsoft.fluentui.generator.ButtonStyle
import com.microsoft.fluentui.generator.resourceProxies.ButtonTokensSystem
import com.microsoft.fluentui.util.FieldUpdateListener
import com.microsoft.fluentui.vNext.persona.AvatarView

/**
 * [ButtonView] is a custom AppCompatButton that uses token system to set predefined button style
 */
open class ButtonView : AppCompatButton {

    var button: Button
        set(value) {
            field = value
            setValues()
        }

    private val tokenSystem: ButtonTokensSystem

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        tokenSystem = ButtonTokensSystem(context, resources)
        button = Button()
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ButtonTokensView)
        val buttonSizeOrdinal = styledAttrs.getInt(R.styleable.ButtonTokensView_fluentUI_buttonSize, ButtonSize.SMALL.ordinal)
        val buttonStyleOrdinal = styledAttrs.getInt(R.styleable.ButtonTokensView_fluentUI_buttonStyle, ButtonStyle.PRIMARY.ordinal)
        button.buttonSize = ButtonSize.values()[buttonSizeOrdinal]
        button.buttonStyle = ButtonStyle.values()[buttonStyleOrdinal]

        val buttonIconResourceId = styledAttrs.getResourceId(R.styleable.ButtonTokensView_fluentUI_buttonIcon, 0)
        if (buttonIconResourceId > 0 && resources.getResourceTypeName(buttonIconResourceId) == "drawable")
            button.buttonIconDrawable = styledAttrs.getDrawable(R.styleable.ButtonTokensView_fluentUI_buttonIcon)

        styledAttrs.recycle()
        setValues()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setListenerForListItemUpdate()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setListenerForListItemUpdate()
    }

    private fun setListenerForListItemUpdate() {
        if(button.fieldUpdateListener == null) {
            button.fieldUpdateListener = object : FieldUpdateListener {
                override fun onFieldUpdated() {
                    setValues()
                }
            }
            setValues() }
    }

    fun setIconDrawable(drawable: Drawable?) {
        button.buttonIconDrawable = drawable
    }

    private fun setValues() {
        tokenSystem.buttonStyle = button.buttonStyle ?: ButtonStyle.PRIMARY
        tokenSystem.buttonSize = button.buttonSize ?: ButtonSize.SMALL
        background = tokenSystem.background
        setTextColor(tokenSystem.textColor)
        setTextAppearance(context, tokenSystem.textFont)
        setPadding(tokenSystem.paddingHorizontal.toInt(), tokenSystem.paddingVertical.toInt(), tokenSystem.paddingHorizontal.toInt(), tokenSystem.paddingVertical.toInt())
        setIcon()
        requestLayout()
    }

    private fun setIcon() {
        button.buttonIconDrawable?.let {
            var iconSize = tokenSystem.iconSize.toInt()
            setCompoundDrawables(it, null, null, null)
            compoundDrawablePadding = tokenSystem.interspace.toInt()
            button.buttonIconDrawable?.setBounds(0, 0, iconSize, iconSize)
        }
    }
}