package com.microsoft.fluentui.actionbar

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import kotlin.math.abs

class Indicator {

    private var itemLength = 0F
    private var itemSize = 0F
    private var itemPadding = 0F

    private val bounds = Rect()
    private var maxDisplayedItems = 3

    private val paint = Paint()
    private var colorFocused = 0
    private var colorBackground = 0

    private var displayMetric: DisplayMetrics? = null

    init {
        paint.strokeCap = Paint.Cap.ROUND
        paint.isAntiAlias = true
    }

    fun getWidth(items: Int): Int {
        val actualItems = maxDisplayedItems.coerceAtMost(items)
        return if (actualItems == 0) 0 else (actualItems * itemLength + (actualItems) * itemPadding).toInt()
    }

    fun getHeight(): Int {
        return itemSize.toInt()
    }

    fun setBounds(width: Int, height: Int) {
        bounds[0, 0, width] = height
    }

    private fun getBounds(): Rect {
        return bounds
    }

    fun draw(canvas: Canvas, items: Int, active: Int, progress: Float) {
        val nonAnimatedOffset = maxDisplayedItems / 2
        val isAnimating = maxDisplayedItems < items && active >= nonAnimatedOffset && active < items - nonAnimatedOffset - 1
        val animationOffset: Float
        animationOffset = if (isAnimating) {
            (itemLength + itemPadding) * -progress
        } else {
            0f
        }
        val bounds = getBounds()
        val maxPossibleItems = (1 + (bounds.width() - itemLength) / (itemLength + itemPadding)).toInt()
        val itemsToDraw = maxPossibleItems.coerceAtMost(maxDisplayedItems.coerceAtMost(items))
        val itemMeasuredWidth = getWidth(itemsToDraw)
        val itemMeasuredHeight = getHeight()
        val offsetX = (bounds.width() - itemMeasuredWidth) / 2
        val offsetY = bounds.height() - itemMeasuredHeight
        val saveBackground = canvas.save()
        canvas.translate(offsetX + animationOffset, offsetY.toFloat())
        drawBackground(canvas, progress, isAnimating, itemsToDraw)
        canvas.restoreToCount(saveBackground)
        val saveActive = canvas.save()
        canvas.translate(offsetX + animationOffset, offsetY.toFloat())
        val activeItem = getNormalizedActiveItem(items, active, nonAnimatedOffset)
        drawActiveIndicator(canvas, progress, activeItem)
        canvas.restoreToCount(saveActive)
    }

    private fun drawBackground(canvas: Canvas, progress: Float, isAnimating: Boolean, itemsToDraw: Int) {
        drawIndicator(canvas, if (isAnimating) 1 - progress else 1f)
        canvas.translate(itemLength + itemPadding, 0f)
        for (i in 1 until itemsToDraw) {
            drawIndicator(canvas, 1f)
            canvas.translate(itemLength + itemPadding, 0f)
        }
        if (isAnimating && progress > 0f) {
            drawIndicator(canvas, progress)
        }
    }

    private fun getNormalizedActiveItem(items: Int, active: Int, nonAnimatedOffset: Int): Int {
        return when {
            active < nonAnimatedOffset -> {
                active
            }
            items - nonAnimatedOffset <= active -> {
                maxDisplayedItems - (items - active)
            }
            else -> {
                nonAnimatedOffset
            }
        }
    }

    private fun drawActiveIndicator(canvas: Canvas, progress: Float) {
        paint.color = colorFocused
        paint.style = Paint.Style.FILL

        val radius = if (progress < 0) abs(progress) * itemSize / 2f
        else (1 - progress) * itemSize / 2f

        canvas.drawCircle(
                (itemPadding + itemLength) / 2f,
                itemSize / 2f,
                radius,
                paint
        )
    }

    private fun drawActiveIndicator(canvas: Canvas, progress: Float, activeItem: Int) {
        canvas.translate(activeItem * itemLength + activeItem * itemPadding, 0f)
        drawActiveIndicator(canvas, progress)
        if (progress != 0f) {
            canvas.translate(itemLength + itemPadding, 0f)
            drawActiveIndicator(canvas, -progress)
        }
    }

    private fun drawIndicator(canvas: Canvas, visibility: Float) {
        paint.color = colorBackground
        paint.strokeWidth = itemSize
        paint.alpha = (visibility * Color.alpha(colorBackground)).toInt()
        paint.style = Paint.Style.FILL

        val radius = itemSize / 2f

        canvas.drawCircle(
                (itemPadding + itemLength) / 2f,
                itemSize / 2f,
                radius,
                paint
        )
    }

    fun setItemSize(size: Int){ this.itemSize = getDisplayUnit(size.toFloat()) }

    fun setItemLength(length: Float) { this.itemLength = getDisplayUnit(length) }

    fun setItemPadding(padding: Float) { this.itemPadding = getDisplayUnit(padding) }

    fun setMaxDisplayedItems(max: Int) { maxDisplayedItems = max }

    fun setColorBackground(colorBackground: Int) { this.colorBackground = colorBackground }

    fun setColorFocused(colorFocused: Int) { this.colorFocused = colorFocused }

    fun setDisplayMetrics(displayMetrics: DisplayMetrics) { this.displayMetric = displayMetrics}

    private fun getDisplayUnit(data: Float): Float{ return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, data, displayMetric!!) }
}