/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.persona.IPersona
import com.microsoft.fluentui.persona.Persona
import com.microsoft.fluentuidemo.R
import java.io.Serializable

fun createPersonaList(context: Context?): ArrayList<IPersona> {
    val context = context ?: return arrayListOf()
    return arrayListOf(
        createPersona(
            context.getString(R.string.persona_name_amanda_brady),
            context.getString(R.string.persona_subtitle_manager),
            imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_amanda_brady),
            email = context.getString(R.string.persona_email_amanda_brady)
        ),
        createPersona(
            context.getString(R.string.persona_name_lydia_bauer),
            context.getString(R.string.persona_subtitle_researcher),
            email = context.getString(R.string.persona_email_lydia_bauer)
        ),
        createPersona(
            context.getString(R.string.persona_name_daisy_phillips),
            context.getString(R.string.persona_subtitle_designer),
            imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_daisy_phillips),
            email = context.getString(R.string.persona_email_daisy_phillips)
        ),
        createPersona(
            context.getString(R.string.persona_name_allan_munger) + context.getString(R.string.persona_truncation),
            context.getString(R.string.persona_subtitle_manager),
            email = context.getString(R.string.persona_email_allan_munger)
        ),
        createPersona(
            context.getString(R.string.persona_name_kat_larsson),
            context.getString(R.string.persona_subtitle_designer),
            imageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar_kat_larsson),
            email = context.getString(R.string.persona_email_kat_larsson)
        ),
        createPersona(
            context.getString(R.string.persona_name_ashley_mccarthy),
            context.getString(R.string.persona_subtitle_engineer)
        ),
        createPersona(
            context.getString(R.string.persona_name_miguel_garcia),
            context.getString(R.string.persona_subtitle_researcher),
            imageUri = getUriFromResource(context, R.drawable.avatar_miguel_garcia),
            email = context.getString(R.string.persona_email_miguel_garcia)
        ),
        createPersona(
            context.getString(R.string.persona_name_carole_poland),
            context.getString(R.string.persona_subtitle_researcher),
            email = context.getString(R.string.persona_email_carole_poland)
        ),
        createPersona(
            context.getString(R.string.persona_name_mona_kane),
            context.getString(R.string.persona_subtitle_designer),
            email = context.getString(R.string.persona_email_mona_kane)
        ),
        createPersona(
            context.getString(R.string.persona_name_carlos_slattery),
            context.getString(R.string.persona_subtitle_engineer),
            email = context.getString(R.string.persona_email_carlos_slattery)
        ),
        createPersona(
            context.getString(R.string.persona_name_wanda_howard),
            context.getString(R.string.persona_subtitle_engineer),
            email = context.getString(R.string.persona_email_wanda_howard)
        ),
        createPersona(
            context.getString(R.string.persona_name_tim_deboer),
            context.getString(R.string.persona_subtitle_researcher),
            email = context.getString(R.string.persona_email_tim_deboer)
        ),
        createPersona(
            context.getString(R.string.persona_name_robin_counts),
            context.getString(R.string.persona_subtitle_designer),
            email = context.getString(R.string.persona_email_robin_counts)
        ),
        createPersona(
            context.getString(R.string.persona_name_elliot_woodward),
            context.getString(R.string.persona_subtitle_designer),
            email = context.getString(R.string.persona_email_elliot_woodward)
        ),
        createPersona(
            context.getString(R.string.persona_name_cecil_folk),
            context.getString(R.string.persona_subtitle_manager),
            email = context.getString(R.string.persona_email_cecil_folk)
        ),
        createPersona(
            context.getString(R.string.persona_name_celeste_burton),
            context.getString(R.string.persona_subtitle_researcher),
            email = context.getString(R.string.persona_email_celeste_burton)
        ),
        createPersona(
            context.getString(R.string.persona_name_elvia_atkins),
            context.getString(R.string.persona_subtitle_designer),
            imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_elvia_atkins),
            email = context.getString(R.string.persona_email_elvia_atkins)
        ),
        createPersona(
            context.getString(R.string.persona_name_colin_ballinger),
            context.getString(R.string.persona_subtitle_manager),
            imageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_colin_ballinger),
            email = context.getString(R.string.persona_email_colin_ballinger)
        ),
        createPersona(
            context.getString(R.string.persona_name_katri_ahokas),
            context.getString(R.string.persona_subtitle_designer),
            imageBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.avatar_katri_ahokas),
            email = context.getString(R.string.persona_email_katri_ahokas)
        ),
        createPersona(
            context.getString(R.string.persona_name_henry_brill),
            context.getString(R.string.persona_subtitle_engineer),
            email = context.getString(R.string.persona_email_henry_brill)
        ),
        createPersona(
            context.getString(R.string.persona_name_johnie_mcconnell),
            context.getString(R.string.persona_subtitle_researcher),
            imageUri = getUriFromResource(context, R.drawable.avatar_johnie_mcconnell),
            email = context.getString(R.string.persona_email_johnie_mcconnell)
        ),
        createPersona(
            context.getString(R.string.persona_name_kevin_sturgis),
            context.getString(R.string.persona_subtitle_researcher),
            email = context.getString(R.string.persona_email_kevin_sturgis)
        ),
        createPersona(
            context.getString(R.string.persona_name_kristen_patterson),
            context.getString(R.string.persona_subtitle_designer),
            email = context.getString(R.string.persona_email_kristen_patterson)
        ),
        createPersona(
            context.getString(R.string.persona_name_charlotte_waltson),
            context.getString(R.string.persona_subtitle_engineer),
            email = context.getString(R.string.persona_email_charlotte_waltson)
        ),
        createPersona(
            context.getString(R.string.persona_name_erik_nason),
            context.getString(R.string.persona_subtitle_engineer),
            email = context.getString(R.string.persona_email_erik_nason)
        ),
        createPersona(
            context.getString(R.string.persona_name_isaac_fielder),
            context.getString(R.string.persona_subtitle_researcher),
            email = context.getString(R.string.persona_email_isaac_fielder)
        ),
        createPersona(
            context.getString(R.string.persona_name_mauricio_august),
            context.getString(R.string.persona_subtitle_designer),
            email = context.getString(R.string.persona_email_mauricio_august)
        ),
        createCustomPersona(context)
    )
}

private fun getUriFromResource(context: Context, avatarDrawable: Int): Uri? {
    return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
        "://" + context.resources.getResourcePackageName(avatarDrawable) +
        '/'.toString() + context.resources.getResourceTypeName(avatarDrawable) +
        '/'.toString() + context.resources.getResourceEntryName(avatarDrawable)
    )
}

private fun createPersona(
    name: String,
    subtitle: String,
    imageResource: Int? = null,
    imageDrawable: Drawable? = null,
    imageBitmap: Bitmap? = null,
    imageUri: Uri? = null,
    email: String = ""
): IPersona {
    val persona = Persona(name)
    persona.email = email
    persona.subtitle = subtitle
    persona.avatarImageResourceId = imageResource
    persona.avatarImageDrawable = imageDrawable
    persona.avatarImageBitmap = imageBitmap
    persona.avatarImageUri = imageUri
    return persona
}

fun createCustomPersona(
    context: Context,
    name: String = context.getString(R.string.persona_name_robert_tolbert),
    email: String = ""
): IPersona {
    val customPersona =  CustomPersona(name, email)
    customPersona.avatarImageDrawable = ContextCompat.getDrawable(context, R.drawable.avatar_robert_tolbert)
    customPersona.description = context.getString(R.string.people_picker_custom_persona_description)
    return customPersona
}

data class CustomPersona(override var name: String = "", override var email: String = "") : IPersona, Serializable {
    var description: String = ""
    override var subtitle: String = ""
    override var footer: String = ""
    override var avatarImageBitmap: Bitmap? = null
    override var avatarImageDrawable: Drawable? = null
    override var avatarImageResourceId: Int? = null
    override var avatarImageUri: Uri? = null
    override var avatarBackgroundColor: Int? = null
    override var avatarContentDescriptionLabel: String = ""
}