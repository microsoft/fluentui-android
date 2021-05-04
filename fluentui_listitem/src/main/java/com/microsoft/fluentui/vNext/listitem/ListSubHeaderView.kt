package com.microsoft.fluentui.vNext.listitem

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.microsoft.fluentui.generator.ListHeaderStyle
import com.microsoft.fluentui.generator.resourceProxies.ListHeaderTokensSystem
import com.microsoft.fluentui.listitem.R
import com.microsoft.fluentui.listitem.databinding.ViewListSubHeaderVnextBinding
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.FieldUpdateListener

class ListSubHeaderView : LinearLayout {

    var listSubHeaderItem: ListSubHeaderItem
        set(value) {
            if (field == value) {
                return
            }
            field = value
            updateTemplate()
        }

    private val tokensSystem:ListHeaderTokensSystem
    private val listSubHeaderBinding: ViewListSubHeaderVnextBinding

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_ListItem), attrs, defStyleAttr) {
        listSubHeaderBinding = ViewListSubHeaderVnextBinding.inflate(LayoutInflater.from(context),this)
        tokensSystem = ListHeaderTokensSystem(context, resources)
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ListHeaderTokensView)
        listSubHeaderItem = ListSubHeaderItem()

        listSubHeaderItem.title = styledAttrs.getString(R.styleable.ListHeaderTokensView_fluentUI_listHeaderTitle) ?: ""

        val titleStyleOrdinal = styledAttrs.getInt(R.styleable.ListHeaderTokensView_fluentUI_listHeaderStyle, ListHeaderStyle.BASE.ordinal)
        listSubHeaderItem.headerStyle = ListHeaderStyle.values()[titleStyleOrdinal]

        styledAttrs.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setListenerForListItemUpdate()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setListenerForListItemUpdate()
    }

    private fun setListenerForListItemUpdate() {
        if(listSubHeaderItem.fieldUpdateListener == null) {
            listSubHeaderItem.fieldUpdateListener = object : FieldUpdateListener {
                override fun onFieldUpdated() {
                    updateTemplate()
                }
            }
            updateTemplate()
        }
    }

    private fun updateTemplate() {
        setValues()
        setPadding(tokensSystem.horizontalPadding.toInt(),tokensSystem.verticalPadding.toInt(), tokensSystem.horizontalPadding.toInt(),tokensSystem.verticalPadding.toInt())
        updateTitleView()
        updateActionViewOrCustomAccessoryView()
        setBackgroundResource(listSubHeaderItem.background)
    }

    private fun updateActionViewOrCustomAccessoryView() {
        listSubHeaderBinding.listSubHeaderCustomAccessoryViewContainer.removeAllViews()
        listSubHeaderBinding.listSubHeaderCustomAccessoryViewContainer.visibility = View.GONE
        var customView:View? = null
        if(listSubHeaderItem.actionText.isNotEmpty()) {
            val actionTextView = TextView(context)
            actionTextView.text = listSubHeaderItem.actionText
            TextViewCompat.setTextAppearance(actionTextView,tokensSystem.actionFont)
            actionTextView.setTextColor(tokensSystem.actionColor)
            customView = actionTextView
        }
        else if(listSubHeaderItem.customAccessoryView != null) {
            // Will only work if there is no action text, so to use customview action should be empty
            customView = listSubHeaderItem.customAccessoryView

        }
        if(customView == null){
            return
        }
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        lp.gravity = Gravity.CENTER_VERTICAL
        lp.marginStart = tokensSystem.actionViewInterspace.toInt()
        if (customView.parent != null) {
            (customView.parent as ViewGroup).removeView(customView)
        }
        listSubHeaderBinding.listSubHeaderCustomAccessoryViewContainer.layoutParams = lp
        listSubHeaderBinding.listSubHeaderCustomAccessoryViewContainer.addView(customView)
        listSubHeaderBinding.listSubHeaderCustomAccessoryViewContainer.visibility = View.VISIBLE
    }

    private fun setValues() {
        tokensSystem.listHeaderStyle = listSubHeaderItem.headerStyle
    }

    private fun updateTitleView() {
        val titleView = listSubHeaderBinding.listSubHeaderTitle ?: return

        titleView.text = listSubHeaderItem.title
        titleView.ellipsize = listSubHeaderItem.titleTruncateAt
        TextViewCompat.setTextAppearance(titleView,tokensSystem.titleFont)
        titleView.setTextColor(tokensSystem.titleColor)
    }
}