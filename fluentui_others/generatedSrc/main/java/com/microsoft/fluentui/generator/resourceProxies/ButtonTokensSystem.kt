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


open class ButtonTokensSystem(val context: Context, val resources: Resources) : GenericProxy(context, resources) {

var buttonSize: ButtonSize = ButtonSize.SMALL
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var buttonStyle: ButtonStyle = ButtonStyle.PRIMARY
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var backgroundColor: Int = 0
var borderColor: Int = 0
var borderRadius: Float = 0f
var borderSize: Float = 0f
var iconColor: Int = 0
var iconSize: Float = 0f
var interspace: Float = 0f
var paddingHorizontal: Float = 0f
var paddingVertical: Float = 0f
var textColor: Int = 0
var textFont: Int = 0
var background: Drawable? = null

private fun updateProxy(){
	val appearProxy  = when(buttonStyle) {
		ButtonStyle.PRIMARY-> PrimaryButtonTokensProxy(context,resources)
		ButtonStyle.SECONDARY-> SecondaryButtonTokensProxy(context,resources)
		ButtonStyle.GHOST-> GhostButtonTokensProxy(context,resources)
	}
	backgroundColor  = when(buttonStyle) {
		ButtonStyle.PRIMARY-> ContextCompat.getColor(context, R.color.primarybuttontokensview_backgroundcolor)
		ButtonStyle.SECONDARY-> ContextCompat.getColor(context, R.color.secondarybuttontokensview_backgroundcolor)
		ButtonStyle.GHOST-> ContextCompat.getColor(context, R.color.ghostbuttontokensview_backgroundcolor)
	}
	borderColor  = when(buttonStyle) {
		ButtonStyle.PRIMARY-> ContextCompat.getColor(context, R.color.primarybuttontokensview_bordercolor)
		ButtonStyle.SECONDARY-> ContextCompat.getColor(context, R.color.secondarybuttontokensview_bordercolor)
		ButtonStyle.GHOST-> ContextCompat.getColor(context, R.color.ghostbuttontokensview_bordercolor)
	}
	borderRadius  = when(buttonSize) {
		ButtonSize.SMALL-> appearProxy.borderRadius_small
		ButtonSize.MEDIUM-> appearProxy.borderRadius_medium
		ButtonSize.LARGE-> appearProxy.borderRadius_large
	}
	borderSize  = when(buttonSize) {
		ButtonSize.SMALL-> appearProxy.borderSize_small
		ButtonSize.MEDIUM-> appearProxy.borderSize_medium
		ButtonSize.LARGE-> appearProxy.borderSize_large
	}
	iconColor  = when(buttonStyle) {
		ButtonStyle.PRIMARY-> ContextCompat.getColor(context, R.color.primarybuttontokensview_iconcolor)
		ButtonStyle.SECONDARY-> ContextCompat.getColor(context, R.color.secondarybuttontokensview_iconcolor)
		ButtonStyle.GHOST-> ContextCompat.getColor(context, R.color.ghostbuttontokensview_iconcolor)
	}
	iconSize  = when(buttonSize) {
		ButtonSize.SMALL-> appearProxy.iconSize_small
		ButtonSize.MEDIUM-> appearProxy.iconSize_medium
		ButtonSize.LARGE-> appearProxy.iconSize_large
	}
	interspace  = when(buttonSize) {
		ButtonSize.SMALL-> appearProxy.interspace_small
		ButtonSize.MEDIUM-> appearProxy.interspace_medium
		ButtonSize.LARGE-> appearProxy.interspace_large
	}
	paddingHorizontal  = when(buttonSize) {
		ButtonSize.SMALL-> appearProxy.paddingHorizontal_small
		ButtonSize.MEDIUM-> appearProxy.paddingHorizontal_medium
		ButtonSize.LARGE-> appearProxy.paddingHorizontal_large
	}
	paddingVertical  = when(buttonSize) {
		ButtonSize.SMALL-> appearProxy.paddingVertical_small
		ButtonSize.MEDIUM-> appearProxy.paddingVertical_medium
		ButtonSize.LARGE-> appearProxy.paddingVertical_large
	}
	textColor  = when(buttonStyle) {
		ButtonStyle.PRIMARY-> ContextCompat.getColor(context, R.color.primarybuttontokensview_textcolor)
		ButtonStyle.SECONDARY-> ContextCompat.getColor(context, R.color.secondarybuttontokensview_textcolor)
		ButtonStyle.GHOST-> ContextCompat.getColor(context, R.color.ghostbuttontokensview_textcolor)
	}
	textFont  = when(buttonSize) {
		ButtonSize.SMALL-> appearProxy.textFont_small
		ButtonSize.MEDIUM-> appearProxy.textFont_medium
		ButtonSize.LARGE-> appearProxy.textFont_large
	}
	background  = when(buttonSize) {
		ButtonSize.SMALL-> appearProxy.background_small
		ButtonSize.MEDIUM-> appearProxy.background_medium
		ButtonSize.LARGE-> appearProxy.background_large
	}
}
init {
	updateProxy()
}

}