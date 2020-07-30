/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.View
import com.microsoft.fluentui.drawer.Drawer
import com.microsoft.fluentui.drawer.DrawerDialog
import com.microsoft.fluentui.drawer.OnDrawerContentCreatedListener
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.util.createPersonaList
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.demo_drawer_content.view.*

class DrawerActivity : DemoActivity(), OnDrawerContentCreatedListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_drawer

    private var drawerDialogDemo: DrawerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        show_drawer_button.setOnClickListener {
            val drawerDemo = Drawer.newInstance(R.layout.demo_drawer_content)
            drawerDemo.show(supportFragmentManager, null)
        }

        show_drawer_dialog_button.setOnClickListener(this::clickListener)
        show_no_fade_bottom_dialog_button.setOnClickListener(this::clickListener)
        show_top_dialog_button.setOnClickListener(this::clickListener)
        show_no_fade_top_dialog_button.setOnClickListener(this::clickListener)
        show_anchor_view_top_dialog_button.setOnClickListener(this::clickListener)
        show_no_title_top_dialog_button.setOnClickListener(this::clickListener)
        show_below_title_top_dialog_button.setOnClickListener(this::clickListener)
        show_left_dialog_button.setOnClickListener(this::clickListener)
        show_right_dialog_button.setOnClickListener(this::clickListener)
    }

    private fun clickListener(v:View) {
        when(v.id) {
            R.id.show_drawer_dialog_button-> { drawerDialogDemo = DrawerDialog(this) }
            R.id.show_no_fade_bottom_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.BOTTOM, 0.0f) }
            R.id.show_top_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.TOP) }
            R.id.show_no_fade_top_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.TOP, 0.0f) }
            R.id.show_anchor_view_top_dialog_button-> { drawerDialogDemo = DrawerDialog(this,DrawerDialog.BehaviorType.TOP, anchorView = findViewById<View>(R.id.show_anchor_view_top_dialog_button)) }
            R.id.show_no_title_top_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.TOP, titleBehavior = DrawerDialog.TitleBehavior.HIDE_TITLE ) }
            R.id.show_below_title_top_dialog_button-> { drawerDialogDemo = DrawerDialog(this,DrawerDialog.BehaviorType.TOP,  titleBehavior =DrawerDialog.TitleBehavior.BELOW_TITLE) }
            R.id.show_left_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.LEFT) }
            R.id.show_right_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.RIGHT) }
        }
        drawerDialogDemo?.onDrawerContentCreatedListener = this

        if(v.id == R.id.show_left_dialog_button || v.id == R.id.show_right_dialog_button)
            drawerDialogDemo?.setContentView(R.layout.demo_side_drawer_content)
        else
            drawerDialogDemo?.setContentView(R.layout.demo_drawer_content)
        drawerDialogDemo?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        drawerDialogDemo?.dismiss()
    }

    override fun onDrawerContentCreated(drawerContents: View) {
        val personaList = createPersonaList(this)
        drawerContents.drawer_demo_persona_list.personas = personaList
    }
}
