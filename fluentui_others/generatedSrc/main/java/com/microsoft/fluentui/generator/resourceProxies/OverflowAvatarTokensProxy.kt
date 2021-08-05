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


open class OverflowAvatarTokensProxy(context: Context, resources: Resources) : AvatarTokensProxy(context, resources) {
	 override var backgroundDefaultColor: Int = ContextCompat.getColor(context, R.color.overflowavatartokensview_backgroundDefaultColor)
	 override var foregroundDefaultColor: Int = ContextCompat.getColor(context, R.color.overflowavatartokensview_foregroundDefaultColor)
	 open var textColor: Int = ContextCompat.getColor(context, R.color.overflowavatartokensview_textColor)
	 override var ringDefaultColor: Int = ContextCompat.getColor(context, R.color.overflowavatartokensview_ringDefaultColor)
}