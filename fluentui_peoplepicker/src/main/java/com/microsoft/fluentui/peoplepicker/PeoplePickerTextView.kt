/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.peoplepicker

import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.customview.widget.ExploreByTouchHelper
import android.text.*
import android.text.method.MovementMethod
import android.text.style.TextAppearanceSpan
import android.text.util.Rfc822Token
import android.text.util.Rfc822Tokenizer
import android.util.AttributeSet
import android.util.Patterns
import android.view.DragEvent
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.KeyEvent
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.MultiAutoCompleteTextView
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.persona.IPersona
import com.microsoft.fluentui.persona.PersonaChipView
import com.microsoft.fluentui.persona.setPersona
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.util.getTextSize
import com.microsoft.fluentui.util.inputMethodManager
import com.microsoft.fluentui.tokenautocomplete.CountSpan
import com.microsoft.fluentui.tokenautocomplete.TokenCompleteTextView
import kotlin.math.max

enum class PeoplePickerPersonaChipClickStyle(internal val tokenClickStyle: TokenCompleteTextView.TokenClickStyle) {
    // Do nothing, but make sure the cursor is not in the persona chip.
    NONE(TokenCompleteTextView.TokenClickStyle.None),
    // Delete the persona chip.
    DELETE(TokenCompleteTextView.TokenClickStyle.Delete),
    // Select the persona chip. A second click will delete it.
    SELECT(TokenCompleteTextView.TokenClickStyle.Select),
    // Select the persona chip. A second click will deselect it.
    SELECT_DESELECT(TokenCompleteTextView.TokenClickStyle.SelectDeselect)
}

/**
 * [PeoplePickerTextView] provides all of the functionality needed to add [PersonaChipView]s as [tokens]
 * into an [EditText] view.
 *
 * Functionality we add in addition to [TokenCompleteTextView]'s functionality includes:
 * - Click api for SELECT_DESELECT persona chips
 * - Drag and drop option
 * - Accessibility
 * - Hiding the cursor when a persona chip is selected
 * - Styling the [CountSpan]
 *
 * TODO Known issues:
 * - Using backspace to delete a selected token does not work if other text is entered in the input;
 * [TokenCompleteTextView] overrides [onCreateInputConnection] which blocks our ability to control this functionality.
 */
internal class PeoplePickerTextView :
    TokenCompleteTextView<IPersona> {
    companion object {
        // Max number of personas the screen reader will announce on focus.
        private const val MAX_PERSONAS_TO_READ = 3
        private const val BACKGROUND_DRAG_ALPHA = 75
        // Removes constraints to the input field
        private val noFilters = arrayOfNulls<InputFilter>(0)
        // Constrains changes that can be made to the input field to none
        private val blockInputFilters = arrayOf(InputFilter { _, _, _, _, _, _ -> "" })
    }

    /**
     * Defines what happens when a user clicks on a persona chip.
     */
    var personaChipClickStyle: PeoplePickerPersonaChipClickStyle =
        PeoplePickerPersonaChipClickStyle.SELECT
        set(value) {
            field = value
            setTokenClickStyle(value.tokenClickStyle)
        }
    /**
     * Flag for enabling Drag and Drop persona chips.
     */
    var allowPersonaChipDragAndDrop: Boolean = false
    /**
     * This will automatically remove persona chips from your text view, but you will need to do extra
     * filtering work to ensure duplicates don't end up in your dropdown list.
     */
    var allowDuplicatePersonaChips: Boolean = false
        set(value) {
            field = value
            allowDuplicates(value)
        }
    /**
     * Limits the total number of persona chips that can be added to the field.
     */
    var personaChipLimit: Int = -1
        set(value) {
            field = value
            setTokenLimit(value)
        }
    /**
     * Store the hint so that we can control when it is announced for accessibility.
     * [PeoplePickerView.showHint] will also display the hint.
     */
    var valueHint: CharSequence = ""
        set(value) {
            field = value
            hint = value
        }
    /**
     * This proxy for [setThreshold] allows a threshold of 0 input characters.
     */
    var characterThreshold: Int = 1
        set(value) {
            field = max(0, value)
            threshold = characterThreshold
        }

    var allowCollapse: Boolean = true
        set(value) {
            field = value
            allowCollapse(value)
        }

    /**
     * When a persona chip with a [PeoplePickerPersonaChipClickStyle] of SELECT_DESELECT is selected,
     * the next touch will fire [PersonaChipClickListener.onClick].
     */
    var personaChipClickListener: PeoplePickerView.PersonaChipClickListener? = null
    lateinit var onCreatePersona: (name: String, email: String) -> IPersona

    val countSpanStart: Int
        get() = text.indexOfFirst { it == '+' }
    private val countSpanEnd: Int
        get() = text.length

    private val accessibilityTouchHelper = AccessibilityTouchHelper(this)
    private var blockedMovementMethod: MovementMethod? = null
    private var gestureDetector: GestureDetector
    private val hiddenPersonaSpans = ArrayList<TokenImageSpan>()
    private val lastPositionForSingleLine: Int
        get() {
            if (layout == null)
                onPreDraw()

            return layout.getLineVisibleEnd(0)
        }
    // Keep track of persona selection for accessibility events
    private var selectedPersona: IPersona? = null
        set(value) {
            field = value
            if (value != null)
                blockInput()
            else
                unblockInput()
        }
    private var shouldAnnouncePersonaAdditionMap = mutableMapOf<IPersona, Boolean>()
    private var shouldAnnouncePersonaRemovalMap = mutableMapOf<IPersona, Boolean>()
    private var searchConstraint: CharSequence = ""
    private var lastSpan: TokenImageSpan? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS

        ViewCompat.setAccessibilityDelegate(this, accessibilityTouchHelper)
        super.setTokenListener(TokenListener(this))
        gestureDetector = GestureDetector(context, SimpleGestureListener())
        setLineSpacing(resources.getDimension(R.dimen.fluentui_people_picker_persona_chip_vertical_spacing), 1f)
    }

    // @JvmOverloads does not work in this scenario due to parameter defaults
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun getViewForObject(`object`: IPersona): View {
        val view = PersonaChipView(context)
        view.showCloseIconWhenSelected = personaChipClickStyle == PeoplePickerPersonaChipClickStyle.SELECT
        view.listener = object : PersonaChipView.Listener {
            override fun onClicked() {
                // no op
            }

            override fun onSelected(selected: Boolean) {
                if (selected)
                    selectedPersona = `object`
                else
                    selectedPersona = null
            }
        }
        view.setPersona(`object`)
        return view
    }

    private fun getViewForObjectWithSpace(`object`: IPersona, marginStart: Int): View {
        val view = getViewForObject(`object`)
        val layout = LinearLayout(context)
        val layoutParam: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParam.marginStart = marginStart
        view.layoutParams = layoutParam
        layout.addView(view, layoutParam)
        return layout
    }

    override fun defaultObject(completionText: String): IPersona? {
        if (completionText.isEmpty() || !isEmailValid(completionText))
            return null

        return onCreatePersona("", completionText)
    }

    override fun buildSpanForObject(obj: IPersona): TokenImageSpan {
        // This ensures that persona spans will be short enough to leave room for the count span.
        val countSpanWidth = resources.getDimension(R.dimen.fluentui_people_picker_count_span_width).toInt()
        lastSpan = TokenImageSpan(getViewForObject(obj), obj, maxTextWidth().toInt() - countSpanWidth)
        return lastSpan as TokenImageSpan
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed)
            performCollapseAndAdjustLayout(hasFocus())
    }

    override fun performCollapse(hasFocus: Boolean) {
        // super.performCollapse is limited to handling focus changes. We adapted the method to handle layout changes as well.
        performCollapseAndAdjustLayout(hasFocus)
    }

    override fun onFocusChanged(hasFocus: Boolean, direction: Int, previous: Rect?) {
        super.onFocusChanged(hasFocus, direction, previous)

        // Soft keyboard does not always show up when the view first loads without this
        if (hasFocus) {
            // add bottom border
            this.background = ContextCompat.getDrawable(context, R.drawable.people_picker_textview_focusable_background)
            post {
                context.inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            }
        } else {
            // remove bottom border
            this.background = null
        }

        /**
        * Along with [enoughToFilter], this is a work around for AutoCompleteTextView preventing filtering when no characters are input.
        */
        if (hasFocus && characterThreshold == 0)
            post {
                showDropDown()
                requestLayout()
            }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        val handled = super.onKeyUp(keyCode, event)
        if(!handled && keyCode == KeyEvent.KEYCODE_TAB){
            if(!event.isShiftPressed) {
                val view = parent.focusSearch(this, FOCUS_FORWARD)
                return view?.requestFocus() ?: false
            }
        }
        return handled
    }

    // super.enoughToFilter() sometimes does not allow for showing suggestions when the threshold is 0.
    override fun enoughToFilter(): Boolean = characterThreshold == 0 || super.enoughToFilter()

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        //TODO Bypassed lint error now. Check the comment:- super.onSelectionChanged is buggy, but we still need the accessibility event from the super super call.
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED)
        // This fixes buggy cursor position in accessibility mode.
        // Cutting spans to the clipboard is not functional so this also prevents that operation from being an option.
        setSelection(text.length)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        selectedPersona = null

        if (lengthAfter > lengthBefore || lengthAfter < lengthBefore && !text.isNullOrEmpty())
            setupSearchConstraint(text)
    }

    override fun replaceText(text: CharSequence?) {
        // Enforce personaChipLimit. TokenCompleteTextView enforces the limit for other scenarios.
        if (objects.size == personaChipLimit)
            return

        super.replaceText(text)
    }

    override fun canDeleteSelection(beforeLength: Int): Boolean {
        // This method is called from keyboard events so any token removed would be coming from the user.
        return super.canDeleteSelection(beforeLength)
    }

    override fun removeObject(`object`: IPersona?) {
        `object`?.let {
            shouldAnnouncePersonaRemovalMap[it] = false
            super.removeObject(it)
        }
    }

    override fun showDropDown() {
        dropDownHeight = getMaxAvailableHeight()
        super.showDropDown()
    }

    internal fun addObjects(personas: List<IPersona>?) {
        if (personas == null || personas.isEmpty())
            return

        personas.forEach {
            // Add the personas as hidden spans,
            // then performCollapseAndAdjustLayout will figure out which to add based on available space.
            hiddenPersonaSpans.add(buildSpanForObject(it))
        }
        // If personas are added during the initial load, performCollapseAndAdjustLayout will be called again from onLayout,
        // which is the call that will add the hidden spans once available space can be determined.
        performCollapseAndAdjustLayout(hasFocus())
    }

    internal fun removeObjects(personas: List<IPersona>?) {
        if (personas == null)
            return

        personas.forEach { removeObject(it) }
        removeCountSpan()
    }

    /**
     * Adapted from Android's PopupWindow.
     */
    private fun getMaxAvailableHeight(): Int {
        val displayFrame = Rect()
        getWindowVisibleDisplayFrame(displayFrame)

        val anchorLocationOnScreen = IntArray(2)
        getLocationOnScreen(anchorLocationOnScreen)

        val anchorTop = anchorLocationOnScreen[1]
        val distanceToBottom = displayFrame.bottom - (anchorTop + height)
        val distanceToTop = anchorTop - displayFrame.top
        var maxAvailableHeight = max(distanceToBottom, distanceToTop)

        if (dropDownBackground != null) {
            val backgroundPadding = Rect()
            dropDownBackground.getPadding(backgroundPadding)
            maxAvailableHeight -= backgroundPadding.top + backgroundPadding.bottom
        }

        return maxAvailableHeight
    }

    private fun setupSearchConstraint(text: CharSequence?) {
        accessibilityTouchHelper.invalidateRoot()
        val personaSpanEnd = text?.indexOfLast { it == ',' }?.plus(1) ?: -1
        searchConstraint = when {
            // Ignore the count span
            countSpanStart != -1 -> ""
            // If we have personas, we'll also have comma tokenizers to remove from the text
            personaSpanEnd > 0 -> text?.removeRange(text.indexOfFirst { it == ',' }, personaSpanEnd)?.trim() ?: ""
            // Any other characters will be used as the search constraint to perform filtering.
            else -> text ?: ""
        }
        // This keeps the entered text accessibility focused as the user types, which makes the suggested personas list the next focusable view.
        if (isFocused)
            accessibilityTouchHelper.sendEventForVirtualView(objects.size, AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
    }

    /**
     * Collapse the view by removing all the persona spans not on the first line.
     * Displays a "+x" count span representing the number of hidden persona spans.
     * Restores the hidden persona spans when the view gains focus.
     * Adjusts persona span layout when the view's layout changes.
     * Adapted from [performCollapse] in [TokenCompleteTextView].
     **/
    private fun performCollapseAndAdjustLayout(hasFocus: Boolean) {
        if (!hasFocus && allowCollapse) {
            val spansToHide = ArrayList<TokenImageSpan>()

            // Spans don't always fit their new space so we rebuild the spans in available space.
            rebuildPersonaSpans(lastPositionForSingleLine)
            // Remove persona spans that won't fit in a single line and save them to later be restored.
            hidePersonaSpansThatDontFit(spansToHide)

            // Sometimes we have room to restore the visibility of more hidden persona spans.
            // We know if we hid persona spans, we won't need to add any.
            if (spansToHide.isEmpty())
                addHiddenPersonaSpansThatFit()

            updateCountSpan()
        } else {
            removeCountSpan()
            rebuildPersonaSpans()

            // Restore the persona spans we have hidden.
            hiddenPersonaSpans.forEach { span ->
                // addObject does not work in this code block when in accessibility mode so we use insertPersonaSpan instead.
                // The persona still gets added to objects through the TokenSpanWatcher.
                shouldAnnouncePersonaAdditionMap[span.token] = false
                insertPersonaSpan(span.token)
            }

            hiddenPersonaSpans.clear()
        }
    }

    /**
     * Add a picked persona
     */
    fun addPickedPersona(persona: IPersona) {
        super.addObject(persona)
    }

    /**
     * Removes a persona from picked items
     */
    fun removePickedPersona(persona: IPersona) {
        shouldAnnouncePersonaRemovalMap[persona] = true
        super.removeObject(persona)
    }

    /**
     * Refreshes picked persona views, since there is no option to refresh the existing views and [invalidate] is not working,
     * so rebuilding all the spans again
     */
    fun refreshPickedPersonaViews() {
        rebuildPersonaSpans()
    }

    /**
     * Insert a new span for an object.
     * Adapted from [insertSpan] and [addObject] in [TokenCompleteTextView].
     * Because [addObject] is in a post runnable sometimes the timing is off,
     * which creates bugs for accessibility and adjusting layout of spans.
     */
    private fun insertPersonaSpan(persona: IPersona) {
        if (!allowDuplicatePersonaChips && objects.contains(persona))
            return
        if (objects.size == personaChipLimit)
            return

        var offset = text.length
        val completionText = currentCompletionText()
        // The user has entered some text that has not yet been tokenized.
        // Find the beginning of this text and insert the new token there.
        if (!completionText.isNullOrEmpty())
            offset = TextUtils.indexOf(text, completionText)

        // We use "," to be consistent with splitChar in TokenCompleteTextView.
        val spannableStringBuilder = SpannableStringBuilder("," + MultiAutoCompleteTextView.CommaTokenizer().terminateToken(""))
        val personaSpan = buildSpanForObject(persona)
        text.insert(offset, spannableStringBuilder)
        text.setSpan(personaSpan, offset, offset + spannableStringBuilder.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun checkForIntersectionWithHinge(tokenImageSpan: TokenImageSpan) {
        if (layout == null) {
            return
        }
        val spanStart = text.getSpanStart(tokenImageSpan)
        val spanEnd = text.getSpanEnd(tokenImageSpan)
        val personaBound = calculateBounds(spanStart, spanEnd, 0)
        val parentTextViewLocation = intArrayOf(0, 0)
        getLocationInWindow(parentTextViewLocation)
        personaBound.left += parentTextViewLocation[0]
        personaBound.right += parentTextViewLocation[0]
        personaBound.top += parentTextViewLocation[1]
        personaBound.bottom += parentTextViewLocation[1]
    }

    // Persona spans don't always fit their new space so we rebuild the spans in available space.
    private fun rebuildPersonaSpans(end: Int = text.length) {
        // We can't cache this array without getting a crash from the generic types in API 19.
        getPersonaSpans<TokenCompleteTextView<IPersona>.TokenImageSpan>(end = end).forEach { personaSpan ->
            val rebuiltSpan = buildSpanForObject(personaSpan.token)
            shouldAnnouncePersonaRemovalMap[personaSpan.token] = false
            shouldAnnouncePersonaAdditionMap[rebuiltSpan.token] = false
            val spanStart = text.getSpanStart(personaSpan)
            val spanEnd = text.getSpanEnd(personaSpan)
            text.removeSpan(personaSpan)
            text.setSpan(rebuiltSpan, spanStart, spanEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    private fun hidePersonaSpansThatDontFit(spansToHide: ArrayList<TokenImageSpan>) {
        // Take spans from the back and add them to the front to maintain persona position.
        for (span in getPersonaSpans<TokenCompleteTextView<IPersona>.TokenImageSpan>().reversed()) {
            if (text.getSpanStart(span) > lastPositionForSingleLine && !hiddenPersonaSpans.contains(span)) {
                spansToHide.add(span)
                hiddenPersonaSpans.add(0, span)
                removeObject(span.token)
            }
        }
    }

    private fun addHiddenPersonaSpansThatFit() {
        if (hiddenPersonaSpans.isEmpty())
            return

        val addedPersonaSpans = ArrayList<TokenImageSpan>()
        for (span in hiddenPersonaSpans) {
            val personaChipView = getViewForObject(span.token)
            personaChipView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)

            val countSpanWidth = resources.getDimension(R.dimen.fluentui_people_picker_count_span_width).toInt()
            val endOfLastLine = layout.getPrimaryHorizontal(lastPositionForSingleLine).toInt()
            val remainingAvailableWidth = width - endOfLastLine - countSpanWidth

            if (personaChipView.measuredWidth <= remainingAvailableWidth) {
                // Using insertPersonaSpan instead of addObject so that remaining available width is more accurate.
                insertPersonaSpan(span.token)
                addedPersonaSpans.add(span)
            } else {
                break
            }
        }

        hiddenPersonaSpans.removeAll(addedPersonaSpans)
    }

    private fun createCountSpan(count: Int): SpannableString {
        val replacementCountSpan = SpannableString("+$count")

        // Set the TextAppearance of the count span
        replacementCountSpan.setSpan(
            TextAppearanceSpan(context, R.style.TextAppearance_FluentUI_PeoplePickerCountSpan),
            0,
            replacementCountSpan.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Center the count span
        val replacementCountSpanPaint = Paint()
        val replacementCountSpanBounds = Rect()
        replacementCountSpanPaint.textSize = context.getTextSize(R.style.TextAppearance_FluentUI_PeoplePickerCountSpan)
        replacementCountSpanPaint.getTextBounds(replacementCountSpan.toString(), 0, replacementCountSpan.length, replacementCountSpanBounds)
        replacementCountSpan.setSpan(
            CenterVerticalSpan(replacementCountSpanBounds),
            0,
            replacementCountSpan.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return replacementCountSpan
    }

    private fun updateCountSpan() {
        post {
            if (hiddenPersonaSpans.size > 0) {
                val replacementCountSpan = createCountSpan(hiddenPersonaSpans.size)
                removeCountSpan()
                text.insert(text.length, replacementCountSpan)
            } else {
                removeCountSpan()
            }
        }
    }

    private fun removeCountSpan() {
        val countSpanStart = countSpanStart
        if (countSpanStart > -1)
            text.delete(countSpanStart, countSpanEnd)
    }

    private fun isEmailValid(email: CharSequence): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun blockInput() {
        isCursorVisible = false
        filters = blockInputFilters

        // Prevents other input from being selected when a persona chip is selected
        blockedMovementMethod = movementMethod
        movementMethod = null
    }

    private fun unblockInput() {
        isCursorVisible = true
        filters = noFilters

        // Restores original MovementMethod we blocked during selection
        if (blockedMovementMethod != null)
            movementMethod = blockedMovementMethod
    }

    private inline fun <reified T> getPersonaSpans(start: Int = 0, end: Int = text.length): Array<T> =
        text.getSpans(start, end, TokenImageSpan::class.java) as Array<T>

    private fun getSpanForPersona(persona: Any): TokenImageSpan? =
        getPersonaSpans<TokenCompleteTextView<IPersona>.TokenImageSpan>().firstOrNull { it.token === persona }

    // Token listener

    private var tokenListener: TokenCompleteTextView.TokenListener<IPersona>? = null

    override fun setTokenListener(l: TokenCompleteTextView.TokenListener<IPersona>?) {
        tokenListener = l
    }

    private class TokenListener(val view: PeoplePickerTextView) : TokenCompleteTextView.TokenListener<IPersona> {
        override fun onTokenAdded(token: IPersona) {
            if (view.shouldAnnouncePersonaAdditionMap[token] != false)
                view.tokenListener?.onTokenAdded(token)
            if (view.isFocused)
                view.announcePersonaAdded(token)
            view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED)
            view.shouldAnnouncePersonaAdditionMap.remove(token)
        }

        override fun onTokenRemoved(token: IPersona) {
            if (view.shouldAnnouncePersonaRemovalMap[token] != false)
                view.tokenListener?.onTokenRemoved(token)
            if (view.isFocused)
                view.announcePersonaRemoved(token)
            view.shouldAnnouncePersonaRemovalMap.remove(token)
        }
    }

    // Drag and drop

    private var isDraggingPersonaChip: Boolean = false
    private var initialTouchedPersonaSpan: TokenImageSpan? = null

    override fun onTouchEvent(event: MotionEvent): Boolean = gestureDetector.onTouchEvent(event)

    override fun onDragEvent(event: DragEvent): Boolean {
        if (!allowPersonaChipDragAndDrop)
            return false

        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> return event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)

            DragEvent.ACTION_DRAG_ENTERED -> requestFocus()

            DragEvent.ACTION_DROP -> return addPersonaFromDragEvent(event)

            DragEvent.ACTION_DRAG_ENDED -> {
                if (!event.result && isDraggingPersonaChip)
                    addPersonaFromDragEvent(event)
                isDraggingPersonaChip = false
            }
        }
        return false
    }

    // This declares whether personaChipClickListener could be called
    private fun isPersonaChipClickable(persona: IPersona): Boolean =
        selectedPersona != null &&
        personaChipClickStyle == PeoplePickerPersonaChipClickStyle.SELECT_DESELECT &&
        persona == selectedPersona

    private fun getClipDataForPersona(persona: IPersona): ClipData? {
        val name = persona.name
        val email = persona.email
        val rfcToken = Rfc822Token(name, email, null)
        return ClipData.newPlainText(if (TextUtils.isEmpty(name)) email else name, rfcToken.toString())
    }

    private fun getPersonaForClipData(clipData: ClipData): IPersona? {
        if (!clipData.description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) || clipData.itemCount != 1)
            return null

        val clipDataItem = clipData.getItemAt(0) ?: return null

        val data = clipDataItem.text
        if (TextUtils.isEmpty(data))
            return null

        val rfcTokens = Rfc822Tokenizer.tokenize(data)
        if (rfcTokens == null || rfcTokens.isEmpty())
            return null

        val rfcToken = rfcTokens[0]
        return onCreatePersona(rfcToken.name ?: "", rfcToken.address ?: "")
    }

    private fun startPersonaDragAndDrop(persona: IPersona) {
        val clipData = getClipDataForPersona(persona) ?: return

        // Layout a copy of the persona chip to use as the drag shadow
        val personaChipView = getViewForObject(persona)
        personaChipView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        personaChipView.layout(0, 0, personaChipView.measuredWidth, personaChipView.measuredHeight)
        personaChipView.background = ColorDrawable(ThemeUtil.getThemeAttrColor(context, R.attr.fluentuiPeoplePickerTextViewDragBackgroundColor))
        personaChipView.background.alpha = BACKGROUND_DRAG_ALPHA

        // We pass the persona object as LocalState so we can restore it when dropping
        // [startDrag] is deprecated, but the new [startDragAndDrop] requires a higher api than our min
        isDraggingPersonaChip = startDrag(clipData, View.DragShadowBuilder(personaChipView), persona, 0)
        if (isDraggingPersonaChip)
            removeObject(persona)
    }

    private fun getPersonaSpanAt(x: Float, y: Float): TokenImageSpan? {
        if (text.isEmpty())
            return null

        val offset = getOffsetForPosition(x, y)
        if (offset == -1)
            return null

        return getPersonaSpans<TokenCompleteTextView<IPersona>.TokenImageSpan>(offset, offset).firstOrNull()
    }

    private fun addPersonaFromDragEvent(event: DragEvent): Boolean {
        var persona = event.localState as? IPersona

        // If it looks like the drag & drop is not coming from us, try to extract a persona object from the clipData
        if (persona == null && event.clipData != null)
            persona = getPersonaForClipData(event.clipData)

        if (persona == null)
            return false

        addObject(persona)

        return true
    }

    private inner class SimpleGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onLongPress(event: MotionEvent) {
            val touchedPersonaSpan = getPersonaSpanAt(event.x, event.y) ?: return
            if (allowPersonaChipDragAndDrop && !isDraggingPersonaChip)
                startPersonaDragAndDrop(touchedPersonaSpan.token)
        }

        override fun onDown(event: MotionEvent): Boolean {
            val touchedPersonaSpan = getPersonaSpanAt(event.x, event.y) ?: return true
            if (allowPersonaChipDragAndDrop)
                initialTouchedPersonaSpan = touchedPersonaSpan

            return true
        }

        override fun onSingleTapUp(event: MotionEvent): Boolean {
            val touchedPersonaSpan = getPersonaSpanAt(event.x, event.y)
            if (isFocused && initialTouchedPersonaSpan == touchedPersonaSpan && touchedPersonaSpan != null) {
                if (isPersonaChipClickable(touchedPersonaSpan.token))
                    personaChipClickListener?.onClick(touchedPersonaSpan.token)
                touchedPersonaSpan.onClick()
            } else if (isFocused) {
                post {
                    context.inputMethodManager.showSoftInput(this@PeoplePickerTextView, InputMethodManager.SHOW_IMPLICIT)
                }
            }

            if (!isFocused)
                requestFocus()

            initialTouchedPersonaSpan = null
            return true
        }
    }

    // Accessibility

    private var customAccessibilityTextProvider: PeoplePickerAccessibilityTextProvider? = null
    private val defaultAccessibilityTextProvider = PeoplePickerAccessibilityTextProvider(resources)
    val accessibilityTextProvider: PeoplePickerAccessibilityTextProvider
        get() = customAccessibilityTextProvider ?: defaultAccessibilityTextProvider

    fun setAccessibilityTextProvider(accessibilityTextProvider: PeoplePickerAccessibilityTextProvider?) {
        customAccessibilityTextProvider = accessibilityTextProvider
    }

    override fun dispatchHoverEvent(motionEvent: MotionEvent): Boolean {
        // Accessibility first
        return if (accessibilityTouchHelper.dispatchHoverEvent(motionEvent))
            true
        else
            super.dispatchHoverEvent(motionEvent)
    }

    private fun announcePersonaAdded(persona: IPersona) {
        accessibilityTouchHelper.invalidateRoot()

        val replacedText = if (searchConstraint.isNotEmpty())
            "${resources.getString(R.string.people_picker_accessibility_replaced, searchConstraint)} "
        else
            ""

        // We only want to announce when a persona was added by a user.
        // If text has been replaced in the text editor and a token was added, the user added a token.
        if (shouldAnnouncePersonaAdditionMap[persona] != false) {
            announceForAccessibility("$replacedText ${getAnnouncementText(
                persona,
                R.string.people_picker_accessibility_persona_added
            )}")
        }
    }

    private fun announcePersonaRemoved(persona: IPersona) {
        accessibilityTouchHelper.invalidateRoot()

        // We only want to announce when a persona was removed by a user.
        if (shouldAnnouncePersonaRemovalMap[persona] != false) {
            announceForAccessibility(getAnnouncementText(
                persona,
                R.string.people_picker_accessibility_persona_removed
            ))
        }
    }

    private fun getAnnouncementText(persona: IPersona, stringResourceId: Int): CharSequence =
        resources.getString(stringResourceId, accessibilityTextProvider.getPersonaDescription(persona))

    private fun positionIsInsidePersonaBounds(x: Float, y: Float, personaSpan: TokenImageSpan?): Boolean =
        getBoundsForPersonaSpan(personaSpan).contains(x.toInt(), y.toInt())

    private fun positionIsInsideSearchConstraintBounds(x: Float, y: Float): Boolean {
        if (searchConstraint.isNotEmpty())
            return getBoundsForSearchConstraint().contains(x.toInt(), y.toInt())
        return false
    }

    private fun getBoundsForSearchConstraint(): Rect {
        val start = text.indexOf(searchConstraint[0])
        val end = text.length
        return calculateBounds(start, end, resources.getDimension(R.dimen.fluentui_people_picker_accessibility_search_constraint_extra_space).toInt())
    }

    private fun getBoundsForPersonaSpan(personaSpan: TokenImageSpan? = null): Rect {
        val start = text.getSpanStart(personaSpan)
        val end = text.getSpanEnd(personaSpan)
        return calculateBounds(start, end)
    }

    private fun calculateBounds(start: Int, end: Int, extraSpaceForLegibility: Int = 0): Rect {
        val line = layout.getLineForOffset(end)
        // Persona spans increase line height. Without them, we need to make the virtual view bound bottom lower.
        val bounds = Rect(
            layout.getPrimaryHorizontal(start).toInt() - extraSpaceForLegibility,
            layout.getLineTop(line),
            layout.getPrimaryHorizontal(end).toInt() + extraSpaceForLegibility,
            if (getPersonaSpans<TokenCompleteTextView<IPersona>.TokenImageSpan>().isEmpty()) bottom else layout.getLineBottom(line)
        )
        bounds.offset(paddingLeft, paddingTop)
        return bounds
    }

    private fun setHint() {
        if (!isFocused)
        // If the edit box is not focused, there is no event that requires a hint.
            hint = ""
        else
            hint = valueHint
    }

    private inner class AccessibilityTouchHelper(host: View) : ExploreByTouchHelper(host) {
        // Host

        val peoplePickerTextViewBounds = Rect(0, 0, width, height)

        override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            setHint()
            setInfoText(info)
        }

        override fun onPopulateAccessibilityEvent(host: View, event: AccessibilityEvent) {
            super.onPopulateAccessibilityEvent(host, event)
            /**
             * The CommaTokenizer is confusing in the screen reader.
             * This overrides announcements that include the CommaTokenizer.
             * We handle cases for replaced text and persona spans added / removed through callbacks.
             */
            if (event?.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED)
                event.text.clear()
        }

        private fun setInfoText(info: AccessibilityNodeInfoCompat) {
            val personas = objects
            if (personas == null || personas.isEmpty())
                return

            var infoText = ""
            val hiddenPersonas = hiddenPersonaSpans.map { it.token }
            val totalPersonas = personas + hiddenPersonas

            // Read all of the personas if the list of personas in the field is short
            // Otherwise, read how many personas are in the field
            if (totalPersonas.size <= MAX_PERSONAS_TO_READ)
                infoText += totalPersonas.map { accessibilityTextProvider.getPersonaDescription(it) }.joinToString { it }
            else
                infoText = accessibilityTextProvider.getPersonaQuantityText(totalPersonas as ArrayList<IPersona>)

            info.text = infoText +
                // Also read any entered text in the field
                if (searchConstraint.isNotEmpty())
                    ", $searchConstraint"
                else
                    ""
        }

        // Virtual views

        override fun getVirtualViewAt(x: Float, y: Float): Int {
            if (objects == null || objects.size == 0)
                return INVALID_ID

            val offset = getOffsetForPosition(x, y)
            if (offset != -1) {
                val personaSpan = getPersonaSpans<TokenCompleteTextView<IPersona>.TokenImageSpan>(offset, offset).firstOrNull()
                if (personaSpan != null && positionIsInsidePersonaBounds(x, y, personaSpan) && isFocused)
                    return objects.indexOf(personaSpan.token)
                else if (searchConstraint.isNotEmpty() && positionIsInsideSearchConstraintBounds(x, y))
                    return objects.size
                else if (peoplePickerTextViewBounds.contains(x.toInt(), y.toInt())) {
                    sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
                    return HOST_ID
                }
            }

            return INVALID_ID
        }

        override fun getVisibleVirtualViews(virtualViewIds: MutableList<Int>) {
            virtualViewIds.clear()

            if (objects == null || objects.size == 0 || !isFocused)
                return

            for (i in objects.indices)
                virtualViewIds.add(i)

            if (searchConstraint.isNotEmpty())
                virtualViewIds.add(objects.size)
        }

        override fun onPopulateEventForVirtualView(virtualViewId: Int, event: AccessibilityEvent) {
            if (objects == null || virtualViewId >= objects.size) {
                // The content description is mandatory.
                event.contentDescription = ""
                return
            }

            if (!isFocused) {
                // Only respond to events for persona chips if the edit box is focused.
                // Without this the user still gets haptic feedback when hovering over a persona chip.
                event.recycle()
                event.contentDescription = ""
                return
            }

            if (virtualViewId == objects.size) {
                event.contentDescription = searchConstraint
                return
            }

            val persona = objects[virtualViewId]
            val personaSpan = getSpanForPersona(persona)
            if (personaSpan != null)
                event.contentDescription = accessibilityTextProvider.getPersonaDescription(persona)

            if (event.eventType == AccessibilityEvent.TYPE_VIEW_SELECTED || (personaSpan != null && persona == selectedPersona))
                event.contentDescription = String.format(
                    resources.getString(R.string.people_picker_accessibility_selected_persona),
                    event.contentDescription
                ) + getSelectedActionText(personaSpan)
        }

        override fun onPopulateNodeForVirtualView(virtualViewId: Int, node: AccessibilityNodeInfoCompat) {
            if (objects == null || virtualViewId > objects.size) {
                // the content description & the bounds are mandatory.
                node.contentDescription = ""
                node.setBoundsInParent(peoplePickerTextViewBounds)
                return
            }

            if (!isFocused) {
                // Only populate nodes for persona chips if the edit box is focused.
                node.recycle()
                node.contentDescription = ""
                node.setBoundsInParent(peoplePickerTextViewBounds)
                return
            }

            if (virtualViewId == objects.size) {
                if (searchConstraint.isNotEmpty()){
                    node.contentDescription = searchConstraint
                    node.setBoundsInParent(getBoundsForSearchConstraint())
                } else {
                    node.contentDescription = ""
                    node.setBoundsInParent(peoplePickerTextViewBounds)
                }
                return
            }

            val persona = objects[virtualViewId]
            val personaSpan = getSpanForPersona(persona)
            if (personaSpan != null) {
                setPersonaSpanClickAction(personaSpan, node)
                if (node.isAccessibilityFocused)
                    node.contentDescription = accessibilityTextProvider.getPersonaDescription(persona)
                else
                    node.contentDescription = ""
                node.setBoundsInParent(getBoundsForPersonaSpan(personaSpan))
            }
        }

        override fun onPerformActionForVirtualView(virtualViewId: Int, action: Int, arguments: Bundle?): Boolean {
            if (objects == null || virtualViewId >= objects.size)
                return false

            if (AccessibilityNodeInfo.ACTION_CLICK == action) {
                val persona = objects[virtualViewId]
                val personaSpan = getSpanForPersona(persona)
                if (personaSpan != null) {
                    personaSpan.onClick()
                    onPersonaSpanAccessibilityClick(personaSpan)
                    shouldAnnouncePersonaRemovalMap[persona] = true
                    return true
                }
            }

            return false
        }

        private fun onPersonaSpanAccessibilityClick(personaSpan: TokenImageSpan) {
            val persona = personaSpan.token
            val personaSpanIndex = getPersonaSpans<TokenCompleteTextView<IPersona>.TokenImageSpan>().indexOf(personaSpan)
            when (personaChipClickStyle) {
                PeoplePickerPersonaChipClickStyle.SELECT, PeoplePickerPersonaChipClickStyle.SELECT_DESELECT -> {
                    if (selectedPersona != null && selectedPersona == persona) {
                        invalidateVirtualView(personaSpanIndex)
                        sendEventForVirtualView(personaSpanIndex, AccessibilityEvent.TYPE_VIEW_CLICKED)
                        sendEventForVirtualView(personaSpanIndex, AccessibilityEvent.TYPE_VIEW_SELECTED)
                    } else {
                        if (personaChipClickStyle == PeoplePickerPersonaChipClickStyle.SELECT_DESELECT) {
                            if (personaChipClickListener != null) {
                                personaChipClickListener?.onClick(persona)
                                announceForAccessibility(resources.getString(
                                    R.string.people_picker_accessibility_clicked_persona,
                                    accessibilityTextProvider.getDefaultPersonaDescription(persona)
                                ))
                            } else {
                                announceForAccessibility(resources.getString(
                                    R.string.people_picker_accessibility_deselected_persona,
                                    accessibilityTextProvider.getDefaultPersonaDescription(persona)
                                ))
                            }
                        }
                        sendEventForVirtualView(personaSpanIndex, AccessibilityEvent.TYPE_VIEW_CLICKED)
                        if (personaChipClickStyle == PeoplePickerPersonaChipClickStyle.SELECT && personaSpanIndex == -1)
                            invalidateRoot()
                    }
                }
                PeoplePickerPersonaChipClickStyle.DELETE -> {
                    sendEventForVirtualView(personaSpanIndex, AccessibilityEvent.TYPE_VIEW_CLICKED)
                    sendEventForVirtualView(personaSpanIndex, AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED)
                }
                else -> {
                    throw IllegalStateException("Invalid persona chip click style")
                }
            }
        }

        private fun setPersonaSpanClickAction(personaSpan: TokenImageSpan, node: AccessibilityNodeInfoCompat) {
            if (personaChipClickStyle == PeoplePickerPersonaChipClickStyle.NONE)
                return

            val clickAction = AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                AccessibilityNodeInfoCompat.ACTION_CLICK,
                getActionText(personaSpan)
            )
            node.addAction(clickAction)
        }

        /**
         * Sets text for the custom click action depending on persona chip click style and selection state.
         */
        private fun getActionText(personaSpan: TokenImageSpan): String {
            return if (personaSpan.token == selectedPersona) {
                when (personaChipClickStyle) {
                    PeoplePickerPersonaChipClickStyle.SELECT ->
                        resources.getString(R.string.people_picker_accessibility_delete_persona)
                    PeoplePickerPersonaChipClickStyle.SELECT_DESELECT ->
                        if (personaChipClickListener != null)
                            resources.getString(R.string.people_picker_accessibility_click_persona)
                        else
                            resources.getString(R.string.people_picker_accessibility_deselect_persona)
                    else -> ""
                }
            } else {
                when (personaChipClickStyle) {
                    PeoplePickerPersonaChipClickStyle.SELECT, PeoplePickerPersonaChipClickStyle.SELECT_DESELECT ->
                        resources.getString(R.string.people_picker_accessibility_select_persona)
                    PeoplePickerPersonaChipClickStyle.DELETE ->
                        resources.getString(R.string.people_picker_accessibility_delete_persona)
                    else -> ""
                }
            }
        }

        /**
         * Describes the action that will happen when already selected personas are activated.
         * We can't set a second action for the the virtual view, so we describe it after the first event occurs.
         */
        private fun getSelectedActionText(personaSpan: TokenImageSpan?): String {
            if (personaSpan == null || personaSpan.token != selectedPersona)
                return ""

            return when (personaChipClickStyle) {
                PeoplePickerPersonaChipClickStyle.SELECT ->
                    resources.getString(R.string.people_picker_accessibility_delete_selected_persona)
                PeoplePickerPersonaChipClickStyle.SELECT_DESELECT ->
                    if (personaChipClickListener != null)
                        resources.getString(R.string.people_picker_accessibility_click_selected_persona)
                    else
                        resources.getString(R.string.people_picker_accessibility_deselect_selected_persona)
                else -> ""
            }
        }
    }
}