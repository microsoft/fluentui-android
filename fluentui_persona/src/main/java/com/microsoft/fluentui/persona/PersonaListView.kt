/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persona

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import java.util.*

/**
 * This is a custom [RecyclerView] with a set adapter and layoutManager. It provides an interface for the list data and onItemClickedListener and
 * adds a custom [DividerItemDecoration] to each row.
 */
class PersonaListView : RecyclerView {
    /**
     * [personas] contains the collection of Personas that the adapter binds to the ViewHolder.
     */
    var personas = ArrayList<IPersona>()
        set(value) {
            field = value
            personaListAdapter.personas = value
        }

    /**
     * This onItemClickedListener is called when a [PersonaView] cell is clicked.
     */
    var onItemClickedListener: OnItemClickedListener? = null
        set(value) {
            field = value
            personaListAdapter.onItemClickedListener = value
        }

    private val personaListAdapter = PersonaListAdapter(context)

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(appContext, R.style.Theme_FluentUI_Persona), attrs, defStyleAttr) {
        adapter = personaListAdapter
        layoutManager = LinearLayoutManager(context)
        addItemDecoration(ListItemDivider(context, DividerItemDecoration.VERTICAL))
    }

    interface OnItemClickedListener {
        fun onItemClicked(persona: IPersona)
    }
}