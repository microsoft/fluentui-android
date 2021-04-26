/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.drawer

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import androidx.appcompat.widget.LinearLayoutCompat
import android.util.AttributeSet

internal class DrawerView(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {
    companion object {
        private const val RADII_SIZE = 8
    }

    enum class BehaviorType {
        BOTTOM, TOP, RIGHT, LEFT
    }

    private val clipPath = Path()
    private var cornerRadius: Float
    var behaviorType: BehaviorType

    init {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SheetBehaviorLayout)
        behaviorType = BehaviorType.valueOf(a.getString(R.styleable.SheetBehaviorLayout_behaviorType)
                ?: "BOTTOM")
        val drawerTypedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawerView)
        cornerRadius = drawerTypedArray.getDimension(R.styleable.DrawerView_cornerRadius, resources.getDimension(R.dimen.fluentui_drawer_corner_radius))
        drawerTypedArray.recycle()
        a.recycle()
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        updateClipPath(width.toFloat(), height.toFloat())
    }

    override fun draw(canvas: Canvas) {
        val save = canvas.save()
        canvas.clipPath(clipPath)
        super.draw(canvas)
        canvas.restoreToCount(save)
    }

    fun setCornerRadius(radius: Float) {
        cornerRadius = radius
        invalidate()
    }

    private fun updateClipPath(width: Float, height: Float) {

        val radii = FloatArray(RADII_SIZE) { 0f }

        when(behaviorType) {
            BehaviorType.BOTTOM -> {
                // top left corner
                radii[0] = cornerRadius
                radii[1] = cornerRadius

                // top right corner
                radii[2] = cornerRadius
                radii[3] = cornerRadius
            }
            BehaviorType.TOP -> {
                // bottom right corner
                radii[4] = cornerRadius
                radii[5] = cornerRadius

                // bottom left corner
                radii[6] = cornerRadius
                radii[7] = cornerRadius
            }
            else -> {
            }
        }

        clipPath.reset()
        clipPath.addRoundRect(RectF(0f, 0f, width, height), radii, Path.Direction.CW)
        clipPath.close()
    }
}