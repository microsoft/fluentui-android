/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.peoplepicker

import android.content.res.Resources
import com.microsoft.fluentui.R
import com.microsoft.fluentui.persona.IPersona

/**
 * Customizes text announced by the screen reader for PeoplePickerTextView.
 */
open class PeoplePickerAccessibilityTextProvider(val resources: Resources) {
    /**
     * Announces when the popup opens showing the list of suggested personas.
     */
    open fun getPersonaSuggestionsOpenedText(personas: ArrayList<IPersona>): String =
        resources.getQuantityString(
            R.plurals.people_picker_accessibility_suggestions_opened,
            personas.size,
            personas.size
        )

    /**
     * Announces how many personas are in the currently focused PeoplePickerTextView.
     */
    open fun getPersonaQuantityText(personas: ArrayList<IPersona>): String =
        resources.getQuantityString(
            R.plurals.people_picker_accessibility_text_view,
            personas.size,
            personas.size
        )

    /**
     * Announced any time a specific persona has focus or receives an event.
     */
    open fun getPersonaDescription(persona: IPersona): String =
        getDefaultPersonaDescription(persona)

    internal fun getDefaultPersonaDescription(persona: IPersona): String =
        if (persona.name.isNotEmpty()) persona.name else persona.email
}