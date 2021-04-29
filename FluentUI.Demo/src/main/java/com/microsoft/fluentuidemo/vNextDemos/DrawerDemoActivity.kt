package com.microsoft.fluentuidemo.vNextDemos

/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.microsoft.fluentui.generator.AvatarSize
import com.microsoft.fluentui.generator.DrawerBehavior
import com.microsoft.fluentui.generator.DrawerType
import com.microsoft.fluentui.generator.ListLeadingViewSize
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.vNext.drawer.DrawerDialog
import com.microsoft.fluentui.vNext.drawer.OnDrawerContentCreatedListener
import com.microsoft.fluentui.vNext.listitem.IBaseListItem
import com.microsoft.fluentui.vNext.listitem.ListItem
import com.microsoft.fluentui.vNext.persona.AvatarPresence
import com.microsoft.fluentui.vNext.persona.AvatarView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.vNextDemos.list.ListAdapter
import kotlinx.android.synthetic.main.activity_drawer_vnext.*
import kotlinx.android.synthetic.main.demo_left_nav_content.view.*
import java.util.ArrayList

class DrawerDemoActivity : DemoActivity(), OnDrawerContentCreatedListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_drawer_vnext

    private var drawerDialogDemo: DrawerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        show_left_dialog_button.setOnClickListener(this::clickListener)
        show_left_dialog_leftnav_button.setOnClickListener(this::clickListener)
        show_left_status_dialog_button.setOnClickListener(this::clickListener)
        show_left_toolbar_dialog_button.setOnClickListener(this::clickListener)
        show_right_dialog_button.setOnClickListener(this::clickListener)
    }

    private fun clickListener(v: View) {
        when (v.id) {
            R.id.show_left_dialog_button -> {
                drawerDialogDemo = DrawerDialog(this, DrawerType.LEFT_NAV, drawerBehavior = DrawerBehavior.FULL)
                drawerDialogDemo?.setContentView(R.layout.demo_side_drawer_content)
            }
            R.id.show_left_dialog_leftnav_button -> {
                drawerDialogDemo = DrawerDialog(this, DrawerType.LEFT_NAV, drawerBehavior = DrawerBehavior.FULL)
                drawerDialogDemo?.onDrawerContentCreatedListener = this
                drawerDialogDemo?.setContentView(R.layout.demo_left_nav_content)
            }
            R.id.show_left_status_dialog_button -> {
                drawerDialogDemo = DrawerDialog(this, DrawerType.LEFT_NAV, drawerBehavior = DrawerBehavior.BELOW_STATUS)
                drawerDialogDemo?.setContentView(R.layout.demo_side_drawer_content)
            }
            R.id.show_left_toolbar_dialog_button -> {
                drawerDialogDemo = DrawerDialog(this, DrawerType.LEFT_NAV, drawerBehavior = DrawerBehavior.BELOW_TOOLBAR)
                drawerDialogDemo?.onDrawerContentCreatedListener = this
                drawerDialogDemo?.setContentView(R.layout.demo_left_nav_content)
            }
            R.id.show_right_dialog_button -> {
                drawerDialogDemo = DrawerDialog(this, DrawerType.RIGHT_NAV)
                drawerDialogDemo?.setContentView(R.layout.demo_side_drawer_content)
            }
        }
        drawerDialogDemo?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        drawerDialogDemo?.dismiss()
    }

    override fun onDrawerContentCreated(drawerContents: View) {
        drawerContents.avatar_header.avatar.apply {
            name = getString(R.string.persona_name_kristen_patterson)
            email = getString(R.string.persona_email_kristen_patterson)
            presence = AvatarPresence.AVAILABLE
        }
        val listAdapter = ListAdapter()
        listAdapter.items = createList()

        drawerContents.demo_list.adapter = listAdapter
    }

    private fun createList(): ArrayList<IBaseListItem> {
        return arrayListOf(
                createListItem(
                        getString(R.string.persona_name_kristen_patterson),
                        subtitle = getString(R.string.persona_email_kristen_patterson),
                        customViewSize = ListLeadingViewSize.LARGE,
                        customView = createExampleAvatarView(R.drawable.avatar_kristin_patterson, R.string.persona_name_kristen_patterson, AvatarSize.MEDIUM)
                ),
                createListItem(getString(R.string.drawer_demo_available_string), customViewSize = ListLeadingViewSize.MEDIUM, customView = createExampleCustomView(getDrawable(R.drawable.ic_fluent_presence_available_16_filled))),
                createListItem(getString(R.string.drawer_demo_status_string), customViewSize = ListLeadingViewSize.MEDIUM, customView = createExampleCustomView(getDrawable(R.drawable.ic_edit_24_filled))),
                createListItem(getString(R.string.drawer_demo_notification_string), customViewSize = ListLeadingViewSize.MEDIUM, customView = createExampleCustomView(getDrawable(R.drawable.ic_alert_28_regular))),
                createListItem(getString(R.string.drawer_demo_settings_string), customViewSize = ListLeadingViewSize.MEDIUM, customView = createExampleCustomView(getDrawable(R.drawable.ic_settings_24_regular)))
        )
    }

    private fun createExampleCustomView(drawable: Drawable?): ImageView {
        val imageView = ImageView(this)
        imageView.setImageDrawable(drawable)
        return imageView
    }

    private fun createExampleAvatarView(avatarImageResourceId: Int = -1, avatarNameStringId: Int = -1, avatarSize: AvatarSize = AvatarSize.SMALL): AvatarView {
        val avatarView = AvatarView(this)
        avatarView.avatar.apply {
            this.avatarSize = avatarSize
            this.avatarImageResourceId = avatarImageResourceId
            if (avatarNameStringId != -1)
                this.name = getString(avatarNameStringId)
            presence = AvatarPresence.BUSY
        }
        return avatarView
    }

    private fun createListItem(
            title: String,
            subtitle: String = "",
            footer: String = "",
            titleStyle: Int = -1,
            customView: View? = null,
            customViewSize: ListLeadingViewSize = ListLeadingViewSize.MEDIUM,
            customAccessoryView: View? = null,
            customSecondarySubtitleView: View? = null,
            addCustomAccessoryViewClick: Boolean = false,
            wrap: Boolean = false
    ): ListItem {
        val item = ListItem()
        item.title = title
        item.subtitle = subtitle
        item.footer = footer
        item.customAccessoryView = customAccessoryView
        item.customView = customView
        item.customViewSize = customViewSize
        item.titleStyleRes = titleStyle
        item.background = R.drawable.ms_ripple_transparent_background
        item.customSecondarySubtitleView = customSecondarySubtitleView

        if (wrap) {
            item.titleMaxLines = 4
            item.subtitleMaxLines = 4
            item.footerMaxLines = 4
        } else {
            item.titleTruncateAt = TextUtils.TruncateAt.MIDDLE
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            // In earlier APIs this crashes with an ArrayIndexOutOfBoundsException
                item.subtitleTruncateAt = TextUtils.TruncateAt.END
        }

        if (addCustomAccessoryViewClick && customAccessoryView != null)
            customAccessoryView.setOnClickListener {
                Snackbar.make(
                        customAccessoryView,
                        getString(R.string.list_item_click_custom_accessory_view),
                        Snackbar.LENGTH_SHORT
                ).show()
            }

        return item
    }
}