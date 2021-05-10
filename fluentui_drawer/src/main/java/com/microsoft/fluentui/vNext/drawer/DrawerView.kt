package com.microsoft.fluentui.vNext.drawer

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.generator.AvatarSize
import com.microsoft.fluentui.generator.DrawerType
import com.microsoft.fluentui.generator.resourceProxies.DrawerTokensSystem
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper

internal class DrawerView(appContext: Context, attrs: AttributeSet) : LinearLayoutCompat(
    FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Drawer), attrs) {
    companion object {
        private const val RADII_SIZE = 8
    }

    private val clipPath = Path()
    private var drawerType: DrawerType
    private var tokensSystem: DrawerTokensSystem

    init {
        val a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.DrawerTokensView)
        val drawerTypeOrdinal = a.getInt(R.styleable.DrawerTokensView_fluentUI_drawerType, DrawerType.LEFT_NAV.ordinal)
        drawerType = DrawerType.values()[drawerTypeOrdinal]
        tokensSystem = DrawerTokensSystem(context,context.resources)
        tokensSystem.drawerType = drawerType
        a.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setValues()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setValues()
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

    private fun setValues() {
        elevation = tokensSystem.elevation
        setBackgroundColor(tokensSystem.backgroundColor)
    }

    private fun updateClipPath(width: Float, height: Float) {

        val radii = FloatArray(RADII_SIZE){0f}
        // top left corner
        radii[0] = tokensSystem.corners_topLeft
        radii[1] = tokensSystem.corners_topLeft
        // top right corner
        radii[2] = tokensSystem.corners_topRight
        radii[3] = tokensSystem.corners_topRight
        // bottom right corner
        radii[4] = tokensSystem.corners_bottomRight
        radii[5] = tokensSystem.corners_bottomRight
        // bottom left corner
        radii[6] = tokensSystem.corners_bottomLeft
        radii[7] = tokensSystem.corners_bottomLeft

        clipPath.reset()
        clipPath.addRoundRect(RectF(0f, 0f, width, height), radii, Path.Direction.CW)
        clipPath.close()
    }
}