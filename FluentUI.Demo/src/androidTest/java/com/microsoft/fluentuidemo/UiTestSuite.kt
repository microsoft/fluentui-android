package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.ActionBarLayoutActivityUITest
import com.microsoft.fluentuidemo.demos.AppBarLayoutActivityUITest
import org.junit.runner.*
import org.junit.runners.*

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ActionBarLayoutActivityUITest::class,
    AppBarLayoutActivityUITest::class
)
class UiTestSuite {
}