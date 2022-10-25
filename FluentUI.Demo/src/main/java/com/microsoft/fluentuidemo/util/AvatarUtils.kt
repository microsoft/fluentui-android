package com.microsoft.fluentuidemo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.persona.IAvatar
import com.microsoft.fluentuidemo.R

fun createAvatarList(context: Context?): ArrayList<IAvatar> {
    val context = context ?: return arrayListOf()
    return arrayListOf(
            createAvatar(
                    context.getString(R.string.persona_name_daisy_phillips),
                    imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_daisy_phillips),
                    email = context.getString(R.string.persona_email_daisy_phillips)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_allan_munger) + context.getString(R.string.persona_truncation),
                    email = context.getString(R.string.persona_email_allan_munger)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_kat_larsson),
                    imageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar_kat_larsson),
                    email = context.getString(R.string.persona_email_kat_larsson)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_ashley_mccarthy)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_miguel_garcia),
                    email = context.getString(R.string.persona_email_miguel_garcia)
            )
    )
}

fun createAvatarNameList(context: Context?): ArrayList<IAvatar> {
    val context = context ?: return arrayListOf()
    return arrayListOf(
            createAvatar(
                    context.getString(R.string.persona_name_amanda_brady),
                    email = context.getString(R.string.persona_email_amanda_brady)
            ),
            createAvatar(
                    context.getString(R.string.persona_subtitle_researcher),
                    email = context.getString(R.string.persona_email_lydia_bauer)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_daisy_phillips),
                    email = context.getString(R.string.persona_email_daisy_phillips)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_allan_munger) + context.getString(R.string.persona_truncation),
                    email = context.getString(R.string.persona_email_allan_munger)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_kat_larsson),
                    email = context.getString(R.string.persona_email_kat_larsson)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_ashley_mccarthy)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_miguel_garcia),
                    email = context.getString(R.string.persona_email_miguel_garcia)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_carole_poland),
                    email = context.getString(R.string.persona_email_carole_poland)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_mona_kane),
                    email = context.getString(R.string.persona_email_mona_kane)
            )
    )
}

fun createSmallAvatarList(context: Context?): ArrayList<IAvatar> {
    val context = context ?: return arrayListOf()
    return arrayListOf(
            createAvatar(
                    context.getString(R.string.persona_name_amanda_brady),
                    imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_amanda_brady),
                    email = context.getString(R.string.persona_email_amanda_brady)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_amanda_brady),
                    email = context.getString(R.string.persona_email_amanda_brady)
            ),
            createAvatar(
                    context.getString(R.string.persona_subtitle_researcher),
                    email = context.getString(R.string.persona_email_lydia_bauer)
            )
    )
}

fun createImageAvatarList(context: Context?): ArrayList<IAvatar> {
    val context = context ?: return arrayListOf()
    return arrayListOf(
            createAvatar(
                    context.getString(R.string.persona_name_amanda_brady),
                    imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_amanda_brady),
                    email = context.getString(R.string.persona_email_amanda_brady)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_daisy_phillips),
                    imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_daisy_phillips),
                    email = context.getString(R.string.persona_email_daisy_phillips)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_elvia_atkins),
                    imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_elvia_atkins),
                    email = context.getString(R.string.persona_email_elvia_atkins)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_colin_ballinger),
                    imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_colin_ballinger),
                    email = context.getString(R.string.persona_email_colin_ballinger)
            ),
            createAvatar(
                    context.getString(R.string.persona_name_katri_ahokas),
                    imageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar_katri_ahokas),
                    email = context.getString(R.string.persona_email_katri_ahokas)
            )
    )
}
fun createAvatar(
        name: String,
        imageResource: Int? = null,
        imageDrawable: Drawable? = null,
        imageBitmap: Bitmap? = null,
        imageUri: Uri? = null,
        imageBackgroundColor: Int? = null,
        email: String = ""
): IAvatar {
    val avatar = Avatar(name)
    avatar.email = email
    avatar.avatarImageResourceId = imageResource
    avatar.avatarImageDrawable = imageDrawable
    avatar.avatarImageBitmap = imageBitmap
    avatar.avatarImageUri = imageUri
    avatar.avatarBackgroundColor = imageBackgroundColor
    // We can set any description here
    avatar.avatarContentDescriptionLabel = name
    return avatar
}

data class Avatar(override var name: String) : IAvatar {
    override var email: String = ""
    override var avatarImageBitmap: Bitmap? = null
    override var avatarImageDrawable: Drawable? = null
    override var avatarImageResourceId: Int? = null
    override var avatarImageUri: Uri? = null
    override var avatarBackgroundColor: Int? = null
    override var avatarContentDescriptionLabel: String = ""
}