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


open class ListHeaderTokensSystem(val context: Context, val resources: Resources) : GenericProxy(context, resources) {

var listHeaderStyle: ListHeaderStyle = ListHeaderStyle.BASE
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var backgroundColor: Int = 0
var titleColor: Int = 0
var actionColor: Int = 0
var actionViewInterspace: Float = 0f
var titleFont: Int = 0
var actionFont: Int = 0
var horizontalPadding: Float = 0f
var verticalPadding: Float = 0f
var background: Drawable? = null

private fun updateProxy(){
	val appearProxy  = when(listHeaderStyle) {
		ListHeaderStyle.BASE-> ListHeaderTokensProxy(context,resources)
		ListHeaderStyle.ACCENT-> AccentListHeaderTokensProxy(context,resources)
	}
	backgroundColor  = when(listHeaderStyle) {
		ListHeaderStyle.BASE-> ContextCompat.getColor(context, R.color.listheadertokensview_backgroundColor)
		ListHeaderStyle.ACCENT-> ContextCompat.getColor(context, R.color.accentlistheadertokensview_backgroundColor)
	}
	titleColor  = when(listHeaderStyle) {
		ListHeaderStyle.BASE-> ContextCompat.getColor(context, R.color.listheadertokensview_titlecolor)
		ListHeaderStyle.ACCENT-> ContextCompat.getColor(context, R.color.accentlistheadertokensview_titlecolor)
	}
	actionColor  = when(listHeaderStyle) {
		ListHeaderStyle.BASE-> ContextCompat.getColor(context, R.color.listheadertokensview_actionColor)
		ListHeaderStyle.ACCENT-> ContextCompat.getColor(context, R.color.accentlistheadertokensview_actionColor)
	}
	actionViewInterspace  = appearProxy.actionViewInterspace
	titleFont  = appearProxy.titleFont
	actionFont  = appearProxy.actionFont
	horizontalPadding  = appearProxy.horizontalPadding
	verticalPadding  = appearProxy.verticalPadding
	background  = appearProxy.background
}
init {
	updateProxy()
}

}