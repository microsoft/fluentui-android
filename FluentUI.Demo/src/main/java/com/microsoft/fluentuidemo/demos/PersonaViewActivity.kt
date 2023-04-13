/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.PersonaView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityPersonaViewBinding

class PersonaViewActivity : DemoActivity() {

    private lateinit var personaBinding: ActivityPersonaViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaBinding = ActivityPersonaViewBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        // Add Persona programmatically
        createNewPersonaFromCode()
    }

    private fun createNewPersonaFromCode() {
        val personaName = resources.getString(R.string.persona_name_mauricio_august)
        val personaView = PersonaView(this)
        personaView.avatarSize = AvatarSize.SMALL
        personaView.name = personaName
        personaView.email = resources.getString(R.string.persona_email_mauricio_august)
        personaView.avatarContentDescriptionLabel = personaName
        personaView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        personaBinding.personaLayout.addView(personaView)
    }
}