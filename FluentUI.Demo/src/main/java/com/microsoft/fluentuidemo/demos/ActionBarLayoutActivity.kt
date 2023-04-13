package com.microsoft.fluentuidemo.demos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.microsoft.fluentui.actionbar.ActionBarLayout
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentuidemo.Demo
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityActionBarLayoutBinding
import com.microsoft.fluentuidemo.demos.actionbar.ActionBarDemoActivity

class ActionBarLayoutActivity : DemoActivity() {

    private lateinit var actionBarBinding: ActivityActionBarLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        actionBarBinding = ActivityActionBarLayoutBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        actionBarBinding.startDemoBtn.setOnClickListener {
            var position = actionBarBinding.actionBarPositionRgroup.checkedRadioButtonId
            var type = actionBarBinding.actionBarTypeRgroup.checkedRadioButtonId
            if (position == -1 || type == -1) {
                Snackbar.make(
                    demoBinding.rootView,
                    "Please select position and type",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                position = when (position) {
                    R.id.action_bar_position_top -> 1
                    else -> 0
                }

                type = when (type) {
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

