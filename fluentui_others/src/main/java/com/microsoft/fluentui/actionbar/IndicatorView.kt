package com.microsoft.fluentui.actionbar

import android.content.Context
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.microsoft.fluentui.R

class IndicatorView(context: Context, attrs: AttributeSet?) : View(context, attrs, 0, 0) {
    private var itemCount = 5
    private var currentPosition = 0
    private var currentPositionOffset = 0f
    private val indicator = Indicator()

    init {
        indicator.setDisplayMetrics(resources.displayMetrics)
        customizeIndicatorDimens(5,5F, 5F)
        indicator.setColorBackground(ContextCompat.getColor(context, R.color.fluentui_action_bar_indicator_background))
        indicator.setColorFocused(ContextCompat.getColor(context, R.color.fluentui_white))
        indicator.setMaxDisplayedItems(itemCount+1)
    }


    fun setItemCount(count: Int){
        this.itemCount = count
        invalidate()
    }

    fun setCurrentPosition(position: Int){
        this.currentPosition = position
        invalidate()
    }

    fun onPageScrolled(position: Int, positionOffset: Float){
        this.currentPosition = position
        this.currentPositionOffset =  positionOffset
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val width: Int
        width = when (widthMode) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> (indicator.getWidth(itemCount) + paddingLeft + paddingRight).coerceAtMost(MeasureSpec.getSize(widthMeasureSpec))
            else -> throw IllegalArgumentException()
        }
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val height: Int
        height = when (heightMode) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
            MeasureSpec.AT_MOST, MeasureSpec.UNSPECIFIED -> (indicator.getHeight() + paddingTop + paddingBottom).coerceAtMost(MeasureSpec.getSize(heightMeasureSpec))
            else -> throw IllegalArgumentException()
        }
        this.setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        indicator.setBounds(
                width - paddingLeft - paddingRight,
                height - paddingTop - paddingBottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val save = canvas.save()
        canvas.translate(paddingLeft.toFloat(), paddingTop.toFloat())
        indicator.draw(canvas, itemCount, currentPosition, currentPositionOffset)
        canvas.restoreToCount(save)
    }

    fun customizeIndicatorDimens(size: Int, length:Float, padding:Float){
        indicator.setItemSize(size)
        indicator.setItemLength(length)
        indicator.setItemPadding(padding)
        invalidate()
    }

}