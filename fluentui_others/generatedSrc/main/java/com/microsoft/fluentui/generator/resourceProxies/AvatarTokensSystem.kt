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


open class AvatarTokensSystem(val context: Context, val resources: Resources) : GenericProxy(context, resources) {

var avatarSize: AvatarSize = AvatarSize.X_SMALL
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var avatarStyle: AvatarStyle = AvatarStyle.BASE
	set(value) {
		if (field == value) return
		field = value
		updateProxy()
	}

var backgroundDefaultColor: Int = 0
var borderRadius: Float = 0f
var foregroundDefaultColor: Int = 0
var presenceIconSize: Float = 0f
var presenceIconOutlineColor: Int = 0
var presenceIconOutlineThickness: Float = 0f
var ringDefaultColor: Int = 0
var ringGapColor: Int = 0
var ringInnerGap: Float = 0f
var ringThickness: Float = 0f
var ringOuterGap: Float = 0f
var size: Float = 0f
var textCalculatedBackgroundColors: Int = 0
var textCalculatedForegroundColors: Int = 0
var textFont: Int = 0

private fun updateProxy(){
	val appearProxy  = when(avatarStyle) {
		AvatarStyle.BASE-> AvatarTokensProxy(context,resources)
		AvatarStyle.ACCENT-> AccentAvatarTokensProxy(context,resources)
		AvatarStyle.GROUP-> GroupAvatarTokensProxy(context,resources)
		AvatarStyle.OUTLINED-> OutlinedAvatarTokensProxy(context,resources)
		AvatarStyle.OUTLINED_PRIMARY-> OutlinedPrimaryAvatarTokensProxy(context,resources)
		AvatarStyle.OVERFLOW-> OverflowAvatarTokensProxy(context,resources)
	}
	backgroundDefaultColor  = when(avatarStyle) {
		AvatarStyle.BASE-> ContextCompat.getColor(context, R.color.avatartokensview_backgroundDefaultColor)
		AvatarStyle.ACCENT-> ContextCompat.getColor(context, R.color.accentavatartokensview_backgroundDefaultColor)
		AvatarStyle.GROUP-> ContextCompat.getColor(context, R.color.groupavatartokensview_backgroundDefaultColor)
		AvatarStyle.OUTLINED-> ContextCompat.getColor(context, R.color.outlinedavatartokensview_backgroundDefaultColor)
		AvatarStyle.OUTLINED_PRIMARY-> ContextCompat.getColor(context, R.color.outlinedprimaryavatartokensview_backgroundDefaultColor)
		AvatarStyle.OVERFLOW-> ContextCompat.getColor(context, R.color.overflowavatartokensview_backgroundDefaultColor)
	}
	borderRadius  = when(avatarSize) {
		AvatarSize.X_SMALL-> appearProxy.borderRadius_xSmall
		AvatarSize.SMALL-> appearProxy.borderRadius_small
		AvatarSize.MEDIUM-> appearProxy.borderRadius_medium
		AvatarSize.LARGE-> appearProxy.borderRadius_large
		AvatarSize.XLARGE-> appearProxy.borderRadius_xlarge
		AvatarSize.XXLARGE-> appearProxy.borderRadius_xxlarge
	}
	foregroundDefaultColor  = when(avatarStyle) {
		AvatarStyle.BASE-> ContextCompat.getColor(context, R.color.avatartokensview_foregroundDefaultColor)
		AvatarStyle.ACCENT-> ContextCompat.getColor(context, R.color.accentavatartokensview_foregroundDefaultColor)
		AvatarStyle.GROUP-> ContextCompat.getColor(context, R.color.groupavatartokensview_foregroundDefaultColor)
		AvatarStyle.OUTLINED-> ContextCompat.getColor(context, R.color.outlinedavatartokensview_foregroundDefaultColor)
		AvatarStyle.OUTLINED_PRIMARY-> ContextCompat.getColor(context, R.color.outlinedprimaryavatartokensview_foregroundDefaultColor)
		AvatarStyle.OVERFLOW-> ContextCompat.getColor(context, R.color.overflowavatartokensview_foregroundDefaultColor)
	}
	presenceIconSize  = when(avatarSize) {
		AvatarSize.X_SMALL-> appearProxy.presenceIconSize_xSmall
		AvatarSize.SMALL-> appearProxy.presenceIconSize_small
		AvatarSize.MEDIUM-> appearProxy.presenceIconSize_medium
		AvatarSize.LARGE-> appearProxy.presenceIconSize_large
		AvatarSize.XLARGE-> appearProxy.presenceIconSize_xlarge
		AvatarSize.XXLARGE-> appearProxy.presenceIconSize_xxlarge
	}
	presenceIconOutlineColor  = when(avatarStyle) {
		AvatarStyle.BASE-> ContextCompat.getColor(context, R.color.avatartokensview_presenceIconOutlineColor)
		AvatarStyle.ACCENT-> ContextCompat.getColor(context, R.color.accentavatartokensview_presenceIconOutlineColor)
		AvatarStyle.GROUP-> ContextCompat.getColor(context, R.color.groupavatartokensview_presenceIconOutlineColor)
		AvatarStyle.OUTLINED-> ContextCompat.getColor(context, R.color.outlinedavatartokensview_presenceIconOutlineColor)
		AvatarStyle.OUTLINED_PRIMARY-> ContextCompat.getColor(context, R.color.outlinedprimaryavatartokensview_presenceIconOutlineColor)
		AvatarStyle.OVERFLOW-> ContextCompat.getColor(context, R.color.overflowavatartokensview_presenceIconOutlineColor)
	}
	presenceIconOutlineThickness  = when(avatarSize) {
		AvatarSize.X_SMALL-> appearProxy.presenceIconOutlineThickness_xSmall
		AvatarSize.SMALL-> appearProxy.presenceIconOutlineThickness_small
		AvatarSize.MEDIUM-> appearProxy.presenceIconOutlineThickness_medium
		AvatarSize.LARGE-> appearProxy.presenceIconOutlineThickness_large
		AvatarSize.XLARGE-> appearProxy.presenceIconOutlineThickness_xlarge
		AvatarSize.XXLARGE-> appearProxy.presenceIconOutlineThickness_xxlarge
	}
	ringDefaultColor  = when(avatarStyle) {
		AvatarStyle.BASE-> ContextCompat.getColor(context, R.color.avatartokensview_ringDefaultColor)
		AvatarStyle.ACCENT-> ContextCompat.getColor(context, R.color.accentavatartokensview_ringDefaultColor)
		AvatarStyle.GROUP-> ContextCompat.getColor(context, R.color.groupavatartokensview_ringDefaultColor)
		AvatarStyle.OUTLINED-> ContextCompat.getColor(context, R.color.outlinedavatartokensview_ringDefaultColor)
		AvatarStyle.OUTLINED_PRIMARY-> ContextCompat.getColor(context, R.color.outlinedprimaryavatartokensview_ringDefaultColor)
		AvatarStyle.OVERFLOW-> ContextCompat.getColor(context, R.color.overflowavatartokensview_ringDefaultColor)
	}
	ringGapColor  = when(avatarStyle) {
		AvatarStyle.BASE-> ContextCompat.getColor(context, R.color.avatartokensview_ringGapColor)
		AvatarStyle.ACCENT-> ContextCompat.getColor(context, R.color.accentavatartokensview_ringGapColor)
		AvatarStyle.GROUP-> ContextCompat.getColor(context, R.color.groupavatartokensview_ringGapColor)
		AvatarStyle.OUTLINED-> ContextCompat.getColor(context, R.color.outlinedavatartokensview_ringGapColor)
		AvatarStyle.OUTLINED_PRIMARY-> ContextCompat.getColor(context, R.color.outlinedprimaryavatartokensview_ringGapColor)
		AvatarStyle.OVERFLOW-> ContextCompat.getColor(context, R.color.overflowavatartokensview_ringGapColor)
	}
	ringInnerGap  = when(avatarSize) {
		AvatarSize.X_SMALL-> appearProxy.ringInnerGap_xSmall
		AvatarSize.SMALL-> appearProxy.ringInnerGap_small
		AvatarSize.MEDIUM-> appearProxy.ringInnerGap_medium
		AvatarSize.LARGE-> appearProxy.ringInnerGap_large
		AvatarSize.XLARGE-> appearProxy.ringInnerGap_xlarge
		AvatarSize.XXLARGE-> appearProxy.ringInnerGap_xxlarge
	}
	ringThickness  = when(avatarSize) {
		AvatarSize.X_SMALL-> appearProxy.ringThickness_xSmall
		AvatarSize.SMALL-> appearProxy.ringThickness_small
		AvatarSize.MEDIUM-> appearProxy.ringThickness_medium
		AvatarSize.LARGE-> appearProxy.ringThickness_large
		AvatarSize.XLARGE-> appearProxy.ringThickness_xlarge
		AvatarSize.XXLARGE-> appearProxy.ringThickness_xxlarge
	}
	ringOuterGap  = when(avatarSize) {
		AvatarSize.X_SMALL-> appearProxy.ringOuterGap_xSmall
		AvatarSize.SMALL-> appearProxy.ringOuterGap_small
		AvatarSize.MEDIUM-> appearProxy.ringOuterGap_medium
		AvatarSize.LARGE-> appearProxy.ringOuterGap_large
		AvatarSize.XLARGE-> appearProxy.ringOuterGap_xlarge
		AvatarSize.XXLARGE-> appearProxy.ringOuterGap_xxlarge
	}
	size  = when(avatarSize) {
		AvatarSize.X_SMALL-> appearProxy.size_xSmall
		AvatarSize.SMALL-> appearProxy.size_small
		AvatarSize.MEDIUM-> appearProxy.size_medium
		AvatarSize.LARGE-> appearProxy.size_large
		AvatarSize.XLARGE-> appearProxy.size_xlarge
		AvatarSize.XXLARGE-> appearProxy.size_xxlarge
	}
	textCalculatedBackgroundColors  = appearProxy.textCalculatedBackgroundColors
	textCalculatedForegroundColors  = appearProxy.textCalculatedForegroundColors
	textFont  = when(avatarSize) {
		AvatarSize.X_SMALL-> appearProxy.textFont_xSmall
		AvatarSize.SMALL-> appearProxy.textFont_small
		AvatarSize.MEDIUM-> appearProxy.textFont_medium
		AvatarSize.LARGE-> appearProxy.textFont_large
		AvatarSize.XLARGE-> appearProxy.textFont_xlarge
		AvatarSize.XXLARGE-> appearProxy.textFont_xxlarge
	}
}
init {
	updateProxy()
}

}