/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.calendar

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import androidx.appcompat.widget.AppCompatButton
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Checkable
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.DateStringUtils
import com.microsoft.fluentui.util.DateTimeUtils
import com.microsoft.fluentui.util.isAccessibilityEnabled
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * [CalendarDayView] View that displays a day of the week
 */
internal class CalendarDayView: AppCompatButton, Checkable {
    companion object {
        private const val MIN_TEXT_SIZE = 2
        private const val AUTO_SIZE_TEXT_GRANULARITY_STEP = 2
    }
    /**
     * sets the date of the View
     */
    var date: LocalDate = LocalDate.now()
        set(value) {
            field = value
            val today = LocalDate.now()
            updateBackgroundColor(today)
            updateText()
            setTextColor(textDayColor)
            updateContentDescription()
            isActivated = DateTimeUtils.isSameDay(today, field)

            ViewCompat.postInvalidateOnAnimation(this)
        }

    /**
     * sets the selected drawable to use
     */
    var selectedDrawable: Drawable? = null
        set(value) {
            field = value
            field?.setBounds(0, 0, measuredWidth, measuredHeight)
            field?.setColorFilter(config.selectionAccentColor, PorterDuff.Mode.SRC_ATOP)

            ViewCompat.postInvalidateOnAnimation(this)
        }

    // underscore prevents JVM platform declaration clash
    private var _foregroundDrawable: Drawable? = null
        set(value) {
            if (field == value)
                return

            // reset foreground drawable
            field?.callback = null
            unscheduleDrawable(field)

            // set foreground drawable
            field = value
            field?.let {
                it.callback = this
                it.state = drawableState
            }

            ViewCompat.postInvalidateOnAnimation(this)
        }

    private lateinit var config: CalendarView.Config

    private var paint = Paint()

    private var _isChecked = false

    private var regularAppearance = 0
    private var todayAppearance = 0
    private var checkedAppearance = 0

    private var todayBackgroundDrawable: Drawable? = null

    private var textDayColor: ColorStateList? = null
    private var viewBackgroundColor = Color.TRANSPARENT

    /**
     * @param [context] Context
     * @param [calendarConfig] Config passes CalendarView attributes
     * @constructor creates an instance of a CalendarDayView
     */
    constructor(appContext: Context, calendarConfig: CalendarView.Config) : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Calendar)) {
        config = calendarConfig
        setWillNotDraw(false)

        todayBackgroundDrawable = ContextCompat.getDrawable(context, R.drawable.calendar_background_today)

        paint.isAntiAlias = true

        regularAppearance = R.style.TextAppearance_FluentUI_CalendarDay
        todayAppearance = R.style.TextAppearance_FluentUI_CalendarDay2
        checkedAppearance = R.style.TextAppearance_FluentUI_CalendarDay2

        textDayColor = config.calendarDayTextColor

        background = null
        gravity = Gravity.CENTER
        includeFontPadding = false
        TextViewCompat.setTextAppearance(this, regularAppearance)
        setTextSize(TypedValue.COMPLEX_UNIT_PX, config.calendarDayTextSize.toFloat())
        isAllCaps = false

        _foregroundDrawable = ContextCompat.getDrawable(context, R.drawable.calendar_day_background)

        setPadding(0, 0, 0, 0)
    }

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr)

    override fun setChecked(checked: Boolean) {
        if (_isChecked == checked)
            return

        _isChecked = checked
        updateTypeface()
        updateText()
        refreshDrawableState()

        if (context.isAccessibilityEnabled) {
            sendAccessibilityEvent(AccessibilityEvent.CONTENT_CHANGE_TYPE_UNDEFINED)
        }

        ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun isChecked(): Boolean {
        return _isChecked
    }

    override fun toggle() {
        _isChecked = !_isChecked
    }

    override fun setActivated(activated: Boolean) {
        val wasActivated = isActivated
        super.setActivated(activated)
        if (wasActivated == activated)
            return

        setTextColor(textDayColor)
        updateTypeface()
        updateText()

        ViewCompat.postInvalidateOnAnimation(this)
    }

    override fun setOnClickListener(listener: View.OnClickListener?) {
        super.setOnClickListener(listener)
        isClickable = listener != null
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        todayBackgroundDrawable?.setBounds(0, 0, measuredWidth, measuredHeight)
        selectedDrawable?.setBounds(0, 0, measuredWidth, measuredHeight)
        _foregroundDrawable?.setBounds(0, 0, measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val width = measuredWidth
        val height = measuredHeight

        if (viewBackgroundColor != Color.TRANSPARENT) {
            paint.color = viewBackgroundColor
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }

        if (isChecked && selectedDrawable != null) {
            selectedDrawable?.setBounds(0, 0, measuredWidth, measuredHeight)
            selectedDrawable?.draw(canvas)
        } else if (isActivated) {
            todayBackgroundDrawable?.draw(canvas)
        }

        _foregroundDrawable?.draw(canvas)

        super.onDraw(canvas)
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, intArrayOf(android.R.attr.state_checked))
        }
        return drawableState
    }

    override fun verifyDrawable(who: Drawable): Boolean {
        return super.verifyDrawable(who) || who === _foregroundDrawable
    }

    override fun jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState()
        _foregroundDrawable?.let { if (it.isStateful) it.jumpToCurrentState() }
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        _foregroundDrawable?.let { if (it.isStateful) it.state = drawableState }
    }

    override fun drawableHotspotChanged(x: Float, y: Float) {
        super.drawableHotspotChanged(x, y)
        _foregroundDrawable?.setHotspot(x, y)
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        updateContentDescription()
        super.onInitializeAccessibilityNodeInfo(info)
    }

    private fun updateText() {
        val dayOfMonth = date.dayOfMonth
        if (dayOfMonth == 1 && !isChecked) {
            val stringBuilder = SpannableStringBuilder()

            stringBuilder.append(DateTimeFormatter.ofPattern("MMM").format(date))
            val monthEndIdx = stringBuilder.length
            stringBuilder.append("\n")
            stringBuilder.append(Integer.toString(date.dayOfMonth))

            var maxTextSize = config.calendarDayTextSize

            if (date.year != ZonedDateTime.now().year) {
                stringBuilder.append("\n")
                stringBuilder.append(Integer.toString(date.year))
                maxTextSize = config.calendarDayMonthYearTextSize
            } else {
                stringBuilder.setSpan(AbsoluteSizeSpan(config.calendarDayMonthYearTextSize),
                    0,
                    monthEndIdx,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
                this,
                MIN_TEXT_SIZE,
                maxTextSize,
                AUTO_SIZE_TEXT_GRANULARITY_STEP,
                TypedValue.COMPLEX_UNIT_PX
            )

            text = stringBuilder
        } else {
            TextViewCompat.setAutoSizeTextTypeWithDefaults(this, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, config.calendarDayTextSize.toFloat())
            text = String.format(Locale.ROOT, Integer.toString(dayOfMonth))
        }
    }

    private fun updateBackgroundColor(localDate: LocalDate) {
        val numMonths = Math.abs(ChronoUnit.MONTHS.between(date.withDayOfMonth(1), localDate.withDayOfMonth(1)))
        viewBackgroundColor =
            if (config.differentiateOddEvenMonth) {
                if (numMonths % 2 == 0L) Color.TRANSPARENT else config.otherMonthBackgroundColor
            } else {
                if (date.isBefore(LocalDate.now())) config.otherMonthBackgroundColor else Color.TRANSPARENT
            }
    }

    private fun updateContentDescription() {
        val formattedDate = DateStringUtils.formatDateWithWeekDay(context, date)
        val stringBuilder = StringBuilder(formattedDate)

        if (isActivated) {
            stringBuilder.append(", ")
            stringBuilder.append(resources.getString(R.string.accessibility_today))
        }

        if (isChecked) {
            stringBuilder.append(", ")
            stringBuilder.append(resources.getString(R.string.accessibility_selected))
        }

        contentDescription = stringBuilder.toString()
    }

    private fun updateTypeface() {
        val appearance = when {
            isActivated -> todayAppearance
            isChecked -> checkedAppearance
            else -> regularAppearance
        }
        TextViewCompat.setTextAppearance(this, appearance)
    }
}