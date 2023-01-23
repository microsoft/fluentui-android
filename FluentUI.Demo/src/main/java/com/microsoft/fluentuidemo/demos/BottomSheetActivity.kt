/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import com.microsoft.fluentui.bottomsheet.BottomSheet
import com.microsoft.fluentui.bottomsheet.BottomSheetDialog
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.util.createBitmapFromLayout
import kotlinx.android.synthetic.main.activity_bottom_sheet.*
import kotlinx.android.synthetic.main.activity_demo_detail.*

class BottomSheetActivity : DemoActivity(), BottomSheetItem.OnClickListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_bottom_sheet

    private var bottomSheetDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Single line items
        show_with_single_line_items_button.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.accessory_content, null)
            val textView = view.findViewById<TextView>(R.id.bottom_sheet_item_nudge)
            textView.text = "10+"
            val bitmap1 = createBitmapFromLayout(view)
            textView.text = "100+"
            val bitmap2 = createBitmapFromLayout(view)
            textView.text = "123456789+"
            val bitmap3 = createBitmapFromLayout(view)

            val bottomSheet = BottomSheet.newInstance(
                arrayListOf(
                    BottomSheetItem(
                        R.id.bottom_sheet_item_flag,
                        R.drawable.ic_fluent_flag_24_regular,
                        getString(R.string.bottom_sheet_item_flag_title),
                        accessoryBitmap = bitmap1
                    ),
                    BottomSheetItem(
                            R.id.bottom_sheet_item_reply,
                            R.drawable.ic_fluent_reply_24_regular,
                            getString(R.string.bottom_sheet_item_reply_title),
                            disabled = true,
                            accessoryBitmap = bitmap2
                    ),
                    BottomSheetItem(
                            R.id.bottom_sheet_item_forward,
                            R.drawable.ic_fluent_forward_24_regular,
                            getString(R.string.bottom_sheet_item_forward_title),
                            accessoryBitmap = bitmap3,
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_delete_title)
                    )
                )
            )
            bottomSheet.show(supportFragmentManager, null)
        }

        // Double line items
        show_with_double_line_items_button.setOnClickListener {
            val bottomSheet = BottomSheet.newInstance(
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
                )
            )
            bottomSheet.show(supportFragmentManager, null)
        }

        // Single line header
        show_with_single_line_items_and_header_button.setOnClickListener {
            val bottomSheet = BottomSheet.newInstance(
                arrayListOf(
                    BottomSheetItem(
                        R.id.bottom_sheet_item_flag,
                        R.drawable.ic_fluent_flag_24_regular,
                        getString(R.string.bottom_sheet_item_flag_title)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_reply,
                        R.drawable.ic_fluent_reply_24_regular,
                        getString(R.string.bottom_sheet_item_reply_title)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_forward,
                        R.drawable.ic_fluent_forward_24_regular,
                        getString(R.string.bottom_sheet_item_forward_title)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_delete_title)
                    )
                ),
                BottomSheetItem(
                   title = getString(R.string.bottom_sheet_item_single_line_header)
                )
            )
            bottomSheet.show(supportFragmentManager, null)
        }

        // Double line header
        show_with_double_line_items_and_two_line_header_button.setOnClickListener {
            val bottomSheet = BottomSheet.newInstance(
                arrayListOf(
                    BottomSheetItem(
                        R.id.bottom_sheet_item_double_line_header_share,
                        R.drawable.ic_share_24_filled,
                        getString(R.string.bottom_sheet_item_double_line_header_share)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_double_line_header_move,
                        R.drawable.ic_folder_move_24_regular,
                        getString(R.string.bottom_sheet_item_double_line_header_move)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_double_line_header_delete,
                        R.drawable.ic_delete_24_regular,
                        getString(R.string.bottom_sheet_item_double_line_header_delete)
                    ),
                    BottomSheetItem(
                        R.id.bottom_sheet_item_double_line_header_info,
                        R.drawable.ic_info_24_regular,
                        getString(R.string.bottom_sheet_item_double_line_header_info),
                        useDivider = true
                    )
                ),
                BottomSheetItem(
                    imageId = R.drawable.ic_folder_24_regular,
                    title = getString(R.string.bottom_sheet_item_double_line_header),
                    subtitle = getString(R.string.bottom_sheet_item_double_line_header_subtitle)
                )
            )
            bottomSheet.show(supportFragmentManager, null)
        }

        // Dialog
        show_bottom_sheet_dialog_button.setOnClickListener {
            if (bottomSheetDialog == null) {
                bottomSheetDialog = BottomSheetDialog(
                    this,
                    arrayListOf(
                        BottomSheetItem(
                            R.id.bottom_sheet_item_clock,
                            R.drawable.ic_clock_24_regular,
                            getString(R.string.bottom_sheet_item_clock_title)
                        ),
                        BottomSheetItem(
                            R.id.bottom_sheet_item_alarm,
                            R.drawable.ic_alert_24_regular,
                            getString(R.string.bottom_sheet_item_alarm_title)
                        ),
                        BottomSheetItem(
                            R.id.bottom_sheet_item_time_zone,
                            R.drawable.ic_globe_24_regular,
                            getString(R.string.bottom_sheet_item_time_zone_title)
                        )
                    )
                )
                bottomSheetDialog?.onItemClickListener = this
            }

            bottomSheetDialog?.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        bottomSheetDialog?.dismiss()
    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        when(item.id) {
            // Single line items & single line header
            R.id.bottom_sheet_item_flag -> showSnackbar(resources.getString(R.string.bottom_sheet_item_flag_toast))
            R.id.bottom_sheet_item_reply -> showSnackbar(resources.getString(R.string.bottom_sheet_item_reply_toast))
            R.id.bottom_sheet_item_forward -> showSnackbar(resources.getString(R.string.bottom_sheet_item_forward_toast))
            R.id.bottom_sheet_item_delete -> showSnackbar(resources.getString(R.string.bottom_sheet_item_delete_toast))

            // Double line items
            R.id.bottom_sheet_item_camera -> showSnackbar(resources.getString(R.string.bottom_sheet_item_camera_toast))
            R.id.bottom_sheet_item_gallery -> showSnackbar(resources.getString(R.string.bottom_sheet_item_gallery_toast))
            R.id.bottom_sheet_item_videos -> showSnackbar(resources.getString(R.string.bottom_sheet_item_videos_toast))
            R.id.bottom_sheet_item_manage -> showSnackbar(resources.getString(R.string.bottom_sheet_item_manage_toast))

            // Double line header
            R.id.bottom_sheet_item_double_line_header_move -> showSnackbar(getString(R.string.bottom_sheet_item_double_line_header_move_toast))
            R.id.bottom_sheet_item_double_line_header_share -> showSnackbar(getString(R.string.bottom_sheet_item_double_line_header_share_toast))
            R.id.bottom_sheet_item_double_line_header_delete -> showSnackbar(getString(R.string.bottom_sheet_item_double_line_header_delete_toast))
            R.id.bottom_sheet_item_double_line_header_info -> showSnackbar(getString(R.string.bottom_sheet_item_double_line_header_info_toast))

            // Dialog
            R.id.bottom_sheet_item_clock -> showSnackbar(resources.getString(R.string.bottom_sheet_item_clock_toast))
            R.id.bottom_sheet_item_alarm -> showSnackbar(resources.getString(R.string.bottom_sheet_item_alarm_toast))
            R.id.bottom_sheet_item_time_zone -> showSnackbar(resources.getString(R.string.bottom_sheet_item_time_zone_toast))
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(root_view, message).show()
    }
}