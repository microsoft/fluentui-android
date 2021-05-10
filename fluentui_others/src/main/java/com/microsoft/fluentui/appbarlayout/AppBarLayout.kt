/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.appbarlayout

import android.animation.AnimatorInflater
import android.content.Context
import android.os.Build
import androidx.annotation.IdRes
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.microsoft.fluentui.R
import com.microsoft.fluentui.appbarlayout.AppBarLayout.ScrollBehavior
import com.microsoft.fluentui.search.Searchbar
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.toolbar.Toolbar
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.activity
import kotlin.math.abs

/**
 * [AppBarLayout] comes with a [Toolbar] and an optional [accessoryView] that appears below the [Toolbar].
 * [ScrollBehavior] provides control over [Toolbar] and [accessoryView] motion during scroll
 * changes and focus changes when using [Searchbar].
 *
 * [ScrollBehavior] works best with a [CoordinatorLayout] parent and either [NestedScrollView] or
 * [RecyclerView] direct siblings.
 *
 * To use a neutral theme with a white background instead of primary background in day mode,
 * apply ThemeOverlay.FluentUI.NeutralAppBar via the theme attribute or a ContextThemeWrapper.
 *
 * TODO
 * - Use Fluent PopupMenu
 * - Add xml attributes
 */
class AppBarLayout : AppBarLayout {
    companion object {
        private val DEFAULT_SCROLL_BEHAVIOR = ScrollBehavior.COLLAPSE_TOOLBAR
    }

    enum class ScrollBehavior {
        NONE, COLLAPSE_TOOLBAR, PIN
    }

    /**
     * This [toolbar] is used as the support action bar.
     */
    lateinit var toolbar: Toolbar
        private set
    /**
     * This view appears below the [toolbar].
     */
    var accessoryView: View? = null
        set(value) {
            if (field == value)
                return

            if (accessoryView != null)
                removeView(accessoryView)

            field = value

            if (field != null)
                addView(field)

            updateViewsWithScrollBehavior()
        }
    /**
     * Defines the [ScrollBehavior] applied to the [toolbar] and [accessoryView] on scroll and focus changes.
     */
    var scrollBehavior: ScrollBehavior = DEFAULT_SCROLL_BEHAVIOR
        set(value) {
            if (field == value)
                return
            field = value
            updateViewsWithScrollBehavior()
        }

    /**
     * Id of the view that [AppBarLayout] uses to determine [scrollBehavior] functionality. Use this id if
     * the scrolling view is not a direct sibling of [AppBarLayout].
     */
    @IdRes
    var scrollTargetViewId: Int = View.NO_ID
        set(value) {
            if (field == value)
                return
            field = value
            scrollTargetView = getOnScrollTargetView()
        }

    private var scrollTargetView: View? = null
        set(value) {
            if (field == value)
                return
            (scrollTargetView as? RecyclerView)?.removeOnScrollListener(recyclerViewScrollListener)
            field = value
            (field as? RecyclerView)?.addOnScrollListener(recyclerViewScrollListener)
        }

    private val behavior = Behavior()

    private val offsetChangedListener = OnOffsetChangedListener { appBarLayout, verticalOffset ->
        toolbar.alpha = 1f - abs(verticalOffset / (appBarLayout.totalScrollRange.toFloat() / 3))
        setStateListAnimator(verticalOffset != 0)
    }

    private val recyclerViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            setStateListAnimator(recyclerView.computeVerticalScrollOffset() != 0)
        }
    }

    constructor(appContext: Context, attrs: AttributeSet?) : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Components), attrs) {
        setupToolbar(context)
        setBackgroundColor(ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiAppBarLayoutBackgroundColor))

        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.AppBarLayout)
        scrollTargetViewId = styledAttributes.getResourceId(R.styleable.AppBarLayout_scrollTargetViewId, View.NO_ID)
        val scrollBehaviorOrdinal = styledAttributes.getInt(R.styleable.AppBarLayout_scrollBehavior, DEFAULT_SCROLL_BEHAVIOR.ordinal)
        scrollBehavior = ScrollBehavior.values()[scrollBehaviorOrdinal]
        styledAttributes.recycle()
    }

    internal fun updateExpanded(expanded: Boolean) {
        if (scrollBehavior == ScrollBehavior.COLLAPSE_TOOLBAR)
            setExpanded(expanded, true)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        scrollTargetView = getOnScrollTargetView()
        updateViewsWithScrollBehavior()
    }

    private fun getOnScrollTargetView(): View? {
        val parent = parent as? ViewGroup ?: return null
        val firstSiblingIndex = parent.indexOfChild(this) + 1
        val firstSibling = parent.getChildAt(firstSiblingIndex)
        val scrollTargetViewWithId = parent.findViewById<View>(scrollTargetViewId)

        return scrollTargetViewWithId ?: firstSibling as? RecyclerView
        ?: firstSibling as? NestedScrollView
    }

    private fun setStateListAnimator(lift: Boolean) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1)
            stateListAnimator = if (lift && scrollBehavior != ScrollBehavior.NONE)
                AnimatorInflater.loadStateListAnimator(context, R.animator.app_bar_layout_elevation_scroll)
            else
                AnimatorInflater.loadStateListAnimator(context, R.animator.app_bar_layout_elevation)
        else
            elevation = resources.getDimension(R.dimen.fluentui_app_bar_layout_elevation)
    }

    private fun setupToolbar(context: Context) {
        toolbar = Toolbar(context)
        addView(toolbar)
        context.activity?.setSupportActionBar(toolbar)
        touchscreenBlocksFocus = false
    }

    private fun updateViewsWithScrollBehavior() {
        val currentBehavior = (layoutParams as? CoordinatorLayout.LayoutParams)?.behavior
        (layoutParams as? CoordinatorLayout.LayoutParams)?.behavior = when {
            scrollBehavior != ScrollBehavior.NONE -> behavior
            currentBehavior != behavior -> currentBehavior
            else -> null
        }

        val toolbarLayoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        when (scrollBehavior) {
            ScrollBehavior.NONE -> {
                toolbarLayoutParams.scrollFlags = 0
                removeOnOffsetChangedListener(offsetChangedListener)
                setStateListAnimator(false)
                toolbar.alpha = 1.0f
            }
            ScrollBehavior.COLLAPSE_TOOLBAR -> {
                toolbarLayoutParams.scrollFlags = SCROLL_FLAG_SCROLL or SCROLL_FLAG_SNAP or SCROLL_FLAG_ENTER_ALWAYS

                val accessoryViewLayoutParams = accessoryView?.layoutParams as? LayoutParams
                accessoryViewLayoutParams?.scrollFlags = 0
                accessoryView?.layoutParams = accessoryViewLayoutParams

                addOnOffsetChangedListener(offsetChangedListener)
            }
            ScrollBehavior.PIN -> {
                toolbarLayoutParams.scrollFlags = 0
                setStateListAnimator(false)
            }
        }
        toolbar.layoutParams = toolbarLayoutParams
    }

    private inner class Behavior : AppBarLayout.Behavior () {
        override fun onStartNestedScroll(
            parent: CoordinatorLayout,
            child: AppBarLayout,
            directTargetChild: View,
            target: View,
            nestedScrollAxes: Int,
            type: Int
        ): Boolean {
            val superResult = super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type)
            // Using a listener for RecyclerViews instead to get a more accurate y position.
            if (target is RecyclerView)
                return superResult

            setStateListAnimator(target.scrollY != 0)
            return true
        }

        override fun onNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            child: AppBarLayout,
            target: View,
            dxConsumed: Int,
            dyConsumed: Int,
            dxUnconsumed: Int,
            dyUnconsumed: Int,
            type: Int
        ) {
            super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)
            setStateListAnimator(target.scrollY != 0)
        }

        override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, abl: AppBarLayout, target: View, type: Int) {
            super.onStopNestedScroll(coordinatorLayout, abl, target, type)
            setStateListAnimator(target.scrollY != 0)
        }
    }
}