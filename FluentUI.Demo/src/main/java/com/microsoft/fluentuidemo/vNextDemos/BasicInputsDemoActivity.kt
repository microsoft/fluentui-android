package com.microsoft.fluentuidemo.vNextDemos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_vnext_basic_inputs.*

class BasicInputsDemoActivity : DemoActivity(), View.OnClickListener {
    override val contentLayoutId: Int
        get() = R.layout.activity_vnext_basic_inputs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        primary_button_large.setOnClickListener(this)
        secondary_button_large.setOnClickListener(this)
        ghost_button_large.setOnClickListener(this)
        icon_button_large.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.primary_button_large ->
                Toast.makeText(this, "primary button", Toast.LENGTH_LONG).show()
            R.id.secondary_button_large ->
                Toast.makeText(this, "secondary button", Toast.LENGTH_LONG).show()
            R.id.ghost_button_large ->
                Toast.makeText(this, "ghost button", Toast.LENGTH_LONG).show()
            R.id.icon_button_large ->
                Toast.makeText(this, "icon button", Toast.LENGTH_LONG).show()
        }
    }
}