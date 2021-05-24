/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persona

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

enum class AvatarStyle {
    CIRCLE, SQUARE
}

enum class AvatarBorderStyle {
    NO_BORDER, RING
}

/**
 * [AvatarView] is a custom ImageView that displays the initials of a person on top of a colored circular
 * background. The initials are extracted from their name or email. The color of the circular
 * background is computed from the name and is based on an array of colors.
 */
open class AvatarView : AppCompatImageView {
    companion object {
        internal val DEFAULT_AVATAR_SIZE = AvatarSize.LARGE
        internal val DEFAULT_AVATAR_STYLE = AvatarStyle.CIRCLE
        internal val DEFAULT_AVATAR_BORDER_STYLE = AvatarBorderStyle.NO_BORDER
    }

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Persona), attrs, defStyleAttr) {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.AvatarView)
        val avatarSizeOrdinal = styledAttrs.getInt(R.styleable.AvatarView_avatarSize, DEFAULT_AVATAR_SIZE.ordinal)
        val avatarStyleOrdinal = styledAttrs.getInt(R.styleable.AvatarView_avatarStyle, DEFAULT_AVATAR_STYLE.ordinal)
        val avatarBorderStyleOrdinal = styledAttrs.getInt(R.styleable.AvatarView_avatarBorderStyle, DEFAULT_AVATAR_BORDER_STYLE.ordinal)

        name = styledAttrs.getString(R.styleable.AvatarView_name) ?: ""
        email = styledAttrs.getString(R.styleable.AvatarView_email) ?: ""
        avatarSize = AvatarSize.values()[avatarSizeOrdinal]
        avatarStyle = AvatarStyle.values()[avatarStyleOrdinal]
        avatarBorderStyle = AvatarBorderStyle.values()[avatarBorderStyleOrdinal]

        val avatarImageResourceId = styledAttrs.getResourceId(R.styleable.AvatarView_avatarImageDrawable, 0)
        if (avatarImageResourceId > 0 && resources.getResourceTypeName(avatarImageResourceId) == "drawable")
            avatarImageDrawable = styledAttrs.getDrawable(R.styleable.AvatarView_avatarImageDrawable)

        val avatarBackgroundColorResourceId = styledAttrs.getResourceId(R.styleable.AvatarView_avatarBackgroundColor, 0)
        if (avatarBackgroundColorResourceId > 0 && resources.getResourceTypeName(avatarBackgroundColorResourceId) == "color")
            avatarBackgroundColor = ContextCompat.getColor(context, avatarBackgroundColorResourceId)

        styledAttrs.recycle()
    }

    var name: String = ""
        set(value) {
            field = value
            initials.setInfo(name, email, avatarBackgroundColor)
        }
    var email: String = ""
        set(value) {
            field = value
            initials.setInfo(name, email, avatarBackgroundColor)
        }
    var avatarImageBitmap: Bitmap? = null
        set(value) {
            field = value
            setImageBitmap(value)
        }
    var avatarImageDrawable: Drawable? = null
        set(value) {
            field = value
            setImageDrawable(value)
        }
    var avatarImageResourceId: Int? = null
        set(value) {
            field = value
            value?.let {
                if( it != -1)
                    setImageResource(it)
            }
        }
    var avatarImageUri: Uri? = null
        set(value) {
            field = value
            setImageURI(value)
        }
    @ColorInt
    var avatarBackgroundColor: Int? = null
        set(value) {
            field = value
            initials.setInfo(name, email, avatarBackgroundColor)
        }
    /**
     * Defines the [AvatarSize] applied to the avatar's height and width.
     */
    var avatarSize: AvatarSize = DEFAULT_AVATAR_SIZE
        set(value) {
            if (field == value)
                return

            field = value
            requestLayout()
        }
    /**
     * Defines the [AvatarStyle] applied to the avatar.
     */
    var avatarStyle: AvatarStyle = DEFAULT_AVATAR_STYLE
        set(value) {
            if (field == value)
                return

            field = value
            invalidate()
        }
    /**
     * Defines the [AvatarBorderStyle] applied to the avatar.
     */
    var avatarBorderStyle: AvatarBorderStyle = DEFAULT_AVATAR_BORDER_STYLE
        set(value) {
            if (field == value)
                return

            field = value
            invalidate()
        }

    /**
     * Defines the [AvatarIsOverFlow] applied to the avatar.
     */
    var avatarIsOverFlow: Boolean = false
        set(value) {
            if (field == value)
                return

            field = value
            initials.setInfo(name, email, avatarBackgroundColor, true)
            invalidate()
        }

    private val initials = InitialsDrawable(context)
    private val path: Path = Path()

    override fun draw(canvas: Canvas) {
        val avatarBoundsRect = Rect(getViewBorderSize(), getViewBorderSize(), getViewSize()-getViewBorderSize(), getViewSize()-getViewBorderSize())

        initials.avatarStyle = avatarStyle
        initials.bounds = avatarBoundsRect
        initials.draw(canvas)

        path.reset()
        when (avatarStyle) {
            AvatarStyle.CIRCLE ->
                path.addCircle(
                        getViewSize() / 2f,
                        getViewSize() / 2f,
                        getViewSize() / 2f,
                        Path.Direction.CW
                )
            AvatarStyle.SQUARE -> {
                val cornerRadius = resources.getDimension(R.dimen.fluentui_avatar_square_corner_radius)
                path.addRoundRect(RectF(avatarBoundsRect), cornerRadius, cornerRadius, Path.Direction.CW)
            }
        }
        canvas.clipPath(path)

        super.draw(canvas)
        checkAndAddRing(canvas)
    }

    private fun checkAndAddRing(canvas: Canvas) {
        if (avatarBorderStyle == AvatarBorderStyle.RING && avatarStyle == AvatarStyle.CIRCLE) {
            path.reset()
            /*
             * There are total 3  rings, 2  outside of  avatar and 1 on top of avatar, thereby
             * hiding some of the view/image.
             * stroke size for each ring will be half of border size(1/2)
             */

            /*
             * Create Path to add the main border in mid of ring
             * To create middle ring. We do -3/4 of border size because, 1/2  will be center of
             * 2 outer rings and we want to create border  of  width bordersize/2. so, it should
             * be at the center of middle which is 1/2+1/4 = 3/4
             */
            path.addCircle(
                    getViewSize() / 2f,
                    getViewSize() / 2f,
                    getViewSize() / 2f - 3 * getViewBorderSize() / 4f,
                    Path.Direction.CW
            )
            val paint = Paint()
            paint.style = Paint.Style.STROKE
            if (avatarIsOverFlow) {
                paint.color = ContextCompat.getColor(context, R.color.fluentui_avatar_border_background)
            }
            else {
                paint.color = avatarBackgroundColor ?: initials.initialsBackgroundColor
            }
            paint.strokeWidth = getViewBorderSize() / 2f
            paint.isAntiAlias = true
            canvas.drawPath(path, paint)
            path.reset()
            /*
            * Create path to add inner ring
            * To create middle ring. We do -5/4 of border size because, border should start
            * from innermost ring  and as stroke is of  width bordersize/2. so, it should
            * be at the center of innermost which is 1+1/4 = 5/4
            */
            paint.color = ContextCompat.getColor(context, R.color.fluentui_avatar_ring_background)
            path.addCircle(
                    getViewSize() / 2f,
                    getViewSize() / 2f,
                    getViewSize() / 2f - 5 * getViewBorderSize() / 4f,
                    Path.Direction.CW
            )
            canvas.drawPath(path, paint)
            path.reset()
            /*
            * Create path to add outermost ring
            * We do -1/4 of border size because, border should start from
            * outermost ring  and as stroke is of  width bordersize/2. so, it should
            * be at the center of outermost which is 1/4
            */
            path.addCircle(
                    getViewSize() / 2f,
                    getViewSize() / 2f,
                    getViewSize() / 2f - getViewBorderSize() / 4f,
                    Path.Direction.CW
            )
            canvas.drawPath(path, paint)
        }
    }

    override fun setImageDrawable(drawable: Drawable?) {
        if (drawable is BitmapDrawable)
            setImageBitmap(drawable.bitmap)
        else
            super.setImageDrawable(drawable)
    }

    override fun setImageBitmap(bitmap: Bitmap?) {
        if (bitmap == null)
            return

        super.setImageDrawable(BitmapDrawable(resources, bitmap))
    }

    override fun setImageURI(uri: Uri?) {
        if (uri == null)
            return

        try {
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            setImageBitmap(bitmap)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            resolveSizeAndState(getViewSize(), widthMeasureSpec, 0),
            resolveSizeAndState(getViewSize(), heightMeasureSpec, 0)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        layoutParams.width = getViewSize()
        layoutParams.height = getViewSize()

        super.onSizeChanged(w, h, oldw, oldh)
    }

    /**
     * returns the [AvatarViewSize] including the border width
     */
    fun getViewSize(): Int {
        return avatarSize.getDisplayValue(context) + 2*getViewBorderSize()
    }

    private fun getViewBorderSize(): Int {
        return when (avatarBorderStyle) {
            AvatarBorderStyle.NO_BORDER -> 0
            AvatarBorderStyle.RING -> when (avatarSize) {
                AvatarSize.XXLARGE -> context.resources.getDimension(R.dimen.fluentui_avatar_border_size_xxlarge).toInt()
                else -> context.resources.getDimension(R.dimen.fluentui_avatar_border_size).toInt()
            }
        }
    }
}

fun AvatarView.setAvatar(avatar: IAvatar) {
    name = avatar.name
    email = avatar.email
    avatarImageBitmap = avatar.avatarImageBitmap
    avatarImageDrawable = avatar.avatarImageDrawable
    avatarImageResourceId = avatar.avatarImageResourceId
    avatarImageUri = avatar.avatarImageUri
    avatarBackgroundColor = avatar.avatarBackgroundColor
}