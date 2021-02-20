/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.DuoSupportUtils
import kotlinx.android.synthetic.main.activity_demo_detail.*
import java.util.*

abstract class DemoActivity : AppCompatActivity() {
    companion object {
        const val DEMO_ID = "demo_id"
    }
    protected abstract val contentLayoutId: Int
        @LayoutRes get
    protected open val contentNeedsScrollableContainer: Boolean
        get() = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_demo_detail)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set demo title
        val demoID = intent.getSerializableExtra(DEMO_ID) as UUID
        var demo: Demo?
        if(DuoSupportUtils.isDualScreenMode(this)){
            demo = DUO_DEMOS.find{ it.id == demoID }
        }
        else{
            demo = DEMOS.find{ it.id == demoID }
        }
        title = demo?.title

        // Load content and place it in the requested container
        val container = if (contentNeedsScrollableContainer) demo_detail_scrollable_container else demo_detail_container
        layoutInflater.inflate(contentLayoutId, container, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, DemoListActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
