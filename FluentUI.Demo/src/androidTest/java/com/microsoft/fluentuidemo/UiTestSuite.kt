package com.microsoft.fluentuidemo

import com.microsoft.fluentuidemo.demos.*
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    V2AppBarLayoutActivityUITest::class,
    V2AvatarActivityUITest::class,
    V2AvatarCarouselActivityUITest::class,
    V2AvatarGroupActivityUITest::class,
    V2BadgeActivityUITest::class,
    V2BannerUITest::class,
    V2BottomDrawerUITest::class,
    V2BottomSheetActivityUITest::class,
    V2ButtonsActivityUITest::class,
    V2CardNudgeActivityUITest::class,
    V2CardUITest::class,
    V2CitationUITest::class,
    V2DialogActivityUITest::class,
    V2DrawerActivityUITest::class,
    V2LabelUITest::class,
    V2ListItemUITest::class,
    V2PeoplePickerUITest::class,
    V2PersonaChipActivityUITest::class,
    V2PersonaListActivityUITest::class,
    V2PersonaUITest::class,
    V2ProgressIndicatorUITest::class,
    V2ScaffoldActivityUITest::class,
    V2SegmentedControlActivityUITest::class,
    V2ShimmerUITest::class,
    V2SnackbarActivityUITest::class,
    V2TabBarActivityUITest::class,
    V2TextFieldActivityUITest::class,
    V2ToolTipActivityUITest::class,
    V2ToolTipUITest::class
)
class UiTestSuite