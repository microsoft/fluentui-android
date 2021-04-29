package com.microsoft.fluentui.vNext.listitem

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import com.microsoft.fluentui.generator.ListLeadingViewSize
import com.microsoft.fluentui.generator.resourceProxies.ListCellTokensSystem
import com.microsoft.fluentui.listitem.R
import com.microsoft.fluentui.listitem.databinding.ViewListItemVnextBinding
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.FieldUpdateListener
import com.microsoft.fluentui.util.setContentAndUpdateVisibility


open class ListItemView : LinearLayout {
    companion object {
        val DEFAULT_TRUNCATION: TextUtils.TruncateAt = TextUtils.TruncateAt.END
        const val DEFAULT_MAX_LINES: Int = 1
    }

    var listItem: ListItem
        set(value) {
            if (field == value) {
                return
            }
            field = value
            updateTemplate()
        }

    private val tokensSystem: ListCellTokensSystem
    private val listItemBinding: ViewListItemVnextBinding

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_ListItem), attrs, defStyleAttr) {
        listItemBinding = ViewListItemVnextBinding.inflate(LayoutInflater.from(context), this)
        tokensSystem = ListCellTokensSystem(context, resources)
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.ListCellTokensView)
        listItem = ListItem()

        listItem.title = styledAttrs.getString(R.styleable.ListCellTokensView_fluentUI_listTitle) ?: ""
        listItem.subtitle = styledAttrs.getString(R.styleable.ListCellTokensView_fluentUI_listSubtitle) ?: ""
        listItem.footer = styledAttrs.getString(R.styleable.ListCellTokensView_fluentUI_listFooter) ?: ""

        listItem.titleMaxLines = styledAttrs.getInt(R.styleable.ListCellTokensView_fluentUI_listTitleMaxLines, DEFAULT_MAX_LINES)
        listItem.subtitleMaxLines = styledAttrs.getInt(R.styleable.ListCellTokensView_fluentUI_listSubtitleMaxLines, DEFAULT_MAX_LINES)
        listItem.footerMaxLines = styledAttrs.getInt(R.styleable.ListCellTokensView_fluentUI_listFooterMaxLines, DEFAULT_MAX_LINES)

        val customViewSizeOrdinal = styledAttrs.getInt(R.styleable.ListCellTokensView_fluentUI_listLeadingViewSize, ListLeadingViewSize.MEDIUM.ordinal)
        listItem.customViewSize = ListLeadingViewSize.values()[customViewSizeOrdinal]
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
        if(listItem.fieldUpdateListener == null) {
            listItem.fieldUpdateListener = object : FieldUpdateListener {
                override fun onFieldUpdated() {
                    updateTemplate()
                }
            }
            updateTemplate()
        }
    }

    private fun updateTemplate() {
        setValues()
        setPadding(tokensSystem.horizontalCellPadding.toInt(),0, tokensSystem.horizontalCellPadding.toInt(),0)
        updateTexts()
        updateCustomAccessoryView()
        updateCustomViewContainerLayout()
        updateSubtitleCustomAccessoryView()

        setBackgroundResource(listItem.background)
        requestLayout()
    }

    private fun updateSubtitleCustomAccessoryView() {
        listItemBinding.listItemCustomSecondarySubtitleViewContainer.setContentAndUpdateVisibility(listItem.customSecondarySubtitleView)
    }

    private fun updateCustomAccessoryView() {
        val customAccessoryViewContainer = listItemBinding.listItemCustomAccessoryViewContainer ?: return
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        lp.gravity = Gravity.CENTER_VERTICAL
        lp.marginStart = if(listItem.customAccessoryView == null) 0 else tokensSystem.accessoryViewInterspace.toInt()

        customAccessoryViewContainer.layoutParams = lp
        customAccessoryViewContainer.setContentAndUpdateVisibility(listItem.customAccessoryView)
    }

    private fun updateTexts() {
        updateTextAppearance()
        updateTextView(listItemBinding.listItemTitle, listItem.title, listItem.titleMaxLines, listItem.titleTruncateAt)
        updateTextView(listItemBinding.listItemSubtitle, listItem.subtitle, listItem.subtitleMaxLines, listItem.subtitleTruncateAt)
        updateTextView(listItemBinding.listItemFooter, listItem.footer, listItem.footerMaxLines, listItem.footerTruncateAt)
        updateTextViewContainerLayout()
    }

    private fun setValues() {
        tokensSystem.listLeadingViewSize = listItem.customViewSize
        tokensSystem.listCellType = listItem.layoutType
    }

    private fun updateTextAppearance() {
        listItemBinding.listItemTitle.apply {
            TextViewCompat.setTextAppearance(this, tokensSystem.labelFont)
            setTextColor(tokensSystem.labelColor)
        }
        listItemBinding.listItemSubtitle.apply {
            TextViewCompat.setTextAppearance(this, tokensSystem.sublabelFont)
            setTextColor(tokensSystem.sublabelColor)
        }
        setCustomTextStyle()
    }

    private fun setCustomTextStyle() {
        if (listItem.titleStyleRes != -1) {
            listItemBinding.listItemTitle.let { TextViewCompat.setTextAppearance(it, listItem.titleStyleRes) }
        }
        if (listItem.subTitleStyleRes != -1) {
            listItemBinding.listItemSubtitle.let { TextViewCompat.setTextAppearance(it, listItem.subTitleStyleRes) }
        }
    }

    private fun updateTextView(textView: TextView?, text: String, maxLines: Int, truncateAt: TextUtils.TruncateAt) {
        textView?.text = text
        textView?.maxLines = maxLines
        textView?.ellipsize = truncateAt
        textView?.visibility = if (text.isNotEmpty()) View.VISIBLE else View.GONE
    }

    // Container layout

    private fun updateCustomViewContainerLayout() {
        val customViewContainer = listItemBinding.listItemCustomViewContainer ?: return
        val customView = listItem.customView
        val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)


        lp.gravity = Gravity.CENTER_VERTICAL
        lp.marginEnd = if(listItem.customView == null) 0 else tokensSystem.leadingViewInterspace.toInt()

        customViewContainer.layoutParams = lp
        customViewContainer.setContentAndUpdateVisibility(customView)
        val customViewLp = RelativeLayout.LayoutParams(tokensSystem.leadingViewSize.toInt(), tokensSystem.leadingViewSize.toInt())
        customViewLp.addRule(RelativeLayout.CENTER_IN_PARENT)
        customView?.layoutParams = customViewLp
    }

    private fun updateTextViewContainerLayout() {
        val textViewContainer = listItemBinding.listItemTextViewContainer ?: return
        val lp = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

        lp.gravity = Gravity.CENTER_VERTICAL
        lp.topMargin = tokensSystem.labelMargin.toInt()
        lp.bottomMargin = tokensSystem.labelMargin.toInt()

        textViewContainer.layoutParams = lp
    }
}