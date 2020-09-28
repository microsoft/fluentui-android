package com.microsoft.fluentuidemo.persistentbottomsheet

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.mg.pbs.R
import com.microsoft.fluentui.persistentbottomsheet.ListItem
import com.microsoft.fluentui.persistentbottomsheet.PersistentBottomSheet
import com.microsoft.fluentui.persistentbottomsheet.SheetHorizontalItemAdapter
import com.microsoft.fluentui.persistentbottomsheet.bottomsheet.BottomSheetAdapter
import com.microsoft.fluentui.persistentbottomsheet.bottomsheet.BottomSheetItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.demo_persistent_sheet_content.*

class MainActivity : AppCompatActivity() , ListItem.OnClickListener,BottomSheetItem.OnClickListener{
     val contentLayoutId: Int
        get() = R.layout.activity_main


    lateinit var persistentBottomSheetDemo: PersistentBottomSheet
    var isBack:Boolean = false
    lateinit var view:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        persistentBottomSheetDemo = findViewById(R.id.demo_persistent_sheet)
        persistentBottomSheetDemo.addSheetContent(R.layout.demo_persistent_sheet_content)
        sheet_horizontal_item_list_1.updateTemplate(arrayListOf(
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
        sheet_horizontal_item_list_2.updateTemplate(arrayListOf(
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

        val marginBetweenView = resources.getDimension(R.dimen.fluentui_persistent_horizontal_item_right_margin).toInt()
        val horizontalListAdapter  = SheetHorizontalItemAdapter(this,
                arrayListOf(
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
                                getString(R.string.persistent_sheet_item_zoom_out_title))
                ), 0, marginBetweenView)
        horizontalListAdapter.onSheetItemClickListener = this
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

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        when (item.id) {
            R.id.bottom_sheet_item_camera -> showToast(resources.getString(R.string.bottom_sheet_item_camera_toast))
            R.id.bottom_sheet_item_gallery -> showToast(resources.getString(R.string.bottom_sheet_item_gallery_toast))
            R.id.bottom_sheet_item_videos -> showToast(resources.getString(R.string.bottom_sheet_item_videos_toast))
            R.id.bottom_sheet_item_manage -> showToast(resources.getString(R.string.bottom_sheet_item_manage_toast))

        }
    }

    override fun onSheetItemClick(item: ListItem) {
        when(item.id) {
                R.id.persistent_sheet_item_add_view -> {
                    if (!this::view.isInitialized || view.parent == null) {
                        view = TextView(this)
                        view.text = getString(R.string.new_view)
                        view.height = 200
                        view.gravity = Gravity.CENTER
                        persistentBottomSheetDemo.addView(view, 1, demo_bottom_sheet)
                    } else {
                        persistentBottomSheetDemo.removeView(view, demo_bottom_sheet)
                    }
                }
                R.id.persistent_sheet_item_change_height_button -> {
                    if (isBack)
                        persistentBottomSheetDemo.changePeekHeight(-400)
                    else
                        persistentBottomSheetDemo.changePeekHeight(400)
                    isBack = !isBack
                }


                R.id.bottom_sheet_item_flag -> showToast(resources.getString(R.string.bottom_sheet_item_flag_toast))
                R.id.bottom_sheet_item_reply -> showToast(resources.getString(R.string.bottom_sheet_item_reply_toast))
                R.id.bottom_sheet_item_forward -> showToast(resources.getString(R.string.bottom_sheet_item_forward_toast))
                R.id.bottom_sheet_item_delete -> showToast(resources.getString(R.string.bottom_sheet_item_delete_toast))

                R.id.persistent_sheet_item_create_new_folder -> showToast(resources.getString(R.string.persistent_sheet_item_create_new_folder_toast))
                R.id.persistent_sheet_item_edit -> showToast(resources.getString(R.string.persistent_sheet_item_edit_toast))
                R.id.persistent_sheet_item_save -> showToast(resources.getString(R.string.persistent_sheet_item_save_toast))
                R.id.persistent_sheet_item_zoom_in -> showToast(resources.getString(R.string.persistent_sheet_item_zoom_in_toast))
                R.id.persistent_sheet_item_zoom_out -> showToast(resources.getString(R.string.persistent_sheet_item_zoom_out_toast))
            }
    }

    private fun showToast(message: String) {
        Log.e("*MG*","Inside show Toast "+message)
        val toast: Toast = Toast.makeText(applicationContext,
                message,
                Toast.LENGTH_SHORT)

        toast.show()
    }
}
