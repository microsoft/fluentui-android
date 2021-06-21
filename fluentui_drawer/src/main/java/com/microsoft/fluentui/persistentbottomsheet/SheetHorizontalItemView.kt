/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.createImageView
import com.microsoft.fluentui.view.TemplateView


class SheetHorizontalItemView: TemplateView {
    private lateinit var sheetItemTitle:TextView
    private lateinit var mainContainer:ViewGroup
    private lateinit var imageContainer: ViewGroup
    private var listener: ChildItemInteractionListener? = null

    private var title: String = ""
    private var customView: View? = null
    private var mSheetItem: SheetItem? = null
    private var textAppearanceResId: Int = R.style.TextAppearance_FluentUI_HorizontalListItemTitle

    var onSheetItemClickListener: SheetItem.OnClickListener? = null

    override val templateId: Int
        get() = R.layout.view_sheet_horizontal_item_view

    @JvmOverloads
    constructor(context: Context,  attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Drawer), attrs, defStyleAttr)

    @SuppressLint("ResourceType")
    constructor(context: Context, sheetItem: SheetItem, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(context,R.style.Theme_FluentUI_Drawer), attrs, defStyleAttr) {
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
        listener?.onChildTemplateLoaded(mainContainer)
    }

    private fun updateTitleView() {
        sheetItemTitle.text = title
        if (title.isNotEmpty()){
            sheetItemTitle.visibility =  View.VISIBLE
            mainContainer.contentDescription = sheetItemTitle.text
        } else{
            sheetItemTitle.visibility = View.GONE
            mainContainer.contentDescription = customView?.contentDescription
        }
        sheetItemTitle.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
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
            sheetItemTitle.let {
                TextViewCompat.setTextAppearance(it, textAppearanceResId) }
        }
    }

    /**
     * parent can set a listener to know if child is loaded/inflated or not
     */
    fun addTemplateLoadListener(listener:ChildItemInteractionListener){
        this.listener = listener
    }

    /**
     * interface for parent to interact with this child
     */
    interface ChildItemInteractionListener{
        fun onChildTemplateLoaded(container:View)
    }
}