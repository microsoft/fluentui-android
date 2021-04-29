package com.microsoft.fluentuidemo.vNextDemos.list

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.microsoft.fluentui.generator.AvatarSize
import com.microsoft.fluentui.generator.ListHeaderStyle
import com.microsoft.fluentui.generator.ListLeadingViewSize
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.vNext.Button
import com.microsoft.fluentui.vNext.listitem.IBaseListItem
import com.microsoft.fluentui.vNext.listitem.ListItem
import com.microsoft.fluentui.vNext.listitem.ListSubHeaderItem
import com.microsoft.fluentui.vNext.persona.AvatarView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_list_item_view.*


class ListDemoActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_list_item_view

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app_bar.scrollTargetViewId = R.id.list_example

        val listAdapter = ListAdapter()
        listAdapter.items = createList()

        list_example.adapter = listAdapter
        //list_example.addItemDecoration(ListItemDivider(this, DividerItemDecoration.VERTICAL))
    }

    private fun createList(): ArrayList<IBaseListItem> {
        val smallIcon = ContextCompat.getDrawable(this, R.drawable.ic_info_24_regular)
        val mediumIcon = ContextCompat.getDrawable(this, R.drawable.ic_folder_24_regular)
        val overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_more_vertical_24_filled)

        // Single-line list example

        return arrayListOf(
                createListSubHeader(getString(R.string.list_header_no_custom_base_style)),
                createListItem(
                        getString(R.string.list_item_title)
                ),
                createListItem(
                        getString(R.string.list_item_title),
                        customAccessoryView = createExampleCustomView(overflowIcon),
                        addCustomAccessoryViewClick = true
                ),
                createListItem(
                        getString(R.string.list_item_custom_title),
                        titleStyle = R.style.TextAppearance_DemoTitle,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_title),
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_custom_padding),
                        customAccessoryView = createExampleTextViewWithPadding()
                ),
                createListItem(
                        getString(R.string.list_item_custom_button),
                        customAccessoryView = createExampleButton()
                ),

                // Small CustomView
                createListSubHeader(getString(R.string.list_header_custom_view_accent_style), ListHeaderStyle.ACCENT),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleCustomView(smallIcon),
                        customViewSize = ListLeadingViewSize.SMALL

                ),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson, avatarSize = AvatarSize.X_SMALL),
                        customViewSize = ListLeadingViewSize.SMALL,
                        customAccessoryView = createExampleCustomView(overflowIcon),
                        addCustomAccessoryViewClick = true
                ),
                createListItem(
                        getString(R.string.list_item_custom_title),
                        customView = createExampleCustomView(smallIcon),
                        customViewSize = ListLeadingViewSize.SMALL,
                        titleStyle = R.style.TextAppearance_DemoTitle,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson, avatarSize = AvatarSize.X_SMALL),
                        customViewSize = ListLeadingViewSize.SMALL,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_custom_padding),
                        customView = createExampleCustomView(smallIcon),
                        customViewSize = ListLeadingViewSize.SMALL,
                        customAccessoryView = createExampleTextViewWithPadding()
                ),
                createListItem(
                        getString(R.string.list_item_custom_button),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson, avatarSize = AvatarSize.X_SMALL),
                        customViewSize = ListLeadingViewSize.SMALL,
                        customAccessoryView = createExampleButton()
                ),

                // Medium CustomView
                createListSubHeader(getString(R.string.list_header_medium_custom_view_action), actionText = getString(R.string.list_item_sub_header_custom_accessory_text)),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleCustomView(mediumIcon),
                        customViewSize = ListLeadingViewSize.MEDIUM

                ),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson),
                        customViewSize = ListLeadingViewSize.MEDIUM,
                        customAccessoryView = createExampleCustomView(overflowIcon),
                        addCustomAccessoryViewClick = true
                ),
                createListItem(
                        getString(R.string.list_item_custom_title),
                        customView = createExampleCustomView(mediumIcon),
                        customViewSize = ListLeadingViewSize.MEDIUM,
                        titleStyle = R.style.TextAppearance_DemoTitle,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson),
                        customViewSize = ListLeadingViewSize.MEDIUM,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_custom_padding),
                        customView = createExampleCustomView(mediumIcon),
                        customViewSize = ListLeadingViewSize.MEDIUM,
                        customAccessoryView = createExampleTextViewWithPadding()
                ),
                createListItem(
                        getString(R.string.list_item_custom_button),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson),
                        customViewSize = ListLeadingViewSize.MEDIUM,
                        customAccessoryView = createExampleButton()
                ),

                // Large CustomView
                createListSubHeader(getString(R.string.list_header_large_custom_view_custom), useCustomAccessoryView = true),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleCustomView(mediumIcon),
                        customViewSize = ListLeadingViewSize.LARGE

                ),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson, avatarSize = AvatarSize.MEDIUM),
                        customViewSize = ListLeadingViewSize.LARGE,
                        customAccessoryView = createExampleCustomView(overflowIcon),
                        addCustomAccessoryViewClick = true
                ),
                createListItem(
                        getString(R.string.list_item_custom_title),
                        customView = createExampleCustomView(mediumIcon),
                        customViewSize = ListLeadingViewSize.LARGE,
                        titleStyle = R.style.TextAppearance_DemoTitle,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson, avatarSize = AvatarSize.MEDIUM),
                        customViewSize = ListLeadingViewSize.LARGE,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_custom_padding),
                        customView = createExampleCustomView(mediumIcon),
                        customViewSize = ListLeadingViewSize.LARGE,
                        customAccessoryView = createExampleTextViewWithPadding()
                ),
                createListItem(
                        getString(R.string.list_item_custom_button),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson, avatarSize = AvatarSize.MEDIUM),
                        customViewSize = ListLeadingViewSize.LARGE,
                        customAccessoryView = createExampleButton()
                ),
                // Two Line
                createListSubHeader(getString(R.string.list_header_two_line_accent),ListHeaderStyle.ACCENT),
                createListItem(
                        getString(R.string.list_item_title),
                        customView = createExampleCustomView(smallIcon),
                        subtitle = getString(R.string.list_item_subtitle),
                        customViewSize = ListLeadingViewSize.SMALL

                ),
                createListItem(
                        getString(R.string.list_item_title),
                        subtitle = getString(R.string.list_item_subtitle),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson, avatarSize = AvatarSize.MEDIUM),
                        customViewSize = ListLeadingViewSize.LARGE,
                        customAccessoryView = createExampleCustomView(overflowIcon),
                        addCustomAccessoryViewClick = true
                ),
                createListItem(
                        getString(R.string.list_item_custom_title),
                        subtitle = getString(R.string.list_item_subtitle),
                        customView = createExampleCustomView(mediumIcon),
                        customViewSize = ListLeadingViewSize.MEDIUM,
                        titleStyle = R.style.TextAppearance_DemoTitle,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_title),
                        subtitle = getString(R.string.long_placeholder),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson),
                        customSecondarySubtitleView = createExampleCustomSecondarySubtitleView(),
                        customViewSize = ListLeadingViewSize.MEDIUM,
                        customAccessoryView = createExampleTextView()
                ),
                createListItem(
                        getString(R.string.list_item_custom_padding),
                        customView = createExampleCustomView(mediumIcon),
                        customViewSize = ListLeadingViewSize.LARGE,
                        customAccessoryView = createExampleTextViewWithPadding()
                ),
                createListItem(
                        getString(R.string.list_item_custom_button),
                        customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson, avatarSize = AvatarSize.MEDIUM),
                        customViewSize = ListLeadingViewSize.LARGE,
                        customAccessoryView = createExampleButton()
                )
        )
    }

    private fun createListSubHeader(
            text: String,
            titleStyle: ListHeaderStyle = ListHeaderStyle.BASE,
            actionText: String = "",
            useCustomAccessoryView: Boolean = false
    ): ListSubHeaderItem {
        val listSubHeader = ListSubHeaderItem()
        listSubHeader.title = text
        listSubHeader.headerStyle = titleStyle
        listSubHeader.actionText = actionText

        if (useCustomAccessoryView) {
            val newContext = FluentUIContextThemeWrapper(baseContext, R.style.Widget_FluentUI_secondarybuttontokensview_small)
            val textCustomAccessoryView = Button(newContext)
            textCustomAccessoryView.text = getString(R.string.list_item_sub_header_custom_accessory_text)
            textCustomAccessoryView.setOnClickListener {
                Snackbar.make(
                        it,
                        resources.getString(R.string.list_item_click_sub_header_custom_accessory_view),
                        Snackbar.LENGTH_SHORT
                ).show()
            }
            listSubHeader.customAccessoryView = textCustomAccessoryView
        }
        return listSubHeader
    }

    private fun createListItem(
            title: String,
            subtitle: String = "",
            footer: String = "",
            titleStyle: Int = -1,
            customView: View? = null,
            customViewSize: ListLeadingViewSize = ListLeadingViewSize.MEDIUM,
            customAccessoryView: View? = null,
            customSecondarySubtitleView: View? = null,
            addCustomAccessoryViewClick: Boolean = false,
            wrap: Boolean = false
    ): ListItem {
        val item = ListItem()
        item.title = title
        item.subtitle = subtitle
        item.footer = footer
        item.customAccessoryView = customAccessoryView
        item.customView = customView
        item.customViewSize = customViewSize
        item.titleStyleRes = titleStyle
        item.customSecondarySubtitleView = customSecondarySubtitleView

        if (wrap) {
            item.titleMaxLines = 4
            item.subtitleMaxLines = 4
            item.footerMaxLines = 4
        } else {
            item.titleTruncateAt = TextUtils.TruncateAt.MIDDLE
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            // In earlier APIs this crashes with an ArrayIndexOutOfBoundsException
                item.subtitleTruncateAt = TextUtils.TruncateAt.END
        }

        if (addCustomAccessoryViewClick && customAccessoryView != null)
            customAccessoryView.setOnClickListener {
                Snackbar.make(
                        customAccessoryView,
                        getString(R.string.list_item_click_custom_accessory_view),
                        Snackbar.LENGTH_SHORT
                ).show()
            }

        return item
    }

    private fun createExampleCustomView(drawable: Drawable?): ImageView {
        val imageView = ImageView(this)
        imageView.setImageDrawable(drawable)
        return imageView
    }

    private fun createExampleTextView(): TextView {
        val textCustomAccessoryView = TextView(this)
        TextViewCompat.setTextAppearance(textCustomAccessoryView, R.style.TextAppearance_ListItemValue)
        textCustomAccessoryView.text = getString(R.string.list_item_custom_text_view)
        return textCustomAccessoryView
    }

    private fun createExampleTextViewWithPadding(): TextView {
        val textCustomAccessoryView = TextView(this)
        TextViewCompat.setTextAppearance(textCustomAccessoryView, R.style.TextAppearance_ListItemValue)
        textCustomAccessoryView.text = getString(R.string.list_item_custom_text_view)
        textCustomAccessoryView.setPadding(40, 0, 40, 0)
        return textCustomAccessoryView
    }

    private fun createExampleButton(): TextView {

        val newContext = ContextThemeWrapper(baseContext, R.style.Widget_FluentUI_secondarybuttontokensview_small)
        val textCustomAccessoryView = TextView(newContext)
        textCustomAccessoryView.text = getString(R.string.list_item_sub_header_custom_accessory_text)
        return textCustomAccessoryView
    }

    private fun createExampleAvatarView(avatarImageResourceId: Int = -1, avatarNameStringId: Int = -1, avatarSize: AvatarSize = AvatarSize.SMALL): AvatarView {
        val avatarView = AvatarView(this)
        avatarView.avatar.apply {
            this.avatarSize = avatarSize
            this.avatarImageResourceId = avatarImageResourceId
            if (avatarNameStringId != -1)
                this.name = getString(avatarNameStringId)
        }
        return avatarView
    }

    private fun createExampleCustomSecondarySubtitleView(): LinearLayout {
        val dotTextView = TextView(this)
        dotTextView.text = " . "

        val secondarySubtitleTextView = TextView(this)
        TextViewCompat.setTextAppearance(secondarySubtitleTextView, R.style.TextAppearance_DemoListItemSubtitle)
        secondarySubtitleTextView.text = getString(R.string.list_item_secondary_subtitle)

        val linearLayout = LinearLayout(this)
        linearLayout.addView(dotTextView)
        linearLayout.addView(secondarySubtitleTextView)
        return linearLayout
    }
}