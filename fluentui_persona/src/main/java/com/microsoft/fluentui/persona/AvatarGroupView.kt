package com.microsoft.fluentui.persona

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper


enum class AvatarGroupStyle {
    STACK, PILE
}

open class AvatarGroupView : FrameLayout {
    companion object {
        internal val DEFAULT_AVATAR_GROUP_STYLE = AvatarGroupStyle.STACK
        internal val DEFAULT_AVATAR_BORDER_STYLE = AvatarBorderStyle.NO_BORDER
        internal const val DEFAULT_AVATAR_ALLOWED = 4
        internal const val DEFAULT_OVERFLOW_AVATAR = 0
    }

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        FluentUIContextThemeWrapper(appContext, R.style.Theme_FluentUI_Persona),
        attrs,
        defStyleAttr
    ) {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.AvatarGroupView)
        val avatarSizeOrdinal = styledAttrs.getInt(
            R.styleable.AvatarGroupView_fluentui_avatarSize,
            AvatarView.DEFAULT_AVATAR_SIZE.ordinal
        )
        val avatarGroupStyleOrdinal = styledAttrs.getInt(
            R.styleable.AvatarGroupView_fluentui_avatarGroupStyle,
            DEFAULT_AVATAR_GROUP_STYLE.ordinal
        )
        val avatarBorderStyleOrdinal = styledAttrs.getInt(
            R.styleable.AvatarGroupView_fluentui_avatarBorderStyle,
            DEFAULT_AVATAR_BORDER_STYLE.ordinal
        )

        avatarSize = AvatarSize.values()[avatarSizeOrdinal]
        avatarGroupStyle = AvatarGroupStyle.values()[avatarGroupStyleOrdinal]
        avatarBorderStyle = AvatarBorderStyle.values()[avatarBorderStyleOrdinal]
        maxDisplayedAvatars = styledAttrs.getInt(
            R.styleable.AvatarGroupView_fluentui_maxDisplayedAvatars,
            DEFAULT_AVATAR_ALLOWED
        )
        overflowAvatarCount = styledAttrs.getInt(
            R.styleable.AvatarGroupView_fluentui_overflowAvatarCount,
            DEFAULT_OVERFLOW_AVATAR
        )
        styledAttrs.recycle()
    }

    private var avatarList: List<IAvatar> = ArrayList()
    private var overflowAvatar: AvatarView? = null
    private val clickListener = OnClickListener {
        listener?.onAvatarClicked(it.tag as Int)
    }
    private val overflowClickListener = OnClickListener {
        listener?.onOverFlowClicked()
    }

    var maxDisplayedAvatars: Int = DEFAULT_AVATAR_ALLOWED
        set(value) {
            if (field == value)
                return

            field = value
            updateView()
        }

    /**
     * Defines the [overflowAvatarCount] applied to avatarGroup.
     * This property when set acts like a manual override and [maxDisplayedAvatar] is not honoured.
     */
    var overflowAvatarCount: Int = DEFAULT_OVERFLOW_AVATAR
        set(value) {
            if (field == value)
                return

            field = value
            updateView()
        }

    var listener: Listener? = null
        set(value) {
            if (field == value)
                return

            field = value
            for (child in 0 until childCount) {
                getChildAt(child).setOnClickListener(clickListener)
            }
            overflowAvatar?.setOnClickListener(overflowClickListener)
        }

    /**
     * Defines the [AvatarGroupStyle] applied to the avatar.
     */
    var avatarGroupStyle: AvatarGroupStyle = DEFAULT_AVATAR_GROUP_STYLE
        set(value) {
            if (field == value)
                return

            field = value
            updateView()
        }

    /**
     * Defines the [AvatarBorderStyle] applied to the avatar.
     */
    var avatarBorderStyle: AvatarBorderStyle = DEFAULT_AVATAR_BORDER_STYLE
        set(value) {
            if (field == value)
                return

            field = value
            updateView()
        }

    /**
     * Defines the [AvatarSize] applied to the avatar's height and width.
     */
    var avatarSize: AvatarSize = AvatarView.DEFAULT_AVATAR_SIZE
        set(value) {
            if (field == value)
                return

            field = value
            updateView()
        }

    /**
     * Defines the ContentDescription which will be applied to the overflow Avatar if it exists
     */
    private var overflowContentDescription: String = ""

    fun setAvatars(avatarList: List<IAvatar>) {
        this.avatarList = avatarList
        updateView()
    }

    /*
        Either we can pass "customString" or "formattedString %s",
        Please note only %s will work here. As we get the overflow icon's count from the name of
        Avatar
     */
    fun setOverflowContentDescription(formatterContentDescription: String) {
        overflowContentDescription = formatterContentDescription
        overflowAvatar?.apply {
            avatarContentDescriptionLabel = String.format(overflowContentDescription, name)
        }
    }

    private fun updateView() {
        removeAllViews()
        overflowAvatar = null
        for ((index, avatar) in avatarList.withIndex()) {
            if (index >= maxDisplayedAvatars && overflowAvatarCount <= 0) {
                if (avatarList.size > maxDisplayedAvatars) {
                    addOverFlowView(avatarList.size - maxDisplayedAvatars, index)
                }
                return
            }
            val avatarView = AvatarView(context)
            avatarView.setAvatar(avatar)
            avatarView.avatarSize = avatarSize
            avatarView.avatarStyle = AvatarStyle.CIRCLE
            avatarView.id = View.generateViewId()
            avatarView.tag = index
            if (listener != null) {
                avatarView.setOnClickListener(clickListener)
            }
            val layoutParams = LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            when (avatarGroupStyle) {
                AvatarGroupStyle.PILE -> {
                    avatarView.avatarBorderStyle = avatarBorderStyle
                    layoutParams.marginStart = index * (avatarView.getViewSize() + getPileSpacing())
                }
                AvatarGroupStyle.STACK -> {
                    if (avatarBorderStyle == AvatarBorderStyle.NO_BORDER)
                        avatarView.avatarBorderStyle = AvatarBorderStyle.SINGLE_RING
                    else
                        avatarView.avatarBorderStyle = AvatarBorderStyle.RING
                    layoutParams.marginStart =
                        index * (avatarView.getViewSize() / 2 + getStackSpacing())
                }
            }
            avatarView.layoutParams = layoutParams
            addView(avatarView)
        }
        if (overflowAvatarCount > 0) {
            addOverFlowView(overflowAvatarCount, avatarList.size)
            return
        }
    }

    private fun addOverFlowView(overflowCount: Int, indexToPlace: Int) {
        val avatarView = AvatarView(context)
        avatarView.name = overflowCount.toString()
        avatarView.avatarBackgroundColor =
            ContextCompat.getColor(context, R.color.fluentui_avatar_overflow_background)
        avatarView.avatarSize = avatarSize
        avatarView.avatarIsOverFlow = true
        avatarView.avatarStyle = AvatarStyle.CIRCLE
        avatarView.id = View.generateViewId()
        if (listener != null) {
            avatarView.setOnClickListener(overflowClickListener)
        }
        val layoutParams =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        when (avatarGroupStyle) {
            AvatarGroupStyle.STACK -> {
                if (avatarBorderStyle == AvatarBorderStyle.NO_BORDER)
                    avatarView.avatarBorderStyle = AvatarBorderStyle.SINGLE_RING
                else
                    avatarView.avatarBorderStyle = AvatarBorderStyle.RING
                layoutParams.marginStart =
                    indexToPlace * (avatarView.getViewSize() / 2 + getStackSpacing())
            }
            AvatarGroupStyle.PILE -> {
                avatarView.avatarBorderStyle = avatarBorderStyle
                layoutParams.marginStart =
                    indexToPlace * (avatarView.getViewSize() + getPileSpacing())
            }
        }
        avatarView.layoutParams = layoutParams
        overflowAvatar = avatarView
        setOverflowContentDescription(overflowContentDescription)
        addView(avatarView)
    }

    private fun getPileSpacing(): Int {
        return when (avatarSize) {
            AvatarSize.XSMALL -> context.resources.getDimension(R.dimen.fluentui_avatar_pile_space_xsmall)
            AvatarSize.SMALL -> context.resources.getDimension(R.dimen.fluentui_avatar_pile_space_small)
            AvatarSize.MEDIUM -> context.resources.getDimension(R.dimen.fluentui_avatar_pile_space_medium)
            AvatarSize.LARGE -> context.resources.getDimension(R.dimen.fluentui_avatar_pile_space_large)
            AvatarSize.XLARGE -> context.resources.getDimension(R.dimen.fluentui_avatar_pile_space_xlarge)
            AvatarSize.XXLARGE -> context.resources.getDimension(R.dimen.fluentui_avatar_pile_space_xxlarge)
        }.toInt()
    }

    private fun getStackSpacing(): Int {
        return when (avatarSize) {
            AvatarSize.XSMALL -> context.resources.getDimension(R.dimen.fluentui_avatar_stack_space_xsmall)
            AvatarSize.SMALL -> context.resources.getDimension(R.dimen.fluentui_avatar_stack_space_small)
            AvatarSize.MEDIUM -> context.resources.getDimension(R.dimen.fluentui_avatar_stack_space_medium)
            AvatarSize.LARGE -> context.resources.getDimension(R.dimen.fluentui_avatar_stack_space_large)
            AvatarSize.XLARGE -> context.resources.getDimension(R.dimen.fluentui_avatar_stack_space_xlarge)
            AvatarSize.XXLARGE -> context.resources.getDimension(R.dimen.fluentui_avatar_stack_space_xxlarge)
        }.toInt()
    }

    interface Listener {
        fun onAvatarClicked(index: Int)
        fun onOverFlowClicked()
    }
}