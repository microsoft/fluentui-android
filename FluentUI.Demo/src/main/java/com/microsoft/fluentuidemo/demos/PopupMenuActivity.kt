/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.microsoft.fluentui.popupmenu.PopupMenu
import com.microsoft.fluentui.popupmenu.PopupMenuItem
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityPopupMenuBinding

class PopupMenuActivity : DemoActivity(), View.OnClickListener {
    companion object {
        private const val SINGLE_CHECKED_ITEM_ID = "singleCheckedItemId"
        private const val ALL_CHECKED_ITEMS = "allCheckedItems"
    }

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private var singleCheckedItemId: Int = -1
    private var allCheckedItems: ArrayList<PopupMenuItem>? = null
    private lateinit var popupMenuBinding: ActivityPopupMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popupMenuBinding = ActivityPopupMenuBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        popupMenuBinding.noCheck.setOnClickListener(this)
        popupMenuBinding.noCheck2.setOnClickListener(this)
        popupMenuBinding.singleCheck.setOnClickListener(this)
        popupMenuBinding.allCheck.setOnClickListener(this)

        savedInstanceState?.let {
            singleCheckedItemId = it.getInt(SINGLE_CHECKED_ITEM_ID)
            allCheckedItems = it.getParcelableArrayList(ALL_CHECKED_ITEMS)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SINGLE_CHECKED_ITEM_ID, singleCheckedItemId)
        outState.putParcelableArrayList(ALL_CHECKED_ITEMS, allCheckedItems)
    }

    override fun onClick(anchorView: View) {
        when (anchorView) {
            popupMenuBinding.noCheck -> showPopupNoCheck(anchorView)
            popupMenuBinding.noCheck2 -> showPopupNoCheck(anchorView)
            popupMenuBinding.singleCheck -> showPopupSingleCheck(anchorView)
            popupMenuBinding.allCheck -> showPopupAllCheck(anchorView)
        }
    }

    private fun showPopupNoCheck(anchorView: View) {
        val popupMenuItems = arrayListOf(
            PopupMenuItem(R.id.popup_menu_item_share, getString(R.string.popup_menu_item_share), roleDescription = "Button"),
            PopupMenuItem(R.id.popup_menu_item_follow, getString(R.string.popup_menu_item_follow), roleDescription = "Button"),
            PopupMenuItem(
                R.id.popup_menu_item_invite_people,
                getString(R.string.popup_menu_item_invite_people),
                roleDescription = "Button"
            ),
            PopupMenuItem(
                R.id.popup_menu_item_refresh_page,
                getString(R.string.popup_menu_item_refresh_page),
                roleDescription = "Button"
            ),
            PopupMenuItem(
                R.id.popup_menu_item_open_in_browser,
                getString(R.string.popup_menu_item_open_in_browser),
                roleDescription = "Button"
            ),
            PopupMenuItem(
                R.id.popup_menu_item_multiline,
                getString(R.string.popup_menu_item_multiline),
                roleDescription = "Button"
            )
        )

        val onPopupMenuItemClickListener = object : PopupMenuItem.OnClickListener {
            override fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem) {
                showSnackbar(popupMenuItem)
            }
        }

        showPopupMenu(
            anchorView,
            popupMenuItems,
            PopupMenu.ItemCheckableBehavior.NONE,
            onPopupMenuItemClickListener
        )
    }

    private fun showPopupSingleCheck(anchorView: View) {
        val popupMenuItems = arrayListOf(
            PopupMenuItem(
                R.id.popup_menu_item_all_news,
                getString(R.string.popup_menu_item_all_news),
                showDividerBelow = true
            ),
            PopupMenuItem(
                R.id.popup_menu_item_saved_news,
                getString(R.string.popup_menu_item_saved_news)
            ),
            PopupMenuItem(
                R.id.popup_menu_item_news_from_sites,
                getString(R.string.popup_menu_item_news_from_sites)
            ),
            PopupMenuItem(
                R.id.popup_menu_item_contoso_travel,
                getString(R.string.popup_menu_item_contoso_travel)
            )
        )

        popupMenuItems.forEach { popupMenuItem ->
            popupMenuItem.isChecked = popupMenuItem.id == singleCheckedItemId
        }

        val onPopupMenuItemClickListener = object : PopupMenuItem.OnClickListener {
            override fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem) {
                showSnackbar(popupMenuItem)
                singleCheckedItemId = popupMenuItem.id
            }
        }

        showPopupMenu(
            anchorView,
            popupMenuItems,
            PopupMenu.ItemCheckableBehavior.SINGLE,
            onPopupMenuItemClickListener
        )
    }

    private fun showPopupAllCheck(anchorView: View) {
        if (allCheckedItems == null)
            allCheckedItems = arrayListOf(
                PopupMenuItem(
                    R.id.popup_menu_item_notify_outside,
                    getString(R.string.popup_menu_item_notify_outside),
                    R.drawable.ic_sync_24_filled,
                ),
                PopupMenuItem(
                    R.id.popup_menu_item_notify_inactive,
                    getString(R.string.popup_menu_item_notify_inactive),
                    R.drawable.ic_clock_24_filled
                )
            )

        val onPopupMenuItemClickListener = object : PopupMenuItem.OnClickListener {
            override fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem) {
                showSnackbar(popupMenuItem)
            }
        }

        showPopupMenu(
            anchorView,
            allCheckedItems!!,
            PopupMenu.ItemCheckableBehavior.ALL,
            onPopupMenuItemClickListener
        )
    }

    private fun showPopupMenu(
        anchorView: View,
        items: ArrayList<PopupMenuItem>,
        itemCheckableBehavior: PopupMenu.ItemCheckableBehavior,
        onItemClickListener: PopupMenuItem.OnClickListener
    ) {
        val popupMenu = PopupMenu(this, anchorView, items, itemCheckableBehavior)
        popupMenu.onItemClickListener = onItemClickListener
        popupMenu.show()
    }

    private fun showSnackbar(popupMenuItem: PopupMenuItem) {
        Snackbar.make(
            demoBinding.rootView,
            "${getString(R.string.popup_menu_item_clicked)} ${popupMenuItem.title}"
        ).show()
    }
}