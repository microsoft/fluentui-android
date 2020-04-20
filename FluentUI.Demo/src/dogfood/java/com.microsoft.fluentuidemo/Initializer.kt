package com.microsoft.fluentuidemo

import android.app.Application
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute

object Initializer {
    fun init(application: Application) {
        initAppCenter(application)
    }

    private fun initAppCenter(application: Application) {
        AppCenter.start(application, BuildConfig.APP_CENTER_SECRET,
            Analytics::class.java,
            Crashes::class.java,
            Distribute::class.java
        )
    }
}