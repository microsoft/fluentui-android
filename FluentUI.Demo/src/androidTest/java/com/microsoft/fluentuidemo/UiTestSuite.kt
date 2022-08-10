package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.ActionBarLayoutActivityUITest
import com.microsoft.fluentuidemo.demos.AppBarLayoutActivityUITest
import com.microsoft.fluentuidemo.demos.V2AvatarGroupViewActivityUITest
import com.microsoft.fluentuidemo.demos.V2AvatarViewActivityUITest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        ActionBarLayoutActivityUITest::class,
        AppBarLayoutActivityUITest::class,
        V2AvatarViewActivityUITest::class,
        V2AvatarGroupViewActivityUITest::class
)
class UiTestSuite