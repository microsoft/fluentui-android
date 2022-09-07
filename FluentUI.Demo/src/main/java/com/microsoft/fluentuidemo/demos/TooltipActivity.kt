/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import com.microsoft.fluentui.calendar.CalendarView
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.tooltip.Tooltip
import com.microsoft.fluentui.util.ThemeUtil
import com.microsoft.fluentui.widget.Button
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_demo_detail.*
import kotlinx.android.synthetic.main.activity_tooltip.*

class TooltipActivity : DemoActivity(), Tooltip.OnDismissListener {
    companion object {
        const val BUTTON_ID = "buttonId"
    }

    enum class TooltipType(
        val buttonId: Int,
        val messageId: Int,
        val offsetXId: Int,
        val offsetYId: Int
    ) {
        TOP_START(R.id.tooltip_anchor_top_start, R.string.tooltip_top_start_message, 0, 0),
        TOP_END(
            R.id.tooltip_anchor_top_end,
            R.string.tooltip_top_end_message,
            R.dimen.tooltip_example_offset_x,
            0
        ),
        BOTTOM_START(R.id.tooltip_anchor_bottom_start, R.string.tooltip_bottom_start_message, 0, 0),
        BOTTOM_END(
            R.id.tooltip_anchor_bottom_end,
            R.string.tooltip_bottom_end_message,
            0,
            R.dimen.tooltip_example_offset_y
        )
    }

    private var tooltip: Tooltip? = null

    private var optionsMenu: Menu? = null

    private var buttonId: Int = 0

    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override val contentLayoutId: Int
        get() = R.layout.activity_tooltip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TooltipType.values().forEach { type ->
            findViewById<Button>(type.buttonId).setOnClickListener {
                val config = Tooltip.Config(getDimen(type.offsetXId), getDimen(type.offsetYId))
                tooltip = Tooltip(this)
                if (it.id == R.id.tooltip_anchor_top_start) {
                    tooltip?.setCustomBackgroundColor(
                        ContextCompat.getColor(
                            baseContext,
                            R.color.tooltip_custom_color
                        )
                    )
                }
                tooltip?.show(it, resources.getString(type.messageId), config)
                buttonId = it.id
            }
        }

        tooltip_anchor_center.setOnClickListener {
            tooltip = Tooltip(this).show(
                it,
                LayoutInflater.from(this).inflate(R.layout.tooltip_custom_view, null),
                Tooltip.Config(touchDismissLocation = Tooltip.TouchDismissLocation.INSIDE)
            )
            tooltip?.onDismissListener = this
            buttonId = it.id
        }

        tooltip_anchor_calendar_demo.setOnClickListener {
            tooltip = Tooltip(this).show(it, CalendarView(this), Tooltip.Config())
            tooltip?.onDismissListener = this
            buttonId = it.id
        }

        savedInstanceState?.let {
            buttonId = it.getInt(BUTTON_ID)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_app_bar_layout, menu)

        optionsMenu = menu

        for (index in 0 until menu.size()) {
            val drawable = menu.getItem(index).icon
            drawable?.setColorFilter(
                ThemeUtil.getThemeAttrColor(this, R.attr.fluentuiToolbarIconColor),
                PorterDuff.Mode.SRC_IN
            )
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_flag -> {
                Tooltip(baseContext).show(
                    findViewById(item.itemId),
                    "Flag Tooltip Clicked",
                    Tooltip.Config(touchDismissLocation = Tooltip.TouchDismissLocation.INSIDE)
                ).onDismissListener = this

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()

        if (buttonId > 0) {
            val button = findViewById<Button>(buttonId)
            button.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // Show tooltip on configuration change
                    button.performClick()
                    button.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(BUTTON_ID, buttonId)
    }

    override fun onPause() {
        super.onPause()

        tooltip?.let {
            if (!it.isShowing)
                buttonId = 0
            it.dismiss()
        }
    }

    override fun onDismiss() {
        Snackbar.make(
            root_view,
            resources.getString(R.string.tooltip_dismiss_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun getDimen(id: Int): Int =
        if (id > 0) Math.round(resources.getDimension(id)) else 0
}