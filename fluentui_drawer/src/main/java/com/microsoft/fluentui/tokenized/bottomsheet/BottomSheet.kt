/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.tokenized.bottomsheet

import android.content.Context
import android.content.res.Configuration
import android.view.*
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.*
import com.microsoft.fluentui.compose.FixedThreshold
import com.microsoft.fluentui.compose.SwipeableDefaults
import com.microsoft.fluentui.compose.SwipeableState
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BottomSheetInfo
import com.microsoft.fluentui.theme.token.controlTokens.BottomSheetTokens
import com.microsoft.fluentui.tokenized.calculateFraction
import com.microsoft.fluentui.util.dpToPx
import com.microsoft.fluentui.util.pxToDp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import com.microsoft.fluentui.tokenized.Scrim

/**
 * Possible values of [BottomSheetState].
 */

enum class BottomSheetValue {
    /**
     * The bottom sheet is not visible.
     */
    Hidden,

    /**
     * The bottom sheet is visible at full height if its height is less than 50% of screen.
     * If its height more than 50% then its visible to 50% of screen height.
     */
    Shown,

    /**
     * The bottom sheet is partially visible at 50% of the screen height. This state is only
     * enabled if the height of the bottom sheet is more than 50% of the screen height.
     */
    Expanded
}

/**
 * State of the [BottomSheet] composable.
 *
 * @param initialValue The initial value of the state which is set to [BottomSheetValue.Hidden].
 * @param animationSpec The default animation that will be used to animate to a new state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
class BottomSheetState(
    internal val initialValue: BottomSheetValue = BottomSheetValue.Hidden,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (BottomSheetValue) -> Boolean = { true }
) : SwipeableState<BottomSheetValue>(
    initialValue = initialValue,
    animationSpec = animationSpec,
    confirmStateChange = confirmStateChange
) {
    /**
     * Whether the bottom sheet is visible.
     */
    var isVisible: Boolean = false
        get() = currentValue != BottomSheetValue.Hidden

    internal val hasExpandedState: Boolean
        get() = anchors.values.contains(BottomSheetValue.Expanded)

    /**
     * Fully expand the bottom sheet with animation and suspend until it if fully expanded or
     * animation has been cancelled.
     * *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun expand() {
        val targetValue = when {
            hasExpandedState -> BottomSheetValue.Expanded
            else -> BottomSheetValue.Shown
        }
        animateTo(targetValue = targetValue)
    }

    /**
     * Show the bottom sheet with animation and suspend until it's shown. The bottom sheet will have
     * peek height visibility.
     *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun show() = animateTo(BottomSheetValue.Shown)

    /**
     * Hide the bottom sheet with animation and suspend until it if fully hidden or animation has
     * been cancelled.
     *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun hide() {
        try {
            animateTo(BottomSheetValue.Hidden)
        } finally {
            isVisible = false
        }
    }

    companion object {
        /**
         * The default [Saver] implementation for [BottomSheetState].
         */
        fun Saver(
            animationSpec: AnimationSpec<Float>, confirmStateChange: (BottomSheetValue) -> Boolean
        ): Saver<BottomSheetState, *> = Saver(save = { it.currentValue }, restore = {
            BottomSheetState(
                initialValue = it,
                animationSpec = animationSpec,
                confirmStateChange = confirmStateChange
            )
        })
    }
}

/**
 * Create a [BottomSheetState] and [remember] it.
 *
 * @param initialValue The initial value of the state.
 * @param animationSpec The default animation that will be used to animate to a new state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Composable
fun rememberBottomSheetState(
    initialValue: BottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (BottomSheetValue) -> Boolean = { true }
): BottomSheetState {
    return rememberSaveable(
        initialValue, animationSpec, confirmStateChange, saver = BottomSheetState.Saver(
            animationSpec = animationSpec, confirmStateChange = confirmStateChange
        )
    ) {
        BottomSheetState(
            initialValue = initialValue,
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    }
}

//Tag use for testing
const val BOTTOMSHEET_HANDLE_TAG = "Fluent Bottom Sheet Handle"
const val BOTTOMSHEET_CONTENT_TAG = "Fluent Bottom Sheet Content"
const val BOTTOMSHEET_SCRIM_TAG = "Fluent Bottom Sheet Scrim"

private const val BottomSheetOpenFraction = 0.5f

/**
 *
 * Bottom sheets present a set of choices while blocking interaction with the rest of the
 * screen. They are an alternative to inline menus and simple dialogs, providing
 * additional room for content, iconography, and actions.
 *
 *
 * @param sheetContent The content of the BottomSheet.
 * @param modifier Optional [Modifier] for the entire component.
 * @param sheetState The state of the bottom sheet.
 * @param expandable if true BottomSheet would expand on drag else BottomSheet open till
 * peeked/wrapped height. The default value is true
 * @param peekHeight The visible height of the BottomSheet in [BottomSheetValue.Shown] state. The
 * peek height should be less than or equal to half screen height. If more than half of the screen
 * height provided, then peek height would be consider half of the screen height.
 * @param scrimVisible create obscures background when scrim visible set to true when the
 * BottomSheet expand. Scrim also blocks interaction with the rest of the screen
 * when visible. The default value is true
 * @param showHandle if true, the handle would be visible on top of the BottomSheet. The default
 * value is true.
 * @param slideOver if true, then sheetContent would be drawn in full length & it just get slided
 * in the visible region. If false then, the sheetContainer placed at the bottom & its height could be at peekHeight, fullheight or hidden when dragged by Handle or swipe down.
 * @param enableSwipeDismiss if false, bottomSheet will not be dismissed after swipe down gesture. Default value is false.
 * @param stickyThresholdUpward The threshold for the upward drag gesture till which the sheet behaves sticky. Default value is 56f.
 * @param stickyThresholdDownward The threshold for the downward drag gesture till which the sheet behaves sticky. Default value is 56f.y
 * @param bottomSheetTokens tokens to provide appearance values. If not provided then bottomSheet
 * tokens will be picked from [AppThemeController]
 * @param content The content of rest of the screen.
 */

@Composable
fun BottomSheet(
    sheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: BottomSheetState = rememberBottomSheetState(BottomSheetValue.Hidden),
    expandable: Boolean = true,
    peekHeight: Dp = 110.dp,
    scrimVisible: Boolean = false,
    showHandle: Boolean = true,
    slideOver: Boolean = true,
    enableSwipeDismiss: Boolean = false,
    preventDismissalOnScrimClick: Boolean = false,  // if true, the sheet will not be dismissed when the scrim is clicked
    stickyThresholdUpward: Float = 56f,
    stickyThresholdDownward: Float = 56f,
    bottomSheetTokens: BottomSheetTokens? = null,
    onDismiss: () -> Unit = {}, // callback to be invoked after the sheet is closed
    content: @Composable () -> Unit
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val tokens = bottomSheetTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BottomSheetControlType] as BottomSheetTokens

    val bottomSheetInfo = BottomSheetInfo()
    val sheetShape: Shape = RoundedCornerShape(
        topStart = tokens.cornerRadius(bottomSheetInfo),
        topEnd = tokens.cornerRadius(bottomSheetInfo)
    )
    val sheetElevation: Dp = tokens.elevation(bottomSheetInfo)
    val sheetBackgroundColor: Brush = tokens.backgroundBrush(bottomSheetInfo)
    val sheetHandleColor: Color = tokens.handleColor(bottomSheetInfo)
    val scrimOpacity: Float = tokens.scrimOpacity(bottomSheetInfo)
    val scrimColor: Color = tokens.scrimColor(bottomSheetInfo).copy(alpha = scrimOpacity)

    val scope = rememberCoroutineScope()
    val maxLandscapeWidth: Float = tokens.maxLandscapeWidth(bottomSheetInfo)

    BoxWithConstraints(modifier) {
        val fullHeight = constraints.maxHeight.toFloat()
        val sheetHeightState = remember(sheetContent.hashCode()) { mutableStateOf<Float?>(null) }

        Box(
            Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .semantics {
                    if (!sheetState.isVisible) {
                        expand {
                            if (sheetState.confirmStateChange(BottomSheetValue.Shown)) {
                                scope.launch { sheetState.show() }
                            }
                            true
                        }
                    }
                }) {
            content()
            if (scrimVisible) {
                Scrim(
                    color = scrimColor,
                    onClose = {
                        if (sheetState.confirmStateChange(BottomSheetValue.Hidden)) {
                            scope.launch { sheetState.hide() }
                        }
                    },
                    fraction = {
                        if (sheetState.anchors.isEmpty() || (sheetHeightState.value != null && sheetHeightState.value == 0f)) {
                            0.toFloat()
                        } else {
                            val targetValue: BottomSheetValue = if (slideOver) {
                                if (sheetState.anchors.entries.firstOrNull { it.value == BottomSheetValue.Expanded } != null) {
                                    BottomSheetValue.Expanded
                                } else if (sheetState.anchors.entries.firstOrNull { it.value == BottomSheetValue.Shown } != null) {
                                    BottomSheetValue.Shown
                                } else {
                                    BottomSheetValue.Hidden
                                }
                            } else {
                                BottomSheetValue.Shown
                            }
                            calculateFraction(
                                sheetState.anchors.entries.firstOrNull { it.value == BottomSheetValue.Hidden }?.key!!,
                                sheetState.anchors.entries.firstOrNull { it.value == targetValue }?.key!!,
                                sheetState.offset.value
                            )
                        }
                    },
                    open = sheetState.isVisible,
                    onScrimClick = onDismiss,
                    preventDismissalOnScrimClick = preventDismissalOnScrimClick,
                    tag = BOTTOMSHEET_SCRIM_TAG
                )
            }
        }
        val configuration = LocalConfiguration.current

        Box(
            Modifier
                .align(Alignment.TopCenter)
                .animateContentSize()
                .fillMaxWidth(
                    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) maxLandscapeWidth
                    else 1F
                )
                .nestedScroll(
                    if (!enableSwipeDismiss && sheetState.offset.value >= (fullHeight - dpToPx(
                            peekHeight
                        ))
                    ) sheetState.NonDismissiblePostDownNestedScrollConnection
                    else if (slideOver) sheetState.PreUpPostDownNestedScrollConnection
                    else sheetState.PostDownNestedScrollConnection
                )
                .offset {
                    val y = if (sheetState.anchors.isEmpty()) {
                        // if we don't know our anchors yet, render the sheet as hidden
                        fullHeight.roundToInt()
                    } else {
                        // if we do know our anchors, respect them
                        sheetState.offset.value.roundToInt()
                    }
                    IntOffset(0, y)
                }
                .bottomSheetSwipeable(
                    sheetState,
                    expandable,
                    stickyThresholdDownward,
                    stickyThresholdUpward,
                    peekHeight,
                    fullHeight,
                    sheetHeightState.value,
                    slideOver
                )
                .onGloballyPositioned {
                    if (slideOver) {
                        val originalSize = it.size.height.toFloat()
                        sheetHeightState.value = if (expandable) {
                            originalSize
                        } else {
                            min(
                                originalSize,
                                min(dpToPx(peekHeight), fullHeight * BottomSheetOpenFraction)
                            )
                        }
                    }
                }
                .sheetHeight(
                    expandable, slideOver, fullHeight, peekHeight, sheetState
                )
                .clip(sheetShape)
                .shadow(sheetElevation)
                .background(sheetBackgroundColor)
                .semantics(mergeDescendants = false) {
                    if (sheetState.isVisible) {
                        if (enableSwipeDismiss) {
                            dismiss {
                                if (sheetState.confirmStateChange(BottomSheetValue.Hidden)) {
                                    scope.launch { sheetState.hide() }
                                }
                                onDismiss()
                                true
                            }
                        }
                        if (sheetState.currentValue == BottomSheetValue.Shown) {
                            expand {
                                if (sheetState.confirmStateChange(BottomSheetValue.Expanded)) {
                                    scope.launch { sheetState.expand() }
                                }
                                true
                            }
                        } else if (sheetState.hasExpandedState) {
                            collapse {
                                if (sheetState.confirmStateChange(BottomSheetValue.Shown)) {
                                    scope.launch { sheetState.show() }
                                }
                                true
                            }
                        }
                    }
                },
        ) {
            Column {
                if (showHandle) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            //TODO : Revisit SwipeableState usage across module to abstract out common modifier.
                            .draggable(
                                orientation = Orientation.Vertical,
                                state = rememberDraggableState { delta ->
                                    if (!enableSwipeDismiss && sheetState.offset.value >= (fullHeight - dpToPx(
                                            peekHeight
                                        ))
                                    ) {
                                        if (delta < 0) {
                                            sheetState.performDrag(delta)
                                        }
                                    } else sheetState.performDrag(delta)
                                },
                                onDragStopped = { velocity ->
                                    launch {
                                        sheetState.performFling(velocity)
                                        if (!sheetState.isVisible) {
                                            if (enableSwipeDismiss) {
                                                scope.launch { sheetState.hide() }
                                                onDismiss()
                                            } else {
                                                scope.launch { sheetState.show() }
                                            }
                                        }
                                    }
                                },
                            )
                            .testTag(BOTTOMSHEET_HANDLE_TAG)
                    ) {
                        val collapsed = LocalContext.current.resources.getString(R.string.collapsed)
                        val expanded = LocalContext.current.resources.getString(R.string.expanded)
                        val accessibilityManager =
                            LocalContext.current.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager
                        Icon(painterResource(id = R.drawable.ic_drawer_handle),
                            contentDescription = if (sheetState.currentValue == BottomSheetValue.Expanded || (sheetState.hasExpandedState && sheetState.isVisible)) {
                                LocalContext.current.resources.getString(R.string.drag_handle)
                            } else {
                                null
                            },
                            tint = sheetHandleColor,
                            modifier = Modifier.clickable(
                                enabled = sheetState.hasExpandedState,
                                role = Role.Button,
                                onClickLabel = if (sheetState.currentValue == BottomSheetValue.Expanded) {
                                    LocalContext.current.resources.getString(R.string.collapse)
                                } else {
                                    if (sheetState.hasExpandedState && sheetState.isVisible) LocalContext.current.resources.getString(
                                        R.string.expand
                                    ) else null
                                }
                            ) {
                                if (sheetState.currentValue == BottomSheetValue.Expanded) {
                                    if (sheetState.confirmStateChange(BottomSheetValue.Shown)) {
                                        scope.launch { sheetState.show() }
                                        accessibilityManager?.let { manager ->
                                            if (manager.isEnabled) {
                                                val event =
                                                    AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT)
                                                        .apply {
                                                            text.add(collapsed)
                                                        }
                                                manager.sendAccessibilityEvent(event)
                                            }
                                        }
                                    }
                                } else if (sheetState.hasExpandedState) {
                                    if (sheetState.confirmStateChange(BottomSheetValue.Expanded)) {
                                        scope.launch { sheetState.expand() }
                                        accessibilityManager?.let { manager ->
                                            if (manager.isEnabled) {
                                                val event =
                                                    AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT)
                                                        .apply {
                                                            text.add(expanded)
                                                        }
                                                manager.sendAccessibilityEvent(event)
                                            }
                                        }
                                    }
                                }
                            })
                    }
                }
                Column(modifier = Modifier
                    .testTag(BOTTOMSHEET_CONTENT_TAG)
                    .then(if (slideOver) Modifier.onFocusChanged { focusState ->
                        if (focusState.hasFocus && sheetState.currentValue != BottomSheetValue.Expanded) {        // this expands the sheet when the content is focused
                            scope.launch { sheetState.expand() }
                        }
                    } else Modifier.fillMaxSize()), content = {
                        sheetContent()
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    fullHeight.dp
                                )
                                .background(sheetBackgroundColor)
                                .onGloballyPositioned {
                                    sheetHeightState.value = sheetHeightState.value?.minus(it.size.height.toFloat())
                                }
                        )
                    })
            }
        }
    }
}

private fun Modifier.bottomSheetSwipeable(
    sheetState: BottomSheetState,
    expandable: Boolean,
    stickyThresholdDownward: Float,
    stickyThresholdUpward: Float,
    peekHeight: Dp,
    fullHeight: Float,
    sheetHeight: Float?,
    slideOver: Boolean

): Modifier {
    var peekHeightPx = min(dpToPx(peekHeight), fullHeight * BottomSheetOpenFraction)
    val keyCorrection = 0.05f
    val modifier = if (slideOver) {
        if (sheetHeight != null && sheetHeight != 0f) {
            val anchors = if (!expandable) {
                mapOf(
                    fullHeight to BottomSheetValue.Hidden, (fullHeight - min(
                        sheetHeight, peekHeightPx
                    )) + keyCorrection to BottomSheetValue.Shown
                )
            } else if (sheetHeight <= peekHeightPx) {
                mapOf(
                    fullHeight to BottomSheetValue.Hidden,
                    (fullHeight - sheetHeight) + keyCorrection to BottomSheetValue.Shown
                )
            } else {
                mapOf(
                    fullHeight to BottomSheetValue.Hidden,
                    (fullHeight - peekHeightPx) + keyCorrection to BottomSheetValue.Shown,
                    (max(
                        0f, fullHeight - sheetHeight
                    )) + (keyCorrection * 2) to BottomSheetValue.Expanded
                )
            }
            if (sheetState.initialValue == BottomSheetValue.Expanded && anchors.entries.firstOrNull { it.value == BottomSheetValue.Expanded } == null) {
                throw IllegalArgumentException(
                    "BottomSheet initial value must not be set to Expanded " + "if the whole content is visible in Shown state itself"
                )
            }
            Modifier.swipeable(
                state = sheetState,
                anchors = anchors,
                orientation = Orientation.Vertical,
                enabled = sheetState.currentValue != BottomSheetValue.Hidden,
                thresholds = { from, to ->
                    val fromKey = anchors.entries.firstOrNull { it.value == from }?.key
                    val toKey = anchors.entries.firstOrNull { it.value == to }?.key

                    if (fromKey == null || toKey == null) {
                        FixedThreshold(56.dp)
                    } //in case of null defaulting to 56.dp threshold
                    else if (fromKey < toKey) {
                        FixedThreshold(stickyThresholdDownward.dp)
                    } // Threshold for drag down
                    else {
                        FixedThreshold(stickyThresholdUpward.dp)
                    } // Threshold for drag up
                },
                resistance = null
            )

        } else {
            Modifier
        }
    } else {
        peekHeightPx = dpToPx(peekHeight)
        val anchors = if (expandable) {
            mapOf(
                fullHeight to BottomSheetValue.Hidden,
                fullHeight - peekHeightPx to BottomSheetValue.Shown,
                0F to BottomSheetValue.Expanded
            )
        } else {
            mapOf(
                fullHeight to BottomSheetValue.Hidden,
                fullHeight - peekHeightPx to BottomSheetValue.Shown
            )
        }
        Modifier.swipeable(
            state = sheetState,
            anchors = anchors,
            orientation = Orientation.Vertical,
            enabled = sheetState.currentValue != BottomSheetValue.Hidden,
            thresholds = { from, to ->
                val fromKey = anchors.entries.firstOrNull { it.value == from }?.key
                val toKey = anchors.entries.firstOrNull { it.value == to }?.key

                if (fromKey == null || toKey == null) {
                    FixedThreshold(56.dp)
                } //in case of null defaulting to 56 as a fallback
                else if (fromKey < toKey) {
                    FixedThreshold(stickyThresholdDownward.dp)
                } // Threshold for drag down
                else {
                    FixedThreshold(stickyThresholdUpward.dp)
                } // Threshold for drag up
            },
            resistance = null
        )

    }

    return this.then(modifier)
}

private fun Modifier.sheetHeight(
    expandable: Boolean,
    slideOver: Boolean,
    fullHeight: Float,
    peekHeight: Dp,
    sheetState: BottomSheetState
): Modifier {
    val modifier = if (slideOver) {
        if (expandable) {
            Modifier
        } else {
            Modifier.heightIn(
                0.dp, pxToDp(min(fullHeight * BottomSheetOpenFraction, dpToPx(peekHeight)))
            )
        }
    } else {
        Modifier.heightIn(0.dp, pxToDp(fullHeight - sheetState.offset.value))
    }
    return this.then(modifier)
}
