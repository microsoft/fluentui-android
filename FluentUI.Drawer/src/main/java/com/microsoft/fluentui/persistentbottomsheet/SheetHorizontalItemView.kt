/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.widget.TextViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.util.createImageView
import com.microsoft.fluentui.view.TemplateView

class SheetHorizontalItemView: TemplateView {
    private lateinit var sheetItemTitle:TextView
    private lateinit var mainContainer:ViewGroup
    private lateinit var imageContainer: ViewGroup

    private var title: String = ""
    private var customView: View? = null
    private var mSheetItem: SheetItem? = null
    private var textAppearanceResId: Int = R.style.TextAppearance_FluentUI_ListItemSubtitle

    var onSheetItemClickListener: SheetItem.OnClickListener? = null

    override val templateId: Int
        get() = R.layout.view_sheet_horizontal_item_view

    @JvmOverloads
    constructor(context: Context,  attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    @SuppressLint("ResourceType")
    constructor(context: Context, sheetItem: SheetItem, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        this.mSheetItem = sheetItem
        this.title = sheetItem.title
        if (sheetItem.bitmap != null) {
            this.customView = context.createImageView(sheetItem.bitmap)
        } else {
            this.customView = context.createImageView(sheetItem.drawable, sheetItem.tint)
        }
    }

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()

        sheetItemTitle = findViewInTemplateById(R.id.sheet_item_title)!!
        mainContainer = findViewInTemplateById(R.id.main_container)!!
        imageContainer = findViewInTemplateById(R.id.sheet_item_view_container)!!
        updateTitleView()
        updateCustomView()
        updateTextAppearance()

        if(mSheetItem != null)
            mainContainer.setOnClickListener {
                onSheetItemClickListener?.onSheetItemClick(mSheetItem!!)
            }
        mainContainer.setBackgroundResource(R.drawable.bottom_sheet_item_ripple_background)
    }

    private fun updateTitleView() {
        sheetItemTitle.text = title
        sheetItemTitle.visibility = if (title.isNotEmpty()) View.VISIBLE else View.GONE
    }

    private fun updateCustomView() {
        if (customView != null)
            imageContainer.addView(customView)
    }

    fun update(title:String, customView:View) {
        this.title = title
        this.customView = customView
        updateTitleView()
        updateCustomView()
    }

    fun updateTextAppearanceResId(resId: Int) {
        textAppearanceResId = resId
        updateTextAppearance()
    }

    private fun updateTextAppearance() {
        if(this::sheetItemTitle.isInitialized) {
            sheetItemTitle.let { TextViewCompat.setTextAppearance(it, textAppearanceResId) }
        }
    }
}