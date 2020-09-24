/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.widget.TextView
import com.microsoft.fluentui.bottomsheet.BottomSheetAdapter
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import com.microsoft.fluentui.persistentbottomsheet.ListItem
import com.microsoft.fluentui.persistentbottomsheet.PersistentBottomSheet
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.util.DuoSupportUtils
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_persistent_bottom_sheet.*
import kotlinx.android.synthetic.main.demo_persistent_sheet_content.*

class PersistentBottomSheetActivity : DemoActivity(), ListItem.OnClickListener, BottomSheetItem.OnClickListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_persistent_bottom_sheet

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    lateinit var persistentBottomSheetDemo: PersistentBottomSheet
    var isBack:Boolean = false
    lateinit var view:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        persistentBottomSheetDemo = findViewById(R.id.demo_persistent_sheet)
        persistentBottomSheetDemo.addSheetContent(R.layout.demo_persistent_sheet_content)

        sheet_horizontal_item_list_1.updateTemplate( arrayListOf(
                ListItem(
                        R.id.persistent_sheet_item_add_view,
                        R.drawable.ic_add_circle_28_fill,
                        getString(R.string.persistent_sheet_item_add_remove_view)),
                ListItem(
                        R.id.persistent_sheet_item_change_height_button,
                        R.drawable.ic_vertical_align_center_28_fill,
                        getString(R.string.persistent_sheet_item_change_collapsed_height))
        ))
        sheet_horizontal_item_list_1.onSheetItemClickListener = this
        sheet_horizontal_item_list_1.setTextAppearance(R.style.TextAppearance_FluentUI_ListItemTitle)

        sheet_horizontal_item_list_2.updateTemplate( arrayListOf(
                ListItem(
                        R.id.bottom_sheet_item_flag,
                        R.drawable.ic_fluent_flag_24_regular,
                        getString(R.string.bottom_sheet_item_flag_title)),
                ListItem(
                        R.id.bottom_sheet_item_reply,
                        R.drawable.ic_fluent_reply_24_regular,
                        getString(R.string.bottom_sheet_item_reply_title)),
                ListItem(
                        R.id.bottom_sheet_item_forward,
                        R.drawable.ic_fluent_forward_24_regular,
                        getString(R.string.bottom_sheet_item_forward_title)),
                ListItem(
                        R.id.bottom_sheet_item_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_delete_title))
        ))
        sheet_horizontal_item_list_2.onSheetItemClickListener = this

        sheet_horizontal_item_list_25.updateTemplate( arrayListOf(
                ListItem(
                        R.id.persistent_sheet_item_create_new_folder,
                        R.drawable.ic_create_new_folder_24_filled,
                        getString(R.string.persistent_sheet_item_create_new_folder_title)),
                ListItem(
                        R.id.persistent_sheet_item_edit,
                        R.drawable.ic_edit_24_filled,
                        getString(R.string.persistent_sheet_item_edit_title)),
                ListItem(
                        R.id.persistent_sheet_item_save,
                        R.drawable.ic_save_24_filled,
                        getString(R.string.persistent_sheet_item_save_title)),
                ListItem(
                        R.id.persistent_sheet_item_zoom_in,
                        R.drawable.ic_zoom_in_24_filled,
                        getString(R.string.persistent_sheet_item_zoom_in_title)),
                ListItem(
                        R.id.persistent_sheet_item_zoom_out,
                        R.drawable.ic_zoom_out_24_filled,
                        getString(R.string.persistent_sheet_item_zoom_out_title)),
                ListItem(
                        R.id.persistent_sheet_item_cancel,
                        R.drawable.ic_zoom_out_24_filled,
                        getString(R.string.persistent_sheet_item_cancel_title))
        ))
        sheet_horizontal_item_list_2.onSheetItemClickListener = this
        sheet_horizontal_item_list_3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)



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
            persistentBottomSheetDemo.getSheetBehavior().state = BottomSheetBehavior.STATE_EXPANDED
        }

        collapse_persistent_bottom_sheet_button.setOnClickListener {
            if(persistentBottomSheetDemo.getSheetBehavior().peekHeight == 0) {
                persistentBottomSheetDemo.showPersistentSheet()
                collapse_persistent_bottom_sheet_button.text = getString(R.string.collapse_persistent_sheet_button)
            }
            else {
                persistentBottomSheetDemo.collapsePersistentSheet()
                collapse_persistent_bottom_sheet_button.text = getString(R.string.show_persistent_sheet_button)
            }
        }
    }

    override fun onSheetItemClick(item: ListItem){
        when(item.id) {
            R.id.persistent_sheet_item_add_view -> {
                if(!this::view.isInitialized || view.parent == null) {
                    view =  TextView(this)
                    view.text = getString(R.string.new_view)
                    view.height = 200
                    if(DuoSupportUtils.isWindowDoublePortrait(this)) {
                        view.x -= DuoSupportUtils.getSingleScreenWidthPixels(this)/2
                    }
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
}
