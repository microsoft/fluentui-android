/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.ActivityProgressBinding

class ProgressActivity : DemoActivity() {

    private lateinit var progressBinding: ActivityProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBinding =
            ActivityProgressBinding.inflate(LayoutInflater.from(container.context), container, true)

        var progressStatus = 0
        val handler = Handler()

        // Updates linear determinate progress
        Thread {
            while (progressStatus < 100) {
                progressStatus++
                handler.post {
                    progressBinding.progressBarLinearDeterminate.progress = progressStatus
                }
                Thread.sleep(20)
                if (progressStatus == 100)
                    progressStatus = 0
            }
        }.start()
    }
}