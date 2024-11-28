/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.peoplepicker

import android.content.Context
import android.graphics.drawable.InsetDrawable
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.peoplepicker.databinding.PeoplePickerSearchDirectoryBinding
import com.microsoft.fluentui.persona.*
import java.util.*

/**
 * Provides views for the DropDownListView that shows the [personas].
 * The DropDown used by [MultiAutoCompleteTextView] (via [TokenCompleteTextView]) uses a ListView
 * so we use an [ArrayAdapter] to generate the views instead of a [RecyclerView.Adapter].
 */
internal class PeoplePickerTextViewAdapter : ArrayAdapter<IPersona>, Filterable {
    private enum class ViewType {
        PERSONA, SEARCH_DIRECTORY
    }

    /**
     * Collection of [Persona] objects that hold data to create the [PersonaView]s
     */
    var personas: ArrayList<IPersona> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var showSearchDirectoryButton: Boolean = false
    var isSearchingDirectory: Boolean = false
        set(value) {
            field = value
            searchDirectoryBinding?.root?.isEnabled = !value
            updateSearchDirectoryText()
        }

    var onSearchDirectoryButtonClicked: View.OnClickListener? = null

    private var filter: Filter
    private var listView: ListView? = null
        set(value) {
            if (value == null || field == value)
                return
            field = value
            value.divider = createDivider()
            // This hides the last divider
            value.overscrollFooter = ContextCompat.getDrawable(context, android.R.color.transparent)
        }
    private var searchDirectoryBinding: PeoplePickerSearchDirectoryBinding? = null
        set(value) {
            field = value
            searchDirectoryBinding?.root?.post {
                // We set this in a post so that the we get the correct instance of the text view.
                // This assumes that the first view is the correct view.
                searchDirectoryTextView = value?.peoplePickerSearchDirectoryText
            }
            value?.root?.setOnClickListener(onSearchDirectoryButtonClicked)
        }
    private var searchDirectoryTextView: TextView? = null
        set(value) {
            if (field == value)
                return
            field = value
            updateSearchDirectoryText()
        }

    constructor(context: Context, objects: List<IPersona>, filter: Filter) : super(context, -1, objects) {
        personas.addAll(objects)
        this.filter = filter
    }

    override fun getItem(position: Int): IPersona? = if (isSearchDirectoryButtonPosition(position)) null else personas[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = personas.size + if (showSearchDirectoryButton) 1 else 0

    override fun getFilter(): Filter = filter

    override fun getItemViewType(position: Int): Int {
        return if (position < personas.size)
            ViewType.PERSONA.ordinal
        else
            ViewType.SEARCH_DIRECTORY.ordinal
    }

    override fun getViewTypeCount(): Int = ViewType.values().size

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return when (getItemViewType(position)) {
            ViewType.PERSONA.ordinal -> getPersonaView(position, convertView, parent)
            ViewType.SEARCH_DIRECTORY.ordinal -> getSearchDirectoryView(convertView, parent)
            else -> throw IllegalStateException("ViewType expected")
        }
    }

    private fun isSearchDirectoryButtonPosition(position: Int): Boolean = showSearchDirectoryButton && position == personas.size

    private fun getPersonaView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView as? PersonaView ?: PersonaView(context)
        view.avatarSize = AvatarSize.LARGE
        view.layoutDensity = ListItemView.LayoutDensity.COMPACT
        view.setPersona(personas[position])
        view.setBackground(ContextCompat.getDrawable(context, R.drawable.ms_ripple_transparent_background))
        listView = parent as? ListView
        return view
    }

    private fun getSearchDirectoryView(convertView: View?, parent: ViewGroup?): View {
        // Need to use the convertView, otherwise accessibility focus breaks. Also more efficient.
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.people_picker_search_directory, parent, false)
        searchDirectoryBinding = PeoplePickerSearchDirectoryBinding.bind(view)
        return view
    }

    private fun updateSearchDirectoryText() {
        if (isSearchingDirectory)
            searchDirectoryTextView?.setText(R.string.people_picker_search_progress)
        else
            searchDirectoryTextView?.setText(R.string.people_picker_search_directory)
    }

    private fun createDivider(): InsetDrawable {
        val spacing = PersonaView.getSpacing(context, AvatarSize.LARGE)
        return InsetDrawable(
            ContextCompat.getDrawable(context, R.drawable.ms_row_divider),
            spacing.insetLeft,
            0,
            spacing.cellPadding,
            0
        )
    }
}