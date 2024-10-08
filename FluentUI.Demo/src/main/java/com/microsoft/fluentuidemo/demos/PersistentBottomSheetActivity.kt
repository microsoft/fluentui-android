/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.microsoft.fluentui.bottomsheet.BottomSheetAdapter
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import com.microsoft.fluentui.persistentbottomsheet.PersistentBottomSheet
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalItemAdapter
import com.microsoft.fluentui.persistentbottomsheet.SheetItem
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityPersistentBottomSheetBinding
import com.microsoft.fluentuidemo.databinding.DemoPersistentSheetContentBinding
import com.microsoft.fluentuidemo.util.createBitmapFromLayout

class PersistentBottomSheetActivity : DemoActivity(), SheetItem.OnClickListener,
    BottomSheetItem.OnClickListener {

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private lateinit var persistentBottomSheetDemo: PersistentBottomSheet
    private lateinit var defaultPersistentBottomSheet: PersistentBottomSheet
    private lateinit var defaultPersistentBottomSheetContent: View
    private lateinit var currentSheet: PersistentBottomSheet
    private lateinit var scrollView: ScrollView
    private var isBack: Boolean = false
    private lateinit var view: TextView
    private lateinit var mHorizontalSheet: MutableList<SheetItem>
    private lateinit var mHorizontalSheet2: MutableList<SheetItem>

    private lateinit var persistentBottomSheetBinding: ActivityPersistentBottomSheetBinding
    private lateinit var persistentSheetContentBinding: DemoPersistentSheetContentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        persistentBottomSheetBinding = ActivityPersistentBottomSheetBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        persistentSheetContentBinding = DemoPersistentSheetContentBinding.inflate(LayoutInflater.from(this))

        persistentBottomSheetDemo = findViewById(R.id.demo_persistent_sheet)
        defaultPersistentBottomSheet = findViewById(R.id.default_persistent_sheet)
        scrollView = findViewById(R.id.scroll_container)
        defaultPersistentBottomSheetContent =
            LayoutInflater.from(this).inflate(R.layout.demo_persistent_sheet_content, null)

        val view = LayoutInflater.from(this).inflate(R.layout.accessory_content, null)
        val textView = view.findViewById<TextView>(R.id.bottom_sheet_item_nudge)
        textView.text = "10+"
        val bitmap1 = createBitmapFromLayout(view)
        textView.text = "100+"
        val bitmap2 = createBitmapFromLayout(view)
        textView.text = "123456789+"
        val bitmap3 = createBitmapFromLayout(view)


        PersistentBottomSheet.DefaultContentBuilder(this)
            .setCustomSheetContent(persistentSheetContentBinding.root)
            .buildWith(persistentBottomSheetDemo)
        persistentBottomSheetDemo.setDrawerHandleContentDescription(
            getString(R.string.drawer_content_desc_collapse_state),
            getString(R.string.drawer_content_desc_expand_state)
        )
        persistentBottomSheetDemo.backgroundViews = listOf(persistentBottomSheetBinding.demoMainContent, persistentBottomSheetBinding.scrollContainer) // These views shouldn't get accessibility focus when Sheet is expanded

        mHorizontalSheet = arrayListOf(
            SheetItem(
                R.id.bottom_sheet_item_flag,
                getString(R.string.bottom_sheet_item_flag_title),
                R.drawable.ic_fluent_flag_24_regular,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = false,
                accessoryBitmap = bitmap1
            ),
            SheetItem(
                R.id.bottom_sheet_item_alarm,
                getString(R.string.bottom_sheet_item_custom_image),
                dummyBitmap(),
                disabled = true,
                accessoryBitmap = bitmap2
            ),
            SheetItem(
                R.id.persistent_sheet_item_add_view,
                getString(R.string.persistent_sheet_item_add_remove_view),
                R.drawable.ic_add_circle_28_fill,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = false,
                accessoryBitmap = bitmap3
            ),
            SheetItem(
                R.id.persistent_sheet_item_change_height_button,
                getString(R.string.persistent_sheet_item_change_collapsed_height),
                R.drawable.ic_vertical_align_center_28_fill,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = true
            ),
            SheetItem(
                R.id.bottom_sheet_item_reply,
                getString(R.string.bottom_sheet_item_reply_title),
                R.drawable.ic_fluent_reply_24_regular,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = false
            ),
            SheetItem(
                R.id.bottom_sheet_item_forward,
                getString(R.string.bottom_sheet_item_forward_title),
                R.drawable.ic_fluent_forward_24_regular,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = true
            ),
            SheetItem(
                R.id.bottom_sheet_item_delete,
                getString(R.string.bottom_sheet_item_delete_title),
                R.drawable.ic_delete_24_regular,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = false
            ),
            SheetItem(
                R.id.bottom_sheet_item_delete,
                getString(R.string.bottom_sheet_item_delete_title),
                R.drawable.ic_delete_24_regular,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = true
            ),
            SheetItem(
                R.id.bottom_sheet_item_delete,
                getString(R.string.bottom_sheet_item_delete_title),
                R.drawable.ic_delete_24_regular,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = false
            )
        )

        mHorizontalSheet2 = arrayListOf(
            SheetItem(
                R.id.bottom_sheet_item_flag,
                getString(R.string.bottom_sheet_item_flag_title),
                R.drawable.ic_fluent_flag_24_regular,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = true
            ),
            SheetItem(
                R.id.bottom_sheet_item_alarm,
                getString(R.string.bottom_sheet_item_custom_image),
                dummyBitmap(),
                disabled = false
            ),
            SheetItem(
                R.id.persistent_sheet_item_add_view,
                getString(R.string.persistent_sheet_item_add_remove_view),
                R.drawable.ic_add_circle_28_fill,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = true
            ),
            SheetItem(
                R.id.persistent_sheet_item_change_height_button,
                getString(R.string.persistent_sheet_item_change_collapsed_height),
                R.drawable.ic_vertical_align_center_28_fill,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = false
            ),
            SheetItem(
                R.id.bottom_sheet_item_reply,
                getString(R.string.bottom_sheet_item_reply_title),
                R.drawable.ic_fluent_reply_24_regular,
                ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                disabled = true
            )
        )


        persistentSheetContentBinding.sheetHorizontalItemList1.createHorizontalItemLayout(
            mHorizontalSheet
        )
        persistentSheetContentBinding.sheetHorizontalItemList1.sheetItemClickListener = this
        persistentSheetContentBinding.sheetHorizontalItemList1.setTextAppearance(R.style.TextAppearance_FluentUI_HorizontalListItemTitle)

        persistentSheetContentBinding.sheetHorizontalItemList2.createHorizontalItemLayout(
            mHorizontalSheet2
        )
        persistentSheetContentBinding.sheetHorizontalItemList2.sheetItemClickListener = this
        persistentSheetContentBinding.sheetHorizontalItemList2.setTextAppearance(R.style.TextAppearance_FluentUI_HorizontalListItemTitle)

        val marginBetweenView =
            resources.getDimension(R.dimen.fluentui_persistent_horizontal_item_right_margin).toInt()
        val horizontalListAdapter = SheetHorizontalItemAdapter(
            this,
            arrayListOf(
                SheetItem(
                    R.id.persistent_sheet_item_create_new_folder,
                    getString(R.string.persistent_sheet_item_create_new_folder_title),
                    R.drawable.ic_create_new_folder_24_filled,
                    ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                    disabled = true
                ),
                SheetItem(
                    R.id.persistent_sheet_item_edit,
                    getString(R.string.persistent_sheet_item_edit_title),
                    R.drawable.ic_edit_24_filled,
                    ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                    disabled = false
                ),
                SheetItem(
                    R.id.persistent_sheet_item_save,
                    getString(R.string.persistent_sheet_item_save_title),
                    R.drawable.ic_save_24_filled,
                    ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                    disabled = true
                ),
                SheetItem(
                    R.id.persistent_sheet_item_zoom_in,
                    getString(R.string.persistent_sheet_item_zoom_in_title),
                    R.drawable.ic_zoom_in_24_filled,
                    ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                ),
                SheetItem(
                    R.id.persistent_sheet_item_zoom_out,
                    getString(R.string.persistent_sheet_item_zoom_out_title),
                    R.drawable.ic_zoom_out_24_filled,
                    ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint),
                    disabled = true
                )
            ), 0, marginBetweenView, drawerTint = ContextCompat.getColor(this, R.color.bottomsheet_horizontal_icon_tint).toInt()
        )
        horizontalListAdapter.mOnSheetItemClickListener = this
        persistentSheetContentBinding.sheetHorizontalItemList3.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        persistentSheetContentBinding.sheetHorizontalItemList3.adapter = horizontalListAdapter


        val verticalListAdapter = BottomSheetAdapter(
            this,
            arrayListOf(
                BottomSheetItem(
                    R.id.bottom_sheet_item_camera,
                    R.drawable.ic_camera_24_regular,
                    getString(R.string.bottom_sheet_item_camera_title),
                    getString(R.string.bottom_sheet_item_camera_subtitle),
                    disabled = false,
                    accessoryBitmap = bitmap1
                ),
                BottomSheetItem(
                    R.id.bottom_sheet_item_gallery,
                    R.drawable.ic_image_library_24_regular,
                    getString(R.string.bottom_sheet_item_gallery_title),
                    getString(R.string.bottom_sheet_item_gallery_subtitle),
                    disabled = true,
                    accessoryBitmap = bitmap2
                ),
                BottomSheetItem(
                    R.id.bottom_sheet_item_videos,
                    R.drawable.ic_video_24_regular,
                    getString(R.string.bottom_sheet_item_videos_title),
                    getString(R.string.bottom_sheet_item_videos_subtitle),
                    accessoryBitmap = bitmap3
                ),
                BottomSheetItem(
                    R.id.bottom_sheet_item_manage,
                    R.drawable.ic_settings_24_regular,
                    getString(R.string.bottom_sheet_item_manage_title),
                    getString(R.string.bottom_sheet_item_manage_subtitle),
                    disabled = true
                )
            ), 0
        )
        verticalListAdapter.onBottomSheetItemClickListener = this
        persistentSheetContentBinding.sheetVerticalItemList1.adapter = verticalListAdapter

        persistentBottomSheetBinding.showPersistentBottomSheetButton.setOnClickListener {
            currentSheet.expand(focusDrawerHandle = true)
        }

        persistentBottomSheetBinding.collapsePersistentBottomSheetButton.setOnClickListener {
            if (currentSheet.getBottomSheetBehaviour().state == BottomSheetBehavior.STATE_HIDDEN) {
                currentSheet.show(focusDrawerHandle = true)
                persistentBottomSheetBinding.collapsePersistentBottomSheetButton.text =
                    getString(R.string.collapse_persistent_sheet_button)
            } else {
                currentSheet.hide()
                persistentBottomSheetBinding.collapsePersistentBottomSheetButton.text =
                    getString(R.string.show_persistent_sheet_button)
            }
        }

        persistentBottomSheetBinding.toggleBottomSheet.setOnClickListener {
            if (defaultPersistentBottomSheet.visibility == View.GONE) {
                currentSheet = defaultPersistentBottomSheet
                persistentBottomSheetDemo.visibility = View.GONE
                persistentBottomSheetBinding.setOneLineContent.visibility = View.VISIBLE
                showDefaultBottomSheet()
            } else {
                currentSheet = persistentBottomSheetDemo
                defaultPersistentBottomSheet.visibility = View.GONE
                persistentBottomSheetBinding.setOneLineContent.visibility = View.GONE
            }
            currentSheet.visibility = View.VISIBLE
            currentSheet.setDrawerHandleContentDescription(
                getString(R.string.drawer_content_desc_collapse_state),
                getString(R.string.drawer_content_desc_expand_state)
            )
        }

        persistentBottomSheetBinding.setOneLineContent.setOnClickListener {
            if (defaultPersistentBottomSheet.visibility == View.VISIBLE) {
                PersistentBottomSheet.DefaultContentBuilder(this)
                    .addHorizontalGridItemList(mHorizontalSheet2.subList(0, 5))
                    .buildWith(defaultPersistentBottomSheet)
                currentSheet.showPersistentSheet()
            }
        }

        //initially
        currentSheet = persistentBottomSheetDemo
        persistentBottomSheetBinding.setOneLineContent.visibility = View.GONE

        // scroll behaviour example
        scrollView.viewTreeObserver.addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener {
            val scrollY: Int = scrollView.scrollY
            toggleBottomSheetVisibility(scrollY)
        })
        currentSheet.getBottomSheetBehaviour().isHideable = true

        persistentBottomSheetBinding.toggleDisableAllItems.setOnClickListener {
            for (item in mHorizontalSheet)
                item.disabled = !item.disabled
            for (item in mHorizontalSheet2)
                item.disabled = !item.disabled

            currentSheet.refreshSheetContent()
        }
    }

    private fun toggleBottomSheetVisibility(scrollY: Int) {
        if (scrollY > scrollView.maxScrollAmount / 2) {
            showHideBottomSheet(false)
        } else {
            showHideBottomSheet(true)
        }
    }

    private fun showHideBottomSheet(show: Boolean) {
        if (show && currentSheet.getBottomSheetBehaviour().state == BottomSheetBehavior.STATE_HIDDEN) {
            currentSheet.show()
            persistentBottomSheetBinding.collapsePersistentBottomSheetButton.text =
                getString(R.string.collapse_persistent_sheet_button)
        } else if (!show) {
            currentSheet.hide()
            persistentBottomSheetBinding.collapsePersistentBottomSheetButton.text =
                getString(R.string.show_persistent_sheet_button)
        }
    }

    private fun showDefaultBottomSheet() {

        defaultPersistentBottomSheet.setItemClickListener(this)
        PersistentBottomSheet.DefaultContentBuilder(this)
            .addHorizontalItemList(mHorizontalSheet2)
            .addDivider()
            .addHorizontalGridItemList(mHorizontalSheet)
            .addDivider()
            .addVerticalItemList(mHorizontalSheet, getString(R.string.fluentui_bottom_sheet_header))
            .addVerticalItemList(mHorizontalSheet, getString(R.string.fluentui_bottom_sheet_header))
            .buildWith(defaultPersistentBottomSheet)

    }


    override fun onSheetItemClick(item: SheetItem) {
        when (item.id) {
            R.id.persistent_sheet_item_add_view -> {
                if (currentSheet == defaultPersistentBottomSheet) {
                    mHorizontalSheet.addAll(mHorizontalSheet2)
                    mHorizontalSheet2.add(mHorizontalSheet2[0])
                    mHorizontalSheet2.removeAt(0)
                    currentSheet.refreshSheetContent()
                    return
                } else if (!this::view.isInitialized || view.parent == null) {
                    view = TextView(this)
                    view.text = getString(R.string.new_view)
                    view.height = 200
                    view.gravity = Gravity.CENTER
                    persistentBottomSheetDemo.addView(
                        view,
                        1,
                        persistentSheetContentBinding.demoBottomSheet
                    )
                } else {
                    persistentBottomSheetDemo.removeView(
                        view,
                        persistentSheetContentBinding.demoBottomSheet
                    )
                }
            }
            R.id.persistent_sheet_item_change_height_button -> {
                if (isBack)
                    persistentBottomSheetDemo.changePeekHeight(-400)
                else
                    persistentBottomSheetDemo.changePeekHeight(400)
                isBack = !isBack
            }


            R.id.bottom_sheet_item_flag -> {
                showSnackbar(resources.getString(R.string.bottom_sheet_item_flag_toast))
                item.drawable =
                    if (item.drawable == R.drawable.ic_fluent_flag_24_regular) R.drawable.ic_flag_24_filled else R.drawable.ic_fluent_flag_24_regular
                item.contentDescription = resources.getString(R.string.bottom_sheet_item_flag_toast)
                currentSheet.refreshSheetContent()
                return
            }
            R.id.bottom_sheet_item_reply -> showSnackbar(resources.getString(R.string.bottom_sheet_item_reply_toast))
            R.id.bottom_sheet_item_forward -> showSnackbar(resources.getString(R.string.bottom_sheet_item_forward_toast))
            R.id.bottom_sheet_item_delete -> showSnackbar(resources.getString(R.string.bottom_sheet_item_delete_toast))

            R.id.persistent_sheet_item_create_new_folder -> showSnackbar(resources.getString(R.string.persistent_sheet_item_create_new_folder_toast))
            R.id.persistent_sheet_item_edit -> showSnackbar(resources.getString(R.string.persistent_sheet_item_edit_toast))
            R.id.persistent_sheet_item_save -> showSnackbar(resources.getString(R.string.persistent_sheet_item_save_toast))
            R.id.persistent_sheet_item_zoom_in -> showSnackbar(resources.getString(R.string.persistent_sheet_item_zoom_in_toast))
            R.id.persistent_sheet_item_zoom_out -> showSnackbar(resources.getString(R.string.persistent_sheet_item_zoom_out_toast))
        }

    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        when (item.id) {
            R.id.bottom_sheet_item_camera -> {
                showSnackbar(resources.getString(R.string.bottom_sheet_item_camera_toast))
            }
            R.id.bottom_sheet_item_gallery -> showSnackbar(resources.getString(R.string.bottom_sheet_item_gallery_toast))
            R.id.bottom_sheet_item_videos -> showSnackbar(resources.getString(R.string.bottom_sheet_item_videos_toast))
            R.id.bottom_sheet_item_manage -> showSnackbar(resources.getString(R.string.bottom_sheet_item_manage_toast))
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(demoBinding.rootView, message).show()
    }

    private fun dummyBitmap(): Bitmap {
        val option = BitmapFactory.Options()
        option.outHeight = resources.getDimensionPixelSize(R.dimen.image_size)
        option.outWidth = resources.getDimensionPixelSize(R.dimen.image_size)
        return Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(
                resources,
                R.drawable.avatar_allan_munger
            ), option.outWidth, option.outHeight, false
        )
    }

    override fun onBackPressed() {
        if (currentSheet.getBottomSheetBehaviour().state == BottomSheetBehavior.STATE_EXPANDED) {
            currentSheet.collapse()
        } else {
            super.onBackPressed()
        }
    }

}
