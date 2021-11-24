/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.microsoft.fluentui.view.TemplateView
import com.microsoft.fluentuidemo.R

enum class CellOrientation {
    HORIZONTAL,
    VERTICAL
}

// Example of a TemplateView subclass

class Cell : TemplateView {
    companion object {
        private val DEFAULT_ORIENTATION = CellOrientation.HORIZONTAL
    }

    var title: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }
    var description: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            updateTemplate()
        }

    var orientation: CellOrientation = DEFAULT_ORIENTATION
        set(value) {
            if (field == value)
                return
            field = value
            invalidateTemplate()
        }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        if (attrs != null) {
            val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.FluentUiCell)
            title = styledAttrs.getString(R.styleable.FluentUiCell_fluentUiTitle)
            description = styledAttrs.getString(R.styleable.FluentUiCell_fluentUiDescription)
            val orientationOrdinal = styledAttrs.getInt(R.styleable.FluentUiCell_fluentUiOrientation, DEFAULT_ORIENTATION.ordinal)
            orientation = CellOrientation.values()[orientationOrdinal]
            styledAttrs.recycle()
        }
    }

    // Template

    override val templateId: Int
        get() = when (orientation) {
            CellOrientation.HORIZONTAL -> R.layout.template_cell_horizontal
            CellOrientation.VERTICAL -> R.layout.template_cell_vertical
        }

    private var titleView: TextView? = null
    private var descriptionView: TextView? = null

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()
        titleView = findViewInTemplateById(R.id.cell_title)
        descriptionView = findViewInTemplateById(R.id.cell_description)
        updateTemplate()
    }

    private fun updateTemplate() {
        titleView?.text = title
        descriptionView?.text = description
    }
}