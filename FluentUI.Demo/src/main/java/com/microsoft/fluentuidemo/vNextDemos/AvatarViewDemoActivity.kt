package com.microsoft.fluentuidemo.vNextDemos

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.widget.ThemeUtils
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.bumptech.glide.Glide
import com.microsoft.fluentui.generator.AvatarSize
import com.microsoft.fluentui.generator.AvatarStyle
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.vNext.persona.Avatar
import com.microsoft.fluentui.vNext.persona.AvatarPresence
import com.microsoft.fluentui.vNext.persona.AvatarView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_avatar_view_v_next.*

class AvatarViewDemoActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_avatar_view_v_next

    private var isOOF: Boolean = false
    private var showPresence: Boolean = false
    private var isTransparent: Boolean = false
    private var showRings: Boolean = false
    private var addedViews: ArrayList<View> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Avatar drawables with bitmap
        change_background.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                background_view.setBackgroundColor(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.fluentui_gray_200
                    )
                )
            } else {
                background_view.setBackgroundColor(ThemeUtil.getColor(FluentUIContextThemeWrapper(baseContext), R.attr.fluentuiBackgroundColor))
            }
        }
        is_oof.setOnCheckedChangeListener { buttonView, isChecked ->
            isOOF = isChecked
            removeAndAddViews()
        }
        show_presence.setOnCheckedChangeListener { buttonView, isChecked ->
            showPresence = isChecked
            removeAndAddViews()
        }
        use_transparency.setOnCheckedChangeListener { buttonView, isChecked ->
            isTransparent = isChecked
            removeAndAddViews()
        }
        show_rings.setOnCheckedChangeListener { buttonView, isChecked ->
            showRings = isChecked
            removeAndAddViews()
        }
        removeAndAddViews()
    }

    private fun removeAndAddViews() {
        for (view in addedViews){
            background_view.removeView(view)
        }
        addLayout(getString(R.string.avatar_default_style), AvatarStyle.BASE, AvatarStyle.BASE)
        addLayout(
            getString(R.string.avatar_fallback_accent_style),
            AvatarStyle.ACCENT,
            AvatarStyle.BASE,
            "+945249393",
            -1
        )
        addLayout(
            getString(R.string.avatar_outline_style),
            AvatarStyle.OUTLINED,
            AvatarStyle.OUTLINED_PRIMARY,
            "+945249393",
            -1
        )
        addLayout(getString(R.string.avatar_group_style), AvatarStyle.GROUP, AvatarStyle.GROUP)
        addLayout(getString(R.string.avatar_overview_style), AvatarStyle.OVERFLOW, AvatarStyle.OVERFLOW, "20", -1)
    }

    private fun addLayout(
        styleName: String, style1: AvatarStyle, style2: AvatarStyle, name: String = getString(
            R.string.persona_name_kat_larsson
        ), drawable: Int = R.drawable.avatar_kat_larsson
    ) {
        val textView = TextView(FluentUIContextThemeWrapper(baseContext)).apply {
            text = styleName
            textSize = 20f
            TextViewCompat.setTextAppearance(this, R.style.FluentUIDemo_ListItemSubHeaderTitle)
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 20
                bottomMargin = 20
            }
        }
        background_view.addView(textView)
        addedViews.add(textView)
        val tableLayout = TableLayout(baseContext).apply { orientation = LinearLayout.VERTICAL }
        background_view.addView(tableLayout)
        addedViews.add(tableLayout)
        for (size in AvatarSize.values().reversedArray()) {
            val tableRow = TableRow(baseContext)
            tableRow.addView(TextView(FluentUIContextThemeWrapper(baseContext)).apply {
                text = size.name
                TextViewCompat.setTextAppearance(this, R.style.FluentUIDemo_ListItemSubHeaderTitle)
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT).apply {
                    weight = 1f
                    gravity = Gravity.CENTER
                }
            })
            tableRow.addView(createAvatarView(name, drawable, size, style1))
            tableRow.addView(createAvatarView(name, -1, size, style2))
            tableLayout.addView(tableRow)
        }
    }

    private fun createAvatarView(
        name: String,
        resourceId: Int,
        size: AvatarSize,
        style: AvatarStyle
    ): AvatarView {
        return AvatarView(baseContext).apply {
            layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT).apply {
                weight = 1f
                gravity = Gravity.CENTER
            }
            avatar = Avatar()
            avatar.name = name
            avatar.avatarSize = size
            avatar.avatarStyle = style
            avatar.avatarImageResourceId = resourceId
            avatar.isRingVisible = showRings
            avatar.presence = getPresence()
            avatar.isOOF = isOOF
            avatar.avatarForegroundColor = null
            avatar.isTransparent = isTransparent
        }

    }

    private var presenceIterator = AvatarPresence.values().iterator()
    private fun getPresence(): AvatarPresence {
        if (!showPresence) return AvatarPresence.NONE
        else if (presenceIterator.hasNext()) {
            val presence = presenceIterator.next()
            if (presence == AvatarPresence.NONE) {
                return getPresence()
            } else {
                return presence
            }
        }
        else {
            presenceIterator = AvatarPresence.values().iterator()
            return getPresence()
        }
    }

    private fun loadBitmapFromPicasso(imageView: AvatarView) {
        Picasso.get()
                .load(R.drawable.avatar_celeste_burton)
                .into(imageView)
    }

    private fun loadBitmapFromGlide(imageView: AvatarView) {
        Glide.with(this)
                .load(R.drawable.avatar_isaac_fielder)
                .into(imageView)
    }

    private fun createNewAvatarFromCode() {
    }
}