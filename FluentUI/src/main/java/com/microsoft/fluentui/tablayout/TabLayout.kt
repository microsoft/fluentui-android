/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.tablayout

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.view.TemplateView

/**
 * [TabLayout] contains a styled TabLayout widget which provides a way to organize content
 * The template uses already existing Android Design Support Library [TabLayout].*/
class TabLayout : TemplateView {
    enum class TabType {
        STANDARD, SWITCH, PILLS
    }

    /* This [tabLayout] stores the Android Design Support Library [TabLayout] attached to the given template. */
    var tabLayout: TabLayout? = null
        private set

    /* This [tabType] stores the type of TabLayout. It supports [TabType.STANDARD], [TabType.SWITCH], [TabType.PILLS] */
    var tabType: TabType? = null
        set(value) {
            field = value
            updateTemplate()
        }

    private var tabLayoutContainer: ViewGroup? = null
    private val containerBackgroundColor: Int
    private val tabsBackgroundColor: Int
    private val selectedTabBackgroundColor: Int
    private val unselectedTabBackgroundColor: Int
    private val tabSelectedTextColor: Int
    private val tabUnselectedTextColor: Int

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context), attrs, defStyleAttr) {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TabLayout)
        val tabTypeOrdinal = styledAttributes.getInt(R.styleable.TabLayout_tabType, TabType.STANDARD.ordinal)
        containerBackgroundColor = styledAttributes.getColor(
            R.styleable.TabLayout_containerBackgroundColor,
            ThemeUtil.getColor(context, R.attr.fluentuiTabLayoutContainerBackgroundColor))
        tabsBackgroundColor = styledAttributes.getColor(
            R.styleable.TabLayout_tabsBackgroundColor,
            ThemeUtil.getColor(context, R.attr.fluentuiTabLayoutBackgroundColor))
        selectedTabBackgroundColor = styledAttributes.getColor(
            R.styleable.TabLayout_tabSelectedBackgroundColor,
            ThemeUtil.getColor(context, R.attr.fluentuiTabSelectedBackgroundColor))
        unselectedTabBackgroundColor = styledAttributes.getColor(
            R.styleable.TabLayout_tabUnselectedBackgroundColor,
            ThemeUtil.getColor(context, R.attr.fluentuiTabUnselectedBackgroundColor))
        tabSelectedTextColor = styledAttributes.getColor(
            R.styleable.TabLayout_tabSelectedTextColor,
            ThemeUtil.getColor(context, R.attr.fluentuiTabSelectedTextColor))
        tabUnselectedTextColor = styledAttributes.getColor(
            R.styleable.TabLayout_tabUnselectedTextColor,
            ThemeUtil.getColor(context, R.attr.fluentuiTabUnselectedTextColor))
        tabType = TabType.values()[tabTypeOrdinal]
        styledAttributes.recycle()
    }

    override val templateId: Int
        get() = R.layout.view_tab_layout

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()
        tabLayoutContainer = findViewInTemplateById(R.id.tab_layout_container)
        tabLayout = findViewInTemplateById(R.id.tab_layout)
        tabLayoutContainer?.setBackgroundColor(containerBackgroundColor)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateTemplate()
    }

    /**
     * Updates the given template based on the [tabType]. For [TabType.PILLS], this method must be called
     * to set the appropriate margins if the [tabType] is set before the Tabs have been added.
     * */
    fun updateTemplate() {
        val tabLayout = tabLayout ?: return
        var paddingHorizontal = resources.getDimension(R.dimen.fluentui_tab_padding_horizontal).toInt()
        val paddingVertical = resources.getDimension(R.dimen.fluentui_tab_padding_vertical).toInt()
        when (tabType) {
            TabType.STANDARD -> {
                tabLayout.tabMode = TabLayout.MODE_FIXED
                tabLayout.layoutParams.width = LayoutParams.MATCH_PARENT
                setTabLayoutBackground()
            }
            TabType.SWITCH -> {
                tabLayout.tabMode = TabLayout.MODE_FIXED
                tabLayout.layoutParams.width = LayoutParams.WRAP_CONTENT
                setTabLayoutBackground()
            }
            TabType.PILLS -> {
                tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
                tabLayout.layoutParams.width = LayoutParams.MATCH_PARENT
                tabLayout.setBackgroundResource(0)
                updateMargin()
                paddingHorizontal = 0
            }
        }
        tabLayoutContainer?.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical)
        setSelectorColors()
        setTextAppearance()
    }

    /**
     * Updates the right margin for the tabs in [tabLayout]. Used for [TabType.PILLS]
     * */
    private fun updateMargin() {
        val tabLayout = tabLayout ?: return
        val viewGroup = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabLayout.tabCount - 1) {
            val tab: View = viewGroup.getChildAt(i)
            tab.layoutParams = (tab.layoutParams as LinearLayout.LayoutParams).apply {
                rightMargin = resources.getDimension(R.dimen.fluentui_tab_margin).toInt()
            }
        }
        tabLayout.requestLayout()
    }

    private fun setTabLayoutBackground() {
        val tabLayout = tabLayout ?: return
        val drawable = ContextCompat.getDrawable(context, R.drawable.tab_layout_background)
        val wrappedDrawable = DrawableCompat.wrap(drawable!!)
        DrawableCompat.setTint(wrappedDrawable, tabsBackgroundColor)
        tabLayout.background = wrappedDrawable
    }

    private fun setTextAppearance() {
        val tabLayout = tabLayout ?: return
        tabLayout.setTabTextColors(tabUnselectedTextColor, tabSelectedTextColor)
    }

    private fun setSelectorColors() {
        val tabLayout = tabLayout ?: return
        val viewGroup = tabLayout.getChildAt(0) as ViewGroup
        for (i in 0 until tabLayout.tabCount) {
            val tab: View = viewGroup.getChildAt(i)
            tab.background = getStateListDrawable()
        }
    }

    private fun getStateListDrawable(): StateListDrawable? {
        val selectedDrawable = ContextCompat.getDrawable(context, R.drawable.tab_background)
            ?: return null
        val selectedWrappedDrawable = DrawableCompat.wrap(selectedDrawable).mutate()
        DrawableCompat.setTint(selectedWrappedDrawable, selectedTabBackgroundColor)

        val unselectedDrawable = selectedDrawable.constantState?.newDrawable()
            ?: return null
        val unselectedWrappedDrawable = DrawableCompat.wrap(unselectedDrawable).mutate()
        DrawableCompat.setTint(unselectedWrappedDrawable, unselectedTabBackgroundColor)

        val states = StateListDrawable()
        states.addState(intArrayOf(android.R.attr.state_selected), selectedWrappedDrawable)
        states.addState(intArrayOf(-android.R.attr.state_selected), unselectedWrappedDrawable)
        return states
    }
}