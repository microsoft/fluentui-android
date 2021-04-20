package com.microsoft.fluentui.persona

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.microsoft.fluentui.R
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper


enum class AvatarGroupStyle {
    STACK, PILE
}

open class AvatarGroupView : FrameLayout {
    companion object {
        internal val DEFAULT_AVATAR_GROUP_STYLE = AvatarGroupStyle.STACK
        internal const val DEFAULT_AVATAR_ALLOWED = 4
    }

    @JvmOverloads
    constructor(appContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Components), attrs, defStyleAttr) {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.AvatarGroupView)
        val avatarSizeOrdinal = styledAttrs.getInt(R.styleable.AvatarGroupView_avatarSize, AvatarView.DEFAULT_AVATAR_SIZE.ordinal)
        val avatarGroupStyleOrdinal = styledAttrs.getInt(R.styleable.AvatarGroupView_avatarGroupStyle, DEFAULT_AVATAR_GROUP_STYLE.ordinal)

        avatarSize = AvatarSize.values()[avatarSizeOrdinal]
        avatarGroupStyle = AvatarGroupStyle.values()[avatarGroupStyleOrdinal]
        maxDisplayedAvatars = styledAttrs.getInt(R.styleable.AvatarGroupView_maxDisplayedAvatars, DEFAULT_AVATAR_ALLOWED)
        styledAttrs.recycle()
    }

    private var avatarList: List<IAvatar> = ArrayList()
    private val clickListener = OnClickListener {
        listener?.onAvatarClicked(it.tag as Int)
    }

    var maxDisplayedAvatars: Int = DEFAULT_AVATAR_ALLOWED
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
     * Defines the [AvatarSize] applied to the avatar's height and width.
     */
    var avatarSize: AvatarSize = AvatarView.DEFAULT_AVATAR_SIZE
        set(value) {
            if (field == value)
                return

            field = value
            updateView()
        }

    fun setAvatars(avatarList: List<IAvatar>) {
        this.avatarList = avatarList
        updateView()
    }

    private fun updateView() {
        removeAllViews()
        for ((index, avatar) in avatarList.withIndex()) {
            if (index >= maxDisplayedAvatars) {
                if (avatarList.size > maxDisplayedAvatars) {
                    addOverFlowView(avatarList.size - maxDisplayedAvatars)
                }
                return
            }
            val avatarView = AvatarView(context)
            avatarView.setAvatar(avatar)
            avatarView.avatarSize = avatarSize
            avatarView.avatarStyle = AvatarStyle.CIRCLE
            avatarView.id = View.generateViewId()
            avatarView.tag = index
            avatarView.setOnClickListener(clickListener)
            val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            when(avatarGroupStyle) {
                AvatarGroupStyle.PILE -> {
                    avatarView.avatarBorderStyle = AvatarBorderStyle.NO_BORDER
                    layoutParams.marginStart = index * (avatarView.getViewSize() + getPileSpacing())
                }
                AvatarGroupStyle.STACK -> {
                    avatarView.avatarBorderStyle = AvatarBorderStyle.RING
                    layoutParams.marginStart = index * (avatarView.getViewSize()/2 + getStackSpacing())
                }
            }
            avatarView.layoutParams = layoutParams
            addView(avatarView)
        }
    }

    private fun addOverFlowView(overflowCount: Int) {
        val avatarView = AvatarView(context)
        avatarView.name = overflowCount.toString()
        avatarView.avatarBackgroundColor = ContextCompat.getColor(context, R.color.fluentui_avatar_overflow_background)
        avatarView.avatarSize = avatarSize
        avatarView.avatarIsOverFlow = true
        avatarView.avatarStyle = AvatarStyle.CIRCLE
        avatarView.id = View.generateViewId()
        avatarView.setOnClickListener {
            listener?.onOverFlowClicked()
        }
        val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        when (avatarGroupStyle) {
            AvatarGroupStyle.STACK -> {
                avatarView.avatarBorderStyle = AvatarBorderStyle.RING
                layoutParams.marginStart = (avatarList.size - overflowCount) * (avatarView.getViewSize() / 2 + getStackSpacing())
            }
            AvatarGroupStyle.PILE -> {
                avatarView.avatarBorderStyle = AvatarBorderStyle.NO_BORDER
                layoutParams.marginStart = (avatarList.size - overflowCount) * (avatarView.getViewSize() + getPileSpacing())
            }
        }
        avatarView.layoutParams = layoutParams
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