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
import com.microsoft.fluentui.drawer.R


open class DrawerTokensSystem(val context: Context, val resources: Resources) : GenericProxy(context, resources) {

var drawerType: DrawerType = DrawerType.LEFT_NAV
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var drawerBehavior: DrawerBehavior = DrawerBehavior.FULL
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var backgroundColor: Int = 0
var backgroundDimmedColor: Int = 0
var elevation: Float = 0f
var corners_topLeft: Float = 0f
var corners_topRight: Float = 0f
var corners_bottomLeft: Float = 0f
var corners_bottomRight: Float = 0f
var shadow1Color: Int = 0
var shadow1Blur: Float = 0f
var shadow1OffsetX: Float = 0f
var shadow1OffsetY: Float = 0f
var shadow2Color: Int = 0
var shadow2Blur: Float = 0f
var shadow2OffsetX: Float = 0f
var shadow2OffsetY: Float = 0f

private fun updateProxy(){
	val appearProxy  = DrawerTokensProxy(context,resources)
	backgroundColor  = ContextCompat.getColor(context, R.color.drawertokensview_backgroundColor)
	backgroundDimmedColor  = ContextCompat.getColor(context, R.color.drawertokensview_backgroundDimmedColor)
	elevation  = appearProxy.elevation
	corners_topLeft  = appearProxy.corners_topLeft
	corners_topRight  = appearProxy.corners_topRight
	corners_bottomLeft  = appearProxy.corners_bottomLeft
	corners_bottomRight  = appearProxy.corners_bottomRight
	shadow1Color  = ContextCompat.getColor(context, R.color.drawertokensview_shadow1Color)
	shadow1Blur  = appearProxy.shadow1Blur
	shadow1OffsetX  = appearProxy.shadow1OffsetX
	shadow1OffsetY  = appearProxy.shadow1OffsetY
	shadow2Color  = ContextCompat.getColor(context, R.color.drawertokensview_shadow2Color)
	shadow2Blur  = appearProxy.shadow2Blur
	shadow2OffsetX  = appearProxy.shadow2OffsetX
	shadow2OffsetY  = appearProxy.shadow2OffsetY
}
init {
	updateProxy()
}

}