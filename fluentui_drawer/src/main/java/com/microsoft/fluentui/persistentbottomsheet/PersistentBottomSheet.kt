/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.persistentbottomsheet

import android.content.Context
import android.graphics.Rect
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.annotation.RestrictTo
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.microsoft.fluentui.drawer.OnDrawerContentCreatedListener
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.drawer.databinding.FluentUiViewPersistentSheetBinding
import com.microsoft.fluentui.persistentbottomsheet.sheetItem.BottomSheetParam
import com.microsoft.fluentui.persistentbottomsheet.sheetItem.PersistentBottomSheetContentViewProvider
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.view.TemplateView


/**
 * [PersistentBottomSheet] is used to display a bottomSheet with a persistent collapsed state of some peekHeight
 */
class PersistentBottomSheet @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        TemplateView(FluentUIContextThemeWrapper(context,R.style.FluentUI_Theme_Drawer), attrs, defStyleAttr), SheetItem.OnClickListener {

    companion object {
        private const val FADE_OUT_THRESHOLD = 160
    }


    private lateinit var persistentSheetBehavior: BottomSheetBehavior<View>
    private lateinit var persistentSheetBinding: FluentUiViewPersistentSheetBinding
    private var onDrawerContentCreatedListener: OnDrawerContentCreatedListener? = null
    private var contentViewProvider: PersistentBottomSheetContentViewProvider? = null
    private var itemLayoutParam: BottomSheetParam.ItemLayoutParam
    private var sheetItemClickListener: SheetItem.OnClickListener? = null
    private var collapsedStateDrawerHandleContentDescription : String? = null
    private var expandedStateDrawerHandleContentDescription : String? = null

    private var colorBackground = ContextCompat.getColor(context, android.R.color.transparent)
    private var shouldInterceptTouch = false
    private var isDrawerHandleVisible = true
    private var sheetContainer: PersistentBottomSheetContentViewProvider.SheetContainerInfo? = null


    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FluentUiPersistentBottomSheet)
        isDrawerHandleVisible = attributes.getBoolean(R.styleable.FluentUiPersistentBottomSheet_fluentUiIsDrawerHandleVisible, true)
        val defaultPeekHeight = attributes.getDimensionPixelSize(R.styleable.FluentUiPersistentBottomSheet_fluentUiPeekHeight, 0)
        val itemsInRow = attributes.getInteger(R.styleable.FluentUiPersistentBottomSheet_fluentUiItemsInRow, R.integer.fluentui_persistent_bottomsheet_max_item_row)
        val horizontalItemTextStyle = attributes.getResourceId(R.styleable.FluentUiPersistentBottomSheet_fluentUiHorizontalItemTextAppearanc,
                R.style.TextAppearance_FluentUI_PersistentBottomSheetHorizontalItem)
        val verticalItemTextStyle = attributes.getResourceId(R.styleable.FluentUiPersistentBottomSheet_fluentUiVerticalItemTextAppearance,
                R.style.TextAppearance_FluentUI_PersistentBottomSheet_Item)
        val verticalSubTextStyle = attributes.getResourceId(R.styleable.FluentUiPersistentBottomSheet_fluentUiVerticalItemSubTextAppearance, 0)
        val headerTextStyle = attributes.getResourceId(R.styleable.FluentUiPersistentBottomSheet_fluentUiHeaderTextAppearance,
                R.style.TextAppearance_FluentUI_PersistentBottomSheetHeading)

        itemLayoutParam = BottomSheetParam.ItemLayoutParam(defaultPeekHeight, itemsInRow,
                horizontalItemTextStyle, verticalItemTextStyle, verticalSubTextStyle, headerTextStyle)
        attributes.recycle()
    }

    private val persistentSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                persistentSheetBehavior.peekHeight = itemLayoutParam.defaultPeekHeight
            }
            if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                persistentSheetBinding.fluentUiScrollContainer.smoothScrollTo(0, 0)
            }
            setDrawerHandleContentDescription(collapsedStateDrawerHandleContentDescription,expandedStateDrawerHandleContentDescription)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            val colorOffset = (slideOffset * FADE_OUT_THRESHOLD).coerceIn(0f,255f)
            persistentSheetBinding.persistentBottomSheetOutlined.setBackgroundColor(ColorUtils.setAlphaComponent(colorBackground, colorOffset.toInt()))
        }

    }

    override val templateId: Int
        get() = R.layout.fluent_ui_view_persistent_sheet

    override fun onTemplateLoaded() {
        persistentSheetBinding = FluentUiViewPersistentSheetBinding.bind(templateRoot!!)
        persistentSheetBehavior = BottomSheetBehavior.from(persistentSheetBinding.fluentUiPersistentBottomSheet)
        persistentSheetBehavior.isHideable = true
        persistentSheetBehavior.setBottomSheetCallback(persistentSheetCallback)
        persistentSheetBehavior.peekHeight = itemLayoutParam.defaultPeekHeight

        if (!isDrawerHandleVisible)
            persistentSheetBinding.fluentUiSheetDrawerHandle.visibility = View.GONE

        updateSheetContent()
        super.onTemplateLoaded()
    }

    private fun createSheetContent(contentParam: BottomSheetParam.ContentParam) {
        contentParam.listener = this
        this.contentViewProvider = PersistentBottomSheetContentViewProvider(context, contentParam)
        updateSheetContent()
    }

    private fun updateSheetContent() {
        persistentSheetBinding.fluentUiPersistentSheetContainer.removeAllViews()
        contentViewProvider?.apply {
            val sheetContainerInfo = this.getSheetContentView(persistentSheetBinding.fluentUiPersistentSheetContainer, itemLayoutParam)
            sheetContainer = sheetContainerInfo
            onDrawerContentCreatedListener?.onDrawerContentCreated(sheetContainerInfo.Container)
            configureBottomSheetDrawerHandle(sheetContainerInfo)
        }
    }

    private fun configureBottomSheetDrawerHandle(sheetContainerInfo: PersistentBottomSheetContentViewProvider.SheetContainerInfo) {
        if (sheetContainerInfo.isSingleLineItem) {
            setDrawerHandleVisibility(View.GONE)
            persistentSheetBinding.fluentUiPersistentSheetContainer.setPadding(persistentSheetBinding.fluentUiPersistentSheetContainer.paddingLeft,
                    resources.getDimensionPixelSize(R.dimen.fluentui_persistent_bottomsheet_content_padding_vertical),
                    persistentSheetBinding.fluentUiPersistentSheetContainer.paddingRight,
                    persistentSheetBinding.fluentUiPersistentSheetContainer.paddingBottom)
            return
        }
        setDrawerHandleVisibility(View.VISIBLE)
        setDrawerHandleContentDescription(collapsedStateDrawerHandleContentDescription, expandedStateDrawerHandleContentDescription)
        persistentSheetBinding.fluentUiPersistentSheetContainer.setPadding(persistentSheetBinding.fluentUiPersistentSheetContainer.paddingLeft, 0,
                persistentSheetBinding.fluentUiPersistentSheetContainer.paddingRight,
                persistentSheetBinding.fluentUiPersistentSheetContainer.paddingBottom)
        persistentSheetBinding.fluentUiSheetDrawerHandle.setOnClickListener {
            when {
                persistentSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED -> expand()
                persistentSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED -> collapse()
                else -> {
                    // it's in transition state do nothing
                }
            }
        }
    }

    fun setDrawerHandleVisibility(visibility: Int) {
        isDrawerHandleVisible = View.VISIBLE == visibility
        persistentSheetBinding.fluentUiSheetDrawerHandle.visibility = visibility
    }


    fun changePeekHeight(dy: Int) {
        val transition = ChangeBounds()
        transition.duration = context.resources.getInteger(R.integer.fluentui_persistent_bottomsheet_fade_in_milliseconds).toLong()
        TransitionManager.beginDelayedTransition(persistentSheetBinding.fluentUiPersistentBottomSheet, transition)
        val newY = persistentSheetBehavior.peekHeight + dy
        persistentSheetBehavior.peekHeight = newY
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val viewRect = Rect()
        persistentSheetBinding.fluentUiPersistentBottomSheet.getGlobalVisibleRect(viewRect)

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

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_ESCAPE) {
            event.dispatch(this, null, null)
            return true
        }
        return super.dispatchKeyEvent(event)
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
        addView(child, index, persistentSheetBinding.fluentUiPersistentSheetContainer)
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
        removeViewAt(index, persistentSheetBinding.fluentUiPersistentSheetContainer)
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
        removeView(child, persistentSheetBinding.fluentUiPersistentSheetContainer)
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    fun removeView(child: View, parentViewGroup: ViewGroup) {
        val childHeight = child.height
        parentViewGroup.removeView(child)
        if (persistentSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED)
            changePeekHeight(-childHeight)
    }

    @Deprecated("use hide() method instead")
    fun hidePersistentSheet() {
        changePeekHeight(-persistentSheetBehavior.peekHeight)
    }

    @Deprecated("use show() method instead")
    fun showPersistentSheet() {
        changePeekHeight(-persistentSheetBehavior.peekHeight + itemLayoutParam.defaultPeekHeight)
    }

    fun collapse() {
        persistentSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        persistentSheetBinding.fluentUiSheetDrawerHandle.requestFocus()
    }

    fun expand() {
        persistentSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
       persistentSheetBinding.fluentUiSheetDrawerHandle.requestFocus()
    }

    fun hide(){
        persistentSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun show(expanded: Boolean = false) {
        if (expanded) {
            persistentSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            persistentSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun updateBottomSheetLayoutParams(peekHeight: Int = itemLayoutParam.defaultPeekHeight,
                                      itemInRow: Int = itemLayoutParam.itemInRow,
                                      @StyleRes horizontalTextAppearance: Int = itemLayoutParam.horizontalTextAppearance,
                                      @StyleRes verticalItemTextAppearance: Int = itemLayoutParam.verticalItemTextAppearance,
                                      @StyleRes verticalSubTextAppearance: Int = itemLayoutParam.verticalSubTextAppearance,
                                      @StyleRes headerTextAppearance: Int = itemLayoutParam.headerTextAppearance)  {
        itemLayoutParam = BottomSheetParam.ItemLayoutParam(peekHeight, itemInRow, horizontalTextAppearance, verticalItemTextAppearance,verticalSubTextAppearance, headerTextAppearance)
        persistentSheetBehavior.peekHeight = itemLayoutParam.defaultPeekHeight
        updateSheetContent()
    }


    /**
     * Way to set content-description can be handled in better way
     *
     * public api call to set content description if it changes based on states.
     */
    fun setDrawerHandleContentDescription(collapsedStateDescription: String?, expandedStateDescription: String?) {
        collapsedStateDrawerHandleContentDescription = collapsedStateDescription
        expandedStateDrawerHandleContentDescription = expandedStateDescription

        val currentStateContentDescription: String?
        persistentSheetBinding.fluentUiSheetDrawerHandle.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES
        when (persistentSheetBehavior.state) {
            BottomSheetBehavior.STATE_COLLAPSED -> {
                currentStateContentDescription =  collapsedStateDescription
            }
            BottomSheetBehavior.STATE_EXPANDED -> {
                currentStateContentDescription =  expandedStateDescription
            }
            else -> {
                persistentSheetBinding.fluentUiSheetDrawerHandle.importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO
                return
            }
        }
        currentStateContentDescription?.apply{
            persistentSheetBinding.fluentUiSheetDrawerHandle.contentDescription = this
            persistentSheetBinding.fluentUiSheetDrawerHandle.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }
    }

    fun getPeekHeight(): Int {
        return getSheetBehavior().peekHeight
    }

    fun getBottomSheetBehaviour(): BottomSheetBehavior<View> {
        return getSheetBehavior()
    }

    fun setItemClickListener(itemClickListener: SheetItem.OnClickListener) {
        sheetItemClickListener = itemClickListener
    }

    override fun onSheetItemClick(item: SheetItem) {
        sheetItemClickListener?.onSheetItemClick(item)
    }

    /**
     * calling this method is mandatory after changing the input
     * item list , otherwise related functionalities might not work
     */
    fun refreshSheetContent() {
        contentViewProvider?.updateSheetContentView(sheetContainer)
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
        @JvmOverloads
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
        @JvmOverloads
        fun addHorizontalGridItemList(itemSheet: List<SheetItem>, header: String? = null): DefaultContentBuilder {
            assertIfCustomIdSet()
            contentParam.add(BottomSheetParam.HorizontalGridItemList(itemSheet, header))
            return this
        }

        /**
         * add items vertically in a groups
         */
        @JvmOverloads
        fun addVerticalItemList(itemSheet: List<SheetItem>, header: String? = null): DefaultContentBuilder {
            assertIfCustomIdSet()
            contentParam.add(BottomSheetParam.VerticalItemList(itemSheet, header))
            return this
        }

        /**
         * adds a divider
         */
        @JvmOverloads
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

        fun setCustomSheetContent(child: View): DefaultContentBuilder {
            contentParam.listOfItemList.clear()
            contentParam.childContent = child
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

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ESCAPE
                && persistentSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            collapse()
            return true
        }
        return super.onKeyUp(keyCode, event)
    }

}