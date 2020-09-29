/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.microsoft.fluentui.bottomsheet.BottomSheetAdapter
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import com.microsoft.fluentui.persistentbottomsheet.PersistentBottomSheet
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalItemAdapter
import com.microsoft.fluentui.persistentbottomsheet.SheetItem
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_persistent_bottom_sheet.*
import kotlinx.android.synthetic.main.demo_persistent_sheet_content.*

class PersistentBottomSheetActivity : DemoActivity(), SheetItem.OnClickListener, BottomSheetItem.OnClickListener {

    override val contentLayoutId: Int
        get() = R.layout.activity_persistent_bottom_sheet

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    private lateinit var persistentBottomSheetDemo: PersistentBottomSheet
    private lateinit var defaultPersistentBottomSheet :PersistentBottomSheet
    private lateinit var currentSheet :PersistentBottomSheet
    private var isBack:Boolean = false
    private lateinit var view:TextView
    private lateinit var mHorizontalSheet:List<SheetItem>
    private lateinit var mHorizontalSheet2:List<SheetItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        persistentBottomSheetDemo = findViewById(R.id.demo_persistent_sheet)
        defaultPersistentBottomSheet = findViewById(R.id.default_persistent_sheet)

        PersistentBottomSheet.DefaultContentBuilder(this)
                .setCustomSheetContent(R.layout.demo_persistent_sheet_content)
                .buildWith(persistentBottomSheetDemo)

        mHorizontalSheet = arrayListOf(
                SheetItem(
                        R.id.bottom_sheet_item_flag,
                        R.drawable.ic_fluent_flag_24_regular,
                        getString(R.string.bottom_sheet_item_flag_title)),
                SheetItem(R.id.bottom_sheet_item_alarm,
                        dummyBitmap(),
                        getString(R.string.bottom_sheet_item_custom_image)),
                SheetItem(
                        R.id.persistent_sheet_item_add_view,
                        R.drawable.ic_add_circle_28_fill,
                        getString(R.string.persistent_sheet_item_add_remove_view)),
                SheetItem(
                        R.id.persistent_sheet_item_change_height_button,
                        R.drawable.ic_vertical_align_center_28_fill,
                        getString(R.string.persistent_sheet_item_change_collapsed_height)),
                SheetItem(
                        R.id.bottom_sheet_item_reply,
                        R.drawable.ic_fluent_reply_24_regular,
                        getString(R.string.bottom_sheet_item_reply_title)),
                SheetItem(
                        R.id.bottom_sheet_item_forward,
                        R.drawable.ic_fluent_forward_24_regular,
                        getString(R.string.bottom_sheet_item_forward_title)),
                SheetItem(
                        R.id.bottom_sheet_item_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_delete_title)),
                SheetItem(
                        R.id.bottom_sheet_item_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_delete_title)),
                SheetItem(
                        R.id.bottom_sheet_item_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_delete_title))
        )

        mHorizontalSheet2 = arrayListOf(
                SheetItem(
                        R.id.bottom_sheet_item_flag,
                        R.drawable.ic_fluent_flag_24_regular,
                        getString(R.string.bottom_sheet_item_flag_title)),
                SheetItem(
                        R.id.bottom_sheet_item_reply,
                        R.drawable.ic_fluent_reply_24_regular,
                        getString(R.string.bottom_sheet_item_reply_title)),
                SheetItem(
                        R.id.bottom_sheet_item_forward,
                        R.drawable.ic_fluent_forward_24_regular,
                        getString(R.string.bottom_sheet_item_forward_title)),
                SheetItem(
                        R.id.bottom_sheet_item_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_delete_title)),
                SheetItem(
                        R.id.persistent_sheet_item_create_new_folder,
                        R.drawable.ic_create_new_folder_24_filled,
                        getString(R.string.persistent_sheet_item_create_new_folder_title))
        )


        sheet_horizontal_item_list_1.createHorizontalItemLayout(mHorizontalSheet)
        sheet_horizontal_item_list_1.sheetItemClickListener = this
        sheet_horizontal_item_list_1.setTextAppearance(R.style.TextAppearance_FluentUI_ListItemTitle)

        sheet_horizontal_item_list_2.createHorizontalItemLayout(mHorizontalSheet2)
        sheet_horizontal_item_list_2.sheetItemClickListener = this
        sheet_horizontal_item_list_2.setTextAppearance(R.style.TextAppearance_FluentUI_ListItemTitle)

        val marginBetweenView = resources.getDimension(R.dimen.fluentui_persistent_horizontal_item_right_margin).toInt()
        val horizontalListAdapter  = SheetHorizontalItemAdapter(this,
                arrayListOf(
                        SheetItem(
                                R.id.persistent_sheet_item_create_new_folder,
                                R.drawable.ic_create_new_folder_24_filled,
                                getString(R.string.persistent_sheet_item_create_new_folder_title)),
                        SheetItem(
                                R.id.persistent_sheet_item_edit,
                                R.drawable.ic_edit_24_filled,
                                getString(R.string.persistent_sheet_item_edit_title)),
                        SheetItem(
                                R.id.persistent_sheet_item_save,
                                R.drawable.ic_save_24_filled,
                                getString(R.string.persistent_sheet_item_save_title)),
                        SheetItem(
                                R.id.persistent_sheet_item_zoom_in,
                                R.drawable.ic_zoom_in_24_filled,
                                getString(R.string.persistent_sheet_item_zoom_in_title)),
                        SheetItem(
                                R.id.persistent_sheet_item_zoom_out,
                                R.drawable.ic_zoom_out_24_filled,
                                getString(R.string.persistent_sheet_item_zoom_out_title))
                ),0, marginBetweenView)
        horizontalListAdapter.mOnSheetItemClickListener = this
        sheet_horizontal_item_list_3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        sheet_horizontal_item_list_3.adapter = horizontalListAdapter


        val verticalListAdapter =  BottomSheetAdapter(this,
                arrayListOf(
                        BottomSheetItem(
                                R.id.bottom_sheet_item_camera,
                                R.drawable.ic_camera_24_regular,
                                getString(R.string.bottom_sheet_item_camera_title),
                                getString(R.string.bottom_sheet_item_camera_subtitle)
                        ),
                        BottomSheetItem(
                                R.id.bottom_sheet_item_gallery,
                                R.drawable.ic_image_library_24_regular,
                                getString(R.string.bottom_sheet_item_gallery_title),
                                getString(R.string.bottom_sheet_item_gallery_subtitle)
                        ),
                        BottomSheetItem(
                                R.id.bottom_sheet_item_videos,
                                R.drawable.ic_video_24_regular,
                                getString(R.string.bottom_sheet_item_videos_title),
                                getString(R.string.bottom_sheet_item_videos_subtitle)
                        ),
                        BottomSheetItem(
                                R.id.bottom_sheet_item_manage,
                                R.drawable.ic_settings_24_regular,
                                getString(R.string.bottom_sheet_item_manage_title),
                                getString(R.string.bottom_sheet_item_manage_subtitle)
                        )
                ), 0)
        verticalListAdapter.onBottomSheetItemClickListener = this
        sheet_vertical_item_list_1.adapter = verticalListAdapter

        show_persistent_bottom_sheet_button.setOnClickListener {
            currentSheet.expand()
        }

        collapse_persistent_bottom_sheet_button.setOnClickListener {
            if(currentSheet.getPeekHeight() == 0) {
                currentSheet.showPersistentSheet()
                collapse_persistent_bottom_sheet_button.text = getString(R.string.collapse_persistent_sheet_button)
            }
            else {
                currentSheet.collapsePersistentSheet()
                collapse_persistent_bottom_sheet_button.text = getString(R.string.show_persistent_sheet_button)
            }
        }

        switch_bottom_sheet.setOnClickListener {
            if (defaultPersistentBottomSheet.visibility == View.GONE) {
                currentSheet = defaultPersistentBottomSheet
                persistentBottomSheetDemo.visibility = View.GONE
                showDefaultBottomSheet()
            } else {
                currentSheet = persistentBottomSheetDemo
                defaultPersistentBottomSheet.visibility = View.GONE
            }
            currentSheet.visibility = View.VISIBLE
        }

        //initially
        currentSheet = persistentBottomSheetDemo
    }

    private fun showDefaultBottomSheet() {

        defaultPersistentBottomSheet.setItemClickListener(this)
        PersistentBottomSheet.DefaultContentBuilder(this)
                .addHorizontalItemList(mHorizontalSheet2)
                .addDivider()
                .addHorizontalGridItemList(mHorizontalSheet)
                .addDivider()
                .addVerticalItemList(mHorizontalSheet, getString(R.string.fluentui_bottom_sheet_header))
                .addVerticalItemList(mHorizontalSheet,getString(R.string.fluentui_bottom_sheet_header))
                .buildWith(defaultPersistentBottomSheet)

    }

    override fun onSheetItemClick(item: SheetItem){
        when(item.id) {
            R.id.persistent_sheet_item_add_view -> {
                if(!this::view.isInitialized || view.parent == null) {
                    view =  TextView(this)
                    view.text = getString(R.string.new_view)
                    view.height = 200
                    view.gravity = Gravity.CENTER
                    persistentBottomSheetDemo.addView(view, 1, demo_bottom_sheet)
                }
                else {
                    persistentBottomSheetDemo.removeView(view, demo_bottom_sheet)
                }
            }
            R.id.persistent_sheet_item_change_height_button -> {
                if(isBack)
                    persistentBottomSheetDemo.changePeekHeight(-400)
                else
                    persistentBottomSheetDemo.changePeekHeight(400)
                isBack = !isBack
            }


            R.id.bottom_sheet_item_flag -> showSnackbar(resources.getString(R.string.bottom_sheet_item_flag_toast))
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
        when(item.id) {
            R.id.bottom_sheet_item_camera -> showSnackbar(resources.getString(R.string.bottom_sheet_item_camera_toast))
            R.id.bottom_sheet_item_gallery -> showSnackbar(resources.getString(R.string.bottom_sheet_item_gallery_toast))
            R.id.bottom_sheet_item_videos -> showSnackbar(resources.getString(R.string.bottom_sheet_item_videos_toast))
            R.id.bottom_sheet_item_manage -> showSnackbar(resources.getString(R.string.bottom_sheet_item_manage_toast))
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(root_view, message).show()
    }

    private fun dummyBitmap(): Bitmap {
        val option = BitmapFactory.Options()
        option.outHeight = resources.getDimensionPixelSize(R.dimen.image_size)
        option.outWidth = resources.getDimensionPixelSize(R.dimen.image_size)
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(resources, R.drawable.avatar_allan_munger), option.outWidth, option.outHeight, false)
    }
}
