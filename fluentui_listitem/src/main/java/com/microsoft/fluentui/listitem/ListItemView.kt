/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.listitem

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.widget.TextViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.microsoft.fluentui.listitem.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.setContentAndUpdateVisibility
import com.microsoft.fluentui.view.TemplateView

/**
 * [ListItemView] is designed to be used as content for rows in RecyclerViews, ListViews, etc.
 * It provides a text area with customizable options for a [title], [subtitle], and [footer]
 * and secondary visual and action areas with [customView] and [customAccessoryView].
 *
 * TODO: Variants still needed:
 * - Large preview
 */
open class ListItemView : TemplateView {
    companion object {
        val DEFAULT_TRUNCATION: TextUtils.TruncateAt = TextUtils.TruncateAt.END
        val DEFAULT_LAYOUT_DENSITY: LayoutDensity = LayoutDensity.REGULAR
        val DEFAULT_CUSTOM_VIEW_SIZE: CustomViewSize = CustomViewSize.MEDIUM

        const val DEFAULT_MAX_LINES: Int = 1
    }

    /**
     * Defines the width and height of the [customView] via [customViewSize].
     */
    enum class CustomViewSize(private val id: Int) {
        SMALL(R.dimen.fluentui_list_item_custom_view_size_small),
        MEDIUM(R.dimen.fluentui_list_item_custom_view_size_medium),
        // Using a large custom view will apply "Large Header" style text appearance and margins to your list item.
        LARGE(R.dimen.fluentui_list_item_custom_view_size_large);

        /**
         * This method provides the actual physical size that is applied to custom view.
         */
        fun getDisplayValue(context: Context): Int = context.resources.getDimension(id).toInt()
    }

    /**
     * Defines the list item view vertical padding densities via [layoutDensity].
     */
    enum class LayoutDensity {
        REGULAR, COMPACT
    }

    private enum class LayoutType {
        ONE_LINE, TWO_LINES, THREE_LINES
    }

    /**
     * First level of hierarchy in the text area.
     */
    var title: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }
    /**
     * Second level of hierarchy in the text area.
     */
    var subtitle: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }
    /**
     * Third level of hierarchy in the text area.
     */
    var footer: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Sets the maxLines for the title view.
     */
    var titleMaxLines: Int = DEFAULT_MAX_LINES
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }
    /**
     * Sets the maxLines for the subtitle view.
     */
    var subtitleMaxLines: Int = DEFAULT_MAX_LINES
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }
    /**
     * Sets the maxLines for the footer view.
     */
    var footerMaxLines: Int = DEFAULT_MAX_LINES
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Determines where the title view will truncate.
     */
    var titleTruncateAt: TextUtils.TruncateAt = DEFAULT_TRUNCATION
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }
    /**
     * Determines where the subtitle view will truncate.
     */
    var subtitleTruncateAt: TextUtils.TruncateAt = DEFAULT_TRUNCATION
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }
    /**
     * Determines where the footer view will truncate.
     */
    var footerTruncateAt: TextUtils.TruncateAt = DEFAULT_TRUNCATION
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * This view will be displayed at the start of the list item view.
     */
    var customView: View? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }
    /**
     * Determines the width and height of the [customView].
     */
    var customViewSize: CustomViewSize = DEFAULT_CUSTOM_VIEW_SIZE
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * This view will be displayed at the end of the list item view.
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
     * This view will be displayed at the end of subtitle view.
     */
    var customSecondarySubtitleView: View? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Determines the list item view vertical padding density.
     */
    var layoutDensity: LayoutDensity = DEFAULT_LAYOUT_DENSITY
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Determines the list item view vertical padding density.
     */
    var titleStyleRes: Int = 0
        set(value) {
            if (field == value)
                return
            field = value
            updateTextAppearance()
        }

    /**
     * Determines the list item view vertical padding density.
     */
    var subTitleStyleRes: Int = 0
        set(value) {
            if (field == value)
                return
            field = value
            updateTextAppearance()
        }

    /**
     * Sets the background color or drawable resource.
     * The default drawable has a ripple animation for selection state.
     */
    @DrawableRes
    var background: Int = R.drawable.list_item_view_background
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    /**
     * Defines the start inset for the text area.
     */
    internal val textAreaStartInset: Float
        get() {
            return when {
                useLargeHeaderStyle -> resources.getDimension(R.dimen.fluentui_list_item_text_area_inset_custom_view_large_header)
                customView != null -> resources.getDimension(R.dimen.fluentui_list_item_text_area_inset_custom_view)
                else -> resources.getDimension(R.dimen.fluentui_list_item_horizontal_margin_regular)
            }
        }

    /**
     * Defines the end inset for the text area.
     */
    internal val textAreaEndInset: Float
        get() = resources.getDimension(R.dimen.fluentui_list_item_horizontal_margin_regular)

    private val layoutType: LayoutType
        get() {
            return if (subtitle.isNotEmpty() && footer.isEmpty())
                LayoutType.TWO_LINES
            else if (subtitle.isNotEmpty() && footer.isNotEmpty())
                LayoutType.THREE_LINES
            else
                LayoutType.ONE_LINE
        }

    private var customAccessoryViewOriginalPaddingStart: Int = 0
    private var customAccessoryViewOriginalPaddingTop: Int = 0
    private var customAccessoryViewOriginalPaddingEnd: Int = 0
    private var customAccessoryViewOriginalPaddingBottom: Int = 0

    private val useLargeHeaderStyle: Boolean
        get() = customView != null && customViewSize == CustomViewSize.LARGE

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_ListItem), attrs, defStyleAttr) {
        // TODO: Add need examples in the demo that tests these attributes. Can inflate a layout in the adapter.
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ListItemView)

        title = styledAttrs.getString(R.styleable.ListItemView_title) ?: ""
        subtitle = styledAttrs.getString(R.styleable.ListItemView_subtitle) ?: ""
        footer = styledAttrs.getString(R.styleable.ListItemView_footer) ?: ""

        titleMaxLines = styledAttrs.getInt(R.styleable.ListItemView_titleMaxLines, DEFAULT_MAX_LINES)
        subtitleMaxLines = styledAttrs.getInt(R.styleable.ListItemView_subtitleMaxLines, DEFAULT_MAX_LINES)
        footerMaxLines = styledAttrs.getInt(R.styleable.ListItemView_footerMaxLines, DEFAULT_MAX_LINES)

        val titleTruncateAtOrdinal = styledAttrs.getInt(R.styleable.ListItemView_titleTruncateAt, DEFAULT_TRUNCATION.ordinal)
        titleTruncateAt = TextUtils.TruncateAt.values()[titleTruncateAtOrdinal]
        val subtitleTruncateAtOrdinal = styledAttrs.getInt(R.styleable.ListItemView_subtitleTruncateAt, DEFAULT_TRUNCATION.ordinal)
        subtitleTruncateAt = TextUtils.TruncateAt.values()[subtitleTruncateAtOrdinal]
        val footerTruncateAtOrdinal = styledAttrs.getInt(R.styleable.ListItemView_footerTruncateAt, DEFAULT_TRUNCATION.ordinal)
        footerTruncateAt = TextUtils.TruncateAt.values()[footerTruncateAtOrdinal]

        val layoutDensityOrdinal = styledAttrs.getInt(R.styleable.ListItemView_layoutDensity, DEFAULT_LAYOUT_DENSITY.ordinal)
        layoutDensity = LayoutDensity.values()[layoutDensityOrdinal]

        val customViewSizeOrdinal = styledAttrs.getInt(R.styleable.ListItemView_customViewSize, DEFAULT_CUSTOM_VIEW_SIZE.ordinal)
        customViewSize = CustomViewSize.values()[customViewSizeOrdinal]

        styledAttrs.recycle()
    }

    // Template

    override val templateId: Int
        get() = R.layout.view_list_item

    private var titleView: TextView? = null
    private var subtitleView: TextView? = null
    private var footerView: TextView? = null

    private var container: LinearLayout? = null
    private var customViewContainer: RelativeLayout? = null
    private var customAccessoryViewContainer: RelativeLayout? = null
    private var customSecondarySubtitleViewContainer: RelativeLayout? = null
    private var textViewContainer: LinearLayout? = null

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()

        titleView = findViewInTemplateById(R.id.list_item_title)
        subtitleView = findViewInTemplateById(R.id.list_item_subtitle)
        footerView = findViewInTemplateById(R.id.list_item_footer)

        container = findViewInTemplateById(R.id.list_item)
        customViewContainer = findViewInTemplateById(R.id.list_item_custom_view_container)
        customAccessoryViewContainer = findViewInTemplateById(R.id.list_item_custom_accessory_view_container)
        customSecondarySubtitleViewContainer = findViewInTemplateById(R.id.list_item_custom_secondary_subtitle_view_container)
        textViewContainer = findViewInTemplateById(R.id.list_item_text_view_container)

        updateTemplate()
    }

    private fun updateTemplate() {
        updateTextAppearance()
        updateTextView(titleView, title, titleMaxLines, titleTruncateAt)
        updateTextView(subtitleView, subtitle, subtitleMaxLines, subtitleTruncateAt)
        updateTextView(footerView, footer, footerMaxLines, footerTruncateAt)

        updateCustomViewContainerLayout()
        updateTextViewContainerLayout()

        customViewContainer?.setContentAndUpdateVisibility(customView, ::updateCustomViewLayout)
        customAccessoryViewContainer?.setContentAndUpdateVisibility(customAccessoryView)
        customSecondarySubtitleViewContainer?.setContentAndUpdateVisibility(customSecondarySubtitleView)

        setBackgroundResource(background)
    }

    private fun updateTextAppearance() {
        if (useLargeHeaderStyle) {
            titleView?.let { TextViewCompat.setTextAppearance(it, R.style.TextAppearance_FluentUI_ListItemTitle_LargeHeader) }
            subtitleView?.let { TextViewCompat.setTextAppearance(it, R.style.TextAppearance_FluentUI_ListItemSubtitle_LargeHeader) }
            footerView?.let { TextViewCompat.setTextAppearance(it, R.style.TextAppearance_FluentUI_ListItemFooter_LargeHeader) }
        } else {
            titleView?.let { TextViewCompat.setTextAppearance(it, R.style.TextAppearance_FluentUI_ListItemTitle) }
            subtitleView?.let { TextViewCompat.setTextAppearance(it, R.style.TextAppearance_FluentUI_ListItemSubtitle) }
            footerView?.let { TextViewCompat.setTextAppearance(it, R.style.TextAppearance_FluentUI_ListItemFooter) }
        }
        setCustomTextStyle()
    }

    private fun setCustomTextStyle() {
        if (titleStyleRes != 0) {
            titleView?.let { TextViewCompat.setTextAppearance(it, titleStyleRes) }
        }
        if (subTitleStyleRes != 0) {
            subtitleView?.let { TextViewCompat.setTextAppearance(it, subTitleStyleRes) }
        }
    }

    private fun updateTextView(textView: TextView?, text: String, maxLines: Int, truncateAt: TextUtils.TruncateAt) {
        textView?.text = text
        textView?.maxLines = maxLines
        textView?.ellipsize = truncateAt
        textView?.visibility = if (text.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun updateCustomViewLayout() {
        val customView = customView ?: return
        val customViewSizeDisplayValue = customViewSize.getDisplayValue(context)
        val lp = RelativeLayout.LayoutParams(customViewSizeDisplayValue, customViewSizeDisplayValue)
        lp.addRule(RelativeLayout.CENTER_IN_PARENT)
        customView.layoutParams = lp
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
        customAccessoryView.setPaddingRelative(
            customAccessoryView.paddingStart + resources.getDimension(R.dimen.fluentui_list_item_horizontal_spacing_custom_accessory_view_start).toInt(),
            customAccessoryView.paddingTop + verticalPadding,
            customAccessoryView.paddingEnd + resources.getDimension(R.dimen.fluentui_list_item_horizontal_margin_regular).toInt(),
            customAccessoryView.paddingBottom + verticalPadding
        )
    }

    // Container layout

    private fun updateCustomViewContainerLayout() {
        val customViewContainer = customViewContainer ?: return
        val customViewSizeDisplayValue = customViewSize.getDisplayValue(context)
        val lp = LinearLayout.LayoutParams(customViewSizeDisplayValue, customViewSizeDisplayValue)

        val customViewContainerVerticalMargin = if (!useLargeHeaderStyle)
            resources.getDimension(R.dimen.fluentui_list_item_vertical_margin_custom_view_minimum).toInt()
        else
            resources.getDimension(R.dimen.fluentui_list_item_vertical_margin_large_header).toInt()

        val customViewSmallMarginEnd = resources.getDimension(R.dimen.fluentui_list_item_margin_end_custom_view_small).toInt()
        val listItemHorizontalMargin = resources.getDimension(R.dimen.fluentui_list_item_spacing).toInt()

        lp.gravity = Gravity.CENTER_VERTICAL

        val extraMarginEnd = if (customViewSize == CustomViewSize.SMALL) customViewSmallMarginEnd else 0
        lp.setMargins(
            0,
            customViewContainerVerticalMargin,
            extraMarginEnd + listItemHorizontalMargin,
            customViewContainerVerticalMargin
        )

        customViewContainer.layoutParams = lp
    }

    private fun updateTextViewContainerLayout() {
        val textViewContainer = textViewContainer ?: return
        val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

        val largeHeaderVerticalMargin = resources.getDimension(R.dimen.fluentui_list_item_vertical_margin_large_header).toInt()
        val oneLineVerticalMargin = resources.getDimension(R.dimen.fluentui_list_item_vertical_margin_text_one_line).toInt()
        val twoLineVerticalMargin = resources.getDimension(R.dimen.fluentui_list_item_vertical_margin_text_two_line).toInt()
        val twoLineCompactVerticalMargin = resources.getDimension(R.dimen.fluentui_list_item_vertical_margin_text_two_line_compact).toInt()
        val threeLineVerticalMargin = resources.getDimension(R.dimen.fluentui_list_item_vertical_margin_text_three_line).toInt()
        val horizontalMargin = resources.getDimension(R.dimen.fluentui_list_item_horizontal_margin_regular).toInt()

        lp.gravity = Gravity.CENTER_VERTICAL
        lp.topMargin = when {
            useLargeHeaderStyle -> largeHeaderVerticalMargin
            layoutType == LayoutType.TWO_LINES && layoutDensity == LayoutDensity.REGULAR -> twoLineVerticalMargin
            layoutType == LayoutType.TWO_LINES && layoutDensity == LayoutDensity.COMPACT -> twoLineCompactVerticalMargin
            layoutType == LayoutType.THREE_LINES -> threeLineVerticalMargin
            else -> oneLineVerticalMargin
        }
        lp.bottomMargin = lp.topMargin
        lp.marginEnd = if (customAccessoryView == null) horizontalMargin else 0

        textViewContainer.layoutParams = lp
    }
}