package com.microsoft.fluentui.vNext.listitem

import android.text.TextUtils
import android.view.View
import androidx.annotation.DrawableRes
import com.microsoft.fluentui.generator.ListHeaderStyle
import com.microsoft.fluentui.listitem.R
import com.microsoft.fluentui.util.FieldUpdateListener

class ListSubHeaderItem : IBaseListItem {
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
     * Will be displayed at the end of view
     */
    var actionText: String = ""
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    var headerStyle: ListHeaderStyle = ListHeaderStyle.BASE
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * Determines where the title view will truncate.
     */
    override var titleTruncateAt: TextUtils.TruncateAt = TextUtils.TruncateAt.END
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }

    /**
     * This view will be displayed at the end of the list item view. Will only work when actionText is empty
     */
    var customAccessoryView: View? = null
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
    override var background: Int = R.drawable.listheadertokensview_background
        set(value) {
            if (field == value)
                return
            field = value
            fieldUpdateListener?.onFieldUpdated()
        }
}