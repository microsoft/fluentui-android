/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.drawer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import com.microsoft.fluentui.R

internal class DrawerView(context: Context, attrs: AttributeSet) : LinearLayoutCompat(context, attrs) {
    companion object {
        private const val RADII_SIZE = 8
    }

    private val clipPath = Path()

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

    private fun updateClipPath(width: Float, height: Float) {
        val cornerRadius = resources.getDimension(R.dimen.fluentui_drawer_corner_radius)
        val radii = FloatArray(RADII_SIZE)

        // top left corner
        radii[0] = cornerRadius
        radii[1] = cornerRadius

        // top right corner
        radii[2] = cornerRadius
        radii[3] = cornerRadius

        // bottom right corner
        radii[4] = 0f
        radii[5] = 0f

        // bottom left corner
        radii[6] = 0f
        radii[7] = 0f

        clipPath.reset()
        clipPath.addRoundRect(RectF(0f, 0f, width, height), radii, Path.Direction.CW)
        clipPath.close()
    }
}