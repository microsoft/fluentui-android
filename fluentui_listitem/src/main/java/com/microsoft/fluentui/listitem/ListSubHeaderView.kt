/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.listitem

import android.content.Context
import androidx.annotation.ColorInt
import androidx.core.widget.TextViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.microsoft.fluentui.listitem.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.setContentAndUpdateVisibility
import com.microsoft.fluentui.view.TemplateView

/**
 * Sub header for list sections with a styled title view and [customAccessoryView].
 *
 * TODO add examples in the demo for [titleColor] and [customAccessoryView].
 */
class ListSubHeaderView : TemplateView {
    companion object {
        val DEFAULT_TITLE_COLOR = TitleColor.PRIMARY
        val DEFAULT_TRUNCATION = TextUtils.TruncateAt.END
    }

    enum class TitleColor {
        PRIMARY, SECONDARY, TERTIARY
    }

    /**
     * Text for the title view that will be displayed at the start of the [ListSubHeaderView].
     */
    var title: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Sets the text color for the title view.
     */
    var titleColor: TitleColor = DEFAULT_TITLE_COLOR
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Sets the text truncation for the title view.
     */
    var titleTruncateAt: TextUtils.TruncateAt = DEFAULT_TRUNCATION
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * This view will be displayed at the end of the [ListSubHeaderView].
     */
    var customAccessoryView: View? = null
        set(value) {
            if (field == value)
                return
            resetCustomAccessoryViewPadding()
            field = value
            updateCustomAccessoryViewPadding()
            updateTemplate()
        }

    /**
     * Sets the background color.
     */
    @ColorInt
    var background: Int = ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiListItemBackgroundColor)
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    private var customAccessoryViewOriginalPaddingStart: Int = 0
    private var customAccessoryViewOriginalPaddingTop: Int = 0
    private var customAccessoryViewOriginalPaddingEnd: Int = 0
    private var customAccessoryViewOriginalPaddingBottom: Int = 0

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_ListItem), attrs, defStyleAttr) {
        // TODO: Add need examples in the demo that tests these attributes. Can inflate a layout in the adapter.
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ListSubHeaderView)

        title = styledAttrs.getString(R.styleable.ListSubHeaderView_title) ?: ""

        val titleColorOrdinal = styledAttrs.getInt(R.styleable.ListSubHeaderView_titleColor, DEFAULT_TITLE_COLOR.ordinal)
        titleColor = TitleColor.values()[titleColorOrdinal]

        val titleTruncateAtOrdinal = styledAttrs.getInt(R.styleable.ListSubHeaderView_titleTruncateAt, DEFAULT_TRUNCATION.ordinal)
        titleTruncateAt = TextUtils.TruncateAt.values()[titleTruncateAtOrdinal]

        styledAttrs.recycle()
    }

    // Template

    override val templateId: Int
        get() = R.layout.view_list_sub_header

    private var titleView: TextView? = null
    private var customAccessoryViewContainer: RelativeLayout? = null

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()

        titleView = findViewInTemplateById(R.id.list_sub_header_title)
        customAccessoryViewContainer = findViewInTemplateById(R.id.list_sub_header_custom_accessory_view_container)

        updateTemplate()
    }

    private fun updateTemplate() {
        updateTitleView()
        customAccessoryViewContainer?.setContentAndUpdateVisibility(customAccessoryView)
        setBackgroundColor(background)
    }

    private fun updateTitleView() {
        val titleView = titleView ?: return

        titleView.text = title
        titleView.ellipsize = titleTruncateAt

        when (titleColor) {
            TitleColor.PRIMARY -> TextViewCompat.setTextAppearance(titleView, R.style.TextAppearance_FluentUI_ListSubHeaderTitle_Primary)
            TitleColor.SECONDARY -> TextViewCompat.setTextAppearance(titleView, R.style.TextAppearance_FluentUI_ListSubHeaderTitle_Secondary)
            TitleColor.TERTIARY -> TextViewCompat.setTextAppearance(titleView, R.style.TextAppearance_FluentUI_ListSubHeaderTitle_Tertiary)
        }

        val lp = titleView.layoutParams as LinearLayout.LayoutParams
        lp.marginEnd = if (customAccessoryView == null)
            resources.getDimension(R.dimen.fluentui_list_item_horizontal_margin_regular).toInt()
        else
            0
    }

    private fun resetCustomAccessoryViewPadding() {
        // Reset paddings with cached values to ensure paddings set in updateCustomAccessoryViewPadding
        // only get adjusted once (which is an issue in RecyclerViews).
        customAccessoryView?.setPaddingRelative(
            customAccessoryViewOriginalPaddingStart,
            customAccessoryViewOriginalPaddingTop,
            customAccessoryViewOriginalPaddingEnd,
            customAccessoryViewOriginalPaddingBottom
        )
    }

    private fun updateCustomAccessoryViewPadding() {
        val customAccessoryView = customAccessoryView ?: return

        // Cache original paddings to preserve paddings set by the consumer.
        customAccessoryViewOriginalPaddingStart = customAccessoryView.paddingStart
        customAccessoryViewOriginalPaddingTop = customAccessoryView.paddingTop
        customAccessoryViewOriginalPaddingEnd = customAccessoryView.paddingEnd
        customAccessoryViewOriginalPaddingBottom = customAccessoryView.paddingBottom

        // Use padding instead of margins here to increase touch area for potential click listeners.
        val verticalPadding = resources.getDimension(R.dimen.fluentui_list_item_vertical_margin_custom_view_minimum).toInt()
        val paddingStart = resources.getDimension(R.dimen.fluentui_list_item_horizontal_spacing_custom_accessory_view_start).toInt()
        val paddingEnd = resources.getDimension(R.dimen.fluentui_list_item_horizontal_margin_regular).toInt()
        customAccessoryView.setPaddingRelative(
            customAccessoryView.paddingStart + paddingStart,
            customAccessoryView.paddingTop + verticalPadding,
            customAccessoryView.paddingEnd + paddingEnd,
            customAccessoryView.paddingBottom + verticalPadding
        )
    }
}