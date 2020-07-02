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
    }

    private fun clickListener(v:View) {
        when(v.id) {
            R.id.show_drawer_dialog_button-> { drawerDialogDemo = DrawerDialog(this) }
            R.id.show_no_fade_bottom_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.BOTTOM, 0.0f) }
            R.id.show_top_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.TOP) }
            R.id.show_no_fade_top_dialog_button-> { drawerDialogDemo = DrawerDialog(this, DrawerDialog.BehaviorType.TOP, 0.0f) }
        }
        drawerDialogDemo?.onDrawerContentCreatedListener = this
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