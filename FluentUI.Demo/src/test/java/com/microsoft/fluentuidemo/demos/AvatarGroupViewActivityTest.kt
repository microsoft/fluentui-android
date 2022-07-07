package com.microsoft.fluentuidemo.demos

import android.app.Activity
import android.content.Intent
import com.microsoft.fluentuidemo.DEMOS
import com.microsoft.fluentuidemo.DemoActivity
import org.junit.*
import org.junit.runner.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class AvatarGroupViewActivityTest {

    private var activity: Activity? = null

    @Before
    fun setup() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        var intent = Intent(context, AvatarViewActivity::class.java)
        intent.putExtra(DemoActivity.DEMO_ID, DEMOS[2].id)
        activity = Robolectric.buildActivity(AvatarViewActivity::class.java, intent).create().start().visible().get()
    }
}