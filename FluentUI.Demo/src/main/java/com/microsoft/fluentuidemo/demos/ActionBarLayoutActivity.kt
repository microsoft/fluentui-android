package com.microsoft.fluentuidemo.demos

import android.content.Intent
import android.os.Bundle
import com.microsoft.fluentui.actionbar.ActionBarLayout
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.Demo
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.demos.actionbar.ActionBarDemoActivity
import kotlinx.android.synthetic.main.activity_action_bar_layout.*
import kotlinx.android.synthetic.main.activity_demo_detail.*

class ActionBarLayoutActivity: DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_action_bar_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        start_demo_btn.setOnClickListener{
            var position = action_bar_position_rgroup.checkedRadioButtonId
            var type = action_bar_type_rgroup.checkedRadioButtonId
            if(position == -1 || type == -1){
                Snackbar.make(root_view,"Please select position and type", Snackbar.LENGTH_SHORT).show()
            }
            else{
                position = when(position){
                    R.id.action_bar_position_top -> 1
                    else -> 0
                }

                type = when(type){
                    R.id.action_bar_type_icon -> ActionBarLayout.Type.ICON.ordinal
                    R.id.action_bar_type_carousel -> ActionBarLayout.Type.CAROUSEL.ordinal
                    else -> ActionBarLayout.Type.BASIC.ordinal
                }

                val demo = Demo("DEMOACTIONBAR", ActionBarDemoActivity::class)
                val actionBarIntent = Intent(this, demo.demoClass.java)
                actionBarIntent.putExtra(DEMO_ID, demo.id)
                actionBarIntent.putExtra("POSITION", position)
                actionBarIntent.putExtra("TYPE", type)
                startActivity(actionBarIntent)
            }
        }
    }
}

