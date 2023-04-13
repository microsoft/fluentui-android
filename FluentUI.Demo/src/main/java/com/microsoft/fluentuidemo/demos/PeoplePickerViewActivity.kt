/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.microsoft.fluentui.peoplepicker.PeoplePickerAccessibilityTextProvider
import com.microsoft.fluentui.peoplepicker.PeoplePickerPersonaChipClickStyle
import com.microsoft.fluentui.peoplepicker.PeoplePickerView
import com.microsoft.fluentui.persona.IPersona
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityPeoplePickerViewBinding
import com.microsoft.fluentuidemo.util.createCustomPersona
import com.microsoft.fluentuidemo.util.createPersonaList
import java.util.*

class PeoplePickerViewActivity : DemoActivity() {

    private lateinit var samplePersonas: ArrayList<IPersona>

    private lateinit var peoplePickerBinding: ActivityPeoplePickerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        peoplePickerBinding = ActivityPeoplePickerViewBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        samplePersonas = createPersonaList(this)
        val accessibilityTextProvider = getAccessibilityTextProvider()

        // Use attributes to set personaChipClickStyle and label

        peoplePickerBinding.peoplePickerSelect.availablePersonas = samplePersonas
        val selectPickedPersonas = arrayListOf(
            samplePersonas[0],
            samplePersonas[1],
            samplePersonas[4],
            samplePersonas[5]
        )
        val selectSearchDirectoryPersonas = arrayListOf(
            samplePersonas[14],
            samplePersonas[7],
            samplePersonas[8],
            samplePersonas[9]
        )
        peoplePickerBinding.peoplePickerSelect.pickedPersonas = selectPickedPersonas
        peoplePickerBinding.peoplePickerSelect.showSearchDirectoryButton = true
        peoplePickerBinding.peoplePickerSelect.searchDirectorySuggestionsListener =
            createPersonaSuggestionsListener(selectSearchDirectoryPersonas)
        peoplePickerBinding.peoplePickerSelect.allowPersonaChipDragAndDrop = true
        peoplePickerBinding.peoplePickerSelect.onCreatePersona = { name, email ->
            createCustomPersona(this, name, email)
        }
        peoplePickerBinding.peoplePickerSelect.accessibilityTextProvider = accessibilityTextProvider

        peoplePickerBinding.peoplePickerSelectDeselect.availablePersonas = samplePersonas
        val selectDeselectPickedPersonas = arrayListOf(samplePersonas[2])
        peoplePickerBinding.peoplePickerSelectDeselect.pickedPersonas = selectDeselectPickedPersonas
        peoplePickerBinding.peoplePickerSelectDeselect.allowPersonaChipDragAndDrop = true
        peoplePickerBinding.peoplePickerSelectDeselect.accessibilityTextProvider =
            accessibilityTextProvider
        peoplePickerBinding.peoplePickerSelectDeselect.personaChipClickListener =
            object : PeoplePickerView.PersonaChipClickListener {
                override fun onClick(persona: IPersona) {
                    showSnackbar(
                        getString(
                            R.string.people_picker_persona_chip_click,
                            accessibilityTextProvider.getPersonaDescription(persona)
                        )
                    )
                }
            }

        // Use code to set personaChipClickStyle and label

        setupPeoplePickerView(
            "",
            samplePersonas,
            PeoplePickerPersonaChipClickStyle.NONE,
            valueHint = getString(R.string.people_picker_hint),
            showHint = true
        )
        setupPeoplePickerView(
            getString(R.string.people_picker_delete_example),
            samplePersonas,
            PeoplePickerPersonaChipClickStyle.DELETE
        )
        setupPeoplePickerView(
            getString(R.string.people_picker_picked_personas_listener),
            samplePersonas,
            pickedPersonasChangeListener = createPickedPersonasChangeListener()
        )
        setupPeoplePickerView(
            getString(R.string.people_picker_suggestions_listener),
            personaSuggestionsListener = createPersonaSuggestionsListener(samplePersonas)
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            peoplePickerBinding.peoplePickerSelect.requestFocus()
    }

    private fun getAccessibilityTextProvider() =
        object : PeoplePickerAccessibilityTextProvider(resources) {
            override fun getPersonaQuantityText(personas: ArrayList<IPersona>): String {
                return resources.getQuantityString(
                    R.plurals.people_picker_accessibility_text_view_example,
                    personas.size,
                    personas.size
                )
            }
        }

    private fun setupPeoplePickerView(
        labelText: String,
        availablePersonas: ArrayList<IPersona> = ArrayList(),
        personaChipClickStyle: PeoplePickerPersonaChipClickStyle = PeoplePickerPersonaChipClickStyle.SELECT,
        personaSuggestionsListener: PeoplePickerView.PersonaSuggestionsListener? = null,
        pickedPersonasChangeListener: PeoplePickerView.PickedPersonasChangeListener? = null,
        valueHint: String = "",
        showHint: Boolean = false
    ) {
        val peoplePickerView = PeoplePickerView(this)
        peoplePickerView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        with(peoplePickerView) {
            label = labelText
            this.availablePersonas = availablePersonas
            this.personaChipClickStyle = personaChipClickStyle
            this.personaSuggestionsListener = personaSuggestionsListener
            this.pickedPersonasChangeListener = pickedPersonasChangeListener
            allowPersonaChipDragAndDrop = true
            this.valueHint = valueHint
            this.showHint = showHint
        }
        peoplePickerBinding.peoplePickerLayout.addView(peoplePickerView)
    }

    private fun createPickedPersonasChangeListener(): PeoplePickerView.PickedPersonasChangeListener {
        return object : PeoplePickerView.PickedPersonasChangeListener {
            override fun onPersonaAdded(persona: IPersona) {
                showSnackbar("${getString(R.string.people_picker_dialog_title_added)} ${if (persona.name.isNotEmpty()) persona.name else persona.email}")
            }

            override fun onPersonaRemoved(persona: IPersona) {
                showSnackbar("${getString(R.string.people_picker_dialog_title_removed)} ${if (persona.name.isNotEmpty()) persona.name else persona.email}")
            }
        }
    }

    private fun createPersonaSuggestionsListener(personas: ArrayList<IPersona>): PeoplePickerView.PersonaSuggestionsListener {
        return object : PeoplePickerView.PersonaSuggestionsListener {
            override fun onGetSuggestedPersonas(
                searchConstraint: CharSequence?,
                availablePersonas: ArrayList<IPersona>?,
                pickedPersonas: ArrayList<IPersona>,
                completion: (suggestedPersonas: ArrayList<IPersona>) -> Unit
            ) {
                // Simulating async filtering with Timer
                Timer().schedule(
                    object : TimerTask() {
                        override fun run() {
                            completion(filterPersonas(searchConstraint, personas, pickedPersonas))
                        }
                    },
                    500
                )
            }
        }
    }

    private fun showSnackbar(text: String) {
        Snackbar.make(demoBinding.rootView, text, Snackbar.LENGTH_SHORT).show()
    }

    // Basic custom filtering example
    private fun filterPersonas(
        searchConstraint: CharSequence?,
        availablePersonas: ArrayList<IPersona>,
        pickedPersonas: ArrayList<IPersona>
    ): ArrayList<IPersona> {
        if (searchConstraint == null)
            return availablePersonas
        val constraint = searchConstraint.toString().lowercase(Locale.getDefault())
        val filteredResults = availablePersonas.filter {
            it.name.lowercase(Locale.getDefault()).contains(constraint) && !pickedPersonas.contains(
                it
            )
        }
        return ArrayList(filteredResults)
    }
}