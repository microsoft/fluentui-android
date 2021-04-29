package com.microsoft.fluentui.vNext.listitem

import android.text.TextUtils
import android.view.View
import androidx.annotation.DrawableRes
import com.microsoft.fluentui.generator.ListCellType
import com.microsoft.fluentui.generator.ListLeadingViewSize
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.listitem.R
import com.microsoft.fluentui.util.FieldUpdateListener

class ListItem : IBaseListItem {
    var fieldUpdateListener: FieldUpdateListener? = null

    /**
     * First level of hierarchy in the text area.
     */
    override var title: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    /**
     * Second level of hierarchy in the text area.
     */
    var subtitle: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    /**
     * Third level of hierarchy in the text area.
     */
    var footer: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * Sets the maxLines for the title view.
     */
    var titleMaxLines: Int = ListItemView.DEFAULT_MAX_LINES
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    /**
     * Sets the maxLines for the subtitle view.
     */
    var subtitleMaxLines: Int = ListItemView.DEFAULT_MAX_LINES
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    /**
     * Sets the maxLines for the footer view.
     */
    var footerMaxLines: Int = ListItemView.DEFAULT_MAX_LINES
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * Determines where the title view will truncate.
     */
    override var titleTruncateAt: TextUtils.TruncateAt = ListItemView.DEFAULT_TRUNCATION
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    /**
     * Determines where the subtitle view will truncate.
     */
    var subtitleTruncateAt: TextUtils.TruncateAt = ListItemView.DEFAULT_TRUNCATION
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    /**
     * Determines where the footer view will truncate.
     */
    var footerTruncateAt: TextUtils.TruncateAt = ListItemView.DEFAULT_TRUNCATION
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * This view will be displayed at the start of the list item view.
     */
    var customView: View? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
    /**
     * Determines the width and height of the [customView].
     */
    var customViewSize: ListLeadingViewSize = ListLeadingViewSize.SMALL
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * This view will be displayed at the end of the list item view.
     */
    var customAccessoryView: View? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * This view will be displayed at the end of subtitle view.
     */
    var customSecondarySubtitleView: View? = null
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * Determines the list item view vertical padding density.
     */
    var titleStyleRes: Int = 0
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * Determines the list item view vertical padding density.
     */
    var subTitleStyleRes: Int = 0
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * Sets the background color or drawable resource.
     * The default drawable has a ripple animation for selection state.
     */
    @DrawableRes
    override var background: Int = R.drawable.listcelltokensview_background
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    var layoutType: ListCellType = ListCellType.ONE_LINE
        private set
        get() {
            return if (subtitle.isNotEmpty() && footer.isEmpty())
                ListCellType.TWO_LINES
            else if (subtitle.isNotEmpty() && footer.isNotEmpty())
                ListCellType.THREE_LINES
            else
                ListCellType.ONE_LINE
        }
}
