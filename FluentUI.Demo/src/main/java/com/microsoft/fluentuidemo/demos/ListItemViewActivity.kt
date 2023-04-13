/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.microsoft.fluentui.listitem.ListItemDivider
import com.microsoft.fluentui.listitem.ListItemView
import com.microsoft.fluentui.listitem.ListItemView.Companion.DEFAULT_CUSTOM_VIEW_SIZE
import com.microsoft.fluentui.listitem.ListItemView.Companion.DEFAULT_LAYOUT_DENSITY
import com.microsoft.fluentui.listitem.ListSubHeaderView
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityListItemViewBinding
import com.microsoft.fluentuidemo.demos.list.IBaseListItem
import com.microsoft.fluentuidemo.demos.list.ListAdapter
import com.microsoft.fluentuidemo.demos.list.ListItem
import com.microsoft.fluentuidemo.demos.list.ListSubHeader

class ListItemViewActivity : DemoActivity() {

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private lateinit var listBinding: ActivityListItemViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listBinding = ActivityListItemViewBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        demoBinding.appBar.scrollTargetViewId = R.id.list_example

        val listAdapter = ListAdapter(this)
        listAdapter.listItems = createList()

        listBinding.listExample.adapter = listAdapter
        listBinding.listExample.addItemDecoration(
            ListItemDivider(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun createList(): ArrayList<IBaseListItem> {
        val smallIcon = ContextCompat.getDrawable(this, R.drawable.ic_folder_24_regular)
        val overflowIcon = ContextCompat.getDrawable(this, R.drawable.ic_more_vertical_24_filled)

        // Single-line list example

        val singleLineSection = createSection(
            createListSubHeader(
                getString(R.string.list_item_sub_header_single_line),
                ListSubHeaderView.TitleColor.TERTIARY,
                true
            ),
            arrayListOf(
                createListItem(
                    getString(R.string.list_item_title),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    customAccessoryView = createExampleCustomView(overflowIcon),
                    addCustomAccessoryViewClick = true
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    customView = createExampleAvatarView(R.drawable.avatar_charlotte_waltson),
                    customViewSize = ListItemView.CustomViewSize.MEDIUM,
                    customAccessoryView = createExampleTextView()
                )
            )
        )

        // Two-line list examples

        val twoLineSection = createSection(
            createListSubHeader(getString(R.string.list_item_sub_header_two_line)),
            arrayListOf(
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    customAccessoryView = createExampleCustomView(overflowIcon),
                    addCustomAccessoryViewClick = true
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleAvatarView(R.drawable.avatar_erik_nason),
                    customViewSize = ListItemView.CustomViewSize.MEDIUM,
                    customAccessoryView = createExampleTextView()
                )
            )
        )

        val twoLineDenseSection = createSection(
            createListSubHeader(getString(R.string.list_item_sub_header_two_line_dense)),
            arrayListOf(
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    layoutDensity = ListItemView.LayoutDensity.COMPACT
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    customAccessoryView = createExampleCustomView(overflowIcon),
                    addCustomAccessoryViewClick = true,
                    layoutDensity = ListItemView.LayoutDensity.COMPACT
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleAvatarView(R.drawable.avatar_wanda_howard),
                    customViewSize = ListItemView.CustomViewSize.MEDIUM,
                    customAccessoryView = createExampleTextView(),
                    layoutDensity = ListItemView.LayoutDensity.COMPACT
                )
            )
        )

        val twoLineCustomSecondarySubtitleSection = createSection(
            createListSubHeader(getString(R.string.list_item_sub_header_two_line_custom_secondary_subtitle)),
            arrayListOf(
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    customSecondarySubtitleView = createExampleCustomSecondarySubtitleView(),
                    layoutDensity = ListItemView.LayoutDensity.COMPACT
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    customAccessoryView = createExampleCustomView(overflowIcon),
                    customSecondarySubtitleView = createExampleCustomSecondarySubtitleView(),
                    addCustomAccessoryViewClick = true,
                    layoutDensity = ListItemView.LayoutDensity.COMPACT
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleAvatarView(R.drawable.avatar_wanda_howard),
                    customViewSize = ListItemView.CustomViewSize.MEDIUM,
                    customAccessoryView = createExampleTextView(),
                    customSecondarySubtitleView = createExampleCustomSecondarySubtitleView(),
                    layoutDensity = ListItemView.LayoutDensity.COMPACT
                )
            )
        )

        // Three-line list example

        val threeLineSection = createSection(
            createListSubHeader(
                getString(R.string.list_item_sub_header_three_line),
                ListSubHeaderView.TitleColor.SECONDARY,
                true
            ),
            arrayListOf(
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    getString(R.string.list_item_footer),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    getString(R.string.list_item_footer),
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    customAccessoryView = createExampleCustomView(overflowIcon),
                    addCustomAccessoryViewClick = true
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    getString(R.string.list_item_footer),
                    customView = createExampleAvatarView(R.drawable.avatar_carole_poland),
                    customViewSize = ListItemView.CustomViewSize.MEDIUM,
                    customAccessoryView = createExampleTextView()
                )
            )
        )


        // Layout variant examples

        val noCustomViewSection = createSection(
            createListSubHeader(getString(R.string.list_item_sub_header_no_custom_views)),
            arrayListOf(
                createListItem(getString(R.string.list_item_title)),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle)
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    getString(R.string.list_item_footer)
                )
            )
        )

        val largeHeaderSection = createSection(
            createListSubHeader(getString(R.string.list_item_sub_header_large_header)),
            arrayListOf(
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    getString(R.string.list_item_footer),
                    customView = createExampleAvatarView(
                        R.drawable.avatar_johnie_mcconnell,
                        avatarSize = AvatarSize.XXLARGE
                    ),
                    customViewSize = ListItemView.CustomViewSize.LARGE
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    footer = getString(R.string.list_item_footer),
                    customView = createExampleAvatarView(
                        avatarNameStringId = R.string.persona_name_elliot_woodward,
                        avatarSize = AvatarSize.XXLARGE
                    ),
                    customViewSize = ListItemView.CustomViewSize.LARGE
                ),
                createListItem(
                    getString(R.string.list_item_title),
                    getString(R.string.list_item_subtitle),
                    customView = createExampleAvatarView(
                        R.drawable.avatar_miguel_garcia,
                        avatarSize = AvatarSize.XXLARGE
                    ),
                    customViewSize = ListItemView.CustomViewSize.LARGE
                )
            )
        )

        val truncationSection = createSection(
            createListSubHeader(getString(R.string.list_item_sub_header_truncated_text)),
            arrayListOf(
                createListItem(
                    "${getString(R.string.list_item_truncation_middle)} ${getString(R.string.long_placeholder)}",
                    "${getString(R.string.long_placeholder)} ${getString(R.string.list_item_truncation_start)}",
                    "${getString(R.string.list_item_truncation_end)} ${getString(R.string.long_placeholder)}",
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL
                ),
                createListItem(
                    "${getString(R.string.list_item_truncation_middle)} ${getString(R.string.long_placeholder)}",
                    "${getString(R.string.long_placeholder)} ${getString(R.string.list_item_truncation_start)}",
                    "${getString(R.string.list_item_truncation_end)} ${getString(R.string.long_placeholder)}",
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    customAccessoryView = createExampleCustomView(overflowIcon),
                    addCustomAccessoryViewClick = true
                ),
                createListItem(
                    "${getString(R.string.list_item_truncation_middle)} ${getString(R.string.long_placeholder)}",
                    "${getString(R.string.long_placeholder)} ${getString(R.string.list_item_truncation_start)}",
                    "${getString(R.string.list_item_truncation_end)} ${getString(R.string.long_placeholder)}",
                    customView = createExampleAvatarView(R.drawable.avatar_robert_tolbert),
                    customViewSize = ListItemView.CustomViewSize.MEDIUM,
                    customAccessoryView = createExampleTextView()
                )
            )
        )

        val wrappingSection = createSection(
            createListSubHeader(getString(R.string.list_item_sub_header_wrapped_text)),
            arrayListOf(
                createListItem(
                    getString(R.string.long_placeholder),
                    wrap = true,
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL
                ),
                createListItem(
                    getString(R.string.long_placeholder),
                    getString(R.string.long_placeholder),
                    wrap = true,
                    customView = createExampleCustomView(smallIcon),
                    customViewSize = ListItemView.CustomViewSize.SMALL,
                    customAccessoryView = createExampleCustomView(overflowIcon),
                    addCustomAccessoryViewClick = true
                ),
                createListItem(
                    getString(R.string.long_placeholder),
                    getString(R.string.long_placeholder),
                    getString(R.string.long_placeholder),
                    wrap = true,
                    customView = createExampleAvatarView(avatarNameStringId = R.string.persona_name_henry_brill),
                    customViewSize = ListItemView.CustomViewSize.MEDIUM,
                    customAccessoryView = createExampleTextView()
                )
            )
        )

        val twoLineListSections =
            twoLineSection + twoLineDenseSection + twoLineCustomSecondarySubtitleSection
        val layoutVariantSections =
            noCustomViewSection + largeHeaderSection + truncationSection + wrappingSection
        return (singleLineSection + twoLineListSections + threeLineSection + layoutVariantSections) as ArrayList<IBaseListItem>
    }

    private fun createSection(
        subHeader: ListSubHeader,
        items: ArrayList<ListItem>
    ): ArrayList<IBaseListItem> {
        val itemArray = arrayListOf(subHeader) as ArrayList<IBaseListItem>
        itemArray.addAll(items)
        return itemArray
    }

    private fun createListSubHeader(
        text: String,
        titleColor: ListSubHeaderView.TitleColor = ListSubHeaderView.DEFAULT_TITLE_COLOR,
        useCustomAccessoryView: Boolean = false
    ): ListSubHeader {
        val listSubHeader = ListSubHeader(text)
        listSubHeader.titleColor = titleColor

        if (useCustomAccessoryView) {
            val customTextView = TextView(this)
            customTextView.text = getString(R.string.list_item_sub_header_custom_accessory_text)
            TextViewCompat.setTextAppearance(
                customTextView,
                R.style.FluentUIDemo_ListItemSubHeaderTitle
            )
            customTextView.setOnClickListener {
                Snackbar.make(
                    it,
                    resources.getString(R.string.list_item_click_sub_header_custom_accessory_view),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            listSubHeader.customAccessoryView = customTextView
        }

        return listSubHeader
    }

    private fun createListItem(
        title: String,
        subtitle: String = "",
        footer: String = "",
        customView: View? = null,
        customViewSize: ListItemView.CustomViewSize = DEFAULT_CUSTOM_VIEW_SIZE,
        customAccessoryView: View? = null,
        customSecondarySubtitleView: View? = null,
        addCustomAccessoryViewClick: Boolean = false,
        layoutDensity: ListItemView.LayoutDensity = DEFAULT_LAYOUT_DENSITY,
        wrap: Boolean = false
    ): ListItem {
        val item = ListItem(title)

        item.subtitle = subtitle
        item.footer = footer
        item.layoutDensity = layoutDensity
        item.customAccessoryView = customAccessoryView
        item.customView = customView
        item.customViewSize = customViewSize
        item.customSecondarySubtitleView = customSecondarySubtitleView

        if (wrap) {
            item.titleMaxLines = 4
            item.subtitleMaxLines = 4
            item.footerMaxLines = 4
        } else {
            item.titleTruncateAt = TextUtils.TruncateAt.MIDDLE
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP)
            // In earlier APIs this crashes with an ArrayIndexOutOfBoundsException
                item.subtitleTruncateAt = TextUtils.TruncateAt.START
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
        imageView.contentDescription = getString(R.string.list_item_more_options)
        return imageView
    }

    private fun createExampleTextView(): TextView {
        val textCustomAccessoryView = TextView(this)
        TextViewCompat.setTextAppearance(
            textCustomAccessoryView,
            R.style.TextAppearance_ListItemValue
        )
        textCustomAccessoryView.text = getString(R.string.list_item_custom_text_view)
        return textCustomAccessoryView
    }

    private fun createExampleAvatarView(
        avatarImageResourceId: Int = -1,
        avatarNameStringId: Int = -1,
        avatarSize: AvatarSize = AvatarSize.LARGE
    ): AvatarView {
        val avatarView = AvatarView(this)
        avatarView.avatarImageResourceId = avatarImageResourceId
        avatarView.avatarSize = avatarSize
        if (avatarNameStringId != -1)
            avatarView.name = getString(avatarNameStringId)
        return avatarView
    }

    private fun createExampleCustomSecondarySubtitleView(): LinearLayout {
        val dotTextView = TextView(this)
        dotTextView.text = " . "

        val secondarySubtitleTextView = TextView(this)
        TextViewCompat.setTextAppearance(
            secondarySubtitleTextView,
            R.style.TextAppearance_DemoListItemSubtitle
        )
        secondarySubtitleTextView.text = getString(R.string.list_item_secondary_subtitle)

        val linearLayout = LinearLayout(this)
        linearLayout.addView(dotTextView)
        linearLayout.addView(secondarySubtitleTextView)
        return linearLayout
    }
}