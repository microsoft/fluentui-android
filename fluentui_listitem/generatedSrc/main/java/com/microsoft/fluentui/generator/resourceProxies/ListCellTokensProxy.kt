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


open class ListCellTokensProxy(context: Context, resources: Resources) : GenericProxy(context, resources) {
	 open var backgroundColor: Int = ContextCompat.getColor(context, R.color.listcelltokensview_backgroundColor)
	 open var borderColor: Int = ContextCompat.getColor(context, R.color.listcelltokensview_borderColor)
	 open var borderSize: Float = resources.getDimension(R.dimen.listcelltokensview_borderSize)
	 open var labelMargin_oneLine: Float = resources.getDimension(R.dimen.listcelltokensview_labelMargin_oneLine)
	 open var labelMargin_twoLines: Float = resources.getDimension(R.dimen.listcelltokensview_labelMargin_twoLines)
	 open var labelMargin_threeLines: Float = resources.getDimension(R.dimen.listcelltokensview_labelMargin_threeLines)
	 open var accessoryViewInterspace: Float = resources.getDimension(R.dimen.listcelltokensview_accessoryViewInterspace)
	 open var horizontalCellPadding: Float = resources.getDimension(R.dimen.listcelltokensview_horizontalCellPadding)
	 open var labelColor: Int = ContextCompat.getColor(context, R.color.listcelltokensview_labelColor)
	 open var labelFont: Int = R.style.listcelltokensview_labelFont
	 open var leadingViewColor: Int = ContextCompat.getColor(context, R.color.listcelltokensview_leadingViewColor)
	 open var leadingViewSize_small: Float = resources.getDimension(R.dimen.listcelltokensview_leadingViewSize_small)
	 open var leadingViewSize_medium: Float = resources.getDimension(R.dimen.listcelltokensview_leadingViewSize_medium)
	 open var leadingViewSize_large: Float = resources.getDimension(R.dimen.listcelltokensview_leadingViewSize_large)
	 open var leadingViewInterspace_small: Float = resources.getDimension(R.dimen.listcelltokensview_leadingViewInterspace_small)
	 open var leadingViewInterspace_medium: Float = resources.getDimension(R.dimen.listcelltokensview_leadingViewInterspace_medium)
	 open var leadingViewInterspace_large: Float = resources.getDimension(R.dimen.listcelltokensview_leadingViewInterspace_large)
	 open var sublabelColor: Int = ContextCompat.getColor(context, R.color.listcelltokensview_sublabelColor)
	 open var sublabelFont: Int = R.style.listcelltokensview_sublabelFont
	 open var background_listcelltokensview: Drawable? = ContextCompat.getDrawable(context, R.drawable.listcelltokensview_background)
}