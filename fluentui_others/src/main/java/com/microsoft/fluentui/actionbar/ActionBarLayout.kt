package com.microsoft.fluentui.actionbar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.view.TemplateView


class ActionBarLayout @JvmOverloads constructor(appContext: Context, attrs: AttributeSet? = null) : TemplateView(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Components), attrs) {
    companion object {
        private val DEFAULT_TYPE = Type.BASIC
    }

    enum class Type {
        BASIC, ICON, CAROUSEL
    }

    override val templateId: Int
        get() = R.layout.view_action_bar

    private lateinit var viewPager: ViewPager
    private var finalPageString: String
    private var currentPosition: Int = 0
    private var itemCount: Int = 0
    private var viewPagerAttr: Int = -1
    private var typeAttr = DEFAULT_TYPE
    private var rightAction: () -> Unit = { viewPager.currentItem = ++currentPosition }
    private var leftAction:  () -> Unit = { viewPager.currentItem = itemCount - 1 }
    private var launchMainScreen: () -> Unit = fun() {}
    private var viewpagerAccessibilityDelegate: AccessibilityDelegate = defaultViewpagerAccessibilityDelegate()
    private lateinit var rightActionIcon: ImageView
    private lateinit var rightActionText: TextView
    private lateinit var rightActionContainer: View
    private lateinit var leftActionText: TextView
    private lateinit var actionBarCarousel: IndicatorView
    private lateinit var actionBarCarouselLayout: View

    init {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ActionBarLayout)
        viewPagerAttr = styledAttributes.getResourceId(R.styleable.ActionBarLayout_viewPager, View.NO_ID)
        typeAttr = Type.values()[styledAttributes.getInt(R.styleable.ActionBarLayout_type, DEFAULT_TYPE.ordinal)]
        styledAttributes.recycle()
        finalPageString = context.getString(R.string.action_bar_default_final_action)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateMode()
        try {
            if (viewPagerAttr != NO_ID) {
                setPager((parent as View).findViewById(viewPagerAttr))
            }
        } catch (e: IllegalStateException) {
        }
    }

    override fun onTemplateLoaded() {
        super.onTemplateLoaded()
        rightActionIcon = findViewInTemplateById(R.id.action_bar_right_icon)!!
        rightActionText = findViewInTemplateById(R.id.action_bar_right_text)!!
        rightActionContainer = findViewInTemplateById(R.id.action_bar_right_action)!!
        leftActionText = findViewInTemplateById(R.id.action_bar_left_action)!!
        actionBarCarousel = findViewInTemplateById(R.id.action_bar_carousel)!!
        actionBarCarouselLayout = findViewInTemplateById(R.id.action_bar_container_layout)!!
    }

    /**
     * This function is used to update the visibility of various componenets of ActionBarLayout based on layout mode set by the user.
     */
    private fun updateMode() = when (typeAttr) {
        Type.BASIC -> {
            setViewVisibility(rightActionIcon, View.GONE)
            setViewVisibility(actionBarCarousel, View.GONE)
        }
        Type.ICON -> {
            setViewVisibility(actionBarCarousel, View.GONE)
        }
        Type.CAROUSEL -> {
            carouselMode()
            setViewVisibility(rightActionText, View.GONE)
        }
    }

    /**
     * This function is used to make changes specific to carousel mode of ActionBarLayout.
     */
    private fun carouselMode() {
        rightActionIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ms_ic_arrow_left_24_filled))
        rightActionIcon.rotation = 180f
        rightActionIcon.contentDescription = resources.getString(R.string.action_bar_right_icon_description)
        rightActionIcon.setColorFilter(ContextCompat.getColor(context, R.color.fluentui_white))
        leftActionText.setTextColor(ContextCompat.getColor(context, R.color.fluentui_white))
        actionBarCarouselLayout.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.fluentui_action_bar_carousel_background))
    }

    /**
     * This function is used to set the viewPager associated with the ActionBarLayout.
     * @param viewPager the [ViewPager] to be associated with the ActionBar. The ViewPager should have PagerAdapter set, else this method throws [NoAdapterFoundError].
     * @throws [NoAdapterFoundError] when adapter is not set for the associated [ViewPager].
     */
    fun setPager(viewPager: ViewPager) {
        this.viewPager = viewPager
        try {
            this.itemCount = viewPager.adapter!!.count
        } catch (e: KotlinNullPointerException) {
            throw NoAdapterFoundError("No Adapter found for the current view pager")
        }
        viewPager.setAccessibilityDelegate(viewpagerAccessibilityDelegate)
        actionBarCarousel.setItemCount(itemCount)
        actionBarCarousel.setCurrentPosition(0)

        updateButtons()
    }

    /**
     * This function updates the actions for Left and Right buttons on ActionBar. It also sets the [ViewPager.OnPageChangeListener] for the ViewPager
     * associated with the ActionBarLayout.
     */
    private fun updateButtons() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                actionBarCarousel.onPageScrolled(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
                if (currentPosition < itemCount - 1) {
                    setViewVisibility(leftActionText, View.VISIBLE)
                    setRightActionText(context.getString(R.string.action_bar_default_right_action))
                    resetActions()
                } else{
                    setViewVisibility(leftActionText, View.INVISIBLE)
                    setRightActionText(finalPageString)
                    updateRightAction(launchMainScreen)
                }
                announcePageForAccessibility()
            }
        })
        resetActions()
    }

    private fun announcePageForAccessibility() {
        announceForAccessibility(resources.getString(R.string.action_bar_accessibility_page_announcement, currentPosition+1, itemCount))
    }

    /**
     * This function is used to change the visibility of a given view. It used for modifying the layout as per the mode set.
     */
    private fun setViewVisibility(view: View, visibility: Int) {
        view.visibility = visibility
    }

    /**
     * This function is used to reset the actions. It is used to set actions for the first time and whenever the user moves to a prior page than last.
     */
    private fun resetActions() {
        updateRightAction(rightAction)
        updateLeftAction(leftAction)
    }

    /**
     * Change the dimensions of the carousel indicator.
     * @param size the size of the indicator dots.
     * @param length the length of the indicator.
     * @param padding the padding between each indicator dots.
     */
    fun customizeIndicatorDimensions(size: Int, length: Float, padding: Float) = actionBarCarousel.customizeIndicatorDimens(size, length, padding)

    /**
     * This function is used to set/change the text for the left action button.
     * @param text String to be set.
     */
    fun setLeftActionText(text: String) {
        leftActionText.text = text
    }

    /**
     * This function is used to set/change the text for the right action button.
     * @param text String to be set.
     */
    fun setRightActionText(text: String) {
        rightActionText.text = text
    }

    /**
     * This function updates the onClickListener for the left action button.
     */
    private fun updateLeftAction(onClickListener: () -> Unit) {
        leftActionText.setOnClickListener { onClickListener() }
    }

    /**
     * This function updates the onClickListener for the right action button.
     */
    private fun updateRightAction(onClickListener: () -> Unit) {
        rightActionContainer.setOnClickListener { onClickListener() }
    }

    /**
     * This function is used to set the left action.
     * @param func is the lamda function definition for the left action.
     */
    fun setLeftAction(func: () -> Unit) {
        this.leftAction = func
        resetActions()
    }

    /**
     * This function is used to set the right action.
     * @param func is the lambda function definition for the right action.
     */
    fun setRightAction(func: () -> Unit) {
        this.rightAction = func
        resetActions()
    }

    /**
     * This function is used to set the final action when on last screen of viewpager.
     * @param func is the lambda function definition for the final action.
     */
    fun setLaunchMainScreen(func: () -> Unit) {
        this.launchMainScreen = func
    }

    /**
     * This function is used to set layout mode programmatically
     * @param mode can take the values [Type.BASIC], [Type.CAROUSEL] or [Type.ICON].
     */
    fun setMode(mode: Int) {
        this.typeAttr = Type.values()[mode]
        updateMode()
    }

    /**
     * This error is thrown when the Adapter is not set for the given ViewPager.
     */
    class NoAdapterFoundError(message: String) : Exception(message)

    // Accessibility
    private inner class defaultViewpagerAccessibilityDelegate() : AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(host: View?, info: AccessibilityNodeInfo?) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            info?.contentDescription = resources.getString(R.string.action_bar_accessibility_viewpager_description, currentPosition+1, itemCount)
        }
    }

    /**
     * This function is used to set viewpagerAccessibilityDelegate programmatically
     */
    fun setViewpagerAccessibilityDelegate(accessibilityDelegate: AccessibilityDelegate) {
        this.viewpagerAccessibilityDelegate = accessibilityDelegate
    }
}