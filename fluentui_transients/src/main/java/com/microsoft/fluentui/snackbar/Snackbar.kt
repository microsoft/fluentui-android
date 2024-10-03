/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.snackbar

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.material.snackbar.BaseTransientBottomBar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatButton
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.microsoft.fluentui.transients.R
import com.microsoft.fluentui.transients.R.id.*
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.transients.databinding.ViewSnackbarBinding
import com.microsoft.fluentui.util.DuoSupportUtils
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.activity

/**
 * Snackbars provide lightweight feedback about an operation by showing a brief message at the bottom of the screen.
 * [Snackbar] can contain a custom action or use a style geared towards making special announcements to your users
 * in addition to custom text and duration.
 *
 * To use a Snackbar with a FAB, it is recommended that your parent layout be a CoordinatorLayout.
 */
class Snackbar : BaseTransientBottomBar<Snackbar> {
    companion object {
        const val LENGTH_INDEFINITE: Int = BaseTransientBottomBar.LENGTH_INDEFINITE
        const val LENGTH_SHORT: Int = BaseTransientBottomBar.LENGTH_SHORT
        const val LENGTH_LONG: Int = BaseTransientBottomBar.LENGTH_LONG

        /**
         * Use [make] to create your Snackbar and attach it to a given [view]'s parent.
         */
        @SuppressLint("WrongConstant")
        fun make(view: View, text: CharSequence, duration: Int = LENGTH_SHORT, style: Style = Style.REGULAR): Snackbar {
            val parent = findSuitableParent(view) ?:
                throw IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.")
            // Need the theme wrapper to avoid crashing in Dark theme.
            val content = LayoutInflater.from(FluentUIContextThemeWrapper(parent.context,R.style.Theme_FluentUI_Transients)).inflate(R.layout.view_snackbar, parent, false)
            val snackbar = Snackbar(parent, content, ContentViewCallback(content))
            snackbar.duration = duration
            snackbar.setStyle(style)
            snackbar.setText(text)
            return snackbar
        }

        /**
         * This is adapted from android.support.design.widget.Snackbar
         * It ensures we can use Snackbars in complex ViewGroups like RecyclerView.
         */
        private fun findSuitableParent(view: View): ViewGroup? {
            var currentView: View? = view
            var fallbackParent: ViewGroup? = null

            do {
                if (currentView is CoordinatorLayout)
                    // We've found a CoordinatorLayout, use it
                    return currentView

                if (currentView is FrameLayout)
                    if (currentView.id == android.R.id.content)
                        // If we've hit the decor content view, then we didn't find a CoL in the
                        // hierarchy, so use it.
                        return currentView
                    else
                        // It's not the content view but we'll use it as our fallback
                        fallbackParent = currentView

                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                currentView = currentView?.parent as? View
            } while (currentView != null)

            // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
            return fallbackParent
        }
    }

    /**
     * Defines the height and width applied to the Snackbar's [customView].
     */
    enum class CustomViewSize(private val id: Int) {
        SMALL(R.dimen.fluentui_snackbar_custom_view_size_small),
        MEDIUM(R.dimen.fluentui_snackbar_custom_view_size_medium);

        /**
         * This method uses [context] to convert the [id] resource into an Int that becomes
         * [Snackbar.customView]'s layout width and height
         */
        fun getDimension(context: Context): Int = context.resources.getDimension(id).toInt()
    }

    /**
     * Defines which style can be applied to the Snackbar.
     * Includes background color, text color, and action button placement.
     */
    enum class Style {
        REGULAR, ANNOUNCEMENT, PRIMARY, LIGHT, WARNING, DANGER
    }

    private val snackbarContainer: RelativeLayout
    private var customView: View? = null
    private val textView: TextView
    private val actionButtonView: AppCompatButton

    private var customViewSize: CustomViewSize = CustomViewSize.SMALL
    private var style: Style = Style.REGULAR

    private val customViewVerticalMargin: Int
        get() {
            val marginResourceId = if (style == Style.ANNOUNCEMENT)
                R.dimen.fluentui_snackbar_custom_view_margin_vertical_announcement
            else
                when(customViewSize) {
                    CustomViewSize.SMALL ->
                        R.dimen.fluentui_snackbar_custom_view_margin_vertical_small
                    CustomViewSize.MEDIUM ->
                        R.dimen.fluentui_snackbar_custom_view_margin_vertical_medium
                }
            return context.resources.getDimension(marginResourceId).toInt()
        }

    private constructor(parent: ViewGroup, content: View, contentViewCallback: ContentViewCallback) : super(parent, content, contentViewCallback) {
        val binding = ViewSnackbarBinding.bind(content)
        textView = binding.snackbarText
        snackbarContainer = binding.snackbarContainer
        actionButtonView = binding.snackbarAction

        updateBackground()
        // Set the margin on the FrameLayout (SnackbarLayout) instead of the content because the content's bottom margin is buggy in some APIs.
        if (content.parent is FrameLayout) {
            context.activity?.let {
                if(DuoSupportUtils.isWindowDoublePortrait(it)) {
                    val singleScreenDisplayPixels = DuoSupportUtils.getSingleScreenWidthPixels(it)
                    val snackbarLP  = getView().layoutParams
                    snackbarLP.width = singleScreenDisplayPixels
                    getView().layoutParams = snackbarLP
                    alignLeft(parent)
                }
            }
        }
    }

    /**
     * This is adapted from android.support.design.widget.Snackbar
     * It ensures we can use Snackbars in complex ViewGroups like RecyclerView.
     */
    private fun alignLeft(view: View) {
        var currentView: View? = view
        var fallbackParent: ViewGroup? = null

        do {
            if (currentView is CoordinatorLayout) {
                // We've found a CoordinatorLayout, use it
                val params = getView().layoutParams as CoordinatorLayout.LayoutParams
                params.gravity = Gravity.BOTTOM
                getView().layoutParams = params
                return
            }

            if (currentView is FrameLayout)
                if (currentView.id == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    val params = getView().layoutParams as FrameLayout.LayoutParams
                    params.gravity = Gravity.BOTTOM
                    view.layoutParams = params
                    return
                } else
                // It's not the content view but we'll use it as our fallback
                    fallbackParent = currentView

            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            currentView = currentView?.parent as? View
        } while (currentView != null)

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return
    }

    /**
     * Use [setText] to set or update text on a Snackbar.
     */
    fun setText(text: CharSequence): Snackbar {
        textView.text = text
        // Update style, but not background. Otherwise the background gets extra margins added when text is updated.
        updateStyle()
        return this
    }

    fun setTextColor(color: Int): Snackbar {
        textView.setTextColor(color)
        return this
    }

    fun setActionTextColor(color: Int): Snackbar {
        actionButtonView.setTextColor(color)
        return this
    }

    /**
     * Use [setAction] to add a button to your Snackbar to prompt a user action.
     */
    fun setAction(text: CharSequence, listener: View.OnClickListener): Snackbar {
        actionButtonView.text = text
        actionButtonView.visibility = View.VISIBLE
        actionButtonView.setOnClickListener { view ->
            listener.onClick(view)
            // dismiss the Snackbar
            dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION)
        }

        updateStyle()

        return this
    }

    /**
     * Use [setCustomView] to add or update an icon or other custom view at the start of the Snackbar.
     */
    fun setCustomView(customView: View?, customViewSize: CustomViewSize = CustomViewSize.SMALL): Snackbar {
        snackbarContainer.removeView(this.customView)

        this.customView = customView
        this.customView?.id = fluentui_snackbar_custom_view
        this.customViewSize = customViewSize

        updateCustomViewLayoutParams()

        if (this.customView != null)
            snackbarContainer.addView(this.customView, 0)

        updateStyle()

        return this
    }

    /**
     * Defines which [Style] is applied to the Snackbar.
     */
    fun setStyle(style: Style): Snackbar {
        if (this.style == style)
            return this

        this.style = style
        updateStyle()
        updateBackground()
        return this
    }

    private fun updateCustomViewLayoutParams() {
        val size = customViewSize.getDimension(context)
        val lp = RelativeLayout.LayoutParams(size, size)
        lp.addRule(RelativeLayout.CENTER_VERTICAL)
        lp.marginStart = context.resources.getDimension(R.dimen.fluentui_snackbar_custom_view_margin_start).toInt()
        customView?.layoutParams = lp
    }

    @SuppressLint("RestrictedApi")
    private fun updateBackground() {
        when (style) {
            Style.REGULAR -> view.background = ContextCompat.getDrawable(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.drawable.snackbar_background)
            Style.ANNOUNCEMENT -> view.background = ContextCompat.getDrawable(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.drawable.snackbar_background_announcement)
            Style.PRIMARY -> view.background = ContextCompat.getDrawable(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.drawable.snackbar_background_primary)
            Style.LIGHT -> view.background = ContextCompat.getDrawable(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.drawable.snackbar_background_light)
            Style.WARNING -> view.background = ContextCompat.getDrawable(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.drawable.snackbar_background_warning)
            Style.DANGER -> view.background = ContextCompat.getDrawable(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.drawable.snackbar_background_danger)
        }
    }

    private fun updateStyle() {
        layoutTextAndActionButton()

        val customViewLayoutParams = customView?.layoutParams as? RelativeLayout.LayoutParams

        when (style) {
            Style.REGULAR -> {
                actionButtonView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarActionTextColor))
                textView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextColor))
                customViewLayoutParams?.addRule(RelativeLayout.CENTER_VERTICAL)
            }
            Style.ANNOUNCEMENT -> {
                actionButtonView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarActionTextAnnouncementColor))
                textView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarActionTextAnnouncementColor))
                customViewLayoutParams?.removeRule(RelativeLayout.CENTER_VERTICAL)
            }
            Style.PRIMARY -> {
                actionButtonView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextPrimaryColor))
                textView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextPrimaryColor))
                customViewLayoutParams?.addRule(RelativeLayout.CENTER_VERTICAL)
            }
            Style.LIGHT -> {
                actionButtonView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextLightColor))
                textView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextLightColor))
                customViewLayoutParams?.addRule(RelativeLayout.CENTER_VERTICAL)
            }
            Style.WARNING -> {
                actionButtonView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextWarningColor))
                textView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextWarningColor))
                customViewLayoutParams?.addRule(RelativeLayout.CENTER_VERTICAL)
            }
            Style.DANGER -> {
                actionButtonView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextDangerColor))
                textView.setTextColor(ThemeUtil.getThemeAttrColor(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Transients), R.attr.fluentuiSnackbarTextDangerColor))
                customViewLayoutParams?.addRule(RelativeLayout.CENTER_VERTICAL)
            }
        }

        customViewLayoutParams?.topMargin = customViewVerticalMargin
        customViewLayoutParams?.bottomMargin = customViewVerticalMargin
        customView?.layoutParams = customViewLayoutParams
    }

    private fun layoutTextAndActionButton() {
        val contentInset = context.resources.getDimension(R.dimen.fluentui_snackbar_content_inset).toInt()
        val textLayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        val buttonLayoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)

        val textWidth = actionButtonView.paint.measureText(actionButtonView.text.toString())
        if (textWidth > context.resources.getDimension(R.dimen.fluentui_snackbar_action_text_wrapping_width) || style == Style.ANNOUNCEMENT) {
            // Action button moves to the bottom of the root view
            textLayoutParams.removeRule(RelativeLayout.START_OF)
            textLayoutParams.removeRule(RelativeLayout.CENTER_VERTICAL)
            textLayoutParams.marginEnd = contentInset
            buttonLayoutParams.addRule(RelativeLayout.BELOW, snackbar_text)
            actionButtonView.setPaddingRelative(contentInset, contentInset, contentInset, contentInset)
        } else {
            // Action button moves to the end of the text view
            textLayoutParams.addRule(RelativeLayout.START_OF, snackbar_action)
            textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
            textLayoutParams.bottomMargin = contentInset
            if (actionButtonView.text.isNullOrEmpty())
                textLayoutParams.marginEnd = contentInset
            buttonLayoutParams.removeRule(RelativeLayout.BELOW)
            actionButtonView.setPaddingRelative(
                context.resources.getDimension(R.dimen.fluentui_snackbar_action_spacing).toInt(),
                contentInset,
                contentInset,
                contentInset
            )
        }

        if (customView != null)
            textLayoutParams.addRule(RelativeLayout.END_OF, fluentui_snackbar_custom_view)
        else
            textLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START)

        textLayoutParams.alignWithParent = true
        textLayoutParams.marginStart = contentInset
        textLayoutParams.topMargin = contentInset
        textView.layoutParams = textLayoutParams

        buttonLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL)
        buttonLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END)
        actionButtonView.layoutParams = buttonLayoutParams
    }

    private class ContentViewCallback(private val content: View) : BaseTransientBottomBar.ContentViewCallback {
        val viewBinding = ViewSnackbarBinding.bind(content)
        override fun animateContentIn(delay: Int, duration: Int) {
            // These animations are from the Android Snackbar
            viewBinding.snackbarText.alpha = 0f
            viewBinding.snackbarText.animate().alpha(1f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()

            if (viewBinding.snackbarAction.visibility == View.VISIBLE) {
                viewBinding.snackbarAction.alpha = 0f
                viewBinding.snackbarAction.animate().alpha(1f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
            }
        }

        override fun animateContentOut(delay: Int, duration: Int) {
            // These animations are from the Android Snackbar
            viewBinding.snackbarText.alpha = 1f
            viewBinding.snackbarText.animate().alpha(0f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()

            if (viewBinding.snackbarAction.visibility == View.VISIBLE) {
                viewBinding.snackbarAction.alpha = 1f
                viewBinding.snackbarAction.animate().alpha(0f).setDuration(duration.toLong()).setStartDelay(delay.toLong()).start()
            }
        }
    }
}