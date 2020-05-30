/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.os.Handler
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.android.synthetic.main.activity_progress.*

class ProgressActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.activity_progress

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        var progressStatus = 0
        var handler : Handler? = null

        // Updates Linear Determinate Progress
        handler = Handler(Handler.Callback {
            progressStatus++
            if(progressStatus == 100) {
                progressStatus = 0
            }
            progress_bar_linear_determinate.progress = progressStatus
            handler?.sendEmptyMessageDelayed(0, 100)
            true
        })
        handler?.sendEmptyMessage(0)
    }
}