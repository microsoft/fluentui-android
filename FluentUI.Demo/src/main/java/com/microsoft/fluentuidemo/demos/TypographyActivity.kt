/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.widget.TextViewCompat
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityTypographyBinding

class TypographyActivity : DemoActivity() {

    private lateinit var typographyBinding: ActivityTypographyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        typographyBinding = ActivityTypographyBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        TextViewCompat.setTextAppearance(
            typographyBinding.typographyExampleBody2,
            R.style.TextAppearance_FluentUI_Body2
        )
    }
}