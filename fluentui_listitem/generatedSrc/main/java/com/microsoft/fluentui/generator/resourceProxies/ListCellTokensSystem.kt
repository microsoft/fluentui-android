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


open class ListCellTokensSystem(val context: Context, val resources: Resources) : GenericProxy(context, resources) {

var listLeadingViewSize: ListLeadingViewSize = ListLeadingViewSize.SMALL
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var listCellType: ListCellType = ListCellType.ONE_LINE
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var backgroundColor: Int = 0
var borderColor: Int = 0
var borderSize: Float = 0f
var labelMargin: Float = 0f
var accessoryViewInterspace: Float = 0f
var horizontalCellPadding: Float = 0f
var labelColor: Int = 0
var labelFont: Int = 0
var leadingViewColor: Int = 0
var leadingViewSize: Float = 0f
var leadingViewInterspace: Float = 0f
var sublabelColor: Int = 0
var sublabelFont: Int = 0
var background_listcelltokensview: Drawable? = null

private fun updateProxy(){
	val appearProxy  = ListCellTokensProxy(context,resources)
	backgroundColor  = ContextCompat.getColor(context, R.color.listcelltokensview_backgroundColor)
	borderColor  = ContextCompat.getColor(context, R.color.listcelltokensview_borderColor)
	borderSize  = appearProxy.borderSize
	labelMargin  = when(listCellType) {
		ListCellType.ONE_LINE-> appearProxy.labelMargin_oneLine
		ListCellType.TWO_LINES-> appearProxy.labelMargin_twoLines
		ListCellType.THREE_LINES-> appearProxy.labelMargin_threeLines
	}
	accessoryViewInterspace  = appearProxy.accessoryViewInterspace
	horizontalCellPadding  = appearProxy.horizontalCellPadding
	labelColor  = ContextCompat.getColor(context, R.color.listcelltokensview_labelColor)
	labelFont  = appearProxy.labelFont
	leadingViewColor  = ContextCompat.getColor(context, R.color.listcelltokensview_leadingViewColor)
	leadingViewSize  = when(listLeadingViewSize) {
		ListLeadingViewSize.SMALL-> appearProxy.leadingViewSize_small
		ListLeadingViewSize.MEDIUM-> appearProxy.leadingViewSize_medium
		ListLeadingViewSize.LARGE-> appearProxy.leadingViewSize_large
	}
	leadingViewInterspace  = when(listLeadingViewSize) {
		ListLeadingViewSize.SMALL-> appearProxy.leadingViewInterspace_small
		ListLeadingViewSize.MEDIUM-> appearProxy.leadingViewInterspace_medium
		ListLeadingViewSize.LARGE-> appearProxy.leadingViewInterspace_large
	}
	sublabelColor  = ContextCompat.getColor(context, R.color.listcelltokensview_sublabelColor)
	sublabelFont  = appearProxy.sublabelFont
	background_listcelltokensview  = appearProxy.background_listcelltokensview
}
init {
	updateProxy()
}

}