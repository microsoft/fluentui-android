/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.microsoft.fluentui.appbarlayout.AppBarLayout
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.listitem.ListSubHeaderView
import com.microsoft.fluentui.search.Searchbar
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.getTintedDrawable
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityAppBarLayoutBinding
import com.microsoft.fluentuidemo.demos.list.*
import com.microsoft.fluentuidemo.util.Avatar

class AppBarLayoutActivity : DemoActivity(), View.OnClickListener {
    companion object {
        private var themeId = R.style.AppTheme
        private const val SCROLL_BEHAVIOR = "scrollBehavior"
        private const val NAVIGATION_ICON_TYPE = "navigationIconType"
        private const val SEARCHBAR_IS_ACTION_MENU_VIEW = "searchbarIsActionMenuView"
        private const val SEARCHBAR_HAS_FOCUS = "searchbarHasFocus"
        private const val SEARCHBAR_QUERY = "searchbarQuery"
    }

    private lateinit var appBarBinding: ActivityAppBarLayoutBinding

    enum class NavigationIconType {
        NONE, AVATAR, BACK_ICON
    }

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private var optionsMenu: Menu? = null
        set(value) {
            field = value
            updateSearchbar()
            updateSearchbarFocus()
            updateSearchbarQuery()
        }
    private var scrollBehavior: AppBarLayout.ScrollBehavior =
        AppBarLayout.ScrollBehavior.COLLAPSE_TOOLBAR
        set(value) {
            field = value
            updateScrollBehavior()
        }
    private var navigationIconType: NavigationIconType = NavigationIconType.BACK_ICON
        set(value) {
            field = value
            updateNavigationIcon()
        }
    private var searchbarIsActionMenuView: Boolean = false
        set(value) {
            field = value
            updateSearchbar()
        }
    private var searchbarHasFocus: Boolean = false
        set(value) {
            field = value
            updateSearchbarFocus()
        }
    private var searchbarQuery: String = ""
        set(value) {
            field = value
            updateSearchbarQuery()
        }

    private val adapter = ListAdapter(this)
    private lateinit var scrollBehaviorSubHeader: ListSubHeader
    private lateinit var navigationIconButton: ButtonItem
    private lateinit var searchbarButton: ButtonItem
    private lateinit var searchbar: Searchbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appBarBinding = ActivityAppBarLayoutBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        setTheme(themeId)

        searchbar = createSearchbar()

        scrollBehaviorSubHeader = createListSubHeader(
            resources.getString(
                R.string.app_bar_layout_toggle_scroll_behavior_sub_header,
                scrollBehavior.toString()
            )
        )
        navigationIconButton = ButtonItem(
            buttonText = resources.getString(R.string.app_bar_layout_hide_icon_button),
            id = R.id.app_bar_layout_toggle_navigation_icon_button,
            onClickListener = this
        )
        searchbarButton = ButtonItem(
            buttonText = resources.getString(R.string.app_bar_layout_searchbar_accessory_view_button),
            id = R.id.app_bar_layout_toggle_searchbar_type_button,
            onClickListener = this
        )
        var scrollBehaviorOrdinal = scrollBehavior.ordinal
        var navigationIconTypeOrdinal = navigationIconType.ordinal
        savedInstanceState?.let {
            scrollBehaviorOrdinal = it.getInt(SCROLL_BEHAVIOR)
            navigationIconTypeOrdinal = it.getInt(NAVIGATION_ICON_TYPE)
            searchbarIsActionMenuView = it.getBoolean(SEARCHBAR_IS_ACTION_MENU_VIEW)
            searchbarHasFocus = it.getBoolean(SEARCHBAR_HAS_FOCUS)
            searchbarQuery = it.getString(SEARCHBAR_QUERY) ?: ""
        }

        scrollBehavior = AppBarLayout.ScrollBehavior.values()[scrollBehaviorOrdinal]
        navigationIconType = NavigationIconType.values()[navigationIconTypeOrdinal]

        setupList()
        demoBinding.appBar.scrollTargetViewId = R.id.app_bar_layout_list

        updateScrollBehavior()
        updateNavigationIcon()
        updateSearchbar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        searchbarQuery = searchbar.query.toString()

        outState.putInt(SCROLL_BEHAVIOR, scrollBehavior.ordinal)
        outState.putInt(NAVIGATION_ICON_TYPE, navigationIconType.ordinal)
        outState.putBoolean(SEARCHBAR_IS_ACTION_MENU_VIEW, searchbarIsActionMenuView)
        outState.putBoolean(SEARCHBAR_HAS_FOCUS, searchbarHasFocus)
        outState.putString(SEARCHBAR_QUERY, searchbarQuery)
    }

    override fun onClick(view: View) {
        val viewId = view.id
        when (view.id) {
            R.id.app_bar_layout_toggle_scroll_behavior_button -> {
                scrollBehavior = when (scrollBehavior) {
                    AppBarLayout.ScrollBehavior.NONE -> AppBarLayout.ScrollBehavior.COLLAPSE_TOOLBAR
                    AppBarLayout.ScrollBehavior.COLLAPSE_TOOLBAR -> AppBarLayout.ScrollBehavior.PIN
                    AppBarLayout.ScrollBehavior.PIN -> AppBarLayout.ScrollBehavior.NONE
                }
            }
            R.id.app_bar_layout_toggle_navigation_icon_button ->
                navigationIconType = when (navigationIconType) {
                    NavigationIconType.NONE -> NavigationIconType.AVATAR
                    NavigationIconType.AVATAR -> NavigationIconType.BACK_ICON
                    NavigationIconType.BACK_ICON -> NavigationIconType.NONE
                }
            R.id.app_bar_layout_toggle_searchbar_type_button ->
                searchbarIsActionMenuView = !searchbarIsActionMenuView
            R.id.app_bar_layout_toggle_theme_button -> {
                themeId = when (themeId) {
                    R.style.AppTheme -> R.style.AppTheme_Neutral
                    R.style.AppTheme_Neutral -> R.style.AppTheme_Orange
                    else -> R.style.AppTheme
                }

                recreate()
            }
        }
        Handler(Looper.getMainLooper()).postDelayed(
            {
                // for setting keyboard focus
                findViewById<View>(viewId).requestFocus()
                if (viewId == R.id.app_bar_layout_toggle_theme_button) {
                    val themeName = when (themeId) {
                        R.style.AppTheme -> "Default"
                        R.style.AppTheme_Neutral -> "Neutral"
                        else -> "Orange"
                    }
                    findViewById<View>(viewId).announceForAccessibility("Theme: ${themeName}")
                }
            }, 500
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar_layout, menu)

        optionsMenu = menu

        for (index in 0 until menu.size()) {
            val drawable = menu.getItem(index).icon
            drawable?.setColorFilter(
                ThemeUtil.getThemeAttrColor(this, R.attr.fluentuiToolbarIconColor),
                PorterDuff.Mode.SRC_IN
            )
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.app_bar_layout_action_search)
            (item.actionView as? Searchbar)?.requestSearchViewFocus()

        return super.onOptionsItemSelected(item)
    }

    private fun updateScrollBehavior() {
        if (demoBinding.appBar == null)
            return

        demoBinding.appBar.scrollBehavior = scrollBehavior

        scrollBehaviorSubHeader.title =
            resources.getString(
                R.string.app_bar_layout_toggle_scroll_behavior_sub_header,
                scrollBehavior.toString()
            )

        adapter.notifyDataSetChanged()
    }

    private fun updateNavigationIcon() {
        if (demoBinding.appBar == null)
            return

        searchbar.clearFocus()
        optionsMenu?.findItem(R.id.app_bar_layout_action_search)?.collapseActionView()

        when (navigationIconType) {
            NavigationIconType.NONE -> {
                demoBinding.appBar.toolbar.navigationIcon = null

                navigationIconButton.buttonText =
                    resources.getString(R.string.app_bar_layout_show_avatar_button)
            }
            NavigationIconType.AVATAR -> {
                val avatar = Avatar(resources.getString(R.string.persona_name_mauricio_august))
                avatar.avatarImageResourceId = R.drawable.avatar_mauricio_august
                demoBinding.appBar.toolbar.setNavigationOnClickListener {
                    Snackbar.make(
                        demoBinding.rootView,
                        getString(R.string.app_bar_layout_navigation_icon_clicked)
                    ).show()
                }
                demoBinding.appBar.toolbar.avatar = avatar

                navigationIconButton.buttonText =
                    resources.getString(R.string.app_bar_layout_show_back_icon_button)
            }
            NavigationIconType.BACK_ICON -> {
                val backArrow =
                    ContextCompat.getDrawable(this, R.drawable.ms_ic_arrow_left_24_filled)
                /*
                 Wrapping this by FluentUIContext so that we need not declare this attr in Theme,
                 In case our theme is not extending Fluent Theme.
                 But if declare this attr in theme then no context wrapping is required
                 */
                backArrow?.setTint(
                    ThemeUtil.getThemeAttrColor(
                        FluentUIContextThemeWrapper(
                            this,
                            R.style.Theme_FluentUI_Components
                        ), R.attr.fluentuiToolbarIconColor
                    )
                )
                demoBinding.appBar.toolbar.navigationIcon = backArrow
                demoBinding.appBar.toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }

                navigationIconButton.buttonText =
                    resources.getString(R.string.app_bar_layout_hide_icon_button)
            }
        }

        adapter.notifyDataSetChanged()
    }

    private fun updateSearchbar() {
        if (demoBinding.appBar == null)
            return

        searchbar.isActionMenuView = searchbarIsActionMenuView
        if (searchbarIsActionMenuView) {
            val optionsMenu = optionsMenu ?: return
            demoBinding.appBar.accessoryView = null

            val searchIcon = getTintedDrawable(
                R.drawable.ms_ic_search_24_filled,
                ThemeUtil.getThemeAttrColor(this, R.attr.fluentuiToolbarIconColor)
            )
            optionsMenu.add(
                R.id.app_bar_menu,
                R.id.app_bar_layout_action_search,
                0,
                getString(R.string.app_bar_layout_menu_search)
            )
                .setIcon(searchIcon)
                .setActionView(searchbar)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_ALWAYS)

            searchbarButton.buttonText =
                resources.getString(R.string.app_bar_layout_searchbar_accessory_view_button)
        } else {
            optionsMenu?.removeItem(R.id.app_bar_layout_action_search)
            demoBinding.appBar.accessoryView = searchbar
            searchbarButton.buttonText =
                resources.getString(R.string.app_bar_layout_searchbar_action_view_button)
        }

        adapter.notifyDataSetChanged()
    }

    private fun updateSearchbarFocus() {
        if (demoBinding.appBar == null)
            return

        if (searchbarHasFocus) {
            optionsMenu?.performIdentifierAction(R.id.app_bar_layout_action_search, 0)
            searchbar.requestSearchViewFocus()
        }
    }

    private fun updateSearchbarQuery() {
        if (demoBinding.appBar == null)
            return

        searchbar.setQuery(searchbarQuery, false)
    }

    private fun createSearchbar(): Searchbar {
        val searchbar = Searchbar(ContextThemeWrapper(this, themeId))
        searchbar.onQueryTextFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            searchbarHasFocus = hasFocus
        }

        return searchbar
    }

    private fun setupList() {
        adapter.listItems = createList()
        appBarBinding.appBarLayoutList.adapter = adapter
        appBarBinding.appBarLayoutList.addItemDecoration(
            ListItemDivider(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun createList(): ArrayList<IBaseListItem> {
        val scrollBehaviorSection = createSection(
            scrollBehaviorSubHeader,
            arrayListOf(
                ButtonItem(
                    buttonText = resources.getString(R.string.app_bar_layout_toggle_scroll_behavior_button),
                    id = R.id.app_bar_layout_toggle_scroll_behavior_button,
                    onClickListener = this
                )
            )
        )

        val navigationIconSection = createSection(
            createListSubHeader(resources.getString(R.string.app_bar_layout_toggle_navigation_icon_sub_header)),
            arrayListOf(navigationIconButton)
        )

        val searchbarSection = createSection(
            createListSubHeader(resources.getString(R.string.app_bar_layout_toggle_searchbar_sub_header)),
            arrayListOf(searchbarButton)
        )

        val themeSection = createSection(
            createListSubHeader(resources.getString(R.string.app_bar_layout_toggle_theme_sub_header)),
            arrayListOf(
                ButtonItem(
                    buttonText = resources.getString(R.string.app_bar_layout_toggle_theme_button),
                    id = R.id.app_bar_layout_toggle_theme_button,
                    onClickListener = this
                )
            )
        )

        val extraListItems = ArrayList<IBaseListItem>()
        for (index in 0..35)
            extraListItems.add(ListItem("${getString(R.string.app_bar_layout_list_item)} $index"))

        val extraScrollableContextSection = createSection(
            createListSubHeader(getString(R.string.app_bar_layout_list_sub_header)),
            extraListItems
        )

        return (scrollBehaviorSection + navigationIconSection + searchbarSection + themeSection + extraScrollableContextSection) as ArrayList<IBaseListItem>
    }

    private fun createSection(
        subHeader: ListSubHeader,
        items: ArrayList<IBaseListItem>
    ): ArrayList<IBaseListItem> {
        val itemArray = arrayListOf(subHeader) as ArrayList<IBaseListItem>
        itemArray.addAll(items)
        return itemArray
    }

    private fun createListSubHeader(text: String): ListSubHeader {
        val listSubHeader = ListSubHeader(text)
        listSubHeader.titleColor = ListSubHeaderView.TitleColor.SECONDARY
        return listSubHeader
    }
}