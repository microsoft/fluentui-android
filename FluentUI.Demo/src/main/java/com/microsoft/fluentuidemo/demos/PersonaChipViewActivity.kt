/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.microsoft.fluentui.persona.PersonaChipView
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityPersonaChipViewBinding

class PersonaChipViewActivity : DemoActivity() {

    private lateinit var personaChipBinding: ActivityPersonaChipViewBinding

    private val personaChipViewListener = object : PersonaChipView.Listener {
        override fun onSelected(selected: Boolean) {}

        override fun onClicked() {
            Snackbar.make(
                demoBinding.rootView,
                getString(R.string.persona_chip_example_click),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaChipBinding = ActivityPersonaChipViewBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        createDisabledPersonaChip()
        personaChipBinding.personaChipExampleBasic.listener = personaChipViewListener
        personaChipBinding.personaChipExampleNoIcon.listener = personaChipViewListener
        personaChipBinding.personaChipExampleError.listener = personaChipViewListener
        personaChipBinding.personaChipExampleError.hasError = true
    }

    private fun createDisabledPersonaChip() {
        val personaName = resources.getString(R.string.persona_name_kat_larsson)
        val personaChipView = PersonaChipView(this)
        personaChipView.isEnabled = false
        personaChipView.name = personaName
        personaChipView.email = resources.getString(R.string.persona_email_kat_larsson)
        personaChipView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        personaChipView.avatarContentDescriptionLabel = personaName
        personaChipBinding.personaChipLayout.addView(personaChipView)
    }
}