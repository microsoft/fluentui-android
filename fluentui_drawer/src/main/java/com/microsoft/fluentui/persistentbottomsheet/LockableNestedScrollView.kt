package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView


class LockableNestedScrollView : NestedScrollView {
    // by default is scrollable
    private var scrollable = true

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)


    fun setScrollingEnabled(enabled: Boolean) {
        scrollable = enabled
    }

    override fun requestChildFocus(child: View?, focused: View?) {
        if (scrollable)
            super.requestChildFocus(child, focused)
        else {
            // avoid scrolling to focused view
            super.requestChildFocus(child, child)
        }
    }
}