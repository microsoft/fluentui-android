package com.microsoft.fluentui.vNext.persona

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.R
import com.microsoft.fluentui.generator.AvatarSize
import com.microsoft.fluentui.generator.AvatarStyle
import com.microsoft.fluentui.generator.resourceProxies.AvatarTokensSystem
import com.microsoft.fluentui.persona.InitialsDrawable
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.FieldUpdateListener
import com.microsoft.fluentui.util.ThemeUtil
import java.lang.Math.pow
import kotlin.math.sqrt


/**
 * [AvatarView] is a custom ImageView that displays the initials of a person on top of a colored circular
 * background. The initials are extracted from their name or email. The color of the circular
 * background is computed from the name and is based on an array of colors.
 */
open class AvatarView : AppCompatImageView {

    var avatar: Avatar
        set(value) {
            field = value
            setValues()
        }

    private var initialString: String = ""
    private var internalPadding = 20
    private var shouldUseCalculatedColors: Boolean = false
    private var shouldUseDefaultImage: Boolean = false
    private var hasImage = false
    private var isImageSet = false
    private var avatarBackgroundColor: Int = -1
    private var avatarForegroundColor: Int = -1
    private var avatarRingColor: Int = -1
    private var avatarRingGapColor: Int = -1
    private var presenceIconOutlineColor: Int = -1
    private var avatarRingThickness: Float = 0f
    private var avatarRingInnerThickness: Float = 0f
    private var avatarRingOuterThickness: Float = 0f
    private var presenceIconOutlineThickness: Float = 0f
    private var presenceIconSize: Float = 0f
    private val path: Path = Path()
    private var fallbackBackground: Drawable? = null
    private val tokenSystem: AvatarTokensSystem
    private val textPaint: TextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        density = context.resources.displayMetrics.density
    }
    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Components), attrs, defStyleAttr) {
        tokenSystem = AvatarTokensSystem(context, resources)
        avatar = Avatar()
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.AvatarTokensView)
        val avatarSizeOrdinal = styledAttrs.getInt(R.styleable.AvatarTokensView_fluentUI_avatarSize, AvatarSize.LARGE.ordinal)
        val avatarStyleOrdinal = styledAttrs.getInt(R.styleable.AvatarTokensView_fluentUI_avatarStyle, AvatarStyle.BASE.ordinal)

        avatar.name = styledAttrs.getString(R.styleable.AvatarTokensView_fluentUI_avatarName) ?: ""
        avatar.email = styledAttrs.getString(R.styleable.AvatarTokensView_fluentUI_avatarEmail) ?: ""
        avatar.avatarSize = AvatarSize.values()[avatarSizeOrdinal]
        avatar.avatarStyle = AvatarStyle.values()[avatarStyleOrdinal]

        val avatarImageResourceId = styledAttrs.getResourceId(R.styleable.AvatarTokensView_fluentUI_avatarImageDrawable, 0)
        if (avatarImageResourceId > 0 && resources.getResourceTypeName(avatarImageResourceId) == "drawable")
            avatar.avatarImageDrawable = styledAttrs.getDrawable(R.styleable.AvatarTokensView_fluentUI_avatarImageDrawable)

        val avatarBackgroundColorResourceId = styledAttrs.getResourceId(R.styleable.AvatarTokensView_fluentUI_avatarBackGroundColor, tokenSystem.backgroundDefaultColor)
        if (avatarBackgroundColorResourceId > 0 && resources.getResourceTypeName(avatarBackgroundColorResourceId) == "color")
            avatar.avatarBackgroundColor = ContextCompat.getColor(context, avatarBackgroundColorResourceId)
        styledAttrs.recycle()
        setValues()
    }

    override fun setImageDrawable(drawable: Drawable?) {
        if (drawable is BitmapDrawable)
            setImageBitmap(drawable.bitmap)
        else {
            avatar.avatarImageDrawable = drawable
            isImageSet = true
            super.setImageDrawable(drawable)
        }
    }

    override fun setImageBitmap(bitmap: Bitmap?) {
        if (bitmap == null)
            return
        avatar.avatarImageBitmap = bitmap
        isImageSet = true
        super.setImageDrawable(BitmapDrawable(resources, bitmap))
    }

    override fun setImageURI(uri: Uri?) {
        if (uri == null)
            return

        try {
            avatar.avatarImageUri = uri
            isImageSet = true
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            setImageBitmap(bitmap)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
                resolveSizeAndState(getViewSize().toInt() + internalPadding, widthMeasureSpec, 0),
                resolveSizeAndState(getViewSize().toInt() + internalPadding, heightMeasureSpec, 0)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        layoutParams.width = getViewSize().toInt() + internalPadding
        layoutParams.height = getViewSize().toInt() + internalPadding

        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setListenerForListItemUpdate()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setListenerForListItemUpdate()
    }

    private fun setValues() {
        checkForImage()
        tokenSystem.avatarStyle = avatar.avatarStyle ?: AvatarStyle.BASE
        tokenSystem.avatarSize = avatar.avatarSize ?: AvatarSize.LARGE
        initialString = if (tokenSystem.avatarStyle == AvatarStyle.OVERFLOW) avatar.name else InitialsDrawable.getInitials(avatar.name, avatar.email)
        shouldUseCalculatedColors = !initialString.isEmpty() && tokenSystem.avatarStyle != AvatarStyle.OVERFLOW
        shouldUseDefaultImage = !hasImage && initialString.isEmpty() && (tokenSystem.avatarStyle != AvatarStyle.OVERFLOW)
        avatarBackgroundColor = avatar.avatarBackgroundColor
                ?: (if (!shouldUseCalculatedColors) tokenSystem.backgroundDefaultColor
                else InitialsDrawable.getInitialsBackgroundColor(ThemeUtil.getColors(context, tokenSystem.textCalculatedBackgroundColors), avatar.name, avatar.email))
        avatarForegroundColor = avatar.avatarForegroundColor
                ?: (if (!shouldUseCalculatedColors) tokenSystem.foregroundDefaultColor
                else InitialsDrawable.getInitialsBackgroundColor(ThemeUtil.getColors(context, tokenSystem.textCalculatedForegroundColors), avatar.name, avatar.email))
        avatarRingColor = if (avatar.isRingVisible) avatar.avatarRingColor
                ?: (if (!shouldUseCalculatedColors) tokenSystem.ringDefaultColor else avatarBackgroundColor) else Color.TRANSPARENT
        avatarRingGapColor = if (avatar.isTransparent) Color.TRANSPARENT else tokenSystem.ringGapColor
        avatarRingThickness = if (avatar.isRingVisible) tokenSystem.ringThickness else 0f
        avatarRingInnerThickness = if (avatar.isRingVisible) tokenSystem.ringInnerGap else 0f
        avatarRingOuterThickness = if (avatar.isRingVisible) tokenSystem.ringOuterGap else 0f
        presenceIconOutlineThickness = if (avatar.presence == AvatarPresence.NONE) 0f else tokenSystem.presenceIconOutlineThickness
        presenceIconSize = if (avatar.presence == AvatarPresence.NONE) 0f else tokenSystem.presenceIconSize
        presenceIconOutlineColor = if (avatar.isTransparent) Color.TRANSPARENT else tokenSystem.presenceIconOutlineColor
        setImage()
    }

    private fun checkForImage() {
        if (avatar.avatarImageBitmap != null || avatar.avatarImageDrawable != null || avatar.avatarImageResourceId != -1 || avatar.avatarImageUri != null) {
            hasImage = true
        }
    }

    private fun setImage() {
        isImageSet = true
        if (avatar.avatarImageBitmap != null) {
            setImageBitmap(avatar.avatarImageBitmap)
        } else if (avatar.avatarImageDrawable != null) {
            setImageDrawable(avatar.avatarImageDrawable)
        } else if (avatar.avatarImageResourceId != -1) {
            setImageResource(avatar.avatarImageResourceId)
        } else if (avatar.avatarImageUri != null) {
            setImageURI(avatar.avatarImageUri)
        } else {
            // resetting the fallbackBackground in case refresh happens
            fallbackBackground = null
            isImageSet = false
        }

        // Setting fallback background
        if (tokenSystem.avatarStyle == AvatarStyle.OUTLINED || tokenSystem.avatarStyle == AvatarStyle.OUTLINED_PRIMARY) {
            // Set "person_48_regular" image
            fallbackBackground = ContextCompat.getDrawable(context, R.drawable.ic_fluent_person_48_regular)
            fallbackBackground = fallbackBackground?.mutate()
        } else if (shouldUseDefaultImage) {
            // Set "person_48_filled" image
            fallbackBackground = ContextCompat.getDrawable(context, R.drawable.ic_fluent_person_48_filled)
            fallbackBackground = fallbackBackground?.mutate()
        }
    }

    override fun draw(canvas: Canvas) {
        val avatarBoundsRect = RectF(measuredWidth/2-getViewSize()/2+getViewBorderSize(), measuredHeight/2-getViewSize()/2+getViewBorderSize(), measuredWidth/2+getViewSize()/2-getViewBorderSize(), measuredHeight/2+getViewSize()/2-getViewBorderSize())
        if (isImageSet) {
            setImageClip(avatarBoundsRect, canvas)

        } else if (fallbackBackground != null) {
            // draw the fallbackBackground in canvas
            setFallbackBackground(avatarBoundsRect,canvas)
        } else {
            setInitialsText(avatarBoundsRect, canvas)
        }
        super.draw(canvas)
    }

    private fun setListenerForListItemUpdate() {
        if(avatar.fieldUpdateListener == null) {
            avatar.fieldUpdateListener = object : FieldUpdateListener {
                override fun onFieldUpdated() {
                    setValues()
                    requestLayout()
                }
            }
            setValues()
            requestLayout()
        }
    }

    private fun setRing(avatarBoundsRect: RectF, canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = avatarRingGapColor

        canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, avatarBoundsRect.width()/2+avatarRingOuterThickness+avatarRingThickness, paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = avatarRingThickness
        paint.color = avatarRingColor
        val radius = (avatarBoundsRect.width() + avatarRingThickness) / 2f
        canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, paint)
    }

    private fun setImageClip(avatarBoundsRect: RectF, canvas: Canvas) {
        path.reset()
        when (tokenSystem.avatarStyle) {
            AvatarStyle.GROUP -> {
                val cornerRadius = tokenSystem.borderRadius
                path.addRoundRect(RectF(avatarBoundsRect), cornerRadius, cornerRadius, Path.Direction.CW)
            }
            else -> {
                setPresenceIcon(avatarBoundsRect, canvas)
                path.addCircle(avatarBoundsRect.width() / 2f + avatarBoundsRect.left, avatarBoundsRect.height() / 2f + avatarBoundsRect.top, (avatarBoundsRect.width() - avatarRingInnerThickness) / 2f, Path.Direction.CW)
                setRing(avatarBoundsRect, canvas)
            }
        }
        canvas.clipPath(path)
    }

    private fun setPresenceIcon(avatarBoundsRect: RectF, canvas: Canvas) {
        if (avatar.presence != AvatarPresence.NONE) {
            path.reset()
            //val positionOnArc = avatarBoundsRect.width()+avatarRingThickness+avatarRingOuterThickness - (presenceIconSize)/2f - presenceIconOutlineThickness
            val positionOnArcX = avatarRingOuterThickness  + measuredWidth / 2f + sqrt(pow((getViewSize()-2*getViewBorderSize()) / 2.0, 2.0) / 2).toFloat()
            val positionOnArcY = avatarRingOuterThickness  + measuredHeight / 2f + sqrt(pow((getViewSize()-2*getViewBorderSize()) / 2.0, 2.0) / 2).toFloat()
            path.addCircle(positionOnArcX, positionOnArcY, (presenceIconSize + 2 * presenceIconOutlineThickness) / 2f, Path.Direction.CW)
            if (!avatar.isTransparent) {
                paint.color = presenceIconOutlineColor
                canvas.drawPath(path, paint)
            }
            addPresenceIcon(canvas, positionOnArcX, positionOnArcY)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                canvas.clipOutPath(path)
            } else {
                canvas.clipPath(path, Region.Op.DIFFERENCE)
            }
        }
    }

    private fun addPresenceIcon(canvas: Canvas, positionOnArcX: Float, positionOnArcY: Float) {
        val drawable = ContextCompat.getDrawable(context, avatar.presence.getImage(avatar.isOOF))
        drawable?.setBounds(positionOnArcX.toInt() - presenceIconSize.toInt() / 2, positionOnArcY.toInt() - presenceIconSize.toInt() / 2, positionOnArcX.toInt() + presenceIconSize.toInt() / 2, positionOnArcY.toInt() + presenceIconSize.toInt() / 2)
        drawable?.draw(canvas)
    }

    private fun setFallbackBackground(avatarBoundsRect: RectF, canvas: Canvas) {
        path.reset()

        when (tokenSystem.avatarStyle) {
            AvatarStyle.GROUP -> {
                val cornerRadius = tokenSystem.borderRadius
                path.addRoundRect(RectF(avatarBoundsRect), cornerRadius, cornerRadius, Path.Direction.CW)
            }
            else -> {
                setPresenceIcon(avatarBoundsRect, canvas)
                path.addCircle(avatarBoundsRect.width() / 2f + avatarBoundsRect.left, avatarBoundsRect.height() / 2f + avatarBoundsRect.top, (avatarBoundsRect.width() - avatarRingInnerThickness) / 2f, Path.Direction.CW)
                setRing(avatarBoundsRect, canvas)
            }
        }
        paint.color = avatarBackgroundColor
        paint.style = Paint.Style.FILL
        canvas.drawPath(path, paint)
        val offsetX = ((measuredWidth-getViewSize())/2).toInt()
        val offsetY = ((measuredHeight-getViewSize())/2).toInt()
        fallbackBackground?.setBounds(offsetX+(getViewSize()*0.2).toInt(),offsetY+(getViewSize()*0.2).toInt(),offsetX+(getViewSize()-getViewSize()*0.2).toInt(),offsetY+(getViewSize()-getViewSize()*0.2).toInt())
        fallbackBackground?.setColorFilter(avatarForegroundColor, PorterDuff.Mode.MULTIPLY );
        fallbackBackground?.draw(canvas)
    }

    private fun setInitialsText(avatarBoundsRect: RectF, canvas: Canvas) {
        path.reset()

        when (tokenSystem.avatarStyle) {
            AvatarStyle.GROUP -> {
                val cornerRadius = tokenSystem.borderRadius
                path.addRoundRect(RectF(avatarBoundsRect), cornerRadius, cornerRadius, Path.Direction.CW)
            }
            else -> {
                setPresenceIcon(avatarBoundsRect, canvas)
                path.addCircle(avatarBoundsRect.width() / 2f + avatarBoundsRect.left, avatarBoundsRect.height() / 2f + avatarBoundsRect.top, (avatarBoundsRect.width() - avatarRingInnerThickness) / 2f, Path.Direction.CW)
                setRing(avatarBoundsRect, canvas)
            }
        }
        paint.color = avatarBackgroundColor
        paint.style = Paint.Style.FILL
        canvas.drawPath(path, paint)
        // Draw Initials
        textPaint.color = avatarForegroundColor
        textPaint.textSize = getTextSizeFromFont()
        textPaint.textAlign = Paint.Align.CENTER

        val xPos = measuredWidth / 2f
        val yPos = (measuredHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f)
        canvas.drawText(initialString, xPos, yPos, textPaint)
    }

    private fun getTextSizeFromFont(): Float {
        // The attributes you want retrieved
        val attrs = intArrayOf(android.R.attr.textSize)

        val ta: TypedArray = context.obtainStyledAttributes(tokenSystem.textFont, attrs)
        val textSize = ta.getDimensionPixelSize(0, 20)
        ta.recycle()
        return textSize.toFloat()
    }

    /**
     * returns the [AvatarViewSize] including the border width
     */
    fun getViewSize(): Float {
        return tokenSystem.size.toInt() + 2 * getViewBorderSize()
    }

    private fun getViewBorderSize(): Float {
        return avatarRingThickness + avatarRingOuterThickness
    }
}