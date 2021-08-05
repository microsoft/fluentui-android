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
import com.microsoft.fluentui.listitem.R


open class ListHeaderTokensProxy(context: Context, resources: Resources) : GenericProxy(context, resources) {
	 open var backgroundColor: Int = ContextCompat.getColor(context, R.color.listheadertokensview_backgroundColor)
	 open var titleColor_rest: Int = ContextCompat.getColor(context, R.color.listheadertokensview_titleColor_rest)
	 open var titleColor_hover: Int = ContextCompat.getColor(context, R.color.listheadertokensview_titleColor_hover)
	 open var titleColor_pressed: Int = ContextCompat.getColor(context, R.color.listheadertokensview_titleColor_pressed)
	 open var titleColor_selected: Int = ContextCompat.getColor(context, R.color.listheadertokensview_titleColor_selected)
	 open var titleColor_disabled: Int = ContextCompat.getColor(context, R.color.listheadertokensview_titleColor_disabled)
	 open var actionColor: Int = ContextCompat.getColor(context, R.color.listheadertokensview_actionColor)
	 open var actionViewInterspace: Float = resources.getDimension(R.dimen.listheadertokensview_actionViewInterspace)
	 open var titleFont: Int = R.style.listheadertokensview_titleFont
	 open var actionFont: Int = R.style.listheadertokensview_actionFont
	 open var horizontalPadding: Float = resources.getDimension(R.dimen.listheadertokensview_horizontalPadding)
	 open var verticalPadding: Float = resources.getDimension(R.dimen.listheadertokensview_verticalPadding)
	 open var background: Drawable? = ContextCompat.getDrawable(context, R.drawable.listheadertokensview_background)
}