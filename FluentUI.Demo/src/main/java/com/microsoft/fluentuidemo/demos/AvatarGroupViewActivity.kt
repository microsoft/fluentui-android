/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.microsoft.fluentui.persona.AvatarBorderStyle
import com.microsoft.fluentui.persona.AvatarGroupStyle
import com.microsoft.fluentui.persona.AvatarGroupView
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.popupmenu.PopupMenu
import com.microsoft.fluentui.popupmenu.PopupMenuItem
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityAvatarGroupViewBinding
import com.microsoft.fluentuidemo.util.createAvatarList
import com.microsoft.fluentuidemo.util.createAvatarNameList
import com.microsoft.fluentuidemo.util.createImageAvatarList
import com.microsoft.fluentuidemo.util.createSmallAvatarList

class AvatarGroupViewActivity : DemoActivity() {
    private var singleCheckedItemId: Int = -1
    var borderStyle: AvatarBorderStyle = AvatarBorderStyle.RING

    private lateinit var avatarGroupBinding: ActivityAvatarGroupViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        avatarGroupBinding = ActivityAvatarGroupViewBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        avatarGroupBinding.avatarFaceStackExampleXxlargePhoto.setAvatars(createAvatarList(this))
        avatarGroupBinding.avatarFaceStackExampleXlargePhoto.setAvatars(createAvatarNameList(this))
        avatarGroupBinding.avatarFaceStackExampleLargePhoto.setAvatars(createSmallAvatarList(this))
        avatarGroupBinding.avatarFaceStackExampleMediumPhoto.setAvatars(createImageAvatarList(this))
        createFaceStackFromCode(avatarGroupBinding.avatarFaceStackExampleSmallPhoto)
        avatarGroupBinding.avatarFaceStackExampleXsmallPhoto.setAvatars(createAvatarList(this))
        avatarGroupBinding.avatarFacePileExampleXxlargePhoto.setAvatars(createAvatarNameList(this))
        avatarGroupBinding.avatarFacePileExampleXlargePhoto.setAvatars(createSmallAvatarList(this))
        avatarGroupBinding.avatarFacePileExampleLargePhoto.setAvatars(createImageAvatarList(this))
        createFacePileFromCode(avatarGroupBinding.avatarFacePileExampleMediumPhoto)
        avatarGroupBinding.avatarFacePileExampleSmallPhoto.setAvatars(createAvatarList(this))
        avatarGroupBinding.avatarFacePileExampleXsmallPhoto.setAvatars(createAvatarList(this))

        avatarGroupBinding.avatarFaceStackExampleXsmallPhotoOverflow.setAvatars(
            createAvatarList(
                this
            )
        )
        avatarGroupBinding.avatarFacePileExampleXsmallPhotoOverflow.setAvatars(createAvatarList(this))

        setupMaxAvatarDisplayed(avatarGroupBinding.maxDisplayedAvatar)

        avatarGroupBinding.overflowAvatarCount.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    avatarGroupBinding.avatarFaceStackExampleXsmallPhotoOverflow.overflowAvatarCount =
                        Integer.parseInt(avatarGroupBinding.overflowAvatarCount.text.toString())
                    avatarGroupBinding.avatarFacePileExampleXsmallPhotoOverflow.overflowAvatarCount =
                        Integer.parseInt(avatarGroupBinding.overflowAvatarCount.text.toString())

                    val imm =
                        v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                    true
                }
                else -> {
                    false
                }
            }
        }

        avatarGroupBinding.avatarBorderToggle.setOnClickListener {
            toggleBorders()
        }
    }

    private fun createFaceStackFromCode(avatarGroupView: AvatarGroupView) {
        avatarGroupView.avatarGroupStyle = AvatarGroupStyle.STACK
        avatarGroupView.avatarSize = AvatarSize.SMALL
        avatarGroupView.avatarBorderStyle = AvatarBorderStyle.RING
        avatarGroupView.setAvatars(createAvatarList(this))
        avatarGroupView.listener = object : AvatarGroupView.Listener {
            override fun onAvatarClicked(index: Int) {
                Snackbar.make(
                    demoBinding.rootView,
                    String.format(getString(R.string.avatar_group_avatar_clicked), index),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onOverFlowClicked() {
                Snackbar.make(
                    demoBinding.rootView,
                    getString(R.string.avatar_group_overflow_clicked),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createFacePileFromCode(avatarGroupView: AvatarGroupView) {
        avatarGroupView.avatarGroupStyle = AvatarGroupStyle.PILE
        avatarGroupView.avatarSize = AvatarSize.MEDIUM
        avatarGroupView.avatarBorderStyle = AvatarBorderStyle.RING
        avatarGroupView.setAvatars(createAvatarList(this))
    }

    private fun setupMaxAvatarDisplayed(anchorView: View) {
        val popupMenuItems: ArrayList<PopupMenuItem> = ArrayList()
        for (id in 1..4) {
            popupMenuItems.add(PopupMenuItem(id, id.toString()))
            avatarGroupBinding.maxDisplayedAvatar.text = id.toString()
        }

        val onPopupMenuItemClickListener = object : PopupMenuItem.OnClickListener {
            override fun onPopupMenuItemClicked(popupMenuItem: PopupMenuItem) {
                singleCheckedItemId = popupMenuItem.id
                avatarGroupBinding.maxDisplayedAvatar.text = popupMenuItem.title
                setMaxAvatarDisplayedForAllViews(popupMenuItem.id)
            }
        }
        val popupMenu =
            PopupMenu(this, anchorView, popupMenuItems, PopupMenu.ItemCheckableBehavior.SINGLE)
        popupMenu.onItemClickListener = onPopupMenuItemClickListener
        anchorView.setOnClickListener {
            popupMenu.show()
        }
    }

    private fun toggleBorders() {
        borderStyle =
            if (borderStyle == AvatarBorderStyle.RING) AvatarBorderStyle.NO_BORDER else AvatarBorderStyle.RING
        avatarGroupBinding.avatarFaceStackExampleXxlargePhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFaceStackExampleXlargePhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFaceStackExampleLargePhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFaceStackExampleMediumPhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFaceStackExampleSmallPhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFaceStackExampleXsmallPhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFacePileExampleXxlargePhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFacePileExampleXlargePhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFacePileExampleLargePhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFacePileExampleMediumPhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFacePileExampleSmallPhoto.avatarBorderStyle = borderStyle
        avatarGroupBinding.avatarFacePileExampleXsmallPhoto.avatarBorderStyle = borderStyle
    }

    private fun setMaxAvatarDisplayedForAllViews(id: Int) {
        avatarGroupBinding.avatarFaceStackExampleXxlargePhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFaceStackExampleXlargePhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFaceStackExampleLargePhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFaceStackExampleMediumPhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFaceStackExampleSmallPhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFaceStackExampleXsmallPhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFacePileExampleXxlargePhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFacePileExampleXlargePhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFacePileExampleLargePhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFacePileExampleMediumPhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFacePileExampleSmallPhoto.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFacePileExampleXsmallPhoto.maxDisplayedAvatars = id

        avatarGroupBinding.avatarFaceStackExampleXsmallPhotoOverflow.maxDisplayedAvatars = id
        avatarGroupBinding.avatarFacePileExampleXsmallPhotoOverflow.maxDisplayedAvatars = id
    }
}