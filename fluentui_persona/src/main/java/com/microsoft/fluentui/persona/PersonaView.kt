/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persona

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.view.accessibility.AccessibilityEvent
import androidx.annotation.ColorInt
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.util.isVisibleOnScreen

/**
 * [PersonaView] is comprised of an [AvatarView] and three TextViews, all single line by default.
 * [AvatarSize.SMALL], [AvatarSize.LARGE], and [AvatarSize.XXLARGE] are the recommended AvatarSizes to use with [PersonaView].
 * [AvatarSize.SMALL] will only have name text. [AvatarSize.LARGE] should use both name and subtitle texts.
 * [AvatarSize.XXLARGE] should use name, subtitle, and footer texts.
 */
class PersonaView : ListItemView {
    companion object {
        val personaAvatarSizes = arrayOf(AvatarSize.SMALL, AvatarSize.LARGE, AvatarSize.XXLARGE)
        const val FOCUS_DELAY = 100L

        data class Spacing(val cellPadding: Int, val insetLeft: Int)

        fun getSpacing(context: Context, avatarSize: AvatarSize): Spacing {
            val avatarDisplaySize = avatarSize.getDisplayValue(context)
            val spacingRight = context.resources.getDimension(R.dimen.fluentui_persona_horizontal_spacing)
            val cellPadding = context.resources.getDimension(R.dimen.fluentui_persona_horizontal_padding).toInt()
            val insetLeft = (avatarDisplaySize + spacingRight + cellPadding).toInt()
            return Spacing(cellPadding, insetLeft)
        }
    }

    /**
     * Text for the top hierarchy of the three TextViews.
     */
    var name: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateViews()
        }

    var email: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateViews()
        }

    /**
     * Used to set internal [AvatarView]'s layout width and height.
     */
    var avatarSize: AvatarSize = AvatarView.DEFAULT_AVATAR_SIZE
        set(value) {
            if (!personaAvatarSizes.contains(value)) {
                throw UnsupportedOperationException("""
                    AvatarSize $value is not supported in PersonaViews.
                    Please replace with one of the following AvatarSizes: ${personaAvatarSizes.joinToString(", ")}
                """.trimIndent())
            }

            if (field == value)
                return
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

    private val avatarView = AvatarView(context)

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(appContext, attrs, defStyleAttr) {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.PersonaView)
        name = styledAttrs.getString(R.styleable.PersonaView_name) ?: ""
        email = styledAttrs.getString(R.styleable.PersonaView_email) ?: ""

        val avatarSizeOrdinal = styledAttrs.getInt(R.styleable.PersonaView_avatarSize, AvatarView.DEFAULT_AVATAR_SIZE.ordinal)
        avatarSize = AvatarSize.values()[avatarSizeOrdinal]

        val avatarImageResourceId = styledAttrs.getResourceId(R.styleable.PersonaView_avatarImageDrawable, 0)
        if (avatarImageResourceId > 0 && resources.getResourceTypeName(avatarImageResourceId) == "drawable")
            avatarImageDrawable = styledAttrs.getDrawable(R.styleable.PersonaView_avatarImageDrawable)

        styledAttrs.recycle()
    }

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()

        updateViews()
    }

    private fun updateViews() {
        title = when {
            name.isNotEmpty() -> name
            email.isNotEmpty() -> email
            else -> context.getString(R.string.persona_title_placeholder)
        }

        avatarView.name = name
        avatarView.email = email
        avatarView.avatarSize = avatarSize
        avatarView.avatarImageDrawable = avatarImageDrawable
        avatarView.avatarImageBitmap = avatarImageBitmap
        avatarView.avatarImageUri = avatarImageUri
        avatarView.avatarBackgroundColor = avatarBackgroundColor

        customView = avatarView

        customViewSize = when(avatarSize) {
            AvatarSize.SMALL -> CustomViewSize.SMALL
            AvatarSize.LARGE -> CustomViewSize.MEDIUM
            else -> CustomViewSize.LARGE
        }
    }

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        if (!isVisibleOnScreen && gainFocus) {
            postDelayed({
                if (isFocused) {
                    sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
                }
            }, FOCUS_DELAY)
        }
    }
}

fun PersonaView.setPersona(persona: IPersona) {
    name = persona.name
    email = persona.email
    subtitle = persona.subtitle
    footer = persona.footer
    avatarImageBitmap = persona.avatarImageBitmap
    avatarImageDrawable = persona.avatarImageDrawable
    avatarImageResourceId = persona.avatarImageResourceId
    avatarImageUri = persona.avatarImageUri
    avatarBackgroundColor = persona.avatarBackgroundColor
}