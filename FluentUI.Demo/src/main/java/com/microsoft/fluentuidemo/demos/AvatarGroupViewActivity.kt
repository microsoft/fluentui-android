/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.View
import com.microsoft.fluentui.persona.AvatarGroupStyle
import com.microsoft.fluentui.persona.AvatarGroupView
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.popupmenu.PopupMenu
import com.microsoft.fluentui.popupmenu.PopupMenuItem
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.util.createAvatarList
import com.microsoft.fluentuidemo.util.createAvatarNameList
import com.microsoft.fluentuidemo.util.createImageAvatarList
import com.microsoft.fluentuidemo.util.createSmallAvatarList
import kotlinx.android.synthetic.main.activity_avatar_group_view.*
import kotlinx.android.synthetic.main.activity_demo_detail.root_view

class AvatarGroupViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_avatar_group_view
    private var singleCheckedItemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        avatar_face_stack_example_xxlarge_photo.setAvatars(createAvatarList(this))
        avatar_face_stack_example_xlarge_photo.setAvatars(createAvatarNameList(this))
        avatar_face_stack_example_large_photo.setAvatars(createSmallAvatarList(this))
        avatar_face_stack_example_medium_photo.setAvatars(createImageAvatarList(this))
        createFaceStackFromCode(avatar_face_stack_example_small_photo)
        avatar_face_stack_example_xsmall_photo.setAvatars(createAvatarList(this))
        avatar_face_pile_example_xxlarge_photo.setAvatars(createAvatarNameList(this))
        avatar_face_pile_example_xlarge_photo.setAvatars(createSmallAvatarList(this))
        avatar_face_pile_example_large_photo.setAvatars(createImageAvatarList(this))
        createFacePileFromCode(avatar_face_pile_example_medium_photo)
        avatar_face_pile_example_small_photo.setAvatars(createAvatarList(this))
        avatar_face_pile_example_xsmall_photo.setAvatars(createAvatarList(this))
        setupMaxAvatarDisplayed(max_displayed_avatar)
    }

    private fun createFaceStackFromCode(avatarGroupView: AvatarGroupView) {
        avatarGroupView.avatarGroupStyle = AvatarGroupStyle.STACK
        avatarGroupView.avatarSize = AvatarSize.SMALL
        avatarGroupView.setAvatars(createAvatarList(this))
        avatarGroupView.listener = object : AvatarGroupView.Listener {
            override fun onAvatarClicked(index: Int) {
                Snackbar.make(root_view, String.format(getString(R.string.avatar_group_avatar_clicked), index), Snackbar.LENGTH_SHORT).show()
            }
            override fun onOverFlowClicked() {
                Snackbar.make(root_view, getString(R.string.avatar_group_overflow_clicked), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun createFacePileFromCode(avatarGroupView: AvatarGroupView) {
        avatarGroupView.avatarGroupStyle = AvatarGroupStyle.PILE
        avatarGroupView.avatarSize = AvatarSize.MEDIUM
        avatarGroupView.setAvatars(createAvatarList(this))
    }

    private fun setupMaxAvatarDisplayed(anchorView: View) {
        val popupMenuItems: ArrayList<PopupMenuItem> = ArrayList()
        for(id in 1..4) {
            popupMenuItems.add(PopupMenuItem(id,id.toString()))
            max_displayed_avatar.text = id.toString()
        }

        val onPopupMenuItemClickListener = object : PopupMenuItem.OnClickListener {
            override fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem) {
                singleCheckedItemId = popupMenuItem.id
                max_displayed_avatar.text = popupMenuItem.title
                setMaxAvatarDisplayedForAllViews(popupMenuItem.id)
            }
        }
        val popupMenu = PopupMenu(this, anchorView, popupMenuItems, PopupMenu.ItemCheckableBehavior.SINGLE)
        popupMenu.onItemClickListener = onPopupMenuItemClickListener
        anchorView.setOnClickListener{
            popupMenu.show()
        }
    }

    private fun setMaxAvatarDisplayedForAllViews(id: Int) {
        avatar_face_stack_example_xxlarge_photo.maxDisplayedAvatars = id
        avatar_face_stack_example_xlarge_photo.maxDisplayedAvatars = id
        avatar_face_stack_example_large_photo.maxDisplayedAvatars = id
        avatar_face_stack_example_medium_photo.maxDisplayedAvatars = id
        avatar_face_stack_example_small_photo.maxDisplayedAvatars = id
        avatar_face_stack_example_xsmall_photo.maxDisplayedAvatars = id
        avatar_face_pile_example_xxlarge_photo.maxDisplayedAvatars = id
        avatar_face_pile_example_xlarge_photo.maxDisplayedAvatars = id
        avatar_face_pile_example_large_photo.maxDisplayedAvatars = id
        avatar_face_pile_example_medium_photo.maxDisplayedAvatars = id
        avatar_face_pile_example_small_photo.maxDisplayedAvatars = id
        avatar_face_pile_example_xsmall_photo.maxDisplayedAvatars = id
    }
}