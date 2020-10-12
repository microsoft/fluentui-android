/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarStyle
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_avatar_view.*

class AvatarViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_avatar_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Avatar drawables with bitmap
        loadBitmapFromPicasso(avatar_example_medium_photo)
        loadBitmapFromGlide(avatar_example_large_photo)

        avatar_example_xlarge_photo.avatarImageResourceId = R.drawable.avatar_erik_nason

        avatar_example_small_photo.name = getString(R.string.persona_name_kat_larsson)
        avatar_example_small_photo.email = getString(R.string.persona_email_kat_larsson)
        avatar_example_small_photo.avatarImageResourceId = R.drawable.avatar_kat_larsson

        // Avatar drawable with initials
        avatar_example_large_initials_square.name = getString(R.string.persona_email_henry_brill)
        avatar_example_large_initials_square.avatarStyle = AvatarStyle.SQUARE

        // Change initials background color
        avatar_example_xsmall_initials.avatarBackgroundColor = Color.DKGRAY

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
        val avatarView = AvatarView(this)
        avatarView.avatarSize = AvatarSize.XXLARGE
        avatarView.name = getString(R.string.persona_name_mauricio_august)
        avatarView.email = getString(R.string.persona_email_mauricio_august)
        avatar_circle_example_xxlarge.addView(avatarView)
    }
}