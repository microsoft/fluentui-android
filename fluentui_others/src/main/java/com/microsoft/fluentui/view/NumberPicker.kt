/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.view

/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.annotation.IntDef
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import androidx.core.widget.TextViewCompat
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.util.TypedValue
import android.view.*
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.animation.DecelerateInterpolator
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.R
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.isAccessibilityEnabled
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.text.DecimalFormatSymbols
import java.util.*

/**
 * A widget that enables the user to select a number from a predefined range.
 * There are two flavors of this widget and which one is presented to the user
 * depends on the current theme.
 *
 */
internal class NumberPicker : LinearLayout {
    companion object {
        const val BUTTON_INCREMENT = 1
        const val BUTTON_DECREMENT = 2

        private const val VIRTUAL_VIEW_ID_INCREMENT = 1
        private const val VIRTUAL_VIEW_ID_TOGGLE = 2
        private const val VIRTUAL_VIEW_ID_DECREMENT = 3
        private const val TOGGLE_VALUE = 2
        /**
         * The number of items show in the selector wheel.
         */
        private const val DEFAULT_SELECTOR_WHEEL_ITEM_COUNT = 3
        /**
         * The default update interval during long press.
         */
        private const val DEFAULT_LONG_PRESS_UPDATE_INTERVAL: Long = 300
        /**
         * The coefficient by which to adjust (divide) the max fling velocity.
         */
        private const val SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8
        /**
         * The the duration for adjusting the selector wheel.
         */
        private const val SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800
        /**
         * The duration of scrolling while snapping to a given position.
         */
        private const val SNAP_SCROLL_DURATION = 300
        /**
         * The strength of fading in the top and bottom while drawing the selector.
         */
        private const val TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f
        /**
         * The default unscaled height of the selection divider.
         */
        private const val UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2
        /**
         * The default unscaled distance between the selection dividers.
         */
        private const val UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48
        /**
         * Constant for unspecified size.
         */
        private const val SIZE_UNSPECIFIED = -1

        private const val ALIGN_LEFT = 0
        private const val ALIGN_CENTER = 1
        private const val ALIGN_RIGHT = 2

        private const val QUICK_ANIMATE_THRESHOLD = 15
        private val sTwoDigitFormatter = TwoDigitFormatter()
        /**
         * @hide
         */
        val twoDigitFormatter: android.widget.NumberPicker.Formatter
            get() = sTwoDigitFormatter

        private fun formatNumberWithLocale(value: Int): String {
            return String.format(Locale.getDefault(), "%d", value)
        }
    }
    /**
     * Returns the value of the picker.
     *
     * @return The value.
     *
     * Set the current value for the number picker.
     *
     * If the argument is less than the [NumberPicker.getMinValue] and
     * [NumberPicker.getWrapSelectorWheel] is `false` the
     * current value is set to the [NumberPicker.getMinValue] value.
     *
     * If the argument is less than the [NumberPicker.getMinValue] and
     * [NumberPicker.getWrapSelectorWheel] is `true` the
     * current value is set to the [NumberPicker.getMaxValue] value.
     *
     * If the argument is less than the [NumberPicker.getMaxValue] and
     * [NumberPicker.getWrapSelectorWheel] is `false` the
     * current value is set to the [NumberPicker.getMaxValue] value.
     *
     * If the argument is less than the [NumberPicker.getMaxValue] and
     * [NumberPicker.getWrapSelectorWheel] is `true` the
     * current value is set to the [NumberPicker.getMinValue] value.
     *
     * @param value The current value.
     * @see .setWrapSelectorWheel
     * @see .setMinValue
     * @see .setMaxValue
     */
    var value: Int
        get() = mValue
        set(value) = setValueInternal(value, false)
    /**
     * The values to be displayed instead the indices.
     *
     * Gets the values to be displayed instead of string values.
     *
     * @return The displayed values.
     *
     * Sets the values to be displayed.
     *
     * @param displayedValues The displayed values.
     *
     * **Note:** The length of the displayed values array
     * must be equal to the range of selectable numbers which is equal to
     * [.getMaxValue] - [.getMinValue] + 1.
     */
    var displayedValues: Array<String>? = null
        set(value) {
            if (value == null ||  displayedValues?.contentEquals(value) == true)
                return

            field = value
            updateTextView()
            initializeSelectorWheelIndices()
            tryComputeMaxWidth()
        }
    /**
     * Lower value of the range of numbers allowed for the NumberPicker
     *
     * Returns the min value of the picker.
     *
     * @return The min value
     *
     * Sets the min value of the picker.
     *
     * @param minValue The min value inclusive.
     *
     * **Note:** The length of the displayed values array
     * set via [.setDisplayedValues] must be equal to the
     * range of selectable numbers which is equal to
     * [.getMaxValue] - [.getMinValue] + 1.
     */
    var minValue: Int = 0
        set(value) {
            if (minValue == value)
                return

            if (value < 0)
                throw IllegalArgumentException("minValue must be >= 0")

            field = value

            if (minValue > mValue)
                mValue = minValue

            wrapSelectorWheel = maxValue - minValue > mSelectorIndices.size
            initializeSelectorWheelIndices()
            updateTextView()
            tryComputeMaxWidth()
            invalidate()
        }
    /**
     * Upper value of the range of numbers allowed for the NumberPicker
     */
    /**
     * Returns the max value of the picker.
     *
     * @return The max value.
     */
    /**
     * Sets the max value of the picker.
     *
     * @param maxValue The max value inclusive.
     *
     * **Note:** The length of the displayed values array
     * set via [.setDisplayedValues] must be equal to the
     * range of selectable numbers which is equal to
     * [.getMaxValue] - [.getMinValue] + 1.
     */
    var maxValue: Int = 0
        set(value) {
            if (maxValue == value)
                return

            if (value < 0)
                throw IllegalArgumentException("maxValue must be >= 0")

            field = value
            if (maxValue < mValue)
                mValue = maxValue

            wrapSelectorWheel = maxValue - minValue > mSelectorIndices.size
            initializeSelectorWheelIndices()
            updateTextView()
            tryComputeMaxWidth()
            invalidate()
        }
    /**
     * Flag whether the selector should wrap around.
     *
     * Gets whether the selector wheel wraps when reaching the min/max value.
     *
     * @return True if the selector wheel wraps.
     *
     * @see .getMinValue
     * @see .getMaxValue
     *
     * Sets whether the selector wheel shown during flinging/scrolling should
     * wrap around the [NumberPicker.getMinValue] and
     * [NumberPicker.getMaxValue] values.
     *
     * By default if the range (max - min) is more than the number of items shown
     * on the selector wheel the selector wheel wrapping is enabled.
     *
     * **Note:** If the number of items, i.e. the range (
     * [.getMaxValue] - [.getMinValue]) is less than
     * the number of items shown on the selector wheel, the selector wheel will
     * not wrap. Hence, in such a case calling this method is a NOP.
     *
     * @param wrapSelectorWheel Whether to wrap.
     */
    var wrapSelectorWheel: Boolean = false
        set(value) {
            if (value == wrapSelectorWheel)
                return

            val wrappingAllowed = maxValue - minValue >= mSelectorIndices.size
            if (!value || wrappingAllowed)
                field = value
        }
    /**
     * The content description for the increment button.
     */
    var virtualIncrementButtonDescription: String = ""
    /**
     * The content description for the decrement button.
     */
    var virtualDecrementButtonDescription: String = ""
    /**
     * The content description for the toggle text view.
     */
    var virtualToggleDescription: String = ""
    /**
     * The click action announcement for the increment button.
     */
    var virtualIncrementClickActionAnnouncement: String = ""
    /**
     * The click action announcement for the decrement button.
     */
    var virtualDecrementClickActionAnnouncement: String = ""
    /**
     * The click action announcement for the toggle button.
     */
    var virtualToggleClickActionAnnouncement: String = ""
    /**
     * The click action announcement for the increment button.
     */
    var virtualIncrementHint: String = ""
    /**
     * The click action announcement for the decrement button.
     */
    var virtualDecrementHint: String = ""
    /**
     * The click action announcement for the toggle button.
     */
    var virtualToggleHint: String = ""

    private val accessibilityTouchHelper: NumberPickerTouchHelper = NumberPickerTouchHelper(this)
    /**
     * If there are only two values the NumberPicker becomes a toggle when in TalkBack.
     */
    private val isToggle: Boolean
        get() = maxValue < TOGGLE_VALUE

    private var mSelectorWheelItemCount: Int = 0
    /**
     * The index of the middle selector item.
     */
    private var mSelectorMiddleItemIndex: Int = 0
    /**
     * The increment button.
     */
    private var mIncrementButton: ImageButton? = null
    /**
     * The decrement button.
     */
    private var mDecrementButton: ImageButton? = null
    /**
     * The text for showing the current value.
     */
    private lateinit var numberPickerTextView: TextView
    /**
     * The distance between the two selection dividers.
     */
    private var mSelectionDividersDistance: Int = 0
    /**
     * The min height of this widget.
     */
    private var mMinHeight: Int = 0
    /**
     * The max height of this widget.
     */
    private var mMaxHeight: Int = 0
    /**
     * The max width of this widget.
     */
    private var mMinWidth: Int = 0
    /**
     * The max width of this widget.
     */
    private var mMaxWidth: Int = 0
    /**
     * Flag whether to compute the max width.
     */
    private var mComputeMaxWidth: Boolean = false
    /**
     * The height of the text.
     */
    private var mTextSize: Int = 0
    /**
     * The height of the gap between text elements if the selector wheel.
     */
    private var mSelectorTextGapHeight: Int = 0
    /**
     * Current value of this NumberPicker
     */
    private var mValue: Int = 0
    /**
     * Listener to be notified upon current value change.
     */
    private var mOnValueChangeListener: OnValueChangeListener? = null
    /**
     * Listener to be notified upon scroll state change.
     */
    private var mOnScrollListener: OnScrollListener? = null
    /**
     * Formatter for for displaying the current value.
     */
    private var mFormatter: android.widget.NumberPicker.Formatter? = null
    /**
     * The speed for updating the value form long press.
     */
    private var mLongPressUpdateInterval = DEFAULT_LONG_PRESS_UPDATE_INTERVAL
    /**
     * Cache for the string representation of selector indices.
     */
    private val mSelectorIndexToStringCache = SparseArray<String>()
    /**
     * The selector indices whose values are shown by the selector.
     */
    private lateinit var mSelectorIndices: IntArray
    /**
     * The [Paint] for drawing the selector.
     */
    private lateinit var mSelectorWheelPaint: Paint
    /**
     * The [Drawable] for pressed virtual (increment/decrement) buttons.
     */
    private var mVirtualButtonPressedDrawable: Drawable? = null
    /**
     * The height of a selector element (text + gap).
     */
    private var mSelectorElementHeight: Int = 0
    /**
     * The initial offset of the scroll selector.
     */
    private var mInitialScrollOffset = Integer.MIN_VALUE
    /**
     * The current offset of the scroll selector.
     */
    private var mCurrentScrollOffset: Int = 0
    /**
     * The [Scroller] responsible for flinging the selector.
     */
    private lateinit var mFlingScroller: Scroller
    /**
     * The [Scroller] responsible for adjusting the selector.
     */
    private lateinit var mAdjustScroller: Scroller
    /**
     * The previous Y coordinate while scrolling the selector.
     */
    private var mPreviousScrollerY: Int = 0
    /**
     * Handle to the reusable command for changing the current value from long
     * press by one.
     */
    private var mChangeCurrentByOneFromLongPressCommand: ChangeCurrentByOneFromLongPressCommand? = null
    /**
     * The Y position of the last down event.
     */
    private var mLastDownEventY: Float = 0.toFloat()
    /**
     * The time of the last down event.
     */
    private var mLastDownEventTime: Long = 0
    /**
     * The Y position of the last down or move event.
     */
    private var mLastDownOrMoveEventY: Float = 0.toFloat()
    /**
     * Determines speed during touch scrolling.
     */
    private var mVelocityTracker: VelocityTracker? = null
    /**
     * @see ViewConfiguration.getScaledTouchSlop
     */
    private var mTouchSlop: Int = 0
    /**
     * @see ViewConfiguration.getScaledMinimumFlingVelocity
     */
    private var mMinimumFlingVelocity: Int = 0
    /**
     * @see ViewConfiguration.getScaledMaximumFlingVelocity
     */
    private var mMaximumFlingVelocity: Int = 0
    /**
     * The back ground color used to optimize scroller fading.
     */
    private var mSolidColor: Int = 0
    /**
     * Flag whether this widget has a selector wheel.
     */
    private var mHasSelectorWheel: Boolean = false
    /**
     * Divider for showing item to be selected while scrolling
     */
    private var mSelectionDivider: Drawable? = null
    /**
     * The height of the selection divider.
     */
    private var mSelectionDividerHeight: Int = 0
    /**
     * The current scroll state of the number picker.
     */
    private var mScrollState = OnScrollListener.SCROLL_STATE_IDLE
    /**
     * Flag whether to ignore move events - we ignore such when we show in IME
     * to prevent the content from scrolling.
     */
    private var mIgnoreMoveEvents: Boolean = false
    /**
     * Flag whether to perform a click on tap.
     */
    private var mPerformClickOnTap: Boolean = false
    /**
     * The top of the top selection divider.
     */
    private var mTopSelectionDividerTop: Int = 0
    /**
     * The bottom of the bottom selection divider.
     */
    private var mBottomSelectionDividerBottom: Int = 0
    /**
     * Whether the increment virtual button is pressed.
     */
    private var mIncrementVirtualButtonPressed: Boolean = false
    /**
     * Whether the decrement virtual button is pressed.
     */
    private var mDecrementVirtualButtonPressed: Boolean = false
    /**
     * Helper class for managing pressed state of the virtual buttons.
     */
    private lateinit var mPressedStateHelper: PressedStateHelper
    /**
     * The keycode of the last handled DPAD down event.
     */
    private var mLastHandledDownDpadKeyCode = -1
    /**
     * If true then the selector wheel is hidden until the picker has focus.
     */
    private var mHideWheelUntilFocused: Boolean = false

    private var mSelectedTextColor: Int = 0
    private var mTextColor: Int = 0

    private var mTextTypeface: Typeface? = null
    private var mSelectedTextTypeface: Typeface? = null

    /**
     * Use a custom NumberPicker formatting callback to use two-digit minutes
     * strings like "01". Keeping a static formatter etc. is the most efficient
     * way to do this; it avoids creating temporary objects on every call to
     * format().
     */
    class TwoDigitFormatter : android.widget.NumberPicker.Formatter {
        private val mBuilder = StringBuilder()
        private var mZeroDigit: Char = ' '
        private var mFmt: java.util.Formatter? = null
        private val mArgs = arrayOfNulls<Any>(1)

        init {
            val locale = Locale.getDefault()
            init(locale)
        }

        private fun init(locale: Locale) {
            mFmt = createFormatter(locale)
            mZeroDigit = getZeroDigit(locale)
        }

        override fun format(value: Int): String {
            val currentLocale = Locale.getDefault()
            if (mZeroDigit != getZeroDigit(currentLocale)) {
                init(currentLocale)
            }
            mArgs[0] = value
            mBuilder.delete(0, mBuilder.length)
            mFmt?.format("%02d", *mArgs)
            return mFmt.toString()
        }

        private fun getZeroDigit(locale: Locale): Char {
            return DecimalFormatSymbols(locale).zeroDigit
        }

        private fun createFormatter(locale: Locale): java.util.Formatter {
            return java.util.Formatter(mBuilder, locale)
        }
    }

    /**
     * Interface to listen for changes of the current value.
     */
    interface OnValueChangeListener {
        /**
         * Called upon a change of the current value.
         *
         * @param picker The NumberPicker associated with this listener.
         * @param oldVal The previous value.
         * @param newVal The new value.
         */
        fun onValueChange(picker: NumberPicker, oldVal: Int, newVal: Int)
    }

    /**
     * Interface to listen for the picker scroll state.
     */
    interface OnScrollListener {
        /** @hide
         */
        @IntDef(SCROLL_STATE_IDLE, SCROLL_STATE_TOUCH_SCROLL, SCROLL_STATE_FLING)
        @Retention(RetentionPolicy.SOURCE)
        annotation class ScrollState

        /**
         * Callback invoked while the number picker scroll state has changed.
         *
         * @param view The view whose scroll state is being reported.
         * @param scrollState The current scroll state. One of
         * [.SCROLL_STATE_IDLE],
         * [.SCROLL_STATE_TOUCH_SCROLL] or
         * [.SCROLL_STATE_IDLE].
         */
        fun onScrollStateChange(view: NumberPicker, @ScrollState scrollState: Int)

        companion object {
            /**
             * The view is not scrolling.
             */
            const val SCROLL_STATE_IDLE = 0
            /**
             * The user is scrolling using touch, and his finger is still on the screen.
             */
            const val SCROLL_STATE_TOUCH_SCROLL = 1
            /**
             * The user had previously been scrolling using touch and performed a fling.
             */
            const val SCROLL_STATE_FLING = 2
        }
    }

    /**
     * Create a new number picker.
     *
     * @param context The application environment.
     */
    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    /**
     * Create a new number picker.
     *
     * @param context The application environment.
     * @param attrs A collection of attributes.
     */
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    /**
     * Create a new number picker
     *
     * @param context the application environment.
     * @param attrs a collection of attributes.
     * @param defStyleAttr An attribute in the current theme that contains a
     * reference to a style resource that supplies default values for
     * the view. Can be 0 to not look for defaults.
     */
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attributesArray = context.obtainStyledAttributes(
            attrs, R.styleable.NumberPicker, defStyleAttr, defStyleRes)

        mHasSelectorWheel = true

        mSelectorWheelItemCount = attributesArray.getInt(
            R.styleable.NumberPicker_selectorWheelItemCount, DEFAULT_SELECTOR_WHEEL_ITEM_COUNT)
        mSelectorMiddleItemIndex = mSelectorWheelItemCount / 2
        mSelectorIndices = IntArray(mSelectorWheelItemCount)
        mHideWheelUntilFocused = attributesArray.getBoolean(
            R.styleable.NumberPicker_hideWheelUntilFocused, false)
        mSolidColor = attributesArray.getColor(R.styleable.NumberPicker_solidColor, 0)
        mSelectionDivider = attributesArray.getDrawable(R.styleable.NumberPicker_selectionDivider)
        val defSelectionDividerHeight = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT.toFloat(),
            resources.displayMetrics).toInt()
        mSelectionDividerHeight = attributesArray.getDimensionPixelSize(
            R.styleable.NumberPicker_selectionDividerHeight, defSelectionDividerHeight)
        val defSelectionDividerDistance = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE.toFloat(),
            resources.displayMetrics).toInt()
        mSelectionDividersDistance = attributesArray.getDimensionPixelSize(
            R.styleable.NumberPicker_selectionDividersDistance, defSelectionDividerDistance)
        mSelectedTextColor = ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiNumberPickerSelectedTextColor)
        mTextColor = ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiNumberPickerDefaultTextColor)
        mMinHeight = attributesArray.getDimensionPixelSize(
            R.styleable.NumberPicker_internalMinHeight, SIZE_UNSPECIFIED)
        mMaxHeight = attributesArray.getDimensionPixelSize(
            R.styleable.NumberPicker_internalMaxHeight, SIZE_UNSPECIFIED)
        if (mMinHeight != SIZE_UNSPECIFIED && mMaxHeight != SIZE_UNSPECIFIED
            && mMinHeight > mMaxHeight) {
            throw IllegalArgumentException("minHeight > maxHeight")
        }
        mMinWidth = attributesArray.getDimensionPixelSize(
            R.styleable.NumberPicker_internalMinWidth, SIZE_UNSPECIFIED)
        mMaxWidth = attributesArray.getDimensionPixelSize(
            R.styleable.NumberPicker_internalMaxWidth, SIZE_UNSPECIFIED)

        if (mMinWidth != SIZE_UNSPECIFIED && mMaxWidth != SIZE_UNSPECIFIED
            && mMinWidth > mMaxWidth) {
            throw IllegalArgumentException("minWidth > maxWidth")
        }
        mComputeMaxWidth = mMaxWidth == SIZE_UNSPECIFIED
        mVirtualButtonPressedDrawable = attributesArray.getDrawable(
            R.styleable.NumberPicker_virtualButtonPressedDrawable)

        val textAlign = attributesArray.getInt(
            R.styleable.NumberPicker_textAlign, ALIGN_CENTER)

        attributesArray.recycle()
        mPressedStateHelper = PressedStateHelper()
        // By default LinearLayout that we extend is not drawn. This is
        // its draw() method is not called but dispatchDraw() is called
        // directly (see ViewGroup.drawChild()). However, this class uses
        // the fading edge effect implemented by View and we need our
        // draw() method to be called. Therefore, we declare we will draw.
        setWillNotDraw(!mHasSelectorWheel)
        /*val inflater = getContext().getSystemService(
            Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(layoutResId, this, true)*/
        val onClickListener = OnClickListener { v ->
            numberPickerTextView.clearFocus()
            if (v.id == R.id.fluentui_number_picker_increment) {
                changeValueByOne(true)
            } else {
                changeValueByOne(false)
            }
        }
        val onLongClickListener = OnLongClickListener { v ->
            numberPickerTextView.clearFocus()
            if (v.id == R.id.fluentui_number_picker_increment) {
                postChangeCurrentByOneFromLongPress(true, 0)
            } else {
                postChangeCurrentByOneFromLongPress(false, 0)
            }
            true
        }
        // increment button
        if (!mHasSelectorWheel) {
            mIncrementButton = findViewById<View>(R.id.fluentui_number_picker_increment) as ImageButton
            if (mIncrementButton == null) {
                mHasSelectorWheel = false
            } else {
                mIncrementButton?.setOnClickListener(onClickListener)
                mIncrementButton?.setOnLongClickListener(onLongClickListener)
            }
        } else {
            mIncrementButton = null
        }
        // decrement button
        if (!mHasSelectorWheel) {
            mDecrementButton = findViewById<View>(R.id.fluentui_number_picker_decrement) as ImageButton
            if (mDecrementButton == null) {
                mHasSelectorWheel = false
            } else {
                mDecrementButton?.setOnClickListener(onClickListener)
                mDecrementButton?.setOnLongClickListener(onLongClickListener)
            }
        } else {
            mDecrementButton = null
        }

        // initialize constants
        val configuration = ViewConfiguration.get(context)
        mTouchSlop = configuration.scaledTouchSlop
        mMinimumFlingVelocity = configuration.scaledMinimumFlingVelocity
        mMaximumFlingVelocity = configuration.scaledMaximumFlingVelocity / SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT

        // NumberPickerTextView
        numberPickerTextView = NumberPickerTextView(context)
        numberPickerTextView.visibility = View.INVISIBLE

        // NumberPickerTextView must be added manually to prevent NPE on Lollipop.
        addView(numberPickerTextView)

        TextViewCompat.setTextAppearance(numberPickerTextView, R.style.TextAppearance_FluentUI_NumberPicker)
        mTextSize = numberPickerTextView.textSize.toInt()
        mTextTypeface = numberPickerTextView.typeface

        TextViewCompat.setTextAppearance(numberPickerTextView, R.style.TextAppearance_FluentUI_NumberPicker_Selected)
        mSelectedTextTypeface = numberPickerTextView.typeface

        // create the selector wheel paint
        val paint = Paint()
        paint.isAntiAlias = true
        when (textAlign) {
            ALIGN_LEFT -> paint.textAlign = Align.LEFT
            ALIGN_CENTER -> paint.textAlign = Align.CENTER
            ALIGN_RIGHT -> paint.textAlign = Align.RIGHT
        }
        paint.textSize = mTextSize.toFloat()
        paint.typeface = mTextTypeface

        paint.color = mTextColor
        mSelectorWheelPaint = paint
        // create the fling and adjust scrollers
        mFlingScroller = Scroller(getContext(), null, true)
        mAdjustScroller = Scroller(getContext(), DecelerateInterpolator(2.5f))
        updateTextView()
        // If not explicitly specified this view is important for accessibility.
        if (ViewCompat.getImportantForAccessibility(this) == ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
            ViewCompat.setImportantForAccessibility(this, ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES)
        }

        isFocusableInTouchMode = true
        background = ContextCompat.getDrawable(context, R.drawable.ms_ripple_transparent_background)
        ViewCompat.setAccessibilityDelegate(this, accessibilityTouchHelper)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (!mHasSelectorWheel) {
            super.onLayout(changed, left, top, right, bottom)
            return
        }

        // text view centered horizontally.
        val textViewMeasuredWidth = numberPickerTextView.measuredWidth
        val textViewMeasuredHeight = numberPickerTextView.measuredHeight
        val textViewLeft = (measuredWidth - textViewMeasuredWidth) / 2
        val textViewTop = (measuredHeight - textViewMeasuredHeight) / 2
        val textViewRight = textViewLeft + textViewMeasuredWidth
        val textViewBottom = textViewTop + textViewMeasuredHeight
        numberPickerTextView.layout(textViewLeft, textViewTop, textViewRight, textViewBottom)
        if (changed) {
            // need to do all this when we know our size
            initializeSelectorWheel()
            mTopSelectionDividerTop = (height - mSelectionDividersDistance) / 2 - mSelectionDividerHeight
            mBottomSelectionDividerBottom = (mTopSelectionDividerTop + 2 * mSelectionDividerHeight
                + mSelectionDividersDistance)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (!mHasSelectorWheel) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }
        // Try greedily to fit the max width and height.
        val newWidthMeasureSpec = makeMeasureSpec(widthMeasureSpec, mMaxWidth)
        val newHeightMeasureSpec = makeMeasureSpec(heightMeasureSpec, mMaxHeight)
        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec)
        // Flag if we are measured with width or height less than the respective min.
        val widthSize = resolveSizeAndStateRespectingMinSize(mMinWidth, measuredWidth,
            widthMeasureSpec)
        val heightSize = resolveSizeAndStateRespectingMinSize(mMinHeight, measuredHeight,
            heightMeasureSpec)
        setMeasuredDimension(widthSize, heightSize)
    }

    /**
     * Move to the final position of a scroller. Ensures to force finish the scroller
     * and if it is not at its final position a scroll of the selector wheel is
     * performed to fast forward to the final position.
     *
     * @param scroller The scroller to whose final position to get.
     * @return True of the a move was performed, i.e. the scroller was not in final position.
     */
    private fun moveToFinalScrollerPosition(scroller: Scroller): Boolean {
        scroller.forceFinished(true)
        var amountToScroll = scroller.finalY - scroller.currY
        val futureScrollOffset = (mCurrentScrollOffset + amountToScroll) % mSelectorElementHeight
        var overshootAdjustment = mInitialScrollOffset - futureScrollOffset
        if (overshootAdjustment != 0) {
            if (Math.abs(overshootAdjustment) > mSelectorElementHeight / 2) {
                if (overshootAdjustment > 0) {
                    overshootAdjustment -= mSelectorElementHeight
                } else {
                    overshootAdjustment += mSelectorElementHeight
                }
            }
            amountToScroll += overshootAdjustment
            scrollBy(0, amountToScroll)
            return true
        }
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (!mHasSelectorWheel || !isEnabled) {
            return false
        }
        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                removeAllCallbacks()
                mLastDownEventY = event.y
                mLastDownOrMoveEventY = mLastDownEventY
                mLastDownEventTime = event.eventTime
                mIgnoreMoveEvents = false
                mPerformClickOnTap = false
                // Handle pressed state before any state change.
                if (mLastDownEventY < mTopSelectionDividerTop) {
                    if (mScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                        mPressedStateHelper.buttonPressDelayed(BUTTON_DECREMENT)
                    }
                } else if (mLastDownEventY > mBottomSelectionDividerBottom) {
                    if (mScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                        mPressedStateHelper.buttonPressDelayed(BUTTON_INCREMENT)
                    }
                }
                // Make sure we support flinging inside scrollables.
                parent.requestDisallowInterceptTouchEvent(true)
                if (!mFlingScroller.isFinished) {
                    mFlingScroller.forceFinished(true)
                    mAdjustScroller.forceFinished(true)
                    onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE)
                } else if (!mAdjustScroller.isFinished) {
                    mFlingScroller.forceFinished(true)
                    mAdjustScroller.forceFinished(true)
                } else if (mLastDownEventY < mTopSelectionDividerTop) {
                    postChangeCurrentByOneFromLongPress(
                        false, ViewConfiguration.getLongPressTimeout().toLong())
                } else if (mLastDownEventY > mBottomSelectionDividerBottom) {
                    postChangeCurrentByOneFromLongPress(
                        true, ViewConfiguration.getLongPressTimeout().toLong())
                } else {
                    mPerformClickOnTap = true
                }
                return true
            }
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled || !mHasSelectorWheel)
            return false

        if (mVelocityTracker == null)
            mVelocityTracker = VelocityTracker.obtain()

        mVelocityTracker?.addMovement(event)
        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_MOVE -> run {
                if (mIgnoreMoveEvents)
                    return@run

                val currentMoveY = event.y
                if (mScrollState != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    val deltaDownY = Math.abs(currentMoveY - mLastDownEventY).toInt()
                    if (deltaDownY > mTouchSlop) {
                        removeAllCallbacks()
                        onScrollStateChange(OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    }
                } else {
                    val deltaMoveY = (currentMoveY - mLastDownOrMoveEventY).toInt()
                    scrollBy(0, deltaMoveY)
                    invalidate()
                }
                mLastDownOrMoveEventY = currentMoveY
            }
            MotionEvent.ACTION_UP -> {
                removeChangeCurrentByOneFromLongPress()
                mPressedStateHelper.cancel()
                val velocityTracker = mVelocityTracker ?: return true
                velocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity.toFloat())
                val initialVelocity = velocityTracker.yVelocity.toInt()
                if (Math.abs(initialVelocity) > mMinimumFlingVelocity) {
                    fling(initialVelocity)
                    onScrollStateChange(OnScrollListener.SCROLL_STATE_FLING)
                } else {
                    val eventY = event.y.toInt()
                    val deltaMoveY = Math.abs(eventY - mLastDownEventY).toInt()
                    val deltaTime = event.eventTime - mLastDownEventTime
                    if (deltaMoveY <= mTouchSlop && deltaTime < ViewConfiguration.getTapTimeout()) {
                        if (mPerformClickOnTap) {
                            mPerformClickOnTap = false
                            performClick()
                        } else {
                            val selectorIndexOffset = eventY / mSelectorElementHeight - mSelectorMiddleItemIndex
                            if (selectorIndexOffset > 0) {
                                changeValueByOne(true)
                                mPressedStateHelper.buttonTapped(BUTTON_INCREMENT)
                            } else if (selectorIndexOffset < 0) {
                                changeValueByOne(false)
                                mPressedStateHelper.buttonTapped(BUTTON_DECREMENT)
                            }
                        }
                    } else {
                        ensureScrollWheelAdjusted()
                    }
                    onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE)
                }
                mVelocityTracker?.recycle()
                mVelocityTracker = null
            }
        }
        return true
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> removeAllCallbacks()
        }
        return super.dispatchTouchEvent(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val keyCode = event.keyCode
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> removeAllCallbacks()
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_UP -> run {
                if (!mHasSelectorWheel) {
                    return@run
                }
                when (event.action) {
                    KeyEvent.ACTION_DOWN -> if (wrapSelectorWheel || if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
                            value < maxValue
                        else
                            value > minValue) {
                        requestFocus()
                        mLastHandledDownDpadKeyCode = keyCode
                        removeAllCallbacks()
                        if (mFlingScroller.isFinished) {
                            changeValueByOne(keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
                        }
                        return true
                    }
                    KeyEvent.ACTION_UP -> if (mLastHandledDownDpadKeyCode == keyCode) {
                        mLastHandledDownDpadKeyCode = -1
                        return true
                    }
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    override fun dispatchTrackballEvent(event: MotionEvent): Boolean {
        val action = event.actionMasked
        when (action) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> removeAllCallbacks()
        }
        return super.dispatchTrackballEvent(event)
    }

    override fun dispatchHoverEvent(event: MotionEvent): Boolean =
        if (!mHasSelectorWheel)
            super.dispatchHoverEvent(event)
        else
            accessibilityTouchHelper.dispatchHoverEvent(event)

    override fun computeScroll() {
        var scroller = mFlingScroller
        if (scroller.isFinished) {
            scroller = mAdjustScroller
            if (scroller.isFinished)
                return
        }
        scroller.computeScrollOffset()
        val currentScrollerY = scroller.currY
        if (mPreviousScrollerY == 0) {
            mPreviousScrollerY = scroller.startY
        }
        scrollBy(0, currentScrollerY - mPreviousScrollerY)
        mPreviousScrollerY = currentScrollerY
        if (scroller.isFinished) {
            onScrollerFinished(scroller)
        } else {
            invalidate()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (!mHasSelectorWheel)
            mIncrementButton?.isEnabled = enabled

        if (!mHasSelectorWheel)
            mDecrementButton?.isEnabled = enabled

        numberPickerTextView.isEnabled = enabled
    }

    override fun scrollBy(x: Int, y: Int) {
        val selectorIndices = mSelectorIndices
        if (!wrapSelectorWheel && y > 0
            && selectorIndices[mSelectorMiddleItemIndex] <= minValue) {
            mCurrentScrollOffset = mInitialScrollOffset
            return
        }
        if (!wrapSelectorWheel && y < 0
            && selectorIndices[mSelectorMiddleItemIndex] >= maxValue) {
            mCurrentScrollOffset = mInitialScrollOffset
            return
        }
        mCurrentScrollOffset += y
        while (mCurrentScrollOffset - mInitialScrollOffset > mSelectorTextGapHeight) {
            mCurrentScrollOffset -= mSelectorElementHeight
            decrementSelectorIndices(selectorIndices)
            setValueInternal(selectorIndices[mSelectorMiddleItemIndex], true)
            if (!wrapSelectorWheel && selectorIndices[mSelectorMiddleItemIndex] <= minValue) {
                mCurrentScrollOffset = mInitialScrollOffset
            }
        }
        while (mCurrentScrollOffset - mInitialScrollOffset < -mSelectorTextGapHeight) {
            mCurrentScrollOffset += mSelectorElementHeight
            incrementSelectorIndices(selectorIndices)
            setValueInternal(selectorIndices[mSelectorMiddleItemIndex], true)
            if (!wrapSelectorWheel && selectorIndices[mSelectorMiddleItemIndex] >= maxValue) {
                mCurrentScrollOffset = mInitialScrollOffset
            }
        }
    }

    override fun computeVerticalScrollOffset(): Int {
        return mCurrentScrollOffset
    }

    override fun computeVerticalScrollRange(): Int {
        return (maxValue - minValue + 1) * mSelectorElementHeight
    }

    override fun computeVerticalScrollExtent(): Int {
        return height
    }

    override fun getSolidColor(): Int {
        return mSolidColor
    }

    /**
     * Sets the listener to be notified on change of the current value.
     *
     * @param onValueChangedListener The listener.
     */
    fun setOnValueChangedListener(onValueChangedListener: OnValueChangeListener) {
        mOnValueChangeListener = onValueChangedListener
    }

    /**
     * Set listener to be notified for scroll state changes.
     *
     * @param onScrollListener The listener.
     */
    fun setOnScrollListener(onScrollListener: OnScrollListener) {
        mOnScrollListener = onScrollListener
    }

    /**
     * Set the formatter to be used for formatting the current value.
     *
     * Note: If you have provided alternative values for the values this
     * formatter is never invoked.
     *
     * @param formatter The formatter object. If formatter is `null`,
     * [String.valueOf] will be used.
     * @see .setDisplayedValues
     */
    fun setFormatter(formatter: android.widget.NumberPicker.Formatter) {
        if (formatter === mFormatter) {
            return
        }
        mFormatter = formatter
        initializeSelectorWheelIndices()
        updateTextView()
    }

    override fun performClick(): Boolean {
        if (!mHasSelectorWheel) {
            return super.performClick()
        } else if (!super.performClick()) {
            if (isToggle && context.isAccessibilityEnabled)
                toggleValue()
        }
        return true
    }

    override fun performLongClick(): Boolean {
        if (!mHasSelectorWheel) {
            return super.performLongClick()
        } else if (!super.performLongClick()) {
            mIgnoreMoveEvents = true
            if (isToggle && context.isAccessibilityEnabled)
                toggleValue()
        }
        return true
    }

    /**
     * Computes the max width if no such specified as an attribute.
     */
    private fun tryComputeMaxWidth() {
        if (!mComputeMaxWidth) {
            return
        }
        var maxTextWidth = 0
        val displayedValues = displayedValues
        if (displayedValues == null) {
            var maxDigitWidth = 0f
            for (i in 0..9) {
                val digitWidth = mSelectorWheelPaint.measureText(formatNumberWithLocale(i))
                if (digitWidth > maxDigitWidth) {
                    maxDigitWidth = digitWidth
                }
            }
            var numberOfDigits = 0
            var current = maxValue
            while (current > 0) {
                numberOfDigits++
                current = current / 10
            }
            maxTextWidth = (numberOfDigits * maxDigitWidth).toInt()
        } else {
            val valueCount = displayedValues.size
            for (i in 0 until valueCount) {
                val textWidth = mSelectorWheelPaint.measureText(displayedValues[i])
                if (textWidth > maxTextWidth) {
                    maxTextWidth = textWidth.toInt()
                }
            }
        }
        maxTextWidth += numberPickerTextView.paddingLeft + numberPickerTextView.paddingRight
        if (mMaxWidth != maxTextWidth) {
            if (maxTextWidth > mMinWidth) {
                mMaxWidth = maxTextWidth
            } else {
                mMaxWidth = mMinWidth
            }
            invalidate()
        }
    }

    /**
     * Sets the speed at which the numbers be incremented and decremented when
     * the up and down buttons are long pressed respectively.
     *
     * The default value is 300 ms.
     *
     * @param intervalMillis The speed (in milliseconds) at which the numbers
     * will be incremented and decremented.
     */
    fun setOnLongPressUpdateInterval(intervalMillis: Long) {
        mLongPressUpdateInterval = intervalMillis
    }

    override fun getTopFadingEdgeStrength(): Float {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH
    }

    override fun getBottomFadingEdgeStrength(): Float {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAllCallbacks()
    }

    override fun onDraw(canvas: Canvas) {
        if (!mHasSelectorWheel) {
            super.onDraw(canvas)
            return
        }
        val showSelectorWheel = if (mHideWheelUntilFocused) hasFocus() else true
        val x: Float
        when (mSelectorWheelPaint.textAlign) {
            Paint.Align.LEFT -> x = ViewCompat.getPaddingStart(this).toFloat()
            Paint.Align.RIGHT -> x = (measuredWidth - ViewCompat.getPaddingEnd(this)).toFloat()
            Paint.Align.CENTER -> x = (measuredWidth / 2).toFloat()
            else -> x = (measuredWidth / 2).toFloat()
        }
        var y = mCurrentScrollOffset.toFloat()
        // draw the virtual buttons pressed state if needed
        if (showSelectorWheel && mVirtualButtonPressedDrawable != null
            && mScrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            if (mDecrementVirtualButtonPressed) {
                mVirtualButtonPressedDrawable?.state = View.PRESSED_STATE_SET
                mVirtualButtonPressedDrawable?.setBounds(0, 0, right, mTopSelectionDividerTop)
                mVirtualButtonPressedDrawable?.draw(canvas)
            }
            if (mIncrementVirtualButtonPressed) {
                mVirtualButtonPressedDrawable?.state = View.PRESSED_STATE_SET
                mVirtualButtonPressedDrawable?.setBounds(0, mBottomSelectionDividerBottom, right,
                    bottom)
                mVirtualButtonPressedDrawable?.draw(canvas)
            }
        }
        // draw the selector wheel
        val selectorIndices = mSelectorIndices
        for (i in selectorIndices.indices) {
            val selectorIndex = selectorIndices[i]
            val scrollSelectorValue = mSelectorIndexToStringCache.get(selectorIndex)
            // Do not draw the middle item if text view is visible since the text view
            // is shown only if the wheel is static and it covers the middle
            // item. Otherwise, if the user starts editing the text via the
            // IME he may see a dimmed version of the old value intermixed
            // with the new one.
            if (showSelectorWheel && i != mSelectorMiddleItemIndex || i == mSelectorMiddleItemIndex && numberPickerTextView.visibility != View.VISIBLE) {
                if (i == mSelectorMiddleItemIndex) {
                    mSelectorWheelPaint.color = mSelectedTextColor
                    mSelectorWheelPaint.typeface = mSelectedTextTypeface
                } else {
                    mSelectorWheelPaint.color = mTextColor
                    mSelectorWheelPaint.typeface = mTextTypeface
                }
                canvas.drawText(scrollSelectorValue, x, y, mSelectorWheelPaint)
            }
            y += mSelectorElementHeight.toFloat()
        }
        // draw the selection dividers
        if (showSelectorWheel && mSelectionDivider != null) {
            // draw the top divider
            val topOfTopDivider = mTopSelectionDividerTop
            val bottomOfTopDivider = topOfTopDivider + mSelectionDividerHeight
            mSelectionDivider?.setBounds(0, topOfTopDivider, right, bottomOfTopDivider)
            mSelectionDivider?.draw(canvas)
            // draw the bottom divider
            val bottomOfBottomDivider = mBottomSelectionDividerBottom
            val topOfBottomDivider = bottomOfBottomDivider - mSelectionDividerHeight
            mSelectionDivider?.setBounds(0, topOfBottomDivider, right, bottomOfBottomDivider)
            mSelectionDivider?.draw(canvas)
        }
    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(event)
        event.className = NumberPicker::class.java.name
        event.isScrollable = true
        event.scrollY = (minValue + mValue) * mSelectorElementHeight
        event.maxScrollY = (maxValue - minValue) * mSelectorElementHeight
    }

    /**
     * Makes a measure spec that tries greedily to use the max value.
     *
     * @param measureSpec The measure spec.
     * @param maxSize The max value for the size.
     * @return A measure spec greedily imposing the max size.
     */
    private fun makeMeasureSpec(measureSpec: Int, maxSize: Int): Int {
        if (maxSize == SIZE_UNSPECIFIED) {
            return measureSpec
        }
        val size = View.MeasureSpec.getSize(measureSpec)
        val mode = View.MeasureSpec.getMode(measureSpec)
        when (mode) {
            View.MeasureSpec.EXACTLY -> return measureSpec
            View.MeasureSpec.AT_MOST -> return View.MeasureSpec.makeMeasureSpec(Math.min(size, maxSize), View.MeasureSpec.EXACTLY)
            View.MeasureSpec.UNSPECIFIED -> return View.MeasureSpec.makeMeasureSpec(maxSize, View.MeasureSpec.EXACTLY)
            else -> throw IllegalArgumentException("Unknown measure mode: $mode")
        }
    }

    /**
     * Utility to reconcile a desired size and state, with constraints imposed
     * by a MeasureSpec. Tries to respect the min size, unless a different size
     * is imposed by the constraints.
     *
     * @param minSize The minimal desired size.
     * @param measuredSize The currently measured size.
     * @param measureSpec The current measure spec.
     * @return The resolved size and state.
     */
    private fun resolveSizeAndStateRespectingMinSize(
        minSize: Int, measuredSize: Int, measureSpec: Int): Int {
        if (minSize != SIZE_UNSPECIFIED) {
            val desiredWidth = Math.max(minSize, measuredSize)
            return View.resolveSizeAndState(desiredWidth, measureSpec, 0)
        } else {
            return measuredSize
        }
    }

    /**
     * Resets the selector indices and clear the cached string representation of
     * these indices.
     */
    private fun initializeSelectorWheelIndices() {
        mSelectorIndexToStringCache.clear()
        val selectorIndices = mSelectorIndices
        val current = value
        for (i in mSelectorIndices.indices) {
            var selectorIndex = current + (i - mSelectorMiddleItemIndex)
            if (wrapSelectorWheel) {
                selectorIndex = getWrappedSelectorIndex(selectorIndex)
            }
            selectorIndices?.let {
                it[i] = selectorIndex
                ensureCachedScrollSelectorValue(it[i])
            }
        }
    }

    /**
     * Sets the current value of this NumberPicker.
     *
     * @param current The new value of the NumberPicker.
     * @param notifyChange Whether to notify if the current value changed.
     */
    private fun setValueInternal(current: Int, notifyChange: Boolean) {
        var current = current
        if (mValue == current) {
            return
        }
        // Wrap around the values if we go past the start or end
        if (wrapSelectorWheel) {
            current = getWrappedSelectorIndex(current)
        } else {
            current = Math.max(current, minValue)
            current = Math.min(current, maxValue)
        }
        val previous = mValue
        mValue = current
        updateTextView()
        if (notifyChange) {
            notifyChange(previous, current)
        }
        initializeSelectorWheelIndices()
        invalidate()
    }

    fun animateValueTo(value: Int) {
        if (mValue == value) {
            return
        }
        changeValueBy(value - mValue, SELECTOR_ADJUSTMENT_DURATION_MILLIS)
    }

    /**
     * Jump to a value near target value then animate to target value
     *
     * @param value The target value
     */
    fun quicklyAnimateValueTo(value: Int) {
        if (mValue == value) {
            return
        }

        val delta = mValue - value
        if (delta > QUICK_ANIMATE_THRESHOLD) {
            this.value = value + QUICK_ANIMATE_THRESHOLD
        } else if (delta < -QUICK_ANIMATE_THRESHOLD) {
            this.value = value - QUICK_ANIMATE_THRESHOLD
        }

        animateValueTo(value)
    }

    /**
     * Changes the current value by one which is increment or
     * decrement based on the passes argument.
     * decrement the current value.
     *
     * @param increment True to increment, false to decrement.
     */
    private fun changeValueByOne(increment: Boolean) {
        changeValueBy(if (increment) 1 else -1, SNAP_SCROLL_DURATION)
    }

    private fun changeValueBy(value: Int, duration: Int) {
        if (mHasSelectorWheel) {
            numberPickerTextView.visibility = View.INVISIBLE
            if (!moveToFinalScrollerPosition(mFlingScroller)) {
                moveToFinalScrollerPosition(mAdjustScroller)
            }
            mPreviousScrollerY = 0
            mFlingScroller.startScroll(0, 0, 0, value * -mSelectorElementHeight, duration)
            invalidate()
        } else {
            setValueInternal(mValue + value, true)
        }
    }

    private fun toggleValue() {
        setValueInternal(if (mValue == 0) 1 else 0, true)
    }

    private fun initializeSelectorWheel() {
        initializeSelectorWheelIndices()
        val selectorIndices = mSelectorIndices
        val totalTextHeight = selectorIndices.size * mTextSize
        val totalTextGapHeight = (bottom - top - totalTextHeight).toFloat()
        val textGapCount = selectorIndices.size.toFloat()
        mSelectorTextGapHeight = (totalTextGapHeight / textGapCount + 0.5f).toInt()
        mSelectorElementHeight = mTextSize + mSelectorTextGapHeight
        // Ensure that the middle item is positioned the same as the text in numberPickerTextView
        val editTextTextPosition = numberPickerTextView.baseline + numberPickerTextView.top
        mInitialScrollOffset = editTextTextPosition - mSelectorElementHeight * mSelectorMiddleItemIndex
        mCurrentScrollOffset = mInitialScrollOffset
        updateTextView()
    }

    /**
     * Callback invoked upon completion of a given `scroller`.
     */
    private fun onScrollerFinished(scroller: Scroller?) {
        if (scroller == mFlingScroller) {
            if (!ensureScrollWheelAdjusted()) {
                updateTextView()
            }
            onScrollStateChange(OnScrollListener.SCROLL_STATE_IDLE)
        } else {
            if (mScrollState != OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                updateTextView()
            }
        }
    }

    /**
     * Handles transition to a given `scrollState`
     */
    private fun onScrollStateChange(scrollState: Int) {
        if (mScrollState == scrollState) {
            return
        }
        mScrollState = scrollState
        mOnScrollListener?.onScrollStateChange(this, scrollState)
    }

    /**
     * Flings the selector with the given `velocityY`.
     */
    private fun fling(velocityY: Int) {
        mPreviousScrollerY = 0
        if (velocityY > 0) {
            mFlingScroller.fling(0, 0, 0, velocityY, 0, 0, 0, Integer.MAX_VALUE)
        } else {
            mFlingScroller.fling(0, Integer.MAX_VALUE, 0, velocityY, 0, 0, 0, Integer.MAX_VALUE)
        }
        invalidate()
    }

    /**
     * @return The wrapped index `selectorIndex` value.
     */
    private fun getWrappedSelectorIndex(selectorIndex: Int): Int {
        if (selectorIndex > maxValue) {
            return minValue + (selectorIndex - maxValue) % (maxValue - minValue) - 1
        } else if (selectorIndex < minValue) {
            return maxValue - (minValue - selectorIndex) % (maxValue - minValue) + 1
        }
        return selectorIndex
    }

    /**
     * Increments the `selectorIndices` whose string representations
     * will be displayed in the selector.
     */
    private fun incrementSelectorIndices(selectorIndices: IntArray) {
        for (i in 0 until selectorIndices.size - 1) {
            selectorIndices[i] = selectorIndices[i + 1]
        }
        var nextScrollSelectorIndex = selectorIndices[selectorIndices.size - 2] + 1
        if (wrapSelectorWheel && nextScrollSelectorIndex > maxValue) {
            nextScrollSelectorIndex = minValue
        }
        selectorIndices[selectorIndices.size - 1] = nextScrollSelectorIndex
        ensureCachedScrollSelectorValue(nextScrollSelectorIndex)
    }

    /**
     * Decrements the `selectorIndices` whose string representations
     * will be displayed in the selector.
     */
    private fun decrementSelectorIndices(selectorIndices: IntArray) {
        for (i in selectorIndices.size - 1 downTo 1) {
            selectorIndices[i] = selectorIndices[i - 1]
        }
        var nextScrollSelectorIndex = selectorIndices[1] - 1
        if (wrapSelectorWheel && nextScrollSelectorIndex < minValue) {
            nextScrollSelectorIndex = maxValue
        }
        selectorIndices[0] = nextScrollSelectorIndex
        ensureCachedScrollSelectorValue(nextScrollSelectorIndex)
    }

    /**
     * Ensures we have a cached string representation of the given
     * `selectorIndex` to avoid multiple instantiations of the same string.
     */
    private fun ensureCachedScrollSelectorValue(selectorIndex: Int) {
        val cache = mSelectorIndexToStringCache
        var scrollSelectorValue = cache.get(selectorIndex)
        if (scrollSelectorValue != null) {
            return
        }
        if (selectorIndex < minValue || selectorIndex > maxValue) {
            scrollSelectorValue = ""
        } else {
            val displayedValues = displayedValues
            if (displayedValues != null) {
                val displayedValueIndex = selectorIndex - minValue
                scrollSelectorValue = displayedValues[displayedValueIndex]
            } else {
                scrollSelectorValue = formatNumber(selectorIndex)
            }
        }
        cache.put(selectorIndex, scrollSelectorValue)
    }

    private fun formatNumber(value: Int): String =
        mFormatter?.format(value) ?: formatNumberWithLocale(value)

    /**
     * Updates the view of this NumberPicker. If displayValues were specified in
     * the string corresponding to the index specified by the current value will
     * be returned. Otherwise, the formatter specified in [.setFormatter]
     * will be used to format the number.
     *
     * @return Whether the text was updated.
     */
    private fun updateTextView(): Boolean {
        /*
         * If we don't have displayed values then use the current number else
         * find the correct value in the displayed values for the current
         * number.
         */
        val displayedValues = displayedValues
        val text = if (displayedValues == null)
            formatNumber(mValue)
        else
            displayedValues[mValue - minValue]

        if (!TextUtils.isEmpty(text) && text != numberPickerTextView.text.toString()) {
            numberPickerTextView.text = text
            return true
        }
        return false
    }

    /**
     * Notifies the listener, if registered, of a change of the value of this
     * NumberPicker.
     */
    private fun notifyChange(previous: Int, current: Int) {
        mOnValueChangeListener?.onValueChange(this, previous, mValue)
    }

    /**
     * Posts a command for changing the current value by one.
     *
     * @param increment Whether to increment or decrement the value.
     */
    private fun postChangeCurrentByOneFromLongPress(increment: Boolean, delayMillis: Long) {
        if (mChangeCurrentByOneFromLongPressCommand == null) {
            mChangeCurrentByOneFromLongPressCommand = ChangeCurrentByOneFromLongPressCommand()
        } else {
            removeCallbacks(mChangeCurrentByOneFromLongPressCommand)
        }
        mChangeCurrentByOneFromLongPressCommand?.setStep(increment)
        postDelayed(mChangeCurrentByOneFromLongPressCommand, delayMillis)
    }

    /**
     * Removes the command for changing the current value by one.
     */
    private fun removeChangeCurrentByOneFromLongPress() {
        if (mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(mChangeCurrentByOneFromLongPressCommand)
        }
    }

    /**
     * Removes all pending callback from the message queue.
     */
    private fun removeAllCallbacks() {
        if (mChangeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(mChangeCurrentByOneFromLongPressCommand)
        }
        mPressedStateHelper.cancel()
    }

    /**
     * Ensures that the scroll wheel is adjusted i.e. there is no offset and the
     * middle element is in the middle of the widget.
     *
     * @return Whether an adjustment has been made.
     */
    private fun ensureScrollWheelAdjusted(): Boolean {
        // adjust to the closest value
        var deltaY = mInitialScrollOffset - mCurrentScrollOffset
        if (deltaY != 0) {
            mPreviousScrollerY = 0
            if (Math.abs(deltaY) > mSelectorElementHeight / 2) {
                deltaY += if (deltaY > 0) -mSelectorElementHeight else mSelectorElementHeight
            }
            mAdjustScroller.startScroll(0, 0, 0, deltaY, SELECTOR_ADJUSTMENT_DURATION_MILLIS)
            invalidate()
            return true
        }
        return false
    }

    private inner class PressedStateHelper : Runnable {
        private val MODE_PRESS = 1
        private val MODE_TAPPED = 2
        private var mManagedButton: Int = 0
        private var mMode: Int = 0

        fun cancel() {
            mMode = 0
            mManagedButton = 0
            this@NumberPicker.removeCallbacks(this)
            if (mIncrementVirtualButtonPressed) {
                mIncrementVirtualButtonPressed = false
                invalidate(0, mBottomSelectionDividerBottom, right, bottom)
            }
            mDecrementVirtualButtonPressed = false
            if (mDecrementVirtualButtonPressed) {
                invalidate(0, 0, right, mTopSelectionDividerTop)
            }
        }

        fun buttonPressDelayed(button: Int) {
            cancel()
            mMode = MODE_PRESS
            mManagedButton = button
            this@NumberPicker.postDelayed(this, ViewConfiguration.getTapTimeout().toLong())
        }

        fun buttonTapped(button: Int) {
            cancel()
            mMode = MODE_TAPPED
            mManagedButton = button
            this@NumberPicker.post(this)
        }

        override fun run() {
            when (mMode) {
                MODE_PRESS -> {
                    when (mManagedButton) {
                        BUTTON_INCREMENT -> {
                            mIncrementVirtualButtonPressed = true
                            invalidate(0, mBottomSelectionDividerBottom, right, bottom)
                        }
                        BUTTON_DECREMENT -> {
                            mDecrementVirtualButtonPressed = true
                            invalidate(0, 0, right, mTopSelectionDividerTop)
                        }
                    }
                }
                MODE_TAPPED -> {
                    when (mManagedButton) {
                        BUTTON_INCREMENT -> {
                            if (!mIncrementVirtualButtonPressed) {
                                this@NumberPicker.postDelayed(this,
                                    ViewConfiguration.getPressedStateDuration().toLong())
                            }
                            mIncrementVirtualButtonPressed = mIncrementVirtualButtonPressed xor true
                            invalidate(0, mBottomSelectionDividerBottom, right, bottom)
                        }
                        BUTTON_DECREMENT -> {
                            if (!mDecrementVirtualButtonPressed) {
                                this@NumberPicker.postDelayed(this,
                                    ViewConfiguration.getPressedStateDuration().toLong())
                            }
                            mDecrementVirtualButtonPressed = mDecrementVirtualButtonPressed xor true
                            invalidate(0, 0, right, mTopSelectionDividerTop)
                        }
                    }
                }
            }
        }
    }

    /**
     * Command for changing the current value from a long press by one.
     */
    private inner class ChangeCurrentByOneFromLongPressCommand : Runnable {
        private var mIncrement: Boolean = false
        fun setStep(increment: Boolean) {
            mIncrement = increment
        }

        override fun run() {
            changeValueByOne(mIncrement)
            postDelayed(this, mLongPressUpdateInterval)
        }
    }

    /**
     * @hide
     */
    private class NumberPickerTextView : AppCompatTextView {
        @JvmOverloads
        constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            gravity = Gravity.CENTER
            maxLines = 1
            background = null
        }

        override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
            super.onInitializeAccessibilityEvent(event)
            if (context !is Activity)
                event.className = View::class.java.name
        }

        override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(info)
            if (context !is Activity)
                info.className = View::class.java.name
        }
    }

    // Accessibility

    private inner class NumberPickerTouchHelper(host: View): ExploreByTouchHelper(host) {
        private val numberPickerBounds = Rect(0, 0, width, height)
        private var decrementBounds = Rect()
        private var incrementBounds = Rect()
        private var toggleBounds = Rect()

        override fun onPopulateNodeForVirtualView(virtualViewId: Int, info: AccessibilityNodeInfoCompat) {
            when (virtualViewId) {
                VIRTUAL_VIEW_ID_DECREMENT -> {
                    decrementBounds = Rect(
                        scrollX,
                        scrollY,
                        scrollX + (right - left),
                        mTopSelectionDividerTop + mSelectionDividerHeight)
                    createAccessibilityNodeInfoForVirtualButton(info, virtualViewId, decrementBounds)
                }
                VIRTUAL_VIEW_ID_INCREMENT -> {
                    incrementBounds = Rect(
                        scrollX,
                        mBottomSelectionDividerBottom - mSelectionDividerHeight,
                        scrollX + (right - left),
                        scrollY + (bottom - top))
                    createAccessibilityNodeInfoForVirtualButton(info, virtualViewId, incrementBounds)
                }
                VIRTUAL_VIEW_ID_TOGGLE -> {
                    toggleBounds = Rect(
                        scrollX,
                        mTopSelectionDividerTop + mSelectionDividerHeight,
                        scrollX + (right - left),
                        mBottomSelectionDividerBottom - mSelectionDividerHeight)
                    createAccessibilityNodeInfoForVirtualButton(info, virtualViewId, toggleBounds)
                }
                else -> {
                    info.contentDescription = ""
                    info.setBoundsInParent(numberPickerBounds)
                }
            }
        }

        override fun onPerformActionForVirtualView(virtualViewId: Int, action: Int, arguments: Bundle?): Boolean {
            if (action == AccessibilityNodeInfo.ACTION_CLICK) {
                when (virtualViewId) {
                    VIRTUAL_VIEW_ID_INCREMENT -> changeValueByOne(true)
                    VIRTUAL_VIEW_ID_DECREMENT -> changeValueByOne(false)
                    VIRTUAL_VIEW_ID_TOGGLE -> toggleValue()
                }
                return true
            }
            return false
        }

        override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>) {
            virtualViewIds.clear()

            if (isToggle) {
                virtualViewIds.add(VIRTUAL_VIEW_ID_TOGGLE)
            } else {
                virtualViewIds.add(VIRTUAL_VIEW_ID_DECREMENT)
                virtualViewIds.add(VIRTUAL_VIEW_ID_INCREMENT)
            }
        }

        override fun getVirtualViewAt(x: Float, y: Float): Int {
            val xPosition = x.toInt()
            val yPosition = y.toInt()
            return when {
                decrementBounds.contains(xPosition, yPosition) -> VIRTUAL_VIEW_ID_DECREMENT
                incrementBounds.contains(xPosition, yPosition) -> VIRTUAL_VIEW_ID_INCREMENT
                toggleBounds.contains(xPosition, yPosition) -> VIRTUAL_VIEW_ID_TOGGLE
                else -> INVALID_ID
            }
        }

        private fun createAccessibilityNodeInfoForVirtualButton(info: AccessibilityNodeInfoCompat, virtualViewId: Int, rect: Rect) {
            info.className = AppCompatButton::class.java.name
            setNodeInfoDescriptions(virtualViewId, info)
            setInfoBounds(info, rect)

            val clickAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                AccessibilityNodeInfo.ACTION_CLICK,
                getNodeInfoClickActionAnnouncement(virtualViewId)
            )

            info.addAction(clickAction)
        }

        private fun setNodeInfoDescriptions(virtualViewId: Int, info: AccessibilityNodeInfoCompat) {
            when (virtualViewId) {
                VIRTUAL_VIEW_ID_DECREMENT -> {
                    info.contentDescription = virtualDecrementButtonDescription
                    info.hintText = virtualDecrementHint
                }
                VIRTUAL_VIEW_ID_INCREMENT -> {
                    info.contentDescription = virtualIncrementButtonDescription
                    info.hintText = virtualIncrementHint
                }
                VIRTUAL_VIEW_ID_TOGGLE -> {
                    info.contentDescription = virtualToggleDescription
                    info.hintText = virtualToggleHint
                }
            }
        }
        
        private fun getNodeInfoClickActionAnnouncement(virtualViewId: Int): String = 
            when (virtualViewId) {
                VIRTUAL_VIEW_ID_DECREMENT -> virtualDecrementClickActionAnnouncement
                VIRTUAL_VIEW_ID_INCREMENT -> virtualIncrementClickActionAnnouncement
                VIRTUAL_VIEW_ID_TOGGLE -> virtualToggleClickActionAnnouncement
                else -> ""
            }

        private fun setInfoBounds(info: AccessibilityNodeInfoCompat, rect: Rect) {
            val boundsInParent = Rect(rect)
            info.setBoundsInParent(boundsInParent)

            val locationOnScreen = IntArray(2)
            getLocationOnScreen(locationOnScreen)
            boundsInParent.offset(locationOnScreen[0], locationOnScreen[1])
            info.setBoundsInScreen(boundsInParent)
        }
    }
}