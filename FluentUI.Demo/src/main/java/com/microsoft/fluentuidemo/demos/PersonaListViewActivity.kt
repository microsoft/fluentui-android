/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import com.microsoft.fluentui.persona.IPersona
import com.microsoft.fluentui.persona.PersonaListView
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityPersonaListViewBinding
import com.microsoft.fluentuidemo.util.createPersonaList

class PersonaListViewActivity : DemoActivity() {

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private lateinit var personaListBinding: ActivityPersonaListViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        personaListBinding = ActivityPersonaListViewBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        demoBinding.appBar.scrollTargetViewId = R.id.persona_list_view_example

        personaListBinding.personaListViewExample.personas = createPersonaList(this)
        personaListBinding.personaListViewExample.onItemClickedListener =
            object : PersonaListView.OnItemClickedListener {
                override fun onItemClicked(persona: IPersona) {
                    Snackbar.make(
                        demoBinding.rootView,
                        "You clicked on the cell for ${persona.name}, ${persona.subtitle}",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }
}