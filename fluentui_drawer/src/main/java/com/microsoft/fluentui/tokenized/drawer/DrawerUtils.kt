package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.animation.core.TweenSpec
import androidx.compose.ui.unit.dp

val EndDrawerPadding = 56.dp
val DrawerVelocityThreshold = 400.dp

val AnimationSpec = TweenSpec<Float>(durationMillis = 256)

const val DrawerOpenFraction = 0.5f

//Tag use for testing
const val DRAWER_HANDLE_TAG = "Fluent Drawer Handle"
const val DRAWER_CONTENT_TAG = "Fluent Drawer Content"
const val DRAWER_SCRIM_TAG = "Fluent Drawer Scrim"

//Drawer Handle height + padding
val DrawerHandleHeightOffset = 20.dp

/**
 * Possible values of [DrawerState].
 */
enum class DrawerValue {
    /**
     * The state of the drawer when it is closed.
     */
    Closed,

    /**
     * The state of the drawer when it is open.
     */
    Open,

    /**
     * The state of the bottom drawer when it is expanded (i.e. at 100% height).
     */
    Expanded
}
