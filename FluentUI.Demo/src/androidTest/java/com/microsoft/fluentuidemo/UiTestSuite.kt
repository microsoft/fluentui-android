package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ActionBarLayoutActivityUITest::class,
    AppBarLayoutActivityUITest::class,
    V2AvatarActivityUITest::class,
    V2AvatarGroupActivityUITest::class,
    V2DrawerActivityUITest::class,
    V2SnackbarActivityUITest::class,
    V2CardNudgeActivityUITest::class
)
class UiTestSuite