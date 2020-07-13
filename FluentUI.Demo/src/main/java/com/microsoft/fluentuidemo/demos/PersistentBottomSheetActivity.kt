/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.microsoft.fluentui.drawer.OnDrawerContentCreatedListener
import com.microsoft.fluentui.persistentbottomsheet.PersistentBottomSheet
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.util.createPersonaList
import kotlinx.android.synthetic.main.activity_persistent_bottom_sheet.*
import kotlinx.android.synthetic.main.demo_persistent_sheet_content.*
import kotlinx.android.synthetic.main.demo_persistent_sheet_content.view.*

class PersistentBottomSheetActivity : DemoActivity(), OnDrawerContentCreatedListener {
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
        persistentBottomSheetDemo.onDrawerContentCreatedListener = this
        persistentBottomSheetDemo.addSheetContent(R.layout.demo_persistent_sheet_content)

        update_view_button.setOnClickListener {
            if(!this::view.isInitialized || view.parent == null) {
                view =  TextView(this)
                view.text = getString(R.string.new_view)
                view.height = 200
                view.gravity = Gravity.CENTER
                persistentBottomSheetDemo.addView(demo_bottom_sheet, view, 1)
                update_view_button.text = getString(R.string.remove_view_button)
            }
            else {
                persistentBottomSheetDemo.removeView(demo_bottom_sheet, view)
                update_view_button.text = getString(R.string.add_view_button)
            }
        }

        change_peek_height_button.setOnClickListener {
            if(isBack) {
                persistentBottomSheetDemo.incrementHeight(-400)
                change_peek_height_button.text=getString(R.string.increase_peek_height_button)
            }
            else {
                persistentBottomSheetDemo.incrementHeight(400)
                change_peek_height_button.text=getString(R.string.decrease_peek_height_button)
            }
            isBack = !isBack
        }

        show_persistent_bottom_sheet_button.setOnClickListener {
            persistentBottomSheetDemo.getSheetBehavior()!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onDrawerContentCreated(drawerContents: View) {
        val personaList = createPersonaList(this)
        drawerContents.drawer_demo_persona_list.personas = personaList
    }
}
