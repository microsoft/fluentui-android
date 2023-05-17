package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ActionBarLayoutActivityUITest::class,
    AppBarLayoutActivityUITest::class,
    V2AvatarActivityUITest::class,
    V2AvatarCarouselActivityUITest::class,
    V2AvatarGroupActivityUITest::class,
    V2CardUITest::class,
    V2CardNudgeActivityUITest::class,
    V2CitationUITest::class,
    V2DrawerActivityUITest::class,
    V2LabelUITest::class,
    V2ListItemActivityUITest::class,
    V2PersonaUITest::class,
    V2PersonaChipActivityUITest::class,
    V2PersonaListActivityUITest::class,
    V2ProgressIndicatorUITest::class,
    V2ScaffoldActivityUITest::class,
    V2ShimmerUITest::class,
    V2SnackbarActivityUITest::class
)
class UiTestSuite