/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarStyle
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityAvatarViewBinding
import com.squareup.picasso.Picasso

class AvatarViewActivity : DemoActivity() {
    private lateinit var avatarBinding: ActivityAvatarViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        avatarBinding = ActivityAvatarViewBinding.inflate(LayoutInflater.from(container.context), container, true)

        // Avatar drawables with bitmap
        loadBitmapFromPicasso(avatarBinding.avatarExampleMediumPhoto)
        loadBitmapFromGlide(avatarBinding.avatarExampleLargePhoto)

        avatarBinding.avatarExampleXlargePhoto.avatarImageResourceId = R.drawable.avatar_erik_nason

        avatarBinding.avatarExampleSmallPhoto.name = getString(R.string.persona_name_kat_larsson)
        avatarBinding.avatarExampleSmallPhoto.email = getString(R.string.persona_email_kat_larsson)
        avatarBinding.avatarExampleSmallPhoto.avatarImageResourceId = R.drawable.avatar_kat_larsson

        // Avatar drawable with initials
        avatarBinding.avatarExampleLargeInitialsSquare.name =
            getString(R.string.persona_email_henry_brill)
        avatarBinding.avatarExampleLargeInitialsSquare.avatarStyle = AvatarStyle.SQUARE

        // Change initials background color
        avatarBinding.avatarExampleXsmallInitials.avatarBackgroundColor = Color.DKGRAY

        // Add AvatarView with code
        createNewAvatarFromCode()
    }

    private fun loadBitmapFromPicasso(imageView: ImageView) {
        Picasso.get()
            .load(R.drawable.avatar_celeste_burton)
            .into(imageView)
    }

    private fun loadBitmapFromGlide(imageView: ImageView) {
        Glide.with(this)
            .load(R.drawable.avatar_isaac_fielder)
            .into(imageView)
    }

    private fun createNewAvatarFromCode() {
        val avatarName = getString(R.string.persona_name_mauricio_august)
        val avatarView = AvatarView(this)
        avatarView.avatarSize = AvatarSize.XXLARGE
        avatarView.name = avatarName
        avatarView.email = getString(R.string.persona_email_mauricio_august)
        avatarView.avatarContentDescriptionLabel = avatarName
        avatarBinding.avatarCircleExampleXxlarge.addView(avatarView)
    }
}