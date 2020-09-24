/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.graphics.Rect
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.annotation.RestrictTo
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.ColorUtils
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.microsoft.fluentui.R
import com.microsoft.fluentui.drawer.OnDrawerContentCreatedListener
import com.microsoft.fluentui.persistentbottomsheet.sheetItem.BottomSheetParam
import com.microsoft.fluentui.persistentbottomsheet.sheetItem.PersistentBottomSheetContentViewProvider
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.view.TemplateView
import kotlinx.android.synthetic.main.view_persistent_sheet.view.*


/**
 * [PersistentBottomSheet] is used to display a bottomSheet with a persistent collapsed state of some peekHeight
 */
class PersistentBottomSheet @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : TemplateView(FluentUIContextThemeWrapper(context), attrs, defStyleAttr), SheetItem.OnClickListener {

    companion object {
        private const val FADE_OUT_THRESHOLD = 160
    }


    lateinit var persistentSheetBehavior: BottomSheetBehavior<View>
    private lateinit var persistentSheet: ViewGroup
    private lateinit var persistentSheetContainer: LinearLayout
    private var onDrawerContentCreatedListener: OnDrawerContentCreatedListener? = null
    private var contentViewProvider: PersistentBottomSheetContentViewProvider? = null
    private val mItemLayoutParam: BottomSheetParam.ItemLayoutParam
    private var sheetItemClickListener: SheetItem.OnClickListener? = null

    private var colorBackground = ContextCompat.getColor(context, android.R.color.transparent)
    private var shouldInterceptTouch = false
    private var isDrawerHandleVisible = true


    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PersistentBottomSheet)
        isDrawerHandleVisible = attributes.getBoolean(R.styleable.PersistentBottomSheet_isDrawerHandleVisible, true)
        val defaultPeekHeight = attributes.getDimensionPixelSize(R.styleable.PersistentBottomSheet_peekHeight, 0)
        val itemsInRow = attributes.getInteger(R.styleable.PersistentBottomSheet_ItemsInRow, R.integer.fluentui_persistent_bottomsheet_max_item_row)
        val horizontalItemTextStyle = attributes.getResourceId(R.styleable.PersistentBottomSheet_horizontalItemTextAppearance,
                R.style.TextAppearance_FluentUI_PersistentBottomSheetHorizontalItem)
        val verticalItemTextStyle = attributes.getResourceId(R.styleable.PersistentBottomSheet_verticalItemTextAppearance,
                R.style.TextAppearance_FluentUI_PersistentBottomSheet_Item)
        val verticalSubTextStyle = attributes.getResourceId(R.styleable.PersistentBottomSheet_verticalItemSubTextAppearance, 0)
        val headerTextStyle = attributes.getResourceId(R.styleable.PersistentBottomSheet_headerTextAppearance,
                R.style.TextAppearance_FluentUI_PersistentBottomSheetHeading)

        mItemLayoutParam = BottomSheetParam.ItemLayoutParam(defaultPeekHeight, itemsInRow,
                horizontalItemTextStyle, verticalItemTextStyle, verticalSubTextStyle, headerTextStyle)
        attributes.recycle()
    }

    private val persistentSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                persistentSheetBehavior.peekHeight = mItemLayoutParam.defaultPeekHeight
            }
            if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                scroll_container.smoothScrollTo(0, 0)
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            persistent_bottom_sheet_outlined.setBackgroundColor(ColorUtils.setAlphaComponent(colorBackground, (slideOffset * FADE_OUT_THRESHOLD).toInt()))
        }

    }

    override val templateId: Int
        get() = R.layout.view_persistent_sheet

    override fun onTemplateLoaded() {
        persistentSheet = findViewInTemplateById(R.id.persistent_bottom_sheet)!!
        persistentSheetContainer = findViewInTemplateById(R.id.persistent_sheet_container)!!
        persistentSheetBehavior = BottomSheetBehavior.from(persistentSheet)
        persistentSheetBehavior.setBottomSheetCallback(persistentSheetCallback)
        persistentSheetBehavior.peekHeight = mItemLayoutParam.defaultPeekHeight

        if (!isDrawerHandleVisible)
            sheet_drawer_handle.visibility = View.GONE

        updateSheetContent()
        super.onTemplateLoaded()
    }

    private fun createSheetContent(contentParam: BottomSheetParam.ContentParam) {
        contentParam.listener = this
        this.contentViewProvider = PersistentBottomSheetContentViewProvider(context, contentParam)
        updateSheetContent()
    }

    private fun updateSheetContent() {
        persistentSheetContainer.removeAllViews()
        contentViewProvider?.apply {
            val contentView = this.getSheetContentView(persistentSheetContainer, mItemLayoutParam)
            onDrawerContentCreatedListener?.onDrawerContentCreated(contentView)
        }
    }


    fun changePeekHeight(dy: Int) {
        val transition = ChangeBounds()
        transition.duration = context.resources.getInteger(R.integer.fluentui_persistent_bottomsheet_fade_in_milliseconds).toLong()
        TransitionManager.beginDelayedTransition(persistentSheet, transition)
        val newY = persistentSheetBehavior.peekHeight + dy
        persistentSheetBehavior.peekHeight = newY
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val viewRect = Rect()
        persistentSheet.getGlobalVisibleRect(viewRect)

        if (persistentSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            if (!viewRect.contains(ev!!.rawX.toInt(), ev.rawY.toInt())) {
                persistentSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                shouldInterceptTouch = true
                return true
            }
        }
        shouldInterceptTouch = false
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (shouldInterceptTouch)
            return true
        return super.onTouchEvent(event)
    }

    internal fun getSheetBehavior(): BottomSheetBehavior<View> {
        return persistentSheetBehavior
    }

    override fun addView(child: View, index: Int) {
        addView(child, index, persistentSheetContainer)
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    fun addView(child: View, index: Int = 0, parentViewGroup: ViewGroup) {
        parentViewGroup.addView(child, index)
        child.post {
            changePeekHeight(child.height)
        }
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    override fun removeViewAt(index: Int) {
        removeViewAt(index, persistentSheetContainer)
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    private fun removeViewAt(index: Int, parentViewGroup: ViewGroup) {
        val childHeight = parentViewGroup.getChildAt(index).height
        parentViewGroup.removeViewAt(index)
        if (persistentSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
            changePeekHeight(-childHeight)
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    override fun removeView(child: View) {
        removeView(child, persistentSheetContainer)
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    fun removeView(child: View, parentViewGroup: ViewGroup) {
        val childHeight = child.height
        parentViewGroup.removeView(child)
        if (persistentSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
            changePeekHeight(-childHeight)
    }

    fun collapsePersistentSheet() {
        changePeekHeight(-persistentSheetBehavior.peekHeight)
    }

    fun showPersistentSheet() {
        changePeekHeight(-persistentSheetBehavior.peekHeight + mItemLayoutParam.defaultPeekHeight)
    }

    fun expand() {
        getSheetBehavior().state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun getPeekHeight(): Int {
        return getSheetBehavior().peekHeight
    }

    fun setItemClickListener(itemClickListener: SheetItem.OnClickListener) {
        sheetItemClickListener = itemClickListener
    }

    override fun onSheetItemClick(item: SheetItem) {
        sheetItemClickListener?.onSheetItemClick(item)
    }

    class DefaultContentBuilder(val context: Context) {

        private val contentParam: BottomSheetParam.ContentParam = BottomSheetParam.ContentParam(ArrayList(),
                context.resources.getDimensionPixelSize(R.dimen.fluentui_divider_height))

        /**
         * This will be used to add a horizontal view which
         * has a fixed (defined in *itemInRow* - property) grid size of items and it populates them in each line
         * equally spaced
         *
         *  ex.  in a list and itemsInRow count is 5
         *   if(** is a view) then will be shown like
         *
         *    5 items -->    | **   **   **   **   **   ** |
         *
         *    4 items -->    |   **     **     **    **    |
         *
         *    8 items -->  | **   **   **   **   **   ** |
         *                 |     **      **       **     |
         *
         *
         */
        fun addHorizontalItemList(itemSheet: List<SheetItem>, header: String? = null): DefaultContentBuilder {
            assertIfCustomIdSet()
            contentParam.add(BottomSheetParam.HorizontalItemList(itemSheet, header))
            return this
        }

        /**
         * This will be used to add a horizontal view which
         * has a fixed(defined in *itemInRow* - property) grid size of items and it populates them from start to end.
         *   ex.  8 items in a grid when itemsinRow count is 5
         *   if(** is a view) then will be shown like
         *
         *        **  **  **  **  **  **
         *        **  **  **
         *
         */
        fun addHorizontalGridItemList(itemSheet: List<SheetItem>, header: String? = null): DefaultContentBuilder {
            assertIfCustomIdSet()
            contentParam.add(BottomSheetParam.HorizontalGridItemList(itemSheet, header))
            return this
        }

        /**
         * add items vertically in a groups
         */
        fun addVerticalItemList(itemSheet: List<SheetItem>, header: String? = null): DefaultContentBuilder {
            assertIfCustomIdSet()
            contentParam.add(BottomSheetParam.VerticalItemList(itemSheet, header))
            return this
        }

        /**
         * adds a divider
         */
        fun addDivider(pixelHeight: Int = context.resources.getDimensionPixelSize(R.dimen.fluentui_divider_height), @ColorRes color: Int = 0): DefaultContentBuilder {
            assertIfCustomIdSet()
            contentParam.add(BottomSheetParam.DividerItemType(pixelHeight, color))
            return this
        }


        fun setCustomSheetContent(@LayoutRes layoutResId: Int): DefaultContentBuilder {
            contentParam.listOfItemList.clear()
            contentParam.layoutResId = layoutResId
            return this
        }

        fun buildWith(persistentBottomSheet: PersistentBottomSheet) {
            persistentBottomSheet.createSheetContent(contentParam)
        }


        private fun assertIfCustomIdSet() {
            if (contentParam.layoutResId != null) {
                throw IllegalStateException(" custom resource Id is set you can not use default items with it${contentParam.layoutResId}")
            }
        }
    }

}