/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.fluentui.util.DuoSupportUtils
import com.microsoft.fluentuidemo.databinding.ActivityDemoDetailBinding
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import java.util.*

abstract class DemoActivity : AppCompatActivity() {
    companion object {
        const val DEMO_ID = "demo_id"
    }

    protected open val contentNeedsScrollableContainer: Boolean
        get() = true

    protected lateinit var demoBinding: ActivityDemoDetailBinding

    protected lateinit var container: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        demoBinding = ActivityDemoDetailBinding.inflate(layoutInflater)
        setContentView(demoBinding.root)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set demo title
        val demoID = intent.getSerializableExtra(DEMO_ID) as UUID
        val demo: Demo? = if (DuoSupportUtils.isDualScreenMode(this)) {
            DUO_DEMOS.find { it.id == demoID }
        } else {
            V1DEMO.find { it.id == demoID }
        }
        if (demo != null)
            title = demo.title

        container =
            if (contentNeedsScrollableContainer) demoBinding.demoDetailScrollableContainer else demoBinding.demoDetailContainer
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, DemoListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
