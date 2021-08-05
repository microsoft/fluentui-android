/**
 * Auto-generated code, all changes will be lost
 */
package com.microsoft.fluentui.generator.resourceProxies


import android.content.Context
import android.util.TypedValue
import android.content.res.Resources
import com.microsoft.fluentui.generator.*
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.R


open class SecondaryButtonTokensProxy(context: Context, resources: Resources) : ButtonTokensProxy(context, resources) {
	 override var borderColor_rest: Int = ContextCompat.getColor(context, R.color.secondarybuttontokensview_borderColor_rest)
	 override var borderColor_hover: Int = ContextCompat.getColor(context, R.color.secondarybuttontokensview_borderColor_hover)
	 override var borderColor_pressed: Int = ContextCompat.getColor(context, R.color.secondarybuttontokensview_borderColor_pressed)
	 override var borderColor_selected: Int = ContextCompat.getColor(context, R.color.secondarybuttontokensview_borderColor_selected)
	 override var borderColor_disabled: Int = ContextCompat.getColor(context, R.color.secondarybuttontokensview_borderColor_disabled)
	 override var borderSize_small: Float = resources.getDimension(R.dimen.secondarybuttontokensview_borderSize_small)
	 override var borderSize_medium: Float = resources.getDimension(R.dimen.secondarybuttontokensview_borderSize_medium)
	 override var borderSize_large: Float = resources.getDimension(R.dimen.secondarybuttontokensview_borderSize_large)
	 override var background_small: Drawable? = ContextCompat.getDrawable(context, R.drawable.secondarybuttontokensview_smallbackground)
	 override var background_medium: Drawable? = ContextCompat.getDrawable(context, R.drawable.secondarybuttontokensview_mediumbackground)
	 override var background_large: Drawable? = ContextCompat.getDrawable(context, R.drawable.secondarybuttontokensview_largebackground)
}