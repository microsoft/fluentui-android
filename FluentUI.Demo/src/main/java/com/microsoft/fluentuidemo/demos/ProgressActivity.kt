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
        val handler = Handler()

        // Updates linear determinate progress
        Thread {
            while(progressStatus<100){
                progressStatus++
                handler.post {
                    progress_bar_linear_determinate.progress = progressStatus
                }
                Thread.sleep(20)
                if(progressStatus == 100)
                    progressStatus = 0
            }
        }.start()
    }
}