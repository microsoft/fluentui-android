package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.ActionBarLayoutActivityUITest
import com.microsoft.fluentuidemo.demos.AppBarLayoutActivityUITest
import com.microsoft.fluentuidemo.demos.V2AvatarActivityUITest
import com.microsoft.fluentuidemo.demos.V2AvatarGroupActivityUITest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        ActionBarLayoutActivityUITest::class,
        AppBarLayoutActivityUITest::class,
        V2AvatarActivityUITest::class,
        V2AvatarGroupActivityUITest::class
)
class UiTestSuite