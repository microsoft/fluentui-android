/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.toolbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.Toolbar
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.microsoft.fluentui.R
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentui.persona.IAvatar
import com.microsoft.fluentui.persona.setAvatar
import com.microsoft.fluentui.search.Searchbar
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

/**
 * [Toolbar] appears at the top of the activity and can display the [avatarView], title,
 * and action items.
 */
class Toolbar : Toolbar {
    companion object {
        private val AVATAR_SIZE = AvatarSize.MEDIUM
    }

    /**
     * [avatar] is used as the navigation icon and appears at the start of the [Toolbar].
     */
    var avatar: IAvatar? = null
        set(value) {
            field = value
            navigationIcon = if (value != null) getAvatarBitmapDrawable() else null
        }

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Components), attrs, R.attr.toolbarStyle) {
        // minHeight can't be set in theme or it will also set title height. Having minHeight helps center option menu icons.
        val styledAttributes = context.theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        val actionBarSize = styledAttributes.getDimensionPixelSize(0, -1)
        styledAttributes.recycle()
        minimumHeight = actionBarSize

        updateStylesAndIcon()
    }

    override fun setNavigationIcon(icon: Drawable?) {
        super.setNavigationIcon(icon)

        updateStylesAndIcon()
    }

    override fun onViewAdded(child: View) {
        super.onViewAdded(child)

        // This accounts for cases when the native back button is added for Searchbar as ActionMenuView.
        if (child is Searchbar && child.isActionMenuView)
            updateStylesAndIcon(true)
    }

    private fun updateStylesAndIcon(nativeBackButtonUsed: Boolean = false) {
        touchscreenBlocksFocus = false
        if (navigationIcon == null && !nativeBackButtonUsed) {
            setPaddingRelative(context.resources.getDimension(R.dimen.fluentui_toolbar_padding_start).toInt(), 0, 0, 0)
            titleMarginStart = context.resources.getDimension(R.dimen.fluentui_toolbar_title_margin_start).toInt()
            return
        }

        setPaddingRelative(context.resources.getDimension(R.dimen.fluentui_toolbar_padding_start_with_avatar).toInt(), 0, 0, 0)
        titleMarginStart = context.resources.getDimension(R.dimen.fluentui_toolbar_title_margin_start_with_avatar).toInt()
    }

    /**
     * Draw the [avatarView]'s initials or bitmap onto a canvas that's used to create a [BitmapDrawable].
     */
    private fun getAvatarBitmapDrawable(): BitmapDrawable? {
        val avatar = avatar ?: return null
        val avatarView = AvatarView(context)
        avatarView.setAvatar(avatar)
        avatarView.avatarSize = AVATAR_SIZE

        val avatarDisplayValue = avatarView.getViewSize()
        val avatarBitmap = Bitmap.createBitmap(avatarDisplayValue, avatarDisplayValue, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(avatarBitmap)

        avatarView.layoutParams = ViewGroup.LayoutParams(avatarDisplayValue, avatarDisplayValue)
        avatarView.layout(0, 0, avatarDisplayValue, avatarDisplayValue)
        avatarView.draw(canvas)

        return BitmapDrawable(resources, avatarBitmap)
    }
}