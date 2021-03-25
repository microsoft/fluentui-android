/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persona

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.util.*

/**
 * This adapter controls data binding and ViewHolders for [PersonaListView].
 */
internal class PersonaListAdapter(private val context: Context) : RecyclerView.Adapter<PersonaListAdapter.ViewHolder>() {
    /**
     * [PersonaListView.OnItemClickedListener] for when a list item is clicked
     */
    var onItemClickedListener: PersonaListView.OnItemClickedListener? = null
    /**
     * Collection of [Persona] objects that hold data to create the [PersonaView]s
     */
    var personas = ArrayList<IPersona>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PersonaView(context)
        view.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        view.avatarSize = AvatarSize.LARGE
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position in 0 until personas.size)
            holder.setPersona(personas[position])
        else
            return
    }

    override fun getItemCount() = personas.size

    private fun onItemClicked(persona: IPersona) {
        onItemClickedListener?.onItemClicked(persona)
    }

    inner class ViewHolder : RecyclerView.ViewHolder, View.OnClickListener {
        private val personaView: PersonaView
        private lateinit var persona: IPersona

        constructor(view: PersonaView) : super(view) {
            personaView = view
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            onItemClicked(persona)
        }

        fun setPersona(persona: IPersona) {
            this.persona = persona
            personaView.setPersona(persona)
        }
    }
}
